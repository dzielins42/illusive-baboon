package pl.dzielins42.illusivebaboon.android.data.local;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import pl.dzielins42.illusivebaboon.android.BuildConfig;
import pl.dzielins42.illusivebaboon.android.data.NameGeneratorWrapper;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AssetGeneratorReaderTest {

    private AssetGeneratorReader mReader;

    @Before
    public void setUp() throws Exception {
        mReader = new AssetGeneratorReader(RuntimeEnvironment.application);
    }

    @After
    public void tearDown() throws Exception {
        mReader = null;
    }

    @Test
    public void read_all() throws Exception {
        List<NameGeneratorWrapper> result = mReader.read().toList().blockingGet();
    }

    @Test
    public void read_specific() throws Exception {
        List<NameGeneratorWrapper> result =
                mReader.read("/generator/generator.xml").toList().blockingGet();
    }
}