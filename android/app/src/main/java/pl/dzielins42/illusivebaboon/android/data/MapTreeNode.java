package pl.dzielins42.illusivebaboon.android.data;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
@Builder
public class MapTreeNode<T> {
    @NonNull
    private String mId;
    private MapTreeNode<T> mParent;
    @Builder.Default
    private Map<String, MapTreeNode<T>> mChildren = new HashMap<>();

    private T mData;
}
