package com.tapjoy.mraid.controller;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import com.tapjoy.TapjoyLog;
import com.tapjoy.mraid.listener.Loc;
import com.tapjoy.mraid.view.MraidView;

public class MraidLocation extends Abstract {
    private static final String TAG = "MRAID Location";
    final int INTERVAL = 1000;
    private boolean allowLocationServices = false;
    private boolean hasPermission = false;
    private Loc mGps;
    private int mLocListenerCount;
    private LocationManager mLocationManager;
    private Loc mNetwork;

    public MraidLocation(MraidView mraidView, Context context) {
        super(mraidView, context);
        try {
            this.mLocationManager = (LocationManager) context.getSystemService("location");
            if (this.mLocationManager.getProvider("gps") != null) {
                this.mGps = new Loc(context, 1000, this, "gps");
            }
            if (this.mLocationManager.getProvider("network") != null) {
                this.mNetwork = new Loc(context, 1000, this, "network");
            }
            this.hasPermission = true;
        } catch (SecurityException e) {
        }
    }

    private static String formatLocation(Location location) {
        return "{ lat: " + location.getLatitude() + ", lon: " + location.getLongitude() + ", acc: " + location.getAccuracy() + "}";
    }

    public void allowLocationServices(boolean z) {
        this.allowLocationServices = z;
    }

    public boolean allowLocationServices() {
        return this.allowLocationServices;
    }

    public void fail() {
        TapjoyLog.e(TAG, "Location can't be determined");
        this.mMraidView.injectMraidJavaScript("window.mraidview.fireErrorEvent(\"Location cannot be identified\", \"OrmmaLocationController\")");
    }

    /* JADX WARNING: Removed duplicated region for block: B:4:0x002d A[LOOP:0: B:4:0x002d->B:7:0x003f, LOOP_START, PHI: r0 
      PHI: (r0v1 android.location.Location) = (r0v0 android.location.Location), (r0v3 android.location.Location) binds: [B:3:0x0021, B:7:0x003f] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getLocation() {
        /*
            r7 = this;
            r4 = 0
            java.lang.String r3 = "MRAID Location"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "getLocation: hasPermission: "
            java.lang.StringBuilder r5 = r5.append(r6)
            boolean r6 = r7.hasPermission
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.tapjoy.TapjoyLog.d(r3, r5)
            boolean r3 = r7.hasPermission
            if (r3 != 0) goto L_0x0021
            r3 = r4
        L_0x0020:
            return r3
        L_0x0021:
            android.location.LocationManager r3 = r7.mLocationManager
            r5 = 1
            java.util.List r2 = r3.getProviders(r5)
            java.util.Iterator r1 = r2.iterator()
            r0 = 0
        L_0x002d:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x0041
            android.location.LocationManager r5 = r7.mLocationManager
            java.lang.Object r3 = r1.next()
            java.lang.String r3 = (java.lang.String) r3
            android.location.Location r0 = r5.getLastKnownLocation(r3)
            if (r0 == 0) goto L_0x002d
        L_0x0041:
            java.lang.String r3 = "MRAID Location"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "getLocation: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            com.tapjoy.TapjoyLog.d(r3, r5)
            if (r0 == 0) goto L_0x0060
            java.lang.String r3 = formatLocation(r0)
            goto L_0x0020
        L_0x0060:
            r3 = r4
            goto L_0x0020
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tapjoy.mraid.controller.MraidLocation.getLocation():java.lang.String");
    }

    public void startLocationListener() {
        if (this.mLocListenerCount == 0) {
            if (this.mNetwork != null) {
                this.mNetwork.start();
            }
            if (this.mGps != null) {
                this.mGps.start();
            }
        }
        this.mLocListenerCount++;
    }

    public void stopAllListeners() {
        this.mLocListenerCount = 0;
        try {
            this.mGps.stop();
        } catch (Exception e) {
        }
        try {
            this.mNetwork.stop();
        } catch (Exception e2) {
        }
    }

    public void stopLocationListener() {
        this.mLocListenerCount--;
        if (this.mLocListenerCount == 0) {
            if (this.mNetwork != null) {
                this.mNetwork.stop();
            }
            if (this.mGps != null) {
                this.mGps.stop();
            }
        }
    }

    public void success(Location location) {
        String str = "window.mraidview.fireChangeEvent({ location: " + formatLocation(location) + "})";
        TapjoyLog.d(TAG, str);
        this.mMraidView.injectMraidJavaScript(str);
    }
}
