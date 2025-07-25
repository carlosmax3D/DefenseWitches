package com.google.android.gms.games.multiplayer;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.Player;

public final class d extends b implements Participant {
    private final com.google.android.gms.games.d wx;

    public d(DataHolder dataHolder, int i) {
        super(dataHolder, i);
        this.wx = new com.google.android.gms.games.d(dataHolder, i);
    }

    public int describeContents() {
        return 0;
    }

    public String dy() {
        return getString("client_address");
    }

    public boolean equals(Object obj) {
        return ParticipantEntity.a(this, obj);
    }

    public Participant freeze() {
        return new ParticipantEntity(this);
    }

    public int getCapabilities() {
        return getInteger("capabilities");
    }

    public String getDisplayName() {
        return M("external_player_id") ? getString("default_display_name") : this.wx.getDisplayName();
    }

    public void getDisplayName(CharArrayBuffer charArrayBuffer) {
        if (M("external_player_id")) {
            a("default_display_name", charArrayBuffer);
        } else {
            this.wx.getDisplayName(charArrayBuffer);
        }
    }

    public Uri getHiResImageUri() {
        return M("external_player_id") ? L("default_display_hi_res_image_uri") : this.wx.getHiResImageUri();
    }

    public Uri getIconImageUri() {
        return M("external_player_id") ? L("default_display_image_uri") : this.wx.getIconImageUri();
    }

    public String getParticipantId() {
        return getString("external_participant_id");
    }

    public Player getPlayer() {
        if (M("external_player_id")) {
            return null;
        }
        return this.wx;
    }

    public ParticipantResult getResult() {
        if (M("result_type")) {
            return null;
        }
        return new ParticipantResult(getParticipantId(), getInteger("result_type"), getInteger("placing"));
    }

    public int getStatus() {
        return getInteger("player_status");
    }

    public int hashCode() {
        return ParticipantEntity.a(this);
    }

    public boolean isConnectedToRoom() {
        return getInteger("connected") > 0;
    }

    public String toString() {
        return ParticipantEntity.b((Participant) this);
    }

    public void writeToParcel(Parcel parcel, int i) {
        ((ParticipantEntity) freeze()).writeToParcel(parcel, i);
    }
}
