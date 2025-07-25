package twitter4j;

import java.io.Serializable;

final class TrendJSONImpl implements Trend, Serializable {
    private static final long serialVersionUID = -4353426776065521132L;
    private final String name;
    private String query;
    private String url;

    TrendJSONImpl(JSONObject jSONObject) {
        this(jSONObject, false);
    }

    TrendJSONImpl(JSONObject jSONObject, boolean z) {
        this.url = null;
        this.query = null;
        this.name = ParseUtil.getRawString("name", jSONObject);
        this.url = ParseUtil.getRawString("url", jSONObject);
        this.query = ParseUtil.getRawString("query", jSONObject);
        if (z) {
            TwitterObjectFactory.registerJSONObject(this, jSONObject);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Trend)) {
            return false;
        }
        Trend trend = (Trend) obj;
        if (!this.name.equals(trend.getName())) {
            return false;
        }
        if (this.query == null ? trend.getQuery() != null : !this.query.equals(trend.getQuery())) {
            return false;
        }
        if (this.url != null) {
            if (this.url.equals(trend.getURL())) {
                return true;
            }
        } else if (trend.getURL() == null) {
            return true;
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    public String getQuery() {
        return this.query;
    }

    public String getURL() {
        return this.url;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.name.hashCode() * 31) + (this.url != null ? this.url.hashCode() : 0)) * 31;
        if (this.query != null) {
            i = this.query.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "TrendJSONImpl{name='" + this.name + '\'' + ", url='" + this.url + '\'' + ", query='" + this.query + '\'' + '}';
    }
}
