package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ee;

public class d implements SafeParcelable {
    public static final e CREATOR = new e();
    private final int kg;
    int xG;
    int xH;
    long xI;

    d(int i, int i2, int i3, long j) {
        this.kg = i;
        this.xG = i2;
        this.xH = i3;
        this.xI = j;
    }

    private String aQ(int i) {
        switch (i) {
            case 0:
                return "STATUS_SUCCESSFUL";
            case 2:
                return "STATUS_TIMED_OUT_ON_SCAN";
            case 3:
                return "STATUS_NO_INFO_IN_DATABASE";
            case 4:
                return "STATUS_INVALID_SCAN";
            case 5:
                return "STATUS_UNABLE_TO_QUERY_DATABASE";
            case 6:
                return "STATUS_SCANS_DISABLED_IN_SETTINGS";
            case 7:
                return "STATUS_LOCATION_DISABLED_IN_SETTINGS";
            case 8:
                return "STATUS_IN_PROGRESS";
            default:
                return "STATUS_UNKNOWN";
        }
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        return this.xG == dVar.xG && this.xH == dVar.xH && this.xI == dVar.xI;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public int hashCode() {
        return ee.hashCode(Integer.valueOf(this.xG), Integer.valueOf(this.xH), Long.valueOf(this.xI));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LocationStatus[cell status: ").append(aQ(this.xG));
        sb.append(", wifi status: ").append(aQ(this.xH));
        sb.append(", elapsed realtime ns: ").append(this.xI);
        sb.append(']');
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        e.a(this, parcel, i);
    }
}
