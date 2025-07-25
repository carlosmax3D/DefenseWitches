package com.flurry.sdk;

import android.os.Looper;
import android.telephony.TelephonyManager;
import java.util.Arrays;

public class dy {
    private static final String a = dy.class.getSimpleName();
    private static byte[] b;

    public static synchronized byte[] a() {
        byte[] bArr;
        synchronized (dy.class) {
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                throw new IllegalStateException("Must be called from a background thread!");
            } else if (b != null) {
                bArr = b;
            } else if (Cdo.a().b().checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
                bArr = null;
            } else {
                b();
                bArr = b;
            }
        }
        return bArr;
    }

    private static void b() {
        String deviceId;
        TelephonyManager telephonyManager = (TelephonyManager) Cdo.a().b().getSystemService("phone");
        if (telephonyManager != null && (deviceId = telephonyManager.getDeviceId()) != null && deviceId.trim().length() > 0) {
            try {
                byte[] d = fe.d(deviceId);
                if (d == null || d.length != 20) {
                    eo.a(6, a, "sha1 is not " + 20 + " bytes long: " + Arrays.toString(d));
                } else {
                    b = d;
                }
            } catch (Exception e) {
                eo.a(6, a, "Exception in generateHashedImei()");
            }
        }
    }
}
