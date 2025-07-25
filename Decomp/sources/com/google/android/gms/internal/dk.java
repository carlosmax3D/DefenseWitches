package com.google.android.gms.internal;

import android.util.Log;

public class dk {
    private static boolean mb = false;
    private String mTag;
    private boolean mc;
    private boolean md;

    public dk(String str) {
        this(str, bc());
    }

    public dk(String str, boolean z) {
        this.mTag = str;
        this.mc = z;
    }

    public static boolean bc() {
        return mb;
    }

    public void a(String str, Object... objArr) {
        if (this.md) {
            Log.v(this.mTag, String.format(str, objArr));
        }
    }

    public void b(String str, Object... objArr) {
        if (this.mc || mb) {
            Log.d(this.mTag, String.format(str, objArr));
        }
    }

    public void c(String str, Object... objArr) {
        Log.i(this.mTag, String.format(str, objArr));
    }

    public void d(String str, Object... objArr) {
        Log.w(this.mTag, String.format(str, objArr));
    }
}
