package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class CreateFileIntentSenderRequest implements SafeParcelable {
    public static final Parcelable.Creator<CreateFileIntentSenderRequest> CREATOR = new d();
    final int kg;
    final int qE;
    final String qL;
    final DriveId qM;
    final MetadataBundle qZ;

    CreateFileIntentSenderRequest(int i, MetadataBundle metadataBundle, int i2, String str, DriveId driveId) {
        this.kg = i;
        this.qZ = metadataBundle;
        this.qE = i2;
        this.qL = str;
        this.qM = driveId;
    }

    public CreateFileIntentSenderRequest(MetadataBundle metadataBundle, int i, String str, DriveId driveId) {
        this(1, metadataBundle, i, str, driveId);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        d.a(this, parcel, i);
    }
}
