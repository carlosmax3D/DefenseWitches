package com.google.android.gms.drive.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.internal.h;

public class k extends m implements DriveFile {

    private abstract class a extends i<Status> {
        private a() {
        }

        /* renamed from: g */
        public Status e(Status status) {
            return status;
        }
    }

    private static class b extends a {
        private final a.c<DriveApi.ContentsResult> jW;
        private final DriveFile.DownloadProgressListener rk;

        public b(a.c<DriveApi.ContentsResult> cVar, DriveFile.DownloadProgressListener downloadProgressListener) {
            this.jW = cVar;
            this.rk = downloadProgressListener;
        }

        public void a(OnContentsResponse onContentsResponse) throws RemoteException {
            this.jW.a(new h.a(Status.nA, onContentsResponse.cQ()));
        }

        public void a(OnDownloadProgressResponse onDownloadProgressResponse) throws RemoteException {
            if (this.rk != null) {
                this.rk.onProgress(onDownloadProgressResponse.cR(), onDownloadProgressResponse.cS());
            }
        }

        public void m(Status status) throws RemoteException {
            this.jW.a(new h.a(status, (Contents) null));
        }
    }

    private abstract class c extends i<DriveApi.ContentsResult> {
        private c() {
        }

        /* renamed from: o */
        public DriveApi.ContentsResult e(Status status) {
            return new h.a(status, (Contents) null);
        }
    }

    public k(DriveId driveId) {
        super(driveId);
    }

    public PendingResult<Status> commitAndCloseContents(GoogleApiClient googleApiClient, final Contents contents) {
        if (contents != null) {
            return googleApiClient.b(new a() {
                /* access modifiers changed from: protected */
                public void a(j jVar) {
                    try {
                        contents.close();
                        jVar.cN().a(new CloseContentsRequest(contents, true), (p) new z(this));
                    } catch (RemoteException e) {
                        a(new Status(8, e.getLocalizedMessage(), (PendingIntent) null));
                    }
                }
            });
        }
        throw new IllegalArgumentException("Contents must be provided.");
    }

    public PendingResult<Status> discardContents(GoogleApiClient googleApiClient, Contents contents) {
        return Drive.DriveApi.discardContents(googleApiClient, contents);
    }

    public PendingResult<DriveApi.ContentsResult> openContents(GoogleApiClient googleApiClient, final int i, final DriveFile.DownloadProgressListener downloadProgressListener) {
        if (i == 268435456 || i == 536870912 || i == 805306368) {
            return googleApiClient.a(new c() {
                /* access modifiers changed from: protected */
                public void a(j jVar) {
                    try {
                        jVar.cN().a(new OpenContentsRequest(k.this.getDriveId(), i), (p) new b(this, downloadProgressListener));
                    } catch (RemoteException e) {
                        a(new h.a(new Status(8, e.getLocalizedMessage(), (PendingIntent) null), (Contents) null));
                    }
                }
            });
        }
        throw new IllegalArgumentException("Invalid mode provided.");
    }
}
