package com.google.android.gms.location;

import android.app.PendingIntent;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class f implements SafeParcelable {
    public static final g CREATOR = new g();
    private final int kg;
    private final String xJ;
    private final PendingIntent xr;

    public f(int i, PendingIntent pendingIntent, String str) {
        this.kg = i;
        this.xr = pendingIntent;
        this.xJ = str;
    }

    public PendingIntent dB() {
        return this.xr;
    }

    public String dC() {
        return this.xJ;
    }

    public int describeContents() {
        g gVar = CREATOR;
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        f fVar = (f) obj;
        if (this.xr == null) {
            if (fVar.xr != null) {
                return false;
            }
        } else if (!this.xr.equals(fVar.xr)) {
            return false;
        }
        return this.xJ == null ? fVar.xJ == null : this.xJ.equals(fVar.xJ);
    }

    public int getVersionCode() {
        return this.kg;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.xr == null ? 0 : this.xr.hashCode()) + 31) * 31;
        if (this.xJ != null) {
            i = this.xJ.hashCode();
        }
        return hashCode + i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        g gVar = CREATOR;
        g.a(this, parcel, i);
    }
}
