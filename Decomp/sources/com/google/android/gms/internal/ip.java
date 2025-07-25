package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ig;
import java.util.HashSet;
import java.util.Set;

public class ip implements Parcelable.Creator<ig.g> {
    static void a(ig.g gVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        Set<Integer> fa = gVar.fa();
        if (fa.contains(1)) {
            b.c(parcel, 1, gVar.getVersionCode());
        }
        if (fa.contains(2)) {
            b.a(parcel, 2, gVar.isPrimary());
        }
        if (fa.contains(3)) {
            b.a(parcel, 3, gVar.getValue(), true);
        }
        b.D(parcel, o);
    }

    /* renamed from: aC */
    public ig.g createFromParcel(Parcel parcel) {
        boolean z = false;
        int n = a.n(parcel);
        HashSet hashSet = new HashSet();
        String str = null;
        int i = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    hashSet.add(1);
                    break;
                case 2:
                    z = a.c(parcel, m);
                    hashSet.add(2);
                    break;
                case 3:
                    str = a.m(parcel, m);
                    hashSet.add(3);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ig.g(hashSet, i, z, str);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bu */
    public ig.g[] newArray(int i) {
        return new ig.g[i];
    }
}
