package pl.dzielins42.illusivebaboon.android.view.details;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import io.reactivex.Observable;

public interface GeneratorDetailsView extends MvpView {

    void render(GeneratorDetailsViewModel viewModel);

    Observable<String> loadIntents();
}
