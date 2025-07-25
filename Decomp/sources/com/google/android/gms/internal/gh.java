package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.List;

public class gh implements SafeParcelable, Cloneable {
    public static final gi CREATOR = new gi();
    private final int kg;
    private final boolean xK;
    private final boolean xL;
    private final boolean xM;
    private final boolean xN;
    private final ArrayList<el> xO;

    public gh(int i, boolean z, boolean z2, boolean z3, boolean z4, ArrayList<el> arrayList) {
        this.kg = i;
        this.xK = z;
        this.xL = z2;
        this.xM = z3;
        this.xN = z4;
        this.xO = arrayList;
    }

    public Object clone() {
        return new gh(this.kg, this.xK, this.xL, this.xM, this.xN, this.xO == null ? null : (ArrayList) this.xO.clone());
    }

    public boolean dD() {
        return this.xL;
    }

    public boolean dE() {
        return this.xM;
    }

    public boolean dF() {
        return this.xN;
    }

    public List<el> dG() {
        return this.xO;
    }

    public int describeContents() {
        gi giVar = CREATOR;
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
        gh ghVar = (gh) obj;
        if (this.xO == null) {
            if (ghVar.xO != null) {
                return false;
            }
        } else if (!this.xO.equals(ghVar.xO)) {
            return false;
        }
        if (this.xK != ghVar.xK) {
            return false;
        }
        if (this.xL != ghVar.xL) {
            return false;
        }
        if (this.xN != ghVar.xN) {
            return false;
        }
        if (this.xM != ghVar.xM) {
            return false;
        }
        return this.kg == ghVar.kg;
    }

    public int getVersionCode() {
        return this.kg;
    }

    public int hashCode() {
        int i = 1231;
        int hashCode = ((this.xN ? 1231 : 1237) + (((this.xL ? 1231 : 1237) + (((this.xK ? 1231 : 1237) + (((this.xO == null ? 0 : this.xO.hashCode()) + 31) * 31)) * 31)) * 31)) * 31;
        if (!this.xM) {
            i = 1237;
        }
        return ((hashCode + i) * 31) + this.kg;
    }

    public boolean isEnabled() {
        return this.xK;
    }

    public String toString() {
        return "CopresenceSettings [mVersionCode=" + this.kg + ", mEnabled=" + this.xK + "," + "mAcl={" + this.xO.toArray() + "}," + ", mNotifiedForNonAcl=" + this.xM + "]";
    }

    public void writeToParcel(Parcel parcel, int i) {
        gi giVar = CREATOR;
        gi.a(this, parcel, i);
    }
}
