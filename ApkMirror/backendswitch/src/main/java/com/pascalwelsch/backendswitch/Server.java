package com.pascalwelsch.backendswitch;

import android.net.Uri;

import java.net.URI;

/**
 * Created by pascalwelsch on 1/14/15.
 */
public abstract class Server {

    protected Uri mUri;

    public Server() {
        //<scheme>://<authority><path>?<query>
        mUri = new Uri.Builder()
                .scheme(getScheme())
                .encodedAuthority(getAuthority())
                .build();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Server)) {
            return false;
        }

        final Server that = (Server) o;

        if (!mUri.equals(that.mUri)) {
            return false;
        }

        return true;
    }

    public abstract String getAuthority();

    public abstract String getName();

    public String getSSLCertificate() {
        return "";
    }

    public String getScheme() {
        return "http";
    }

    public Uri getUri() {
        return mUri;
    }

    public URI getURI() {
        return URI.create(mUri.toString());
    }

    public Uri.Builder getUriBuilder() {
        return getUri().buildUpon();
    }

    public String getUrl() {
        return getUri().toString();
    }

    @Override
    public int hashCode() {
        return mUri.hashCode();
    }
}
