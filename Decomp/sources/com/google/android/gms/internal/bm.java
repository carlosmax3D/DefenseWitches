package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.b;
import com.google.android.gms.dynamic.c;

public final class bm implements SafeParcelable {
    public static final bl CREATOR = new bl();
    public final cu ej;
    public final bj gG;
    public final q gH;
    public final bn gI;
    public final cw gJ;
    public final al gK;
    public final String gL;
    public final boolean gM;
    public final String gN;
    public final bq gO;
    public final int gP;
    public final String go;
    public final int orientation;
    public final int versionCode;

    bm(int i, bj bjVar, IBinder iBinder, IBinder iBinder2, IBinder iBinder3, IBinder iBinder4, String str, boolean z, String str2, IBinder iBinder5, int i2, int i3, String str3, cu cuVar) {
        this.versionCode = i;
        this.gG = bjVar;
        this.gH = (q) c.b(b.a.E(iBinder));
        this.gI = (bn) c.b(b.a.E(iBinder2));
        this.gJ = (cw) c.b(b.a.E(iBinder3));
        this.gK = (al) c.b(b.a.E(iBinder4));
        this.gL = str;
        this.gM = z;
        this.gN = str2;
        this.gO = (bq) c.b(b.a.E(iBinder5));
        this.orientation = i2;
        this.gP = i3;
        this.go = str3;
        this.ej = cuVar;
    }

    public bm(bj bjVar, q qVar, bn bnVar, bq bqVar, cu cuVar) {
        this.versionCode = 1;
        this.gG = bjVar;
        this.gH = qVar;
        this.gI = bnVar;
        this.gJ = null;
        this.gK = null;
        this.gL = null;
        this.gM = false;
        this.gN = null;
        this.gO = bqVar;
        this.orientation = -1;
        this.gP = 4;
        this.go = null;
        this.ej = cuVar;
    }

    public bm(q qVar, bn bnVar, al alVar, bq bqVar, cw cwVar, boolean z, int i, String str, cu cuVar) {
        this.versionCode = 1;
        this.gG = null;
        this.gH = qVar;
        this.gI = bnVar;
        this.gJ = cwVar;
        this.gK = alVar;
        this.gL = null;
        this.gM = z;
        this.gN = null;
        this.gO = bqVar;
        this.orientation = i;
        this.gP = 3;
        this.go = str;
        this.ej = cuVar;
    }

    public bm(q qVar, bn bnVar, al alVar, bq bqVar, cw cwVar, boolean z, int i, String str, String str2, cu cuVar) {
        this.versionCode = 1;
        this.gG = null;
        this.gH = qVar;
        this.gI = bnVar;
        this.gJ = cwVar;
        this.gK = alVar;
        this.gL = str2;
        this.gM = z;
        this.gN = str;
        this.gO = bqVar;
        this.orientation = i;
        this.gP = 3;
        this.go = null;
        this.ej = cuVar;
    }

    public bm(q qVar, bn bnVar, bq bqVar, cw cwVar, int i, cu cuVar) {
        this.versionCode = 1;
        this.gG = null;
        this.gH = qVar;
        this.gI = bnVar;
        this.gJ = cwVar;
        this.gK = null;
        this.gL = null;
        this.gM = false;
        this.gN = null;
        this.gO = bqVar;
        this.orientation = i;
        this.gP = 1;
        this.go = null;
        this.ej = cuVar;
    }

    public bm(q qVar, bn bnVar, bq bqVar, cw cwVar, boolean z, int i, cu cuVar) {
        this.versionCode = 1;
        this.gG = null;
        this.gH = qVar;
        this.gI = bnVar;
        this.gJ = cwVar;
        this.gK = null;
        this.gL = null;
        this.gM = z;
        this.gN = null;
        this.gO = bqVar;
        this.orientation = i;
        this.gP = 2;
        this.go = null;
        this.ej = cuVar;
    }

    public static bm a(Intent intent) {
        try {
            Bundle bundleExtra = intent.getBundleExtra("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo");
            bundleExtra.setClassLoader(bm.class.getClassLoader());
            return (bm) bundleExtra.getParcelable("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo");
        } catch (Exception e) {
            return null;
        }
    }

    public static void a(Intent intent, bm bmVar) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo", bmVar);
        intent.putExtra("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo", bundle);
    }

    /* access modifiers changed from: package-private */
    public IBinder aa() {
        return c.h(this.gH).asBinder();
    }

    /* access modifiers changed from: package-private */
    public IBinder ab() {
        return c.h(this.gI).asBinder();
    }

    /* access modifiers changed from: package-private */
    public IBinder ac() {
        return c.h(this.gJ).asBinder();
    }

    /* access modifiers changed from: package-private */
    public IBinder ad() {
        return c.h(this.gK).asBinder();
    }

    /* access modifiers changed from: package-private */
    public IBinder ae() {
        return c.h(this.gO).asBinder();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        bl.a(this, parcel, i);
    }
}
