package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.gk;
import com.google.android.gms.internal.gl;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationStatusCodes;
import java.util.HashMap;
import java.util.List;

public class gn extends dw<gl> {
    private final String jG;
    private final gq<gl> xP = new c();
    private final gm xV;
    private final HashMap xW = new HashMap();
    private final String xX;

    private final class a extends dw<gl>.b<LocationClient.OnAddGeofencesResultListener> {
        private final int mC;
        private final String[] xY;

        public a(LocationClient.OnAddGeofencesResultListener onAddGeofencesResultListener, int i, String[] strArr) {
            super(onAddGeofencesResultListener);
            this.mC = LocationStatusCodes.aR(i);
            this.xY = strArr;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void b(LocationClient.OnAddGeofencesResultListener onAddGeofencesResultListener) {
            if (onAddGeofencesResultListener != null) {
                onAddGeofencesResultListener.onAddGeofencesResult(this.mC, this.xY);
            }
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }
    }

    private static final class b extends gk.a {
        private LocationClient.OnAddGeofencesResultListener ya;
        private LocationClient.OnRemoveGeofencesResultListener yb;
        private gn yc;

        public b(LocationClient.OnAddGeofencesResultListener onAddGeofencesResultListener, gn gnVar) {
            this.ya = onAddGeofencesResultListener;
            this.yb = null;
            this.yc = gnVar;
        }

        public b(LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener, gn gnVar) {
            this.yb = onRemoveGeofencesResultListener;
            this.ya = null;
            this.yc = gnVar;
        }

        public void onAddGeofencesResult(int i, String[] strArr) throws RemoteException {
            if (this.yc == null) {
                Log.wtf("LocationClientImpl", "onAddGeofenceResult called multiple times");
                return;
            }
            gn gnVar = this.yc;
            gn gnVar2 = this.yc;
            gnVar2.getClass();
            gnVar.a((dw<T>.b<?>) new a(this.ya, i, strArr));
            this.yc = null;
            this.ya = null;
            this.yb = null;
        }

        public void onRemoveGeofencesByPendingIntentResult(int i, PendingIntent pendingIntent) {
            if (this.yc == null) {
                Log.wtf("LocationClientImpl", "onRemoveGeofencesByPendingIntentResult called multiple times");
                return;
            }
            gn gnVar = this.yc;
            gn gnVar2 = this.yc;
            gnVar2.getClass();
            gnVar.a((dw<T>.b<?>) new d(gnVar2, 1, this.yb, i, pendingIntent));
            this.yc = null;
            this.ya = null;
            this.yb = null;
        }

        public void onRemoveGeofencesByRequestIdsResult(int i, String[] strArr) {
            if (this.yc == null) {
                Log.wtf("LocationClientImpl", "onRemoveGeofencesByRequestIdsResult called multiple times");
                return;
            }
            gn gnVar = this.yc;
            gn gnVar2 = this.yc;
            gnVar2.getClass();
            gnVar.a((dw<T>.b<?>) new d(2, this.yb, i, strArr));
            this.yc = null;
            this.ya = null;
            this.yb = null;
        }
    }

    private final class c implements gq<gl> {
        private c() {
        }

        public void bP() {
            gn.this.bP();
        }

        /* renamed from: dJ */
        public gl bQ() {
            return (gl) gn.this.bQ();
        }
    }

    private final class d extends dw<gl>.b<LocationClient.OnRemoveGeofencesResultListener> {
        private final int mC;
        private final PendingIntent mPendingIntent;
        private final String[] xY;
        private final int yd;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public d(gn gnVar, int i, LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener, int i2, PendingIntent pendingIntent) {
            super(onRemoveGeofencesResultListener);
            boolean z = true;
            gn.this = gnVar;
            ds.p(i != 1 ? false : z);
            this.yd = i;
            this.mC = LocationStatusCodes.aR(i2);
            this.mPendingIntent = pendingIntent;
            this.xY = null;
        }

        public d(int i, LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener, int i2, String[] strArr) {
            super(onRemoveGeofencesResultListener);
            ds.p(i == 2);
            this.yd = i;
            this.mC = LocationStatusCodes.aR(i2);
            this.xY = strArr;
            this.mPendingIntent = null;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void b(LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener) {
            if (onRemoveGeofencesResultListener != null) {
                switch (this.yd) {
                    case 1:
                        onRemoveGeofencesResultListener.onRemoveGeofencesByPendingIntentResult(this.mC, this.mPendingIntent);
                        return;
                    case 2:
                        onRemoveGeofencesResultListener.onRemoveGeofencesByRequestIdsResult(this.mC, this.xY);
                        return;
                    default:
                        Log.wtf("LocationClientImpl", "Unsupported action: " + this.yd);
                        return;
                }
            }
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }
    }

    public gn(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, String str) {
        super(context, connectionCallbacks, onConnectionFailedListener, new String[0]);
        this.xV = new gm(context, this.xP);
        this.xX = str;
        this.jG = null;
    }

    /* access modifiers changed from: protected */
    /* renamed from: M */
    public gl p(IBinder iBinder) {
        return gl.a.L(iBinder);
    }

    /* access modifiers changed from: protected */
    public void a(ec ecVar, dw.e eVar) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putString("client_name", this.xX);
        ecVar.e(eVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), bundle);
    }

    public void addGeofences(List<go> list, PendingIntent pendingIntent, LocationClient.OnAddGeofencesResultListener onAddGeofencesResultListener) {
        bP();
        eg.b(list != null && list.size() > 0, (Object) "At least one geofence must be specified.");
        eg.b(pendingIntent, (Object) "PendingIntent must be specified.");
        eg.b(onAddGeofencesResultListener, (Object) "OnAddGeofencesResultListener not provided.");
        try {
            ((gl) bQ()).a(list, pendingIntent, (gk) onAddGeofencesResultListener == null ? null : new b(onAddGeofencesResultListener, this), getContext().getPackageName());
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: protected */
    public String am() {
        return "com.google.android.location.internal.GoogleLocationManagerService.START";
    }

    /* access modifiers changed from: protected */
    public String an() {
        return "com.google.android.gms.location.internal.IGoogleLocationManagerService";
    }

    public void disconnect() {
        synchronized (this.xV) {
            if (isConnected()) {
                this.xV.removeAllListeners();
                this.xV.dI();
            }
            super.disconnect();
        }
    }

    public Location getLastLocation() {
        return this.xV.getLastLocation();
    }

    public void removeActivityUpdates(PendingIntent pendingIntent) {
        bP();
        eg.f(pendingIntent);
        try {
            ((gl) bQ()).removeActivityUpdates(pendingIntent);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeGeofences(PendingIntent pendingIntent, LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener) {
        bP();
        eg.b(pendingIntent, (Object) "PendingIntent must be specified.");
        eg.b(onRemoveGeofencesResultListener, (Object) "OnRemoveGeofencesResultListener not provided.");
        try {
            ((gl) bQ()).a(pendingIntent, (gk) onRemoveGeofencesResultListener == null ? null : new b(onRemoveGeofencesResultListener, this), getContext().getPackageName());
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeGeofences(List<String> list, LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener) {
        bP();
        eg.b(list != null && list.size() > 0, (Object) "geofenceRequestIds can't be null nor empty.");
        eg.b(onRemoveGeofencesResultListener, (Object) "OnRemoveGeofencesResultListener not provided.");
        try {
            ((gl) bQ()).a((String[]) list.toArray(new String[0]), (gk) onRemoveGeofencesResultListener == null ? null : new b(onRemoveGeofencesResultListener, this), getContext().getPackageName());
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeLocationUpdates(PendingIntent pendingIntent) {
        this.xV.removeLocationUpdates(pendingIntent);
    }

    public void removeLocationUpdates(LocationListener locationListener) {
        this.xV.removeLocationUpdates(locationListener);
    }

    public void requestActivityUpdates(long j, PendingIntent pendingIntent) {
        boolean z = true;
        bP();
        eg.f(pendingIntent);
        if (j < 0) {
            z = false;
        }
        eg.b(z, (Object) "detectionIntervalMillis must be >= 0");
        try {
            ((gl) bQ()).a(j, true, pendingIntent);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void requestLocationUpdates(LocationRequest locationRequest, PendingIntent pendingIntent) {
        this.xV.requestLocationUpdates(locationRequest, pendingIntent);
    }

    public void requestLocationUpdates(LocationRequest locationRequest, LocationListener locationListener) {
        requestLocationUpdates(locationRequest, locationListener, (Looper) null);
    }

    public void requestLocationUpdates(LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
        synchronized (this.xV) {
            this.xV.requestLocationUpdates(locationRequest, locationListener, looper);
        }
    }

    public void setMockLocation(Location location) {
        this.xV.setMockLocation(location);
    }

    public void setMockMode(boolean z) {
        this.xV.setMockMode(z);
    }
}
