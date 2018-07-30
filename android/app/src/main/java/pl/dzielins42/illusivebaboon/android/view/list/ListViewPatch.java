package pl.dzielins42.illusivebaboon.android.view.list;

import java.util.List;

import lombok.Value;
import lombok.experimental.Accessors;
import pl.dzielins42.illusivebaboon.android.data.GeneratorMetaData;

public interface ListViewPatch {

    GeneratorListViewModel apply(GeneratorListViewModel viewModel);

    @Value
    @Accessors(prefix = "m")
    final class SetItems implements ListViewPatch {

        private final List<GeneratorMetaData> mItems;

        @Override
        public GeneratorListViewModel apply(GeneratorListViewModel viewModel) {
            return viewModel.toBuilder()
                    .clearItems()
                    .items(mItems)
                    .build();
        }
    }
}
