package seleznov.nope.player.ui.lastfm;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import seleznov.nope.player.di.ActivityScoped;
import seleznov.nope.player.di.FragmentScoped;
import seleznov.nope.player.ui.lastfm.lastfmweb.WebWrapFragment;


/**
 * Created by User on 19.05.2018.
 */

@Module
public abstract class LastFmModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LastFmFragment lastFmFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract WebWrapFragment lastFmWebFragment();

    @ActivityScoped
    @Binds
    abstract LastFmContract.Presenter soundCloudPresenter(LastFmPresenter presenter);

    @Provides
    @ActivityScoped
    static LastFmAdapter getCloudAdapter(){
        return new LastFmAdapter();
    }

}
