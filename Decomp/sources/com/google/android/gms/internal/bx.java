package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.internal.bw;

public abstract class bx extends cm {
    private final bz fw;
    private final bw.a hn;

    public static final class a extends bx {
        private final Context mContext;

        public a(Context context, bz bzVar, bw.a aVar) {
            super(bzVar, aVar);
            this.mContext = context;
        }

        public void ak() {
        }

        public cd al() {
            return ce.a(this.mContext, new ar());
        }
    }

    public static final class b extends bx implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
        private final Object fx = new Object();
        private final bw.a hn;
        private final by ho;

        public b(Context context, bz bzVar, bw.a aVar) {
            super(bzVar, aVar);
            this.hn = aVar;
            this.ho = new by(context, this, this, bzVar.ej.iL);
            this.ho.connect();
        }

        public void ak() {
            synchronized (this.fx) {
                if (this.ho.isConnected() || this.ho.isConnecting()) {
                    this.ho.disconnect();
                }
            }
        }

        public cd al() {
            cd cdVar;
            synchronized (this.fx) {
                try {
                    cdVar = this.ho.ao();
                } catch (IllegalStateException e) {
                    cdVar = null;
                }
            }
            return cdVar;
        }

        public void onConnected(Bundle bundle) {
            start();
        }

        public void onConnectionFailed(ConnectionResult connectionResult) {
            this.hn.a(new cb(0));
        }

        public void onDisconnected() {
            ct.r("Disconnected from remote ad request service.");
        }
    }

    public bx(bz bzVar, bw.a aVar) {
        this.fw = bzVar;
        this.hn = aVar;
    }

    private static cb a(cd cdVar, bz bzVar) {
        try {
            return cdVar.b(bzVar);
        } catch (RemoteException e) {
            ct.b("Could not fetch ad response from ad request service.", e);
            return null;
        }
    }

    /* JADX INFO: finally extract failed */
    public final void ai() {
        cb a2;
        try {
            cd al = al();
            if (al == null) {
                a2 = new cb(0);
            } else {
                a2 = a(al, this.fw);
                if (a2 == null) {
                    a2 = new cb(0);
                }
            }
            ak();
            this.hn.a(a2);
        } catch (Throwable th) {
            ak();
            throw th;
        }
    }

    public abstract void ak();

    public abstract cd al();

    public final void onStop() {
        ak();
    }
}
