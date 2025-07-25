package com.tapjoy.mraid.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tapjoy.mraid.controller.Display;

public class ConfigBroadcastReceiver extends BroadcastReceiver {
    private int mLastOrientation = this.mMraidDisplay.getOrientation();
    private Display mMraidDisplay;

    public ConfigBroadcastReceiver(Display display) {
        this.mMraidDisplay = display;
    }

    public void onReceive(Context context, Intent intent) {
        int orientation;
        if (intent.getAction().equals("android.intent.action.CONFIGURATION_CHANGED") && (orientation = this.mMraidDisplay.getOrientation()) != this.mLastOrientation) {
            this.mLastOrientation = orientation;
            this.mMraidDisplay.onOrientationChanged(this.mLastOrientation);
        }
    }
}
