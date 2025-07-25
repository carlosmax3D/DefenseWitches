package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class f implements Parcelable.Creator<CreateFolderRequest> {
    static void a(CreateFolderRequest createFolderRequest, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, createFolderRequest.kg);
        b.a(parcel, 2, (Parcelable) createFolderRequest.ra, i, false);
        b.a(parcel, 3, (Parcelable) createFolderRequest.qZ, i, false);
        b.D(parcel, o);
    }

    /* renamed from: E */
    public CreateFolderRequest createFromParcel(Parcel parcel) {
        MetadataBundle metadataBundle;
        DriveId driveId;
        int i;
        MetadataBundle metadataBundle2 = null;
        int n = a.n(parcel);
        int i2 = 0;
        DriveId driveId2 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    MetadataBundle metadataBundle3 = metadataBundle2;
                    driveId = driveId2;
                    i = a.g(parcel, m);
                    metadataBundle = metadataBundle3;
                    break;
                case 2:
                    i = i2;
                    DriveId driveId3 = (DriveId) a.a(parcel, m, DriveId.CREATOR);
                    metadataBundle = metadataBundle2;
                    driveId = driveId3;
                    break;
                case 3:
                    metadataBundle = (MetadataBundle) a.a(parcel, m, MetadataBundle.CREATOR);
                    driveId = driveId2;
                    i = i2;
                    break;
                default:
                    a.b(parcel, m);
                    metadataBundle = metadataBundle2;
                    driveId = driveId2;
                    i = i2;
                    break;
            }
            i2 = i;
            driveId2 = driveId;
            metadataBundle2 = metadataBundle;
        }
        if (parcel.dataPosition() == n) {
            return new CreateFolderRequest(i2, driveId2, metadataBundle2);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: ae */
    public CreateFolderRequest[] newArray(int i) {
        return new CreateFolderRequest[i];
    }
}
