package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.eg;

public class CreateFileRequest implements SafeParcelable {
    public static final Parcelable.Creator<CreateFileRequest> CREATOR = new e();
    final int kg;
    final Contents qX;
    final MetadataBundle qZ;
    final DriveId ra;

    CreateFileRequest(int i, DriveId driveId, MetadataBundle metadataBundle, Contents contents) {
        this.kg = i;
        this.ra = (DriveId) eg.f(driveId);
        this.qZ = (MetadataBundle) eg.f(metadataBundle);
        this.qX = (Contents) eg.f(contents);
    }

    public CreateFileRequest(DriveId driveId, MetadataBundle metadataBundle, Contents contents) {
        this(1, driveId, metadataBundle, contents);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        e.a(this, parcel, i);
    }
}
