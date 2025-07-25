package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class b implements Parcelable.Creator<FieldOnlyFilter> {
    static void a(FieldOnlyFilter fieldOnlyFilter, Parcel parcel, int i) {
        int o = com.google.android.gms.common.internal.safeparcel.b.o(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, fieldOnlyFilter.kg);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 1, (Parcelable) fieldOnlyFilter.rS, i, false);
        com.google.android.gms.common.internal.safeparcel.b.D(parcel, o);
    }

    /* renamed from: S */
    public FieldOnlyFilter createFromParcel(Parcel parcel) {
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
            return new FieldOnlyFilter(i, metadataBundle);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: as */
    public FieldOnlyFilter[] newArray(int i) {
        return new FieldOnlyFilter[i];
    }
}
