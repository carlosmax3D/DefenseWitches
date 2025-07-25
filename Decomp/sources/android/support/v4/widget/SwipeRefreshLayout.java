package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;

public class SwipeRefreshLayout extends ViewGroup {
    private static final int ALPHA_ANIMATION_DURATION = 300;
    private static final int ANIMATE_TO_START_DURATION = 200;
    private static final int ANIMATE_TO_TRIGGER_DURATION = 200;
    private static final int CIRCLE_BG_LIGHT = -328966;
    private static final int CIRCLE_DIAMETER = 40;
    private static final int CIRCLE_DIAMETER_LARGE = 56;
    private static final float DECELERATE_INTERPOLATION_FACTOR = 2.0f;
    public static final int DEFAULT = 1;
    private static final int DEFAULT_CIRCLE_TARGET = 64;
    private static final float DRAG_RATE = 0.5f;
    private static final int INVALID_POINTER = -1;
    public static final int LARGE = 0;
    private static final int[] LAYOUT_ATTRS = {16842766};
    private static final String LOG_TAG = SwipeRefreshLayout.class.getSimpleName();
    private static final int MAX_ALPHA = 255;
    private static final float MAX_PROGRESS_ANGLE = 0.8f;
    private static final int SCALE_DOWN_DURATION = 150;
    private static final int STARTING_PROGRESS_ALPHA = 76;
    private int mActivePointerId;
    private Animation mAlphaMaxAnimation;
    private Animation mAlphaStartAnimation;
    private final Animation mAnimateToCorrectPosition;
    private final Animation mAnimateToStartPosition;
    private int mCircleHeight;
    /* access modifiers changed from: private */
    public CircleImageView mCircleView;
    private int mCircleViewIndex;
    private int mCircleWidth;
    /* access modifiers changed from: private */
    public int mCurrentTargetOffsetTop;
    private final DecelerateInterpolator mDecelerateInterpolator;
    protected int mFrom;
    private float mInitialDownY;
    private float mInitialMotionY;
    private boolean mIsBeingDragged;
    /* access modifiers changed from: private */
    public OnRefreshListener mListener;
    private int mMediumAnimationDuration;
    /* access modifiers changed from: private */
    public boolean mNotify;
    private boolean mOriginalOffsetCalculated;
    protected int mOriginalOffsetTop;
    /* access modifiers changed from: private */
    public MaterialProgressDrawable mProgress;
    private Animation.AnimationListener mRefreshListener;
    /* access modifiers changed from: private */
    public boolean mRefreshing;
    private boolean mReturningToStart;
    /* access modifiers changed from: private */
    public boolean mScale;
    private Animation mScaleAnimation;
    private Animation mScaleDownAnimation;
    private Animation mScaleDownToStartAnimation;
    /* access modifiers changed from: private */
    public float mSpinnerFinalOffset;
    /* access modifiers changed from: private */
    public float mStartingScale;
    private View mTarget;
    private float mTotalDragDistance;
    private int mTouchSlop;
    /* access modifiers changed from: private */
    public boolean mUsingCustomStart;

    public interface OnRefreshListener {
        void onRefresh();
    }

    public SwipeRefreshLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public SwipeRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRefreshing = false;
        this.mTotalDragDistance = -1.0f;
        this.mOriginalOffsetCalculated = false;
        this.mActivePointerId = -1;
        this.mCircleViewIndex = -1;
        this.mRefreshListener = new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (SwipeRefreshLayout.this.mRefreshing) {
                    SwipeRefreshLayout.this.mProgress.setAlpha(255);
                    SwipeRefreshLayout.this.mProgress.start();
                    if (SwipeRefreshLayout.this.mNotify && SwipeRefreshLayout.this.mListener != null) {
                        SwipeRefreshLayout.this.mListener.onRefresh();
                    }
                } else {
                    SwipeRefreshLayout.this.mProgress.stop();
                    SwipeRefreshLayout.this.mCircleView.setVisibility(8);
                    SwipeRefreshLayout.this.setColorViewAlpha(255);
                    if (SwipeRefreshLayout.this.mScale) {
                        SwipeRefreshLayout.this.setAnimationProgress(0.0f);
                    } else {
                        SwipeRefreshLayout.this.setTargetOffsetTopAndBottom(SwipeRefreshLayout.this.mOriginalOffsetTop - SwipeRefreshLayout.this.mCurrentTargetOffsetTop, true);
                    }
                }
                int unused = SwipeRefreshLayout.this.mCurrentTargetOffsetTop = SwipeRefreshLayout.this.mCircleView.getTop();
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        };
        this.mAnimateToCorrectPosition = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.setTargetOffsetTopAndBottom((SwipeRefreshLayout.this.mFrom + ((int) (((float) ((!SwipeRefreshLayout.this.mUsingCustomStart ? (int) (SwipeRefreshLayout.this.mSpinnerFinalOffset - ((float) Math.abs(SwipeRefreshLayout.this.mOriginalOffsetTop))) : (int) SwipeRefreshLayout.this.mSpinnerFinalOffset) - SwipeRefreshLayout.this.mFrom)) * f))) - SwipeRefreshLayout.this.mCircleView.getTop(), false);
                SwipeRefreshLayout.this.mProgress.setArrowScale(1.0f - f);
            }
        };
        this.mAnimateToStartPosition = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.moveToStart(f);
            }
        };
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mMediumAnimationDuration = getResources().getInteger(17694721);
        setWillNotDraw(false);
        this.mDecelerateInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, LAYOUT_ATTRS);
        setEnabled(obtainStyledAttributes.getBoolean(0, true));
        obtainStyledAttributes.recycle();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.mCircleWidth = (int) (displayMetrics.density * 40.0f);
        this.mCircleHeight = (int) (displayMetrics.density * 40.0f);
        createProgressView();
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
        this.mSpinnerFinalOffset = 64.0f * displayMetrics.density;
        this.mTotalDragDistance = this.mSpinnerFinalOffset;
    }

    private void animateOffsetToCorrectPosition(int i, Animation.AnimationListener animationListener) {
        this.mFrom = i;
        this.mAnimateToCorrectPosition.reset();
        this.mAnimateToCorrectPosition.setDuration(200);
        this.mAnimateToCorrectPosition.setInterpolator(this.mDecelerateInterpolator);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mAnimateToCorrectPosition);
    }

    private void animateOffsetToStartPosition(int i, Animation.AnimationListener animationListener) {
        if (this.mScale) {
            startScaleDownReturnToStartAnimation(i, animationListener);
            return;
        }
        this.mFrom = i;
        this.mAnimateToStartPosition.reset();
        this.mAnimateToStartPosition.setDuration(200);
        this.mAnimateToStartPosition.setInterpolator(this.mDecelerateInterpolator);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mAnimateToStartPosition);
    }

    private void createProgressView() {
        this.mCircleView = new CircleImageView(getContext(), CIRCLE_BG_LIGHT, 20.0f);
        this.mProgress = new MaterialProgressDrawable(getContext(), this);
        this.mProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
        this.mCircleView.setImageDrawable(this.mProgress);
        this.mCircleView.setVisibility(8);
        addView(this.mCircleView);
    }

    private void ensureTarget() {
        if (this.mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (!childAt.equals(this.mCircleView)) {
                    this.mTarget = childAt;
                    return;
                }
            }
        }
    }

    private float getMotionEventY(MotionEvent motionEvent, int i) {
        int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, i);
        if (findPointerIndex < 0) {
            return -1.0f;
        }
        return MotionEventCompat.getY(motionEvent, findPointerIndex);
    }

    private boolean isAlphaUsedForScale() {
        return Build.VERSION.SDK_INT < 11;
    }

    private boolean isAnimationRunning(Animation animation) {
        return animation != null && animation.hasStarted() && !animation.hasEnded();
    }

    /* access modifiers changed from: private */
    public void moveToStart(float f) {
        setTargetOffsetTopAndBottom((this.mFrom + ((int) (((float) (this.mOriginalOffsetTop - this.mFrom)) * f))) - this.mCircleView.getTop(), false);
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
        if (MotionEventCompat.getPointerId(motionEvent, actionIndex) == this.mActivePointerId) {
            this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, actionIndex == 0 ? 1 : 0);
        }
    }

    /* access modifiers changed from: private */
    public void setAnimationProgress(float f) {
        if (isAlphaUsedForScale()) {
            setColorViewAlpha((int) (255.0f * f));
            return;
        }
        ViewCompat.setScaleX(this.mCircleView, f);
        ViewCompat.setScaleY(this.mCircleView, f);
    }

    /* access modifiers changed from: private */
    public void setColorViewAlpha(int i) {
        this.mCircleView.getBackground().setAlpha(i);
        this.mProgress.setAlpha(i);
    }

    private void setRefreshing(boolean z, boolean z2) {
        if (this.mRefreshing != z) {
            this.mNotify = z2;
            ensureTarget();
            this.mRefreshing = z;
            if (this.mRefreshing) {
                animateOffsetToCorrectPosition(this.mCurrentTargetOffsetTop, this.mRefreshListener);
            } else {
                startScaleDownAnimation(this.mRefreshListener);
            }
        }
    }

    /* access modifiers changed from: private */
    public void setTargetOffsetTopAndBottom(int i, boolean z) {
        this.mCircleView.bringToFront();
        this.mCircleView.offsetTopAndBottom(i);
        this.mCurrentTargetOffsetTop = this.mCircleView.getTop();
        if (z && Build.VERSION.SDK_INT < 11) {
            invalidate();
        }
    }

    private Animation startAlphaAnimation(final int i, final int i2) {
        if (this.mScale && isAlphaUsedForScale()) {
            return null;
        }
        AnonymousClass4 r0 = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.mProgress.setAlpha((int) (((float) i) + (((float) (i2 - i)) * f)));
            }
        };
        r0.setDuration(300);
        this.mCircleView.setAnimationListener((Animation.AnimationListener) null);
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(r0);
        return r0;
    }

    private void startProgressAlphaMaxAnimation() {
        this.mAlphaMaxAnimation = startAlphaAnimation(this.mProgress.getAlpha(), 255);
    }

    private void startProgressAlphaStartAnimation() {
        this.mAlphaStartAnimation = startAlphaAnimation(this.mProgress.getAlpha(), STARTING_PROGRESS_ALPHA);
    }

    /* access modifiers changed from: private */
    public void startScaleDownAnimation(Animation.AnimationListener animationListener) {
        this.mScaleDownAnimation = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(1.0f - f);
            }
        };
        this.mScaleDownAnimation.setDuration(150);
        this.mCircleView.setAnimationListener(animationListener);
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleDownAnimation);
    }

    private void startScaleDownReturnToStartAnimation(int i, Animation.AnimationListener animationListener) {
        this.mFrom = i;
        if (isAlphaUsedForScale()) {
            this.mStartingScale = (float) this.mProgress.getAlpha();
        } else {
            this.mStartingScale = ViewCompat.getScaleX(this.mCircleView);
        }
        this.mScaleDownToStartAnimation = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(SwipeRefreshLayout.this.mStartingScale + ((-SwipeRefreshLayout.this.mStartingScale) * f));
                SwipeRefreshLayout.this.moveToStart(f);
            }
        };
        this.mScaleDownToStartAnimation.setDuration(150);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleDownToStartAnimation);
    }

    private void startScaleUpAnimation(Animation.AnimationListener animationListener) {
        this.mCircleView.setVisibility(0);
        if (Build.VERSION.SDK_INT >= 11) {
            this.mProgress.setAlpha(255);
        }
        this.mScaleAnimation = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(f);
            }
        };
        this.mScaleAnimation.setDuration((long) this.mMediumAnimationDuration);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleAnimation);
    }

    public boolean canChildScrollUp() {
        if (Build.VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(this.mTarget, -1);
        }
        if (!(this.mTarget instanceof AbsListView)) {
            return this.mTarget.getScrollY() > 0;
        }
        AbsListView absListView = (AbsListView) this.mTarget;
        return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i, int i2) {
        return this.mCircleViewIndex < 0 ? i2 : i2 == i + -1 ? this.mCircleViewIndex : i2 >= this.mCircleViewIndex ? i2 + 1 : i2;
    }

    public int getProgressCircleDiameter() {
        if (this.mCircleView != null) {
            return this.mCircleView.getMeasuredHeight();
        }
        return 0;
    }

    public boolean isRefreshing() {
        return this.mRefreshing;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        ensureTarget();
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        if (this.mReturningToStart && actionMasked == 0) {
            this.mReturningToStart = false;
        }
        if (!isEnabled() || this.mReturningToStart || canChildScrollUp() || this.mRefreshing) {
            return false;
        }
        switch (actionMasked) {
            case 0:
                setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.mCircleView.getTop(), true);
                this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                this.mIsBeingDragged = false;
                float motionEventY = getMotionEventY(motionEvent, this.mActivePointerId);
                if (motionEventY != -1.0f) {
                    this.mInitialDownY = motionEventY;
                    break;
                } else {
                    return false;
                }
            case 1:
            case 3:
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                break;
            case 2:
                if (this.mActivePointerId == -1) {
                    Log.e(LOG_TAG, "Got ACTION_MOVE event but don't have an active pointer id.");
                    return false;
                }
                float motionEventY2 = getMotionEventY(motionEvent, this.mActivePointerId);
                if (motionEventY2 != -1.0f) {
                    if (motionEventY2 - this.mInitialDownY > ((float) this.mTouchSlop) && !this.mIsBeingDragged) {
                        this.mInitialMotionY = this.mInitialDownY + ((float) this.mTouchSlop);
                        this.mIsBeingDragged = true;
                        this.mProgress.setAlpha(STARTING_PROGRESS_ALPHA);
                        break;
                    }
                } else {
                    return false;
                }
            case 6:
                onSecondaryPointerUp(motionEvent);
                break;
        }
        return this.mIsBeingDragged;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (getChildCount() != 0) {
            if (this.mTarget == null) {
                ensureTarget();
            }
            if (this.mTarget != null) {
                View view = this.mTarget;
                int paddingLeft = getPaddingLeft();
                int paddingTop = getPaddingTop();
                view.layout(paddingLeft, paddingTop, paddingLeft + ((measuredWidth - getPaddingLeft()) - getPaddingRight()), paddingTop + ((measuredHeight - getPaddingTop()) - getPaddingBottom()));
                int measuredWidth2 = this.mCircleView.getMeasuredWidth();
                this.mCircleView.layout((measuredWidth / 2) - (measuredWidth2 / 2), this.mCurrentTargetOffsetTop, (measuredWidth / 2) + (measuredWidth2 / 2), this.mCurrentTargetOffsetTop + this.mCircleView.getMeasuredHeight());
            }
        }
    }

    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mTarget == null) {
            ensureTarget();
        }
        if (this.mTarget != null) {
            this.mTarget.measure(View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824));
            this.mCircleView.measure(View.MeasureSpec.makeMeasureSpec(this.mCircleWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mCircleHeight, 1073741824));
            if (!this.mUsingCustomStart && !this.mOriginalOffsetCalculated) {
                this.mOriginalOffsetCalculated = true;
                int i3 = -this.mCircleView.getMeasuredHeight();
                this.mOriginalOffsetTop = i3;
                this.mCurrentTargetOffsetTop = i3;
            }
            this.mCircleViewIndex = -1;
            for (int i4 = 0; i4 < getChildCount(); i4++) {
                if (getChildAt(i4) == this.mCircleView) {
                    this.mCircleViewIndex = i4;
                    return;
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        if (this.mReturningToStart && actionMasked == 0) {
            this.mReturningToStart = false;
        }
        if (!isEnabled() || this.mReturningToStart || canChildScrollUp()) {
            return false;
        }
        switch (actionMasked) {
            case 0:
                this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                this.mIsBeingDragged = false;
                break;
            case 1:
            case 3:
                if (this.mActivePointerId == -1) {
                    if (actionMasked == 1) {
                        Log.e(LOG_TAG, "Got ACTION_UP event but don't have an active pointer id.");
                    }
                    return false;
                }
                float y = (MotionEventCompat.getY(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId)) - this.mInitialMotionY) * DRAG_RATE;
                this.mIsBeingDragged = false;
                if (y > this.mTotalDragDistance) {
                    setRefreshing(true, true);
                } else {
                    this.mRefreshing = false;
                    this.mProgress.setStartEndTrim(0.0f, 0.0f);
                    AnonymousClass5 r10 = null;
                    if (!this.mScale) {
                        r10 = new Animation.AnimationListener() {
                            public void onAnimationEnd(Animation animation) {
                                if (!SwipeRefreshLayout.this.mScale) {
                                    SwipeRefreshLayout.this.startScaleDownAnimation((Animation.AnimationListener) null);
                                }
                            }

                            public void onAnimationRepeat(Animation animation) {
                            }

                            public void onAnimationStart(Animation animation) {
                            }
                        };
                    }
                    animateOffsetToStartPosition(this.mCurrentTargetOffsetTop, r10);
                    this.mProgress.showArrow(false);
                }
                this.mActivePointerId = -1;
                return false;
            case 2:
                int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId);
                if (findPointerIndex >= 0) {
                    float y2 = (MotionEventCompat.getY(motionEvent, findPointerIndex) - this.mInitialMotionY) * DRAG_RATE;
                    if (this.mIsBeingDragged) {
                        this.mProgress.showArrow(true);
                        float f = y2 / this.mTotalDragDistance;
                        if (f >= 0.0f) {
                            float min = Math.min(1.0f, Math.abs(f));
                            float max = (((float) Math.max(((double) min) - 0.4d, 0.0d)) * 5.0f) / 3.0f;
                            float abs = Math.abs(y2) - this.mTotalDragDistance;
                            float f2 = this.mUsingCustomStart ? this.mSpinnerFinalOffset - ((float) this.mOriginalOffsetTop) : this.mSpinnerFinalOffset;
                            float max2 = Math.max(0.0f, Math.min(abs, DECELERATE_INTERPOLATION_FACTOR * f2) / f2);
                            float pow = ((float) (((double) (max2 / 4.0f)) - Math.pow((double) (max2 / 4.0f), 2.0d))) * DECELERATE_INTERPOLATION_FACTOR;
                            int i = this.mOriginalOffsetTop + ((int) ((f2 * min) + (f2 * pow * DECELERATE_INTERPOLATION_FACTOR)));
                            if (this.mCircleView.getVisibility() != 0) {
                                this.mCircleView.setVisibility(0);
                            }
                            if (!this.mScale) {
                                ViewCompat.setScaleX(this.mCircleView, 1.0f);
                                ViewCompat.setScaleY(this.mCircleView, 1.0f);
                            }
                            if (y2 < this.mTotalDragDistance) {
                                if (this.mScale) {
                                    setAnimationProgress(y2 / this.mTotalDragDistance);
                                }
                                if (this.mProgress.getAlpha() > STARTING_PROGRESS_ALPHA) {
                                    if (!isAnimationRunning(this.mAlphaStartAnimation)) {
                                        startProgressAlphaStartAnimation();
                                    }
                                }
                                this.mProgress.setStartEndTrim(0.0f, Math.min(MAX_PROGRESS_ANGLE, max * MAX_PROGRESS_ANGLE));
                                this.mProgress.setArrowScale(Math.min(1.0f, max));
                            } else if (this.mProgress.getAlpha() < 255) {
                                if (!isAnimationRunning(this.mAlphaMaxAnimation)) {
                                    startProgressAlphaMaxAnimation();
                                }
                            }
                            this.mProgress.setProgressRotation((-0.25f + (0.4f * max) + (DECELERATE_INTERPOLATION_FACTOR * pow)) * DRAG_RATE);
                            setTargetOffsetTopAndBottom(i - this.mCurrentTargetOffsetTop, true);
                            break;
                        } else {
                            return false;
                        }
                    }
                } else {
                    Log.e(LOG_TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                break;
            case 5:
                this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, MotionEventCompat.getActionIndex(motionEvent));
                break;
            case 6:
                onSecondaryPointerUp(motionEvent);
                break;
        }
        return true;
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
    }

    @Deprecated
    public void setColorScheme(int... iArr) {
        setColorSchemeResources(iArr);
    }

    public void setColorSchemeColors(int... iArr) {
        ensureTarget();
        this.mProgress.setColorSchemeColors(iArr);
    }

    public void setColorSchemeResources(int... iArr) {
        Resources resources = getResources();
        int[] iArr2 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = resources.getColor(iArr[i]);
        }
        setColorSchemeColors(iArr2);
    }

    public void setDistanceToTriggerSync(int i) {
        this.mTotalDragDistance = (float) i;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mListener = onRefreshListener;
    }

    @Deprecated
    public void setProgressBackgroundColor(int i) {
        setProgressBackgroundColorSchemeResource(i);
    }

    public void setProgressBackgroundColorSchemeColor(int i) {
        this.mCircleView.setBackgroundColor(i);
        this.mProgress.setBackgroundColor(i);
    }

    public void setProgressBackgroundColorSchemeResource(int i) {
        setProgressBackgroundColorSchemeColor(getResources().getColor(i));
    }

    public void setProgressViewEndTarget(boolean z, int i) {
        this.mSpinnerFinalOffset = (float) i;
        this.mScale = z;
        this.mCircleView.invalidate();
    }

    public void setProgressViewOffset(boolean z, int i, int i2) {
        this.mScale = z;
        this.mCircleView.setVisibility(8);
        this.mCurrentTargetOffsetTop = i;
        this.mOriginalOffsetTop = i;
        this.mSpinnerFinalOffset = (float) i2;
        this.mUsingCustomStart = true;
        this.mCircleView.invalidate();
    }

    public void setRefreshing(boolean z) {
        if (!z || this.mRefreshing == z) {
            setRefreshing(z, false);
            return;
        }
        this.mRefreshing = z;
        setTargetOffsetTopAndBottom((!this.mUsingCustomStart ? (int) (this.mSpinnerFinalOffset + ((float) this.mOriginalOffsetTop)) : (int) this.mSpinnerFinalOffset) - this.mCurrentTargetOffsetTop, true);
        this.mNotify = false;
        startScaleUpAnimation(this.mRefreshListener);
    }

    public void setSize(int i) {
        if (i == 0 || i == 1) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            if (i == 0) {
                int i2 = (int) (56.0f * displayMetrics.density);
                this.mCircleWidth = i2;
                this.mCircleHeight = i2;
            } else {
                int i3 = (int) (40.0f * displayMetrics.density);
                this.mCircleWidth = i3;
                this.mCircleHeight = i3;
            }
            this.mCircleView.setImageDrawable((Drawable) null);
            this.mProgress.updateSizes(i);
            this.mCircleView.setImageDrawable(this.mProgress);
        }
    }
}
