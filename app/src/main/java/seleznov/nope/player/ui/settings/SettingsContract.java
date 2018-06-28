package seleznov.nope.player.ui.settings;

import seleznov.nope.player.IPresenter;
import seleznov.nope.player.IView;

/**
 * Created by User on 20.05.2018.
 */

public interface SettingsContract {

    interface Presenter extends IPresenter<SettingsContract.View> {

    }

    interface View extends IView<SettingsContract.Presenter> {

    }

}
