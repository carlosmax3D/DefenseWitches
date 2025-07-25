package com.google.android.gms.internal;

import java.util.Collections;
import java.util.List;

public final class cj {
    public final int errorCode;
    public final List<String> fK;
    public final List<String> fL;
    public final long fO;
    public final cw gJ;
    public final at gb;
    public final bc gc;
    public final String gd;
    public final aw ge;
    public final List<String> hA;
    public final v hr;
    public final String hu;
    public final long hx;
    public final boolean hy;
    public final long hz;
    public final au is;
    public final x it;
    public final int orientation;

    public cj(v vVar, cw cwVar, List<String> list, int i, List<String> list2, List<String> list3, int i2, long j, String str, boolean z, at atVar, bc bcVar, String str2, au auVar, aw awVar, long j2, x xVar, long j3) {
        this.hr = vVar;
        this.gJ = cwVar;
        this.fK = list != null ? Collections.unmodifiableList(list) : null;
        this.errorCode = i;
        this.fL = list2 != null ? Collections.unmodifiableList(list2) : null;
        this.hA = list3 != null ? Collections.unmodifiableList(list3) : null;
        this.orientation = i2;
        this.fO = j;
        this.hu = str;
        this.hy = z;
        this.gb = atVar;
        this.gc = bcVar;
        this.gd = str2;
        this.is = auVar;
        this.ge = awVar;
        this.hz = j2;
        this.it = xVar;
        this.hx = j3;
    }
}
