package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class hc implements Parcelable.Creator<hb> {
    static void a(hb hbVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, (T[]) hbVar.dU(), i, false);
        b.c(parcel, 1000, hbVar.kg);
        b.a(parcel, 2, hbVar.dV(), false);
        b.a(parcel, 3, hbVar.getTimestampMillis());
        b.D(parcel, o);
    }

    /* renamed from: an */
    public hb createFromParcel(Parcel parcel) {
        float[] fArr = null;
        int n = a.n(parcel);
        int i = 0;
        long j = 0;
        hd[] hdVarArr = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    hdVarArr = (hd[]) a.b(parcel, m, hd.CREATOR);
                    break;
                case 2:
                    fArr = a.u(parcel, m);
                    break;
                case 3:
                    j = a.h(parcel, m);
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
            return new hb(i, hdVarArr, fArr, j);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bc */
    public hb[] newArray(int i) {
        return new hb[i];
    }
}
