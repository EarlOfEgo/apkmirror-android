package com.pascalwelsch.apkmirror.connection;

import com.pascalwelsch.apkmirror.BuildConfig;
import com.pascalwelsch.backendswitch.Server;

import android.net.Uri;

/**
 * Created by pascalwelsch on 2/1/15.
 */
public class CustomBackend extends Server {

    public static final String NAME = "alternative";

    private static final Uri URI = Uri.parse(BuildConfig.ALTERNATIVE_BACKEND_URL);

    public CustomBackend() {
        mUri = URI;
    }

    @Override
    public String getAuthority() {
        return URI.getAuthority();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getScheme() {
        return URI.getScheme();
    }

}
