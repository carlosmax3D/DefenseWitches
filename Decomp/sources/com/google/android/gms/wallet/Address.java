package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class Address implements SafeParcelable {
    public static final Parcelable.Creator<Address> CREATOR = new a();
    String Ga;
    String Gb;
    String Gc;
    String Gd;
    String Ge;
    String Gf;
    String Gg;
    boolean Gh;
    String Gi;
    String id;
    private final int kg;
    String name;

    Address() {
        this.kg = 1;
    }

    Address(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, boolean z, String str10) {
        this.kg = i;
        this.name = str;
        this.Ga = str2;
        this.Gb = str3;
        this.Gc = str4;
        this.id = str5;
        this.Gd = str6;
        this.Ge = str7;
        this.Gf = str8;
        this.Gg = str9;
        this.Gh = z;
        this.Gi = str10;
    }

    public int describeContents() {
        return 0;
    }

    public String getAddress1() {
        return this.Ga;
    }

    public String getAddress2() {
        return this.Gb;
    }

    public String getAddress3() {
        return this.Gc;
    }

    public String getCity() {
        return this.Gd;
    }

    public String getCompanyName() {
        return this.Gi;
    }

    public String getCountryCode() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.Gg;
    }

    public String getPostalCode() {
        return this.Gf;
    }

    public String getState() {
        return this.Ge;
    }

    public int getVersionCode() {
        return this.kg;
    }

    public boolean isPostBox() {
        return this.Gh;
    }

    public void writeToParcel(Parcel parcel, int i) {
        a.a(this, parcel, i);
    }
}
