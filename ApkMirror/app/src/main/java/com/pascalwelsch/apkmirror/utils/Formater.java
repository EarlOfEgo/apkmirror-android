package com.pascalwelsch.apkmirror.utils;

import com.pascalwelsch.apkmirror.R;
import com.pascalwelsch.apkmirror.model.AppUpdate;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.widget.TextView;

/**
 * ApkMirror
 *
 * @author stephan 09.11.14
 */
public class Formater {

    /**
     * http://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java
     */
    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static void setButtonText(final Context context, final TextView button,
            final AppUpdate appUpdate) {
        try {
            PackageInfo pInfo = context.getPackageManager()
                    .getPackageInfo(appUpdate.getPackageName(), 0);
            int installedVersion = pInfo.versionCode;
            int updateVersion = appUpdate.getVersion();
            if (installedVersion < updateVersion) {
                button.setText(R.string.app_action_update);
            } else {
                button.setText(R.string.app_action_open);
            }
        } catch (PackageManager.NameNotFoundException e) {
            button.setText(R.string.app_action_install);
        }
    }

    public static void setButtonTextColor(final Context context, final TextView button,
            final AppUpdate appUpdate) {
        final Resources resources = context.getResources();
        try {
            PackageInfo pInfo = context.getPackageManager()
                    .getPackageInfo(appUpdate.getPackageName(), 0);
            int installedVersion = pInfo.versionCode;
            int updateVersion = appUpdate.getVersion();
            if (installedVersion < updateVersion) {
                button.setTextColor(resources.getColor(R.color.highlights));
            } else {
                button.setTextColor(resources.getColor(R.color.text_light));
            }
        } catch (PackageManager.NameNotFoundException e) {
            button.setTextColor(resources.getColor(R.color.highlights));
        }
    }
}
