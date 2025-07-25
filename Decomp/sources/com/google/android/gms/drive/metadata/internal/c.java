package com.google.android.gms.drive.metadata.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.internal.fh;
import com.google.android.gms.internal.fi;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class c {
    private static Map<String, MetadataField<?>> rE = new HashMap();

    static {
        b(fh.rG);
        b(fh.TITLE);
        b(fh.MIME_TYPE);
        b(fh.STARRED);
        b(fh.TRASHED);
        b(fh.rH);
        b(fh.rI);
        b(fh.PARENTS);
        b(fi.rL);
        b(fi.rJ);
        b(fi.rK);
        b(fi.rM);
    }

    public static MetadataField<?> ac(String str) {
        return rE.get(str);
    }

    private static void b(MetadataField<?> metadataField) {
        if (rE.containsKey(metadataField.getName())) {
            throw new IllegalArgumentException("Duplicate field name registered: " + metadataField.getName());
        }
        rE.put(metadataField.getName(), metadataField);
    }

    public static Collection<MetadataField<?>> cW() {
        return Collections.unmodifiableCollection(rE.values());
    }
}
