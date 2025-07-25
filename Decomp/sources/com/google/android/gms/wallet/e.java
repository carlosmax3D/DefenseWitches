package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class e implements Parcelable.Creator<FullWalletRequest> {
    static void a(FullWalletRequest fullWalletRequest, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, fullWalletRequest.getVersionCode());
        b.a(parcel, 2, fullWalletRequest.Gn, false);
        b.a(parcel, 3, fullWalletRequest.Go, false);
        b.a(parcel, 4, (Parcelable) fullWalletRequest.Gu, i, false);
        b.D(parcel, o);
    }

    /* renamed from: aI */
    public FullWalletRequest createFromParcel(Parcel parcel) {
        Cart cart = null;
        int n = a.n(parcel);
        int i = 0;
        String str = null;
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
                case 4:
                    cart = (Cart) a.a(parcel, m, Cart.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new FullWalletRequest(i, str2, str, cart);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bA */
    public FullWalletRequest[] newArray(int i) {
        return new FullWalletRequest[i];
    }
}
