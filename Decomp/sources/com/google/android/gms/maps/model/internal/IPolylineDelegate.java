package com.google.android.gms.maps.model.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public interface IPolylineDelegate extends IInterface {

    public static abstract class a extends Binder implements IPolylineDelegate {

        /* renamed from: com.google.android.gms.maps.model.internal.IPolylineDelegate$a$a  reason: collision with other inner class name */
        private static class C0068a implements IPolylineDelegate {
            private IBinder dU;

            C0068a(IBinder iBinder) {
                this.dU = iBinder;
            }

            public IBinder asBinder() {
                return this.dU;
            }

            public boolean equalsRemote(IPolylineDelegate iPolylineDelegate) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    obtain.writeStrongBinder(iPolylineDelegate != null ? iPolylineDelegate.asBinder() : null);
                    this.dU.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getColor() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.dU.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getId() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.dU.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public List<LatLng> getPoints() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.dU.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(LatLng.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getWidth() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.dU.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getZIndex() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.dU.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int hashCodeRemote() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.dU.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isGeodesic() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.dU.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isVisible() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.dU.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void remove() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.dU.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setColor(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    obtain.writeInt(i);
                    this.dU.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setGeodesic(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.dU.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setPoints(List<LatLng> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    obtain.writeTypedList(list);
                    this.dU.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setVisible(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.dU.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setWidth(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    obtain.writeFloat(f);
                    this.dU.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setZIndex(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    obtain.writeFloat(f);
                    this.dU.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IPolylineDelegate ao(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IPolylineDelegate)) ? new C0068a(iBinder) : (IPolylineDelegate) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            boolean z = false;
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    remove();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    String id = getId();
                    parcel2.writeNoException();
                    parcel2.writeString(id);
                    return true;
                case 3:
                    parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    setPoints(parcel.createTypedArrayList(LatLng.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    List<LatLng> points = getPoints();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(points);
                    return true;
                case 5:
                    parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    setWidth(parcel.readFloat());
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    float width = getWidth();
                    parcel2.writeNoException();
                    parcel2.writeFloat(width);
                    return true;
                case 7:
                    parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    setColor(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    int color = getColor();
                    parcel2.writeNoException();
                    parcel2.writeInt(color);
                    return true;
                case 9:
                    parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    setZIndex(parcel.readFloat());
                    parcel2.writeNoException();
                    return true;
                case 10:
                    parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    float zIndex = getZIndex();
                    parcel2.writeNoException();
                    parcel2.writeFloat(zIndex);
                    return true;
                case 11:
                    parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    setVisible(z);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    boolean isVisible = isVisible();
                    parcel2.writeNoException();
                    if (isVisible) {
                        z = true;
                    }
                    parcel2.writeInt(z ? 1 : 0);
                    return true;
                case 13:
                    parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    setGeodesic(z);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    boolean isGeodesic = isGeodesic();
                    parcel2.writeNoException();
                    if (isGeodesic) {
                        z = true;
                    }
                    parcel2.writeInt(z ? 1 : 0);
                    return true;
                case 15:
                    parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    boolean equalsRemote = equalsRemote(ao(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (equalsRemote) {
                        z = true;
                    }
                    parcel2.writeInt(z ? 1 : 0);
                    return true;
                case 16:
                    parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    int hashCodeRemote = hashCodeRemote();
                    parcel2.writeNoException();
                    parcel2.writeInt(hashCodeRemote);
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    boolean equalsRemote(IPolylineDelegate iPolylineDelegate) throws RemoteException;

    int getColor() throws RemoteException;

    String getId() throws RemoteException;

    List<LatLng> getPoints() throws RemoteException;

    float getWidth() throws RemoteException;

    float getZIndex() throws RemoteException;

    int hashCodeRemote() throws RemoteException;

    boolean isGeodesic() throws RemoteException;

    boolean isVisible() throws RemoteException;

    void remove() throws RemoteException;

    void setColor(int i) throws RemoteException;

    void setGeodesic(boolean z) throws RemoteException;

    void setPoints(List<LatLng> list) throws RemoteException;

    void setVisible(boolean z) throws RemoteException;

    void setWidth(float f) throws RemoteException;

    void setZIndex(float f) throws RemoteException;
}
