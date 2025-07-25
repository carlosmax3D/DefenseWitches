package com.google.android.gms.drive;

import android.content.Context;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.internal.h;
import com.google.android.gms.drive.internal.j;
import com.google.android.gms.internal.dt;
import java.util.List;

public final class Drive {
    public static final Api API = new Api(jO, new Scope[0]);
    public static final DriveApi DriveApi = new h();
    public static final Scope SCOPE_FILE = new Scope(Scopes.DRIVE_FILE);
    public static final Api.b<j> jO = new Api.b<j>() {
        /* renamed from: d */
        public j b(Context context, dt dtVar, GoogleApiClient.ApiOptions apiOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            List<String> bH = dtVar.bH();
            return new j(context, dtVar, connectionCallbacks, onConnectionFailedListener, (String[]) bH.toArray(new String[bH.size()]));
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }
    };

    private Drive() {
    }
}
