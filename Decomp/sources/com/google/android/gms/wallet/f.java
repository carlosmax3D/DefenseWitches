package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class f implements Parcelable.Creator<LineItem> {
    static void a(LineItem lineItem, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, lineItem.getVersionCode());
        b.a(parcel, 2, lineItem.description, false);
        b.a(parcel, 3, lineItem.Gw, false);
        b.a(parcel, 4, lineItem.Gx, false);
        b.a(parcel, 5, lineItem.Gj, false);
        b.c(parcel, 6, lineItem.Gy);
        b.a(parcel, 7, lineItem.Gk, false);
        b.D(parcel, o);
    }

    /* renamed from: aJ */
    public LineItem createFromParcel(Parcel parcel) {
        int i = 0;
        String str = null;
        int n = a.n(parcel);
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        int i2 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i2 = a.g(parcel, m);
                    break;
                case 2:
                    str5 = a.m(parcel, m);
                    break;
                case 3:
                    str4 = a.m(parcel, m);
                    break;
                case 4:
                    str3 = a.m(parcel, m);
                    break;
                case 5:
                    str2 = a.m(parcel, m);
                    break;
                case 6:
                    i = a.g(parcel, m);
                    break;
                case 7:
                    str = a.m(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new LineItem(i2, str5, str4, str3, str2, i, str);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bB */
    public LineItem[] newArray(int i) {
        return new LineItem[i];
    }
}
