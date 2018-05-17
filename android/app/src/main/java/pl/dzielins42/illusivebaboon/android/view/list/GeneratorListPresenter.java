package pl.dzielins42.illusivebaboon.android.view.list;

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class GeneratorListPresenter
        extends MviBasePresenter<GeneratorListView, GeneratorListState> {

    @Inject
    public GeneratorListPresenter() {
        super();
    }

    @Override
    protected void bindIntents() {
        Observable<GeneratorListState> intentsObservable = Observable.interval(1, TimeUnit.SECONDS)
                .map(i -> new GeneratorListState(i));

        // Intents generate Patches which are applied to previous State producing new State

        subscribeViewState(intentsObservable, GeneratorListView::render);
    }
}
