package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.maps.model.CameraPosition;

public class GoogleMapOptionsCreator implements Parcelable.Creator<GoogleMapOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(GoogleMapOptions googleMapOptions, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, googleMapOptions.getVersionCode());
        b.a(parcel, 2, googleMapOptions.eo());
        b.a(parcel, 3, googleMapOptions.ep());
        b.c(parcel, 4, googleMapOptions.getMapType());
        b.a(parcel, 5, (Parcelable) googleMapOptions.getCamera(), i, false);
        b.a(parcel, 6, googleMapOptions.eq());
        b.a(parcel, 7, googleMapOptions.er());
        b.a(parcel, 8, googleMapOptions.es());
        b.a(parcel, 9, googleMapOptions.et());
        b.a(parcel, 10, googleMapOptions.eu());
        b.a(parcel, 11, googleMapOptions.ev());
        b.D(parcel, o);
    }

    public GoogleMapOptions createFromParcel(Parcel parcel) {
        byte b = 0;
        int n = a.n(parcel);
        CameraPosition cameraPosition = null;
        byte b2 = 0;
        byte b3 = 0;
        byte b4 = 0;
        byte b5 = 0;
        byte b6 = 0;
        int i = 0;
        byte b7 = 0;
        byte b8 = 0;
        int i2 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i2 = a.g(parcel, m);
                    break;
                case 2:
                    b8 = a.e(parcel, m);
                    break;
                case 3:
                    b7 = a.e(parcel, m);
                    break;
                case 4:
                    i = a.g(parcel, m);
                    break;
                case 5:
                    cameraPosition = (CameraPosition) a.a(parcel, m, CameraPosition.CREATOR);
                    break;
                case 6:
                    b6 = a.e(parcel, m);
                    break;
                case 7:
                    b5 = a.e(parcel, m);
                    break;
                case 8:
                    b4 = a.e(parcel, m);
                    break;
                case 9:
                    b3 = a.e(parcel, m);
                    break;
                case 10:
                    b2 = a.e(parcel, m);
                    break;
                case 11:
                    b = a.e(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new GoogleMapOptions(i2, b8, b7, i, cameraPosition, b6, b5, b4, b3, b2, b);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public GoogleMapOptions[] newArray(int i) {
        return new GoogleMapOptions[i];
    }
}
