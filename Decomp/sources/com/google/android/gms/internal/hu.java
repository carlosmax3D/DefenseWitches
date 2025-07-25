package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Arrays;

public class hu implements SafeParcelable {
    public static final hw CREATOR = new hw();
    private final String[] DR;
    private final String[] DS;
    private final String[] DT;
    private final String DU;
    private final String DV;
    private final String DW;
    private final String DX;
    private final String jG;
    private final int kg;

    hu(int i, String str, String[] strArr, String[] strArr2, String[] strArr3, String str2, String str3, String str4, String str5) {
        this.kg = i;
        this.jG = str;
        this.DR = strArr;
        this.DS = strArr2;
        this.DT = strArr3;
        this.DU = str2;
        this.DV = str3;
        this.DW = str4;
        this.DX = str5;
    }

    public hu(String str, String[] strArr, String[] strArr2, String[] strArr3, String str2, String str3, String str4) {
        this.kg = 1;
        this.jG = str;
        this.DR = strArr;
        this.DS = strArr2;
        this.DT = strArr3;
        this.DU = str2;
        this.DV = str3;
        this.DW = str4;
        this.DX = null;
    }

    public int describeContents() {
        return 0;
    }

    public String[] eR() {
        return this.DR;
    }

    public String[] eS() {
        return this.DS;
    }

    public String[] eT() {
        return this.DT;
    }

    public String eU() {
        return this.DU;
    }

    public String eV() {
        return this.DV;
    }

    public String eW() {
        return this.DW;
    }

    public String eX() {
        return this.DX;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof hu)) {
            return false;
        }
        hu huVar = (hu) obj;
        return this.kg == huVar.kg && ee.equal(this.jG, huVar.jG) && Arrays.equals(this.DR, huVar.DR) && Arrays.equals(this.DS, huVar.DS) && Arrays.equals(this.DT, huVar.DT) && ee.equal(this.DU, huVar.DU) && ee.equal(this.DV, huVar.DV) && ee.equal(this.DW, huVar.DW) && ee.equal(this.DX, huVar.DX);
    }

    public String getAccountName() {
        return this.jG;
    }

    public int getVersionCode() {
        return this.kg;
    }

    public int hashCode() {
        return ee.hashCode(Integer.valueOf(this.kg), this.jG, this.DR, this.DS, this.DT, this.DU, this.DV, this.DW, this.DX);
    }

    public String toString() {
        return ee.e(this).a("versionCode", Integer.valueOf(this.kg)).a("accountName", this.jG).a("requestedScopes", this.DR).a("visibleActivities", this.DS).a("requiredFeatures", this.DT).a("packageNameForAuth", this.DU).a("callingPackageName", this.DV).a("applicationName", this.DW).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        hw.a(this, parcel, i);
    }
}
