package com.google.android.gms.games.multiplayer.realtime;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.d;

public final class a extends d<Room> {
    public a(DataHolder dataHolder) {
        super(dataHolder);
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public Room a(int i, int i2) {
        return new c(this.nE, i, i2);
    }

    /* access modifiers changed from: protected */
    public String getPrimaryDataMarkerColumn() {
        return "external_match_id";
    }
}
