package com.google.android.gms.common.api;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.internal.eg;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class a {

    /* renamed from: com.google.android.gms.common.api.a$a  reason: collision with other inner class name */
    public static abstract class C0001a<R extends Result, A extends Api.a> implements GoogleApiClient.b<A>, PendingResult<R>, c<R> {
        private final Api.b<A> mU;
        private final Object mV = new Object();
        private final b<R> mW;
        private final CountDownLatch mX = new CountDownLatch(1);
        private final ArrayList<PendingResult.a> mY = new ArrayList<>();
        private ResultCallback<R> mZ;
        private R na;
        private boolean nb;
        private GoogleApiClient.a nc;

        public C0001a(Api.b<A> bVar) {
            this.mU = bVar;
            this.mW = new b<R>() {
                /* access modifiers changed from: protected */
                public void a(ResultCallback<R> resultCallback, R r) {
                    resultCallback.onResult(r);
                }
            };
        }

        private R bl() {
            R r;
            synchronized (this.mV) {
                eg.a(!this.nb, "Result has already been consumed.");
                eg.a(isReady(), "Result is not ready.");
                r = this.na;
                bm();
            }
            return r;
        }

        /* access modifiers changed from: protected */
        public abstract void a(A a);

        public void a(GoogleApiClient.a aVar) {
            this.nc = aVar;
        }

        public final void a(R r) {
            boolean z = true;
            eg.a(!isReady(), "Results have already been set");
            if (this.nb) {
                z = false;
            }
            eg.a(z, "Result has already been consumed");
            synchronized (this.mV) {
                this.na = r;
                this.mX.countDown();
                Status status = this.na.getStatus();
                if (this.mZ != null) {
                    this.mW.b(this.mZ, bl());
                }
                Iterator<PendingResult.a> it = this.mY.iterator();
                while (it.hasNext()) {
                    it.next().l(status);
                }
                this.mY.clear();
            }
        }

        public final R await() {
            eg.a(!this.nb, "Results has already been consumed");
            try {
                this.mX.await();
            } catch (InterruptedException e) {
                a(e(Status.nB));
            }
            eg.a(isReady(), "Result is not ready.");
            return bl();
        }

        public final R await(long j, TimeUnit timeUnit) {
            eg.a(!this.nb, "Result has already been consumed.");
            try {
                if (!this.mX.await(j, timeUnit)) {
                    a(e(Status.nC));
                }
            } catch (InterruptedException e) {
                a(e(Status.nB));
            }
            eg.a(isReady(), "Result is not ready.");
            return bl();
        }

        public final void b(A a) {
            a(a);
        }

        public final Api.b<A> bj() {
            return this.mU;
        }

        /* access modifiers changed from: package-private */
        public void bm() {
            this.nb = true;
            this.na = null;
            if (this.nc != null) {
                this.nc.b(this);
            }
        }

        public final boolean isReady() {
            return this.mX.getCount() == 0;
        }

        public final void setResultCallback(ResultCallback<R> resultCallback) {
            eg.a(!this.nb, "Result has already been consumed.");
            synchronized (this.mV) {
                if (isReady()) {
                    this.mW.b(resultCallback, bl());
                } else {
                    this.mZ = resultCallback;
                }
            }
        }
    }

    static abstract class b<R extends Result> extends Handler {
        public b() {
            this(Looper.getMainLooper());
        }

        public b(Looper looper) {
            super(looper);
        }

        /* access modifiers changed from: protected */
        public abstract void a(ResultCallback<R> resultCallback, R r);

        public void b(ResultCallback<R> resultCallback, R r) {
            sendMessage(obtainMessage(1, new Pair(resultCallback, r)));
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Pair pair = (Pair) message.obj;
                    a((ResultCallback) pair.first, (Result) pair.second);
                    return;
                default:
                    Log.wtf("GoogleApi", "Don't know how to handle this message.");
                    return;
            }
        }
    }

    public interface c<R> {
        void a(R r);
    }
}
