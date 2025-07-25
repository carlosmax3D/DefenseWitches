package com.flurry.sdk;

import java.util.List;
import jp.stargarage.g2metrics.BuildConfig;

public class fb {
    /* access modifiers changed from: private */
    public static final String a = fb.class.getSimpleName();
    private final dt<a> b = new dt<>();
    /* access modifiers changed from: private */
    public long c = 1000;
    /* access modifiers changed from: private */
    public boolean d = true;
    /* access modifiers changed from: private */
    public boolean e = false;
    /* access modifiers changed from: private */
    public ff f = new ff() {
        public void a() {
            try {
                for (a a2 : fb.this.f()) {
                    a2.a(fb.this);
                }
            } catch (Throwable th) {
                eo.a(6, fb.a, BuildConfig.FLAVOR, th);
            }
            if (fb.this.d && fb.this.e) {
                Cdo.a().a(fb.this.f, fb.this.c);
            }
        }
    };

    public interface a {
        void a(fb fbVar);
    }

    /* access modifiers changed from: private */
    public synchronized List<a> f() {
        return this.b.a();
    }

    public synchronized void a() {
        if (!this.e) {
            Cdo.a().a(this.f, this.c);
            this.e = true;
        }
    }

    public void a(long j) {
        this.c = j;
    }

    public synchronized void a(a aVar) {
        this.b.a(aVar);
    }

    public void a(boolean z) {
        this.d = z;
    }

    public synchronized void b() {
        if (this.e) {
            Cdo.a().b(this.f);
            this.e = false;
        }
    }

    public synchronized boolean b(a aVar) {
        return this.b.b(aVar);
    }

    public synchronized boolean c() {
        return this.e;
    }

    public synchronized int d() {
        return this.b.b();
    }
}
