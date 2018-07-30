package pl.dzielins42.illusivebaboon.android.view.list;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import pl.dzielins42.illusivebaboon.android.data.interactor.GeneratorHierarchyRepositoryInteractor;

@Singleton
public class GeneratorListPresenter
        extends MviBasePresenter<GeneratorListView, GeneratorListViewModel> {

    private static final String TAG = GeneratorListPresenter.class.getSimpleName();

    private static final GeneratorListViewModel INITIAL_VIEW_MODEL =
            GeneratorListViewModel.builder().build();

    @Inject
    GeneratorHierarchyRepositoryInteractor mGeneratorHierarchyRepositoryInteractor;

    @Inject
    public GeneratorListPresenter() {
        super();
    }

    @Override
    protected void bindIntents() {
        // Intents generate Patches which are applied to previous State producing new State

        subscribeViewState(
                intent(GeneratorListView::eventsObservable)
                        .publish(event -> process(event))
                        .doOnError(throwable -> Log.e(TAG, "Error: ", throwable))
                        .scan(INITIAL_VIEW_MODEL, (model, patch) -> patch.apply(model)),
                GeneratorListView::render
        );
    }

    private Observable<ListViewPatch> process(Observable<ListEvent> shared) {
        Observable<ListViewPatch> init = shared.ofType(ListEvent.Initialize.class)
                .flatMap(event -> mGeneratorHierarchyRepositoryInteractor.list(null).toObservable())
                .map(results -> new ListViewPatch.SetItems(results));

        return init;
    }
}
