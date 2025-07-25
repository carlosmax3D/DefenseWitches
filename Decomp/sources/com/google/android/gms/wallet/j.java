package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class j implements Parcelable.Creator<NotifyTransactionStatusRequest> {
    static void a(NotifyTransactionStatusRequest notifyTransactionStatusRequest, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, notifyTransactionStatusRequest.kg);
        b.a(parcel, 2, notifyTransactionStatusRequest.Gn, false);
        b.c(parcel, 3, notifyTransactionStatusRequest.status);
        b.a(parcel, 4, notifyTransactionStatusRequest.GV, false);
        b.D(parcel, o);
    }

    /* renamed from: aN */
    public NotifyTransactionStatusRequest createFromParcel(Parcel parcel) {
        String str = null;
        int i = 0;
        int n = a.n(parcel);
        String str2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i2 = a.g(parcel, m);
                    break;
                case 2:
                    str2 = a.m(parcel, m);
                    break;
                case 3:
                    i = a.g(parcel, m);
                    break;
                case 4:
                    str = a.m(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new NotifyTransactionStatusRequest(i2, str2, i, str);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bF */
    public NotifyTransactionStatusRequest[] newArray(int i) {
        return new NotifyTransactionStatusRequest[i];
    }
}
