package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class LocationRequestCreator implements Parcelable.Creator<LocationRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(LocationRequest locationRequest, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, locationRequest.mPriority);
        b.c(parcel, 1000, locationRequest.getVersionCode());
        b.a(parcel, 2, locationRequest.xB);
        b.a(parcel, 3, locationRequest.xC);
        b.a(parcel, 4, locationRequest.xD);
        b.a(parcel, 5, locationRequest.xu);
        b.c(parcel, 6, locationRequest.xE);
        b.a(parcel, 7, locationRequest.xF);
        b.D(parcel, o);
    }

    public LocationRequest createFromParcel(Parcel parcel) {
        boolean z = false;
        int n = a.n(parcel);
        int i = 102;
        long j = 3600000;
        long j2 = 600000;
        long j3 = Long.MAX_VALUE;
        int i2 = Integer.MAX_VALUE;
        float f = 0.0f;
        int i3 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    j = a.h(parcel, m);
                    break;
                case 3:
                    j2 = a.h(parcel, m);
                    break;
                case 4:
                    z = a.c(parcel, m);
                    break;
                case 5:
                    j3 = a.h(parcel, m);
                    break;
                case 6:
                    i2 = a.g(parcel, m);
                    break;
                case 7:
                    f = a.j(parcel, m);
                    break;
                case 1000:
                    i3 = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new LocationRequest(i3, i, j, j2, z, j3, i2, f);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public LocationRequest[] newArray(int i) {
        return new LocationRequest[i];
    }
}
