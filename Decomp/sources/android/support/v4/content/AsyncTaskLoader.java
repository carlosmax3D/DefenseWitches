package android.support.v4.content;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.util.TimeUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;

public abstract class AsyncTaskLoader<D> extends Loader<D> {
    static final boolean DEBUG = false;
    static final String TAG = "AsyncTaskLoader";
    volatile AsyncTaskLoader<D>.LoadTask mCancellingTask;
    Handler mHandler;
    long mLastLoadCompleteTime = -10000;
    volatile AsyncTaskLoader<D>.LoadTask mTask;
    long mUpdateThrottle;

    final class LoadTask extends ModernAsyncTask<Void, Void, D> implements Runnable {
        /* access modifiers changed from: private */
        public CountDownLatch done = new CountDownLatch(1);
        D result;
        boolean waiting;

        LoadTask() {
        }

        /* access modifiers changed from: protected */
        public D doInBackground(Void... voidArr) {
            this.result = AsyncTaskLoader.this.onLoadInBackground();
            return this.result;
        }

        /* access modifiers changed from: protected */
        public void onCancelled() {
            try {
                AsyncTaskLoader.this.dispatchOnCancelled(this, this.result);
            } finally {
                this.done.countDown();
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(D d) {
            try {
                AsyncTaskLoader.this.dispatchOnLoadComplete(this, d);
            } finally {
                this.done.countDown();
            }
        }

        public void run() {
            this.waiting = false;
            AsyncTaskLoader.this.executePendingTask();
        }
    }

    public AsyncTaskLoader(Context context) {
        super(context);
    }

    public boolean cancelLoad() {
        boolean z = false;
        if (this.mTask != null) {
            if (this.mCancellingTask != null) {
                if (this.mTask.waiting) {
                    this.mTask.waiting = false;
                    this.mHandler.removeCallbacks(this.mTask);
                }
                this.mTask = null;
            } else if (this.mTask.waiting) {
                this.mTask.waiting = false;
                this.mHandler.removeCallbacks(this.mTask);
                this.mTask = null;
            } else {
                z = this.mTask.cancel(false);
                if (z) {
                    this.mCancellingTask = this.mTask;
                }
                this.mTask = null;
            }
        }
        return z;
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnCancelled(AsyncTaskLoader<D>.LoadTask loadTask, D d) {
        onCanceled(d);
        if (this.mCancellingTask == loadTask) {
            rollbackContentChanged();
            this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
            this.mCancellingTask = null;
            executePendingTask();
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnLoadComplete(AsyncTaskLoader<D>.LoadTask loadTask, D d) {
        if (this.mTask != loadTask) {
            dispatchOnCancelled(loadTask, d);
        } else if (isAbandoned()) {
            onCanceled(d);
        } else {
            commitContentChanged();
            this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
            this.mTask = null;
            deliverResult(d);
        }
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        if (this.mTask != null) {
            printWriter.print(str);
            printWriter.print("mTask=");
            printWriter.print(this.mTask);
            printWriter.print(" waiting=");
            printWriter.println(this.mTask.waiting);
        }
        if (this.mCancellingTask != null) {
            printWriter.print(str);
            printWriter.print("mCancellingTask=");
            printWriter.print(this.mCancellingTask);
            printWriter.print(" waiting=");
            printWriter.println(this.mCancellingTask.waiting);
        }
        if (this.mUpdateThrottle != 0) {
            printWriter.print(str);
            printWriter.print("mUpdateThrottle=");
            TimeUtils.formatDuration(this.mUpdateThrottle, printWriter);
            printWriter.print(" mLastLoadCompleteTime=");
            TimeUtils.formatDuration(this.mLastLoadCompleteTime, SystemClock.uptimeMillis(), printWriter);
            printWriter.println();
        }
    }

    /* access modifiers changed from: package-private */
    public void executePendingTask() {
        if (this.mCancellingTask == null && this.mTask != null) {
            if (this.mTask.waiting) {
                this.mTask.waiting = false;
                this.mHandler.removeCallbacks(this.mTask);
            }
            if (this.mUpdateThrottle <= 0 || SystemClock.uptimeMillis() >= this.mLastLoadCompleteTime + this.mUpdateThrottle) {
                this.mTask.executeOnExecutor(ModernAsyncTask.THREAD_POOL_EXECUTOR, (Params[]) null);
                return;
            }
            this.mTask.waiting = true;
            this.mHandler.postAtTime(this.mTask, this.mLastLoadCompleteTime + this.mUpdateThrottle);
        }
    }

    public abstract D loadInBackground();

    public void onCanceled(D d) {
    }

    /* access modifiers changed from: protected */
    public void onForceLoad() {
        super.onForceLoad();
        cancelLoad();
        this.mTask = new LoadTask();
        executePendingTask();
    }

    /* access modifiers changed from: protected */
    public D onLoadInBackground() {
        return loadInBackground();
    }

    public void setUpdateThrottle(long j) {
        this.mUpdateThrottle = j;
        if (j != 0) {
            this.mHandler = new Handler();
        }
    }

    public void waitForLoader() {
        AsyncTaskLoader<D>.LoadTask loadTask = this.mTask;
        if (loadTask != null) {
            try {
                loadTask.done.await();
            } catch (InterruptedException e) {
            }
        }
    }
}
