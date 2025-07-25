package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.query.Query;

public class y implements Parcelable.Creator<QueryRequest> {
    static void a(QueryRequest queryRequest, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, queryRequest.kg);
        b.a(parcel, 2, (Parcelable) queryRequest.rA, i, false);
        b.D(parcel, o);
    }

    /* renamed from: N */
    public QueryRequest createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        Query query = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    query = (Query) a.a(parcel, m, Query.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new QueryRequest(i, query);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: an */
    public QueryRequest[] newArray(int i) {
        return new QueryRequest[i];
    }
}
