package seleznov.nope.player.model;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import seleznov.nope.player.model.local.dto.LTrack;

/**
 * Created by User on 25.05.2018.
 */
@Module
public class ModelModule {

    @Singleton
    @Provides
     ArrayList<LTrack> getTrackList(){
        return new ArrayList<>();
    }

    @Singleton
    @Provides
     TrackListManager getTrackListManager(){
        return new TrackListManager();
    }

}
