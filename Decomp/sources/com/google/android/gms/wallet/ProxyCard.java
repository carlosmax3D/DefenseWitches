package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class ProxyCard implements SafeParcelable {
    public static final Parcelable.Creator<ProxyCard> CREATOR = new l();
    String GY;
    String GZ;
    int Ha;
    int Hb;
    private final int kg;

    ProxyCard(int i, String str, String str2, int i2, int i3) {
        this.kg = i;
        this.GY = str;
        this.GZ = str2;
        this.Ha = i2;
        this.Hb = i3;
    }

    public int describeContents() {
        return 0;
    }

    public String getCvn() {
        return this.GZ;
    }

    public int getExpirationMonth() {
        return this.Ha;
    }

    public int getExpirationYear() {
        return this.Hb;
    }

    public String getPan() {
        return this.GY;
    }

    public int getVersionCode() {
        return this.kg;
    }

    public void writeToParcel(Parcel parcel, int i) {
        l.a(this, parcel, i);
    }
}
