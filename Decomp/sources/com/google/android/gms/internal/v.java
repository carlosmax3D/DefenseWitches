package com.google.android.gms.internal;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.ads.mediation.admob.AdMobExtras;
import com.google.android.gms.ads.search.SearchAdRequest;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

public final class v implements SafeParcelable {
    public static final w CREATOR = new w();
    public final boolean eA;
    public final boolean eB;
    public final String eC;
    public final ai eD;
    public final Location eE;
    public final long ex;
    public final Bundle extras;
    public final int ey;
    public final List<String> ez;
    public final int tagForChildDirectedTreatment;
    public final int versionCode;

    v(int i, long j, Bundle bundle, int i2, List<String> list, boolean z, int i3, boolean z2, String str, ai aiVar, Location location) {
        this.versionCode = i;
        this.ex = j;
        this.extras = bundle;
        this.ey = i2;
        this.ez = list;
        this.eA = z;
        this.tagForChildDirectedTreatment = i3;
        this.eB = z2;
        this.eC = str;
        this.eD = aiVar;
        this.eE = location;
    }

    public v(Context context, af afVar) {
        ai aiVar = null;
        this.versionCode = 2;
        Date birthday = afVar.getBirthday();
        this.ex = birthday != null ? birthday.getTime() : -1;
        this.ey = afVar.getGender();
        Set<String> keywords = afVar.getKeywords();
        this.ez = !keywords.isEmpty() ? Collections.unmodifiableList(new ArrayList(keywords)) : null;
        this.eA = afVar.isTestDevice(context);
        this.tagForChildDirectedTreatment = afVar.S();
        this.eE = afVar.getLocation();
        AdMobExtras adMobExtras = (AdMobExtras) afVar.getNetworkExtras(AdMobExtras.class);
        this.extras = adMobExtras != null ? adMobExtras.getExtras() : null;
        this.eB = afVar.getManualImpressionsEnabled();
        this.eC = afVar.getPublisherProvidedId();
        SearchAdRequest Q = afVar.Q();
        this.eD = Q != null ? new ai(Q) : aiVar;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        w.a(this, parcel, i);
    }
}
