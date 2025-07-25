package com.google.android.gms.drive.query;

import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.CollectionMetadataField;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.OrderedMetadataField;
import com.google.android.gms.internal.fh;
import com.google.android.gms.internal.fi;
import java.util.Date;

public class SearchableField {
    public static final MetadataField<String> MIME_TYPE = fh.MIME_TYPE;
    public static final OrderedMetadataField<Date> MODIFIED_DATE = fi.rJ;
    public static final CollectionMetadataField<DriveId> PARENTS = fh.PARENTS;
    public static final MetadataField<Boolean> STARRED = fh.STARRED;
    public static final MetadataField<String> TITLE = fh.TITLE;
    public static final MetadataField<Boolean> TRASHED = fh.TRASHED;
    public static final OrderedMetadataField<Date> rM = fi.rM;
}
