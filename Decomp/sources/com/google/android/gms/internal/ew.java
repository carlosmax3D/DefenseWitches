package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ev;
import java.util.ArrayList;

public class ew implements Parcelable.Creator<ev> {
    static void a(ev evVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, evVar.getVersionCode());
        b.b(parcel, 2, evVar.cy(), false);
        b.a(parcel, 3, evVar.cz(), false);
        b.D(parcel, o);
    }

    /* renamed from: T */
    public ev[] newArray(int i) {
        return new ev[i];
    }

    /* renamed from: v */
    public ev createFromParcel(Parcel parcel) {
        String str = null;
        int n = a.n(parcel);
        int i = 0;
        ArrayList arrayList = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    arrayList = a.c(parcel, m, ev.a.CREATOR);
                    break;
                case 3:
                    str = a.m(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ev(i, arrayList, str);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
