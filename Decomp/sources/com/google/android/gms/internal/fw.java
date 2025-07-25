package com.google.android.gms.internal;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;

public final class fw implements Leaderboards {

    private static abstract class a extends Games.a<Leaderboards.LeaderboardMetadataResult> {
        private a() {
        }

        /* renamed from: x */
        public Leaderboards.LeaderboardMetadataResult e(final Status status) {
            return new Leaderboards.LeaderboardMetadataResult() {
                public LeaderboardBuffer getLeaderboards() {
                    return new LeaderboardBuffer(DataHolder.empty(14));
                }

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }
    }

    private static abstract class b extends Games.a<Leaderboards.LoadPlayerScoreResult> {
        private b() {
        }

        /* renamed from: y */
        public Leaderboards.LoadPlayerScoreResult e(final Status status) {
            return new Leaderboards.LoadPlayerScoreResult() {
                public LeaderboardScore getScore() {
                    return null;
                }

                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    private static abstract class c extends Games.a<Leaderboards.LoadScoresResult> {
        private c() {
        }

        /* renamed from: z */
        public Leaderboards.LoadScoresResult e(final Status status) {
            return new Leaderboards.LoadScoresResult() {
                public Leaderboard getLeaderboard() {
                    return null;
                }

                public LeaderboardScoreBuffer getScores() {
                    return new LeaderboardScoreBuffer(DataHolder.empty(14));
                }

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }
    }

    protected static abstract class d extends Games.a<Leaderboards.SubmitScoreResult> {
        protected d() {
        }

        /* renamed from: A */
        public Leaderboards.SubmitScoreResult e(final Status status) {
            return new Leaderboards.SubmitScoreResult() {
                public ScoreSubmissionData getScoreData() {
                    return new ScoreSubmissionData(DataHolder.empty(14));
                }

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }
    }

    public Intent getAllLeaderboardsIntent(GoogleApiClient googleApiClient) {
        return Games.j(googleApiClient).getAllLeaderboardsIntent();
    }

    public Intent getLeaderboardIntent(GoogleApiClient googleApiClient, String str) {
        return Games.j(googleApiClient).getLeaderboardIntent(str);
    }

    public PendingResult<Leaderboards.LoadPlayerScoreResult> loadCurrentPlayerLeaderboardScore(GoogleApiClient googleApiClient, final String str, final int i, final int i2) {
        return googleApiClient.a(new b() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.a((a.c<Leaderboards.LoadPlayerScoreResult>) this, (String) null, str, i, i2);
            }
        });
    }

    public PendingResult<Leaderboards.LeaderboardMetadataResult> loadLeaderboardMetadata(GoogleApiClient googleApiClient, final String str, final boolean z) {
        return googleApiClient.a(new a() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.a((a.c<Leaderboards.LeaderboardMetadataResult>) this, str, z);
            }
        });
    }

    public PendingResult<Leaderboards.LeaderboardMetadataResult> loadLeaderboardMetadata(GoogleApiClient googleApiClient, final boolean z) {
        return googleApiClient.a(new a() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.a((a.c<Leaderboards.LeaderboardMetadataResult>) this, z);
            }
        });
    }

    public PendingResult<Leaderboards.LoadScoresResult> loadMoreScores(GoogleApiClient googleApiClient, final LeaderboardScoreBuffer leaderboardScoreBuffer, final int i, final int i2) {
        return googleApiClient.a(new c() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.a((a.c<Leaderboards.LoadScoresResult>) this, leaderboardScoreBuffer, i, i2);
            }
        });
    }

    public PendingResult<Leaderboards.LoadScoresResult> loadPlayerCenteredScores(GoogleApiClient googleApiClient, String str, int i, int i2, int i3) {
        return loadPlayerCenteredScores(googleApiClient, str, i, i2, i3, false);
    }

    public PendingResult<Leaderboards.LoadScoresResult> loadPlayerCenteredScores(GoogleApiClient googleApiClient, String str, int i, int i2, int i3, boolean z) {
        final String str2 = str;
        final int i4 = i;
        final int i5 = i2;
        final int i6 = i3;
        final boolean z2 = z;
        return googleApiClient.a(new c() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.b(this, str2, i4, i5, i6, z2);
            }
        });
    }

    public PendingResult<Leaderboards.LoadScoresResult> loadTopScores(GoogleApiClient googleApiClient, String str, int i, int i2, int i3) {
        return loadTopScores(googleApiClient, str, i, i2, i3, false);
    }

    public PendingResult<Leaderboards.LoadScoresResult> loadTopScores(GoogleApiClient googleApiClient, String str, int i, int i2, int i3, boolean z) {
        final String str2 = str;
        final int i4 = i;
        final int i5 = i2;
        final int i6 = i3;
        final boolean z2 = z;
        return googleApiClient.a(new c() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.a(this, str2, i4, i5, i6, z2);
            }
        });
    }

    public void submitScore(GoogleApiClient googleApiClient, String str, long j) {
        submitScore(googleApiClient, str, j, (String) null);
    }

    public void submitScore(GoogleApiClient googleApiClient, String str, long j, String str2) {
        Games.j(googleApiClient).a((a.c<Leaderboards.SubmitScoreResult>) null, str, j, str2);
    }

    public PendingResult<Leaderboards.SubmitScoreResult> submitScoreImmediate(GoogleApiClient googleApiClient, String str, long j) {
        return submitScoreImmediate(googleApiClient, str, j, (String) null);
    }

    public PendingResult<Leaderboards.SubmitScoreResult> submitScoreImmediate(GoogleApiClient googleApiClient, String str, long j, String str2) {
        final String str3 = str;
        final long j2 = j;
        final String str4 = str2;
        return googleApiClient.b(new d() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.a((a.c<Leaderboards.SubmitScoreResult>) this, str3, j2, str4);
            }
        });
    }
}
