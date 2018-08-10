package pl.dzielins42.illusivebaboon.android.view.results;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import lombok.Value;
import lombok.experimental.Accessors;

public interface ResultsViewPatch {

    ResultsViewModel apply(ResultsViewModel viewModel);

    @Value
    @Accessors(prefix = "m")
    final class AddMetaData implements ResultsViewPatch {

        @NonNull
        private final String mGeneratorId;
        @NonNull
        private final String mGeneratorName;
        private final String mGeneratorDescription;

        @Override
        public ResultsViewModel apply(ResultsViewModel viewModel) {
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
    final class SetResults implements ResultsViewPatch {

        @Nullable
        private final List<String> mResults;

        @Override
        public ResultsViewModel apply(ResultsViewModel viewModel) {
            return viewModel.toBuilder()
                    .results(mResults)
                    .build();
        }
    }
}
