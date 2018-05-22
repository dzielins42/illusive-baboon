package pl.dzielins42.illusivebaboon.android.view.details;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.List;

public class GeneratorDetailsViewModel {

    @NonNull
    private List<String> mResults;
    @NonNull
    private String mGeneratorId;

    @NonNull
    public List<String> getResults() {
        return mResults;
    }

    @NonNull
    public String getGeneratorId() {
        return mGeneratorId;
    }

    public static GeneratorDetailsViewModel create() {
        return new Builder().build();
    }

    public Builder builder() {
        return new Builder(this);
    }

    @Override
    public String toString() {
        return "GeneratorDetailsViewModel{" +
                "results=" + mResults +
                "generatorId=" + mGeneratorId +
                '}';
    }

    public final static class Builder {

        @NonNull
        private final GeneratorDetailsViewModel mPreviousModel;

        @Nullable
        private List<String> mResults;
        @Nullable
        private String mGeneratorId;

        Builder() {
            mPreviousModel = new GeneratorDetailsViewModel();

            mPreviousModel.mResults = Collections.emptyList();
            mPreviousModel.mGeneratorId = null;
        }

        Builder(GeneratorDetailsViewModel previousModel) {
            mPreviousModel = previousModel;
        }

        @NonNull
        public Builder setResults(List<String> results) {
            mResults = results;
            return this;
        }

        @NonNull
        public Builder setGeneratorId(String generatorId) {
            mGeneratorId = generatorId;
            return this;
        }

        @NonNull
        public GeneratorDetailsViewModel build() {
            GeneratorDetailsViewModel newModel = new GeneratorDetailsViewModel();

            newModel.mResults = mResults == null
                    ? mPreviousModel.mResults
                    : mResults;
            newModel.mGeneratorId = mGeneratorId == null
                    ? mPreviousModel.mGeneratorId
                    : mGeneratorId;

            return newModel;
        }
    }
}
