package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.internal.q;
import com.google.android.gms.internal.eg;
import com.google.android.gms.internal.iy;
import com.google.android.gms.internal.iz;
import jp.stargarage.g2metrics.BuildConfig;

public class DriveId implements SafeParcelable {
    public static final Parcelable.Creator<DriveId> CREATOR = new c();
    final int kg;
    final String qO;
    final long qP;
    final long qQ;
    private volatile String qR;

    DriveId(int i, String str, long j, long j2) {
        boolean z = false;
        this.qR = null;
        this.kg = i;
        this.qO = str;
        eg.r(!BuildConfig.FLAVOR.equals(str));
        eg.r((str == null && j == -1) ? z : true);
        this.qP = j;
        this.qQ = j2;
    }

    public DriveId(String str, long j, long j2) {
        this(1, str, j, j2);
    }

    public static DriveId ab(String str) {
        eg.f(str);
        return new DriveId(str, -1, -1);
    }

    static DriveId d(byte[] bArr) {
        try {
            q e = q.e(bArr);
            return new DriveId(e.versionCode, BuildConfig.FLAVOR.equals(e.rt) ? null : e.rt, e.ru, e.rv);
        } catch (iy e2) {
            throw new IllegalArgumentException();
        }
    }

    public static DriveId decodeFromString(String str) {
        eg.b(str.startsWith("DriveId:"), (Object) "Invalid DriveId: " + str);
        return d(Base64.decode(str.substring("DriveId:".length()), 10));
    }

    /* access modifiers changed from: package-private */
    public final byte[] cL() {
        q qVar = new q();
        qVar.versionCode = this.kg;
        qVar.rt = this.qO == null ? BuildConfig.FLAVOR : this.qO;
        qVar.ru = this.qP;
        qVar.rv = this.qQ;
        return iz.a((iz) qVar);
    }

    public int describeContents() {
        return 0;
    }

    public final String encodeToString() {
        if (this.qR == null) {
            this.qR = "DriveId:" + Base64.encodeToString(cL(), 10);
        }
        return this.qR;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DriveId)) {
            return false;
        }
        DriveId driveId = (DriveId) obj;
        if (driveId.qQ == this.qQ) {
            return (driveId.qP == -1 && this.qP == -1) ? driveId.qO.equals(this.qO) : driveId.qP == this.qP;
        }
        Log.w("DriveId", "Attempt to compare invalid DriveId detected. Has local storage been cleared?");
        return false;
    }

    public String getResourceId() {
        return this.qO;
    }

    public int hashCode() {
        return this.qP == -1 ? this.qO.hashCode() : (String.valueOf(this.qQ) + String.valueOf(this.qP)).hashCode();
    }

    public String toString() {
        return encodeToString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        c.a(this, parcel, i);
    }
}
