package pl.dzielins42.illusivebaboon.android;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import pl.dzielins42.illusivebaboon.android.data.local.FragmentHelloService;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    @Inject
    FragmentHelloService mFragmentHelloService;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(
                "MainActivityFragment",
                "onResume: " + (mFragmentHelloService != null ? mFragmentHelloService.hello() : "FragmentHelloService is null")
        );
    }
}
