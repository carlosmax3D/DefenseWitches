package com.google.android.gms.internal;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

public final class bh {
    public static boolean a(Context context, bj bjVar, bq bqVar) {
        if (bjVar == null) {
            ct.v("No intent data for launcher overlay.");
            return false;
        }
        Intent intent = new Intent();
        if (TextUtils.isEmpty(bjVar.go)) {
            ct.v("Open GMSG did not contain a URL.");
            return false;
        }
        if (!TextUtils.isEmpty(bjVar.mimeType)) {
            intent.setDataAndType(Uri.parse(bjVar.go), bjVar.mimeType);
        } else {
            intent.setData(Uri.parse(bjVar.go));
        }
        intent.setAction("android.intent.action.VIEW");
        if (!TextUtils.isEmpty(bjVar.packageName)) {
            intent.setPackage(bjVar.packageName);
        }
        if (!TextUtils.isEmpty(bjVar.gp)) {
            String[] split = bjVar.gp.split("/", 2);
            if (split.length < 2) {
                ct.v("Could not parse component name from open GMSG: " + bjVar.gp);
                return false;
            }
            intent.setClassName(split[0], split[1]);
        }
        try {
            ct.u("Launching an intent: " + intent);
            context.startActivity(intent);
            bqVar.z();
            return true;
        } catch (ActivityNotFoundException e) {
            ct.v(e.getMessage());
            return false;
        }
    }
}
