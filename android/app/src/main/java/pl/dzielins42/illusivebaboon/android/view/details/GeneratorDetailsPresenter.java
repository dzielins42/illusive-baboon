package pl.dzielins42.illusivebaboon.android.view.details;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import pl.dzielins42.illusivebaboon.android.data.interactor.GeneratorResultsInteractor;

@Singleton
public class GeneratorDetailsPresenter
        extends MviBasePresenter<GeneratorDetailsView, GeneratorDetailsViewModel> {

    private static final String TAG = GeneratorDetailsPresenter.class.getSimpleName();

    private static final GeneratorDetailsViewModel INITIAL_VIEW_MODEL =
            GeneratorDetailsViewModel.create();

    @Inject
    GeneratorResultsInteractor mGeneratorResultsInteractor;

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
                        .scan(INITIAL_VIEW_MODEL, (model, patch) -> patch.apply(model)),
                GeneratorDetailsView::render
        );
    }

    private Observable<DetailsViewPatch> process(Observable<DetailsEvent> shared) {
        Observable<DetailsViewPatch> init = shared.ofType(DetailsEvent.Initialize.class)
                .map(event -> new DetailsViewPatch.AddMetaData(event.getGeneratorId()));

        Observable<DetailsViewPatch> generate = shared.ofType(DetailsEvent.Generate.class)
                .flatMap(event -> mGeneratorResultsInteractor.generate(event.getGeneratorId(), event.getCount()).toObservable())
                .doOnError(throwable -> Log.e(TAG, "Error: ", throwable))
                .map(results -> new DetailsViewPatch.SetResults(results));

        return Observable.merge(init, generate);
    }
}
