package com.google.android.gms.games.multiplayer.turnbased;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.d;

public final class TurnBasedMatchBuffer extends d<TurnBasedMatch> {
    public TurnBasedMatchBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    /* access modifiers changed from: protected */
    /* renamed from: getEntry */
    public TurnBasedMatch a(int i, int i2) {
        return new a(this.nE, i, i2);
    }

    /* access modifiers changed from: protected */
    public String getPrimaryDataMarkerColumn() {
        return "external_match_id";
    }
}
