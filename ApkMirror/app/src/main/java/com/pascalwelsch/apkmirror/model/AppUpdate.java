package com.pascalwelsch.apkmirror.model;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pascalwelsch on 10/19/14.
 */
public class AppUpdate implements Parcelable {


    public static final Parcelable.Creator<AppUpdate> CREATOR
            = new Parcelable.Creator<AppUpdate>() {
        public AppUpdate createFromParcel(Parcel source) {
            return new AppUpdate(source);
        }

        public AppUpdate[] newArray(int size) {
            return new AppUpdate[size];
        }
    };

    @SerializedName("downloadUrl")
    private String mDownloadUrl;

    @SerializedName("downloads")
    private int mDownloads;

    @SerializedName("fileSize")
    private long mFileSize;

    @SerializedName("filename")
    private String mFilename;

    @SerializedName("iconUrl")
    private String mIconUrl;

    @SerializedName("listingUrl")
    private String mListingUrl;

    @SerializedName("md5")
    private String mMd5;

    @SerializedName("minSdk")
    private int mMinSdk;

    @SerializedName("name")
    private String mName;

    @SerializedName("packageName")
    private String mPackageName;

    @SerializedName("publisher")
    private String mPublisher;

    @SerializedName("sha1")
    private String mSha1;

    @SerializedName("uploaded")
    private String mUploaded;

    @SerializedName("uploader")
    private String mUploader;

    @SerializedName("version")
    private int mVersion;

    @SerializedName("versionName")
    private String mVersionName;

    public AppUpdate() {
    }


    public AppUpdate(final String publisher, final String downloadUrl, final int downloads,
            final String filename, final long filesize, final String iconUrl,
            final String listingUrl, final String md5, final int minSdk, final String name,
            final String packageName, final String sha1, final String uploaded,
            final String uploader, final int version, final String versionName) {
        mPublisher = publisher;
        mDownloadUrl = downloadUrl;
        mDownloads = downloads;
        mFilename = filename;
        mFileSize = filesize;
        mIconUrl = iconUrl;
        mListingUrl = listingUrl;
        mMd5 = md5;
        mMinSdk = minSdk;
        mName = name;
        mPackageName = packageName;
        mSha1 = sha1;
        mUploaded = uploaded;
        mUploader = uploader;
        mVersion = version;
        mVersionName = versionName;
    }


    private AppUpdate(Parcel in) {
        this.mPublisher = in.readString();
        this.mDownloadUrl = in.readString();
        this.mDownloads = in.readInt();
        this.mFilename = in.readString();
        this.mFileSize = in.readLong();
        this.mIconUrl = in.readString();
        this.mListingUrl = in.readString();
        this.mMd5 = in.readString();
        this.mMinSdk = in.readInt();
        this.mName = in.readString();
        this.mPackageName = in.readString();
        this.mSha1 = in.readString();
        this.mUploaded = in.readString();
        this.mUploader = in.readString();
        this.mVersion = in.readInt();
        this.mVersionName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public int getDownloads() {
        return mDownloads;
    }

    public long getFileSize() {
        return mFileSize;
    }

    public String getFilename() {
        return mFilename;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public String getListingUrl() {
        return mListingUrl;
    }

    public String getMd5() {
        return mMd5;
    }

    public int getMinSdk() {
        return mMinSdk;
    }

    public String getName() {
        return mName;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public String getSha1() {
        return mSha1;
    }

    public String getUploaded() {
        return mUploaded;
    }

    public String getUploader() {
        return mUploader;
    }

    public int getVersion() {
        return mVersion;
    }

    public String getVersionName() {
        return mVersionName;
    }

    public void setDownloadUrl(final String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public void setDownloads(final int downloads) {
        mDownloads = downloads;
    }

    public void setFileSize(final long filesize) {
        mFileSize = filesize;
    }

    public void setFilename(final String filename) {
        mFilename = filename;
    }

    public void setIconUrl(final String iconUrl) {
        mIconUrl = iconUrl;
    }

    public void setListingUrl(final String listingUrl) {
        mListingUrl = listingUrl;
    }

    public void setMd5(final String md5) {
        mMd5 = md5;
    }

    public void setMinSdk(final int minSdk) {
        mMinSdk = minSdk;
    }

    public void setName(final String name) {
        mName = name;
    }

    public void setPackageName(final String packageName) {
        mPackageName = packageName;
    }

    public void setPublisher(final String publisher) {
        mPublisher = publisher;
    }

    public void setSha1(final String sha1) {
        mSha1 = sha1;
    }

    public void setUploaded(final String uploaded) {
        mUploaded = uploaded;
    }

    public void setUploader(final String uploader) {
        mUploader = uploader;
    }

    public void setVersion(final int version) {
        mVersion = version;
    }

    public void setVersionName(final String versionName) {
        mVersionName = versionName;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mPublisher);
        dest.writeString(this.mDownloadUrl);
        dest.writeInt(this.mDownloads);
        dest.writeString(this.mFilename);
        dest.writeLong(this.mFileSize);
        dest.writeString(this.mIconUrl);
        dest.writeString(this.mListingUrl);
        dest.writeString(this.mMd5);
        dest.writeInt(this.mMinSdk);
        dest.writeString(this.mName);
        dest.writeString(this.mPackageName);
        dest.writeString(this.mSha1);
        dest.writeString(this.mUploaded);
        dest.writeString(this.mUploader);
        dest.writeInt(this.mVersion);
        dest.writeString(this.mVersionName);
    }
}
