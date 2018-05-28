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

import dagger.Module;
import dagger.Provides;
import seleznov.nope.player.di.ServiceScoped;

/**
 * Created by User on 25.05.2018.
 */
@Module
public abstract class PlaybackModule {

    @Provides
    @ServiceScoped
    static MediaSessionCompat getMediaSession(PlaybackService service){
        return new MediaSessionCompat(service.getApplicationContext(),
                service.getClass().getSimpleName());
    }

    @Provides
    @ServiceScoped
    static MediaMetadataCompat.Builder getMetadataBuilder(){
        return new MediaMetadataCompat.Builder();
    }

    @Provides
    @ServiceScoped
    static PlaybackStateCompat.Builder getPlaybackBuilder(){
        return new PlaybackStateCompat.Builder();
    }

    @Provides
    @ServiceScoped
    static DefaultRenderersFactory getRenderersFactory(Context context){
         return new DefaultRenderersFactory(context);
    }

    @Provides
    @ServiceScoped
    static DefaultTrackSelector getTrackSelector(){
        return new DefaultTrackSelector();
    }

    @Provides
    @ServiceScoped
    static DefaultLoadControl getLoadControl(){
        return new DefaultLoadControl();
    }

    @Provides
    @ServiceScoped
    static SimpleExoPlayer getExoPlayer(DefaultRenderersFactory renderers, DefaultTrackSelector track,
                                 DefaultLoadControl load){
        return ExoPlayerFactory.newSimpleInstance(renderers, track, load);
    }

}
