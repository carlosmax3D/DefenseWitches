package com.google.android.gms.drive.metadata;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.eg;
import com.tapjoy.TJAdUnitConstants;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class MetadataField<T> {
    private final String rC;
    private final Set<String> rD;

    protected MetadataField(String str) {
        this.rC = (String) eg.b(str, (Object) "fieldName");
        this.rD = Collections.singleton(str);
    }

    protected MetadataField(String str, Collection<String> collection) {
        this.rC = (String) eg.b(str, (Object) "fieldName");
        this.rD = Collections.unmodifiableSet(new HashSet(collection));
    }

    /* access modifiers changed from: protected */
    public abstract void a(Bundle bundle, T t);

    public final void a(DataHolder dataHolder, MetadataBundle metadataBundle, int i, int i2) {
        eg.b(dataHolder, (Object) "dataHolder");
        eg.b(metadataBundle, (Object) TJAdUnitConstants.String.BUNDLE);
        metadataBundle.b(this, c(dataHolder, i, i2));
    }

    public final void a(T t, Bundle bundle) {
        eg.b(bundle, (Object) TJAdUnitConstants.String.BUNDLE);
        if (t == null) {
            bundle.putString(getName(), (String) null);
        } else {
            a(bundle, t);
        }
    }

    /* access modifiers changed from: protected */
    public abstract T b(DataHolder dataHolder, int i, int i2);

    public final T c(DataHolder dataHolder, int i, int i2) {
        for (String hasNull : this.rD) {
            if (dataHolder.hasNull(hasNull, i, i2)) {
                return null;
            }
        }
        return b(dataHolder, i, i2);
    }

    public final Collection<String> cV() {
        return this.rD;
    }

    public final T d(Bundle bundle) {
        eg.b(bundle, (Object) TJAdUnitConstants.String.BUNDLE);
        if (bundle.get(getName()) != null) {
            return e(bundle);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public abstract T e(Bundle bundle);

    public final String getName() {
        return this.rC;
    }

    public String toString() {
        return this.rC;
    }
}
