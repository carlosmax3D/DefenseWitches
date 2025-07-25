package com.google.android.gms.internal;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer;
import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import java.util.List;

public final class ga implements RealTimeMultiplayer {
    public void create(GoogleApiClient googleApiClient, RoomConfig roomConfig) {
        Games.j(googleApiClient).createRoom(roomConfig);
    }

    public void declineInvitation(GoogleApiClient googleApiClient, String str) {
        Games.j(googleApiClient).j(str, 0);
    }

    public void dismissInvitation(GoogleApiClient googleApiClient, String str) {
        Games.j(googleApiClient).i(str, 0);
    }

    public Intent getSelectOpponentsIntent(GoogleApiClient googleApiClient, int i, int i2) {
        return Games.j(googleApiClient).getRealTimeSelectOpponentsIntent(i, i2, true);
    }

    public Intent getSelectOpponentsIntent(GoogleApiClient googleApiClient, int i, int i2, boolean z) {
        return Games.j(googleApiClient).getRealTimeSelectOpponentsIntent(i, i2, z);
    }

    public RealTimeSocket getSocketForParticipant(GoogleApiClient googleApiClient, String str, String str2) {
        return Games.j(googleApiClient).getRealTimeSocketForParticipant(str, str2);
    }

    public Intent getWaitingRoomIntent(GoogleApiClient googleApiClient, Room room, int i) {
        return Games.j(googleApiClient).getRealTimeWaitingRoomIntent(room, i);
    }

    public void join(GoogleApiClient googleApiClient, RoomConfig roomConfig) {
        Games.j(googleApiClient).joinRoom(roomConfig);
    }

    public void leave(GoogleApiClient googleApiClient, RoomUpdateListener roomUpdateListener, String str) {
        Games.j(googleApiClient).leaveRoom(roomUpdateListener, str);
    }

    public int sendReliableMessage(GoogleApiClient googleApiClient, RealTimeMultiplayer.ReliableMessageSentCallback reliableMessageSentCallback, byte[] bArr, String str, String str2) {
        return Games.j(googleApiClient).a(reliableMessageSentCallback, bArr, str, str2);
    }

    public int sendUnreliableMessage(GoogleApiClient googleApiClient, byte[] bArr, String str, String str2) {
        return Games.j(googleApiClient).a(bArr, str, new String[]{str2});
    }

    public int sendUnreliableMessage(GoogleApiClient googleApiClient, byte[] bArr, String str, List<String> list) {
        return Games.j(googleApiClient).a(bArr, str, (String[]) list.toArray(new String[list.size()]));
    }

    public int sendUnreliableMessageToAll(GoogleApiClient googleApiClient, byte[] bArr, String str) {
        return Games.j(googleApiClient).sendUnreliableRealTimeMessageToAll(bArr, str);
    }
}
