package seleznov.nope.player.lastfm;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import seleznov.nope.player.di.ActivityScoped;
import seleznov.nope.player.di.FragmentScoped;


/**
 * Created by User on 19.05.2018.
 */

@Module
public abstract class LastFmModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LastFmFragment soundCloudFragment();

    @ActivityScoped
    @Binds
    abstract LastFmContract.Presenter soundCloudPresenter(LastFmPresenter presenter);

    @Provides
    @ActivityScoped
    static LastFmAdapter getCloudAdapter(){
        return new LastFmAdapter();
    }

}
