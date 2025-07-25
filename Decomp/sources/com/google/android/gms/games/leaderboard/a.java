package com.google.android.gms.games.leaderboard;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.internal.ee;
import com.google.android.gms.internal.fc;
import java.util.ArrayList;

public final class a implements Leaderboard {
    private final String qa;
    private final Uri sL;
    private final String vD;
    private final int vE;
    private final ArrayList<f> vF;
    private final Game vG;

    public a(Leaderboard leaderboard) {
        this.vD = leaderboard.getLeaderboardId();
        this.qa = leaderboard.getDisplayName();
        this.sL = leaderboard.getIconImageUri();
        this.vE = leaderboard.getScoreOrder();
        Game game = leaderboard.getGame();
        this.vG = game == null ? null : new GameEntity(game);
        ArrayList<LeaderboardVariant> variants = leaderboard.getVariants();
        int size = variants.size();
        this.vF = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            this.vF.add((f) variants.get(i).freeze());
        }
    }

    static int a(Leaderboard leaderboard) {
        return ee.hashCode(leaderboard.getLeaderboardId(), leaderboard.getDisplayName(), leaderboard.getIconImageUri(), Integer.valueOf(leaderboard.getScoreOrder()), leaderboard.getVariants());
    }

    static boolean a(Leaderboard leaderboard, Object obj) {
        if (!(obj instanceof Leaderboard)) {
            return false;
        }
        if (leaderboard == obj) {
            return true;
        }
        Leaderboard leaderboard2 = (Leaderboard) obj;
        return ee.equal(leaderboard2.getLeaderboardId(), leaderboard.getLeaderboardId()) && ee.equal(leaderboard2.getDisplayName(), leaderboard.getDisplayName()) && ee.equal(leaderboard2.getIconImageUri(), leaderboard.getIconImageUri()) && ee.equal(Integer.valueOf(leaderboard2.getScoreOrder()), Integer.valueOf(leaderboard.getScoreOrder())) && ee.equal(leaderboard2.getVariants(), leaderboard.getVariants());
    }

    static String b(Leaderboard leaderboard) {
        return ee.e(leaderboard).a("LeaderboardId", leaderboard.getLeaderboardId()).a("DisplayName", leaderboard.getDisplayName()).a("IconImageUri", leaderboard.getIconImageUri()).a("ScoreOrder", Integer.valueOf(leaderboard.getScoreOrder())).a("Variants", leaderboard.getVariants()).toString();
    }

    /* renamed from: dp */
    public Leaderboard freeze() {
        return this;
    }

    public boolean equals(Object obj) {
        return a(this, obj);
    }

    public String getDisplayName() {
        return this.qa;
    }

    public void getDisplayName(CharArrayBuffer charArrayBuffer) {
        fc.b(this.qa, charArrayBuffer);
    }

    public Game getGame() {
        return this.vG;
    }

    public Uri getIconImageUri() {
        return this.sL;
    }

    public String getLeaderboardId() {
        return this.vD;
    }

    public int getScoreOrder() {
        return this.vE;
    }

    public ArrayList<LeaderboardVariant> getVariants() {
        return new ArrayList<>(this.vF);
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
