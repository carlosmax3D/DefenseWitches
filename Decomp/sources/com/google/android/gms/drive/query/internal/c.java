package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class c implements Parcelable.Creator<FilterHolder> {
    static void a(FilterHolder filterHolder, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, (Parcelable) filterHolder.rU, i, false);
        b.c(parcel, 1000, filterHolder.kg);
        b.a(parcel, 2, (Parcelable) filterHolder.rV, i, false);
        b.a(parcel, 3, (Parcelable) filterHolder.rW, i, false);
        b.a(parcel, 4, (Parcelable) filterHolder.rX, i, false);
        b.a(parcel, 5, (Parcelable) filterHolder.rY, i, false);
        b.D(parcel, o);
    }

    /* renamed from: T */
    public FilterHolder createFromParcel(Parcel parcel) {
        InFilter inFilter = null;
        int n = a.n(parcel);
        int i = 0;
        NotFilter notFilter = null;
        LogicalFilter logicalFilter = null;
        FieldOnlyFilter fieldOnlyFilter = null;
        ComparisonFilter comparisonFilter = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    comparisonFilter = (ComparisonFilter) a.a(parcel, m, ComparisonFilter.CREATOR);
                    break;
                case 2:
                    fieldOnlyFilter = (FieldOnlyFilter) a.a(parcel, m, FieldOnlyFilter.CREATOR);
                    break;
                case 3:
                    logicalFilter = (LogicalFilter) a.a(parcel, m, LogicalFilter.CREATOR);
                    break;
                case 4:
                    notFilter = (NotFilter) a.a(parcel, m, NotFilter.CREATOR);
                    break;
                case 5:
                    inFilter = (InFilter) a.a(parcel, m, InFilter.CREATOR);
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
            return new FilterHolder(i, comparisonFilter, fieldOnlyFilter, logicalFilter, notFilter, inFilter);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: at */
    public FilterHolder[] newArray(int i) {
        return new FilterHolder[i];
    }
}
