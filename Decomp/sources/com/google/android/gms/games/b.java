package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;

public final class b extends com.google.android.gms.common.data.b implements Game {
    public b(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return GameEntity.a(this, obj);
    }

    public Game freeze() {
        return new GameEntity(this);
    }

    public int getAchievementTotalCount() {
        return getInteger("achievement_total_count");
    }

    public String getApplicationId() {
        return getString("external_game_id");
    }

    public String getDescription() {
        return getString("game_description");
    }

    public void getDescription(CharArrayBuffer charArrayBuffer) {
        a("game_description", charArrayBuffer);
    }

    public String getDeveloperName() {
        return getString("developer_name");
    }

    public void getDeveloperName(CharArrayBuffer charArrayBuffer) {
        a("developer_name", charArrayBuffer);
    }

    public String getDisplayName() {
        return getString("display_name");
    }

    public void getDisplayName(CharArrayBuffer charArrayBuffer) {
        a("display_name", charArrayBuffer);
    }

    public Uri getFeaturedImageUri() {
        return L("featured_image_uri");
    }

    public int getGameplayAclStatus() {
        return getInteger("gameplay_acl_status");
    }

    public Uri getHiResImageUri() {
        return L("game_hi_res_image_uri");
    }

    public Uri getIconImageUri() {
        return L("game_icon_image_uri");
    }

    public String getInstancePackageName() {
        return getString("package_name");
    }

    public int getLeaderboardCount() {
        return getInteger("leaderboard_count");
    }

    public String getPrimaryCategory() {
        return getString("primary_category");
    }

    public String getSecondaryCategory() {
        return getString("secondary_category");
    }

    public int hashCode() {
        return GameEntity.a(this);
    }

    public boolean isInstanceInstalled() {
        return getInteger("installed") > 0;
    }

    public boolean isPlayEnabledGame() {
        return getBoolean("play_enabled_game");
    }

    public boolean isRealTimeMultiplayerEnabled() {
        return getInteger("real_time_support") > 0;
    }

    public boolean isTurnBasedMultiplayerEnabled() {
        return getInteger("turn_based_support") > 0;
    }

    public String toString() {
        return GameEntity.b((Game) this);
    }

    public void writeToParcel(Parcel parcel, int i) {
        ((GameEntity) freeze()).writeToParcel(parcel, i);
    }
}
