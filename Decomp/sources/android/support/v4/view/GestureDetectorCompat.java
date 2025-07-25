package android.support.v4.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public class GestureDetectorCompat {
    private final GestureDetectorCompatImpl mImpl;

    interface GestureDetectorCompatImpl {
        boolean isLongpressEnabled();

        boolean onTouchEvent(MotionEvent motionEvent);

        void setIsLongpressEnabled(boolean z);

        void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener);
    }

    static class GestureDetectorCompatImplBase implements GestureDetectorCompatImpl {
        private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
        private static final int LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
        private static final int LONG_PRESS = 2;
        private static final int SHOW_PRESS = 1;
        private static final int TAP = 3;
        private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
        private boolean mAlwaysInBiggerTapRegion;
        private boolean mAlwaysInTapRegion;
        /* access modifiers changed from: private */
        public MotionEvent mCurrentDownEvent;
        /* access modifiers changed from: private */
        public boolean mDeferConfirmSingleTap;
        /* access modifiers changed from: private */
        public GestureDetector.OnDoubleTapListener mDoubleTapListener;
        private int mDoubleTapSlopSquare;
        private float mDownFocusX;
        private float mDownFocusY;
        private final Handler mHandler;
        private boolean mInLongPress;
        private boolean mIsDoubleTapping;
        private boolean mIsLongpressEnabled;
        private float mLastFocusX;
        private float mLastFocusY;
        /* access modifiers changed from: private */
        public final GestureDetector.OnGestureListener mListener;
        private int mMaximumFlingVelocity;
        private int mMinimumFlingVelocity;
        private MotionEvent mPreviousUpEvent;
        /* access modifiers changed from: private */
        public boolean mStillDown;
        private int mTouchSlopSquare;
        private VelocityTracker mVelocityTracker;

        private class GestureHandler extends Handler {
            GestureHandler() {
            }

            GestureHandler(Handler handler) {
                super(handler.getLooper());
            }

            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        GestureDetectorCompatImplBase.this.mListener.onShowPress(GestureDetectorCompatImplBase.this.mCurrentDownEvent);
                        return;
                    case 2:
                        GestureDetectorCompatImplBase.this.dispatchLongPress();
                        return;
                    case 3:
                        if (GestureDetectorCompatImplBase.this.mDoubleTapListener == null) {
                            return;
                        }
                        if (!GestureDetectorCompatImplBase.this.mStillDown) {
                            GestureDetectorCompatImplBase.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetectorCompatImplBase.this.mCurrentDownEvent);
                            return;
                        } else {
                            boolean unused = GestureDetectorCompatImplBase.this.mDeferConfirmSingleTap = true;
                            return;
                        }
                    default:
                        throw new RuntimeException("Unknown message " + message);
                }
            }
        }

        public GestureDetectorCompatImplBase(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
            if (handler != null) {
                this.mHandler = new GestureHandler(handler);
            } else {
                this.mHandler = new GestureHandler();
            }
            this.mListener = onGestureListener;
            if (onGestureListener instanceof GestureDetector.OnDoubleTapListener) {
                setOnDoubleTapListener((GestureDetector.OnDoubleTapListener) onGestureListener);
            }
            init(context);
        }

        private void cancel() {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
            this.mIsDoubleTapping = false;
            this.mStillDown = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mInLongPress) {
                this.mInLongPress = false;
            }
        }

        private void cancelTaps() {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mIsDoubleTapping = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mInLongPress) {
                this.mInLongPress = false;
            }
        }

        /* access modifiers changed from: private */
        public void dispatchLongPress() {
            this.mHandler.removeMessages(3);
            this.mDeferConfirmSingleTap = false;
            this.mInLongPress = true;
            this.mListener.onLongPress(this.mCurrentDownEvent);
        }

        private void init(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null");
            } else if (this.mListener == null) {
                throw new IllegalArgumentException("OnGestureListener must not be null");
            } else {
                this.mIsLongpressEnabled = true;
                ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
                int scaledTouchSlop = viewConfiguration.getScaledTouchSlop();
                int scaledDoubleTapSlop = viewConfiguration.getScaledDoubleTapSlop();
                this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
                this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
                this.mTouchSlopSquare = scaledTouchSlop * scaledTouchSlop;
                this.mDoubleTapSlopSquare = scaledDoubleTapSlop * scaledDoubleTapSlop;
            }
        }

        private boolean isConsideredDoubleTap(MotionEvent motionEvent, MotionEvent motionEvent2, MotionEvent motionEvent3) {
            if (!this.mAlwaysInBiggerTapRegion || motionEvent3.getEventTime() - motionEvent2.getEventTime() > ((long) DOUBLE_TAP_TIMEOUT)) {
                return false;
            }
            int x = ((int) motionEvent.getX()) - ((int) motionEvent3.getX());
            int y = ((int) motionEvent.getY()) - ((int) motionEvent3.getY());
            return (x * x) + (y * y) < this.mDoubleTapSlopSquare;
        }

        public boolean isLongpressEnabled() {
            return this.mIsLongpressEnabled;
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(motionEvent);
            boolean z = (action & MotionEventCompat.ACTION_MASK) == 6;
            int actionIndex = z ? MotionEventCompat.getActionIndex(motionEvent) : -1;
            float f = 0.0f;
            float f2 = 0.0f;
            int pointerCount = MotionEventCompat.getPointerCount(motionEvent);
            for (int i = 0; i < pointerCount; i++) {
                if (actionIndex != i) {
                    f += MotionEventCompat.getX(motionEvent, i);
                    f2 += MotionEventCompat.getY(motionEvent, i);
                }
            }
            int i2 = z ? pointerCount - 1 : pointerCount;
            float f3 = f / ((float) i2);
            float f4 = f2 / ((float) i2);
            boolean z2 = false;
            switch (action & MotionEventCompat.ACTION_MASK) {
                case 0:
                    if (this.mDoubleTapListener != null) {
                        boolean hasMessages = this.mHandler.hasMessages(3);
                        if (hasMessages) {
                            this.mHandler.removeMessages(3);
                        }
                        if (!(this.mCurrentDownEvent == null || this.mPreviousUpEvent == null || !hasMessages)) {
                            if (isConsideredDoubleTap(this.mCurrentDownEvent, this.mPreviousUpEvent, motionEvent)) {
                                this.mIsDoubleTapping = true;
                                z2 = false | this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent) | this.mDoubleTapListener.onDoubleTapEvent(motionEvent);
                            }
                        }
                        this.mHandler.sendEmptyMessageDelayed(3, (long) DOUBLE_TAP_TIMEOUT);
                    }
                    this.mLastFocusX = f3;
                    this.mDownFocusX = f3;
                    this.mLastFocusY = f4;
                    this.mDownFocusY = f4;
                    if (this.mCurrentDownEvent != null) {
                        this.mCurrentDownEvent.recycle();
                    }
                    this.mCurrentDownEvent = MotionEvent.obtain(motionEvent);
                    this.mAlwaysInTapRegion = true;
                    this.mAlwaysInBiggerTapRegion = true;
                    this.mStillDown = true;
                    this.mInLongPress = false;
                    this.mDeferConfirmSingleTap = false;
                    if (this.mIsLongpressEnabled) {
                        this.mHandler.removeMessages(2);
                        this.mHandler.sendEmptyMessageAtTime(2, this.mCurrentDownEvent.getDownTime() + ((long) TAP_TIMEOUT) + ((long) LONGPRESS_TIMEOUT));
                    }
                    this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + ((long) TAP_TIMEOUT));
                    return z2 | this.mListener.onDown(motionEvent);
                case 1:
                    this.mStillDown = false;
                    MotionEvent obtain = MotionEvent.obtain(motionEvent);
                    if (this.mIsDoubleTapping) {
                        z2 = false | this.mDoubleTapListener.onDoubleTapEvent(motionEvent);
                    } else if (this.mInLongPress) {
                        this.mHandler.removeMessages(3);
                        this.mInLongPress = false;
                    } else if (this.mAlwaysInTapRegion) {
                        z2 = this.mListener.onSingleTapUp(motionEvent);
                        if (this.mDeferConfirmSingleTap && this.mDoubleTapListener != null) {
                            this.mDoubleTapListener.onSingleTapConfirmed(motionEvent);
                        }
                    } else {
                        VelocityTracker velocityTracker = this.mVelocityTracker;
                        int pointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                        velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumFlingVelocity);
                        float yVelocity = VelocityTrackerCompat.getYVelocity(velocityTracker, pointerId);
                        float xVelocity = VelocityTrackerCompat.getXVelocity(velocityTracker, pointerId);
                        if (Math.abs(yVelocity) > ((float) this.mMinimumFlingVelocity) || Math.abs(xVelocity) > ((float) this.mMinimumFlingVelocity)) {
                            z2 = this.mListener.onFling(this.mCurrentDownEvent, motionEvent, xVelocity, yVelocity);
                        }
                    }
                    if (this.mPreviousUpEvent != null) {
                        this.mPreviousUpEvent.recycle();
                    }
                    this.mPreviousUpEvent = obtain;
                    if (this.mVelocityTracker != null) {
                        this.mVelocityTracker.recycle();
                        this.mVelocityTracker = null;
                    }
                    this.mIsDoubleTapping = false;
                    this.mDeferConfirmSingleTap = false;
                    this.mHandler.removeMessages(1);
                    this.mHandler.removeMessages(2);
                    return z2;
                case 2:
                    if (this.mInLongPress) {
                        return false;
                    }
                    float f5 = this.mLastFocusX - f3;
                    float f6 = this.mLastFocusY - f4;
                    if (this.mIsDoubleTapping) {
                        return false | this.mDoubleTapListener.onDoubleTapEvent(motionEvent);
                    }
                    if (this.mAlwaysInTapRegion) {
                        int i3 = (int) (f3 - this.mDownFocusX);
                        int i4 = (int) (f4 - this.mDownFocusY);
                        int i5 = (i3 * i3) + (i4 * i4);
                        if (i5 > this.mTouchSlopSquare) {
                            z2 = this.mListener.onScroll(this.mCurrentDownEvent, motionEvent, f5, f6);
                            this.mLastFocusX = f3;
                            this.mLastFocusY = f4;
                            this.mAlwaysInTapRegion = false;
                            this.mHandler.removeMessages(3);
                            this.mHandler.removeMessages(1);
                            this.mHandler.removeMessages(2);
                        }
                        if (i5 <= this.mTouchSlopSquare) {
                            return z2;
                        }
                        this.mAlwaysInBiggerTapRegion = false;
                        return z2;
                    } else if (Math.abs(f5) < 1.0f && Math.abs(f6) < 1.0f) {
                        return false;
                    } else {
                        boolean onScroll = this.mListener.onScroll(this.mCurrentDownEvent, motionEvent, f5, f6);
                        this.mLastFocusX = f3;
                        this.mLastFocusY = f4;
                        return onScroll;
                    }
                case 3:
                    cancel();
                    return false;
                case 5:
                    this.mLastFocusX = f3;
                    this.mDownFocusX = f3;
                    this.mLastFocusY = f4;
                    this.mDownFocusY = f4;
                    cancelTaps();
                    return false;
                case 6:
                    this.mLastFocusX = f3;
                    this.mDownFocusX = f3;
                    this.mLastFocusY = f4;
                    this.mDownFocusY = f4;
                    this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumFlingVelocity);
                    int actionIndex2 = MotionEventCompat.getActionIndex(motionEvent);
                    int pointerId2 = MotionEventCompat.getPointerId(motionEvent, actionIndex2);
                    float xVelocity2 = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, pointerId2);
                    float yVelocity2 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, pointerId2);
                    for (int i6 = 0; i6 < pointerCount; i6++) {
                        if (i6 != actionIndex2) {
                            int pointerId3 = MotionEventCompat.getPointerId(motionEvent, i6);
                            if ((xVelocity2 * VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, pointerId3)) + (yVelocity2 * VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, pointerId3)) < 0.0f) {
                                this.mVelocityTracker.clear();
                                return false;
                            }
                        }
                    }
                    return false;
                default:
                    return false;
            }
        }

        public void setIsLongpressEnabled(boolean z) {
            this.mIsLongpressEnabled = z;
        }

        public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.mDoubleTapListener = onDoubleTapListener;
        }
    }

    static class GestureDetectorCompatImplJellybeanMr2 implements GestureDetectorCompatImpl {
        private final GestureDetector mDetector;

        public GestureDetectorCompatImplJellybeanMr2(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
            this.mDetector = new GestureDetector(context, onGestureListener, handler);
        }

        public boolean isLongpressEnabled() {
            return this.mDetector.isLongpressEnabled();
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return this.mDetector.onTouchEvent(motionEvent);
        }

        public void setIsLongpressEnabled(boolean z) {
            this.mDetector.setIsLongpressEnabled(z);
        }

        public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.mDetector.setOnDoubleTapListener(onDoubleTapListener);
        }
    }

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener) {
        this(context, onGestureListener, (Handler) null);
    }

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
        if (Build.VERSION.SDK_INT > 17) {
            this.mImpl = new GestureDetectorCompatImplJellybeanMr2(context, onGestureListener, handler);
        } else {
            this.mImpl = new GestureDetectorCompatImplBase(context, onGestureListener, handler);
        }
    }

    public boolean isLongpressEnabled() {
        return this.mImpl.isLongpressEnabled();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mImpl.onTouchEvent(motionEvent);
    }

    public void setIsLongpressEnabled(boolean z) {
        this.mImpl.setIsLongpressEnabled(z);
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.mImpl.setOnDoubleTapListener(onDoubleTapListener);
    }
}
