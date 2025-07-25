package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class c implements Parcelable.Creator<DriveId> {
    static void a(DriveId driveId, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, driveId.kg);
        b.a(parcel, 2, driveId.qO, false);
        b.a(parcel, 3, driveId.qP);
        b.a(parcel, 4, driveId.qQ);
        b.D(parcel, o);
    }

    /* renamed from: Z */
    public DriveId[] newArray(int i) {
        return new DriveId[i];
    }

    /* renamed from: z */
    public DriveId createFromParcel(Parcel parcel) {
        long j = 0;
        int n = a.n(parcel);
        int i = 0;
        String str = null;
        long j2 = 0;
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
                    j2 = a.h(parcel, m);
                    break;
                case 4:
                    j = a.h(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new DriveId(i, str, j2, j);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
