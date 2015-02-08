package com.pascalwelsch.apkmirror.model;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by pascalwelsch on 10/19/14.
 */
public class AppList implements Parcelable {

    public static final Creator<AppList> CREATOR = new Creator<AppList>() {
        public AppList createFromParcel(Parcel source) {
            return new AppList(source);
        }

        public AppList[] newArray(int size) {
            return new AppList[size];
        }
    };

    @SerializedName("apps")
    private List<AppInfo> mApps;

    @SerializedName("number")
    private int mNumber;

    public AppList(final List<AppInfo> apps, final int number) {
        mApps = apps;
        mNumber = number;
    }

    private AppList(Parcel in) {
        in.readList(this.mApps, AppInfo.class.getClassLoader());
        this.mNumber = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<AppInfo> getApps() {
        return mApps;
    }

    public void setApps(final List<AppInfo> apps) {
        mApps = apps;
    }

    public static Creator<AppList> getCreator() {
        return CREATOR;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(final int number) {
        mNumber = number;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.mApps);
        dest.writeInt(this.mNumber);
    }
}
