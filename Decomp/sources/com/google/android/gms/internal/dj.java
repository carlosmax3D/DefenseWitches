package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.cast.ApplicationMetadata;

public interface dj extends IInterface {

    public static abstract class a extends Binder implements dj {
        public a() {
            attachInterface(this, "com.google.android.gms.cast.internal.ICastDeviceControllerListener");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            boolean z = false;
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    t(parcel.readInt());
                    return true;
                case 2:
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    ApplicationMetadata createFromParcel = parcel.readInt() != 0 ? ApplicationMetadata.CREATOR.createFromParcel(parcel) : null;
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    a(createFromParcel, readString, readString2, z);
                    return true;
                case 3:
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    u(parcel.readInt());
                    return true;
                case 4:
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    String readString3 = parcel.readString();
                    double readDouble = parcel.readDouble();
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    b(readString3, readDouble, z);
                    return true;
                case 5:
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    a(parcel.readString(), parcel.readString());
                    return true;
                case 6:
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    b(parcel.readString(), parcel.createByteArray());
                    return true;
                case 7:
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    w(parcel.readInt());
                    return true;
                case 8:
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    v(parcel.readInt());
                    return true;
                case 9:
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    onApplicationDisconnected(parcel.readInt());
                    return true;
                case 10:
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    a(parcel.readString(), parcel.readLong(), parcel.readInt());
                    return true;
                case 11:
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    a(parcel.readString(), parcel.readLong());
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void a(ApplicationMetadata applicationMetadata, String str, String str2, boolean z) throws RemoteException;

    void a(String str, long j) throws RemoteException;

    void a(String str, long j, int i) throws RemoteException;

    void a(String str, String str2) throws RemoteException;

    void b(String str, double d, boolean z) throws RemoteException;

    void b(String str, byte[] bArr) throws RemoteException;

    void onApplicationDisconnected(int i) throws RemoteException;

    void t(int i) throws RemoteException;

    void u(int i) throws RemoteException;

    void v(int i) throws RemoteException;

    void w(int i) throws RemoteException;
}
