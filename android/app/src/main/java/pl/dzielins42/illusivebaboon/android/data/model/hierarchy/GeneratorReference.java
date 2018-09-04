package pl.dzielins42.illusivebaboon.android.data.model.hierarchy;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(prefix = "m")
@Builder
public class GeneratorReference {

    @NonNull
    @SerializedName("id")
    private final String mId;
    @NonNull
    @SerializedName("name")
    private final String mName;
    @NonNull
    @SerializedName("generator-id")
    private final String mGeneratorId;
}
