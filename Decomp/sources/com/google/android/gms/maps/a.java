package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.b;

public class a {
    static void a(GoogleMapOptions googleMapOptions, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, googleMapOptions.getVersionCode());
        b.a(parcel, 2, googleMapOptions.eo());
        b.a(parcel, 3, googleMapOptions.ep());
        b.c(parcel, 4, googleMapOptions.getMapType());
        b.a(parcel, 5, (Parcelable) googleMapOptions.getCamera(), i, false);
        b.a(parcel, 6, googleMapOptions.eq());
        b.a(parcel, 7, googleMapOptions.er());
        b.a(parcel, 8, googleMapOptions.es());
        b.a(parcel, 9, googleMapOptions.et());
        b.a(parcel, 10, googleMapOptions.eu());
        b.a(parcel, 11, googleMapOptions.ev());
        b.D(parcel, o);
    }
}
