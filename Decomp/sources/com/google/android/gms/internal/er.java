package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ep;

public class er implements Parcelable.Creator<ep.a> {
    static void a(ep.a aVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, aVar.versionCode);
        b.a(parcel, 2, aVar.qg, false);
        b.c(parcel, 3, aVar.qh);
        b.D(parcel, o);
    }

    /* renamed from: Q */
    public ep.a[] newArray(int i) {
        return new ep.a[i];
    }

    /* renamed from: s */
    public ep.a createFromParcel(Parcel parcel) {
        int i = 0;
        int n = a.n(parcel);
        String str = null;
        int i2 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i2 = a.g(parcel, m);
                    break;
                case 2:
                    str = a.m(parcel, m);
                    break;
                case 3:
                    i = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ep.a(i2, str, i);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
