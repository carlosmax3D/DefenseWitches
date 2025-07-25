package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.internal.ee;
import com.google.android.gms.internal.eg;

public abstract class b {
    protected final DataHolder nE;
    protected final int nG;
    private final int nH;

    public b(DataHolder dataHolder, int i) {
        this.nE = (DataHolder) eg.f(dataHolder);
        eg.p(i >= 0 && i < dataHolder.getCount());
        this.nG = i;
        this.nH = dataHolder.C(this.nG);
    }

    /* access modifiers changed from: protected */
    public Uri L(String str) {
        return this.nE.parseUri(str, this.nG, this.nH);
    }

    /* access modifiers changed from: protected */
    public boolean M(String str) {
        return this.nE.hasNull(str, this.nG, this.nH);
    }

    /* access modifiers changed from: protected */
    public void a(String str, CharArrayBuffer charArrayBuffer) {
        this.nE.copyToBuffer(str, this.nG, this.nH, charArrayBuffer);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return ee.equal(Integer.valueOf(bVar.nG), Integer.valueOf(this.nG)) && ee.equal(Integer.valueOf(bVar.nH), Integer.valueOf(this.nH)) && bVar.nE == this.nE;
    }

    /* access modifiers changed from: protected */
    public boolean getBoolean(String str) {
        return this.nE.getBoolean(str, this.nG, this.nH);
    }

    /* access modifiers changed from: protected */
    public byte[] getByteArray(String str) {
        return this.nE.getByteArray(str, this.nG, this.nH);
    }

    /* access modifiers changed from: protected */
    public int getInteger(String str) {
        return this.nE.getInteger(str, this.nG, this.nH);
    }

    /* access modifiers changed from: protected */
    public long getLong(String str) {
        return this.nE.getLong(str, this.nG, this.nH);
    }

    /* access modifiers changed from: protected */
    public String getString(String str) {
        return this.nE.getString(str, this.nG, this.nH);
    }

    public boolean hasColumn(String str) {
        return this.nE.hasColumn(str);
    }

    public int hashCode() {
        return ee.hashCode(Integer.valueOf(this.nG), Integer.valueOf(this.nH), this.nE);
    }

    public boolean isDataValid() {
        return !this.nE.isClosed();
    }
}
