package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ig;
import java.util.HashSet;
import java.util.Set;

public class ik implements Parcelable.Creator<ig.b.a> {
    static void a(ig.b.a aVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        Set<Integer> fa = aVar.fa();
        if (fa.contains(1)) {
            b.c(parcel, 1, aVar.getVersionCode());
        }
        if (fa.contains(2)) {
            b.c(parcel, 2, aVar.getLeftImageOffset());
        }
        if (fa.contains(3)) {
            b.c(parcel, 3, aVar.getTopImageOffset());
        }
        b.D(parcel, o);
    }

    /* renamed from: ax */
    public ig.b.a createFromParcel(Parcel parcel) {
        int i = 0;
        int n = a.n(parcel);
        HashSet hashSet = new HashSet();
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i3 = a.g(parcel, m);
                    hashSet.add(1);
                    break;
                case 2:
                    i2 = a.g(parcel, m);
                    hashSet.add(2);
                    break;
                case 3:
                    i = a.g(parcel, m);
                    hashSet.add(3);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ig.b.a(hashSet, i3, i2, i);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bp */
    public ig.b.a[] newArray(int i) {
        return new ig.b.a[i];
    }
}
