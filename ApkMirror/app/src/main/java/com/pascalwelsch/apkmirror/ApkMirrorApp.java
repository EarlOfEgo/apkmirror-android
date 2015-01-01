package com.pascalwelsch.apkmirror;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import javax.inject.Inject;

/**
 * Created by pascalwelsch on 12/8/14.
 */
public class ApkMirrorApp extends Application {

    private static final String TAG = ApkMirrorApp.class.getSimpleName();

    @Inject
    OkHttpClient mOkHttpClient;

    @Inject
    Picasso mPicasso;

    public ApkMirrorComponent getComponent() {
        return mComponent;
    }

    private ApkMirrorComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.v(TAG, "injecting application");
        mComponent = Dagger_ApkMirrorComponent.builder()
                .testModule(new TestModule(this)).build();
        mComponent.inject(this);
        Log.v(TAG, "application injected");

        Log.v(TAG, "OkHttpClient is " + mOkHttpClient);
        Log.v(TAG, "Picasso is " + mPicasso);

    }

    public static ApkMirrorApp get(final Context context){
        return (ApkMirrorApp) context.getApplicationContext();
    }
}
