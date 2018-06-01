package seleznov.nope.player.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import seleznov.nope.player.App;
import seleznov.nope.player.eventbus.EventBusModule;
import seleznov.nope.player.model.ModelModule;
import seleznov.nope.player.playback.PlaybackModule;

/**
 * Created by User on 19.03.2018.
 */

@Singleton
@Component(modules = {EventBusModule.class,
        ModelModule.class,
        ApplicationModule.class,
        ComponentBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<App> {

    Context getContext();

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
