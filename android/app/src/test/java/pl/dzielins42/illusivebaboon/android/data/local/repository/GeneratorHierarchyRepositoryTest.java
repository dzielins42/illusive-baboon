package pl.dzielins42.illusivebaboon.android.data.local.repository;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
import pl.dzielins42.illusivebaboon.android.data.ItemData;

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
        ItemData data = ItemData.builder().path("id").name("name").build();
        mRepository.add(data);
        assertFalse(mRepository.mRoot.getChildren().isEmpty());
    }

    @Test
    public void add_complex() throws Exception {
        ItemData data1 = ItemData.builder().path("id1").name("name1").build();
        ItemData data2 = ItemData.builder().path("id1/id2").name("name2").build();
        ItemData data3 = ItemData.builder().path("id1/id2/id3").name("name3").build();
        mRepository.add(data1);
        mRepository.add(data2);
        mRepository.add(data3);
    }

    @Test
    public void add_no_duplicates() throws Exception {
        ItemData data1 = ItemData.builder().path("id").name("name").build();
        ItemData data2 = ItemData.builder().path("id").name("name").build();
        mRepository.add(data1);
        mRepository.add(data1);
        mRepository.add(data2);
        assertEquals(1, mRepository.mRoot.getChildren().size());
    }

    @Test
    public void get_empty_root() throws Exception {
        //assertTrue(mRepository.getChildren(null).isEmpty());
        assertTrue(mRepository.get(null).getChildren().isEmpty());
    }

    @Test
    public void get_root() throws Exception {
        final int c = 8;
        List<ItemData> data = new ArrayList<>(c);
        for (int i = 0; i < c; i++) {
            ItemData item = generateRandomItem(null);
            data.add(item);
            mRepository.add(item);
        }

        Assert.assertThat(mRepository.get(null).getChildren(), containsInAnyOrder(data.toArray()));
        Assert.assertThat(mRepository.get("").getChildren(), containsInAnyOrder(data.toArray()));
    }

    @Test
    public void get_path() throws Exception {
        ItemData data1 = ItemData.builder().path("id1").name("name1").build();
        ItemData data2 = ItemData.builder().path("id1/id2").name("name2").build();
        ItemData data3 = ItemData.builder().path("id1/id2/id3").name("name3").build();
        mRepository.add(data1);
        mRepository.add(data2);
        mRepository.add(data3);

        final int c = 8;
        List<ItemData> data = new ArrayList<>(c);
        for (int i = 0; i < c; i++) {
            ItemData item = generateRandomItem("id1/id2/id3/");
            data.add(item);
            mRepository.add(item);
        }

        Assert.assertThat(mRepository.get("id1/id2/id3").getChildren(), containsInAnyOrder(data.toArray()));
    }

    private ItemData generateRandomItem(String pathPrefix) {
        final String path;
        if (StringUtils.isEmpty(pathPrefix)) {
            path = RandomStringUtils.randomAlphanumeric(8);
        } else {
            path = pathPrefix + RandomStringUtils.randomAlphanumeric(8);
        }
        return ItemData.builder()
                .path(path)
                .name(RandomStringUtils.randomAlphanumeric(8))
                .build();
    }
}