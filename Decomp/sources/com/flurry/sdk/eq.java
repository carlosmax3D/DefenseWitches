package com.flurry.sdk;

import android.os.Build;
import android.text.TextUtils;

public final class eq extends es {
    private final int a;

    public eq(String str, int i) {
        super(a(str, i));
        this.a = i;
    }

    private static et a(String str, int i) {
        if (b(str, i)) {
            return er.a(str);
        }
        return null;
    }

    private static boolean b(String str, int i) {
        return !TextUtils.isEmpty(str) && Build.VERSION.SDK_INT >= i;
    }
}
