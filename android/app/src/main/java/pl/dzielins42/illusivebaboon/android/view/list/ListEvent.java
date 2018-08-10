package pl.dzielins42.illusivebaboon.android.view.list;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import pl.dzielins42.illusivebaboon.android.data.ItemData;

public interface ListEvent {

    @Value
    @Accessors(prefix = "m")
    @Builder
    final class Initialize implements ListEvent {
        private final String mPath;
    }

    @Value
    @Accessors(prefix = "m")
    @Builder
    final class NavigateTo implements ListEvent {
        private final ItemData mDestination;
    }
}
