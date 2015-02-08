package com.pascalwelsch.backendswitch;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by pascalwelsch on 2/8/15.
 */
public abstract class Initializer {

    private Context mContext;

    public Initializer(final Context context) {
        mContext = context;
    }

    public Set<Server> getBackends() {
        return new HashSet<>();
    }

    @NonNull
    public abstract Server getDefaultBackend();

    public void init() {
        Backend.sBackends.add(getDefaultBackend());
        Backend.sBackends.addAll(getBackends());
        Backend.load(mContext);
    }
}
