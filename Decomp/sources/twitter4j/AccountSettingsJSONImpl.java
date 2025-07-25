package twitter4j;

import com.tapjoy.TJAdUnitConstants;
import java.io.Serializable;
import twitter4j.conf.Configuration;

class AccountSettingsJSONImpl extends TwitterResponseImpl implements AccountSettings, Serializable {
    private static final long serialVersionUID = 603189815663175766L;
    private final boolean ALWAYS_USE_HTTPS;
    private final boolean DISCOVERABLE_BY_EMAIL;
    private final boolean GEO_ENABLED;
    private final String LANGUAGE;
    private final String SCREEN_NAME;
    private final String SLEEP_END_TIME;
    private final String SLEEP_START_TIME;
    private final boolean SLEEP_TIME_ENABLED;
    private final TimeZone TIMEZONE;
    private final Location[] TREND_LOCATION;

    private AccountSettingsJSONImpl(HttpResponse httpResponse, JSONObject jSONObject) throws TwitterException {
        super(httpResponse);
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("sleep_time");
            this.SLEEP_TIME_ENABLED = ParseUtil.getBoolean(TJAdUnitConstants.String.ENABLED, jSONObject2);
            this.SLEEP_START_TIME = jSONObject2.getString("start_time");
            this.SLEEP_END_TIME = jSONObject2.getString("end_time");
            if (jSONObject.isNull("trend_location")) {
                this.TREND_LOCATION = new Location[0];
            } else {
                JSONArray jSONArray = jSONObject.getJSONArray("trend_location");
                this.TREND_LOCATION = new Location[jSONArray.length()];
                for (int i = 0; i < jSONArray.length(); i++) {
                    this.TREND_LOCATION[i] = new LocationJSONImpl(jSONArray.getJSONObject(i));
                }
            }
            this.GEO_ENABLED = ParseUtil.getBoolean("geo_enabled", jSONObject);
            this.LANGUAGE = jSONObject.getString("language");
            this.ALWAYS_USE_HTTPS = ParseUtil.getBoolean("always_use_https", jSONObject);
            this.DISCOVERABLE_BY_EMAIL = ParseUtil.getBoolean("discoverable_by_email", jSONObject);
            if (jSONObject.isNull("time_zone")) {
                this.TIMEZONE = null;
            } else {
                this.TIMEZONE = new TimeZoneJSONImpl(jSONObject.getJSONObject("time_zone"));
            }
            this.SCREEN_NAME = jSONObject.getString("screen_name");
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    AccountSettingsJSONImpl(HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        this(httpResponse, httpResponse.asJSONObject());
        if (configuration.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, httpResponse.asJSONObject());
        }
    }

    AccountSettingsJSONImpl(JSONObject jSONObject) throws TwitterException {
        this((HttpResponse) null, jSONObject);
    }

    public String getLanguage() {
        return this.LANGUAGE;
    }

    public String getScreenName() {
        return this.SCREEN_NAME;
    }

    public String getSleepEndTime() {
        return this.SLEEP_END_TIME;
    }

    public String getSleepStartTime() {
        return this.SLEEP_START_TIME;
    }

    public TimeZone getTimeZone() {
        return this.TIMEZONE;
    }

    public Location[] getTrendLocations() {
        return this.TREND_LOCATION;
    }

    public boolean isAlwaysUseHttps() {
        return this.ALWAYS_USE_HTTPS;
    }

    public boolean isDiscoverableByEmail() {
        return this.DISCOVERABLE_BY_EMAIL;
    }

    public boolean isGeoEnabled() {
        return this.GEO_ENABLED;
    }

    public boolean isSleepTimeEnabled() {
        return this.SLEEP_TIME_ENABLED;
    }
}
