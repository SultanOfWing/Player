package seleznov.nope.player;

/**
 * Created by User on 19.03.2018.
 */

public interface IPresenter<T> {

    void takeView(T view);

    void dropView();

}
