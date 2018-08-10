package pl.dzielins42.illusivebaboon.android.data.local.repository;

import android.text.TextUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.NonNull;
import pl.dzielins42.illusivebaboon.android.data.HierarchyData;
import pl.dzielins42.illusivebaboon.android.data.ItemData;
import pl.dzielins42.illusivebaboon.android.data.MapTreeNode;

@Singleton
public class GeneratorHierarchyRepository {

    final private ItemData mRootItemData = ItemData.builder().path("").name("").build();
    final MapTreeNode<ItemData> mRoot = MapTreeNode.<ItemData>builder().id("").data(mRootItemData).build();

    @Inject
    public GeneratorHierarchyRepository() {
    }

    public void add(@NonNull ItemData data) {
        final String parent;
        final String id;
        if (StringUtils.contains(data.getPath(), "/")) {
            parent = StringUtils.substringBeforeLast(data.getPath(), "/");
            id = StringUtils.substringAfterLast(data.getPath(), "/");
        } else {
            parent = null;
            id = data.getPath();
        }
        MapTreeNode<ItemData> node = MapTreeNode.<ItemData>builder()
                .id(id)
                .data(data)
                .build();
        MapTreeNode<ItemData> parentNode = getNode(parent);
        if (!parentNode.getChildren().containsKey(id)) {
            node.setParent(parentNode);
            parentNode.getChildren().put(id, node);
        }
    }

    public HierarchyData get(String path) {
        MapTreeNode<ItemData> node = getNode(path);

        if (node == null) {
            return null;
        }

        return HierarchyData.builder()
                .data(node.getData())
                .children(convertNodesToData(node.getChildren().values()))
                .build();
    }

    private MapTreeNode<ItemData> getNode(String path) {
        if (TextUtils.isEmpty(path)) {
            return mRoot;
        }

        String[] parts = TextUtils.split(path, "/");

        MapTreeNode<ItemData> currentNode = mRoot;
        for (int i = 0; i < parts.length; i++) {
            MapTreeNode<ItemData> nextNode = currentNode.getChildren().get(parts[i]);
            if (nextNode == null) {
                throw new IllegalStateException("Cannot find " + path + " (no " + parts[i] + ")");
            }
            currentNode = nextNode;
        }

        return currentNode;
    }

    private List<ItemData> convertNodesToData(
            @NonNull Iterable<MapTreeNode<ItemData>> nodes
    ) {
        if (nodes == null) {
            return null;
        }

        List<ItemData> data = new ArrayList<>();

        for (MapTreeNode<ItemData> node : nodes) {
            data.add(node.getData());
        }

        return data;
    }
}
