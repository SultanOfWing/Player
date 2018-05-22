package seleznov.nope.player.helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by User on 22.05.2018.
 */

public class PermissionInspector {


    public static boolean checkPermission(Context context, String[] permissions){
        int result = ContextCompat.checkSelfPermission(context,
                permissions[0]);

        return result == PackageManager.PERMISSION_GRANTED;
    }

}
