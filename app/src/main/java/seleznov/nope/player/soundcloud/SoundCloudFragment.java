package seleznov.nope.player.soundcloud;

import android.view.View;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by User on 19.05.2018.
 */

public class SoundCloudFragment extends DaggerFragment implements SoundCloudContract.View {

    @Inject
    SoundCloudContract.Presenter mSoundCloudPresenter;

    private View mView;

    @Inject
    public SoundCloudFragment(){};

}
