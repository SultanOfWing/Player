package seleznov.nope.player.eventbus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by User on 01.06.2018.
 */
@Module
public class EventBusModule {

    @Singleton
    @Provides
    RxEventBus getRxEventBus(){
        return new RxEventBus();
    }

}
