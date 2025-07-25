package com.google.android.gms.maps.model;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ee;
import com.google.android.gms.internal.eg;
import com.google.android.gms.maps.internal.r;

public final class CameraPosition implements SafeParcelable {
    public static final CameraPositionCreator CREATOR = new CameraPositionCreator();
    public final float bearing;
    private final int kg;
    public final LatLng target;
    public final float tilt;
    public final float zoom;

    public static final class Builder {
        private LatLng Cm;
        private float Cn;
        private float Co;
        private float Cp;

        public Builder() {
        }

        public Builder(CameraPosition cameraPosition) {
            this.Cm = cameraPosition.target;
            this.Cn = cameraPosition.zoom;
            this.Co = cameraPosition.tilt;
            this.Cp = cameraPosition.bearing;
        }

        public Builder bearing(float f) {
            this.Cp = f;
            return this;
        }

        public CameraPosition build() {
            return new CameraPosition(this.Cm, this.Cn, this.Co, this.Cp);
        }

        public Builder target(LatLng latLng) {
            this.Cm = latLng;
            return this;
        }

        public Builder tilt(float f) {
            this.Co = f;
            return this;
        }

        public Builder zoom(float f) {
            this.Cn = f;
            return this;
        }
    }

    CameraPosition(int i, LatLng latLng, float f, float f2, float f3) {
        eg.b(latLng, (Object) "null camera target");
        eg.b(0.0f <= f2 && f2 <= 90.0f, (Object) "Tilt needs to be between 0 and 90 inclusive");
        this.kg = i;
        this.target = latLng;
        this.zoom = f;
        this.tilt = f2 + 0.0f;
        this.bearing = (((double) f3) <= 0.0d ? (f3 % 360.0f) + 360.0f : f3) % 360.0f;
    }

    public CameraPosition(LatLng latLng, float f, float f2, float f3) {
        this(1, latLng, f, f2, f3);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(CameraPosition cameraPosition) {
        return new Builder(cameraPosition);
    }

    public static CameraPosition createFromAttributes(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return null;
        }
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, R.styleable.MapAttrs);
        LatLng latLng = new LatLng((double) (obtainAttributes.hasValue(2) ? obtainAttributes.getFloat(2, 0.0f) : 0.0f), (double) (obtainAttributes.hasValue(3) ? obtainAttributes.getFloat(3, 0.0f) : 0.0f));
        Builder builder = builder();
        builder.target(latLng);
        if (obtainAttributes.hasValue(5)) {
            builder.zoom(obtainAttributes.getFloat(5, 0.0f));
        }
        if (obtainAttributes.hasValue(1)) {
            builder.bearing(obtainAttributes.getFloat(1, 0.0f));
        }
        if (obtainAttributes.hasValue(4)) {
            builder.tilt(obtainAttributes.getFloat(4, 0.0f));
        }
        return builder.build();
    }

    public static final CameraPosition fromLatLngZoom(LatLng latLng, float f) {
        return new CameraPosition(latLng, f, 0.0f, 0.0f);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CameraPosition)) {
            return false;
        }
        CameraPosition cameraPosition = (CameraPosition) obj;
        return this.target.equals(cameraPosition.target) && Float.floatToIntBits(this.zoom) == Float.floatToIntBits(cameraPosition.zoom) && Float.floatToIntBits(this.tilt) == Float.floatToIntBits(cameraPosition.tilt) && Float.floatToIntBits(this.bearing) == Float.floatToIntBits(cameraPosition.bearing);
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public int hashCode() {
        return ee.hashCode(this.target, Float.valueOf(this.zoom), Float.valueOf(this.tilt), Float.valueOf(this.bearing));
    }

    public String toString() {
        return ee.e(this).a("target", this.target).a("zoom", Float.valueOf(this.zoom)).a("tilt", Float.valueOf(this.tilt)).a("bearing", Float.valueOf(this.bearing)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (r.eD()) {
            a.a(this, parcel, i);
        } else {
            CameraPositionCreator.a(this, parcel, i);
        }
    }
}
