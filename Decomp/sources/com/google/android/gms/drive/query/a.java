package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.query.internal.LogicalFilter;

public class a implements Parcelable.Creator<Query> {
    static void a(Query query, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1000, query.kg);
        b.a(parcel, 1, (Parcelable) query.rO, i, false);
        b.a(parcel, 3, query.rP, false);
        b.D(parcel, o);
    }

    /* renamed from: Q */
    public Query createFromParcel(Parcel parcel) {
        String m;
        LogicalFilter logicalFilter;
        int i;
        String str = null;
        int n = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
        int i2 = 0;
        LogicalFilter logicalFilter2 = null;
        while (parcel.dataPosition() < n) {
            int m2 = com.google.android.gms.common.internal.safeparcel.a.m(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.M(m2)) {
                case 1:
                    i = i2;
                    LogicalFilter logicalFilter3 = (LogicalFilter) com.google.android.gms.common.internal.safeparcel.a.a(parcel, m2, LogicalFilter.CREATOR);
                    m = str;
                    logicalFilter = logicalFilter3;
                    break;
                case 3:
                    m = com.google.android.gms.common.internal.safeparcel.a.m(parcel, m2);
                    logicalFilter = logicalFilter2;
                    i = i2;
                    break;
                case 1000:
                    String str2 = str;
                    logicalFilter = logicalFilter2;
                    i = com.google.android.gms.common.internal.safeparcel.a.g(parcel, m2);
                    m = str2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, m2);
                    m = str;
                    logicalFilter = logicalFilter2;
                    i = i2;
                    break;
            }
            i2 = i;
            logicalFilter2 = logicalFilter;
            str = m;
        }
        if (parcel.dataPosition() == n) {
            return new Query(i2, logicalFilter2, str);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: aq */
    public Query[] newArray(int i) {
        return new Query[i];
    }
}
