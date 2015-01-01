package com.pascalwelsch.apkmirror;

import com.pascalwelsch.apkmirror.detail.AppDetailFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by pascalwelsch on 12/9/14.
 */
@Singleton
@Component(modules = TestModule.class)
public interface ApkMirrorComponent {

    void inject(ApkMirrorApp application);

    void inject(AppDetailFragment fragment);
}