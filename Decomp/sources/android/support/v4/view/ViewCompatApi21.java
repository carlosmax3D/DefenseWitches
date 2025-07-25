package android.support.v4.view;

import android.view.View;
import android.view.WindowInsets;

class ViewCompatApi21 {
    ViewCompatApi21() {
    }

    public static float getElevation(View view) {
        return view.getElevation();
    }

    public static String getTransitionName(View view) {
        return view.getTransitionName();
    }

    public static float getTranslationZ(View view) {
        return view.getTranslationZ();
    }

    public static boolean isImportantForAccessibility(View view) {
        return view.isImportantForAccessibility();
    }

    public static void requestApplyInsets(View view) {
        view.requestApplyInsets();
    }

    public static void setElevation(View view, float f) {
        view.setElevation(f);
    }

    public static void setOnApplyWindowInsetsListener(View view, final OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return ((WindowInsetsCompatApi21) onApplyWindowInsetsListener.onApplyWindowInsets(view, new WindowInsetsCompatApi21(windowInsets))).unwrap();
            }
        });
    }

    public static void setTransitionName(View view, String str) {
        view.setTransitionName(str);
    }

    public static void setTranslationZ(View view, float f) {
        view.setTranslationZ(f);
    }
}
