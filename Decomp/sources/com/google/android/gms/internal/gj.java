package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

public interface gj extends IInterface {

    public static abstract class a extends Binder implements gj {

        /* renamed from: com.google.android.gms.internal.gj$a$a  reason: collision with other inner class name */
        private static class C0030a implements gj {
            private IBinder dU;

            C0030a(IBinder iBinder) {
                this.dU = iBinder;
            }

            public void I(Status status) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.ICopresenceCallbacks");
                    if (status != null) {
                        obtain.writeInt(1);
                        status.writeToParcel(obtain, 0);
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

            public void a(int i, gh ghVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.ICopresenceCallbacks");
                    obtain.writeInt(i);
                    if (ghVar != null) {
                        obtain.writeInt(1);
                        ghVar.writeToParcel(obtain, 0);
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

            public void a(int i, boolean z) throws RemoteException {
                int i2 = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.ICopresenceCallbacks");
                    obtain.writeInt(i);
                    if (z) {
                        i2 = 1;
                    }
                    obtain.writeInt(i2);
                    this.dU.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.dU;
            }

            public void b(int i, gh ghVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.ICopresenceCallbacks");
                    obtain.writeInt(i);
                    if (ghVar != null) {
                        obtain.writeInt(1);
                        ghVar.writeToParcel(obtain, 0);
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
        }

        public static gj J(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.internal.ICopresenceCallbacks");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof gj)) ? new C0030a(iBinder) : (gj) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: com.google.android.gms.internal.gh} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: com.google.android.gms.internal.gh} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: com.google.android.gms.common.api.Status} */
        /* JADX WARNING: type inference failed for: r0v0 */
        /* JADX WARNING: type inference failed for: r0v16 */
        /* JADX WARNING: type inference failed for: r0v17 */
        /* JADX WARNING: type inference failed for: r0v18 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                r0 = 0
                r1 = 1
                switch(r5) {
                    case 3: goto L_0x0010;
                    case 4: goto L_0x0028;
                    case 5: goto L_0x0044;
                    case 6: goto L_0x0060;
                    case 1598968902: goto L_0x000a;
                    default: goto L_0x0005;
                }
            L_0x0005:
                boolean r1 = super.onTransact(r5, r6, r7, r8)
            L_0x0009:
                return r1
            L_0x000a:
                java.lang.String r0 = "com.google.android.gms.location.internal.ICopresenceCallbacks"
                r7.writeString(r0)
                goto L_0x0009
            L_0x0010:
                java.lang.String r2 = "com.google.android.gms.location.internal.ICopresenceCallbacks"
                r6.enforceInterface(r2)
                int r2 = r6.readInt()
                if (r2 == 0) goto L_0x0021
                com.google.android.gms.common.api.StatusCreator r0 = com.google.android.gms.common.api.Status.CREATOR
                com.google.android.gms.common.api.Status r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x0021:
                r4.I(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x0028:
                java.lang.String r2 = "com.google.android.gms.location.internal.ICopresenceCallbacks"
                r6.enforceInterface(r2)
                int r2 = r6.readInt()
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x003d
                com.google.android.gms.internal.gi r0 = com.google.android.gms.internal.gh.CREATOR
                com.google.android.gms.internal.gh r0 = r0.createFromParcel(r6)
            L_0x003d:
                r4.a((int) r2, (com.google.android.gms.internal.gh) r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x0044:
                java.lang.String r2 = "com.google.android.gms.location.internal.ICopresenceCallbacks"
                r6.enforceInterface(r2)
                int r2 = r6.readInt()
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0059
                com.google.android.gms.internal.gi r0 = com.google.android.gms.internal.gh.CREATOR
                com.google.android.gms.internal.gh r0 = r0.createFromParcel(r6)
            L_0x0059:
                r4.b(r2, r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x0060:
                java.lang.String r0 = "com.google.android.gms.location.internal.ICopresenceCallbacks"
                r6.enforceInterface(r0)
                int r2 = r6.readInt()
                int r0 = r6.readInt()
                if (r0 == 0) goto L_0x0077
                r0 = r1
            L_0x0070:
                r4.a((int) r2, (boolean) r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x0077:
                r0 = 0
                goto L_0x0070
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gj.a.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void I(Status status) throws RemoteException;

    void a(int i, gh ghVar) throws RemoteException;

    void a(int i, boolean z) throws RemoteException;

    void b(int i, gh ghVar) throws RemoteException;
}
