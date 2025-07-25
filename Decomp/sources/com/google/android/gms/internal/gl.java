package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.hd;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.c;
import com.google.android.gms.location.d;
import com.google.android.gms.location.f;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.List;

public interface gl extends IInterface {

    public static abstract class a extends Binder implements gl {

        /* renamed from: com.google.android.gms.internal.gl$a$a  reason: collision with other inner class name */
        private static class C0032a implements gl {
            private IBinder dU;

            C0032a(IBinder iBinder) {
                this.dU = iBinder;
            }

            public void a(long j, boolean z, PendingIntent pendingIntent) throws RemoteException {
                int i = 1;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeLong(j);
                    if (!z) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
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

            public void a(PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(PendingIntent pendingIntent, gk gkVar, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(gkVar != null ? gkVar.asBinder() : null);
                    obtain.writeString(str);
                    this.dU.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(Location location, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (location != null) {
                        obtain.writeInt(1);
                        location.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.dU.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(gk gkVar, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeStrongBinder(gkVar != null ? gkVar.asBinder() : null);
                    obtain.writeString(str);
                    this.dU.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(gt gtVar, hi hiVar, gz gzVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (gtVar != null) {
                        obtain.writeInt(1);
                        gtVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (hiVar != null) {
                        obtain.writeInt(1);
                        hiVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(gzVar != null ? gzVar.asBinder() : null);
                    this.dU.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(gv gvVar, hi hiVar, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (gvVar != null) {
                        obtain.writeInt(1);
                        gvVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (hiVar != null) {
                        obtain.writeInt(1);
                        hiVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(hi hiVar, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (hiVar != null) {
                        obtain.writeInt(1);
                        hiVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(LocationRequest locationRequest, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationRequest != null) {
                        obtain.writeInt(1);
                        locationRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(LocationRequest locationRequest, c cVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationRequest != null) {
                        obtain.writeInt(1);
                        locationRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(cVar != null ? cVar.asBinder() : null);
                    this.dU.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(LocationRequest locationRequest, c cVar, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationRequest != null) {
                        obtain.writeInt(1);
                        locationRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(cVar != null ? cVar.asBinder() : null);
                    obtain.writeString(str);
                    this.dU.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(c cVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeStrongBinder(cVar != null ? cVar.asBinder() : null);
                    this.dU.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(LatLng latLng, gt gtVar, hi hiVar, gz gzVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (latLng != null) {
                        obtain.writeInt(1);
                        latLng.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (gtVar != null) {
                        obtain.writeInt(1);
                        gtVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (hiVar != null) {
                        obtain.writeInt(1);
                        hiVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(gzVar != null ? gzVar.asBinder() : null);
                    this.dU.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(LatLngBounds latLngBounds, int i, gt gtVar, hi hiVar, gz gzVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (latLngBounds != null) {
                        obtain.writeInt(1);
                        latLngBounds.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    if (gtVar != null) {
                        obtain.writeInt(1);
                        gtVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (hiVar != null) {
                        obtain.writeInt(1);
                        hiVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(gzVar != null ? gzVar.asBinder() : null);
                    this.dU.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(String str, gh ghVar, gj gjVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    if (ghVar != null) {
                        obtain.writeInt(1);
                        ghVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(gjVar != null ? gjVar.asBinder() : null);
                    this.dU.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(String str, gj gjVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(gjVar != null ? gjVar.asBinder() : null);
                    this.dU.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(String str, hi hiVar, gz gzVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    if (hiVar != null) {
                        obtain.writeInt(1);
                        hiVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(gzVar != null ? gzVar.asBinder() : null);
                    this.dU.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(String str, com.google.android.gms.location.a aVar, gj gjVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    if (aVar != null) {
                        obtain.writeInt(1);
                        aVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(gjVar != null ? gjVar.asBinder() : null);
                    this.dU.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(String str, f fVar, gj gjVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    if (fVar != null) {
                        obtain.writeInt(1);
                        fVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(gjVar != null ? gjVar.asBinder() : null);
                    this.dU.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(String str, String str2, hi hiVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (hiVar != null) {
                        obtain.writeInt(1);
                        hiVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(25, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void a(String str, boolean z, gj gjVar) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(gjVar != null ? gjVar.asBinder() : null);
                    this.dU.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(List<hd.a> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeTypedList(list);
                    this.dU.transact(24, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void a(List<go> list, PendingIntent pendingIntent, gk gkVar, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeTypedList(list);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(gkVar != null ? gkVar.asBinder() : null);
                    obtain.writeString(str);
                    this.dU.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(String[] strArr, gk gkVar, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongBinder(gkVar != null ? gkVar.asBinder() : null);
                    obtain.writeString(str);
                    this.dU.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Location an(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    this.dU.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public d ao(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    this.dU.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? d.CREATOR.af(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.dU;
            }

            public void b(String str, gj gjVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    obtain.writeStrongBinder(gjVar != null ? gjVar.asBinder() : null);
                    this.dU.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void b(String str, f fVar, gj gjVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    if (fVar != null) {
                        obtain.writeInt(1);
                        fVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(gjVar != null ? gjVar.asBinder() : null);
                    this.dU.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Location dH() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    this.dU.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void removeActivityUpdates(PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
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

            public void setMockLocation(Location location) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (location != null) {
                        obtain.writeInt(1);
                        location.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setMockMode(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.dU.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static gl L(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof gl)) ? new C0032a(iBinder) : (gl) queryLocalInterface;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: com.google.android.gms.location.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: com.google.android.gms.location.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: com.google.android.gms.internal.gh} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: com.google.android.gms.location.a} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: android.location.Location} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v26, resolved type: com.google.android.gms.internal.hi} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v35, resolved type: com.google.android.gms.location.LocationRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v38, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v42, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v46, resolved type: com.google.android.gms.internal.hi} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v49, resolved type: com.google.android.gms.internal.hi} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v52, resolved type: com.google.android.gms.internal.hi} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v58, resolved type: android.location.Location} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v64, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v71, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v75, resolved type: com.google.android.gms.location.LocationRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v80, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v84, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v93, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v97, resolved type: android.app.PendingIntent} */
        /* JADX WARNING: type inference failed for: r0v0 */
        /* JADX WARNING: type inference failed for: r0v102 */
        /* JADX WARNING: type inference failed for: r0v103 */
        /* JADX WARNING: type inference failed for: r0v104 */
        /* JADX WARNING: type inference failed for: r0v105 */
        /* JADX WARNING: type inference failed for: r0v106 */
        /* JADX WARNING: type inference failed for: r0v107 */
        /* JADX WARNING: type inference failed for: r0v108 */
        /* JADX WARNING: type inference failed for: r0v109 */
        /* JADX WARNING: type inference failed for: r0v110 */
        /* JADX WARNING: type inference failed for: r0v111 */
        /* JADX WARNING: type inference failed for: r0v112 */
        /* JADX WARNING: type inference failed for: r0v113 */
        /* JADX WARNING: type inference failed for: r0v114 */
        /* JADX WARNING: type inference failed for: r0v115 */
        /* JADX WARNING: type inference failed for: r0v116 */
        /* JADX WARNING: type inference failed for: r0v117 */
        /* JADX WARNING: type inference failed for: r0v118 */
        /* JADX WARNING: type inference failed for: r0v119 */
        /* JADX WARNING: type inference failed for: r0v120 */
        /* JADX WARNING: type inference failed for: r0v121 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r8, android.os.Parcel r9, android.os.Parcel r10, int r11) throws android.os.RemoteException {
            /*
                r7 = this;
                r1 = 0
                r0 = 0
                r6 = 1
                switch(r8) {
                    case 1: goto L_0x0011;
                    case 2: goto L_0x003d;
                    case 3: goto L_0x0063;
                    case 4: goto L_0x007f;
                    case 5: goto L_0x0098;
                    case 6: goto L_0x00be;
                    case 7: goto L_0x00d9;
                    case 8: goto L_0x00f4;
                    case 9: goto L_0x0115;
                    case 10: goto L_0x013e;
                    case 11: goto L_0x0153;
                    case 12: goto L_0x016e;
                    case 13: goto L_0x0182;
                    case 14: goto L_0x019d;
                    case 15: goto L_0x01e1;
                    case 16: goto L_0x0206;
                    case 17: goto L_0x0243;
                    case 18: goto L_0x0272;
                    case 19: goto L_0x02a9;
                    case 20: goto L_0x02d2;
                    case 21: goto L_0x02f7;
                    case 24: goto L_0x0316;
                    case 25: goto L_0x0326;
                    case 26: goto L_0x0344;
                    case 27: goto L_0x0363;
                    case 28: goto L_0x0388;
                    case 29: goto L_0x03ad;
                    case 30: goto L_0x03c6;
                    case 31: goto L_0x03eb;
                    case 32: goto L_0x0410;
                    case 33: goto L_0x0430;
                    case 34: goto L_0x0449;
                    case 1598968902: goto L_0x000b;
                    default: goto L_0x0006;
                }
            L_0x0006:
                boolean r6 = super.onTransact(r8, r9, r10, r11)
            L_0x000a:
                return r6
            L_0x000b:
                java.lang.String r0 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r10.writeString(r0)
                goto L_0x000a
            L_0x0011:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                com.google.android.gms.internal.gp r1 = com.google.android.gms.internal.go.CREATOR
                java.util.ArrayList r1 = r9.createTypedArrayList(r1)
                int r2 = r9.readInt()
                if (r2 == 0) goto L_0x002a
                android.os.Parcelable$Creator r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            L_0x002a:
                android.os.IBinder r2 = r9.readStrongBinder()
                com.google.android.gms.internal.gk r2 = com.google.android.gms.internal.gk.a.K(r2)
                java.lang.String r3 = r9.readString()
                r7.a((java.util.List<com.google.android.gms.internal.go>) r1, (android.app.PendingIntent) r0, (com.google.android.gms.internal.gk) r2, (java.lang.String) r3)
                r10.writeNoException()
                goto L_0x000a
            L_0x003d:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x0050
                android.os.Parcelable$Creator r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            L_0x0050:
                android.os.IBinder r1 = r9.readStrongBinder()
                com.google.android.gms.internal.gk r1 = com.google.android.gms.internal.gk.a.K(r1)
                java.lang.String r2 = r9.readString()
                r7.a((android.app.PendingIntent) r0, (com.google.android.gms.internal.gk) r1, (java.lang.String) r2)
                r10.writeNoException()
                goto L_0x000a
            L_0x0063:
                java.lang.String r0 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r0)
                java.lang.String[] r0 = r9.createStringArray()
                android.os.IBinder r1 = r9.readStrongBinder()
                com.google.android.gms.internal.gk r1 = com.google.android.gms.internal.gk.a.K(r1)
                java.lang.String r2 = r9.readString()
                r7.a((java.lang.String[]) r0, (com.google.android.gms.internal.gk) r1, (java.lang.String) r2)
                r10.writeNoException()
                goto L_0x000a
            L_0x007f:
                java.lang.String r0 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r0)
                android.os.IBinder r0 = r9.readStrongBinder()
                com.google.android.gms.internal.gk r0 = com.google.android.gms.internal.gk.a.K(r0)
                java.lang.String r1 = r9.readString()
                r7.a((com.google.android.gms.internal.gk) r0, (java.lang.String) r1)
                r10.writeNoException()
                goto L_0x000a
            L_0x0098:
                java.lang.String r2 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r2)
                long r2 = r9.readLong()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x00a8
                r1 = r6
            L_0x00a8:
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x00b6
                android.os.Parcelable$Creator r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            L_0x00b6:
                r7.a((long) r2, (boolean) r1, (android.app.PendingIntent) r0)
                r10.writeNoException()
                goto L_0x000a
            L_0x00be:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x00d1
                android.os.Parcelable$Creator r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            L_0x00d1:
                r7.removeActivityUpdates(r0)
                r10.writeNoException()
                goto L_0x000a
            L_0x00d9:
                java.lang.String r0 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r0)
                android.location.Location r0 = r7.dH()
                r10.writeNoException()
                if (r0 == 0) goto L_0x00ef
                r10.writeInt(r6)
                r0.writeToParcel(r10, r6)
                goto L_0x000a
            L_0x00ef:
                r10.writeInt(r1)
                goto L_0x000a
            L_0x00f4:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x0105
                com.google.android.gms.location.LocationRequestCreator r0 = com.google.android.gms.location.LocationRequest.CREATOR
                com.google.android.gms.location.LocationRequest r0 = r0.createFromParcel((android.os.Parcel) r9)
            L_0x0105:
                android.os.IBinder r1 = r9.readStrongBinder()
                com.google.android.gms.location.c r1 = com.google.android.gms.location.c.a.I(r1)
                r7.a((com.google.android.gms.location.LocationRequest) r0, (com.google.android.gms.location.c) r1)
                r10.writeNoException()
                goto L_0x000a
            L_0x0115:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x013c
                com.google.android.gms.location.LocationRequestCreator r1 = com.google.android.gms.location.LocationRequest.CREATOR
                com.google.android.gms.location.LocationRequest r1 = r1.createFromParcel((android.os.Parcel) r9)
            L_0x0126:
                int r2 = r9.readInt()
                if (r2 == 0) goto L_0x0134
                android.os.Parcelable$Creator r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            L_0x0134:
                r7.a((com.google.android.gms.location.LocationRequest) r1, (android.app.PendingIntent) r0)
                r10.writeNoException()
                goto L_0x000a
            L_0x013c:
                r1 = r0
                goto L_0x0126
            L_0x013e:
                java.lang.String r0 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r0)
                android.os.IBinder r0 = r9.readStrongBinder()
                com.google.android.gms.location.c r0 = com.google.android.gms.location.c.a.I(r0)
                r7.a((com.google.android.gms.location.c) r0)
                r10.writeNoException()
                goto L_0x000a
            L_0x0153:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x0166
                android.os.Parcelable$Creator r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            L_0x0166:
                r7.a((android.app.PendingIntent) r0)
                r10.writeNoException()
                goto L_0x000a
            L_0x016e:
                java.lang.String r0 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r0)
                int r0 = r9.readInt()
                if (r0 == 0) goto L_0x017a
                r1 = r6
            L_0x017a:
                r7.setMockMode(r1)
                r10.writeNoException()
                goto L_0x000a
            L_0x0182:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x0195
                android.os.Parcelable$Creator r0 = android.location.Location.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.location.Location r0 = (android.location.Location) r0
            L_0x0195:
                r7.setMockLocation(r0)
                r10.writeNoException()
                goto L_0x000a
            L_0x019d:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x01db
                com.google.android.gms.maps.model.LatLngBoundsCreator r1 = com.google.android.gms.maps.model.LatLngBounds.CREATOR
                com.google.android.gms.maps.model.LatLngBounds r1 = r1.createFromParcel((android.os.Parcel) r9)
            L_0x01ae:
                int r2 = r9.readInt()
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x01dd
                com.google.android.gms.internal.gu r3 = com.google.android.gms.internal.gt.CREATOR
                com.google.android.gms.internal.gt r3 = r3.createFromParcel(r9)
            L_0x01be:
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x01df
                com.google.android.gms.internal.hj r0 = com.google.android.gms.internal.hi.CREATOR
                com.google.android.gms.internal.hi r4 = r0.createFromParcel(r9)
            L_0x01ca:
                android.os.IBinder r0 = r9.readStrongBinder()
                com.google.android.gms.internal.gz r5 = com.google.android.gms.internal.gz.a.N(r0)
                r0 = r7
                r0.a(r1, r2, r3, r4, r5)
                r10.writeNoException()
                goto L_0x000a
            L_0x01db:
                r1 = r0
                goto L_0x01ae
            L_0x01dd:
                r3 = r0
                goto L_0x01be
            L_0x01df:
                r4 = r0
                goto L_0x01ca
            L_0x01e1:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                java.lang.String r1 = r9.readString()
                int r2 = r9.readInt()
                if (r2 == 0) goto L_0x01f6
                com.google.android.gms.internal.hj r0 = com.google.android.gms.internal.hi.CREATOR
                com.google.android.gms.internal.hi r0 = r0.createFromParcel(r9)
            L_0x01f6:
                android.os.IBinder r2 = r9.readStrongBinder()
                com.google.android.gms.internal.gz r2 = com.google.android.gms.internal.gz.a.N(r2)
                r7.a((java.lang.String) r1, (com.google.android.gms.internal.hi) r0, (com.google.android.gms.internal.gz) r2)
                r10.writeNoException()
                goto L_0x000a
            L_0x0206:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x023f
                com.google.android.gms.maps.model.LatLngCreator r1 = com.google.android.gms.maps.model.LatLng.CREATOR
                com.google.android.gms.maps.model.LatLng r1 = r1.createFromParcel((android.os.Parcel) r9)
            L_0x0217:
                int r2 = r9.readInt()
                if (r2 == 0) goto L_0x0241
                com.google.android.gms.internal.gu r2 = com.google.android.gms.internal.gt.CREATOR
                com.google.android.gms.internal.gt r2 = r2.createFromParcel(r9)
            L_0x0223:
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x022f
                com.google.android.gms.internal.hj r0 = com.google.android.gms.internal.hi.CREATOR
                com.google.android.gms.internal.hi r0 = r0.createFromParcel(r9)
            L_0x022f:
                android.os.IBinder r3 = r9.readStrongBinder()
                com.google.android.gms.internal.gz r3 = com.google.android.gms.internal.gz.a.N(r3)
                r7.a((com.google.android.gms.maps.model.LatLng) r1, (com.google.android.gms.internal.gt) r2, (com.google.android.gms.internal.hi) r0, (com.google.android.gms.internal.gz) r3)
                r10.writeNoException()
                goto L_0x000a
            L_0x023f:
                r1 = r0
                goto L_0x0217
            L_0x0241:
                r2 = r0
                goto L_0x0223
            L_0x0243:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x0270
                com.google.android.gms.internal.gu r1 = com.google.android.gms.internal.gt.CREATOR
                com.google.android.gms.internal.gt r1 = r1.createFromParcel(r9)
            L_0x0254:
                int r2 = r9.readInt()
                if (r2 == 0) goto L_0x0260
                com.google.android.gms.internal.hj r0 = com.google.android.gms.internal.hi.CREATOR
                com.google.android.gms.internal.hi r0 = r0.createFromParcel(r9)
            L_0x0260:
                android.os.IBinder r2 = r9.readStrongBinder()
                com.google.android.gms.internal.gz r2 = com.google.android.gms.internal.gz.a.N(r2)
                r7.a((com.google.android.gms.internal.gt) r1, (com.google.android.gms.internal.hi) r0, (com.google.android.gms.internal.gz) r2)
                r10.writeNoException()
                goto L_0x000a
            L_0x0270:
                r1 = r0
                goto L_0x0254
            L_0x0272:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x02a5
                com.google.android.gms.internal.gw r1 = com.google.android.gms.internal.gv.CREATOR
                com.google.android.gms.internal.gv r1 = r1.createFromParcel(r9)
            L_0x0283:
                int r2 = r9.readInt()
                if (r2 == 0) goto L_0x02a7
                com.google.android.gms.internal.hj r2 = com.google.android.gms.internal.hi.CREATOR
                com.google.android.gms.internal.hi r2 = r2.createFromParcel(r9)
            L_0x028f:
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x029d
                android.os.Parcelable$Creator r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            L_0x029d:
                r7.a((com.google.android.gms.internal.gv) r1, (com.google.android.gms.internal.hi) r2, (android.app.PendingIntent) r0)
                r10.writeNoException()
                goto L_0x000a
            L_0x02a5:
                r1 = r0
                goto L_0x0283
            L_0x02a7:
                r2 = r0
                goto L_0x028f
            L_0x02a9:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x02d0
                com.google.android.gms.internal.hj r1 = com.google.android.gms.internal.hi.CREATOR
                com.google.android.gms.internal.hi r1 = r1.createFromParcel(r9)
            L_0x02ba:
                int r2 = r9.readInt()
                if (r2 == 0) goto L_0x02c8
                android.os.Parcelable$Creator r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            L_0x02c8:
                r7.a((com.google.android.gms.internal.hi) r1, (android.app.PendingIntent) r0)
                r10.writeNoException()
                goto L_0x000a
            L_0x02d0:
                r1 = r0
                goto L_0x02ba
            L_0x02d2:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x02e3
                com.google.android.gms.location.LocationRequestCreator r0 = com.google.android.gms.location.LocationRequest.CREATOR
                com.google.android.gms.location.LocationRequest r0 = r0.createFromParcel((android.os.Parcel) r9)
            L_0x02e3:
                android.os.IBinder r1 = r9.readStrongBinder()
                com.google.android.gms.location.c r1 = com.google.android.gms.location.c.a.I(r1)
                java.lang.String r2 = r9.readString()
                r7.a((com.google.android.gms.location.LocationRequest) r0, (com.google.android.gms.location.c) r1, (java.lang.String) r2)
                r10.writeNoException()
                goto L_0x000a
            L_0x02f7:
                java.lang.String r0 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r0)
                java.lang.String r0 = r9.readString()
                android.location.Location r0 = r7.an(r0)
                r10.writeNoException()
                if (r0 == 0) goto L_0x0311
                r10.writeInt(r6)
                r0.writeToParcel(r10, r6)
                goto L_0x000a
            L_0x0311:
                r10.writeInt(r1)
                goto L_0x000a
            L_0x0316:
                java.lang.String r0 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r0)
                com.google.android.gms.internal.ha r0 = com.google.android.gms.internal.hd.a.CREATOR
                java.util.ArrayList r0 = r9.createTypedArrayList(r0)
                r7.a((java.util.List<com.google.android.gms.internal.hd.a>) r0)
                goto L_0x000a
            L_0x0326:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                java.lang.String r1 = r9.readString()
                java.lang.String r2 = r9.readString()
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x033f
                com.google.android.gms.internal.hj r0 = com.google.android.gms.internal.hi.CREATOR
                com.google.android.gms.internal.hi r0 = r0.createFromParcel(r9)
            L_0x033f:
                r7.a((java.lang.String) r1, (java.lang.String) r2, (com.google.android.gms.internal.hi) r0)
                goto L_0x000a
            L_0x0344:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x0357
                android.os.Parcelable$Creator r0 = android.location.Location.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.location.Location r0 = (android.location.Location) r0
            L_0x0357:
                int r1 = r9.readInt()
                r7.a((android.location.Location) r0, (int) r1)
                r10.writeNoException()
                goto L_0x000a
            L_0x0363:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                java.lang.String r1 = r9.readString()
                int r2 = r9.readInt()
                if (r2 == 0) goto L_0x0378
                com.google.android.gms.location.b r0 = com.google.android.gms.location.a.CREATOR
                com.google.android.gms.location.a r0 = r0.createFromParcel(r9)
            L_0x0378:
                android.os.IBinder r2 = r9.readStrongBinder()
                com.google.android.gms.internal.gj r2 = com.google.android.gms.internal.gj.a.J(r2)
                r7.a((java.lang.String) r1, (com.google.android.gms.location.a) r0, (com.google.android.gms.internal.gj) r2)
                r10.writeNoException()
                goto L_0x000a
            L_0x0388:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                java.lang.String r1 = r9.readString()
                int r2 = r9.readInt()
                if (r2 == 0) goto L_0x039d
                com.google.android.gms.internal.gi r0 = com.google.android.gms.internal.gh.CREATOR
                com.google.android.gms.internal.gh r0 = r0.createFromParcel(r9)
            L_0x039d:
                android.os.IBinder r2 = r9.readStrongBinder()
                com.google.android.gms.internal.gj r2 = com.google.android.gms.internal.gj.a.J(r2)
                r7.a((java.lang.String) r1, (com.google.android.gms.internal.gh) r0, (com.google.android.gms.internal.gj) r2)
                r10.writeNoException()
                goto L_0x000a
            L_0x03ad:
                java.lang.String r0 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r0)
                java.lang.String r0 = r9.readString()
                android.os.IBinder r1 = r9.readStrongBinder()
                com.google.android.gms.internal.gj r1 = com.google.android.gms.internal.gj.a.J(r1)
                r7.a((java.lang.String) r0, (com.google.android.gms.internal.gj) r1)
                r10.writeNoException()
                goto L_0x000a
            L_0x03c6:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                java.lang.String r1 = r9.readString()
                int r2 = r9.readInt()
                if (r2 == 0) goto L_0x03db
                com.google.android.gms.location.g r0 = com.google.android.gms.location.f.CREATOR
                com.google.android.gms.location.f r0 = r0.createFromParcel(r9)
            L_0x03db:
                android.os.IBinder r2 = r9.readStrongBinder()
                com.google.android.gms.internal.gj r2 = com.google.android.gms.internal.gj.a.J(r2)
                r7.a((java.lang.String) r1, (com.google.android.gms.location.f) r0, (com.google.android.gms.internal.gj) r2)
                r10.writeNoException()
                goto L_0x000a
            L_0x03eb:
                java.lang.String r1 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r1)
                java.lang.String r1 = r9.readString()
                int r2 = r9.readInt()
                if (r2 == 0) goto L_0x0400
                com.google.android.gms.location.g r0 = com.google.android.gms.location.f.CREATOR
                com.google.android.gms.location.f r0 = r0.createFromParcel(r9)
            L_0x0400:
                android.os.IBinder r2 = r9.readStrongBinder()
                com.google.android.gms.internal.gj r2 = com.google.android.gms.internal.gj.a.J(r2)
                r7.b(r1, r0, r2)
                r10.writeNoException()
                goto L_0x000a
            L_0x0410:
                java.lang.String r0 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r0)
                java.lang.String r0 = r9.readString()
                int r2 = r9.readInt()
                if (r2 == 0) goto L_0x0420
                r1 = r6
            L_0x0420:
                android.os.IBinder r2 = r9.readStrongBinder()
                com.google.android.gms.internal.gj r2 = com.google.android.gms.internal.gj.a.J(r2)
                r7.a((java.lang.String) r0, (boolean) r1, (com.google.android.gms.internal.gj) r2)
                r10.writeNoException()
                goto L_0x000a
            L_0x0430:
                java.lang.String r0 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r0)
                java.lang.String r0 = r9.readString()
                android.os.IBinder r1 = r9.readStrongBinder()
                com.google.android.gms.internal.gj r1 = com.google.android.gms.internal.gj.a.J(r1)
                r7.b(r0, r1)
                r10.writeNoException()
                goto L_0x000a
            L_0x0449:
                java.lang.String r0 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.enforceInterface(r0)
                java.lang.String r0 = r9.readString()
                com.google.android.gms.location.d r0 = r7.ao(r0)
                r10.writeNoException()
                if (r0 == 0) goto L_0x0463
                r10.writeInt(r6)
                r0.writeToParcel(r10, r6)
                goto L_0x000a
            L_0x0463:
                r10.writeInt(r1)
                goto L_0x000a
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gl.a.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void a(long j, boolean z, PendingIntent pendingIntent) throws RemoteException;

    void a(PendingIntent pendingIntent) throws RemoteException;

    void a(PendingIntent pendingIntent, gk gkVar, String str) throws RemoteException;

    void a(Location location, int i) throws RemoteException;

    void a(gk gkVar, String str) throws RemoteException;

    void a(gt gtVar, hi hiVar, gz gzVar) throws RemoteException;

    void a(gv gvVar, hi hiVar, PendingIntent pendingIntent) throws RemoteException;

    void a(hi hiVar, PendingIntent pendingIntent) throws RemoteException;

    void a(LocationRequest locationRequest, PendingIntent pendingIntent) throws RemoteException;

    void a(LocationRequest locationRequest, c cVar) throws RemoteException;

    void a(LocationRequest locationRequest, c cVar, String str) throws RemoteException;

    void a(c cVar) throws RemoteException;

    void a(LatLng latLng, gt gtVar, hi hiVar, gz gzVar) throws RemoteException;

    void a(LatLngBounds latLngBounds, int i, gt gtVar, hi hiVar, gz gzVar) throws RemoteException;

    void a(String str, gh ghVar, gj gjVar) throws RemoteException;

    void a(String str, gj gjVar) throws RemoteException;

    void a(String str, hi hiVar, gz gzVar) throws RemoteException;

    void a(String str, com.google.android.gms.location.a aVar, gj gjVar) throws RemoteException;

    void a(String str, f fVar, gj gjVar) throws RemoteException;

    void a(String str, String str2, hi hiVar) throws RemoteException;

    void a(String str, boolean z, gj gjVar) throws RemoteException;

    void a(List<hd.a> list) throws RemoteException;

    void a(List<go> list, PendingIntent pendingIntent, gk gkVar, String str) throws RemoteException;

    void a(String[] strArr, gk gkVar, String str) throws RemoteException;

    Location an(String str) throws RemoteException;

    d ao(String str) throws RemoteException;

    void b(String str, gj gjVar) throws RemoteException;

    void b(String str, f fVar, gj gjVar) throws RemoteException;

    Location dH() throws RemoteException;

    void removeActivityUpdates(PendingIntent pendingIntent) throws RemoteException;

    void setMockLocation(Location location) throws RemoteException;

    void setMockMode(boolean z) throws RemoteException;
}
