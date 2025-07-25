package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class bj implements SafeParcelable {
    public static final bi CREATOR = new bi();
    public final String gn;
    public final String go;
    public final String gp;
    public final String gq;
    public final String gr;
    public final String mimeType;
    public final String packageName;
    public final int versionCode;

    public bj(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.versionCode = i;
        this.gn = str;
        this.go = str2;
        this.mimeType = str3;
        this.packageName = str4;
        this.gp = str5;
        this.gq = str6;
        this.gr = str7;
    }

    public bj(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this(1, str, str2, str3, str4, str5, str6, str7);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        bi.a(this, parcel, i);
    }
}
