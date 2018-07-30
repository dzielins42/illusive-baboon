package pl.dzielins42.illusivebaboon.android.data.local.repository;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.NonNull;
import pl.dzielins42.illusivebaboon.android.data.HierarchyData;
import pl.dzielins42.illusivebaboon.android.data.MapTreeNode;

@Singleton
public class GeneratorHierarchyRepository {

    final MapTreeNode<HierarchyData> mRoot = MapTreeNode.<HierarchyData>builder().id("").build();

    @Inject
    public GeneratorHierarchyRepository() {
    }

    public void add(String path, @NonNull HierarchyData data) {
        MapTreeNode<HierarchyData> node = MapTreeNode.<HierarchyData>builder()
                .id(data.getId())
                .data(data)
                .build();
        MapTreeNode<HierarchyData> parentNode = getNode(path);
        if (!parentNode.getChildren().containsKey(data.getId())) {
            node.setParent(parentNode);
            parentNode.getChildren().put(data.getId(), node);
        }
    }

    public List<HierarchyData> getChildren(String path) {
        MapTreeNode<HierarchyData> node = getNode(path);

        return convertNodesToData(node.getChildren().values());
    }

    public HierarchyData get(String path) {
        MapTreeNode<HierarchyData> node = getNode(path);

        return node != null ? node.getData() : null;
    }

    private MapTreeNode<HierarchyData> getNode(String path) {
        if (TextUtils.isEmpty(path)) {
            return mRoot;
        }

        String[] parts = TextUtils.split(path, "/");

        MapTreeNode<HierarchyData> currentNode = mRoot;
        for (int i = 0; i < parts.length; i++) {
            MapTreeNode<HierarchyData> nextNode = currentNode.getChildren().get(parts[i]);
            if (nextNode == null) {
                throw new IllegalStateException("Cannot find " + path + " (no " + parts[i] + ")");
            }
            currentNode = nextNode;
        }

        return currentNode;
    }

    private List<HierarchyData> convertNodesToData(
            @NonNull Iterable<MapTreeNode<HierarchyData>> nodes
    ) {
        List<HierarchyData> data = new ArrayList<>();

        for (MapTreeNode<HierarchyData> node : nodes) {
            data.add(node.getData());
        }

        return data;
    }
}
