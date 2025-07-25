package com.google.android.gms.games.leaderboard;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.d;

public final class LeaderboardBuffer extends d<Leaderboard> {
    public LeaderboardBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    /* access modifiers changed from: protected */
    /* renamed from: getEntry */
    public Leaderboard a(int i, int i2) {
        return new b(this.nE, i, i2);
    }

    /* access modifiers changed from: protected */
    public String getPrimaryDataMarkerColumn() {
        return "external_leaderboard_id";
    }
}
