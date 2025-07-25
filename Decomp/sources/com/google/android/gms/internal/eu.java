package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.es;
import com.google.android.gms.internal.ev;

public class eu implements Parcelable.Creator<ev.b> {
    static void a(ev.b bVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, bVar.versionCode);
        b.a(parcel, 2, bVar.qw, false);
        b.a(parcel, 3, (Parcelable) bVar.qx, i, false);
        b.D(parcel, o);
    }

    /* renamed from: S */
    public ev.b[] newArray(int i) {
        return new ev.b[i];
    }

    /* renamed from: u */
    public ev.b createFromParcel(Parcel parcel) {
        es.a aVar = null;
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
                    aVar = (es.a) a.a(parcel, m, es.a.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ev.b(i, str, aVar);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
