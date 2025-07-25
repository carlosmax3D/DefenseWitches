package com.google.android.gms.games.multiplayer.realtime;

import android.database.CharArrayBuffer;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import com.google.android.gms.internal.ee;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fm;
import java.util.ArrayList;

public final class RoomEntity extends fm implements Room {
    public static final Parcelable.Creator<RoomEntity> CREATOR = new a();
    private final int kg;
    private final String sJ;
    private final String uk;
    private final Bundle wH;
    private final String wL;
    private final int wM;
    private final int wN;
    private final long wk;
    private final ArrayList<ParticipantEntity> wn;
    private final int wo;

    static final class a extends b {
        a() {
        }

        /* renamed from: ad */
        public RoomEntity createFromParcel(Parcel parcel) {
            if (RoomEntity.c(RoomEntity.bM()) || RoomEntity.P(RoomEntity.class.getCanonicalName())) {
                return super.createFromParcel(parcel);
            }
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            long readLong = parcel.readLong();
            int readInt = parcel.readInt();
            String readString3 = parcel.readString();
            int readInt2 = parcel.readInt();
            Bundle readBundle = parcel.readBundle();
            int readInt3 = parcel.readInt();
            ArrayList arrayList = new ArrayList(readInt3);
            for (int i = 0; i < readInt3; i++) {
                arrayList.add(ParticipantEntity.CREATOR.createFromParcel(parcel));
            }
            return new RoomEntity(2, readString, readString2, readLong, readInt, readString3, readInt2, readBundle, arrayList, -1);
        }
    }

    RoomEntity(int i, String str, String str2, long j, int i2, String str3, int i3, Bundle bundle, ArrayList<ParticipantEntity> arrayList, int i4) {
        this.kg = i;
        this.uk = str;
        this.wL = str2;
        this.wk = j;
        this.wM = i2;
        this.sJ = str3;
        this.wo = i3;
        this.wH = bundle;
        this.wn = arrayList;
        this.wN = i4;
    }

    public RoomEntity(Room room) {
        this.kg = 2;
        this.uk = room.getRoomId();
        this.wL = room.getCreatorId();
        this.wk = room.getCreationTimestamp();
        this.wM = room.getStatus();
        this.sJ = room.getDescription();
        this.wo = room.getVariant();
        this.wH = room.getAutoMatchCriteria();
        ArrayList<Participant> participants = room.getParticipants();
        int size = participants.size();
        this.wn = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            this.wn.add((ParticipantEntity) participants.get(i).freeze());
        }
        this.wN = room.getAutoMatchWaitEstimateSeconds();
    }

    static int a(Room room) {
        return ee.hashCode(room.getRoomId(), room.getCreatorId(), Long.valueOf(room.getCreationTimestamp()), Integer.valueOf(room.getStatus()), room.getDescription(), Integer.valueOf(room.getVariant()), room.getAutoMatchCriteria(), room.getParticipants(), Integer.valueOf(room.getAutoMatchWaitEstimateSeconds()));
    }

    static int a(Room room, String str) {
        ArrayList<Participant> participants = room.getParticipants();
        int size = participants.size();
        for (int i = 0; i < size; i++) {
            Participant participant = participants.get(i);
            if (participant.getParticipantId().equals(str)) {
                return participant.getStatus();
            }
        }
        throw new IllegalStateException("Participant " + str + " is not in room " + room.getRoomId());
    }

    static boolean a(Room room, Object obj) {
        if (!(obj instanceof Room)) {
            return false;
        }
        if (room == obj) {
            return true;
        }
        Room room2 = (Room) obj;
        return ee.equal(room2.getRoomId(), room.getRoomId()) && ee.equal(room2.getCreatorId(), room.getCreatorId()) && ee.equal(Long.valueOf(room2.getCreationTimestamp()), Long.valueOf(room.getCreationTimestamp())) && ee.equal(Integer.valueOf(room2.getStatus()), Integer.valueOf(room.getStatus())) && ee.equal(room2.getDescription(), room.getDescription()) && ee.equal(Integer.valueOf(room2.getVariant()), Integer.valueOf(room.getVariant())) && ee.equal(room2.getAutoMatchCriteria(), room.getAutoMatchCriteria()) && ee.equal(room2.getParticipants(), room.getParticipants()) && ee.equal(Integer.valueOf(room2.getAutoMatchWaitEstimateSeconds()), Integer.valueOf(room.getAutoMatchWaitEstimateSeconds()));
    }

    static String b(Room room) {
        return ee.e(room).a("RoomId", room.getRoomId()).a("CreatorId", room.getCreatorId()).a("CreationTimestamp", Long.valueOf(room.getCreationTimestamp())).a("RoomStatus", Integer.valueOf(room.getStatus())).a("Description", room.getDescription()).a("Variant", Integer.valueOf(room.getVariant())).a("AutoMatchCriteria", room.getAutoMatchCriteria()).a("Participants", room.getParticipants()).a("AutoMatchWaitEstimateSeconds", Integer.valueOf(room.getAutoMatchWaitEstimateSeconds())).toString();
    }

    static String b(Room room, String str) {
        ArrayList<Participant> participants = room.getParticipants();
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

    static Participant c(Room room, String str) {
        ArrayList<Participant> participants = room.getParticipants();
        int size = participants.size();
        for (int i = 0; i < size; i++) {
            Participant participant = participants.get(i);
            if (participant.getParticipantId().equals(str)) {
                return participant;
            }
        }
        throw new IllegalStateException("Participant " + str + " is not in match " + room.getRoomId());
    }

    static ArrayList<String> c(Room room) {
        ArrayList<Participant> participants = room.getParticipants();
        int size = participants.size();
        ArrayList<String> arrayList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(participants.get(i).getParticipantId());
        }
        return arrayList;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return a((Room) this, obj);
    }

    public Room freeze() {
        return this;
    }

    public Bundle getAutoMatchCriteria() {
        return this.wH;
    }

    public int getAutoMatchWaitEstimateSeconds() {
        return this.wN;
    }

    public long getCreationTimestamp() {
        return this.wk;
    }

    public String getCreatorId() {
        return this.wL;
    }

    public String getDescription() {
        return this.sJ;
    }

    public void getDescription(CharArrayBuffer charArrayBuffer) {
        fc.b(this.sJ, charArrayBuffer);
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
        return a((Room) this, str);
    }

    public ArrayList<Participant> getParticipants() {
        return new ArrayList<>(this.wn);
    }

    public String getRoomId() {
        return this.uk;
    }

    public int getStatus() {
        return this.wM;
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
        return b((Room) this);
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (!bN()) {
            b.a(this, parcel, i);
            return;
        }
        parcel.writeString(this.uk);
        parcel.writeString(this.wL);
        parcel.writeLong(this.wk);
        parcel.writeInt(this.wM);
        parcel.writeString(this.sJ);
        parcel.writeInt(this.wo);
        parcel.writeBundle(this.wH);
        int size = this.wn.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            this.wn.get(i2).writeToParcel(parcel, i);
        }
    }
}
