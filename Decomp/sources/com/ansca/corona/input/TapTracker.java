package com.ansca.corona.input;

import android.os.Build;
import android.view.MotionEvent;

public class TapTracker implements Cloneable {
    private int fDeviceId;
    private TouchPoint fFirstTouchBeganPoint;
    private long fLastTapTimestamp;
    private TouchPoint fLastTouchBeganPoint;
    private int fTapCount;
    private TouchPoint fTapPoint;

    private static class ApiLevel14 {
        private ApiLevel14() {
        }

        public static boolean isNonPrimaryMouseButtonDownFor(MotionEvent motionEvent) {
            return (motionEvent == null || (motionEvent.getButtonState() & -2) == 0) ? false : true;
        }
    }

    public TapTracker(int i) {
        this.fDeviceId = i;
        reset();
    }

    private boolean areCoordinatesWithinTapBounds(TouchPoint touchPoint, TouchPoint touchPoint2) {
        if (touchPoint == null || touchPoint2 == null) {
            return false;
        }
        return Math.abs(touchPoint2.getX() - touchPoint.getX()) <= 40.0f && Math.abs(touchPoint2.getY() - touchPoint.getY()) <= 40.0f;
    }

    public TapTracker clone() {
        try {
            return (TapTracker) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    public int getDeviceId() {
        return this.fDeviceId;
    }

    public int getTapCount() {
        if (this.fTapPoint == null) {
            return 0;
        }
        return this.fTapCount;
    }

    public TouchPoint getTapPoint() {
        return this.fTapPoint;
    }

    public boolean hasTapOccurred() {
        return this.fTapPoint != null;
    }

    public void reset() {
        this.fFirstTouchBeganPoint = null;
        this.fLastTouchBeganPoint = null;
        this.fLastTapTimestamp = 0;
        this.fTapPoint = null;
        this.fTapCount = 0;
    }

    public void updateWith(MotionEvent motionEvent) {
        if (motionEvent == null) {
            throw new NullPointerException();
        }
        this.fTapPoint = null;
        if (motionEvent.getDeviceId() == this.fDeviceId) {
            if (motionEvent.getPointerCount() > 1) {
                reset();
            } else if (Build.VERSION.SDK_INT < 14 || InputDeviceType.from(motionEvent) != InputDeviceType.MOUSE || !ApiLevel14.isNonPrimaryMouseButtonDownFor(motionEvent)) {
                TouchPhase from = TouchPhase.from(motionEvent);
                if (from != null) {
                    updateWith(new TouchPoint(motionEvent.getX(), motionEvent.getY(), motionEvent.getEventTime()), from);
                }
            } else {
                reset();
            }
        }
    }

    public void updateWith(TouchPoint touchPoint, TouchPhase touchPhase) {
        if (touchPoint == null || touchPhase == null) {
            throw new NullPointerException();
        }
        this.fTapPoint = null;
        if (touchPhase == TouchPhase.BEGAN) {
            if (this.fFirstTouchBeganPoint == null) {
                this.fFirstTouchBeganPoint = touchPoint;
            }
            this.fLastTouchBeganPoint = touchPoint;
            if (!areCoordinatesWithinTapBounds(this.fLastTouchBeganPoint, this.fFirstTouchBeganPoint)) {
                this.fTapCount = 0;
                this.fFirstTouchBeganPoint = this.fLastTouchBeganPoint;
            }
        } else if (this.fLastTouchBeganPoint != null && touchPhase == TouchPhase.ENDED && areCoordinatesWithinTapBounds(touchPoint, this.fLastTouchBeganPoint)) {
            if (touchPoint.getTimestamp() - this.fLastTapTimestamp > 500) {
                this.fTapCount = 1;
            } else if (this.fTapCount < Integer.MAX_VALUE) {
                this.fTapCount++;
            }
            this.fLastTapTimestamp = touchPoint.getTimestamp();
            this.fTapPoint = touchPoint;
            this.fLastTouchBeganPoint = null;
        }
    }
}
