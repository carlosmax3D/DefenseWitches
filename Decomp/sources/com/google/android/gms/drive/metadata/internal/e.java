package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.drive.metadata.CollectionMetadataField;
import java.util.ArrayList;
import java.util.Collection;

public class e<T extends Parcelable> extends CollectionMetadataField<T> {
    public e(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle, Collection<T> collection) {
        bundle.putParcelableArrayList(getName(), new ArrayList(collection));
    }

    /* access modifiers changed from: protected */
    /* renamed from: i */
    public Collection<T> e(Bundle bundle) {
        return bundle.getParcelableArrayList(getName());
    }
}
