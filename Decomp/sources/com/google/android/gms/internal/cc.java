package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.ArrayList;

public class cc implements Parcelable.Creator<cb> {
    static void a(cb cbVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, cbVar.versionCode);
        b.a(parcel, 2, cbVar.gL, false);
        b.a(parcel, 3, cbVar.hw, false);
        b.a(parcel, 4, cbVar.fK, false);
        b.c(parcel, 5, cbVar.errorCode);
        b.a(parcel, 6, cbVar.fL, false);
        b.a(parcel, 7, cbVar.hx);
        b.a(parcel, 8, cbVar.hy);
        b.a(parcel, 9, cbVar.hz);
        b.a(parcel, 10, cbVar.hA, false);
        b.a(parcel, 11, cbVar.fO);
        b.c(parcel, 12, cbVar.orientation);
        b.a(parcel, 13, cbVar.hB, false);
        b.D(parcel, o);
    }

    /* renamed from: g */
    public cb createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        ArrayList<String> arrayList = null;
        int i2 = 0;
        ArrayList<String> arrayList2 = null;
        long j = 0;
        boolean z = false;
        long j2 = 0;
        ArrayList<String> arrayList3 = null;
        long j3 = 0;
        int i3 = 0;
        String str3 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    str = a.m(parcel, m);
                    break;
                case 3:
                    str2 = a.m(parcel, m);
                    break;
                case 4:
                    arrayList = a.y(parcel, m);
                    break;
                case 5:
                    i2 = a.g(parcel, m);
                    break;
                case 6:
                    arrayList2 = a.y(parcel, m);
                    break;
                case 7:
                    j = a.h(parcel, m);
                    break;
                case 8:
                    z = a.c(parcel, m);
                    break;
                case 9:
                    j2 = a.h(parcel, m);
                    break;
                case 10:
                    arrayList3 = a.y(parcel, m);
                    break;
                case 11:
                    j3 = a.h(parcel, m);
                    break;
                case 12:
                    i3 = a.g(parcel, m);
                    break;
                case 13:
                    str3 = a.m(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new cb(i, str, str2, arrayList, i2, arrayList2, j, z, j2, arrayList3, j3, i3, str3);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: l */
    public cb[] newArray(int i) {
        return new cb[i];
    }
}
