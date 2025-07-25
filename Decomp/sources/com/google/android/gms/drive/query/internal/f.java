package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.ArrayList;
import java.util.List;

public class f implements Parcelable.Creator<LogicalFilter> {
    static void a(LogicalFilter logicalFilter, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1000, logicalFilter.kg);
        b.a(parcel, 1, (Parcelable) logicalFilter.rR, i, false);
        b.b(parcel, 2, logicalFilter.sb, false);
        b.D(parcel, o);
    }

    /* renamed from: V */
    public LogicalFilter createFromParcel(Parcel parcel) {
        ArrayList<FilterHolder> c;
        Operator operator;
        int i;
        ArrayList<FilterHolder> arrayList = null;
        int n = a.n(parcel);
        int i2 = 0;
        Operator operator2 = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = i2;
                    Operator operator3 = (Operator) a.a(parcel, m, Operator.CREATOR);
                    c = arrayList;
                    operator = operator3;
                    break;
                case 2:
                    c = a.c(parcel, m, FilterHolder.CREATOR);
                    operator = operator2;
                    i = i2;
                    break;
                case 1000:
                    ArrayList<FilterHolder> arrayList2 = arrayList;
                    operator = operator2;
                    i = a.g(parcel, m);
                    c = arrayList2;
                    break;
                default:
                    a.b(parcel, m);
                    c = arrayList;
                    operator = operator2;
                    i = i2;
                    break;
            }
            i2 = i;
            operator2 = operator;
            arrayList = c;
        }
        if (parcel.dataPosition() == n) {
            return new LogicalFilter(i2, operator2, (List<FilterHolder>) arrayList);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: av */
    public LogicalFilter[] newArray(int i) {
        return new LogicalFilter[i];
    }
}
