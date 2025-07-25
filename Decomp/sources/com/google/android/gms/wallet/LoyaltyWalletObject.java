package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class LoyaltyWalletObject implements SafeParcelable {
    public static final Parcelable.Creator<LoyaltyWalletObject> CREATOR = new g();
    String GA;
    String GB;
    String GC;
    String GD;
    String GE;
    String GF;
    String GG;
    String GH;
    private final int kg;

    LoyaltyWalletObject() {
        this.kg = 3;
    }

    LoyaltyWalletObject(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.kg = i;
        this.GA = str;
        this.GB = str2;
        this.GC = str3;
        this.GD = str4;
        this.GE = str5;
        this.GF = str6;
        this.GG = str7;
        this.GH = str8;
    }

    public int describeContents() {
        return 0;
    }

    public String getAccountId() {
        return this.GB;
    }

    public String getAccountName() {
        return this.GE;
    }

    public String getBarcodeAlternateText() {
        return this.GF;
    }

    public String getBarcodeType() {
        return this.GG;
    }

    public String getBarcodeValue() {
        return this.GH;
    }

    public String getId() {
        return this.GA;
    }

    public String getIssuerName() {
        return this.GC;
    }

    public String getProgramName() {
        return this.GD;
    }

    public int getVersionCode() {
        return this.kg;
    }

    public void writeToParcel(Parcel parcel, int i) {
        g.a(this, parcel, i);
    }
}
