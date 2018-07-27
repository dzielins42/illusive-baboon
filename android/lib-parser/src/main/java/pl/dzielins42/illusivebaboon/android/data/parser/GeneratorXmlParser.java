package pl.dzielins42.illusivebaboon.android.data.parser;

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
import pl.dzielins42.illusivebaboon.android.data.NameGeneratorWrapper;

public class GeneratorXmlParser {

    private static final String EMPTY_GENERATOR_ID = "";

    private static final String ROOT_TAG = "data";
    private static final String PROBABILITY_GENERATOR_TAG = "probability-gen";
    private static final String STRING_ITEM_TAG = "string-item";
    private static final String REFERENCE_ITEM_TAG = "ref-item";

    private static final String PROBABILITY_ATTRIBUTE = "probability";
    private static final String ID_ATTRIBUTE = "id";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String DESCRIPTION_ATTRIBUTE = "description";

    public List<NameGeneratorWrapper> parse(
            XmlPullParser parser
    ) throws XmlPullParserException, IOException {
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
        // Sanity test
        if (!PROBABILITY_GENERATOR_TAG.equals(parser.getName())) {
            throw new IllegalStateException(
                    parser.getName() + " is not a " + PROBABILITY_GENERATOR_TAG
            );
        }

        List<NameGeneratorItem> items = new ArrayList<>();
        List<Double> probabilities = new ArrayList<>();

        String id = getStringAttribute(parser, null, ID_ATTRIBUTE);
        String name = getStringAttribute(parser, null, NAME_ATTRIBUTE);
        String description = getStringAttribute(parser, null, DESCRIPTION_ATTRIBUTE);

        // Items have to be parsed here because they have additional properties used only in
        // ProbabilityNameGenerator
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_TAG) {
            if (XmlPullParser.START_TAG != eventType) {
                eventType = parser.next();
                continue;
            }

            final String tagName = parser.getName();
            switch (tagName) {
                case STRING_ITEM_TAG:
                    probabilities.add(getDoubleAttribute(
                            parser,
                            null,
                            PROBABILITY_ATTRIBUTE
                    ));
                    items.add(new StringNameGeneratorItem(readText(parser)));
                    break;
                case REFERENCE_ITEM_TAG:
                    final String refId = getStringAttribute(parser, null, ID_ATTRIBUTE);
                    if (refId == null || !parsingContext.generatorsMap.containsKey(refId)) {
                        throw new NoSuchElementException(
                                "Cannot reference " + String.valueOf(refId)
                        );
                    }
                    probabilities.add(getDoubleAttribute(
                            parser,
                            null,
                            PROBABILITY_ATTRIBUTE
                    ));
                    items.add(new ReferenceNameGeneratorItem(
                            parsingContext.generatorsMap.get(refId)
                    ));
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
            throw new IllegalStateException("Inconsistent probabilities");
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

        return NameGeneratorWrapper.builder()
                .id(id)
                .name(name)
                .description(description)
                .generator(generator)
                .build();
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
