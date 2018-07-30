package pl.dzielins42.illusivebaboon.android.view.list;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import io.reactivex.Observable;

public interface GeneratorListView extends MvpView {

    void render(GeneratorListViewModel viewModel);

    Observable<ListEvent> eventsObservable();
}
