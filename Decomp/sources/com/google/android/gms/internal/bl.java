package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class bl implements Parcelable.Creator<bm> {
    static void a(bm bmVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, bmVar.versionCode);
        b.a(parcel, 2, (Parcelable) bmVar.gG, i, false);
        b.a(parcel, 3, bmVar.aa(), false);
        b.a(parcel, 4, bmVar.ab(), false);
        b.a(parcel, 5, bmVar.ac(), false);
        b.a(parcel, 6, bmVar.ad(), false);
        b.a(parcel, 7, bmVar.gL, false);
        b.a(parcel, 8, bmVar.gM);
        b.a(parcel, 9, bmVar.gN, false);
        b.a(parcel, 10, bmVar.ae(), false);
        b.c(parcel, 11, bmVar.orientation);
        b.c(parcel, 12, bmVar.gP);
        b.a(parcel, 13, bmVar.go, false);
        b.a(parcel, 14, (Parcelable) bmVar.ej, i, false);
        b.D(parcel, o);
    }

    /* renamed from: e */
    public bm createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        bj bjVar = null;
        IBinder iBinder = null;
        IBinder iBinder2 = null;
        IBinder iBinder3 = null;
        IBinder iBinder4 = null;
        String str = null;
        boolean z = false;
        String str2 = null;
        IBinder iBinder5 = null;
        int i2 = 0;
        int i3 = 0;
        String str3 = null;
        cu cuVar = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    bjVar = (bj) a.a(parcel, m, bj.CREATOR);
                    break;
                case 3:
                    iBinder = a.n(parcel, m);
                    break;
                case 4:
                    iBinder2 = a.n(parcel, m);
                    break;
                case 5:
                    iBinder3 = a.n(parcel, m);
                    break;
                case 6:
                    iBinder4 = a.n(parcel, m);
                    break;
                case 7:
                    str = a.m(parcel, m);
                    break;
                case 8:
                    z = a.c(parcel, m);
                    break;
                case 9:
                    str2 = a.m(parcel, m);
                    break;
                case 10:
                    iBinder5 = a.n(parcel, m);
                    break;
                case 11:
                    i2 = a.g(parcel, m);
                    break;
                case 12:
                    i3 = a.g(parcel, m);
                    break;
                case 13:
                    str3 = a.m(parcel, m);
                    break;
                case 14:
                    cuVar = (cu) a.a(parcel, m, cu.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new bm(i, bjVar, iBinder, iBinder2, iBinder3, iBinder4, str, z, str2, iBinder5, i2, i3, str3, cuVar);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: j */
    public bm[] newArray(int i) {
        return new bm[i];
    }
}
