package com.google.android.gms.internal;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.webkit.WebView;
import com.google.android.gms.internal.bu;
import com.google.android.gms.internal.bw;
import com.google.android.gms.internal.bz;
import com.google.android.gms.internal.cx;
import org.json.JSONException;

public final class bv extends cm implements bw.a, cx.a {
    private final bb ed;
    /* access modifiers changed from: private */
    public final Object fx = new Object();
    private au fy;
    /* access modifiers changed from: private */
    public final cw gv;
    /* access modifiers changed from: private */
    public final bu.a hb;
    private final Object hc = new Object();
    private final bz.a hd;
    private final h he;
    private cm hf;
    /* access modifiers changed from: private */
    public cb hg;
    private boolean hh = false;
    private as hi;
    private ay hj;
    private final Context mContext;

    private static final class a extends Exception {
        private final int hm;

        public a(String str, int i) {
            super(str);
            this.hm = i;
        }

        public int getErrorCode() {
            return this.hm;
        }
    }

    public bv(Context context, bz.a aVar, h hVar, cw cwVar, bb bbVar, bu.a aVar2) {
        this.ed = bbVar;
        this.hb = aVar2;
        this.gv = cwVar;
        this.mContext = context;
        this.hd = aVar;
        this.he = hVar;
    }

    private x a(bz bzVar) throws a {
        if (this.hg.hB == null) {
            throw new a("The ad response must specify one of the supported ad sizes.", 0);
        }
        String[] split = this.hg.hB.split("x");
        if (split.length != 2) {
            throw new a("Could not parse the ad size from the ad response: " + this.hg.hB, 0);
        }
        try {
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            for (x xVar : bzVar.em.eH) {
                float f = this.mContext.getResources().getDisplayMetrics().density;
                int i = xVar.width == -1 ? (int) (((float) xVar.widthPixels) / f) : xVar.width;
                int i2 = xVar.height == -2 ? (int) (((float) xVar.heightPixels) / f) : xVar.height;
                if (parseInt == i && parseInt2 == i2) {
                    return new x(xVar, bzVar.em.eH);
                }
            }
            throw new a("The ad size from the ad response was not one of the requested sizes: " + this.hg.hB, 0);
        } catch (NumberFormatException e) {
            throw new a("Could not parse the ad size from the ad response: " + this.hg.hB, 0);
        }
    }

    private void a(bz bzVar, long j) throws a {
        synchronized (this.hc) {
            this.hi = new as(this.mContext, bzVar, this.ed, this.fy);
        }
        this.hj = this.hi.a(j, 60000);
        switch (this.hj.ga) {
            case 0:
                return;
            case 1:
                throw new a("No fill from any mediation ad networks.", 3);
            default:
                throw new a("Unexpected mediation result: " + this.hj.ga, 0);
        }
    }

    private void aj() throws a {
        if (this.hg.errorCode != -3) {
            if (TextUtils.isEmpty(this.hg.hw)) {
                throw new a("No fill from ad server.", 3);
            } else if (this.hg.hy) {
                try {
                    this.fy = new au(this.hg.hw);
                } catch (JSONException e) {
                    throw new a("Could not parse mediation config: " + this.hg.hw, 0);
                }
            }
        }
    }

    private void b(long j) throws a {
        cs.iI.post(new Runnable() {
            /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
                return;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r7 = this;
                    com.google.android.gms.internal.bv r0 = com.google.android.gms.internal.bv.this
                    java.lang.Object r6 = r0.fx
                    monitor-enter(r6)
                    com.google.android.gms.internal.bv r0 = com.google.android.gms.internal.bv.this     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.cb r0 = r0.hg     // Catch:{ all -> 0x005f }
                    int r0 = r0.errorCode     // Catch:{ all -> 0x005f }
                    r1 = -2
                    if (r0 == r1) goto L_0x0014
                    monitor-exit(r6)     // Catch:{ all -> 0x005f }
                L_0x0013:
                    return
                L_0x0014:
                    com.google.android.gms.internal.bv r0 = com.google.android.gms.internal.bv.this     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.cw r0 = r0.gv     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.cx r0 = r0.aC()     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.bv r1 = com.google.android.gms.internal.bv.this     // Catch:{ all -> 0x005f }
                    r0.a((com.google.android.gms.internal.cx.a) r1)     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.bv r0 = com.google.android.gms.internal.bv.this     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.cb r0 = r0.hg     // Catch:{ all -> 0x005f }
                    int r0 = r0.errorCode     // Catch:{ all -> 0x005f }
                    r1 = -3
                    if (r0 != r1) goto L_0x0062
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x005f }
                    r0.<init>()     // Catch:{ all -> 0x005f }
                    java.lang.String r1 = "Loading URL in WebView: "
                    java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.bv r1 = com.google.android.gms.internal.bv.this     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.cb r1 = r1.hg     // Catch:{ all -> 0x005f }
                    java.lang.String r1 = r1.gL     // Catch:{ all -> 0x005f }
                    java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x005f }
                    java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.ct.u(r0)     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.bv r0 = com.google.android.gms.internal.bv.this     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.cw r0 = r0.gv     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.bv r1 = com.google.android.gms.internal.bv.this     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.cb r1 = r1.hg     // Catch:{ all -> 0x005f }
                    java.lang.String r1 = r1.gL     // Catch:{ all -> 0x005f }
                    r0.loadUrl(r1)     // Catch:{ all -> 0x005f }
                L_0x005d:
                    monitor-exit(r6)     // Catch:{ all -> 0x005f }
                    goto L_0x0013
                L_0x005f:
                    r0 = move-exception
                    monitor-exit(r6)     // Catch:{ all -> 0x005f }
                    throw r0
                L_0x0062:
                    java.lang.String r0 = "Loading HTML in WebView."
                    com.google.android.gms.internal.ct.u(r0)     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.bv r0 = com.google.android.gms.internal.bv.this     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.cw r0 = r0.gv     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.bv r1 = com.google.android.gms.internal.bv.this     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.cb r1 = r1.hg     // Catch:{ all -> 0x005f }
                    java.lang.String r1 = r1.gL     // Catch:{ all -> 0x005f }
                    java.lang.String r1 = com.google.android.gms.internal.co.o(r1)     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.bv r2 = com.google.android.gms.internal.bv.this     // Catch:{ all -> 0x005f }
                    com.google.android.gms.internal.cb r2 = r2.hg     // Catch:{ all -> 0x005f }
                    java.lang.String r2 = r2.hw     // Catch:{ all -> 0x005f }
                    java.lang.String r3 = "text/html"
                    java.lang.String r4 = "UTF-8"
                    r5 = 0
                    r0.loadDataWithBaseURL(r1, r2, r3, r4, r5)     // Catch:{ all -> 0x005f }
                    goto L_0x005d
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.bv.AnonymousClass3.run():void");
            }
        });
        d(j);
    }

    private void c(long j) throws a {
        while (e(j)) {
            if (this.hg != null) {
                synchronized (this.hc) {
                    this.hf = null;
                }
                if (this.hg.errorCode != -2 && this.hg.errorCode != -3) {
                    throw new a("There was a problem getting an ad response. ErrorCode: " + this.hg.errorCode, this.hg.errorCode);
                }
                return;
            }
        }
        throw new a("Timed out waiting for ad response.", 2);
    }

    private void d(long j) throws a {
        while (e(j)) {
            if (this.hh) {
                return;
            }
        }
        throw new a("Timed out waiting for WebView to finish loading.", 2);
    }

    private boolean e(long j) throws a {
        long elapsedRealtime = 60000 - (SystemClock.elapsedRealtime() - j);
        if (elapsedRealtime <= 0) {
            return false;
        }
        try {
            this.fx.wait(elapsedRealtime);
            return true;
        } catch (InterruptedException e) {
            throw new a("Ad request cancelled.", -1);
        }
    }

    public void a(cb cbVar) {
        synchronized (this.fx) {
            ct.r("Received ad response.");
            this.hg = cbVar;
            this.fx.notify();
        }
    }

    public void a(cw cwVar) {
        synchronized (this.fx) {
            ct.r("WebView finished loading.");
            this.hh = true;
            this.fx.notify();
        }
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
        	at java.util.ArrayList.get(ArrayList.java:435)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public void ai() {
        /*
            r25 = this;
            r0 = r25
            java.lang.Object r0 = r0.fx
            r24 = r0
            monitor-enter(r24)
            java.lang.String r2 = "AdLoaderBackgroundTask started."
            com.google.android.gms.internal.ct.r(r2)     // Catch:{ all -> 0x0142 }
            r0 = r25
            com.google.android.gms.internal.h r2 = r0.he     // Catch:{ all -> 0x0142 }
            com.google.android.gms.internal.d r2 = r2.g()     // Catch:{ all -> 0x0142 }
            r0 = r25
            android.content.Context r3 = r0.mContext     // Catch:{ all -> 0x0142 }
            java.lang.String r2 = r2.a((android.content.Context) r3)     // Catch:{ all -> 0x0142 }
            com.google.android.gms.internal.bz r12 = new com.google.android.gms.internal.bz     // Catch:{ all -> 0x0142 }
            r0 = r25
            com.google.android.gms.internal.bz$a r3 = r0.hd     // Catch:{ all -> 0x0142 }
            r12.<init>(r3, r2)     // Catch:{ all -> 0x0142 }
            r3 = 0
            r6 = -2
            long r4 = android.os.SystemClock.elapsedRealtime()     // Catch:{ a -> 0x0050 }
            r0 = r25
            android.content.Context r2 = r0.mContext     // Catch:{ a -> 0x0050 }
            r0 = r25
            com.google.android.gms.internal.cm r2 = com.google.android.gms.internal.bw.a(r2, r12, r0)     // Catch:{ a -> 0x0050 }
            r0 = r25
            java.lang.Object r7 = r0.hc     // Catch:{ a -> 0x0050 }
            monitor-enter(r7)     // Catch:{ a -> 0x0050 }
            r0 = r25
            r0.hf = r2     // Catch:{ all -> 0x004d }
            r0 = r25
            com.google.android.gms.internal.cm r2 = r0.hf     // Catch:{ all -> 0x004d }
            if (r2 != 0) goto L_0x0116
            com.google.android.gms.internal.bv$a r2 = new com.google.android.gms.internal.bv$a     // Catch:{ all -> 0x004d }
            java.lang.String r4 = "Could not start the ad request service."
            r5 = 0
            r2.<init>(r4, r5)     // Catch:{ all -> 0x004d }
            throw r2     // Catch:{ all -> 0x004d }
        L_0x004d:
            r2 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x004d }
            throw r2     // Catch:{ a -> 0x0050 }
        L_0x0050:
            r2 = move-exception
            int r6 = r2.getErrorCode()     // Catch:{ all -> 0x0142 }
            r4 = 3
            if (r6 == r4) goto L_0x005b
            r4 = -1
            if (r6 != r4) goto L_0x0145
        L_0x005b:
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x0142 }
            com.google.android.gms.internal.ct.t(r2)     // Catch:{ all -> 0x0142 }
        L_0x0062:
            com.google.android.gms.internal.cb r2 = new com.google.android.gms.internal.cb     // Catch:{ all -> 0x0142 }
            r2.<init>(r6)     // Catch:{ all -> 0x0142 }
            r0 = r25
            r0.hg = r2     // Catch:{ all -> 0x0142 }
            android.os.Handler r2 = com.google.android.gms.internal.cs.iI     // Catch:{ all -> 0x0142 }
            com.google.android.gms.internal.bv$1 r4 = new com.google.android.gms.internal.bv$1     // Catch:{ all -> 0x0142 }
            r0 = r25
            r4.<init>()     // Catch:{ all -> 0x0142 }
            r2.post(r4)     // Catch:{ all -> 0x0142 }
            r21 = r3
        L_0x0079:
            com.google.android.gms.internal.cj r2 = new com.google.android.gms.internal.cj     // Catch:{ all -> 0x0142 }
            com.google.android.gms.internal.v r3 = r12.hr     // Catch:{ all -> 0x0142 }
            r0 = r25
            com.google.android.gms.internal.cw r4 = r0.gv     // Catch:{ all -> 0x0142 }
            r0 = r25
            com.google.android.gms.internal.cb r5 = r0.hg     // Catch:{ all -> 0x0142 }
            java.util.List<java.lang.String> r5 = r5.fK     // Catch:{ all -> 0x0142 }
            r0 = r25
            com.google.android.gms.internal.cb r7 = r0.hg     // Catch:{ all -> 0x0142 }
            java.util.List<java.lang.String> r7 = r7.fL     // Catch:{ all -> 0x0142 }
            r0 = r25
            com.google.android.gms.internal.cb r8 = r0.hg     // Catch:{ all -> 0x0142 }
            java.util.List<java.lang.String> r8 = r8.hA     // Catch:{ all -> 0x0142 }
            r0 = r25
            com.google.android.gms.internal.cb r9 = r0.hg     // Catch:{ all -> 0x0142 }
            int r9 = r9.orientation     // Catch:{ all -> 0x0142 }
            r0 = r25
            com.google.android.gms.internal.cb r10 = r0.hg     // Catch:{ all -> 0x0142 }
            long r10 = r10.fO     // Catch:{ all -> 0x0142 }
            java.lang.String r12 = r12.hu     // Catch:{ all -> 0x0142 }
            r0 = r25
            com.google.android.gms.internal.cb r13 = r0.hg     // Catch:{ all -> 0x0142 }
            boolean r13 = r13.hy     // Catch:{ all -> 0x0142 }
            r0 = r25
            com.google.android.gms.internal.ay r14 = r0.hj     // Catch:{ all -> 0x0142 }
            if (r14 == 0) goto L_0x014e
            r0 = r25
            com.google.android.gms.internal.ay r14 = r0.hj     // Catch:{ all -> 0x0142 }
            com.google.android.gms.internal.at r14 = r14.gb     // Catch:{ all -> 0x0142 }
        L_0x00b3:
            r0 = r25
            com.google.android.gms.internal.ay r15 = r0.hj     // Catch:{ all -> 0x0142 }
            if (r15 == 0) goto L_0x0151
            r0 = r25
            com.google.android.gms.internal.ay r15 = r0.hj     // Catch:{ all -> 0x0142 }
            com.google.android.gms.internal.bc r15 = r15.gc     // Catch:{ all -> 0x0142 }
        L_0x00bf:
            r0 = r25
            com.google.android.gms.internal.ay r0 = r0.hj     // Catch:{ all -> 0x0142 }
            r16 = r0
            if (r16 == 0) goto L_0x0154
            r0 = r25
            com.google.android.gms.internal.ay r0 = r0.hj     // Catch:{ all -> 0x0142 }
            r16 = r0
            r0 = r16
            java.lang.String r0 = r0.gd     // Catch:{ all -> 0x0142 }
            r16 = r0
        L_0x00d3:
            r0 = r25
            com.google.android.gms.internal.au r0 = r0.fy     // Catch:{ all -> 0x0142 }
            r17 = r0
            r0 = r25
            com.google.android.gms.internal.ay r0 = r0.hj     // Catch:{ all -> 0x0142 }
            r18 = r0
            if (r18 == 0) goto L_0x0158
            r0 = r25
            com.google.android.gms.internal.ay r0 = r0.hj     // Catch:{ all -> 0x0142 }
            r18 = r0
            r0 = r18
            com.google.android.gms.internal.aw r0 = r0.ge     // Catch:{ all -> 0x0142 }
            r18 = r0
        L_0x00ed:
            r0 = r25
            com.google.android.gms.internal.cb r0 = r0.hg     // Catch:{ all -> 0x0142 }
            r19 = r0
            r0 = r19
            long r0 = r0.hz     // Catch:{ all -> 0x0142 }
            r19 = r0
            r0 = r25
            com.google.android.gms.internal.cb r0 = r0.hg     // Catch:{ all -> 0x0142 }
            r22 = r0
            r0 = r22
            long r0 = r0.hx     // Catch:{ all -> 0x0142 }
            r22 = r0
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r12, r13, r14, r15, r16, r17, r18, r19, r21, r22)     // Catch:{ all -> 0x0142 }
            android.os.Handler r3 = com.google.android.gms.internal.cs.iI     // Catch:{ all -> 0x0142 }
            com.google.android.gms.internal.bv$2 r4 = new com.google.android.gms.internal.bv$2     // Catch:{ all -> 0x0142 }
            r0 = r25
            r4.<init>(r2)     // Catch:{ all -> 0x0142 }
            r3.post(r4)     // Catch:{ all -> 0x0142 }
            monitor-exit(r24)     // Catch:{ all -> 0x0142 }
            return
        L_0x0116:
            monitor-exit(r7)     // Catch:{ all -> 0x004d }
            r0 = r25
            r0.c((long) r4)     // Catch:{ a -> 0x0050 }
            r25.aj()     // Catch:{ a -> 0x0050 }
            com.google.android.gms.internal.x r2 = r12.em     // Catch:{ a -> 0x0050 }
            com.google.android.gms.internal.x[] r2 = r2.eH     // Catch:{ a -> 0x0050 }
            if (r2 == 0) goto L_0x012b
            r0 = r25
            com.google.android.gms.internal.x r3 = r0.a((com.google.android.gms.internal.bz) r12)     // Catch:{ a -> 0x0050 }
        L_0x012b:
            r0 = r25
            com.google.android.gms.internal.cb r2 = r0.hg     // Catch:{ a -> 0x0050 }
            boolean r2 = r2.hy     // Catch:{ a -> 0x0050 }
            if (r2 == 0) goto L_0x013c
            r0 = r25
            r0.a(r12, r4)     // Catch:{ a -> 0x0050 }
        L_0x0138:
            r21 = r3
            goto L_0x0079
        L_0x013c:
            r0 = r25
            r0.b((long) r4)     // Catch:{ a -> 0x0050 }
            goto L_0x0138
        L_0x0142:
            r2 = move-exception
            monitor-exit(r24)     // Catch:{ all -> 0x0142 }
            throw r2
        L_0x0145:
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x0142 }
            com.google.android.gms.internal.ct.v(r2)     // Catch:{ all -> 0x0142 }
            goto L_0x0062
        L_0x014e:
            r14 = 0
            goto L_0x00b3
        L_0x0151:
            r15 = 0
            goto L_0x00bf
        L_0x0154:
            r16 = 0
            goto L_0x00d3
        L_0x0158:
            r18 = 0
            goto L_0x00ed
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.bv.ai():void");
    }

    public void onStop() {
        synchronized (this.hc) {
            if (this.hf != null) {
                this.hf.cancel();
            }
            this.gv.stopLoading();
            co.a((WebView) this.gv);
            if (this.hi != null) {
                this.hi.cancel();
            }
        }
    }
}
