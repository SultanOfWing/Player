package seleznov.nope.player.playlist;

import android.view.View;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by User on 19.05.2018.
 */

public class PlayListFragment extends DaggerFragment implements PlayListContract.View {

    @Inject
    PlayListContract.Presenter mPlayListPresenter;

    private View mView;

}
