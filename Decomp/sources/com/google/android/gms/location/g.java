package com.google.android.gms.location;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class g implements Parcelable.Creator<f> {
    static void a(f fVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, (Parcelable) fVar.dB(), i, false);
        b.c(parcel, 1000, fVar.getVersionCode());
        b.a(parcel, 2, fVar.dC(), false);
        b.D(parcel, o);
    }

    /* renamed from: aT */
    public f[] newArray(int i) {
        return new f[i];
    }

    /* renamed from: ag */
    public f createFromParcel(Parcel parcel) {
        String m;
        PendingIntent pendingIntent;
        int i;
        String str = null;
        int n = a.n(parcel);
        int i2 = 0;
        PendingIntent pendingIntent2 = null;
        while (parcel.dataPosition() < n) {
            int m2 = a.m(parcel);
            switch (a.M(m2)) {
                case 1:
                    i = i2;
                    PendingIntent pendingIntent3 = (PendingIntent) a.a(parcel, m2, PendingIntent.CREATOR);
                    m = str;
                    pendingIntent = pendingIntent3;
                    break;
                case 2:
                    m = a.m(parcel, m2);
                    pendingIntent = pendingIntent2;
                    i = i2;
                    break;
                case 1000:
                    String str2 = str;
                    pendingIntent = pendingIntent2;
                    i = a.g(parcel, m2);
                    m = str2;
                    break;
                default:
                    a.b(parcel, m2);
                    m = str;
                    pendingIntent = pendingIntent2;
                    i = i2;
                    break;
            }
            i2 = i;
            pendingIntent2 = pendingIntent;
            str = m;
        }
        if (parcel.dataPosition() == n) {
            return new f(i2, pendingIntent2, str);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
