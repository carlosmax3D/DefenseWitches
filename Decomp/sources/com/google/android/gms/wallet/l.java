package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class l implements Parcelable.Creator<ProxyCard> {
    static void a(ProxyCard proxyCard, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, proxyCard.getVersionCode());
        b.a(parcel, 2, proxyCard.GY, false);
        b.a(parcel, 3, proxyCard.GZ, false);
        b.c(parcel, 4, proxyCard.Ha);
        b.c(parcel, 5, proxyCard.Hb);
        b.D(parcel, o);
    }

    /* renamed from: aP */
    public ProxyCard createFromParcel(Parcel parcel) {
        String str = null;
        int i = 0;
        int n = a.n(parcel);
        int i2 = 0;
        String str2 = null;
        int i3 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i3 = a.g(parcel, m);
                    break;
                case 2:
                    str2 = a.m(parcel, m);
                    break;
                case 3:
                    str = a.m(parcel, m);
                    break;
                case 4:
                    i2 = a.g(parcel, m);
                    break;
                case 5:
                    i = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ProxyCard(i3, str2, str, i2, i);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bH */
    public ProxyCard[] newArray(int i) {
        return new ProxyCard[i];
    }
}
