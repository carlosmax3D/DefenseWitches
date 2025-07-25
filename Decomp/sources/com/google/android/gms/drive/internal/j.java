package com.google.android.gms.drive.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.internal.o;
import com.google.android.gms.internal.dt;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.ec;
import com.google.android.gms.internal.eg;

public class j extends dw<o> {
    private final String jG;
    private DriveId rg;

    public j(Context context, dt dtVar, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String[] strArr) {
        super(context, connectionCallbacks, onConnectionFailedListener, strArr);
        this.jG = (String) eg.b(dtVar.bF(), (Object) "Must call Api.ClientBuilder.setAccountName()");
    }

    /* access modifiers changed from: protected */
    /* renamed from: B */
    public o p(IBinder iBinder) {
        return o.a.C(iBinder);
    }

    /* access modifiers changed from: protected */
    public void a(int i, IBinder iBinder, Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(getClass().getClassLoader());
            this.rg = (DriveId) bundle.getParcelable("com.google.android.gms.drive.root_id");
        }
        super.a(i, iBinder, bundle);
    }

    /* access modifiers changed from: protected */
    public void a(ec ecVar, dw.e eVar) throws RemoteException {
        String packageName = getContext().getPackageName();
        eg.f(eVar);
        eg.f(packageName);
        eg.f(bO());
        ecVar.a(eVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, packageName, bO(), this.jG, new Bundle());
    }

    /* access modifiers changed from: protected */
    public String am() {
        return "com.google.android.gms.drive.ApiService.START";
    }

    /* access modifiers changed from: protected */
    public String an() {
        return "com.google.android.gms.drive.internal.IDriveService";
    }

    public o cN() {
        return (o) bQ();
    }

    public DriveId cO() {
        return this.rg;
    }
}
