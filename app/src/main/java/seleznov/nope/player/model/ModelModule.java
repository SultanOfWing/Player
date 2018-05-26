package seleznov.nope.player.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import seleznov.nope.player.di.ActivityScoped;
import seleznov.nope.player.model.dto.Track;

/**
 * Created by User on 25.05.2018.
 */
@Module
public class ModelModule {

    @Singleton
    @Provides
     ArrayList<Track> getTrackList(){
        return new ArrayList<>();
    }

    @Singleton
    @Provides
     TrackListManager getTrackListManager(){
        return new TrackListManager();
    }

}
