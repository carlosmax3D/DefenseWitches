package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class i implements Parcelable.Creator<MaskedWalletRequest> {
    static void a(MaskedWalletRequest maskedWalletRequest, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, maskedWalletRequest.getVersionCode());
        b.a(parcel, 2, maskedWalletRequest.Go, false);
        b.a(parcel, 3, maskedWalletRequest.GK);
        b.a(parcel, 4, maskedWalletRequest.GL);
        b.a(parcel, 5, maskedWalletRequest.GM);
        b.a(parcel, 6, maskedWalletRequest.GN, false);
        b.a(parcel, 7, maskedWalletRequest.Gk, false);
        b.a(parcel, 8, maskedWalletRequest.GO, false);
        b.a(parcel, 9, (Parcelable) maskedWalletRequest.Gu, i, false);
        b.a(parcel, 10, maskedWalletRequest.GP);
        b.a(parcel, 11, maskedWalletRequest.GQ);
        b.a(parcel, 12, (T[]) maskedWalletRequest.GR, i, false);
        b.a(parcel, 13, maskedWalletRequest.GS);
        b.a(parcel, 14, maskedWalletRequest.GT);
        b.D(parcel, o);
    }

    /* renamed from: aM */
    public MaskedWalletRequest createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        String str = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        Cart cart = null;
        boolean z4 = false;
        boolean z5 = false;
        CountrySpecification[] countrySpecificationArr = null;
        boolean z6 = true;
        boolean z7 = true;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    str = a.m(parcel, m);
                    break;
                case 3:
                    z = a.c(parcel, m);
                    break;
                case 4:
                    z2 = a.c(parcel, m);
                    break;
                case 5:
                    z3 = a.c(parcel, m);
                    break;
                case 6:
                    str2 = a.m(parcel, m);
                    break;
                case 7:
                    str3 = a.m(parcel, m);
                    break;
                case 8:
                    str4 = a.m(parcel, m);
                    break;
                case 9:
                    cart = (Cart) a.a(parcel, m, Cart.CREATOR);
                    break;
                case 10:
                    z4 = a.c(parcel, m);
                    break;
                case 11:
                    z5 = a.c(parcel, m);
                    break;
                case 12:
                    countrySpecificationArr = (CountrySpecification[]) a.b(parcel, m, CountrySpecification.CREATOR);
                    break;
                case 13:
                    z6 = a.c(parcel, m);
                    break;
                case 14:
                    z7 = a.c(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new MaskedWalletRequest(i, str, z, z2, z3, str2, str3, str4, cart, z4, z5, countrySpecificationArr, z6, z7);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bE */
    public MaskedWalletRequest[] newArray(int i) {
        return new MaskedWalletRequest[i];
    }
}
