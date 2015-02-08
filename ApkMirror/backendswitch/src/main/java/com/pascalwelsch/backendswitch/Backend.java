package com.pascalwelsch.backendswitch;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pascalwelsch on 1/8/15.
 */
public class Backend {

    /*package*/ static Server currentActiveFacade;

    /*package*/ static ArrayList<Server> sBackends = new ArrayList<>();

    public static ArrayList<Server> getsBackends() {
        return sBackends;
    }

    /**
     * @return the current selected Facade
     */
    public static Server get() {
        if (currentActiveFacade == null) {
            throw new IllegalStateException("Backend in not initialized in Application#onCreate()");
        }
        return currentActiveFacade;
    }

    /**
     * sets a new facade by default. overrides the facade set in BuildConfig#BACKEND
     */
    public static void load(final Context context) {

        final String backendName = new BackendPreferences(context)
                .getString(BackendPreferences.BACKEND_NAME, BuildConfig.BACKEND);

        currentActiveFacade = Backend.nameToBackend(backendName);

        if (currentActiveFacade == null) {
            currentActiveFacade = sBackends.get(0);
        }
    }

    /**
     * sets a new facade by default. overrides the facade set in BuildConfig#BACKEND_NAME
     */
    public static void load(final Context context, final String backendName) {
        new BackendPreferences(context).put(BackendPreferences.BACKEND_NAME, backendName);
        load(context);
    }

    private static Server nameToBackend(final String backendName) {
        if (backendName == null) {
            return null;
        }
        Server foundBackend = null;
        for (Server backend : sBackends) {
            if (backend.getName().equals(backendName)) {
                foundBackend = backend;
                break;
            }
        }

        return foundBackend;
    }
}
