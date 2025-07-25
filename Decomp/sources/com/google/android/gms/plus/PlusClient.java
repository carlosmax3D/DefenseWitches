package com.google.android.gms.plus;

import android.content.Context;
import android.net.Uri;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.hs;
import com.google.android.gms.internal.hv;
import com.google.android.gms.plus.Moments;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.plus.model.moments.MomentBuffer;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.Collection;

@Deprecated
public class PlusClient implements GooglePlayServicesClient {
    final hs Du;

    @Deprecated
    public static class Builder {
        private final hv DA = new hv(this.mContext);
        private final GooglePlayServicesClient.ConnectionCallbacks Dz;
        private final GooglePlayServicesClient.OnConnectionFailedListener jE;
        private final Context mContext;

        public Builder(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.mContext = context;
            this.Dz = connectionCallbacks;
            this.jE = onConnectionFailedListener;
        }

        public PlusClient build() {
            return new PlusClient(new hs(this.mContext, this.Dz, this.jE, this.DA.eZ()));
        }

        public Builder clearScopes() {
            this.DA.eY();
            return this;
        }

        public Builder setAccountName(String str) {
            this.DA.aA(str);
            return this;
        }

        public Builder setActions(String... strArr) {
            this.DA.e(strArr);
            return this;
        }

        public Builder setScopes(String... strArr) {
            this.DA.d(strArr);
            return this;
        }
    }

    @Deprecated
    public interface OnAccessRevokedListener {
        void onAccessRevoked(ConnectionResult connectionResult);
    }

    @Deprecated
    public interface OnMomentsLoadedListener {
        @Deprecated
        void onMomentsLoaded(ConnectionResult connectionResult, MomentBuffer momentBuffer, String str, String str2);
    }

    @Deprecated
    public interface OnPeopleLoadedListener {
        void onPeopleLoaded(ConnectionResult connectionResult, PersonBuffer personBuffer, String str);
    }

    @Deprecated
    public interface OrderBy {
        @Deprecated
        public static final int ALPHABETICAL = 0;
        @Deprecated
        public static final int BEST = 1;
    }

    PlusClient(hs hsVar) {
        this.Du = hsVar;
    }

    @Deprecated
    public void clearDefaultAccount() {
        this.Du.clearDefaultAccount();
    }

    @Deprecated
    public void connect() {
        this.Du.connect();
    }

    @Deprecated
    public void disconnect() {
        this.Du.disconnect();
    }

    /* access modifiers changed from: package-private */
    public hs eK() {
        return this.Du;
    }

    @Deprecated
    public String getAccountName() {
        return this.Du.getAccountName();
    }

    @Deprecated
    public Person getCurrentPerson() {
        return this.Du.getCurrentPerson();
    }

    @Deprecated
    public boolean isConnected() {
        return this.Du.isConnected();
    }

    @Deprecated
    public boolean isConnecting() {
        return this.Du.isConnecting();
    }

    @Deprecated
    public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        return this.Du.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    @Deprecated
    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.Du.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    @Deprecated
    public void loadMoments(final OnMomentsLoadedListener onMomentsLoadedListener) {
        this.Du.j(new a.c<Moments.LoadMomentsResult>() {
            public void a(Moments.LoadMomentsResult loadMomentsResult) {
                onMomentsLoadedListener.onMomentsLoaded(loadMomentsResult.getStatus().bu(), loadMomentsResult.getMomentBuffer(), loadMomentsResult.getNextPageToken(), loadMomentsResult.getUpdated());
            }
        });
    }

    @Deprecated
    public void loadMoments(final OnMomentsLoadedListener onMomentsLoadedListener, int i, String str, Uri uri, String str2, String str3) {
        this.Du.a(new a.c<Moments.LoadMomentsResult>() {
            public void a(Moments.LoadMomentsResult loadMomentsResult) {
                onMomentsLoadedListener.onMomentsLoaded(loadMomentsResult.getStatus().bu(), loadMomentsResult.getMomentBuffer(), loadMomentsResult.getNextPageToken(), loadMomentsResult.getUpdated());
            }
        }, i, str, uri, str2, str3);
    }

    @Deprecated
    public void loadPeople(final OnPeopleLoadedListener onPeopleLoadedListener, Collection<String> collection) {
        this.Du.a((a.c<People.LoadPeopleResult>) new a.c<People.LoadPeopleResult>() {
            public void a(People.LoadPeopleResult loadPeopleResult) {
                onPeopleLoadedListener.onPeopleLoaded(loadPeopleResult.getStatus().bu(), loadPeopleResult.getPersonBuffer(), loadPeopleResult.getNextPageToken());
            }
        }, collection);
    }

    @Deprecated
    public void loadPeople(final OnPeopleLoadedListener onPeopleLoadedListener, String... strArr) {
        this.Du.a((a.c<People.LoadPeopleResult>) new a.c<People.LoadPeopleResult>() {
            public void a(People.LoadPeopleResult loadPeopleResult) {
                onPeopleLoadedListener.onPeopleLoaded(loadPeopleResult.getStatus().bu(), loadPeopleResult.getPersonBuffer(), loadPeopleResult.getNextPageToken());
            }
        }, strArr);
    }

    @Deprecated
    public void loadVisiblePeople(final OnPeopleLoadedListener onPeopleLoadedListener, int i, String str) {
        this.Du.a((a.c<People.LoadPeopleResult>) new a.c<People.LoadPeopleResult>() {
            public void a(People.LoadPeopleResult loadPeopleResult) {
                onPeopleLoadedListener.onPeopleLoaded(loadPeopleResult.getStatus().bu(), loadPeopleResult.getPersonBuffer(), loadPeopleResult.getNextPageToken());
            }
        }, i, str);
    }

    @Deprecated
    public void loadVisiblePeople(final OnPeopleLoadedListener onPeopleLoadedListener, String str) {
        this.Du.i(new a.c<People.LoadPeopleResult>() {
            public void a(People.LoadPeopleResult loadPeopleResult) {
                onPeopleLoadedListener.onPeopleLoaded(loadPeopleResult.getStatus().bu(), loadPeopleResult.getPersonBuffer(), loadPeopleResult.getNextPageToken());
            }
        }, str);
    }

    @Deprecated
    public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.Du.registerConnectionCallbacks(connectionCallbacks);
    }

    @Deprecated
    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.Du.registerConnectionFailedListener(onConnectionFailedListener);
    }

    @Deprecated
    public void removeMoment(String str) {
        this.Du.removeMoment(str);
    }

    @Deprecated
    public void revokeAccessAndDisconnect(final OnAccessRevokedListener onAccessRevokedListener) {
        this.Du.l(new a.c<Status>() {
            public void a(Status status) {
                onAccessRevokedListener.onAccessRevoked(status.getStatus().bu());
            }
        });
    }

    @Deprecated
    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.Du.unregisterConnectionCallbacks(connectionCallbacks);
    }

    @Deprecated
    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.Du.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    @Deprecated
    public void writeMoment(Moment moment) {
        this.Du.writeMoment(moment);
    }
}
