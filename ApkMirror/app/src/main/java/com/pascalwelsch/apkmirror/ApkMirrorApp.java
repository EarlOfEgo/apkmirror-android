package com.pascalwelsch.apkmirror;

import com.pascalwelsch.apkmirror.connection.BackendInitializer;

import android.app.Application;

/**
 * Created by pascalwelsch on 2/8/15.
 */
public class ApkMirrorApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        final BackendInitializer backendInitializer = new BackendInitializer(this);
        backendInitializer.init();
    }
}
