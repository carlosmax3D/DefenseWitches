package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.ArrayList;

public class PolylineOptionsCreator implements Parcelable.Creator<PolylineOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(PolylineOptions polylineOptions, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, polylineOptions.getVersionCode());
        b.b(parcel, 2, polylineOptions.getPoints(), false);
        b.a(parcel, 3, polylineOptions.getWidth());
        b.c(parcel, 4, polylineOptions.getColor());
        b.a(parcel, 5, polylineOptions.getZIndex());
        b.a(parcel, 6, polylineOptions.isVisible());
        b.a(parcel, 7, polylineOptions.isGeodesic());
        b.D(parcel, o);
    }

    public PolylineOptions createFromParcel(Parcel parcel) {
        float f = 0.0f;
        boolean z = false;
        int n = a.n(parcel);
        ArrayList arrayList = null;
        boolean z2 = false;
        int i = 0;
        float f2 = 0.0f;
        int i2 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i2 = a.g(parcel, m);
                    break;
                case 2:
                    arrayList = a.c(parcel, m, LatLng.CREATOR);
                    break;
                case 3:
                    f2 = a.j(parcel, m);
                    break;
                case 4:
                    i = a.g(parcel, m);
                    break;
                case 5:
                    f = a.j(parcel, m);
                    break;
                case 6:
                    z2 = a.c(parcel, m);
                    break;
                case 7:
                    z = a.c(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new PolylineOptions(i2, arrayList, f2, i, f, z2, z);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public PolylineOptions[] newArray(int i) {
        return new PolylineOptions[i];
    }
}
