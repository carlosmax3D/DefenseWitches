package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.Contents;

public class OnContentsResponse implements SafeParcelable {
    public static final Parcelable.Creator<OnContentsResponse> CREATOR = new r();
    final int kg;
    final Contents qK;

    OnContentsResponse(int i, Contents contents) {
        this.kg = i;
        this.qK = contents;
    }

    public Contents cQ() {
        return this.qK;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        r.a(this, parcel, i);
    }
}
