package com.ansca.corona.graphics;

public class TypefaceSettings implements Cloneable {
    private boolean fIsBold = false;
    private boolean fIsItalic = false;
    private String fName = null;

    public TypefaceSettings clone() {
        try {
            return (TypefaceSettings) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean equals(TypefaceSettings typefaceSettings) {
        if (typefaceSettings == this) {
            return true;
        }
        if (typefaceSettings == null) {
            return false;
        }
        if ((this.fName == null && typefaceSettings.fName != null) || (this.fName != null && typefaceSettings.fName == null)) {
            return false;
        }
        if (this.fName == null || this.fName.equals(typefaceSettings.fName)) {
            return this.fIsBold == typefaceSettings.fIsBold && this.fIsItalic == typefaceSettings.fIsItalic;
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TypefaceSettings)) {
            return false;
        }
        return equals((TypefaceSettings) obj);
    }

    public int getAndroidTypefaceStyle() {
        int i = 0;
        if (this.fIsBold) {
            i = 0 | 1;
        }
        return this.fIsItalic ? i | 2 : i;
    }

    public String getName() {
        return this.fName;
    }

    public int hashCode() {
        int androidTypefaceStyle = getAndroidTypefaceStyle();
        return this.fName != null ? androidTypefaceStyle ^ this.fName.hashCode() : androidTypefaceStyle;
    }

    public boolean isBold() {
        return this.fIsBold;
    }

    public boolean isItalic() {
        return this.fIsItalic;
    }

    public void setIsBold(boolean z) {
        this.fIsBold = z;
    }

    public void setIsItalic(boolean z) {
        this.fIsItalic = z;
    }

    public void setName(String str) {
        this.fName = str;
    }
}
