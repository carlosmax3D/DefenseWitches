package com.google.android.gms.internal;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.Achievements;

public final class fs implements Achievements {

    private static abstract class a extends Games.a<Achievements.LoadAchievementsResult> {
        private a() {
        }

        /* renamed from: t */
        public Achievements.LoadAchievementsResult e(final Status status) {
            return new Achievements.LoadAchievementsResult() {
                public AchievementBuffer getAchievements() {
                    return new AchievementBuffer(DataHolder.empty(14));
                }

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }
    }

    private static abstract class b extends Games.a<Achievements.UpdateAchievementResult> {
        /* access modifiers changed from: private */
        public final String uS;

        public b(String str) {
            this.uS = str;
        }

        /* renamed from: u */
        public Achievements.UpdateAchievementResult e(final Status status) {
            return new Achievements.UpdateAchievementResult() {
                public String getAchievementId() {
                    return b.this.uS;
                }

                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    public Intent getAchievementsIntent(GoogleApiClient googleApiClient) {
        return Games.j(googleApiClient).getAchievementsIntent();
    }

    public void increment(GoogleApiClient googleApiClient, final String str, final int i) {
        googleApiClient.b(new b(str) {
            public void a(fl flVar) {
                flVar.a((a.c<Achievements.UpdateAchievementResult>) null, str, i);
            }
        });
    }

    public PendingResult<Achievements.UpdateAchievementResult> incrementImmediate(GoogleApiClient googleApiClient, final String str, final int i) {
        return googleApiClient.b(new b(str) {
            public void a(fl flVar) {
                flVar.a((a.c<Achievements.UpdateAchievementResult>) this, str, i);
            }
        });
    }

    public PendingResult<Achievements.LoadAchievementsResult> load(GoogleApiClient googleApiClient, final boolean z) {
        return googleApiClient.a(new a() {
            public void a(fl flVar) {
                flVar.b((a.c<Achievements.LoadAchievementsResult>) this, z);
            }
        });
    }

    public void reveal(GoogleApiClient googleApiClient, final String str) {
        googleApiClient.b(new b(str) {
            public void a(fl flVar) {
                flVar.b((a.c<Achievements.UpdateAchievementResult>) null, str);
            }
        });
    }

    public PendingResult<Achievements.UpdateAchievementResult> revealImmediate(GoogleApiClient googleApiClient, final String str) {
        return googleApiClient.b(new b(str) {
            public void a(fl flVar) {
                flVar.b((a.c<Achievements.UpdateAchievementResult>) this, str);
            }
        });
    }

    public void setSteps(GoogleApiClient googleApiClient, final String str, final int i) {
        googleApiClient.b(new b(str) {
            public void a(fl flVar) {
                flVar.b((a.c<Achievements.UpdateAchievementResult>) null, str, i);
            }
        });
    }

    public PendingResult<Achievements.UpdateAchievementResult> setStepsImmediate(GoogleApiClient googleApiClient, final String str, final int i) {
        return googleApiClient.b(new b(str) {
            public void a(fl flVar) {
                flVar.b(this, str, i);
            }
        });
    }

    public void unlock(GoogleApiClient googleApiClient, final String str) {
        googleApiClient.b(new b(str) {
            public void a(fl flVar) {
                flVar.c((a.c<Achievements.UpdateAchievementResult>) null, str);
            }
        });
    }

    public PendingResult<Achievements.UpdateAchievementResult> unlockImmediate(GoogleApiClient googleApiClient, final String str) {
        return googleApiClient.b(new b(str) {
            public void a(fl flVar) {
                flVar.c(this, str);
            }
        });
    }
}
