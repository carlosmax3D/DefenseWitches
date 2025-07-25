package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.query.Filter;

public class FieldOnlyFilter implements SafeParcelable, Filter {
    public static final Parcelable.Creator<FieldOnlyFilter> CREATOR = new b();
    final int kg;
    final MetadataBundle rS;
    private final MetadataField<?> rT;

    FieldOnlyFilter(int i, MetadataBundle metadataBundle) {
        this.kg = i;
        this.rS = metadataBundle;
        this.rT = d.b(metadataBundle);
    }

    public FieldOnlyFilter(MetadataField<?> metadataField) {
        this(1, MetadataBundle.a(metadataField, null));
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        b.a(this, parcel, i);
    }
}
