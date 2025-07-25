package com.google.android.gms.internal;

public abstract class cm {
    private final Runnable ep = new Runnable() {
        public final void run() {
            Thread unused = cm.this.ix = Thread.currentThread();
            cm.this.ai();
        }
    };
    /* access modifiers changed from: private */
    public volatile Thread ix;

    public abstract void ai();

    public final void cancel() {
        onStop();
        if (this.ix != null) {
            this.ix.interrupt();
        }
    }

    public abstract void onStop();

    public final void start() {
        cn.execute(this.ep);
    }
}
