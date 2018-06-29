package seleznov.nope.player.ui.lastfm;

import seleznov.nope.player.IPresenter;
import seleznov.nope.player.IView;
import seleznov.nope.player.model.remote.dto.Tracks;

/**
 * Created by User on 20.05.2018.
 */

public interface LastFmContract {

    interface Presenter extends IPresenter<View>{
        void updateChartTopList();
        void updateArtistTopList(String artist);
    }

    interface View extends IView<Presenter>{
        void setTopList(Tracks tracks);
    }
}