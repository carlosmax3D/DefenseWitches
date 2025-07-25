package com.flurry.sdk;

import android.text.TextUtils;

public final class er {
    private static final String a = er.class.getSimpleName();

    public static et a(String str) {
        et etVar;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            etVar = (et) Class.forName(str).getDeclaredMethod("getInstance", new Class[0]).invoke((Object) null, new Object[0]);
        } catch (Exception e) {
            eo.a(5, a, "FlurryModule " + str + " is not available:", e);
            etVar = null;
        }
        return etVar;
    }
}
