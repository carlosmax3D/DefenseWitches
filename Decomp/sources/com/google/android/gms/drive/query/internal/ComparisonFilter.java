package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.query.Filter;

public class ComparisonFilter<T> implements SafeParcelable, Filter {
    public static final a CREATOR = new a();
    final int kg;
    final Operator rR;
    final MetadataBundle rS;
    final MetadataField<T> rT;

    ComparisonFilter(int i, Operator operator, MetadataBundle metadataBundle) {
        this.kg = i;
        this.rR = operator;
        this.rS = metadataBundle;
        this.rT = d.b(metadataBundle);
    }

    public ComparisonFilter(Operator operator, MetadataField<T> metadataField, T t) {
        this(1, operator, MetadataBundle.a(metadataField, t));
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        a.a(this, parcel, i);
    }
}
