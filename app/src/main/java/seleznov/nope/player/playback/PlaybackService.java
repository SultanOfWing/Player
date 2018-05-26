package seleznov.nope.player.playback;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;


import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.FileDataSource;

import javax.inject.Inject;

import seleznov.nope.player.DaggerActivity;
import seleznov.nope.player.model.TrackListManager;
import seleznov.nope.player.model.dto.Track;

/**
 * Created by User on 25.05.2018.
 */

public class PlaybackService extends Service {

    @Inject
    SimpleExoPlayer mExoPlayer;
    @Inject
    PlaybackStateCompat.Builder mStateBuilder;
    @Inject
    MediaMetadataCompat.Builder mMetadataBuilder;
    @Inject
    TrackListManager mTrackListManager;
    @Inject
    MediaSessionCompat mSession;


    @Inject
    public PlaybackService() {}

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PlayerServiceBinder();
    }

    private void init() {
        mStateBuilder.setActions(PlaybackStateCompat.ACTION_PLAY
                | PlaybackStateCompat.ACTION_STOP
                | PlaybackStateCompat.ACTION_PAUSE
                | PlaybackStateCompat.ACTION_PLAY_PAUSE
                | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS);

        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mSession.setCallback(mediaSessionCallback);

        Context context = getApplicationContext();
        Intent intent = DaggerActivity.newIntent(context);
        mSession.setSessionActivity(PendingIntent.getActivity(context,
                0, intent, 0));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSession.release();
    }

     MediaSessionCompat.Callback mediaSessionCallback = new MediaSessionCompat.Callback() {

        @Override
        public void onPlay() {
            Track track = mTrackListManager.getTrack();
            MediaMetadataCompat metadata = mMetadataBuilder
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, track.getId())
                    .putString(MediaMetadataCompat.METADATA_KEY_TITLE, track.getTitle())
                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, track.getAlbum())
                    .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, track.getArtist())
                    .putString(MediaMetadataCompat.METADATA_KEY_ART_URI, track.getAlbumArt())
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, track.getUri())
                    .build();

            mSession.setMetadata(metadata);

            mSession.setActive(true);

            mSession.setPlaybackState(
                    mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                            PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1).build());

            prepareFromExternal(track.getUri());
            mExoPlayer.setPlayWhenReady(true);

        }

        @Override
        public void onPause() {
            super.onPause();
            mExoPlayer.setPlayWhenReady(false);

            mSession.setPlaybackState(
                    mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                            PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1).build());
        }

        @Override
        public void onStop() {
            super.onStop();
            mExoPlayer.setPlayWhenReady(false);

            mSession.setActive(false);

            mSession.setPlaybackState(
                    mStateBuilder.setState(PlaybackStateCompat.STATE_STOPPED,
                            PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1).build());
        }

        private void prepareFromExternal(String stringUri){
            Uri uri = Uri.parse(stringUri);//"file:///"+

            DataSpec dataSpec = new DataSpec(uri);
            final FileDataSource fileDataSource = new FileDataSource();
            try {
                fileDataSource.open(dataSpec);
            } catch (FileDataSource.FileDataSourceException e) {
                e.printStackTrace();
            }

            DataSource.Factory factory = new DataSource.Factory() {
                @Override
                public DataSource createDataSource() {
                    return fileDataSource;
                }
            };
            MediaSource audioSource = new ExtractorMediaSource(fileDataSource.getUri(),
                    factory, new DefaultExtractorsFactory(), null, null);

            mExoPlayer.prepare(audioSource);
            
        }

    };

    public class PlayerServiceBinder extends Binder {
        public MediaSessionCompat.Token getMediaSessionToken() {
            return mSession.getSessionToken();
        }
    }
}
