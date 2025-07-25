package com.google.android.gms.games;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.multiplayer.Invitations;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.android.gms.internal.dt;
import com.google.android.gms.internal.eg;
import com.google.android.gms.internal.fl;
import com.google.android.gms.internal.fs;
import com.google.android.gms.internal.ft;
import com.google.android.gms.internal.fu;
import com.google.android.gms.internal.fv;
import com.google.android.gms.internal.fw;
import com.google.android.gms.internal.fx;
import com.google.android.gms.internal.fy;
import com.google.android.gms.internal.fz;
import com.google.android.gms.internal.ga;
import com.google.android.gms.internal.gb;
import com.google.android.gms.internal.gg;

public final class Games {
    public static final Api API = new Api(jO, SCOPE_GAMES);
    public static final Achievements Achievements = new fs();
    public static final GamesMetadata GamesMetadata = new fu();
    public static final Invitations Invitations = new fv();
    public static final Leaderboards Leaderboards = new fw();
    public static final Notifications Notifications = new fy();
    public static final Players Players = new fz();
    public static final RealTimeMultiplayer RealTimeMultiplayer = new ga();
    public static final Scope SCOPE_GAMES = new Scope(Scopes.GAMES);
    public static final TurnBasedMultiplayer TurnBasedMultiplayer = new gb();
    static final Api.b<fl> jO = new Api.b<fl>() {
        /* renamed from: e */
        public fl b(Context context, dt dtVar, GoogleApiClient.ApiOptions apiOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            GamesOptions gamesOptions;
            GamesOptions gamesOptions2 = new GamesOptions();
            if (apiOptions != null) {
                eg.b(apiOptions instanceof GamesOptions, (Object) "Must provide valid GamesOptions!");
                gamesOptions = (GamesOptions) apiOptions;
            } else {
                gamesOptions = gamesOptions2;
            }
            return new fl(context, dtVar.bJ(), dtVar.bF(), connectionCallbacks, onConnectionFailedListener, dtVar.bI(), dtVar.bG(), dtVar.bK(), gamesOptions.ta, gamesOptions.tb, gamesOptions.tc, gamesOptions.td);
        }

        public int getPriority() {
            return 1;
        }
    };
    public static final Scope sW = new Scope("https://www.googleapis.com/auth/games.firstparty");
    public static final Api sX = new Api(jO, sW);
    public static final Multiplayer sY = new fx();
    public static final gg sZ = new ft();

    public static final class GamesOptions implements GoogleApiClient.ApiOptions {
        final boolean ta;
        final boolean tb;
        final int tc;
        final int td;

        public static final class Builder {
            boolean ta;
            boolean tb;
            int tc;
            int td;

            private Builder() {
                this.ta = false;
                this.tb = true;
                this.tc = 17;
                this.td = 4368;
            }

            public GamesOptions build() {
                return new GamesOptions(this);
            }

            public Builder setSdkVariant(int i) {
                this.td = i;
                return this;
            }

            public Builder setShowConnectingPopup(boolean z) {
                this.tb = z;
                this.tc = 17;
                return this;
            }

            public Builder setShowConnectingPopup(boolean z, int i) {
                this.tb = z;
                this.tc = i;
                return this;
            }
        }

        private GamesOptions() {
            this.ta = false;
            this.tb = true;
            this.tc = 17;
            this.td = 4368;
        }

        private GamesOptions(Builder builder) {
            this.ta = builder.ta;
            this.tb = builder.tb;
            this.tc = builder.tc;
            this.td = builder.td;
        }

        public static Builder builder() {
            return new Builder();
        }
    }

    public static abstract class a<R extends Result> extends a.C0001a<R, fl> implements PendingResult<R> {
        public a() {
            super(Games.jO);
        }
    }

    private static abstract class b extends a<Status> {
        private b() {
        }

        /* renamed from: g */
        public Status e(Status status) {
            return status;
        }
    }

    private Games() {
    }

    public static String getAppId(GoogleApiClient googleApiClient) {
        return j(googleApiClient).getAppId();
    }

    public static String getCurrentAccountName(GoogleApiClient googleApiClient) {
        return j(googleApiClient).getCurrentAccountName();
    }

    public static int getSdkVariant(GoogleApiClient googleApiClient) {
        return j(googleApiClient).dd();
    }

    public static Intent getSettingsIntent(GoogleApiClient googleApiClient) {
        return j(googleApiClient).getSettingsIntent();
    }

    public static fl j(GoogleApiClient googleApiClient) {
        boolean z = true;
        eg.b(googleApiClient != null, (Object) "GoogleApiClient parameter is required.");
        eg.a(googleApiClient.isConnected(), "GoogleApiClient must be connected.");
        fl flVar = (fl) googleApiClient.a(jO);
        if (flVar == null) {
            z = false;
        }
        eg.a(z, "GoogleApiClient is not configured to use the Games Api. Pass Games.API into GoogleApiClient.Builder#addApi() to use this feature.");
        return flVar;
    }

    public static void setGravityForPopups(GoogleApiClient googleApiClient, int i) {
        j(googleApiClient).setGravityForPopups(i);
    }

    public static void setViewForPopups(GoogleApiClient googleApiClient, View view) {
        eg.f(view);
        j(googleApiClient).setViewForPopups(view);
    }

    public static PendingResult<Status> signOut(GoogleApiClient googleApiClient) {
        return googleApiClient.b(new b() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.b(this);
            }
        });
    }
}
