package com.pascalwelsch.apkmirror.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pascalwelsch on 10/19/14.
 */
public class AppUpdate implements Parcelable {

    public static final Creator<AppUpdate> CREATOR = new Creator<AppUpdate>() {
        public AppUpdate createFromParcel(Parcel source) {
            return new AppUpdate(source);
        }

        public AppUpdate[] newArray(int size) {
            return new AppUpdate[size];
        }
    };

    private final String mPublisher;

    private final String mUpdateDate;

    private String mIcon;

    private String mName;

    private String mPackageName;

    private int mVersionCode;

    public AppUpdate(final String name, final String icon, final String publisher,
            final String updateDate, final String packageName, final int versionCode) {
        mName = name;
        mIcon = icon;
        mPublisher = publisher;
        mUpdateDate = updateDate;
        mPackageName = packageName;
        mVersionCode = versionCode;
    }

    private AppUpdate(Parcel in) {
        this.mPublisher = in.readString();
        this.mUpdateDate = in.readString();
        this.mIcon = in.readString();
        this.mName = in.readString();
        this.mPackageName = in.readString();
        this.mVersionCode = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getIcon() {
        return mIcon;
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

    public String getUpdateDate() {
        return mUpdateDate;
    }

    public int getVersionCode() {
        return mVersionCode;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mPublisher);
        dest.writeString(this.mUpdateDate);
        dest.writeString(this.mIcon);
        dest.writeString(this.mName);
        dest.writeString(this.mPackageName);
        dest.writeInt(this.mVersionCode);
    }
}
