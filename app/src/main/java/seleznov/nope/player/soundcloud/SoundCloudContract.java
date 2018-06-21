package seleznov.nope.player.soundcloud;

import java.util.List;

import seleznov.nope.player.IPresenter;
import seleznov.nope.player.IView;
import seleznov.nope.player.model.dto.Track;

/**
 * Created by User on 20.05.2018.
 */

public interface SoundCloudContract {

    interface Presenter extends IPresenter<View>{
        void updateCloudList();
    }

    interface View extends IView<Presenter>{
        void setCloudList(List<Track> list);
    }
}