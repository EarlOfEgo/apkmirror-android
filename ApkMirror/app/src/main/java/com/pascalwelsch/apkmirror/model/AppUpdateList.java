package com.pascalwelsch.apkmirror.model;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by pascalwelsch on 10/19/14.
 */
public class AppUpdateList implements Parcelable {

    public static final Creator<AppUpdateList> CREATOR = new Creator<AppUpdateList>() {
        public AppUpdateList createFromParcel(Parcel source) {
            return new AppUpdateList(source);
        }

        public AppUpdateList[] newArray(int size) {
            return new AppUpdateList[size];
        }
    };

    @SerializedName("apps")
    private List<AppUpdate> mApps;

    @SerializedName("number")
    private int mNumber;

    public AppUpdateList(final List<AppUpdate> apps, final int number) {
        mApps = apps;
        mNumber = number;
    }

    private AppUpdateList(Parcel in) {
        in.readList(this.mApps, AppUpdate.class.getClassLoader());
        this.mNumber = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<AppUpdate> getApps() {
        return mApps;
    }

    public void setApps(final List<AppUpdate> apps) {
        mApps = apps;
    }

    public static Creator<AppUpdateList> getCreator() {
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
