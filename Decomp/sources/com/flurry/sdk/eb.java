package com.flurry.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.facebook.internal.AnalyticsEvents;
import jp.stargarage.g2metrics.BuildConfig;

public class eb {
    private static final String a = eb.class.getSimpleName();
    private static String b;
    private static String c;

    public static synchronized String a() {
        String str;
        synchronized (eb.class) {
            if (!TextUtils.isEmpty(b)) {
                str = b;
            } else if (!TextUtils.isEmpty(c)) {
                str = c;
            } else {
                c = b();
                str = c;
            }
        }
        return str;
    }

    public static void a(String str) {
        b = str;
    }

    private static String b() {
        try {
            Context b2 = Cdo.a().b();
            PackageInfo packageInfo = b2.getPackageManager().getPackageInfo(b2.getPackageName(), 0);
            if (packageInfo.versionName != null) {
                return packageInfo.versionName;
            }
            if (packageInfo.versionCode != 0) {
                return Integer.toString(packageInfo.versionCode);
            }
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        } catch (Throwable th) {
            eo.a(6, a, BuildConfig.FLAVOR, th);
        }
    }
}
