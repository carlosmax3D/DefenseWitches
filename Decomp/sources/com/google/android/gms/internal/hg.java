package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.ArrayList;

public class hg implements Parcelable.Creator<hf> {
    static void a(hf hfVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, hfVar.name, false);
        b.c(parcel, 1000, hfVar.versionCode);
        b.a(parcel, 2, hfVar.Bf, false);
        b.a(parcel, 3, hfVar.Bg, false);
        b.a(parcel, 4, hfVar.Bh, false);
        b.a(parcel, 5, hfVar.Bi, false);
        b.D(parcel, o);
    }

    /* renamed from: ap */
    public hf createFromParcel(Parcel parcel) {
        ArrayList<String> arrayList = null;
        int n = a.n(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    str4 = a.m(parcel, m);
                    break;
                case 2:
                    str3 = a.m(parcel, m);
                    break;
                case 3:
                    str2 = a.m(parcel, m);
                    break;
                case 4:
                    str = a.m(parcel, m);
                    break;
                case 5:
                    arrayList = a.y(parcel, m);
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
            return new hf(i, str4, str3, str2, str, arrayList);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bf */
    public hf[] newArray(int i) {
        return new hf[i];
    }
}
