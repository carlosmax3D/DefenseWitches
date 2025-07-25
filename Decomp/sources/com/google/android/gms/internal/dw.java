package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.dx;
import com.google.android.gms.internal.eb;
import com.google.android.gms.internal.ec;
import java.util.ArrayList;

public abstract class dw<T extends IInterface> implements GooglePlayServicesClient, Api.a, dx.b {
    public static final String[] pk = {"service_esmobile", "service_googleme"};
    private final String[] jF;
    /* access modifiers changed from: private */
    public final Context mContext;
    final Handler mHandler;
    /* access modifiers changed from: private */
    public final dx ne;
    /* access modifiers changed from: private */
    public T pe;
    /* access modifiers changed from: private */
    public final ArrayList<dw<T>.b<?>> pf;
    /* access modifiers changed from: private */
    public dw<T>.f pg;
    boolean ph;
    boolean pi;
    /* access modifiers changed from: private */
    public final Object pj;

    final class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            if (message.what != 1 || dw.this.isConnecting()) {
                synchronized (dw.this.pj) {
                    dw.this.pi = false;
                }
                if (message.what == 3) {
                    dw.this.ne.a(new ConnectionResult(((Integer) message.obj).intValue(), (PendingIntent) null));
                } else if (message.what == 4) {
                    dw.this.ne.J(((Integer) message.obj).intValue());
                } else if (message.what == 2 && !dw.this.isConnected()) {
                    b bVar = (b) message.obj;
                    bVar.aL();
                    bVar.unregister();
                } else if (message.what == 2 || message.what == 1) {
                    ((b) message.obj).bR();
                } else {
                    Log.wtf("GmsClient", "Don't know how to handle this message.");
                }
            } else {
                b bVar2 = (b) message.obj;
                bVar2.aL();
                bVar2.unregister();
            }
        }
    }

    protected abstract class b<TListener> {
        private TListener mListener;
        private boolean pm = false;

        public b(TListener tlistener) {
            this.mListener = tlistener;
        }

        /* access modifiers changed from: protected */
        public abstract void aL();

        /* access modifiers changed from: protected */
        public abstract void b(TListener tlistener);

        public void bR() {
            TListener tlistener;
            synchronized (this) {
                tlistener = this.mListener;
                if (this.pm) {
                    Log.w("GmsClient", "Callback proxy " + this + " being reused. This is not safe.");
                }
            }
            if (tlistener != null) {
                try {
                    b(tlistener);
                } catch (RuntimeException e) {
                    aL();
                    throw e;
                }
            } else {
                aL();
            }
            synchronized (this) {
                this.pm = true;
            }
            unregister();
        }

        public void bS() {
            synchronized (this) {
                this.mListener = null;
            }
        }

        public void unregister() {
            bS();
            synchronized (dw.this.pf) {
                dw.this.pf.remove(this);
            }
        }
    }

    public static final class c implements GoogleApiClient.ConnectionCallbacks {
        private final GooglePlayServicesClient.ConnectionCallbacks pn;

        public c(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
            this.pn = connectionCallbacks;
        }

        public boolean equals(Object obj) {
            return obj instanceof c ? this.pn.equals(((c) obj).pn) : this.pn.equals(obj);
        }

        public void onConnected(Bundle bundle) {
            this.pn.onConnected(bundle);
        }

        public void onConnectionSuspended(int i) {
            this.pn.onDisconnected();
        }
    }

    public abstract class d<TListener> extends dw<T>.b<TListener> {
        private final DataHolder nE;

        public d(TListener tlistener, DataHolder dataHolder) {
            super(tlistener);
            this.nE = dataHolder;
        }

        /* access modifiers changed from: protected */
        public abstract void a(TListener tlistener, DataHolder dataHolder);

        /* access modifiers changed from: protected */
        public void aL() {
            if (this.nE != null) {
                this.nE.close();
            }
        }

        /* access modifiers changed from: protected */
        public final void b(TListener tlistener) {
            a(tlistener, this.nE);
        }

        public /* bridge */ /* synthetic */ void bR() {
            super.bR();
        }

        public /* bridge */ /* synthetic */ void bS() {
            super.bS();
        }

        public /* bridge */ /* synthetic */ void unregister() {
            super.unregister();
        }
    }

    public static final class e extends eb.a {
        private dw po;

        public e(dw dwVar) {
            this.po = dwVar;
        }

        public void b(int i, IBinder iBinder, Bundle bundle) {
            eg.b("onPostInitComplete can be called only once per call to getServiceFromBroker", (Object) this.po);
            this.po.a(i, iBinder, bundle);
            this.po = null;
        }
    }

    final class f implements ServiceConnection {
        f() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            dw.this.w(iBinder);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            IInterface unused = dw.this.pe = null;
            dw.this.ne.J(1);
        }
    }

    public static final class g implements GoogleApiClient.OnConnectionFailedListener {
        private final GooglePlayServicesClient.OnConnectionFailedListener pp;

        public g(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.pp = onConnectionFailedListener;
        }

        public boolean equals(Object obj) {
            return obj instanceof g ? this.pp.equals(((g) obj).pp) : this.pp.equals(obj);
        }

        public void onConnectionFailed(ConnectionResult connectionResult) {
            this.pp.onConnectionFailed(connectionResult);
        }
    }

    protected final class h extends dw<T>.b<Boolean> {
        public final Bundle pq;
        public final IBinder pr;
        public final int statusCode;

        public h(int i, IBinder iBinder, Bundle bundle) {
            super(true);
            this.statusCode = i;
            this.pr = iBinder;
            this.pq = bundle;
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }

        /* access modifiers changed from: protected */
        public void b(Boolean bool) {
            if (bool != null) {
                switch (this.statusCode) {
                    case 0:
                        try {
                            if (dw.this.an().equals(this.pr.getInterfaceDescriptor())) {
                                IInterface unused = dw.this.pe = dw.this.p(this.pr);
                                if (dw.this.pe != null) {
                                    dw.this.ne.bT();
                                    return;
                                }
                            }
                        } catch (RemoteException e) {
                        }
                        dy.s(dw.this.mContext).b(dw.this.am(), dw.this.pg);
                        f unused2 = dw.this.pg = null;
                        IInterface unused3 = dw.this.pe = null;
                        dw.this.ne.a(new ConnectionResult(8, (PendingIntent) null));
                        return;
                    case 10:
                        throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
                    default:
                        PendingIntent pendingIntent = this.pq != null ? (PendingIntent) this.pq.getParcelable("pendingIntent") : null;
                        if (dw.this.pg != null) {
                            dy.s(dw.this.mContext).b(dw.this.am(), dw.this.pg);
                            f unused4 = dw.this.pg = null;
                        }
                        IInterface unused5 = dw.this.pe = null;
                        dw.this.ne.a(new ConnectionResult(this.statusCode, pendingIntent));
                        return;
                }
            }
        }
    }

    protected dw(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, String... strArr) {
        this(context, (GoogleApiClient.ConnectionCallbacks) new c(connectionCallbacks), (GoogleApiClient.OnConnectionFailedListener) new g(onConnectionFailedListener), strArr);
    }

    protected dw(Context context, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String... strArr) {
        this.pf = new ArrayList<>();
        this.ph = false;
        this.pi = false;
        this.pj = new Object();
        this.mContext = (Context) eg.f(context);
        this.ne = new dx(context, this, (Handler) null);
        this.mHandler = new a(context.getMainLooper());
        a(strArr);
        this.jF = strArr;
        registerConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) eg.f(connectionCallbacks));
        registerConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) eg.f(onConnectionFailedListener));
    }

    public void I(int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, Integer.valueOf(i)));
    }

    /* access modifiers changed from: protected */
    public void a(int i, IBinder iBinder, Bundle bundle) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, new h(i, iBinder, bundle)));
    }

    public final void a(dw<T>.b<?> bVar) {
        synchronized (this.pf) {
            this.pf.add(bVar);
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2, bVar));
    }

    /* access modifiers changed from: protected */
    public abstract void a(ec ecVar, e eVar) throws RemoteException;

    /* access modifiers changed from: protected */
    public void a(String... strArr) {
    }

    public Bundle aU() {
        return null;
    }

    /* access modifiers changed from: protected */
    public abstract String am();

    /* access modifiers changed from: protected */
    public abstract String an();

    public final String[] bO() {
        return this.jF;
    }

    /* access modifiers changed from: protected */
    public final void bP() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    /* access modifiers changed from: protected */
    public final T bQ() {
        bP();
        return this.pe;
    }

    public boolean bp() {
        return this.ph;
    }

    public void connect() {
        this.ph = true;
        synchronized (this.pj) {
            this.pi = true;
        }
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mContext);
        if (isGooglePlayServicesAvailable != 0) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, Integer.valueOf(isGooglePlayServicesAvailable)));
            return;
        }
        if (this.pg != null) {
            Log.e("GmsClient", "Calling connect() while still connected, missing disconnect().");
            this.pe = null;
            dy.s(this.mContext).b(am(), this.pg);
        }
        this.pg = new f();
        if (!dy.s(this.mContext).a(am(), this.pg)) {
            Log.e("GmsClient", "unable to connect to service: " + am());
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, 9));
        }
    }

    public void disconnect() {
        this.ph = false;
        synchronized (this.pj) {
            this.pi = false;
        }
        synchronized (this.pf) {
            int size = this.pf.size();
            for (int i = 0; i < size; i++) {
                this.pf.get(i).bS();
            }
            this.pf.clear();
        }
        this.pe = null;
        if (this.pg != null) {
            dy.s(this.mContext).b(am(), this.pg);
            this.pg = null;
            this.ne.J(-1);
        }
    }

    public final Context getContext() {
        return this.mContext;
    }

    public boolean isConnected() {
        return this.pe != null;
    }

    public boolean isConnecting() {
        boolean z;
        synchronized (this.pj) {
            z = this.pi;
        }
        return z;
    }

    public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        return this.ne.isConnectionCallbacksRegistered(new c(connectionCallbacks));
    }

    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.ne.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    /* access modifiers changed from: protected */
    public abstract T p(IBinder iBinder);

    public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.ne.registerConnectionCallbacks(new c(connectionCallbacks));
    }

    public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.ne.registerConnectionCallbacks(connectionCallbacks);
    }

    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.ne.registerConnectionFailedListener(onConnectionFailedListener);
    }

    public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.ne.registerConnectionFailedListener(onConnectionFailedListener);
    }

    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.ne.unregisterConnectionCallbacks(new c(connectionCallbacks));
    }

    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.ne.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    /* access modifiers changed from: protected */
    public final void w(IBinder iBinder) {
        try {
            a(ec.a.y(iBinder), new e(this));
        } catch (RemoteException e2) {
            Log.w("GmsClient", "service died");
        }
    }
}
