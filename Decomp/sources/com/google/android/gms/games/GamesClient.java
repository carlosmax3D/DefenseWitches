package com.google.android.gms.games;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.games.GamesMetadata;
import com.google.android.gms.games.Players;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.achievement.OnAchievementUpdatedListener;
import com.google.android.gms.games.achievement.OnAchievementsLoadedListener;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.leaderboard.OnLeaderboardMetadataLoadedListener;
import com.google.android.gms.games.leaderboard.OnLeaderboardScoresLoadedListener;
import com.google.android.gms.games.leaderboard.OnPlayerLeaderboardScoreLoadedListener;
import com.google.android.gms.games.leaderboard.OnScoreSubmittedListener;
import com.google.android.gms.games.leaderboard.SubmitScoreResult;
import com.google.android.gms.games.multiplayer.Invitations;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.OnInvitationsLoadedListener;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer;
import com.google.android.gms.games.multiplayer.realtime.RealTimeReliableMessageSentListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchCanceledListener;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchInitiatedListener;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchLeftListener;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchLoadedListener;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdateReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdatedListener;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchesLoadedListener;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.android.gms.internal.eg;
import com.google.android.gms.internal.fl;
import java.util.List;

@Deprecated
public final class GamesClient implements GooglePlayServicesClient {
    public static final String EXTRA_EXCLUSIVE_BIT_MASK = "exclusive_bit_mask";
    public static final String EXTRA_INVITATION = "invitation";
    public static final String EXTRA_MAX_AUTOMATCH_PLAYERS = "max_automatch_players";
    public static final String EXTRA_MIN_AUTOMATCH_PLAYERS = "min_automatch_players";
    public static final String EXTRA_PLAYERS = "players";
    public static final String EXTRA_PLAYER_SEARCH_RESULTS = "player_search_results";
    public static final String EXTRA_ROOM = "room";
    public static final String EXTRA_TURN_BASED_MATCH = "turn_based_match";
    public static final int MAX_RELIABLE_MESSAGE_LEN = 1400;
    public static final int MAX_UNRELIABLE_MESSAGE_LEN = 1168;
    public static final int NOTIFICATION_TYPES_ALL = -1;
    public static final int NOTIFICATION_TYPES_MULTIPLAYER = 3;
    public static final int NOTIFICATION_TYPE_INVITATION = 1;
    public static final int NOTIFICATION_TYPE_MATCH_UPDATE = 2;
    public static final int STATUS_ACHIEVEMENT_NOT_INCREMENTAL = 3002;
    public static final int STATUS_ACHIEVEMENT_UNKNOWN = 3001;
    public static final int STATUS_ACHIEVEMENT_UNLOCKED = 3003;
    public static final int STATUS_ACHIEVEMENT_UNLOCK_FAILURE = 3000;
    public static final int STATUS_APP_MISCONFIGURED = 8;
    public static final int STATUS_CLIENT_RECONNECT_REQUIRED = 2;
    public static final int STATUS_GAME_NOT_FOUND = 9;
    public static final int STATUS_INTERNAL_ERROR = 1;
    public static final int STATUS_INVALID_REAL_TIME_ROOM_ID = 7002;
    public static final int STATUS_LICENSE_CHECK_FAILED = 7;
    public static final int STATUS_MATCH_ERROR_ALREADY_REMATCHED = 6505;
    public static final int STATUS_MATCH_ERROR_INACTIVE_MATCH = 6501;
    public static final int STATUS_MATCH_ERROR_INVALID_MATCH_RESULTS = 6504;
    public static final int STATUS_MATCH_ERROR_INVALID_MATCH_STATE = 6502;
    public static final int STATUS_MATCH_ERROR_INVALID_PARTICIPANT_STATE = 6500;
    public static final int STATUS_MATCH_ERROR_LOCALLY_MODIFIED = 6507;
    public static final int STATUS_MATCH_ERROR_OUT_OF_DATE_VERSION = 6503;
    public static final int STATUS_MATCH_NOT_FOUND = 6506;
    public static final int STATUS_MULTIPLAYER_DISABLED = 6003;
    public static final int STATUS_MULTIPLAYER_ERROR_CREATION_NOT_ALLOWED = 6000;
    public static final int STATUS_MULTIPLAYER_ERROR_INVALID_MULTIPLAYER_TYPE = 6002;
    public static final int STATUS_MULTIPLAYER_ERROR_INVALID_OPERATION = 6004;
    public static final int STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER = 6001;
    public static final int STATUS_NETWORK_ERROR_NO_DATA = 4;
    public static final int STATUS_NETWORK_ERROR_OPERATION_DEFERRED = 5;
    public static final int STATUS_NETWORK_ERROR_OPERATION_FAILED = 6;
    public static final int STATUS_NETWORK_ERROR_STALE_DATA = 3;
    public static final int STATUS_OK = 0;
    public static final int STATUS_OPERATION_IN_FLIGHT = 7007;
    public static final int STATUS_PARTICIPANT_NOT_CONNECTED = 7003;
    public static final int STATUS_REAL_TIME_CONNECTION_FAILED = 7000;
    public static final int STATUS_REAL_TIME_INACTIVE_ROOM = 7005;
    public static final int STATUS_REAL_TIME_MESSAGE_FAILED = -1;
    public static final int STATUS_REAL_TIME_MESSAGE_SEND_FAILED = 7001;
    public static final int STATUS_REAL_TIME_ROOM_NOT_JOINED = 7004;
    private final fl te;

    @Deprecated
    public static final class Builder {
        private final GooglePlayServicesClient.ConnectionCallbacks jD;
        private final GooglePlayServicesClient.OnConnectionFailedListener jE;
        private String[] jF = {Scopes.GAMES};
        private String jG = "<<default account>>";
        private final Context mContext;
        private boolean tA = true;
        private int tB = 17;
        private String tx;
        private int ty = 49;
        private View tz;

        public Builder(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.mContext = context;
            this.tx = context.getPackageName();
            this.jD = connectionCallbacks;
            this.jE = onConnectionFailedListener;
        }

        public GamesClient create() {
            return new GamesClient(this.mContext, this.tx, this.jG, this.jD, this.jE, this.jF, this.ty, this.tz, this.tA, this.tB);
        }

        public Builder setAccountName(String str) {
            this.jG = (String) eg.f(str);
            return this;
        }

        public Builder setGravityForPopups(int i) {
            this.ty = i;
            return this;
        }

        public Builder setScopes(String... strArr) {
            this.jF = strArr;
            return this;
        }

        public Builder setShowConnectingPopup(boolean z) {
            this.tA = z;
            this.tB = 17;
            return this;
        }

        public Builder setShowConnectingPopup(boolean z, int i) {
            this.tA = z;
            this.tB = i;
            return this;
        }

        public Builder setViewForPopups(View view) {
            this.tz = (View) eg.f(view);
            return this;
        }
    }

    private GamesClient(Context context, String str, String str2, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, String[] strArr, int i, View view, boolean z, int i2) {
        this.te = new fl(context, str, str2, connectionCallbacks, onConnectionFailedListener, strArr, i, view, false, z, i2);
    }

    @Deprecated
    public void acceptTurnBasedInvitation(final OnTurnBasedMatchInitiatedListener onTurnBasedMatchInitiatedListener, String str) {
        this.te.e(new a.c<TurnBasedMultiplayer.InitiateMatchResult>() {
            public void a(TurnBasedMultiplayer.InitiateMatchResult initiateMatchResult) {
                onTurnBasedMatchInitiatedListener.onTurnBasedMatchInitiated(initiateMatchResult.getStatus().getStatusCode(), initiateMatchResult.getMatch());
            }
        }, str);
    }

    @Deprecated
    public void cancelTurnBasedMatch(final OnTurnBasedMatchCanceledListener onTurnBasedMatchCanceledListener, String str) {
        this.te.g(new a.c<TurnBasedMultiplayer.CancelMatchResult>() {
            public void a(TurnBasedMultiplayer.CancelMatchResult cancelMatchResult) {
                onTurnBasedMatchCanceledListener.onTurnBasedMatchCanceled(cancelMatchResult.getStatus().getStatusCode(), cancelMatchResult.getMatchId());
            }
        }, str);
    }

    @Deprecated
    public void cancelTurnBasedMatch(String str) {
        this.te.g(new a.c<TurnBasedMultiplayer.CancelMatchResult>() {
            public void a(TurnBasedMultiplayer.CancelMatchResult cancelMatchResult) {
            }
        }, str);
    }

    @Deprecated
    public void clearAllNotifications() {
        this.te.clearNotifications(-1);
    }

    @Deprecated
    public void clearNotifications(int i) {
        this.te.clearNotifications(i);
    }

    @Deprecated
    public void connect() {
        this.te.connect();
    }

    @Deprecated
    public void createRoom(RoomConfig roomConfig) {
        this.te.createRoom(roomConfig);
    }

    @Deprecated
    public void createTurnBasedMatch(final OnTurnBasedMatchInitiatedListener onTurnBasedMatchInitiatedListener, TurnBasedMatchConfig turnBasedMatchConfig) {
        this.te.a((a.c<TurnBasedMultiplayer.InitiateMatchResult>) new a.c<TurnBasedMultiplayer.InitiateMatchResult>() {
            public void a(TurnBasedMultiplayer.InitiateMatchResult initiateMatchResult) {
                onTurnBasedMatchInitiatedListener.onTurnBasedMatchInitiated(initiateMatchResult.getStatus().getStatusCode(), initiateMatchResult.getMatch());
            }
        }, turnBasedMatchConfig);
    }

    @Deprecated
    public void declineRoomInvitation(String str) {
        this.te.j(str, 0);
    }

    @Deprecated
    public void declineTurnBasedInvitation(String str) {
        this.te.j(str, 1);
    }

    @Deprecated
    public void disconnect() {
        this.te.disconnect();
    }

    @Deprecated
    public void dismissRoomInvitation(String str) {
        this.te.i(str, 0);
    }

    @Deprecated
    public void dismissTurnBasedInvitation(String str) {
        this.te.i(str, 1);
    }

    @Deprecated
    public void dismissTurnBasedMatch(String str) {
        this.te.dismissTurnBasedMatch(str);
    }

    @Deprecated
    public void finishTurnBasedMatch(final OnTurnBasedMatchUpdatedListener onTurnBasedMatchUpdatedListener, String str) {
        this.te.a((a.c<TurnBasedMultiplayer.UpdateMatchResult>) new a.c<TurnBasedMultiplayer.UpdateMatchResult>() {
            public void a(TurnBasedMultiplayer.UpdateMatchResult updateMatchResult) {
                onTurnBasedMatchUpdatedListener.onTurnBasedMatchUpdated(updateMatchResult.getStatus().getStatusCode(), updateMatchResult.getMatch());
            }
        }, str, (byte[]) null, (ParticipantResult[]) null);
    }

    @Deprecated
    public void finishTurnBasedMatch(OnTurnBasedMatchUpdatedListener onTurnBasedMatchUpdatedListener, String str, byte[] bArr, List<ParticipantResult> list) {
        finishTurnBasedMatch(onTurnBasedMatchUpdatedListener, str, bArr, list == null ? null : (ParticipantResult[]) list.toArray(new ParticipantResult[list.size()]));
    }

    @Deprecated
    public void finishTurnBasedMatch(final OnTurnBasedMatchUpdatedListener onTurnBasedMatchUpdatedListener, String str, byte[] bArr, ParticipantResult... participantResultArr) {
        this.te.a((a.c<TurnBasedMultiplayer.UpdateMatchResult>) new a.c<TurnBasedMultiplayer.UpdateMatchResult>() {
            public void a(TurnBasedMultiplayer.UpdateMatchResult updateMatchResult) {
                onTurnBasedMatchUpdatedListener.onTurnBasedMatchUpdated(updateMatchResult.getStatus().getStatusCode(), updateMatchResult.getMatch());
            }
        }, str, bArr, participantResultArr);
    }

    @Deprecated
    public Intent getAchievementsIntent() {
        return this.te.getAchievementsIntent();
    }

    @Deprecated
    public Intent getAllLeaderboardsIntent() {
        return this.te.getAllLeaderboardsIntent();
    }

    @Deprecated
    public String getAppId() {
        return this.te.getAppId();
    }

    @Deprecated
    public String getCurrentAccountName() {
        return this.te.getCurrentAccountName();
    }

    @Deprecated
    public Game getCurrentGame() {
        return this.te.getCurrentGame();
    }

    @Deprecated
    public Player getCurrentPlayer() {
        return this.te.getCurrentPlayer();
    }

    @Deprecated
    public String getCurrentPlayerId() {
        return this.te.getCurrentPlayerId();
    }

    @Deprecated
    public Intent getInvitationInboxIntent() {
        return this.te.getInvitationInboxIntent();
    }

    @Deprecated
    public Intent getLeaderboardIntent(String str) {
        return this.te.getLeaderboardIntent(str);
    }

    @Deprecated
    public Intent getMatchInboxIntent() {
        return this.te.getMatchInboxIntent();
    }

    @Deprecated
    public int getMaxTurnBasedMatchDataSize() {
        return this.te.getMaxTurnBasedMatchDataSize();
    }

    @Deprecated
    public Intent getPlayerSearchIntent() {
        return this.te.getPlayerSearchIntent();
    }

    @Deprecated
    public Intent getRealTimeSelectOpponentsIntent(int i, int i2) {
        return this.te.getRealTimeSelectOpponentsIntent(i, i2, true);
    }

    @Deprecated
    public Intent getRealTimeSelectOpponentsIntent(int i, int i2, boolean z) {
        return this.te.getRealTimeSelectOpponentsIntent(i, i2, z);
    }

    @Deprecated
    public RealTimeSocket getRealTimeSocketForParticipant(String str, String str2) {
        return this.te.getRealTimeSocketForParticipant(str, str2);
    }

    @Deprecated
    public Intent getRealTimeWaitingRoomIntent(Room room, int i) {
        return this.te.getRealTimeWaitingRoomIntent(room, i);
    }

    @Deprecated
    public Intent getSettingsIntent() {
        return this.te.getSettingsIntent();
    }

    @Deprecated
    public void getTurnBasedMatch(final OnTurnBasedMatchLoadedListener onTurnBasedMatchLoadedListener, String str) {
        this.te.h(new a.c<TurnBasedMultiplayer.LoadMatchResult>() {
            public void a(TurnBasedMultiplayer.LoadMatchResult loadMatchResult) {
                onTurnBasedMatchLoadedListener.onTurnBasedMatchLoaded(loadMatchResult.getStatus().getStatusCode(), loadMatchResult.getMatch());
            }
        }, str);
    }

    @Deprecated
    public Intent getTurnBasedSelectOpponentsIntent(int i, int i2) {
        return this.te.getTurnBasedSelectOpponentsIntent(i, i2, true);
    }

    @Deprecated
    public Intent getTurnBasedSelectOpponentsIntent(int i, int i2, boolean z) {
        return this.te.getTurnBasedSelectOpponentsIntent(i, i2, z);
    }

    @Deprecated
    public void incrementAchievement(String str, int i) {
        this.te.a((a.c<Achievements.UpdateAchievementResult>) null, str, i);
    }

    @Deprecated
    public void incrementAchievementImmediate(final OnAchievementUpdatedListener onAchievementUpdatedListener, String str, int i) {
        this.te.a((a.c<Achievements.UpdateAchievementResult>) new a.c<Achievements.UpdateAchievementResult>() {
            public void a(Achievements.UpdateAchievementResult updateAchievementResult) {
                onAchievementUpdatedListener.onAchievementUpdated(updateAchievementResult.getStatus().getStatusCode(), updateAchievementResult.getAchievementId());
            }
        }, str, i);
    }

    @Deprecated
    public boolean isConnected() {
        return this.te.isConnected();
    }

    @Deprecated
    public boolean isConnecting() {
        return this.te.isConnecting();
    }

    @Deprecated
    public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        return this.te.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    @Deprecated
    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.te.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    @Deprecated
    public void joinRoom(RoomConfig roomConfig) {
        this.te.joinRoom(roomConfig);
    }

    @Deprecated
    public void leaveRoom(RoomUpdateListener roomUpdateListener, String str) {
        this.te.leaveRoom(roomUpdateListener, str);
    }

    @Deprecated
    public void leaveTurnBasedMatch(final OnTurnBasedMatchLeftListener onTurnBasedMatchLeftListener, String str) {
        this.te.f(new a.c<TurnBasedMultiplayer.LeaveMatchResult>() {
            public void a(TurnBasedMultiplayer.LeaveMatchResult leaveMatchResult) {
                onTurnBasedMatchLeftListener.onTurnBasedMatchLeft(leaveMatchResult.getStatus().getStatusCode(), leaveMatchResult.getMatch());
            }
        }, str);
    }

    @Deprecated
    public void leaveTurnBasedMatchDuringTurn(final OnTurnBasedMatchLeftListener onTurnBasedMatchLeftListener, String str, String str2) {
        this.te.a((a.c<TurnBasedMultiplayer.LeaveMatchResult>) new a.c<TurnBasedMultiplayer.LeaveMatchResult>() {
            public void a(TurnBasedMultiplayer.LeaveMatchResult leaveMatchResult) {
                onTurnBasedMatchLeftListener.onTurnBasedMatchLeft(leaveMatchResult.getStatus().getStatusCode(), leaveMatchResult.getMatch());
            }
        }, str, str2);
    }

    @Deprecated
    public void loadAchievements(final OnAchievementsLoadedListener onAchievementsLoadedListener, boolean z) {
        this.te.b((a.c<Achievements.LoadAchievementsResult>) new a.c<Achievements.LoadAchievementsResult>() {
            public void a(Achievements.LoadAchievementsResult loadAchievementsResult) {
                onAchievementsLoadedListener.onAchievementsLoaded(loadAchievementsResult.getStatus().getStatusCode(), loadAchievementsResult.getAchievements());
            }
        }, z);
    }

    @Deprecated
    public void loadCurrentPlayerLeaderboardScore(final OnPlayerLeaderboardScoreLoadedListener onPlayerLeaderboardScoreLoadedListener, String str, int i, int i2) {
        this.te.a((a.c<Leaderboards.LoadPlayerScoreResult>) new a.c<Leaderboards.LoadPlayerScoreResult>() {
            public void a(Leaderboards.LoadPlayerScoreResult loadPlayerScoreResult) {
                onPlayerLeaderboardScoreLoadedListener.onPlayerLeaderboardScoreLoaded(loadPlayerScoreResult.getStatus().getStatusCode(), loadPlayerScoreResult.getScore());
            }
        }, (String) null, str, i, i2);
    }

    @Deprecated
    public void loadGame(final OnGamesLoadedListener onGamesLoadedListener) {
        this.te.g(new a.c<GamesMetadata.LoadGamesResult>() {
            public void a(GamesMetadata.LoadGamesResult loadGamesResult) {
                onGamesLoadedListener.onGamesLoaded(loadGamesResult.getStatus().getStatusCode(), loadGamesResult.getGames());
            }
        });
    }

    @Deprecated
    public void loadInvitablePlayers(final OnPlayersLoadedListener onPlayersLoadedListener, int i, boolean z) {
        this.te.a((a.c<Players.LoadPlayersResult>) new a.c<Players.LoadPlayersResult>() {
            public void a(Players.LoadPlayersResult loadPlayersResult) {
                onPlayersLoadedListener.onPlayersLoaded(loadPlayersResult.getStatus().getStatusCode(), loadPlayersResult.getPlayers());
            }
        }, i, false, z);
    }

    @Deprecated
    public void loadInvitations(final OnInvitationsLoadedListener onInvitationsLoadedListener) {
        this.te.h(new a.c<Invitations.LoadInvitationsResult>() {
            public void a(Invitations.LoadInvitationsResult loadInvitationsResult) {
                onInvitationsLoadedListener.onInvitationsLoaded(loadInvitationsResult.getStatus().getStatusCode(), loadInvitationsResult.getInvitations());
            }
        });
    }

    @Deprecated
    public void loadLeaderboardMetadata(final OnLeaderboardMetadataLoadedListener onLeaderboardMetadataLoadedListener, String str, boolean z) {
        this.te.a((a.c<Leaderboards.LeaderboardMetadataResult>) new a.c<Leaderboards.LeaderboardMetadataResult>() {
            public void a(Leaderboards.LeaderboardMetadataResult leaderboardMetadataResult) {
                onLeaderboardMetadataLoadedListener.onLeaderboardMetadataLoaded(leaderboardMetadataResult.getStatus().getStatusCode(), leaderboardMetadataResult.getLeaderboards());
            }
        }, str, z);
    }

    @Deprecated
    public void loadLeaderboardMetadata(final OnLeaderboardMetadataLoadedListener onLeaderboardMetadataLoadedListener, boolean z) {
        this.te.a((a.c<Leaderboards.LeaderboardMetadataResult>) new a.c<Leaderboards.LeaderboardMetadataResult>() {
            public void a(Leaderboards.LeaderboardMetadataResult leaderboardMetadataResult) {
                onLeaderboardMetadataLoadedListener.onLeaderboardMetadataLoaded(leaderboardMetadataResult.getStatus().getStatusCode(), leaderboardMetadataResult.getLeaderboards());
            }
        }, z);
    }

    @Deprecated
    public void loadMoreInvitablePlayers(final OnPlayersLoadedListener onPlayersLoadedListener, int i) {
        this.te.a((a.c<Players.LoadPlayersResult>) new a.c<Players.LoadPlayersResult>() {
            public void a(Players.LoadPlayersResult loadPlayersResult) {
                onPlayersLoadedListener.onPlayersLoaded(loadPlayersResult.getStatus().getStatusCode(), loadPlayersResult.getPlayers());
            }
        }, i, true, false);
    }

    @Deprecated
    public void loadMoreScores(final OnLeaderboardScoresLoadedListener onLeaderboardScoresLoadedListener, LeaderboardScoreBuffer leaderboardScoreBuffer, int i, int i2) {
        this.te.a((a.c<Leaderboards.LoadScoresResult>) new a.c<Leaderboards.LoadScoresResult>() {
            public void a(Leaderboards.LoadScoresResult loadScoresResult) {
                onLeaderboardScoresLoadedListener.onLeaderboardScoresLoaded(loadScoresResult.getStatus().getStatusCode(), loadScoresResult.getLeaderboard(), loadScoresResult.getScores());
            }
        }, leaderboardScoreBuffer, i, i2);
    }

    @Deprecated
    public void loadPlayer(final OnPlayersLoadedListener onPlayersLoadedListener, String str) {
        this.te.a((a.c<Players.LoadPlayersResult>) new a.c<Players.LoadPlayersResult>() {
            public void a(Players.LoadPlayersResult loadPlayersResult) {
                onPlayersLoadedListener.onPlayersLoaded(loadPlayersResult.getStatus().getStatusCode(), loadPlayersResult.getPlayers());
            }
        }, str);
    }

    @Deprecated
    public void loadPlayerCenteredScores(final OnLeaderboardScoresLoadedListener onLeaderboardScoresLoadedListener, String str, int i, int i2, int i3) {
        this.te.b(new a.c<Leaderboards.LoadScoresResult>() {
            public void a(Leaderboards.LoadScoresResult loadScoresResult) {
                onLeaderboardScoresLoadedListener.onLeaderboardScoresLoaded(loadScoresResult.getStatus().getStatusCode(), loadScoresResult.getLeaderboard(), loadScoresResult.getScores());
            }
        }, str, i, i2, i3, false);
    }

    @Deprecated
    public void loadPlayerCenteredScores(final OnLeaderboardScoresLoadedListener onLeaderboardScoresLoadedListener, String str, int i, int i2, int i3, boolean z) {
        this.te.b(new a.c<Leaderboards.LoadScoresResult>() {
            public void a(Leaderboards.LoadScoresResult loadScoresResult) {
                onLeaderboardScoresLoadedListener.onLeaderboardScoresLoaded(loadScoresResult.getStatus().getStatusCode(), loadScoresResult.getLeaderboard(), loadScoresResult.getScores());
            }
        }, str, i, i2, i3, z);
    }

    @Deprecated
    public void loadTopScores(final OnLeaderboardScoresLoadedListener onLeaderboardScoresLoadedListener, String str, int i, int i2, int i3) {
        this.te.a(new a.c<Leaderboards.LoadScoresResult>() {
            public void a(Leaderboards.LoadScoresResult loadScoresResult) {
                onLeaderboardScoresLoadedListener.onLeaderboardScoresLoaded(loadScoresResult.getStatus().getStatusCode(), loadScoresResult.getLeaderboard(), loadScoresResult.getScores());
            }
        }, str, i, i2, i3, false);
    }

    @Deprecated
    public void loadTopScores(final OnLeaderboardScoresLoadedListener onLeaderboardScoresLoadedListener, String str, int i, int i2, int i3, boolean z) {
        this.te.a(new a.c<Leaderboards.LoadScoresResult>() {
            public void a(Leaderboards.LoadScoresResult loadScoresResult) {
                onLeaderboardScoresLoadedListener.onLeaderboardScoresLoaded(loadScoresResult.getStatus().getStatusCode(), loadScoresResult.getLeaderboard(), loadScoresResult.getScores());
            }
        }, str, i, i2, i3, z);
    }

    @Deprecated
    public void loadTurnBasedMatches(final OnTurnBasedMatchesLoadedListener onTurnBasedMatchesLoadedListener, int... iArr) {
        this.te.a((a.c<TurnBasedMultiplayer.LoadMatchesResult>) new a.c<TurnBasedMultiplayer.LoadMatchesResult>() {
            public void a(TurnBasedMultiplayer.LoadMatchesResult loadMatchesResult) {
                onTurnBasedMatchesLoadedListener.onTurnBasedMatchesLoaded(loadMatchesResult.getStatus().getStatusCode(), loadMatchesResult.getMatches());
            }
        }, iArr);
    }

    @Deprecated
    public void reconnect() {
        this.te.disconnect();
        this.te.connect();
    }

    @Deprecated
    public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.te.registerConnectionCallbacks(connectionCallbacks);
    }

    @Deprecated
    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.te.registerConnectionFailedListener(onConnectionFailedListener);
    }

    @Deprecated
    public void registerInvitationListener(OnInvitationReceivedListener onInvitationReceivedListener) {
        this.te.registerInvitationListener(onInvitationReceivedListener);
    }

    @Deprecated
    public void registerMatchUpdateListener(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
        this.te.registerMatchUpdateListener(onTurnBasedMatchUpdateReceivedListener);
    }

    @Deprecated
    public void rematchTurnBasedMatch(final OnTurnBasedMatchInitiatedListener onTurnBasedMatchInitiatedListener, String str) {
        this.te.d(new a.c<TurnBasedMultiplayer.InitiateMatchResult>() {
            public void a(TurnBasedMultiplayer.InitiateMatchResult initiateMatchResult) {
                onTurnBasedMatchInitiatedListener.onTurnBasedMatchInitiated(initiateMatchResult.getStatus().getStatusCode(), initiateMatchResult.getMatch());
            }
        }, str);
    }

    @Deprecated
    public void revealAchievement(String str) {
        this.te.b((a.c<Achievements.UpdateAchievementResult>) null, str);
    }

    @Deprecated
    public void revealAchievementImmediate(final OnAchievementUpdatedListener onAchievementUpdatedListener, String str) {
        this.te.b((a.c<Achievements.UpdateAchievementResult>) new a.c<Achievements.UpdateAchievementResult>() {
            public void a(Achievements.UpdateAchievementResult updateAchievementResult) {
                onAchievementUpdatedListener.onAchievementUpdated(updateAchievementResult.getStatus().getStatusCode(), updateAchievementResult.getAchievementId());
            }
        }, str);
    }

    @Deprecated
    public int sendReliableRealTimeMessage(final RealTimeReliableMessageSentListener realTimeReliableMessageSentListener, byte[] bArr, String str, String str2) {
        return this.te.a((RealTimeMultiplayer.ReliableMessageSentCallback) new RealTimeMultiplayer.ReliableMessageSentCallback() {
            public void onRealTimeMessageSent(int i, int i2, String str) {
                realTimeReliableMessageSentListener.onRealTimeMessageSent(i, i2, str);
            }
        }, bArr, str, str2);
    }

    @Deprecated
    public int sendUnreliableRealTimeMessage(byte[] bArr, String str, String str2) {
        return this.te.a(bArr, str, new String[]{str2});
    }

    @Deprecated
    public int sendUnreliableRealTimeMessage(byte[] bArr, String str, List<String> list) {
        return this.te.a(bArr, str, (String[]) list.toArray(new String[list.size()]));
    }

    @Deprecated
    public int sendUnreliableRealTimeMessageToAll(byte[] bArr, String str) {
        return this.te.sendUnreliableRealTimeMessageToAll(bArr, str);
    }

    @Deprecated
    public void setAchievementSteps(String str, int i) {
        this.te.b((a.c<Achievements.UpdateAchievementResult>) null, str, i);
    }

    @Deprecated
    public void setAchievementStepsImmediate(final OnAchievementUpdatedListener onAchievementUpdatedListener, String str, int i) {
        this.te.b(new a.c<Achievements.UpdateAchievementResult>() {
            public void a(Achievements.UpdateAchievementResult updateAchievementResult) {
                onAchievementUpdatedListener.onAchievementUpdated(updateAchievementResult.getStatus().getStatusCode(), updateAchievementResult.getAchievementId());
            }
        }, str, i);
    }

    @Deprecated
    public void setGravityForPopups(int i) {
        this.te.setGravityForPopups(i);
    }

    @Deprecated
    public void setViewForPopups(View view) {
        eg.f(view);
        this.te.setViewForPopups(view);
    }

    @Deprecated
    public void signOut() {
        this.te.b(new a.c<Status>() {
            public void a(Status status) {
            }
        });
    }

    @Deprecated
    public void signOut(final OnSignOutCompleteListener onSignOutCompleteListener) {
        this.te.b(new a.c<Status>() {
            public void a(Status status) {
                onSignOutCompleteListener.onSignOutComplete();
            }
        });
    }

    @Deprecated
    public void submitScore(String str, long j) {
        this.te.a((a.c<Leaderboards.SubmitScoreResult>) null, str, j, (String) null);
    }

    @Deprecated
    public void submitScore(String str, long j, String str2) {
        this.te.a((a.c<Leaderboards.SubmitScoreResult>) null, str, j, str2);
    }

    @Deprecated
    public void submitScoreImmediate(final OnScoreSubmittedListener onScoreSubmittedListener, String str, long j) {
        this.te.a((a.c<Leaderboards.SubmitScoreResult>) new a.c<Leaderboards.SubmitScoreResult>() {
            public void a(Leaderboards.SubmitScoreResult submitScoreResult) {
                onScoreSubmittedListener.onScoreSubmitted(submitScoreResult.getStatus().getStatusCode(), new SubmitScoreResult(submitScoreResult.getScoreData().dx()));
            }
        }, str, j, (String) null);
    }

    @Deprecated
    public void submitScoreImmediate(final OnScoreSubmittedListener onScoreSubmittedListener, String str, long j, String str2) {
        this.te.a((a.c<Leaderboards.SubmitScoreResult>) new a.c<Leaderboards.SubmitScoreResult>() {
            public void a(Leaderboards.SubmitScoreResult submitScoreResult) {
                onScoreSubmittedListener.onScoreSubmitted(submitScoreResult.getStatus().getStatusCode(), new SubmitScoreResult(submitScoreResult.getScoreData().dx()));
            }
        }, str, j, str2);
    }

    @Deprecated
    public void takeTurn(final OnTurnBasedMatchUpdatedListener onTurnBasedMatchUpdatedListener, String str, byte[] bArr, String str2) {
        this.te.a((a.c<TurnBasedMultiplayer.UpdateMatchResult>) new a.c<TurnBasedMultiplayer.UpdateMatchResult>() {
            public void a(TurnBasedMultiplayer.UpdateMatchResult updateMatchResult) {
                onTurnBasedMatchUpdatedListener.onTurnBasedMatchUpdated(updateMatchResult.getStatus().getStatusCode(), updateMatchResult.getMatch());
            }
        }, str, bArr, str2, (ParticipantResult[]) null);
    }

    @Deprecated
    public void takeTurn(final OnTurnBasedMatchUpdatedListener onTurnBasedMatchUpdatedListener, String str, byte[] bArr, String str2, List<ParticipantResult> list) {
        this.te.a((a.c<TurnBasedMultiplayer.UpdateMatchResult>) new a.c<TurnBasedMultiplayer.UpdateMatchResult>() {
            public void a(TurnBasedMultiplayer.UpdateMatchResult updateMatchResult) {
                onTurnBasedMatchUpdatedListener.onTurnBasedMatchUpdated(updateMatchResult.getStatus().getStatusCode(), updateMatchResult.getMatch());
            }
        }, str, bArr, str2, list == null ? null : (ParticipantResult[]) list.toArray(new ParticipantResult[list.size()]));
    }

    @Deprecated
    public void takeTurn(final OnTurnBasedMatchUpdatedListener onTurnBasedMatchUpdatedListener, String str, byte[] bArr, String str2, ParticipantResult... participantResultArr) {
        this.te.a((a.c<TurnBasedMultiplayer.UpdateMatchResult>) new a.c<TurnBasedMultiplayer.UpdateMatchResult>() {
            public void a(TurnBasedMultiplayer.UpdateMatchResult updateMatchResult) {
                onTurnBasedMatchUpdatedListener.onTurnBasedMatchUpdated(updateMatchResult.getStatus().getStatusCode(), updateMatchResult.getMatch());
            }
        }, str, bArr, str2, participantResultArr);
    }

    @Deprecated
    public void unlockAchievement(String str) {
        this.te.c((a.c<Achievements.UpdateAchievementResult>) null, str);
    }

    @Deprecated
    public void unlockAchievementImmediate(final OnAchievementUpdatedListener onAchievementUpdatedListener, String str) {
        this.te.c(new a.c<Achievements.UpdateAchievementResult>() {
            public void a(Achievements.UpdateAchievementResult updateAchievementResult) {
                onAchievementUpdatedListener.onAchievementUpdated(updateAchievementResult.getStatus().getStatusCode(), updateAchievementResult.getAchievementId());
            }
        }, str);
    }

    @Deprecated
    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.te.unregisterConnectionCallbacks(connectionCallbacks);
    }

    @Deprecated
    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.te.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    @Deprecated
    public void unregisterInvitationListener() {
        this.te.unregisterInvitationListener();
    }

    @Deprecated
    public void unregisterMatchUpdateListener() {
        this.te.unregisterMatchUpdateListener();
    }
}
