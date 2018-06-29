package seleznov.nope.player.model.remote;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import seleznov.nope.player.model.remote.dto.LastFm;

/**
 * Created by User on 21.06.2018.
 */

public interface LastFmApi {
    @GET("/2.0/")
    Observable<LastFm> getTracks(@Query("method") String method, @Query("api_key")
            String apiKey, @Query("format") String format);

    @GET("/2.0/")
    Observable<LastFm> getTracksByArtist(@Query("method") String method,
                                         @Query("artist") String artist,
                                         @Query("api_key") String apiKey,
                                         @Query("format") String format);
}
