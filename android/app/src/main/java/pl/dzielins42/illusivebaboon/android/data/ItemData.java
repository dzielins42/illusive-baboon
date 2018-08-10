package pl.dzielins42.illusivebaboon.android.data;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(prefix = "m")
@Builder
public class ItemData {
    @NonNull
    private final String mPath;
    @NonNull
    private final String mName;
    private final String mGeneratorId;
    private final String mDescription;
}
