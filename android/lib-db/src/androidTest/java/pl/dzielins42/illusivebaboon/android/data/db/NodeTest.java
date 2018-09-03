package pl.dzielins42.illusivebaboon.android.data.db;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import pl.dzielins42.illusivebaboon.android.data.db.dao.NodeDao;
import pl.dzielins42.illusivebaboon.android.data.db.entity.Node;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class NodeTest extends BaseDatabaseTest {

    private NodeDao mNodeDao;

    @Override
    public void setUp() {
        super.setUp();
        mNodeDao = mDb.nodeDao();
    }

    @Override
    public void tearDown() throws IOException {
        super.tearDown();
        mNodeDao = null;
    }

    @Test
    public void insert() throws Exception {
        Node node = Node.builder()
                .id("id")
                .name("name")
                .description("description")
                .build();

        mNodeDao.insert(node);
    }

    @Test
    public void insert_get_all() throws Exception {
        Node node1 = Node.builder()
                .id("id1")
                .name("name")
                .description("description")
                .build();
        mNodeDao.insert(node1);

        Node node2 = Node.builder()
                .id("id2")
                .name("name")
                .description("description")
                .build();
        mNodeDao.insert(node2);

        List<Node> categories = mNodeDao.all();
        assertTrue(categories.contains(node1));
        assertTrue(categories.contains(node2));
    }

    @Test
    public void insert_get_byParentId() throws Exception {
        Node node1 = Node.builder()
                .id("parent")
                .name("name")
                .description("description")
                .build();

        mNodeDao.insert(node1);
        Node node2 = Node.builder()
                .id("child")
                .name("name")
                .description("description")
                .parentId("parent")
                .build();

        mNodeDao.insert(node2);

        List<Node> categories = mNodeDao.byParentId("parent");
        assertEquals(node2, categories.get(0));
    }
}