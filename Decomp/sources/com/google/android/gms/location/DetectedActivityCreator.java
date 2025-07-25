package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class DetectedActivityCreator implements Parcelable.Creator<DetectedActivity> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(DetectedActivity detectedActivity, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, detectedActivity.xp);
        b.c(parcel, 1000, detectedActivity.getVersionCode());
        b.c(parcel, 2, detectedActivity.xq);
        b.D(parcel, o);
    }

    public DetectedActivity createFromParcel(Parcel parcel) {
        int i = 0;
        int n = a.n(parcel);
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i2 = a.g(parcel, m);
                    break;
                case 2:
                    i = a.g(parcel, m);
                    break;
                case 1000:
                    i3 = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new DetectedActivity(i3, i2, i);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public DetectedActivity[] newArray(int i) {
        return new DetectedActivity[i];
    }
}
