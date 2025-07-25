package com.google.android.gms.internal;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.games.Players;

public final class fz implements Players {

    private static abstract class a extends Games.a<Players.LoadPlayersResult> {
        private a() {
        }

        /* renamed from: B */
        public Players.LoadPlayersResult e(final Status status) {
            return new Players.LoadPlayersResult() {
                public PlayerBuffer getPlayers() {
                    return new PlayerBuffer(DataHolder.empty(14));
                }

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }
    }

    public Player getCurrentPlayer(GoogleApiClient googleApiClient) {
        return Games.j(googleApiClient).getCurrentPlayer();
    }

    public String getCurrentPlayerId(GoogleApiClient googleApiClient) {
        return Games.j(googleApiClient).getCurrentPlayerId();
    }

    public Intent getPlayerSearchIntent(GoogleApiClient googleApiClient) {
        return Games.j(googleApiClient).getPlayerSearchIntent();
    }

    public PendingResult<Players.LoadPlayersResult> loadInvitablePlayers(GoogleApiClient googleApiClient, final int i, final boolean z) {
        return googleApiClient.a(new a() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.a((a.c<Players.LoadPlayersResult>) this, i, false, z);
            }
        });
    }

    public PendingResult<Players.LoadPlayersResult> loadMoreInvitablePlayers(GoogleApiClient googleApiClient, final int i) {
        return googleApiClient.a(new a() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.a((a.c<Players.LoadPlayersResult>) this, i, true, false);
            }
        });
    }

    public PendingResult<Players.LoadPlayersResult> loadMoreRecentlyPlayedWithPlayers(GoogleApiClient googleApiClient, final int i) {
        return googleApiClient.a(new a() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.a((a.c<Players.LoadPlayersResult>) this, "playedWith", i, true, false);
            }
        });
    }

    public PendingResult<Players.LoadPlayersResult> loadPlayer(GoogleApiClient googleApiClient, final String str) {
        return googleApiClient.a(new a() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.a((a.c<Players.LoadPlayersResult>) this, str);
            }
        });
    }

    public PendingResult<Players.LoadPlayersResult> loadRecentlyPlayedWithPlayers(GoogleApiClient googleApiClient, final int i, final boolean z) {
        return googleApiClient.a(new a() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.a((a.c<Players.LoadPlayersResult>) this, "playedWith", i, false, z);
            }
        });
    }
}
