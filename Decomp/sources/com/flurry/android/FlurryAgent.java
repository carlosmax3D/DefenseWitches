package com.flurry.android;

import android.content.Context;
import android.location.Criteria;
import android.os.Build;
import com.flurry.sdk.cx;
import com.flurry.sdk.dl;
import com.flurry.sdk.dn;
import com.flurry.sdk.dp;
import com.flurry.sdk.dz;
import com.flurry.sdk.eo;
import com.flurry.sdk.fe;
import java.util.Date;
import java.util.Map;
import jp.stargarage.g2metrics.BuildConfig;

public final class FlurryAgent {
    private static final String a = FlurryAgent.class.getSimpleName();

    private FlurryAgent() {
    }

    public static void addOrigin(String str, String str2) {
        addOrigin(str, str2, (Map<String, String>) null);
    }

    public static void addOrigin(String str, String str2, Map<String, String> map) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("originName not specified");
        } else if (str2 == null || str2.length() == 0) {
            throw new IllegalArgumentException("originVersion not specified");
        } else {
            try {
                cx.a().a(str, str2, map);
            } catch (Throwable th) {
                eo.a(a, BuildConfig.FLAVOR, th);
            }
        }
    }

    public static void clearLocation() {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else {
            dz.a().e();
        }
    }

    public static void endTimedEvent(String str) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            eo.b(a, "String eventId passed to endTimedEvent was null.");
        } else {
            try {
                cx.a().b(str);
            } catch (Throwable th) {
                eo.a(a, "Failed to signify the end of event: " + str, th);
            }
        }
    }

    public static void endTimedEvent(String str, Map<String, String> map) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            eo.b(a, "String eventId passed to endTimedEvent was null.");
        } else if (map == null) {
            eo.b(a, "String eventId passed to endTimedEvent was null.");
        } else {
            try {
                cx.a().b(str, map);
            } catch (Throwable th) {
                eo.a(a, "Failed to signify the end of event: " + str, th);
            }
        }
    }

    public static int getAgentVersion() {
        return dn.a().b();
    }

    public static String getReleaseVersion() {
        return dn.a().g();
    }

    public static boolean getUseHttps() {
        if (Build.VERSION.SDK_INT >= 10) {
            return ((Boolean) dp.a().a("UseHttps")).booleanValue();
        }
        eo.b(a, "Device SDK Version older than 10");
        return false;
    }

    public static void logEvent(String str) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            eo.b(a, "String eventId passed to logEvent was null.");
        } else {
            try {
                cx.a().a(str);
            } catch (Throwable th) {
                eo.a(a, "Failed to log event: " + str, th);
            }
        }
    }

    public static void logEvent(String str, Map<String, String> map) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            eo.b(a, "String eventId passed to logEvent was null.");
        } else if (map == null) {
            eo.b(a, "String parameters passed to logEvent was null.");
        } else {
            try {
                cx.a().a(str, map);
            } catch (Throwable th) {
                eo.a(a, "Failed to log event: " + str, th);
            }
        }
    }

    public static void logEvent(String str, Map<String, String> map, boolean z) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            eo.b(a, "String eventId passed to logEvent was null.");
        } else if (map == null) {
            eo.b(a, "String parameters passed to logEvent was null.");
        } else {
            try {
                cx.a().a(str, map, z);
            } catch (Throwable th) {
                eo.a(a, "Failed to log event: " + str, th);
            }
        }
    }

    public static void logEvent(String str, boolean z) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            eo.b(a, "String eventId passed to logEvent was null.");
        } else {
            try {
                cx.a().a(str, z);
            } catch (Throwable th) {
                eo.a(a, "Failed to log event: " + str, th);
            }
        }
    }

    public static void onEndSession(Context context) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (context == null) {
            throw new NullPointerException("Null context");
        } else {
            try {
                dl.a().a(context);
            } catch (Throwable th) {
                eo.a(a, BuildConfig.FLAVOR, th);
            }
        }
    }

    @Deprecated
    public static void onError(String str, String str2, String str3) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            eo.b(a, "String errorId passed to onError was null.");
        } else if (str2 == null) {
            eo.b(a, "String message passed to onError was null.");
        } else if (str3 == null) {
            eo.b(a, "String errorClass passed to onError was null.");
        } else {
            try {
                cx.a().a(str, str2, str3);
            } catch (Throwable th) {
                eo.a(a, BuildConfig.FLAVOR, th);
            }
        }
    }

    public static void onError(String str, String str2, Throwable th) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            eo.b(a, "String errorId passed to onError was null.");
        } else if (str2 == null) {
            eo.b(a, "String message passed to onError was null.");
        } else if (th == null) {
            eo.b(a, "Throwable passed to onError was null.");
        } else {
            try {
                cx.a().a(str, str2, th);
            } catch (Throwable th2) {
                eo.a(a, BuildConfig.FLAVOR, th2);
            }
        }
    }

    @Deprecated
    public static void onEvent(String str) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            eo.b(a, "String eventId passed to onEvent was null.");
        } else {
            try {
                cx.a().c(str);
            } catch (Throwable th) {
                eo.a(a, BuildConfig.FLAVOR, th);
            }
        }
    }

    @Deprecated
    public static void onEvent(String str, Map<String, String> map) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            eo.b(a, "String eventId passed to onEvent was null.");
        } else if (map == null) {
            eo.b(a, "Parameters Map passed to onEvent was null.");
        } else {
            try {
                cx.a().c(str, map);
            } catch (Throwable th) {
                eo.a(a, BuildConfig.FLAVOR, th);
            }
        }
    }

    public static void onPageView() {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
            return;
        }
        try {
            cx.a().c();
        } catch (Throwable th) {
            eo.a(a, BuildConfig.FLAVOR, th);
        }
    }

    public static void onStartSession(Context context, String str) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (context == null) {
            throw new NullPointerException("Null context");
        } else if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Api key not specified");
        } else {
            try {
                dl.a().a(context, str);
            } catch (Throwable th) {
                eo.a(a, BuildConfig.FLAVOR, th);
            }
        }
    }

    public static void setAge(int i) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (i > 0 && i < 110) {
            dp.a().a("Age", (Object) Long.valueOf(new Date(new Date(System.currentTimeMillis() - (((long) i) * 31449600000L)).getYear(), 1, 1).getTime()));
        }
    }

    public static void setCaptureUncaughtExceptions(boolean z) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else {
            dp.a().a("CaptureUncaughtExceptions", (Object) Boolean.valueOf(z));
        }
    }

    public static void setContinueSessionMillis(long j) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (j < 5000) {
            eo.b(a, "Invalid time set for session resumption: " + j);
        } else {
            dp.a().a("ContinueSessionMillis", (Object) Long.valueOf(j));
        }
    }

    public static void setGender(byte b) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
            return;
        }
        switch (b) {
            case 0:
            case 1:
                dp.a().a("Gender", (Object) Byte.valueOf(b));
                return;
            default:
                dp.a().a("Gender", (Object) (byte) -1);
                return;
        }
    }

    public static void setLocation(float f, float f2) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else {
            dz.a().a(f, f2);
        }
    }

    public static void setLocationCriteria(Criteria criteria) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else {
            dp.a().a("LocationCriteria", (Object) criteria);
        }
    }

    public static void setLogEnabled(boolean z) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (z) {
            eo.b();
        } else {
            eo.a();
        }
    }

    public static void setLogEvents(boolean z) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else {
            dp.a().a("LogEvents", (Object) Boolean.valueOf(z));
        }
    }

    public static void setLogLevel(int i) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else {
            eo.a(i);
        }
    }

    public static void setReportLocation(boolean z) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else {
            dp.a().a("ReportLocation", (Object) Boolean.valueOf(z));
        }
    }

    public static void setReportUrl(String str) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else {
            dp.a().a("ReportUrl", (Object) str);
        }
    }

    public static void setUseHttps(boolean z) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else {
            dp.a().a("UseHttps", (Object) Boolean.valueOf(z));
        }
    }

    public static void setUserId(String str) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            eo.b(a, "String userId passed to setUserId was null.");
        } else {
            dp.a().a("UserId", (Object) fe.a(str));
        }
    }

    public static void setVersionName(String str) {
        if (Build.VERSION.SDK_INT < 10) {
            eo.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            eo.b(a, "String versionName passed to setVersionName was null.");
        } else {
            dp.a().a("VersionName", (Object) str);
        }
    }
}
