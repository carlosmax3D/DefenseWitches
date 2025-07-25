package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class c implements Parcelable.Creator<PlayerEntity> {
    static void a(PlayerEntity playerEntity, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, playerEntity.getPlayerId(), false);
        b.c(parcel, 1000, playerEntity.getVersionCode());
        b.a(parcel, 2, playerEntity.getDisplayName(), false);
        b.a(parcel, 3, (Parcelable) playerEntity.getIconImageUri(), i, false);
        b.a(parcel, 4, (Parcelable) playerEntity.getHiResImageUri(), i, false);
        b.a(parcel, 5, playerEntity.getRetrievedTimestamp());
        b.c(parcel, 6, playerEntity.db());
        b.a(parcel, 7, playerEntity.getLastPlayedWithTimestamp());
        b.D(parcel, o);
    }

    /* renamed from: Z */
    public PlayerEntity createFromParcel(Parcel parcel) {
        long j = 0;
        int i = 0;
        Uri uri = null;
        int n = a.n(parcel);
        long j2 = 0;
        Uri uri2 = null;
        String str = null;
        String str2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    str2 = a.m(parcel, m);
                    break;
                case 2:
                    str = a.m(parcel, m);
                    break;
                case 3:
                    uri2 = (Uri) a.a(parcel, m, Uri.CREATOR);
                    break;
                case 4:
                    uri = (Uri) a.a(parcel, m, Uri.CREATOR);
                    break;
                case 5:
                    j2 = a.h(parcel, m);
                    break;
                case 6:
                    i = a.g(parcel, m);
                    break;
                case 7:
                    j = a.h(parcel, m);
                    break;
                case 1000:
                    i2 = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new PlayerEntity(i2, str2, str, uri2, uri, j2, i, j);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: aA */
    public PlayerEntity[] newArray(int i) {
        return new PlayerEntity[i];
    }
}
