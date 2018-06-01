package seleznov.nope.player.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaControllerCompat.TransportControls;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerFragment;
import seleznov.nope.player.R;
import seleznov.nope.player.eventbus.RxEventBus;
import seleznov.nope.player.model.TrackListManager;
import seleznov.nope.player.model.dto.Track;
import seleznov.nope.player.playback.PlaybackService;

/**
 * Created by User on 26.05.2018.
 */

public class ControllerFragment extends DaggerFragment {

    @BindView(R.id.image_controller)
    ImageView albumImg;
    @BindView(R.id.button_play_pause)
    ImageButton playStopButton;

    @Inject
    RxEventBus mEventBus;
    @Inject
    TrackListManager mListManager;

    private MediaControllerCompat mControllerCompat;
    private PlaybackService.PlaybackServiceBinder mServiceBinder;
    private boolean isPlaying;

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

        albumImg.setImageResource(R.drawable.placeholder);

        playStopButton.setOnClickListener(view1 -> {
            if(mControllerCompat == null)
                return;

            TransportControls transportControls =
                    mControllerCompat.getTransportControls();
            if(!isPlaying){
                transportControls.play();
            }else {
                transportControls.pause();
            }
        });

        mEventBus.subscribe((message) -> {
            if (message instanceof Integer) {
                Integer position = (Integer) message;
                mListManager.setTrack(position);
                TransportControls transportControls =
                        mControllerCompat.getTransportControls();
                transportControls.play();
               }
            });

        return view;
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

    private MediaControllerCompat.Callback mCallback = new MediaControllerCompat.Callback() {

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
           String albumArtUri = metadata.getString(
                   MediaMetadataCompat.METADATA_KEY_ART_URI);

            Picasso.with(getContext())
                    .load(albumArtUri)
                    .placeholder(R.drawable.placeholder)
                    .into(albumImg);
        }

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            if (state == null)
                return;

            isPlaying = state.getState() == PlaybackStateCompat.STATE_PLAYING;
            if(isPlaying){
                playStopButton.setImageResource(R.drawable.ic_pause);
            }else {
                playStopButton.setImageResource(R.drawable.ic_play);
            }

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
                mCallback.onPlaybackStateChanged(mControllerCompat.getPlaybackState());
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

