package com.google.android.gms.games.achievement;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.d;
import com.google.android.gms.internal.ds;
import com.google.android.gms.internal.ee;

public final class a extends b implements Achievement {
    a(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    public String getAchievementId() {
        return getString("external_achievement_id");
    }

    public int getCurrentSteps() {
        boolean z = true;
        if (getType() != 1) {
            z = false;
        }
        ds.p(z);
        return getInteger("current_steps");
    }

    public String getDescription() {
        return getString("description");
    }

    public void getDescription(CharArrayBuffer charArrayBuffer) {
        a("description", charArrayBuffer);
    }

    public String getFormattedCurrentSteps() {
        boolean z = true;
        if (getType() != 1) {
            z = false;
        }
        ds.p(z);
        return getString("formatted_current_steps");
    }

    public void getFormattedCurrentSteps(CharArrayBuffer charArrayBuffer) {
        boolean z = true;
        if (getType() != 1) {
            z = false;
        }
        ds.p(z);
        a("formatted_current_steps", charArrayBuffer);
    }

    public String getFormattedTotalSteps() {
        boolean z = true;
        if (getType() != 1) {
            z = false;
        }
        ds.p(z);
        return getString("formatted_total_steps");
    }

    public void getFormattedTotalSteps(CharArrayBuffer charArrayBuffer) {
        boolean z = true;
        if (getType() != 1) {
            z = false;
        }
        ds.p(z);
        a("formatted_total_steps", charArrayBuffer);
    }

    public long getLastUpdatedTimestamp() {
        return getLong("last_updated_timestamp");
    }

    public String getName() {
        return getString("name");
    }

    public void getName(CharArrayBuffer charArrayBuffer) {
        a("name", charArrayBuffer);
    }

    public Player getPlayer() {
        return new d(this.nE, this.nG);
    }

    public Uri getRevealedImageUri() {
        return L("revealed_icon_image_uri");
    }

    public int getState() {
        return getInteger("state");
    }

    public int getTotalSteps() {
        boolean z = true;
        if (getType() != 1) {
            z = false;
        }
        ds.p(z);
        return getInteger("total_steps");
    }

    public int getType() {
        return getInteger("type");
    }

    public Uri getUnlockedImageUri() {
        return L("unlocked_icon_image_uri");
    }

    public String toString() {
        ee.a a = ee.e(this).a(ShareConstants.WEB_DIALOG_PARAM_ID, getAchievementId()).a("name", getName()).a("state", Integer.valueOf(getState())).a("type", Integer.valueOf(getType()));
        if (getType() == 1) {
            a.a("steps", getCurrentSteps() + "/" + getTotalSteps());
        }
        return a.toString();
    }
}
