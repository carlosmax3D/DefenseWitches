package com.ansca.corona;

import android.app.Activity;
import android.provider.Settings;

public class CoronaActivityInfo {
    private Activity fActivity;
    private boolean fSupportsOrientationChanges;

    public CoronaActivityInfo(Activity activity) {
        this.fActivity = activity;
    }

    private boolean supportsOrientationChanges() {
        if (this.fActivity == null || Settings.System.getInt(this.fActivity.getContentResolver(), "accelerometer_rotation", 0) == 0) {
            return false;
        }
        switch (this.fActivity.getRequestedOrientation()) {
            case -1:
            case 4:
            case 6:
            case 7:
            case 10:
            case 11:
            case 12:
            case 13:
                return true;
            default:
                return false;
        }
    }

    public boolean hasFixedOrientation() {
        return !supportsOrientationChanges();
    }
}
