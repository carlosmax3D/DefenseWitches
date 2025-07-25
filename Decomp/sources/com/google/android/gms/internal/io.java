package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ig;
import java.util.HashSet;
import java.util.Set;

public class io implements Parcelable.Creator<ig.f> {
    static void a(ig.f fVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        Set<Integer> fa = fVar.fa();
        if (fa.contains(1)) {
            b.c(parcel, 1, fVar.getVersionCode());
        }
        if (fa.contains(2)) {
            b.a(parcel, 2, fVar.getDepartment(), true);
        }
        if (fa.contains(3)) {
            b.a(parcel, 3, fVar.getDescription(), true);
        }
        if (fa.contains(4)) {
            b.a(parcel, 4, fVar.getEndDate(), true);
        }
        if (fa.contains(5)) {
            b.a(parcel, 5, fVar.getLocation(), true);
        }
        if (fa.contains(6)) {
            b.a(parcel, 6, fVar.getName(), true);
        }
        if (fa.contains(7)) {
            b.a(parcel, 7, fVar.isPrimary());
        }
        if (fa.contains(8)) {
            b.a(parcel, 8, fVar.getStartDate(), true);
        }
        if (fa.contains(9)) {
            b.a(parcel, 9, fVar.getTitle(), true);
        }
        if (fa.contains(10)) {
            b.c(parcel, 10, fVar.getType());
        }
        b.D(parcel, o);
    }

    /* renamed from: aB */
    public ig.f createFromParcel(Parcel parcel) {
        int i = 0;
        String str = null;
        int n = a.n(parcel);
        HashSet hashSet = new HashSet();
        String str2 = null;
        boolean z = false;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        int i2 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i2 = a.g(parcel, m);
                    hashSet.add(1);
                    break;
                case 2:
                    str7 = a.m(parcel, m);
                    hashSet.add(2);
                    break;
                case 3:
                    str6 = a.m(parcel, m);
                    hashSet.add(3);
                    break;
                case 4:
                    str5 = a.m(parcel, m);
                    hashSet.add(4);
                    break;
                case 5:
                    str4 = a.m(parcel, m);
                    hashSet.add(5);
                    break;
                case 6:
                    str3 = a.m(parcel, m);
                    hashSet.add(6);
                    break;
                case 7:
                    z = a.c(parcel, m);
                    hashSet.add(7);
                    break;
                case 8:
                    str2 = a.m(parcel, m);
                    hashSet.add(8);
                    break;
                case 9:
                    str = a.m(parcel, m);
                    hashSet.add(9);
                    break;
                case 10:
                    i = a.g(parcel, m);
                    hashSet.add(10);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ig.f(hashSet, i2, str7, str6, str5, str4, str3, z, str2, str, i);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bt */
    public ig.f[] newArray(int i) {
        return new ig.f[i];
    }
}
