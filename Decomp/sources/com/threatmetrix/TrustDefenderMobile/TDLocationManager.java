package com.threatmetrix.TrustDefenderMobile;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.util.Log;

class TDLocationManager {
    private static final String TAG = TDLocationManager.class.getName();
    private int m_accuracy;
    private Context m_context;
    private long m_highPowerUpdateTime;
    private Location m_location;
    private TDLocationListener m_locationListener;
    private LocationManager m_locationManager;
    private long m_lowPowerUpdateTime;
    private boolean m_paused = false;

    TDLocationManager() {
    }

    private boolean enabled() {
        return (this.m_locationManager == null || this.m_locationListener == null) ? false : true;
    }

    private void getLastLocation() {
        float f = Float.MAX_VALUE;
        long j = 0;
        Location location = null;
        String str = TAG;
        for (String lastKnownLocation : this.m_locationManager.getAllProviders()) {
            Location lastKnownLocation2 = this.m_locationManager.getLastKnownLocation(lastKnownLocation);
            if (lastKnownLocation2 != null) {
                String str2 = TAG;
                new StringBuilder("getLastLocation() : ").append(lastKnownLocation2.getProvider()).append(":").append(lastKnownLocation2.getLatitude()).append(":").append(lastKnownLocation2.getLongitude()).append(":").append(lastKnownLocation2.getAccuracy());
                float accuracy = lastKnownLocation2.getAccuracy();
                long time = lastKnownLocation2.getTime();
                if (time > this.m_lowPowerUpdateTime && accuracy < f) {
                    location = lastKnownLocation2;
                    f = accuracy;
                    j = time;
                } else if (time < this.m_lowPowerUpdateTime && f == Float.MAX_VALUE && time > j) {
                    location = lastKnownLocation2;
                    j = time;
                }
            }
        }
        if (location != null) {
            this.m_location = new Location(location);
        }
    }

    private boolean registerLocationServices() {
        String str = TAG;
        new StringBuilder("registerLocationServices: low power ").append(this.m_lowPowerUpdateTime).append(" high power ").append(this.m_highPowerUpdateTime).append(" with accuracy ").append(this.m_accuracy);
        this.m_locationManager = (LocationManager) this.m_context.getSystemService("location");
        if (this.m_locationManager == null) {
            Log.e(TAG, "Insufficient permissions to acquire location manager");
            return false;
        }
        if (this.m_locationListener != null) {
            this.m_locationManager.removeUpdates(this.m_locationListener);
        }
        Criteria criteria = new Criteria();
        criteria.setAccuracy(this.m_accuracy);
        criteria.setAltitudeRequired(false);
        criteria.setBearingAccuracy(0);
        criteria.setCostAllowed(false);
        criteria.setSpeedAccuracy(0);
        criteria.setSpeedRequired(false);
        criteria.setVerticalAccuracy(0);
        Criteria criteria2 = new Criteria();
        criteria2.setPowerRequirement(1);
        criteria2.setAccuracy(2);
        String bestProvider = this.m_locationManager.getBestProvider(criteria, true);
        if (bestProvider != null) {
            String str2 = TAG;
            new StringBuilder("fine provider: ").append(bestProvider);
        }
        String bestProvider2 = this.m_locationManager.getBestProvider(criteria2, true);
        if (bestProvider2 != null) {
            String str3 = TAG;
            new StringBuilder("course provider: ").append(bestProvider2);
        }
        if (bestProvider == null && bestProvider2 == null) {
            this.m_locationListener = null;
            this.m_locationManager = null;
            Log.e(TAG, "Unable to find location provider, possibly incorrect permissions. Check that android.permission.ACCESS_COARSE_LOCATION or android.permission.ACCESS_FINE_LOCATION is set");
            return false;
        }
        getLastLocation();
        boolean z = false;
        if (!(bestProvider == null || bestProvider2 == null)) {
            z = bestProvider.equals(bestProvider2);
        }
        boolean z2 = false;
        this.m_locationListener = new TDLocationListener();
        try {
            this.m_locationManager.requestLocationUpdates(this.m_lowPowerUpdateTime, 0.0f, criteria2, this.m_locationListener, (Looper) null);
            z2 = true;
            String str4 = TAG;
            new StringBuilder("LocationManager created: ").append(this.m_locationManager.getBestProvider(criteria2, true));
        } catch (SecurityException e) {
            Log.e(TAG, "Security settings error:" + e.getLocalizedMessage());
        }
        if (!z) {
            try {
                this.m_locationManager.requestLocationUpdates(this.m_highPowerUpdateTime, 0.0f, criteria, this.m_locationListener, (Looper) null);
                z2 = true;
                String str5 = TAG;
                new StringBuilder("LocationManager created: ").append(this.m_locationManager.getBestProvider(criteria, true));
            } catch (SecurityException e2) {
                Log.e(TAG, "Security settings error:" + e2.getLocalizedMessage());
            }
        }
        if (z2) {
            return z2;
        }
        this.m_locationListener = null;
        this.m_locationManager = null;
        return z2;
    }

    private void setLocation(Location location) {
        this.m_location = new Location(location);
    }

    public final Location getLocation() {
        Location location = this.m_location;
        return (location != null || this.m_locationListener == null) ? location : this.m_locationListener.getLastLocation();
    }

    public final void pause() {
        if (!this.m_paused && enabled()) {
            String str = TAG;
            this.m_locationManager.removeUpdates(this.m_locationListener);
            this.m_paused = true;
        }
    }

    public final boolean registerLocationServices(Context context, long j, long j2, int i) {
        this.m_context = context;
        this.m_lowPowerUpdateTime = j;
        this.m_highPowerUpdateTime = j2;
        this.m_accuracy = i;
        return registerLocationServices();
    }

    public final void resume() {
        if (this.m_paused) {
            String str = TAG;
            registerLocationServices();
            this.m_paused = false;
        }
    }

    public final void unregister() {
        if (enabled()) {
            this.m_locationManager.removeUpdates(this.m_locationListener);
        }
    }
}
