package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class LatLngCreator implements Parcelable.Creator<LatLng> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(LatLng latLng, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, latLng.getVersionCode());
        b.a(parcel, 2, latLng.latitude);
        b.a(parcel, 3, latLng.longitude);
        b.D(parcel, o);
    }

    public LatLng createFromParcel(Parcel parcel) {
        double d = 0.0d;
        int n = a.n(parcel);
        int i = 0;
        double d2 = 0.0d;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    d2 = a.k(parcel, m);
                    break;
                case 3:
                    d = a.k(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new LatLng(i, d2, d);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public LatLng[] newArray(int i) {
        return new LatLng[i];
    }
}
