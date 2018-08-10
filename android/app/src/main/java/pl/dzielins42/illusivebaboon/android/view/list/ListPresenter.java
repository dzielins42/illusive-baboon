package pl.dzielins42.illusivebaboon.android.view.list;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import pl.dzielins42.illusivebaboon.android.data.interactor.GeneratorHierarchyRepositoryInteractor;
import pl.dzielins42.illusivebaboon.android.view.NavigationController;

public class ListPresenter
        extends MviBasePresenter<ListView, ListViewModel> {

    private static final String TAG = ListPresenter.class.getSimpleName();

    private static final ListViewModel INITIAL_VIEW_MODEL =
            ListViewModel.builder().build();

    private ListView mView;

    @Inject
    GeneratorHierarchyRepositoryInteractor mGeneratorHierarchyRepositoryInteractor;
    @Inject
    NavigationController mNavigationController;

    @Inject
    public ListPresenter() {
        super();
    }

    @Override
    protected void bindIntents() {
        // Intents generate Patches which are applied to previous State producing new State

        subscribeViewState(
                intent(ListView::eventsObservable)
                        .doOnNext(event -> Log.d(TAG, String.valueOf(event)))
                        .publish(event -> process(event))
                        .doOnError(throwable -> Log.e(TAG, "Error: ", throwable))
                        .scan(INITIAL_VIEW_MODEL, (model, patch) -> patch.apply(model))
                        // Skip INITIAL_VIEW_MODEL
                        .skip(1),
                ListView::render
        );
    }

    private Observable<ListViewPatch> process(Observable<ListEvent> shared) {
        Observable<ListViewPatch> initialize = shared.ofType(ListEvent.Initialize.class)
                .flatMap(event -> mGeneratorHierarchyRepositoryInteractor.get(event.getPath())
                        .map(
                                hierarchyData -> new ListViewPatch.SetData(
                                        event.getPath(),
                                        hierarchyData.getData(),
                                        hierarchyData.getChildren()
                                )
                        )
                        .toObservable()
                );

        Observable<ListViewPatch> navigateTo = shared.ofType(ListEvent.NavigateTo.class)
                .map(event -> {
                    final String generatorId = event.getDestination().getGeneratorId();
                    final String path = event.getDestination().getPath();
                    if (TextUtils.isEmpty(generatorId)) {
                        mView.navigateToList(path);
                    } else {
                        mView.navigateToResults(path);
                    }
                    // No change
                    return (ListViewPatch) viewModel -> viewModel;
                });

        return Observable.merge(initialize, navigateTo);
    }

    @Override
    public void attachView(@NonNull ListView view) {
        super.attachView(view);
        mView = view;
    }
}
