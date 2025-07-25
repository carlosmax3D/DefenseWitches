package com.google.android.gms.internal;

import java.util.Map;

public final class cg {
    /* access modifiers changed from: private */
    public final Object fx = new Object();
    private cw gv;
    /* access modifiers changed from: private */
    public String hK;
    /* access modifiers changed from: private */
    public String hL;
    public final an hM = new an() {
        public void a(cw cwVar, Map<String, String> map) {
            synchronized (cg.this.fx) {
                ct.v("Invalid " + map.get("type") + " request error: " + map.get("errors"));
                int unused = cg.this.hm = 1;
                cg.this.fx.notify();
            }
        }
    };
    public final an hN = new an() {
        public void a(cw cwVar, Map<String, String> map) {
            synchronized (cg.this.fx) {
                String str = map.get("url");
                if (str == null) {
                    ct.v("URL missing in loadAdUrl GMSG.");
                    return;
                }
                if (str.contains("%40mediation_adapters%40")) {
                    str = str.replaceAll("%40mediation_adapters%40", cl.b(cwVar.getContext(), map.get("check_adapters"), cg.this.hK));
                    ct.u("Ad request URL modified to " + str);
                }
                String unused = cg.this.hL = str;
                cg.this.fx.notify();
            }
        }
    };
    /* access modifiers changed from: private */
    public int hm = -2;

    public cg(String str) {
        this.hK = str;
    }

    public String ap() {
        String str;
        synchronized (this.fx) {
            while (this.hL == null && this.hm == -2) {
                try {
                    this.fx.wait();
                } catch (InterruptedException e) {
                    ct.v("Ad request service was interrupted.");
                    str = null;
                }
            }
            str = this.hL;
        }
        return str;
    }

    public void b(cw cwVar) {
        synchronized (this.fx) {
            this.gv = cwVar;
        }
    }

    public int getErrorCode() {
        int i;
        synchronized (this.fx) {
            i = this.hm;
        }
        return i;
    }
}
