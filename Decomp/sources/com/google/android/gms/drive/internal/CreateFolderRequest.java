package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.eg;

public class CreateFolderRequest implements SafeParcelable {
    public static final Parcelable.Creator<CreateFolderRequest> CREATOR = new f();
    final int kg;
    final MetadataBundle qZ;
    final DriveId ra;

    CreateFolderRequest(int i, DriveId driveId, MetadataBundle metadataBundle) {
        this.kg = i;
        this.ra = (DriveId) eg.f(driveId);
        this.qZ = (MetadataBundle) eg.f(metadataBundle);
    }

    public CreateFolderRequest(DriveId driveId, MetadataBundle metadataBundle) {
        this(1, driveId, metadataBundle);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        f.a(this, parcel, i);
    }
}
