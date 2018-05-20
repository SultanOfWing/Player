package seleznov.nope.player.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import seleznov.nope.player.playlist.PlayListFragment;
import seleznov.nope.player.soundcloud.SoundCloudFragment;

/**
 * Created by User on 19.03.2018.
 */
@Module
public abstract class ApplicationModule {

    @Binds
    abstract Context bindContext(Application application);

}
