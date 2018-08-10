package pl.dzielins42.illusivebaboon.android.view.results;

import lombok.Value;
import lombok.experimental.Accessors;

public interface ResultsEvent {

    @Value
    @Accessors(prefix = "m")
    final class Initialize implements ResultsEvent {
        private final String mPath;
    }

    final class Generate implements ResultsEvent {

        private final String mGeneratorId;
        private final int mCount;

        public Generate(String generatorId, int count) {
            mGeneratorId = generatorId;
            mCount = count;
        }

        public String getGeneratorId() {
            return mGeneratorId;
        }

        public int getCount() {
            return mCount;
        }

        @Override
        public String toString() {
            return "Generate{" +
                    "generatorId='" + mGeneratorId + "'" +
                    ", count=" + mCount +
                    "}";
        }
    }

}
