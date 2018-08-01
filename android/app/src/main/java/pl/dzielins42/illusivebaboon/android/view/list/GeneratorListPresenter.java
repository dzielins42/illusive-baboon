package pl.dzielins42.illusivebaboon.android.view.list;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import pl.dzielins42.illusivebaboon.android.data.interactor.GeneratorHierarchyRepositoryInteractor;
import pl.dzielins42.illusivebaboon.android.view.NavigationController;

@Singleton
public class GeneratorListPresenter
        extends MviBasePresenter<GeneratorListView, GeneratorListViewModel> {

    private static final String TAG = GeneratorListPresenter.class.getSimpleName();

    private static final GeneratorListViewModel INITIAL_VIEW_MODEL =
            GeneratorListViewModel.builder().build();

    @Inject
    GeneratorHierarchyRepositoryInteractor mGeneratorHierarchyRepositoryInteractor;
    @Inject
    NavigationController mNavigationController;

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
        Observable<ListViewPatch> init = shared.ofType(ListEvent.Load.class)
                .flatMap(event -> mGeneratorHierarchyRepositoryInteractor.list(event.getPath())
                        .map(hierarchyData -> {
                            if (hierarchyData == null || hierarchyData.isEmpty()) {
                                mNavigationController.openDetails(event.getPath());
                                return new ListViewPatch.NoChange();
                            } else {
                                return new ListViewPatch.SetItems(
                                        event.getPath(), hierarchyData
                                );
                            }
                        })
                        .toObservable()
                );

        return init;
    }
}
