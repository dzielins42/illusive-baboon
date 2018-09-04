package pl.dzielins42.illusivebaboon.android.data.model.hierarchy;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Node may be a leaf, then it contains at least one {@link GeneratorReference}, or a branch,
 * then it contains at least one Node child. Other types of nodes are not valid.
 */
@Value
@Accessors(prefix = "m")
@Builder
public class Node {

    @NonNull
    @SerializedName("id")
    private final String mId;
    @NonNull
    @SerializedName("name")
    private final String mName;
    @SerializedName("description")
    private final String mDescription;
    @Singular
    @SerializedName("children")
    private final Set<Node> mChildren;
    @Singular
    @SerializedName("references")
    private final Set<GeneratorReference> mReferences;
}
