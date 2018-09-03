package pl.dzielins42.illusivebaboon.android.data.db;

import com.google.common.collect.ImmutableMap;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import pl.dzielins42.illusivebaboon.android.data.db.dao.ReferenceDao;
import pl.dzielins42.illusivebaboon.android.data.db.entity.Reference;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static pl.dzielins42.illusivebaboon.android.data.db.TestUtils.NULL_PLACEHOLDER;

public class ReferenceTest extends BaseDatabaseTest {

    private ReferenceDao mReferenceDao;

    @Override
    public void setUp() {
        super.setUp();
        mReferenceDao = mDb.referenceDao();
    }

    @Override
    public void tearDown() throws IOException {
        super.tearDown();
        mReferenceDao = null;
    }

    @Test
    public void insert() throws Exception {
        TestUtils.insertNodes(
                mDb.nodeDao(),
                ImmutableMap.<String, String>builder()
                        .put("parent", NULL_PLACEHOLDER)
                        .build()
        );

        Reference reference = Reference.builder()
                .id("id")
                .name("name")
                .parentId("parent")
                .build();

        mReferenceDao.insert(reference);
    }

    @Test
    public void insert_get_all() throws Exception {
        TestUtils.insertNodes(
                mDb.nodeDao(),
                ImmutableMap.<String, String>builder()
                        .put("parent", NULL_PLACEHOLDER)
                        .build()
        );

        Reference ref1 = Reference.builder()
                .id("id1")
                .name("name")
                .parentId("parent")
                .build();
        mReferenceDao.insert(ref1);

        Reference ref2 = Reference.builder()
                .id("id2")
                .name("name")
                .parentId("parent")
                .build();
        mReferenceDao.insert(ref2);

        List<Reference> references = mReferenceDao.all();
        assertTrue(references.contains(ref1));
        assertTrue(references.contains(ref2));
    }

    @Test
    public void insert_get_byParentNodeId() throws Exception {
        TestUtils.insertNodes(
                mDb.nodeDao(),
                ImmutableMap.<String, String>builder()
                        .put("parent", NULL_PLACEHOLDER)
                        .put("other_parent", NULL_PLACEHOLDER)
                        .build()
        );

        Reference ref1 = Reference.builder()
                .id("id1")
                .name("name")
                .parentId("parent")
                .build();
        mReferenceDao.insert(ref1);

        Reference ref2 = Reference.builder()
                .id("id2")
                .name("name")
                .parentId("parent")
                .build();
        mReferenceDao.insert(ref2);

        Reference ref3 = Reference.builder()
                .id("id3")
                .name("name")
                .parentId("other_parent")
                .build();
        mReferenceDao.insert(ref3);

        List<Reference> references = mReferenceDao.byParentNodeId("parent");
        assertTrue(references.contains(ref1));
        assertTrue(references.contains(ref2));
        assertFalse(references.contains(ref3));
    }
}