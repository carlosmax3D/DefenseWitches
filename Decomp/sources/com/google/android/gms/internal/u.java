package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.c;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.ad;

public final class u extends e<ad> {
    private static final u ew = new u();

    private u() {
        super("com.google.android.gms.ads.AdManagerCreatorImpl");
    }

    public static ac a(Context context, x xVar, String str, ba baVar) {
        ac b;
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0 && (b = ew.b(context, xVar, str, baVar)) != null) {
            return b;
        }
        ct.r("Using AdManager from the client jar.");
        return new r(context, xVar, str, baVar, new cu(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, true));
    }

    private ac b(Context context, x xVar, String str, ba baVar) {
        try {
            return ac.a.f(((ad) t(context)).a(c.h(context), xVar, str, baVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE));
        } catch (RemoteException e) {
            ct.b("Could not create remote AdManager.", e);
            return null;
        } catch (e.a e2) {
            ct.b("Could not create remote AdManager.", e2);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public ad d(IBinder iBinder) {
        return ad.a.g(iBinder);
    }
}
