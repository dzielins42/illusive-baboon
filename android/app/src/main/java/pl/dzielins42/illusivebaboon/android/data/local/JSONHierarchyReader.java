package pl.dzielins42.illusivebaboon.android.data.local;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.StringUtils;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import pl.dzielins42.illusivebaboon.android.data.ItemData;

public class JSONHierarchyReader {

    private final Gson mGson;

    @Inject
    public JSONHierarchyReader(Gson gson) {
        mGson = gson;
    }

    public List<ItemData> read(@NonNull Reader reader) {
        return parse(mGson.fromJson(reader, JSONHierarchyNode[].class));
    }

    public List<ItemData> read(@NonNull String data) {
        return parse(mGson.fromJson(data, JSONHierarchyNode[].class));
    }

    List<ItemData> parse(JSONHierarchyNode[] nodes) {
        List<ItemData> list = new ArrayList<>(nodes.length);

        for (JSONHierarchyNode node : nodes) {
            final ItemData data = ItemData.builder()
                    .path(node.getPath())
                    .generatorId(node.getGeneratorId())
                    .name(node.getName())
                    .description(node.getDescription())
                    .build();
            list.add(data);
        }

        Collections.sort(list, (a, b) -> StringUtils.compare(a.getPath(), b.getPath()));

        return list;
    }

    @Value
    @Accessors(prefix = "m")
    @Builder
    static final class JSONHierarchyNode {
        /**
         * Full path to this node.
         */
        @NonNull
        @SerializedName("path")
        private final String mPath;
        /**
         * Id of the generator. Null if node is not a generator.
         */
        @SerializedName("generatorId")
        private final String mGeneratorId;
        @NonNull
        @SerializedName("name")
        private final String mName;
        @SerializedName("description")
        private final String mDescription;
    }
}
