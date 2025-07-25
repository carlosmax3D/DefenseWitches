package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.c;
import com.google.android.gms.internal.hp;
import com.google.android.gms.plus.PlusOneDummyView;

public final class ht {
    private static Context Ci;
    private static hp DQ;

    public static class a extends Exception {
        public a(String str) {
            super(str);
        }
    }

    public static View a(Context context, int i, int i2, String str, int i3) {
        if (str != null) {
            return (View) c.b(x(context).a(c.h(context), i, i2, str, i3));
        }
        try {
            throw new NullPointerException();
        } catch (Exception e) {
            return new PlusOneDummyView(context, i);
        }
    }

    public static View a(Context context, int i, int i2, String str, String str2) {
        if (str != null) {
            return (View) c.b(x(context).a(c.h(context), i, i2, str, str2));
        }
        try {
            throw new NullPointerException();
        } catch (Exception e) {
            return new PlusOneDummyView(context, i);
        }
    }

    private static hp x(Context context) throws a {
        eg.f(context);
        if (DQ == null) {
            if (Ci == null) {
                Ci = GooglePlayServicesUtil.getRemoteContext(context);
                if (Ci == null) {
                    throw new a("Could not get remote context.");
                }
            }
            try {
                DQ = hp.a.av((IBinder) Ci.getClassLoader().loadClass("com.google.android.gms.plus.plusone.PlusOneButtonCreatorImpl").newInstance());
            } catch (ClassNotFoundException e) {
                throw new a("Could not load creator class.");
            } catch (InstantiationException e2) {
                throw new a("Could not instantiate creator.");
            } catch (IllegalAccessException e3) {
                throw new a("Could not access creator.");
            }
        }
        return DQ;
    }
}
