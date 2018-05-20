package seleznov.nope.player.playlist;

import javax.inject.Inject;

import seleznov.nope.player.soundcloud.SoundCloudContract;

/**
 * Created by User on 20.05.2018.
 */

public class PlayListPresenter implements PlayListContract.Presenter {

    @Override
    public void takeView(SoundCloudContract.View view) {

    }

    @Override
    public void dropView() {

    }

    @Inject
    PlayListPresenter(){

    }

}
