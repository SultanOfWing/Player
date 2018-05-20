package seleznov.nope.player.soundcloud;

import seleznov.nope.player.IPresenter;
import seleznov.nope.player.IView;

/**
 * Created by User on 20.05.2018.
 */

public interface SoundCloudContract {

    interface Presenter extends IPresenter<View>{

    }

    interface View extends IView<Presenter>{

    }
}