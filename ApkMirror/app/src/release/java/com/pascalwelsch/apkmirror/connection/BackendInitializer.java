package com.pascalwelsch.apkmirror.connection;

import com.pascalwelsch.backendswitch.Initializer;
import com.pascalwelsch.backendswitch.Server;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by pascalwelsch on 2/8/15.
 */
public class BackendInitializer extends Initializer {

    public BackendInitializer(final Context context) {
        super(context);
    }

    @NonNull
    @Override
    public Server getDefaultBackend() {
        return new ProductionBackend();
    }
}
