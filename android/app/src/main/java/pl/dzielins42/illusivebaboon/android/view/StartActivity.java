package pl.dzielins42.illusivebaboon.android.view;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import pl.dzielins42.illusivebaboon.android.R;
import pl.dzielins42.illusivebaboon.android.data.local.ActivityHelloService;
import pl.dzielins42.illusivebaboon.android.data.local.AppHelloService;

public class StartActivity
        extends AppCompatActivity
        implements OnFragmentInteractionListener, HasSupportFragmentInjector {
    private static final String TAG = StartActivity.class.getSimpleName();

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;

    @Inject
    Context injectedContext;
    @Inject
    AppHelloService mAppHelloService;
    @Inject
    ActivityHelloService mActivityHelloService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        NavigationUI.setupActionBarWithNavController(
                this,
                getNavController()
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        getNavController().navigateUp();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, "onFragmentInteraction() called with: uri = [" + uri + "]");
    }

    private NavController getNavController(){
        return Navigation.findNavController(this, R.id.my_nav_host_fragment);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(
                "GeneratorListActivity",
                "onResume: " + (mAppHelloService != null ? mAppHelloService.hello() : "AppHelloService is null")
        );
        Log.d(
                "GeneratorListActivity",
                "onResume: " + (mActivityHelloService != null ? mActivityHelloService.hello() : "ActivityHelloService is null")
        );
    }
}
