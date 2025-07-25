package com.google.android.gms.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;

public interface fo extends IInterface {

    public static abstract class a extends Binder implements fo {

        /* renamed from: com.google.android.gms.internal.fo$a$a  reason: collision with other inner class name */
        private static class C0028a implements fo {
            private IBinder dU;

            C0028a(IBinder iBinder) {
                this.dU = iBinder;
            }

            public void A(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5038, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void B(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5035, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void C(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5039, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void D(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(8001, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(8002, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(int i, String str, boolean z) throws RemoteException {
                int i2 = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (z) {
                        i2 = 1;
                    }
                    obtain.writeInt(i2);
                    this.dU.transact(5034, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(DataHolder dataHolder, DataHolder dataHolder2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (dataHolder2 != null) {
                        obtain.writeInt(1);
                        dataHolder2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5005, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void a(DataHolder dataHolder, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStringArray(strArr);
                    this.dU.transact(5026, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void aB(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    this.dU.transact(5013, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void aC(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    this.dU.transact(5015, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void aD(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    this.dU.transact(5036, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void aE(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    this.dU.transact(5040, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.dU;
            }

            public void b(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.dU.transact(5033, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void b(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5002, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void b(DataHolder dataHolder, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStringArray(strArr);
                    this.dU.transact(5027, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void c(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5004, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void c(DataHolder dataHolder, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStringArray(strArr);
                    this.dU.transact(5028, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void d(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.dU.transact(5001, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void d(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5006, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void d(DataHolder dataHolder, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStringArray(strArr);
                    this.dU.transact(5029, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void e(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5007, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void e(DataHolder dataHolder, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStringArray(strArr);
                    this.dU.transact(5030, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void f(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5008, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void f(DataHolder dataHolder, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStringArray(strArr);
                    this.dU.transact(5031, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void g(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5009, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void h(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5010, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void i(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5011, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void j(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(9001, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void k(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5017, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void l(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5037, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(8003, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void n(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(8004, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void o(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(8005, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onAchievementUpdated(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.dU.transact(5003, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onInvitationRemoved(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeString(str);
                    this.dU.transact(8010, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onLeftRoom(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.dU.transact(5020, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onP2PConnected(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeString(str);
                    this.dU.transact(6001, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onP2PDisconnected(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeString(str);
                    this.dU.transact(6002, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (realTimeMessage != null) {
                        obtain.writeInt(1);
                        realTimeMessage.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5032, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onSignOutComplete() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    this.dU.transact(5016, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onTurnBasedMatchCanceled(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.dU.transact(8007, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onTurnBasedMatchRemoved(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeString(str);
                    this.dU.transact(8009, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void p(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(8006, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void q(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(8008, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void r(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5012, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void s(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5014, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void t(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5018, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void u(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5019, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void v(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5021, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void w(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5022, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void x(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5023, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void y(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5024, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void z(DataHolder dataHolder) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (dataHolder != null) {
                        obtain.writeInt(1);
                        dataHolder.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.dU.transact(5025, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public a() {
            attachInterface(this, "com.google.android.gms.games.internal.IGamesCallbacks");
        }

        public static fo G(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.games.internal.IGamesCallbacks");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof fo)) ? new C0028a(iBinder) : (fo) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v13, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v25, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v29, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v38, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v43, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v46, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v56, resolved type: com.google.android.gms.games.multiplayer.realtime.RealTimeMessage} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v60, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v63, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v66, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v69, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v72, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v75, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v78, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v81, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v84, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v87, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v90, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v95, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v98, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v101, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v104, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v110, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v115, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v118, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v121, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v124, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v127, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v130, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v133, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v136, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v139, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v144, resolved type: com.google.android.gms.common.data.DataHolder} */
        /* JADX WARNING: type inference failed for: r0v0 */
        /* JADX WARNING: type inference failed for: r0v150 */
        /* JADX WARNING: type inference failed for: r0v151 */
        /* JADX WARNING: type inference failed for: r0v152 */
        /* JADX WARNING: type inference failed for: r0v153 */
        /* JADX WARNING: type inference failed for: r0v154 */
        /* JADX WARNING: type inference failed for: r0v155 */
        /* JADX WARNING: type inference failed for: r0v156 */
        /* JADX WARNING: type inference failed for: r0v157 */
        /* JADX WARNING: type inference failed for: r0v158 */
        /* JADX WARNING: type inference failed for: r0v159 */
        /* JADX WARNING: type inference failed for: r0v160 */
        /* JADX WARNING: type inference failed for: r0v161 */
        /* JADX WARNING: type inference failed for: r0v162 */
        /* JADX WARNING: type inference failed for: r0v163 */
        /* JADX WARNING: type inference failed for: r0v164 */
        /* JADX WARNING: type inference failed for: r0v165 */
        /* JADX WARNING: type inference failed for: r0v166 */
        /* JADX WARNING: type inference failed for: r0v167 */
        /* JADX WARNING: type inference failed for: r0v168 */
        /* JADX WARNING: type inference failed for: r0v169 */
        /* JADX WARNING: type inference failed for: r0v170 */
        /* JADX WARNING: type inference failed for: r0v171 */
        /* JADX WARNING: type inference failed for: r0v172 */
        /* JADX WARNING: type inference failed for: r0v173 */
        /* JADX WARNING: type inference failed for: r0v174 */
        /* JADX WARNING: type inference failed for: r0v175 */
        /* JADX WARNING: type inference failed for: r0v176 */
        /* JADX WARNING: type inference failed for: r0v177 */
        /* JADX WARNING: type inference failed for: r0v178 */
        /* JADX WARNING: type inference failed for: r0v179 */
        /* JADX WARNING: type inference failed for: r0v180 */
        /* JADX WARNING: type inference failed for: r0v181 */
        /* JADX WARNING: type inference failed for: r0v182 */
        /* JADX WARNING: type inference failed for: r0v183 */
        /* JADX WARNING: type inference failed for: r0v184 */
        /* JADX WARNING: type inference failed for: r0v185 */
        /* JADX WARNING: type inference failed for: r0v186 */
        /* JADX WARNING: type inference failed for: r0v187 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                r0 = 0
                r2 = 1
                switch(r5) {
                    case 5001: goto L_0x0010;
                    case 5002: goto L_0x0024;
                    case 5003: goto L_0x003c;
                    case 5004: goto L_0x0050;
                    case 5005: goto L_0x0068;
                    case 5006: goto L_0x008f;
                    case 5007: goto L_0x00a8;
                    case 5008: goto L_0x00c1;
                    case 5009: goto L_0x00da;
                    case 5010: goto L_0x00f3;
                    case 5011: goto L_0x010c;
                    case 5012: goto L_0x0125;
                    case 5013: goto L_0x013e;
                    case 5014: goto L_0x014f;
                    case 5015: goto L_0x0168;
                    case 5016: goto L_0x0179;
                    case 5017: goto L_0x0186;
                    case 5018: goto L_0x01b8;
                    case 5019: goto L_0x01d1;
                    case 5020: goto L_0x01ea;
                    case 5021: goto L_0x01ff;
                    case 5022: goto L_0x0218;
                    case 5023: goto L_0x0231;
                    case 5024: goto L_0x024a;
                    case 5025: goto L_0x0263;
                    case 5026: goto L_0x027c;
                    case 5027: goto L_0x0299;
                    case 5028: goto L_0x02b6;
                    case 5029: goto L_0x02d3;
                    case 5030: goto L_0x02f0;
                    case 5031: goto L_0x030d;
                    case 5032: goto L_0x032a;
                    case 5033: goto L_0x0345;
                    case 5034: goto L_0x035e;
                    case 5035: goto L_0x0395;
                    case 5036: goto L_0x03ae;
                    case 5037: goto L_0x019f;
                    case 5038: goto L_0x037c;
                    case 5039: goto L_0x03bf;
                    case 5040: goto L_0x03d8;
                    case 6001: goto L_0x03e9;
                    case 6002: goto L_0x03fa;
                    case 8001: goto L_0x040b;
                    case 8002: goto L_0x0424;
                    case 8003: goto L_0x0443;
                    case 8004: goto L_0x045c;
                    case 8005: goto L_0x0475;
                    case 8006: goto L_0x048e;
                    case 8007: goto L_0x04a7;
                    case 8008: goto L_0x04bc;
                    case 8009: goto L_0x04d5;
                    case 8010: goto L_0x04e6;
                    case 9001: goto L_0x04f7;
                    case 1598968902: goto L_0x000a;
                    default: goto L_0x0005;
                }
            L_0x0005:
                boolean r2 = super.onTransact(r5, r6, r7, r8)
            L_0x0009:
                return r2
            L_0x000a:
                java.lang.String r0 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r7.writeString(r0)
                goto L_0x0009
            L_0x0010:
                java.lang.String r0 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r0)
                int r0 = r6.readInt()
                java.lang.String r1 = r6.readString()
                r4.d((int) r0, (java.lang.String) r1)
                r7.writeNoException()
                goto L_0x0009
            L_0x0024:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0035
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x0035:
                r4.b(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x003c:
                java.lang.String r0 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r0)
                int r0 = r6.readInt()
                java.lang.String r1 = r6.readString()
                r4.onAchievementUpdated(r0, r1)
                r7.writeNoException()
                goto L_0x0009
            L_0x0050:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0061
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x0061:
                r4.c(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x0068:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x008d
                com.google.android.gms.common.data.DataHolderCreator r1 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r1 = r1.createFromParcel((android.os.Parcel) r6)
            L_0x0079:
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0085
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x0085:
                r4.a((com.google.android.gms.common.data.DataHolder) r1, (com.google.android.gms.common.data.DataHolder) r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x008d:
                r1 = r0
                goto L_0x0079
            L_0x008f:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x00a0
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x00a0:
                r4.d(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x00a8:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x00b9
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x00b9:
                r4.e(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x00c1:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x00d2
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x00d2:
                r4.f(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x00da:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x00eb
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x00eb:
                r4.g(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x00f3:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0104
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x0104:
                r4.h(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x010c:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x011d
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x011d:
                r4.i(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x0125:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0136
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x0136:
                r4.r(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x013e:
                java.lang.String r0 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r0)
                int r0 = r6.readInt()
                r4.aB(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x014f:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0160
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x0160:
                r4.s(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x0168:
                java.lang.String r0 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r0)
                int r0 = r6.readInt()
                r4.aC(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x0179:
                java.lang.String r0 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r0)
                r4.onSignOutComplete()
                r7.writeNoException()
                goto L_0x0009
            L_0x0186:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0197
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x0197:
                r4.k(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x019f:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x01b0
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x01b0:
                r4.l(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x01b8:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x01c9
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x01c9:
                r4.t(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x01d1:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x01e2
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x01e2:
                r4.u(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x01ea:
                java.lang.String r0 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r0)
                int r0 = r6.readInt()
                java.lang.String r1 = r6.readString()
                r4.onLeftRoom(r0, r1)
                r7.writeNoException()
                goto L_0x0009
            L_0x01ff:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0210
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x0210:
                r4.v(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x0218:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0229
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x0229:
                r4.w(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x0231:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0242
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x0242:
                r4.x(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x024a:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x025b
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x025b:
                r4.y(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x0263:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0274
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x0274:
                r4.z(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x027c:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x028d
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x028d:
                java.lang.String[] r1 = r6.createStringArray()
                r4.a((com.google.android.gms.common.data.DataHolder) r0, (java.lang.String[]) r1)
                r7.writeNoException()
                goto L_0x0009
            L_0x0299:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x02aa
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x02aa:
                java.lang.String[] r1 = r6.createStringArray()
                r4.b(r0, r1)
                r7.writeNoException()
                goto L_0x0009
            L_0x02b6:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x02c7
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x02c7:
                java.lang.String[] r1 = r6.createStringArray()
                r4.c(r0, r1)
                r7.writeNoException()
                goto L_0x0009
            L_0x02d3:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x02e4
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x02e4:
                java.lang.String[] r1 = r6.createStringArray()
                r4.d((com.google.android.gms.common.data.DataHolder) r0, (java.lang.String[]) r1)
                r7.writeNoException()
                goto L_0x0009
            L_0x02f0:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0301
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x0301:
                java.lang.String[] r1 = r6.createStringArray()
                r4.e(r0, r1)
                r7.writeNoException()
                goto L_0x0009
            L_0x030d:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x031e
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x031e:
                java.lang.String[] r1 = r6.createStringArray()
                r4.f(r0, r1)
                r7.writeNoException()
                goto L_0x0009
            L_0x032a:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x033d
                android.os.Parcelable$Creator<com.google.android.gms.games.multiplayer.realtime.RealTimeMessage> r0 = com.google.android.gms.games.multiplayer.realtime.RealTimeMessage.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r6)
                com.google.android.gms.games.multiplayer.realtime.RealTimeMessage r0 = (com.google.android.gms.games.multiplayer.realtime.RealTimeMessage) r0
            L_0x033d:
                r4.onRealTimeMessageReceived(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x0345:
                java.lang.String r0 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r0)
                int r0 = r6.readInt()
                int r1 = r6.readInt()
                java.lang.String r3 = r6.readString()
                r4.b(r0, r1, r3)
                r7.writeNoException()
                goto L_0x0009
            L_0x035e:
                java.lang.String r0 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                java.lang.String r3 = r6.readString()
                int r0 = r6.readInt()
                if (r0 == 0) goto L_0x037a
                r0 = r2
            L_0x0372:
                r4.a(r1, r3, r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x037a:
                r0 = 0
                goto L_0x0372
            L_0x037c:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x038d
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x038d:
                r4.A(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x0395:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x03a6
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x03a6:
                r4.B(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x03ae:
                java.lang.String r0 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r0)
                int r0 = r6.readInt()
                r4.aD(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x03bf:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x03d0
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x03d0:
                r4.C(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x03d8:
                java.lang.String r0 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r0)
                int r0 = r6.readInt()
                r4.aE(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x03e9:
                java.lang.String r0 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r0)
                java.lang.String r0 = r6.readString()
                r4.onP2PConnected(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x03fa:
                java.lang.String r0 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r0)
                java.lang.String r0 = r6.readString()
                r4.onP2PDisconnected(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x040b:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x041c
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x041c:
                r4.D(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x0424:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x043b
                android.os.Parcelable$Creator r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r6)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x043b:
                r4.a((int) r1, (android.os.Bundle) r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x0443:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0454
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x0454:
                r4.m(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x045c:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x046d
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x046d:
                r4.n(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x0475:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0486
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x0486:
                r4.o(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x048e:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x049f
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x049f:
                r4.p(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x04a7:
                java.lang.String r0 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r0)
                int r0 = r6.readInt()
                java.lang.String r1 = r6.readString()
                r4.onTurnBasedMatchCanceled(r0, r1)
                r7.writeNoException()
                goto L_0x0009
            L_0x04bc:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x04cd
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x04cd:
                r4.q(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x04d5:
                java.lang.String r0 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r0)
                java.lang.String r0 = r6.readString()
                r4.onTurnBasedMatchRemoved(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x04e6:
                java.lang.String r0 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r0)
                java.lang.String r0 = r6.readString()
                r4.onInvitationRemoved(r0)
                r7.writeNoException()
                goto L_0x0009
            L_0x04f7:
                java.lang.String r1 = "com.google.android.gms.games.internal.IGamesCallbacks"
                r6.enforceInterface(r1)
                int r1 = r6.readInt()
                if (r1 == 0) goto L_0x0508
                com.google.android.gms.common.data.DataHolderCreator r0 = com.google.android.gms.common.data.DataHolder.CREATOR
                com.google.android.gms.common.data.DataHolder r0 = r0.createFromParcel((android.os.Parcel) r6)
            L_0x0508:
                r4.j(r0)
                r7.writeNoException()
                goto L_0x0009
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.fo.a.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void A(DataHolder dataHolder) throws RemoteException;

    void B(DataHolder dataHolder) throws RemoteException;

    void C(DataHolder dataHolder) throws RemoteException;

    void D(DataHolder dataHolder) throws RemoteException;

    void a(int i, Bundle bundle) throws RemoteException;

    void a(int i, String str, boolean z) throws RemoteException;

    void a(DataHolder dataHolder, DataHolder dataHolder2) throws RemoteException;

    void a(DataHolder dataHolder, String[] strArr) throws RemoteException;

    void aB(int i) throws RemoteException;

    void aC(int i) throws RemoteException;

    void aD(int i) throws RemoteException;

    void aE(int i) throws RemoteException;

    void b(int i, int i2, String str) throws RemoteException;

    void b(DataHolder dataHolder) throws RemoteException;

    void b(DataHolder dataHolder, String[] strArr) throws RemoteException;

    void c(DataHolder dataHolder) throws RemoteException;

    void c(DataHolder dataHolder, String[] strArr) throws RemoteException;

    void d(int i, String str) throws RemoteException;

    void d(DataHolder dataHolder) throws RemoteException;

    void d(DataHolder dataHolder, String[] strArr) throws RemoteException;

    void e(DataHolder dataHolder) throws RemoteException;

    void e(DataHolder dataHolder, String[] strArr) throws RemoteException;

    void f(DataHolder dataHolder) throws RemoteException;

    void f(DataHolder dataHolder, String[] strArr) throws RemoteException;

    void g(DataHolder dataHolder) throws RemoteException;

    void h(DataHolder dataHolder) throws RemoteException;

    void i(DataHolder dataHolder) throws RemoteException;

    void j(DataHolder dataHolder) throws RemoteException;

    void k(DataHolder dataHolder) throws RemoteException;

    void l(DataHolder dataHolder) throws RemoteException;

    void m(DataHolder dataHolder) throws RemoteException;

    void n(DataHolder dataHolder) throws RemoteException;

    void o(DataHolder dataHolder) throws RemoteException;

    void onAchievementUpdated(int i, String str) throws RemoteException;

    void onInvitationRemoved(String str) throws RemoteException;

    void onLeftRoom(int i, String str) throws RemoteException;

    void onP2PConnected(String str) throws RemoteException;

    void onP2PDisconnected(String str) throws RemoteException;

    void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) throws RemoteException;

    void onSignOutComplete() throws RemoteException;

    void onTurnBasedMatchCanceled(int i, String str) throws RemoteException;

    void onTurnBasedMatchRemoved(String str) throws RemoteException;

    void p(DataHolder dataHolder) throws RemoteException;

    void q(DataHolder dataHolder) throws RemoteException;

    void r(DataHolder dataHolder) throws RemoteException;

    void s(DataHolder dataHolder) throws RemoteException;

    void t(DataHolder dataHolder) throws RemoteException;

    void u(DataHolder dataHolder) throws RemoteException;

    void v(DataHolder dataHolder) throws RemoteException;

    void w(DataHolder dataHolder) throws RemoteException;

    void x(DataHolder dataHolder) throws RemoteException;

    void y(DataHolder dataHolder) throws RemoteException;

    void z(DataHolder dataHolder) throws RemoteException;
}
