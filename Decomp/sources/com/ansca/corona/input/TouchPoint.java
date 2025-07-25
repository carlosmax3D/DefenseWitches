package com.ansca.corona.input;

import android.os.SystemClock;

public class TouchPoint {
    private float fPointX;
    private float fPointY;
    private long fTimestamp;

    public TouchPoint(float f, float f2) {
        this(f, f2, SystemClock.uptimeMillis());
    }

    public TouchPoint(float f, float f2, long j) {
        this.fPointX = f;
        this.fPointY = f2;
        this.fTimestamp = j;
    }

    public long getTimestamp() {
        return this.fTimestamp;
    }

    public float getX() {
        return this.fPointX;
    }

    public float getY() {
        return this.fPointY;
    }

    public String toString() {
        return "(" + Float.toString(this.fPointX) + ", " + Float.toString(this.fPointY) + ", " + Long.toString(this.fTimestamp) + ")";
    }
}
