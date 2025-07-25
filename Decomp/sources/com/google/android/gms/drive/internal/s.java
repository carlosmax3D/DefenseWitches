package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class s implements Parcelable.Creator<OnDownloadProgressResponse> {
    static void a(OnDownloadProgressResponse onDownloadProgressResponse, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, onDownloadProgressResponse.kg);
        b.a(parcel, 2, onDownloadProgressResponse.rx);
        b.a(parcel, 3, onDownloadProgressResponse.ry);
        b.D(parcel, o);
    }

    /* renamed from: H */
    public OnDownloadProgressResponse createFromParcel(Parcel parcel) {
        long j = 0;
        int n = a.n(parcel);
        int i = 0;
        long j2 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    j2 = a.h(parcel, m);
                    break;
                case 3:
                    j = a.h(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new OnDownloadProgressResponse(i, j2, j);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: ah */
    public OnDownloadProgressResponse[] newArray(int i) {
        return new OnDownloadProgressResponse[i];
    }
}
