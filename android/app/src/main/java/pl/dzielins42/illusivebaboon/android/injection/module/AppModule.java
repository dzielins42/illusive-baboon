package pl.dzielins42.illusivebaboon.android.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.dzielins42.illusivebaboon.android.data.local.ActivityHelloService;
import pl.dzielins42.illusivebaboon.android.data.local.AppHelloService;
import pl.dzielins42.illusivebaboon.android.data.local.FragmentHelloService;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    AppHelloService provideAppHelloService() {
        return new AppHelloService();
    }

    @Provides
    @Singleton
    ActivityHelloService provideActivityHelloService() {
        return new ActivityHelloService();
    }

    @Provides
    @Singleton
    FragmentHelloService provideFragmentHelloService() {
        return new FragmentHelloService();
    }
}
