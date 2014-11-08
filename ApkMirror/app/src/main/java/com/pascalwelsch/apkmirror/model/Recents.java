package com.pascalwelsch.apkmirror.model;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ApkMirror
 *
 * @author stephan 07.11.14
 */
public class Recents implements Parcelable {


    public static final Creator<Recents> CREATOR = new Creator<Recents>() {
        public Recents createFromParcel(Parcel source) {
            return new Recents(source);
        }

        public Recents[] newArray(int size) {
            return new Recents[size];
        }
    };

    @SerializedName("apps")
    List<AppUpdate> mApps;

    @SerializedName("number")
    int mNumber;

    public Recents() {
    }

    public List<AppUpdate> getApps() {
        return mApps;
    }

    private Recents(Parcel in) {
        in.readTypedList(mApps, AppUpdate.CREATOR);
        this.mNumber = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(final int number) {
        mNumber = number;
    }

    public void setApps(final List<AppUpdate> apps) {
        mApps = apps;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mApps);
        dest.writeInt(this.mNumber);
    }
}
