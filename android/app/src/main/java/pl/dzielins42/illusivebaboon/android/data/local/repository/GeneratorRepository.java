package pl.dzielins42.illusivebaboon.android.data.local.repository;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import pl.dzielins42.illusivebaboon.android.data.NameGeneratorWrapper;

@Singleton
public class GeneratorRepository {

    private final Map<String, NameGeneratorWrapper> mData = new HashMap<>();

    @Inject
    public GeneratorRepository() {
    }

    public NameGeneratorWrapper get(@NonNull String id) {
        return mData.get(id);
    }

    public void save(@NonNull NameGeneratorWrapper generator) {
        mData.put(generator.getId(), generator);
    }

    public boolean contains(@NonNull String id) {
        return mData.containsKey(id);
    }

    public boolean isEmpty() {
        return mData.isEmpty();
    }
}
