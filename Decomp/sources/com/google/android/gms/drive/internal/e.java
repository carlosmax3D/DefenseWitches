package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class e implements Parcelable.Creator<CreateFileRequest> {
    static void a(CreateFileRequest createFileRequest, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, createFileRequest.kg);
        b.a(parcel, 2, (Parcelable) createFileRequest.ra, i, false);
        b.a(parcel, 3, (Parcelable) createFileRequest.qZ, i, false);
        b.a(parcel, 4, (Parcelable) createFileRequest.qX, i, false);
        b.D(parcel, o);
    }

    /* renamed from: D */
    public CreateFileRequest createFromParcel(Parcel parcel) {
        Contents contents;
        MetadataBundle metadataBundle;
        DriveId driveId;
        int i;
        Contents contents2 = null;
        int n = a.n(parcel);
        int i2 = 0;
        MetadataBundle metadataBundle2 = null;
        DriveId driveId2 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    Contents contents3 = contents2;
                    metadataBundle = metadataBundle2;
                    driveId = driveId2;
                    i = a.g(parcel, m);
                    contents = contents3;
                    break;
                case 2:
                    i = i2;
                    MetadataBundle metadataBundle3 = metadataBundle2;
                    driveId = (DriveId) a.a(parcel, m, DriveId.CREATOR);
                    contents = contents2;
                    metadataBundle = metadataBundle3;
                    break;
                case 3:
                    driveId = driveId2;
                    i = i2;
                    Contents contents4 = contents2;
                    metadataBundle = (MetadataBundle) a.a(parcel, m, MetadataBundle.CREATOR);
                    contents = contents4;
                    break;
                case 4:
                    contents = (Contents) a.a(parcel, m, Contents.CREATOR);
                    metadataBundle = metadataBundle2;
                    driveId = driveId2;
                    i = i2;
                    break;
                default:
                    a.b(parcel, m);
                    contents = contents2;
                    metadataBundle = metadataBundle2;
                    driveId = driveId2;
                    i = i2;
                    break;
            }
            i2 = i;
            driveId2 = driveId;
            metadataBundle2 = metadataBundle;
            contents2 = contents;
        }
        if (parcel.dataPosition() == n) {
            return new CreateFileRequest(i2, driveId2, metadataBundle2, contents2);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: ad */
    public CreateFileRequest[] newArray(int i) {
        return new CreateFileRequest[i];
    }
}
