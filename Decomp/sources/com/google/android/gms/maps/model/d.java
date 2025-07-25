package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.b;

public class d {
    static void a(LatLngBounds latLngBounds, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, latLngBounds.getVersionCode());
        b.a(parcel, 2, (Parcelable) latLngBounds.southwest, i, false);
        b.a(parcel, 3, (Parcelable) latLngBounds.northeast, i, false);
        b.D(parcel, o);
    }
}
