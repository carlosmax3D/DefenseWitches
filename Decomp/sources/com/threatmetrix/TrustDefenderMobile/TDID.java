package com.threatmetrix.TrustDefenderMobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.tapjoy.TapjoyConstants;
import java.util.Locale;
import java.util.UUID;
import jp.stargarage.g2metrics.BuildConfig;

class TDID {
    private static final String TAG = TDID.class.getName();

    TDID() {
    }

    private static String checkLength(String str) {
        String str2 = str;
        if (str == null) {
            return null;
        }
        if (str.length() == 32) {
            return str2;
        }
        return str.length() < 32 ? str + StringUtils.MD5(str).substring(0, 32 - str.length()) : StringUtils.MD5(str);
    }

    static String getAndroidID(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), TapjoyConstants.TJC_ANDROID_ID);
        if (string != null && !string.equals("9774d56d682e549c") && string.length() >= 15) {
            return string;
        }
        String str = TAG;
        return null;
    }

    static String getCookie(String str, boolean z) {
        String str2 = str;
        if (str != null) {
            if (z) {
                str2 = StringUtils.MD5(str);
            }
            String str3 = TAG;
            new StringBuilder("using ANDROID_ID for TPC:").append(str2);
        }
        return checkLength(str2);
    }

    static String getFlashCookie$26396263(String str, String str2, String str3, boolean z) {
        String str4 = Build.SERIAL == null ? BuildConfig.FLAVOR : Build.SERIAL;
        String str5 = (str3 == null || str3.isEmpty() || str3.equals("000000000000000")) ? str != null ? str4 + str : str4 + str2 : str4 + str3;
        if (z) {
            str5 = StringUtils.MD5(str5);
        }
        return checkLength(StringUtils.MD5(str5));
    }

    static String getHTML5Cookie(String str, boolean z) {
        String str2 = str;
        if (z) {
            str2 = StringUtils.MD5(str);
        }
        String str3 = TAG;
        new StringBuilder("using generated ID for LSC:").append(str2);
        return checkLength(str2);
    }

    static String getIMEI(Context context) {
        try {
            String deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            String str = TAG;
            new StringBuilder("imei: ").append(deviceId);
            return deviceId;
        } catch (SecurityException e) {
            String str2 = TAG;
            return BuildConfig.FLAVOR;
        }
    }

    static String getPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ThreatMetrixMobileSDK", 0);
        String string = sharedPreferences.getString("ThreatMetrixMobileSDK", (String) null);
        if (string != null) {
            return string;
        }
        String str = TAG;
        String lowerCase = UUID.randomUUID().toString().replace(AdNetworkKey.DEFAULT_AD, BuildConfig.FLAVOR).toLowerCase(Locale.US);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("ThreatMetrixMobileSDK", lowerCase);
        edit.commit();
        return lowerCase;
    }

    private static String getSerial(String str, String str2, String str3) {
        String str4 = Build.SERIAL == null ? BuildConfig.FLAVOR : Build.SERIAL;
        return (str3 == null || str3.isEmpty() || str3.equals("000000000000000")) ? str != null ? str4 + str : str4 + str2 : str4 + str3;
    }

    static boolean isDodgySerial() {
        String str = Build.SERIAL;
        if (str != null) {
            return str.equals("unknown") || str.equals("1234567890ABCDEF");
        }
        return false;
    }
}
