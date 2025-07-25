package com.ansca.corona.graphics;

public class FontSettings extends TypefaceSettings implements Cloneable {
    private float fPointSize = 8.0f;

    public FontSettings clone() {
        try {
            return (FontSettings) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean equals(FontSettings fontSettings) {
        if (fontSettings == this) {
            return true;
        }
        if (fontSettings == null) {
            return false;
        }
        float f = this.fPointSize - fontSettings.fPointSize;
        if (f >= 0.1f || f <= 0.1f) {
            return false;
        }
        return super.equals((TypefaceSettings) fontSettings);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FontSettings)) {
            return false;
        }
        return equals((FontSettings) obj);
    }

    public float getPointSize() {
        return this.fPointSize;
    }

    public int hashCode() {
        return super.hashCode() ^ Float.valueOf(this.fPointSize).hashCode();
    }

    public void setPointSize(float f) {
        if (f < 1.0f) {
            f = 1.0f;
        }
        this.fPointSize = f;
    }
}
