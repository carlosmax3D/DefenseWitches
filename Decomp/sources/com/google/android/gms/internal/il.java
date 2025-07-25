package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ig;
import java.util.HashSet;
import java.util.Set;

public class il implements Parcelable.Creator<ig.b.C0041b> {
    static void a(ig.b.C0041b bVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        Set<Integer> fa = bVar.fa();
        if (fa.contains(1)) {
            b.c(parcel, 1, bVar.getVersionCode());
        }
        if (fa.contains(2)) {
            b.c(parcel, 2, bVar.getHeight());
        }
        if (fa.contains(3)) {
            b.a(parcel, 3, bVar.getUrl(), true);
        }
        if (fa.contains(4)) {
            b.c(parcel, 4, bVar.getWidth());
        }
        b.D(parcel, o);
    }

    /* renamed from: ay */
    public ig.b.C0041b createFromParcel(Parcel parcel) {
        int i = 0;
        int n = a.n(parcel);
        HashSet hashSet = new HashSet();
        String str = null;
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
                    str = a.m(parcel, m);
                    hashSet.add(3);
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
            return new ig.b.C0041b(hashSet, i3, i2, str, i);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bq */
    public ig.b.C0041b[] newArray(int i) {
        return new ig.b.C0041b[i];
    }
}
