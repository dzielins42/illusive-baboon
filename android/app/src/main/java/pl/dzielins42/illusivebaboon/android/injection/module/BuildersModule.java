package pl.dzielins42.illusivebaboon.android.injection.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import pl.dzielins42.illusivebaboon.android.MainActivity;
import pl.dzielins42.illusivebaboon.android.MainActivityFragment;

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract MainActivityFragment bindMainActivityFragment();

    // Add bindings for other sub-components here
}
