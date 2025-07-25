package com.ansca.corona.input;

import android.os.Build;
import android.view.MotionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

public class AxisType {
    public static final AxisType BRAKE = new AxisType(23, 16, "brake");
    public static final AxisType GAS = new AxisType(22, 15, "gas");
    public static final AxisType GENERIC_1 = new AxisType(32, 31, "generic1");
    public static final AxisType GENERIC_10 = new AxisType(41, 40, "generic10");
    public static final AxisType GENERIC_11 = new AxisType(42, 41, "generic11");
    public static final AxisType GENERIC_12 = new AxisType(43, 42, "generic12");
    public static final AxisType GENERIC_13 = new AxisType(44, 43, "generic13");
    public static final AxisType GENERIC_14 = new AxisType(45, 44, "generic14");
    public static final AxisType GENERIC_15 = new AxisType(46, 45, "generic15");
    public static final AxisType GENERIC_16 = new AxisType(47, 46, "generic16");
    public static final AxisType GENERIC_2 = new AxisType(33, 32, "generic2");
    public static final AxisType GENERIC_3 = new AxisType(34, 33, "generic3");
    public static final AxisType GENERIC_4 = new AxisType(35, 34, "generic4");
    public static final AxisType GENERIC_5 = new AxisType(36, 35, "generic5");
    public static final AxisType GENERIC_6 = new AxisType(37, 36, "generic6");
    public static final AxisType GENERIC_7 = new AxisType(38, 37, "generic7");
    public static final AxisType GENERIC_8 = new AxisType(39, 38, "generic8");
    public static final AxisType GENERIC_9 = new AxisType(40, 39, "generic9");
    public static final AxisType HAT_X = new AxisType(15, 11, "hatX");
    public static final AxisType HAT_Y = new AxisType(16, 12, "hatY");
    public static final AxisType HORIZONTAL_SCROLL = new AxisType(10, 21, "horizontalScroll");
    public static final AxisType HOVER_DISTANCE = new AxisType(24, 23, "hoverDistance");
    public static final AxisType HOVER_MAJOR = new AxisType(6, 24, "hoverMajor");
    public static final AxisType HOVER_MINOR = new AxisType(7, 25, "hoverMinor");
    public static final AxisType LEFT_TRIGGER = new AxisType(17, 13, "leftTrigger");
    public static final AxisType LEFT_X = new AxisType(-1, 7, "leftX");
    public static final AxisType LEFT_Y = new AxisType(-1, 8, "leftY");
    public static final AxisType ORIENTATION = new AxisType(8, 22, "orientation");
    public static final AxisType PRESSURE = new AxisType(2, 29, "pressure");
    public static final AxisType RIGHT_TRIGGER = new AxisType(18, 14, "rightTrigger");
    public static final AxisType RIGHT_X = new AxisType(-1, 9, "rightX");
    public static final AxisType RIGHT_Y = new AxisType(-1, 10, "rightY");
    public static final AxisType ROTATION_X = new AxisType(12, 4, "rotationX");
    public static final AxisType ROTATION_Y = new AxisType(13, 5, "rotationY");
    public static final AxisType ROTATION_Z = new AxisType(14, 6, "rotationZ");
    public static final AxisType RUDDER = new AxisType(20, 18, "rudder");
    public static final AxisType THROTTLE = new AxisType(19, 19, "throttle");
    public static final AxisType TILT = new AxisType(25, 30, "tilt");
    public static final AxisType TOUCH_MAJOR = new AxisType(4, 27, "touchMajor");
    public static final AxisType TOUCH_MINOR = new AxisType(5, 28, "touchMinor");
    public static final AxisType TOUCH_SIZE = new AxisType(3, 26, "touchSize");
    public static final AxisType UNKNOWN = new AxisType(-1, 0, "unknown");
    public static final AxisType VERTICAL_SCROLL = new AxisType(9, 20, "verticalScroll");
    public static final AxisType WHEEL = new AxisType(21, 17, "wheel");
    public static final AxisType X = new AxisType(0, 1, "x");
    public static final AxisType Y = new AxisType(1, 2, "y");
    public static final AxisType Z = new AxisType(11, 3, "z");
    private static ArrayList<AxisType> sTypeCollection = null;
    private int fAndroidIntegerId;
    private int fCoronaIntegerId;
    private String fCoronaStringId;

    private static class ApiLevel12 {
        private ApiLevel12() {
        }

        public static String getSymbolicNameFromAndroidIntegerId(int i) {
            return MotionEvent.axisToString(i);
        }
    }

    private AxisType(int i, int i2, String str) {
        this.fAndroidIntegerId = i;
        this.fCoronaIntegerId = i2;
        this.fCoronaStringId = str;
    }

    public static AxisType fromAndroidIntegerId(int i) {
        AxisType axisType;
        if (sTypeCollection == null) {
            try {
                sTypeCollection = new ArrayList<>();
                for (Field field : AxisType.class.getDeclaredFields()) {
                    if (field.getType().equals(AxisType.class) && (axisType = (AxisType) field.get((Object) null)) != null) {
                        sTypeCollection.add(axisType);
                    }
                }
            } catch (Exception e) {
            }
            if (sTypeCollection == null) {
                return UNKNOWN;
            }
        }
        Iterator<AxisType> it = sTypeCollection.iterator();
        while (it.hasNext()) {
            AxisType next = it.next();
            if (next.toAndroidIntegerId() == i) {
                return next;
            }
        }
        return UNKNOWN;
    }

    public boolean hasAndroidIntegerId() {
        return this.fAndroidIntegerId >= 0;
    }

    public int toAndroidIntegerId() {
        return this.fAndroidIntegerId;
    }

    public String toAndroidSymbolicName() {
        return Build.VERSION.SDK_INT >= 12 ? ApiLevel12.getSymbolicNameFromAndroidIntegerId(this.fAndroidIntegerId) : Integer.toString(this.fAndroidIntegerId);
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
