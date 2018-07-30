package pl.dzielins42.illusivebaboon.android.view.list;

import lombok.Value;
import lombok.experimental.Accessors;

public interface ListEvent {

    @Value
    @Accessors(prefix = "m")
    final class Initialize implements ListEvent {
    }
}
