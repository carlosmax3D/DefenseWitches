package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class StatusCreator implements Parcelable.Creator<Status> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(Status status, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, status.getStatusCode());
        b.c(parcel, 1000, status.getVersionCode());
        b.a(parcel, 2, status.bt(), false);
        b.a(parcel, 3, (Parcelable) status.bs(), i, false);
        b.D(parcel, o);
    }

    public Status createFromParcel(Parcel parcel) {
        PendingIntent pendingIntent = null;
        int i = 0;
        int n = a.n(parcel);
        String str = null;
        int i2 = 0;
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
                    pendingIntent = (PendingIntent) a.a(parcel, m, PendingIntent.CREATOR);
                    break;
                case 1000:
                    i2 = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new Status(i2, i, str, pendingIntent);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public Status[] newArray(int i) {
        return new Status[i];
    }
}
