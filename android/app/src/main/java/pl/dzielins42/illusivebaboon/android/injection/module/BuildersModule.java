package pl.dzielins42.illusivebaboon.android.injection.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import pl.dzielins42.illusivebaboon.android.MainActivity;
import pl.dzielins42.illusivebaboon.android.MainActivityFragment;
import pl.dzielins42.illusivebaboon.android.view.StartActivity;
import pl.dzielins42.illusivebaboon.android.view.list.ListFragment;
import pl.dzielins42.illusivebaboon.android.view.results.GeneratorDetailsActivity;
import pl.dzielins42.illusivebaboon.android.view.results.ResultsFragment;

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract MainActivityFragment bindMainActivityFragment();

    @ContributesAndroidInjector
    abstract GeneratorDetailsActivity bindGeneratorDetailsActivity();

    @ContributesAndroidInjector
    abstract StartActivity bindStartActivity();

    @ContributesAndroidInjector
    abstract ListFragment bindListFragment();

    @ContributesAndroidInjector
    abstract ResultsFragment bindResultsFragment();

    // Add bindings for other sub-components here
}
