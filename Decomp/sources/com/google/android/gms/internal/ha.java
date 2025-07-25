package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.hd;

public class ha implements Parcelable.Creator<hd.a> {
    static void a(hd.a aVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, aVar.eh(), false);
        b.c(parcel, 1000, aVar.kg);
        b.a(parcel, 2, aVar.getTag(), false);
        b.a(parcel, 3, aVar.ei(), false);
        b.c(parcel, 4, aVar.ej());
        b.D(parcel, o);
    }

    /* renamed from: am */
    public hd.a createFromParcel(Parcel parcel) {
        int i = 0;
        String str = null;
        int n = a.n(parcel);
        String str2 = null;
        String str3 = null;
        int i2 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    str3 = a.m(parcel, m);
                    break;
                case 2:
                    str2 = a.m(parcel, m);
                    break;
                case 3:
                    str = a.m(parcel, m);
                    break;
                case 4:
                    i = a.g(parcel, m);
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
            return new hd.a(i2, str3, str2, str, i);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bb */
    public hd.a[] newArray(int i) {
        return new hd.a[i];
    }
}
