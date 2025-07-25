package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ig;
import java.util.HashSet;
import java.util.Set;

public class im implements Parcelable.Creator<ig.c> {
    static void a(ig.c cVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        Set<Integer> fa = cVar.fa();
        if (fa.contains(1)) {
            b.c(parcel, 1, cVar.getVersionCode());
        }
        if (fa.contains(2)) {
            b.a(parcel, 2, cVar.getUrl(), true);
        }
        b.D(parcel, o);
    }

    /* renamed from: az */
    public ig.c createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        HashSet hashSet = new HashSet();
        int i = 0;
        String str = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    hashSet.add(1);
                    break;
                case 2:
                    str = a.m(parcel, m);
                    hashSet.add(2);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ig.c(hashSet, i, str);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: br */
    public ig.c[] newArray(int i) {
        return new ig.c[i];
    }
}
