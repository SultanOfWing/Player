package seleznov.nope.player.playlist;

import java.util.List;

import seleznov.nope.player.IPresenter;
import seleznov.nope.player.IView;
import seleznov.nope.player.model.local.dto.LTrack;

/**
 * Created by User on 20.05.2018.
 */

public interface PlayListContract {

    interface Presenter extends IPresenter<PlayListContract.View> {
        void updatePlayList();
    }

    interface View extends IView<PlayListContract.Presenter> {
        void setPlayList(List<LTrack> list);
    }
}
