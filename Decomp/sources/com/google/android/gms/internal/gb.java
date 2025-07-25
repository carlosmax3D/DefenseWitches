package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.turnbased.LoadMatchesResponse;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdateReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import java.util.List;

public final class gb implements TurnBasedMultiplayer {

    private static abstract class a extends Games.a<TurnBasedMultiplayer.CancelMatchResult> {
        /* access modifiers changed from: private */
        public final String uS;

        public a(String str) {
            this.uS = str;
        }

        /* renamed from: C */
        public TurnBasedMultiplayer.CancelMatchResult e(final Status status) {
            return new TurnBasedMultiplayer.CancelMatchResult() {
                public String getMatchId() {
                    return a.this.uS;
                }

                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    private static abstract class b extends Games.a<TurnBasedMultiplayer.InitiateMatchResult> {
        private b() {
        }

        /* renamed from: D */
        public TurnBasedMultiplayer.InitiateMatchResult e(final Status status) {
            return new TurnBasedMultiplayer.InitiateMatchResult() {
                public TurnBasedMatch getMatch() {
                    return null;
                }

                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    private static abstract class c extends Games.a<TurnBasedMultiplayer.LeaveMatchResult> {
        private c() {
        }

        /* renamed from: E */
        public TurnBasedMultiplayer.LeaveMatchResult e(final Status status) {
            return new TurnBasedMultiplayer.LeaveMatchResult() {
                public TurnBasedMatch getMatch() {
                    return null;
                }

                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    private static abstract class d extends Games.a<TurnBasedMultiplayer.LoadMatchResult> {
        private d() {
        }

        /* renamed from: F */
        public TurnBasedMultiplayer.LoadMatchResult e(final Status status) {
            return new TurnBasedMultiplayer.LoadMatchResult() {
                public TurnBasedMatch getMatch() {
                    return null;
                }

                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    private static abstract class e extends Games.a<TurnBasedMultiplayer.LoadMatchesResult> {
        private e() {
        }

        /* renamed from: G */
        public TurnBasedMultiplayer.LoadMatchesResult e(final Status status) {
            return new TurnBasedMultiplayer.LoadMatchesResult() {
                public LoadMatchesResponse getMatches() {
                    return new LoadMatchesResponse(new Bundle());
                }

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }
    }

    private static abstract class f extends Games.a<TurnBasedMultiplayer.UpdateMatchResult> {
        private f() {
        }

        /* renamed from: H */
        public TurnBasedMultiplayer.UpdateMatchResult e(final Status status) {
            return new TurnBasedMultiplayer.UpdateMatchResult() {
                public TurnBasedMatch getMatch() {
                    return null;
                }

                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    public PendingResult<TurnBasedMultiplayer.InitiateMatchResult> acceptInvitation(GoogleApiClient googleApiClient, final String str) {
        return googleApiClient.b(new b() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.e(this, str);
            }
        });
    }

    public PendingResult<TurnBasedMultiplayer.CancelMatchResult> cancelMatch(GoogleApiClient googleApiClient, final String str) {
        return googleApiClient.b(new a(str) {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.g(this, str);
            }
        });
    }

    public PendingResult<TurnBasedMultiplayer.InitiateMatchResult> createMatch(GoogleApiClient googleApiClient, final TurnBasedMatchConfig turnBasedMatchConfig) {
        return googleApiClient.b(new b() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.a((a.c<TurnBasedMultiplayer.InitiateMatchResult>) this, turnBasedMatchConfig);
            }
        });
    }

    public void declineInvitation(GoogleApiClient googleApiClient, String str) {
        Games.j(googleApiClient).j(str, 1);
    }

    public void dismissInvitation(GoogleApiClient googleApiClient, String str) {
        Games.j(googleApiClient).i(str, 1);
    }

    public void dismissMatch(GoogleApiClient googleApiClient, String str) {
        Games.j(googleApiClient).dismissTurnBasedMatch(str);
    }

    public PendingResult<TurnBasedMultiplayer.UpdateMatchResult> finishMatch(GoogleApiClient googleApiClient, String str) {
        return finishMatch(googleApiClient, str, (byte[]) null, (ParticipantResult[]) null);
    }

    public PendingResult<TurnBasedMultiplayer.UpdateMatchResult> finishMatch(GoogleApiClient googleApiClient, String str, byte[] bArr, List<ParticipantResult> list) {
        return finishMatch(googleApiClient, str, bArr, list == null ? null : (ParticipantResult[]) list.toArray(new ParticipantResult[list.size()]));
    }

    public PendingResult<TurnBasedMultiplayer.UpdateMatchResult> finishMatch(GoogleApiClient googleApiClient, final String str, final byte[] bArr, final ParticipantResult... participantResultArr) {
        return googleApiClient.b(new f() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.a((a.c<TurnBasedMultiplayer.UpdateMatchResult>) this, str, bArr, participantResultArr);
            }
        });
    }

    public Intent getInboxIntent(GoogleApiClient googleApiClient) {
        return Games.j(googleApiClient).getMatchInboxIntent();
    }

    public int getMaxMatchDataSize(GoogleApiClient googleApiClient) {
        return Games.j(googleApiClient).getMaxTurnBasedMatchDataSize();
    }

    public Intent getSelectOpponentsIntent(GoogleApiClient googleApiClient, int i, int i2) {
        return Games.j(googleApiClient).getTurnBasedSelectOpponentsIntent(i, i2, true);
    }

    public Intent getSelectOpponentsIntent(GoogleApiClient googleApiClient, int i, int i2, boolean z) {
        return Games.j(googleApiClient).getTurnBasedSelectOpponentsIntent(i, i2, z);
    }

    public PendingResult<TurnBasedMultiplayer.LeaveMatchResult> leaveMatch(GoogleApiClient googleApiClient, final String str) {
        return googleApiClient.b(new c() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.f(this, str);
            }
        });
    }

    public PendingResult<TurnBasedMultiplayer.LeaveMatchResult> leaveMatchDuringTurn(GoogleApiClient googleApiClient, final String str, final String str2) {
        return googleApiClient.b(new c() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.a((a.c<TurnBasedMultiplayer.LeaveMatchResult>) this, str, str2);
            }
        });
    }

    public PendingResult<TurnBasedMultiplayer.LoadMatchResult> loadMatch(GoogleApiClient googleApiClient, final String str) {
        return googleApiClient.a(new d() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.h(this, str);
            }
        });
    }

    public PendingResult<TurnBasedMultiplayer.LoadMatchesResult> loadMatchesByStatus(GoogleApiClient googleApiClient, final int... iArr) {
        return googleApiClient.a(new e() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.a((a.c<TurnBasedMultiplayer.LoadMatchesResult>) this, iArr);
            }
        });
    }

    public void registerMatchUpdateListener(GoogleApiClient googleApiClient, OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
        Games.j(googleApiClient).registerMatchUpdateListener(onTurnBasedMatchUpdateReceivedListener);
    }

    public PendingResult<TurnBasedMultiplayer.InitiateMatchResult> rematch(GoogleApiClient googleApiClient, final String str) {
        return googleApiClient.b(new b() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.d(this, str);
            }
        });
    }

    public PendingResult<TurnBasedMultiplayer.UpdateMatchResult> takeTurn(GoogleApiClient googleApiClient, String str, byte[] bArr, String str2) {
        return takeTurn(googleApiClient, str, bArr, str2, (ParticipantResult[]) null);
    }

    public PendingResult<TurnBasedMultiplayer.UpdateMatchResult> takeTurn(GoogleApiClient googleApiClient, String str, byte[] bArr, String str2, List<ParticipantResult> list) {
        return takeTurn(googleApiClient, str, bArr, str2, list == null ? null : (ParticipantResult[]) list.toArray(new ParticipantResult[list.size()]));
    }

    public PendingResult<TurnBasedMultiplayer.UpdateMatchResult> takeTurn(GoogleApiClient googleApiClient, String str, byte[] bArr, String str2, ParticipantResult... participantResultArr) {
        final String str3 = str;
        final byte[] bArr2 = bArr;
        final String str4 = str2;
        final ParticipantResult[] participantResultArr2 = participantResultArr;
        return googleApiClient.b(new f() {
            /* access modifiers changed from: protected */
            public void a(fl flVar) {
                flVar.a((a.c<TurnBasedMultiplayer.UpdateMatchResult>) this, str3, bArr2, str4, participantResultArr2);
            }
        });
    }

    public void unregisterMatchUpdateListener(GoogleApiClient googleApiClient) {
        Games.j(googleApiClient).unregisterMatchUpdateListener();
    }
}
