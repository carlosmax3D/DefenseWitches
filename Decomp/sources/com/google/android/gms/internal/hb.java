package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class hb extends gs implements SafeParcelable {
    private static final hb AJ = new hb(0, new hd[0], new float[0], 0);
    public static final hc CREATOR = new hc();
    private final hd[] AK;
    private final float[] AL;
    final int kg;
    private final long vO;

    hb(int i, hd[] hdVarArr, float[] fArr, long j) {
        eg.b(hdVarArr.length == fArr.length, (Object) "mismatched places to probabilities arrays");
        this.kg = i;
        this.AK = hdVarArr;
        this.AL = fArr;
        this.vO = j;
    }

    public hd[] dU() {
        return this.AK;
    }

    public float[] dV() {
        return this.AL;
    }

    public int describeContents() {
        hc hcVar = CREATOR;
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof hb)) {
            return false;
        }
        hb hbVar = (hb) obj;
        return this.AK.equals(hbVar.AK) && this.AL.equals(hbVar.AL);
    }

    public long getTimestampMillis() {
        return this.vO;
    }

    public int hashCode() {
        return ee.hashCode(this.AK, this.AL);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PlaceEstimate{");
        for (int i = 0; i < this.AK.length; i++) {
            sb.append(String.format("(%f, %s)", new Object[]{Float.valueOf(this.AL[i]), this.AK[i].toString()}));
            if (i != this.AK.length - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        hc hcVar = CREATOR;
        hc.a(this, parcel, i);
    }
}
