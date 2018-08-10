package pl.dzielins42.illusivebaboon.android.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import pl.dzielins42.illusivebaboon.android.view.results.GeneratorDetailsActivity;

@Singleton
public class NavigationController {

    private static final String TAG = NavigationController.class.getSimpleName();

    private final Context mContext;

    @Inject
    public NavigationController(Context context) {
        mContext = context;
    }

    public void openDetails(@NonNull String path) {
        Log.d(TAG, "openDetails() called with: path = [" + path + "]");
        Intent intent = new Intent(mContext, GeneratorDetailsActivity.class);
        intent.putExtra(GeneratorDetailsActivity.KEY_GENERATOR_ID, path);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
