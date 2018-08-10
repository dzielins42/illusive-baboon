package pl.dzielins42.illusivebaboon.android.view;

import android.content.Context;
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
import pl.dzielins42.illusivebaboon.android.view.list.ListFragment;
import pl.dzielins42.illusivebaboon.android.view.results.ResultsFragment;

public class StartActivity
        extends AppCompatActivity
        implements ListFragment.Host, ResultsFragment.Host, HasSupportFragmentInjector {
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

    @Override
    public void navigateToList(String path) {
        Bundle bundle = new Bundle();
        bundle.putString(ListFragment.ARG_PATH, path);
        getNavController().navigate(R.id.action_listFragment_self, bundle);
    }

    @Override
    public void navigateToResults(String path) {
        Bundle bundle = new Bundle();
        bundle.putString(ResultsFragment.ARG_PATH, path);
        getNavController().navigate(R.id.action_listFragment_to_resultsFragment, bundle);
    }
}
