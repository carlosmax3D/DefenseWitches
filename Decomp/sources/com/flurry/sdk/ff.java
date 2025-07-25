package com.flurry.sdk;

import java.io.PrintStream;
import java.io.PrintWriter;
import jp.stargarage.g2metrics.BuildConfig;

public abstract class ff implements Runnable {
    private static final String a = ff.class.getSimpleName();
    PrintStream g;
    PrintWriter h;

    public abstract void a();

    public final void run() {
        try {
            a();
        } catch (Throwable th) {
            if (this.g != null) {
                th.printStackTrace(this.g);
            } else if (this.h != null) {
                th.printStackTrace(this.h);
            } else {
                th.printStackTrace();
            }
            eo.a(6, a, BuildConfig.FLAVOR, th);
        }
    }
}
