package com.ansca.corona.version.android8;

import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.ansca.corona.version.IAndroidVersionSpecific;

public class AndroidVersionSpecific implements IAndroidVersionSpecific {
    public int apiVersion() {
        return 8;
    }

    public int audioChannelMono() {
        return 16;
    }

    public int inputTypeFlagsNoSuggestions() {
        return AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END;
    }
}
