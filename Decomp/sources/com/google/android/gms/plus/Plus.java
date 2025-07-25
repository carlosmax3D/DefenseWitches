package com.google.android.gms.plus;

import android.content.Context;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.dt;
import com.google.android.gms.internal.eg;
import com.google.android.gms.internal.hs;
import com.google.android.gms.internal.hu;
import com.google.android.gms.internal.hx;
import com.google.android.gms.internal.hy;
import com.google.android.gms.internal.hz;
import com.google.android.gms.internal.ia;
import java.util.HashSet;
import java.util.Set;

public final class Plus {
    public static final Api API = new Api(jO, new Scope[0]);
    public static final Account AccountApi = new hx(jO);
    public static final a Dr = new hy(jO);
    public static final Moments MomentsApi = new hz(jO);
    public static final People PeopleApi = new ia(jO);
    public static final Scope SCOPE_PLUS_LOGIN = new Scope(Scopes.PLUS_LOGIN);
    public static final Scope SCOPE_PLUS_PROFILE = new Scope(Scopes.PLUS_ME);
    static final Api.b<hs> jO = new Api.b<hs>() {
        /* renamed from: g */
        public hs b(Context context, dt dtVar, GoogleApiClient.ApiOptions apiOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            PlusOptions plusOptions;
            PlusOptions plusOptions2 = new PlusOptions();
            if (apiOptions != null) {
                eg.b(apiOptions instanceof PlusOptions, (Object) "Must provide valid PlusOptions!");
                plusOptions = (PlusOptions) apiOptions;
            } else {
                plusOptions = plusOptions2;
            }
            return new hs(context, connectionCallbacks, onConnectionFailedListener, new hu(dtVar.bF(), dtVar.bI(), (String[]) plusOptions.Dt.toArray(new String[0]), new String[0], context.getPackageName(), context.getPackageName(), (String) null));
        }

        public int getPriority() {
            return 2;
        }
    };

    public static final class PlusOptions implements GoogleApiClient.ApiOptions {
        final String Ds;
        final Set<String> Dt;

        public static final class Builder {
            String Ds;
            final Set<String> Dt = new HashSet();

            public Builder addActivityTypes(String... strArr) {
                eg.b(strArr, (Object) "activityTypes may not be null.");
                for (String add : strArr) {
                    this.Dt.add(add);
                }
                return this;
            }

            public PlusOptions build() {
                return new PlusOptions(this);
            }

            public Builder setServerClientId(String str) {
                this.Ds = str;
                return this;
            }
        }

        private PlusOptions() {
            this.Ds = null;
            this.Dt = new HashSet();
        }

        private PlusOptions(Builder builder) {
            this.Ds = builder.Ds;
            this.Dt = builder.Dt;
        }

        public static Builder builder() {
            return new Builder();
        }
    }

    public static abstract class a<R extends Result> extends a.C0001a<R, hs> {
        public a(Api.b<hs> bVar) {
            super(bVar);
        }
    }

    private Plus() {
    }

    public static hs a(GoogleApiClient googleApiClient, Api.b<hs> bVar) {
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
}
