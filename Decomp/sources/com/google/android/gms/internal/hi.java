package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class hi implements SafeParcelable {
    public static final hj CREATOR = new hj();
    public final String Bn;
    public final String Bo;
    public final int versionCode;

    public hi(int i, String str, String str2) {
        this.versionCode = i;
        this.Bn = str;
        this.Bo = str2;
    }

    public int describeContents() {
        hj hjVar = CREATOR;
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof hi)) {
            return false;
        }
        hi hiVar = (hi) obj;
        return this.Bo.equals(hiVar.Bo) && this.Bn.equals(hiVar.Bn);
    }

    public int hashCode() {
        return ee.hashCode(this.Bn, this.Bo);
    }

    public String toString() {
        return ee.e(this).a(GoogleAuthUtil.KEY_CLIENT_PACKAGE_NAME, this.Bn).a("locale", this.Bo).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        hj hjVar = CREATOR;
        hj.a(this, parcel, i);
    }
}
