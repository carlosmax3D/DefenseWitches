package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.query.Filter;

public class NotFilter implements SafeParcelable, Filter {
    public static final Parcelable.Creator<NotFilter> CREATOR = new g();
    final int kg;
    final FilterHolder sc;

    NotFilter(int i, FilterHolder filterHolder) {
        this.kg = i;
        this.sc = filterHolder;
    }

    public NotFilter(Filter filter) {
        this(1, new FilterHolder(filter));
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        g.a(this, parcel, i);
    }
}
