package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.a;
import com.google.android.gms.internal.eg;
import com.google.android.gms.internal.p;
import java.io.IOException;

public final class AdvertisingIdClient {

    public static final class Info {
        private final String eb;
        private final boolean ec;

        Info(String str, boolean z) {
            this.eb = str;
            this.ec = z;
        }

        public String getId() {
            return this.eb;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.ec;
        }
    }

    private static a g(Context context) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            try {
                GooglePlayServicesUtil.m(context);
                a aVar = new a();
                Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
                if (context.bindService(intent, aVar, 1)) {
                    return aVar;
                }
                throw new IOException("Connection failure");
            } catch (GooglePlayServicesNotAvailableException e) {
                throw new IOException(e);
            }
        } catch (PackageManager.NameNotFoundException e2) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
    }

    public static Info getAdvertisingIdInfo(Context context) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        eg.O("Calling this from your main thread can lead to deadlock");
        a g = g(context);
        try {
            p b = p.a.b(g.bg());
            Info info = new Info(b.getId(), b.a(true));
            context.unbindService(g);
            return info;
        } catch (RemoteException e) {
            Log.i("AdvertisingIdClient", "GMS remote exception ", e);
            throw new IOException("Remote exception");
        } catch (InterruptedException e2) {
            throw new IOException("Interrupted exception");
        } catch (Throwable th) {
            context.unbindService(g);
            throw th;
        }
    }
}
