package pl.dzielins42.illusivebaboon.android.data.local;

import android.content.Context;
import android.content.res.AssetManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import pl.dzielins42.illusivebaboon.android.data.NameGeneratorWrapper;
import pl.dzielins42.illusivebaboon.android.data.parser.GeneratorXmlParser;

@Singleton
public class AssetGeneratorReader {

    private final Context mContext;

    @Inject
    public AssetGeneratorReader(Context context) {
        mContext = context;
    }

    Flowable<NameGeneratorWrapper> read(final String basePath) {
        return Flowable.create(
                emitter -> readToEmitter(basePath, emitter),
                BackpressureStrategy.BUFFER
        );
    }

    void readToEmitter(
            final String basePath,
            FlowableEmitter<NameGeneratorWrapper> emitter
    ) throws Exception {
        final AssetManager assetManager = mContext.getAssets();

        List<String> filesToRead = scan(assetManager, basePath);

        GeneratorXmlParser generatorXmlParser = new GeneratorXmlParser();
        for (String file : filesToRead) {
            InputStream is = assetManager.open(file);
            try {
                final XmlPullParser xmlPullParser =
                        XmlPullParserFactory.newInstance().newPullParser();
                xmlPullParser.setInput(is, null);
                List<NameGeneratorWrapper> parsedResults = generatorXmlParser.parse(xmlPullParser);
                if (parsedResults != null && !parsedResults.isEmpty()) {
                    for (NameGeneratorWrapper wrapper : parsedResults) {
                        emitter.onNext(wrapper);
                    }
                }
            } finally {
                is.close();
            }
        }

        assetManager.close();
        emitter.onComplete();
    }

    List<String> scan(final AssetManager assetManager, final String basePath) throws IOException {
        final List<String> result = new ArrayList<>();
        final String[] content = assetManager.list(basePath);
        if (content.length == 0) {
            // It's file (or error)
            result.add(basePath);
        } else {
            for (String item : content) {
                result.addAll(scan(assetManager, basePath + File.separator + item));
            }
        }
        return result;
    }
}
