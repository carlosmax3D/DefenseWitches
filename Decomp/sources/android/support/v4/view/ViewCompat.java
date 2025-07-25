package android.support.v4.view;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

public class ViewCompat {
    public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
    public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
    private static final long FAKE_FRAME_TIME = 10;
    static final ViewCompatImpl IMPL;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
    public static final int LAYER_TYPE_HARDWARE = 2;
    public static final int LAYER_TYPE_NONE = 0;
    public static final int LAYER_TYPE_SOFTWARE = 1;
    public static final int LAYOUT_DIRECTION_INHERIT = 2;
    public static final int LAYOUT_DIRECTION_LOCALE = 3;
    public static final int LAYOUT_DIRECTION_LTR = 0;
    public static final int LAYOUT_DIRECTION_RTL = 1;
    public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
    public static final int MEASURED_SIZE_MASK = 16777215;
    public static final int MEASURED_STATE_MASK = -16777216;
    public static final int MEASURED_STATE_TOO_SMALL = 16777216;
    public static final int OVER_SCROLL_ALWAYS = 0;
    public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
    public static final int OVER_SCROLL_NEVER = 2;
    private static final String TAG = "ViewCompat";

    @IntDef({0, 1, 2})
    @Retention(RetentionPolicy.SOURCE)
    private @interface AccessibilityLiveRegion {
    }

    static class Api21ViewCompatImpl extends KitKatViewCompatImpl {
        Api21ViewCompatImpl() {
        }

        public float getElevation(View view) {
            return ViewCompatApi21.getElevation(view);
        }

        public String getTransitionName(View view) {
            return ViewCompatApi21.getTransitionName(view);
        }

        public float getTranslationZ(View view) {
            return ViewCompatApi21.getTranslationZ(view);
        }

        public boolean isImportantForAccessibility(View view) {
            return ViewCompatApi21.isImportantForAccessibility(view);
        }

        public void requestApplyInsets(View view) {
            ViewCompatApi21.requestApplyInsets(view);
        }

        public void setElevation(View view, float f) {
            ViewCompatApi21.setElevation(view, f);
        }

        public void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
            ViewCompatApi21.setOnApplyWindowInsetsListener(view, onApplyWindowInsetsListener);
        }

        public void setTransitionName(View view, String str) {
            ViewCompatApi21.setTransitionName(view, str);
        }

        public void setTranslationZ(View view, float f) {
            ViewCompatApi21.setTranslationZ(view, f);
        }
    }

    static class BaseViewCompatImpl implements ViewCompatImpl {
        private Method mDispatchFinishTemporaryDetach;
        private Method mDispatchStartTemporaryDetach;
        private boolean mTempDetachBound;
        WeakHashMap<View, ViewPropertyAnimatorCompat> mViewPropertyAnimatorCompatMap = null;

        BaseViewCompatImpl() {
        }

        private void bindTempDetach() {
            try {
                this.mDispatchStartTemporaryDetach = View.class.getDeclaredMethod("dispatchStartTemporaryDetach", new Class[0]);
                this.mDispatchFinishTemporaryDetach = View.class.getDeclaredMethod("dispatchFinishTemporaryDetach", new Class[0]);
            } catch (NoSuchMethodException e) {
                Log.e("ViewCompat", "Couldn't find method", e);
            }
            this.mTempDetachBound = true;
        }

        public ViewPropertyAnimatorCompat animate(View view) {
            return new ViewPropertyAnimatorCompat(view);
        }

        public boolean canScrollHorizontally(View view, int i) {
            return false;
        }

        public boolean canScrollVertically(View view, int i) {
            return false;
        }

        public void dispatchFinishTemporaryDetach(View view) {
            if (!this.mTempDetachBound) {
                bindTempDetach();
            }
            if (this.mDispatchFinishTemporaryDetach != null) {
                try {
                    this.mDispatchFinishTemporaryDetach.invoke(view, new Object[0]);
                } catch (Exception e) {
                    Log.d("ViewCompat", "Error calling dispatchFinishTemporaryDetach", e);
                }
            } else {
                view.onFinishTemporaryDetach();
            }
        }

        public void dispatchStartTemporaryDetach(View view) {
            if (!this.mTempDetachBound) {
                bindTempDetach();
            }
            if (this.mDispatchStartTemporaryDetach != null) {
                try {
                    this.mDispatchStartTemporaryDetach.invoke(view, new Object[0]);
                } catch (Exception e) {
                    Log.d("ViewCompat", "Error calling dispatchStartTemporaryDetach", e);
                }
            } else {
                view.onStartTemporaryDetach();
            }
        }

        public int getAccessibilityLiveRegion(View view) {
            return 0;
        }

        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
            return null;
        }

        public float getAlpha(View view) {
            return 1.0f;
        }

        public float getElevation(View view) {
            return 0.0f;
        }

        public boolean getFitsSystemWindows(View view) {
            return false;
        }

        /* access modifiers changed from: package-private */
        public long getFrameTime() {
            return ViewCompat.FAKE_FRAME_TIME;
        }

        public int getImportantForAccessibility(View view) {
            return 0;
        }

        public int getLabelFor(View view) {
            return 0;
        }

        public int getLayerType(View view) {
            return 0;
        }

        public int getLayoutDirection(View view) {
            return 0;
        }

        public int getMeasuredHeightAndState(View view) {
            return view.getMeasuredHeight();
        }

        public int getMeasuredState(View view) {
            return 0;
        }

        public int getMeasuredWidthAndState(View view) {
            return view.getMeasuredWidth();
        }

        public int getMinimumHeight(View view) {
            return 0;
        }

        public int getMinimumWidth(View view) {
            return 0;
        }

        public int getOverScrollMode(View view) {
            return 2;
        }

        public int getPaddingEnd(View view) {
            return view.getPaddingRight();
        }

        public int getPaddingStart(View view) {
            return view.getPaddingLeft();
        }

        public ViewParent getParentForAccessibility(View view) {
            return view.getParent();
        }

        public float getPivotX(View view) {
            return 0.0f;
        }

        public float getPivotY(View view) {
            return 0.0f;
        }

        public float getRotation(View view) {
            return 0.0f;
        }

        public float getRotationX(View view) {
            return 0.0f;
        }

        public float getRotationY(View view) {
            return 0.0f;
        }

        public float getScaleX(View view) {
            return 0.0f;
        }

        public float getScaleY(View view) {
            return 0.0f;
        }

        public String getTransitionName(View view) {
            return null;
        }

        public float getTranslationX(View view) {
            return 0.0f;
        }

        public float getTranslationY(View view) {
            return 0.0f;
        }

        public float getTranslationZ(View view) {
            return 0.0f;
        }

        public int getWindowSystemUiVisibility(View view) {
            return 0;
        }

        public float getX(View view) {
            return 0.0f;
        }

        public float getY(View view) {
            return 0.0f;
        }

        public boolean hasAccessibilityDelegate(View view) {
            return false;
        }

        public boolean hasTransientState(View view) {
            return false;
        }

        public boolean isImportantForAccessibility(View view) {
            return true;
        }

        public boolean isOpaque(View view) {
            Drawable background = view.getBackground();
            return background != null && background.getOpacity() == -1;
        }

        public void jumpDrawablesToCurrentState(View view) {
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        }

        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            return false;
        }

        public void postInvalidateOnAnimation(View view) {
            view.invalidate();
        }

        public void postInvalidateOnAnimation(View view, int i, int i2, int i3, int i4) {
            view.invalidate(i, i2, i3, i4);
        }

        public void postOnAnimation(View view, Runnable runnable) {
            view.postDelayed(runnable, getFrameTime());
        }

        public void postOnAnimationDelayed(View view, Runnable runnable, long j) {
            view.postDelayed(runnable, getFrameTime() + j);
        }

        public void requestApplyInsets(View view) {
        }

        public int resolveSizeAndState(int i, int i2, int i3) {
            return View.resolveSize(i, i2);
        }

        public void setAccessibilityDelegate(View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
        }

        public void setAccessibilityLiveRegion(View view, int i) {
        }

        public void setActivated(View view, boolean z) {
        }

        public void setAlpha(View view, float f) {
        }

        public void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean z) {
        }

        public void setElevation(View view, float f) {
        }

        public void setFitsSystemWindows(View view, boolean z) {
        }

        public void setHasTransientState(View view, boolean z) {
        }

        public void setImportantForAccessibility(View view, int i) {
        }

        public void setLabelFor(View view, int i) {
        }

        public void setLayerPaint(View view, Paint paint) {
        }

        public void setLayerType(View view, int i, Paint paint) {
        }

        public void setLayoutDirection(View view, int i) {
        }

        public void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        }

        public void setOverScrollMode(View view, int i) {
        }

        public void setPaddingRelative(View view, int i, int i2, int i3, int i4) {
            view.setPadding(i, i2, i3, i4);
        }

        public void setPivotX(View view, float f) {
        }

        public void setPivotY(View view, float f) {
        }

        public void setRotation(View view, float f) {
        }

        public void setRotationX(View view, float f) {
        }

        public void setRotationY(View view, float f) {
        }

        public void setSaveFromParentEnabled(View view, boolean z) {
        }

        public void setScaleX(View view, float f) {
        }

        public void setScaleY(View view, float f) {
        }

        public void setTransitionName(View view, String str) {
        }

        public void setTranslationX(View view, float f) {
        }

        public void setTranslationY(View view, float f) {
        }

        public void setTranslationZ(View view, float f) {
        }

        public void setX(View view, float f) {
        }

        public void setY(View view, float f) {
        }
    }

    static class EclairMr1ViewCompatImpl extends BaseViewCompatImpl {
        EclairMr1ViewCompatImpl() {
        }

        public boolean isOpaque(View view) {
            return ViewCompatEclairMr1.isOpaque(view);
        }

        public void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean z) {
            ViewCompatEclairMr1.setChildrenDrawingOrderEnabled(viewGroup, z);
        }
    }

    static class GBViewCompatImpl extends EclairMr1ViewCompatImpl {
        GBViewCompatImpl() {
        }

        public int getOverScrollMode(View view) {
            return ViewCompatGingerbread.getOverScrollMode(view);
        }

        public void setOverScrollMode(View view, int i) {
            ViewCompatGingerbread.setOverScrollMode(view, i);
        }
    }

    static class HCViewCompatImpl extends GBViewCompatImpl {
        HCViewCompatImpl() {
        }

        public float getAlpha(View view) {
            return ViewCompatHC.getAlpha(view);
        }

        /* access modifiers changed from: package-private */
        public long getFrameTime() {
            return ViewCompatHC.getFrameTime();
        }

        public int getLayerType(View view) {
            return ViewCompatHC.getLayerType(view);
        }

        public int getMeasuredHeightAndState(View view) {
            return ViewCompatHC.getMeasuredHeightAndState(view);
        }

        public int getMeasuredState(View view) {
            return ViewCompatHC.getMeasuredState(view);
        }

        public int getMeasuredWidthAndState(View view) {
            return ViewCompatHC.getMeasuredWidthAndState(view);
        }

        public float getPivotX(View view) {
            return ViewCompatHC.getPivotX(view);
        }

        public float getPivotY(View view) {
            return ViewCompatHC.getPivotY(view);
        }

        public float getRotation(View view) {
            return ViewCompatHC.getRotation(view);
        }

        public float getRotationX(View view) {
            return ViewCompatHC.getRotationX(view);
        }

        public float getRotationY(View view) {
            return ViewCompatHC.getRotationY(view);
        }

        public float getScaleX(View view) {
            return ViewCompatHC.getScaleX(view);
        }

        public float getScaleY(View view) {
            return ViewCompatHC.getScaleY(view);
        }

        public float getTranslationX(View view) {
            return ViewCompatHC.getTranslationX(view);
        }

        public float getTranslationY(View view) {
            return ViewCompatHC.getTranslationY(view);
        }

        public float getX(View view) {
            return ViewCompatHC.getX(view);
        }

        public float getY(View view) {
            return ViewCompatHC.getY(view);
        }

        public void jumpDrawablesToCurrentState(View view) {
            ViewCompatHC.jumpDrawablesToCurrentState(view);
        }

        public int resolveSizeAndState(int i, int i2, int i3) {
            return ViewCompatHC.resolveSizeAndState(i, i2, i3);
        }

        public void setActivated(View view, boolean z) {
            ViewCompatHC.setActivated(view, z);
        }

        public void setAlpha(View view, float f) {
            ViewCompatHC.setAlpha(view, f);
        }

        public void setLayerPaint(View view, Paint paint) {
            setLayerType(view, getLayerType(view), paint);
            view.invalidate();
        }

        public void setLayerType(View view, int i, Paint paint) {
            ViewCompatHC.setLayerType(view, i, paint);
        }

        public void setPivotX(View view, float f) {
            ViewCompatHC.setPivotX(view, f);
        }

        public void setPivotY(View view, float f) {
            ViewCompatHC.setPivotY(view, f);
        }

        public void setRotation(View view, float f) {
            ViewCompatHC.setRotation(view, f);
        }

        public void setRotationX(View view, float f) {
            ViewCompatHC.setRotationX(view, f);
        }

        public void setRotationY(View view, float f) {
            ViewCompatHC.setRotationY(view, f);
        }

        public void setSaveFromParentEnabled(View view, boolean z) {
            ViewCompatHC.setSaveFromParentEnabled(view, z);
        }

        public void setScaleX(View view, float f) {
            ViewCompatHC.setScaleX(view, f);
        }

        public void setScaleY(View view, float f) {
            ViewCompatHC.setScaleY(view, f);
        }

        public void setTranslationX(View view, float f) {
            ViewCompatHC.setTranslationX(view, f);
        }

        public void setTranslationY(View view, float f) {
            ViewCompatHC.setTranslationY(view, f);
        }

        public void setX(View view, float f) {
            ViewCompatHC.setX(view, f);
        }

        public void setY(View view, float f) {
            ViewCompatHC.setY(view, f);
        }
    }

    static class ICSViewCompatImpl extends HCViewCompatImpl {
        static boolean accessibilityDelegateCheckFailed = false;
        static Field mAccessibilityDelegateField;

        ICSViewCompatImpl() {
        }

        public ViewPropertyAnimatorCompat animate(View view) {
            if (this.mViewPropertyAnimatorCompatMap == null) {
                this.mViewPropertyAnimatorCompatMap = new WeakHashMap();
            }
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = (ViewPropertyAnimatorCompat) this.mViewPropertyAnimatorCompatMap.get(view);
            if (viewPropertyAnimatorCompat != null) {
                return viewPropertyAnimatorCompat;
            }
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = new ViewPropertyAnimatorCompat(view);
            this.mViewPropertyAnimatorCompatMap.put(view, viewPropertyAnimatorCompat2);
            return viewPropertyAnimatorCompat2;
        }

        public boolean canScrollHorizontally(View view, int i) {
            return ViewCompatICS.canScrollHorizontally(view, i);
        }

        public boolean canScrollVertically(View view, int i) {
            return ViewCompatICS.canScrollVertically(view, i);
        }

        public boolean hasAccessibilityDelegate(View view) {
            boolean z = true;
            if (accessibilityDelegateCheckFailed) {
                return false;
            }
            if (mAccessibilityDelegateField == null) {
                try {
                    mAccessibilityDelegateField = View.class.getDeclaredField("mAccessibilityDelegate");
                    mAccessibilityDelegateField.setAccessible(true);
                } catch (Throwable th) {
                    accessibilityDelegateCheckFailed = true;
                    return false;
                }
            }
            try {
                if (mAccessibilityDelegateField.get(view) == null) {
                    z = false;
                }
                return z;
            } catch (Throwable th2) {
                accessibilityDelegateCheckFailed = true;
                return false;
            }
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            ViewCompatICS.onInitializeAccessibilityEvent(view, accessibilityEvent);
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            ViewCompatICS.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.getInfo());
        }

        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            ViewCompatICS.onPopulateAccessibilityEvent(view, accessibilityEvent);
        }

        public void setAccessibilityDelegate(View view, @Nullable AccessibilityDelegateCompat accessibilityDelegateCompat) {
            ViewCompatICS.setAccessibilityDelegate(view, accessibilityDelegateCompat == null ? null : accessibilityDelegateCompat.getBridge());
        }

        public void setFitsSystemWindows(View view, boolean z) {
            ViewCompatICS.setFitsSystemWindows(view, z);
        }
    }

    @IntDef({0, 1, 2, 4})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ImportantForAccessibility {
    }

    static class JBViewCompatImpl extends ICSViewCompatImpl {
        JBViewCompatImpl() {
        }

        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
            Object accessibilityNodeProvider = ViewCompatJB.getAccessibilityNodeProvider(view);
            if (accessibilityNodeProvider != null) {
                return new AccessibilityNodeProviderCompat(accessibilityNodeProvider);
            }
            return null;
        }

        public boolean getFitsSystemWindows(View view) {
            return ViewCompatJB.getFitsSystemWindows(view);
        }

        public int getImportantForAccessibility(View view) {
            return ViewCompatJB.getImportantForAccessibility(view);
        }

        public int getMinimumHeight(View view) {
            return ViewCompatJB.getMinimumHeight(view);
        }

        public int getMinimumWidth(View view) {
            return ViewCompatJB.getMinimumWidth(view);
        }

        public ViewParent getParentForAccessibility(View view) {
            return ViewCompatJB.getParentForAccessibility(view);
        }

        public boolean hasTransientState(View view) {
            return ViewCompatJB.hasTransientState(view);
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            return ViewCompatJB.performAccessibilityAction(view, i, bundle);
        }

        public void postInvalidateOnAnimation(View view) {
            ViewCompatJB.postInvalidateOnAnimation(view);
        }

        public void postInvalidateOnAnimation(View view, int i, int i2, int i3, int i4) {
            ViewCompatJB.postInvalidateOnAnimation(view, i, i2, i3, i4);
        }

        public void postOnAnimation(View view, Runnable runnable) {
            ViewCompatJB.postOnAnimation(view, runnable);
        }

        public void postOnAnimationDelayed(View view, Runnable runnable, long j) {
            ViewCompatJB.postOnAnimationDelayed(view, runnable, j);
        }

        public void requestApplyInsets(View view) {
            ViewCompatJB.requestApplyInsets(view);
        }

        public void setHasTransientState(View view, boolean z) {
            ViewCompatJB.setHasTransientState(view, z);
        }

        public void setImportantForAccessibility(View view, int i) {
            if (i == 4) {
                i = 2;
            }
            ViewCompatJB.setImportantForAccessibility(view, i);
        }
    }

    static class JbMr1ViewCompatImpl extends JBViewCompatImpl {
        JbMr1ViewCompatImpl() {
        }

        public int getLabelFor(View view) {
            return ViewCompatJellybeanMr1.getLabelFor(view);
        }

        public int getLayoutDirection(View view) {
            return ViewCompatJellybeanMr1.getLayoutDirection(view);
        }

        public int getPaddingEnd(View view) {
            return ViewCompatJellybeanMr1.getPaddingEnd(view);
        }

        public int getPaddingStart(View view) {
            return ViewCompatJellybeanMr1.getPaddingStart(view);
        }

        public int getWindowSystemUiVisibility(View view) {
            return ViewCompatJellybeanMr1.getWindowSystemUiVisibility(view);
        }

        public void setLabelFor(View view, int i) {
            ViewCompatJellybeanMr1.setLabelFor(view, i);
        }

        public void setLayerPaint(View view, Paint paint) {
            ViewCompatJellybeanMr1.setLayerPaint(view, paint);
        }

        public void setLayoutDirection(View view, int i) {
            ViewCompatJellybeanMr1.setLayoutDirection(view, i);
        }

        public void setPaddingRelative(View view, int i, int i2, int i3, int i4) {
            ViewCompatJellybeanMr1.setPaddingRelative(view, i, i2, i3, i4);
        }
    }

    static class KitKatViewCompatImpl extends JbMr1ViewCompatImpl {
        KitKatViewCompatImpl() {
        }

        public int getAccessibilityLiveRegion(View view) {
            return ViewCompatKitKat.getAccessibilityLiveRegion(view);
        }

        public void setAccessibilityLiveRegion(View view, int i) {
            ViewCompatKitKat.setAccessibilityLiveRegion(view, i);
        }

        public void setImportantForAccessibility(View view, int i) {
            ViewCompatJB.setImportantForAccessibility(view, i);
        }
    }

    @IntDef({0, 1, 2})
    @Retention(RetentionPolicy.SOURCE)
    private @interface LayerType {
    }

    @IntDef({0, 1, 2, 3})
    @Retention(RetentionPolicy.SOURCE)
    private @interface LayoutDirectionMode {
    }

    @IntDef({0, 1, 1})
    @Retention(RetentionPolicy.SOURCE)
    private @interface OverScroll {
    }

    @IntDef({0, 1})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ResolvedLayoutDirectionMode {
    }

    interface ViewCompatImpl {
        ViewPropertyAnimatorCompat animate(View view);

        boolean canScrollHorizontally(View view, int i);

        boolean canScrollVertically(View view, int i);

        void dispatchFinishTemporaryDetach(View view);

        void dispatchStartTemporaryDetach(View view);

        int getAccessibilityLiveRegion(View view);

        AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view);

        float getAlpha(View view);

        float getElevation(View view);

        boolean getFitsSystemWindows(View view);

        int getImportantForAccessibility(View view);

        int getLabelFor(View view);

        int getLayerType(View view);

        int getLayoutDirection(View view);

        int getMeasuredHeightAndState(View view);

        int getMeasuredState(View view);

        int getMeasuredWidthAndState(View view);

        int getMinimumHeight(View view);

        int getMinimumWidth(View view);

        int getOverScrollMode(View view);

        int getPaddingEnd(View view);

        int getPaddingStart(View view);

        ViewParent getParentForAccessibility(View view);

        float getPivotX(View view);

        float getPivotY(View view);

        float getRotation(View view);

        float getRotationX(View view);

        float getRotationY(View view);

        float getScaleX(View view);

        float getScaleY(View view);

        String getTransitionName(View view);

        float getTranslationX(View view);

        float getTranslationY(View view);

        float getTranslationZ(View view);

        int getWindowSystemUiVisibility(View view);

        float getX(View view);

        float getY(View view);

        boolean hasAccessibilityDelegate(View view);

        boolean hasTransientState(View view);

        boolean isImportantForAccessibility(View view);

        boolean isOpaque(View view);

        void jumpDrawablesToCurrentState(View view);

        void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

        void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

        void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

        boolean performAccessibilityAction(View view, int i, Bundle bundle);

        void postInvalidateOnAnimation(View view);

        void postInvalidateOnAnimation(View view, int i, int i2, int i3, int i4);

        void postOnAnimation(View view, Runnable runnable);

        void postOnAnimationDelayed(View view, Runnable runnable, long j);

        void requestApplyInsets(View view);

        int resolveSizeAndState(int i, int i2, int i3);

        void setAccessibilityDelegate(View view, @Nullable AccessibilityDelegateCompat accessibilityDelegateCompat);

        void setAccessibilityLiveRegion(View view, int i);

        void setActivated(View view, boolean z);

        void setAlpha(View view, float f);

        void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean z);

        void setElevation(View view, float f);

        void setFitsSystemWindows(View view, boolean z);

        void setHasTransientState(View view, boolean z);

        void setImportantForAccessibility(View view, int i);

        void setLabelFor(View view, int i);

        void setLayerPaint(View view, Paint paint);

        void setLayerType(View view, int i, Paint paint);

        void setLayoutDirection(View view, int i);

        void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener);

        void setOverScrollMode(View view, int i);

        void setPaddingRelative(View view, int i, int i2, int i3, int i4);

        void setPivotX(View view, float f);

        void setPivotY(View view, float f);

        void setRotation(View view, float f);

        void setRotationX(View view, float f);

        void setRotationY(View view, float f);

        void setSaveFromParentEnabled(View view, boolean z);

        void setScaleX(View view, float f);

        void setScaleY(View view, float f);

        void setTransitionName(View view, String str);

        void setTranslationX(View view, float f);

        void setTranslationY(View view, float f);

        void setTranslationZ(View view, float f);

        void setX(View view, float f);

        void setY(View view, float f);
    }

    static {
        int i = Build.VERSION.SDK_INT;
        if (i >= 21) {
            IMPL = new Api21ViewCompatImpl();
        } else if (i >= 19) {
            IMPL = new KitKatViewCompatImpl();
        } else if (i >= 17) {
            IMPL = new JbMr1ViewCompatImpl();
        } else if (i >= 16) {
            IMPL = new JBViewCompatImpl();
        } else if (i >= 14) {
            IMPL = new ICSViewCompatImpl();
        } else if (i >= 11) {
            IMPL = new HCViewCompatImpl();
        } else if (i >= 9) {
            IMPL = new GBViewCompatImpl();
        } else if (i >= 7) {
            IMPL = new EclairMr1ViewCompatImpl();
        } else {
            IMPL = new BaseViewCompatImpl();
        }
    }

    public static ViewPropertyAnimatorCompat animate(View view) {
        return IMPL.animate(view);
    }

    public static boolean canScrollHorizontally(View view, int i) {
        return IMPL.canScrollHorizontally(view, i);
    }

    public static boolean canScrollVertically(View view, int i) {
        return IMPL.canScrollVertically(view, i);
    }

    public static void dispatchFinishTemporaryDetach(View view) {
        IMPL.dispatchFinishTemporaryDetach(view);
    }

    public static void dispatchStartTemporaryDetach(View view) {
        IMPL.dispatchStartTemporaryDetach(view);
    }

    public static int getAccessibilityLiveRegion(View view) {
        return IMPL.getAccessibilityLiveRegion(view);
    }

    public static AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        return IMPL.getAccessibilityNodeProvider(view);
    }

    public static float getAlpha(View view) {
        return IMPL.getAlpha(view);
    }

    public static float getElevation(View view) {
        return IMPL.getElevation(view);
    }

    public static boolean getFitsSystemWindows(View view) {
        return IMPL.getFitsSystemWindows(view);
    }

    public static int getImportantForAccessibility(View view) {
        return IMPL.getImportantForAccessibility(view);
    }

    public static int getLabelFor(View view) {
        return IMPL.getLabelFor(view);
    }

    public static int getLayerType(View view) {
        return IMPL.getLayerType(view);
    }

    public static int getLayoutDirection(View view) {
        return IMPL.getLayoutDirection(view);
    }

    public static int getMeasuredHeightAndState(View view) {
        return IMPL.getMeasuredHeightAndState(view);
    }

    public static int getMeasuredState(View view) {
        return IMPL.getMeasuredState(view);
    }

    public static int getMeasuredWidthAndState(View view) {
        return IMPL.getMeasuredWidthAndState(view);
    }

    public static int getMinimumHeight(View view) {
        return IMPL.getMinimumHeight(view);
    }

    public static int getMinimumWidth(View view) {
        return IMPL.getMinimumWidth(view);
    }

    public static int getOverScrollMode(View view) {
        return IMPL.getOverScrollMode(view);
    }

    public static int getPaddingEnd(View view) {
        return IMPL.getPaddingEnd(view);
    }

    public static int getPaddingStart(View view) {
        return IMPL.getPaddingStart(view);
    }

    public static ViewParent getParentForAccessibility(View view) {
        return IMPL.getParentForAccessibility(view);
    }

    public static float getPivotX(View view) {
        return IMPL.getPivotX(view);
    }

    public static float getPivotY(View view) {
        return IMPL.getPivotY(view);
    }

    public static float getRotation(View view) {
        return IMPL.getRotation(view);
    }

    public static float getRotationX(View view) {
        return IMPL.getRotationX(view);
    }

    public static float getRotationY(View view) {
        return IMPL.getRotationY(view);
    }

    public static float getScaleX(View view) {
        return IMPL.getScaleX(view);
    }

    public static float getScaleY(View view) {
        return IMPL.getScaleY(view);
    }

    public static String getTransitionName(View view) {
        return IMPL.getTransitionName(view);
    }

    public static float getTranslationX(View view) {
        return IMPL.getTranslationX(view);
    }

    public static float getTranslationY(View view) {
        return IMPL.getTranslationY(view);
    }

    public static float getTranslationZ(View view) {
        return IMPL.getTranslationZ(view);
    }

    public static int getWindowSystemUiVisibility(View view) {
        return IMPL.getWindowSystemUiVisibility(view);
    }

    public static float getX(View view) {
        return IMPL.getX(view);
    }

    public static float getY(View view) {
        return IMPL.getY(view);
    }

    public static boolean hasAccessibilityDelegate(View view) {
        return IMPL.hasAccessibilityDelegate(view);
    }

    public static boolean hasTransientState(View view) {
        return IMPL.hasTransientState(view);
    }

    public static boolean isOpaque(View view) {
        return IMPL.isOpaque(view);
    }

    public static void jumpDrawablesToCurrentState(View view) {
        IMPL.jumpDrawablesToCurrentState(view);
    }

    public static void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        IMPL.onInitializeAccessibilityEvent(view, accessibilityEvent);
    }

    public static void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        IMPL.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
    }

    public static void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        IMPL.onPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public static boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        return IMPL.performAccessibilityAction(view, i, bundle);
    }

    public static void postInvalidateOnAnimation(View view) {
        IMPL.postInvalidateOnAnimation(view);
    }

    public static void postInvalidateOnAnimation(View view, int i, int i2, int i3, int i4) {
        IMPL.postInvalidateOnAnimation(view, i, i2, i3, i4);
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        IMPL.postOnAnimation(view, runnable);
    }

    public static void postOnAnimationDelayed(View view, Runnable runnable, long j) {
        IMPL.postOnAnimationDelayed(view, runnable, j);
    }

    public static void requestApplyInsets(View view) {
        IMPL.requestApplyInsets(view);
    }

    public static int resolveSizeAndState(int i, int i2, int i3) {
        return IMPL.resolveSizeAndState(i, i2, i3);
    }

    public static void setAccessibilityDelegate(View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
        IMPL.setAccessibilityDelegate(view, accessibilityDelegateCompat);
    }

    public static void setAccessibilityLiveRegion(View view, int i) {
        IMPL.setAccessibilityLiveRegion(view, i);
    }

    public static void setActivated(View view, boolean z) {
        IMPL.setActivated(view, z);
    }

    public static void setAlpha(View view, float f) {
        IMPL.setAlpha(view, f);
    }

    public static void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean z) {
        IMPL.setChildrenDrawingOrderEnabled(viewGroup, z);
    }

    public static void setElevation(View view, float f) {
        IMPL.setElevation(view, f);
    }

    public static void setFitsSystemWindows(View view, boolean z) {
        IMPL.setFitsSystemWindows(view, z);
    }

    public static void setHasTransientState(View view, boolean z) {
        IMPL.setHasTransientState(view, z);
    }

    public static void setImportantForAccessibility(View view, int i) {
        IMPL.setImportantForAccessibility(view, i);
    }

    public static void setLabelFor(View view, int i) {
        IMPL.setLabelFor(view, i);
    }

    public static void setLayerPaint(View view, Paint paint) {
        IMPL.setLayerPaint(view, paint);
    }

    public static void setLayerType(View view, int i, Paint paint) {
        IMPL.setLayerType(view, i, paint);
    }

    public static void setLayoutDirection(View view, int i) {
        IMPL.setLayoutDirection(view, i);
    }

    public static void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        IMPL.setOnApplyWindowInsetsListener(view, onApplyWindowInsetsListener);
    }

    public static void setOverScrollMode(View view, int i) {
        IMPL.setOverScrollMode(view, i);
    }

    public static void setPaddingRelative(View view, int i, int i2, int i3, int i4) {
        IMPL.setPaddingRelative(view, i, i2, i3, i4);
    }

    public static void setPivotX(View view, float f) {
        IMPL.setPivotX(view, f);
    }

    public static void setPivotY(View view, float f) {
        IMPL.setPivotX(view, f);
    }

    public static void setRotation(View view, float f) {
        IMPL.setRotation(view, f);
    }

    public static void setRotationX(View view, float f) {
        IMPL.setRotationX(view, f);
    }

    public static void setRotationY(View view, float f) {
        IMPL.setRotationY(view, f);
    }

    public static void setSaveFromParentEnabled(View view, boolean z) {
        IMPL.setSaveFromParentEnabled(view, z);
    }

    public static void setScaleX(View view, float f) {
        IMPL.setScaleX(view, f);
    }

    public static void setScaleY(View view, float f) {
        IMPL.setScaleY(view, f);
    }

    public static void setTransitionName(View view, String str) {
        IMPL.setTransitionName(view, str);
    }

    public static void setTranslationX(View view, float f) {
        IMPL.setTranslationX(view, f);
    }

    public static void setTranslationY(View view, float f) {
        IMPL.setTranslationY(view, f);
    }

    public static void setTranslationZ(View view, float f) {
        IMPL.setTranslationZ(view, f);
    }

    public static void setX(View view, float f) {
        IMPL.setX(view, f);
    }

    public static void setY(View view, float f) {
        IMPL.setY(view, f);
    }
}
