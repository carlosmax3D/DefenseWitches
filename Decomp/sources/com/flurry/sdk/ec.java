package com.flurry.sdk;

import java.lang.Thread;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class ec {
    private static ec a;
    private final Thread.UncaughtExceptionHandler b = Thread.getDefaultUncaughtExceptionHandler();
    private final Map<Thread.UncaughtExceptionHandler, Void> c = new WeakHashMap();

    final class a implements Thread.UncaughtExceptionHandler {
        private a() {
        }

        public void uncaughtException(Thread thread, Throwable th) {
            ec.this.a(thread, th);
            ec.this.b(thread, th);
        }
    }

    private ec() {
        Thread.setDefaultUncaughtExceptionHandler(new a());
    }

    public static synchronized ec a() {
        ec ecVar;
        synchronized (ec.class) {
            if (a == null) {
                a = new ec();
            }
            ecVar = a;
        }
        return ecVar;
    }

    /* access modifiers changed from: private */
    public void a(Thread thread, Throwable th) {
        for (Thread.UncaughtExceptionHandler uncaughtException : b()) {
            try {
                uncaughtException.uncaughtException(thread, th);
            } catch (Throwable th2) {
            }
        }
    }

    private Set<Thread.UncaughtExceptionHandler> b() {
        Set<Thread.UncaughtExceptionHandler> keySet;
        synchronized (this.c) {
            keySet = this.c.keySet();
        }
        return keySet;
    }

    /* access modifiers changed from: private */
    public void b(Thread thread, Throwable th) {
        if (this.b != null) {
            this.b.uncaughtException(thread, th);
        }
    }

    public void a(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        synchronized (this.c) {
            this.c.put(uncaughtExceptionHandler, (Object) null);
        }
    }
}
