package com.google.android.gms.internal;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class ca implements Parcelable.Creator<bz> {
    static void a(bz bzVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, bzVar.versionCode);
        b.a(parcel, 2, bzVar.hq, false);
        b.a(parcel, 3, (Parcelable) bzVar.hr, i, false);
        b.a(parcel, 4, (Parcelable) bzVar.em, i, false);
        b.a(parcel, 5, bzVar.adUnitId, false);
        b.a(parcel, 6, (Parcelable) bzVar.applicationInfo, i, false);
        b.a(parcel, 7, (Parcelable) bzVar.hs, i, false);
        b.a(parcel, 8, bzVar.ht, false);
        b.a(parcel, 9, bzVar.hu, false);
        b.a(parcel, 10, bzVar.hv, false);
        b.a(parcel, 11, (Parcelable) bzVar.ej, i, false);
        b.D(parcel, o);
    }

    /* renamed from: f */
    public bz createFromParcel(Parcel parcel) {
        cu cuVar = null;
        int n = a.n(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        PackageInfo packageInfo = null;
        ApplicationInfo applicationInfo = null;
        String str4 = null;
        x xVar = null;
        v vVar = null;
        Bundle bundle = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    bundle = a.o(parcel, m);
                    break;
                case 3:
                    vVar = (v) a.a(parcel, m, v.CREATOR);
                    break;
                case 4:
                    xVar = (x) a.a(parcel, m, x.CREATOR);
                    break;
                case 5:
                    str4 = a.m(parcel, m);
                    break;
                case 6:
                    applicationInfo = (ApplicationInfo) a.a(parcel, m, ApplicationInfo.CREATOR);
                    break;
                case 7:
                    packageInfo = (PackageInfo) a.a(parcel, m, PackageInfo.CREATOR);
                    break;
                case 8:
                    str3 = a.m(parcel, m);
                    break;
                case 9:
                    str2 = a.m(parcel, m);
                    break;
                case 10:
                    str = a.m(parcel, m);
                    break;
                case 11:
                    cuVar = (cu) a.a(parcel, m, cu.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new bz(i, bundle, vVar, xVar, str4, applicationInfo, packageInfo, str3, str2, str, cuVar);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: k */
    public bz[] newArray(int i) {
        return new bz[i];
    }
}
