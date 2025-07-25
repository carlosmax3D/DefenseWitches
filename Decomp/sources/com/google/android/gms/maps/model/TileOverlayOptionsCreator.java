package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class TileOverlayOptionsCreator implements Parcelable.Creator<TileOverlayOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(TileOverlayOptions tileOverlayOptions, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, tileOverlayOptions.getVersionCode());
        b.a(parcel, 2, tileOverlayOptions.eI(), false);
        b.a(parcel, 3, tileOverlayOptions.isVisible());
        b.a(parcel, 4, tileOverlayOptions.getZIndex());
        b.a(parcel, 5, tileOverlayOptions.getFadeIn());
        b.D(parcel, o);
    }

    public TileOverlayOptions createFromParcel(Parcel parcel) {
        boolean z = false;
        int n = a.n(parcel);
        IBinder iBinder = null;
        float f = 0.0f;
        boolean z2 = true;
        int i = 0;
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
                    z = a.c(parcel, m);
                    break;
                case 4:
                    f = a.j(parcel, m);
                    break;
                case 5:
                    z2 = a.c(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new TileOverlayOptions(i, iBinder, z, f, z2);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public TileOverlayOptions[] newArray(int i) {
        return new TileOverlayOptions[i];
    }
}
