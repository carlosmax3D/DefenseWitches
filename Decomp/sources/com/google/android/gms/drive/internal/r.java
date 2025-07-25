package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.Contents;

public class r implements Parcelable.Creator<OnContentsResponse> {
    static void a(OnContentsResponse onContentsResponse, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, onContentsResponse.kg);
        b.a(parcel, 2, (Parcelable) onContentsResponse.qK, i, false);
        b.D(parcel, o);
    }

    /* renamed from: G */
    public OnContentsResponse createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        Contents contents = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    contents = (Contents) a.a(parcel, m, Contents.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new OnContentsResponse(i, contents);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: ag */
    public OnContentsResponse[] newArray(int i) {
        return new OnContentsResponse[i];
    }
}
