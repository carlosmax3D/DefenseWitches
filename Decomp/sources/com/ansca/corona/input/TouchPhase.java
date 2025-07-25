package com.ansca.corona.input;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import com.facebook.internal.AnalyticsEvents;

public class TouchPhase {
    public static final TouchPhase BEGAN = new TouchPhase(0, "began");
    public static final TouchPhase CANCELED = new TouchPhase(4, AnalyticsEvents.PARAMETER_SHARE_OUTCOME_CANCELLED);
    public static final TouchPhase ENDED = new TouchPhase(3, "ended");
    public static final TouchPhase MOVED = new TouchPhase(1, "moved");
    private int fCoronaNumericId;
    private String fCoronaStringId;

    private TouchPhase(int i, String str) {
        this.fCoronaNumericId = i;
        this.fCoronaStringId = str;
    }

    public static TouchPhase from(MotionEvent motionEvent) {
        if (motionEvent == null) {
            return null;
        }
        switch (motionEvent.getAction() & MotionEventCompat.ACTION_MASK) {
            case 0:
            case 5:
                return BEGAN;
            case 1:
            case 6:
                return ENDED;
            case 2:
                return MOVED;
            case 3:
                return CANCELED;
            default:
                return null;
        }
    }

    public int toCoronaNumericId() {
        return this.fCoronaNumericId;
    }

    public String toCoronaStringId() {
        return this.fCoronaStringId;
    }

    public String toString() {
        return this.fCoronaStringId;
    }
}
