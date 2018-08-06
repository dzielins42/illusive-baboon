package pl.dzielins42.illusivebaboon.android.view;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import pl.dzielins42.illusivebaboon.android.R;

public class StartActivity
        extends AppCompatActivity
        implements OnFragmentInteractionListener {

    private static final String TAG = StartActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}
