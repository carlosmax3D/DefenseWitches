package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

class DrawableCompatL {
    DrawableCompatL() {
    }

    public static void setHotspot(Drawable drawable, float f, float f2) {
        drawable.setHotspot(f, f2);
    }

    public static void setHotspotBounds(Drawable drawable, int i, int i2, int i3, int i4) {
        drawable.setHotspotBounds(i, i2, i3, i4);
    }

    public static void setTint(Drawable drawable, int i) {
        drawable.setTint(i);
    }

    public static void setTintList(Drawable drawable, ColorStateList colorStateList) {
        drawable.setTintList(colorStateList);
    }

    public static void setTintMode(Drawable drawable, PorterDuff.Mode mode) {
        drawable.setTintMode(mode);
    }
}
