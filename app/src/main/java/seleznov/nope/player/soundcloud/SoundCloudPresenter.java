package seleznov.nope.player.soundcloud;

import android.content.Context;

import javax.annotation.Nullable;
import javax.inject.Inject;


/**
 * Created by User on 20.05.2018.
 */

public class SoundCloudPresenter implements SoundCloudContract.Presenter {

    @Nullable
    private SoundCloudContract.View mView;

    @Inject
    SoundCloudPresenter(Context context){}

    @Override
    public void updateCloudList() {

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
