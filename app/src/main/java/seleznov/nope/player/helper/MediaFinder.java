package seleznov.nope.player.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;



/**
 * Created by User on 22.05.2018.
 */

public class MediaFinder {

    public static Cursor getAllMedia(Context context){
        ContentResolver resolver =
                context.getContentResolver();

        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        return resolver.query(musicUri, null,
                null, null, null);

    }

    

}
