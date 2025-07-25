package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class k implements Parcelable.Creator<OfferWalletObject> {
    static void a(OfferWalletObject offerWalletObject, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, offerWalletObject.getVersionCode());
        b.a(parcel, 2, offerWalletObject.GA, false);
        b.a(parcel, 3, offerWalletObject.GX, false);
        b.D(parcel, o);
    }

    /* renamed from: aO */
    public OfferWalletObject createFromParcel(Parcel parcel) {
        String str = null;
        int n = a.n(parcel);
        int i = 0;
        String str2 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    str2 = a.m(parcel, m);
                    break;
                case 3:
                    str = a.m(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new OfferWalletObject(i, str2, str);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bG */
    public OfferWalletObject[] newArray(int i) {
        return new OfferWalletObject[i];
    }
}
