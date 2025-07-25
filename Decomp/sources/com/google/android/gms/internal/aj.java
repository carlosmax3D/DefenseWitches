package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class aj implements Parcelable.Creator<ai> {
    static void a(ai aiVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, aiVar.versionCode);
        b.c(parcel, 2, aiVar.eZ);
        b.c(parcel, 3, aiVar.backgroundColor);
        b.c(parcel, 4, aiVar.fa);
        b.c(parcel, 5, aiVar.fb);
        b.c(parcel, 6, aiVar.fc);
        b.c(parcel, 7, aiVar.fd);
        b.c(parcel, 8, aiVar.fe);
        b.c(parcel, 9, aiVar.ff);
        b.a(parcel, 10, aiVar.fg, false);
        b.c(parcel, 11, aiVar.fh);
        b.a(parcel, 12, aiVar.fi, false);
        b.c(parcel, 13, aiVar.fj);
        b.c(parcel, 14, aiVar.fk);
        b.a(parcel, 15, aiVar.fl, false);
        b.D(parcel, o);
    }

    /* renamed from: c */
    public ai createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        String str = null;
        int i10 = 0;
        String str2 = null;
        int i11 = 0;
        int i12 = 0;
        String str3 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    i2 = a.g(parcel, m);
                    break;
                case 3:
                    i3 = a.g(parcel, m);
                    break;
                case 4:
                    i4 = a.g(parcel, m);
                    break;
                case 5:
                    i5 = a.g(parcel, m);
                    break;
                case 6:
                    i6 = a.g(parcel, m);
                    break;
                case 7:
                    i7 = a.g(parcel, m);
                    break;
                case 8:
                    i8 = a.g(parcel, m);
                    break;
                case 9:
                    i9 = a.g(parcel, m);
                    break;
                case 10:
                    str = a.m(parcel, m);
                    break;
                case 11:
                    i10 = a.g(parcel, m);
                    break;
                case 12:
                    str2 = a.m(parcel, m);
                    break;
                case 13:
                    i11 = a.g(parcel, m);
                    break;
                case 14:
                    i12 = a.g(parcel, m);
                    break;
                case 15:
                    str3 = a.m(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ai(i, i2, i3, i4, i5, i6, i7, i8, i9, str, i10, str2, i11, i12, str3);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: e */
    public ai[] newArray(int i) {
        return new ai[i];
    }
}
