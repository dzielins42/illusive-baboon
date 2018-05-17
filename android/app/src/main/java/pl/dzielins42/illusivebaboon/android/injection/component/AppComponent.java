package pl.dzielins42.illusivebaboon.android.injection.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import pl.dzielins42.illusivebaboon.android.DaggerApplication;
import pl.dzielins42.illusivebaboon.android.injection.module.AppModule;
import pl.dzielins42.illusivebaboon.android.injection.module.BuildersModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, BuildersModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

    void inject(DaggerApplication application);

}
