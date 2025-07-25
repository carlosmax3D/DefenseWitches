package com.google.android.gms.internal;

import android.content.Context;
import android.content.Intent;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameBuffer;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.GamesMetadata;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.Players;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationBuffer;
import com.google.android.gms.games.multiplayer.Invitations;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.ParticipantUtils;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer;
import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomEntity;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.android.gms.games.multiplayer.turnbased.LoadMatchesResponse;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdateReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchBuffer;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.fp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class fl extends dw<fp> implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private final String jG;
    private boolean tA;
    private int tB;
    private final String tO;
    private final Map<String, RealTimeSocket> tP;
    private PlayerEntity tQ;
    private GameEntity tR;
    private final fq tS;
    private boolean tT;
    private final Binder tU;
    private final long tV;
    private final boolean tW;
    private final int tX;

    abstract class a extends c {
        private final ArrayList<String> tY = new ArrayList<>();

        a(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder, String[] strArr) {
            super(roomStatusUpdateListener, dataHolder);
            for (String add : strArr) {
                this.tY.add(add);
            }
        }

        /* access modifiers changed from: protected */
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            a(roomStatusUpdateListener, room, this.tY);
        }

        /* access modifiers changed from: protected */
        public abstract void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList);
    }

    final class aa extends dw<fp>.b<RoomStatusUpdateListener> {
        private final String up;

        aa(RoomStatusUpdateListener roomStatusUpdateListener, String str) {
            super(roomStatusUpdateListener);
            this.up = str;
        }

        /* renamed from: a */
        public void b(RoomStatusUpdateListener roomStatusUpdateListener) {
            if (roomStatusUpdateListener != null) {
                roomStatusUpdateListener.onP2PConnected(this.up);
            }
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }
    }

    final class ab extends dw<fp>.b<RoomStatusUpdateListener> {
        private final String up;

        ab(RoomStatusUpdateListener roomStatusUpdateListener, String str) {
            super(roomStatusUpdateListener);
            this.up = str;
        }

        /* renamed from: a */
        public void b(RoomStatusUpdateListener roomStatusUpdateListener) {
            if (roomStatusUpdateListener != null) {
                roomStatusUpdateListener.onP2PDisconnected(this.up);
            }
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }
    }

    final class ac extends a {
        ac(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder, String[] strArr) {
            super(roomStatusUpdateListener, dataHolder, strArr);
        }

        /* access modifiers changed from: protected */
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeersConnected(room, arrayList);
        }
    }

    final class ad extends a {
        ad(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder, String[] strArr) {
            super(roomStatusUpdateListener, dataHolder, strArr);
        }

        /* access modifiers changed from: protected */
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerDeclined(room, arrayList);
        }
    }

    final class ae extends a {
        ae(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder, String[] strArr) {
            super(roomStatusUpdateListener, dataHolder, strArr);
        }

        /* access modifiers changed from: protected */
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeersDisconnected(room, arrayList);
        }
    }

    final class af extends a {
        af(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder, String[] strArr) {
            super(roomStatusUpdateListener, dataHolder, strArr);
        }

        /* access modifiers changed from: protected */
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerInvitedToRoom(room, arrayList);
        }
    }

    final class ag extends a {
        ag(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder, String[] strArr) {
            super(roomStatusUpdateListener, dataHolder, strArr);
        }

        /* access modifiers changed from: protected */
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerJoined(room, arrayList);
        }
    }

    final class ah extends a {
        ah(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder, String[] strArr) {
            super(roomStatusUpdateListener, dataHolder, strArr);
        }

        /* access modifiers changed from: protected */
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerLeft(room, arrayList);
        }
    }

    final class ai extends fk {
        private final a.c<Leaderboards.LoadPlayerScoreResult> jW;

        ai(a.c<Leaderboards.LoadPlayerScoreResult> cVar) {
            this.jW = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void D(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new aj(this.jW, dataHolder));
        }
    }

    final class aj extends dw<fp>.d<a.c<Leaderboards.LoadPlayerScoreResult>> implements Leaderboards.LoadPlayerScoreResult {
        private final Status jY;
        private final com.google.android.gms.games.leaderboard.d uq;

        aj(a.c<Leaderboards.LoadPlayerScoreResult> cVar, DataHolder dataHolder) {
            super(cVar, dataHolder);
            this.jY = new Status(dataHolder.getStatusCode());
            LeaderboardScoreBuffer leaderboardScoreBuffer = new LeaderboardScoreBuffer(dataHolder);
            try {
                if (leaderboardScoreBuffer.getCount() > 0) {
                    this.uq = (com.google.android.gms.games.leaderboard.d) leaderboardScoreBuffer.get(0).freeze();
                } else {
                    this.uq = null;
                }
            } finally {
                leaderboardScoreBuffer.close();
            }
        }

        /* access modifiers changed from: protected */
        public void a(a.c<Leaderboards.LoadPlayerScoreResult> cVar, DataHolder dataHolder) {
            cVar.a(this);
        }

        public LeaderboardScore getScore() {
            return this.uq;
        }

        public Status getStatus() {
            return this.jY;
        }
    }

    final class ak extends fk {
        private final a.c<Players.LoadPlayersResult> jW;

        ak(a.c<Players.LoadPlayersResult> cVar) {
            this.jW = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void e(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new al(this.jW, dataHolder));
        }
    }

    final class al extends ao<a.c<Players.LoadPlayersResult>> implements Players.LoadPlayersResult {
        private final PlayerBuffer ur;

        al(a.c<Players.LoadPlayersResult> cVar, DataHolder dataHolder) {
            super(cVar, dataHolder);
            this.ur = new PlayerBuffer(dataHolder);
        }

        /* access modifiers changed from: protected */
        public void a(a.c<Players.LoadPlayersResult> cVar, DataHolder dataHolder) {
            cVar.a(this);
        }

        public PlayerBuffer getPlayers() {
            return this.ur;
        }
    }

    final class am extends dw<fp>.b<RealTimeMultiplayer.ReliableMessageSentCallback> {
        private final int mC;
        private final String us;
        private final int ut;

        am(RealTimeMultiplayer.ReliableMessageSentCallback reliableMessageSentCallback, int i, int i2, String str) {
            super(reliableMessageSentCallback);
            this.mC = i;
            this.ut = i2;
            this.us = str;
        }

        /* renamed from: a */
        public void b(RealTimeMultiplayer.ReliableMessageSentCallback reliableMessageSentCallback) {
            if (reliableMessageSentCallback != null) {
                reliableMessageSentCallback.onRealTimeMessageSent(this.mC, this.ut, this.us);
            }
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }
    }

    final class an extends fk {
        final RealTimeMultiplayer.ReliableMessageSentCallback uu;

        public an(RealTimeMultiplayer.ReliableMessageSentCallback reliableMessageSentCallback) {
            this.uu = reliableMessageSentCallback;
        }

        public void b(int i, int i2, String str) {
            fl.this.a((dw<T>.b<?>) new am(this.uu, i, i2, str));
        }
    }

    abstract class ao<R extends a.c<?>> extends dw<fp>.d<R> implements Releasable, Result {
        final Status jY;
        final DataHolder nE;

        public ao(R r, DataHolder dataHolder) {
            super(r, dataHolder);
            this.jY = new Status(dataHolder.getStatusCode());
            this.nE = dataHolder;
        }

        public Status getStatus() {
            return this.jY;
        }

        public void release() {
            if (this.nE != null) {
                this.nE.close();
            }
        }
    }

    final class ap extends c {
        ap(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder) {
            super(roomStatusUpdateListener, dataHolder);
        }

        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onRoomAutoMatching(room);
        }
    }

    final class aq extends fk {
        private final RoomUpdateListener uv;
        private final RoomStatusUpdateListener uw;
        private final RealTimeMessageReceivedListener ux;

        public aq(RoomUpdateListener roomUpdateListener) {
            this.uv = (RoomUpdateListener) eg.b(roomUpdateListener, (Object) "Callbacks must not be null");
            this.uw = null;
            this.ux = null;
        }

        public aq(RoomUpdateListener roomUpdateListener, RoomStatusUpdateListener roomStatusUpdateListener, RealTimeMessageReceivedListener realTimeMessageReceivedListener) {
            this.uv = (RoomUpdateListener) eg.b(roomUpdateListener, (Object) "Callbacks must not be null");
            this.uw = roomStatusUpdateListener;
            this.ux = realTimeMessageReceivedListener;
        }

        public void a(DataHolder dataHolder, String[] strArr) {
            fl.this.a((dw<T>.b<?>) new af(this.uw, dataHolder, strArr));
        }

        public void b(DataHolder dataHolder, String[] strArr) {
            fl.this.a((dw<T>.b<?>) new ag(this.uw, dataHolder, strArr));
        }

        public void c(DataHolder dataHolder, String[] strArr) {
            fl.this.a((dw<T>.b<?>) new ah(this.uw, dataHolder, strArr));
        }

        public void d(DataHolder dataHolder, String[] strArr) {
            fl.this.a((dw<T>.b<?>) new ad(this.uw, dataHolder, strArr));
        }

        public void e(DataHolder dataHolder, String[] strArr) {
            fl.this.a((dw<T>.b<?>) new ac(this.uw, dataHolder, strArr));
        }

        public void f(DataHolder dataHolder, String[] strArr) {
            fl.this.a((dw<T>.b<?>) new ae(this.uw, dataHolder, strArr));
        }

        public void onLeftRoom(int i, String str) {
            fl.this.a((dw<T>.b<?>) new v(this.uv, i, str));
        }

        public void onP2PConnected(String str) {
            fl.this.a((dw<T>.b<?>) new aa(this.uw, str));
        }

        public void onP2PDisconnected(String str) {
            fl.this.a((dw<T>.b<?>) new ab(this.uw, str));
        }

        public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) {
            fl.this.a((dw<T>.b<?>) new z(this.ux, realTimeMessage));
        }

        public void t(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new at(this.uv, dataHolder));
        }

        public void u(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new q(this.uv, dataHolder));
        }

        public void v(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new as(this.uw, dataHolder));
        }

        public void w(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new ap(this.uw, dataHolder));
        }

        public void x(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new ar(this.uv, dataHolder));
        }

        public void y(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new h(this.uw, dataHolder));
        }

        public void z(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new i(this.uw, dataHolder));
        }
    }

    final class ar extends b {
        ar(RoomUpdateListener roomUpdateListener, DataHolder dataHolder) {
            super(roomUpdateListener, dataHolder);
        }

        public void a(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onRoomConnected(i, room);
        }
    }

    final class as extends c {
        as(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder) {
            super(roomStatusUpdateListener, dataHolder);
        }

        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onRoomConnecting(room);
        }
    }

    final class at extends b {
        public at(RoomUpdateListener roomUpdateListener, DataHolder dataHolder) {
            super(roomUpdateListener, dataHolder);
        }

        public void a(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onRoomCreated(i, room);
        }
    }

    final class au extends fk {
        private final a.c<Status> jW;

        public au(a.c<Status> cVar) {
            this.jW = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void onSignOutComplete() {
            fl.this.a((dw<T>.b<?>) new av(this.jW, new Status(0)));
        }
    }

    final class av extends dw<fp>.b<a.c<Status>> {
        private final Status jY;

        public av(a.c<Status> cVar, Status status) {
            super(cVar);
            this.jY = status;
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }

        /* renamed from: c */
        public void b(a.c<Status> cVar) {
            cVar.a(this.jY);
        }
    }

    final class aw extends fk {
        private final a.c<Leaderboards.SubmitScoreResult> jW;

        public aw(a.c<Leaderboards.SubmitScoreResult> cVar) {
            this.jW = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void d(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new ax(this.jW, dataHolder));
        }
    }

    final class ax extends ao<a.c<Leaderboards.SubmitScoreResult>> implements Leaderboards.SubmitScoreResult {
        private final ScoreSubmissionData uy;

        public ax(a.c<Leaderboards.SubmitScoreResult> cVar, DataHolder dataHolder) {
            super(cVar, dataHolder);
            this.uy = new ScoreSubmissionData(dataHolder);
        }

        public void a(a.c<Leaderboards.SubmitScoreResult> cVar, DataHolder dataHolder) {
            cVar.a(this);
        }

        public ScoreSubmissionData getScoreData() {
            return this.uy;
        }
    }

    abstract class ay<T extends a.c<?>> extends ao<T> {
        final TurnBasedMatch un;

        ay(T t, DataHolder dataHolder) {
            super(t, dataHolder);
            TurnBasedMatchBuffer turnBasedMatchBuffer = new TurnBasedMatchBuffer(dataHolder);
            try {
                if (turnBasedMatchBuffer.getCount() > 0) {
                    this.un = (TurnBasedMatch) ((TurnBasedMatch) turnBasedMatchBuffer.get(0)).freeze();
                } else {
                    this.un = null;
                }
            } finally {
                turnBasedMatchBuffer.close();
            }
        }

        /* access modifiers changed from: protected */
        public void a(T t, DataHolder dataHolder) {
            i(t);
        }

        public TurnBasedMatch getMatch() {
            return this.un;
        }

        /* access modifiers changed from: package-private */
        public abstract void i(T t);
    }

    final class az extends fk {
        private final a.c<TurnBasedMultiplayer.CancelMatchResult> uz;

        public az(a.c<TurnBasedMultiplayer.CancelMatchResult> cVar) {
            this.uz = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void onTurnBasedMatchCanceled(int i, String str) {
            fl.this.a((dw<T>.b<?>) new ba(this.uz, new Status(i), str));
        }
    }

    abstract class b extends dw<fp>.d<RoomUpdateListener> {
        b(RoomUpdateListener roomUpdateListener, DataHolder dataHolder) {
            super(roomUpdateListener, dataHolder);
        }

        /* access modifiers changed from: protected */
        public void a(RoomUpdateListener roomUpdateListener, DataHolder dataHolder) {
            a(roomUpdateListener, fl.this.E(dataHolder), dataHolder.getStatusCode());
        }

        /* access modifiers changed from: protected */
        public abstract void a(RoomUpdateListener roomUpdateListener, Room room, int i);
    }

    final class ba extends dw<fp>.b<a.c<TurnBasedMultiplayer.CancelMatchResult>> implements TurnBasedMultiplayer.CancelMatchResult {
        private final Status jY;
        private final String uA;

        ba(a.c<TurnBasedMultiplayer.CancelMatchResult> cVar, Status status, String str) {
            super(cVar);
            this.jY = status;
            this.uA = str;
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }

        /* renamed from: c */
        public void b(a.c<TurnBasedMultiplayer.CancelMatchResult> cVar) {
            cVar.a(this);
        }

        public String getMatchId() {
            return this.uA;
        }

        public Status getStatus() {
            return this.jY;
        }
    }

    final class bb extends fk {
        private final a.c<TurnBasedMultiplayer.InitiateMatchResult> uB;

        public bb(a.c<TurnBasedMultiplayer.InitiateMatchResult> cVar) {
            this.uB = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void n(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new bc(this.uB, dataHolder));
        }
    }

    final class bc extends ay<a.c<TurnBasedMultiplayer.InitiateMatchResult>> implements TurnBasedMultiplayer.InitiateMatchResult {
        bc(a.c<TurnBasedMultiplayer.InitiateMatchResult> cVar, DataHolder dataHolder) {
            super(cVar, dataHolder);
        }

        /* access modifiers changed from: protected */
        public void i(a.c<TurnBasedMultiplayer.InitiateMatchResult> cVar) {
            cVar.a(this);
        }
    }

    final class bd extends fk {
        private final a.c<TurnBasedMultiplayer.LeaveMatchResult> uC;

        public bd(a.c<TurnBasedMultiplayer.LeaveMatchResult> cVar) {
            this.uC = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void p(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new be(this.uC, dataHolder));
        }
    }

    final class be extends ay<a.c<TurnBasedMultiplayer.LeaveMatchResult>> implements TurnBasedMultiplayer.LeaveMatchResult {
        be(a.c<TurnBasedMultiplayer.LeaveMatchResult> cVar, DataHolder dataHolder) {
            super(cVar, dataHolder);
        }

        /* access modifiers changed from: protected */
        public void i(a.c<TurnBasedMultiplayer.LeaveMatchResult> cVar) {
            cVar.a(this);
        }
    }

    final class bf extends fk {
        private final a.c<TurnBasedMultiplayer.LoadMatchResult> uD;

        public bf(a.c<TurnBasedMultiplayer.LoadMatchResult> cVar) {
            this.uD = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void m(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new bg(this.uD, dataHolder));
        }
    }

    final class bg extends ay<a.c<TurnBasedMultiplayer.LoadMatchResult>> implements TurnBasedMultiplayer.LoadMatchResult {
        bg(a.c<TurnBasedMultiplayer.LoadMatchResult> cVar, DataHolder dataHolder) {
            super(cVar, dataHolder);
        }

        /* access modifiers changed from: protected */
        public void i(a.c<TurnBasedMultiplayer.LoadMatchResult> cVar) {
            cVar.a(this);
        }
    }

    final class bh extends fk {
        private final a.c<TurnBasedMultiplayer.UpdateMatchResult> uE;

        public bh(a.c<TurnBasedMultiplayer.UpdateMatchResult> cVar) {
            this.uE = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void o(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new bi(this.uE, dataHolder));
        }
    }

    final class bi extends ay<a.c<TurnBasedMultiplayer.UpdateMatchResult>> implements TurnBasedMultiplayer.UpdateMatchResult {
        bi(a.c<TurnBasedMultiplayer.UpdateMatchResult> cVar, DataHolder dataHolder) {
            super(cVar, dataHolder);
        }

        /* access modifiers changed from: protected */
        public void i(a.c<TurnBasedMultiplayer.UpdateMatchResult> cVar) {
            cVar.a(this);
        }
    }

    final class bj extends fk {
        private final a.c<TurnBasedMultiplayer.LoadMatchesResult> uF;

        public bj(a.c<TurnBasedMultiplayer.LoadMatchesResult> cVar) {
            this.uF = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void a(int i, Bundle bundle) {
            bundle.setClassLoader(getClass().getClassLoader());
            fl.this.a((dw<T>.b<?>) new bk(this.uF, new Status(i), bundle));
        }
    }

    final class bk extends dw<fp>.b<a.c<TurnBasedMultiplayer.LoadMatchesResult>> implements TurnBasedMultiplayer.LoadMatchesResult {
        private final Status jY;
        private final LoadMatchesResponse uG;

        bk(a.c<TurnBasedMultiplayer.LoadMatchesResult> cVar, Status status, Bundle bundle) {
            super(cVar);
            this.jY = status;
            this.uG = new LoadMatchesResponse(bundle);
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: c */
        public void b(a.c<TurnBasedMultiplayer.LoadMatchesResult> cVar) {
            cVar.a(this);
        }

        public LoadMatchesResponse getMatches() {
            return this.uG;
        }

        public Status getStatus() {
            return this.jY;
        }

        public void release() {
            this.uG.close();
        }
    }

    abstract class c extends dw<fp>.d<RoomStatusUpdateListener> {
        c(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder) {
            super(roomStatusUpdateListener, dataHolder);
        }

        /* access modifiers changed from: protected */
        public void a(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder) {
            a(roomStatusUpdateListener, fl.this.E(dataHolder));
        }

        /* access modifiers changed from: protected */
        public abstract void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room);
    }

    final class d extends fk {
        private final a.c<Achievements.UpdateAchievementResult> jW;

        d(a.c<Achievements.UpdateAchievementResult> cVar) {
            this.jW = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void onAchievementUpdated(int i, String str) {
            fl.this.a((dw<T>.b<?>) new e(this.jW, i, str));
        }
    }

    final class e extends dw<fp>.b<a.c<Achievements.UpdateAchievementResult>> implements Achievements.UpdateAchievementResult {
        private final Status jY;
        private final String ua;

        e(a.c<Achievements.UpdateAchievementResult> cVar, int i, String str) {
            super(cVar);
            this.jY = new Status(i);
            this.ua = str;
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: c */
        public void b(a.c<Achievements.UpdateAchievementResult> cVar) {
            cVar.a(this);
        }

        public String getAchievementId() {
            return this.ua;
        }

        public Status getStatus() {
            return this.jY;
        }
    }

    final class f extends fk {
        private final a.c<Achievements.LoadAchievementsResult> jW;

        f(a.c<Achievements.LoadAchievementsResult> cVar) {
            this.jW = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void b(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new g(this.jW, dataHolder));
        }
    }

    final class g extends ao<a.c<Achievements.LoadAchievementsResult>> implements Achievements.LoadAchievementsResult {
        private final AchievementBuffer ub;

        g(a.c<Achievements.LoadAchievementsResult> cVar, DataHolder dataHolder) {
            super(cVar, dataHolder);
            this.ub = new AchievementBuffer(dataHolder);
        }

        /* access modifiers changed from: protected */
        public void a(a.c<Achievements.LoadAchievementsResult> cVar, DataHolder dataHolder) {
            cVar.a(this);
        }

        public AchievementBuffer getAchievements() {
            return this.ub;
        }
    }

    final class h extends c {
        h(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder) {
            super(roomStatusUpdateListener, dataHolder);
        }

        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onConnectedToRoom(room);
        }
    }

    final class i extends c {
        i(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder) {
            super(roomStatusUpdateListener, dataHolder);
        }

        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onDisconnectedFromRoom(room);
        }
    }

    final class j extends fk {
        private final a.c<GamesMetadata.LoadGamesResult> jW;

        j(a.c<GamesMetadata.LoadGamesResult> cVar) {
            this.jW = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void g(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new k(this.jW, dataHolder));
        }
    }

    final class k extends ao<a.c<GamesMetadata.LoadGamesResult>> implements GamesMetadata.LoadGamesResult {
        private final GameBuffer uc;

        k(a.c<GamesMetadata.LoadGamesResult> cVar, DataHolder dataHolder) {
            super(cVar, dataHolder);
            this.uc = new GameBuffer(dataHolder);
        }

        /* access modifiers changed from: protected */
        public void a(a.c<GamesMetadata.LoadGamesResult> cVar, DataHolder dataHolder) {
            cVar.a(this);
        }

        public GameBuffer getGames() {
            return this.uc;
        }
    }

    final class l extends fk {
        private final OnInvitationReceivedListener ud;

        l(OnInvitationReceivedListener onInvitationReceivedListener) {
            this.ud = onInvitationReceivedListener;
        }

        public void l(DataHolder dataHolder) {
            InvitationBuffer invitationBuffer = new InvitationBuffer(dataHolder);
            Invitation invitation = null;
            try {
                if (invitationBuffer.getCount() > 0) {
                    invitation = (Invitation) ((Invitation) invitationBuffer.get(0)).freeze();
                }
                if (invitation != null) {
                    fl.this.a((dw<T>.b<?>) new m(this.ud, invitation));
                }
            } finally {
                invitationBuffer.close();
            }
        }

        public void onInvitationRemoved(String str) {
            fl.this.a((dw<T>.b<?>) new n(this.ud, str));
        }
    }

    final class m extends dw<fp>.b<OnInvitationReceivedListener> {
        private final Invitation ue;

        m(OnInvitationReceivedListener onInvitationReceivedListener, Invitation invitation) {
            super(onInvitationReceivedListener);
            this.ue = invitation;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void b(OnInvitationReceivedListener onInvitationReceivedListener) {
            onInvitationReceivedListener.onInvitationReceived(this.ue);
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }
    }

    final class n extends dw<fp>.b<OnInvitationReceivedListener> {
        private final String uf;

        n(OnInvitationReceivedListener onInvitationReceivedListener, String str) {
            super(onInvitationReceivedListener);
            this.uf = str;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void b(OnInvitationReceivedListener onInvitationReceivedListener) {
            onInvitationReceivedListener.onInvitationRemoved(this.uf);
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }
    }

    final class o extends fk {
        private final a.c<Invitations.LoadInvitationsResult> jW;

        o(a.c<Invitations.LoadInvitationsResult> cVar) {
            this.jW = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void k(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new p(this.jW, dataHolder));
        }
    }

    final class p extends ao<a.c<Invitations.LoadInvitationsResult>> implements Invitations.LoadInvitationsResult {
        private final InvitationBuffer ug;

        p(a.c<Invitations.LoadInvitationsResult> cVar, DataHolder dataHolder) {
            super(cVar, dataHolder);
            this.ug = new InvitationBuffer(dataHolder);
        }

        /* access modifiers changed from: protected */
        public void a(a.c<Invitations.LoadInvitationsResult> cVar, DataHolder dataHolder) {
            cVar.a(this);
        }

        public InvitationBuffer getInvitations() {
            return this.ug;
        }
    }

    final class q extends b {
        public q(RoomUpdateListener roomUpdateListener, DataHolder dataHolder) {
            super(roomUpdateListener, dataHolder);
        }

        public void a(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onJoinedRoom(i, room);
        }
    }

    final class r extends fk {
        private final a.c<Leaderboards.LoadScoresResult> jW;

        r(a.c<Leaderboards.LoadScoresResult> cVar) {
            this.jW = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void a(DataHolder dataHolder, DataHolder dataHolder2) {
            fl.this.a((dw<T>.b<?>) new s(this.jW, dataHolder, dataHolder2));
        }
    }

    final class s extends ao<a.c<Leaderboards.LoadScoresResult>> implements Leaderboards.LoadScoresResult {
        private final com.google.android.gms.games.leaderboard.a uh;
        private final LeaderboardScoreBuffer ui;

        /* JADX INFO: finally extract failed */
        s(a.c<Leaderboards.LoadScoresResult> cVar, DataHolder dataHolder, DataHolder dataHolder2) {
            super(cVar, dataHolder2);
            LeaderboardBuffer leaderboardBuffer = new LeaderboardBuffer(dataHolder);
            try {
                if (leaderboardBuffer.getCount() > 0) {
                    this.uh = (com.google.android.gms.games.leaderboard.a) ((Leaderboard) leaderboardBuffer.get(0)).freeze();
                } else {
                    this.uh = null;
                }
                leaderboardBuffer.close();
                this.ui = new LeaderboardScoreBuffer(dataHolder2);
            } catch (Throwable th) {
                leaderboardBuffer.close();
                throw th;
            }
        }

        /* access modifiers changed from: protected */
        public void a(a.c<Leaderboards.LoadScoresResult> cVar, DataHolder dataHolder) {
            cVar.a(this);
        }

        public Leaderboard getLeaderboard() {
            return this.uh;
        }

        public LeaderboardScoreBuffer getScores() {
            return this.ui;
        }
    }

    final class t extends fk {
        private final a.c<Leaderboards.LeaderboardMetadataResult> jW;

        t(a.c<Leaderboards.LeaderboardMetadataResult> cVar) {
            this.jW = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void c(DataHolder dataHolder) {
            fl.this.a((dw<T>.b<?>) new u(this.jW, dataHolder));
        }
    }

    final class u extends ao<a.c<Leaderboards.LeaderboardMetadataResult>> implements Leaderboards.LeaderboardMetadataResult {
        private final LeaderboardBuffer uj;

        u(a.c<Leaderboards.LeaderboardMetadataResult> cVar, DataHolder dataHolder) {
            super(cVar, dataHolder);
            this.uj = new LeaderboardBuffer(dataHolder);
        }

        /* access modifiers changed from: protected */
        public void a(a.c<Leaderboards.LeaderboardMetadataResult> cVar, DataHolder dataHolder) {
            cVar.a(this);
        }

        public LeaderboardBuffer getLeaderboards() {
            return this.uj;
        }
    }

    final class v extends dw<fp>.b<RoomUpdateListener> {
        private final int mC;
        private final String uk;

        v(RoomUpdateListener roomUpdateListener, int i, String str) {
            super(roomUpdateListener);
            this.mC = i;
            this.uk = str;
        }

        /* renamed from: a */
        public void b(RoomUpdateListener roomUpdateListener) {
            roomUpdateListener.onLeftRoom(this.mC, this.uk);
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }
    }

    final class w extends dw<fp>.b<OnTurnBasedMatchUpdateReceivedListener> {
        private final String ul;

        w(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener, String str) {
            super(onTurnBasedMatchUpdateReceivedListener);
            this.ul = str;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void b(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
            onTurnBasedMatchUpdateReceivedListener.onTurnBasedMatchRemoved(this.ul);
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }
    }

    final class x extends fk {
        private final OnTurnBasedMatchUpdateReceivedListener um;

        x(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
            this.um = onTurnBasedMatchUpdateReceivedListener;
        }

        public void onTurnBasedMatchRemoved(String str) {
            fl.this.a((dw<T>.b<?>) new w(this.um, str));
        }

        public void q(DataHolder dataHolder) {
            TurnBasedMatchBuffer turnBasedMatchBuffer = new TurnBasedMatchBuffer(dataHolder);
            TurnBasedMatch turnBasedMatch = null;
            try {
                if (turnBasedMatchBuffer.getCount() > 0) {
                    turnBasedMatch = (TurnBasedMatch) ((TurnBasedMatch) turnBasedMatchBuffer.get(0)).freeze();
                }
                if (turnBasedMatch != null) {
                    fl.this.a((dw<T>.b<?>) new y(this.um, turnBasedMatch));
                }
            } finally {
                turnBasedMatchBuffer.close();
            }
        }
    }

    final class y extends dw<fp>.b<OnTurnBasedMatchUpdateReceivedListener> {
        private final TurnBasedMatch un;

        y(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener, TurnBasedMatch turnBasedMatch) {
            super(onTurnBasedMatchUpdateReceivedListener);
            this.un = turnBasedMatch;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void b(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
            onTurnBasedMatchUpdateReceivedListener.onTurnBasedMatchReceived(this.un);
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }
    }

    final class z extends dw<fp>.b<RealTimeMessageReceivedListener> {
        private final RealTimeMessage uo;

        z(RealTimeMessageReceivedListener realTimeMessageReceivedListener, RealTimeMessage realTimeMessage) {
            super(realTimeMessageReceivedListener);
            this.uo = realTimeMessage;
        }

        /* renamed from: a */
        public void b(RealTimeMessageReceivedListener realTimeMessageReceivedListener) {
            if (realTimeMessageReceivedListener != null) {
                realTimeMessageReceivedListener.onRealTimeMessageReceived(this.uo);
            }
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }
    }

    @Deprecated
    public fl(Context context, String str, String str2, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, String[] strArr, int i2, View view, boolean z2, boolean z3, int i3) {
        this(context, str, str2, new dw.c(connectionCallbacks), new dw.g(onConnectionFailedListener), strArr, i2, view, z2, z3, i3, 4368);
    }

    public fl(Context context, String str, String str2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String[] strArr, int i2, View view, boolean z2, boolean z3, int i3, int i4) {
        super(context, connectionCallbacks, onConnectionFailedListener, strArr);
        this.tT = false;
        this.tA = false;
        this.tO = str;
        this.jG = (String) eg.f(str2);
        this.tU = new Binder();
        this.tP = new HashMap();
        this.tS = fq.a(this, i2);
        setViewForPopups(view);
        this.tA = z3;
        this.tB = i3;
        this.tV = (long) hashCode();
        this.tW = z2;
        this.tX = i4;
        registerConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this);
        registerConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this);
    }

    /* access modifiers changed from: private */
    public Room E(DataHolder dataHolder) {
        com.google.android.gms.games.multiplayer.realtime.a aVar = new com.google.android.gms.games.multiplayer.realtime.a(dataHolder);
        Room room = null;
        try {
            if (aVar.getCount() > 0) {
                room = (Room) ((Room) aVar.get(0)).freeze();
            }
            return room;
        } finally {
            aVar.close();
        }
    }

    private RealTimeSocket ae(String str) {
        try {
            String ag2 = ((fp) bQ()).ag(str);
            if (ag2 == null) {
                return null;
            }
            LocalSocket localSocket = new LocalSocket();
            try {
                localSocket.connect(new LocalSocketAddress(ag2));
                fr frVar = new fr(localSocket, str);
                this.tP.put(str, frVar);
                return frVar;
            } catch (IOException e2) {
                fn.d("GamesClientImpl", "connect() call failed on socket: " + e2.getMessage());
                return null;
            }
        } catch (RemoteException e3) {
            fn.d("GamesClientImpl", "Unable to create socket. Service died.");
            return null;
        }
    }

    private void dc() {
        this.tQ = null;
    }

    private void de() {
        for (RealTimeSocket close : this.tP.values()) {
            try {
                close.close();
            } catch (IOException e2) {
                fn.a("GamesClientImpl", "IOException:", e2);
            }
        }
        this.tP.clear();
    }

    /* access modifiers changed from: protected */
    /* renamed from: F */
    public fp p(IBinder iBinder) {
        return fp.a.H(iBinder);
    }

    public int a(RealTimeMultiplayer.ReliableMessageSentCallback reliableMessageSentCallback, byte[] bArr, String str, String str2) {
        try {
            return ((fp) bQ()).a((fo) new an(reliableMessageSentCallback), bArr, str, str2);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return -1;
        }
    }

    public int a(byte[] bArr, String str, String[] strArr) {
        eg.b(strArr, (Object) "Participant IDs must not be null");
        try {
            return ((fp) bQ()).b(bArr, str, strArr);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return -1;
        }
    }

    /* access modifiers changed from: protected */
    public void a(int i2, IBinder iBinder, Bundle bundle) {
        if (i2 == 0 && bundle != null) {
            this.tT = bundle.getBoolean("show_welcome_popup");
        }
        super.a(i2, iBinder, bundle);
    }

    public void a(IBinder iBinder, Bundle bundle) {
        if (isConnected()) {
            try {
                ((fp) bQ()).a(iBinder, bundle);
            } catch (RemoteException e2) {
                fn.c("GamesClientImpl", "service died");
            }
        }
    }

    public void a(a.c<Players.LoadPlayersResult> cVar, int i2, boolean z2, boolean z3) {
        try {
            ((fp) bQ()).a((fo) new ak(cVar), i2, z2, z3);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void a(a.c<Leaderboards.LoadScoresResult> cVar, LeaderboardScoreBuffer leaderboardScoreBuffer, int i2, int i3) {
        try {
            ((fp) bQ()).a((fo) new r(cVar), leaderboardScoreBuffer.dq().dr(), i2, i3);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void a(a.c<TurnBasedMultiplayer.InitiateMatchResult> cVar, TurnBasedMatchConfig turnBasedMatchConfig) {
        try {
            ((fp) bQ()).a((fo) new bb(cVar), turnBasedMatchConfig.getVariant(), turnBasedMatchConfig.getMinPlayers(), turnBasedMatchConfig.getInvitedPlayerIds(), turnBasedMatchConfig.getAutoMatchCriteria());
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void a(a.c<Players.LoadPlayersResult> cVar, String str) {
        try {
            ((fp) bQ()).c((fo) new ak(cVar), str);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void a(a.c<Achievements.UpdateAchievementResult> cVar, String str, int i2) {
        try {
            ((fp) bQ()).a((fo) cVar == null ? null : new d(cVar), str, i2, this.tS.dn(), this.tS.dm());
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void a(a.c<Leaderboards.LoadScoresResult> cVar, String str, int i2, int i3, int i4, boolean z2) {
        try {
            ((fp) bQ()).a((fo) new r(cVar), str, i2, i3, i4, z2);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void a(a.c<Players.LoadPlayersResult> cVar, String str, int i2, boolean z2, boolean z3) {
        if (!str.equals("playedWith")) {
            throw new IllegalArgumentException("Invalid player collection: " + str);
        }
        try {
            ((fp) bQ()).d(new ak(cVar), str, i2, z2, z3);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void a(a.c<Leaderboards.SubmitScoreResult> cVar, String str, long j2, String str2) {
        try {
            ((fp) bQ()).a((fo) cVar == null ? null : new aw(cVar), str, j2, str2);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void a(a.c<TurnBasedMultiplayer.LeaveMatchResult> cVar, String str, String str2) {
        try {
            ((fp) bQ()).d((fo) new bd(cVar), str, str2);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void a(a.c<Leaderboards.LoadPlayerScoreResult> cVar, String str, String str2, int i2, int i3) {
        try {
            ((fp) bQ()).a((fo) new ai(cVar), str, str2, i2, i3);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void a(a.c<Leaderboards.LeaderboardMetadataResult> cVar, String str, boolean z2) {
        try {
            ((fp) bQ()).c((fo) new t(cVar), str, z2);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void a(a.c<TurnBasedMultiplayer.UpdateMatchResult> cVar, String str, byte[] bArr, String str2, ParticipantResult[] participantResultArr) {
        try {
            ((fp) bQ()).a((fo) new bh(cVar), str, bArr, str2, participantResultArr);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void a(a.c<TurnBasedMultiplayer.UpdateMatchResult> cVar, String str, byte[] bArr, ParticipantResult[] participantResultArr) {
        try {
            ((fp) bQ()).a((fo) new bh(cVar), str, bArr, participantResultArr);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void a(a.c<Leaderboards.LeaderboardMetadataResult> cVar, boolean z2) {
        try {
            ((fp) bQ()).b((fo) new t(cVar), z2);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void a(a.c<TurnBasedMultiplayer.LoadMatchesResult> cVar, int[] iArr) {
        try {
            ((fp) bQ()).a((fo) new bj(cVar), iArr);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    /* access modifiers changed from: protected */
    public void a(ec ecVar, dw.e eVar) throws RemoteException {
        String locale = getContext().getResources().getConfiguration().locale.toString();
        Bundle bundle = new Bundle();
        bundle.putBoolean("com.google.android.gms.games.key.isHeadless", this.tW);
        bundle.putBoolean("com.google.android.gms.games.key.showConnectingPopup", this.tA);
        bundle.putInt("com.google.android.gms.games.key.connectingPopupGravity", this.tB);
        bundle.putInt("com.google.android.gms.games.key.sdkVariant", this.tX);
        ecVar.a(eVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), this.jG, bO(), this.tO, this.tS.dn(), locale, bundle);
    }

    /* access modifiers changed from: protected */
    public void a(String... strArr) {
        boolean z2 = false;
        boolean z3 = false;
        for (String str : strArr) {
            if (str.equals(Scopes.GAMES)) {
                z3 = true;
            } else if (str.equals("https://www.googleapis.com/auth/games.firstparty")) {
                z2 = true;
            }
        }
        if (z2) {
            eg.a(!z3, String.format("Cannot have both %s and %s!", new Object[]{Scopes.GAMES, "https://www.googleapis.com/auth/games.firstparty"}));
            return;
        }
        eg.a(z3, String.format("Games APIs requires %s to function.", new Object[]{Scopes.GAMES}));
    }

    public Bundle aU() {
        try {
            Bundle aU = ((fp) bQ()).aU();
            if (aU == null) {
                return aU;
            }
            aU.setClassLoader(fl.class.getClassLoader());
            return aU;
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public String am() {
        return "com.google.android.gms.games.service.START";
    }

    /* access modifiers changed from: protected */
    public String an() {
        return "com.google.android.gms.games.internal.IGamesService";
    }

    public void b(a.c<Status> cVar) {
        try {
            ((fp) bQ()).a(new au(cVar));
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void b(a.c<Achievements.UpdateAchievementResult> cVar, String str) {
        try {
            ((fp) bQ()).a((fo) cVar == null ? null : new d(cVar), str, this.tS.dn(), this.tS.dm());
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void b(a.c<Achievements.UpdateAchievementResult> cVar, String str, int i2) {
        try {
            ((fp) bQ()).b((fo) cVar == null ? null : new d(cVar), str, i2, this.tS.dn(), this.tS.dm());
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void b(a.c<Leaderboards.LoadScoresResult> cVar, String str, int i2, int i3, int i4, boolean z2) {
        try {
            ((fp) bQ()).b(new r(cVar), str, i2, i3, i4, z2);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void b(a.c<Achievements.LoadAchievementsResult> cVar, boolean z2) {
        try {
            ((fp) bQ()).a((fo) new f(cVar), z2);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void c(a.c<Achievements.UpdateAchievementResult> cVar, String str) {
        try {
            ((fp) bQ()).b((fo) cVar == null ? null : new d(cVar), str, this.tS.dn(), this.tS.dm());
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void clearNotifications(int i2) {
        try {
            ((fp) bQ()).clearNotifications(i2);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void connect() {
        dc();
        super.connect();
    }

    public void createRoom(RoomConfig roomConfig) {
        try {
            ((fp) bQ()).a((fo) new aq(roomConfig.getRoomUpdateListener(), roomConfig.getRoomStatusUpdateListener(), roomConfig.getMessageReceivedListener()), (IBinder) this.tU, roomConfig.getVariant(), roomConfig.getInvitedPlayerIds(), roomConfig.getAutoMatchCriteria(), roomConfig.isSocketEnabled(), this.tV);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void d(a.c<TurnBasedMultiplayer.InitiateMatchResult> cVar, String str) {
        try {
            ((fp) bQ()).n(new bb(cVar), str);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public int dd() {
        try {
            return ((fp) bQ()).dd();
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return 4368;
        }
    }

    public void df() {
        if (isConnected()) {
            try {
                ((fp) bQ()).df();
            } catch (RemoteException e2) {
                fn.c("GamesClientImpl", "service died");
            }
        }
    }

    public void disconnect() {
        this.tT = false;
        if (isConnected()) {
            try {
                fp fpVar = (fp) bQ();
                fpVar.df();
                fpVar.j(this.tV);
            } catch (RemoteException e2) {
                fn.c("GamesClientImpl", "Failed to notify client disconnect.");
            }
        }
        de();
        super.disconnect();
    }

    public void dismissTurnBasedMatch(String str) {
        try {
            ((fp) bQ()).ak(str);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void e(a.c<TurnBasedMultiplayer.InitiateMatchResult> cVar, String str) {
        try {
            ((fp) bQ()).o(new bb(cVar), str);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void f(a.c<TurnBasedMultiplayer.LeaveMatchResult> cVar, String str) {
        try {
            ((fp) bQ()).q(new bd(cVar), str);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void g(a.c<GamesMetadata.LoadGamesResult> cVar) {
        try {
            ((fp) bQ()).d(new j(cVar));
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void g(a.c<TurnBasedMultiplayer.CancelMatchResult> cVar, String str) {
        try {
            ((fp) bQ()).p(new az(cVar), str);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public Intent getAchievementsIntent() {
        try {
            return ((fp) bQ()).getAchievementsIntent();
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent getAllLeaderboardsIntent() {
        try {
            return ((fp) bQ()).getAllLeaderboardsIntent();
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return null;
        }
    }

    public String getAppId() {
        try {
            return ((fp) bQ()).getAppId();
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return null;
        }
    }

    public String getCurrentAccountName() {
        try {
            return ((fp) bQ()).getCurrentAccountName();
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return null;
        }
    }

    public Game getCurrentGame() {
        GameBuffer gameBuffer;
        bP();
        synchronized (this) {
            if (this.tR == null) {
                try {
                    gameBuffer = new GameBuffer(((fp) bQ()).di());
                    if (gameBuffer.getCount() > 0) {
                        this.tR = (GameEntity) gameBuffer.get(0).freeze();
                    }
                    gameBuffer.close();
                } catch (RemoteException e2) {
                    fn.c("GamesClientImpl", "service died");
                } catch (Throwable th) {
                    gameBuffer.close();
                    throw th;
                }
            }
        }
        return this.tR;
    }

    public Player getCurrentPlayer() {
        PlayerBuffer playerBuffer;
        bP();
        synchronized (this) {
            if (this.tQ == null) {
                try {
                    playerBuffer = new PlayerBuffer(((fp) bQ()).dg());
                    if (playerBuffer.getCount() > 0) {
                        this.tQ = (PlayerEntity) playerBuffer.get(0).freeze();
                    }
                    playerBuffer.close();
                } catch (RemoteException e2) {
                    fn.c("GamesClientImpl", "service died");
                } catch (Throwable th) {
                    playerBuffer.close();
                    throw th;
                }
            }
        }
        return this.tQ;
    }

    public String getCurrentPlayerId() {
        try {
            return ((fp) bQ()).getCurrentPlayerId();
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent getInvitationInboxIntent() {
        try {
            return ((fp) bQ()).getInvitationInboxIntent();
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent getLeaderboardIntent(String str) {
        try {
            return ((fp) bQ()).getLeaderboardIntent(str);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent getMatchInboxIntent() {
        try {
            return ((fp) bQ()).getMatchInboxIntent();
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return null;
        }
    }

    public int getMaxTurnBasedMatchDataSize() {
        try {
            return ((fp) bQ()).getMaxTurnBasedMatchDataSize();
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return 2;
        }
    }

    public Intent getPlayerSearchIntent() {
        try {
            return ((fp) bQ()).getPlayerSearchIntent();
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent getRealTimeSelectOpponentsIntent(int i2, int i3, boolean z2) {
        try {
            return ((fp) bQ()).getRealTimeSelectOpponentsIntent(i2, i3, z2);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return null;
        }
    }

    public RealTimeSocket getRealTimeSocketForParticipant(String str, String str2) {
        if (str2 == null || !ParticipantUtils.am(str2)) {
            throw new IllegalArgumentException("Bad participant ID");
        }
        RealTimeSocket realTimeSocket = this.tP.get(str2);
        return (realTimeSocket == null || realTimeSocket.isClosed()) ? ae(str2) : realTimeSocket;
    }

    public Intent getRealTimeWaitingRoomIntent(Room room, int i2) {
        try {
            return ((fp) bQ()).a((RoomEntity) room.freeze(), i2);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent getSettingsIntent() {
        try {
            return ((fp) bQ()).getSettingsIntent();
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent getTurnBasedSelectOpponentsIntent(int i2, int i3, boolean z2) {
        try {
            return ((fp) bQ()).getTurnBasedSelectOpponentsIntent(i2, i3, z2);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return null;
        }
    }

    public void h(a.c<Invitations.LoadInvitationsResult> cVar) {
        try {
            ((fp) bQ()).e((fo) new o(cVar));
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void h(a.c<TurnBasedMultiplayer.LoadMatchResult> cVar, String str) {
        try {
            ((fp) bQ()).r(new bf(cVar), str);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void i(String str, int i2) {
        try {
            ((fp) bQ()).i(str, i2);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void j(String str, int i2) {
        try {
            ((fp) bQ()).j(str, i2);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void joinRoom(RoomConfig roomConfig) {
        try {
            ((fp) bQ()).a((fo) new aq(roomConfig.getRoomUpdateListener(), roomConfig.getRoomStatusUpdateListener(), roomConfig.getMessageReceivedListener()), (IBinder) this.tU, roomConfig.getInvitationId(), roomConfig.isSocketEnabled(), this.tV);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void leaveRoom(RoomUpdateListener roomUpdateListener, String str) {
        try {
            ((fp) bQ()).e((fo) new aq(roomUpdateListener), str);
            de();
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void onConnected(Bundle bundle) {
        if (this.tT) {
            this.tS.dl();
            this.tT = false;
        }
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.tT = false;
    }

    public void onConnectionSuspended(int i2) {
    }

    public void registerInvitationListener(OnInvitationReceivedListener onInvitationReceivedListener) {
        try {
            ((fp) bQ()).a((fo) new l(onInvitationReceivedListener), this.tV);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void registerMatchUpdateListener(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
        try {
            ((fp) bQ()).b((fo) new x(onTurnBasedMatchUpdateReceivedListener), this.tV);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public int sendUnreliableRealTimeMessageToAll(byte[] bArr, String str) {
        try {
            return ((fp) bQ()).b(bArr, str, (String[]) null);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
            return -1;
        }
    }

    public void setGravityForPopups(int i2) {
        this.tS.setGravity(i2);
    }

    public void setViewForPopups(View view) {
        this.tS.e(view);
    }

    public void unregisterInvitationListener() {
        try {
            ((fp) bQ()).k(this.tV);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }

    public void unregisterMatchUpdateListener() {
        try {
            ((fp) bQ()).l(this.tV);
        } catch (RemoteException e2) {
            fn.c("GamesClientImpl", "service died");
        }
    }
}
