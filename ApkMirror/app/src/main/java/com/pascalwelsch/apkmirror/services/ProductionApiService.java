package com.pascalwelsch.apkmirror.services;

import com.pascalwelsch.backendswitch.Backend;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;

/**
 * Created by jannisveerkamp on 07.11.14.
 */
public class ProductionApiService implements ApiService {

    private final static String URL_RECENTS = "recents";

    private final OkHttpClient mClient;

    public ProductionApiService() {
        mClient = new OkHttpClient();
        mClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(final Chain chain) throws IOException {
                final Request request = chain.request();
                Log.v("OkHttp", request.toString());
                return chain.proceed(request);
            }
        });
    }

    @Override
    public void getRecents(Callback responseCallback) {
        final Uri uri = Backend.get().getUriBuilder().appendEncodedPath(URL_RECENTS).build();
        Request request = new Request.Builder().url(uri.toString()).build();
        mClient.newCall(request).enqueue(responseCallback);
    }

}
