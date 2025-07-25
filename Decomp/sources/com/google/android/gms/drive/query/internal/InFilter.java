package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.metadata.CollectionMetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.query.Filter;
import java.util.Collections;

public class InFilter<T> implements SafeParcelable, Filter {
    public static final e CREATOR = new e();
    final int kg;
    final MetadataBundle rS;
    private final CollectionMetadataField<T> sa;

    InFilter(int i, MetadataBundle metadataBundle) {
        this.kg = i;
        this.rS = metadataBundle;
        this.sa = (CollectionMetadataField) d.b(metadataBundle);
    }

    public InFilter(CollectionMetadataField<T> collectionMetadataField, T t) {
        this(1, MetadataBundle.a(collectionMetadataField, Collections.singleton(t)));
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        e.a(this, parcel, i);
    }
}
