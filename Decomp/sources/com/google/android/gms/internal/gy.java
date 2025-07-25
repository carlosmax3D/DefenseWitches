package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class gy implements Parcelable.Creator<gx> {
    static void a(gx gxVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, gxVar.AI, false);
        b.c(parcel, 1000, gxVar.kg);
        b.D(parcel, o);
    }

    /* renamed from: al */
    public gx createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        String str = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
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
            return new gx(i, str);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: ba */
    public gx[] newArray(int i) {
        return new gx[i];
    }
}
