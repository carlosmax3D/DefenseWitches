package com.google.android.gms.drive;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.metadata.internal.c;
import java.util.ArrayList;

public final class MetadataBuffer extends DataBuffer<Metadata> {
    private static final String[] qS;
    private final String qT;

    private static class a extends Metadata {
        private final DataHolder nE;
        private final int nH;
        private final int qU;

        public a(DataHolder dataHolder, int i) {
            this.nE = dataHolder;
            this.qU = i;
            this.nH = dataHolder.C(i);
        }

        /* access modifiers changed from: protected */
        public <T> T a(MetadataField<T> metadataField) {
            return metadataField.c(this.nE, this.qU, this.nH);
        }

        /* renamed from: cK */
        public Metadata freeze() {
            MetadataBundle cX = MetadataBundle.cX();
            for (MetadataField<?> a : c.cW()) {
                a.a(this.nE, cX, this.qU, this.nH);
            }
            return new b(cX);
        }

        public boolean isDataValid() {
            return !this.nE.isClosed();
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        for (MetadataField<?> cV : c.cW()) {
            arrayList.addAll(cV.cV());
        }
        qS = (String[]) arrayList.toArray(new String[0]);
    }

    public MetadataBuffer(DataHolder dataHolder, String str) {
        super(dataHolder);
        this.qT = str;
    }

    public Metadata get(int i) {
        return new a(this.nE, i);
    }

    public String getNextPageToken() {
        return this.qT;
    }
}
