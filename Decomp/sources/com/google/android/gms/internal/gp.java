package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class gp implements Parcelable.Creator<go> {
    static void a(go goVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, goVar.getRequestId(), false);
        b.c(parcel, 1000, goVar.getVersionCode());
        b.a(parcel, 2, goVar.getExpirationTime());
        b.a(parcel, 3, goVar.dK());
        b.a(parcel, 4, goVar.getLatitude());
        b.a(parcel, 5, goVar.getLongitude());
        b.a(parcel, 6, goVar.dL());
        b.c(parcel, 7, goVar.dM());
        b.c(parcel, 8, goVar.getNotificationResponsiveness());
        b.c(parcel, 9, goVar.dN());
        b.D(parcel, o);
    }

    /* renamed from: aX */
    public go[] newArray(int i) {
        return new go[i];
    }

    /* renamed from: ai */
    public go createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        String str = null;
        int i2 = 0;
        short s = 0;
        double d = 0.0d;
        double d2 = 0.0d;
        float f = 0.0f;
        long j = 0;
        int i3 = 0;
        int i4 = -1;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    str = a.m(parcel, m);
                    break;
                case 2:
                    j = a.h(parcel, m);
                    break;
                case 3:
                    s = a.f(parcel, m);
                    break;
                case 4:
                    d = a.k(parcel, m);
                    break;
                case 5:
                    d2 = a.k(parcel, m);
                    break;
                case 6:
                    f = a.j(parcel, m);
                    break;
                case 7:
                    i2 = a.g(parcel, m);
                    break;
                case 8:
                    i3 = a.g(parcel, m);
                    break;
                case 9:
                    i4 = a.g(parcel, m);
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
            return new go(i, str, i2, s, d, d2, f, j, i3, i4);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
