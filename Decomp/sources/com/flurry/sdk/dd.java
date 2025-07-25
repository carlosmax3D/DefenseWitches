package com.flurry.sdk;

import android.widget.Toast;
import com.flurry.sdk.de;
import com.flurry.sdk.dq;
import com.flurry.sdk.el;
import com.flurry.sdk.en;

public class dd extends de implements dq.a {
    static String a;
    static String b = "http://data.flurry.com/aap.do";
    static String c = "https://data.flurry.com/aap.do";
    /* access modifiers changed from: private */
    public static final String h = dd.class.getSimpleName();
    private boolean i;

    public dd() {
        this((de.a) null);
    }

    dd(de.a aVar) {
        super("Analytics", dd.class.getSimpleName());
        this.g = "AnalyticsData_";
        g();
        a(aVar);
    }

    private void b(String str) {
        if (str != null && !str.endsWith(".do")) {
            eo.a(5, h, "overriding analytics agent report URL without an endpoint, are you sure?");
        }
        a = str;
    }

    private void g() {
        dq a2 = dp.a();
        this.i = ((Boolean) a2.a("UseHttps")).booleanValue();
        a2.a("UseHttps", (dq.a) this);
        eo.a(4, h, "initSettings, UseHttps = " + this.i);
        String str = (String) a2.a("ReportUrl");
        a2.a("ReportUrl", (dq.a) this);
        b(str);
        eo.a(4, h, "initSettings, ReportUrl = " + str);
    }

    /* access modifiers changed from: package-private */
    public String a() {
        return a != null ? a : this.i ? c : b;
    }

    public void a(String str, Object obj) {
        if (str.equals("UseHttps")) {
            this.i = ((Boolean) obj).booleanValue();
            eo.a(4, h, "onSettingUpdate, UseHttps = " + this.i);
        } else if (str.equals("ReportUrl")) {
            String str2 = (String) obj;
            b(str2);
            eo.a(4, h, "onSettingUpdate, ReportUrl = " + str2);
        } else {
            eo.a(6, h, "onSettingUpdate internal error!");
        }
    }

    /* access modifiers changed from: protected */
    public void a(String str, String str2, final int i2) {
        a((ff) new ff() {
            public void a() {
                dj c;
                if (i2 == 200 && (c = dl.a().c()) != null) {
                    c.b();
                }
            }
        });
        super.a(str, str2, i2);
    }

    /* access modifiers changed from: protected */
    public void a(byte[] bArr, final String str, final String str2) {
        String a2 = a();
        eo.a(4, h, "FlurryDataSender: start upload data " + bArr + " with id = " + str + " to " + a2);
        el elVar = new el();
        elVar.a(a2);
        elVar.a(en.a.kPost);
        elVar.a("Content-Type", "application/octet-stream");
        elVar.a(new ew());
        elVar.a(bArr);
        elVar.a(new el.a<byte[], Void>() {
            public void a(el<byte[], Void> elVar, Void voidR) {
                final int e = elVar.e();
                if (e > 0) {
                    eo.d(dd.h, "FlurryDataSender: report " + str + " sent. HTTP response: " + e);
                    if (eo.c() <= 3 && eo.d()) {
                        Cdo.a().a((Runnable) new Runnable() {
                            public void run() {
                                Toast.makeText(Cdo.a().b(), "SD HTTP Response Code: " + e, 0).show();
                            }
                        });
                    }
                    dd.this.a(str, str2, e);
                    dd.this.d();
                    return;
                }
                dd.this.b(str, str2);
            }
        });
        em.a().a((Object) this, elVar);
    }
}
