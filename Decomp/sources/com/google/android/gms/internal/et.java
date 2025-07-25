package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.es;

public class et implements Parcelable.Creator<es.a> {
    static void a(es.a aVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, aVar.getVersionCode());
        b.c(parcel, 2, aVar.ch());
        b.a(parcel, 3, aVar.cn());
        b.c(parcel, 4, aVar.ci());
        b.a(parcel, 5, aVar.co());
        b.a(parcel, 6, aVar.cp(), false);
        b.c(parcel, 7, aVar.cq());
        b.a(parcel, 8, aVar.cs(), false);
        b.a(parcel, 9, (Parcelable) aVar.cu(), i, false);
        b.D(parcel, o);
    }

    /* renamed from: R */
    public es.a[] newArray(int i) {
        return new es.a[i];
    }

    /* renamed from: t */
    public es.a createFromParcel(Parcel parcel) {
        en enVar = null;
        int i = 0;
        int n = a.n(parcel);
        String str = null;
        String str2 = null;
        boolean z = false;
        int i2 = 0;
        boolean z2 = false;
        int i3 = 0;
        int i4 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i4 = a.g(parcel, m);
                    break;
                case 2:
                    i3 = a.g(parcel, m);
                    break;
                case 3:
                    z2 = a.c(parcel, m);
                    break;
                case 4:
                    i2 = a.g(parcel, m);
                    break;
                case 5:
                    z = a.c(parcel, m);
                    break;
                case 6:
                    str2 = a.m(parcel, m);
                    break;
                case 7:
                    i = a.g(parcel, m);
                    break;
                case 8:
                    str = a.m(parcel, m);
                    break;
                case 9:
                    enVar = (en) a.a(parcel, m, en.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new es.a(i4, i3, z2, i2, z, str2, i, str, enVar);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
