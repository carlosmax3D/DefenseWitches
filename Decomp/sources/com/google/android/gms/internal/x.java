package com.google.android.gms.internal;

import android.content.Context;
import android.os.Parcel;
import android.util.DisplayMetrics;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.a;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class x implements SafeParcelable {
    public static final y CREATOR = new y();
    public final String eF;
    public final boolean eG;
    public final x[] eH;
    public final int height;
    public final int heightPixels;
    public final int versionCode;
    public final int width;
    public final int widthPixels;

    public x() {
        this(2, "interstitial_mb", 0, 0, true, 0, 0, (x[]) null);
    }

    x(int i, String str, int i2, int i3, boolean z, int i4, int i5, x[] xVarArr) {
        this.versionCode = i;
        this.eF = str;
        this.height = i2;
        this.heightPixels = i3;
        this.eG = z;
        this.width = i4;
        this.widthPixels = i5;
        this.eH = xVarArr;
    }

    public x(Context context, AdSize adSize) {
        this(context, new AdSize[]{adSize});
    }

    public x(Context context, AdSize[] adSizeArr) {
        int i;
        AdSize adSize = adSizeArr[0];
        this.versionCode = 2;
        this.eG = false;
        this.width = adSize.getWidth();
        this.height = adSize.getHeight();
        boolean z = this.width == -1;
        boolean z2 = this.height == -2;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (z) {
            this.widthPixels = a(displayMetrics);
            i = (int) (((float) this.widthPixels) / displayMetrics.density);
        } else {
            int i2 = this.width;
            this.widthPixels = cs.a(displayMetrics, this.width);
            i = i2;
        }
        int c = z2 ? c(displayMetrics) : this.height;
        this.heightPixels = cs.a(displayMetrics, c);
        if (z || z2) {
            this.eF = i + "x" + c + "_as";
        } else {
            this.eF = adSize.toString();
        }
        if (adSizeArr.length > 1) {
            this.eH = new x[adSizeArr.length];
            for (int i3 = 0; i3 < adSizeArr.length; i3++) {
                this.eH[i3] = new x(context, adSizeArr[i3]);
            }
            return;
        }
        this.eH = null;
    }

    public x(x xVar, x[] xVarArr) {
        this(2, xVar.eF, xVar.height, xVar.heightPixels, xVar.eG, xVar.width, xVar.widthPixels, xVarArr);
    }

    public static int a(DisplayMetrics displayMetrics) {
        return displayMetrics.widthPixels;
    }

    public static int b(DisplayMetrics displayMetrics) {
        return (int) (((float) c(displayMetrics)) * displayMetrics.density);
    }

    private static int c(DisplayMetrics displayMetrics) {
        int i = (int) (((float) displayMetrics.heightPixels) / displayMetrics.density);
        if (i <= 400) {
            return 32;
        }
        return i <= 720 ? 50 : 90;
    }

    public AdSize P() {
        return a.a(this.width, this.height, this.eF);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        y.a(this, parcel, i);
    }
}
