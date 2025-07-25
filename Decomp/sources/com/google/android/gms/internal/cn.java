package com.google.android.gms.internal;

import android.os.Process;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class cn {
    private static final ThreadPoolExecutor iA = new ThreadPoolExecutor(0, 10, 65, TimeUnit.SECONDS, new SynchronousQueue(true), iz);
    private static final ThreadFactory iz = new ThreadFactory() {
        private final AtomicInteger iC = new AtomicInteger(1);

        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "AdWorker #" + this.iC.getAndIncrement());
        }
    };

    public static void execute(final Runnable runnable) {
        try {
            iA.execute(new Runnable() {
                public void run() {
                    Process.setThreadPriority(10);
                    runnable.run();
                }
            });
        } catch (RejectedExecutionException e) {
            ct.b("Too many background threads already running. Aborting task.", e);
        }
    }
}
