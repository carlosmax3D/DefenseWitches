package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ig;
import java.util.HashSet;
import java.util.Set;

public class in implements Parcelable.Creator<ig.d> {
    static void a(ig.d dVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        Set<Integer> fa = dVar.fa();
        if (fa.contains(1)) {
            b.c(parcel, 1, dVar.getVersionCode());
        }
        if (fa.contains(2)) {
            b.a(parcel, 2, dVar.getFamilyName(), true);
        }
        if (fa.contains(3)) {
            b.a(parcel, 3, dVar.getFormatted(), true);
        }
        if (fa.contains(4)) {
            b.a(parcel, 4, dVar.getGivenName(), true);
        }
        if (fa.contains(5)) {
            b.a(parcel, 5, dVar.getHonorificPrefix(), true);
        }
        if (fa.contains(6)) {
            b.a(parcel, 6, dVar.getHonorificSuffix(), true);
        }
        if (fa.contains(7)) {
            b.a(parcel, 7, dVar.getMiddleName(), true);
        }
        b.D(parcel, o);
    }

    /* renamed from: aA */
    public ig.d createFromParcel(Parcel parcel) {
        String str = null;
        int n = a.n(parcel);
        HashSet hashSet = new HashSet();
        int i = 0;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    hashSet.add(1);
                    break;
                case 2:
                    str6 = a.m(parcel, m);
                    hashSet.add(2);
                    break;
                case 3:
                    str5 = a.m(parcel, m);
                    hashSet.add(3);
                    break;
                case 4:
                    str4 = a.m(parcel, m);
                    hashSet.add(4);
                    break;
                case 5:
                    str3 = a.m(parcel, m);
                    hashSet.add(5);
                    break;
                case 6:
                    str2 = a.m(parcel, m);
                    hashSet.add(6);
                    break;
                case 7:
                    str = a.m(parcel, m);
                    hashSet.add(7);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ig.d(hashSet, i, str6, str5, str4, str3, str2, str);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bs */
    public ig.d[] newArray(int i) {
        return new ig.d[i];
    }
}
