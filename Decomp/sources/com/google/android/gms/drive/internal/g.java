package com.google.android.gms.drive.internal;

import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class g extends Metadata {
    private final MetadataBundle qN;

    public g(MetadataBundle metadataBundle) {
        this.qN = metadataBundle;
    }

    /* access modifiers changed from: protected */
    public <T> T a(MetadataField<T> metadataField) {
        return this.qN.a(metadataField);
    }

    /* renamed from: cK */
    public Metadata freeze() {
        return new g(MetadataBundle.a(this.qN));
    }

    public boolean isDataValid() {
        return this.qN != null;
    }

    public String toString() {
        return "Metadata [mImpl=" + this.qN + "]";
    }
}
