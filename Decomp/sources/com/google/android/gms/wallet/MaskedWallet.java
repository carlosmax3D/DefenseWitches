package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class MaskedWallet implements SafeParcelable {
    public static final Parcelable.Creator<MaskedWallet> CREATOR = new h();
    LoyaltyWalletObject[] GI;
    OfferWalletObject[] GJ;
    String Gn;
    String Go;
    String Gq;
    Address Gr;
    Address Gs;
    String[] Gt;
    private final int kg;

    MaskedWallet() {
        this.kg = 2;
    }

    MaskedWallet(int i, String str, String str2, String[] strArr, String str3, Address address, Address address2, LoyaltyWalletObject[] loyaltyWalletObjectArr, OfferWalletObject[] offerWalletObjectArr) {
        this.kg = i;
        this.Gn = str;
        this.Go = str2;
        this.Gt = strArr;
        this.Gq = str3;
        this.Gr = address;
        this.Gs = address2;
        this.GI = loyaltyWalletObjectArr;
        this.GJ = offerWalletObjectArr;
    }

    public int describeContents() {
        return 0;
    }

    public Address getBillingAddress() {
        return this.Gr;
    }

    public String getEmail() {
        return this.Gq;
    }

    public String getGoogleTransactionId() {
        return this.Gn;
    }

    public LoyaltyWalletObject[] getLoyaltyWalletObjects() {
        return this.GI;
    }

    public String getMerchantTransactionId() {
        return this.Go;
    }

    public OfferWalletObject[] getOfferWalletObjects() {
        return this.GJ;
    }

    public String[] getPaymentDescriptions() {
        return this.Gt;
    }

    public Address getShippingAddress() {
        return this.Gs;
    }

    public int getVersionCode() {
        return this.kg;
    }

    public void writeToParcel(Parcel parcel, int i) {
        h.a(this, parcel, i);
    }
}
