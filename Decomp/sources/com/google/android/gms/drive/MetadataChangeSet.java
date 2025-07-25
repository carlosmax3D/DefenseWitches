package com.google.android.gms.drive;

import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.fh;

public final class MetadataChangeSet {
    private final MetadataBundle qV;

    public static class Builder {
        private final MetadataBundle qV = MetadataBundle.cX();

        public MetadataChangeSet build() {
            return new MetadataChangeSet(this.qV);
        }

        public Builder setMimeType(String str) {
            this.qV.b(fh.MIME_TYPE, str);
            return this;
        }

        public Builder setStarred(boolean z) {
            this.qV.b(fh.STARRED, Boolean.valueOf(z));
            return this;
        }

        public Builder setTitle(String str) {
            this.qV.b(fh.TITLE, str);
            return this;
        }
    }

    private MetadataChangeSet(MetadataBundle metadataBundle) {
        this.qV = MetadataBundle.a(metadataBundle);
    }

    public MetadataBundle cM() {
        return this.qV;
    }

    public String getMimeType() {
        return (String) this.qV.a(fh.MIME_TYPE);
    }

    public String getTitle() {
        return (String) this.qV.a(fh.TITLE);
    }

    public Boolean isStarred() {
        return (Boolean) this.qV.a(fh.STARRED);
    }
}
