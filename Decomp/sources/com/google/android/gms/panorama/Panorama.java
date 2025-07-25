package com.google.android.gms.panorama;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.dt;
import com.google.android.gms.internal.hm;

public final class Panorama {
    public static final Api API = new Api(jO, new Scope[0]);
    static final Api.b<hm> jO = new Api.b<hm>() {
        /* renamed from: f */
        public hm b(Context context, dt dtVar, GoogleApiClient.ApiOptions apiOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            return new hm(context, connectionCallbacks, onConnectionFailedListener);
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }
    };

    public interface PanoramaResult extends Result {
        Intent getViewerIntent();
    }

    public interface a extends PanoramaResult {
    }

    private static abstract class b extends a.C0001a<PanoramaResult, hm> {
        public b() {
            super(Panorama.jO);
        }

        /* renamed from: J */
        public PanoramaResult e(final Status status) {
            return new PanoramaResult() {
                public Status getStatus() {
                    return status;
                }

                public Intent getViewerIntent() {
                    return null;
                }
            };
        }
    }

    private Panorama() {
    }

    public static PendingResult<PanoramaResult> loadPanoramaInfo(GoogleApiClient googleApiClient, final Uri uri) {
        return googleApiClient.a(new b() {
            /* access modifiers changed from: protected */
            public void a(hm hmVar) {
                hmVar.a(this, uri, false);
            }
        });
    }

    public static PendingResult<PanoramaResult> loadPanoramaInfoAndGrantAccess(GoogleApiClient googleApiClient, final Uri uri) {
        return googleApiClient.a(new b() {
            /* access modifiers changed from: protected */
            public void a(hm hmVar) {
                hmVar.a(this, uri, true);
            }
        });
    }
}
