package bolts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Task<TResult> {
    public static final ExecutorService BACKGROUND_EXECUTOR = BoltsExecutors.background();
    private static final Executor IMMEDIATE_EXECUTOR = BoltsExecutors.immediate();
    public static final Executor UI_THREAD_EXECUTOR = AndroidExecutors.uiThread();
    /* access modifiers changed from: private */
    public boolean cancelled;
    /* access modifiers changed from: private */
    public boolean complete;
    private List<Continuation<TResult, Void>> continuations = new ArrayList();
    /* access modifiers changed from: private */
    public Exception error;
    /* access modifiers changed from: private */
    public final Object lock = new Object();
    /* access modifiers changed from: private */
    public TResult result;

    public class TaskCompletionSource {
        private TaskCompletionSource() {
        }

        public Task<TResult> getTask() {
            return Task.this;
        }

        public void setCancelled() {
            if (!trySetCancelled()) {
                throw new IllegalStateException("Cannot cancel a completed task.");
            }
        }

        public void setError(Exception exc) {
            if (!trySetError(exc)) {
                throw new IllegalStateException("Cannot set the error on a completed task.");
            }
        }

        public void setResult(TResult tresult) {
            if (!trySetResult(tresult)) {
                throw new IllegalStateException("Cannot set the result of a completed task.");
            }
        }

        public boolean trySetCancelled() {
            boolean z = true;
            synchronized (Task.this.lock) {
                if (Task.this.complete) {
                    z = false;
                } else {
                    boolean unused = Task.this.complete = true;
                    boolean unused2 = Task.this.cancelled = true;
                    Task.this.lock.notifyAll();
                    Task.this.runContinuations();
                }
            }
            return z;
        }

        public boolean trySetError(Exception exc) {
            boolean z = true;
            synchronized (Task.this.lock) {
                if (Task.this.complete) {
                    z = false;
                } else {
                    boolean unused = Task.this.complete = true;
                    Exception unused2 = Task.this.error = exc;
                    Task.this.lock.notifyAll();
                    Task.this.runContinuations();
                }
            }
            return z;
        }

        public boolean trySetResult(TResult tresult) {
            boolean z = true;
            synchronized (Task.this.lock) {
                if (Task.this.complete) {
                    z = false;
                } else {
                    boolean unused = Task.this.complete = true;
                    Object unused2 = Task.this.result = tresult;
                    Task.this.lock.notifyAll();
                    Task.this.runContinuations();
                }
            }
            return z;
        }
    }

    private Task() {
    }

    public static <TResult> Task<TResult> call(Callable<TResult> callable) {
        return call(callable, IMMEDIATE_EXECUTOR);
    }

    public static <TResult> Task<TResult> call(final Callable<TResult> callable, Executor executor) {
        final Task<TResult>.TaskCompletionSource create = create();
        executor.execute(new Runnable() {
            public void run() {
                try {
                    create.setResult(callable.call());
                } catch (Exception e) {
                    create.setError(e);
                }
            }
        });
        return create.getTask();
    }

    public static <TResult> Task<TResult> callInBackground(Callable<TResult> callable) {
        return call(callable, BACKGROUND_EXECUTOR);
    }

    public static <TResult> Task<TResult> cancelled() {
        Task<TResult>.TaskCompletionSource create = create();
        create.setCancelled();
        return create.getTask();
    }

    /* access modifiers changed from: private */
    public static <TContinuationResult, TResult> void completeAfterTask(final Task<TContinuationResult>.TaskCompletionSource taskCompletionSource, final Continuation<TResult, Task<TContinuationResult>> continuation, final Task<TResult> task, Executor executor) {
        executor.execute(new Runnable() {
            public void run() {
                try {
                    Task task = (Task) continuation.then(task);
                    if (task == null) {
                        taskCompletionSource.setResult(null);
                    } else {
                        task.continueWith(new Continuation<TContinuationResult, Void>() {
                            public Void then(Task<TContinuationResult> task) {
                                if (task.isCancelled()) {
                                    taskCompletionSource.setCancelled();
                                    return null;
                                } else if (task.isFaulted()) {
                                    taskCompletionSource.setError(task.getError());
                                    return null;
                                } else {
                                    taskCompletionSource.setResult(task.getResult());
                                    return null;
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    taskCompletionSource.setError(e);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static <TContinuationResult, TResult> void completeImmediately(final Task<TContinuationResult>.TaskCompletionSource taskCompletionSource, final Continuation<TResult, TContinuationResult> continuation, final Task<TResult> task, Executor executor) {
        executor.execute(new Runnable() {
            public void run() {
                try {
                    taskCompletionSource.setResult(continuation.then(task));
                } catch (Exception e) {
                    taskCompletionSource.setError(e);
                }
            }
        });
    }

    public static <TResult> Task<TResult>.TaskCompletionSource create() {
        Task task = new Task();
        task.getClass();
        return new TaskCompletionSource();
    }

    public static <TResult> Task<TResult> forError(Exception exc) {
        Task<TResult>.TaskCompletionSource create = create();
        create.setError(exc);
        return create.getTask();
    }

    public static <TResult> Task<TResult> forResult(TResult tresult) {
        Task<TResult>.TaskCompletionSource create = create();
        create.setResult(tresult);
        return create.getTask();
    }

    /* access modifiers changed from: private */
    public void runContinuations() {
        synchronized (this.lock) {
            for (Continuation then : this.continuations) {
                try {
                    then.then(this);
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
            this.continuations = null;
        }
    }

    public static Task<Void> whenAll(Collection<? extends Task<?>> collection) {
        if (collection.size() == 0) {
            return forResult((Object) null);
        }
        final Task<TResult>.TaskCompletionSource create = create();
        final ArrayList arrayList = new ArrayList();
        final Object obj = new Object();
        final AtomicInteger atomicInteger = new AtomicInteger(collection.size());
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        for (Task continueWith : collection) {
            continueWith.continueWith(new Continuation<Object, Void>() {
                public Void then(Task<Object> task) {
                    if (task.isFaulted()) {
                        synchronized (obj) {
                            arrayList.add(task.getError());
                        }
                    }
                    if (task.isCancelled()) {
                        atomicBoolean.set(true);
                    }
                    if (atomicInteger.decrementAndGet() == 0) {
                        if (arrayList.size() != 0) {
                            if (arrayList.size() == 1) {
                                create.setError((Exception) arrayList.get(0));
                            } else {
                                create.setError(new AggregateException(arrayList));
                            }
                        } else if (atomicBoolean.get()) {
                            create.setCancelled();
                        } else {
                            create.setResult(null);
                        }
                    }
                    return null;
                }
            });
        }
        return create.getTask();
    }

    public <TOut> Task<TOut> cast() {
        return this;
    }

    public Task<Void> continueWhile(Callable<Boolean> callable, Continuation<Void, Task<Void>> continuation) {
        return continueWhile(callable, continuation, IMMEDIATE_EXECUTOR);
    }

    public Task<Void> continueWhile(Callable<Boolean> callable, Continuation<Void, Task<Void>> continuation, Executor executor) {
        final Capture capture = new Capture();
        final Callable<Boolean> callable2 = callable;
        final Continuation<Void, Task<Void>> continuation2 = continuation;
        final Executor executor2 = executor;
        capture.set(new Continuation<Void, Task<Void>>() {
            public Task<Void> then(Task<Void> task) throws Exception {
                return ((Boolean) callable2.call()).booleanValue() ? Task.forResult(null).onSuccessTask(continuation2, executor2).onSuccessTask((Continuation) capture.get(), executor2) : Task.forResult(null);
            }
        });
        return makeVoid().continueWithTask((Continuation) capture.get(), executor);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> continuation) {
        return continueWith(continuation, IMMEDIATE_EXECUTOR);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(final Continuation<TResult, TContinuationResult> continuation, final Executor executor) {
        boolean isCompleted;
        final Task<TResult>.TaskCompletionSource create = create();
        synchronized (this.lock) {
            isCompleted = isCompleted();
            if (!isCompleted) {
                this.continuations.add(new Continuation<TResult, Void>() {
                    public Void then(Task<TResult> task) {
                        Task.completeImmediately(create, continuation, task, executor);
                        return null;
                    }
                });
            }
        }
        if (isCompleted) {
            completeImmediately(create, continuation, this, executor);
        }
        return create.getTask();
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> continuation) {
        return continueWithTask(continuation, IMMEDIATE_EXECUTOR);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(final Continuation<TResult, Task<TContinuationResult>> continuation, final Executor executor) {
        boolean isCompleted;
        final Task<TResult>.TaskCompletionSource create = create();
        synchronized (this.lock) {
            isCompleted = isCompleted();
            if (!isCompleted) {
                this.continuations.add(new Continuation<TResult, Void>() {
                    public Void then(Task<TResult> task) {
                        Task.completeAfterTask(create, continuation, task, executor);
                        return null;
                    }
                });
            }
        }
        if (isCompleted) {
            completeAfterTask(create, continuation, this, executor);
        }
        return create.getTask();
    }

    public Exception getError() {
        Exception exc;
        synchronized (this.lock) {
            exc = this.error;
        }
        return exc;
    }

    public TResult getResult() {
        TResult tresult;
        synchronized (this.lock) {
            tresult = this.result;
        }
        return tresult;
    }

    public boolean isCancelled() {
        boolean z;
        synchronized (this.lock) {
            z = this.cancelled;
        }
        return z;
    }

    public boolean isCompleted() {
        boolean z;
        synchronized (this.lock) {
            z = this.complete;
        }
        return z;
    }

    public boolean isFaulted() {
        boolean z;
        synchronized (this.lock) {
            z = this.error != null;
        }
        return z;
    }

    public Task<Void> makeVoid() {
        return continueWithTask(new Continuation<TResult, Task<Void>>() {
            public Task<Void> then(Task<TResult> task) throws Exception {
                return task.isCancelled() ? Task.cancelled() : task.isFaulted() ? Task.forError(task.getError()) : Task.forResult(null);
            }
        });
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccess(Continuation<TResult, TContinuationResult> continuation) {
        return onSuccess(continuation, IMMEDIATE_EXECUTOR);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccess(final Continuation<TResult, TContinuationResult> continuation, Executor executor) {
        return continueWithTask(new Continuation<TResult, Task<TContinuationResult>>() {
            public Task<TContinuationResult> then(Task<TResult> task) {
                return task.isFaulted() ? Task.forError(task.getError()) : task.isCancelled() ? Task.cancelled() : task.continueWith(continuation);
            }
        }, executor);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccessTask(Continuation<TResult, Task<TContinuationResult>> continuation) {
        return onSuccessTask(continuation, IMMEDIATE_EXECUTOR);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccessTask(final Continuation<TResult, Task<TContinuationResult>> continuation, Executor executor) {
        return continueWithTask(new Continuation<TResult, Task<TContinuationResult>>() {
            public Task<TContinuationResult> then(Task<TResult> task) {
                return task.isFaulted() ? Task.forError(task.getError()) : task.isCancelled() ? Task.cancelled() : task.continueWithTask(continuation);
            }
        }, executor);
    }

    public void waitForCompletion() throws InterruptedException {
        synchronized (this.lock) {
            if (!isCompleted()) {
                this.lock.wait();
            }
        }
    }
}
