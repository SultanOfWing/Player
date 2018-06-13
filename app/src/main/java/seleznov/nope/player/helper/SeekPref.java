package seleznov.nope.player.helper;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by User on 13.06.2018.
 */

public class SeekPref {
    private static final String LAST_TRACK_DUR = "trackDur";

    public static int getLastTrackDur(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(LAST_TRACK_DUR, 0);
    }

    public static void setLastTrackDur(Context context, int dur) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(LAST_TRACK_DUR, dur)
                .apply();
    }
}
