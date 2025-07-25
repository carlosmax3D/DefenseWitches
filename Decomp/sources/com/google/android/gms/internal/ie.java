package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.HashSet;
import java.util.Set;

public class ie implements Parcelable.Creator<id> {
    static void a(id idVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        Set<Integer> fa = idVar.fa();
        if (fa.contains(1)) {
            b.c(parcel, 1, idVar.getVersionCode());
        }
        if (fa.contains(2)) {
            b.a(parcel, 2, idVar.getId(), true);
        }
        if (fa.contains(4)) {
            b.a(parcel, 4, (Parcelable) idVar.fr(), i, true);
        }
        if (fa.contains(5)) {
            b.a(parcel, 5, idVar.getStartDate(), true);
        }
        if (fa.contains(6)) {
            b.a(parcel, 6, (Parcelable) idVar.fs(), i, true);
        }
        if (fa.contains(7)) {
            b.a(parcel, 7, idVar.getType(), true);
        }
        b.D(parcel, o);
    }

    /* renamed from: at */
    public id createFromParcel(Parcel parcel) {
        String str = null;
        int n = a.n(parcel);
        HashSet hashSet = new HashSet();
        int i = 0;
        ib ibVar = null;
        String str2 = null;
        ib ibVar2 = null;
        String str3 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    hashSet.add(1);
                    break;
                case 2:
                    str3 = a.m(parcel, m);
                    hashSet.add(2);
                    break;
                case 4:
                    hashSet.add(4);
                    ibVar2 = (ib) a.a(parcel, m, ib.CREATOR);
                    break;
                case 5:
                    str2 = a.m(parcel, m);
                    hashSet.add(5);
                    break;
                case 6:
                    hashSet.add(6);
                    ibVar = (ib) a.a(parcel, m, ib.CREATOR);
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
            return new id(hashSet, i, str3, ibVar2, str2, ibVar, str);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bl */
    public id[] newArray(int i) {
        return new id[i];
    }
}
