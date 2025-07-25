package com.ansca.corona.input;

import android.os.SystemClock;

public class AxisDataPoint {
    private long fTimestamp;
    private float fValue;

    public AxisDataPoint(float f) {
        this(f, SystemClock.uptimeMillis());
    }

    public AxisDataPoint(float f, long j) {
        this.fValue = f;
        this.fTimestamp = j;
    }

    public long getTimestamp() {
        return this.fTimestamp;
    }

    public float getValue() {
        return this.fValue;
    }

    public String toString() {
        return "(" + Float.toString(this.fValue) + ", " + Long.toString(this.fTimestamp) + ")";
    }
}
