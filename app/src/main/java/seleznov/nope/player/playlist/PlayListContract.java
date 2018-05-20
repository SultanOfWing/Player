package seleznov.nope.player.playlist;

import seleznov.nope.player.IPresenter;
import seleznov.nope.player.IView;
import seleznov.nope.player.soundcloud.SoundCloudContract;

/**
 * Created by User on 20.05.2018.
 */

public interface PlayListContract {

    interface Presenter extends IPresenter<SoundCloudContract.View> {

    }

    interface View extends IView<SoundCloudContract.Presenter> {

    }
}
