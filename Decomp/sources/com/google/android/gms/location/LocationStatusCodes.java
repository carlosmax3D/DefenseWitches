package com.google.android.gms.location;

public final class LocationStatusCodes {
    public static final int ERROR = 1;
    public static final int GEOFENCE_NOT_AVAILABLE = 1000;
    public static final int GEOFENCE_TOO_MANY_GEOFENCES = 1001;
    public static final int GEOFENCE_TOO_MANY_PENDING_INTENTS = 1002;
    public static final int SUCCESS = 0;

    private LocationStatusCodes() {
    }

    public static int aR(int i) {
        if (i >= 0 && i <= 1) {
            return i;
        }
        if (1000 > i || i > 1002) {
            return 1;
        }
        return i;
    }
}
