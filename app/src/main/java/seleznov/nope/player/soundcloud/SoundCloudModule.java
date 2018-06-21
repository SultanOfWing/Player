package seleznov.nope.player.soundcloud;

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
public abstract class SoundCloudModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract SoundCloudFragment soundCloudFragment();

    @ActivityScoped
    @Binds
    abstract SoundCloudContract.Presenter soundCloudPresenter(SoundCloudPresenter presenter);

    @Provides
    @ActivityScoped
    static CloudAdapter getCloudAdapter(){
        return new CloudAdapter();
    }

}
