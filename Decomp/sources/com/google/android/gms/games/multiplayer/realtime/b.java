package com.google.android.gms.games.multiplayer.realtime;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;

public class b implements Parcelable.Creator<RoomEntity> {
    static void a(RoomEntity roomEntity, Parcel parcel, int i) {
        int o = com.google.android.gms.common.internal.safeparcel.b.o(parcel);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 1, roomEntity.getRoomId(), false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, roomEntity.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, roomEntity.getCreatorId(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, roomEntity.getCreationTimestamp());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 4, roomEntity.getStatus());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, roomEntity.getDescription(), false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 6, roomEntity.getVariant());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, roomEntity.getAutoMatchCriteria(), false);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 8, roomEntity.getParticipants(), false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 9, roomEntity.getAutoMatchWaitEstimateSeconds());
        com.google.android.gms.common.internal.safeparcel.b.D(parcel, o);
    }

    /* renamed from: aL */
    public RoomEntity[] newArray(int i) {
        return new RoomEntity[i];
    }

    /* renamed from: ad */
    public RoomEntity createFromParcel(Parcel parcel) {
        int i = 0;
        ArrayList<ParticipantEntity> arrayList = null;
        int n = a.n(parcel);
        long j = 0;
        Bundle bundle = null;
        int i2 = 0;
        String str = null;
        int i3 = 0;
        String str2 = null;
        String str3 = null;
        int i4 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    str3 = a.m(parcel, m);
                    break;
                case 2:
                    str2 = a.m(parcel, m);
                    break;
                case 3:
                    j = a.h(parcel, m);
                    break;
                case 4:
                    i3 = a.g(parcel, m);
                    break;
                case 5:
                    str = a.m(parcel, m);
                    break;
                case 6:
                    i2 = a.g(parcel, m);
                    break;
                case 7:
                    bundle = a.o(parcel, m);
                    break;
                case 8:
                    arrayList = a.c(parcel, m, ParticipantEntity.CREATOR);
                    break;
                case 9:
                    i = a.g(parcel, m);
                    break;
                case 1000:
                    i4 = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new RoomEntity(i4, str3, str2, j, i3, str, i2, bundle, arrayList, i);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
