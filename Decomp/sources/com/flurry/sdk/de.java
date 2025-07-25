package com.flurry.sdk;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class de implements eu {
    protected final String d;
    Set<String> e = new HashSet();
    dg f;
    protected String g = "defaultDataKey_";

    public interface a {
        void a();
    }

    public de(String str, String str2) {
        this.d = str2;
        ev.a().a((eu) this);
        a(str);
    }

    /* access modifiers changed from: protected */
    public String a(String str, String str2) {
        return this.g + str + "_" + str2;
    }

    /* access modifiers changed from: protected */
    public void a(final a aVar) {
        a((ff) new ff() {
            public void a() {
                de.this.f();
                if (aVar != null) {
                    aVar.a();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void a(ff ffVar) {
        Cdo.a().c(ffVar);
    }

    /* access modifiers changed from: protected */
    public void a(final String str) {
        a((ff) new ff() {
            public void a() {
                de.this.f = new dg(str);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void a(final String str, final String str2, int i) {
        a((ff) new ff() {
            public void a() {
                if (!de.this.f.a(str, str2)) {
                    eo.a(6, de.this.d, "Internal error. Block wasn't deleted with id = " + str);
                }
                if (!de.this.e.remove(str)) {
                    eo.a(6, de.this.d, "Internal error. Block with id = " + str + " was not in progress state");
                }
            }
        });
    }

    public void a(boolean z) {
        eo.a(4, this.d, "onNetworkStateChanged : isNetworkEnable = " + z);
        if (z) {
            d();
        }
    }

    /* access modifiers changed from: protected */
    public abstract void a(byte[] bArr, String str, String str2);

    /* access modifiers changed from: protected */
    public void a(byte[] bArr, String str, String str2, a aVar) {
        if (bArr == null || bArr.length == 0) {
            eo.a(6, this.d, "Report that has to be sent is EMPTY or NULL");
            return;
        }
        c(bArr, str, str2);
        a(aVar);
    }

    /* access modifiers changed from: protected */
    public void b(final String str, String str2) {
        a((ff) new ff() {
            public void a() {
                if (!de.this.e.remove(str)) {
                    eo.a(6, de.this.d, "Internal error. Block with id = " + str + " was not in progress state");
                }
            }
        });
    }

    public void b(byte[] bArr, String str, String str2) {
        a(bArr, str, str2, (a) null);
    }

    /* access modifiers changed from: protected */
    public int c() {
        return this.e.size();
    }

    /* access modifiers changed from: protected */
    public void c(String str, String str2) {
        if (!this.f.a(str, str2)) {
            eo.a(6, this.d, "Internal error. Block wasn't deleted with id = " + str);
        }
        if (!this.e.remove(str)) {
            eo.a(6, this.d, "Internal error. Block with id = " + str + " was not in progress state");
        }
    }

    /* access modifiers changed from: protected */
    public void c(final byte[] bArr, final String str, final String str2) {
        a((ff) new ff() {
            public void a() {
                de.this.d(bArr, str, str2);
            }
        });
    }

    /* access modifiers changed from: protected */
    public String d(byte[] bArr, String str, String str2) {
        String a2 = a(str, str2);
        df dfVar = new df();
        dfVar.a(bArr);
        String a3 = dfVar.a();
        this.f.a(dfVar, a2);
        return a3;
    }

    /* access modifiers changed from: protected */
    public void d() {
        a((a) null);
    }

    /* access modifiers changed from: protected */
    public boolean e() {
        return c() <= 5;
    }

    /* access modifiers changed from: protected */
    public void f() {
        if (!ev.a().c()) {
            eo.a(5, this.d, "Reports were not sent! No Internet connection!");
            return;
        }
        List<String> a2 = this.f.a();
        if (a2 == null || a2.isEmpty()) {
            eo.a(4, this.d, "No more reports to send.");
            return;
        }
        for (String next : a2) {
            if (e()) {
                List<String> c = this.f.c(next);
                eo.a(4, this.d, "Number of not sent blocks = " + c.size());
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 >= c.size()) {
                        break;
                    }
                    String str = c.get(i2);
                    if (!this.e.contains(str)) {
                        if (!e()) {
                            break;
                        }
                        byte[] b = new df(str).b();
                        if (b == null || b.length == 0) {
                            eo.a(6, this.d, "Internal ERROR! Report is empty!");
                            this.f.a(str, next);
                        } else {
                            this.e.add(str);
                            a(b, str, next);
                        }
                    }
                    i = i2 + 1;
                }
            } else {
                return;
            }
        }
    }
}
