package com.pascalwelsch.apkmirror.connection;

import com.pascalwelsch.backendswitch.Initializer;
import com.pascalwelsch.backendswitch.Server;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.Set;

/**
 * Created by pascalwelsch on 2/8/15.
 */
public class BackendInitializer extends Initializer {

    public BackendInitializer(final Context context) {
        super(context);
    }

    @Override
    public Set<Server> getBackends() {
        final Set<Server> backends = super.getBackends();
        backends.add(new TestBackend());
        backends.add(new CustomBackend());
        return backends;
    }

    @NonNull
    @Override
    public Server getDefaultBackend() {
        return new ProductionBackend();
    }
}
