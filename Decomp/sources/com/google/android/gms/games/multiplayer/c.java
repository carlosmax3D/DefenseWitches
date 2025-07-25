package com.google.android.gms.games.multiplayer;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.PlayerEntity;

public class c implements Parcelable.Creator<ParticipantEntity> {
    static void a(ParticipantEntity participantEntity, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, participantEntity.getParticipantId(), false);
        b.c(parcel, 1000, participantEntity.getVersionCode());
        b.a(parcel, 2, participantEntity.getDisplayName(), false);
        b.a(parcel, 3, (Parcelable) participantEntity.getIconImageUri(), i, false);
        b.a(parcel, 4, (Parcelable) participantEntity.getHiResImageUri(), i, false);
        b.c(parcel, 5, participantEntity.getStatus());
        b.a(parcel, 6, participantEntity.dy(), false);
        b.a(parcel, 7, participantEntity.isConnectedToRoom());
        b.a(parcel, 8, (Parcelable) participantEntity.getPlayer(), i, false);
        b.c(parcel, 9, participantEntity.getCapabilities());
        b.a(parcel, 10, (Parcelable) participantEntity.getResult(), i, false);
        b.D(parcel, o);
    }

    /* renamed from: aJ */
    public ParticipantEntity[] newArray(int i) {
        return new ParticipantEntity[i];
    }

    /* renamed from: ab */
    public ParticipantEntity createFromParcel(Parcel parcel) {
        int i = 0;
        ParticipantResult participantResult = null;
        int n = a.n(parcel);
        PlayerEntity playerEntity = null;
        boolean z = false;
        String str = null;
        int i2 = 0;
        Uri uri = null;
        Uri uri2 = null;
        String str2 = null;
        String str3 = null;
        int i3 = 0;
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
                    uri2 = (Uri) a.a(parcel, m, Uri.CREATOR);
                    break;
                case 4:
                    uri = (Uri) a.a(parcel, m, Uri.CREATOR);
                    break;
                case 5:
                    i2 = a.g(parcel, m);
                    break;
                case 6:
                    str = a.m(parcel, m);
                    break;
                case 7:
                    z = a.c(parcel, m);
                    break;
                case 8:
                    playerEntity = (PlayerEntity) a.a(parcel, m, PlayerEntity.CREATOR);
                    break;
                case 9:
                    i = a.g(parcel, m);
                    break;
                case 10:
                    participantResult = (ParticipantResult) a.a(parcel, m, ParticipantResult.CREATOR);
                    break;
                case 1000:
                    i3 = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ParticipantEntity(i3, str3, str2, uri2, uri, i2, str, z, playerEntity, i, participantResult);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
