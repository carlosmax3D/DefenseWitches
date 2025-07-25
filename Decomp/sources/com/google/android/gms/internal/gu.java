package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.ArrayList;

public class gu implements Parcelable.Creator<gt> {
    static void a(gt gtVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, gtVar.dO());
        b.c(parcel, 1000, gtVar.kg);
        b.b(parcel, 2, gtVar.yg, false);
        b.a(parcel, 3, gtVar.dP(), false);
        b.a(parcel, 4, gtVar.dQ(), false);
        b.a(parcel, 5, gtVar.dR());
        b.D(parcel, o);
    }

    /* renamed from: aY */
    public gt[] newArray(int i) {
        return new gt[i];
    }

    /* renamed from: aj */
    public gt createFromParcel(Parcel parcel) {
        String str = null;
        boolean z = false;
        int n = a.n(parcel);
        String str2 = null;
        ArrayList arrayList = null;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    arrayList = a.c(parcel, m, gx.CREATOR);
                    break;
                case 3:
                    str2 = a.m(parcel, m);
                    break;
                case 4:
                    str = a.m(parcel, m);
                    break;
                case 5:
                    z = a.c(parcel, m);
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
            return new gt(i2, i, arrayList, str2, str, z);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
