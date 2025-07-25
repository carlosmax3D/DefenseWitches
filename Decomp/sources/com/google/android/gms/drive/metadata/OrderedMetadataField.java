package com.google.android.gms.drive.metadata;

import java.lang.Comparable;
import java.util.Collection;

public abstract class OrderedMetadataField<T extends Comparable<T>> extends MetadataField<T> {
    protected OrderedMetadataField(String str) {
        super(str);
    }

    protected OrderedMetadataField(String str, Collection<String> collection) {
        super(str, collection);
    }
}
