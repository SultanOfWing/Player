package seleznov.nope.player.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


/**
 * Created by User on 22.05.2018.
 */

public class MediaFinder {

    private static final Uri MEDIA_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private static final Uri ALBUM_URI = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

    public static Cursor getAllMedia(Context context){
        ContentResolver resolver =
                context.getContentResolver();

        return resolver.query(MEDIA_URI, null,
                null, null, null);

    }

    public static Cursor getAlbumArt(Context context, String albumId){
        ContentResolver resolver =
                context.getContentResolver();

        return resolver.query(ALBUM_URI, new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + " =?",
                new String[] {String.valueOf(albumId)},
                null);
    }

}
