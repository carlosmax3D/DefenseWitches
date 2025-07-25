package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.dd;

public interface de extends IInterface {

    public static abstract class a extends Binder implements de {

        /* renamed from: com.google.android.gms.internal.de$a$a  reason: collision with other inner class name */
        private static class C0021a implements de {
            private IBinder dU;

            C0021a(IBinder iBinder) {
                this.dU = iBinder;
            }

            public void a(dd ddVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    obtain.writeStrongBinder(ddVar != null ? ddVar.asBinder() : null);
                    this.dU.transact(5005, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(dd ddVar, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    obtain.writeStrongBinder(ddVar != null ? ddVar.asBinder() : null);
                    obtain.writeInt(i);
                    this.dU.transact(5004, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(dd ddVar, int i, String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    obtain.writeStrongBinder(ddVar != null ? ddVar.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.dU.transact(5006, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(dd ddVar, int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    obtain.writeStrongBinder(ddVar != null ? ddVar.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.dU.transact(5003, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.dU;
            }

            public void b(dd ddVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    obtain.writeStrongBinder(ddVar != null ? ddVar.asBinder() : null);
                    this.dU.transact(5008, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void b(dd ddVar, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    obtain.writeStrongBinder(ddVar != null ? ddVar.asBinder() : null);
                    obtain.writeInt(i);
                    this.dU.transact(5007, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void c(dd ddVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    obtain.writeStrongBinder(ddVar != null ? ddVar.asBinder() : null);
                    this.dU.transact(5009, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getMaxNumKeys() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    this.dU.transact(5002, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getMaxStateSize() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    this.dU.transact(5001, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static de t(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.appstate.internal.IAppStateService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof de)) ? new C0021a(iBinder) : (de) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 5001:
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    int maxStateSize = getMaxStateSize();
                    parcel2.writeNoException();
                    parcel2.writeInt(maxStateSize);
                    return true;
                case 5002:
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    int maxNumKeys = getMaxNumKeys();
                    parcel2.writeNoException();
                    parcel2.writeInt(maxNumKeys);
                    return true;
                case 5003:
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    a(dd.a.s(parcel.readStrongBinder()), parcel.readInt(), parcel.createByteArray());
                    parcel2.writeNoException();
                    return true;
                case 5004:
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    a(dd.a.s(parcel.readStrongBinder()), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 5005:
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    a(dd.a.s(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 5006:
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    a(dd.a.s(parcel.readStrongBinder()), parcel.readInt(), parcel.readString(), parcel.createByteArray());
                    parcel2.writeNoException();
                    return true;
                case 5007:
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    b(dd.a.s(parcel.readStrongBinder()), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 5008:
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    b(dd.a.s(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 5009:
                    parcel.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    c(dd.a.s(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.appstate.internal.IAppStateService");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void a(dd ddVar) throws RemoteException;

    void a(dd ddVar, int i) throws RemoteException;

    void a(dd ddVar, int i, String str, byte[] bArr) throws RemoteException;

    void a(dd ddVar, int i, byte[] bArr) throws RemoteException;

    void b(dd ddVar) throws RemoteException;

    void b(dd ddVar, int i) throws RemoteException;

    void c(dd ddVar) throws RemoteException;

    int getMaxNumKeys() throws RemoteException;

    int getMaxStateSize() throws RemoteException;
}
