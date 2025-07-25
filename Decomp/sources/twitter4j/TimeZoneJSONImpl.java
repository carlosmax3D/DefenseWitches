package twitter4j;

public class TimeZoneJSONImpl implements TimeZone {
    private static final long serialVersionUID = 81958969762484144L;
    private final String NAME;
    private final String TZINFO_NAME;
    private final int UTC_OFFSET;

    TimeZoneJSONImpl(JSONObject jSONObject) throws TwitterException {
        try {
            this.UTC_OFFSET = ParseUtil.getInt("utc_offset", jSONObject);
            this.NAME = jSONObject.getString("name");
            this.TZINFO_NAME = jSONObject.getString("tzinfo_name");
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    public String getName() {
        return this.NAME;
    }

    public String tzinfoName() {
        return this.TZINFO_NAME;
    }

    public int utcOffset() {
        return this.UTC_OFFSET;
    }
}
