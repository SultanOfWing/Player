package seleznov.nope.player.ui.lastfm;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import seleznov.nope.player.di.ActivityScoped;
import seleznov.nope.player.di.FragmentScoped;
import seleznov.nope.player.ui.WebWrapActivity;

import static seleznov.nope.player.ui.WebWrapActivity.STRING_URL;


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
    abstract WebWrapFragment webWrapFragment();

    @ActivityScoped
    @Binds
    abstract LastFmContract.Presenter soundCloudPresenter(LastFmPresenter presenter);

    @Provides
    static String provideUrl(WebWrapActivity activity) {
        return activity.getIntent().getStringExtra(STRING_URL);
    }

    @Provides
    @ActivityScoped
    static LastFmAdapter getCloudAdapter(){
        return new LastFmAdapter();
    }


}
