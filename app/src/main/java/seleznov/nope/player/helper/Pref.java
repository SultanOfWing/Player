package seleznov.nope.player.helper;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by User on 13.06.2018.
 */

public class Pref {
    private static final String PREF_LAST_TRACK_DUR = "trackDur";
    private static final String PREF_SEARCH_QUERY = "searchQuery";

    public static int getLastTrackDur(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_LAST_TRACK_DUR, 0);
    }

    public static void setLastTrackDur(Context context, int dur) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_LAST_TRACK_DUR, dur)
                .apply();
    }

    public static String getQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SEARCH_QUERY, null);
    }

    public static void setQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();
    }

}
