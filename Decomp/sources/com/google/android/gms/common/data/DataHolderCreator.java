package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class DataHolderCreator implements Parcelable.Creator<DataHolder> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(DataHolder dataHolder, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.a(parcel, 1, dataHolder.bv(), false);
        b.c(parcel, 1000, dataHolder.getVersionCode());
        b.a(parcel, 2, (T[]) dataHolder.bw(), i, false);
        b.c(parcel, 3, dataHolder.getStatusCode());
        b.a(parcel, 4, dataHolder.getMetadata(), false);
        b.D(parcel, o);
    }

    public DataHolder createFromParcel(Parcel parcel) {
        int i = 0;
        Bundle bundle = null;
        int n = a.n(parcel);
        CursorWindow[] cursorWindowArr = null;
        String[] strArr = null;
        int i2 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    strArr = a.x(parcel, m);
                    break;
                case 2:
                    cursorWindowArr = (CursorWindow[]) a.b(parcel, m, CursorWindow.CREATOR);
                    break;
                case 3:
                    i = a.g(parcel, m);
                    break;
                case 4:
                    bundle = a.o(parcel, m);
                    break;
                case 1000:
                    i2 = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() != n) {
            throw new a.C0003a("Overread allowed size end=" + n, parcel);
        }
        DataHolder dataHolder = new DataHolder(i2, strArr, cursorWindowArr, i, bundle);
        dataHolder.validateContents();
        return dataHolder;
    }

    public DataHolder[] newArray(int i) {
        return new DataHolder[i];
    }
}
