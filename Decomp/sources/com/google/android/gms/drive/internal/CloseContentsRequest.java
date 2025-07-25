package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.Contents;

public class CloseContentsRequest implements SafeParcelable {
    public static final Parcelable.Creator<CloseContentsRequest> CREATOR = new b();
    final int kg;
    final Contents qX;
    final Boolean qY;

    CloseContentsRequest(int i, Contents contents, Boolean bool) {
        this.kg = i;
        this.qX = contents;
        this.qY = bool;
    }

    public CloseContentsRequest(Contents contents, boolean z) {
        this(1, contents, Boolean.valueOf(z));
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        b.a(this, parcel, i);
    }
}
