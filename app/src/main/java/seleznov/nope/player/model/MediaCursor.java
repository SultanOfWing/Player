package seleznov.nope.player.model;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.provider.MediaStore;

import seleznov.nope.player.model.dto.Track;

/**
 * Created by User on 22.05.2018.
 */

public class MediaCursor extends CursorWrapper {

    public MediaCursor(Cursor cursor) {
        super(cursor);
    }

    public Track getTrack(){
        String id = getString(getColumnIndex(MediaStore.Audio.Media._ID));
        String title = getString(getColumnIndex(MediaStore.Audio.Media.TITLE));
        String artist = getString(getColumnIndex(MediaStore.Audio.Media.ARTIST));
        String album = getString(getColumnIndex(MediaStore.Audio.Media.ALBUM));
        String albumId = getString(getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
        String data = getString(getColumnIndex(MediaStore.Audio.Media.DATA));

        return new Track(id, title, artist, album, albumId, data);
    }


}
