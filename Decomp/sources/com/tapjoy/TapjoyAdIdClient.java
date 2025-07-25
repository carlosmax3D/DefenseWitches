package com.tapjoy;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.lang.reflect.Method;

public class TapjoyAdIdClient {
    private static final String TAG = "TapjoyAdIdClient";
    private boolean adTrackingEnabled;
    private String advertisingID;
    private Context context;

    public TapjoyAdIdClient(Context context2) {
        this.context = context2;
    }

    public String getAdvertisingId() {
        return this.advertisingID;
    }

    public boolean isAdTrackingEnabled() {
        return this.adTrackingEnabled;
    }

    public boolean setupAdIdInfo() {
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.context);
            this.advertisingID = advertisingIdInfo.getId();
            this.adTrackingEnabled = !advertisingIdInfo.isLimitAdTrackingEnabled();
            return true;
        } catch (Exception e) {
            return false;
        } catch (Error e2) {
            return false;
        }
    }

    public boolean setupAdIdInfoReflection() {
        try {
            Class<?> cls = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient");
            Method method = cls.getMethod("getAdvertisingIdInfo", new Class[]{Context.class});
            TapjoyLog.i(TAG, "Found method: " + method);
            Object invoke = method.invoke(cls, new Object[]{this.context});
            Method method2 = invoke.getClass().getMethod("isLimitAdTrackingEnabled", new Class[0]);
            Method method3 = invoke.getClass().getMethod("getId", new Class[0]);
            this.adTrackingEnabled = !((Boolean) method2.invoke(invoke, new Object[0])).booleanValue();
            this.advertisingID = (String) method3.invoke(invoke, new Object[0]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
