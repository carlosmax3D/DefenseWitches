package com.google.android.gms.internal;

import android.os.Parcel;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class cu implements SafeParcelable {
    public static final cv CREATOR = new cv();
    public String iJ;
    public int iK;
    public int iL;
    public boolean iM;
    public final int versionCode;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public cu(int i, int i2, boolean z) {
        this(1, "afma-sdk-a-v" + i + "." + i2 + "." + (z ? AppEventsConstants.EVENT_PARAM_VALUE_NO : AppEventsConstants.EVENT_PARAM_VALUE_YES), i, i2, z);
    }

    cu(int i, String str, int i2, int i3, boolean z) {
        this.versionCode = i;
        this.iJ = str;
        this.iK = i2;
        this.iL = i3;
        this.iM = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        cv.a(this, parcel, i);
    }
}
