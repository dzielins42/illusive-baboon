package pl.dzielins42.illusivebaboon.android.data.interactor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import pl.dzielins42.illusivebaboon.android.data.NameGeneratorWrapper;
import pl.dzielins42.illusivebaboon.android.data.local.AssetGeneratorReader;
import pl.dzielins42.illusivebaboon.android.data.local.repository.GeneratorRepository;

@Singleton
public class GeneratorRepositoryInteractor {
    private static final String TAG = GeneratorRepositoryInteractor.class.getSimpleName();

    private final Context mContext;
    private final GeneratorRepository mGeneratorRepository;

    private boolean mAssetsLoaded = false;

    @Inject
    public GeneratorRepositoryInteractor(
            Context context,
            GeneratorRepository generatorRepository
    ) {
        mContext = context;
        mGeneratorRepository = generatorRepository;
    }

    public Maybe<NameGeneratorWrapper> get(@NonNull String id) {
        Log.d(TAG, "get() called with: id = [" + id + "]");

        Maybe<NameGeneratorWrapper> maybe;
        if (mGeneratorRepository.contains(id)) {
            maybe = Maybe.just(mGeneratorRepository.get(id));
        } else if (!mAssetsLoaded) {
            maybe = loadFromAssets()
                    .filter(generator -> id.equals(generator.getId()))
                    .firstElement()
                    .doFinally(() -> mAssetsLoaded = true);
        } else {
            // TODO error that generator is not available
            // Network call in the future?
            maybe = Maybe.empty();
        }

        return maybe;
    }

    Flowable<NameGeneratorWrapper> loadFromAssets() {
        final AssetGeneratorReader assetGeneratorReader = new AssetGeneratorReader(mContext);
        return assetGeneratorReader.read()
                .doOnNext(generator -> mGeneratorRepository.save(generator));
    }
}
