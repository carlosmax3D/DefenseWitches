package com.google.android.gms.internal;

import android.net.Uri;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Moments;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.plus.model.moments.MomentBuffer;

public final class hz implements Moments {
    private final Api.b<hs> Ea;

    private static abstract class a extends Plus.a<Moments.LoadMomentsResult> {
        a(Api.b<hs> bVar) {
            super(bVar);
        }

        /* renamed from: K */
        public Moments.LoadMomentsResult e(final Status status) {
            return new Moments.LoadMomentsResult() {
                public MomentBuffer getMomentBuffer() {
                    return null;
                }

                public String getNextPageToken() {
                    return null;
                }

                public Status getStatus() {
                    return status;
                }

                public String getUpdated() {
                    return null;
                }

                public void release() {
                }
            };
        }
    }

    private static abstract class b extends Plus.a<Status> {
        b(Api.b<hs> bVar) {
            super(bVar);
        }

        /* renamed from: g */
        public Status e(Status status) {
            return status;
        }
    }

    private static abstract class c extends Plus.a<Status> {
        c(Api.b<hs> bVar) {
            super(bVar);
        }

        /* renamed from: g */
        public Status e(Status status) {
            return status;
        }
    }

    public hz(Api.b<hs> bVar) {
        this.Ea = bVar;
    }

    public PendingResult<Moments.LoadMomentsResult> load(GoogleApiClient googleApiClient) {
        return googleApiClient.a(new a(this.Ea) {
            /* access modifiers changed from: protected */
            public void a(hs hsVar) {
                hsVar.j(this);
            }
        });
    }

    public PendingResult<Moments.LoadMomentsResult> load(GoogleApiClient googleApiClient, int i, String str, Uri uri, String str2, String str3) {
        final int i2 = i;
        final String str4 = str;
        final Uri uri2 = uri;
        final String str5 = str2;
        final String str6 = str3;
        return googleApiClient.a(new a(this.Ea) {
            /* access modifiers changed from: protected */
            public void a(hs hsVar) {
                hsVar.a(this, i2, str4, uri2, str5, str6);
            }
        });
    }

    public PendingResult<Status> remove(GoogleApiClient googleApiClient, final String str) {
        return googleApiClient.b(new b(this.Ea) {
            /* access modifiers changed from: protected */
            public void a(hs hsVar) {
                hsVar.removeMoment(str);
                a(Status.nA);
            }
        });
    }

    public PendingResult<Status> write(GoogleApiClient googleApiClient, final Moment moment) {
        return googleApiClient.b(new c(this.Ea) {
            /* access modifiers changed from: protected */
            public void a(hs hsVar) {
                hsVar.writeMoment(moment);
                a(Status.nA);
            }
        });
    }
}
