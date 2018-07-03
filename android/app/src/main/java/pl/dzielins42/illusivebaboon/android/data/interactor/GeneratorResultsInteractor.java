package pl.dzielins42.illusivebaboon.android.data.interactor;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import pl.dzielins42.dmtools.generator.name.NameGenerator;
import pl.dzielins42.dmtools.generator.name.NameGeneratorOptions;
import pl.dzielins42.dmtools.util.random.RandomAdapter;

//TODO It's a stub!
@Singleton
public class GeneratorResultsInteractor {

    private final NameGenerator mGenerator;
    private final NameGeneratorOptions mOptions;

    @Inject
    public GeneratorResultsInteractor() {
        mOptions = new NameGeneratorOptions(new RandomAdapter());
        mGenerator = null;
    }

    public Flowable<List<String>> generate(@NonNull String generatorId, int count) {
        return Flowable.never();//mGenerator.generate(mOptions, count);
    }
}
