package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class a implements Parcelable.Creator<ComparisonFilter> {
    static void a(ComparisonFilter comparisonFilter, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1000, comparisonFilter.kg);
        b.a(parcel, 1, (Parcelable) comparisonFilter.rR, i, false);
        b.a(parcel, 2, (Parcelable) comparisonFilter.rS, i, false);
        b.D(parcel, o);
    }

    /* renamed from: R */
    public ComparisonFilter createFromParcel(Parcel parcel) {
        MetadataBundle metadataBundle;
        Operator operator;
        int i;
        MetadataBundle metadataBundle2 = null;
        int n = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
        int i2 = 0;
        Operator operator2 = null;
        while (parcel.dataPosition() < n) {
            int m = com.google.android.gms.common.internal.safeparcel.a.m(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.M(m)) {
                case 1:
                    i = i2;
                    Operator operator3 = (Operator) com.google.android.gms.common.internal.safeparcel.a.a(parcel, m, Operator.CREATOR);
                    metadataBundle = metadataBundle2;
                    operator = operator3;
                    break;
                case 2:
                    metadataBundle = (MetadataBundle) com.google.android.gms.common.internal.safeparcel.a.a(parcel, m, MetadataBundle.CREATOR);
                    operator = operator2;
                    i = i2;
                    break;
                case 1000:
                    MetadataBundle metadataBundle3 = metadataBundle2;
                    operator = operator2;
                    i = com.google.android.gms.common.internal.safeparcel.a.g(parcel, m);
                    metadataBundle = metadataBundle3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, m);
                    metadataBundle = metadataBundle2;
                    operator = operator2;
                    i = i2;
                    break;
            }
            i2 = i;
            operator2 = operator;
            metadataBundle2 = metadataBundle;
        }
        if (parcel.dataPosition() == n) {
            return new ComparisonFilter(i2, operator2, metadataBundle2);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: ar */
    public ComparisonFilter[] newArray(int i) {
        return new ComparisonFilter[i];
    }
}
