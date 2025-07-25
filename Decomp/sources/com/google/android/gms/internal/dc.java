package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.appstate.AppStateBuffer;
import com.google.android.gms.appstate.AppStateManager;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.de;
import com.google.android.gms.internal.dw;

public final class dc extends dw<de> {
    private final String jG;

    final class a extends db {
        private final a.c<AppStateManager.StateDeletedResult> jW;

        public a(a.c<AppStateManager.StateDeletedResult> cVar) {
            this.jW = (a.c) eg.b(cVar, (Object) "Result holder must not be null");
        }

        public void onStateDeleted(int i, int i2) {
            dc.this.a((dw<T>.b<?>) new b(this.jW, new Status(i), i2));
        }
    }

    final class b extends dw<de>.b<a.c<AppStateManager.StateDeletedResult>> implements AppStateManager.StateDeletedResult {
        private final Status jY;
        private final int jZ;

        public b(a.c<AppStateManager.StateDeletedResult> cVar, Status status, int i) {
            super(cVar);
            this.jY = status;
            this.jZ = i;
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }

        /* renamed from: c */
        public void b(a.c<AppStateManager.StateDeletedResult> cVar) {
            cVar.a(this);
        }

        public int getStateKey() {
            return this.jZ;
        }

        public Status getStatus() {
            return this.jY;
        }
    }

    final class c extends db {
        private final a.c<AppStateManager.StateListResult> jW;

        public c(a.c<AppStateManager.StateListResult> cVar) {
            this.jW = (a.c) eg.b(cVar, (Object) "Result holder must not be null");
        }

        public void a(DataHolder dataHolder) {
            dc.this.a((dw<T>.b<?>) new d(this.jW, new Status(dataHolder.getStatusCode()), dataHolder));
        }
    }

    final class d extends dw<de>.d<a.c<AppStateManager.StateListResult>> implements AppStateManager.StateListResult {
        private final Status jY;
        private final AppStateBuffer ka;

        public d(a.c<AppStateManager.StateListResult> cVar, Status status, DataHolder dataHolder) {
            super(cVar, dataHolder);
            this.jY = status;
            this.ka = new AppStateBuffer(dataHolder);
        }

        public void a(a.c<AppStateManager.StateListResult> cVar, DataHolder dataHolder) {
            cVar.a(this);
        }

        public AppStateBuffer getStateBuffer() {
            return this.ka;
        }

        public Status getStatus() {
            return this.jY;
        }
    }

    final class e extends db {
        private final a.c<AppStateManager.StateResult> jW;

        public e(a.c<AppStateManager.StateResult> cVar) {
            this.jW = (a.c) eg.b(cVar, (Object) "Result holder must not be null");
        }

        public void a(int i, DataHolder dataHolder) {
            dc.this.a((dw<T>.b<?>) new f(this.jW, i, dataHolder));
        }
    }

    final class f extends dw<de>.d<a.c<AppStateManager.StateResult>> implements AppStateManager.StateConflictResult, AppStateManager.StateLoadedResult, AppStateManager.StateResult {
        private final Status jY;
        private final int jZ;
        private final AppStateBuffer ka;

        public f(a.c<AppStateManager.StateResult> cVar, int i, DataHolder dataHolder) {
            super(cVar, dataHolder);
            this.jZ = i;
            this.jY = new Status(dataHolder.getStatusCode());
            this.ka = new AppStateBuffer(dataHolder);
        }

        private boolean aM() {
            return this.jY.getStatusCode() == 2000;
        }

        public void a(a.c<AppStateManager.StateResult> cVar, DataHolder dataHolder) {
            cVar.a(this);
        }

        public AppStateManager.StateConflictResult getConflictResult() {
            if (aM()) {
                return this;
            }
            return null;
        }

        public AppStateManager.StateLoadedResult getLoadedResult() {
            if (aM()) {
                return null;
            }
            return this;
        }

        public byte[] getLocalData() {
            if (this.ka.getCount() == 0) {
                return null;
            }
            return this.ka.get(0).getLocalData();
        }

        public String getResolvedVersion() {
            if (this.ka.getCount() == 0) {
                return null;
            }
            return this.ka.get(0).getConflictVersion();
        }

        public byte[] getServerData() {
            if (this.ka.getCount() == 0) {
                return null;
            }
            return this.ka.get(0).getConflictData();
        }

        public int getStateKey() {
            return this.jZ;
        }

        public Status getStatus() {
            return this.jY;
        }
    }

    final class g extends db {
        a.c<Status> jW;

        public g(a.c<Status> cVar) {
            this.jW = (a.c) eg.b(cVar, (Object) "Holder must not be null");
        }

        public void onSignOutComplete() {
            dc.this.a((dw<T>.b<?>) new h(this.jW, new Status(0)));
        }
    }

    final class h extends dw<de>.b<a.c<Status>> {
        private final Status jY;

        public h(a.c<Status> cVar, Status status) {
            super(cVar);
            this.jY = status;
        }

        /* access modifiers changed from: protected */
        public void aL() {
        }

        /* renamed from: c */
        public void b(a.c<Status> cVar) {
            cVar.a(this.jY);
        }
    }

    @Deprecated
    public dc(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, String str, String[] strArr) {
        this(context, (GoogleApiClient.ConnectionCallbacks) new dw.c(connectionCallbacks), (GoogleApiClient.OnConnectionFailedListener) new dw.g(onConnectionFailedListener), str, strArr);
    }

    public dc(Context context, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String str, String[] strArr) {
        super(context, connectionCallbacks, onConnectionFailedListener, strArr);
        this.jG = (String) eg.f(str);
    }

    public void a(a.c<AppStateManager.StateListResult> cVar) {
        try {
            ((de) bQ()).a(new c(cVar));
        } catch (RemoteException e2) {
            Log.w("AppStateClient", "service died");
        }
    }

    public void a(a.c<AppStateManager.StateDeletedResult> cVar, int i) {
        try {
            ((de) bQ()).b(new a(cVar), i);
        } catch (RemoteException e2) {
            Log.w("AppStateClient", "service died");
        }
    }

    public void a(a.c<AppStateManager.StateResult> cVar, int i, String str, byte[] bArr) {
        try {
            ((de) bQ()).a(new e(cVar), i, str, bArr);
        } catch (RemoteException e2) {
            Log.w("AppStateClient", "service died");
        }
    }

    public void a(a.c<AppStateManager.StateResult> cVar, int i, byte[] bArr) {
        try {
            ((de) bQ()).a(cVar == null ? null : new e(cVar), i, bArr);
        } catch (RemoteException e2) {
            Log.w("AppStateClient", "service died");
        }
    }

    /* access modifiers changed from: protected */
    public void a(ec ecVar, dw.e eVar) throws RemoteException {
        ecVar.a((eb) eVar, (int) GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), this.jG, bO());
    }

    /* access modifiers changed from: protected */
    public void a(String... strArr) {
        boolean z = false;
        for (String equals : strArr) {
            if (equals.equals(Scopes.APP_STATE)) {
                z = true;
            }
        }
        eg.a(z, String.format("App State APIs requires %s to function.", new Object[]{Scopes.APP_STATE}));
    }

    /* access modifiers changed from: protected */
    public String am() {
        return "com.google.android.gms.appstate.service.START";
    }

    /* access modifiers changed from: protected */
    public String an() {
        return "com.google.android.gms.appstate.internal.IAppStateService";
    }

    public void b(a.c<Status> cVar) {
        try {
            ((de) bQ()).b(new g(cVar));
        } catch (RemoteException e2) {
            Log.w("AppStateClient", "service died");
        }
    }

    public void b(a.c<AppStateManager.StateResult> cVar, int i) {
        try {
            ((de) bQ()).a(new e(cVar), i);
        } catch (RemoteException e2) {
            Log.w("AppStateClient", "service died");
        }
    }

    public int getMaxNumKeys() {
        try {
            return ((de) bQ()).getMaxNumKeys();
        } catch (RemoteException e2) {
            Log.w("AppStateClient", "service died");
            return 2;
        }
    }

    public int getMaxStateSize() {
        try {
            return ((de) bQ()).getMaxStateSize();
        } catch (RemoteException e2) {
            Log.w("AppStateClient", "service died");
            return 2;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: r */
    public de p(IBinder iBinder) {
        return de.a.t(iBinder);
    }
}
