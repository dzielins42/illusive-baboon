package pl.dzielins42.illusivebaboon.android.view.details;

import android.text.TextUtils;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import pl.dzielins42.illusivebaboon.android.data.interactor.GeneratorHierarchyRepositoryInteractor;
import pl.dzielins42.illusivebaboon.android.data.interactor.GeneratorRepositoryInteractor;
import pl.dzielins42.illusivebaboon.android.data.interactor.GeneratorResultsInteractor;

@Singleton
public class GeneratorDetailsPresenter
        extends MviBasePresenter<GeneratorDetailsView, GeneratorDetailsViewModel> {

    private static final String TAG = GeneratorDetailsPresenter.class.getSimpleName();

    private static final GeneratorDetailsViewModel INITIAL_VIEW_MODEL =
            GeneratorDetailsViewModel.builder().build();

    @Inject
    GeneratorResultsInteractor mGeneratorResultsInteractor;
    @Inject
    GeneratorRepositoryInteractor mGeneratorRepositoryInteractor;
    @Inject
    GeneratorHierarchyRepositoryInteractor mGeneratorHierarchyRepositoryInteractor;

    @Inject
    public GeneratorDetailsPresenter() {
        super(INITIAL_VIEW_MODEL);
    }

    @Override
    protected void bindIntents() {
        // Intents generate Patches which are applied to previous State producing new State

        subscribeViewState(
                intent(GeneratorDetailsView::eventsObservable)
                        .publish(event -> process(event))
                        .doOnError(throwable -> Log.e(TAG, "Error: ", throwable))
                        .scan(INITIAL_VIEW_MODEL, (model, patch) -> patch.apply(model)),
                GeneratorDetailsView::render
        );
    }

    private Observable<DetailsViewPatch> process(Observable<DetailsEvent> shared) {
        Observable<DetailsViewPatch> init = shared.ofType(DetailsEvent.Initialize.class)
                .flatMap(event -> mGeneratorHierarchyRepositoryInteractor.get(event.getPath()).toObservable())
                .map(results -> new DetailsViewPatch.AddMetaData(
                             results.getId(),
                             results.getName(),
                             results.getDescription()
                     )
                );
                //.map(event -> new DetailsViewPatch.AddMetaData(event.getGeneratorId()));

        Observable<DetailsViewPatch> generate = shared.ofType(DetailsEvent.Generate.class)
                .flatMap(event -> mGeneratorResultsInteractor.generate(stripPath(event.getGeneratorId()), event.getCount()).toObservable())
                .map(results -> new DetailsViewPatch.SetResults(results));

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
