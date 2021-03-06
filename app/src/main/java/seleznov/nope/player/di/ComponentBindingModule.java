package seleznov.nope.player.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import seleznov.nope.player.ui.PrimaryActivity;
import seleznov.nope.player.controller.ControllerModule;
import seleznov.nope.player.ui.WebWrapActivity;
import seleznov.nope.player.playback.PlaybackModule;
import seleznov.nope.player.playback.PlaybackService;
import seleznov.nope.player.ui.playlist.PlayListModule;
import seleznov.nope.player.ui.settings.SettingsModule;
import seleznov.nope.player.ui.lastfm.LastFmModule;

/**
 * Created by User on 19.03.2018.
 */

@Module
public abstract class ComponentBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {PlayListModule.class, LastFmModule.class,
            SettingsModule.class, ControllerModule.class})
    abstract PrimaryActivity primaryActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = LastFmModule.class)
    abstract WebWrapActivity webWrapActivity();

    @ServiceScoped
    @ContributesAndroidInjector(modules = PlaybackModule.class)
    abstract PlaybackService playbackService();

}
