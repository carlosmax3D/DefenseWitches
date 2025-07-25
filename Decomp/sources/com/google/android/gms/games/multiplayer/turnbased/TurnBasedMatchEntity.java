package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import com.google.android.gms.internal.ee;
import java.util.ArrayList;

public final class TurnBasedMatchEntity implements SafeParcelable, TurnBasedMatch {
    public static final TurnBasedMatchEntityCreator CREATOR = new TurnBasedMatchEntityCreator();
    private final int kg;
    private final String ul;
    private final Bundle wH;
    private final String wL;
    private final String wU;
    private final long wV;
    private final String wW;
    private final int wX;
    private final int wY;
    private final byte[] wZ;
    private final GameEntity wj;
    private final long wk;
    private final ArrayList<ParticipantEntity> wn;
    private final int wo;
    private final String xa;
    private final byte[] xb;
    private final int xc;
    private final int xd;
    private final boolean xe;

    TurnBasedMatchEntity(int i, GameEntity gameEntity, String str, String str2, long j, String str3, long j2, String str4, int i2, int i3, int i4, byte[] bArr, ArrayList<ParticipantEntity> arrayList, String str5, byte[] bArr2, int i5, Bundle bundle, int i6, boolean z) {
        this.kg = i;
        this.wj = gameEntity;
        this.ul = str;
        this.wL = str2;
        this.wk = j;
        this.wU = str3;
        this.wV = j2;
        this.wW = str4;
        this.wX = i2;
        this.xd = i6;
        this.wo = i3;
        this.wY = i4;
        this.wZ = bArr;
        this.wn = arrayList;
        this.xa = str5;
        this.xb = bArr2;
        this.xc = i5;
        this.wH = bundle;
        this.xe = z;
    }

    public TurnBasedMatchEntity(TurnBasedMatch turnBasedMatch) {
        this.kg = 2;
        this.wj = new GameEntity(turnBasedMatch.getGame());
        this.ul = turnBasedMatch.getMatchId();
        this.wL = turnBasedMatch.getCreatorId();
        this.wk = turnBasedMatch.getCreationTimestamp();
        this.wU = turnBasedMatch.getLastUpdaterId();
        this.wV = turnBasedMatch.getLastUpdatedTimestamp();
        this.wW = turnBasedMatch.getPendingParticipantId();
        this.wX = turnBasedMatch.getStatus();
        this.xd = turnBasedMatch.getTurnStatus();
        this.wo = turnBasedMatch.getVariant();
        this.wY = turnBasedMatch.getVersion();
        this.xa = turnBasedMatch.getRematchId();
        this.xc = turnBasedMatch.getMatchNumber();
        this.wH = turnBasedMatch.getAutoMatchCriteria();
        this.xe = turnBasedMatch.isLocallyModified();
        byte[] data = turnBasedMatch.getData();
        if (data == null) {
            this.wZ = null;
        } else {
            this.wZ = new byte[data.length];
            System.arraycopy(data, 0, this.wZ, 0, data.length);
        }
        byte[] previousMatchData = turnBasedMatch.getPreviousMatchData();
        if (previousMatchData == null) {
            this.xb = null;
        } else {
            this.xb = new byte[previousMatchData.length];
            System.arraycopy(previousMatchData, 0, this.xb, 0, previousMatchData.length);
        }
        ArrayList<Participant> participants = turnBasedMatch.getParticipants();
        int size = participants.size();
        this.wn = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            this.wn.add((ParticipantEntity) participants.get(i).freeze());
        }
    }

    static int a(TurnBasedMatch turnBasedMatch) {
        return ee.hashCode(turnBasedMatch.getGame(), turnBasedMatch.getMatchId(), turnBasedMatch.getCreatorId(), Long.valueOf(turnBasedMatch.getCreationTimestamp()), turnBasedMatch.getLastUpdaterId(), Long.valueOf(turnBasedMatch.getLastUpdatedTimestamp()), turnBasedMatch.getPendingParticipantId(), Integer.valueOf(turnBasedMatch.getStatus()), Integer.valueOf(turnBasedMatch.getTurnStatus()), Integer.valueOf(turnBasedMatch.getVariant()), Integer.valueOf(turnBasedMatch.getVersion()), turnBasedMatch.getParticipants(), turnBasedMatch.getRematchId(), Integer.valueOf(turnBasedMatch.getMatchNumber()), turnBasedMatch.getAutoMatchCriteria(), Integer.valueOf(turnBasedMatch.getAvailableAutoMatchSlots()), Boolean.valueOf(turnBasedMatch.isLocallyModified()));
    }

    static int a(TurnBasedMatch turnBasedMatch, String str) {
        ArrayList<Participant> participants = turnBasedMatch.getParticipants();
        int size = participants.size();
        for (int i = 0; i < size; i++) {
            Participant participant = participants.get(i);
            if (participant.getParticipantId().equals(str)) {
                return participant.getStatus();
            }
        }
        throw new IllegalStateException("Participant " + str + " is not in match " + turnBasedMatch.getMatchId());
    }

    static boolean a(TurnBasedMatch turnBasedMatch, Object obj) {
        if (!(obj instanceof TurnBasedMatch)) {
            return false;
        }
        if (turnBasedMatch == obj) {
            return true;
        }
        TurnBasedMatch turnBasedMatch2 = (TurnBasedMatch) obj;
        return ee.equal(turnBasedMatch2.getGame(), turnBasedMatch.getGame()) && ee.equal(turnBasedMatch2.getMatchId(), turnBasedMatch.getMatchId()) && ee.equal(turnBasedMatch2.getCreatorId(), turnBasedMatch.getCreatorId()) && ee.equal(Long.valueOf(turnBasedMatch2.getCreationTimestamp()), Long.valueOf(turnBasedMatch.getCreationTimestamp())) && ee.equal(turnBasedMatch2.getLastUpdaterId(), turnBasedMatch.getLastUpdaterId()) && ee.equal(Long.valueOf(turnBasedMatch2.getLastUpdatedTimestamp()), Long.valueOf(turnBasedMatch.getLastUpdatedTimestamp())) && ee.equal(turnBasedMatch2.getPendingParticipantId(), turnBasedMatch.getPendingParticipantId()) && ee.equal(Integer.valueOf(turnBasedMatch2.getStatus()), Integer.valueOf(turnBasedMatch.getStatus())) && ee.equal(Integer.valueOf(turnBasedMatch2.getTurnStatus()), Integer.valueOf(turnBasedMatch.getTurnStatus())) && ee.equal(Integer.valueOf(turnBasedMatch2.getVariant()), Integer.valueOf(turnBasedMatch.getVariant())) && ee.equal(Integer.valueOf(turnBasedMatch2.getVersion()), Integer.valueOf(turnBasedMatch.getVersion())) && ee.equal(turnBasedMatch2.getParticipants(), turnBasedMatch.getParticipants()) && ee.equal(turnBasedMatch2.getRematchId(), turnBasedMatch.getRematchId()) && ee.equal(Integer.valueOf(turnBasedMatch2.getMatchNumber()), Integer.valueOf(turnBasedMatch.getMatchNumber())) && ee.equal(turnBasedMatch2.getAutoMatchCriteria(), turnBasedMatch.getAutoMatchCriteria()) && ee.equal(Integer.valueOf(turnBasedMatch2.getAvailableAutoMatchSlots()), Integer.valueOf(turnBasedMatch.getAvailableAutoMatchSlots())) && ee.equal(Boolean.valueOf(turnBasedMatch2.isLocallyModified()), Boolean.valueOf(turnBasedMatch.isLocallyModified()));
    }

    static String b(TurnBasedMatch turnBasedMatch) {
        return ee.e(turnBasedMatch).a("Game", turnBasedMatch.getGame()).a("MatchId", turnBasedMatch.getMatchId()).a("CreatorId", turnBasedMatch.getCreatorId()).a("CreationTimestamp", Long.valueOf(turnBasedMatch.getCreationTimestamp())).a("LastUpdaterId", turnBasedMatch.getLastUpdaterId()).a("LastUpdatedTimestamp", Long.valueOf(turnBasedMatch.getLastUpdatedTimestamp())).a("PendingParticipantId", turnBasedMatch.getPendingParticipantId()).a("MatchStatus", Integer.valueOf(turnBasedMatch.getStatus())).a("TurnStatus", Integer.valueOf(turnBasedMatch.getTurnStatus())).a("Variant", Integer.valueOf(turnBasedMatch.getVariant())).a("Data", turnBasedMatch.getData()).a("Version", Integer.valueOf(turnBasedMatch.getVersion())).a("Participants", turnBasedMatch.getParticipants()).a("RematchId", turnBasedMatch.getRematchId()).a("PreviousData", turnBasedMatch.getPreviousMatchData()).a("MatchNumber", Integer.valueOf(turnBasedMatch.getMatchNumber())).a("AutoMatchCriteria", turnBasedMatch.getAutoMatchCriteria()).a("AvailableAutoMatchSlots", Integer.valueOf(turnBasedMatch.getAvailableAutoMatchSlots())).a("LocallyModified", Boolean.valueOf(turnBasedMatch.isLocallyModified())).toString();
    }

    static String b(TurnBasedMatch turnBasedMatch, String str) {
        ArrayList<Participant> participants = turnBasedMatch.getParticipants();
        int size = participants.size();
        for (int i = 0; i < size; i++) {
            Participant participant = participants.get(i);
            Player player = participant.getPlayer();
            if (player != null && player.getPlayerId().equals(str)) {
                return participant.getParticipantId();
            }
        }
        return null;
    }

    static Participant c(TurnBasedMatch turnBasedMatch, String str) {
        ArrayList<Participant> participants = turnBasedMatch.getParticipants();
        int size = participants.size();
        for (int i = 0; i < size; i++) {
            Participant participant = participants.get(i);
            if (participant.getParticipantId().equals(str)) {
                return participant;
            }
        }
        throw new IllegalStateException("Participant " + str + " is not in match " + turnBasedMatch.getMatchId());
    }

    static ArrayList<String> c(TurnBasedMatch turnBasedMatch) {
        ArrayList<Participant> participants = turnBasedMatch.getParticipants();
        int size = participants.size();
        ArrayList<String> arrayList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(participants.get(i).getParticipantId());
        }
        return arrayList;
    }

    public boolean canRematch() {
        return this.wX == 2 && this.xa == null;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return a((TurnBasedMatch) this, obj);
    }

    public TurnBasedMatch freeze() {
        return this;
    }

    public Bundle getAutoMatchCriteria() {
        return this.wH;
    }

    public int getAvailableAutoMatchSlots() {
        if (this.wH == null) {
            return 0;
        }
        return this.wH.getInt("max_automatch_players");
    }

    public long getCreationTimestamp() {
        return this.wk;
    }

    public String getCreatorId() {
        return this.wL;
    }

    public byte[] getData() {
        return this.wZ;
    }

    public Game getGame() {
        return this.wj;
    }

    public long getLastUpdatedTimestamp() {
        return this.wV;
    }

    public String getLastUpdaterId() {
        return this.wU;
    }

    public String getMatchId() {
        return this.ul;
    }

    public int getMatchNumber() {
        return this.xc;
    }

    public Participant getParticipant(String str) {
        return c(this, str);
    }

    public String getParticipantId(String str) {
        return b(this, str);
    }

    public ArrayList<String> getParticipantIds() {
        return c(this);
    }

    public int getParticipantStatus(String str) {
        return a((TurnBasedMatch) this, str);
    }

    public ArrayList<Participant> getParticipants() {
        return new ArrayList<>(this.wn);
    }

    public String getPendingParticipantId() {
        return this.wW;
    }

    public byte[] getPreviousMatchData() {
        return this.xb;
    }

    public String getRematchId() {
        return this.xa;
    }

    public int getStatus() {
        return this.wX;
    }

    public int getTurnStatus() {
        return this.xd;
    }

    public int getVariant() {
        return this.wo;
    }

    public int getVersion() {
        return this.wY;
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

    public boolean isLocallyModified() {
        return this.xe;
    }

    public String toString() {
        return b(this);
    }

    public void writeToParcel(Parcel parcel, int i) {
        TurnBasedMatchEntityCreator.a(this, parcel, i);
    }
}
