package com.ansca.corona;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

public final class WindowOrientation {
    public static final WindowOrientation LANDSCAPE_LEFT = new WindowOrientation(4, "landscapeLeft");
    public static final WindowOrientation LANDSCAPE_RIGHT = new WindowOrientation(2, "landscapeRight");
    public static final WindowOrientation PORTRAIT_UPRIGHT = new WindowOrientation(1, "portrait");
    public static final WindowOrientation PORTRAIT_UPSIDE_DOWN = new WindowOrientation(3, "portraitUpsideDown");
    public static final WindowOrientation UNKNOWN = new WindowOrientation(0, "unknown");
    private int fCoronaIntegerId;
    private String fCoronaStringId;

    private WindowOrientation(int i, String str) {
        if (str == null) {
            throw new NullPointerException();
        }
        this.fCoronaIntegerId = i;
        this.fCoronaStringId = str;
    }

    public static WindowOrientation fromCurrentWindowUsing(Context context) {
        int i;
        if (context == null) {
            throw new NullPointerException();
        }
        switch (((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRotation()) {
            case 1:
                i = 90;
                break;
            case 2:
                i = 180;
                break;
            case 3:
                i = 270;
                break;
            default:
                i = 0;
                break;
        }
        return fromDegrees(context, i);
    }

    public static WindowOrientation fromDegrees(Context context, int i) {
        if (context == null) {
            throw new NullPointerException();
        }
        boolean z = true;
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        switch (defaultDisplay.getRotation()) {
            case 0:
            case 2:
                if (defaultDisplay.getWidth() >= defaultDisplay.getHeight()) {
                    z = false;
                    break;
                } else {
                    z = true;
                    break;
                }
            case 1:
            case 3:
                if (defaultDisplay.getWidth() <= defaultDisplay.getHeight()) {
                    z = false;
                    break;
                } else {
                    z = true;
                    break;
                }
        }
        if (!z) {
            i = (i + 90) % 360;
        }
        return (i < 45 || i >= 135) ? (i < 135 || i >= 225) ? (i < 225 || i >= 315) ? PORTRAIT_UPRIGHT : LANDSCAPE_LEFT : PORTRAIT_UPSIDE_DOWN : LANDSCAPE_RIGHT;
    }

    public boolean isLandscape() {
        return equals(LANDSCAPE_RIGHT) || equals(LANDSCAPE_LEFT);
    }

    public boolean isPortrait() {
        return equals(PORTRAIT_UPRIGHT) || equals(PORTRAIT_UPSIDE_DOWN);
    }

    public boolean isSupportedBy(Activity activity) {
        if (activity == null) {
            throw new NullPointerException();
        }
        switch (activity.getRequestedOrientation()) {
            case -1:
            case 4:
            case 10:
                return true;
            case 0:
            case 6:
            case 8:
                return isLandscape();
            case 1:
            case 7:
            case 9:
                return isPortrait();
            default:
                return false;
        }
    }

    public int toCoronaIntegerId() {
        return this.fCoronaIntegerId;
    }

    public String toCoronaStringId() {
        return this.fCoronaStringId;
    }

    public String toString() {
        return this.fCoronaStringId;
    }
}
