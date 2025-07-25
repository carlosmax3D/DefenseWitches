package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.query.Filter;
import java.util.ArrayList;
import java.util.List;

public class LogicalFilter implements SafeParcelable, Filter {
    public static final Parcelable.Creator<LogicalFilter> CREATOR = new f();
    final int kg;
    private List<Filter> rQ;
    final Operator rR;
    final List<FilterHolder> sb;

    LogicalFilter(int i, Operator operator, List<FilterHolder> list) {
        this.kg = i;
        this.rR = operator;
        this.sb = list;
    }

    public LogicalFilter(Operator operator, Filter filter, Filter... filterArr) {
        this.kg = 1;
        this.rR = operator;
        this.sb = new ArrayList(filterArr.length + 1);
        this.sb.add(new FilterHolder(filter));
        this.rQ = new ArrayList(filterArr.length + 1);
        this.rQ.add(filter);
        for (Filter filter2 : filterArr) {
            this.sb.add(new FilterHolder(filter2));
            this.rQ.add(filter2);
        }
    }

    public LogicalFilter(Operator operator, List<Filter> list) {
        this.kg = 1;
        this.rR = operator;
        this.rQ = list;
        this.sb = new ArrayList(list.size());
        for (Filter filterHolder : list) {
            this.sb.add(new FilterHolder(filterHolder));
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        f.a(this, parcel, i);
    }
}
