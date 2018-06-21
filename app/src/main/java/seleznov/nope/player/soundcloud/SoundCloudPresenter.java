package seleznov.nope.player.soundcloud;

import android.content.Context;

import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import seleznov.nope.player.model.remote.LastFmApi;


/**
 * Created by User on 20.05.2018.
 */

public class SoundCloudPresenter implements SoundCloudContract.Presenter {

    private static final String API_KEY = "dccedcac6040621326d2c54a7722a54b";

    private static final String FORMAT = "json";

    private static final String METHOD_TOP = "chart.gettoptracks";



    @Nullable
    private SoundCloudContract.View mView;
    private LastFmApi mLastFmApi;

    @Inject
    SoundCloudPresenter(LastFmApi lastFmApi){
        mLastFmApi = lastFmApi;
    }

    @Override
    public void updateTopList() {
        if(mView == null){
            return;
        }
        mLastFmApi.getTracks(METHOD_TOP, API_KEY, FORMAT)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lastFm -> {mView.setTopList(lastFm.getTracks());},
                        Throwable::printStackTrace);
    }

    @Override
    public void takeView(SoundCloudContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

}
