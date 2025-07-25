package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class c<T extends SafeParcelable> extends DataBuffer<T> {
    private static final String[] nI = {"data"};
    private final Parcelable.Creator<T> nJ;

    public c(DataHolder dataHolder, Parcelable.Creator<T> creator) {
        super(dataHolder);
        this.nJ = creator;
    }

    /* renamed from: B */
    public T get(int i) {
        byte[] byteArray = this.nE.getByteArray("data", i, 0);
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(byteArray, 0, byteArray.length);
        obtain.setDataPosition(0);
        T t = (SafeParcelable) this.nJ.createFromParcel(obtain);
        obtain.recycle();
        return t;
    }
}
