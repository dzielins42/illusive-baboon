package pl.dzielins42.illusivebaboon.android.view.results;

import android.text.TextUtils;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import pl.dzielins42.illusivebaboon.android.data.interactor.GeneratorHierarchyRepositoryInteractor;
import pl.dzielins42.illusivebaboon.android.data.interactor.GeneratorRepositoryInteractor;
import pl.dzielins42.illusivebaboon.android.data.interactor.GeneratorResultsInteractor;

public class ResultsPresenter
        extends MviBasePresenter<ResultsView, ResultsViewModel> {

    private static final String TAG = ResultsPresenter.class.getSimpleName();

    private static final ResultsViewModel INITIAL_VIEW_MODEL =
            ResultsViewModel.builder().build();

    @Inject
    GeneratorResultsInteractor mGeneratorResultsInteractor;
    @Inject
    GeneratorRepositoryInteractor mGeneratorRepositoryInteractor;
    @Inject
    GeneratorHierarchyRepositoryInteractor mGeneratorHierarchyRepositoryInteractor;

    @Inject
    public ResultsPresenter() {
        super(INITIAL_VIEW_MODEL);
    }

    @Override
    protected void bindIntents() {
        // Intents generate Patches which are applied to previous State producing new State

        subscribeViewState(
                intent(ResultsView::eventsObservable)
                        .doOnNext(event -> Log.d(TAG, String.valueOf(event)))
                        .publish(event -> process(event))
                        .doOnError(throwable -> Log.e(TAG, "Error: ", throwable))
                        .scan(INITIAL_VIEW_MODEL, (model, patch) -> patch.apply(model))
                        // Skip INITIAL_VIEW_MODEL
                        .skip(1),
                ResultsView::render
        );
    }

    private Observable<ResultsViewPatch> process(Observable<ResultsEvent> shared) {
        Observable<ResultsViewPatch> init = shared.ofType(ResultsEvent.Initialize.class)
                .flatMap(event -> mGeneratorHierarchyRepositoryInteractor.get(event.getPath()).toObservable())
                .map(results -> new ResultsViewPatch.AddMetaData(
                             results.getData().getGeneratorId(),
                             results.getData().getName(),
                             results.getData().getDescription()
                     )
                );
                //.map(event -> new ResultsViewPatch.AddMetaData(event.getGeneratorId()));

        Observable<ResultsViewPatch> generate = shared.ofType(ResultsEvent.Generate.class)
                .flatMap(event -> mGeneratorResultsInteractor.generate(stripPath(event.getGeneratorId()), event.getCount()).toObservable())
                .map(results -> new ResultsViewPatch.SetResults(results));

        return Observable.merge(init, generate);
    }

    private String stripPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return path;
        }
        String[] parts = TextUtils.split(path, "/");
        return parts[parts.length - 1];
    }
}
