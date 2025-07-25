package twitter4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class FilterQuery implements Serializable {
    private static final long serialVersionUID = -142808200594318258L;
    private int count;
    private long[] follow;
    private String[] language;
    private double[][] locations;
    private String[] track;

    public FilterQuery() {
        this.count = 0;
        this.follow = null;
        this.track = null;
        this.locations = null;
        this.language = null;
    }

    public FilterQuery(int i, long[] jArr) {
        this();
        this.count = i;
        this.follow = jArr;
    }

    public FilterQuery(int i, long[] jArr, String[] strArr) {
        this();
        this.count = i;
        this.follow = jArr;
        this.track = strArr;
    }

    public FilterQuery(int i, long[] jArr, String[] strArr, double[][] dArr) {
        this.count = i;
        this.follow = jArr;
        this.track = strArr;
        this.locations = dArr;
    }

    public FilterQuery(int i, long[] jArr, String[] strArr, double[][] dArr, String[] strArr2) {
        this.count = i;
        this.follow = jArr;
        this.track = strArr;
        this.language = strArr2;
    }

    public FilterQuery(long[] jArr) {
        this();
        this.count = 0;
        this.follow = jArr;
    }

    private String toLocationsString(double[][] dArr) {
        StringBuilder sb = new StringBuilder(dArr.length * 20 * 2);
        for (double[] dArr2 : dArr) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(dArr2[0]);
            sb.append(",");
            sb.append(dArr2[1]);
        }
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public HttpParameter[] asHttpParameterArray(HttpParameter httpParameter) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new HttpParameter("count", this.count));
        if (this.follow != null && this.follow.length > 0) {
            arrayList.add(new HttpParameter("follow", StringUtil.join(this.follow)));
        }
        if (this.track != null && this.track.length > 0) {
            arrayList.add(new HttpParameter("track", StringUtil.join(this.track)));
        }
        if (this.locations != null && this.locations.length > 0) {
            arrayList.add(new HttpParameter("locations", toLocationsString(this.locations)));
        }
        if (this.language != null && this.language.length > 0) {
            arrayList.add(new HttpParameter("language", StringUtil.join(this.language)));
        }
        arrayList.add(httpParameter);
        return (HttpParameter[]) arrayList.toArray(new HttpParameter[arrayList.size()]);
    }

    public FilterQuery count(int i) {
        this.count = i;
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FilterQuery filterQuery = (FilterQuery) obj;
        if (this.count != filterQuery.count) {
            return false;
        }
        if (!Arrays.equals(this.follow, filterQuery.follow)) {
            return false;
        }
        if (!Arrays.equals(this.track, filterQuery.track)) {
            return false;
        }
        return Arrays.equals(this.language, filterQuery.language);
    }

    public FilterQuery follow(long[] jArr) {
        this.follow = jArr;
        return this;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((this.count * 31) + (this.follow != null ? Arrays.hashCode(this.follow) : 0)) * 31) + (this.track != null ? Arrays.hashCode(this.track) : 0)) * 31;
        if (this.language != null) {
            i = Arrays.hashCode(this.language);
        }
        return hashCode + i;
    }

    public FilterQuery language(String[] strArr) {
        this.language = strArr;
        return this;
    }

    public FilterQuery locations(double[][] dArr) {
        this.locations = dArr;
        return this;
    }

    public String toString() {
        List list = null;
        StringBuilder append = new StringBuilder().append("FilterQuery{count=").append(this.count).append(", follow=").append(Arrays.toString(this.follow)).append(", track=").append(this.track == null ? null : Arrays.asList(this.track)).append(", locations=").append(this.locations == null ? null : Arrays.asList(this.locations)).append(", language=");
        if (this.language != null) {
            list = Arrays.asList(this.language);
        }
        return append.append(list).append('}').toString();
    }

    public FilterQuery track(String[] strArr) {
        this.track = strArr;
        return this;
    }
}
