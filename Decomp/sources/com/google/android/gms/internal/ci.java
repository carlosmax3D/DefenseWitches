package com.google.android.gms.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import java.util.Locale;

public final class ci {
    public final int hZ;
    public final boolean ia;
    public final boolean ib;
    public final String ic;
    public final String id;
    public final boolean ie;

    /* renamed from: if  reason: not valid java name */
    public final boolean f1if;
    public final boolean ig;
    public final String ih;
    public final String ii;
    public final int ij;
    public final int ik;
    public final int il;
    public final int im;
    public final int in;
    public final int io;
    public final float ip;
    public final int iq;
    public final int ir;

    public ci(Context context) {
        boolean z = true;
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Locale locale = Locale.getDefault();
        PackageManager packageManager = context.getPackageManager();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.hZ = audioManager.getMode();
        this.ia = a(packageManager, "geo:0,0?q=donuts") != null;
        this.ib = a(packageManager, "http://www.google.com") == null ? false : z;
        this.ic = telephonyManager.getNetworkOperator();
        this.id = locale.getCountry();
        this.ie = cs.ax();
        this.f1if = audioManager.isMusicActive();
        this.ig = audioManager.isSpeakerphoneOn();
        this.ih = locale.getLanguage();
        this.ii = a(packageManager);
        this.ij = audioManager.getStreamVolume(3);
        this.ik = a(context, connectivityManager, packageManager);
        this.il = telephonyManager.getNetworkType();
        this.im = telephonyManager.getPhoneType();
        this.in = audioManager.getRingerMode();
        this.io = audioManager.getStreamVolume(2);
        this.ip = displayMetrics.density;
        this.iq = displayMetrics.widthPixels;
        this.ir = displayMetrics.heightPixels;
    }

    private static int a(Context context, ConnectivityManager connectivityManager, PackageManager packageManager) {
        if (!co.a(packageManager, context.getPackageName(), "android.permission.ACCESS_NETWORK_STATE")) {
            return -2;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.getType();
        }
        return -1;
    }

    private static ResolveInfo a(PackageManager packageManager, String str) {
        return packageManager.resolveActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)), 65536);
    }

    private static String a(PackageManager packageManager) {
        ActivityInfo activityInfo;
        ResolveInfo a = a(packageManager, "market://details?id=com.google.android.gms.ads");
        if (a == null || (activityInfo = a.activityInfo) == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(activityInfo.packageName, 0);
            if (packageInfo != null) {
                return packageInfo.versionCode + "." + activityInfo.packageName;
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
