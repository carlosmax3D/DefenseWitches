package com.google.android.gms.appstate;

import android.content.Context;
import com.google.android.gms.appstate.AppStateManager;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.dc;
import com.google.android.gms.internal.ds;
import com.google.android.gms.internal.eg;

@Deprecated
public final class AppStateClient implements GooglePlayServicesClient {
    public static final int STATUS_CLIENT_RECONNECT_REQUIRED = 2;
    public static final int STATUS_DEVELOPER_ERROR = 7;
    public static final int STATUS_INTERNAL_ERROR = 1;
    public static final int STATUS_NETWORK_ERROR_NO_DATA = 4;
    public static final int STATUS_NETWORK_ERROR_OPERATION_DEFERRED = 5;
    public static final int STATUS_NETWORK_ERROR_OPERATION_FAILED = 6;
    public static final int STATUS_NETWORK_ERROR_STALE_DATA = 3;
    public static final int STATUS_OK = 0;
    public static final int STATUS_STATE_KEY_LIMIT_EXCEEDED = 2003;
    public static final int STATUS_STATE_KEY_NOT_FOUND = 2002;
    public static final int STATUS_WRITE_OUT_OF_DATE_VERSION = 2000;
    public static final int STATUS_WRITE_SIZE_EXCEEDED = 2001;
    private final dc jx;

    @Deprecated
    public static final class Builder {
        private static final String[] jC = {Scopes.APP_STATE};
        private GooglePlayServicesClient.ConnectionCallbacks jD;
        private GooglePlayServicesClient.OnConnectionFailedListener jE;
        private String[] jF = jC;
        private String jG = "<<default account>>";
        private Context mContext;

        public Builder(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.mContext = context;
            this.jD = connectionCallbacks;
            this.jE = onConnectionFailedListener;
        }

        public AppStateClient create() {
            return new AppStateClient(this.mContext, this.jD, this.jE, this.jG, this.jF);
        }

        public Builder setAccountName(String str) {
            this.jG = (String) eg.f(str);
            return this;
        }

        public Builder setScopes(String... strArr) {
            this.jF = strArr;
            return this;
        }
    }

    private static final class a implements a.c<AppStateManager.StateResult> {
        private final OnStateLoadedListener jH;

        a(OnStateLoadedListener onStateLoadedListener) {
            this.jH = onStateLoadedListener;
        }

        public void a(AppStateManager.StateResult stateResult) {
            if (this.jH != null) {
                if (stateResult.getStatus().getStatusCode() == 2000) {
                    AppStateManager.StateConflictResult conflictResult = stateResult.getConflictResult();
                    ds.d(conflictResult);
                    this.jH.onStateConflict(conflictResult.getStateKey(), conflictResult.getResolvedVersion(), conflictResult.getLocalData(), conflictResult.getServerData());
                    return;
                }
                AppStateManager.StateLoadedResult loadedResult = stateResult.getLoadedResult();
                ds.d(loadedResult);
                this.jH.onStateLoaded(loadedResult.getStatus().getStatusCode(), loadedResult.getStateKey(), loadedResult.getLocalData());
            }
        }
    }

    private AppStateClient(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, String str, String[] strArr) {
        this.jx = new dc(context, connectionCallbacks, onConnectionFailedListener, str, strArr);
    }

    @Deprecated
    public void connect() {
        this.jx.connect();
    }

    @Deprecated
    public void deleteState(final OnStateDeletedListener onStateDeletedListener, int i) {
        this.jx.a((a.c<AppStateManager.StateDeletedResult>) new a.c<AppStateManager.StateDeletedResult>() {
            public void a(AppStateManager.StateDeletedResult stateDeletedResult) {
                onStateDeletedListener.onStateDeleted(stateDeletedResult.getStatus().getStatusCode(), stateDeletedResult.getStateKey());
            }
        }, i);
    }

    @Deprecated
    public void disconnect() {
        this.jx.disconnect();
    }

    @Deprecated
    public int getMaxNumKeys() {
        return this.jx.getMaxNumKeys();
    }

    @Deprecated
    public int getMaxStateSize() {
        return this.jx.getMaxStateSize();
    }

    @Deprecated
    public boolean isConnected() {
        return this.jx.isConnected();
    }

    @Deprecated
    public boolean isConnecting() {
        return this.jx.isConnecting();
    }

    @Deprecated
    public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        return this.jx.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    @Deprecated
    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.jx.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    @Deprecated
    public void listStates(final OnStateListLoadedListener onStateListLoadedListener) {
        this.jx.a((a.c<AppStateManager.StateListResult>) new a.c<AppStateManager.StateListResult>() {
            public void a(AppStateManager.StateListResult stateListResult) {
                onStateListLoadedListener.onStateListLoaded(stateListResult.getStatus().getStatusCode(), stateListResult.getStateBuffer());
            }
        });
    }

    @Deprecated
    public void loadState(OnStateLoadedListener onStateLoadedListener, int i) {
        this.jx.b(new a(onStateLoadedListener), i);
    }

    @Deprecated
    public void reconnect() {
        this.jx.disconnect();
        this.jx.connect();
    }

    @Deprecated
    public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.jx.registerConnectionCallbacks(connectionCallbacks);
    }

    @Deprecated
    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.jx.registerConnectionFailedListener(onConnectionFailedListener);
    }

    @Deprecated
    public void resolveState(OnStateLoadedListener onStateLoadedListener, int i, String str, byte[] bArr) {
        this.jx.a(new a(onStateLoadedListener), i, str, bArr);
    }

    @Deprecated
    public void signOut() {
        this.jx.b(new a.c<Status>() {
            public void a(Status status) {
            }
        });
    }

    @Deprecated
    public void signOut(final OnSignOutCompleteListener onSignOutCompleteListener) {
        eg.b(onSignOutCompleteListener, (Object) "Must provide a valid listener");
        this.jx.b(new a.c<Status>() {
            public void a(Status status) {
                onSignOutCompleteListener.onSignOutComplete();
            }
        });
    }

    @Deprecated
    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.jx.unregisterConnectionCallbacks(connectionCallbacks);
    }

    @Deprecated
    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.jx.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    @Deprecated
    public void updateState(int i, byte[] bArr) {
        this.jx.a(new a((OnStateLoadedListener) null), i, bArr);
    }

    @Deprecated
    public void updateStateImmediate(OnStateLoadedListener onStateLoadedListener, int i, byte[] bArr) {
        eg.b(onStateLoadedListener, (Object) "Must provide a valid listener");
        this.jx.a(new a(onStateLoadedListener), i, bArr);
    }
}
