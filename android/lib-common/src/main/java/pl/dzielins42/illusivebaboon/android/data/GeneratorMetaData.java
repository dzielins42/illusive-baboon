package pl.dzielins42.illusivebaboon.android.data;

import android.support.annotation.NonNull;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(prefix = "m")
@Builder
public class GeneratorMetaData {
    @NonNull
    private final String mId;
    @NonNull
    private final String mName;
    private final String mDescription;
}
