package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class d implements Parcelable.Creator<MetadataBundle> {
    static void a(MetadataBundle metadataBundle, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, metadataBundle.kg);
        b.a(parcel, 2, metadataBundle.rF, false);
        b.D(parcel, o);
    }

    /* renamed from: P */
    public MetadataBundle createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        Bundle bundle = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    bundle = a.o(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new MetadataBundle(i, bundle);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: ap */
    public MetadataBundle[] newArray(int i) {
        return new MetadataBundle[i];
    }
}
