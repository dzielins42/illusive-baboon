package pl.dzielins42.illusivebaboon.android.view.list;

import java.util.List;

import lombok.Value;
import lombok.experimental.Accessors;
import pl.dzielins42.illusivebaboon.android.data.HierarchyData;

public interface ListViewPatch {

    GeneratorListViewModel apply(GeneratorListViewModel viewModel);

    @Value
    @Accessors(prefix = "m")
    final class SetItems implements ListViewPatch {

        private final String mPath;
        private final List<HierarchyData> mItems;

        @Override
        public GeneratorListViewModel apply(GeneratorListViewModel viewModel) {
            return viewModel.toBuilder()
                    .path(mPath)
                    .clearItems()
                    .items(mItems)
                    .build();
        }
    }
}
