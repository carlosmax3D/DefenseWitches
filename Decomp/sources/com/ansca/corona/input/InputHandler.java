package com.ansca.corona.input;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.ansca.corona.Controller;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;

public class InputHandler {
    private Controller fController;
    private TapTrackerCollection fTapTrackers = new TapTrackerCollection();
    private CoronaRuntimeTaskDispatcher fTaskDispatcher = null;
    private TouchTrackerCollection fTouchTrackers = new TouchTrackerCollection();

    private static class ApiLevel12 {
        private ApiLevel12() {
        }

        public static float getAxisValueFrom(MotionEvent motionEvent, int i) {
            if (motionEvent != null) {
                return motionEvent.getAxisValue(i);
            }
            throw new NullPointerException();
        }
    }

    private static class ApiLevel14 {
        private ApiLevel14() {
        }

        public static boolean isMiddleMouseButtonPressedFor(MotionEvent motionEvent) {
            return (motionEvent == null || (motionEvent.getButtonState() & 4) == 0) ? false : true;
        }

        public static boolean isPrimaryMouseButtonPressedFor(MotionEvent motionEvent) {
            return (motionEvent == null || (motionEvent.getButtonState() & 1) == 0) ? false : true;
        }

        public static boolean isSecondaryMouseButtonPressedFor(MotionEvent motionEvent) {
            return (motionEvent == null || (motionEvent.getButtonState() & 2) == 0) ? false : true;
        }
    }

    public InputHandler(Controller controller) {
        this.fController = controller;
    }

    private boolean handleAxisEvent(MotionEvent motionEvent) {
        Context applicationContext;
        if (motionEvent != null && Build.VERSION.SDK_INT >= 12) {
            InputDeviceInterface inputDeviceInterface = null;
            if (motionEvent.getDeviceId() > 0 && (applicationContext = CoronaEnvironment.getApplicationContext()) != null) {
                inputDeviceInterface = new InputDeviceServices(applicationContext).fetchDeviceFrom(motionEvent);
            }
            if (inputDeviceInterface != null) {
                inputDeviceInterface.getContext().beginUpdate();
                ReadOnlyAxisInfoCollection axes = inputDeviceInterface.getDeviceInfo().getAxes();
                for (int i = 0; i < axes.size(); i++) {
                    AxisInfo byIndex = axes.getByIndex(i);
                    if (byIndex != null) {
                        try {
                            inputDeviceInterface.getContext().update(i, new AxisDataPoint(ApiLevel12.getAxisValueFrom(motionEvent, byIndex.getType().toAndroidIntegerId()), motionEvent.getEventTime()));
                        } catch (Exception e) {
                        }
                    }
                }
                inputDeviceInterface.getContext().endUpdate();
            }
        }
        return false;
    }

    private boolean handleMouseEvent(MotionEvent motionEvent) {
        if (motionEvent == null || InputDeviceType.from(motionEvent) != InputDeviceType.MOUSE || motionEvent.getPointerCount() <= 0) {
            return false;
        }
        int action = motionEvent.getAction() & MotionEventCompat.ACTION_MASK;
        TouchPoint touchPoint = new TouchPoint(motionEvent.getX(), motionEvent.getY(), motionEvent.getEventTime());
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        if (Build.VERSION.SDK_INT >= 14) {
            z = ApiLevel14.isPrimaryMouseButtonPressedFor(motionEvent);
            z2 = ApiLevel14.isSecondaryMouseButtonPressedFor(motionEvent);
            z3 = ApiLevel14.isMiddleMouseButtonPressedFor(motionEvent);
        } else if (action == 0 || action == 2) {
            z = true;
        }
        if (this.fTaskDispatcher != null) {
            this.fTaskDispatcher.send(new RaiseMouseEventTask(touchPoint.getX(), touchPoint.getY(), touchPoint.getTimestamp(), z, z2, z3));
        }
        int deviceId = motionEvent.getDeviceId();
        int pointerId = motionEvent.getPointerId(0);
        TouchTracker byDeviceAndPointerId = this.fTouchTrackers.getByDeviceAndPointerId(deviceId, pointerId);
        if (byDeviceAndPointerId == null) {
            byDeviceAndPointerId = new TouchTracker(deviceId, pointerId);
            this.fTouchTrackers.add(byDeviceAndPointerId);
        }
        boolean z4 = false;
        if (!z || action == 3) {
            if (byDeviceAndPointerId.hasStarted()) {
                if (action == 3) {
                    byDeviceAndPointerId.updateWith(touchPoint, TouchPhase.CANCELED);
                } else {
                    byDeviceAndPointerId.updateWith(touchPoint, TouchPhase.ENDED);
                }
                z4 = true;
            }
        } else if (byDeviceAndPointerId.hasNotStarted()) {
            byDeviceAndPointerId.updateWith(touchPoint, TouchPhase.BEGAN);
            z4 = true;
        } else {
            float abs = Math.abs(touchPoint.getX() - byDeviceAndPointerId.getLastPoint().getX());
            float abs2 = Math.abs(touchPoint.getY() - byDeviceAndPointerId.getLastPoint().getY());
            if (abs >= 1.0f || abs2 >= 1.0f) {
                byDeviceAndPointerId.updateWith(touchPoint, TouchPhase.MOVED);
                z4 = true;
            }
        }
        if (z4 && this.fTaskDispatcher != null) {
            if (this.fController.isMultitouchEnabled()) {
                TouchTrackerCollection touchTrackerCollection = new TouchTrackerCollection();
                touchTrackerCollection.add(byDeviceAndPointerId);
                this.fTaskDispatcher.send(new RaiseMultitouchEventTask(touchTrackerCollection));
            } else {
                this.fTaskDispatcher.send(new RaiseTouchEventTask(byDeviceAndPointerId));
            }
        }
        if (byDeviceAndPointerId.getPhase() == TouchPhase.ENDED || byDeviceAndPointerId.getPhase() == TouchPhase.CANCELED) {
            byDeviceAndPointerId.reset();
        }
        return true;
    }

    private boolean handleTapEvent(MotionEvent motionEvent) {
        if (motionEvent == null) {
            return false;
        }
        InputDeviceType from = InputDeviceType.from(motionEvent);
        if (from != InputDeviceType.TOUCHSCREEN && from != InputDeviceType.STYLUS && from != InputDeviceType.MOUSE) {
            return false;
        }
        TapTracker byDeviceId = this.fTapTrackers.getByDeviceId(motionEvent.getDeviceId());
        if (byDeviceId == null) {
            byDeviceId = new TapTracker(motionEvent.getDeviceId());
            this.fTapTrackers.add(byDeviceId);
        }
        byDeviceId.updateWith(motionEvent);
        if (byDeviceId.hasTapOccurred() && this.fTaskDispatcher != null) {
            this.fTaskDispatcher.send(new RaiseTapEventTask(byDeviceId.getTapPoint(), byDeviceId.getTapCount()));
        }
        return true;
    }

    private boolean handleTouchEvent(MotionEvent motionEvent) {
        if (motionEvent == null) {
            return false;
        }
        InputDeviceType from = InputDeviceType.from(motionEvent);
        if (from != InputDeviceType.TOUCHSCREEN && from != InputDeviceType.STYLUS) {
            return false;
        }
        TouchTrackerCollection touchTrackerCollection = null;
        if (this.fController.isMultitouchEnabled()) {
            touchTrackerCollection = new TouchTrackerCollection();
        }
        TouchPhase from2 = TouchPhase.from(motionEvent);
        switch (motionEvent.getAction() & MotionEventCompat.ACTION_MASK) {
            case 0:
                this.fTouchTrackers.removeByDeviceId(motionEvent.getDeviceId());
                TouchTracker touchTracker = new TouchTracker(motionEvent.getDeviceId(), motionEvent.getPointerId(0));
                touchTracker.updateWith(new TouchPoint(motionEvent.getX(), motionEvent.getY(), motionEvent.getEventTime()), TouchPhase.BEGAN);
                this.fTouchTrackers.add(touchTracker);
                if (touchTrackerCollection == null) {
                    if (this.fTaskDispatcher != null) {
                        this.fTaskDispatcher.send(new RaiseTouchEventTask(touchTracker));
                        break;
                    }
                } else {
                    touchTrackerCollection.add(touchTracker);
                    break;
                }
                break;
            case 1:
            case 2:
            case 3:
                int pointerCount = motionEvent.getPointerCount();
                for (int i = 0; i < pointerCount; i++) {
                    TouchTracker byDeviceAndPointerId = this.fTouchTrackers.getByDeviceAndPointerId(motionEvent.getDeviceId(), motionEvent.getPointerId(i));
                    if (byDeviceAndPointerId != null && !byDeviceAndPointerId.hasNotStarted()) {
                        float x = motionEvent.getX(i);
                        float y = motionEvent.getY(i);
                        if (from2 == TouchPhase.MOVED) {
                            float abs = Math.abs(x - byDeviceAndPointerId.getLastPoint().getX());
                            float abs2 = Math.abs(y - byDeviceAndPointerId.getLastPoint().getY());
                            if (abs < 1.0f && abs2 < 1.0f) {
                            }
                        }
                        byDeviceAndPointerId.updateWith(new TouchPoint(x, y, motionEvent.getEventTime()), from2);
                        if (touchTrackerCollection != null) {
                            touchTrackerCollection.add(byDeviceAndPointerId);
                        } else if (this.fTaskDispatcher != null) {
                            this.fTaskDispatcher.send(new RaiseTouchEventTask(byDeviceAndPointerId));
                        }
                    }
                }
                if (from2 == TouchPhase.ENDED || from2 == TouchPhase.CANCELED) {
                    this.fTouchTrackers.removeByDeviceId(motionEvent.getDeviceId());
                    break;
                }
            case 5:
                if (touchTrackerCollection != null) {
                    int actionIndex = motionEvent.getActionIndex();
                    int pointerId = motionEvent.getPointerId(actionIndex);
                    this.fTouchTrackers.removeByDeviceAndPointerId(motionEvent.getDeviceId(), pointerId);
                    TouchPoint touchPoint = new TouchPoint(motionEvent.getX(actionIndex), motionEvent.getY(actionIndex), motionEvent.getEventTime());
                    TouchTracker touchTracker2 = new TouchTracker(motionEvent.getDeviceId(), pointerId);
                    touchTracker2.updateWith(touchPoint, from2);
                    this.fTouchTrackers.add(touchTracker2);
                    touchTrackerCollection.add(touchTracker2);
                    break;
                }
                break;
            case 6:
                int actionIndex2 = motionEvent.getActionIndex();
                int pointerId2 = motionEvent.getPointerId(actionIndex2);
                TouchTracker byDeviceAndPointerId2 = this.fTouchTrackers.getByDeviceAndPointerId(motionEvent.getDeviceId(), pointerId2);
                if (byDeviceAndPointerId2 != null) {
                    byDeviceAndPointerId2.updateWith(new TouchPoint(motionEvent.getX(actionIndex2), motionEvent.getY(actionIndex2), motionEvent.getEventTime()), from2);
                    if (touchTrackerCollection == null) {
                        if (this.fTaskDispatcher != null) {
                            this.fTaskDispatcher.send(new RaiseTouchEventTask(byDeviceAndPointerId2));
                        }
                        this.fTouchTrackers.removeByDeviceId(motionEvent.getDeviceId());
                        break;
                    } else {
                        touchTrackerCollection.add(byDeviceAndPointerId2);
                        this.fTouchTrackers.removeByDeviceAndPointerId(motionEvent.getDeviceId(), pointerId2);
                        break;
                    }
                }
                break;
        }
        if (!(touchTrackerCollection == null || touchTrackerCollection.size() <= 0 || this.fTaskDispatcher == null)) {
            this.fTaskDispatcher.send(new RaiseMultitouchEventTask(touchTrackerCollection));
        }
        return true;
    }

    public CoronaRuntimeTaskDispatcher getDispatcher() {
        return this.fTaskDispatcher;
    }

    public boolean handle(KeyEvent keyEvent) {
        Context applicationContext;
        if (keyEvent == null) {
            return false;
        }
        if (KeyPhase.from(keyEvent) == KeyPhase.DOWN && keyEvent.getRepeatCount() > 0) {
            return true;
        }
        if (keyEvent instanceof CoronaKeyEvent) {
            return false;
        }
        InputDeviceInterface inputDeviceInterface = null;
        if (keyEvent.getDeviceId() > 0 && (applicationContext = CoronaEnvironment.getApplicationContext()) != null) {
            inputDeviceInterface = new InputDeviceServices(applicationContext).fetchDeviceFrom(keyEvent);
        }
        if (inputDeviceInterface != null) {
            inputDeviceInterface.getContext().update(ConnectionState.CONNECTED);
        }
        if (CoronaEnvironment.getCoronaActivity() == null || keyEvent.getKeyCode() == 3 || keyEvent.getKeyCode() == 26 || this.fTaskDispatcher == null) {
            return false;
        }
        this.fTaskDispatcher.send(new RaiseKeyEventTask(inputDeviceInterface, keyEvent));
        return true;
    }

    public boolean handle(MotionEvent motionEvent) {
        if (motionEvent == null) {
            return false;
        }
        return false | handleAxisEvent(motionEvent) | handleMouseEvent(motionEvent) | handleTouchEvent(motionEvent) | handleTapEvent(motionEvent);
    }

    public void setDispatcher(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher) {
        this.fTaskDispatcher = coronaRuntimeTaskDispatcher;
    }
}
