package com.google.android.gms.internal;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class bz implements SafeParcelable {
    public static final ca CREATOR = new ca();
    public final String adUnitId;
    public final ApplicationInfo applicationInfo;
    public final cu ej;
    public final x em;
    public final Bundle hq;
    public final v hr;
    public final PackageInfo hs;
    public final String ht;
    public final String hu;
    public final String hv;
    public final int versionCode;

    public static final class a {
        public final String adUnitId;
        public final ApplicationInfo applicationInfo;
        public final cu ej;
        public final x em;
        public final Bundle hq;
        public final v hr;
        public final PackageInfo hs;
        public final String hu;
        public final String hv;

        public a(Bundle bundle, v vVar, x xVar, String str, ApplicationInfo applicationInfo2, PackageInfo packageInfo, String str2, String str3, cu cuVar) {
            this.hq = bundle;
            this.hr = vVar;
            this.em = xVar;
            this.adUnitId = str;
            this.applicationInfo = applicationInfo2;
            this.hs = packageInfo;
            this.hu = str2;
            this.hv = str3;
            this.ej = cuVar;
        }
    }

    bz(int i, Bundle bundle, v vVar, x xVar, String str, ApplicationInfo applicationInfo2, PackageInfo packageInfo, String str2, String str3, String str4, cu cuVar) {
        this.versionCode = i;
        this.hq = bundle;
        this.hr = vVar;
        this.em = xVar;
        this.adUnitId = str;
        this.applicationInfo = applicationInfo2;
        this.hs = packageInfo;
        this.ht = str2;
        this.hu = str3;
        this.hv = str4;
        this.ej = cuVar;
    }

    public bz(Bundle bundle, v vVar, x xVar, String str, ApplicationInfo applicationInfo2, PackageInfo packageInfo, String str2, String str3, String str4, cu cuVar) {
        this(1, bundle, vVar, xVar, str, applicationInfo2, packageInfo, str2, str3, str4, cuVar);
    }

    public bz(a aVar, String str) {
        this(aVar.hq, aVar.hr, aVar.em, aVar.adUnitId, aVar.applicationInfo, aVar.hs, str, aVar.hu, aVar.hv, aVar.ej);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        ca.a(this, parcel, i);
    }
}
