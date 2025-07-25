package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.internal.eg;
import java.util.ArrayList;

public final class b extends com.google.android.gms.common.data.b implements Invitation {
    private final Game vG;
    private final ArrayList<Participant> wn;
    private final d wq;

    b(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.vG = new com.google.android.gms.games.b(dataHolder, i);
        this.wn = new ArrayList<>(i2);
        String string = getString("external_inviter_id");
        d dVar = null;
        for (int i3 = 0; i3 < i2; i3++) {
            d dVar2 = new d(this.nE, this.nG + i3);
            if (dVar2.getParticipantId().equals(string)) {
                dVar = dVar2;
            }
            this.wn.add(dVar2);
        }
        this.wq = (d) eg.b(dVar, (Object) "Must have a valid inviter!");
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return InvitationEntity.a(this, obj);
    }

    public Invitation freeze() {
        return new InvitationEntity(this);
    }

    public int getAvailableAutoMatchSlots() {
        if (!getBoolean("has_automatch_criteria")) {
            return 0;
        }
        return getInteger("automatch_max_players");
    }

    public long getCreationTimestamp() {
        return Math.max(getLong("creation_timestamp"), getLong("last_modified_timestamp"));
    }

    public Game getGame() {
        return this.vG;
    }

    public String getInvitationId() {
        return getString("external_invitation_id");
    }

    public int getInvitationType() {
        return getInteger("type");
    }

    public Participant getInviter() {
        return this.wq;
    }

    public ArrayList<Participant> getParticipants() {
        return this.wn;
    }

    public int getVariant() {
        return getInteger("variant");
    }

    public int hashCode() {
        return InvitationEntity.a(this);
    }

    public String toString() {
        return InvitationEntity.b((Invitation) this);
    }

    public void writeToParcel(Parcel parcel, int i) {
        ((InvitationEntity) freeze()).writeToParcel(parcel, i);
    }
}
