package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class CreateContentsRequest implements SafeParcelable {
    public static final Parcelable.Creator<CreateContentsRequest> CREATOR = new c();
    final int kg;

    public CreateContentsRequest() {
        this(1);
    }

    CreateContentsRequest(int i) {
        this.kg = i;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        c.a(this, parcel, i);
    }
}
