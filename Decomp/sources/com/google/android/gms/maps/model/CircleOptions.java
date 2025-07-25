package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.r;

public final class CircleOptions implements SafeParcelable {
    public static final CircleOptionsCreator CREATOR = new CircleOptionsCreator();
    private LatLng Cr;
    private double Cs;
    private float Ct;
    private int Cu;
    private int Cv;
    private float Cw;
    private boolean Cx;
    private final int kg;

    public CircleOptions() {
        this.Cr = null;
        this.Cs = 0.0d;
        this.Ct = 10.0f;
        this.Cu = -16777216;
        this.Cv = 0;
        this.Cw = 0.0f;
        this.Cx = true;
        this.kg = 1;
    }

    CircleOptions(int i, LatLng latLng, double d, float f, int i2, int i3, float f2, boolean z) {
        this.Cr = null;
        this.Cs = 0.0d;
        this.Ct = 10.0f;
        this.Cu = -16777216;
        this.Cv = 0;
        this.Cw = 0.0f;
        this.Cx = true;
        this.kg = i;
        this.Cr = latLng;
        this.Cs = d;
        this.Ct = f;
        this.Cu = i2;
        this.Cv = i3;
        this.Cw = f2;
        this.Cx = z;
    }

    public CircleOptions center(LatLng latLng) {
        this.Cr = latLng;
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public CircleOptions fillColor(int i) {
        this.Cv = i;
        return this;
    }

    public LatLng getCenter() {
        return this.Cr;
    }

    public int getFillColor() {
        return this.Cv;
    }

    public double getRadius() {
        return this.Cs;
    }

    public int getStrokeColor() {
        return this.Cu;
    }

    public float getStrokeWidth() {
        return this.Ct;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public float getZIndex() {
        return this.Cw;
    }

    public boolean isVisible() {
        return this.Cx;
    }

    public CircleOptions radius(double d) {
        this.Cs = d;
        return this;
    }

    public CircleOptions strokeColor(int i) {
        this.Cu = i;
        return this;
    }

    public CircleOptions strokeWidth(float f) {
        this.Ct = f;
        return this;
    }

    public CircleOptions visible(boolean z) {
        this.Cx = z;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (r.eD()) {
            b.a(this, parcel, i);
        } else {
            CircleOptionsCreator.a(this, parcel, i);
        }
    }

    public CircleOptions zIndex(float f) {
        this.Cw = f;
        return this;
    }
}
