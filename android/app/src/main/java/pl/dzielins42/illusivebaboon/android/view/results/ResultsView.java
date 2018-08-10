package pl.dzielins42.illusivebaboon.android.view.results;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import io.reactivex.Observable;

public interface ResultsView extends MvpView {

    void render(ResultsViewModel viewModel);

    Observable<ResultsEvent> eventsObservable();
}
