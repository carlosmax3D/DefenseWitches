package com.google.android.gms.drive.internal;

import android.content.IntentSender;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface o extends IInterface {

    public static abstract class a extends Binder implements o {

        /* renamed from: com.google.android.gms.drive.internal.o$a$a  reason: collision with other inner class name */
        private static class C0005a implements o {
            private IBinder dU;

            C0005a(IBinder iBinder) {
                this.dU = iBinder;
            }

            public IntentSender a(CreateFileIntentSenderRequest createFileIntentSenderRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (createFileIntentSenderRequest != null) {
                        obtain.writeInt(1);
                        createFileIntentSenderRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (IntentSender) IntentSender.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IntentSender a(OpenFileIntentSenderRequest openFileIntentSenderRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (openFileIntentSenderRequest != null) {
                        obtain.writeInt(1);
                        openFileIntentSenderRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (IntentSender) IntentSender.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(CloseContentsRequest closeContentsRequest, p pVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (closeContentsRequest != null) {
                        obtain.writeInt(1);
                        closeContentsRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(pVar != null ? pVar.asBinder() : null);
                    this.dU.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(CreateContentsRequest createContentsRequest, p pVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (createContentsRequest != null) {
                        obtain.writeInt(1);
                        createContentsRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(pVar != null ? pVar.asBinder() : null);
                    this.dU.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(CreateFileRequest createFileRequest, p pVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (createFileRequest != null) {
                        obtain.writeInt(1);
                        createFileRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(pVar != null ? pVar.asBinder() : null);
                    this.dU.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(CreateFolderRequest createFolderRequest, p pVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (createFolderRequest != null) {
                        obtain.writeInt(1);
                        createFolderRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(pVar != null ? pVar.asBinder() : null);
                    this.dU.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(GetMetadataRequest getMetadataRequest, p pVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (getMetadataRequest != null) {
                        obtain.writeInt(1);
                        getMetadataRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(pVar != null ? pVar.asBinder() : null);
                    this.dU.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(OpenContentsRequest openContentsRequest, p pVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (openContentsRequest != null) {
                        obtain.writeInt(1);
                        openContentsRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(pVar != null ? pVar.asBinder() : null);
                    this.dU.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(QueryRequest queryRequest, p pVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (queryRequest != null) {
                        obtain.writeInt(1);
                        queryRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(pVar != null ? pVar.asBinder() : null);
                    this.dU.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(UpdateMetadataRequest updateMetadataRequest, p pVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (updateMetadataRequest != null) {
                        obtain.writeInt(1);
                        updateMetadataRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(pVar != null ? pVar.asBinder() : null);
                    this.dU.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(p pVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    obtain.writeStrongBinder(pVar != null ? pVar.asBinder() : null);
                    this.dU.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.dU;
            }
        }

        public static o C(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.drive.internal.IDriveService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof o)) ? new C0005a(iBinder) : (o) queryLocalInterface;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: com.google.android.gms.drive.internal.CreateFileIntentSenderRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: com.google.android.gms.drive.internal.OpenFileIntentSenderRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: com.google.android.gms.drive.internal.CloseContentsRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: com.google.android.gms.drive.internal.OpenContentsRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v27, resolved type: com.google.android.gms.drive.internal.CreateFolderRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v32, resolved type: com.google.android.gms.drive.internal.CreateFileRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v37, resolved type: com.google.android.gms.drive.internal.CreateContentsRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v42, resolved type: com.google.android.gms.drive.internal.UpdateMetadataRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v47, resolved type: com.google.android.gms.drive.internal.QueryRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v52, resolved type: com.google.android.gms.drive.internal.GetMetadataRequest} */
        /* JADX WARNING: type inference failed for: r0v0 */
        /* JADX WARNING: type inference failed for: r0v60 */
        /* JADX WARNING: type inference failed for: r0v61 */
        /* JADX WARNING: type inference failed for: r0v62 */
        /* JADX WARNING: type inference failed for: r0v63 */
        /* JADX WARNING: type inference failed for: r0v64 */
        /* JADX WARNING: type inference failed for: r0v65 */
        /* JADX WARNING: type inference failed for: r0v66 */
        /* JADX WARNING: type inference failed for: r0v67 */
        /* JADX WARNING: type inference failed for: r0v68 */
        /* JADX WARNING: type inference failed for: r0v69 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                r3 = 0
                r0 = 0
                r1 = 1
                switch(r5) {
                    case 1: goto L_0x0012;
                    case 2: goto L_0x0035;
                    case 3: goto L_0x0058;
                    case 4: goto L_0x007b;
                    case 5: goto L_0x009f;
                    case 6: goto L_0x00c3;
                    case 7: goto L_0x00e7;
                    case 8: goto L_0x010b;
                    case 9: goto L_0x012f;
                    case 10: goto L_0x0145;
                    case 11: goto L_0x016e;
                    case 1598968902: goto L_0x000b;
                    default: goto L_0x0006;
                }
            L_0x0006:
                boolean r0 = super.onTransact(r5, r6, r7, r8)
            L_0x000a:
                return r0
            L_0x000b:
                java.lang.String r0 = "com.google.android.gms.drive.internal.IDriveService"
                r7.writeString(r0)
                r0 = r1
                goto L_0x000a
            L_0x0012:
                java.lang.String r2 = "com.google.android.gms.drive.internal.IDriveService"
                r6.enforceInterface(r2)
                int r2 = r6.readInt()
                if (r2 == 0) goto L_0x0025
                android.os.Parcelable$Creator<com.google.android.gms.drive.internal.GetMetadataRequest> r0 = com.google.android.gms.drive.internal.GetMetadataRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r6)
                com.google.android.gms.drive.internal.GetMetadataRequest r0 = (com.google.android.gms.drive.internal.GetMetadataRequest) r0
            L_0x0025:
                android.os.IBinder r2 = r6.readStrongBinder()
                com.google.android.gms.drive.internal.p r2 = com.google.android.gms.drive.internal.p.a.D(r2)
                r4.a((com.google.android.gms.drive.internal.GetMetadataRequest) r0, (com.google.android.gms.drive.internal.p) r2)
                r7.writeNoException()
                r0 = r1
                goto L_0x000a
            L_0x0035:
                java.lang.String r2 = "com.google.android.gms.drive.internal.IDriveService"
                r6.enforceInterface(r2)
                int r2 = r6.readInt()
                if (r2 == 0) goto L_0x0048
                android.os.Parcelable$Creator<com.google.android.gms.drive.internal.QueryRequest> r0 = com.google.android.gms.drive.internal.QueryRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r6)
                com.google.android.gms.drive.internal.QueryRequest r0 = (com.google.android.gms.drive.internal.QueryRequest) r0
            L_0x0048:
                android.os.IBinder r2 = r6.readStrongBinder()
                com.google.android.gms.drive.internal.p r2 = com.google.android.gms.drive.internal.p.a.D(r2)
                r4.a((com.google.android.gms.drive.internal.QueryRequest) r0, (com.google.android.gms.drive.internal.p) r2)
                r7.writeNoException()
                r0 = r1
                goto L_0x000a
            L_0x0058:
                java.lang.String r2 = "com.google.android.gms.drive.internal.IDriveService"
                r6.enforceInterface(r2)
                int r2 = r6.readInt()
                if (r2 == 0) goto L_0x006b
                android.os.Parcelable$Creator<com.google.android.gms.drive.internal.UpdateMetadataRequest> r0 = com.google.android.gms.drive.internal.UpdateMetadataRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r6)
                com.google.android.gms.drive.internal.UpdateMetadataRequest r0 = (com.google.android.gms.drive.internal.UpdateMetadataRequest) r0
            L_0x006b:
                android.os.IBinder r2 = r6.readStrongBinder()
                com.google.android.gms.drive.internal.p r2 = com.google.android.gms.drive.internal.p.a.D(r2)
                r4.a((com.google.android.gms.drive.internal.UpdateMetadataRequest) r0, (com.google.android.gms.drive.internal.p) r2)
                r7.writeNoException()
                r0 = r1
                goto L_0x000a
            L_0x007b:
                java.lang.String r2 = "com.google.android.gms.drive.internal.IDriveService"
                r6.enforceInterface(r2)
                int r2 = r6.readInt()
                if (r2 == 0) goto L_0x008e
                android.os.Parcelable$Creator<com.google.android.gms.drive.internal.CreateContentsRequest> r0 = com.google.android.gms.drive.internal.CreateContentsRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r6)
                com.google.android.gms.drive.internal.CreateContentsRequest r0 = (com.google.android.gms.drive.internal.CreateContentsRequest) r0
            L_0x008e:
                android.os.IBinder r2 = r6.readStrongBinder()
                com.google.android.gms.drive.internal.p r2 = com.google.android.gms.drive.internal.p.a.D(r2)
                r4.a((com.google.android.gms.drive.internal.CreateContentsRequest) r0, (com.google.android.gms.drive.internal.p) r2)
                r7.writeNoException()
                r0 = r1
                goto L_0x000a
            L_0x009f:
                java.lang.String r2 = "com.google.android.gms.drive.internal.IDriveService"
                r6.enforceInterface(r2)
                int r2 = r6.readInt()
                if (r2 == 0) goto L_0x00b2
                android.os.Parcelable$Creator<com.google.android.gms.drive.internal.CreateFileRequest> r0 = com.google.android.gms.drive.internal.CreateFileRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r6)
                com.google.android.gms.drive.internal.CreateFileRequest r0 = (com.google.android.gms.drive.internal.CreateFileRequest) r0
            L_0x00b2:
                android.os.IBinder r2 = r6.readStrongBinder()
                com.google.android.gms.drive.internal.p r2 = com.google.android.gms.drive.internal.p.a.D(r2)
                r4.a((com.google.android.gms.drive.internal.CreateFileRequest) r0, (com.google.android.gms.drive.internal.p) r2)
                r7.writeNoException()
                r0 = r1
                goto L_0x000a
            L_0x00c3:
                java.lang.String r2 = "com.google.android.gms.drive.internal.IDriveService"
                r6.enforceInterface(r2)
                int r2 = r6.readInt()
                if (r2 == 0) goto L_0x00d6
                android.os.Parcelable$Creator<com.google.android.gms.drive.internal.CreateFolderRequest> r0 = com.google.android.gms.drive.internal.CreateFolderRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r6)
                com.google.android.gms.drive.internal.CreateFolderRequest r0 = (com.google.android.gms.drive.internal.CreateFolderRequest) r0
            L_0x00d6:
                android.os.IBinder r2 = r6.readStrongBinder()
                com.google.android.gms.drive.internal.p r2 = com.google.android.gms.drive.internal.p.a.D(r2)
                r4.a((com.google.android.gms.drive.internal.CreateFolderRequest) r0, (com.google.android.gms.drive.internal.p) r2)
                r7.writeNoException()
                r0 = r1
                goto L_0x000a
            L_0x00e7:
                java.lang.String r2 = "com.google.android.gms.drive.internal.IDriveService"
                r6.enforceInterface(r2)
                int r2 = r6.readInt()
                if (r2 == 0) goto L_0x00fa
                android.os.Parcelable$Creator<com.google.android.gms.drive.internal.OpenContentsRequest> r0 = com.google.android.gms.drive.internal.OpenContentsRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r6)
                com.google.android.gms.drive.internal.OpenContentsRequest r0 = (com.google.android.gms.drive.internal.OpenContentsRequest) r0
            L_0x00fa:
                android.os.IBinder r2 = r6.readStrongBinder()
                com.google.android.gms.drive.internal.p r2 = com.google.android.gms.drive.internal.p.a.D(r2)
                r4.a((com.google.android.gms.drive.internal.OpenContentsRequest) r0, (com.google.android.gms.drive.internal.p) r2)
                r7.writeNoException()
                r0 = r1
                goto L_0x000a
            L_0x010b:
                java.lang.String r2 = "com.google.android.gms.drive.internal.IDriveService"
                r6.enforceInterface(r2)
                int r2 = r6.readInt()
                if (r2 == 0) goto L_0x011e
                android.os.Parcelable$Creator<com.google.android.gms.drive.internal.CloseContentsRequest> r0 = com.google.android.gms.drive.internal.CloseContentsRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r6)
                com.google.android.gms.drive.internal.CloseContentsRequest r0 = (com.google.android.gms.drive.internal.CloseContentsRequest) r0
            L_0x011e:
                android.os.IBinder r2 = r6.readStrongBinder()
                com.google.android.gms.drive.internal.p r2 = com.google.android.gms.drive.internal.p.a.D(r2)
                r4.a((com.google.android.gms.drive.internal.CloseContentsRequest) r0, (com.google.android.gms.drive.internal.p) r2)
                r7.writeNoException()
                r0 = r1
                goto L_0x000a
            L_0x012f:
                java.lang.String r0 = "com.google.android.gms.drive.internal.IDriveService"
                r6.enforceInterface(r0)
                android.os.IBinder r0 = r6.readStrongBinder()
                com.google.android.gms.drive.internal.p r0 = com.google.android.gms.drive.internal.p.a.D(r0)
                r4.a((com.google.android.gms.drive.internal.p) r0)
                r7.writeNoException()
                r0 = r1
                goto L_0x000a
            L_0x0145:
                java.lang.String r2 = "com.google.android.gms.drive.internal.IDriveService"
                r6.enforceInterface(r2)
                int r2 = r6.readInt()
                if (r2 == 0) goto L_0x0158
                android.os.Parcelable$Creator<com.google.android.gms.drive.internal.OpenFileIntentSenderRequest> r0 = com.google.android.gms.drive.internal.OpenFileIntentSenderRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r6)
                com.google.android.gms.drive.internal.OpenFileIntentSenderRequest r0 = (com.google.android.gms.drive.internal.OpenFileIntentSenderRequest) r0
            L_0x0158:
                android.content.IntentSender r0 = r4.a((com.google.android.gms.drive.internal.OpenFileIntentSenderRequest) r0)
                r7.writeNoException()
                if (r0 == 0) goto L_0x016a
                r7.writeInt(r1)
                r0.writeToParcel(r7, r1)
            L_0x0167:
                r0 = r1
                goto L_0x000a
            L_0x016a:
                r7.writeInt(r3)
                goto L_0x0167
            L_0x016e:
                java.lang.String r2 = "com.google.android.gms.drive.internal.IDriveService"
                r6.enforceInterface(r2)
                int r2 = r6.readInt()
                if (r2 == 0) goto L_0x0181
                android.os.Parcelable$Creator<com.google.android.gms.drive.internal.CreateFileIntentSenderRequest> r0 = com.google.android.gms.drive.internal.CreateFileIntentSenderRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r6)
                com.google.android.gms.drive.internal.CreateFileIntentSenderRequest r0 = (com.google.android.gms.drive.internal.CreateFileIntentSenderRequest) r0
            L_0x0181:
                android.content.IntentSender r0 = r4.a((com.google.android.gms.drive.internal.CreateFileIntentSenderRequest) r0)
                r7.writeNoException()
                if (r0 == 0) goto L_0x0193
                r7.writeInt(r1)
                r0.writeToParcel(r7, r1)
            L_0x0190:
                r0 = r1
                goto L_0x000a
            L_0x0193:
                r7.writeInt(r3)
                goto L_0x0190
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.drive.internal.o.a.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    IntentSender a(CreateFileIntentSenderRequest createFileIntentSenderRequest) throws RemoteException;

    IntentSender a(OpenFileIntentSenderRequest openFileIntentSenderRequest) throws RemoteException;

    void a(CloseContentsRequest closeContentsRequest, p pVar) throws RemoteException;

    void a(CreateContentsRequest createContentsRequest, p pVar) throws RemoteException;

    void a(CreateFileRequest createFileRequest, p pVar) throws RemoteException;

    void a(CreateFolderRequest createFolderRequest, p pVar) throws RemoteException;

    void a(GetMetadataRequest getMetadataRequest, p pVar) throws RemoteException;

    void a(OpenContentsRequest openContentsRequest, p pVar) throws RemoteException;

    void a(QueryRequest queryRequest, p pVar) throws RemoteException;

    void a(UpdateMetadataRequest updateMetadataRequest, p pVar) throws RemoteException;

    void a(p pVar) throws RemoteException;
}
