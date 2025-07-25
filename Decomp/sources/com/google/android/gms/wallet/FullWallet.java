package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class FullWallet implements SafeParcelable {
    public static final Parcelable.Creator<FullWallet> CREATOR = new d();
    String Gn;
    String Go;
    ProxyCard Gp;
    String Gq;
    Address Gr;
    Address Gs;
    String[] Gt;
    private final int kg;

    FullWallet() {
        this.kg = 1;
    }

    FullWallet(int i, String str, String str2, ProxyCard proxyCard, String str3, Address address, Address address2, String[] strArr) {
        this.kg = i;
        this.Gn = str;
        this.Go = str2;
        this.Gp = proxyCard;
        this.Gq = str3;
        this.Gr = address;
        this.Gs = address2;
        this.Gt = strArr;
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

    public String getMerchantTransactionId() {
        return this.Go;
    }

    public String[] getPaymentDescriptions() {
        return this.Gt;
    }

    public ProxyCard getProxyCard() {
        return this.Gp;
    }

    public Address getShippingAddress() {
        return this.Gs;
    }

    public int getVersionCode() {
        return this.kg;
    }

    public void writeToParcel(Parcel parcel, int i) {
        d.a(this, parcel, i);
    }
}
