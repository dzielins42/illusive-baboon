package pl.dzielins42.illusivebaboon.android.view.details;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import pl.dzielins42.illusivebaboon.android.data.interactor.GeneratorResultsInteractor;

@Singleton
public class GeneratorDetailsPresenter
        extends MviBasePresenter<GeneratorDetailsView, GeneratorDetailsViewModel> {

    @Inject
    GeneratorResultsInteractor mGeneratorResultsInteractor;

    @Inject
    public GeneratorDetailsPresenter() {
        super(GeneratorDetailsViewModel.create());
    }

    @Override
    protected void bindIntents() {
        Observable<Integer> load = intent(GeneratorDetailsView::loadIntents).map(s -> {
            return 1;
        });

        // Intents generate Patches which are applied to previous State producing new State

        Observable<GeneratorDetailsViewModel> intents = mGeneratorResultsInteractor
                .generate("", 10)
                .map(results -> GeneratorDetailsViewModel.create().builder().setResults(results).build())
                .toObservable();

        subscribeViewState(intents, GeneratorDetailsView::render);
    }
}
