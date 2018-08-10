package pl.dzielins42.illusivebaboon.android.view.list;

import java.util.List;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.Accessors;
import pl.dzielins42.illusivebaboon.android.data.ItemData;

@Value
@Accessors(prefix = "m")
@Builder(toBuilder = true)
public class ListViewModel {
    private final String mPath;
    @NonNull
    @Singular
    private final List<ItemData> mItems;
}
