package seleznov.nope.player.ui.lastfm;

import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import seleznov.nope.player.model.remote.LastFmApi;


/**
 * Created by User on 20.05.2018.
 */

public class LastFmPresenter implements LastFmContract.Presenter {

    private static final String API_KEY = "dccedcac6040621326d2c54a7722a54b";
    private static final String FORMAT = "json";

    private static final String METHOD_CHART_TOP = "chart.gettoptracks";
    private static final String METHOD_ARTIST_TOP = "artist.gettoptracks";

    @Nullable
    private LastFmContract.View mView;
    private LastFmApi mLastFmApi;

    @Inject
    LastFmPresenter(LastFmApi lastFmApi){
        mLastFmApi = lastFmApi;
    }

    @Override
    public void updateChartTopList(String artist) {
        if(mView == null){
            return;
        }
        if(artist == null){
            mLastFmApi.getTracks(METHOD_CHART_TOP, API_KEY, FORMAT)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(lastFm -> {mView.setTopList(lastFm.getTracks());},
                            Throwable::printStackTrace);
        }else {
            mLastFmApi.getTracksByArtist(METHOD_ARTIST_TOP, artist, API_KEY, FORMAT)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(lastFm -> {mView.setTopList(lastFm.getToptracks());},
                            Throwable::printStackTrace);
        }

    }

    @Override
    public void takeView(LastFmContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

}
