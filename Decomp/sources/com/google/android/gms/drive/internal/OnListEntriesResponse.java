package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class OnListEntriesResponse implements SafeParcelable {
    public static final Parcelable.Creator<OnListEntriesResponse> CREATOR = new u();
    final int kg;
    final DataHolder rz;

    OnListEntriesResponse(int i, DataHolder dataHolder) {
        this.kg = i;
        this.rz = dataHolder;
    }

    public DataHolder cT() {
        return this.rz;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        u.a(this, parcel, i);
    }
}
