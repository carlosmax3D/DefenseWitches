package com.google.android.gms.drive.metadata;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;

public final class StringMetadataField extends MetadataField<String> {
    public StringMetadataField(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle, String str) {
        bundle.putString(getName(), str);
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public String b(DataHolder dataHolder, int i, int i2) {
        return dataHolder.getString(getName(), i, i2);
    }

    /* access modifiers changed from: protected */
    /* renamed from: f */
    public String e(Bundle bundle) {
        return bundle.getString(getName());
    }
}
