package seleznov.nope.player.ui.playlist;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import seleznov.nope.player.model.local.MediaCursor;
import seleznov.nope.player.helper.MediaFinder;
import seleznov.nope.player.model.local.dto.LocalTrack;

/**
 * Created by User on 20.05.2018.
 */

public class PlayListPresenter implements PlayListContract.Presenter {


    @Nullable
    private PlayListContract.View mView;
    private Context mAppContext;

    @Inject
    PlayListPresenter(Context context){
        mAppContext = context;
    }

    @Override
    public void updatePlayList() {
        List<LocalTrack> LocalTrackList = new ArrayList<>();
        Cursor cursor = MediaFinder.getAllMedia(mAppContext);
        MediaCursor mediaCursor = new MediaCursor(cursor);

        try {
            mediaCursor.moveToFirst();
            while (!mediaCursor.isAfterLast()) {
                LocalTrack currLocalTrack =  mediaCursor.getTrack();
                Cursor albumAptCursor = MediaFinder.getAlbumArt(mAppContext,
                        currLocalTrack.getAlbumId());

                String albumArtUri;
                try{
                    if(albumAptCursor.moveToFirst()){
                        albumArtUri = albumAptCursor.getString(
                                albumAptCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                        currLocalTrack.setAlbumArt(albumArtUri);
                    }
                }
                finally {
                    albumAptCursor.close();
                }


                LocalTrackList.add(currLocalTrack);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        mView.setPlayList(LocalTrackList);
    }

    @Override
    public void takeView(PlayListContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }



}
