package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ig;
import java.util.HashSet;
import java.util.Set;

public class ij implements Parcelable.Creator<ig.b> {
    static void a(ig.b bVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        Set<Integer> fa = bVar.fa();
        if (fa.contains(1)) {
            b.c(parcel, 1, bVar.getVersionCode());
        }
        if (fa.contains(2)) {
            b.a(parcel, 2, (Parcelable) bVar.fE(), i, true);
        }
        if (fa.contains(3)) {
            b.a(parcel, 3, (Parcelable) bVar.fF(), i, true);
        }
        if (fa.contains(4)) {
            b.c(parcel, 4, bVar.getLayout());
        }
        b.D(parcel, o);
    }

    /* renamed from: aw */
    public ig.b createFromParcel(Parcel parcel) {
        ig.b.C0041b bVar = null;
        int i = 0;
        int n = a.n(parcel);
        HashSet hashSet = new HashSet();
        ig.b.a aVar = null;
        int i2 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i2 = a.g(parcel, m);
                    hashSet.add(1);
                    break;
                case 2:
                    hashSet.add(2);
                    aVar = (ig.b.a) a.a(parcel, m, ig.b.a.CREATOR);
                    break;
                case 3:
                    hashSet.add(3);
                    bVar = (ig.b.C0041b) a.a(parcel, m, ig.b.C0041b.CREATOR);
                    break;
                case 4:
                    i = a.g(parcel, m);
                    hashSet.add(4);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ig.b(hashSet, i2, aVar, bVar, i);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bo */
    public ig.b[] newArray(int i) {
        return new ig.b[i];
    }
}
