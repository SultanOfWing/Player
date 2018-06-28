package seleznov.nope.player.controller;

import android.os.Handler;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import seleznov.nope.player.di.ActivityScoped;
import seleznov.nope.player.di.FragmentScoped;

/**
 * Created by User on 28.05.2018.
 */
@Module
public abstract class ControllerModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ControllerFragment playControllerFragment();

    @Provides
    @ActivityScoped
    static Handler getHandler(){
        return new Handler();
    }

}
