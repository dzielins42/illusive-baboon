package pl.dzielins42.illusivebaboon.android.data.db;


import android.support.annotation.NonNull;

import java.util.Map;

import pl.dzielins42.illusivebaboon.android.data.db.dao.NodeDao;
import pl.dzielins42.illusivebaboon.android.data.db.dao.ReferenceDao;
import pl.dzielins42.illusivebaboon.android.data.db.entity.Node;
import pl.dzielins42.illusivebaboon.android.data.db.entity.Reference;

public class TestUtils {

    public static final String NULL_PLACEHOLDER = "le_null";

    public static void insertNodes(
            @NonNull NodeDao nodeDao,
            @NonNull Map<String, String> idToParentId
    ) {
        if (idToParentId.isEmpty()) {
            return;
        }

        for (Map.Entry<String, String> entry : idToParentId.entrySet()) {
            Node node = Node.builder()
                    .id(entry.getKey())
                    .name("name")
                    .description("description")
                    .parentId(NULL_PLACEHOLDER.equals(entry.getValue()) ? null : entry.getValue())
                    .build();
            nodeDao.insert(node);
        }
    }

    public static void insertReferences(
            @NonNull ReferenceDao referenceDao,
            @NonNull Map<String, String> idToParentNodeId
    ) {
        if (idToParentNodeId.isEmpty()) {
            return;
        }

        for (Map.Entry<String, String> entry : idToParentNodeId.entrySet()) {
            Reference ref = Reference.builder()
                    .id(entry.getKey())
                    .name("name")
                    .parentId(entry.getValue())
                    .build();
            referenceDao.insert(ref);
        }
    }
}
