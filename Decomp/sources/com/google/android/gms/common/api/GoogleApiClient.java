package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.dt;
import com.google.android.gms.internal.dx;
import com.google.android.gms.internal.eg;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class GoogleApiClient {
    /* access modifiers changed from: private */
    public final Object mV;
    private final a nc;
    private final dx ne;
    final Queue<b<?>> nf;
    /* access modifiers changed from: private */
    public ConnectionResult ng;
    /* access modifiers changed from: private */
    public int nh;
    /* access modifiers changed from: private */
    public int ni;
    private int nj;
    /* access modifiers changed from: private */
    public final Bundle nk;
    private final Map<Api.b<?>, Api.a> nl;
    /* access modifiers changed from: private */
    public boolean nm;
    final Set<b> nn;
    final ConnectionCallbacks no;
    private final dx.b np;

    public interface ApiOptions {
    }

    public static final class Builder {
        private String jG;
        private final Context mContext;
        private final Set<String> ns;
        private int nt;
        private View nu;
        private String nv;
        private final Map<Api, ApiOptions> nw;
        private final Set<ConnectionCallbacks> nx;
        private final Set<OnConnectionFailedListener> ny;

        public Builder(Context context) {
            this.ns = new HashSet();
            this.nw = new HashMap();
            this.nx = new HashSet();
            this.ny = new HashSet();
            this.mContext = context;
            this.nv = context.getPackageName();
        }

        public Builder(Context context, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            this(context);
            eg.b(connectionCallbacks, (Object) "Must provide a connected listener");
            this.nx.add(connectionCallbacks);
            eg.b(onConnectionFailedListener, (Object) "Must provide a connection failed listener");
            this.ny.add(onConnectionFailedListener);
        }

        public Builder addApi(Api api) {
            return addApi(api, (ApiOptions) null);
        }

        public Builder addApi(Api api, ApiOptions apiOptions) {
            this.nw.put(api, apiOptions);
            List<Scope> bk = api.bk();
            int size = bk.size();
            for (int i = 0; i < size; i++) {
                this.ns.add(bk.get(i).br());
            }
            return this;
        }

        public Builder addConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
            this.nx.add(connectionCallbacks);
            return this;
        }

        public Builder addOnConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {
            this.ny.add(onConnectionFailedListener);
            return this;
        }

        public Builder addScope(Scope scope) {
            this.ns.add(scope.br());
            return this;
        }

        public dt bq() {
            return new dt(this.jG, this.ns, this.nt, this.nu, this.nv);
        }

        public GoogleApiClient build() {
            return new GoogleApiClient(this.mContext, bq(), this.nw, this.nx, this.ny);
        }

        public Builder setAccountName(String str) {
            this.jG = str;
            return this;
        }

        public Builder setGravityForPopups(int i) {
            this.nt = i;
            return this;
        }

        public Builder setViewForPopups(View view) {
            this.nu = view;
            return this;
        }

        public Builder useDefaultAccount() {
            return setAccountName("<<default account>>");
        }
    }

    public interface ConnectionCallbacks {
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        void onConnected(Bundle bundle);

        void onConnectionSuspended(int i);
    }

    public interface OnConnectionFailedListener extends GooglePlayServicesClient.OnConnectionFailedListener {
    }

    public interface a {
        void b(b bVar);
    }

    public interface b<A extends Api.a> {
        void a(a aVar);

        void b(A a);

        Api.b<A> bj();
    }

    private GoogleApiClient(Context context, dt dtVar, Map<Api, ApiOptions> map, Set<ConnectionCallbacks> set, Set<OnConnectionFailedListener> set2) {
        this.mV = new Object();
        this.nf = new LinkedList();
        this.ni = 4;
        this.nk = new Bundle();
        this.nl = new HashMap();
        this.nn = new HashSet();
        this.nc = new a() {
            public void b(b bVar) {
                synchronized (GoogleApiClient.this.mV) {
                    GoogleApiClient.this.nn.remove(bVar);
                }
            }
        };
        this.no = new ConnectionCallbacks() {
            public void onConnected(Bundle bundle) {
                synchronized (GoogleApiClient.this.mV) {
                    if (GoogleApiClient.this.ni == 1) {
                        if (bundle != null) {
                            GoogleApiClient.this.nk.putAll(bundle);
                        }
                        GoogleApiClient.this.bn();
                    }
                }
            }

            public void onConnectionSuspended(int i) {
                synchronized (GoogleApiClient.this.mV) {
                    GoogleApiClient.this.A(i);
                    if (i == 2) {
                        GoogleApiClient.this.connect();
                    }
                }
            }
        };
        this.np = new dx.b() {
            public Bundle aU() {
                return null;
            }

            public boolean bp() {
                return GoogleApiClient.this.nm;
            }

            public boolean isConnected() {
                return GoogleApiClient.this.isConnected();
            }
        };
        this.ne = new dx(context, this.np);
        for (ConnectionCallbacks registerConnectionCallbacks : set) {
            this.ne.registerConnectionCallbacks(registerConnectionCallbacks);
        }
        for (OnConnectionFailedListener registerConnectionFailedListener : set2) {
            this.ne.registerConnectionFailedListener(registerConnectionFailedListener);
        }
        for (Api next : map.keySet()) {
            final Api.b<?> bj = next.bj();
            this.nl.put(bj, bj.b(context, dtVar, map.get(next), this.no, new OnConnectionFailedListener() {
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    synchronized (GoogleApiClient.this.mV) {
                        if (GoogleApiClient.this.ng == null || bj.getPriority() < GoogleApiClient.this.nh) {
                            ConnectionResult unused = GoogleApiClient.this.ng = connectionResult;
                            int unused2 = GoogleApiClient.this.nh = bj.getPriority();
                        }
                        GoogleApiClient.this.bn();
                    }
                }
            }));
        }
    }

    /* access modifiers changed from: private */
    public void A(int i) {
        synchronized (this.mV) {
            if (this.ni != 3) {
                boolean isConnected = isConnected();
                this.ni = 3;
                if (i == -1) {
                    this.nf.clear();
                }
                for (b next : this.nn) {
                    if (next instanceof Releasable) {
                        try {
                            ((Releasable) next).release();
                        } catch (Exception e) {
                            Log.w("GoogleApiClient", "Unable to release " + next, e);
                        }
                    }
                }
                this.nn.clear();
                this.nm = false;
                for (Api.a next2 : this.nl.values()) {
                    if (next2.isConnected()) {
                        next2.disconnect();
                    }
                }
                this.nm = true;
                this.ni = 4;
                if (isConnected) {
                    if (i != -1) {
                        this.ne.J(i);
                    }
                    this.nm = false;
                }
            }
        }
    }

    private <A extends Api.a> void a(b<A> bVar) {
        synchronized (this.mV) {
            eg.a(isConnected(), "GoogleApiClient is not connected yet.");
            eg.a(bVar.bj() != null, "This task can not be executed or enqueued (it's probably a Batch or malformed)");
            if (bVar instanceof Releasable) {
                this.nn.add(bVar);
                bVar.a(this.nc);
            }
            bVar.b(a(bVar.bj()));
        }
    }

    /* access modifiers changed from: private */
    public void bn() {
        synchronized (this.mV) {
            this.nj--;
            if (this.nj == 0) {
                if (this.ng != null) {
                    A(3);
                    this.ne.a(this.ng);
                    this.nm = false;
                } else {
                    this.ni = 2;
                    bo();
                    this.ne.b(this.nk.isEmpty() ? null : this.nk);
                }
            }
        }
    }

    private void bo() {
        eg.a(isConnected(), "GoogleApiClient is not connected yet.");
        synchronized (this.mV) {
            while (!this.nf.isEmpty()) {
                a(this.nf.remove());
            }
        }
    }

    public <C extends Api.a> C a(Api.b<C> bVar) {
        C c = (Api.a) this.nl.get(bVar);
        eg.b(c, (Object) "Appropriate Api was not requested.");
        return c;
    }

    public <A extends Api.a, T extends a.C0001a<? extends Result, A>> T a(T t) {
        synchronized (this.mV) {
            if (isConnected()) {
                b(t);
            } else {
                this.nf.add(t);
            }
        }
        return t;
    }

    public <A extends Api.a, T extends a.C0001a<? extends Result, A>> T b(T t) {
        eg.a(isConnected(), "GoogleApiClient is not connected yet.");
        bo();
        a(t);
        return t;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect() {
        /*
            r3 = this;
            java.lang.Object r1 = r3.mV
            monitor-enter(r1)
            boolean r0 = r3.isConnected()     // Catch:{ all -> 0x0041 }
            if (r0 != 0) goto L_0x000f
            boolean r0 = r3.isConnecting()     // Catch:{ all -> 0x0041 }
            if (r0 == 0) goto L_0x0011
        L_0x000f:
            monitor-exit(r1)     // Catch:{ all -> 0x0041 }
        L_0x0010:
            return
        L_0x0011:
            r0 = 1
            r3.nm = r0     // Catch:{ all -> 0x0041 }
            r0 = 0
            r3.ng = r0     // Catch:{ all -> 0x0041 }
            r0 = 1
            r3.ni = r0     // Catch:{ all -> 0x0041 }
            android.os.Bundle r0 = r3.nk     // Catch:{ all -> 0x0041 }
            r0.clear()     // Catch:{ all -> 0x0041 }
            java.util.Map<com.google.android.gms.common.api.Api$b<?>, com.google.android.gms.common.api.Api$a> r0 = r3.nl     // Catch:{ all -> 0x0041 }
            int r0 = r0.size()     // Catch:{ all -> 0x0041 }
            r3.nj = r0     // Catch:{ all -> 0x0041 }
            java.util.Map<com.google.android.gms.common.api.Api$b<?>, com.google.android.gms.common.api.Api$a> r0 = r3.nl     // Catch:{ all -> 0x0041 }
            java.util.Collection r0 = r0.values()     // Catch:{ all -> 0x0041 }
            java.util.Iterator r2 = r0.iterator()     // Catch:{ all -> 0x0041 }
        L_0x0031:
            boolean r0 = r2.hasNext()     // Catch:{ all -> 0x0041 }
            if (r0 == 0) goto L_0x0044
            java.lang.Object r0 = r2.next()     // Catch:{ all -> 0x0041 }
            com.google.android.gms.common.api.Api$a r0 = (com.google.android.gms.common.api.Api.a) r0     // Catch:{ all -> 0x0041 }
            r0.connect()     // Catch:{ all -> 0x0041 }
            goto L_0x0031
        L_0x0041:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0041 }
            throw r0
        L_0x0044:
            monitor-exit(r1)     // Catch:{ all -> 0x0041 }
            goto L_0x0010
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.GoogleApiClient.connect():void");
    }

    public void disconnect() {
        A(-1);
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this.mV) {
            z = this.ni == 2;
        }
        return z;
    }

    public boolean isConnecting() {
        boolean z = true;
        synchronized (this.mV) {
            if (this.ni != 1) {
                z = false;
            }
        }
        return z;
    }

    public boolean isConnectionCallbacksRegistered(ConnectionCallbacks connectionCallbacks) {
        return this.ne.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    public boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener onConnectionFailedListener) {
        return this.ne.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    public void reconnect() {
        disconnect();
        connect();
    }

    public void registerConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
        this.ne.registerConnectionCallbacks(connectionCallbacks);
    }

    public void registerConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {
        this.ne.registerConnectionFailedListener(onConnectionFailedListener);
    }

    public void unregisterConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
        this.ne.unregisterConnectionCallbacks(connectionCallbacks);
    }

    public void unregisterConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {
        this.ne.unregisterConnectionFailedListener(onConnectionFailedListener);
    }
}
