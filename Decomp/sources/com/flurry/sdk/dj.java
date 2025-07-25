package com.flurry.sdk;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import com.flurry.android.impl.analytics.FlurryAnalyticsModule;
import com.flurry.sdk.cx;
import com.flurry.sdk.dm;
import com.flurry.sdk.dq;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;
import jp.stargarage.g2metrics.BuildConfig;

public class dj implements dm.b, dq.a {
    static int a = 100;
    static int b = 10;
    static int c = 1000;
    static int d = 160000;
    static int e = 50;
    static int f = 20;
    private static final String g = dj.class.getSimpleName();
    private String A = BuildConfig.FLAVOR;
    private String B = BuildConfig.FLAVOR;
    private byte C = -1;
    private Location D;
    private boolean E;
    private String F;
    private byte G;
    private long H;
    private long I;
    private final Map<String, cx.a> J = new HashMap();
    private final List<db> K = new ArrayList();
    private boolean L;
    private int M;
    private final List<da> N = new ArrayList();
    private int O;
    private int P;
    private final cy Q = new cy();
    private Map<String, List<String>> R;
    private dm S;
    private int T;
    private boolean U = false;
    private final AtomicInteger h = new AtomicInteger(0);
    private final AtomicInteger i = new AtomicInteger(0);
    private final AtomicInteger j = new AtomicInteger(0);
    private final String k;
    private String l;
    private WeakReference<Context> m;
    private File n = null;
    private List<dh> o;
    private final Map<dr, ByteBuffer> p = new HashMap();
    private boolean q;
    private long r;
    /* access modifiers changed from: private */
    public List<dh> s = new ArrayList();
    /* access modifiers changed from: private */
    public AdvertisingIdClient.Info t;
    /* access modifiers changed from: private */
    public byte[] u;
    /* access modifiers changed from: private */
    public String v;
    private long w;
    private long x;
    private long y;
    private long z;

    public dj(String str) {
        eo.a(4, g, "Creating new Flurry session");
        this.k = str;
        this.m = new WeakReference<>((Object) null);
    }

    private void A() {
        try {
            eo.a(3, g, "generating agent report");
            dc dcVar = new dc(this.k, this.l, this.q, o(), this.r, this.w, this.s, p(), this.Q.a(false), a(), cx.a().b(), System.currentTimeMillis());
            this.o = new ArrayList(this.s);
            if (dcVar == null || dcVar.a() == null) {
                eo.d(g, "Error generating report");
                return;
            }
            eo.a(3, g, "generated report of size " + dcVar.a().length + " with " + this.s.size() + " reports.");
            a(dcVar.a());
            this.s.removeAll(this.o);
            this.o = null;
            B();
        } catch (Throwable th) {
            eo.a(6, g, BuildConfig.FLAVOR, th);
        }
    }

    /* access modifiers changed from: private */
    public synchronized void B() {
        if (!fd.a(this.n)) {
            eo.d(g, "Error persisting report: could not create directory");
        } else {
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(this.n));
                dk dkVar = new dk();
                dkVar.a(this.q);
                dkVar.a(this.r);
                dkVar.a(this.s);
                dkVar.a(dataOutputStream, this.k, i());
            } catch (Exception e2) {
                eo.b(g, "Error saving persistent data", (Throwable) e2);
            }
        }
        return;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x005b A[Catch:{ Exception -> 0x0073, all -> 0x0084 }] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x006a A[Catch:{ Exception -> 0x0073, all -> 0x0084 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void C() {
        /*
            r8 = this;
            r2 = 0
            r4 = 0
            monitor-enter(r8)
            r0 = 4
            java.lang.String r1 = g     // Catch:{ all -> 0x008e }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x008e }
            r3.<init>()     // Catch:{ all -> 0x008e }
            java.lang.String r5 = "Loading persistent data: "
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ all -> 0x008e }
            java.io.File r5 = r8.n     // Catch:{ all -> 0x008e }
            java.lang.String r5 = r5.getAbsolutePath()     // Catch:{ all -> 0x008e }
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ all -> 0x008e }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x008e }
            com.flurry.sdk.eo.a((int) r0, (java.lang.String) r1, (java.lang.String) r3)     // Catch:{ all -> 0x008e }
            java.io.File r0 = r8.n     // Catch:{ all -> 0x008e }
            boolean r0 = r0.exists()     // Catch:{ all -> 0x008e }
            if (r0 == 0) goto L_0x0091
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0073, all -> 0x0084 }
            java.io.File r0 = r8.n     // Catch:{ Exception -> 0x0073, all -> 0x0084 }
            r3.<init>(r0)     // Catch:{ Exception -> 0x0073, all -> 0x0084 }
            java.io.DataInputStream r1 = new java.io.DataInputStream     // Catch:{ Exception -> 0x00a3, all -> 0x009b }
            r1.<init>(r3)     // Catch:{ Exception -> 0x00a3, all -> 0x009b }
            com.flurry.sdk.dk r0 = new com.flurry.sdk.dk     // Catch:{ Exception -> 0x00a7, all -> 0x009e }
            r0.<init>()     // Catch:{ Exception -> 0x00a7, all -> 0x009e }
            java.lang.String r2 = r8.k     // Catch:{ Exception -> 0x00a7, all -> 0x009e }
            r0.a((java.io.DataInputStream) r1, (java.lang.String) r2)     // Catch:{ Exception -> 0x00a7, all -> 0x009e }
            boolean r2 = r0.a()     // Catch:{ Exception -> 0x00a7, all -> 0x009e }
            r8.q = r2     // Catch:{ Exception -> 0x00a7, all -> 0x009e }
            long r6 = r0.c()     // Catch:{ Exception -> 0x00a7, all -> 0x009e }
            r8.r = r6     // Catch:{ Exception -> 0x00a7, all -> 0x009e }
            java.util.List r0 = r0.b()     // Catch:{ Exception -> 0x00a7, all -> 0x009e }
            r8.s = r0     // Catch:{ Exception -> 0x00a7, all -> 0x009e }
            r0 = 1
            com.flurry.sdk.fe.a((java.io.Closeable) r1)     // Catch:{ all -> 0x008e }
            com.flurry.sdk.fe.a((java.io.Closeable) r3)     // Catch:{ all -> 0x008e }
        L_0x0059:
            if (r0 != 0) goto L_0x0068
            r1 = 3
            java.lang.String r2 = g     // Catch:{ all -> 0x008e }
            java.lang.String r3 = "Deleting agent cache file"
            com.flurry.sdk.eo.a((int) r1, (java.lang.String) r2, (java.lang.String) r3)     // Catch:{ all -> 0x008e }
            java.io.File r1 = r8.n     // Catch:{ all -> 0x008e }
            r1.delete()     // Catch:{ all -> 0x008e }
        L_0x0068:
            if (r0 != 0) goto L_0x0071
            r0 = 0
            r8.q = r0     // Catch:{ all -> 0x008e }
            long r0 = r8.w     // Catch:{ all -> 0x008e }
            r8.r = r0     // Catch:{ all -> 0x008e }
        L_0x0071:
            monitor-exit(r8)
            return
        L_0x0073:
            r0 = move-exception
            r1 = r2
        L_0x0075:
            java.lang.String r3 = g     // Catch:{ all -> 0x00a0 }
            java.lang.String r5 = "Error loading persistent data"
            com.flurry.sdk.eo.b((java.lang.String) r3, (java.lang.String) r5, (java.lang.Throwable) r0)     // Catch:{ all -> 0x00a0 }
            com.flurry.sdk.fe.a((java.io.Closeable) r1)     // Catch:{ all -> 0x008e }
            com.flurry.sdk.fe.a((java.io.Closeable) r2)     // Catch:{ all -> 0x008e }
            r0 = r4
            goto L_0x0059
        L_0x0084:
            r0 = move-exception
            r1 = r2
            r3 = r2
        L_0x0087:
            com.flurry.sdk.fe.a((java.io.Closeable) r1)     // Catch:{ all -> 0x008e }
            com.flurry.sdk.fe.a((java.io.Closeable) r3)     // Catch:{ all -> 0x008e }
            throw r0     // Catch:{ all -> 0x008e }
        L_0x008e:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        L_0x0091:
            r0 = 4
            java.lang.String r1 = g     // Catch:{ all -> 0x008e }
            java.lang.String r2 = "Agent cache file doesn't exist."
            com.flurry.sdk.eo.a((int) r0, (java.lang.String) r1, (java.lang.String) r2)     // Catch:{ all -> 0x008e }
            r0 = r4
            goto L_0x0068
        L_0x009b:
            r0 = move-exception
            r1 = r2
            goto L_0x0087
        L_0x009e:
            r0 = move-exception
            goto L_0x0087
        L_0x00a0:
            r0 = move-exception
            r3 = r2
            goto L_0x0087
        L_0x00a3:
            r0 = move-exception
            r1 = r2
            r2 = r3
            goto L_0x0075
        L_0x00a7:
            r0 = move-exception
            r2 = r3
            goto L_0x0075
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.dj.C():void");
    }

    private void D() {
        this.T++;
    }

    private void E() {
        if (this.T > 0) {
            this.T--;
        }
    }

    private String F() {
        return ".flurryagent." + Integer.toString(this.k.hashCode(), 16);
    }

    private int G() {
        return this.h.incrementAndGet();
    }

    private int H() {
        return this.i.incrementAndGet();
    }

    private synchronized void a(long j2) {
        for (db next : this.K) {
            if (next.a() && !next.b()) {
                next.a(j2);
            }
        }
    }

    private void a(dr drVar, ByteBuffer byteBuffer) {
        synchronized (this.p) {
            this.p.put(drVar, byteBuffer);
        }
    }

    private void a(byte[] bArr) {
        FlurryAnalyticsModule.getInstance().a().b(bArr, this.k, BuildConfig.FLAVOR + dn.a().b());
    }

    private Map<String, List<String>> d(Context context) {
        Bundle extras;
        if (!(context instanceof Activity) || (extras = ((Activity) context).getIntent().getExtras()) == null) {
            return null;
        }
        eo.a(3, g, "Launch Options Bundle is present " + extras.toString());
        HashMap hashMap = new HashMap();
        for (String str : extras.keySet()) {
            if (str != null) {
                Object obj = extras.get(str);
                String obj2 = obj != null ? obj.toString() : "null";
                hashMap.put(str, new ArrayList(Arrays.asList(new String[]{obj2})));
                eo.a(3, g, "Launch options Key: " + str + ". Its value: " + obj2);
            }
        }
        return hashMap;
    }

    private void u() {
        dq a2 = dp.a();
        this.G = ((Byte) a2.a("Gender")).byteValue();
        a2.a("Gender", (dq.a) this);
        eo.a(4, g, "initSettings, Gender = " + this.G);
        this.F = (String) a2.a("UserId");
        a2.a("UserId", (dq.a) this);
        eo.a(4, g, "initSettings, UserId = " + this.F);
        this.E = ((Boolean) a2.a("LogEvents")).booleanValue();
        a2.a("LogEvents", (dq.a) this);
        eo.a(4, g, "initSettings, LogEvents = " + this.E);
        this.H = ((Long) a2.a("Age")).longValue();
        a2.a("Age", (dq.a) this);
        eo.a(4, g, "initSettings, BirthDate = " + this.H);
        this.I = ((Long) a2.a("ContinueSessionMillis")).longValue();
        a2.a("ContinueSessionMillis", (dq.a) this);
        eo.a(4, g, "initSettings, ContinueSessionMillis = " + this.I);
    }

    /* access modifiers changed from: private */
    public void v() {
        try {
            if (this.v != null) {
                eo.a(3, g, "Fetched phone id");
                a(dr.PhoneId, ByteBuffer.wrap(this.v.getBytes()));
            }
            if (this.u != null) {
                eo.a(3, g, "Fetched hashed IMEI");
                a(dr.Sha1Imei, ByteBuffer.wrap(this.u));
            }
            if (this.t != null) {
                eo.a(3, g, "Fetched advertising id");
                a(dr.AndroidAdvertisingId, ByteBuffer.wrap(this.t.getId().getBytes()));
            }
            A();
        } catch (Throwable th) {
            eo.a(6, g, BuildConfig.FLAVOR, th);
        }
    }

    private void w() {
        a((ff) new ff() {
            public void a() {
                dh d = dj.this.d();
                dj.this.s.clear();
                dj.this.s.add(d);
                dj.this.B();
            }
        });
    }

    private void x() {
        a((ff) new ff() {
            public void a() {
                dj.this.y();
            }
        });
    }

    /* access modifiers changed from: private */
    public void y() {
        boolean z2;
        try {
            synchronized (this) {
                z2 = this.s.size() > 0;
            }
            if (z2) {
                A();
            }
        } catch (Throwable th) {
            eo.a(6, g, BuildConfig.FLAVOR, th);
        }
    }

    private void z() {
        a((ff) new ff() {
            public void a() {
                dz.a().d();
            }
        });
    }

    /* access modifiers changed from: package-private */
    public Map<String, List<String>> a() {
        return this.R;
    }

    public synchronized void a(Context context) {
        eo.a(4, g, "Initializing new Flurry session with context:" + context);
        this.m = new WeakReference<>(context);
        this.S = new dm(this);
        this.n = context.getFileStreamPath(F());
        this.l = eb.a();
        this.y = -1;
        this.O = 0;
        this.B = TimeZone.getDefault().getID();
        this.A = Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry();
        this.L = true;
        this.M = 0;
        this.P = 0;
        this.R = d(context);
        u();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.w = System.currentTimeMillis();
        this.x = elapsedRealtime;
        a((ff) new ff() {
            public void a() {
                AdvertisingIdClient.Info unused = dj.this.t = dw.a();
            }
        });
        a((ff) new ff() {
            public void a() {
                byte[] unused = dj.this.u = dy.a();
            }
        });
        a((ff) new ff() {
            public void a() {
                String unused = dj.this.v = ea.a();
            }
        });
        a((ff) new ff() {
            public void a() {
                dj.this.C();
            }
        });
        a((ff) new ff() {
            public void a() {
                dj.this.v();
            }
        });
        ep.a().a(this, context);
        this.U = true;
    }

    public void a(ff ffVar) {
        Cdo.a().c(ffVar);
    }

    public void a(String str, Object obj) {
        if (str.equals("Gender")) {
            this.G = ((Byte) obj).byteValue();
            eo.a(4, g, "onSettingUpdate, Gender = " + this.G);
        } else if (str.equals("UserId")) {
            this.F = (String) obj;
            eo.a(4, g, "onSettingUpdate, UserId = " + this.F);
        } else if (str.equals("LogEvents")) {
            this.E = ((Boolean) obj).booleanValue();
            eo.a(4, g, "onSettingUpdate, LogEvents = " + this.E);
        } else if (str.equals("Age")) {
            this.H = ((Long) obj).longValue();
            eo.a(4, g, "onSettingUpdate, Birthdate = " + this.H);
        } else if (str.equals("ContinueSessionMillis")) {
            this.I = ((Long) obj).longValue();
            eo.a(4, g, "onSettingUpdate, ContinueSessionMillis = " + this.I);
        } else {
            eo.a(6, g, "onSettingUpdate internal error!");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x005b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(java.lang.String r10, java.lang.String r11, java.lang.String r12, java.lang.Throwable r13) {
        /*
            r9 = this;
            r0 = 0
            monitor-enter(r9)
            if (r10 == 0) goto L_0x0059
            java.lang.String r1 = "uncaught"
            boolean r1 = r1.equals(r10)     // Catch:{ all -> 0x009f }
            if (r1 == 0) goto L_0x0059
            r1 = 1
        L_0x000d:
            int r2 = r9.O     // Catch:{ all -> 0x009f }
            int r2 = r2 + 1
            r9.O = r2     // Catch:{ all -> 0x009f }
            java.util.List<com.flurry.sdk.da> r2 = r9.N     // Catch:{ all -> 0x009f }
            int r2 = r2.size()     // Catch:{ all -> 0x009f }
            int r3 = e     // Catch:{ all -> 0x009f }
            if (r2 >= r3) goto L_0x005b
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x009f }
            java.lang.Long r2 = java.lang.Long.valueOf(r0)     // Catch:{ all -> 0x009f }
            com.flurry.sdk.da r0 = new com.flurry.sdk.da     // Catch:{ all -> 0x009f }
            int r1 = r9.H()     // Catch:{ all -> 0x009f }
            long r2 = r2.longValue()     // Catch:{ all -> 0x009f }
            r4 = r10
            r5 = r11
            r6 = r12
            r7 = r13
            r0.<init>(r1, r2, r4, r5, r6, r7)     // Catch:{ all -> 0x009f }
            java.util.List<com.flurry.sdk.da> r1 = r9.N     // Catch:{ all -> 0x009f }
            r1.add(r0)     // Catch:{ all -> 0x009f }
            java.lang.String r1 = g     // Catch:{ all -> 0x009f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x009f }
            r2.<init>()     // Catch:{ all -> 0x009f }
            java.lang.String r3 = "Error logged: "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x009f }
            java.lang.String r0 = r0.c()     // Catch:{ all -> 0x009f }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ all -> 0x009f }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x009f }
            com.flurry.sdk.eo.d(r1, r0)     // Catch:{ all -> 0x009f }
        L_0x0057:
            monitor-exit(r9)
            return
        L_0x0059:
            r1 = r0
            goto L_0x000d
        L_0x005b:
            if (r1 == 0) goto L_0x00a6
            r8 = r0
        L_0x005e:
            java.util.List<com.flurry.sdk.da> r0 = r9.N     // Catch:{ all -> 0x009f }
            int r0 = r0.size()     // Catch:{ all -> 0x009f }
            if (r8 >= r0) goto L_0x0057
            java.util.List<com.flurry.sdk.da> r0 = r9.N     // Catch:{ all -> 0x009f }
            java.lang.Object r0 = r0.get(r8)     // Catch:{ all -> 0x009f }
            com.flurry.sdk.da r0 = (com.flurry.sdk.da) r0     // Catch:{ all -> 0x009f }
            java.lang.String r1 = r0.c()     // Catch:{ all -> 0x009f }
            if (r1 == 0) goto L_0x00a2
            java.lang.String r1 = "uncaught"
            java.lang.String r0 = r0.c()     // Catch:{ all -> 0x009f }
            boolean r0 = r1.equals(r0)     // Catch:{ all -> 0x009f }
            if (r0 != 0) goto L_0x00a2
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x009f }
            java.lang.Long r2 = java.lang.Long.valueOf(r0)     // Catch:{ all -> 0x009f }
            com.flurry.sdk.da r0 = new com.flurry.sdk.da     // Catch:{ all -> 0x009f }
            int r1 = r9.H()     // Catch:{ all -> 0x009f }
            long r2 = r2.longValue()     // Catch:{ all -> 0x009f }
            r4 = r10
            r5 = r11
            r6 = r12
            r7 = r13
            r0.<init>(r1, r2, r4, r5, r6, r7)     // Catch:{ all -> 0x009f }
            java.util.List<com.flurry.sdk.da> r1 = r9.N     // Catch:{ all -> 0x009f }
            r1.set(r8, r0)     // Catch:{ all -> 0x009f }
            goto L_0x0057
        L_0x009f:
            r0 = move-exception
            monitor-exit(r9)
            throw r0
        L_0x00a2:
            int r0 = r8 + 1
            r8 = r0
            goto L_0x005e
        L_0x00a6:
            java.lang.String r0 = g     // Catch:{ all -> 0x009f }
            java.lang.String r1 = "Max errors logged. No more errors logged."
            com.flurry.sdk.eo.d(r0, r1)     // Catch:{ all -> 0x009f }
            goto L_0x0057
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.dj.a(java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0026, code lost:
        if (r9.size() <= 0) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002c, code lost:
        if (r7.M >= d) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002e, code lost:
        r1 = r7.M - r0.d();
        r4 = new java.util.HashMap(r0.c());
        r0.a(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0048, code lost:
        if ((r0.d() + r1) > d) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0054, code lost:
        if (r0.c().size() <= b) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0056, code lost:
        com.flurry.sdk.eo.d(g, "MaxEventParams exceeded on endEvent: " + r0.c().size());
        r0.b(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0079, code lost:
        r0.a(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r7.M = r1 + r0.d();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r0.b(r4);
        r7.L = false;
        r7.M = d;
        com.flurry.sdk.eo.d(g, "Event Log size exceeded. No more event details logged.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0019, code lost:
        r2 = android.os.SystemClock.elapsedRealtime() - r7.x;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0020, code lost:
        if (r9 == null) goto L_0x0079;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(java.lang.String r8, java.util.Map<java.lang.String, java.lang.String> r9) {
        /*
            r7 = this;
            monitor-enter(r7)
            java.util.List<com.flurry.sdk.db> r0 = r7.K     // Catch:{ all -> 0x0086 }
            java.util.Iterator r1 = r0.iterator()     // Catch:{ all -> 0x0086 }
        L_0x0007:
            boolean r0 = r1.hasNext()     // Catch:{ all -> 0x0086 }
            if (r0 == 0) goto L_0x007c
            java.lang.Object r0 = r1.next()     // Catch:{ all -> 0x0086 }
            com.flurry.sdk.db r0 = (com.flurry.sdk.db) r0     // Catch:{ all -> 0x0086 }
            boolean r2 = r0.a((java.lang.String) r8)     // Catch:{ all -> 0x0086 }
            if (r2 == 0) goto L_0x0007
            long r2 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x0086 }
            long r4 = r7.x     // Catch:{ all -> 0x0086 }
            long r2 = r2 - r4
            if (r9 == 0) goto L_0x0079
            int r1 = r9.size()     // Catch:{ all -> 0x0086 }
            if (r1 <= 0) goto L_0x0079
            int r1 = r7.M     // Catch:{ all -> 0x0086 }
            int r4 = d     // Catch:{ all -> 0x0086 }
            if (r1 >= r4) goto L_0x0079
            int r1 = r7.M     // Catch:{ all -> 0x0086 }
            int r4 = r0.d()     // Catch:{ all -> 0x0086 }
            int r1 = r1 - r4
            java.util.HashMap r4 = new java.util.HashMap     // Catch:{ all -> 0x0086 }
            java.util.Map r5 = r0.c()     // Catch:{ all -> 0x0086 }
            r4.<init>(r5)     // Catch:{ all -> 0x0086 }
            r0.a((java.util.Map<java.lang.String, java.lang.String>) r9)     // Catch:{ all -> 0x0086 }
            int r5 = r0.d()     // Catch:{ all -> 0x0086 }
            int r5 = r5 + r1
            int r6 = d     // Catch:{ all -> 0x0086 }
            if (r5 > r6) goto L_0x0089
            java.util.Map r5 = r0.c()     // Catch:{ all -> 0x0086 }
            int r5 = r5.size()     // Catch:{ all -> 0x0086 }
            int r6 = b     // Catch:{ all -> 0x0086 }
            if (r5 <= r6) goto L_0x007e
            java.lang.String r1 = g     // Catch:{ all -> 0x0086 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0086 }
            r5.<init>()     // Catch:{ all -> 0x0086 }
            java.lang.String r6 = "MaxEventParams exceeded on endEvent: "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0086 }
            java.util.Map r6 = r0.c()     // Catch:{ all -> 0x0086 }
            int r6 = r6.size()     // Catch:{ all -> 0x0086 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0086 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0086 }
            com.flurry.sdk.eo.d(r1, r5)     // Catch:{ all -> 0x0086 }
            r0.b(r4)     // Catch:{ all -> 0x0086 }
        L_0x0079:
            r0.a((long) r2)     // Catch:{ all -> 0x0086 }
        L_0x007c:
            monitor-exit(r7)
            return
        L_0x007e:
            int r4 = r0.d()     // Catch:{ all -> 0x0086 }
            int r1 = r1 + r4
            r7.M = r1     // Catch:{ all -> 0x0086 }
            goto L_0x0079
        L_0x0086:
            r0 = move-exception
            monitor-exit(r7)
            throw r0
        L_0x0089:
            r0.b(r4)     // Catch:{ all -> 0x0086 }
            r1 = 0
            r7.L = r1     // Catch:{ all -> 0x0086 }
            int r1 = d     // Catch:{ all -> 0x0086 }
            r7.M = r1     // Catch:{ all -> 0x0086 }
            java.lang.String r1 = g     // Catch:{ all -> 0x0086 }
            java.lang.String r4 = "Event Log size exceeded. No more event details logged."
            com.flurry.sdk.eo.d(r1, r4)     // Catch:{ all -> 0x0086 }
            goto L_0x0079
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.dj.a(java.lang.String, java.util.Map):void");
    }

    public synchronized void a(String str, Map<String, String> map, boolean z2) {
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.x;
        String a2 = fe.a(str);
        if (a2.length() != 0) {
            cx.a aVar = this.J.get(a2);
            if (aVar != null) {
                aVar.a++;
                eo.d(g, "Event count incremented: " + a2);
            } else if (this.J.size() < a) {
                cx.a aVar2 = new cx.a();
                aVar2.a = 1;
                this.J.put(a2, aVar2);
                eo.d(g, "Event count started: " + a2);
            } else {
                eo.d(g, "Too many different events. Event not counted: " + a2);
            }
            if (!this.E || this.K.size() >= c || this.M >= d) {
                this.L = false;
            } else {
                Map<String, String> emptyMap = map == null ? Collections.emptyMap() : map;
                if (emptyMap.size() > b) {
                    eo.d(g, "MaxEventParams exceeded: " + emptyMap.size());
                } else {
                    db dbVar = new db(G(), a2, emptyMap, elapsedRealtime, z2);
                    if (dbVar.d() + this.M <= d) {
                        this.K.add(dbVar);
                        this.M = dbVar.d() + this.M;
                    } else {
                        this.M = d;
                        this.L = false;
                        eo.d(g, "Event Log size exceeded. No more event details logged.");
                    }
                }
            }
        }
    }

    public void b() {
        this.q = true;
    }

    public synchronized void b(Context context) {
        if (this.U) {
            eo.d(g, "Start session with context: " + context + " count:" + g());
            this.m = new WeakReference<>(context);
            if (this.S.b()) {
                this.S.a();
            }
            D();
            dz.a().c();
            this.D = dz.a().f();
            ep.a().b(this, context);
        }
    }

    public synchronized void c() {
        if (this.U) {
            eo.d(g, "Finalize session");
            if (this.S.b()) {
                this.S.a();
            }
            if (g() != 0) {
                eo.a(6, g, "Session with apiKey = " + j() + " was ended while getSessionCount() is not 0");
            }
            this.T = 0;
            x();
            ep.a().a(this);
            a((ff) new ff() {
                public void a() {
                    dl.a().a(dj.this.j());
                }
            });
            dp.a().b("Gender", (dq.a) this);
            dp.a().b("UserId", (dq.a) this);
            dp.a().b("Age", (dq.a) this);
            dp.a().b("LogEvents", (dq.a) this);
            dp.a().b("ContinueSessionMillis", (dq.a) this);
        }
    }

    public synchronized void c(Context context) {
        if (this.U) {
            ep.a().c(this, context);
            this.D = dz.a().f();
            z();
            E();
            eo.d(g, "End session with context: " + context + " count:" + g());
            this.y = SystemClock.elapsedRealtime() - this.x;
            a(this.y);
            w();
            if (g() == 0) {
                this.S.a(this.I);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized dh d() {
        dh dhVar;
        di diVar = new di();
        diVar.a(this.l);
        diVar.a(this.w);
        diVar.b(this.y);
        diVar.c(this.z);
        diVar.b(k());
        diVar.c(l());
        diVar.a((int) this.C);
        diVar.d(h());
        diVar.a(this.D);
        diVar.b(f());
        diVar.a(this.G);
        diVar.a(Long.valueOf(this.H));
        diVar.a(t());
        diVar.a(r());
        diVar.a(this.L);
        diVar.b(s());
        diVar.c(this.O);
        try {
            dhVar = new dh(diVar);
        } catch (IOException e2) {
            e2.printStackTrace();
            dhVar = null;
        }
        if (dhVar == null) {
            eo.d(g, "New session report wasn't created");
        }
        return dhVar;
    }

    public synchronized void e() {
        this.P++;
    }

    /* access modifiers changed from: package-private */
    public int f() {
        return this.P;
    }

    /* access modifiers changed from: package-private */
    public int g() {
        return this.T;
    }

    /* access modifiers changed from: package-private */
    public String h() {
        return this.F == null ? BuildConfig.FLAVOR : this.F;
    }

    public String i() {
        return this.v;
    }

    public String j() {
        return this.k;
    }

    public String k() {
        return this.A;
    }

    public String l() {
        return this.B;
    }

    public long m() {
        return this.w;
    }

    public long n() {
        return this.x;
    }

    public boolean o() {
        return this.t == null || !this.t.isLimitAdTrackingEnabled();
    }

    public Map<dr, ByteBuffer> p() {
        return new HashMap(this.p);
    }

    public void q() {
        c();
    }

    /* access modifiers changed from: package-private */
    public List<db> r() {
        return this.K;
    }

    /* access modifiers changed from: package-private */
    public List<da> s() {
        return this.N;
    }

    /* access modifiers changed from: package-private */
    public Map<String, cx.a> t() {
        return this.J;
    }
}
