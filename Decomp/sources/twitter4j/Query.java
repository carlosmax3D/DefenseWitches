package twitter4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Query implements Serializable {
    public static final Unit KILOMETERS = Unit.km;
    public static final Unit MILES = Unit.mi;
    public static final ResultType MIXED = ResultType.mixed;
    public static final ResultType POPULAR = ResultType.popular;
    public static final ResultType RECENT = ResultType.recent;
    private static final HttpParameter WITH_TWITTER_USER_ID = new HttpParameter("with_twitter_user_id", "true");
    private static final long serialVersionUID = 7196404519192910019L;
    private int count = -1;
    private String geocode = null;
    private String lang = null;
    private String locale = null;
    private long maxId = -1;
    private String nextPageQuery = null;
    private String query = null;
    private ResultType resultType = null;
    private String since = null;
    private long sinceId = -1;
    private String until = null;

    public enum ResultType {
        popular,
        mixed,
        recent
    }

    public enum Unit {
        mi,
        km
    }

    public Query() {
    }

    public Query(String str) {
        this.query = str;
    }

    private void appendParameter(String str, long j, List<HttpParameter> list) {
        if (0 <= j) {
            list.add(new HttpParameter(str, String.valueOf(j)));
        }
    }

    private void appendParameter(String str, String str2, List<HttpParameter> list) {
        if (str2 != null) {
            list.add(new HttpParameter(str, str2));
        }
    }

    static Query createWithNextPageQuery(String str) {
        Query query2 = new Query();
        query2.nextPageQuery = str;
        return query2;
    }

    /* access modifiers changed from: package-private */
    public HttpParameter[] asHttpParameterArray() {
        ArrayList arrayList = new ArrayList(12);
        appendParameter("q", this.query, (List<HttpParameter>) arrayList);
        appendParameter("lang", this.lang, (List<HttpParameter>) arrayList);
        appendParameter("locale", this.locale, (List<HttpParameter>) arrayList);
        appendParameter("max_id", this.maxId, (List<HttpParameter>) arrayList);
        appendParameter("count", (long) this.count, (List<HttpParameter>) arrayList);
        appendParameter("since", this.since, (List<HttpParameter>) arrayList);
        appendParameter("since_id", this.sinceId, (List<HttpParameter>) arrayList);
        appendParameter("geocode", this.geocode, (List<HttpParameter>) arrayList);
        appendParameter("until", this.until, (List<HttpParameter>) arrayList);
        if (this.resultType != null) {
            arrayList.add(new HttpParameter("result_type", this.resultType.name()));
        }
        arrayList.add(WITH_TWITTER_USER_ID);
        return (HttpParameter[]) arrayList.toArray(new HttpParameter[arrayList.size()]);
    }

    public Query count(int i) {
        setCount(i);
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Query query2 = (Query) obj;
        if (this.maxId != query2.maxId) {
            return false;
        }
        if (this.count != query2.count) {
            return false;
        }
        if (this.sinceId != query2.sinceId) {
            return false;
        }
        if (this.geocode == null ? query2.geocode != null : !this.geocode.equals(query2.geocode)) {
            return false;
        }
        if (this.lang == null ? query2.lang != null : !this.lang.equals(query2.lang)) {
            return false;
        }
        if (this.locale == null ? query2.locale != null : !this.locale.equals(query2.locale)) {
            return false;
        }
        if (this.nextPageQuery == null ? query2.nextPageQuery != null : !this.nextPageQuery.equals(query2.nextPageQuery)) {
            return false;
        }
        if (this.query == null ? query2.query != null : !this.query.equals(query2.query)) {
            return false;
        }
        if (this.resultType == null ? query2.resultType != null : !this.resultType.equals(query2.resultType)) {
            return false;
        }
        if (this.since == null ? query2.since != null : !this.since.equals(query2.since)) {
            return false;
        }
        if (this.until != null) {
            if (this.until.equals(query2.until)) {
                return true;
            }
        } else if (query2.until == null) {
            return true;
        }
        return false;
    }

    public Query geoCode(GeoLocation geoLocation, double d, String str) {
        setGeoCode(geoLocation, d, str);
        return this;
    }

    public int getCount() {
        return this.count;
    }

    public String getGeocode() {
        return this.geocode;
    }

    public String getLang() {
        return this.lang;
    }

    public String getLocale() {
        return this.locale;
    }

    public long getMaxId() {
        return this.maxId;
    }

    public String getQuery() {
        return this.query;
    }

    public ResultType getResultType() {
        return this.resultType;
    }

    public String getSince() {
        return this.since;
    }

    public long getSinceId() {
        return this.sinceId;
    }

    public String getUntil() {
        return this.until;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (((((((((((((((((((this.query != null ? this.query.hashCode() : 0) * 31) + (this.lang != null ? this.lang.hashCode() : 0)) * 31) + (this.locale != null ? this.locale.hashCode() : 0)) * 31) + ((int) (this.maxId ^ (this.maxId >>> 32)))) * 31) + this.count) * 31) + (this.since != null ? this.since.hashCode() : 0)) * 31) + ((int) (this.sinceId ^ (this.sinceId >>> 32)))) * 31) + (this.geocode != null ? this.geocode.hashCode() : 0)) * 31) + (this.until != null ? this.until.hashCode() : 0)) * 31) + (this.resultType != null ? this.resultType.hashCode() : 0)) * 31;
        if (this.nextPageQuery != null) {
            i = this.nextPageQuery.hashCode();
        }
        return hashCode + i;
    }

    public Query lang(String str) {
        setLang(str);
        return this;
    }

    public Query locale(String str) {
        setLocale(str);
        return this;
    }

    public Query maxId(long j) {
        setMaxId(j);
        return this;
    }

    /* access modifiers changed from: package-private */
    public String nextPage() {
        return this.nextPageQuery;
    }

    public Query query(String str) {
        setQuery(str);
        return this;
    }

    public Query resultType(ResultType resultType2) {
        setResultType(resultType2);
        return this;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public void setGeoCode(GeoLocation geoLocation, double d, String str) {
        this.geocode = geoLocation.getLatitude() + "," + geoLocation.getLongitude() + "," + d + str;
    }

    public void setGeoCode(GeoLocation geoLocation, double d, Unit unit) {
        this.geocode = geoLocation.getLatitude() + "," + geoLocation.getLongitude() + "," + d + unit.name();
    }

    public void setLang(String str) {
        this.lang = str;
    }

    public void setLocale(String str) {
        this.locale = str;
    }

    public void setMaxId(long j) {
        this.maxId = j;
    }

    public void setQuery(String str) {
        this.query = str;
    }

    public void setResultType(ResultType resultType2) {
        this.resultType = resultType2;
    }

    public void setSince(String str) {
        this.since = str;
    }

    public void setSinceId(long j) {
        this.sinceId = j;
    }

    public void setUntil(String str) {
        this.until = str;
    }

    public Query since(String str) {
        setSince(str);
        return this;
    }

    public Query sinceId(long j) {
        setSinceId(j);
        return this;
    }

    public String toString() {
        return "Query{query='" + this.query + '\'' + ", lang='" + this.lang + '\'' + ", locale='" + this.locale + '\'' + ", maxId=" + this.maxId + ", count=" + this.count + ", since='" + this.since + '\'' + ", sinceId=" + this.sinceId + ", geocode='" + this.geocode + '\'' + ", until='" + this.until + '\'' + ", resultType='" + this.resultType + '\'' + ", nextPageQuery='" + this.nextPageQuery + '\'' + '}';
    }

    public Query until(String str) {
        setUntil(str);
        return this;
    }
}
