package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.location.LocationRequest;

public class gw implements Parcelable.Creator<gv> {
    static void a(gv gvVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, (Parcelable) gvVar.dS(), i, false);
        b.c(parcel, 1000, gvVar.kg);
        b.a(parcel, 2, (Parcelable) gvVar.dT(), i, false);
        b.D(parcel, o);
    }

    /* renamed from: aZ */
    public gv[] newArray(int i) {
        return new gv[i];
    }

    /* renamed from: ak */
    public gv createFromParcel(Parcel parcel) {
        gt gtVar;
        LocationRequest locationRequest;
        int i;
        gt gtVar2 = null;
        int n = a.n(parcel);
        int i2 = 0;
        LocationRequest locationRequest2 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = i2;
                    LocationRequest locationRequest3 = (LocationRequest) a.a(parcel, m, LocationRequest.CREATOR);
                    gtVar = gtVar2;
                    locationRequest = locationRequest3;
                    break;
                case 2:
                    gtVar = (gt) a.a(parcel, m, gt.CREATOR);
                    locationRequest = locationRequest2;
                    i = i2;
                    break;
                case 1000:
                    gt gtVar3 = gtVar2;
                    locationRequest = locationRequest2;
                    i = a.g(parcel, m);
                    gtVar = gtVar3;
                    break;
                default:
                    a.b(parcel, m);
                    gtVar = gtVar2;
                    locationRequest = locationRequest2;
                    i = i2;
                    break;
            }
            i2 = i;
            locationRequest2 = locationRequest;
            gtVar2 = gtVar;
        }
        if (parcel.dataPosition() == n) {
            return new gv(i2, locationRequest2, gtVar2);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
