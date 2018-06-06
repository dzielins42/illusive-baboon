package pl.dzielins42.illusivebaboon.android.view.details;

public interface DetailsEvent {

    final class Initialize implements DetailsEvent {
        private final String mGeneratorId;

        public Initialize(String generatorId) {
            mGeneratorId = generatorId;
        }

        public String getGeneratorId() {
            return mGeneratorId;
        }

        @Override
        public String toString() {
            return "Initialize{" +
                    "generatorId='" + mGeneratorId + "'" +
                    "}";
        }
    }

    final class Generate implements DetailsEvent {

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
