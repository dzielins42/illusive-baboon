package pl.dzielins42.illusivebaboon.android.data;

import android.support.annotation.NonNull;

import java.util.Collection;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import pl.dzielins42.dmtools.generator.name.NameGenerator;
import pl.dzielins42.dmtools.generator.name.NameGeneratorOptions;

@Value
@Accessors(prefix = "m")
@Builder
public class NameGeneratorWrapper implements NameGenerator {

    @NonNull
    private final String mId;
    @NonNull
    private final String mName;
    @NonNull
    private final String mDescription;
    @NonNull
    private final NameGenerator mGenerator;

    @Override
    public String generate(NameGeneratorOptions options) {
        return mGenerator.generate(options);
    }

    @Override
    public Collection<String> generate(NameGeneratorOptions options, int count) {
        return mGenerator.generate(options, count);
    }
}
