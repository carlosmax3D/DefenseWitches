package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class cv implements Parcelable.Creator<cu> {
    static void a(cu cuVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, cuVar.versionCode);
        b.a(parcel, 2, cuVar.iJ, false);
        b.c(parcel, 3, cuVar.iK);
        b.c(parcel, 4, cuVar.iL);
        b.a(parcel, 5, cuVar.iM);
        b.D(parcel, o);
    }

    /* renamed from: h */
    public cu createFromParcel(Parcel parcel) {
        boolean z = false;
        int n = a.n(parcel);
        String str = null;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i3 = a.g(parcel, m);
                    break;
                case 2:
                    str = a.m(parcel, m);
                    break;
                case 3:
                    i2 = a.g(parcel, m);
                    break;
                case 4:
                    i = a.g(parcel, m);
                    break;
                case 5:
                    z = a.c(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new cu(i3, str, i2, i, z);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: o */
    public cu[] newArray(int i) {
        return new cu[i];
    }
}
