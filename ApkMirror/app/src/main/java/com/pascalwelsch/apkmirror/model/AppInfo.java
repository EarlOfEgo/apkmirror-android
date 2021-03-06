package com.pascalwelsch.apkmirror.model;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pascalwelsch on 10/19/14.
 */
public class AppInfo implements Parcelable {


    public static final Parcelable.Creator<AppInfo> CREATOR
            = new Parcelable.Creator<AppInfo>() {
        public AppInfo createFromParcel(Parcel source) {
            return new AppInfo(source);
        }

        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
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


    public AppInfo() {
    }


    public AppInfo(final String publisher, final String downloadUrl, final int downloads,
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

    private AppInfo(Parcel in) {
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

    public void setDownloadUrl(final String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public int getDownloads() {
        return mDownloads;
    }

    public void setDownloads(final int downloads) {
        mDownloads = downloads;
    }

    public long getFileSize() {
        return mFileSize;
    }

    public void setFileSize(final long filesize) {
        mFileSize = filesize;
    }

    public String getFilename() {
        return mFilename;
    }

    public void setFilename(final String filename) {
        mFilename = filename;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(final String iconUrl) {
        mIconUrl = iconUrl;
    }

    public String getListingUrl() {
        return mListingUrl;
    }

    public void setListingUrl(final String listingUrl) {
        mListingUrl = listingUrl;
    }

    public String getMd5() {
        return mMd5;
    }

    public void setMd5(final String md5) {
        mMd5 = md5;
    }

    public int getMinSdk() {
        return mMinSdk;
    }

    public void setMinSdk(final int minSdk) {
        mMinSdk = minSdk;
    }

    public String getName() {
        return mName;
    }

    public void setName(final String name) {
        mName = name;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(final String packageName) {
        mPackageName = packageName;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public String getSha1() {
        return mSha1;
    }

    public void setSha1(final String sha1) {
        mSha1 = sha1;
    }

    public String getUploaded() {
        return mUploaded;
    }

    public void setUploaded(final String uploaded) {
        mUploaded = uploaded;
    }

    public String getUploader() {
        return mUploader;
    }

    public void setUploader(final String uploader) {
        mUploader = uploader;
    }

    public int getVersion() {
        return mVersion;
    }

    public void setVersion(final int version) {
        mVersion = version;
    }

    public String getVersionName() {
        return mVersionName;
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
