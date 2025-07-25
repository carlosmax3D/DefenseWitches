package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class e implements Parcelable.Creator<InFilter> {
    static void a(InFilter inFilter, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1000, inFilter.kg);
        b.a(parcel, 1, (Parcelable) inFilter.rS, i, false);
        b.D(parcel, o);
    }

    /* renamed from: U */
    public InFilter createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        MetadataBundle metadataBundle = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    metadataBundle = (MetadataBundle) a.a(parcel, m, MetadataBundle.CREATOR);
                    break;
                case 1000:
                    i = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new InFilter(i, metadataBundle);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: au */
    public InFilter[] newArray(int i) {
        return new InFilter[i];
    }
}
