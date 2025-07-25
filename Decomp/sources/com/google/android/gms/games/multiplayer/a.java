package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.GameEntity;
import java.util.ArrayList;

public class a implements Parcelable.Creator<InvitationEntity> {
    static void a(InvitationEntity invitationEntity, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, (Parcelable) invitationEntity.getGame(), i, false);
        b.c(parcel, 1000, invitationEntity.getVersionCode());
        b.a(parcel, 2, invitationEntity.getInvitationId(), false);
        b.a(parcel, 3, invitationEntity.getCreationTimestamp());
        b.c(parcel, 4, invitationEntity.getInvitationType());
        b.a(parcel, 5, (Parcelable) invitationEntity.getInviter(), i, false);
        b.b(parcel, 6, invitationEntity.getParticipants(), false);
        b.c(parcel, 7, invitationEntity.getVariant());
        b.c(parcel, 8, invitationEntity.getAvailableAutoMatchSlots());
        b.D(parcel, o);
    }

    /* renamed from: aI */
    public InvitationEntity[] newArray(int i) {
        return new InvitationEntity[i];
    }

    /* renamed from: aa */
    public InvitationEntity createFromParcel(Parcel parcel) {
        ArrayList<ParticipantEntity> arrayList = null;
        int i = 0;
        int n = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
        long j = 0;
        int i2 = 0;
        ParticipantEntity participantEntity = null;
        int i3 = 0;
        String str = null;
        GameEntity gameEntity = null;
        int i4 = 0;
        while (parcel.dataPosition() < n) {
            int m = com.google.android.gms.common.internal.safeparcel.a.m(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.M(m)) {
                case 1:
                    gameEntity = (GameEntity) com.google.android.gms.common.internal.safeparcel.a.a(parcel, m, GameEntity.CREATOR);
                    break;
                case 2:
                    str = com.google.android.gms.common.internal.safeparcel.a.m(parcel, m);
                    break;
                case 3:
                    j = com.google.android.gms.common.internal.safeparcel.a.h(parcel, m);
                    break;
                case 4:
                    i3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, m);
                    break;
                case 5:
                    participantEntity = (ParticipantEntity) com.google.android.gms.common.internal.safeparcel.a.a(parcel, m, ParticipantEntity.CREATOR);
                    break;
                case 6:
                    arrayList = com.google.android.gms.common.internal.safeparcel.a.c(parcel, m, ParticipantEntity.CREATOR);
                    break;
                case 7:
                    i2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, m);
                    break;
                case 8:
                    i = com.google.android.gms.common.internal.safeparcel.a.g(parcel, m);
                    break;
                case 1000:
                    i4 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, m);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new InvitationEntity(i4, gameEntity, str, j, i3, participantEntity, arrayList, i2, i);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
