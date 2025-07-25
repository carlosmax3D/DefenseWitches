package com.ansca.corona.graphics;

public class TypefaceInfo {
    private TypefaceSettings fSettings;

    public TypefaceInfo(TypefaceSettings typefaceSettings) {
        if (typefaceSettings == null) {
            throw new NullPointerException();
        }
        this.fSettings = typefaceSettings.clone();
    }

    public boolean equals(TypefaceInfo typefaceInfo) {
        if (typefaceInfo == null) {
            return false;
        }
        return equals(typefaceInfo.fSettings);
    }

    public boolean equals(TypefaceSettings typefaceSettings) {
        if (typefaceSettings == null) {
            return false;
        }
        return this.fSettings.equals(typefaceSettings);
    }

    public boolean equals(Object obj) {
        if (obj instanceof TypefaceInfo) {
            return equals((TypefaceInfo) obj);
        }
        if (obj instanceof TypefaceSettings) {
            return equals((TypefaceSettings) obj);
        }
        return false;
    }

    public int getAndroidTypefaceStyle() {
        return this.fSettings.getAndroidTypefaceStyle();
    }

    public String getName() {
        return this.fSettings.getName();
    }

    public int hashCode() {
        return this.fSettings.hashCode();
    }

    public boolean isBold() {
        return this.fSettings.isBold();
    }

    public boolean isItalic() {
        return this.fSettings.isItalic();
    }
}
