package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.internal.ee;
import com.google.android.gms.internal.eg;
import com.google.android.gms.internal.fm;
import java.util.ArrayList;

public final class InvitationEntity extends fm implements Invitation {
    public static final Parcelable.Creator<InvitationEntity> CREATOR = new a();
    private final int kg;
    private final String uf;
    private final GameEntity wj;
    private final long wk;
    private final int wl;
    private final ParticipantEntity wm;
    private final ArrayList<ParticipantEntity> wn;
    private final int wo;
    private final int wp;

    static final class a extends a {
        a() {
        }

        /* renamed from: aa */
        public InvitationEntity createFromParcel(Parcel parcel) {
            if (InvitationEntity.c(InvitationEntity.bM()) || InvitationEntity.P(InvitationEntity.class.getCanonicalName())) {
                return super.createFromParcel(parcel);
            }
            GameEntity createFromParcel = GameEntity.CREATOR.createFromParcel(parcel);
            String readString = parcel.readString();
            long readLong = parcel.readLong();
            int readInt = parcel.readInt();
            ParticipantEntity createFromParcel2 = ParticipantEntity.CREATOR.createFromParcel(parcel);
            int readInt2 = parcel.readInt();
            ArrayList arrayList = new ArrayList(readInt2);
            for (int i = 0; i < readInt2; i++) {
                arrayList.add(ParticipantEntity.CREATOR.createFromParcel(parcel));
            }
            return new InvitationEntity(2, createFromParcel, readString, readLong, readInt, createFromParcel2, arrayList, -1, 0);
        }
    }

    InvitationEntity(int i, GameEntity gameEntity, String str, long j, int i2, ParticipantEntity participantEntity, ArrayList<ParticipantEntity> arrayList, int i3, int i4) {
        this.kg = i;
        this.wj = gameEntity;
        this.uf = str;
        this.wk = j;
        this.wl = i2;
        this.wm = participantEntity;
        this.wn = arrayList;
        this.wo = i3;
        this.wp = i4;
    }

    InvitationEntity(Invitation invitation) {
        this.kg = 2;
        this.wj = new GameEntity(invitation.getGame());
        this.uf = invitation.getInvitationId();
        this.wk = invitation.getCreationTimestamp();
        this.wl = invitation.getInvitationType();
        this.wo = invitation.getVariant();
        this.wp = invitation.getAvailableAutoMatchSlots();
        String participantId = invitation.getInviter().getParticipantId();
        Participant participant = null;
        ArrayList<Participant> participants = invitation.getParticipants();
        int size = participants.size();
        this.wn = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Participant participant2 = participants.get(i);
            if (participant2.getParticipantId().equals(participantId)) {
                participant = participant2;
            }
            this.wn.add((ParticipantEntity) participant2.freeze());
        }
        eg.b(participant, (Object) "Must have a valid inviter!");
        this.wm = (ParticipantEntity) participant.freeze();
    }

    static int a(Invitation invitation) {
        return ee.hashCode(invitation.getGame(), invitation.getInvitationId(), Long.valueOf(invitation.getCreationTimestamp()), Integer.valueOf(invitation.getInvitationType()), invitation.getInviter(), invitation.getParticipants(), Integer.valueOf(invitation.getVariant()), Integer.valueOf(invitation.getAvailableAutoMatchSlots()));
    }

    static boolean a(Invitation invitation, Object obj) {
        if (!(obj instanceof Invitation)) {
            return false;
        }
        if (invitation == obj) {
            return true;
        }
        Invitation invitation2 = (Invitation) obj;
        return ee.equal(invitation2.getGame(), invitation.getGame()) && ee.equal(invitation2.getInvitationId(), invitation.getInvitationId()) && ee.equal(Long.valueOf(invitation2.getCreationTimestamp()), Long.valueOf(invitation.getCreationTimestamp())) && ee.equal(Integer.valueOf(invitation2.getInvitationType()), Integer.valueOf(invitation.getInvitationType())) && ee.equal(invitation2.getInviter(), invitation.getInviter()) && ee.equal(invitation2.getParticipants(), invitation.getParticipants()) && ee.equal(Integer.valueOf(invitation2.getVariant()), Integer.valueOf(invitation.getVariant())) && ee.equal(Integer.valueOf(invitation2.getAvailableAutoMatchSlots()), Integer.valueOf(invitation.getAvailableAutoMatchSlots()));
    }

    static String b(Invitation invitation) {
        return ee.e(invitation).a("Game", invitation.getGame()).a("InvitationId", invitation.getInvitationId()).a("CreationTimestamp", Long.valueOf(invitation.getCreationTimestamp())).a("InvitationType", Integer.valueOf(invitation.getInvitationType())).a("Inviter", invitation.getInviter()).a("Participants", invitation.getParticipants()).a("Variant", Integer.valueOf(invitation.getVariant())).a("AvailableAutoMatchSlots", Integer.valueOf(invitation.getAvailableAutoMatchSlots())).toString();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return a(this, obj);
    }

    public Invitation freeze() {
        return this;
    }

    public int getAvailableAutoMatchSlots() {
        return this.wp;
    }

    public long getCreationTimestamp() {
        return this.wk;
    }

    public Game getGame() {
        return this.wj;
    }

    public String getInvitationId() {
        return this.uf;
    }

    public int getInvitationType() {
        return this.wl;
    }

    public Participant getInviter() {
        return this.wm;
    }

    public ArrayList<Participant> getParticipants() {
        return new ArrayList<>(this.wn);
    }

    public int getVariant() {
        return this.wo;
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

    public String toString() {
        return b((Invitation) this);
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (!bN()) {
            a.a(this, parcel, i);
            return;
        }
        this.wj.writeToParcel(parcel, i);
        parcel.writeString(this.uf);
        parcel.writeLong(this.wk);
        parcel.writeInt(this.wl);
        this.wm.writeToParcel(parcel, i);
        int size = this.wn.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            this.wn.get(i2).writeToParcel(parcel, i);
        }
    }
}
