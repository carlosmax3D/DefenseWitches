package com.google.android.gms.internal;

import java.io.IOException;

public abstract class df {
    protected final dk lx;
    private final String ly;
    private dm lz;

    protected df(String str, String str2) {
        this.ly = str;
        this.lx = new dk(str2);
    }

    public void B(String str) {
    }

    public void a(long j, int i) {
    }

    public final void a(dm dmVar) {
        this.lz = dmVar;
        if (this.lz == null) {
            aT();
        }
    }

    /* access modifiers changed from: protected */
    public final void a(String str, long j, String str2) throws IOException {
        this.lx.a("Sending text message: %s to: %s", str, str2);
        this.lz.a(this.ly, str, j, str2);
    }

    /* access modifiers changed from: protected */
    public final long aS() {
        return this.lz.aR();
    }

    public void aT() {
    }

    public final String getNamespace() {
        return this.ly;
    }
}
