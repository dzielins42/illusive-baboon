package pl.dzielins42.illusivebaboon.android.data.interactor;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;
import pl.dzielins42.illusivebaboon.android.R;
import pl.dzielins42.illusivebaboon.android.data.HierarchyData;
import pl.dzielins42.illusivebaboon.android.data.local.JSONHierarchyReader;
import pl.dzielins42.illusivebaboon.android.data.local.repository.GeneratorHierarchyRepository;

@Singleton
public class GeneratorHierarchyRepositoryInteractor {
    private static final String TAG = GeneratorHierarchyRepositoryInteractor.class.getSimpleName();

    private final Context mContext;
    private final GeneratorHierarchyRepository mRepository;

    @Inject
    public GeneratorHierarchyRepositoryInteractor(
            Context context,
            GeneratorHierarchyRepository repository,
            JSONHierarchyReader jsonHierarchyReader
    ) {
        mContext = context;
        mRepository = repository;

        // Load local data
        List<Pair<String, HierarchyData>> data = null;
        try (InputStream is = mContext.getResources().openRawResource(R.raw.local)) {
            Reader reader = new InputStreamReader(is, "UTF-8");

            data = jsonHierarchyReader.read(reader);

            reader.close();
        } catch (IOException e) {
            Log.e(TAG, "GeneratorHierarchyRepositoryInteractor: ", e);
        }

        if (data != null && !data.isEmpty()) {
            for (Pair<String, HierarchyData> item : data) {
                mRepository.add(item.first, item.second);
            }
        }
    }

    public Maybe<List<HierarchyData>> list(String path) {
        return Maybe.just(mRepository.getChildren(path));
    }

    public Maybe<HierarchyData> get(String path) {
        return Maybe.just(mRepository.get(path));
    }
}
