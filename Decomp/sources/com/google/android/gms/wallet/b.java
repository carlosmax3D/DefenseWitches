package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import java.util.ArrayList;

public class b implements Parcelable.Creator<Cart> {
    static void a(Cart cart, Parcel parcel, int i) {
        int o = com.google.android.gms.common.internal.safeparcel.b.o(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, cart.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, cart.Gj, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, cart.Gk, false);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 4, cart.Gl, false);
        com.google.android.gms.common.internal.safeparcel.b.D(parcel, o);
    }

    /* renamed from: aF */
    public Cart createFromParcel(Parcel parcel) {
        String str = null;
        int n = a.n(parcel);
        int i = 0;
        ArrayList<LineItem> arrayList = new ArrayList<>();
        String str2 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    str2 = a.m(parcel, m);
                    break;
                case 3:
                    str = a.m(parcel, m);
                    break;
                case 4:
                    arrayList = a.c(parcel, m, LineItem.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new Cart(i, str2, str, arrayList);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: bx */
    public Cart[] newArray(int i) {
        return new Cart[i];
    }
}
