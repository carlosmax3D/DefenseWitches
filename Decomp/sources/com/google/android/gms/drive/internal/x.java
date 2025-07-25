package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.DriveId;

public class x implements Parcelable.Creator<OpenFileIntentSenderRequest> {
    static void a(OpenFileIntentSenderRequest openFileIntentSenderRequest, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, openFileIntentSenderRequest.kg);
        b.a(parcel, 2, openFileIntentSenderRequest.qL, false);
        b.a(parcel, 3, openFileIntentSenderRequest.qW, false);
        b.a(parcel, 4, (Parcelable) openFileIntentSenderRequest.qM, i, false);
        b.D(parcel, o);
    }

    /* renamed from: M */
    public OpenFileIntentSenderRequest createFromParcel(Parcel parcel) {
        DriveId driveId = null;
        int n = a.n(parcel);
        int i = 0;
        String[] strArr = null;
        String str = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    str = a.m(parcel, m);
                    break;
                case 3:
                    strArr = a.x(parcel, m);
                    break;
                case 4:
                    driveId = (DriveId) a.a(parcel, m, DriveId.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new OpenFileIntentSenderRequest(i, str, strArr, driveId);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: am */
    public OpenFileIntentSenderRequest[] newArray(int i) {
        return new OpenFileIntentSenderRequest[i];
    }
}
