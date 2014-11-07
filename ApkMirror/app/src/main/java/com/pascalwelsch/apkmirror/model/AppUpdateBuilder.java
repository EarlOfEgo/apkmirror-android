package com.pascalwelsch.apkmirror.model;

import android.os.Parcel;

public class AppUpdateBuilder {

    private String mDownloadUrl;

    private int mDownloads;

    private String mFilename;

    private int mFilesize;

    private String mIconUrl;

    private Parcel mIn;

    private String mListingUrl;

    private String mMd5;

    private int mMinSdk;

    private String mName;

    private String mPackageName;

    private String mPublisher;

    private String mSha1;

    private String mUploaded;

    private String mUploader;

    private int mVersion;

    private String mVersionName;

    public AppUpdate createAppUpdate() {
        return new AppUpdate(mPublisher, mDownloadUrl, mDownloads, mFilename, mFilesize, mIconUrl,
                mListingUrl, mMd5, mMinSdk, mName, mPackageName, mSha1, mUploaded, mUploader,
                mVersion, mVersionName);
    }

    public AppUpdateBuilder setDownloadUrl(final String downloadUrl) {
        mDownloadUrl = downloadUrl;
        return this;
    }

    public AppUpdateBuilder setDownloads(final int downloads) {
        mDownloads = downloads;
        return this;
    }

    public AppUpdateBuilder setFilename(final String filename) {
        mFilename = filename;
        return this;
    }

    public AppUpdateBuilder setFilesize(final int filesize) {
        mFilesize = filesize;
        return this;
    }

    public AppUpdateBuilder setIconUrl(final String iconUrl) {
        mIconUrl = iconUrl;
        return this;
    }

    public AppUpdateBuilder setIn(final Parcel in) {
        mIn = in;
        return this;
    }

    public AppUpdateBuilder setListingUrl(final String listingUrl) {
        mListingUrl = listingUrl;
        return this;
    }

    public AppUpdateBuilder setMd5(final String md5) {
        mMd5 = md5;
        return this;
    }

    public AppUpdateBuilder setMinSdk(final int minSdk) {
        mMinSdk = minSdk;
        return this;
    }

    public AppUpdateBuilder setName(final String name) {
        mName = name;
        return this;
    }

    public AppUpdateBuilder setPackageName(final String packageName) {
        mPackageName = packageName;
        return this;
    }

    public AppUpdateBuilder setPublisher(final String publisher) {
        mPublisher = publisher;
        return this;
    }

    public AppUpdateBuilder setSha1(final String sha1) {
        mSha1 = sha1;
        return this;
    }

    public AppUpdateBuilder setUploaded(final String uploaded) {
        mUploaded = uploaded;
        return this;
    }

    public AppUpdateBuilder setUploader(final String uploader) {
        mUploader = uploader;
        return this;
    }

    public AppUpdateBuilder setVersion(final int version) {
        mVersion = version;
        return this;
    }

    public AppUpdateBuilder setVersionName(final String versionName) {
        mVersionName = versionName;
        return this;
    }
}