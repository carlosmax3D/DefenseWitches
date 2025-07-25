package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.ArrayList;
import java.util.List;

public class PolygonOptionsCreator implements Parcelable.Creator<PolygonOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(PolygonOptions polygonOptions, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, polygonOptions.getVersionCode());
        b.b(parcel, 2, polygonOptions.getPoints(), false);
        b.c(parcel, 3, polygonOptions.eH(), false);
        b.a(parcel, 4, polygonOptions.getStrokeWidth());
        b.c(parcel, 5, polygonOptions.getStrokeColor());
        b.c(parcel, 6, polygonOptions.getFillColor());
        b.a(parcel, 7, polygonOptions.getZIndex());
        b.a(parcel, 8, polygonOptions.isVisible());
        b.a(parcel, 9, polygonOptions.isGeodesic());
        b.D(parcel, o);
    }

    public PolygonOptions createFromParcel(Parcel parcel) {
        float f = 0.0f;
        boolean z = false;
        int n = a.n(parcel);
        ArrayList arrayList = null;
        ArrayList arrayList2 = new ArrayList();
        boolean z2 = false;
        int i = 0;
        int i2 = 0;
        float f2 = 0.0f;
        int i3 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i3 = a.g(parcel, m);
                    break;
                case 2:
                    arrayList = a.c(parcel, m, LatLng.CREATOR);
                    break;
                case 3:
                    a.a(parcel, m, (List) arrayList2, getClass().getClassLoader());
                    break;
                case 4:
                    f2 = a.j(parcel, m);
                    break;
                case 5:
                    i2 = a.g(parcel, m);
                    break;
                case 6:
                    i = a.g(parcel, m);
                    break;
                case 7:
                    f = a.j(parcel, m);
                    break;
                case 8:
                    z2 = a.c(parcel, m);
                    break;
                case 9:
                    z = a.c(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new PolygonOptions(i3, arrayList, arrayList2, f2, i2, i, f, z2, z);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public PolygonOptions[] newArray(int i) {
        return new PolygonOptions[i];
    }
}
