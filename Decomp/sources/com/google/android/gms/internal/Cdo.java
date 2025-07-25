package com.google.android.gms.internal;

import android.os.SystemClock;
import org.json.JSONObject;

/* renamed from: com.google.android.gms.internal.do  reason: invalid class name */
public final class Cdo {
    private static final dk lA = new dk("RequestTracker");
    public static final Object mw = new Object();
    private long ms;
    private long mt = -1;
    private long mu = 0;
    private dn mv;

    public Cdo(long j) {
        this.ms = j;
    }

    private void be() {
        this.mt = -1;
        this.mv = null;
        this.mu = 0;
    }

    public void a(long j, dn dnVar) {
        dn dnVar2;
        long j2;
        synchronized (mw) {
            dnVar2 = this.mv;
            j2 = this.mt;
            this.mt = j;
            this.mv = dnVar;
            this.mu = SystemClock.elapsedRealtime();
        }
        if (dnVar2 != null) {
            dnVar2.g(j2);
        }
    }

    public boolean b(long j, int i, JSONObject jSONObject) {
        boolean z = true;
        dn dnVar = null;
        synchronized (mw) {
            if (this.mt == -1 || this.mt != j) {
                z = false;
            } else {
                lA.b("request %d completed", Long.valueOf(this.mt));
                dnVar = this.mv;
                be();
            }
        }
        if (dnVar != null) {
            dnVar.a(j, i, jSONObject);
        }
        return z;
    }

    public boolean bf() {
        boolean z;
        synchronized (mw) {
            z = this.mt != -1;
        }
        return z;
    }

    public boolean c(long j, int i) {
        return b(j, i, (JSONObject) null);
    }

    public void clear() {
        synchronized (mw) {
            if (this.mt != -1) {
                be();
            }
        }
    }

    public boolean d(long j, int i) {
        dn dnVar;
        boolean z = true;
        long j2 = 0;
        synchronized (mw) {
            if (this.mt == -1 || j - this.mu < this.ms) {
                z = false;
                dnVar = null;
            } else {
                lA.b("request %d timed out", Long.valueOf(this.mt));
                j2 = this.mt;
                dnVar = this.mv;
                be();
            }
        }
        if (dnVar != null) {
            dnVar.a(j2, i, (JSONObject) null);
        }
        return z;
    }

    public boolean i(long j) {
        boolean z;
        synchronized (mw) {
            z = this.mt != -1 && this.mt == j;
        }
        return z;
    }
}
