package com.pascalwelsch.apkmirror.services;

import com.squareup.okhttp.Callback;

/**
 * Created by pascalwelsch on 2/1/15.
 */
public interface ApiService {

    void getRecents(Callback responseCallback);
}
