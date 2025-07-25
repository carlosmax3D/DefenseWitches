package android.support.v4.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import java.util.Arrays;

public class ViewDragHelper {
    private static final int BASE_SETTLE_DURATION = 256;
    public static final int DIRECTION_ALL = 3;
    public static final int DIRECTION_HORIZONTAL = 1;
    public static final int DIRECTION_VERTICAL = 2;
    public static final int EDGE_ALL = 15;
    public static final int EDGE_BOTTOM = 8;
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    private static final int EDGE_SIZE = 20;
    public static final int EDGE_TOP = 4;
    public static final int INVALID_POINTER = -1;
    private static final int MAX_SETTLE_DURATION = 600;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "ViewDragHelper";
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    private int mActivePointerId = -1;
    private final Callback mCallback;
    private View mCapturedView;
    private int mDragState;
    private int[] mEdgeDragsInProgress;
    private int[] mEdgeDragsLocked;
    private int mEdgeSize;
    private int[] mInitialEdgesTouched;
    private float[] mInitialMotionX;
    private float[] mInitialMotionY;
    private float[] mLastMotionX;
    private float[] mLastMotionY;
    private float mMaxVelocity;
    private float mMinVelocity;
    private final ViewGroup mParentView;
    private int mPointersDown;
    private boolean mReleaseInProgress;
    private ScrollerCompat mScroller;
    private final Runnable mSetIdleRunnable = new Runnable() {
        public void run() {
            ViewDragHelper.this.setDragState(0);
        }
    };
    private int mTouchSlop;
    private int mTrackingEdges;
    private VelocityTracker mVelocityTracker;

    public static abstract class Callback {
        public int clampViewPositionHorizontal(View view, int i, int i2) {
            return 0;
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            return 0;
        }

        public int getOrderedChildIndex(int i) {
            return i;
        }

        public int getViewHorizontalDragRange(View view) {
            return 0;
        }

        public int getViewVerticalDragRange(View view) {
            return 0;
        }

        public void onEdgeDragStarted(int i, int i2) {
        }

        public boolean onEdgeLock(int i) {
            return false;
        }

        public void onEdgeTouched(int i, int i2) {
        }

        public void onViewCaptured(View view, int i) {
        }

        public void onViewDragStateChanged(int i) {
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
        }

        public void onViewReleased(View view, float f, float f2) {
        }

        public abstract boolean tryCaptureView(View view, int i);
    }

    private ViewDragHelper(Context context, ViewGroup viewGroup, Callback callback) {
        if (viewGroup == null) {
            throw new IllegalArgumentException("Parent view may not be null");
        } else if (callback == null) {
            throw new IllegalArgumentException("Callback may not be null");
        } else {
            this.mParentView = viewGroup;
            this.mCallback = callback;
            ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
            this.mEdgeSize = (int) ((20.0f * context.getResources().getDisplayMetrics().density) + 0.5f);
            this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
            this.mMaxVelocity = (float) viewConfiguration.getScaledMaximumFlingVelocity();
            this.mMinVelocity = (float) viewConfiguration.getScaledMinimumFlingVelocity();
            this.mScroller = ScrollerCompat.create(context, sInterpolator);
        }
    }

    private boolean checkNewEdgeDrag(float f, float f2, int i, int i2) {
        float abs = Math.abs(f);
        float abs2 = Math.abs(f2);
        if ((this.mInitialEdgesTouched[i] & i2) != i2 || (this.mTrackingEdges & i2) == 0 || (this.mEdgeDragsLocked[i] & i2) == i2 || (this.mEdgeDragsInProgress[i] & i2) == i2) {
            return false;
        }
        if (abs <= ((float) this.mTouchSlop) && abs2 <= ((float) this.mTouchSlop)) {
            return false;
        }
        if (abs >= 0.5f * abs2 || !this.mCallback.onEdgeLock(i2)) {
            return (this.mEdgeDragsInProgress[i] & i2) == 0 && abs > ((float) this.mTouchSlop);
        }
        int[] iArr = this.mEdgeDragsLocked;
        iArr[i] = iArr[i] | i2;
        return false;
    }

    private boolean checkTouchSlop(View view, float f, float f2) {
        if (view == null) {
            return false;
        }
        boolean z = this.mCallback.getViewHorizontalDragRange(view) > 0;
        boolean z2 = this.mCallback.getViewVerticalDragRange(view) > 0;
        if (z && z2) {
            return (f * f) + (f2 * f2) > ((float) (this.mTouchSlop * this.mTouchSlop));
        }
        if (z) {
            return Math.abs(f) > ((float) this.mTouchSlop);
        }
        if (z2) {
            return Math.abs(f2) > ((float) this.mTouchSlop);
        }
        return false;
    }

    private float clampMag(float f, float f2, float f3) {
        float abs = Math.abs(f);
        if (abs < f2) {
            return 0.0f;
        }
        return abs > f3 ? f <= 0.0f ? -f3 : f3 : f;
    }

    private int clampMag(int i, int i2, int i3) {
        int abs = Math.abs(i);
        if (abs < i2) {
            return 0;
        }
        return abs > i3 ? i <= 0 ? -i3 : i3 : i;
    }

    private void clearMotionHistory() {
        if (this.mInitialMotionX != null) {
            Arrays.fill(this.mInitialMotionX, 0.0f);
            Arrays.fill(this.mInitialMotionY, 0.0f);
            Arrays.fill(this.mLastMotionX, 0.0f);
            Arrays.fill(this.mLastMotionY, 0.0f);
            Arrays.fill(this.mInitialEdgesTouched, 0);
            Arrays.fill(this.mEdgeDragsInProgress, 0);
            Arrays.fill(this.mEdgeDragsLocked, 0);
            this.mPointersDown = 0;
        }
    }

    private void clearMotionHistory(int i) {
        if (this.mInitialMotionX != null) {
            this.mInitialMotionX[i] = 0.0f;
            this.mInitialMotionY[i] = 0.0f;
            this.mLastMotionX[i] = 0.0f;
            this.mLastMotionY[i] = 0.0f;
            this.mInitialEdgesTouched[i] = 0;
            this.mEdgeDragsInProgress[i] = 0;
            this.mEdgeDragsLocked[i] = 0;
            this.mPointersDown &= (1 << i) ^ -1;
        }
    }

    private int computeAxisDuration(int i, int i2, int i3) {
        if (i == 0) {
            return 0;
        }
        int width = this.mParentView.getWidth();
        int i4 = width / 2;
        float distanceInfluenceForSnapDuration = ((float) i4) + (((float) i4) * distanceInfluenceForSnapDuration(Math.min(1.0f, ((float) Math.abs(i)) / ((float) width))));
        int abs = Math.abs(i2);
        return Math.min(abs > 0 ? Math.round(1000.0f * Math.abs(distanceInfluenceForSnapDuration / ((float) abs))) * 4 : (int) (((((float) Math.abs(i)) / ((float) i3)) + 1.0f) * 256.0f), MAX_SETTLE_DURATION);
    }

    private int computeSettleDuration(View view, int i, int i2, int i3, int i4) {
        int clampMag = clampMag(i3, (int) this.mMinVelocity, (int) this.mMaxVelocity);
        int clampMag2 = clampMag(i4, (int) this.mMinVelocity, (int) this.mMaxVelocity);
        int abs = Math.abs(i);
        int abs2 = Math.abs(i2);
        int abs3 = Math.abs(clampMag);
        int abs4 = Math.abs(clampMag2);
        int i5 = abs3 + abs4;
        int i6 = abs + abs2;
        return (int) ((((float) computeAxisDuration(i, clampMag, this.mCallback.getViewHorizontalDragRange(view))) * (clampMag != 0 ? ((float) abs3) / ((float) i5) : ((float) abs) / ((float) i6))) + (((float) computeAxisDuration(i2, clampMag2, this.mCallback.getViewVerticalDragRange(view))) * (clampMag2 != 0 ? ((float) abs4) / ((float) i5) : ((float) abs2) / ((float) i6))));
    }

    public static ViewDragHelper create(ViewGroup viewGroup, float f, Callback callback) {
        ViewDragHelper create = create(viewGroup, callback);
        create.mTouchSlop = (int) (((float) create.mTouchSlop) * (1.0f / f));
        return create;
    }

    public static ViewDragHelper create(ViewGroup viewGroup, Callback callback) {
        return new ViewDragHelper(viewGroup.getContext(), viewGroup, callback);
    }

    private void dispatchViewReleased(float f, float f2) {
        this.mReleaseInProgress = true;
        this.mCallback.onViewReleased(this.mCapturedView, f, f2);
        this.mReleaseInProgress = false;
        if (this.mDragState == 1) {
            setDragState(0);
        }
    }

    private float distanceInfluenceForSnapDuration(float f) {
        return (float) Math.sin((double) ((float) (((double) (f - 0.5f)) * 0.4712389167638204d)));
    }

    private void dragTo(int i, int i2, int i3, int i4) {
        int i5 = i;
        int i6 = i2;
        int left = this.mCapturedView.getLeft();
        int top = this.mCapturedView.getTop();
        if (i3 != 0) {
            i5 = this.mCallback.clampViewPositionHorizontal(this.mCapturedView, i, i3);
            this.mCapturedView.offsetLeftAndRight(i5 - left);
        }
        if (i4 != 0) {
            i6 = this.mCallback.clampViewPositionVertical(this.mCapturedView, i2, i4);
            this.mCapturedView.offsetTopAndBottom(i6 - top);
        }
        if (i3 != 0 || i4 != 0) {
            this.mCallback.onViewPositionChanged(this.mCapturedView, i5, i6, i5 - left, i6 - top);
        }
    }

    private void ensureMotionHistorySizeForId(int i) {
        if (this.mInitialMotionX == null || this.mInitialMotionX.length <= i) {
            float[] fArr = new float[(i + 1)];
            float[] fArr2 = new float[(i + 1)];
            float[] fArr3 = new float[(i + 1)];
            float[] fArr4 = new float[(i + 1)];
            int[] iArr = new int[(i + 1)];
            int[] iArr2 = new int[(i + 1)];
            int[] iArr3 = new int[(i + 1)];
            if (this.mInitialMotionX != null) {
                System.arraycopy(this.mInitialMotionX, 0, fArr, 0, this.mInitialMotionX.length);
                System.arraycopy(this.mInitialMotionY, 0, fArr2, 0, this.mInitialMotionY.length);
                System.arraycopy(this.mLastMotionX, 0, fArr3, 0, this.mLastMotionX.length);
                System.arraycopy(this.mLastMotionY, 0, fArr4, 0, this.mLastMotionY.length);
                System.arraycopy(this.mInitialEdgesTouched, 0, iArr, 0, this.mInitialEdgesTouched.length);
                System.arraycopy(this.mEdgeDragsInProgress, 0, iArr2, 0, this.mEdgeDragsInProgress.length);
                System.arraycopy(this.mEdgeDragsLocked, 0, iArr3, 0, this.mEdgeDragsLocked.length);
            }
            this.mInitialMotionX = fArr;
            this.mInitialMotionY = fArr2;
            this.mLastMotionX = fArr3;
            this.mLastMotionY = fArr4;
            this.mInitialEdgesTouched = iArr;
            this.mEdgeDragsInProgress = iArr2;
            this.mEdgeDragsLocked = iArr3;
        }
    }

    private boolean forceSettleCapturedViewAt(int i, int i2, int i3, int i4) {
        int left = this.mCapturedView.getLeft();
        int top = this.mCapturedView.getTop();
        int i5 = i - left;
        int i6 = i2 - top;
        if (i5 == 0 && i6 == 0) {
            this.mScroller.abortAnimation();
            setDragState(0);
            return false;
        }
        this.mScroller.startScroll(left, top, i5, i6, computeSettleDuration(this.mCapturedView, i5, i6, i3, i4));
        setDragState(2);
        return true;
    }

    private int getEdgesTouched(int i, int i2) {
        int i3 = 0;
        if (i < this.mParentView.getLeft() + this.mEdgeSize) {
            i3 = 0 | 1;
        }
        if (i2 < this.mParentView.getTop() + this.mEdgeSize) {
            i3 |= 4;
        }
        if (i > this.mParentView.getRight() - this.mEdgeSize) {
            i3 |= 2;
        }
        return i2 > this.mParentView.getBottom() - this.mEdgeSize ? i3 | 8 : i3;
    }

    private void releaseViewForPointerUp() {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
        dispatchViewReleased(clampMag(VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity), clampMag(VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity));
    }

    private void reportNewEdgeDrags(float f, float f2, int i) {
        int i2 = 0;
        if (checkNewEdgeDrag(f, f2, i, 1)) {
            i2 = 0 | 1;
        }
        if (checkNewEdgeDrag(f2, f, i, 4)) {
            i2 |= 4;
        }
        if (checkNewEdgeDrag(f, f2, i, 2)) {
            i2 |= 2;
        }
        if (checkNewEdgeDrag(f2, f, i, 8)) {
            i2 |= 8;
        }
        if (i2 != 0) {
            int[] iArr = this.mEdgeDragsInProgress;
            iArr[i] = iArr[i] | i2;
            this.mCallback.onEdgeDragStarted(i2, i);
        }
    }

    private void saveInitialMotion(float f, float f2, int i) {
        ensureMotionHistorySizeForId(i);
        float[] fArr = this.mInitialMotionX;
        this.mLastMotionX[i] = f;
        fArr[i] = f;
        float[] fArr2 = this.mInitialMotionY;
        this.mLastMotionY[i] = f2;
        fArr2[i] = f2;
        this.mInitialEdgesTouched[i] = getEdgesTouched((int) f, (int) f2);
        this.mPointersDown |= 1 << i;
    }

    private void saveLastMotion(MotionEvent motionEvent) {
        int pointerCount = MotionEventCompat.getPointerCount(motionEvent);
        for (int i = 0; i < pointerCount; i++) {
            int pointerId = MotionEventCompat.getPointerId(motionEvent, i);
            float x = MotionEventCompat.getX(motionEvent, i);
            float y = MotionEventCompat.getY(motionEvent, i);
            this.mLastMotionX[pointerId] = x;
            this.mLastMotionY[pointerId] = y;
        }
    }

    public void abort() {
        cancel();
        if (this.mDragState == 2) {
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            this.mScroller.abortAnimation();
            int currX2 = this.mScroller.getCurrX();
            int currY2 = this.mScroller.getCurrY();
            this.mCallback.onViewPositionChanged(this.mCapturedView, currX2, currY2, currX2 - currX, currY2 - currY);
        }
        setDragState(0);
    }

    /* access modifiers changed from: protected */
    public boolean canScroll(View view, boolean z, int i, int i2, int i3, int i4) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                if (i3 + scrollX >= childAt.getLeft() && i3 + scrollX < childAt.getRight() && i4 + scrollY >= childAt.getTop() && i4 + scrollY < childAt.getBottom()) {
                    if (canScroll(childAt, true, i, i2, (i3 + scrollX) - childAt.getLeft(), (i4 + scrollY) - childAt.getTop())) {
                        return true;
                    }
                }
            }
        }
        return z && (ViewCompat.canScrollHorizontally(view, -i) || ViewCompat.canScrollVertically(view, -i2));
    }

    public void cancel() {
        this.mActivePointerId = -1;
        clearMotionHistory();
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public void captureChildView(View view, int i) {
        if (view.getParent() != this.mParentView) {
            throw new IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + this.mParentView + ")");
        }
        this.mCapturedView = view;
        this.mActivePointerId = i;
        this.mCallback.onViewCaptured(view, i);
        setDragState(1);
    }

    public boolean checkTouchSlop(int i) {
        int length = this.mInitialMotionX.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (checkTouchSlop(i, i2)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTouchSlop(int i, int i2) {
        if (!isPointerDown(i2)) {
            return false;
        }
        boolean z = (i & 1) == 1;
        boolean z2 = (i & 2) == 2;
        float f = this.mLastMotionX[i2] - this.mInitialMotionX[i2];
        float f2 = this.mLastMotionY[i2] - this.mInitialMotionY[i2];
        if (z && z2) {
            return (f * f) + (f2 * f2) > ((float) (this.mTouchSlop * this.mTouchSlop));
        }
        if (z) {
            return Math.abs(f) > ((float) this.mTouchSlop);
        }
        if (z2) {
            return Math.abs(f2) > ((float) this.mTouchSlop);
        }
        return false;
    }

    public boolean continueSettling(boolean z) {
        if (this.mDragState == 2) {
            boolean computeScrollOffset = this.mScroller.computeScrollOffset();
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            int left = currX - this.mCapturedView.getLeft();
            int top = currY - this.mCapturedView.getTop();
            if (left != 0) {
                this.mCapturedView.offsetLeftAndRight(left);
            }
            if (top != 0) {
                this.mCapturedView.offsetTopAndBottom(top);
            }
            if (!(left == 0 && top == 0)) {
                this.mCallback.onViewPositionChanged(this.mCapturedView, currX, currY, left, top);
            }
            if (computeScrollOffset && currX == this.mScroller.getFinalX() && currY == this.mScroller.getFinalY()) {
                this.mScroller.abortAnimation();
                computeScrollOffset = false;
            }
            if (!computeScrollOffset) {
                if (z) {
                    this.mParentView.post(this.mSetIdleRunnable);
                } else {
                    setDragState(0);
                }
            }
        }
        return this.mDragState == 2;
    }

    public View findTopChildUnder(int i, int i2) {
        for (int childCount = this.mParentView.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = this.mParentView.getChildAt(this.mCallback.getOrderedChildIndex(childCount));
            if (i >= childAt.getLeft() && i < childAt.getRight() && i2 >= childAt.getTop() && i2 < childAt.getBottom()) {
                return childAt;
            }
        }
        return null;
    }

    public void flingCapturedView(int i, int i2, int i3, int i4) {
        if (!this.mReleaseInProgress) {
            throw new IllegalStateException("Cannot flingCapturedView outside of a call to Callback#onViewReleased");
        }
        this.mScroller.fling(this.mCapturedView.getLeft(), this.mCapturedView.getTop(), (int) VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), (int) VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId), i, i3, i2, i4);
        setDragState(2);
    }

    public int getActivePointerId() {
        return this.mActivePointerId;
    }

    public View getCapturedView() {
        return this.mCapturedView;
    }

    public int getEdgeSize() {
        return this.mEdgeSize;
    }

    public float getMinVelocity() {
        return this.mMinVelocity;
    }

    public int getTouchSlop() {
        return this.mTouchSlop;
    }

    public int getViewDragState() {
        return this.mDragState;
    }

    public boolean isCapturedViewUnder(int i, int i2) {
        return isViewUnder(this.mCapturedView, i, i2);
    }

    public boolean isEdgeTouched(int i) {
        int length = this.mInitialEdgesTouched.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (isEdgeTouched(i, i2)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEdgeTouched(int i, int i2) {
        return isPointerDown(i2) && (this.mInitialEdgesTouched[i2] & i) != 0;
    }

    public boolean isPointerDown(int i) {
        return (this.mPointersDown & (1 << i)) != 0;
    }

    public boolean isViewUnder(View view, int i, int i2) {
        return view != null && i >= view.getLeft() && i < view.getRight() && i2 >= view.getTop() && i2 < view.getBottom();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0237, code lost:
        r17 = android.support.v4.view.MotionEventCompat.getX(r22, r8);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void processTouchEvent(android.view.MotionEvent r22) {
        /*
            r21 = this;
            int r3 = android.support.v4.view.MotionEventCompat.getActionMasked(r22)
            int r4 = android.support.v4.view.MotionEventCompat.getActionIndex(r22)
            if (r3 != 0) goto L_0x000d
            r21.cancel()
        L_0x000d:
            r0 = r21
            android.view.VelocityTracker r0 = r0.mVelocityTracker
            r19 = r0
            if (r19 != 0) goto L_0x001f
            android.view.VelocityTracker r19 = android.view.VelocityTracker.obtain()
            r0 = r19
            r1 = r21
            r1.mVelocityTracker = r0
        L_0x001f:
            r0 = r21
            android.view.VelocityTracker r0 = r0.mVelocityTracker
            r19 = r0
            r0 = r19
            r1 = r22
            r0.addMovement(r1)
            switch(r3) {
                case 0: goto L_0x0030;
                case 1: goto L_0x0287;
                case 2: goto L_0x011a;
                case 3: goto L_0x029d;
                case 4: goto L_0x002f;
                case 5: goto L_0x008e;
                case 6: goto L_0x01fe;
                default: goto L_0x002f;
            }
        L_0x002f:
            return
        L_0x0030:
            float r17 = r22.getX()
            float r18 = r22.getY()
            r19 = 0
            r0 = r22
            r1 = r19
            int r15 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r1)
            r0 = r17
            int r0 = (int) r0
            r19 = r0
            r0 = r18
            int r0 = (int) r0
            r20 = r0
            r0 = r21
            r1 = r19
            r2 = r20
            android.view.View r16 = r0.findTopChildUnder(r1, r2)
            r0 = r21
            r1 = r17
            r2 = r18
            r0.saveInitialMotion(r1, r2, r15)
            r0 = r21
            r1 = r16
            r0.tryCaptureViewForDrag(r1, r15)
            r0 = r21
            int[] r0 = r0.mInitialEdgesTouched
            r19 = r0
            r7 = r19[r15]
            r0 = r21
            int r0 = r0.mTrackingEdges
            r19 = r0
            r19 = r19 & r7
            if (r19 == 0) goto L_0x002f
            r0 = r21
            android.support.v4.widget.ViewDragHelper$Callback r0 = r0.mCallback
            r19 = r0
            r0 = r21
            int r0 = r0.mTrackingEdges
            r20 = r0
            r20 = r20 & r7
            r0 = r19
            r1 = r20
            r0.onEdgeTouched(r1, r15)
            goto L_0x002f
        L_0x008e:
            r0 = r22
            int r15 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r4)
            r0 = r22
            float r17 = android.support.v4.view.MotionEventCompat.getX(r0, r4)
            r0 = r22
            float r18 = android.support.v4.view.MotionEventCompat.getY(r0, r4)
            r0 = r21
            r1 = r17
            r2 = r18
            r0.saveInitialMotion(r1, r2, r15)
            r0 = r21
            int r0 = r0.mDragState
            r19 = r0
            if (r19 != 0) goto L_0x00f5
            r0 = r17
            int r0 = (int) r0
            r19 = r0
            r0 = r18
            int r0 = (int) r0
            r20 = r0
            r0 = r21
            r1 = r19
            r2 = r20
            android.view.View r16 = r0.findTopChildUnder(r1, r2)
            r0 = r21
            r1 = r16
            r0.tryCaptureViewForDrag(r1, r15)
            r0 = r21
            int[] r0 = r0.mInitialEdgesTouched
            r19 = r0
            r7 = r19[r15]
            r0 = r21
            int r0 = r0.mTrackingEdges
            r19 = r0
            r19 = r19 & r7
            if (r19 == 0) goto L_0x002f
            r0 = r21
            android.support.v4.widget.ViewDragHelper$Callback r0 = r0.mCallback
            r19 = r0
            r0 = r21
            int r0 = r0.mTrackingEdges
            r20 = r0
            r20 = r20 & r7
            r0 = r19
            r1 = r20
            r0.onEdgeTouched(r1, r15)
            goto L_0x002f
        L_0x00f5:
            r0 = r17
            int r0 = (int) r0
            r19 = r0
            r0 = r18
            int r0 = (int) r0
            r20 = r0
            r0 = r21
            r1 = r19
            r2 = r20
            boolean r19 = r0.isCapturedViewUnder(r1, r2)
            if (r19 == 0) goto L_0x002f
            r0 = r21
            android.view.View r0 = r0.mCapturedView
            r19 = r0
            r0 = r21
            r1 = r19
            r0.tryCaptureViewForDrag(r1, r15)
            goto L_0x002f
        L_0x011a:
            r0 = r21
            int r0 = r0.mDragState
            r19 = r0
            r20 = 1
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x018e
            r0 = r21
            int r0 = r0.mActivePointerId
            r19 = r0
            r0 = r22
            r1 = r19
            int r12 = android.support.v4.view.MotionEventCompat.findPointerIndex(r0, r1)
            r0 = r22
            float r17 = android.support.v4.view.MotionEventCompat.getX(r0, r12)
            r0 = r22
            float r18 = android.support.v4.view.MotionEventCompat.getY(r0, r12)
            r0 = r21
            float[] r0 = r0.mLastMotionX
            r19 = r0
            r0 = r21
            int r0 = r0.mActivePointerId
            r20 = r0
            r19 = r19[r20]
            float r19 = r17 - r19
            r0 = r19
            int r10 = (int) r0
            r0 = r21
            float[] r0 = r0.mLastMotionY
            r19 = r0
            r0 = r21
            int r0 = r0.mActivePointerId
            r20 = r0
            r19 = r19[r20]
            float r19 = r18 - r19
            r0 = r19
            int r11 = (int) r0
            r0 = r21
            android.view.View r0 = r0.mCapturedView
            r19 = r0
            int r19 = r19.getLeft()
            int r19 = r19 + r10
            r0 = r21
            android.view.View r0 = r0.mCapturedView
            r20 = r0
            int r20 = r20.getTop()
            int r20 = r20 + r11
            r0 = r21
            r1 = r19
            r2 = r20
            r0.dragTo(r1, r2, r10, r11)
            r21.saveLastMotion(r22)
            goto L_0x002f
        L_0x018e:
            int r14 = android.support.v4.view.MotionEventCompat.getPointerCount(r22)
            r8 = 0
        L_0x0193:
            if (r8 >= r14) goto L_0x01ce
            r0 = r22
            int r15 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r8)
            r0 = r22
            float r17 = android.support.v4.view.MotionEventCompat.getX(r0, r8)
            r0 = r22
            float r18 = android.support.v4.view.MotionEventCompat.getY(r0, r8)
            r0 = r21
            float[] r0 = r0.mInitialMotionX
            r19 = r0
            r19 = r19[r15]
            float r5 = r17 - r19
            r0 = r21
            float[] r0 = r0.mInitialMotionY
            r19 = r0
            r19 = r19[r15]
            float r6 = r18 - r19
            r0 = r21
            r0.reportNewEdgeDrags(r5, r6, r15)
            r0 = r21
            int r0 = r0.mDragState
            r19 = r0
            r20 = 1
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x01d3
        L_0x01ce:
            r21.saveLastMotion(r22)
            goto L_0x002f
        L_0x01d3:
            r0 = r17
            int r0 = (int) r0
            r19 = r0
            r0 = r18
            int r0 = (int) r0
            r20 = r0
            r0 = r21
            r1 = r19
            r2 = r20
            android.view.View r16 = r0.findTopChildUnder(r1, r2)
            r0 = r21
            r1 = r16
            boolean r19 = r0.checkTouchSlop(r1, r5, r6)
            if (r19 == 0) goto L_0x01fb
            r0 = r21
            r1 = r16
            boolean r19 = r0.tryCaptureViewForDrag(r1, r15)
            if (r19 != 0) goto L_0x01ce
        L_0x01fb:
            int r8 = r8 + 1
            goto L_0x0193
        L_0x01fe:
            r0 = r22
            int r15 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r4)
            r0 = r21
            int r0 = r0.mDragState
            r19 = r0
            r20 = 1
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x0280
            r0 = r21
            int r0 = r0.mActivePointerId
            r19 = r0
            r0 = r19
            if (r15 != r0) goto L_0x0280
            r13 = -1
            int r14 = android.support.v4.view.MotionEventCompat.getPointerCount(r22)
            r8 = 0
        L_0x0222:
            if (r8 >= r14) goto L_0x0277
            r0 = r22
            int r9 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r8)
            r0 = r21
            int r0 = r0.mActivePointerId
            r19 = r0
            r0 = r19
            if (r9 != r0) goto L_0x0237
        L_0x0234:
            int r8 = r8 + 1
            goto L_0x0222
        L_0x0237:
            r0 = r22
            float r17 = android.support.v4.view.MotionEventCompat.getX(r0, r8)
            r0 = r22
            float r18 = android.support.v4.view.MotionEventCompat.getY(r0, r8)
            r0 = r17
            int r0 = (int) r0
            r19 = r0
            r0 = r18
            int r0 = (int) r0
            r20 = r0
            r0 = r21
            r1 = r19
            r2 = r20
            android.view.View r19 = r0.findTopChildUnder(r1, r2)
            r0 = r21
            android.view.View r0 = r0.mCapturedView
            r20 = r0
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x0234
            r0 = r21
            android.view.View r0 = r0.mCapturedView
            r19 = r0
            r0 = r21
            r1 = r19
            boolean r19 = r0.tryCaptureViewForDrag(r1, r9)
            if (r19 == 0) goto L_0x0234
            r0 = r21
            int r13 = r0.mActivePointerId
        L_0x0277:
            r19 = -1
            r0 = r19
            if (r13 != r0) goto L_0x0280
            r21.releaseViewForPointerUp()
        L_0x0280:
            r0 = r21
            r0.clearMotionHistory(r15)
            goto L_0x002f
        L_0x0287:
            r0 = r21
            int r0 = r0.mDragState
            r19 = r0
            r20 = 1
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x0298
            r21.releaseViewForPointerUp()
        L_0x0298:
            r21.cancel()
            goto L_0x002f
        L_0x029d:
            r0 = r21
            int r0 = r0.mDragState
            r19 = r0
            r20 = 1
            r0 = r19
            r1 = r20
            if (r0 != r1) goto L_0x02b8
            r19 = 0
            r20 = 0
            r0 = r21
            r1 = r19
            r2 = r20
            r0.dispatchViewReleased(r1, r2)
        L_0x02b8:
            r21.cancel()
            goto L_0x002f
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.ViewDragHelper.processTouchEvent(android.view.MotionEvent):void");
    }

    /* access modifiers changed from: package-private */
    public void setDragState(int i) {
        this.mParentView.removeCallbacks(this.mSetIdleRunnable);
        if (this.mDragState != i) {
            this.mDragState = i;
            this.mCallback.onViewDragStateChanged(i);
            if (this.mDragState == 0) {
                this.mCapturedView = null;
            }
        }
    }

    public void setEdgeTrackingEnabled(int i) {
        this.mTrackingEdges = i;
    }

    public void setMinVelocity(float f) {
        this.mMinVelocity = f;
    }

    public boolean settleCapturedViewAt(int i, int i2) {
        if (this.mReleaseInProgress) {
            return forceSettleCapturedViewAt(i, i2, (int) VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), (int) VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId));
        }
        throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x01f6, code lost:
        if (r11 != r13) goto L_0x0205;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean shouldInterceptTouchEvent(android.view.MotionEvent r27) {
        /*
            r26 = this;
            int r4 = android.support.v4.view.MotionEventCompat.getActionMasked(r27)
            int r5 = android.support.v4.view.MotionEventCompat.getActionIndex(r27)
            if (r4 != 0) goto L_0x000d
            r26.cancel()
        L_0x000d:
            r0 = r26
            android.view.VelocityTracker r0 = r0.mVelocityTracker
            r24 = r0
            if (r24 != 0) goto L_0x001f
            android.view.VelocityTracker r24 = android.view.VelocityTracker.obtain()
            r0 = r24
            r1 = r26
            r1.mVelocityTracker = r0
        L_0x001f:
            r0 = r26
            android.view.VelocityTracker r0 = r0.mVelocityTracker
            r24 = r0
            r0 = r24
            r1 = r27
            r0.addMovement(r1)
            switch(r4) {
                case 0: goto L_0x0040;
                case 1: goto L_0x023b;
                case 2: goto L_0x0148;
                case 3: goto L_0x023b;
                case 4: goto L_0x002f;
                case 5: goto L_0x00bf;
                case 6: goto L_0x022c;
                default: goto L_0x002f;
            }
        L_0x002f:
            r0 = r26
            int r0 = r0.mDragState
            r24 = r0
            r25 = 1
            r0 = r24
            r1 = r25
            if (r0 != r1) goto L_0x0240
            r24 = 1
        L_0x003f:
            return r24
        L_0x0040:
            float r22 = r27.getX()
            float r23 = r27.getY()
            r24 = 0
            r0 = r27
            r1 = r24
            int r17 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r1)
            r0 = r26
            r1 = r22
            r2 = r23
            r3 = r17
            r0.saveInitialMotion(r1, r2, r3)
            r0 = r22
            int r0 = (int) r0
            r24 = r0
            r0 = r23
            int r0 = (int) r0
            r25 = r0
            r0 = r26
            r1 = r24
            r2 = r25
            android.view.View r20 = r0.findTopChildUnder(r1, r2)
            r0 = r26
            android.view.View r0 = r0.mCapturedView
            r24 = r0
            r0 = r20
            r1 = r24
            if (r0 != r1) goto L_0x0094
            r0 = r26
            int r0 = r0.mDragState
            r24 = r0
            r25 = 2
            r0 = r24
            r1 = r25
            if (r0 != r1) goto L_0x0094
            r0 = r26
            r1 = r20
            r2 = r17
            r0.tryCaptureViewForDrag(r1, r2)
        L_0x0094:
            r0 = r26
            int[] r0 = r0.mInitialEdgesTouched
            r24 = r0
            r8 = r24[r17]
            r0 = r26
            int r0 = r0.mTrackingEdges
            r24 = r0
            r24 = r24 & r8
            if (r24 == 0) goto L_0x002f
            r0 = r26
            android.support.v4.widget.ViewDragHelper$Callback r0 = r0.mCallback
            r24 = r0
            r0 = r26
            int r0 = r0.mTrackingEdges
            r25 = r0
            r25 = r25 & r8
            r0 = r24
            r1 = r25
            r2 = r17
            r0.onEdgeTouched(r1, r2)
            goto L_0x002f
        L_0x00bf:
            r0 = r27
            int r17 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r5)
            r0 = r27
            float r22 = android.support.v4.view.MotionEventCompat.getX(r0, r5)
            r0 = r27
            float r23 = android.support.v4.view.MotionEventCompat.getY(r0, r5)
            r0 = r26
            r1 = r22
            r2 = r23
            r3 = r17
            r0.saveInitialMotion(r1, r2, r3)
            r0 = r26
            int r0 = r0.mDragState
            r24 = r0
            if (r24 != 0) goto L_0x010f
            r0 = r26
            int[] r0 = r0.mInitialEdgesTouched
            r24 = r0
            r8 = r24[r17]
            r0 = r26
            int r0 = r0.mTrackingEdges
            r24 = r0
            r24 = r24 & r8
            if (r24 == 0) goto L_0x002f
            r0 = r26
            android.support.v4.widget.ViewDragHelper$Callback r0 = r0.mCallback
            r24 = r0
            r0 = r26
            int r0 = r0.mTrackingEdges
            r25 = r0
            r25 = r25 & r8
            r0 = r24
            r1 = r25
            r2 = r17
            r0.onEdgeTouched(r1, r2)
            goto L_0x002f
        L_0x010f:
            r0 = r26
            int r0 = r0.mDragState
            r24 = r0
            r25 = 2
            r0 = r24
            r1 = r25
            if (r0 != r1) goto L_0x002f
            r0 = r22
            int r0 = (int) r0
            r24 = r0
            r0 = r23
            int r0 = (int) r0
            r25 = r0
            r0 = r26
            r1 = r24
            r2 = r25
            android.view.View r20 = r0.findTopChildUnder(r1, r2)
            r0 = r26
            android.view.View r0 = r0.mCapturedView
            r24 = r0
            r0 = r20
            r1 = r24
            if (r0 != r1) goto L_0x002f
            r0 = r26
            r1 = r20
            r2 = r17
            r0.tryCaptureViewForDrag(r1, r2)
            goto L_0x002f
        L_0x0148:
            int r16 = android.support.v4.view.MotionEventCompat.getPointerCount(r27)
            r10 = 0
        L_0x014d:
            r0 = r16
            if (r10 >= r0) goto L_0x01fe
            r0 = r27
            int r17 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r10)
            r0 = r27
            float r22 = android.support.v4.view.MotionEventCompat.getX(r0, r10)
            r0 = r27
            float r23 = android.support.v4.view.MotionEventCompat.getY(r0, r10)
            r0 = r26
            float[] r0 = r0.mInitialMotionX
            r24 = r0
            r24 = r24[r17]
            float r6 = r22 - r24
            r0 = r26
            float[] r0 = r0.mInitialMotionY
            r24 = r0
            r24 = r24[r17]
            float r7 = r23 - r24
            r0 = r22
            int r0 = (int) r0
            r24 = r0
            r0 = r23
            int r0 = (int) r0
            r25 = r0
            r0 = r26
            r1 = r24
            r2 = r25
            android.view.View r20 = r0.findTopChildUnder(r1, r2)
            if (r20 == 0) goto L_0x0203
            r0 = r26
            r1 = r20
            boolean r24 = r0.checkTouchSlop(r1, r6, r7)
            if (r24 == 0) goto L_0x0203
            r15 = 1
        L_0x0198:
            if (r15 == 0) goto L_0x0205
            int r13 = r20.getLeft()
            int r0 = (int) r6
            r24 = r0
            int r18 = r13 + r24
            r0 = r26
            android.support.v4.widget.ViewDragHelper$Callback r0 = r0.mCallback
            r24 = r0
            int r0 = (int) r6
            r25 = r0
            r0 = r24
            r1 = r20
            r2 = r18
            r3 = r25
            int r11 = r0.clampViewPositionHorizontal(r1, r2, r3)
            int r14 = r20.getTop()
            int r0 = (int) r7
            r24 = r0
            int r19 = r14 + r24
            r0 = r26
            android.support.v4.widget.ViewDragHelper$Callback r0 = r0.mCallback
            r24 = r0
            int r0 = (int) r7
            r25 = r0
            r0 = r24
            r1 = r20
            r2 = r19
            r3 = r25
            int r12 = r0.clampViewPositionVertical(r1, r2, r3)
            r0 = r26
            android.support.v4.widget.ViewDragHelper$Callback r0 = r0.mCallback
            r24 = r0
            r0 = r24
            r1 = r20
            int r9 = r0.getViewHorizontalDragRange(r1)
            r0 = r26
            android.support.v4.widget.ViewDragHelper$Callback r0 = r0.mCallback
            r24 = r0
            r0 = r24
            r1 = r20
            int r21 = r0.getViewVerticalDragRange(r1)
            if (r9 == 0) goto L_0x01f8
            if (r9 <= 0) goto L_0x0205
            if (r11 != r13) goto L_0x0205
        L_0x01f8:
            if (r21 == 0) goto L_0x01fe
            if (r21 <= 0) goto L_0x0205
            if (r12 != r14) goto L_0x0205
        L_0x01fe:
            r26.saveLastMotion(r27)
            goto L_0x002f
        L_0x0203:
            r15 = 0
            goto L_0x0198
        L_0x0205:
            r0 = r26
            r1 = r17
            r0.reportNewEdgeDrags(r6, r7, r1)
            r0 = r26
            int r0 = r0.mDragState
            r24 = r0
            r25 = 1
            r0 = r24
            r1 = r25
            if (r0 == r1) goto L_0x01fe
            if (r15 == 0) goto L_0x0228
            r0 = r26
            r1 = r20
            r2 = r17
            boolean r24 = r0.tryCaptureViewForDrag(r1, r2)
            if (r24 != 0) goto L_0x01fe
        L_0x0228:
            int r10 = r10 + 1
            goto L_0x014d
        L_0x022c:
            r0 = r27
            int r17 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r5)
            r0 = r26
            r1 = r17
            r0.clearMotionHistory(r1)
            goto L_0x002f
        L_0x023b:
            r26.cancel()
            goto L_0x002f
        L_0x0240:
            r24 = 0
            goto L_0x003f
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.ViewDragHelper.shouldInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean smoothSlideViewTo(View view, int i, int i2) {
        this.mCapturedView = view;
        this.mActivePointerId = -1;
        boolean forceSettleCapturedViewAt = forceSettleCapturedViewAt(i, i2, 0, 0);
        if (!forceSettleCapturedViewAt && this.mDragState == 0 && this.mCapturedView != null) {
            this.mCapturedView = null;
        }
        return forceSettleCapturedViewAt;
    }

    /* access modifiers changed from: package-private */
    public boolean tryCaptureViewForDrag(View view, int i) {
        if (view == this.mCapturedView && this.mActivePointerId == i) {
            return true;
        }
        if (view == null || !this.mCallback.tryCaptureView(view, i)) {
            return false;
        }
        this.mActivePointerId = i;
        captureChildView(view, i);
        return true;
    }
}
