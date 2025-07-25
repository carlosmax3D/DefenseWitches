package com.tapjoy;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class TapjoyDisplayMetricsUtil {
    private Configuration configuration;
    private Context context;
    private DisplayMetrics metrics = new DisplayMetrics();

    public TapjoyDisplayMetricsUtil(Context context2) {
        this.context = context2;
        ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay().getMetrics(this.metrics);
        this.configuration = this.context.getResources().getConfiguration();
    }

    public int getScreenDensityDPI() {
        return this.metrics.densityDpi;
    }

    public float getScreenDensityScale() {
        return this.metrics.density;
    }

    public int getScreenLayoutSize() {
        return this.configuration.screenLayout & 15;
    }
}
