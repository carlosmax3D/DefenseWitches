package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.r;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PolygonOptions implements SafeParcelable {
    public static final PolygonOptionsCreator CREATOR = new PolygonOptionsCreator();
    private final List<LatLng> CV;
    private final List<List<LatLng>> CW;
    private boolean CX;
    private float Ct;
    private int Cu;
    private int Cv;
    private float Cw;
    private boolean Cx;
    private final int kg;

    public PolygonOptions() {
        this.Ct = 10.0f;
        this.Cu = -16777216;
        this.Cv = 0;
        this.Cw = 0.0f;
        this.Cx = true;
        this.CX = false;
        this.kg = 1;
        this.CV = new ArrayList();
        this.CW = new ArrayList();
    }

    PolygonOptions(int i, List<LatLng> list, List list2, float f, int i2, int i3, float f2, boolean z, boolean z2) {
        this.Ct = 10.0f;
        this.Cu = -16777216;
        this.Cv = 0;
        this.Cw = 0.0f;
        this.Cx = true;
        this.CX = false;
        this.kg = i;
        this.CV = list;
        this.CW = list2;
        this.Ct = f;
        this.Cu = i2;
        this.Cv = i3;
        this.Cw = f2;
        this.Cx = z;
        this.CX = z2;
    }

    public PolygonOptions add(LatLng latLng) {
        this.CV.add(latLng);
        return this;
    }

    public PolygonOptions add(LatLng... latLngArr) {
        this.CV.addAll(Arrays.asList(latLngArr));
        return this;
    }

    public PolygonOptions addAll(Iterable<LatLng> iterable) {
        for (LatLng add : iterable) {
            this.CV.add(add);
        }
        return this;
    }

    public PolygonOptions addHole(Iterable<LatLng> iterable) {
        ArrayList arrayList = new ArrayList();
        for (LatLng add : iterable) {
            arrayList.add(add);
        }
        this.CW.add(arrayList);
        return this;
    }

    public int describeContents() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public List eH() {
        return this.CW;
    }

    public PolygonOptions fillColor(int i) {
        this.Cv = i;
        return this;
    }

    public PolygonOptions geodesic(boolean z) {
        this.CX = z;
        return this;
    }

    public int getFillColor() {
        return this.Cv;
    }

    public List<List<LatLng>> getHoles() {
        return this.CW;
    }

    public List<LatLng> getPoints() {
        return this.CV;
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

    public boolean isGeodesic() {
        return this.CX;
    }

    public boolean isVisible() {
        return this.Cx;
    }

    public PolygonOptions strokeColor(int i) {
        this.Cu = i;
        return this;
    }

    public PolygonOptions strokeWidth(float f) {
        this.Ct = f;
        return this;
    }

    public PolygonOptions visible(boolean z) {
        this.Cx = z;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (r.eD()) {
            g.a(this, parcel, i);
        } else {
            PolygonOptionsCreator.a(this, parcel, i);
        }
    }

    public PolygonOptions zIndex(float f) {
        this.Cw = f;
        return this;
    }
}
