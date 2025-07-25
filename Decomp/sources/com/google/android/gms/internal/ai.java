package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.ads.search.SearchAdRequest;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class ai implements SafeParcelable {
    public static final aj CREATOR = new aj();
    public final int backgroundColor;
    public final int eZ;
    public final int fa;
    public final int fb;
    public final int fc;
    public final int fd;
    public final int fe;
    public final int ff;
    public final String fg;
    public final int fh;
    public final String fi;
    public final int fj;
    public final int fk;
    public final String fl;
    public final int versionCode;

    ai(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, String str, int i10, String str2, int i11, int i12, String str3) {
        this.versionCode = i;
        this.eZ = i2;
        this.backgroundColor = i3;
        this.fa = i4;
        this.fb = i5;
        this.fc = i6;
        this.fd = i7;
        this.fe = i8;
        this.ff = i9;
        this.fg = str;
        this.fh = i10;
        this.fi = str2;
        this.fj = i11;
        this.fk = i12;
        this.fl = str3;
    }

    public ai(SearchAdRequest searchAdRequest) {
        this.versionCode = 1;
        this.eZ = searchAdRequest.getAnchorTextColor();
        this.backgroundColor = searchAdRequest.getBackgroundColor();
        this.fa = searchAdRequest.getBackgroundGradientBottom();
        this.fb = searchAdRequest.getBackgroundGradientTop();
        this.fc = searchAdRequest.getBorderColor();
        this.fd = searchAdRequest.getBorderThickness();
        this.fe = searchAdRequest.getBorderType();
        this.ff = searchAdRequest.getCallButtonColor();
        this.fg = searchAdRequest.getCustomChannels();
        this.fh = searchAdRequest.getDescriptionTextColor();
        this.fi = searchAdRequest.getFontFace();
        this.fj = searchAdRequest.getHeaderTextColor();
        this.fk = searchAdRequest.getHeaderTextSize();
        this.fl = searchAdRequest.getQuery();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        aj.a(this, parcel, i);
    }
}
