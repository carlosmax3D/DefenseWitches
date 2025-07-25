package com.flurry.sdk;

import com.flurry.sdk.fg;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class eg<T extends fg> {
    private static final String a = eg.class.getSimpleName();
    private final ds<Object, T> b = new ds<>();
    private final HashMap<T, Object> c = new HashMap<>();
    /* access modifiers changed from: private */
    public final HashMap<T, Future<?>> d = new HashMap<>();
    private final ThreadPoolExecutor e;

    public eg(String str, int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        this.e = new ThreadPoolExecutor(i, i2, j, timeUnit, blockingQueue) {
            /* access modifiers changed from: protected */
            public void afterExecute(Runnable runnable, Throwable th) {
                super.afterExecute(runnable, th);
                final fg a2 = eg.this.a(runnable);
                if (a2 != null) {
                    synchronized (eg.this.d) {
                        eg.this.d.remove(a2);
                    }
                    eg.this.b(a2);
                    new ff() {
                        public void a() {
                            a2.k();
                        }
                    }.run();
                }
            }

            /* access modifiers changed from: protected */
            public void beforeExecute(Thread thread, Runnable runnable) {
                super.beforeExecute(thread, runnable);
                final fg a2 = eg.this.a(runnable);
                if (a2 != null) {
                    new ff() {
                        public void a() {
                            a2.j();
                        }
                    }.run();
                }
            }

            /* access modifiers changed from: protected */
            public <V> RunnableFuture<V> newTaskFor(Runnable runnable, V v) {
                ef efVar = new ef(runnable, v);
                synchronized (eg.this.d) {
                    eg.this.d.put((fg) runnable, efVar);
                }
                return efVar;
            }

            /* access modifiers changed from: protected */
            public <V> RunnableFuture<V> newTaskFor(Callable<V> callable) {
                throw new UnsupportedOperationException("Callable not supported");
            }
        };
        this.e.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy() {
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                super.rejectedExecution(runnable, threadPoolExecutor);
                final fg a2 = eg.this.a(runnable);
                if (a2 != null) {
                    synchronized (eg.this.d) {
                        eg.this.d.remove(a2);
                    }
                    eg.this.b(a2);
                    new ff() {
                        public void a() {
                            a2.l();
                        }
                    }.run();
                }
            }
        });
        this.e.setThreadFactory(new ez(str, 1));
    }

    /* access modifiers changed from: private */
    public T a(Runnable runnable) {
        if (runnable instanceof ef) {
            return (fg) ((ef) runnable).a();
        }
        if (runnable instanceof fg) {
            return (fg) runnable;
        }
        eo.a(6, a, "Unknown runnable class: " + runnable.getClass().getName());
        return null;
    }

    /* access modifiers changed from: private */
    public synchronized void b(T t) {
        c(this.c.get(t), t);
    }

    private synchronized void b(Object obj, T t) {
        this.b.a(obj, t);
        this.c.put(t, obj);
    }

    private synchronized void c(Object obj, T t) {
        this.b.b(obj, t);
        this.c.remove(t);
    }

    public synchronized void a(final T t) {
        Future remove;
        if (t != null) {
            synchronized (this.d) {
                remove = this.d.remove(t);
            }
            b(t);
            if (remove != null) {
                remove.cancel(true);
            }
            new ff() {
                public void a() {
                    t.h();
                }
            }.run();
        }
    }

    public synchronized void a(Object obj) {
        if (obj != null) {
            HashSet<fg> hashSet = new HashSet<>();
            hashSet.addAll(this.b.a(obj));
            for (fg a2 : hashSet) {
                a(a2);
            }
        }
    }

    public synchronized void a(Object obj, T t) {
        if (!(obj == null || t == null)) {
            b(obj, t);
            this.e.submit(t);
        }
    }

    public synchronized long b(Object obj) {
        return obj == null ? 0 : (long) this.b.a(obj).size();
    }
}
