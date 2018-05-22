package seleznov.nope.player.playlist;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import seleznov.nope.player.model.MediaCursor;
import seleznov.nope.player.model.dto.Track;
import seleznov.nope.player.helper.MediaFinder;

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
        List<Track> trackList = new ArrayList<>();
        Cursor cursor = MediaFinder.getAllMedia(mAppContext);
        MediaCursor mediaCursor = new MediaCursor(cursor);

        try {
            mediaCursor.moveToFirst();
            while (!mediaCursor.isAfterLast()) {
                trackList.add(mediaCursor.getTrack());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        mView.setPlayList(trackList);
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
