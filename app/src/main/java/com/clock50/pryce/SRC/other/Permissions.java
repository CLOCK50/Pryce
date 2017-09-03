package com.clock50.pryce.SRC.other;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Handles the permissions
 */
public class Permissions {

    /**
     * Return whether the given permissions have been given by the user.
     *
     * @param context the current context
     * @param permissions the permissions being checked
     * @return false if any one of the permissions have not been given, true if all permissions given
     */
    public static boolean checkPermissions(Context context, String[] permissions){
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Requests all the given permissions required by the activity.
     *
     * @param activity the activity from which the permissions are being requested
     * @param permissions the permissions that are being requested
     */
    public static void requestPermissions(Activity activity, String[] permissions){
        ActivityCompat.requestPermissions(activity, permissions, 8000);
    }

}
