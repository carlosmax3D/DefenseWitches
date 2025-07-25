package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class LatLngBoundsCreator implements Parcelable.Creator<LatLngBounds> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(LatLngBounds latLngBounds, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, latLngBounds.getVersionCode());
        b.a(parcel, 2, (Parcelable) latLngBounds.southwest, i, false);
        b.a(parcel, 3, (Parcelable) latLngBounds.northeast, i, false);
        b.D(parcel, o);
    }

    public LatLngBounds createFromParcel(Parcel parcel) {
        LatLng latLng;
        LatLng latLng2;
        int i;
        LatLng latLng3 = null;
        int n = a.n(parcel);
        int i2 = 0;
        LatLng latLng4 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    LatLng latLng5 = latLng3;
                    latLng2 = latLng4;
                    i = a.g(parcel, m);
                    latLng = latLng5;
                    break;
                case 2:
                    i = i2;
                    LatLng latLng6 = (LatLng) a.a(parcel, m, LatLng.CREATOR);
                    latLng = latLng3;
                    latLng2 = latLng6;
                    break;
                case 3:
                    latLng = (LatLng) a.a(parcel, m, LatLng.CREATOR);
                    latLng2 = latLng4;
                    i = i2;
                    break;
                default:
                    a.b(parcel, m);
                    latLng = latLng3;
                    latLng2 = latLng4;
                    i = i2;
                    break;
            }
            i2 = i;
            latLng4 = latLng2;
            latLng3 = latLng;
        }
        if (parcel.dataPosition() == n) {
            return new LatLngBounds(i2, latLng4, latLng3);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public LatLngBounds[] newArray(int i) {
        return new LatLngBounds[i];
    }
}
