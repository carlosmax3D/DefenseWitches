package com.google.android.gms.games.leaderboard;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;

public final class g extends b implements LeaderboardVariant {
    g(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    public String dt() {
        return getString("top_page_token_next");
    }

    public String du() {
        return getString("window_page_token_prev");
    }

    public String dv() {
        return getString("window_page_token_next");
    }

    /* renamed from: dw */
    public LeaderboardVariant freeze() {
        return new f(this);
    }

    public boolean equals(Object obj) {
        return f.a(this, obj);
    }

    public int getCollection() {
        return getInteger("collection");
    }

    public String getDisplayPlayerRank() {
        return getString("player_display_rank");
    }

    public String getDisplayPlayerScore() {
        return getString("player_display_score");
    }

    public long getNumScores() {
        if (M("total_scores")) {
            return -1;
        }
        return getLong("total_scores");
    }

    public long getPlayerRank() {
        if (M("player_rank")) {
            return -1;
        }
        return getLong("player_rank");
    }

    public String getPlayerScoreTag() {
        return getString("player_score_tag");
    }

    public long getRawPlayerScore() {
        if (M("player_raw_score")) {
            return -1;
        }
        return getLong("player_raw_score");
    }

    public int getTimeSpan() {
        return getInteger("timespan");
    }

    public boolean hasPlayerInfo() {
        return !M("player_raw_score");
    }

    public int hashCode() {
        return f.a(this);
    }

    public String toString() {
        return f.b(this);
    }
}
