package seleznov.nope.player.eventbus;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by User on 01.06.2018.
 */

public class RxEventBus {

    private  PublishSubject<Object> mSubject = PublishSubject.create();

    RxEventBus(){};

    public  Disposable subscribe(@NonNull Consumer<Object> action) {
        return mSubject.subscribe(action);
    }

    public  void publish(@NonNull Object message) {
        mSubject.onNext(message);
    }

}
