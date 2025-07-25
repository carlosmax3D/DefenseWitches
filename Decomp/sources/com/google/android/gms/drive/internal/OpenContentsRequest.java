package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.DriveId;

public class OpenContentsRequest implements SafeParcelable {
    public static final Parcelable.Creator<OpenContentsRequest> CREATOR = new w();
    final int kg;
    final int qF;
    final DriveId rr;

    OpenContentsRequest(int i, DriveId driveId, int i2) {
        this.kg = i;
        this.rr = driveId;
        this.qF = i2;
    }

    public OpenContentsRequest(DriveId driveId, int i) {
        this(1, driveId, i);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        w.a(this, parcel, i);
    }
}
