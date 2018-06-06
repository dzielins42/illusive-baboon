package pl.dzielins42.illusivebaboon.android.view.details;

import android.support.annotation.Nullable;

import java.util.List;

public interface DetailsViewPatch {

    GeneratorDetailsViewModel apply(GeneratorDetailsViewModel viewModel);

    final class AddMetaData implements DetailsViewPatch {

        private final String mGeneratorId;

        public AddMetaData(String generatorId) {
            mGeneratorId = generatorId;
        }

        @Override
        public GeneratorDetailsViewModel apply(GeneratorDetailsViewModel viewModel) {
            return viewModel.builder()
                    .setGeneratorId(mGeneratorId)
                    .build();
        }
    }

    final class SetResults implements DetailsViewPatch {

        @Nullable
        private final List<String> mResults;

        public SetResults(@Nullable List<String> results) {
            mResults = results;
        }

        @Override
        public GeneratorDetailsViewModel apply(GeneratorDetailsViewModel viewModel) {
            return viewModel.builder()
                    .addResults(mResults)
                    .build();
        }
    }
}
