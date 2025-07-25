package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class MarkerOptionsCreator implements Parcelable.Creator<MarkerOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(MarkerOptions markerOptions, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, markerOptions.getVersionCode());
        b.a(parcel, 2, (Parcelable) markerOptions.getPosition(), i, false);
        b.a(parcel, 3, markerOptions.getTitle(), false);
        b.a(parcel, 4, markerOptions.getSnippet(), false);
        b.a(parcel, 5, markerOptions.eG(), false);
        b.a(parcel, 6, markerOptions.getAnchorU());
        b.a(parcel, 7, markerOptions.getAnchorV());
        b.a(parcel, 8, markerOptions.isDraggable());
        b.a(parcel, 9, markerOptions.isVisible());
        b.a(parcel, 10, markerOptions.isFlat());
        b.a(parcel, 11, markerOptions.getRotation());
        b.a(parcel, 12, markerOptions.getInfoWindowAnchorU());
        b.a(parcel, 13, markerOptions.getInfoWindowAnchorV());
        b.a(parcel, 14, markerOptions.getAlpha());
        b.D(parcel, o);
    }

    public MarkerOptions createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        LatLng latLng = null;
        String str = null;
        String str2 = null;
        IBinder iBinder = null;
        float f = 0.0f;
        float f2 = 0.0f;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        float f3 = 0.0f;
        float f4 = 0.5f;
        float f5 = 0.0f;
        float f6 = 1.0f;
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
                    str = a.m(parcel, m);
                    break;
                case 4:
                    str2 = a.m(parcel, m);
                    break;
                case 5:
                    iBinder = a.n(parcel, m);
                    break;
                case 6:
                    f = a.j(parcel, m);
                    break;
                case 7:
                    f2 = a.j(parcel, m);
                    break;
                case 8:
                    z = a.c(parcel, m);
                    break;
                case 9:
                    z2 = a.c(parcel, m);
                    break;
                case 10:
                    z3 = a.c(parcel, m);
                    break;
                case 11:
                    f3 = a.j(parcel, m);
                    break;
                case 12:
                    f4 = a.j(parcel, m);
                    break;
                case 13:
                    f5 = a.j(parcel, m);
                    break;
                case 14:
                    f6 = a.j(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new MarkerOptions(i, latLng, str, str2, iBinder, f, f2, z, z2, z3, f3, f4, f5, f6);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public MarkerOptions[] newArray(int i) {
        return new MarkerOptions[i];
    }
}
