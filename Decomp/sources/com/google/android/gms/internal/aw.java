package com.google.android.gms.internal;

import com.google.android.gms.internal.ay;
import com.google.android.gms.internal.bd;

public final class aw extends bd.a {
    private ay.a fP;
    private av fQ;
    private final Object fx = new Object();

    public void a(av avVar) {
        synchronized (this.fx) {
            this.fQ = avVar;
        }
    }

    public void a(ay.a aVar) {
        synchronized (this.fx) {
            this.fP = aVar;
        }
    }

    public void onAdClosed() {
        synchronized (this.fx) {
            if (this.fQ != null) {
                this.fQ.D();
            }
        }
    }

    public void onAdFailedToLoad(int i) {
        synchronized (this.fx) {
            if (this.fP != null) {
                this.fP.f(i == 3 ? 1 : 2);
                this.fP = null;
            }
        }
    }

    public void onAdLeftApplication() {
        synchronized (this.fx) {
            if (this.fQ != null) {
                this.fQ.E();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onAdLoaded() {
        /*
            r3 = this;
            java.lang.Object r1 = r3.fx
            monitor-enter(r1)
            com.google.android.gms.internal.ay$a r0 = r3.fP     // Catch:{ all -> 0x001d }
            if (r0 == 0) goto L_0x0012
            com.google.android.gms.internal.ay$a r0 = r3.fP     // Catch:{ all -> 0x001d }
            r2 = 0
            r0.f(r2)     // Catch:{ all -> 0x001d }
            r0 = 0
            r3.fP = r0     // Catch:{ all -> 0x001d }
            monitor-exit(r1)     // Catch:{ all -> 0x001d }
        L_0x0011:
            return
        L_0x0012:
            com.google.android.gms.internal.av r0 = r3.fQ     // Catch:{ all -> 0x001d }
            if (r0 == 0) goto L_0x001b
            com.google.android.gms.internal.av r0 = r3.fQ     // Catch:{ all -> 0x001d }
            r0.G()     // Catch:{ all -> 0x001d }
        L_0x001b:
            monitor-exit(r1)     // Catch:{ all -> 0x001d }
            goto L_0x0011
        L_0x001d:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001d }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.aw.onAdLoaded():void");
    }

    public void onAdOpened() {
        synchronized (this.fx) {
            if (this.fQ != null) {
                this.fQ.F();
            }
        }
    }

    public void w() {
        synchronized (this.fx) {
            if (this.fQ != null) {
                this.fQ.C();
            }
        }
    }
}
