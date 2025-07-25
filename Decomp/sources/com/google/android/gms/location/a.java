package com.google.android.gms.location;

import android.app.PendingIntent;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class a implements SafeParcelable {
    public static final b CREATOR = new b();
    private final int kg;
    private final PendingIntent xr;

    public a(int i, PendingIntent pendingIntent) {
        this.kg = i;
        this.xr = pendingIntent;
    }

    public PendingIntent dB() {
        return this.xr;
    }

    public int describeContents() {
        b bVar = CREATOR;
        return 0;
    }

    public int getVersionCode() {
        return this.kg;
    }

    public void writeToParcel(Parcel parcel, int i) {
        b bVar = CREATOR;
        b.a(this, parcel, i);
    }
}
