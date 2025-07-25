package com.google.android.gms.drive;

import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.internal.fh;
import com.google.android.gms.internal.fi;
import java.util.Date;

public abstract class Metadata implements Freezable<Metadata> {
    /* access modifiers changed from: protected */
    public abstract <T> T a(MetadataField<T> metadataField);

    public Date getCreatedDate() {
        return (Date) a(fi.rL);
    }

    public DriveId getDriveId() {
        return (DriveId) a(fh.rG);
    }

    public String getMimeType() {
        return (String) a(fh.MIME_TYPE);
    }

    public Date getModifiedByMeDate() {
        return (Date) a(fi.rK);
    }

    public Date getModifiedDate() {
        return (Date) a(fi.rJ);
    }

    public Date getSharedWithMeDate() {
        return (Date) a(fi.rM);
    }

    public String getTitle() {
        return (String) a(fh.TITLE);
    }

    public boolean isEditable() {
        Boolean bool = (Boolean) a(fh.rH);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isFolder() {
        return DriveFolder.MIME_TYPE.equals(getMimeType());
    }

    public boolean isStarred() {
        Boolean bool = (Boolean) a(fh.STARRED);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isTrashed() {
        Boolean bool = (Boolean) a(fh.TRASHED);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }
}
