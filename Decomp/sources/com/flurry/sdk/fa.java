package com.flurry.sdk;

import com.flurry.sdk.fb;

public class fa {
    private static long a = 1000;
    private static fa b = null;
    private final fb c = new fb();

    public fa() {
        this.c.a(a);
        this.c.a(true);
    }

    public static synchronized fa a() {
        fa faVar;
        synchronized (fa.class) {
            if (b == null) {
                b = new fa();
            }
            faVar = b;
        }
        return faVar;
    }

    public synchronized void a(fb.a aVar) {
        this.c.a(aVar);
        if (!this.c.c() && this.c.d() > 0) {
            this.c.a();
        }
    }

    public synchronized boolean b(fb.a aVar) {
        boolean b2;
        b2 = this.c.b(aVar);
        if (this.c.d() == 0) {
            this.c.b();
        }
        return b2;
    }
}
