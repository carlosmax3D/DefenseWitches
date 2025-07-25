package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class eo implements Parcelable.Creator<en> {
    static void a(en enVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, enVar.getVersionCode());
        b.a(parcel, 2, (Parcelable) enVar.ce(), i, false);
        b.D(parcel, o);
    }

    /* renamed from: O */
    public en[] newArray(int i) {
        return new en[i];
    }

    /* renamed from: q */
    public en createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        ep epVar = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    epVar = (ep) a.a(parcel, m, ep.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new en(i, epVar);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
