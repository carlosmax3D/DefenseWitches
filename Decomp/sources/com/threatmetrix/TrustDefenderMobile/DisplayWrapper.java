package com.threatmetrix.TrustDefenderMobile;

import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import java.lang.reflect.Method;

class DisplayWrapper extends WrapperHelper {
    private static final String TAG = DisplayWrapper.class.getName();
    private static final Method m_getHeight = getMethod(Display.class, "getHeight", new Class[0]);
    private static final Method m_getSize = getMethod(Display.class, "getSize", Point.class);
    private static final Method m_getWidth = getMethod(Display.class, "getWidth", new Class[0]);
    private final Display m_display;

    public DisplayWrapper(Display display) {
        this.m_display = display;
    }

    public final int getHeight() {
        Integer num;
        if (m_getSize != null) {
            Point point = new Point();
            invoke(this.m_display, m_getSize, point);
            return point.y;
        } else if (m_getHeight != null && (num = (Integer) invoke(this.m_display, m_getHeight, new Object[0])) != null) {
            return num.intValue();
        } else {
            Log.w(TAG, "unable to get display height");
            return 0;
        }
    }

    public final int getWidth() {
        Integer num;
        if (m_getSize != null) {
            Point point = new Point();
            invoke(this.m_display, m_getSize, point);
            return point.x;
        } else if (m_getWidth != null && (num = (Integer) invoke(this.m_display, m_getWidth, new Object[0])) != null) {
            return num.intValue();
        } else {
            Log.w(TAG, "unable to get display width");
            return 0;
        }
    }
}
