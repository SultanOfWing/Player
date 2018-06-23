package seleznov.nope.player.lastfmweb;


import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import seleznov.nope.player.di.FragmentScoped;

import static seleznov.nope.player.lastfmweb.WebWrapActivity.STRING_URL;

/**
 * Created by User on 23.06.2018.
 */
@Module
public abstract class WebWrapModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract WebWrapFragment webWrapFragment();

    @Provides
    static String provideUrl(WebWrapActivity activity) {
        return activity.getIntent().getStringExtra(STRING_URL);
    }
}
