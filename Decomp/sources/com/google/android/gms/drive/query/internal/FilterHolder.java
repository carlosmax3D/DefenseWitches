package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.query.Filter;

public class FilterHolder implements SafeParcelable {
    public static final Parcelable.Creator<FilterHolder> CREATOR = new c();
    final int kg;
    final ComparisonFilter<?> rU;
    final FieldOnlyFilter rV;
    final LogicalFilter rW;
    final NotFilter rX;
    final InFilter<?> rY;
    private final Filter rZ;

    FilterHolder(int i, ComparisonFilter<?> comparisonFilter, FieldOnlyFilter fieldOnlyFilter, LogicalFilter logicalFilter, NotFilter notFilter, InFilter<?> inFilter) {
        this.kg = i;
        this.rU = comparisonFilter;
        this.rV = fieldOnlyFilter;
        this.rW = logicalFilter;
        this.rX = notFilter;
        this.rY = inFilter;
        if (this.rU != null) {
            this.rZ = this.rU;
        } else if (this.rV != null) {
            this.rZ = this.rV;
        } else if (this.rW != null) {
            this.rZ = this.rW;
        } else if (this.rX != null) {
            this.rZ = this.rX;
        } else if (this.rY != null) {
            this.rZ = this.rY;
        } else {
            throw new IllegalArgumentException("At least one filter must be set.");
        }
    }

    public FilterHolder(Filter filter) {
        this.kg = 1;
        this.rU = filter instanceof ComparisonFilter ? (ComparisonFilter) filter : null;
        this.rV = filter instanceof FieldOnlyFilter ? (FieldOnlyFilter) filter : null;
        this.rW = filter instanceof LogicalFilter ? (LogicalFilter) filter : null;
        this.rX = filter instanceof NotFilter ? (NotFilter) filter : null;
        this.rY = filter instanceof InFilter ? (InFilter) filter : null;
        if (this.rU == null && this.rV == null && this.rW == null && this.rX == null && this.rY == null) {
            throw new IllegalArgumentException("Invalid filter type or null filter.");
        }
        this.rZ = filter;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        c.a(this, parcel, i);
    }
}
