package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class VisibleRegionCreator implements Parcelable.Creator<VisibleRegion> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(VisibleRegion visibleRegion, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, visibleRegion.getVersionCode());
        b.a(parcel, 2, (Parcelable) visibleRegion.nearLeft, i, false);
        b.a(parcel, 3, (Parcelable) visibleRegion.nearRight, i, false);
        b.a(parcel, 4, (Parcelable) visibleRegion.farLeft, i, false);
        b.a(parcel, 5, (Parcelable) visibleRegion.farRight, i, false);
        b.a(parcel, 6, (Parcelable) visibleRegion.latLngBounds, i, false);
        b.D(parcel, o);
    }

    public VisibleRegion createFromParcel(Parcel parcel) {
        LatLngBounds latLngBounds = null;
        int n = a.n(parcel);
        int i = 0;
        LatLng latLng = null;
        LatLng latLng2 = null;
        LatLng latLng3 = null;
        LatLng latLng4 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    latLng4 = (LatLng) a.a(parcel, m, LatLng.CREATOR);
                    break;
                case 3:
                    latLng3 = (LatLng) a.a(parcel, m, LatLng.CREATOR);
                    break;
                case 4:
                    latLng2 = (LatLng) a.a(parcel, m, LatLng.CREATOR);
                    break;
                case 5:
                    latLng = (LatLng) a.a(parcel, m, LatLng.CREATOR);
                    break;
                case 6:
                    latLngBounds = (LatLngBounds) a.a(parcel, m, LatLngBounds.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new VisibleRegion(i, latLng4, latLng3, latLng2, latLng, latLngBounds);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public VisibleRegion[] newArray(int i) {
        return new VisibleRegion[i];
    }
}
