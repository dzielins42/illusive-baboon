package pl.dzielins42.illusivebaboon.android.data;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import pl.dzielins42.dmtools.generator.name.NameGenerator;
import pl.dzielins42.dmtools.generator.name.NameGeneratorItem;
import pl.dzielins42.dmtools.generator.name.ProbabilityNameGenerator;
import pl.dzielins42.dmtools.generator.name.ReferenceNameGeneratorItem;
import pl.dzielins42.dmtools.generator.name.StringNameGeneratorItem;

public class GeneratorXmlParser {

    private static final String EMPTY_GENERATOR_ID = "";

    private static final String PROBABILITY_GENERATOR_TAG = "probability-gen";

    private static final String STRING_ITEM_TAG = "string-item";
    private static final String REFERENCE_ITEM_TAG = "ref-item";

    private static final String PROBABILITY_ATTRIBUTE = "probability";
    private static final String ID_ATTRIBUTE = "id";

    public List<NameGeneratorWrapper> parse(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        final List<NameGeneratorWrapper> list = new LinkedList<>();
        final ParsingContext parsingContext = new ParsingContext();
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            final String name = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (PROBABILITY_GENERATOR_TAG.equals(name)) {
                        list.add(parseProbabilityNameGenerator(parser, parsingContext));
                    }
                    break;
            }
            eventType = parser.next();
        }

        return list;
    }

    NameGeneratorWrapper parseProbabilityNameGenerator(
            XmlPullParser parser,
            ParsingContext parsingContext
    ) throws XmlPullParserException, IOException {
        if (!PROBABILITY_GENERATOR_TAG.equals(parser.getName())) {
            throw new IllegalStateException();
        }

        List<NameGeneratorItem> items = new ArrayList<>();
        List<Double> probabilities = new ArrayList<>();

        String id = getStringAttribute(parser, null, ID_ATTRIBUTE);

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_TAG) {
            final String name = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (STRING_ITEM_TAG.equals(name)) {
                        probabilities.add(getDoubleAttribute(parser, null, PROBABILITY_ATTRIBUTE));
                        new StringNameGeneratorItem(readText(parser));
                    } else if (REFERENCE_ITEM_TAG.equals(name)) {
                        final String refId = getStringAttribute(parser, null, ID_ATTRIBUTE);
                        if (refId == null || !parsingContext.generatorsMap.containsKey(refId)) {
                            throw new NoSuchElementException(
                                    "Cannot reference " + String.valueOf(refId)
                            );
                        }
                        probabilities.add(getDoubleAttribute(parser, null, PROBABILITY_ATTRIBUTE));
                        items.add(new ReferenceNameGeneratorItem(
                                parsingContext.generatorsMap.get(refId)
                        ));
                    }
                    break;
            }
            eventType = parser.next();
        }

        NameGeneratorItem[] itemsArray = new NameGeneratorItem[items.size()];
        items.toArray(itemsArray);
        boolean containsNulls = false;
        boolean containsNonNulls = false;
        for (Double d : probabilities) {
            if (d == null) {
                containsNulls = true;
            } else {
                containsNonNulls = true;
            }
        }
        if (containsNulls == containsNonNulls) {
            throw new IllegalStateException();
        }

        ProbabilityNameGenerator generator;
        if (containsNulls) {
            generator = new ProbabilityNameGenerator(itemsArray);
        } else {
            double[] probabilitiesArray = new double[probabilities.size()];
            for (int i = 0; i < probabilities.size(); i++) {
                probabilitiesArray[i] = probabilities.get(i);
            }

            generator = new ProbabilityNameGenerator(itemsArray, probabilitiesArray);
        }

        if (id != null) {
            parsingContext.generatorsMap.put(id, generator);
        } else {
            id = EMPTY_GENERATOR_ID;
        }

        return new NameGeneratorWrapper(id, generator);
    }

    Double getDoubleAttribute(XmlPullParser parser, String namespace, String name) {
        final String stringValue = parser.getAttributeValue(namespace, name);
        if (stringValue == null) {
            return null;
        }
        return Double.parseDouble(stringValue);
    }

    String getStringAttribute(XmlPullParser parser, String namespace, String name) {
        return parser.getAttributeValue(namespace, name);
    }

    String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = null;
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private static final class ParsingContext {
        Map<String, NameGenerator> generatorsMap = new HashMap<>();
    }
}
