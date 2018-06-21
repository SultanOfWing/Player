package seleznov.nope.player.playback;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;


import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.FileDataSource;


import javax.inject.Inject;

import dagger.android.DaggerService;
import seleznov.nope.player.DaggerActivity;
import seleznov.nope.player.R;
import seleznov.nope.player.helper.MediaStyleHelper;
import seleznov.nope.player.model.TrackListManager;
import seleznov.nope.player.model.local.dto.LTrack;

import static android.support.v4.media.app.NotificationCompat.MediaStyle;

/**
 * Created by User on 25.05.2018.
 */

public class PlaybackService extends DaggerService {

    private static final String TAG = "PlaybackService";

    private static final int NOTIFICATION_ID = 1;

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

    private AudioManager mAudioManager;

    public static Intent newIntent(Context context){
        return new Intent(context, PlaybackService.class);
    }

    @Inject
    public PlaybackService() {}

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null,
                context, MediaButtonReceiver.class);
        mSession.setMediaButtonReceiver(PendingIntent.getBroadcast(context,
                0, mediaButtonIntent, 0));
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        init();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PlaybackServiceBinder();
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
    public int onStartCommand(Intent intent, int flags, int startId) {
        MediaButtonReceiver.handleIntent(mSession, intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSession.release();
    }

    private void refreshNotification(int state){
        switch (state) {
            case PlaybackStateCompat.STATE_PLAYING: {
                startForeground(NOTIFICATION_ID, buildNotification(state));
                break;
            }
            case PlaybackStateCompat.STATE_PAUSED: {
                NotificationManagerCompat.from(PlaybackService.this)
                        .notify(NOTIFICATION_ID, buildNotification(state));
                stopForeground(false);
                break;
            }
            default: {
                stopForeground(true);
                break;
            }
        }
    }

    private Notification buildNotification(int state){

        NotificationCompat.Builder builder = MediaStyleHelper.from(this, mSession);

        builder.addAction(
                new NotificationCompat.Action(
                        android.R.drawable.ic_media_previous, getString(R.string.previous),
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                                this,
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)));

        if (state == PlaybackStateCompat.STATE_PLAYING)
            builder.addAction(
                    new NotificationCompat.Action(
                            android.R.drawable.ic_media_pause, getString(R.string.pause),
                            MediaButtonReceiver.buildMediaButtonPendingIntent(
                                    this,
                                    PlaybackStateCompat.ACTION_PLAY_PAUSE)));
        else
            builder.addAction(
                    new NotificationCompat.Action(
                            android.R.drawable.ic_media_play, getString(R.string.play),
                            MediaButtonReceiver.buildMediaButtonPendingIntent(
                                    this,
                                    PlaybackStateCompat.ACTION_PLAY_PAUSE)));
        builder.addAction(
                new NotificationCompat.Action(android.R.drawable.ic_media_next, getString(R.string.next),
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                                this,
                                PlaybackStateCompat.ACTION_SKIP_TO_NEXT)));

        builder.setStyle(new MediaStyle()
                .setShowActionsInCompactView(1)
                .setShowCancelButton(true)
                .setCancelButtonIntent(
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                                this,
                                PlaybackStateCompat.ACTION_STOP))
                .setMediaSession(mSession.getSessionToken()));

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setColor(ContextCompat.getColor(this,
                R.color.colorPrimaryDark));
        builder.setShowWhen(false);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setOnlyAlertOnce(true);

        return builder.build();
    }

     private MediaSessionCompat.Callback mediaSessionCallback = new MediaSessionCompat.Callback() {

        private int state = PlaybackStateCompat.STATE_STOPPED;

        @Override
        public void onPlay() {
            startService(PlaybackService.newIntent(getApplicationContext()));

            LTrack LTrack = mTrackListManager.getTrack();
            setMeta(LTrack);
            String s = LTrack.getUri();
            prepareFromExternal(Uri.parse(s));

            int audioFocusResult = mAudioManager.requestAudioFocus(
                    mAudioFocusChangeListener,
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN);
            if (audioFocusResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                return;

            mSession.setActive(true);

            registerReceiver(mBecomingNoiseRec, new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY));

            mExoPlayer.setPlayWhenReady(true);

            long pos = mExoPlayer.getCurrentPosition();
            mExoPlayer.seekTo(pos);
            mSession.setPlaybackState(
                    mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                            pos, 1).build());

            state = PlaybackStateCompat.STATE_PLAYING;
            refreshNotification(state);

        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
            try{
                unregisterReceiver(mBecomingNoiseRec);
            }catch (IllegalArgumentException e){
                Log.w(TAG, "attempt to unregister an " +
                        "unregistered receiver");
            }



            long pos  = mExoPlayer.getCurrentPosition();
            mExoPlayer.seekTo(pos);
            mSession.setPlaybackState(
                    mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                            pos, 1).build());


            state = PlaybackStateCompat.STATE_PAUSED;
            refreshNotification(state);
        }

        @Override
        public void onStop() {
            mExoPlayer.setPlayWhenReady(false);
            try{
                unregisterReceiver(mBecomingNoiseRec);
            }catch (IllegalArgumentException e){
                Log.w(TAG, "attempt to unregister an " +
                        "unregistered receiver");
            }

            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);

            mSession.setActive(false);

            mSession.setPlaybackState(
                    mStateBuilder.setState(PlaybackStateCompat.STATE_STOPPED,
                            PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1).build());

            state = PlaybackStateCompat.STATE_STOPPED;
            refreshNotification(state);

            stopSelf();
        }

         @Override
         public void onSeekTo(long pos) {
             mSession.setPlaybackState(
                     mStateBuilder.setState(state,
                             pos, 1).build());
            mExoPlayer.seekTo(pos);
         }

         @Override
         public void onSkipToNext() {
             LTrack LTrack = mTrackListManager.getNext();
             setMeta(LTrack);

             mExoPlayer.seekTo(0);
             mSession.setPlaybackState(
                     mStateBuilder.setState(state,
                             PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1).build());
             refreshNotification(state);

             prepareFromExternal(Uri.parse(LTrack.getUri()));
         }

         @Override
         public void onSkipToPrevious() {
             LTrack LTrack =  mTrackListManager.getPrevious();
             setMeta(LTrack);

             mExoPlayer.seekTo(0);
             mSession.setPlaybackState(
                     mStateBuilder.setState(state,
                             PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1).build());
             refreshNotification(state);

             prepareFromExternal(Uri.parse(LTrack.getUri()));
         }

        private void prepareFromExternal(Uri uri){
            DataSpec dataSpec = new DataSpec(uri);
            final FileDataSource fileDataSource = new FileDataSource();
            try {
                fileDataSource.open(dataSpec);
            } catch (FileDataSource.FileDataSourceException e) {
                e.printStackTrace();
            }

            DataSource.Factory factory = () -> fileDataSource;
            MediaSource audioSource = new ExtractorMediaSource(fileDataSource.getUri(),
                    factory, new DefaultExtractorsFactory(), null, null);

            mExoPlayer.prepare(audioSource);
            
        }

        private void setMeta(LTrack LTrack){
            MediaMetadataCompat metadata = mMetadataBuilder
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, LTrack.getId().toString())
                    .putString(MediaMetadataCompat.METADATA_KEY_TITLE, LTrack.getTitle())
                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, LTrack.getAlbum())
                    .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, LTrack.getArtist())
                    .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, LTrack.getDuration())
                    .putString(MediaMetadataCompat.METADATA_KEY_ART_URI, LTrack.getAlbumArt())
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, LTrack.getUri())
                    .build();

            mSession.setMetadata(metadata);
        }

    };

    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener =
            i -> {
                switch (i) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        mediaSessionCallback.onPlay();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        mediaSessionCallback.onPause();
                        break;
                    default:
                        mediaSessionCallback.onPause();
                        break;
                }

            };

    private BroadcastReceiver mBecomingNoiseRec = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
                mediaSessionCallback.onPause();
            }
        }
    };

    public class PlaybackServiceBinder extends Binder {
        public MediaSessionCompat.Token getMediaSessionToken() {
            return mSession.getSessionToken();
        }
    }
}
