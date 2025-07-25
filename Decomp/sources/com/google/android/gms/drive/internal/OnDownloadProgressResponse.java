package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class OnDownloadProgressResponse implements SafeParcelable {
    public static final Parcelable.Creator<OnDownloadProgressResponse> CREATOR = new s();
    final int kg;
    final long rx;
    final long ry;

    OnDownloadProgressResponse(int i, long j, long j2) {
        this.kg = i;
        this.rx = j;
        this.ry = j2;
    }

    public long cR() {
        return this.rx;
    }

    public long cS() {
        return this.ry;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        s.a(this, parcel, i);
    }
}
