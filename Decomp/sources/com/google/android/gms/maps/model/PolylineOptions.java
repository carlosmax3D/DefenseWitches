package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.r;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PolylineOptions implements SafeParcelable {
    public static final PolylineOptionsCreator CREATOR = new PolylineOptionsCreator();
    private float CB;
    private final List<LatLng> CV;
    private boolean CX;
    private float Cw;
    private boolean Cx;
    private final int kg;
    private int mP;

    public PolylineOptions() {
        this.CB = 10.0f;
        this.mP = -16777216;
        this.Cw = 0.0f;
        this.Cx = true;
        this.CX = false;
        this.kg = 1;
        this.CV = new ArrayList();
    }

    PolylineOptions(int i, List list, float f, int i2, float f2, boolean z, boolean z2) {
        this.CB = 10.0f;
        this.mP = -16777216;
        this.Cw = 0.0f;
        this.Cx = true;
        this.CX = false;
        this.kg = i;
        this.CV = list;
        this.CB = f;
        this.mP = i2;
        this.Cw = f2;
        this.Cx = z;
        this.CX = z2;
    }

    public PolylineOptions add(LatLng latLng) {
        this.CV.add(latLng);
        return this;
    }

    public PolylineOptions add(LatLng... latLngArr) {
        this.CV.addAll(Arrays.asList(latLngArr));
        return this;
    }

    public PolylineOptions addAll(Iterable<LatLng> iterable) {
        for (LatLng add : iterable) {
            this.CV.add(add);
        }
        return this;
    }

    public PolylineOptions color(int i) {
        this.mP = i;
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public PolylineOptions geodesic(boolean z) {
        this.CX = z;
        return this;
    }

    public int getColor() {
        return this.mP;
    }

    public List<LatLng> getPoints() {
        return this.CV;
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

    public boolean isGeodesic() {
        return this.CX;
    }

    public boolean isVisible() {
        return this.Cx;
    }

    public PolylineOptions visible(boolean z) {
        this.Cx = z;
        return this;
    }

    public PolylineOptions width(float f) {
        this.CB = f;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (r.eD()) {
            h.a(this, parcel, i);
        } else {
            PolylineOptionsCreator.a(this, parcel, i);
        }
    }

    public PolylineOptions zIndex(float f) {
        this.Cw = f;
        return this;
    }
}
