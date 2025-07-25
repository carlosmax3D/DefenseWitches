package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class DetectedActivity implements SafeParcelable {
    public static final DetectedActivityCreator CREATOR = new DetectedActivityCreator();
    public static final int IN_VEHICLE = 0;
    public static final int ON_BICYCLE = 1;
    public static final int ON_FOOT = 2;
    public static final int STILL = 3;
    public static final int TILTING = 5;
    public static final int UNKNOWN = 4;
    private final int kg;
    int xp;
    int xq;

    public DetectedActivity(int i, int i2) {
        this.kg = 1;
        this.xp = i;
        this.xq = i2;
    }

    public DetectedActivity(int i, int i2, int i3) {
        this.kg = i;
        this.xp = i2;
        this.xq = i3;
    }

    private int aM(int i) {
        if (i > 6) {
            return 4;
        }
        return i;
    }

    public int describeContents() {
        return 0;
    }

    public int getConfidence() {
        return this.xq;
    }

    public int getType() {
        return aM(this.xp);
    }

    public int getVersionCode() {
        return this.kg;
    }

    public String toString() {
        return "DetectedActivity [type=" + getType() + ", confidence=" + this.xq + "]";
    }

    public void writeToParcel(Parcel parcel, int i) {
        DetectedActivityCreator.a(this, parcel, i);
    }
}
