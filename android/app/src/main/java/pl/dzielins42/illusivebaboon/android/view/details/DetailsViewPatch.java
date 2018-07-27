package pl.dzielins42.illusivebaboon.android.view.details;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import lombok.Value;
import lombok.experimental.Accessors;

public interface DetailsViewPatch {

    GeneratorDetailsViewModel apply(GeneratorDetailsViewModel viewModel);

    @Value
    @Accessors(prefix = "m")
    final class AddMetaData implements DetailsViewPatch {

        @NonNull
        private final String mGeneratorId;
        @NonNull
        private final String mGeneratorName;
        @NonNull
        private final String mGeneratorDescription;

        @Override
        public GeneratorDetailsViewModel apply(GeneratorDetailsViewModel viewModel) {
            return viewModel.toBuilder()
                    .generatorId(mGeneratorId)
                    .generatorName(mGeneratorName)
                    .generatorDescription(mGeneratorDescription)
                    .clearResults()
                    .build();
        }
    }

    @Value
    @Accessors(prefix = "m")
    final class SetResults implements DetailsViewPatch {

        @Nullable
        private final List<String> mResults;

        @Override
        public GeneratorDetailsViewModel apply(GeneratorDetailsViewModel viewModel) {
            return viewModel.toBuilder()
                    .results(mResults)
                    .build();
        }
    }
}
