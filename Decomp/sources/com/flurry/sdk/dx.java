package com.flurry.sdk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import jp.stargarage.g2metrics.BuildConfig;

public final class dx {
    private static final String a = dx.class.getSimpleName();

    public static PackageInfo a(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 20815);
        } catch (PackageManager.NameNotFoundException e) {
            eo.a(a, "Cannot find package info for package: " + context.getPackageName());
            return null;
        }
    }

    public static ApplicationInfo b(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
        } catch (PackageManager.NameNotFoundException e) {
            eo.a(a, "Cannot find application info for package: " + context.getPackageName());
            return null;
        }
    }

    public static String c(Context context) {
        PackageInfo a2 = a(context);
        return (a2 == null || a2.packageName == null) ? BuildConfig.FLAVOR : a2.packageName;
    }

    public static Bundle d(Context context) {
        ApplicationInfo b = b(context);
        return (b == null || b.metaData == null) ? Bundle.EMPTY : b.metaData;
    }
}
