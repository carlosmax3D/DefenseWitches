package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.DriveId;

public class GetMetadataRequest implements SafeParcelable {
    public static final Parcelable.Creator<GetMetadataRequest> CREATOR = new n();
    final int kg;
    final DriveId rr;

    GetMetadataRequest(int i, DriveId driveId) {
        this.kg = i;
        this.rr = driveId;
    }

    public GetMetadataRequest(DriveId driveId) {
        this(1, driveId);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        n.a(this, parcel, i);
    }
}
