package twitter4j;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import twitter4j.conf.Configuration;

final class TrendsJSONImpl extends TwitterResponseImpl implements Trends, Serializable {
    private static final long serialVersionUID = 2054973282133379835L;
    private Date asOf;
    private Location location;
    private Date trendAt;
    private Trend[] trends;

    TrendsJSONImpl(String str) throws TwitterException {
        this(str, false);
    }

    TrendsJSONImpl(String str, boolean z) throws TwitterException {
        init(str, z);
    }

    TrendsJSONImpl(Date date, Location location2, Date date2, Trend[] trendArr) {
        this.asOf = date;
        this.location = location2;
        this.trendAt = date2;
        this.trends = trendArr;
    }

    TrendsJSONImpl(HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        super(httpResponse);
        init(httpResponse.asString(), configuration.isJSONStoreEnabled());
        if (configuration.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, httpResponse.asString());
        }
    }

    static ResponseList<Trends> createTrendsList(HttpResponse httpResponse, boolean z) throws TwitterException {
        JSONObject asJSONObject = httpResponse.asJSONObject();
        try {
            Date parseTrendsDate = ParseUtil.parseTrendsDate(asJSONObject.getString("as_of"));
            JSONObject jSONObject = asJSONObject.getJSONObject("trends");
            Location extractLocation = extractLocation(asJSONObject, z);
            ResponseListImpl responseListImpl = new ResponseListImpl(jSONObject.length(), httpResponse);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                Trend[] jsonArrayToTrendArray = jsonArrayToTrendArray(jSONObject.getJSONArray(str), z);
                if (str.length() == 19) {
                    responseListImpl.add(new TrendsJSONImpl(parseTrendsDate, extractLocation, ParseUtil.getDate(str, "yyyy-MM-dd HH:mm:ss"), jsonArrayToTrendArray));
                } else if (str.length() == 16) {
                    responseListImpl.add(new TrendsJSONImpl(parseTrendsDate, extractLocation, ParseUtil.getDate(str, "yyyy-MM-dd HH:mm"), jsonArrayToTrendArray));
                } else if (str.length() == 10) {
                    responseListImpl.add(new TrendsJSONImpl(parseTrendsDate, extractLocation, ParseUtil.getDate(str, "yyyy-MM-dd"), jsonArrayToTrendArray));
                }
            }
            Collections.sort(responseListImpl);
            return responseListImpl;
        } catch (JSONException e) {
            throw new TwitterException(e.getMessage() + ":" + httpResponse.asString(), (Throwable) e);
        }
    }

    private static Location extractLocation(JSONObject jSONObject, boolean z) throws TwitterException {
        if (jSONObject.isNull("locations")) {
            return null;
        }
        try {
            ResponseList<Location> createLocationList = LocationJSONImpl.createLocationList(jSONObject.getJSONArray("locations"), z);
            if (createLocationList.size() != 0) {
                return (Location) createLocationList.get(0);
            }
            return null;
        } catch (JSONException e) {
            throw new AssertionError("locations can't be null");
        }
    }

    private static Trend[] jsonArrayToTrendArray(JSONArray jSONArray, boolean z) throws JSONException {
        Trend[] trendArr = new Trend[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            trendArr[i] = new TrendJSONImpl(jSONArray.getJSONObject(i), z);
        }
        return trendArr;
    }

    public int compareTo(Trends trends2) {
        return this.trendAt.compareTo(trends2.getTrendAt());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Trends)) {
            return false;
        }
        Trends trends2 = (Trends) obj;
        if (this.asOf == null ? trends2.getAsOf() != null : !this.asOf.equals(trends2.getAsOf())) {
            return false;
        }
        if (this.trendAt == null ? trends2.getTrendAt() != null : !this.trendAt.equals(trends2.getTrendAt())) {
            return false;
        }
        return Arrays.equals(this.trends, trends2.getTrends());
    }

    public Date getAsOf() {
        return this.asOf;
    }

    public Location getLocation() {
        return this.location;
    }

    public Date getTrendAt() {
        return this.trendAt;
    }

    public Trend[] getTrends() {
        return this.trends;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (((this.asOf != null ? this.asOf.hashCode() : 0) * 31) + (this.trendAt != null ? this.trendAt.hashCode() : 0)) * 31;
        if (this.trends != null) {
            i = Arrays.hashCode(this.trends);
        }
        return hashCode + i;
    }

    /* access modifiers changed from: package-private */
    public void init(String str, boolean z) throws TwitterException {
        JSONObject jSONObject;
        try {
            if (str.startsWith("[")) {
                JSONArray jSONArray = new JSONArray(str);
                if (jSONArray.length() > 0) {
                    jSONObject = jSONArray.getJSONObject(0);
                } else {
                    throw new TwitterException("No trends found on the specified woeid");
                }
            } else {
                jSONObject = new JSONObject(str);
            }
            this.asOf = ParseUtil.parseTrendsDate(jSONObject.getString("as_of"));
            this.location = extractLocation(jSONObject, z);
            JSONArray jSONArray2 = jSONObject.getJSONArray("trends");
            this.trendAt = this.asOf;
            this.trends = jsonArrayToTrendArray(jSONArray2, z);
        } catch (JSONException e) {
            throw new TwitterException(e.getMessage(), (Throwable) e);
        }
    }

    public String toString() {
        return "TrendsJSONImpl{asOf=" + this.asOf + ", trendAt=" + this.trendAt + ", trends=" + (this.trends == null ? null : Arrays.asList(this.trends)) + '}';
    }
}
