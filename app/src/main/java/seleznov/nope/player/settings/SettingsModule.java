package seleznov.nope.player.settings;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import seleznov.nope.player.di.ActivityScoped;
import seleznov.nope.player.di.FragmentScoped;
import seleznov.nope.player.soundcloud.SoundCloudContract;
import seleznov.nope.player.soundcloud.SoundCloudFragment;
import seleznov.nope.player.soundcloud.SoundCloudPresenter;

/**
 * Created by User on 19.05.2018.
 */


@Module
public abstract class SettingsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract SettingsFragment settingsFragment();

    @ActivityScoped
    @Binds
    abstract SettingsContract.Presenter settingsPresenter(SettingsPresenter presenter);

}