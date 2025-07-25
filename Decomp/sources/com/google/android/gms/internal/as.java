package com.google.android.gms.internal;

import android.content.Context;

public final class as {
    private final bb ed;
    private ax fA;
    private final bz fw;
    private final Object fx = new Object();
    private final au fy;
    private boolean fz = false;
    private final Context mContext;

    public as(Context context, bz bzVar, bb bbVar, au auVar) {
        this.mContext = context;
        this.fw = bzVar;
        this.ed = bbVar;
        this.fy = auVar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0080, code lost:
        r4 = r17.fA.b(r18, r20);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x008e, code lost:
        if (r4.ga != 0) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0090, code lost:
        com.google.android.gms.internal.ct.r("Adapter succeeded.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x009b, code lost:
        if (r4.gc == null) goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x009d, code lost:
        com.google.android.gms.internal.cs.iI.post(new com.google.android.gms.internal.as.AnonymousClass1(r17));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        return r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.gms.internal.ay a(long r18, long r20) {
        /*
            r17 = this;
            java.lang.String r4 = "Starting mediation."
            com.google.android.gms.internal.ct.r(r4)
            r0 = r17
            com.google.android.gms.internal.au r4 = r0.fy
            java.util.List<com.google.android.gms.internal.at> r4 = r4.fI
            java.util.Iterator r13 = r4.iterator()
        L_0x000f:
            boolean r4 = r13.hasNext()
            if (r4 == 0) goto L_0x00aa
            java.lang.Object r9 = r13.next()
            com.google.android.gms.internal.at r9 = (com.google.android.gms.internal.at) r9
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Trying mediation network: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = r9.fD
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.google.android.gms.internal.ct.t(r4)
            java.util.List<java.lang.String> r4 = r9.fE
            java.util.Iterator r14 = r4.iterator()
        L_0x0039:
            boolean r4 = r14.hasNext()
            if (r4 == 0) goto L_0x000f
            java.lang.Object r6 = r14.next()
            java.lang.String r6 = (java.lang.String) r6
            r0 = r17
            java.lang.Object r15 = r0.fx
            monitor-enter(r15)
            r0 = r17
            boolean r4 = r0.fz     // Catch:{ all -> 0x0096 }
            if (r4 == 0) goto L_0x0058
            com.google.android.gms.internal.ay r4 = new com.google.android.gms.internal.ay     // Catch:{ all -> 0x0096 }
            r5 = -1
            r4.<init>(r5)     // Catch:{ all -> 0x0096 }
            monitor-exit(r15)     // Catch:{ all -> 0x0096 }
        L_0x0057:
            return r4
        L_0x0058:
            com.google.android.gms.internal.ax r4 = new com.google.android.gms.internal.ax     // Catch:{ all -> 0x0096 }
            r0 = r17
            android.content.Context r5 = r0.mContext     // Catch:{ all -> 0x0096 }
            r0 = r17
            com.google.android.gms.internal.bb r7 = r0.ed     // Catch:{ all -> 0x0096 }
            r0 = r17
            com.google.android.gms.internal.au r8 = r0.fy     // Catch:{ all -> 0x0096 }
            r0 = r17
            com.google.android.gms.internal.bz r10 = r0.fw     // Catch:{ all -> 0x0096 }
            com.google.android.gms.internal.v r10 = r10.hr     // Catch:{ all -> 0x0096 }
            r0 = r17
            com.google.android.gms.internal.bz r11 = r0.fw     // Catch:{ all -> 0x0096 }
            com.google.android.gms.internal.x r11 = r11.em     // Catch:{ all -> 0x0096 }
            r0 = r17
            com.google.android.gms.internal.bz r12 = r0.fw     // Catch:{ all -> 0x0096 }
            com.google.android.gms.internal.cu r12 = r12.ej     // Catch:{ all -> 0x0096 }
            r4.<init>(r5, r6, r7, r8, r9, r10, r11, r12)     // Catch:{ all -> 0x0096 }
            r0 = r17
            r0.fA = r4     // Catch:{ all -> 0x0096 }
            monitor-exit(r15)     // Catch:{ all -> 0x0096 }
            r0 = r17
            com.google.android.gms.internal.ax r4 = r0.fA
            r0 = r18
            r2 = r20
            com.google.android.gms.internal.ay r4 = r4.b(r0, r2)
            int r5 = r4.ga
            if (r5 != 0) goto L_0x0099
            java.lang.String r5 = "Adapter succeeded."
            com.google.android.gms.internal.ct.r(r5)
            goto L_0x0057
        L_0x0096:
            r4 = move-exception
            monitor-exit(r15)     // Catch:{ all -> 0x0096 }
            throw r4
        L_0x0099:
            com.google.android.gms.internal.bc r5 = r4.gc
            if (r5 == 0) goto L_0x0039
            android.os.Handler r5 = com.google.android.gms.internal.cs.iI
            com.google.android.gms.internal.as$1 r6 = new com.google.android.gms.internal.as$1
            r0 = r17
            r6.<init>(r4)
            r5.post(r6)
            goto L_0x0039
        L_0x00aa:
            com.google.android.gms.internal.ay r4 = new com.google.android.gms.internal.ay
            r5 = 1
            r4.<init>(r5)
            goto L_0x0057
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.as.a(long, long):com.google.android.gms.internal.ay");
    }

    public void cancel() {
        synchronized (this.fx) {
            this.fz = true;
            if (this.fA != null) {
                this.fA.cancel();
            }
        }
    }
}
