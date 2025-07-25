package com.flurry.sdk;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ep implements et {
    private static ep a;
    private final List<et> b = b();

    private ep() {
    }

    public static synchronized ep a() {
        ep epVar;
        synchronized (ep.class) {
            if (a == null) {
                a = new ep();
            }
            epVar = a;
        }
        return epVar;
    }

    private static List<et> b() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new eq("com.flurry.android.impl.analytics.FlurryAnalyticsModule", 10));
        arrayList.add(new eq("com.flurry.android.impl.ads.FlurryAdModule", 10));
        return Collections.unmodifiableList(arrayList);
    }

    public void a(dj djVar) {
        for (et a2 : this.b) {
            a2.a(djVar);
        }
    }

    public void a(dj djVar, Context context) {
        for (et a2 : this.b) {
            a2.a(djVar, context);
        }
    }

    public void b(dj djVar, Context context) {
        for (et b2 : this.b) {
            b2.b(djVar, context);
        }
    }

    public void c(dj djVar, Context context) {
        for (et c : this.b) {
            c.c(djVar, context);
        }
    }
}
