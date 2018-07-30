package pl.dzielins42.illusivebaboon.android.data.local.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Collection;

import pl.dzielins42.dmtools.generator.name.NameGenerator;
import pl.dzielins42.dmtools.generator.name.NameGeneratorOptions;
import pl.dzielins42.illusivebaboon.android.BuildConfig;
import pl.dzielins42.illusivebaboon.android.data.NameGeneratorWrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class GeneratorRepositoryTest {

    private GeneratorRepository mRepository;

    @Before
    public void setUp() throws Exception {
        mRepository = new GeneratorRepository();
    }

    @After
    public void tearDown() throws Exception {
        mRepository = null;
    }

    @Test
    public void get_returns_null_when_empty() throws Exception {
        NameGeneratorWrapper generator = mRepository.get("test");
        assertNull(generator);
    }

    @Test
    public void get_returns_null_when_invalid_id() throws Exception {
        insertDummyData();
        NameGeneratorWrapper generator = mRepository.get("qwerty");
        assertNull(generator);
    }

    @Test
    public void get_returns_valid() throws Exception {
        NameGeneratorWrapper generator = createDummyDataItem();
        mRepository.save(generator);
        assertEquals(generator, mRepository.get("test"));
    }

    @Test
    public void save() throws Exception {
        insertDummyData();
    }

    @Test
    public void contains() throws Exception {
        insertDummyData();
        assertTrue(mRepository.contains("test"));
    }

    @Test
    public void isEmpty() throws Exception {
        assertTrue(mRepository.isEmpty());
        insertDummyData();
        assertFalse(mRepository.isEmpty());
    }

    private void insertDummyData() {
        mRepository.save(createDummyDataItem());
    }

    private NameGeneratorWrapper createDummyDataItem() {
        return NameGeneratorWrapper.builder()
                .id("test")
                .name("name")
                .description("description")
                .generator(new NameGenerator() {
                    @Override
                    public String generate(NameGeneratorOptions options) {
                        return null;
                    }

                    @Override
                    public Collection<String> generate(
                            NameGeneratorOptions options,
                            int count
                    ) {
                        return null;
                    }
                })
                .build();
    }
}