package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class el implements SafeParcelable {
    public static final em CREATOR = new em();
    private final int kg;
    @Deprecated
    private final Bundle nN;
    private final int os;
    private final int pX;
    private final String pY;
    private final String pZ;
    private final String qa;
    private final String qb;

    el(int i, int i2, int i3, String str, String str2, String str3, String str4, Bundle bundle) {
        this.kg = i;
        this.os = i2;
        this.pX = i3;
        this.pY = str;
        this.pZ = str2;
        this.qa = str3;
        this.qb = str4;
        this.nN = bundle == null ? new Bundle() : bundle;
    }

    public int bY() {
        return this.pX;
    }

    public String bZ() {
        return this.pY;
    }

    public String ca() {
        return this.pZ;
    }

    public String cb() {
        return this.qb;
    }

    public boolean cc() {
        return this.os == 1 && this.pX == -1;
    }

    public boolean cd() {
        return this.os == 2;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof el)) {
            return false;
        }
        el elVar = (el) obj;
        return this.kg == elVar.kg && this.os == elVar.os && this.pX == elVar.pX && ee.equal(this.pY, elVar.pY) && ee.equal(this.pZ, elVar.pZ);
    }

    public String getDisplayName() {
        return this.qa;
    }

    @Deprecated
    public Bundle getMetadata() {
        return this.nN;
    }

    public int getType() {
        return this.os;
    }

    public int getVersionCode() {
        return this.kg;
    }

    public int hashCode() {
        return ee.hashCode(Integer.valueOf(this.kg), Integer.valueOf(this.os), Integer.valueOf(this.pX), this.pY, this.pZ);
    }

    public String toString() {
        if (cd()) {
            return String.format("Person [%s] %s", new Object[]{ca(), getDisplayName()});
        } else if (cc()) {
            return String.format("Circle [%s] %s", new Object[]{bZ(), getDisplayName()});
        } else {
            return String.format("Group [%s] %s", new Object[]{bZ(), getDisplayName()});
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        em.a(this, parcel, i);
    }
}
