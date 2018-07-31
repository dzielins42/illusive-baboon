package pl.dzielins42.illusivebaboon.android.view.list;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

public interface ListEvent {

    @Value
    @Accessors(prefix = "m")
    @Builder
    final class Load implements ListEvent {
        private final String mPath;
    }
}
