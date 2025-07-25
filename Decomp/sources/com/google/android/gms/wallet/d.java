package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class d implements Parcelable.Creator<FullWallet> {
    static void a(FullWallet fullWallet, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, fullWallet.getVersionCode());
        b.a(parcel, 2, fullWallet.Gn, false);
        b.a(parcel, 3, fullWallet.Go, false);
        b.a(parcel, 4, (Parcelable) fullWallet.Gp, i, false);
        b.a(parcel, 5, fullWallet.Gq, false);
        b.a(parcel, 6, (Parcelable) fullWallet.Gr, i, false);
        b.a(parcel, 7, (Parcelable) fullWallet.Gs, i, false);
        b.a(parcel, 8, fullWallet.Gt, false);
        b.D(parcel, o);
    }

    /* renamed from: aH */
    public FullWallet createFromParcel(Parcel parcel) {
        String[] strArr = null;
        int n = a.n(parcel);
        int i = 0;
        Address address = null;
        Address address2 = null;
        String str = null;
        ProxyCard proxyCard = null;
        String str2 = null;
        String str3 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    str3 = a.m(parcel, m);
                    break;
                case 3:
                    str2 = a.m(parcel, m);
                    break;
                case 4:
                    proxyCard = (ProxyCard) a.a(parcel, m, ProxyCard.CREATOR);
                    break;
                case 5:
                    str = a.m(parcel, m);
                    break;
                case 6:
                    address2 = (Address) a.a(parcel, m, Address.CREATOR);
                    break;
                case 7:
                    address = (Address) a.a(parcel, m, Address.CREATOR);
                    break;
                case 8:
                    strArr = a.x(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new FullWallet(i, str3, str2, proxyCard, str, address2, address, strArr);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bz */
    public FullWallet[] newArray(int i) {
        return new FullWallet[i];
    }
}
