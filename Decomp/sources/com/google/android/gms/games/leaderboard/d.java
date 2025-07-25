package com.google.android.gms.games.leaderboard;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.internal.ee;
import com.google.android.gms.internal.eg;
import com.google.android.gms.internal.fc;

public final class d implements LeaderboardScore {
    private final long vK;
    private final String vL;
    private final String vM;
    private final long vN;
    private final long vO;
    private final String vP;
    private final Uri vQ;
    private final Uri vR;
    private final PlayerEntity vS;
    private final String vT;

    public d(LeaderboardScore leaderboardScore) {
        this.vK = leaderboardScore.getRank();
        this.vL = (String) eg.f(leaderboardScore.getDisplayRank());
        this.vM = (String) eg.f(leaderboardScore.getDisplayScore());
        this.vN = leaderboardScore.getRawScore();
        this.vO = leaderboardScore.getTimestampMillis();
        this.vP = leaderboardScore.getScoreHolderDisplayName();
        this.vQ = leaderboardScore.getScoreHolderIconImageUri();
        this.vR = leaderboardScore.getScoreHolderHiResImageUri();
        Player scoreHolder = leaderboardScore.getScoreHolder();
        this.vS = scoreHolder == null ? null : (PlayerEntity) scoreHolder.freeze();
        this.vT = leaderboardScore.getScoreTag();
    }

    static int a(LeaderboardScore leaderboardScore) {
        return ee.hashCode(Long.valueOf(leaderboardScore.getRank()), leaderboardScore.getDisplayRank(), Long.valueOf(leaderboardScore.getRawScore()), leaderboardScore.getDisplayScore(), Long.valueOf(leaderboardScore.getTimestampMillis()), leaderboardScore.getScoreHolderDisplayName(), leaderboardScore.getScoreHolderIconImageUri(), leaderboardScore.getScoreHolderHiResImageUri(), leaderboardScore.getScoreHolder());
    }

    static boolean a(LeaderboardScore leaderboardScore, Object obj) {
        if (!(obj instanceof LeaderboardScore)) {
            return false;
        }
        if (leaderboardScore == obj) {
            return true;
        }
        LeaderboardScore leaderboardScore2 = (LeaderboardScore) obj;
        return ee.equal(Long.valueOf(leaderboardScore2.getRank()), Long.valueOf(leaderboardScore.getRank())) && ee.equal(leaderboardScore2.getDisplayRank(), leaderboardScore.getDisplayRank()) && ee.equal(Long.valueOf(leaderboardScore2.getRawScore()), Long.valueOf(leaderboardScore.getRawScore())) && ee.equal(leaderboardScore2.getDisplayScore(), leaderboardScore.getDisplayScore()) && ee.equal(Long.valueOf(leaderboardScore2.getTimestampMillis()), Long.valueOf(leaderboardScore.getTimestampMillis())) && ee.equal(leaderboardScore2.getScoreHolderDisplayName(), leaderboardScore.getScoreHolderDisplayName()) && ee.equal(leaderboardScore2.getScoreHolderIconImageUri(), leaderboardScore.getScoreHolderIconImageUri()) && ee.equal(leaderboardScore2.getScoreHolderHiResImageUri(), leaderboardScore.getScoreHolderHiResImageUri()) && ee.equal(leaderboardScore2.getScoreHolder(), leaderboardScore.getScoreHolder()) && ee.equal(leaderboardScore2.getScoreTag(), leaderboardScore.getScoreTag());
    }

    static String b(LeaderboardScore leaderboardScore) {
        return ee.e(leaderboardScore).a("Rank", Long.valueOf(leaderboardScore.getRank())).a("DisplayRank", leaderboardScore.getDisplayRank()).a("Score", Long.valueOf(leaderboardScore.getRawScore())).a("DisplayScore", leaderboardScore.getDisplayScore()).a("Timestamp", Long.valueOf(leaderboardScore.getTimestampMillis())).a("DisplayName", leaderboardScore.getScoreHolderDisplayName()).a("IconImageUri", leaderboardScore.getScoreHolderIconImageUri()).a("HiResImageUri", leaderboardScore.getScoreHolderHiResImageUri()).a("Player", leaderboardScore.getScoreHolder() == null ? null : leaderboardScore.getScoreHolder()).a("ScoreTag", leaderboardScore.getScoreTag()).toString();
    }

    /* renamed from: ds */
    public LeaderboardScore freeze() {
        return this;
    }

    public boolean equals(Object obj) {
        return a(this, obj);
    }

    public String getDisplayRank() {
        return this.vL;
    }

    public void getDisplayRank(CharArrayBuffer charArrayBuffer) {
        fc.b(this.vL, charArrayBuffer);
    }

    public String getDisplayScore() {
        return this.vM;
    }

    public void getDisplayScore(CharArrayBuffer charArrayBuffer) {
        fc.b(this.vM, charArrayBuffer);
    }

    public long getRank() {
        return this.vK;
    }

    public long getRawScore() {
        return this.vN;
    }

    public Player getScoreHolder() {
        return this.vS;
    }

    public String getScoreHolderDisplayName() {
        return this.vS == null ? this.vP : this.vS.getDisplayName();
    }

    public void getScoreHolderDisplayName(CharArrayBuffer charArrayBuffer) {
        if (this.vS == null) {
            fc.b(this.vP, charArrayBuffer);
        } else {
            this.vS.getDisplayName(charArrayBuffer);
        }
    }

    public Uri getScoreHolderHiResImageUri() {
        return this.vS == null ? this.vR : this.vS.getHiResImageUri();
    }

    public Uri getScoreHolderIconImageUri() {
        return this.vS == null ? this.vQ : this.vS.getIconImageUri();
    }

    public String getScoreTag() {
        return this.vT;
    }

    public long getTimestampMillis() {
        return this.vO;
    }

    public int hashCode() {
        return a(this);
    }

    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return b(this);
    }
}
