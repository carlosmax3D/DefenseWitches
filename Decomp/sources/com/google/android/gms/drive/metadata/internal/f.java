package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.drive.metadata.MetadataField;
import java.util.Collection;

public abstract class f<T extends Parcelable> extends MetadataField<T> {
    public f(String str, Collection<String> collection) {
        super(str, collection);
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle, T t) {
        bundle.putParcelable(getName(), t);
    }

    /* access modifiers changed from: protected */
    /* renamed from: j */
    public T e(Bundle bundle) {
        return bundle.getParcelable(getName());
    }
}
