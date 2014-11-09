package com.pascalwelsch.apkmirror.services;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * Created by jannisveerkamp on 07.11.14.
 */
public class ApiService {

    private final static String BASE_URL = "http://pascalwelsch.com:4040";

    private final static String URL_RECENTS = "/recents";

    public static void getRecents(Callback responseCallback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(BASE_URL + URL_RECENTS).build();
        client.newCall(request).enqueue(responseCallback);
    }

}
