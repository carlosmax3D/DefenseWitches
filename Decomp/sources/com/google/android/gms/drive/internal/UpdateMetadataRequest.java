package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class UpdateMetadataRequest implements SafeParcelable {
    public static final Parcelable.Creator<UpdateMetadataRequest> CREATOR = new aa();
    final int kg;
    final MetadataBundle rB;
    final DriveId rr;

    UpdateMetadataRequest(int i, DriveId driveId, MetadataBundle metadataBundle) {
        this.kg = i;
        this.rr = driveId;
        this.rB = metadataBundle;
    }

    public UpdateMetadataRequest(DriveId driveId, MetadataBundle metadataBundle) {
        this(1, driveId, metadataBundle);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        aa.a(this, parcel, i);
    }
}
