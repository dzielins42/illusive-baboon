package pl.dzielins42.illusivebaboon.android.data.db;

import com.google.common.collect.ImmutableMap;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import pl.dzielins42.illusivebaboon.android.data.db.dao.HistoryValueDao;
import pl.dzielins42.illusivebaboon.android.data.db.entity.HistoryValue;
import pl.dzielins42.illusivebaboon.android.data.db.entity.Reference;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static pl.dzielins42.illusivebaboon.android.data.db.TestUtils.NULL_PLACEHOLDER;

public class HistoryValueTest extends BaseDatabaseTest {

    private HistoryValueDao mHistoryValueDao;

    @Override
    public void setUp() {
        super.setUp();
        mHistoryValueDao = mDb.historyValueDao();
    }

    @Override
    public void tearDown() throws IOException {
        super.tearDown();
        mHistoryValueDao = null;
    }

    @Test
    public void insert() throws Exception {
        TestUtils.insertNodes(
                mDb.nodeDao(),
                ImmutableMap.<String, String>builder()
                        .put("node1", NULL_PLACEHOLDER)
                        .build()
        );
        TestUtils.insertReferences(
                mDb.referenceDao(),
                ImmutableMap.<String, String>builder()
                        .put("ref1", "node1")
                        .build()
        );
        HistoryValue hv = HistoryValue.builder()
                .value("value")
                .referenceId("ref1")
                .timestamp(0)
                .build();
        mHistoryValueDao.insert(hv);
    }

    @Test
    public void insert_get_all() throws Exception {
        TestUtils.insertNodes(
                mDb.nodeDao(),
                ImmutableMap.<String, String>builder()
                        .put("node1", NULL_PLACEHOLDER)
                        .build()
        );
        TestUtils.insertReferences(
                mDb.referenceDao(),
                ImmutableMap.<String, String>builder()
                        .put("ref1", "node1")
                        .build()
        );
        HistoryValue hv1 = HistoryValue.builder()
                .id(1)
                .value("value")
                .referenceId("ref1")
                .timestamp(0)
                .build();
        mHistoryValueDao.insert(hv1);
        HistoryValue hv2 = HistoryValue.builder()
                .id(2)
                .value("value")
                .referenceId("ref1")
                .timestamp(0)
                .build();
        mHistoryValueDao.insert(hv2);

        List<HistoryValue> historyValues = mHistoryValueDao.all();
        assertTrue(historyValues.contains(hv1));
        assertTrue(historyValues.contains(hv2));
    }

    @Test
    public void insert_get_byParentNodeId() throws Exception {
        TestUtils.insertNodes(
                mDb.nodeDao(),
                ImmutableMap.<String, String>builder()
                        .put("node1", NULL_PLACEHOLDER)
                        .put("node2", NULL_PLACEHOLDER)
                        .build()
        );
        TestUtils.insertReferences(
                mDb.referenceDao(),
                ImmutableMap.<String, String>builder()
                        .put("ref1", "node1")
                        .put("ref2", "node1")
                        .put("ref3", "node2")
                        .build()
        );
        HistoryValue hv1 = HistoryValue.builder()
                .id(1)
                .value("value")
                .referenceId("ref1")
                .timestamp(0)
                .build();
        mHistoryValueDao.insert(hv1);
        HistoryValue hv2 = HistoryValue.builder()
                .id(2)
                .value("value")
                .referenceId("ref2")
                .timestamp(0)
                .build();
        mHistoryValueDao.insert(hv2);
        HistoryValue hv3 = HistoryValue.builder()
                .id(3)
                .value("value")
                .referenceId("ref3")
                .timestamp(0)
                .build();
        mHistoryValueDao.insert(hv3);

        List<HistoryValue> historyValues = mHistoryValueDao.byParentNodeId("node1");
        assertTrue(historyValues.contains(hv1));
        assertTrue(historyValues.contains(hv2));
        assertFalse(historyValues.contains(hv3));
    }
}