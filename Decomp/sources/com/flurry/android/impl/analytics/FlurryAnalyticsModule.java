package com.flurry.android.impl.analytics;

import android.content.Context;
import com.flurry.sdk.dd;
import com.flurry.sdk.dj;
import com.flurry.sdk.et;

public class FlurryAnalyticsModule implements et {
    private static FlurryAnalyticsModule a;
    private dd b;

    private FlurryAnalyticsModule() {
    }

    public static synchronized FlurryAnalyticsModule getInstance() {
        FlurryAnalyticsModule flurryAnalyticsModule;
        synchronized (FlurryAnalyticsModule.class) {
            if (a == null) {
                a = new FlurryAnalyticsModule();
            }
            flurryAnalyticsModule = a;
        }
        return flurryAnalyticsModule;
    }

    public dd a() {
        return this.b;
    }

    public void a(dj djVar) {
    }

    public void a(dj djVar, Context context) {
        if (this.b == null) {
            this.b = new dd();
        }
    }

    public void b(dj djVar, Context context) {
    }

    public void c(dj djVar, Context context) {
    }
}
