package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class bi implements Parcelable.Creator<bj> {
    static void a(bj bjVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, bjVar.versionCode);
        b.a(parcel, 2, bjVar.gn, false);
        b.a(parcel, 3, bjVar.go, false);
        b.a(parcel, 4, bjVar.mimeType, false);
        b.a(parcel, 5, bjVar.packageName, false);
        b.a(parcel, 6, bjVar.gp, false);
        b.a(parcel, 7, bjVar.gq, false);
        b.a(parcel, 8, bjVar.gr, false);
        b.D(parcel, o);
    }

    /* renamed from: d */
    public bj createFromParcel(Parcel parcel) {
        String str = null;
        int n = a.n(parcel);
        int i = 0;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    str7 = a.m(parcel, m);
                    break;
                case 3:
                    str6 = a.m(parcel, m);
                    break;
                case 4:
                    str5 = a.m(parcel, m);
                    break;
                case 5:
                    str4 = a.m(parcel, m);
                    break;
                case 6:
                    str3 = a.m(parcel, m);
                    break;
                case 7:
                    str2 = a.m(parcel, m);
                    break;
                case 8:
                    str = a.m(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new bj(i, str7, str6, str5, str4, str3, str2, str);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: i */
    public bj[] newArray(int i) {
        return new bj[i];
    }
}
