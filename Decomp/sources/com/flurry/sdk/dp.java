package com.flurry.sdk;

import android.location.Criteria;
import jp.stargarage.g2metrics.BuildConfig;

public class dp {
    public static final Integer a = 171;
    public static final Integer b = 4;
    public static final Integer c = 1;
    public static final Integer d = 0;
    public static final String e = null;
    public static final Boolean f = true;
    public static final Boolean g = true;
    public static final String h = null;
    public static final Boolean i = true;
    public static final Criteria j = null;
    public static final Long k = 10000L;
    public static final Boolean l = true;
    public static final Long m = 0L;
    public static final Byte n = (byte) -1;
    private static dq o;

    public static synchronized dq a() {
        dq dqVar;
        synchronized (dp.class) {
            if (o == null) {
                o = new dq();
                b();
            }
            dqVar = o;
        }
        return dqVar;
    }

    private static void b() {
        if (o == null) {
            o = new dq();
        }
        o.a("AgentVersion", (Object) a);
        o.a("ReleaseMajorVersion", (Object) b);
        o.a("ReleaseMinorVersion", (Object) c);
        o.a("ReleasePatchVersion", (Object) d);
        o.a("ReleaseBetaVersion", (Object) BuildConfig.FLAVOR);
        o.a("VersionName", (Object) e);
        o.a("CaptureUncaughtExceptions", (Object) f);
        o.a("UseHttps", (Object) g);
        o.a("ReportUrl", (Object) h);
        o.a("ReportLocation", (Object) i);
        o.a("LocationCriteria", (Object) j);
        o.a("ContinueSessionMillis", (Object) k);
        o.a("LogEvents", (Object) l);
        o.a("Age", (Object) m);
        o.a("Gender", (Object) n);
        o.a("UserId", (Object) BuildConfig.FLAVOR);
    }
}
