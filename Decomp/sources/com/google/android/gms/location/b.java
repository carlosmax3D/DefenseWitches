package com.google.android.gms.location;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

public class b implements Parcelable.Creator<a> {
    static void a(a aVar, Parcel parcel, int i) {
        int o = com.google.android.gms.common.internal.safeparcel.b.o(parcel);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 1, (Parcelable) aVar.dB(), i, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, aVar.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.D(parcel, o);
    }

    /* renamed from: aN */
    public a[] newArray(int i) {
        return new a[i];
    }

    /* renamed from: ae */
    public a createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        PendingIntent pendingIntent = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    pendingIntent = (PendingIntent) a.a(parcel, m, PendingIntent.CREATOR);
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
            return new a(i, pendingIntent);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
