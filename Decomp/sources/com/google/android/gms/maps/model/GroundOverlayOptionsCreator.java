package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class GroundOverlayOptionsCreator implements Parcelable.Creator<GroundOverlayOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(GroundOverlayOptions groundOverlayOptions, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, groundOverlayOptions.getVersionCode());
        b.a(parcel, 2, groundOverlayOptions.eF(), false);
        b.a(parcel, 3, (Parcelable) groundOverlayOptions.getLocation(), i, false);
        b.a(parcel, 4, groundOverlayOptions.getWidth());
        b.a(parcel, 5, groundOverlayOptions.getHeight());
        b.a(parcel, 6, (Parcelable) groundOverlayOptions.getBounds(), i, false);
        b.a(parcel, 7, groundOverlayOptions.getBearing());
        b.a(parcel, 8, groundOverlayOptions.getZIndex());
        b.a(parcel, 9, groundOverlayOptions.isVisible());
        b.a(parcel, 10, groundOverlayOptions.getTransparency());
        b.a(parcel, 11, groundOverlayOptions.getAnchorU());
        b.a(parcel, 12, groundOverlayOptions.getAnchorV());
        b.D(parcel, o);
    }

    public GroundOverlayOptions createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        IBinder iBinder = null;
        LatLng latLng = null;
        float f = 0.0f;
        float f2 = 0.0f;
        LatLngBounds latLngBounds = null;
        float f3 = 0.0f;
        float f4 = 0.0f;
        boolean z = false;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    iBinder = a.n(parcel, m);
                    break;
                case 3:
                    latLng = (LatLng) a.a(parcel, m, LatLng.CREATOR);
                    break;
                case 4:
                    f = a.j(parcel, m);
                    break;
                case 5:
                    f2 = a.j(parcel, m);
                    break;
                case 6:
                    latLngBounds = (LatLngBounds) a.a(parcel, m, LatLngBounds.CREATOR);
                    break;
                case 7:
                    f3 = a.j(parcel, m);
                    break;
                case 8:
                    f4 = a.j(parcel, m);
                    break;
                case 9:
                    z = a.c(parcel, m);
                    break;
                case 10:
                    f5 = a.j(parcel, m);
                    break;
                case 11:
                    f6 = a.j(parcel, m);
                    break;
                case 12:
                    f7 = a.j(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new GroundOverlayOptions(i, iBinder, latLng, f, f2, latLngBounds, f3, f4, z, f5, f6, f7);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public GroundOverlayOptions[] newArray(int i) {
        return new GroundOverlayOptions[i];
    }
}
