package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.ee;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fm;

public final class GameEntity extends fm implements Game {
    public static final Parcelable.Creator<GameEntity> CREATOR = new a();
    private final int kg;
    private final String kh;
    private final String qa;
    private final String sH;
    private final String sI;
    private final String sJ;
    private final String sK;
    private final Uri sL;
    private final Uri sM;
    private final Uri sN;
    private final boolean sO;
    private final boolean sP;
    private final String sQ;
    private final int sR;
    private final int sS;
    private final int sT;
    private final boolean sU;
    private final boolean sV;

    static final class a extends a {
        a() {
        }

        /* renamed from: Y */
        public GameEntity createFromParcel(Parcel parcel) {
            if (GameEntity.c(GameEntity.bM()) || GameEntity.P(GameEntity.class.getCanonicalName())) {
                return super.createFromParcel(parcel);
            }
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            String readString4 = parcel.readString();
            String readString5 = parcel.readString();
            String readString6 = parcel.readString();
            String readString7 = parcel.readString();
            Uri parse = readString7 == null ? null : Uri.parse(readString7);
            String readString8 = parcel.readString();
            Uri parse2 = readString8 == null ? null : Uri.parse(readString8);
            String readString9 = parcel.readString();
            return new GameEntity(2, readString, readString2, readString3, readString4, readString5, readString6, parse, parse2, readString9 == null ? null : Uri.parse(readString9), parcel.readInt() > 0, parcel.readInt() > 0, parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), false, false);
        }
    }

    GameEntity(int i, String str, String str2, String str3, String str4, String str5, String str6, Uri uri, Uri uri2, Uri uri3, boolean z, boolean z2, String str7, int i2, int i3, int i4, boolean z3, boolean z4) {
        this.kg = i;
        this.kh = str;
        this.qa = str2;
        this.sH = str3;
        this.sI = str4;
        this.sJ = str5;
        this.sK = str6;
        this.sL = uri;
        this.sM = uri2;
        this.sN = uri3;
        this.sO = z;
        this.sP = z2;
        this.sQ = str7;
        this.sR = i2;
        this.sS = i3;
        this.sT = i4;
        this.sU = z3;
        this.sV = z4;
    }

    public GameEntity(Game game) {
        this.kg = 2;
        this.kh = game.getApplicationId();
        this.sH = game.getPrimaryCategory();
        this.sI = game.getSecondaryCategory();
        this.sJ = game.getDescription();
        this.sK = game.getDeveloperName();
        this.qa = game.getDisplayName();
        this.sL = game.getIconImageUri();
        this.sM = game.getHiResImageUri();
        this.sN = game.getFeaturedImageUri();
        this.sO = game.isPlayEnabledGame();
        this.sP = game.isInstanceInstalled();
        this.sQ = game.getInstancePackageName();
        this.sR = game.getGameplayAclStatus();
        this.sS = game.getAchievementTotalCount();
        this.sT = game.getLeaderboardCount();
        this.sU = game.isRealTimeMultiplayerEnabled();
        this.sV = game.isTurnBasedMultiplayerEnabled();
    }

    static int a(Game game) {
        return ee.hashCode(game.getApplicationId(), game.getDisplayName(), game.getPrimaryCategory(), game.getSecondaryCategory(), game.getDescription(), game.getDeveloperName(), game.getIconImageUri(), game.getHiResImageUri(), game.getFeaturedImageUri(), Boolean.valueOf(game.isPlayEnabledGame()), Boolean.valueOf(game.isInstanceInstalled()), game.getInstancePackageName(), Integer.valueOf(game.getGameplayAclStatus()), Integer.valueOf(game.getAchievementTotalCount()), Integer.valueOf(game.getLeaderboardCount()), Boolean.valueOf(game.isRealTimeMultiplayerEnabled()), Boolean.valueOf(game.isTurnBasedMultiplayerEnabled()));
    }

    static boolean a(Game game, Object obj) {
        if (!(obj instanceof Game)) {
            return false;
        }
        if (game == obj) {
            return true;
        }
        Game game2 = (Game) obj;
        return ee.equal(game2.getApplicationId(), game.getApplicationId()) && ee.equal(game2.getDisplayName(), game.getDisplayName()) && ee.equal(game2.getPrimaryCategory(), game.getPrimaryCategory()) && ee.equal(game2.getSecondaryCategory(), game.getSecondaryCategory()) && ee.equal(game2.getDescription(), game.getDescription()) && ee.equal(game2.getDeveloperName(), game.getDeveloperName()) && ee.equal(game2.getIconImageUri(), game.getIconImageUri()) && ee.equal(game2.getHiResImageUri(), game.getHiResImageUri()) && ee.equal(game2.getFeaturedImageUri(), game.getFeaturedImageUri()) && ee.equal(Boolean.valueOf(game2.isPlayEnabledGame()), Boolean.valueOf(game.isPlayEnabledGame())) && ee.equal(Boolean.valueOf(game2.isInstanceInstalled()), Boolean.valueOf(game.isInstanceInstalled())) && ee.equal(game2.getInstancePackageName(), game.getInstancePackageName()) && ee.equal(Integer.valueOf(game2.getGameplayAclStatus()), Integer.valueOf(game.getGameplayAclStatus())) && ee.equal(Integer.valueOf(game2.getAchievementTotalCount()), Integer.valueOf(game.getAchievementTotalCount())) && ee.equal(Integer.valueOf(game2.getLeaderboardCount()), Integer.valueOf(game.getLeaderboardCount())) && ee.equal(Boolean.valueOf(game2.isRealTimeMultiplayerEnabled()), Boolean.valueOf(game.isRealTimeMultiplayerEnabled())) && ee.equal(Boolean.valueOf(game2.isTurnBasedMultiplayerEnabled()), Boolean.valueOf(game.isTurnBasedMultiplayerEnabled()));
    }

    static String b(Game game) {
        return ee.e(game).a("ApplicationId", game.getApplicationId()).a("DisplayName", game.getDisplayName()).a("PrimaryCategory", game.getPrimaryCategory()).a("SecondaryCategory", game.getSecondaryCategory()).a("Description", game.getDescription()).a("DeveloperName", game.getDeveloperName()).a("IconImageUri", game.getIconImageUri()).a("HiResImageUri", game.getHiResImageUri()).a("FeaturedImageUri", game.getFeaturedImageUri()).a("PlayEnabledGame", Boolean.valueOf(game.isPlayEnabledGame())).a("InstanceInstalled", Boolean.valueOf(game.isInstanceInstalled())).a("InstancePackageName", game.getInstancePackageName()).a("GameplayAclStatus", Integer.valueOf(game.getGameplayAclStatus())).a("AchievementTotalCount", Integer.valueOf(game.getAchievementTotalCount())).a("LeaderboardCount", Integer.valueOf(game.getLeaderboardCount())).a("RealTimeMultiplayerEnabled", Boolean.valueOf(game.isRealTimeMultiplayerEnabled())).a("TurnBasedMultiplayerEnabled", Boolean.valueOf(game.isTurnBasedMultiplayerEnabled())).toString();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return a(this, obj);
    }

    public Game freeze() {
        return this;
    }

    public int getAchievementTotalCount() {
        return this.sS;
    }

    public String getApplicationId() {
        return this.kh;
    }

    public String getDescription() {
        return this.sJ;
    }

    public void getDescription(CharArrayBuffer charArrayBuffer) {
        fc.b(this.sJ, charArrayBuffer);
    }

    public String getDeveloperName() {
        return this.sK;
    }

    public void getDeveloperName(CharArrayBuffer charArrayBuffer) {
        fc.b(this.sK, charArrayBuffer);
    }

    public String getDisplayName() {
        return this.qa;
    }

    public void getDisplayName(CharArrayBuffer charArrayBuffer) {
        fc.b(this.qa, charArrayBuffer);
    }

    public Uri getFeaturedImageUri() {
        return this.sN;
    }

    public int getGameplayAclStatus() {
        return this.sR;
    }

    public Uri getHiResImageUri() {
        return this.sM;
    }

    public Uri getIconImageUri() {
        return this.sL;
    }

    public String getInstancePackageName() {
        return this.sQ;
    }

    public int getLeaderboardCount() {
        return this.sT;
    }

    public String getPrimaryCategory() {
        return this.sH;
    }

    public String getSecondaryCategory() {
        return this.sI;
    }

    public int getVersionCode() {
        return this.kg;
    }

    public int hashCode() {
        return a(this);
    }

    public boolean isDataValid() {
        return true;
    }

    public boolean isInstanceInstalled() {
        return this.sP;
    }

    public boolean isPlayEnabledGame() {
        return this.sO;
    }

    public boolean isRealTimeMultiplayerEnabled() {
        return this.sU;
    }

    public boolean isTurnBasedMultiplayerEnabled() {
        return this.sV;
    }

    public String toString() {
        return b((Game) this);
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        String str = null;
        if (!bN()) {
            a.a(this, parcel, i);
            return;
        }
        parcel.writeString(this.kh);
        parcel.writeString(this.qa);
        parcel.writeString(this.sH);
        parcel.writeString(this.sI);
        parcel.writeString(this.sJ);
        parcel.writeString(this.sK);
        parcel.writeString(this.sL == null ? null : this.sL.toString());
        parcel.writeString(this.sM == null ? null : this.sM.toString());
        if (this.sN != null) {
            str = this.sN.toString();
        }
        parcel.writeString(str);
        parcel.writeInt(this.sO ? 1 : 0);
        if (!this.sP) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        parcel.writeString(this.sQ);
        parcel.writeInt(this.sR);
        parcel.writeInt(this.sS);
        parcel.writeInt(this.sT);
    }
}
