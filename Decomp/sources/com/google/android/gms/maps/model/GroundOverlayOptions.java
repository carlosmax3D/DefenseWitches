package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.b;
import com.google.android.gms.internal.eg;
import com.google.android.gms.maps.internal.r;

public final class GroundOverlayOptions implements SafeParcelable {
    public static final GroundOverlayOptionsCreator CREATOR = new GroundOverlayOptionsCreator();
    public static final float NO_DIMENSION = -1.0f;
    private LatLng CA;
    private float CB;
    private float CC;
    private LatLngBounds CD;
    private float CE;
    private float CF;
    private float CG;
    private float Cp;
    private float Cw;
    private boolean Cx;
    private BitmapDescriptor Cz;
    private final int kg;

    public GroundOverlayOptions() {
        this.Cx = true;
        this.CE = 0.0f;
        this.CF = 0.5f;
        this.CG = 0.5f;
        this.kg = 1;
    }

    GroundOverlayOptions(int i, IBinder iBinder, LatLng latLng, float f, float f2, LatLngBounds latLngBounds, float f3, float f4, boolean z, float f5, float f6, float f7) {
        this.Cx = true;
        this.CE = 0.0f;
        this.CF = 0.5f;
        this.CG = 0.5f;
        this.kg = i;
        this.Cz = new BitmapDescriptor(b.a.E(iBinder));
        this.CA = latLng;
        this.CB = f;
        this.CC = f2;
        this.CD = latLngBounds;
        this.Cp = f3;
        this.Cw = f4;
        this.Cx = z;
        this.CE = f5;
        this.CF = f6;
        this.CG = f7;
    }

    private GroundOverlayOptions a(LatLng latLng, float f, float f2) {
        this.CA = latLng;
        this.CB = f;
        this.CC = f2;
        return this;
    }

    public GroundOverlayOptions anchor(float f, float f2) {
        this.CF = f;
        this.CG = f2;
        return this;
    }

    public GroundOverlayOptions bearing(float f) {
        this.Cp = ((f % 360.0f) + 360.0f) % 360.0f;
        return this;
    }

    public int describeContents() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public IBinder eF() {
        return this.Cz.el().asBinder();
    }

    public float getAnchorU() {
        return this.CF;
    }

    public float getAnchorV() {
        return this.CG;
    }

    public float getBearing() {
        return this.Cp;
    }

    public LatLngBounds getBounds() {
        return this.CD;
    }

    public float getHeight() {
        return this.CC;
    }

    public BitmapDescriptor getImage() {
        return this.Cz;
    }

    public LatLng getLocation() {
        return this.CA;
    }

    public float getTransparency() {
        return this.CE;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public float getWidth() {
        return this.CB;
    }

    public float getZIndex() {
        return this.Cw;
    }

    public GroundOverlayOptions image(BitmapDescriptor bitmapDescriptor) {
        this.Cz = bitmapDescriptor;
        return this;
    }

    public boolean isVisible() {
        return this.Cx;
    }

    public GroundOverlayOptions position(LatLng latLng, float f) {
        boolean z = true;
        eg.a(this.CD == null, "Position has already been set using positionFromBounds");
        eg.b(latLng != null, (Object) "Location must be specified");
        if (f < 0.0f) {
            z = false;
        }
        eg.b(z, (Object) "Width must be non-negative");
        return a(latLng, f, -1.0f);
    }

    public GroundOverlayOptions position(LatLng latLng, float f, float f2) {
        boolean z = true;
        eg.a(this.CD == null, "Position has already been set using positionFromBounds");
        eg.b(latLng != null, (Object) "Location must be specified");
        eg.b(f >= 0.0f, (Object) "Width must be non-negative");
        if (f2 < 0.0f) {
            z = false;
        }
        eg.b(z, (Object) "Height must be non-negative");
        return a(latLng, f, f2);
    }

    public GroundOverlayOptions positionFromBounds(LatLngBounds latLngBounds) {
        eg.a(this.CA == null, "Position has already been set using position: " + this.CA);
        this.CD = latLngBounds;
        return this;
    }

    public GroundOverlayOptions transparency(float f) {
        eg.b(f >= 0.0f && f <= 1.0f, (Object) "Transparency must be in the range [0..1]");
        this.CE = f;
        return this;
    }

    public GroundOverlayOptions visible(boolean z) {
        this.Cx = z;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (r.eD()) {
            c.a(this, parcel, i);
        } else {
            GroundOverlayOptionsCreator.a(this, parcel, i);
        }
    }

    public GroundOverlayOptions zIndex(float f) {
        this.Cw = f;
        return this;
    }
}
