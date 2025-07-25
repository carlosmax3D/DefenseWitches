package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class v implements Parcelable.Creator<OnMetadataResponse> {
    static void a(OnMetadataResponse onMetadataResponse, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, onMetadataResponse.kg);
        b.a(parcel, 2, (Parcelable) onMetadataResponse.qZ, i, false);
        b.D(parcel, o);
    }

    /* renamed from: K */
    public OnMetadataResponse createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        MetadataBundle metadataBundle = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    metadataBundle = (MetadataBundle) a.a(parcel, m, MetadataBundle.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new OnMetadataResponse(i, metadataBundle);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: ak */
    public OnMetadataResponse[] newArray(int i) {
        return new OnMetadataResponse[i];
    }
}
