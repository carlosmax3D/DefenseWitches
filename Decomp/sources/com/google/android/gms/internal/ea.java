package com.google.android.gms.internal;

import android.util.Log;

public final class ea {
    private final String pM;

    public ea(String str) {
        this.pM = (String) eg.f(str);
    }

    public boolean K(int i) {
        return Log.isLoggable(this.pM, i);
    }

    public void a(String str, String str2, Throwable th) {
        if (K(6)) {
            Log.e(str, str2, th);
        }
    }

    public void c(String str, String str2) {
        if (K(5)) {
            Log.w(str, str2);
        }
    }

    public void d(String str, String str2) {
        if (K(6)) {
            Log.e(str, str2);
        }
    }
}
