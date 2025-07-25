package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class ParticipantResultCreator implements Parcelable.Creator<ParticipantResult> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(ParticipantResult participantResult, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, participantResult.getParticipantId(), false);
        b.c(parcel, 1000, participantResult.getVersionCode());
        b.c(parcel, 2, participantResult.getResult());
        b.c(parcel, 3, participantResult.getPlacing());
        b.D(parcel, o);
    }

    public ParticipantResult createFromParcel(Parcel parcel) {
        int i = 0;
        int n = a.n(parcel);
        String str = null;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    str = a.m(parcel, m);
                    break;
                case 2:
                    i2 = a.g(parcel, m);
                    break;
                case 3:
                    i = a.g(parcel, m);
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
            return new ParticipantResult(i3, str, i2, i);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public ParticipantResult[] newArray(int i) {
        return new ParticipantResult[i];
    }
}
