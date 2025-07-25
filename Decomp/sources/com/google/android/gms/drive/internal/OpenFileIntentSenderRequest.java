package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.DriveId;

public class OpenFileIntentSenderRequest implements SafeParcelable {
    public static final Parcelable.Creator<OpenFileIntentSenderRequest> CREATOR = new x();
    final int kg;
    final String qL;
    final DriveId qM;
    final String[] qW;

    OpenFileIntentSenderRequest(int i, String str, String[] strArr, DriveId driveId) {
        this.kg = i;
        this.qL = str;
        this.qW = strArr;
        this.qM = driveId;
    }

    public OpenFileIntentSenderRequest(String str, String[] strArr, DriveId driveId) {
        this(1, str, strArr, driveId);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        x.a(this, parcel, i);
    }
}
