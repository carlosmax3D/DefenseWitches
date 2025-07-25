package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jp.stargarage.g2metrics.BuildConfig;

public final class gt implements SafeParcelable {
    public static final gu CREATOR = new gu();
    final int kg;
    private final int yf;
    final List<gx> yg;
    private final String yh;
    private final String yi;
    private final boolean yj;
    private final Set<gx> yk;

    gt(int i, int i2, List<gx> list, String str, String str2, boolean z) {
        this.kg = i;
        this.yf = i2;
        this.yg = list == null ? Collections.emptyList() : Collections.unmodifiableList(list);
        this.yh = str == null ? BuildConfig.FLAVOR : str;
        this.yi = str2 == null ? BuildConfig.FLAVOR : str2;
        this.yj = z;
        if (this.yg.isEmpty()) {
            this.yk = Collections.emptySet();
        } else {
            this.yk = Collections.unmodifiableSet(new HashSet(this.yg));
        }
    }

    public int dO() {
        return this.yf;
    }

    public String dP() {
        return this.yh;
    }

    public String dQ() {
        return this.yi;
    }

    public boolean dR() {
        return this.yj;
    }

    public int describeContents() {
        gu guVar = CREATOR;
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof gt)) {
            return false;
        }
        gt gtVar = (gt) obj;
        return this.yf == gtVar.yf && this.yk.equals(gtVar.yk) && this.yh == gtVar.yh && this.yi == gtVar.yi && this.yj == gtVar.yj;
    }

    public int hashCode() {
        return ee.hashCode(Integer.valueOf(this.yf), this.yk, this.yh, this.yi, Boolean.valueOf(this.yj));
    }

    public String toString() {
        return ee.e(this).a("maxResults", Integer.valueOf(this.yf)).a("types", this.yk).a("nameQuery", this.yh).a("textQuery", this.yi).a("isOpenNowRequired", Boolean.valueOf(this.yj)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        gu guVar = CREATOR;
        gu.a(this, parcel, i);
    }
}
