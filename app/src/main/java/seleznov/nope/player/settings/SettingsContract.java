package seleznov.nope.player.settings;

import seleznov.nope.player.IPresenter;
import seleznov.nope.player.IView;
import seleznov.nope.player.playlist.PlayListContract;

/**
 * Created by User on 20.05.2018.
 */

public interface SettingsContract {

    interface Presenter extends IPresenter<SettingsContract.View> {

    }

    interface View extends IView<SettingsContract.Presenter> {

    }

}
