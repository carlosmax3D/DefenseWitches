package com.google.android.gms.drive.query.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.Set;

class d {
    static MetadataField<?> b(MetadataBundle metadataBundle) {
        Set<MetadataField<?>> cY = metadataBundle.cY();
        if (cY.size() == 1) {
            return cY.iterator().next();
        }
        throw new IllegalArgumentException("bundle should have exactly 1 populated field");
    }
}
