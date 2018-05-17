package pl.dzielins42.illusivebaboon.android.injection.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import pl.dzielins42.illusivebaboon.android.MainActivity;
import pl.dzielins42.illusivebaboon.android.MainActivityFragment;
import pl.dzielins42.illusivebaboon.android.view.list.GeneratorListActivity;
import pl.dzielins42.illusivebaboon.android.view.list.GeneratorListActivityFragment;

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract MainActivityFragment bindMainActivityFragment();

    @ContributesAndroidInjector
    abstract GeneratorListActivity bindGeneratorListActivity();

    @ContributesAndroidInjector
    abstract GeneratorListActivityFragment bindGeneratorListActivityFragment();

    // Add bindings for other sub-components here
}
