package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.TimeUtils;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;

public class TurnBasedMatchEntityCreator implements Parcelable.Creator<TurnBasedMatchEntity> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(TurnBasedMatchEntity turnBasedMatchEntity, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, (Parcelable) turnBasedMatchEntity.getGame(), i, false);
        b.a(parcel, 2, turnBasedMatchEntity.getMatchId(), false);
        b.a(parcel, 3, turnBasedMatchEntity.getCreatorId(), false);
        b.a(parcel, 4, turnBasedMatchEntity.getCreationTimestamp());
        b.a(parcel, 5, turnBasedMatchEntity.getLastUpdaterId(), false);
        b.a(parcel, 6, turnBasedMatchEntity.getLastUpdatedTimestamp());
        b.a(parcel, 7, turnBasedMatchEntity.getPendingParticipantId(), false);
        b.c(parcel, 8, turnBasedMatchEntity.getStatus());
        b.c(parcel, 10, turnBasedMatchEntity.getVariant());
        b.c(parcel, 11, turnBasedMatchEntity.getVersion());
        b.a(parcel, 12, turnBasedMatchEntity.getData(), false);
        b.b(parcel, 13, turnBasedMatchEntity.getParticipants(), false);
        b.a(parcel, 14, turnBasedMatchEntity.getRematchId(), false);
        b.a(parcel, 15, turnBasedMatchEntity.getPreviousMatchData(), false);
        b.a(parcel, 17, turnBasedMatchEntity.getAutoMatchCriteria(), false);
        b.c(parcel, 16, turnBasedMatchEntity.getMatchNumber());
        b.c(parcel, 1000, turnBasedMatchEntity.getVersionCode());
        b.a(parcel, 19, turnBasedMatchEntity.isLocallyModified());
        b.c(parcel, 18, turnBasedMatchEntity.getTurnStatus());
        b.D(parcel, o);
    }

    public TurnBasedMatchEntity createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        GameEntity gameEntity = null;
        String str = null;
        String str2 = null;
        long j = 0;
        String str3 = null;
        long j2 = 0;
        String str4 = null;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        byte[] bArr = null;
        ArrayList<ParticipantEntity> arrayList = null;
        String str5 = null;
        byte[] bArr2 = null;
        int i5 = 0;
        Bundle bundle = null;
        int i6 = 0;
        boolean z = false;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    gameEntity = (GameEntity) a.a(parcel, m, GameEntity.CREATOR);
                    break;
                case 2:
                    str = a.m(parcel, m);
                    break;
                case 3:
                    str2 = a.m(parcel, m);
                    break;
                case 4:
                    j = a.h(parcel, m);
                    break;
                case 5:
                    str3 = a.m(parcel, m);
                    break;
                case 6:
                    j2 = a.h(parcel, m);
                    break;
                case 7:
                    str4 = a.m(parcel, m);
                    break;
                case 8:
                    i2 = a.g(parcel, m);
                    break;
                case 10:
                    i3 = a.g(parcel, m);
                    break;
                case 11:
                    i4 = a.g(parcel, m);
                    break;
                case 12:
                    bArr = a.p(parcel, m);
                    break;
                case 13:
                    arrayList = a.c(parcel, m, ParticipantEntity.CREATOR);
                    break;
                case 14:
                    str5 = a.m(parcel, m);
                    break;
                case 15:
                    bArr2 = a.p(parcel, m);
                    break;
                case 16:
                    i5 = a.g(parcel, m);
                    break;
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS:
                    bundle = a.o(parcel, m);
                    break;
                case 18:
                    i6 = a.g(parcel, m);
                    break;
                case TimeUtils.HUNDRED_DAY_FIELD_LEN:
                    z = a.c(parcel, m);
                    break;
                case 1000:
                    i = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new TurnBasedMatchEntity(i, gameEntity, str, str2, j, str3, j2, str4, i2, i3, i4, bArr, arrayList, str5, bArr2, i5, bundle, i6, z);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public TurnBasedMatchEntity[] newArray(int i) {
        return new TurnBasedMatchEntity[i];
    }
}
