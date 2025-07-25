package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class d implements Parcelable.Creator<CreateFileIntentSenderRequest> {
    static void a(CreateFileIntentSenderRequest createFileIntentSenderRequest, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, createFileIntentSenderRequest.kg);
        b.a(parcel, 2, (Parcelable) createFileIntentSenderRequest.qZ, i, false);
        b.c(parcel, 3, createFileIntentSenderRequest.qE);
        b.a(parcel, 4, createFileIntentSenderRequest.qL, false);
        b.a(parcel, 5, (Parcelable) createFileIntentSenderRequest.qM, i, false);
        b.D(parcel, o);
    }

    /* renamed from: C */
    public CreateFileIntentSenderRequest createFromParcel(Parcel parcel) {
        int i = 0;
        DriveId driveId = null;
        int n = a.n(parcel);
        String str = null;
        MetadataBundle metadataBundle = null;
        int i2 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i2 = a.g(parcel, m);
                    break;
                case 2:
                    metadataBundle = (MetadataBundle) a.a(parcel, m, MetadataBundle.CREATOR);
                    break;
                case 3:
                    i = a.g(parcel, m);
                    break;
                case 4:
                    str = a.m(parcel, m);
                    break;
                case 5:
                    driveId = (DriveId) a.a(parcel, m, DriveId.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new CreateFileIntentSenderRequest(i2, metadataBundle, i, str, driveId);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: ac */
    public CreateFileIntentSenderRequest[] newArray(int i) {
        return new CreateFileIntentSenderRequest[i];
    }
}
