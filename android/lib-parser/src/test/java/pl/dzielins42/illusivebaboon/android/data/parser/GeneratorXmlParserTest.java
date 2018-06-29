package pl.dzielins42.illusivebaboon.android.data.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import pl.dzielins42.illusivebaboon.android.BuildConfig;
import pl.dzielins42.illusivebaboon.android.data.NameGeneratorWrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class GeneratorXmlParserTest {

    private GeneratorXmlParser mGeneratorXmlParser;
    private XmlPullParser mXmlPullParser;

    @Before
    public void setUp() throws Exception {
        mGeneratorXmlParser = new GeneratorXmlParser();
        mXmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
    }

    @After
    public void tearDown() throws Exception {
        mGeneratorXmlParser = null;
        mXmlPullParser = null;
    }

    @Test
    public void parse_general() throws Exception {
        String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<data>\n" +
                "   <probability-gen id=\"common.color.blue\">\n" +
                "      <string-item>blue</string-item>\n" +
                "   </probability-gen>\n" +
                "   <probability-gen id=\"common.color\">\n" +
                "      <string-item>black</string-item>\n" +
                "      <string-item>white</string-item>\n" +
                "      <string-item>red</string-item>\n" +
                "      <string-item>green</string-item>\n" +
                "      <ref-item id=\"common.color.blue\" />\n" +
                "   </probability-gen>\n" +
                "</data>";
        InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        mXmlPullParser.setInput(stream, null);
        List<NameGeneratorWrapper> result = mGeneratorXmlParser.parse(mXmlPullParser);

        assertNotNull(result);
        assertEquals(result.size(), 2);
    }

    @Test
    public void parseProbabilityNameGenerator_general() throws Exception {
        String data = "<?xml version=\"1.0\"?>\n" +
                "<probability-gen id=\"common.color\">\n" +
                "  <string-item>black</string-item>\n" +
                "  <string-item>white</string-item>\n" +
                "  <string-item>red</string-item>\n" +
                "  <string-item>green</string-item>\n" +
                "  <string-item>blue</string-item>\n" +
                "</probability-gen>\n";
        InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        mXmlPullParser.setInput(stream, null);
        List<NameGeneratorWrapper> result = mGeneratorXmlParser.parse(mXmlPullParser);

        assertNotNull(result);
        assertEquals(result.size(),1);
    }

    @Test
    public void getDoubleAttribute_general() throws Exception {
        String data = "<test d=\"1.0\"/>";
        InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        mXmlPullParser.setInput(stream, null);
        mXmlPullParser.next();
        Double d = mGeneratorXmlParser.getDoubleAttribute(mXmlPullParser, null, "d");

        assertNotNull(d);
        assertEquals(d, new Double(1.0d));
    }

    @Test
    public void getStringAttribute_general() throws Exception {
        String data = "<test s=\"swordfish\"/>";
        InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        mXmlPullParser.setInput(stream, null);
        mXmlPullParser.next();
        String s = mGeneratorXmlParser.getStringAttribute(mXmlPullParser, null, "s");

        assertNotNull(s);
        assertEquals(s, "swordfish");
    }

    @Test
    public void readText_general() throws Exception {
        String data = "<test>swordfish</test>";
        InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        mXmlPullParser.setInput(stream, null);
        mXmlPullParser.next();
        String s = mGeneratorXmlParser.readText(mXmlPullParser);

        assertNotNull(s);
        assertEquals(s, "swordfish");
    }
}