package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.a;
import com.google.android.gms.maps.internal.r;
import com.google.android.gms.maps.model.CameraPosition;

public final class GoogleMapOptions implements SafeParcelable {
    public static final GoogleMapOptionsCreator CREATOR = new GoogleMapOptionsCreator();
    private Boolean BJ;
    private Boolean BK;
    private int BL;
    private CameraPosition BM;
    private Boolean BN;
    private Boolean BO;
    private Boolean BP;
    private Boolean BQ;
    private Boolean BR;
    private Boolean BS;
    private final int kg;

    public GoogleMapOptions() {
        this.BL = -1;
        this.kg = 1;
    }

    GoogleMapOptions(int i, byte b, byte b2, int i2, CameraPosition cameraPosition, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) {
        this.BL = -1;
        this.kg = i;
        this.BJ = a.a(b);
        this.BK = a.a(b2);
        this.BL = i2;
        this.BM = cameraPosition;
        this.BN = a.a(b3);
        this.BO = a.a(b4);
        this.BP = a.a(b5);
        this.BQ = a.a(b6);
        this.BR = a.a(b7);
        this.BS = a.a(b8);
    }

    public static GoogleMapOptions createFromAttributes(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return null;
        }
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, R.styleable.MapAttrs);
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        if (obtainAttributes.hasValue(0)) {
            googleMapOptions.mapType(obtainAttributes.getInt(0, -1));
        }
        if (obtainAttributes.hasValue(13)) {
            googleMapOptions.zOrderOnTop(obtainAttributes.getBoolean(13, false));
        }
        if (obtainAttributes.hasValue(12)) {
            googleMapOptions.useViewLifecycleInFragment(obtainAttributes.getBoolean(12, false));
        }
        if (obtainAttributes.hasValue(6)) {
            googleMapOptions.compassEnabled(obtainAttributes.getBoolean(6, true));
        }
        if (obtainAttributes.hasValue(7)) {
            googleMapOptions.rotateGesturesEnabled(obtainAttributes.getBoolean(7, true));
        }
        if (obtainAttributes.hasValue(8)) {
            googleMapOptions.scrollGesturesEnabled(obtainAttributes.getBoolean(8, true));
        }
        if (obtainAttributes.hasValue(9)) {
            googleMapOptions.tiltGesturesEnabled(obtainAttributes.getBoolean(9, true));
        }
        if (obtainAttributes.hasValue(11)) {
            googleMapOptions.zoomGesturesEnabled(obtainAttributes.getBoolean(11, true));
        }
        if (obtainAttributes.hasValue(10)) {
            googleMapOptions.zoomControlsEnabled(obtainAttributes.getBoolean(10, true));
        }
        googleMapOptions.camera(CameraPosition.createFromAttributes(context, attributeSet));
        obtainAttributes.recycle();
        return googleMapOptions;
    }

    public GoogleMapOptions camera(CameraPosition cameraPosition) {
        this.BM = cameraPosition;
        return this;
    }

    public GoogleMapOptions compassEnabled(boolean z) {
        this.BO = Boolean.valueOf(z);
        return this;
    }

    public int describeContents() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public byte eo() {
        return a.c(this.BJ);
    }

    /* access modifiers changed from: package-private */
    public byte ep() {
        return a.c(this.BK);
    }

    /* access modifiers changed from: package-private */
    public byte eq() {
        return a.c(this.BN);
    }

    /* access modifiers changed from: package-private */
    public byte er() {
        return a.c(this.BO);
    }

    /* access modifiers changed from: package-private */
    public byte es() {
        return a.c(this.BP);
    }

    /* access modifiers changed from: package-private */
    public byte et() {
        return a.c(this.BQ);
    }

    /* access modifiers changed from: package-private */
    public byte eu() {
        return a.c(this.BR);
    }

    /* access modifiers changed from: package-private */
    public byte ev() {
        return a.c(this.BS);
    }

    public CameraPosition getCamera() {
        return this.BM;
    }

    public Boolean getCompassEnabled() {
        return this.BO;
    }

    public int getMapType() {
        return this.BL;
    }

    public Boolean getRotateGesturesEnabled() {
        return this.BS;
    }

    public Boolean getScrollGesturesEnabled() {
        return this.BP;
    }

    public Boolean getTiltGesturesEnabled() {
        return this.BR;
    }

    public Boolean getUseViewLifecycleInFragment() {
        return this.BK;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public Boolean getZOrderOnTop() {
        return this.BJ;
    }

    public Boolean getZoomControlsEnabled() {
        return this.BN;
    }

    public Boolean getZoomGesturesEnabled() {
        return this.BQ;
    }

    public GoogleMapOptions mapType(int i) {
        this.BL = i;
        return this;
    }

    public GoogleMapOptions rotateGesturesEnabled(boolean z) {
        this.BS = Boolean.valueOf(z);
        return this;
    }

    public GoogleMapOptions scrollGesturesEnabled(boolean z) {
        this.BP = Boolean.valueOf(z);
        return this;
    }

    public GoogleMapOptions tiltGesturesEnabled(boolean z) {
        this.BR = Boolean.valueOf(z);
        return this;
    }

    public GoogleMapOptions useViewLifecycleInFragment(boolean z) {
        this.BK = Boolean.valueOf(z);
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (r.eD()) {
            a.a(this, parcel, i);
        } else {
            GoogleMapOptionsCreator.a(this, parcel, i);
        }
    }

    public GoogleMapOptions zOrderOnTop(boolean z) {
        this.BJ = Boolean.valueOf(z);
        return this;
    }

    public GoogleMapOptions zoomControlsEnabled(boolean z) {
        this.BN = Boolean.valueOf(z);
        return this;
    }

    public GoogleMapOptions zoomGesturesEnabled(boolean z) {
        this.BQ = Boolean.valueOf(z);
        return this;
    }
}
