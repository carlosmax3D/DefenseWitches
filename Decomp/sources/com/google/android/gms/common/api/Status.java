package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ee;

public final class Status implements Result, SafeParcelable {
    public static final StatusCreator CREATOR = new StatusCreator();
    public static final Status nA = new Status(0, (String) null, (PendingIntent) null);
    public static final Status nB = new Status(14, (String) null, (PendingIntent) null);
    public static final Status nC = new Status(15, (String) null, (PendingIntent) null);
    private final int kg;
    private final int mC;
    private final PendingIntent mPendingIntent;
    private final String nD;

    public Status(int i) {
        this(1, i, (String) null, (PendingIntent) null);
    }

    Status(int i, int i2, String str, PendingIntent pendingIntent) {
        this.kg = i;
        this.mC = i2;
        this.nD = str;
        this.mPendingIntent = pendingIntent;
    }

    public Status(int i, String str, PendingIntent pendingIntent) {
        this(1, i, str, pendingIntent);
    }

    private String bh() {
        return this.nD != null ? this.nD : CommonStatusCodes.getStatusCodeString(this.mC);
    }

    /* access modifiers changed from: package-private */
    public PendingIntent bs() {
        return this.mPendingIntent;
    }

    /* access modifiers changed from: package-private */
    public String bt() {
        return this.nD;
    }

    @Deprecated
    public ConnectionResult bu() {
        return new ConnectionResult(this.mC, this.mPendingIntent);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Status)) {
            return false;
        }
        Status status = (Status) obj;
        return this.kg == status.kg && this.mC == status.mC && ee.equal(this.nD, status.nD) && ee.equal(this.mPendingIntent, status.mPendingIntent);
    }

    public PendingIntent getResolution() {
        return this.mPendingIntent;
    }

    public Status getStatus() {
        return this;
    }

    public int getStatusCode() {
        return this.mC;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public boolean hasResolution() {
        return this.mPendingIntent != null;
    }

    public int hashCode() {
        return ee.hashCode(Integer.valueOf(this.kg), Integer.valueOf(this.mC), this.nD, this.mPendingIntent);
    }

    public boolean isInterrupted() {
        return this.mC == 14;
    }

    public boolean isSuccess() {
        return this.mC <= 0;
    }

    public void startResolutionForResult(Activity activity, int i) throws IntentSender.SendIntentException {
        if (hasResolution()) {
            activity.startIntentSenderForResult(this.mPendingIntent.getIntentSender(), i, (Intent) null, 0, 0, 0);
        }
    }

    public String toString() {
        return ee.e(this).a("statusCode", bh()).a("resolution", this.mPendingIntent).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        StatusCreator.a(this, parcel, i);
    }
}
