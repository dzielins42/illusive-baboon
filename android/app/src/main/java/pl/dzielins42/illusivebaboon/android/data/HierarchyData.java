package pl.dzielins42.illusivebaboon.android.data;

import java.util.List;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(prefix = "m")
@Builder
public class HierarchyData {
    @NonNull
    private final ItemData mData;
    @Singular
    private final List<ItemData> mChildren;
}
