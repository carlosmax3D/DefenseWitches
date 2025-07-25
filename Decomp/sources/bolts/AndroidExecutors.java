package bolts;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

final class AndroidExecutors {
    static final int CORE_POOL_SIZE = (CPU_COUNT + 1);
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final AndroidExecutors INSTANCE = new AndroidExecutors();
    static final long KEEP_ALIVE_TIME = 1;
    static final int MAX_POOL_SIZE = ((CPU_COUNT * 2) + 1);
    static final int MAX_QUEUE_SIZE = 128;
    private final Executor uiThread = new UIThreadExecutor();

    private static class UIThreadExecutor implements Executor {
        private UIThreadExecutor() {
        }

        public void execute(Runnable runnable) {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    private AndroidExecutors() {
    }

    @SuppressLint({"NewApi"})
    public static void allowCoreThreadTimeout(ThreadPoolExecutor threadPoolExecutor, boolean z) {
        if (Build.VERSION.SDK_INT >= 9) {
            threadPoolExecutor.allowCoreThreadTimeOut(z);
        }
    }

    public static ExecutorService newCachedThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 1, TimeUnit.SECONDS, new LinkedBlockingQueue(128));
        allowCoreThreadTimeout(threadPoolExecutor, true);
        return threadPoolExecutor;
    }

    public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 1, TimeUnit.SECONDS, new LinkedBlockingQueue(128), threadFactory);
        allowCoreThreadTimeout(threadPoolExecutor, true);
        return threadPoolExecutor;
    }

    public static Executor uiThread() {
        return INSTANCE.uiThread;
    }
}
