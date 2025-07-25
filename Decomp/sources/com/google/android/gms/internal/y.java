package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class y implements Parcelable.Creator<x> {
    static void a(x xVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, xVar.versionCode);
        b.a(parcel, 2, xVar.eF, false);
        b.c(parcel, 3, xVar.height);
        b.c(parcel, 4, xVar.heightPixels);
        b.a(parcel, 5, xVar.eG);
        b.c(parcel, 6, xVar.width);
        b.c(parcel, 7, xVar.widthPixels);
        b.a(parcel, 8, (T[]) xVar.eH, i, false);
        b.D(parcel, o);
    }

    /* renamed from: b */
    public x createFromParcel(Parcel parcel) {
        x[] xVarArr = null;
        int i = 0;
        int n = a.n(parcel);
        int i2 = 0;
        boolean z = false;
        int i3 = 0;
        int i4 = 0;
        String str = null;
        int i5 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i5 = a.g(parcel, m);
                    break;
                case 2:
                    str = a.m(parcel, m);
                    break;
                case 3:
                    i4 = a.g(parcel, m);
                    break;
                case 4:
                    i3 = a.g(parcel, m);
                    break;
                case 5:
                    z = a.c(parcel, m);
                    break;
                case 6:
                    i2 = a.g(parcel, m);
                    break;
                case 7:
                    i = a.g(parcel, m);
                    break;
                case 8:
                    xVarArr = (x[]) a.b(parcel, m, x.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new x(i5, str, i4, i3, z, i2, i, xVarArr);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: c */
    public x[] newArray(int i) {
        return new x[i];
    }
}
