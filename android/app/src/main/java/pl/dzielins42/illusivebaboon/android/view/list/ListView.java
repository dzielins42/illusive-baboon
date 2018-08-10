package pl.dzielins42.illusivebaboon.android.view.list;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import io.reactivex.Observable;

public interface ListView extends MvpView {

    void render(ListViewModel viewModel);

    void navigateToList(String path);

    void navigateToResults(String generatorId);

    Observable<ListEvent> eventsObservable();
}
