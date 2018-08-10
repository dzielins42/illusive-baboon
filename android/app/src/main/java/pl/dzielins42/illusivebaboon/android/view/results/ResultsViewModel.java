package pl.dzielins42.illusivebaboon.android.view.results;

import java.util.List;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(prefix = "m")
@Builder(toBuilder = true)
public class ResultsViewModel {

    @Singular
    private List<String> mResults;
    private String mGeneratorId;
    private String mGeneratorName;
    private String mGeneratorDescription;
}
