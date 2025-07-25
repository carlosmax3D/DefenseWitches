package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class hw implements Parcelable.Creator<hu> {
    static void a(hu huVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, huVar.getAccountName(), false);
        b.c(parcel, 1000, huVar.getVersionCode());
        b.a(parcel, 2, huVar.eR(), false);
        b.a(parcel, 3, huVar.eS(), false);
        b.a(parcel, 4, huVar.eT(), false);
        b.a(parcel, 5, huVar.eU(), false);
        b.a(parcel, 6, huVar.eV(), false);
        b.a(parcel, 7, huVar.eW(), false);
        b.a(parcel, 8, huVar.eX(), false);
        b.D(parcel, o);
    }

    /* renamed from: ar */
    public hu createFromParcel(Parcel parcel) {
        String str = null;
        int n = a.n(parcel);
        int i = 0;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String[] strArr = null;
        String[] strArr2 = null;
        String[] strArr3 = null;
        String str5 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    str5 = a.m(parcel, m);
                    break;
                case 2:
                    strArr3 = a.x(parcel, m);
                    break;
                case 3:
                    strArr2 = a.x(parcel, m);
                    break;
                case 4:
                    strArr = a.x(parcel, m);
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
                case 1000:
                    i = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new hu(i, str5, strArr3, strArr2, strArr, str4, str3, str2, str);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bj */
    public hu[] newArray(int i) {
        return new hu[i];
    }
}
