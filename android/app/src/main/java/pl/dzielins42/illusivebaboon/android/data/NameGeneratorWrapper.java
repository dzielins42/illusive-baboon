package pl.dzielins42.illusivebaboon.android.data;

import android.support.annotation.NonNull;

import java.util.Collection;

import pl.dzielins42.dmtools.generator.name.NameGenerator;
import pl.dzielins42.dmtools.generator.name.NameGeneratorOptions;

public class NameGeneratorWrapper implements NameGenerator {

    private final String mId;
    private final NameGenerator mGenerator;

    public NameGeneratorWrapper(
            @NonNull String id,
            @NonNull NameGenerator generator
    ) {
        mId = id;
        mGenerator = generator;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Override
    public String generate(NameGeneratorOptions options) {
        return mGenerator.generate(options);
    }

    @Override
    public Collection<String> generate(NameGeneratorOptions options, int count) {
        return mGenerator.generate(options, count);
    }
}
