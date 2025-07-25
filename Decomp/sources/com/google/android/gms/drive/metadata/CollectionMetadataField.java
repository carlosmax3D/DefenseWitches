package com.google.android.gms.drive.metadata;

import com.google.android.gms.common.data.DataHolder;
import java.util.Collection;

public abstract class CollectionMetadataField<T> extends MetadataField<Collection<T>> {
    protected CollectionMetadataField(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Collection<T> b(DataHolder dataHolder, int i, int i2) {
        throw new UnsupportedOperationException("Cannot read collections from a dataHolder.");
    }
}
