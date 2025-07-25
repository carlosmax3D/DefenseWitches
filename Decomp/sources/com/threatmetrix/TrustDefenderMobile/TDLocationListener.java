package com.threatmetrix.TrustDefenderMobile;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

class TDLocationListener implements LocationListener {
    private static final String TAG = TDLocationListener.class.getName();
    private static final int TWO_MINUTES = 120000;
    private Location m_currentBestLocation = null;

    private boolean isBetterLocation(Location location, Location location2) {
        if (location2 == null) {
            return true;
        }
        long time = location.getTime() - location2.getTime();
        boolean z = time > 120000;
        boolean z2 = time < -120000;
        boolean z3 = time > 0;
        if (z) {
            return true;
        }
        if (z2) {
            return false;
        }
        int accuracy = (int) (location.getAccuracy() - location2.getAccuracy());
        boolean z4 = accuracy > 0;
        boolean z5 = accuracy < 0;
        boolean z6 = accuracy > 200;
        String provider = location.getProvider();
        String provider2 = location2.getProvider();
        boolean equals = provider == null ? provider2 == null : provider.equals(provider2);
        if (z5) {
            return true;
        }
        if (!z3 || z4) {
            return z3 && !z6 && equals;
        }
        return true;
    }

    private static boolean isSameProvider(String str, String str2) {
        return str == null ? str2 == null : str.equals(str2);
    }

    public final Location getLastLocation() {
        if (this.m_currentBestLocation != null) {
            return new Location(this.m_currentBestLocation);
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008f, code lost:
        if (r5 != false) goto L_0x0091;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0095, code lost:
        if (r3 == false) goto L_0x0097;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onLocationChanged(android.location.Location r11) {
        /*
            r10 = this;
            r2 = 0
            r1 = 1
            java.lang.String r0 = TAG
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = "onLocationChanged() : "
            r0.<init>(r3)
            java.lang.String r3 = r11.getProvider()
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r3 = ":"
            java.lang.StringBuilder r0 = r0.append(r3)
            double r4 = r11.getLatitude()
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.String r3 = ":"
            java.lang.StringBuilder r0 = r0.append(r3)
            double r4 = r11.getLongitude()
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.String r3 = ":"
            java.lang.StringBuilder r0 = r0.append(r3)
            float r3 = r11.getAccuracy()
            r0.append(r3)
            android.location.Location r7 = r10.m_currentBestLocation
            if (r7 != 0) goto L_0x0045
        L_0x0040:
            if (r1 == 0) goto L_0x0044
            r10.m_currentBestLocation = r11
        L_0x0044:
            return
        L_0x0045:
            long r4 = r11.getTime()
            long r8 = r7.getTime()
            long r4 = r4 - r8
            r8 = 120000(0x1d4c0, double:5.9288E-319)
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 <= 0) goto L_0x0099
            r3 = r1
        L_0x0056:
            r8 = -120000(0xfffffffffffe2b40, double:NaN)
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 >= 0) goto L_0x009b
            r0 = r1
        L_0x005e:
            r8 = 0
            int r4 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r4 <= 0) goto L_0x009d
            r6 = r1
        L_0x0065:
            if (r3 != 0) goto L_0x0040
            if (r0 != 0) goto L_0x0097
            float r0 = r11.getAccuracy()
            float r3 = r7.getAccuracy()
            float r0 = r0 - r3
            int r0 = (int) r0
            if (r0 <= 0) goto L_0x009f
            r5 = r1
        L_0x0076:
            if (r0 >= 0) goto L_0x00a1
            r4 = r1
        L_0x0079:
            r3 = 200(0xc8, float:2.8E-43)
            if (r0 <= r3) goto L_0x00a3
            r0 = r1
        L_0x007e:
            java.lang.String r3 = r11.getProvider()
            java.lang.String r7 = r7.getProvider()
            if (r3 != 0) goto L_0x00a7
            if (r7 != 0) goto L_0x00a5
            r3 = r1
        L_0x008b:
            if (r4 != 0) goto L_0x0040
            if (r6 == 0) goto L_0x0091
            if (r5 == 0) goto L_0x0040
        L_0x0091:
            if (r6 == 0) goto L_0x0097
            if (r0 != 0) goto L_0x0097
            if (r3 != 0) goto L_0x0040
        L_0x0097:
            r1 = r2
            goto L_0x0040
        L_0x0099:
            r3 = r2
            goto L_0x0056
        L_0x009b:
            r0 = r2
            goto L_0x005e
        L_0x009d:
            r6 = r2
            goto L_0x0065
        L_0x009f:
            r5 = r2
            goto L_0x0076
        L_0x00a1:
            r4 = r2
            goto L_0x0079
        L_0x00a3:
            r0 = r2
            goto L_0x007e
        L_0x00a5:
            r3 = r2
            goto L_0x008b
        L_0x00a7:
            boolean r3 = r3.equals(r7)
            goto L_0x008b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.threatmetrix.TrustDefenderMobile.TDLocationListener.onLocationChanged(android.location.Location):void");
    }

    public void onProviderDisabled(String str) {
        String str2 = TAG;
        new StringBuilder("onProviderDisabled: ").append(str);
    }

    public void onProviderEnabled(String str) {
        String str2 = TAG;
        new StringBuilder("onProviderEnabled: ").append(str);
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
        String str2 = TAG;
        new StringBuilder("onStatusChanged: ").append(str).append(" status: ").append(i == 2 ? "available " : i == 1 ? "temporarily unavailable" : i == 0 ? "Out of Service" : "unknown");
    }
}
