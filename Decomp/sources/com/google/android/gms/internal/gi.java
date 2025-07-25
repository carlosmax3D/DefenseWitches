package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.ArrayList;

public class gi implements Parcelable.Creator<gh> {
    static void a(gh ghVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1000, ghVar.getVersionCode());
        b.a(parcel, 2, ghVar.isEnabled());
        b.a(parcel, 3, ghVar.dD());
        b.a(parcel, 4, ghVar.dE());
        b.a(parcel, 5, ghVar.dF());
        b.b(parcel, 6, ghVar.dG(), false);
        b.D(parcel, o);
    }

    /* renamed from: aU */
    public gh[] newArray(int i) {
        return new gh[i];
    }

    /* renamed from: ah */
    public gh createFromParcel(Parcel parcel) {
        boolean z = false;
        int n = a.n(parcel);
        ArrayList arrayList = null;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        int i = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 2:
                    z4 = a.c(parcel, m);
                    break;
                case 3:
                    z3 = a.c(parcel, m);
                    break;
                case 4:
                    z2 = a.c(parcel, m);
                    break;
                case 5:
                    z = a.c(parcel, m);
                    break;
                case 6:
                    arrayList = a.c(parcel, m, el.CREATOR);
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
            return new gh(i, z4, z3, z2, z, arrayList);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
