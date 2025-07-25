package com.google.android.gms.appstate;

import android.content.Context;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.dc;
import com.google.android.gms.internal.dt;
import com.google.android.gms.internal.eg;

public final class AppStateManager {
    public static final Api API = new Api(jO, SCOPE_APP_STATE);
    public static final Scope SCOPE_APP_STATE = new Scope(Scopes.APP_STATE);
    static final Api.b<dc> jO = new Api.b<dc>() {
        /* renamed from: a */
        public dc b(Context context, dt dtVar, GoogleApiClient.ApiOptions apiOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            return new dc(context, connectionCallbacks, onConnectionFailedListener, dtVar.bF(), (String[]) dtVar.bH().toArray(new String[0]));
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }
    };

    public interface StateConflictResult extends Result {
        byte[] getLocalData();

        String getResolvedVersion();

        byte[] getServerData();

        int getStateKey();
    }

    public interface StateDeletedResult extends Result {
        int getStateKey();
    }

    public interface StateListResult extends Result {
        AppStateBuffer getStateBuffer();
    }

    public interface StateLoadedResult extends Result {
        byte[] getLocalData();

        int getStateKey();
    }

    public interface StateResult extends Result {
        StateConflictResult getConflictResult();

        StateLoadedResult getLoadedResult();
    }

    public static abstract class a<R extends Result> extends a.C0001a<R, dc> implements PendingResult<R> {
        public a() {
            super(AppStateManager.jO);
        }
    }

    private static abstract class b extends a<StateDeletedResult> {
        private b() {
        }
    }

    private static abstract class c extends a<StateListResult> {
        private c() {
        }

        /* renamed from: f */
        public StateListResult e(final Status status) {
            return new StateListResult() {
                public AppStateBuffer getStateBuffer() {
                    return new AppStateBuffer((DataHolder) null);
                }

                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    private static abstract class d extends a<Status> {
        private d() {
        }

        /* renamed from: g */
        public Status e(Status status) {
            return status;
        }
    }

    private static abstract class e extends a<StateResult> {
        private e() {
        }

        /* renamed from: h */
        public StateResult e(Status status) {
            return AppStateManager.b(status);
        }
    }

    private AppStateManager() {
    }

    public static dc a(GoogleApiClient googleApiClient) {
        boolean z = true;
        eg.b(googleApiClient != null, (Object) "GoogleApiClient parameter is required.");
        eg.a(googleApiClient.isConnected(), "GoogleApiClient must be connected.");
        dc dcVar = (dc) googleApiClient.a(jO);
        if (dcVar == null) {
            z = false;
        }
        eg.a(z, "GoogleApiClient is not configured to use the AppState API. Pass AppStateManager.API into GoogleApiClient.Builder#addApi() to use this feature.");
        return dcVar;
    }

    /* access modifiers changed from: private */
    public static StateResult b(final Status status) {
        return new StateResult() {
            public StateConflictResult getConflictResult() {
                return null;
            }

            public StateLoadedResult getLoadedResult() {
                return null;
            }

            public Status getStatus() {
                return status;
            }
        };
    }

    public static PendingResult<StateDeletedResult> delete(GoogleApiClient googleApiClient, final int i) {
        return googleApiClient.b(new b() {
            /* access modifiers changed from: protected */
            public void a(dc dcVar) {
                dcVar.a((a.c<StateDeletedResult>) this, i);
            }

            /* renamed from: d */
            public StateDeletedResult e(final Status status) {
                return new StateDeletedResult() {
                    public int getStateKey() {
                        return i;
                    }

                    public Status getStatus() {
                        return status;
                    }
                };
            }
        });
    }

    public static int getMaxNumKeys(GoogleApiClient googleApiClient) {
        return a(googleApiClient).getMaxNumKeys();
    }

    public static int getMaxStateSize(GoogleApiClient googleApiClient) {
        return a(googleApiClient).getMaxStateSize();
    }

    public static PendingResult<StateListResult> list(GoogleApiClient googleApiClient) {
        return googleApiClient.a(new c() {
            /* access modifiers changed from: protected */
            public void a(dc dcVar) {
                dcVar.a((a.c<StateListResult>) this);
            }
        });
    }

    public static PendingResult<StateResult> load(GoogleApiClient googleApiClient, final int i) {
        return googleApiClient.a(new e() {
            /* access modifiers changed from: protected */
            public void a(dc dcVar) {
                dcVar.b(this, i);
            }
        });
    }

    public static PendingResult<StateResult> resolve(GoogleApiClient googleApiClient, final int i, final String str, final byte[] bArr) {
        return googleApiClient.b(new e() {
            /* access modifiers changed from: protected */
            public void a(dc dcVar) {
                dcVar.a(this, i, str, bArr);
            }
        });
    }

    public static PendingResult<Status> signOut(GoogleApiClient googleApiClient) {
        return googleApiClient.b(new d() {
            /* access modifiers changed from: protected */
            public void a(dc dcVar) {
                dcVar.b(this);
            }
        });
    }

    public static void update(GoogleApiClient googleApiClient, final int i, final byte[] bArr) {
        googleApiClient.b(new e() {
            /* access modifiers changed from: protected */
            public void a(dc dcVar) {
                dcVar.a((a.c<StateResult>) null, i, bArr);
            }
        });
    }

    public static PendingResult<StateResult> updateImmediate(GoogleApiClient googleApiClient, final int i, final byte[] bArr) {
        return googleApiClient.b(new e() {
            /* access modifiers changed from: protected */
            public void a(dc dcVar) {
                dcVar.a(this, i, bArr);
            }
        });
    }
}
