package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class a implements Parcelable.Creator<Contents> {
    static void a(Contents contents, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, contents.kg);
        b.a(parcel, 2, (Parcelable) contents.om, i, false);
        b.c(parcel, 3, contents.qE);
        b.c(parcel, 4, contents.qF);
        b.a(parcel, 5, (Parcelable) contents.qG, i, false);
        b.D(parcel, o);
    }

    /* renamed from: Y */
    public Contents[] newArray(int i) {
        return new Contents[i];
    }

    /* renamed from: y */
    public Contents createFromParcel(Parcel parcel) {
        DriveId driveId = null;
        int i = 0;
        int n = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
        int i2 = 0;
        ParcelFileDescriptor parcelFileDescriptor = null;
        int i3 = 0;
        while (parcel.dataPosition() < n) {
            int m = com.google.android.gms.common.internal.safeparcel.a.m(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.M(m)) {
                case 1:
                    i3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, m);
                    break;
                case 2:
                    parcelFileDescriptor = (ParcelFileDescriptor) com.google.android.gms.common.internal.safeparcel.a.a(parcel, m, ParcelFileDescriptor.CREATOR);
                    break;
                case 3:
                    i2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, m);
                    break;
                case 4:
                    i = com.google.android.gms.common.internal.safeparcel.a.g(parcel, m);
                    break;
                case 5:
                    driveId = (DriveId) com.google.android.gms.common.internal.safeparcel.a.a(parcel, m, DriveId.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new Contents(i3, parcelFileDescriptor, i2, i, driveId);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
