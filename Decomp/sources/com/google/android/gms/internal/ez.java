package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class ez implements Parcelable.Creator<ey> {
    static void a(ey eyVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, eyVar.getVersionCode());
        b.a(parcel, 2, eyVar.cB(), false);
        b.a(parcel, 3, (Parcelable) eyVar.cC(), i, false);
        b.D(parcel, o);
    }

    /* renamed from: V */
    public ey[] newArray(int i) {
        return new ey[i];
    }

    /* renamed from: x */
    public ey createFromParcel(Parcel parcel) {
        ev evVar = null;
        int n = a.n(parcel);
        int i = 0;
        Parcel parcel2 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    parcel2 = a.z(parcel, m);
                    break;
                case 3:
                    evVar = (ev) a.a(parcel, m, ev.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ey(i, parcel2, evVar);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
