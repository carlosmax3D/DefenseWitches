package com.google.android.gms.games.leaderboard;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import java.util.ArrayList;

public final class b extends com.google.android.gms.common.data.b implements Leaderboard {
    private final Game vG;
    private final int vH;

    b(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.vH = i2;
        this.vG = new com.google.android.gms.games.b(dataHolder, i);
    }

    /* renamed from: dp */
    public Leaderboard freeze() {
        return new a(this);
    }

    public boolean equals(Object obj) {
        return a.a(this, obj);
    }

    public String getDisplayName() {
        return getString("name");
    }

    public void getDisplayName(CharArrayBuffer charArrayBuffer) {
        a("name", charArrayBuffer);
    }

    public Game getGame() {
        return this.vG;
    }

    public Uri getIconImageUri() {
        return L("board_icon_image_uri");
    }

    public String getLeaderboardId() {
        return getString("external_leaderboard_id");
    }

    public int getScoreOrder() {
        return getInteger("score_order");
    }

    public ArrayList<LeaderboardVariant> getVariants() {
        ArrayList<LeaderboardVariant> arrayList = new ArrayList<>(this.vH);
        for (int i = 0; i < this.vH; i++) {
            arrayList.add(new g(this.nE, this.nG + i));
        }
        return arrayList;
    }

    public int hashCode() {
        return a.a(this);
    }

    public String toString() {
        return a.b(this);
    }
}
