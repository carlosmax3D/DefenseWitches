package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.CollectionMetadataField;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.StringMetadataField;
import com.google.android.gms.drive.metadata.internal.a;
import com.google.android.gms.drive.metadata.internal.e;

public class fh {
    public static final MetadataField<String> MIME_TYPE = new StringMetadataField("mimeType");
    public static final CollectionMetadataField<DriveId> PARENTS = new e("parents");
    public static final MetadataField<Boolean> STARRED = new a("starred");
    public static final MetadataField<String> TITLE = new StringMetadataField("title");
    public static final MetadataField<Boolean> TRASHED = new a("trashed") {
        /* access modifiers changed from: protected */
        /* renamed from: e */
        public Boolean b(DataHolder dataHolder, int i, int i2) {
            return Boolean.valueOf(dataHolder.getInteger(getName(), i, i2) != 0);
        }
    };
    public static final MetadataField<DriveId> rG = fj.rN;
    public static final MetadataField<Boolean> rH = new a("isEditable");
    public static final MetadataField<Boolean> rI = new a("isPinned");
}
