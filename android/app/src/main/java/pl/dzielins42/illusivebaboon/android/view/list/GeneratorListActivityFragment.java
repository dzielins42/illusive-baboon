package pl.dzielins42.illusivebaboon.android.view.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvi.MviFragment;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import pl.dzielins42.illusivebaboon.android.R;
import pl.dzielins42.illusivebaboon.android.data.local.FragmentHelloService;

/**
 * A placeholder fragment containing a simple view.
 */
public class GeneratorListActivityFragment
        extends MviFragment<GeneratorListView, GeneratorListPresenter>
        implements GeneratorListView {

    @Inject
    FragmentHelloService mFragmentHelloService;
    @Inject
    GeneratorListPresenter mPresenter;

    public GeneratorListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_generator_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @NonNull
    @Override
    public GeneratorListPresenter createPresenter() {
        return mPresenter;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(
                "GeneratorListActivityFragment",
                "onResume: " + (mFragmentHelloService != null ? mFragmentHelloService.hello() : "FragmentHelloService is null")
        );
    }

    @Override
    public void render(GeneratorListState state) {
        Log.d(
                "GeneratorListActivityFragment",
                "render: " + state.couter
        );
    }
}
