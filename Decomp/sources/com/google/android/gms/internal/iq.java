package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ig;
import java.util.HashSet;
import java.util.Set;

public class iq implements Parcelable.Creator<ig.h> {
    static void a(ig.h hVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        Set<Integer> fa = hVar.fa();
        if (fa.contains(1)) {
            b.c(parcel, 1, hVar.getVersionCode());
        }
        if (fa.contains(3)) {
            b.c(parcel, 3, hVar.fN());
        }
        if (fa.contains(4)) {
            b.a(parcel, 4, hVar.getValue(), true);
        }
        if (fa.contains(5)) {
            b.a(parcel, 5, hVar.getLabel(), true);
        }
        if (fa.contains(6)) {
            b.c(parcel, 6, hVar.getType());
        }
        b.D(parcel, o);
    }

    /* renamed from: aD */
    public ig.h createFromParcel(Parcel parcel) {
        String str = null;
        int i = 0;
        int n = a.n(parcel);
        HashSet hashSet = new HashSet();
        int i2 = 0;
        String str2 = null;
        int i3 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i3 = a.g(parcel, m);
                    hashSet.add(1);
                    break;
                case 3:
                    i = a.g(parcel, m);
                    hashSet.add(3);
                    break;
                case 4:
                    str = a.m(parcel, m);
                    hashSet.add(4);
                    break;
                case 5:
                    str2 = a.m(parcel, m);
                    hashSet.add(5);
                    break;
                case 6:
                    i2 = a.g(parcel, m);
                    hashSet.add(6);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ig.h(hashSet, i3, str2, i2, str, i);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bv */
    public ig.h[] newArray(int i) {
        return new ig.h[i];
    }
}
