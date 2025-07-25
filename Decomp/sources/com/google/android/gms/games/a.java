package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class a implements Parcelable.Creator<GameEntity> {
    static void a(GameEntity gameEntity, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, gameEntity.getApplicationId(), false);
        b.a(parcel, 2, gameEntity.getDisplayName(), false);
        b.a(parcel, 3, gameEntity.getPrimaryCategory(), false);
        b.a(parcel, 4, gameEntity.getSecondaryCategory(), false);
        b.a(parcel, 5, gameEntity.getDescription(), false);
        b.a(parcel, 6, gameEntity.getDeveloperName(), false);
        b.a(parcel, 7, (Parcelable) gameEntity.getIconImageUri(), i, false);
        b.a(parcel, 8, (Parcelable) gameEntity.getHiResImageUri(), i, false);
        b.a(parcel, 9, (Parcelable) gameEntity.getFeaturedImageUri(), i, false);
        b.a(parcel, 10, gameEntity.isPlayEnabledGame());
        b.a(parcel, 11, gameEntity.isInstanceInstalled());
        b.a(parcel, 12, gameEntity.getInstancePackageName(), false);
        b.c(parcel, 13, gameEntity.getGameplayAclStatus());
        b.c(parcel, 14, gameEntity.getAchievementTotalCount());
        b.c(parcel, 15, gameEntity.getLeaderboardCount());
        b.a(parcel, 17, gameEntity.isTurnBasedMultiplayerEnabled());
        b.a(parcel, 16, gameEntity.isRealTimeMultiplayerEnabled());
        b.c(parcel, 1000, gameEntity.getVersionCode());
        b.D(parcel, o);
    }

    /* renamed from: Y */
    public GameEntity createFromParcel(Parcel parcel) {
        int n = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        Uri uri = null;
        Uri uri2 = null;
        Uri uri3 = null;
        boolean z = false;
        boolean z2 = false;
        String str7 = null;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        boolean z3 = false;
        boolean z4 = false;
        while (parcel.dataPosition() < n) {
            int m = com.google.android.gms.common.internal.safeparcel.a.m(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.M(m)) {
                case 1:
                    str = com.google.android.gms.common.internal.safeparcel.a.m(parcel, m);
                    break;
                case 2:
                    str2 = com.google.android.gms.common.internal.safeparcel.a.m(parcel, m);
                    break;
                case 3:
                    str3 = com.google.android.gms.common.internal.safeparcel.a.m(parcel, m);
                    break;
                case 4:
                    str4 = com.google.android.gms.common.internal.safeparcel.a.m(parcel, m);
                    break;
                case 5:
                    str5 = com.google.android.gms.common.internal.safeparcel.a.m(parcel, m);
                    break;
                case 6:
                    str6 = com.google.android.gms.common.internal.safeparcel.a.m(parcel, m);
                    break;
                case 7:
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, m, Uri.CREATOR);
                    break;
                case 8:
                    uri2 = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, m, Uri.CREATOR);
                    break;
                case 9:
                    uri3 = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, m, Uri.CREATOR);
                    break;
                case 10:
                    z = com.google.android.gms.common.internal.safeparcel.a.c(parcel, m);
                    break;
                case 11:
                    z2 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, m);
                    break;
                case 12:
                    str7 = com.google.android.gms.common.internal.safeparcel.a.m(parcel, m);
                    break;
                case 13:
                    i2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, m);
                    break;
                case 14:
                    i3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, m);
                    break;
                case 15:
                    i4 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, m);
                    break;
                case 16:
                    z3 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, m);
                    break;
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS:
                    z4 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, m);
                    break;
                case 1000:
                    i = com.google.android.gms.common.internal.safeparcel.a.g(parcel, m);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new GameEntity(i, str, str2, str3, str4, str5, str6, uri, uri2, uri3, z, z2, str7, i2, i3, i4, z3, z4);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: az */
    public GameEntity[] newArray(int i) {
        return new GameEntity[i];
    }
}
