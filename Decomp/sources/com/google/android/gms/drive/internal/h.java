package com.google.android.gms.drive.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.CreateFileActivityBuilder;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.android.gms.drive.query.Query;

public class h implements DriveApi {

    static class a implements DriveApi.ContentsResult {
        private final Status jY;
        private final Contents qK;

        public a(Status status, Contents contents) {
            this.jY = status;
            this.qK = contents;
        }

        public Contents getContents() {
            return this.qK;
        }

        public Status getStatus() {
            return this.jY;
        }
    }

    abstract class b extends i<Status> {
        b() {
        }

        /* renamed from: g */
        public Status e(Status status) {
            return status;
        }
    }

    private static class c extends a {
        private final a.c<DriveApi.DriveIdResult> jW;

        public c(a.c<DriveApi.DriveIdResult> cVar) {
            this.jW = cVar;
        }

        public void a(OnMetadataResponse onMetadataResponse) throws RemoteException {
            this.jW.a(new d(Status.nA, new g(onMetadataResponse.cU()).getDriveId()));
        }

        public void m(Status status) throws RemoteException {
            this.jW.a(new d(status, (DriveId) null));
        }
    }

    static class d implements DriveApi.DriveIdResult {
        private final Status jY;
        private final DriveId qG;

        public d(Status status, DriveId driveId) {
            this.jY = status;
            this.qG = driveId;
        }

        public DriveId getDriveId() {
            return this.qG;
        }

        public Status getStatus() {
            return this.jY;
        }
    }

    abstract class e extends i<DriveApi.DriveIdResult> {
        e() {
        }

        /* renamed from: n */
        public DriveApi.DriveIdResult e(Status status) {
            return new d(status, (DriveId) null);
        }
    }

    static class f implements DriveApi.MetadataBufferResult {
        private final Status jY;
        private final MetadataBuffer rf;

        public f(Status status, MetadataBuffer metadataBuffer) {
            this.jY = status;
            this.rf = metadataBuffer;
        }

        public MetadataBuffer getMetadataBuffer() {
            return this.rf;
        }

        public Status getStatus() {
            return this.jY;
        }
    }

    private static class g extends a {
        private final a.c<DriveApi.ContentsResult> jW;

        public g(a.c<DriveApi.ContentsResult> cVar) {
            this.jW = cVar;
        }

        public void a(OnContentsResponse onContentsResponse) throws RemoteException {
            this.jW.a(new a(Status.nA, onContentsResponse.cQ()));
        }

        public void m(Status status) throws RemoteException {
            this.jW.a(new a(status, (Contents) null));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.h$h  reason: collision with other inner class name */
    abstract class C0004h extends i<DriveApi.ContentsResult> {
        C0004h() {
        }

        /* renamed from: o */
        public DriveApi.ContentsResult e(Status status) {
            return new a(status, (Contents) null);
        }
    }

    static class i extends a {
        private final a.c<DriveApi.MetadataBufferResult> jW;

        public i(a.c<DriveApi.MetadataBufferResult> cVar) {
            this.jW = cVar;
        }

        public void a(OnListEntriesResponse onListEntriesResponse) throws RemoteException {
            this.jW.a(new f(Status.nA, new MetadataBuffer(onListEntriesResponse.cT(), (String) null)));
        }

        public void m(Status status) throws RemoteException {
            this.jW.a(new f(status, (MetadataBuffer) null));
        }
    }

    abstract class j extends i<DriveApi.MetadataBufferResult> {
        j() {
        }

        /* renamed from: p */
        public DriveApi.MetadataBufferResult e(Status status) {
            return new f(status, (MetadataBuffer) null);
        }
    }

    abstract class k extends i<Status> {
        k() {
        }

        /* renamed from: g */
        public Status e(Status status) {
            return status;
        }
    }

    public PendingResult<Status> discardContents(GoogleApiClient googleApiClient, final Contents contents) {
        return googleApiClient.b(new b() {
            /* access modifiers changed from: protected */
            public void a(j jVar) {
                try {
                    jVar.cN().a(new CloseContentsRequest(contents, false), (p) new z(this));
                } catch (RemoteException e) {
                    a(new Status(8, e.getLocalizedMessage(), (PendingIntent) null));
                }
            }
        });
    }

    public PendingResult<DriveApi.DriveIdResult> fetchDriveId(GoogleApiClient googleApiClient, final String str) {
        return googleApiClient.a(new e() {
            /* access modifiers changed from: protected */
            public void a(j jVar) {
                try {
                    jVar.cN().a(new GetMetadataRequest(DriveId.ab(str)), (p) new c(this));
                } catch (RemoteException e) {
                    a(new d(new Status(8, e.getLocalizedMessage(), (PendingIntent) null), (DriveId) null));
                }
            }
        });
    }

    public DriveFile getFile(GoogleApiClient googleApiClient, DriveId driveId) {
        if (driveId == null) {
            throw new IllegalArgumentException("Id must be provided.");
        } else if (googleApiClient.isConnected()) {
            return new k(driveId);
        } else {
            throw new IllegalStateException("Client must be connected");
        }
    }

    public DriveFolder getFolder(GoogleApiClient googleApiClient, DriveId driveId) {
        if (driveId == null) {
            throw new IllegalArgumentException("Id must be provided.");
        } else if (googleApiClient.isConnected()) {
            return new l(driveId);
        } else {
            throw new IllegalStateException("Client must be connected");
        }
    }

    public DriveFolder getRootFolder(GoogleApiClient googleApiClient) {
        if (googleApiClient.isConnected()) {
            return new l(((j) googleApiClient.a(Drive.jO)).cO());
        }
        throw new IllegalStateException("Client must be connected");
    }

    public PendingResult<DriveApi.ContentsResult> newContents(GoogleApiClient googleApiClient) {
        return googleApiClient.a(new C0004h() {
            /* access modifiers changed from: protected */
            public void a(j jVar) {
                try {
                    jVar.cN().a(new CreateContentsRequest(), (p) new g(this));
                } catch (RemoteException e) {
                    a(new a(new Status(8, e.getLocalizedMessage(), (PendingIntent) null), (Contents) null));
                }
            }
        });
    }

    public CreateFileActivityBuilder newCreateFileActivityBuilder() {
        return new CreateFileActivityBuilder();
    }

    public OpenFileActivityBuilder newOpenFileActivityBuilder() {
        return new OpenFileActivityBuilder();
    }

    public PendingResult<DriveApi.MetadataBufferResult> query(GoogleApiClient googleApiClient, final Query query) {
        if (query != null) {
            return googleApiClient.a(new j() {
                /* access modifiers changed from: protected */
                public void a(j jVar) {
                    try {
                        jVar.cN().a(new QueryRequest(query), (p) new i(this));
                    } catch (RemoteException e) {
                        a(new f(new Status(8, e.getLocalizedMessage(), (PendingIntent) null), (MetadataBuffer) null));
                    }
                }
            });
        }
        throw new IllegalArgumentException("Query must be provided.");
    }

    public PendingResult<Status> requestSync(GoogleApiClient googleApiClient) {
        return googleApiClient.b(new k() {
            /* access modifiers changed from: protected */
            public void a(j jVar) {
                try {
                    jVar.cN().a((p) new z(this));
                } catch (RemoteException e) {
                    a(new Status(8, e.getLocalizedMessage(), (PendingIntent) null));
                }
            }
        });
    }
}
