package com.google.android.gms.maps.internal;

import android.content.Context;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.c;
import com.google.android.gms.internal.eg;
import com.google.android.gms.maps.internal.c;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class q {
    private static Context Ci;
    private static c Cj;

    private static <T> T a(ClassLoader classLoader, String str) {
        try {
            return c(((ClassLoader) eg.f(classLoader)).loadClass(str));
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Unable to find dynamic class " + str);
        }
    }

    private static <T> T c(Class<?> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException("Unable to instantiate the dynamic class " + cls.getName());
        } catch (IllegalAccessException e2) {
            throw new IllegalStateException("Unable to call the default constructor of " + cls.getName());
        }
    }

    private static boolean eB() {
        return false;
    }

    private static Class<?> eC() {
        try {
            return Build.VERSION.SDK_INT < 15 ? Class.forName("com.google.android.gms.maps.internal.CreatorImplGmm6") : Class.forName("com.google.android.gms.maps.internal.CreatorImpl");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Context getRemoteContext(Context context) {
        if (Ci == null) {
            if (eB()) {
                Ci = context;
            } else {
                Ci = GooglePlayServicesUtil.getRemoteContext(context);
            }
        }
        return Ci;
    }

    public static c u(Context context) throws GooglePlayServicesNotAvailableException {
        eg.f(context);
        if (Cj != null) {
            return Cj;
        }
        v(context);
        Cj = w(context);
        try {
            Cj.a(c.h(getRemoteContext(context).getResources()), (int) GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE);
            return Cj;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    private static void v(Context context) throws GooglePlayServicesNotAvailableException {
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        switch (isGooglePlayServicesAvailable) {
            case 0:
                return;
            default:
                throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
        }
    }

    private static c w(Context context) {
        if (!eB()) {
            return c.a.Q((IBinder) a(getRemoteContext(context).getClassLoader(), "com.google.android.gms.maps.internal.CreatorImpl"));
        }
        Log.i(q.class.getSimpleName(), "Making Creator statically");
        return (c) c(eC());
    }
}
