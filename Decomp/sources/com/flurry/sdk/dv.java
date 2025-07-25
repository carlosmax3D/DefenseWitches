package com.flurry.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import com.flurry.sdk.du;
import java.util.List;

public class dv {
    private static dv b;
    private final dt<du> a = new dt<>();

    @TargetApi(14)
    private dv() {
        if (Build.VERSION.SDK_INT >= 14) {
            ((Application) Cdo.a().b()).registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                /* access modifiers changed from: protected */
                public void a(Activity activity, du.a aVar) {
                    for (du a2 : dv.this.c()) {
                        a2.a(activity, aVar);
                    }
                }

                public void onActivityCreated(Activity activity, Bundle bundle) {
                    a(activity, du.a.kCreated);
                }

                public void onActivityDestroyed(Activity activity) {
                    a(activity, du.a.kDestroyed);
                }

                public void onActivityPaused(Activity activity) {
                    a(activity, du.a.kPaused);
                }

                public void onActivityResumed(Activity activity) {
                    a(activity, du.a.kResumed);
                }

                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                    a(activity, du.a.kSaveState);
                }

                public void onActivityStarted(Activity activity) {
                    a(activity, du.a.kStarted);
                }

                public void onActivityStopped(Activity activity) {
                    a(activity, du.a.kStopped);
                }
            });
        }
    }

    public static synchronized dv a() {
        dv dvVar;
        synchronized (dv.class) {
            if (b == null) {
                b = new dv();
            }
            dvVar = b;
        }
        return dvVar;
    }

    /* access modifiers changed from: private */
    public synchronized List<du> c() {
        return this.a.a();
    }

    public synchronized void a(du duVar) {
        this.a.a(duVar);
    }

    public boolean b() {
        return Build.VERSION.SDK_INT >= 14;
    }
}
