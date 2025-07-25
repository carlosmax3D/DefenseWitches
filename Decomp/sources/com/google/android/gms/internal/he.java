package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.ArrayList;

public class he implements Parcelable.Creator<hd> {
    static void a(hd hdVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, hdVar.getId(), false);
        b.a(parcel, 2, hdVar.ee(), false);
        b.a(parcel, 3, (Parcelable) hdVar.ef(), i, false);
        b.a(parcel, 4, (Parcelable) hdVar.dX(), i, false);
        b.a(parcel, 5, hdVar.dY());
        b.a(parcel, 6, (Parcelable) hdVar.dZ(), i, false);
        b.a(parcel, 7, hdVar.eg(), false);
        b.a(parcel, 8, (Parcelable) hdVar.ea(), i, false);
        b.a(parcel, 9, hdVar.eb());
        b.a(parcel, 10, hdVar.getRating());
        b.c(parcel, 11, hdVar.ec());
        b.a(parcel, 12, hdVar.ed());
        b.b(parcel, 13, hdVar.dW(), false);
        b.c(parcel, 1000, hdVar.kg);
        b.D(parcel, o);
    }

    /* renamed from: ao */
    public hd createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        String str = null;
        ArrayList arrayList = null;
        Bundle bundle = null;
        hf hfVar = null;
        LatLng latLng = null;
        float f = 0.0f;
        LatLngBounds latLngBounds = null;
        String str2 = null;
        Uri uri = null;
        boolean z = false;
        float f2 = 0.0f;
        int i2 = 0;
        long j = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    str = a.m(parcel, m);
                    break;
                case 2:
                    bundle = a.o(parcel, m);
                    break;
                case 3:
                    hfVar = (hf) a.a(parcel, m, hf.CREATOR);
                    break;
                case 4:
                    latLng = (LatLng) a.a(parcel, m, LatLng.CREATOR);
                    break;
                case 5:
                    f = a.j(parcel, m);
                    break;
                case 6:
                    latLngBounds = (LatLngBounds) a.a(parcel, m, LatLngBounds.CREATOR);
                    break;
                case 7:
                    str2 = a.m(parcel, m);
                    break;
                case 8:
                    uri = (Uri) a.a(parcel, m, Uri.CREATOR);
                    break;
                case 9:
                    z = a.c(parcel, m);
                    break;
                case 10:
                    f2 = a.j(parcel, m);
                    break;
                case 11:
                    i2 = a.g(parcel, m);
                    break;
                case 12:
                    j = a.h(parcel, m);
                    break;
                case 13:
                    arrayList = a.c(parcel, m, gx.CREATOR);
                    break;
                case 1000:
                    i = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new hd(i, str, arrayList, bundle, hfVar, latLng, f, latLngBounds, str2, uri, z, f2, i2, j);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: be */
    public hd[] newArray(int i) {
        return new hd[i];
    }
}
