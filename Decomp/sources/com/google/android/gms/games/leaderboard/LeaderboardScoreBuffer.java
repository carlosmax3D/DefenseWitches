package com.google.android.gms.games.leaderboard;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;

public final class LeaderboardScoreBuffer extends DataBuffer<LeaderboardScore> {
    private final c vI;

    public LeaderboardScoreBuffer(DataHolder dataHolder) {
        super(dataHolder);
        this.vI = new c(dataHolder.getMetadata());
    }

    public c dq() {
        return this.vI;
    }

    public LeaderboardScore get(int i) {
        return new e(this.nE, i);
    }
}
