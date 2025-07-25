package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class h implements Parcelable.Creator<Operator> {
    static void a(Operator operator, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1000, operator.kg);
        b.a(parcel, 1, operator.mTag, false);
        b.D(parcel, o);
    }

    /* renamed from: X */
    public Operator createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        String str = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    str = a.m(parcel, m);
                    break;
                case 1000:
                    i = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new Operator(i, str);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: ax */
    public Operator[] newArray(int i) {
        return new Operator[i];
    }
}
