package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ev;
import java.util.ArrayList;

public class ex implements Parcelable.Creator<ev.a> {
    static void a(ev.a aVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, aVar.versionCode);
        b.a(parcel, 2, aVar.className, false);
        b.b(parcel, 3, aVar.qv, false);
        b.D(parcel, o);
    }

    /* renamed from: U */
    public ev.a[] newArray(int i) {
        return new ev.a[i];
    }

    /* renamed from: w */
    public ev.a createFromParcel(Parcel parcel) {
        ArrayList arrayList = null;
        int n = a.n(parcel);
        int i = 0;
        String str = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    str = a.m(parcel, m);
                    break;
                case 3:
                    arrayList = a.c(parcel, m, ev.b.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ev.a(i, str, arrayList);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
