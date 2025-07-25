package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameBuffer;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesMetadata;

public final class fu implements GamesMetadata {

    private static abstract class a extends Games.a<GamesMetadata.LoadGamesResult> {
        private a() {
        }

        /* renamed from: v */
        public GamesMetadata.LoadGamesResult e(final Status status) {
            return new GamesMetadata.LoadGamesResult() {
                public GameBuffer getGames() {
                    return new GameBuffer(DataHolder.empty(14));
                }

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }
    }

    public Game getCurrentGame(GoogleApiClient googleApiClient) {
        return Games.j(googleApiClient).getCurrentGame();
    }

    public PendingResult<GamesMetadata.LoadGamesResult> loadGame(GoogleApiClient googleApiClient) {
        return googleApiClient.a(new a() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.g(this);
            }
        });
    }
}
