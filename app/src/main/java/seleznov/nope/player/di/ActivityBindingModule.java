package seleznov.nope.player.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import seleznov.nope.player.playlist.PlayListActivity;
import seleznov.nope.player.playlist.PlayListModule;
import seleznov.nope.player.settings.SettingsActivity;
import seleznov.nope.player.settings.SettingsModule;
import seleznov.nope.player.soundcloud.SoundCloudActivity;
import seleznov.nope.player.soundcloud.SoundCloudModule;

/**
 * Created by User on 19.03.2018.
 */

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = PlayListModule.class)
    abstract PlayListActivity playListActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = SoundCloudModule.class)
    abstract SoundCloudActivity soundCloudActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = SettingsModule.class)
    abstract SettingsActivity settingsActivity();
}
