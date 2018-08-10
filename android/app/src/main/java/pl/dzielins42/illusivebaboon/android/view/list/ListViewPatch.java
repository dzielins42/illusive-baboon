package pl.dzielins42.illusivebaboon.android.view.list;

import java.util.List;

import lombok.Value;
import lombok.experimental.Accessors;
import pl.dzielins42.illusivebaboon.android.data.ItemData;

public interface ListViewPatch {

    ListViewModel apply(ListViewModel viewModel);

    @Value
    @Accessors(prefix = "m")
    final class SetData implements ListViewPatch {

        private final String mPath;
        private final ItemData mData;
        private final List<ItemData> mItems;

        @Override
        public ListViewModel apply(ListViewModel viewModel) {
            return viewModel.toBuilder()
                    .path(mPath)
                    .clearItems()
                    .items(mItems)
                    .build();
        }
    }
}
