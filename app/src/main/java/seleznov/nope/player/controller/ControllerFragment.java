package seleznov.nope.player.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaControllerCompat.TransportControls;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.squareup.picasso.Picasso;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerFragment;
import seleznov.nope.player.R;
import seleznov.nope.player.eventbus.RxEventBus;
import seleznov.nope.player.model.TrackListManager;
import seleznov.nope.player.playback.PlaybackService;

/**
 * Created by User on 26.05.2018.
 */

public class ControllerFragment extends DaggerFragment {

    @BindView(R.id.image_controller)
    ImageView albumImg;
    @BindView(R.id.button_play_pause)
    ImageButton playStopButton;
    @BindView(R.id.audio_progress)
    SeekBar seekBar;

    @Inject
    RxEventBus mEventBus;
    @Inject
    TrackListManager mListManager;
    @Inject
    Handler mHandler;

    private static final long UPDATE_INTERNAL = 1000;
    private static final long INITIAL_INTERVAL = 100;
    private static final String TAG = "ControllerFragment";
    private static final String DURATION_KEY = "trackDuration";

    private MediaControllerCompat mControllerCompat;
    private PlaybackService.PlaybackServiceBinder mServiceBinder;
    private PlaybackStateCompat mPlaybackState;
    private ScheduledFuture<?> mScheduleFuture;
    private boolean mIsPlaying;
    private int mTrackDur;

    private final Runnable mUpdateTask = this::updateSeek;
    private final ScheduledExecutorService mScheduledExecutorService =
            Executors.newSingleThreadScheduledExecutor();

    @Inject
    public ControllerFragment(){};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_controller, container,
                false);

        ButterKnife.bind(this, view);
        Intent intent = PlaybackService.newIntent(getContext());
        getActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        if(savedInstanceState != null){
            mTrackDur = savedInstanceState.getInt(DURATION_KEY, 0);
            seekBar.setMax(mTrackDur);
        }
        albumImg.setImageResource(R.drawable.placeholder);

        playStopButton.setOnClickListener(view1 -> {
            if(mControllerCompat == null)
                return;

            TransportControls transportControls =
                    mControllerCompat.getTransportControls();
            if(!mIsPlaying){
                transportControls.play();
                startUpdateSeek();
            }else {
                transportControls.pause();
                stopUpdateSeek();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopUpdateSeek();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mControllerCompat == null)
                    return;

                TransportControls transportControls =
                        mControllerCompat.getTransportControls();
                transportControls.seekTo(seekBar.getProgress());
                startUpdateSeek();
            }
        });

        mEventBus.subscribe((message) -> {
            if (message instanceof Integer) {
                TransportControls transportControls =
                        mControllerCompat.getTransportControls();
                Integer position = (Integer) message;
                if(position == mListManager.getPos() && mIsPlaying){
                    transportControls.pause();
                }
                else transportControls.play();

                mListManager.setPos(position);
               }
            });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopUpdateSeek();
        mScheduledExecutorService.shutdown();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(DURATION_KEY, mTrackDur);
    }

    @OnClick(R.id.button_forward)
    public void onClickButtonForward(){
        if(mControllerCompat == null)
            return;
        mControllerCompat.getTransportControls().skipToNext();
    }

    @OnClick(R.id.button_back)
    public void onClickButtonBack(){
        if(mControllerCompat == null)
            return;
        mControllerCompat.getTransportControls().skipToPrevious();
    }

    private void updateSeek(){
        if (mPlaybackState == null) return;

        long currPos  = (int) mPlaybackState.getPosition();
        if (mPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING) {
            long timeDelta = SystemClock.elapsedRealtime() -
                    mPlaybackState.getLastPositionUpdateTime();
            Log.i("delta", timeDelta+" "+currPos+" "+mPlaybackState.getPlaybackSpeed());
            currPos += (int) timeDelta * mPlaybackState.getPlaybackSpeed();

        }
        Log.i(TAG, String.valueOf(currPos));
        seekBar.setProgress((int) currPos);

    }

    private void updateState(PlaybackStateCompat state){
        if (state == null)
            return;
        mPlaybackState = state;
        mIsPlaying = mPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING;
        if(mIsPlaying){
            playStopButton.setImageResource(R.drawable.ic_pause);
            startUpdateSeek();
        }else {
            playStopButton.setImageResource(R.drawable.ic_play);
            stopUpdateSeek();
        }
    }

    private void startUpdateSeek(){
        stopUpdateSeek();
        if (!mScheduledExecutorService.isShutdown()) {
            mScheduleFuture = mScheduledExecutorService.scheduleAtFixedRate(
                    () -> mHandler.post(mUpdateTask), INITIAL_INTERVAL,
                    UPDATE_INTERNAL, TimeUnit.MILLISECONDS);
        }
    }

    private void stopUpdateSeek(){
        if (mScheduleFuture != null) {
            mScheduleFuture.cancel(false);
        }
    }

    private MediaControllerCompat.Callback mCallback = new MediaControllerCompat.Callback() {

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
           String albumArtUri = metadata.getString(
                   MediaMetadataCompat.METADATA_KEY_ART_URI);
           mTrackDur =(int) metadata.getLong(
                   MediaMetadataCompat.METADATA_KEY_DURATION);
           seekBar.setMax(mTrackDur);

            Picasso.with(getContext())
                    .load(albumArtUri)
                    .placeholder(R.drawable.placeholder)
                    .into(albumImg);
        }

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            updateState(state);
        }
    };

    private ServiceConnection mServiceConnection =  new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mServiceBinder = (PlaybackService.PlaybackServiceBinder) iBinder;
            try {
                mControllerCompat = new MediaControllerCompat(getActivity(),
                        mServiceBinder.getMediaSessionToken());
                mControllerCompat.registerCallback(mCallback);
                mCallback.onPlaybackStateChanged(
                        mControllerCompat.getPlaybackState());

            }catch (RemoteException e){
                mControllerCompat = null;
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mServiceBinder = null;
            if (mControllerCompat != null) {
                mControllerCompat.unregisterCallback(mCallback);
                mControllerCompat = null;
            }
        }
    };

}

