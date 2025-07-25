package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.dt;
import java.util.ArrayList;

public class ef implements Parcelable.Creator<dt.a> {
    static void a(dt.a aVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, aVar.getAccountName(), false);
        b.c(parcel, 1000, aVar.getVersionCode());
        b.a(parcel, 2, aVar.bH(), false);
        b.c(parcel, 3, aVar.bG());
        b.a(parcel, 4, aVar.bJ(), false);
        b.D(parcel, o);
    }

    /* renamed from: L */
    public dt.a[] newArray(int i) {
        return new dt.a[i];
    }

    /* renamed from: l */
    public dt.a createFromParcel(Parcel parcel) {
        int i = 0;
        String str = null;
        int n = a.n(parcel);
        ArrayList<String> arrayList = null;
        String str2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    str2 = a.m(parcel, m);
                    break;
                case 2:
                    arrayList = a.y(parcel, m);
                    break;
                case 3:
                    i = a.g(parcel, m);
                    break;
                case 4:
                    str = a.m(parcel, m);
                    break;
                case 1000:
                    i2 = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new dt.a(i2, str2, arrayList, i, str);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
