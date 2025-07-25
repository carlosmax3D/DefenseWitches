package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class TileCreator implements Parcelable.Creator<Tile> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(Tile tile, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, tile.getVersionCode());
        b.c(parcel, 2, tile.width);
        b.c(parcel, 3, tile.height);
        b.a(parcel, 4, tile.data, false);
        b.D(parcel, o);
    }

    public Tile createFromParcel(Parcel parcel) {
        int i = 0;
        int n = a.n(parcel);
        byte[] bArr = null;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i3 = a.g(parcel, m);
                    break;
                case 2:
                    i2 = a.g(parcel, m);
                    break;
                case 3:
                    i = a.g(parcel, m);
                    break;
                case 4:
                    bArr = a.p(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new Tile(i3, i2, i, bArr);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public Tile[] newArray(int i) {
        return new Tile[i];
    }
}
