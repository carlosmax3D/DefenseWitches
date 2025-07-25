package com.ansca.corona.input;

public class TouchTracker implements Cloneable {
    private static int sNextTouchId = 0;
    private int fDeviceId;
    private TouchPoint fLastPoint;
    private TouchPhase fPhase;
    private int fPointerId;
    private TouchPoint fStartPoint;
    private int fTouchId = sNextTouchId;

    public TouchTracker(int i, int i2) {
        sNextTouchId++;
        this.fDeviceId = i;
        this.fPointerId = i2;
        reset();
    }

    public TouchTracker clone() {
        try {
            return (TouchTracker) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    public int getDeviceId() {
        return this.fDeviceId;
    }

    public TouchPoint getLastPoint() {
        return this.fLastPoint;
    }

    public TouchPhase getPhase() {
        return this.fPhase;
    }

    public int getPointerId() {
        return this.fPointerId;
    }

    public TouchPoint getStartPoint() {
        return this.fStartPoint;
    }

    public int getTouchId() {
        return this.fTouchId;
    }

    public boolean hasNotStarted() {
        return !hasStarted();
    }

    public boolean hasStarted() {
        return this.fStartPoint != null;
    }

    public void reset() {
        this.fStartPoint = null;
        this.fLastPoint = null;
        this.fPhase = null;
    }

    public void updateWith(TouchPoint touchPoint, TouchPhase touchPhase) {
        if (touchPoint == null || touchPhase == null) {
            throw new NullPointerException();
        }
        if (this.fStartPoint == null || touchPhase == TouchPhase.BEGAN) {
            this.fStartPoint = touchPoint;
        }
        this.fLastPoint = touchPoint;
        this.fPhase = touchPhase;
    }
}
