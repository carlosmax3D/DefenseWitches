package android.support.v4.view;

import android.os.Build;
import android.view.ViewGroup;

public class MarginLayoutParamsCompat {
    static final MarginLayoutParamsCompatImpl IMPL;

    interface MarginLayoutParamsCompatImpl {
        int getLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams);

        int getMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams);

        int getMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams);

        boolean isMarginRelative(ViewGroup.MarginLayoutParams marginLayoutParams);

        void resolveLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, int i);

        void setLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, int i);

        void setMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams, int i);

        void setMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams, int i);
    }

    static class MarginLayoutParamsCompatImplBase implements MarginLayoutParamsCompatImpl {
        MarginLayoutParamsCompatImplBase() {
        }

        public int getLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams) {
            return 0;
        }

        public int getMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams) {
            return marginLayoutParams.rightMargin;
        }

        public int getMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams) {
            return marginLayoutParams.leftMargin;
        }

        public boolean isMarginRelative(ViewGroup.MarginLayoutParams marginLayoutParams) {
            return false;
        }

        public void resolveLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
        }

        public void setLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
        }

        public void setMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
            marginLayoutParams.rightMargin = i;
        }

        public void setMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
            marginLayoutParams.leftMargin = i;
        }
    }

    static class MarginLayoutParamsCompatImplJbMr1 implements MarginLayoutParamsCompatImpl {
        MarginLayoutParamsCompatImplJbMr1() {
        }

        public int getLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams) {
            return MarginLayoutParamsCompatJellybeanMr1.getLayoutDirection(marginLayoutParams);
        }

        public int getMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams) {
            return MarginLayoutParamsCompatJellybeanMr1.getMarginEnd(marginLayoutParams);
        }

        public int getMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams) {
            return MarginLayoutParamsCompatJellybeanMr1.getMarginStart(marginLayoutParams);
        }

        public boolean isMarginRelative(ViewGroup.MarginLayoutParams marginLayoutParams) {
            return MarginLayoutParamsCompatJellybeanMr1.isMarginRelative(marginLayoutParams);
        }

        public void resolveLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
            MarginLayoutParamsCompatJellybeanMr1.resolveLayoutDirection(marginLayoutParams, i);
        }

        public void setLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
            MarginLayoutParamsCompatJellybeanMr1.setLayoutDirection(marginLayoutParams, i);
        }

        public void setMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
            MarginLayoutParamsCompatJellybeanMr1.setMarginEnd(marginLayoutParams, i);
        }

        public void setMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
            MarginLayoutParamsCompatJellybeanMr1.setMarginStart(marginLayoutParams, i);
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 17) {
            IMPL = new MarginLayoutParamsCompatImplJbMr1();
        } else {
            IMPL = new MarginLayoutParamsCompatImplBase();
        }
    }

    public static int getLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return IMPL.getLayoutDirection(marginLayoutParams);
    }

    public static int getMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return IMPL.getMarginEnd(marginLayoutParams);
    }

    public static int getMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return IMPL.getMarginStart(marginLayoutParams);
    }

    public static boolean isMarginRelative(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return IMPL.isMarginRelative(marginLayoutParams);
    }

    public static void resolveLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
        IMPL.resolveLayoutDirection(marginLayoutParams, i);
    }

    public static void setLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
        IMPL.setLayoutDirection(marginLayoutParams, i);
    }

    public static void setMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
        IMPL.setMarginEnd(marginLayoutParams, i);
    }

    public static void setMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
        IMPL.setMarginStart(marginLayoutParams, i);
    }
}
