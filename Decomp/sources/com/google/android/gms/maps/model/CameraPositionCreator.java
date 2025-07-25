package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class CameraPositionCreator implements Parcelable.Creator<CameraPosition> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(CameraPosition cameraPosition, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, cameraPosition.getVersionCode());
        b.a(parcel, 2, (Parcelable) cameraPosition.target, i, false);
        b.a(parcel, 3, cameraPosition.zoom);
        b.a(parcel, 4, cameraPosition.tilt);
        b.a(parcel, 5, cameraPosition.bearing);
        b.D(parcel, o);
    }

    public CameraPosition createFromParcel(Parcel parcel) {
        float f = 0.0f;
        int n = a.n(parcel);
        int i = 0;
        LatLng latLng = null;
        float f2 = 0.0f;
        float f3 = 0.0f;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    latLng = (LatLng) a.a(parcel, m, LatLng.CREATOR);
                    break;
                case 3:
                    f3 = a.j(parcel, m);
                    break;
                case 4:
                    f2 = a.j(parcel, m);
                    break;
                case 5:
                    f = a.j(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new CameraPosition(i, latLng, f3, f2, f);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public CameraPosition[] newArray(int i) {
        return new CameraPosition[i];
    }
}
