package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.eg;

public abstract class e<T> {
    private final String sF;
    private T sG;

    public static class a extends Exception {
        public a(String str) {
            super(str);
        }

        public a(String str, Throwable th) {
            super(str, th);
        }
    }

    protected e(String str) {
        this.sF = str;
    }

    /* access modifiers changed from: protected */
    public abstract T d(IBinder iBinder);

    /* access modifiers changed from: protected */
    public final T t(Context context) throws a {
        if (this.sG == null) {
            eg.f(context);
            Context remoteContext = GooglePlayServicesUtil.getRemoteContext(context);
            if (remoteContext == null) {
                throw new a("Could not get remote context.");
            }
            try {
                this.sG = d((IBinder) remoteContext.getClassLoader().loadClass(this.sF).newInstance());
            } catch (ClassNotFoundException e) {
                throw new a("Could not load creator class.");
            } catch (InstantiationException e2) {
                throw new a("Could not instantiate creator.");
            } catch (IllegalAccessException e3) {
                throw new a("Could not access creator.");
            }
        }
        return this.sG;
    }
}
