package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Account;
import com.google.android.gms.plus.Plus;

public final class hx implements Account {
    private final Api.b<hs> Ea;

    private static abstract class a extends Plus.a<Status> {
        a(Api.b<hs> bVar) {
            super(bVar);
        }

        /* renamed from: g */
        public Status e(Status status) {
            return status;
        }
    }

    public hx(Api.b<hs> bVar) {
        this.Ea = bVar;
    }

    private static hs a(GoogleApiClient googleApiClient, Api.b<hs> bVar) {
        boolean z = true;
        eg.b(googleApiClient != null, (Object) "GoogleApiClient parameter is required.");
        eg.a(googleApiClient.isConnected(), "GoogleApiClient must be connected.");
        hs hsVar = (hs) googleApiClient.a(bVar);
        if (hsVar == null) {
            z = false;
        }
        eg.a(z, "GoogleApiClient is not configured to use the Plus.API Api. Pass this into GoogleApiClient.Builder#addApi() to use this feature.");
        return hsVar;
    }

    public void clearDefaultAccount(GoogleApiClient googleApiClient) {
        a(googleApiClient, this.Ea).clearDefaultAccount();
    }

    public String getAccountName(GoogleApiClient googleApiClient) {
        return a(googleApiClient, this.Ea).getAccountName();
    }

    public PendingResult<Status> revokeAccessAndDisconnect(GoogleApiClient googleApiClient) {
        return googleApiClient.b(new a(this.Ea) {
            /* access modifiers changed from: protected */
            public void a(hs hsVar) {
                hsVar.l(this);
            }
        });
    }
}
