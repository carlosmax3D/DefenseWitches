package com.flurry.sdk;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ef<V> extends FutureTask<V> {
    private final WeakReference<Callable<V>> a = null;
    private final WeakReference<Runnable> b;

    public ef(Runnable runnable, V v) {
        super(runnable, v);
        this.b = new WeakReference<>(runnable);
    }

    public Runnable a() {
        return (Runnable) this.b.get();
    }
}
