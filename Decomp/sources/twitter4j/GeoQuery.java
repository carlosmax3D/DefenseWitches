package twitter4j;

import com.tapjoy.TJAdUnitConstants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class GeoQuery implements Serializable {
    private static final long serialVersionUID = 5434503339001056634L;
    private String accuracy = null;
    private String granularity = null;
    private String ip = null;
    private GeoLocation location;
    private int maxResults = -1;
    private String query = null;

    public GeoQuery(String str) {
        this.ip = str;
    }

    public GeoQuery(GeoLocation geoLocation) {
        this.location = geoLocation;
    }

    private void appendParameter(String str, double d, List<HttpParameter> list) {
        list.add(new HttpParameter(str, String.valueOf(d)));
    }

    private void appendParameter(String str, int i, List<HttpParameter> list) {
        if (i > 0) {
            list.add(new HttpParameter(str, String.valueOf(i)));
        }
    }

    private void appendParameter(String str, String str2, List<HttpParameter> list) {
        if (str2 != null) {
            list.add(new HttpParameter(str, str2));
        }
    }

    public GeoQuery accuracy(String str) {
        setAccuracy(str);
        return this;
    }

    /* access modifiers changed from: package-private */
    public HttpParameter[] asHttpParameterArray() {
        ArrayList arrayList = new ArrayList();
        if (this.location != null) {
            appendParameter(TJAdUnitConstants.String.LAT, this.location.getLatitude(), (List<HttpParameter>) arrayList);
            appendParameter(TJAdUnitConstants.String.LONG, this.location.getLongitude(), (List<HttpParameter>) arrayList);
        }
        if (this.ip != null) {
            appendParameter("ip", this.ip, (List<HttpParameter>) arrayList);
        }
        appendParameter("accuracy", this.accuracy, (List<HttpParameter>) arrayList);
        appendParameter("query", this.query, (List<HttpParameter>) arrayList);
        appendParameter("granularity", this.granularity, (List<HttpParameter>) arrayList);
        appendParameter("max_results", this.maxResults, (List<HttpParameter>) arrayList);
        return (HttpParameter[]) arrayList.toArray(new HttpParameter[arrayList.size()]);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GeoQuery geoQuery = (GeoQuery) obj;
        if (this.maxResults != geoQuery.maxResults) {
            return false;
        }
        if (this.accuracy == null ? geoQuery.accuracy != null : !this.accuracy.equals(geoQuery.accuracy)) {
            return false;
        }
        if (this.granularity == null ? geoQuery.granularity != null : !this.granularity.equals(geoQuery.granularity)) {
            return false;
        }
        if (this.ip == null ? geoQuery.ip != null : !this.ip.equals(geoQuery.ip)) {
            return false;
        }
        if (this.location != null) {
            if (this.location.equals(geoQuery.location)) {
                return true;
            }
        } else if (geoQuery.location == null) {
            return true;
        }
        return false;
    }

    public String getAccuracy() {
        return this.accuracy;
    }

    public String getGranularity() {
        return this.granularity;
    }

    public String getIp() {
        return this.ip;
    }

    public GeoLocation getLocation() {
        return this.location;
    }

    public int getMaxResults() {
        return this.maxResults;
    }

    public String getQuery() {
        return this.query;
    }

    public GeoQuery granularity(String str) {
        setGranularity(str);
        return this;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (((((this.location != null ? this.location.hashCode() : 0) * 31) + (this.ip != null ? this.ip.hashCode() : 0)) * 31) + (this.accuracy != null ? this.accuracy.hashCode() : 0)) * 31;
        if (this.granularity != null) {
            i = this.granularity.hashCode();
        }
        return ((hashCode + i) * 31) + this.maxResults;
    }

    public GeoQuery maxResults(int i) {
        setMaxResults(i);
        return this;
    }

    public void setAccuracy(String str) {
        this.accuracy = str;
    }

    public void setGranularity(String str) {
        this.granularity = str;
    }

    public void setMaxResults(int i) {
        this.maxResults = i;
    }

    public void setQuery(String str) {
        this.query = str;
    }

    public String toString() {
        return "GeoQuery{location=" + this.location + ", query='" + this.query + '\'' + ", ip='" + this.ip + '\'' + ", accuracy='" + this.accuracy + '\'' + ", granularity='" + this.granularity + '\'' + ", maxResults=" + this.maxResults + '}';
    }
}
