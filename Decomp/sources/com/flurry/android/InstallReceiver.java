package com.flurry.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.flurry.sdk.cy;
import com.flurry.sdk.eo;
import com.flurry.sdk.fe;

public final class InstallReceiver extends BroadcastReceiver {
    static final String a = InstallReceiver.class.getSimpleName();

    public void onReceive(Context context, Intent intent) {
        eo.a(4, a, "Received an Install nofication of " + intent.getAction());
        String string = intent.getExtras().getString("referrer");
        eo.a(4, a, "Received an Install referrer of " + string);
        if (string == null || !"com.android.vending.INSTALL_REFERRER".equals(intent.getAction())) {
            eo.a(5, a, "referrer is null");
            return;
        }
        if (!string.contains("=")) {
            eo.a(4, a, "referrer is before decoding: " + string);
            string = fe.c(string);
            eo.a(4, a, "referrer is: " + string);
        }
        new cy(context).a(string);
    }
}
