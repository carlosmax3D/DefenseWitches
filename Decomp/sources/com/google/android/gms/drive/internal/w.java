package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.DriveId;

public class w implements Parcelable.Creator<OpenContentsRequest> {
    static void a(OpenContentsRequest openContentsRequest, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, openContentsRequest.kg);
        b.a(parcel, 2, (Parcelable) openContentsRequest.rr, i, false);
        b.c(parcel, 3, openContentsRequest.qF);
        b.D(parcel, o);
    }

    /* renamed from: L */
    public OpenContentsRequest createFromParcel(Parcel parcel) {
        int g;
        DriveId driveId;
        int i;
        int i2 = 0;
        int n = a.n(parcel);
        DriveId driveId2 = null;
        int i3 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    int i4 = i2;
                    driveId = driveId2;
                    i = a.g(parcel, m);
                    g = i4;
                    break;
                case 2:
                    i = i3;
                    DriveId driveId3 = (DriveId) a.a(parcel, m, DriveId.CREATOR);
                    g = i2;
                    driveId = driveId3;
                    break;
                case 3:
                    g = a.g(parcel, m);
                    driveId = driveId2;
                    i = i3;
                    break;
                default:
                    a.b(parcel, m);
                    g = i2;
                    driveId = driveId2;
                    i = i3;
                    break;
            }
            i3 = i;
            driveId2 = driveId;
            i2 = g;
        }
        if (parcel.dataPosition() == n) {
            return new OpenContentsRequest(i3, driveId2, i2);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: al */
    public OpenContentsRequest[] newArray(int i) {
        return new OpenContentsRequest[i];
    }
}
