package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.query.internal.LogicalFilter;
import com.google.android.gms.drive.query.internal.Operator;
import java.util.ArrayList;
import java.util.List;

public class Query implements SafeParcelable {
    public static final Parcelable.Creator<Query> CREATOR = new a();
    final int kg;
    LogicalFilter rO;
    String rP;

    public static class Builder {
        private String rP;
        private final List<Filter> rQ = new ArrayList();

        public Builder addFilter(Filter filter) {
            this.rQ.add(filter);
            return this;
        }

        public Query build() {
            return new Query(new LogicalFilter(Operator.si, this.rQ), this.rP);
        }

        public Builder setPageToken(String str) {
            this.rP = str;
            return this;
        }
    }

    Query(int i, LogicalFilter logicalFilter, String str) {
        this.kg = i;
        this.rO = logicalFilter;
        this.rP = str;
    }

    Query(LogicalFilter logicalFilter, String str) {
        this(1, logicalFilter, str);
    }

    public int describeContents() {
        return 0;
    }

    public Filter getFilter() {
        return this.rO;
    }

    public String getPageToken() {
        return this.rP;
    }

    public void writeToParcel(Parcel parcel, int i) {
        a.a(this, parcel, i);
    }
}
