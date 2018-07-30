package pl.dzielins42.illusivebaboon.android.data.local.repository;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import pl.dzielins42.illusivebaboon.android.BuildConfig;
import pl.dzielins42.illusivebaboon.android.data.HierarchyData;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.containsInAnyOrder;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class GeneratorHierarchyRepositoryTest {

    private GeneratorHierarchyRepository mRepository;

    @Before
    public void setUp() throws Exception {
        mRepository = new GeneratorHierarchyRepository();
    }

    @After
    public void tearDown() throws Exception {
        mRepository = null;
    }

    @Test
    public void add_simple() throws Exception {
        HierarchyData data = HierarchyData.builder().id("id").name("name").build();
        mRepository.add(null, data);
        assertFalse(mRepository.mRoot.getChildren().isEmpty());
    }

    @Test
    public void add_complex() throws Exception {
        HierarchyData data1 = HierarchyData.builder().id("id1").name("name1").build();
        HierarchyData data2 = HierarchyData.builder().id("id2").name("name2").build();
        HierarchyData data3 = HierarchyData.builder().id("id3").name("name3").build();
        mRepository.add(null, data1);
        mRepository.add("id1", data2);
        mRepository.add("id1/id2", data3);
    }

    @Test
    public void add_no_duplicates() throws Exception {
        HierarchyData data1 = HierarchyData.builder().id("id").name("name").build();
        HierarchyData data2 = HierarchyData.builder().id("id").name("name").build();
        mRepository.add(null, data1);
        mRepository.add(null, data1);
        mRepository.add(null, data2);
        assertEquals(1, mRepository.mRoot.getChildren().size());
    }

    @Test
    public void get_empty_root() throws Exception {
        assertTrue(mRepository.getChildren(null).isEmpty());
    }

    @Test
    public void get_root() throws Exception {
        final int c = 8;
        List<HierarchyData> data = new ArrayList<>(c);
        for (int i = 0; i < c; i++) {
            HierarchyData item = generateRandomItem();
            data.add(item);
            mRepository.add(null, item);
        }

        Assert.assertThat(mRepository.getChildren(null), containsInAnyOrder(data.toArray()));
        Assert.assertThat(mRepository.getChildren(""), containsInAnyOrder(data.toArray()));
    }

    @Test
    public void get_path() throws Exception {
        HierarchyData data1 = HierarchyData.builder().id("id1").name("name1").build();
        HierarchyData data2 = HierarchyData.builder().id("id2").name("name2").build();
        HierarchyData data3 = HierarchyData.builder().id("id3").name("name3").build();
        mRepository.add(null, data1);
        mRepository.add("id1", data2);
        mRepository.add("id1/id2", data3);

        final int c = 8;
        List<HierarchyData> data = new ArrayList<>(c);
        for (int i = 0; i < c; i++) {
            HierarchyData item = generateRandomItem();
            data.add(item);
            mRepository.add("id1/id2/id3", item);
        }

        Assert.assertThat(mRepository.getChildren("id1/id2/id3"), containsInAnyOrder(data.toArray()));
    }

    private HierarchyData generateRandomItem() {
        return HierarchyData.builder()
                .id(RandomStringUtils.randomAlphanumeric(8))
                .name(RandomStringUtils.randomAlphanumeric(8))
                .build();
    }
}