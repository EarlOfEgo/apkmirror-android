package com.pascalwelsch.backendswitch;

import net.grandcentrix.tray.TrayModulePreferences;

import android.content.Context;

/**
 * Created by pascalwelsch on 2/1/15.
 */
public class BackendPreferences extends TrayModulePreferences {

    public static final String BACKEND_NAME = "backendName";

    public BackendPreferences(final Context context) {
        super(context, "backend");
    }
}
