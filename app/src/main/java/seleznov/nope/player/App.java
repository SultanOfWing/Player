package seleznov.nope.player;

import android.app.Application;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import seleznov.nope.player.di.DaggerAppComponent;

/**
 * Created by User on 19.05.2018.
 */

public class App extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
