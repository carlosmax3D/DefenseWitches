package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ep;
import java.util.ArrayList;

public class eq implements Parcelable.Creator<ep> {
    static void a(ep epVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, epVar.getVersionCode());
        b.b(parcel, 2, epVar.cg(), false);
        b.D(parcel, o);
    }

    /* renamed from: P */
    public ep[] newArray(int i) {
        return new ep[i];
    }

    /* renamed from: r */
    public ep createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        ArrayList arrayList = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    arrayList = a.c(parcel, m, ep.a.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ep(i, arrayList);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
