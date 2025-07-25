package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import android.os.Parcel;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.d;
import java.util.ArrayList;

public final class a extends b implements TurnBasedMatch {
    private final Game vG;
    private final int vH;

    a(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.vG = new com.google.android.gms.games.b(dataHolder, i);
        this.vH = i2;
    }

    public boolean canRematch() {
        return getTurnStatus() == 3 && getRematchId() == null && getParticipants().size() > 1;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return TurnBasedMatchEntity.a((TurnBasedMatch) this, obj);
    }

    public TurnBasedMatch freeze() {
        return new TurnBasedMatchEntity(this);
    }

    public Bundle getAutoMatchCriteria() {
        if (!getBoolean("has_automatch_criteria")) {
            return null;
        }
        return TurnBasedMatchConfig.createAutoMatchCriteria(getInteger("automatch_min_players"), getInteger("automatch_max_players"), getLong("automatch_bit_mask"));
    }

    public int getAvailableAutoMatchSlots() {
        if (!getBoolean("has_automatch_criteria")) {
            return 0;
        }
        return getInteger("automatch_max_players");
    }

    public long getCreationTimestamp() {
        return getLong("creation_timestamp");
    }

    public String getCreatorId() {
        return getString("creator_external");
    }

    public byte[] getData() {
        return getByteArray("data");
    }

    public Game getGame() {
        return this.vG;
    }

    public long getLastUpdatedTimestamp() {
        return getLong("last_updated_timestamp");
    }

    public String getLastUpdaterId() {
        return getString("last_updater_external");
    }

    public String getMatchId() {
        return getString("external_match_id");
    }

    public int getMatchNumber() {
        return getInteger("match_number");
    }

    public Participant getParticipant(String str) {
        return TurnBasedMatchEntity.c(this, str);
    }

    public String getParticipantId(String str) {
        return TurnBasedMatchEntity.b(this, str);
    }

    public ArrayList<String> getParticipantIds() {
        return TurnBasedMatchEntity.c(this);
    }

    public int getParticipantStatus(String str) {
        return TurnBasedMatchEntity.a((TurnBasedMatch) this, str);
    }

    public ArrayList<Participant> getParticipants() {
        ArrayList<Participant> arrayList = new ArrayList<>(this.vH);
        for (int i = 0; i < this.vH; i++) {
            arrayList.add(new d(this.nE, this.nG + i));
        }
        return arrayList;
    }

    public String getPendingParticipantId() {
        return getString("pending_participant_external");
    }

    public byte[] getPreviousMatchData() {
        return getByteArray("previous_match_data");
    }

    public String getRematchId() {
        return getString("rematch_id");
    }

    public int getStatus() {
        return getInteger("status");
    }

    public int getTurnStatus() {
        return getInteger("user_match_status");
    }

    public int getVariant() {
        return getInteger("variant");
    }

    public int getVersion() {
        return getInteger(ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION);
    }

    public int hashCode() {
        return TurnBasedMatchEntity.a(this);
    }

    public boolean isLocallyModified() {
        return getBoolean("upsync_required");
    }

    public String toString() {
        return TurnBasedMatchEntity.b(this);
    }

    public void writeToParcel(Parcel parcel, int i) {
        ((TurnBasedMatchEntity) freeze()).writeToParcel(parcel, i);
    }
}
