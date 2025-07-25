package com.google.android.gms.internal;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.facebook.share.internal.ShareConstants;

public class dz {
    private static final Uri pK = Uri.parse("http://plus.google.com/");
    private static final Uri pL = pK.buildUpon().appendPath("circles").appendPath("find").build();

    public static Intent Q(String str) {
        Uri fromParts = Uri.fromParts("package", str, (String) null);
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(fromParts);
        return intent;
    }

    private static Uri R(String str) {
        return Uri.parse("market://details").buildUpon().appendQueryParameter(ShareConstants.WEB_DIALOG_PARAM_ID, str).build();
    }

    public static Intent S(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(R(str));
        intent.setPackage("com.android.vending");
        intent.addFlags(AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END);
        return intent;
    }

    public static Intent T(String str) {
        Uri parse = Uri.parse("bazaar://search?q=pname:" + str);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(parse);
        intent.setFlags(AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END);
        return intent;
    }

    public static Intent bX() {
        return new Intent("android.settings.DATE_SETTINGS");
    }
}
