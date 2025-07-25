package com.google.android.gms.drive.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;

public class l extends m implements DriveFolder {

    private static class a extends a {
        private final a.c<DriveFolder.DriveFileResult> jW;

        public a(a.c<DriveFolder.DriveFileResult> cVar) {
            this.jW = cVar;
        }

        public void a(OnDriveIdResponse onDriveIdResponse) throws RemoteException {
            this.jW.a(new d(Status.nA, new k(onDriveIdResponse.getDriveId())));
        }

        public void m(Status status) throws RemoteException {
            this.jW.a(new d(status, (DriveFile) null));
        }
    }

    private static class b extends a {
        private final a.c<DriveFolder.DriveFolderResult> jW;

        public b(a.c<DriveFolder.DriveFolderResult> cVar) {
            this.jW = cVar;
        }

        public void a(OnDriveIdResponse onDriveIdResponse) throws RemoteException {
            this.jW.a(new e(Status.nA, new l(onDriveIdResponse.getDriveId())));
        }

        public void m(Status status) throws RemoteException {
            this.jW.a(new e(status, (DriveFolder) null));
        }
    }

    private abstract class c extends i<DriveFolder.DriveFolderResult> {
        private c() {
        }

        /* renamed from: r */
        public DriveFolder.DriveFolderResult e(Status status) {
            return new e(status, (DriveFolder) null);
        }
    }

    private static class d implements DriveFolder.DriveFileResult {
        private final Status jY;
        private final DriveFile rn;

        public d(Status status, DriveFile driveFile) {
            this.jY = status;
            this.rn = driveFile;
        }

        public DriveFile getDriveFile() {
            return this.rn;
        }

        public Status getStatus() {
            return this.jY;
        }
    }

    private static class e implements DriveFolder.DriveFolderResult {
        private final Status jY;
        private final DriveFolder ro;

        public e(Status status, DriveFolder driveFolder) {
            this.jY = status;
            this.ro = driveFolder;
        }

        public DriveFolder getDriveFolder() {
            return this.ro;
        }

        public Status getStatus() {
            return this.jY;
        }
    }

    public l(DriveId driveId) {
        super(driveId);
    }

    public PendingResult<DriveFolder.DriveFileResult> createFile(GoogleApiClient googleApiClient, final MetadataChangeSet metadataChangeSet, final Contents contents) {
        if (metadataChangeSet == null) {
            throw new IllegalArgumentException("MetatadataChangeSet must be provided.");
        } else if (contents == null) {
            throw new IllegalArgumentException("Contents must be provided.");
        } else if (!DriveFolder.MIME_TYPE.equals(metadataChangeSet.getMimeType())) {
            return googleApiClient.b(new i<DriveFolder.DriveFileResult>() {
                /* access modifiers changed from: protected */
                public void a(j jVar) {
                    try {
                        contents.close();
                        jVar.cN().a(new CreateFileRequest(l.this.getDriveId(), metadataChangeSet.cM(), contents), (p) new a(this));
                    } catch (RemoteException e) {
                        a(new d(new Status(8, e.getLocalizedMessage(), (PendingIntent) null), (DriveFile) null));
                    }
                }

                /* renamed from: q */
                public DriveFolder.DriveFileResult e(Status status) {
                    return new d(status, (DriveFile) null);
                }
            });
        } else {
            throw new IllegalArgumentException("May not create folders (mimetype: application/vnd.google-apps.folder) using this method. Use DriveFolder.createFolder() instead.");
        }
    }

    public PendingResult<DriveFolder.DriveFolderResult> createFolder(GoogleApiClient googleApiClient, final MetadataChangeSet metadataChangeSet) {
        if (metadataChangeSet == null) {
            throw new IllegalArgumentException("MetatadataChangeSet must be provided.");
        } else if (metadataChangeSet.getMimeType() == null || metadataChangeSet.getMimeType().equals(DriveFolder.MIME_TYPE)) {
            return googleApiClient.b(new c() {
                /* access modifiers changed from: protected */
                public void a(j jVar) {
                    try {
                        jVar.cN().a(new CreateFolderRequest(l.this.getDriveId(), metadataChangeSet.cM()), (p) new b(this));
                    } catch (RemoteException e) {
                        a(new e(new Status(8, e.getLocalizedMessage(), (PendingIntent) null), (DriveFolder) null));
                    }
                }
            });
        } else {
            throw new IllegalArgumentException("The mimetype must be of type application/vnd.google-apps.folder");
        }
    }

    public PendingResult<DriveApi.MetadataBufferResult> listChildren(GoogleApiClient googleApiClient) {
        return queryChildren(googleApiClient, (Query) null);
    }

    public PendingResult<DriveApi.MetadataBufferResult> queryChildren(GoogleApiClient googleApiClient, Query query) {
        Query.Builder addFilter = new Query.Builder().addFilter(Filters.in(SearchableField.PARENTS, getDriveId()));
        if (query != null) {
            if (query.getFilter() != null) {
                addFilter.addFilter(query.getFilter());
            }
            addFilter.setPageToken(query.getPageToken());
        }
        return new h().query(googleApiClient, addFilter.build());
    }
}
