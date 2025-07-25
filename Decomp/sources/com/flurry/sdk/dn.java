package com.flurry.sdk;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import jp.stargarage.g2metrics.BuildConfig;

public class dn {
    private static final String a = dn.class.getSimpleName();
    private static dn b;

    public static synchronized dn a() {
        dn dnVar;
        synchronized (dn.class) {
            if (b == null) {
                b = new dn();
            }
            dnVar = b;
        }
        return dnVar;
    }

    public int b() {
        int intValue = ((Integer) dp.a().a("AgentVersion")).intValue();
        eo.a(4, a, "getAgentVersion() = " + intValue);
        return intValue;
    }

    /* access modifiers changed from: package-private */
    public int c() {
        return ((Integer) dp.a().a("ReleaseMajorVersion")).intValue();
    }

    /* access modifiers changed from: package-private */
    public int d() {
        return ((Integer) dp.a().a("ReleaseMinorVersion")).intValue();
    }

    /* access modifiers changed from: package-private */
    public int e() {
        return ((Integer) dp.a().a("ReleasePatchVersion")).intValue();
    }

    /* access modifiers changed from: package-private */
    public String f() {
        return (String) dp.a().a("ReleaseBetaVersion");
    }

    public String g() {
        return String.format(Locale.getDefault(), "Flurry_Android_%d_%d.%d.%d%s%s", new Object[]{Integer.valueOf(b()), Integer.valueOf(c()), Integer.valueOf(d()), Integer.valueOf(e()), f().length() > 0 ? "." : BuildConfig.FLAVOR, f()});
    }

    public String h() {
        dj c = dl.a().c();
        if (c != null) {
            return c.j();
        }
        return null;
    }

    public String i() {
        dj c = dl.a().c();
        if (c != null) {
            return c.k();
        }
        return null;
    }

    public String j() {
        dj c = dl.a().c();
        if (c != null) {
            return c.l();
        }
        return null;
    }

    public boolean k() {
        dj c = dl.a().c();
        if (c != null) {
            return c.o();
        }
        return true;
    }

    public Map<dr, ByteBuffer> l() {
        dj c = dl.a().c();
        return c != null ? c.p() : Collections.emptyMap();
    }
}
