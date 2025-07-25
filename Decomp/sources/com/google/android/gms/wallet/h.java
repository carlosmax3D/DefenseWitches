package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class h implements Parcelable.Creator<MaskedWallet> {
    static void a(MaskedWallet maskedWallet, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, maskedWallet.getVersionCode());
        b.a(parcel, 2, maskedWallet.Gn, false);
        b.a(parcel, 3, maskedWallet.Go, false);
        b.a(parcel, 4, maskedWallet.Gt, false);
        b.a(parcel, 5, maskedWallet.Gq, false);
        b.a(parcel, 6, (Parcelable) maskedWallet.Gr, i, false);
        b.a(parcel, 7, (Parcelable) maskedWallet.Gs, i, false);
        b.a(parcel, 8, (T[]) maskedWallet.GI, i, false);
        b.a(parcel, 9, (T[]) maskedWallet.GJ, i, false);
        b.D(parcel, o);
    }

    /* renamed from: aL */
    public MaskedWallet createFromParcel(Parcel parcel) {
        OfferWalletObject[] offerWalletObjectArr = null;
        int n = a.n(parcel);
        int i = 0;
        LoyaltyWalletObject[] loyaltyWalletObjectArr = null;
        Address address = null;
        Address address2 = null;
        String str = null;
        String[] strArr = null;
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
                    strArr = a.x(parcel, m);
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
                    loyaltyWalletObjectArr = (LoyaltyWalletObject[]) a.b(parcel, m, LoyaltyWalletObject.CREATOR);
                    break;
                case 9:
                    offerWalletObjectArr = (OfferWalletObject[]) a.b(parcel, m, OfferWalletObject.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new MaskedWallet(i, str3, str2, strArr, str, address2, address, loyaltyWalletObjectArr, offerWalletObjectArr);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bD */
    public MaskedWallet[] newArray(int i) {
        return new MaskedWallet[i];
    }
}
