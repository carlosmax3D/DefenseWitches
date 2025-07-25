package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.dynamic.c;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.internal.bs;
import com.google.android.gms.internal.bt;

public final class br extends e<bt> {
    private static final br ha = new br();

    private static final class a extends Exception {
        public a(String str) {
            super(str);
        }
    }

    private br() {
        super("com.google.android.gms.ads.AdOverlayCreatorImpl");
    }

    public static bs a(Activity activity) {
        try {
            if (!b(activity)) {
                return ha.c(activity);
            }
            ct.r("Using AdOverlay from the client jar.");
            return new bk(activity);
        } catch (a e) {
            ct.v(e.getMessage());
            return null;
        }
    }

    private static boolean b(Activity activity) throws a {
        Intent intent = activity.getIntent();
        if (intent.hasExtra("com.google.android.gms.ads.internal.overlay.useClientJar")) {
            return intent.getBooleanExtra("com.google.android.gms.ads.internal.overlay.useClientJar", false);
        }
        throw new a("Ad overlay requires the useClientJar flag in intent extras.");
    }

    private bs c(Activity activity) {
        try {
            return bs.a.m(((bt) t(activity)).a(c.h(activity)));
        } catch (RemoteException e) {
            ct.b("Could not create remote AdOverlay.", e);
            return null;
        } catch (e.a e2) {
            ct.b("Could not create remote AdOverlay.", e2);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: l */
    public bt d(IBinder iBinder) {
        return bt.a.n(iBinder);
    }
}
