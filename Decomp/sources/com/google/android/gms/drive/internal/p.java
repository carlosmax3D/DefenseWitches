package com.google.android.gms.drive.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

public interface p extends IInterface {

    public static abstract class a extends Binder implements p {

        /* renamed from: com.google.android.gms.drive.internal.p$a$a  reason: collision with other inner class name */
        private static class C0006a implements p {
            private IBinder dU;

            C0006a(IBinder iBinder) {
                this.dU = iBinder;
            }

            public void a(OnContentsResponse onContentsResponse) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    if (onContentsResponse != null) {
                        obtain.writeInt(1);
                        onContentsResponse.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(OnDownloadProgressResponse onDownloadProgressResponse) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    if (onDownloadProgressResponse != null) {
                        obtain.writeInt(1);
                        onDownloadProgressResponse.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(OnDriveIdResponse onDriveIdResponse) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    if (onDriveIdResponse != null) {
                        obtain.writeInt(1);
                        onDriveIdResponse.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(OnListEntriesResponse onListEntriesResponse) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    if (onListEntriesResponse != null) {
                        obtain.writeInt(1);
                        onListEntriesResponse.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(OnMetadataResponse onMetadataResponse) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    if (onMetadataResponse != null) {
                        obtain.writeInt(1);
                        onMetadataResponse.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.dU;
            }

            public void m(Status status) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    if (status != null) {
                        obtain.writeInt(1);
                        status.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    this.dU.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public a() {
            attachInterface(this, "com.google.android.gms.drive.internal.IDriveServiceCallbacks");
        }

        public static p D(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof p)) ? new C0006a(iBinder) : (p) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: com.google.android.gms.common.api.Status} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: com.google.android.gms.drive.internal.OnContentsResponse} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: com.google.android.gms.drive.internal.OnMetadataResponse} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: com.google.android.gms.drive.internal.OnDriveIdResponse} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: com.google.android.gms.drive.internal.OnListEntriesResponse} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v27, resolved type: com.google.android.gms.drive.internal.OnDownloadProgressResponse} */
        /* JADX WARNING: type inference failed for: r0v0 */
        /* JADX WARNING: type inference failed for: r0v35 */
        /* JADX WARNING: type inference failed for: r0v36 */
        /* JADX WARNING: type inference failed for: r0v37 */
        /* JADX WARNING: type inference failed for: r0v38 */
        /* JADX WARNING: type inference failed for: r0v39 */
        /* JADX WARNING: type inference failed for: r0v40 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r4, android.os.Parcel r5, android.os.Parcel r6, int r7) throws android.os.RemoteException {
            /*
                r3 = this;
                r0 = 0
                r1 = 1
                switch(r4) {
                    case 1: goto L_0x0011;
                    case 2: goto L_0x002c;
                    case 3: goto L_0x0047;
                    case 4: goto L_0x0062;
                    case 5: goto L_0x007d;
                    case 6: goto L_0x0099;
                    case 7: goto L_0x00b3;
                    case 1598968902: goto L_0x000a;
                    default: goto L_0x0005;
                }
            L_0x0005:
                boolean r0 = super.onTransact(r4, r5, r6, r7)
            L_0x0009:
                return r0
            L_0x000a:
                java.lang.String r0 = "com.google.android.gms.drive.internal.IDriveServiceCallbacks"
                r6.writeString(r0)
                r0 = r1
                goto L_0x0009
            L_0x0011:
                java.lang.String r2 = "com.google.android.gms.drive.internal.IDriveServiceCallbacks"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0024
                android.os.Parcelable$Creator<com.google.android.gms.drive.internal.OnDownloadProgressResponse> r0 = com.google.android.gms.drive.internal.OnDownloadProgressResponse.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.drive.internal.OnDownloadProgressResponse r0 = (com.google.android.gms.drive.internal.OnDownloadProgressResponse) r0
            L_0x0024:
                r3.a((com.google.android.gms.drive.internal.OnDownloadProgressResponse) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x002c:
                java.lang.String r2 = "com.google.android.gms.drive.internal.IDriveServiceCallbacks"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x003f
                android.os.Parcelable$Creator<com.google.android.gms.drive.internal.OnListEntriesResponse> r0 = com.google.android.gms.drive.internal.OnListEntriesResponse.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.drive.internal.OnListEntriesResponse r0 = (com.google.android.gms.drive.internal.OnListEntriesResponse) r0
            L_0x003f:
                r3.a((com.google.android.gms.drive.internal.OnListEntriesResponse) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0047:
                java.lang.String r2 = "com.google.android.gms.drive.internal.IDriveServiceCallbacks"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x005a
                android.os.Parcelable$Creator<com.google.android.gms.drive.internal.OnDriveIdResponse> r0 = com.google.android.gms.drive.internal.OnDriveIdResponse.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.drive.internal.OnDriveIdResponse r0 = (com.google.android.gms.drive.internal.OnDriveIdResponse) r0
            L_0x005a:
                r3.a((com.google.android.gms.drive.internal.OnDriveIdResponse) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0062:
                java.lang.String r2 = "com.google.android.gms.drive.internal.IDriveServiceCallbacks"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0075
                android.os.Parcelable$Creator<com.google.android.gms.drive.internal.OnMetadataResponse> r0 = com.google.android.gms.drive.internal.OnMetadataResponse.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.drive.internal.OnMetadataResponse r0 = (com.google.android.gms.drive.internal.OnMetadataResponse) r0
            L_0x0075:
                r3.a((com.google.android.gms.drive.internal.OnMetadataResponse) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x007d:
                java.lang.String r2 = "com.google.android.gms.drive.internal.IDriveServiceCallbacks"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0090
                android.os.Parcelable$Creator<com.google.android.gms.drive.internal.OnContentsResponse> r0 = com.google.android.gms.drive.internal.OnContentsResponse.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.drive.internal.OnContentsResponse r0 = (com.google.android.gms.drive.internal.OnContentsResponse) r0
            L_0x0090:
                r3.a((com.google.android.gms.drive.internal.OnContentsResponse) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0099:
                java.lang.String r2 = "com.google.android.gms.drive.internal.IDriveServiceCallbacks"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x00aa
                com.google.android.gms.common.api.StatusCreator r0 = com.google.android.gms.common.api.Status.CREATOR
                com.google.android.gms.common.api.Status r0 = r0.createFromParcel((android.os.Parcel) r5)
            L_0x00aa:
                r3.m(r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x00b3:
                java.lang.String r0 = "com.google.android.gms.drive.internal.IDriveServiceCallbacks"
                r5.enforceInterface(r0)
                r3.onSuccess()
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.drive.internal.p.a.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void a(OnContentsResponse onContentsResponse) throws RemoteException;

    void a(OnDownloadProgressResponse onDownloadProgressResponse) throws RemoteException;

    void a(OnDriveIdResponse onDriveIdResponse) throws RemoteException;

    void a(OnListEntriesResponse onListEntriesResponse) throws RemoteException;

    void a(OnMetadataResponse onMetadataResponse) throws RemoteException;

    void m(Status status) throws RemoteException;

    void onSuccess() throws RemoteException;
}
