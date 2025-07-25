package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.di;
import com.google.android.gms.internal.dj;
import com.google.android.gms.internal.dw;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public final class dg extends dw<di> {
    /* access modifiers changed from: private */
    public static final dk lA = new dk("CastClientImpl");
    /* access modifiers changed from: private */
    public static final Object lQ = new Object();
    /* access modifiers changed from: private */
    public static final Object lR = new Object();
    /* access modifiers changed from: private */
    public final Cast.Listener kw;
    /* access modifiers changed from: private */
    public ApplicationMetadata lB = null;
    /* access modifiers changed from: private */
    public final CastDevice lC;
    private final dj lD = new dj.a() {
        private void b(long j, int i) {
            a.c cVar;
            synchronized (dg.this.lN) {
                cVar = (a.c) dg.this.lN.remove(Long.valueOf(j));
            }
            if (cVar != null) {
                cVar.a(new Status(i));
            }
        }

        private boolean x(int i) {
            synchronized (dg.lR) {
                if (dg.this.lP == null) {
                    return false;
                }
                dg.this.lP.a(new Status(i));
                a.c unused = dg.this.lP = null;
                return true;
            }
        }

        public void a(ApplicationMetadata applicationMetadata, String str, String str2, boolean z) {
            String unused = dg.this.lK = applicationMetadata.getApplicationId();
            String unused2 = dg.this.lL = str2;
            synchronized (dg.lQ) {
                if (dg.this.lO != null) {
                    dg.this.lO.a(new a(new Status(0), applicationMetadata, str, str2, z));
                    a.c unused3 = dg.this.lO = null;
                }
            }
        }

        public void a(String str, long j) {
            b(j, 0);
        }

        public void a(String str, long j, int i) {
            b(j, i);
        }

        public void a(final String str, final String str2) {
            dg.lA.b("Receive (type=text, ns=%s) %s", str, str2);
            dg.this.mHandler.post(new Runnable() {
                public void run() {
                    Cast.MessageReceivedCallback messageReceivedCallback;
                    synchronized (dg.this.lE) {
                        messageReceivedCallback = (Cast.MessageReceivedCallback) dg.this.lE.get(str);
                    }
                    if (messageReceivedCallback != null) {
                        messageReceivedCallback.onMessageReceived(dg.this.lC, str, str2);
                        return;
                    }
                    dg.lA.b("Discarded message for unknown namespace '%s'", str);
                }
            });
        }

        public void b(String str, double d, boolean z) {
            final String str2 = str;
            final double d2 = d;
            final boolean z2 = z;
            dg.this.mHandler.post(new Runnable() {
                public void run() {
                    dg.this.a(str2, d2, z2);
                }
            });
        }

        public void b(String str, byte[] bArr) {
            dg.lA.b("IGNORING: Receive (type=binary, ns=%s) <%d bytes>", str, Integer.valueOf(bArr.length));
        }

        public void onApplicationDisconnected(final int i) {
            String unused = dg.this.lK = null;
            String unused2 = dg.this.lL = null;
            if (!x(i) && dg.this.kw != null) {
                dg.this.mHandler.post(new Runnable() {
                    public void run() {
                        if (dg.this.kw != null) {
                            dg.this.kw.onApplicationDisconnected(i);
                        }
                    }
                });
            }
        }

        public void t(int i) {
            dg.lA.b("ICastDeviceControllerListener.onDisconnected", new Object[0]);
            boolean unused = dg.this.lI = false;
            ApplicationMetadata unused2 = dg.this.lB = null;
            if (i != 0) {
                dg.this.I(2);
            }
        }

        public void u(int i) {
            synchronized (dg.lQ) {
                if (dg.this.lO != null) {
                    dg.this.lO.a(new a(new Status(i)));
                    a.c unused = dg.this.lO = null;
                }
            }
        }

        public void v(int i) {
            x(i);
        }

        public void w(int i) {
            x(i);
        }
    };
    /* access modifiers changed from: private */
    public final Map<String, Cast.MessageReceivedCallback> lE = new HashMap();
    private final long lF;
    private String lG = null;
    private boolean lH;
    /* access modifiers changed from: private */
    public boolean lI = false;
    private final AtomicLong lJ = new AtomicLong(0);
    /* access modifiers changed from: private */
    public String lK;
    /* access modifiers changed from: private */
    public String lL;
    private Bundle lM;
    /* access modifiers changed from: private */
    public Map<Long, a.c<Status>> lN = new HashMap();
    /* access modifiers changed from: private */
    public a.c<Cast.ApplicationConnectionResult> lO;
    /* access modifiers changed from: private */
    public a.c<Status> lP;
    private double lb = 0.0d;
    private boolean lc = false;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler(Looper.getMainLooper());

    private static final class a implements Cast.ApplicationConnectionResult {
        private final Status jY;
        private final ApplicationMetadata lX;
        private final String lY;
        private final String lZ;
        private final boolean ma;

        public a(Status status) {
            this(status, (ApplicationMetadata) null, (String) null, (String) null, false);
        }

        public a(Status status, ApplicationMetadata applicationMetadata, String str, String str2, boolean z) {
            this.jY = status;
            this.lX = applicationMetadata;
            this.lY = str;
            this.lZ = str2;
            this.ma = z;
        }

        public ApplicationMetadata getApplicationMetadata() {
            return this.lX;
        }

        public String getApplicationStatus() {
            return this.lY;
        }

        public String getSessionId() {
            return this.lZ;
        }

        public Status getStatus() {
            return this.jY;
        }

        public boolean getWasLaunched() {
            return this.ma;
        }
    }

    public dg(Context context, CastDevice castDevice, long j, Cast.Listener listener, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, connectionCallbacks, onConnectionFailedListener, (String[]) null);
        this.lC = castDevice;
        this.kw = listener;
        this.lF = j;
    }

    /* access modifiers changed from: private */
    public void a(String str, double d, boolean z) {
        boolean z2;
        boolean z3;
        if (!dh.a(str, this.lG)) {
            this.lG = str;
            z2 = true;
        } else {
            z2 = false;
        }
        if (this.kw != null && (z2 || this.lH)) {
            this.kw.onApplicationStatusChanged();
        }
        if (d != this.lb) {
            this.lb = d;
            z3 = true;
        } else {
            z3 = false;
        }
        if (z != this.lc) {
            this.lc = z;
            z3 = true;
        }
        lA.b("hasChange=%b, mFirstStatusUpdate=%b", Boolean.valueOf(z3), Boolean.valueOf(this.lH));
        if (this.kw != null && (z3 || this.lH)) {
            this.kw.onVolumeChanged();
        }
        this.lH = false;
    }

    private void aX() throws IllegalStateException {
        if (!this.lI) {
            throw new IllegalStateException("not connected to a device");
        }
    }

    private void d(a.c<Cast.ApplicationConnectionResult> cVar) {
        synchronized (lQ) {
            if (this.lO != null) {
                this.lO.a(new a(new Status(2002)));
            }
            this.lO = cVar;
        }
    }

    private void f(a.c<Status> cVar) {
        synchronized (lR) {
            if (this.lP != null) {
                cVar.a(new Status(2001));
            } else {
                this.lP = cVar;
            }
        }
    }

    public void C(String str) throws IllegalArgumentException, IllegalStateException, RemoteException {
        Cast.MessageReceivedCallback remove;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Channel namespace cannot be null or empty");
        }
        synchronized (this.lE) {
            remove = this.lE.remove(str);
        }
        if (remove != null) {
            ((di) bQ()).F(str);
        }
    }

    public void a(double d) throws IllegalArgumentException, IllegalStateException, RemoteException {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Volume cannot be " + d);
        }
        ((di) bQ()).a(d, this.lb, this.lc);
    }

    /* access modifiers changed from: protected */
    public void a(int i, IBinder iBinder, Bundle bundle) {
        if (i == 0 || i == 1001) {
            this.lI = true;
            this.lH = true;
        } else {
            this.lI = false;
        }
        if (i == 1001) {
            this.lM = new Bundle();
            this.lM.putBoolean(Cast.EXTRA_APP_NO_LONGER_RUNNING, true);
            i = 0;
        }
        super.a(i, iBinder, bundle);
    }

    /* access modifiers changed from: protected */
    public void a(ec ecVar, dw.e eVar) throws RemoteException {
        Bundle bundle = new Bundle();
        lA.b("getServiceFromBroker(): mLastApplicationId=%s, mLastSessionId=%s", this.lK, this.lL);
        this.lC.putInBundle(bundle);
        bundle.putLong("com.google.android.gms.cast.EXTRA_CAST_FLAGS", this.lF);
        if (this.lK != null) {
            bundle.putString("last_application_id", this.lK);
            if (this.lL != null) {
                bundle.putString("last_session_id", this.lL);
            }
        }
        ecVar.a((eb) eVar, (int) GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), this.lD.asBinder(), bundle);
    }

    public void a(String str, Cast.MessageReceivedCallback messageReceivedCallback) throws IllegalArgumentException, IllegalStateException, RemoteException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Channel namespace cannot be null or empty");
        }
        C(str);
        if (messageReceivedCallback != null) {
            synchronized (this.lE) {
                this.lE.put(str, messageReceivedCallback);
            }
            ((di) bQ()).E(str);
        }
    }

    public void a(String str, a.c<Status> cVar) throws IllegalStateException, RemoteException {
        f(cVar);
        ((di) bQ()).D(str);
    }

    public void a(String str, String str2, a.c<Status> cVar) throws IllegalArgumentException, IllegalStateException, RemoteException {
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("The message payload cannot be null or empty");
        } else if (str == null || str.length() > 128) {
            throw new IllegalArgumentException("Invalid namespace length");
        } else if (str2.length() > 65536) {
            throw new IllegalArgumentException("Message exceeds maximum size");
        } else {
            aX();
            long incrementAndGet = this.lJ.incrementAndGet();
            ((di) bQ()).a(str, str2, incrementAndGet);
            this.lN.put(Long.valueOf(incrementAndGet), cVar);
        }
    }

    public void a(String str, boolean z, a.c<Cast.ApplicationConnectionResult> cVar) throws IllegalStateException, RemoteException {
        d(cVar);
        ((di) bQ()).c(str, z);
    }

    public Bundle aU() {
        if (this.lM == null) {
            return super.aU();
        }
        Bundle bundle = this.lM;
        this.lM = null;
        return bundle;
    }

    public void aV() throws IllegalStateException, RemoteException {
        ((di) bQ()).aV();
    }

    public double aW() throws IllegalStateException {
        aX();
        return this.lb;
    }

    /* access modifiers changed from: protected */
    public String am() {
        return "com.google.android.gms.cast.service.BIND_CAST_DEVICE_CONTROLLER_SERVICE";
    }

    /* access modifiers changed from: protected */
    public String an() {
        return "com.google.android.gms.cast.internal.ICastDeviceController";
    }

    public void b(String str, String str2, a.c<Cast.ApplicationConnectionResult> cVar) throws IllegalStateException, RemoteException {
        d(cVar);
        ((di) bQ()).b(str, str2);
    }

    public void disconnect() {
        try {
            if (isConnected()) {
                synchronized (this.lE) {
                    this.lE.clear();
                }
                ((di) bQ()).disconnect();
            }
        } catch (RemoteException e) {
            try {
                lA.b("Error while disconnecting the controller interface: %s", e.getMessage());
            } catch (Throwable th) {
                super.disconnect();
                throw th;
            }
        }
        super.disconnect();
    }

    public void e(a.c<Status> cVar) throws IllegalStateException, RemoteException {
        f(cVar);
        ((di) bQ()).bb();
    }

    public ApplicationMetadata getApplicationMetadata() throws IllegalStateException {
        aX();
        return this.lB;
    }

    public String getApplicationStatus() throws IllegalStateException {
        aX();
        return this.lG;
    }

    public boolean isMute() throws IllegalStateException {
        aX();
        return this.lc;
    }

    public void n(boolean z) throws IllegalStateException, RemoteException {
        ((di) bQ()).a(z, this.lb, this.lc);
    }

    /* access modifiers changed from: protected */
    /* renamed from: u */
    public di p(IBinder iBinder) {
        return di.a.v(iBinder);
    }
}
