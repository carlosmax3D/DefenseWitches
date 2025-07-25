package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class hj implements Parcelable.Creator<hi> {
    static void a(hi hiVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, hiVar.Bn, false);
        b.c(parcel, 1000, hiVar.versionCode);
        b.a(parcel, 2, hiVar.Bo, false);
        b.D(parcel, o);
    }

    /* renamed from: aq */
    public hi createFromParcel(Parcel parcel) {
        String str = null;
        int n = a.n(parcel);
        int i = 0;
        String str2 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    str2 = a.m(parcel, m);
                    break;
                case 2:
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
            return new hi(i, str2, str);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bg */
    public hi[] newArray(int i) {
        return new hi[i];
    }
}
