package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ee;
import com.google.android.gms.internal.eg;
import com.google.android.gms.maps.internal.r;

public final class LatLngBounds implements SafeParcelable {
    public static final LatLngBoundsCreator CREATOR = new LatLngBoundsCreator();
    private final int kg;
    public final LatLng northeast;
    public final LatLng southwest;

    public static final class Builder {
        private double CH = Double.POSITIVE_INFINITY;
        private double CI = Double.NEGATIVE_INFINITY;
        private double CJ = Double.NaN;
        private double CK = Double.NaN;

        private boolean d(double d) {
            boolean z = false;
            if (this.CJ <= this.CK) {
                return this.CJ <= d && d <= this.CK;
            }
            if (this.CJ <= d || d <= this.CK) {
                z = true;
            }
            return z;
        }

        public LatLngBounds build() {
            eg.a(!Double.isNaN(this.CJ), "no included points");
            return new LatLngBounds(new LatLng(this.CH, this.CJ), new LatLng(this.CI, this.CK));
        }

        public Builder include(LatLng latLng) {
            this.CH = Math.min(this.CH, latLng.latitude);
            this.CI = Math.max(this.CI, latLng.latitude);
            double d = latLng.longitude;
            if (Double.isNaN(this.CJ)) {
                this.CJ = d;
                this.CK = d;
            } else if (!d(d)) {
                if (LatLngBounds.b(this.CJ, d) < LatLngBounds.c(this.CK, d)) {
                    this.CJ = d;
                } else {
                    this.CK = d;
                }
            }
            return this;
        }
    }

    LatLngBounds(int i, LatLng latLng, LatLng latLng2) {
        eg.b(latLng, (Object) "null southwest");
        eg.b(latLng2, (Object) "null northeast");
        eg.a(latLng2.latitude >= latLng.latitude, "southern latitude exceeds northern latitude (%s > %s)", Double.valueOf(latLng.latitude), Double.valueOf(latLng2.latitude));
        this.kg = i;
        this.southwest = latLng;
        this.northeast = latLng2;
    }

    public LatLngBounds(LatLng latLng, LatLng latLng2) {
        this(1, latLng, latLng2);
    }

    /* access modifiers changed from: private */
    public static double b(double d, double d2) {
        return ((d - d2) + 360.0d) % 360.0d;
    }

    public static Builder builder() {
        return new Builder();
    }

    /* access modifiers changed from: private */
    public static double c(double d, double d2) {
        return ((d2 - d) + 360.0d) % 360.0d;
    }

    private boolean c(double d) {
        return this.southwest.latitude <= d && d <= this.northeast.latitude;
    }

    private boolean d(double d) {
        boolean z = false;
        if (this.southwest.longitude <= this.northeast.longitude) {
            return this.southwest.longitude <= d && d <= this.northeast.longitude;
        }
        if (this.southwest.longitude <= d || d <= this.northeast.longitude) {
            z = true;
        }
        return z;
    }

    public boolean contains(LatLng latLng) {
        return c(latLng.latitude) && d(latLng.longitude);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LatLngBounds)) {
            return false;
        }
        LatLngBounds latLngBounds = (LatLngBounds) obj;
        return this.southwest.equals(latLngBounds.southwest) && this.northeast.equals(latLngBounds.northeast);
    }

    public LatLng getCenter() {
        double d = (this.southwest.latitude + this.northeast.latitude) / 2.0d;
        double d2 = this.northeast.longitude;
        double d3 = this.southwest.longitude;
        return new LatLng(d, d3 <= d2 ? (d2 + d3) / 2.0d : ((d2 + 360.0d) + d3) / 2.0d);
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public int hashCode() {
        return ee.hashCode(this.southwest, this.northeast);
    }

    public LatLngBounds including(LatLng latLng) {
        double d;
        double min = Math.min(this.southwest.latitude, latLng.latitude);
        double max = Math.max(this.northeast.latitude, latLng.latitude);
        double d2 = this.northeast.longitude;
        double d3 = this.southwest.longitude;
        double d4 = latLng.longitude;
        if (d(d4)) {
            d4 = d3;
            d = d2;
        } else if (b(d3, d4) < c(d2, d4)) {
            d = d2;
        } else {
            double d5 = d3;
            d = d4;
            d4 = d5;
        }
        return new LatLngBounds(new LatLng(min, d4), new LatLng(max, d));
    }

    public String toString() {
        return ee.e(this).a("southwest", this.southwest).a("northeast", this.northeast).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (r.eD()) {
            d.a(this, parcel, i);
        } else {
            LatLngBoundsCreator.a(this, parcel, i);
        }
    }
}
