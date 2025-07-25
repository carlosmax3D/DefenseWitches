package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class c implements Parcelable.Creator<CreateContentsRequest> {
    static void a(CreateContentsRequest createContentsRequest, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, createContentsRequest.kg);
        b.D(parcel, o);
    }

    /* renamed from: B */
    public CreateContentsRequest createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new CreateContentsRequest(i);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: ab */
    public CreateContentsRequest[] newArray(int i) {
        return new CreateContentsRequest[i];
    }
}
