package seleznov.nope.player.playback;

import android.content.Context;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import seleznov.nope.player.di.ActivityScoped;

/**
 * Created by User on 25.05.2018.
 */
@Module
public class PlaybackModule {

    @Singleton
    @Provides
     MediaSessionCompat getMediaSession(PlaybackService service){
        return new MediaSessionCompat(service.getApplicationContext(),
                service.getClass().getSimpleName());
    }

    @Singleton
    @Provides
     MediaMetadataCompat.Builder getMetadataBuilder(){
        return new MediaMetadataCompat.Builder();
    }

    @Singleton
    @Provides
     PlaybackStateCompat.Builder getPlaybackBuilder(){
        return new PlaybackStateCompat.Builder();
    }

    @Singleton
    @Provides
    DefaultRenderersFactory getRenderersFactory(Context context){
         return new DefaultRenderersFactory(context);
    }

    @Singleton
    @Provides
    DefaultTrackSelector getTrackSelector(){
        return new DefaultTrackSelector();
    }

    @Singleton
    @Provides
    DefaultLoadControl getLoadControl(){
        return new DefaultLoadControl();
    }

    @Singleton
    @Provides
    SimpleExoPlayer getExoPlayer(DefaultRenderersFactory renderers, DefaultTrackSelector track,
                                 DefaultLoadControl load){
        return ExoPlayerFactory.newSimpleInstance(renderers, track, load);
    }

}
