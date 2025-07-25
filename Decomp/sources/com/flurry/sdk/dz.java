package com.flurry.sdk;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import com.flurry.sdk.dq;
import com.flurry.sdk.fb;

public class dz implements dq.a, fb.a {
    /* access modifiers changed from: private */
    public static final String c = dz.class.getSimpleName();
    private static dz q;
    boolean a = false;
    boolean b;
    private final int d = 3;
    private final long e = 10000;
    private final long f = 90000;
    private final long g = 0;
    private long h = 0;
    private LocationManager i;
    private Criteria j;
    /* access modifiers changed from: private */
    public Location k;
    private a l = new a();
    private String m;
    private int n = 0;
    private int o = 0;
    private volatile Location p;

    class a implements LocationListener {
        public a() {
        }

        public void onLocationChanged(Location location) {
            if (location != null) {
                Location unused = dz.this.k = location;
            }
            eo.a(4, dz.c, "Location received");
            if (dz.a(dz.this) >= 3) {
                eo.a(4, dz.c, "Max location reports reached, stopping");
                dz.this.i();
            }
        }

        public void onProviderDisabled(String str) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
        }
    }

    private dz() {
        dq a2 = dp.a();
        this.j = (Criteria) a2.a("LocationCriteria");
        a2.a("LocationCriteria", (dq.a) this);
        eo.a(4, c, "initSettings, LocationCriteria = " + this.j);
        this.b = ((Boolean) a2.a("ReportLocation")).booleanValue();
        a2.a("ReportLocation", (dq.a) this);
        eo.a(4, c, "initSettings, ReportLocation = " + this.b);
    }

    static /* synthetic */ int a(dz dzVar) {
        int i2 = dzVar.o + 1;
        dzVar.o = i2;
        return i2;
    }

    public static synchronized dz a() {
        dz dzVar;
        synchronized (dz.class) {
            if (q == null) {
                q = new dz();
            }
            dzVar = q;
        }
        return dzVar;
    }

    private void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.i.requestLocationUpdates(str, 10000, 0.0f, this.l, Looper.getMainLooper());
        }
    }

    private Location b(String str) {
        if (!TextUtils.isEmpty(str)) {
            return this.i.getLastKnownLocation(str);
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void i() {
        this.i.removeUpdates(this.l);
        this.a = false;
        this.o = 0;
        this.h = 0;
        m();
        eo.a(4, c, "LocationProvider stopped");
    }

    private void j() {
        if (this.b && this.p == null) {
            Context b2 = Cdo.a().b();
            if (b2.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0 || b2.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0) {
                i();
                String k2 = k();
                a(k2);
                this.k = b(k2);
                this.h = System.currentTimeMillis() + 90000;
                l();
                this.a = true;
                eo.a(4, c, "LocationProvider started");
            }
        }
    }

    private String k() {
        Criteria criteria = this.j;
        if (criteria == null) {
            criteria = new Criteria();
        }
        String bestProvider = TextUtils.isEmpty(this.m) ? this.i.getBestProvider(criteria, true) : this.m;
        eo.a(4, c, "provider = " + bestProvider);
        return bestProvider;
    }

    private void l() {
        eo.a(4, c, "Register location timer");
        fa.a().a(this);
    }

    private void m() {
        eo.a(4, c, "Unregister location timer");
        fa.a().b(this);
    }

    public void a(float f2, float f3) {
        this.p = new Location("Explicit");
        this.p.setLatitude((double) f2);
        this.p.setLongitude((double) f3);
    }

    public void a(fb fbVar) {
        if (this.h > 0 && this.h < System.currentTimeMillis()) {
            eo.a(4, c, "No location received in 90 seconds , stopping LocationManager");
            i();
        }
    }

    public void a(String str, Object obj) {
        if (str.equals("LocationCriteria")) {
            this.j = (Criteria) obj;
            eo.a(4, c, "onSettingUpdate, LocationCriteria = " + this.j);
            if (this.a) {
                j();
            }
        } else if (str.equals("ReportLocation")) {
            this.b = ((Boolean) obj).booleanValue();
            eo.a(4, c, "onSettingUpdate, ReportLocation = " + this.b);
            if (!this.b) {
                i();
            } else if (!this.a && this.n > 0) {
                j();
            }
        } else {
            eo.a(6, c, "LocationProvider internal error! Had to be LocationCriteria or ReportLocation key.");
        }
    }

    public synchronized void b() {
        if (this.i == null) {
            this.i = (LocationManager) Cdo.a().b().getSystemService("location");
        }
    }

    public synchronized void c() {
        eo.a(4, c, "Location provider subscribed");
        this.n++;
        if (!this.a && this.o < 3) {
            j();
        }
    }

    public synchronized void d() {
        eo.a(4, c, "Location provider unsubscribed");
        if (this.n <= 0) {
            eo.a(6, c, "Error! Unsubscribed too many times!");
        } else {
            this.n--;
            if (this.n == 0) {
                i();
            }
        }
    }

    public void e() {
        this.p = null;
    }

    public Location f() {
        Location location = null;
        if (this.p != null) {
            return this.p;
        }
        if (this.b) {
            Location b2 = b(k());
            if (b2 != null) {
                this.k = b2;
            }
            location = this.k;
        }
        eo.a(4, c, "getLocation() = " + location);
        return location;
    }

    public void g() {
        this.n = 0;
        i();
    }
}
