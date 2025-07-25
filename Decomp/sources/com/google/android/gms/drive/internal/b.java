package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.drive.Contents;

public class b implements Parcelable.Creator<CloseContentsRequest> {
    static void a(CloseContentsRequest closeContentsRequest, Parcel parcel, int i) {
        int o = com.google.android.gms.common.internal.safeparcel.b.o(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, closeContentsRequest.kg);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable) closeContentsRequest.qX, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, closeContentsRequest.qY, false);
        com.google.android.gms.common.internal.safeparcel.b.D(parcel, o);
    }

    /* renamed from: A */
    public CloseContentsRequest createFromParcel(Parcel parcel) {
        Boolean d;
        Contents contents;
        int i;
        Boolean bool = null;
        int n = a.n(parcel);
        int i2 = 0;
        Contents contents2 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    Boolean bool2 = bool;
                    contents = contents2;
                    i = a.g(parcel, m);
                    d = bool2;
                    break;
                case 2:
                    i = i2;
                    Contents contents3 = (Contents) a.a(parcel, m, Contents.CREATOR);
                    d = bool;
                    contents = contents3;
                    break;
                case 3:
                    d = a.d(parcel, m);
                    contents = contents2;
                    i = i2;
                    break;
                default:
                    a.b(parcel, m);
                    d = bool;
                    contents = contents2;
                    i = i2;
                    break;
            }
            i2 = i;
            contents2 = contents;
            bool = d;
        }
        if (parcel.dataPosition() == n) {
            return new CloseContentsRequest(i2, contents2, bool);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: aa */
    public CloseContentsRequest[] newArray(int i) {
        return new CloseContentsRequest[i];
    }
}
