package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.b;
import com.google.android.gms.maps.internal.r;

public final class MarkerOptions implements SafeParcelable {
    public static final MarkerOptionsCreator CREATOR = new MarkerOptionsCreator();
    private float CF;
    private float CG;
    private LatLng CM;
    private String CN;
    private BitmapDescriptor CO;
    private boolean CP;
    private boolean CQ;
    private float CR;
    private float CS;
    private float CT;
    private boolean Cx;
    private final int kg;
    private float mAlpha;
    private String qL;

    public MarkerOptions() {
        this.CF = 0.5f;
        this.CG = 1.0f;
        this.Cx = true;
        this.CQ = false;
        this.CR = 0.0f;
        this.CS = 0.5f;
        this.CT = 0.0f;
        this.mAlpha = 1.0f;
        this.kg = 1;
    }

    MarkerOptions(int i, LatLng latLng, String str, String str2, IBinder iBinder, float f, float f2, boolean z, boolean z2, boolean z3, float f3, float f4, float f5, float f6) {
        this.CF = 0.5f;
        this.CG = 1.0f;
        this.Cx = true;
        this.CQ = false;
        this.CR = 0.0f;
        this.CS = 0.5f;
        this.CT = 0.0f;
        this.mAlpha = 1.0f;
        this.kg = i;
        this.CM = latLng;
        this.qL = str;
        this.CN = str2;
        this.CO = iBinder == null ? null : new BitmapDescriptor(b.a.E(iBinder));
        this.CF = f;
        this.CG = f2;
        this.CP = z;
        this.Cx = z2;
        this.CQ = z3;
        this.CR = f3;
        this.CS = f4;
        this.CT = f5;
        this.mAlpha = f6;
    }

    public MarkerOptions alpha(float f) {
        this.mAlpha = f;
        return this;
    }

    public MarkerOptions anchor(float f, float f2) {
        this.CF = f;
        this.CG = f2;
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public MarkerOptions draggable(boolean z) {
        this.CP = z;
        return this;
    }

    /* access modifiers changed from: package-private */
    public IBinder eG() {
        if (this.CO == null) {
            return null;
        }
        return this.CO.el().asBinder();
    }

    public MarkerOptions flat(boolean z) {
        this.CQ = z;
        return this;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public float getAnchorU() {
        return this.CF;
    }

    public float getAnchorV() {
        return this.CG;
    }

    public BitmapDescriptor getIcon() {
        return this.CO;
    }

    public float getInfoWindowAnchorU() {
        return this.CS;
    }

    public float getInfoWindowAnchorV() {
        return this.CT;
    }

    public LatLng getPosition() {
        return this.CM;
    }

    public float getRotation() {
        return this.CR;
    }

    public String getSnippet() {
        return this.CN;
    }

    public String getTitle() {
        return this.qL;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public MarkerOptions icon(BitmapDescriptor bitmapDescriptor) {
        this.CO = bitmapDescriptor;
        return this;
    }

    public MarkerOptions infoWindowAnchor(float f, float f2) {
        this.CS = f;
        this.CT = f2;
        return this;
    }

    public boolean isDraggable() {
        return this.CP;
    }

    public boolean isFlat() {
        return this.CQ;
    }

    public boolean isVisible() {
        return this.Cx;
    }

    public MarkerOptions position(LatLng latLng) {
        this.CM = latLng;
        return this;
    }

    public MarkerOptions rotation(float f) {
        this.CR = f;
        return this;
    }

    public MarkerOptions snippet(String str) {
        this.CN = str;
        return this;
    }

    public MarkerOptions title(String str) {
        this.qL = str;
        return this;
    }

    public MarkerOptions visible(boolean z) {
        this.Cx = z;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (r.eD()) {
            f.a(this, parcel, i);
        } else {
            MarkerOptionsCreator.a(this, parcel, i);
        }
    }
}
