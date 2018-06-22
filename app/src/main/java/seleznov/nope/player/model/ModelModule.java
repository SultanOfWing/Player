package seleznov.nope.player.model;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import seleznov.nope.player.model.local.TrackListManager;
import seleznov.nope.player.model.local.dto.LocalTrack;
import seleznov.nope.player.model.remote.LastFmApi;

/**
 * Created by User on 25.05.2018.
 */
@Module
public class ModelModule {

    private static final String BASE_URL = "http://ws.audioscrobbler.com/";

    @Singleton
    @Provides
     ArrayList<LocalTrack> getTrackList(){
        return new ArrayList<>();
    }

    @Singleton
    @Provides
    TrackListManager getTrackListManager(){
        return new TrackListManager();
    }

    @Singleton
    @Provides
    LastFmApi providesLastFmApi(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory
                        .createWithScheduler(Schedulers.io()))
                .build();
        return retrofit.create(LastFmApi.class);
    }


}
