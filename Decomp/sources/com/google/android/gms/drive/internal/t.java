package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.DriveId;

public class t implements Parcelable.Creator<OnDriveIdResponse> {
    static void a(OnDriveIdResponse onDriveIdResponse, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, onDriveIdResponse.kg);
        b.a(parcel, 2, (Parcelable) onDriveIdResponse.rr, i, false);
        b.D(parcel, o);
    }

    /* renamed from: I */
    public OnDriveIdResponse createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        DriveId driveId = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    driveId = (DriveId) a.a(parcel, m, DriveId.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new OnDriveIdResponse(i, driveId);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: ai */
    public OnDriveIdResponse[] newArray(int i) {
        return new OnDriveIdResponse[i];
    }
}
