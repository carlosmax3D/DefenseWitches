package com.google.android.gms.ads;

import android.content.Context;
import com.google.android.gms.internal.cs;
import com.google.android.gms.internal.x;
import twitter4j.HttpResponseCode;

public final class AdSize {
    public static final int AUTO_HEIGHT = -2;
    public static final AdSize BANNER = new AdSize(320, 50, "320x50_mb");
    public static final AdSize FULL_BANNER = new AdSize(468, 60, "468x60_as");
    public static final int FULL_WIDTH = -1;
    public static final AdSize LEADERBOARD = new AdSize(728, 90, "728x90_as");
    public static final AdSize MEDIUM_RECTANGLE = new AdSize(HttpResponseCode.MULTIPLE_CHOICES, 250, "300x250_as");
    public static final AdSize SMART_BANNER = new AdSize(-1, -2, "smart_banner");
    public static final AdSize WIDE_SKYSCRAPER = new AdSize(160, 600, "160x600_as");
    private final String dY;
    private final int v;
    private final int w;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public AdSize(int i, int i2) {
        this(i, i2, (i == -1 ? "FULL" : String.valueOf(i)) + "x" + (i2 == -2 ? "AUTO" : String.valueOf(i2)) + "_as");
    }

    AdSize(int i, int i2, String str) {
        if (i < 0 && i != -1) {
            throw new IllegalArgumentException("Invalid width for AdSize: " + i);
        } else if (i2 >= 0 || i2 == -2) {
            this.w = i;
            this.v = i2;
            this.dY = str;
        } else {
            throw new IllegalArgumentException("Invalid height for AdSize: " + i2);
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AdSize)) {
            return false;
        }
        AdSize adSize = (AdSize) obj;
        return this.w == adSize.w && this.v == adSize.v && this.dY.equals(adSize.dY);
    }

    public int getHeight() {
        return this.v;
    }

    public int getHeightInPixels(Context context) {
        return this.v == -2 ? x.b(context.getResources().getDisplayMetrics()) : cs.a(context, this.v);
    }

    public int getWidth() {
        return this.w;
    }

    public int getWidthInPixels(Context context) {
        return this.w == -1 ? x.a(context.getResources().getDisplayMetrics()) : cs.a(context, this.w);
    }

    public int hashCode() {
        return this.dY.hashCode();
    }

    public boolean isAutoHeight() {
        return this.v == -2;
    }

    public boolean isFullWidth() {
        return this.w == -1;
    }

    public String toString() {
        return this.dY;
    }
}
