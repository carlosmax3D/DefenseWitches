package com.flurry.sdk;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/* renamed from: com.flurry.sdk.do  reason: invalid class name */
public class Cdo {
    private static Cdo a;
    private final Context b;
    private final Handler c = new Handler(Looper.getMainLooper());
    private final Handler d;

    private Cdo(Context context) {
        this.b = context.getApplicationContext();
        HandlerThread handlerThread = new HandlerThread("FlurryAgent");
        handlerThread.start();
        this.d = new Handler(handlerThread.getLooper());
    }

    public static Cdo a() {
        return a;
    }

    public static synchronized void a(Context context) {
        synchronized (Cdo.class) {
            if (a == null) {
                if (context == null) {
                    throw new IllegalArgumentException("Context cannot be null");
                }
                a = new Cdo(context);
            }
        }
    }

    public void a(Runnable runnable) {
        if (runnable != null) {
            this.c.post(runnable);
        }
    }

    public void a(Runnable runnable, long j) {
        if (runnable != null) {
            this.c.postDelayed(runnable, j);
        }
    }

    public Context b() {
        return this.b;
    }

    public void b(Runnable runnable) {
        if (runnable != null) {
            this.c.removeCallbacks(runnable);
        }
    }

    public void b(Runnable runnable, long j) {
        if (runnable != null) {
            this.d.postDelayed(runnable, j);
        }
    }

    public PackageManager c() {
        return this.b.getPackageManager();
    }

    public void c(Runnable runnable) {
        if (runnable != null) {
            this.d.post(runnable);
        }
    }

    public void d(Runnable runnable) {
        if (runnable != null) {
            this.d.removeCallbacks(runnable);
        }
    }
}
