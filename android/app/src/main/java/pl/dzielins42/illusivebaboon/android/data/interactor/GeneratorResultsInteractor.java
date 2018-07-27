package pl.dzielins42.illusivebaboon.android.data.interactor;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import pl.dzielins42.dmtools.generator.name.NameGeneratorOptions;
import pl.dzielins42.dmtools.util.random.RandomAdapter;

@Singleton
public class GeneratorResultsInteractor {

    private final GeneratorRepositoryInteractor mRepositoryInteractor;
    private final NameGeneratorOptions mOptions;

    @Inject
    public GeneratorResultsInteractor(GeneratorRepositoryInteractor repositoryInteractor) {
        mRepositoryInteractor = repositoryInteractor;
        mOptions = new NameGeneratorOptions(new RandomAdapter());
    }

    public Flowable<List<String>> generate(@NonNull String id, int count) {
        return mRepositoryInteractor.get(id)
                .map(generator -> {
                    List<String> asList = new ArrayList<>(generator.generate(mOptions, count));
                    return asList;
                })
                .toFlowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
