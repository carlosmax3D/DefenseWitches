package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import com.google.android.gms.dynamic.c;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.internal.ed;

public final class eh extends e<ed> {
    private static final eh pP = new eh();

    private eh() {
        super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
    }

    public static View d(Context context, int i, int i2) throws e.a {
        return pP.e(context, i, i2);
    }

    private View e(Context context, int i, int i2) throws e.a {
        try {
            return (View) c.b(((ed) t(context)).a(c.h(context), i, i2));
        } catch (Exception e) {
            throw new e.a("Could not get button with size " + i + " and color " + i2, e);
        }
    }

    /* renamed from: A */
    public ed d(IBinder iBinder) {
        return ed.a.z(iBinder);
    }
}
