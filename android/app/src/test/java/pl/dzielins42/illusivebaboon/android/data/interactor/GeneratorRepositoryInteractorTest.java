package pl.dzielins42.illusivebaboon.android.data.interactor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import pl.dzielins42.illusivebaboon.android.BuildConfig;
import pl.dzielins42.illusivebaboon.android.data.NameGeneratorWrapper;
import pl.dzielins42.illusivebaboon.android.data.local.AssetGeneratorReader;
import pl.dzielins42.illusivebaboon.android.data.local.repository.GeneratorRepository;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class GeneratorRepositoryInteractorTest {

    private GeneratorRepositoryInteractor mInteractor;
    private GeneratorRepository mRepository;

    @Before
    public void setUp() throws Exception {
        mRepository = new GeneratorRepository();
        mInteractor = new GeneratorRepositoryInteractor(
                mRepository,
                new AssetGeneratorReader(RuntimeEnvironment.application)
        );
    }

    @After
    public void tearDown() throws Exception {
        mInteractor = null;
        mRepository = null;
    }

    @Test
    public void get_existing() throws Exception {
        NameGeneratorWrapper generator = mInteractor.get("name.human.male").blockingGet();
        assertNotNull(generator);
    }

    @Test
    public void get_non_existing() throws Exception {
        NameGeneratorWrapper generator = mInteractor.get("qwerty").blockingGet();
        assertNull(generator);
    }

    @Test
    public void loadFromAssets() throws Exception {
        mInteractor.loadFromAssets().toList().blockingGet();
        assertFalse(mRepository.isEmpty());
    }
}