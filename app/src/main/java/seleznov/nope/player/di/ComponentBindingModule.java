package seleznov.nope.player.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import seleznov.nope.player.PrimaryActivity;
import seleznov.nope.player.controller.ControllerModule;
import seleznov.nope.player.lastfmweb.WebWrapActivity;
import seleznov.nope.player.lastfmweb.WebWrapModule;
import seleznov.nope.player.playback.PlaybackModule;
import seleznov.nope.player.playback.PlaybackService;
import seleznov.nope.player.playlist.PlayListModule;
import seleznov.nope.player.settings.SettingsModule;
import seleznov.nope.player.lastfm.LastFmModule;

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
    @ContributesAndroidInjector(modules = WebWrapModule.class)
    abstract WebWrapActivity webWrapActivity();

    @ServiceScoped
    @ContributesAndroidInjector(modules = PlaybackModule.class)
    abstract PlaybackService playbackService();

}
