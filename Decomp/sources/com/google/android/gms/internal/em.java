package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class em implements Parcelable.Creator<el> {
    static void a(el elVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, elVar.getType());
        b.c(parcel, 1000, elVar.getVersionCode());
        b.c(parcel, 2, elVar.bY());
        b.a(parcel, 3, elVar.bZ(), false);
        b.a(parcel, 4, elVar.ca(), false);
        b.a(parcel, 5, elVar.getDisplayName(), false);
        b.a(parcel, 6, elVar.cb(), false);
        b.a(parcel, 7, elVar.getMetadata(), false);
        b.D(parcel, o);
    }

    /* renamed from: N */
    public el[] newArray(int i) {
        return new el[i];
    }

    /* renamed from: p */
    public el createFromParcel(Parcel parcel) {
        int i = 0;
        Bundle bundle = null;
        int n = a.n(parcel);
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i2 = a.g(parcel, m);
                    break;
                case 2:
                    i = a.g(parcel, m);
                    break;
                case 3:
                    str4 = a.m(parcel, m);
                    break;
                case 4:
                    str3 = a.m(parcel, m);
                    break;
                case 5:
                    str2 = a.m(parcel, m);
                    break;
                case 6:
                    str = a.m(parcel, m);
                    break;
                case 7:
                    bundle = a.o(parcel, m);
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
            return new el(i3, i2, i, str4, str3, str2, str, bundle);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
