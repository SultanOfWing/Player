package seleznov.nope.player.playlist;

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
public abstract class PlayListModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract PlayListFragment playListFragment();

    @ActivityScoped
    @Binds
    abstract PlayListContract.Presenter presenter(PlayListPresenter presenter);

    @Provides
    @ActivityScoped
    static PlayListAdapter getFeedListAdapter(){
        return new PlayListAdapter();
    }

}
