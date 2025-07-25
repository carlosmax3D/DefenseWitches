package twitter4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import twitter4j.conf.Configuration;

final class DispatcherImpl implements Dispatcher {
    /* access modifiers changed from: private */
    public final ExecutorService executorService;

    public DispatcherImpl(final Configuration configuration) {
        this.executorService = Executors.newFixedThreadPool(configuration.getAsyncNumThreads(), new ThreadFactory() {
            int count = 0;

            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                int i = this.count;
                this.count = i + 1;
                thread.setName(String.format("Twitter4J Async Dispatcher[%d]", new Object[]{Integer.valueOf(i)}));
                thread.setDaemon(configuration.isDaemonEnabled());
                return thread;
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                DispatcherImpl.this.executorService.shutdown();
            }
        });
    }

    public synchronized void invokeLater(Runnable runnable) {
        this.executorService.execute(runnable);
    }

    public synchronized void shutdown() {
        this.executorService.shutdown();
    }
}
