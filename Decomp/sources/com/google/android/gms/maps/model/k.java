package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.b;

public class k {
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
}
