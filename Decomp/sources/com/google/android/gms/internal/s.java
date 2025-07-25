package com.google.android.gms.internal;

import java.lang.ref.WeakReference;

public final class s {
    private final Runnable ep;
    /* access modifiers changed from: private */
    public v eq;
    /* access modifiers changed from: private */
    public boolean er = false;

    public s(final r rVar) {
        this.ep = new Runnable() {
            private final WeakReference<r> es = new WeakReference<>(rVar);

            public void run() {
                boolean unused = s.this.er = false;
                r rVar = (r) this.es.get();
                if (rVar != null) {
                    rVar.b(s.this.eq);
                }
            }
        };
    }

    public void a(v vVar, long j) {
        if (this.er) {
            ct.v("An ad refresh is already scheduled.");
            return;
        }
        ct.t("Scheduling ad refresh " + j + " milliseconds from now.");
        this.eq = vVar;
        this.er = true;
        cs.iI.postDelayed(this.ep, j);
    }

    public void cancel() {
        cs.iI.removeCallbacks(this.ep);
    }

    public void d(v vVar) {
        a(vVar, 60000);
    }
}
