package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class e implements Parcelable.Creator<d> {
    static void a(d dVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, dVar.xG);
        b.c(parcel, 1000, dVar.getVersionCode());
        b.c(parcel, 2, dVar.xH);
        b.a(parcel, 3, dVar.xI);
        b.D(parcel, o);
    }

    /* renamed from: aS */
    public d[] newArray(int i) {
        return new d[i];
    }

    /* renamed from: af */
    public d createFromParcel(Parcel parcel) {
        int i = 1;
        int n = a.n(parcel);
        int i2 = 0;
        long j = 0;
        int i3 = 1;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i3 = a.g(parcel, m);
                    break;
                case 2:
                    i = a.g(parcel, m);
                    break;
                case 3:
                    j = a.h(parcel, m);
                    break;
                case 1000:
                    i2 = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new d(i2, i3, i, j);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
