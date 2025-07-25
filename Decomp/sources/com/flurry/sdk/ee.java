package com.flurry.sdk;

import java.util.Comparator;

public class ee implements Comparator<Runnable> {
    private static final String a = ee.class.getSimpleName();

    private int a(Runnable runnable) {
        if (runnable == null) {
            return Integer.MAX_VALUE;
        }
        if (runnable instanceof ef) {
            fg fgVar = (fg) ((ef) runnable).a();
            return fgVar != null ? fgVar.i() : Integer.MAX_VALUE;
        } else if (runnable instanceof fg) {
            return ((fg) runnable).i();
        } else {
            eo.a(6, a, "Unknown runnable class: " + runnable.getClass().getName());
            return Integer.MAX_VALUE;
        }
    }

    /* renamed from: a */
    public int compare(Runnable runnable, Runnable runnable2) {
        int a2 = a(runnable);
        int a3 = a(runnable2);
        if (a2 < a3) {
            return -1;
        }
        return a2 > a3 ? 1 : 0;
    }
}
