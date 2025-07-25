package com.google.android.gms.drive.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataChangeSet;

public class m implements DriveResource {
    /* access modifiers changed from: private */
    public final DriveId qG;

    private abstract class a extends i<DriveResource.MetadataResult> {
        private a() {
        }

        /* renamed from: s */
        public DriveResource.MetadataResult e(Status status) {
            return new c(status, (Metadata) null);
        }
    }

    private static class b extends a {
        private final a.c<DriveResource.MetadataResult> jW;

        public b(a.c<DriveResource.MetadataResult> cVar) {
            this.jW = cVar;
        }

        public void a(OnMetadataResponse onMetadataResponse) throws RemoteException {
            this.jW.a(new c(Status.nA, new g(onMetadataResponse.cU())));
        }

        public void m(Status status) throws RemoteException {
            this.jW.a(new c(status, (Metadata) null));
        }
    }

    private static class c implements DriveResource.MetadataResult {
        private final Status jY;
        private final Metadata rq;

        public c(Status status, Metadata metadata) {
            this.jY = status;
            this.rq = metadata;
        }

        public Metadata getMetadata() {
            return this.rq;
        }

        public Status getStatus() {
            return this.jY;
        }
    }

    private abstract class d extends i<DriveResource.MetadataResult> {
        private d() {
        }

        /* renamed from: s */
        public DriveResource.MetadataResult e(Status status) {
            return new c(status, (Metadata) null);
        }
    }

    public m(DriveId driveId) {
        this.qG = driveId;
    }

    public DriveId getDriveId() {
        return this.qG;
    }

    public PendingResult<DriveResource.MetadataResult> getMetadata(GoogleApiClient googleApiClient) {
        return googleApiClient.a(new a() {
            /* access modifiers changed from: protected */
            public void a(j jVar) {
                try {
                    jVar.cN().a(new GetMetadataRequest(m.this.qG), (p) new b(this));
                } catch (RemoteException e) {
                    a(new c(new Status(8, e.getLocalizedMessage(), (PendingIntent) null), (Metadata) null));
                }
            }
        });
    }

    public PendingResult<DriveResource.MetadataResult> updateMetadata(GoogleApiClient googleApiClient, final MetadataChangeSet metadataChangeSet) {
        if (metadataChangeSet != null) {
            return googleApiClient.b(new d() {
                /* access modifiers changed from: protected */
                public void a(j jVar) {
                    try {
                        jVar.cN().a(new UpdateMetadataRequest(m.this.qG, metadataChangeSet.cM()), (p) new b(this));
                    } catch (RemoteException e) {
                        a(new c(new Status(8, e.getLocalizedMessage(), (PendingIntent) null), (Metadata) null));
                    }
                }
            });
        }
        throw new IllegalArgumentException("ChangeSet must be provided.");
    }
}
