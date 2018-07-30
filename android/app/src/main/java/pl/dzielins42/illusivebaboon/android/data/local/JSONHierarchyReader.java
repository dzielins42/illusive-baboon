package pl.dzielins42.illusivebaboon.android.data.local;


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
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;
import pl.dzielins42.illusivebaboon.android.data.HierarchyData;

public class JSONHierarchyReader {

    private final Gson mGson;

    @Inject
    public JSONHierarchyReader(Gson gson) {
        mGson = gson;
    }

    public List<Pair<String, HierarchyData>> read (@NonNull Reader reader) {
        return parse(mGson.fromJson(reader, JSONHierarchyNode[].class));
    }

    public List<Pair<String, HierarchyData>> read (@NonNull String data) {
        return parse(mGson.fromJson(data, JSONHierarchyNode[].class));
    }

    List<Pair<String, HierarchyData>> parse(JSONHierarchyNode[] nodes) {
        List<Pair<String, HierarchyData>> list = new ArrayList<>(nodes.length);

        for (JSONHierarchyNode node : nodes) {
            final HierarchyData data = HierarchyData.builder()
                    .id(node.getId())
                    .name(node.getName())
                    .description(node.getDescription())
                    .build();
            final String path = node.getPath();
            list.add(Pair.create(path, data));
        }

        Collections.sort(list, (a, b) -> StringUtils.compare(a.first, b.first));

        return list;
    }

    @Value
    @Accessors(prefix = "m")
    @Builder
    static final class JSONHierarchyNode {
        @SerializedName("path")
        private final String mPath;
        @NonNull
        @SerializedName("id")
        private final String mId;
        @NonNull
        @SerializedName("name")
        private final String mName;
        @SerializedName("description")
        private final String mDescription;
    }
}
