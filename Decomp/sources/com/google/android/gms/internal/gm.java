package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.c;
import java.util.HashMap;

public class gm {
    private final Context mContext;
    private final gq<gl> xP;
    private ContentProviderClient xQ = null;
    private boolean xR = false;
    private HashMap<LocationListener, b> xS = new HashMap<>();

    private static class a extends Handler {
        private final LocationListener xT;

        public a(LocationListener locationListener) {
            this.xT = locationListener;
        }

        public a(LocationListener locationListener, Looper looper) {
            super(looper);
            this.xT = locationListener;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    this.xT.onLocationChanged(new Location((Location) message.obj));
                    return;
                default:
                    Log.e("LocationClientHelper", "unknown message in LocationHandler.handleMessage");
                    return;
            }
        }
    }

    private static class b extends c.a {
        private Handler xU;

        b(LocationListener locationListener, Looper looper) {
            this.xU = looper == null ? new a(locationListener) : new a(locationListener, looper);
        }

        public void onLocationChanged(Location location) {
            if (this.xU == null) {
                Log.e("LocationClientHelper", "Received a location in client after calling removeLocationUpdates.");
                return;
            }
            Message obtain = Message.obtain();
            obtain.what = 1;
            obtain.obj = location;
            this.xU.sendMessage(obtain);
        }

        public void release() {
            this.xU = null;
        }
    }

    public gm(Context context, gq<gl> gqVar) {
        this.mContext = context;
        this.xP = gqVar;
    }

    public void dI() {
        if (this.xR) {
            setMockMode(false);
        }
    }

    public Location getLastLocation() {
        this.xP.bP();
        try {
            return this.xP.bQ().an(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeAllListeners() {
        try {
            synchronized (this.xS) {
                for (b next : this.xS.values()) {
                    if (next != null) {
                        this.xP.bQ().a((c) next);
                    }
                }
                this.xS.clear();
            }
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeLocationUpdates(PendingIntent pendingIntent) {
        this.xP.bP();
        try {
            this.xP.bQ().a(pendingIntent);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeLocationUpdates(LocationListener locationListener) {
        this.xP.bP();
        eg.b(locationListener, (Object) "Invalid null listener");
        synchronized (this.xS) {
            b remove = this.xS.remove(locationListener);
            if (this.xQ != null && this.xS.isEmpty()) {
                this.xQ.release();
                this.xQ = null;
            }
            if (remove != null) {
                remove.release();
                try {
                    this.xP.bQ().a((c) remove);
                } catch (RemoteException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    public void requestLocationUpdates(LocationRequest locationRequest, PendingIntent pendingIntent) {
        this.xP.bP();
        try {
            this.xP.bQ().a(locationRequest, pendingIntent);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void requestLocationUpdates(LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
        this.xP.bP();
        if (looper == null) {
            eg.b(Looper.myLooper(), (Object) "Can't create handler inside thread that has not called Looper.prepare()");
        }
        synchronized (this.xS) {
            b bVar = this.xS.get(locationListener);
            b bVar2 = bVar == null ? new b(locationListener, looper) : bVar;
            this.xS.put(locationListener, bVar2);
            try {
                this.xP.bQ().a(locationRequest, (c) bVar2, this.mContext.getPackageName());
            } catch (RemoteException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public void setMockLocation(Location location) {
        this.xP.bP();
        try {
            this.xP.bQ().setMockLocation(location);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void setMockMode(boolean z) {
        this.xP.bP();
        try {
            this.xP.bQ().setMockMode(z);
            this.xR = z;
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }
}
