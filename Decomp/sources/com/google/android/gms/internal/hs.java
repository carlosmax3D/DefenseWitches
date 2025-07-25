package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.hr;
import com.google.android.gms.plus.Moments;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.plus.model.moments.MomentBuffer;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class hs extends dw<hr> implements GooglePlayServicesClient {
    private Person DK;
    private hu DL;

    final class a extends hn {
        private final a.c<Moments.LoadMomentsResult> Dp;

        public a(a.c<Moments.LoadMomentsResult> cVar) {
            this.Dp = cVar;
        }

        public void a(DataHolder dataHolder, String str, String str2) {
            DataHolder dataHolder2;
            Status status = new Status(dataHolder.getStatusCode(), (String) null, dataHolder.getMetadata() != null ? (PendingIntent) dataHolder.getMetadata().getParcelable("pendingIntent") : null);
            if (status.isSuccess() || dataHolder == null) {
                dataHolder2 = dataHolder;
            } else {
                if (!dataHolder.isClosed()) {
                    dataHolder.close();
                }
                dataHolder2 = null;
            }
            hs.this.a((dw<T>.b<?>) new b(this.Dp, status, dataHolder2, str, str2));
        }
    }

    final class b extends dw<hr>.d<a.c<Moments.LoadMomentsResult>> implements Moments.LoadMomentsResult {
        private final String DN;
        private MomentBuffer DO;
        private final Status jY;
        private final String qT;

        public b(a.c<Moments.LoadMomentsResult> cVar, Status status, DataHolder dataHolder, String str, String str2) {
            super(cVar, dataHolder);
            this.jY = status;
            this.qT = str;
            this.DN = str2;
        }

        /* access modifiers changed from: protected */
        public void a(a.c<Moments.LoadMomentsResult> cVar, DataHolder dataHolder) {
            this.DO = dataHolder != null ? new MomentBuffer(dataHolder) : null;
            cVar.a(this);
        }

        public MomentBuffer getMomentBuffer() {
            return this.DO;
        }

        public String getNextPageToken() {
            return this.qT;
        }

        public Status getStatus() {
            return this.jY;
        }

        public String getUpdated() {
            return this.DN;
        }

        public void release() {
            if (this.DO != null) {
                this.DO.close();
            }
        }
    }

    final class c extends hn {
        private final a.c<People.LoadPeopleResult> Dp;

        public c(a.c<People.LoadPeopleResult> cVar) {
            this.Dp = cVar;
        }

        public void a(DataHolder dataHolder, String str) {
            DataHolder dataHolder2;
            Status status = new Status(dataHolder.getStatusCode(), (String) null, dataHolder.getMetadata() != null ? (PendingIntent) dataHolder.getMetadata().getParcelable("pendingIntent") : null);
            if (status.isSuccess() || dataHolder == null) {
                dataHolder2 = dataHolder;
            } else {
                if (!dataHolder.isClosed()) {
                    dataHolder.close();
                }
                dataHolder2 = null;
            }
            hs.this.a((dw<T>.b<?>) new d(this.Dp, status, dataHolder2, str));
        }
    }

    final class d extends dw<hr>.d<a.c<People.LoadPeopleResult>> implements People.LoadPeopleResult {
        private PersonBuffer DP;
        private final Status jY;
        private final String qT;

        public d(a.c<People.LoadPeopleResult> cVar, Status status, DataHolder dataHolder, String str) {
            super(cVar, dataHolder);
            this.jY = status;
            this.qT = str;
        }

        /* access modifiers changed from: protected */
        public void a(a.c<People.LoadPeopleResult> cVar, DataHolder dataHolder) {
            this.DP = dataHolder != null ? new PersonBuffer(dataHolder) : null;
            cVar.a(this);
        }

        public String getNextPageToken() {
            return this.qT;
        }

        public PersonBuffer getPersonBuffer() {
            return this.DP;
        }

        public Status getStatus() {
            return this.jY;
        }

        public void release() {
            if (this.DP != null) {
                this.DP.close();
            }
        }
    }

    final class e extends hn {
        private final a.c<Status> Dp;

        public e(a.c<Status> cVar) {
            this.Dp = cVar;
        }

        public void b(int i, Bundle bundle) {
            hs.this.a((dw<T>.b<?>) new f(this.Dp, new Status(i, (String) null, bundle != null ? (PendingIntent) bundle.getParcelable("pendingIntent") : null)));
        }
    }

    final class f extends dw<hr>.b<a.c<Status>> {
        private final Status jY;

        public f(a.c<Status> cVar, Status status) {
            super(cVar);
            this.jY = status;
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: c */
        public void b(a.c<Status> cVar) {
            hs.this.disconnect();
            if (cVar != null) {
                cVar.a(this.jY);
            }
        }
    }

    @Deprecated
    public hs(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, hu huVar) {
        this(context, (GoogleApiClient.ConnectionCallbacks) new dw.c(connectionCallbacks), (GoogleApiClient.OnConnectionFailedListener) new dw.g(onConnectionFailedListener), huVar);
    }

    public hs(Context context, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, hu huVar) {
        super(context, connectionCallbacks, onConnectionFailedListener, huVar.eR());
        this.DL = huVar;
    }

    /* access modifiers changed from: protected */
    public void a(int i, IBinder iBinder, Bundle bundle) {
        if (i == 0 && bundle != null && bundle.containsKey("loaded_person")) {
            this.DK = ig.g(bundle.getByteArray("loaded_person"));
        }
        super.a(i, iBinder, bundle);
    }

    public void a(a.c<People.LoadPeopleResult> cVar, int i, String str) {
        bP();
        c cVar2 = new c(cVar);
        try {
            ((hr) bQ()).a(cVar2, 1, i, -1, str);
        } catch (RemoteException e2) {
            cVar2.a(DataHolder.empty(8), (String) null);
        }
    }

    public void a(a.c<Moments.LoadMomentsResult> cVar, int i, String str, Uri uri, String str2, String str3) {
        bP();
        a aVar = cVar != null ? new a(cVar) : null;
        try {
            ((hr) bQ()).a(aVar, i, str, uri, str2, str3);
        } catch (RemoteException e2) {
            aVar.a(DataHolder.empty(8), (String) null, (String) null);
        }
    }

    public void a(a.c<People.LoadPeopleResult> cVar, Collection<String> collection) {
        bP();
        c cVar2 = new c(cVar);
        try {
            ((hr) bQ()).a((ho) cVar2, (List<String>) new ArrayList(collection));
        } catch (RemoteException e2) {
            cVar2.a(DataHolder.empty(8), (String) null);
        }
    }

    public void a(a.c<People.LoadPeopleResult> cVar, String[] strArr) {
        a(cVar, (Collection<String>) Arrays.asList(strArr));
    }

    /* access modifiers changed from: protected */
    public void a(ec ecVar, dw.e eVar) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putStringArray("request_visible_actions", this.DL.eS());
        ecVar.a(eVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, this.DL.eV(), this.DL.eU(), bO(), this.DL.getAccountName(), bundle);
    }

    /* access modifiers changed from: protected */
    public String am() {
        return "com.google.android.gms.plus.service.START";
    }

    /* access modifiers changed from: protected */
    public String an() {
        return "com.google.android.gms.plus.internal.IPlusService";
    }

    /* access modifiers changed from: protected */
    /* renamed from: ay */
    public hr p(IBinder iBinder) {
        return hr.a.ax(iBinder);
    }

    public boolean az(String str) {
        return Arrays.asList(bO()).contains(str);
    }

    public void clearDefaultAccount() {
        bP();
        try {
            this.DK = null;
            ((hr) bQ()).clearDefaultAccount();
        } catch (RemoteException e2) {
            throw new IllegalStateException(e2);
        }
    }

    public String getAccountName() {
        bP();
        try {
            return ((hr) bQ()).getAccountName();
        } catch (RemoteException e2) {
            throw new IllegalStateException(e2);
        }
    }

    public Person getCurrentPerson() {
        bP();
        return this.DK;
    }

    public void i(a.c<People.LoadPeopleResult> cVar, String str) {
        a(cVar, 0, str);
    }

    public void j(a.c<Moments.LoadMomentsResult> cVar) {
        a(cVar, 20, (String) null, (Uri) null, (String) null, "me");
    }

    public void k(a.c<People.LoadPeopleResult> cVar) {
        bP();
        c cVar2 = new c(cVar);
        try {
            ((hr) bQ()).a(cVar2, 2, 1, -1, (String) null);
        } catch (RemoteException e2) {
            cVar2.a(DataHolder.empty(8), (String) null);
        }
    }

    public void l(a.c<Status> cVar) {
        bP();
        clearDefaultAccount();
        e eVar = new e(cVar);
        try {
            ((hr) bQ()).b(eVar);
        } catch (RemoteException e2) {
            eVar.b(8, (Bundle) null);
        }
    }

    public void removeMoment(String str) {
        bP();
        try {
            ((hr) bQ()).removeMoment(str);
        } catch (RemoteException e2) {
            throw new IllegalStateException(e2);
        }
    }

    public void writeMoment(Moment moment) {
        bP();
        try {
            ((hr) bQ()).a(ey.a((id) moment));
        } catch (RemoteException e2) {
            throw new IllegalStateException(e2);
        }
    }
}
