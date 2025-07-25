package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.LocationRequest;

public final class gv implements SafeParcelable {
    public static final gw CREATOR = new gw();
    final int kg;
    private final LocationRequest yl;
    private final gt ym;

    public gv(int i, LocationRequest locationRequest, gt gtVar) {
        this.kg = i;
        this.yl = locationRequest;
        this.ym = gtVar;
    }

    public LocationRequest dS() {
        return this.yl;
    }

    public gt dT() {
        return this.ym;
    }

    public int describeContents() {
        gw gwVar = CREATOR;
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof gv)) {
            return false;
        }
        gv gvVar = (gv) obj;
        return this.yl.equals(gvVar.yl) && this.ym.equals(gvVar.ym);
    }

    public int hashCode() {
        return ee.hashCode(this.yl, this.ym);
    }

    public String toString() {
        return ee.e(this).a("locationRequest", this.yl).a("filter", this.ym).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        gw gwVar = CREATOR;
        gw.a(this, parcel, i);
    }
}
