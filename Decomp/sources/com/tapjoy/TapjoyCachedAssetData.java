package com.tapjoy;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class TapjoyCachedAssetData implements Serializable {
    private static final String TAG = "TapjoyCachedAssetData";
    private static final long serialVersionUID = 1;
    private String assetURL;
    private String localFilePath;
    private String localURL;
    private String mimeType;
    private String offerId;
    private long timeOfDeathInSeconds;
    private long timeToLiveInSeconds;
    private long timestampInSeconds;

    public TapjoyCachedAssetData(String str, String str2, long j) {
        this(str, str2, j, System.currentTimeMillis() / 1000);
    }

    public TapjoyCachedAssetData(String str, String str2, long j, long j2) {
        setAssetURL(str);
        setLocalFilePath(str2);
        this.timeToLiveInSeconds = j;
        this.timestampInSeconds = j2;
        this.timeOfDeathInSeconds = j2 + j;
    }

    public static TapjoyCachedAssetData fromJSONObject(JSONObject jSONObject) {
        TapjoyCachedAssetData tapjoyCachedAssetData;
        try {
            tapjoyCachedAssetData = new TapjoyCachedAssetData(jSONObject.getString("assetURL"), jSONObject.getString("localFilePath"), jSONObject.getLong(TapjoyConstants.TJC_TIME_TO_LIVE), jSONObject.getLong("timestamp"));
            try {
                tapjoyCachedAssetData.setOfferID(jSONObject.optString("offerID"));
            } catch (JSONException e) {
                TapjoyLog.i(TAG, "Can not build TapjoyVideoObject -- not enough data.");
                return tapjoyCachedAssetData;
            }
        } catch (JSONException e2) {
            tapjoyCachedAssetData = null;
            TapjoyLog.i(TAG, "Can not build TapjoyVideoObject -- not enough data.");
            return tapjoyCachedAssetData;
        }
        return tapjoyCachedAssetData;
    }

    public static TapjoyCachedAssetData fromRawJSONString(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            try {
                JSONObject jSONObject2 = jSONObject;
                return fromJSONObject(jSONObject);
            } catch (JSONException e) {
                JSONObject jSONObject3 = jSONObject;
                TapjoyLog.i(TAG, "Can not build TapjoyVideoObject -- error reading json string");
                return null;
            }
        } catch (JSONException e2) {
            TapjoyLog.i(TAG, "Can not build TapjoyVideoObject -- error reading json string");
            return null;
        }
    }

    public String getAssetURL() {
        return this.assetURL;
    }

    public String getLocalFilePath() {
        return this.localFilePath;
    }

    public String getLocalURL() {
        return this.localURL;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public String getOfferId() {
        return this.offerId;
    }

    public long getTimeOfDeathInSeconds() {
        return this.timeOfDeathInSeconds;
    }

    public long getTimeToLiveInSeconds() {
        return this.timeToLiveInSeconds;
    }

    public long getTimestampInSeconds() {
        return this.timestampInSeconds;
    }

    public void resetTimeToLive(long j) {
        this.timeToLiveInSeconds = j;
        this.timeOfDeathInSeconds = (System.currentTimeMillis() / 1000) + j;
    }

    public void setAssetURL(String str) {
        this.assetURL = str;
        this.mimeType = TapjoyUtil.determineMimeType(str);
    }

    public void setLocalFilePath(String str) {
        this.localFilePath = str;
        this.localURL = "file://" + str;
    }

    public void setOfferID(String str) {
        this.offerId = str;
    }

    public JSONObject toJSON() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("timestamp", getTimestampInSeconds());
            jSONObject.put(TapjoyConstants.TJC_TIME_TO_LIVE, getTimeToLiveInSeconds());
            jSONObject.put("assetURL", getAssetURL());
            jSONObject.put("localFilePath", getLocalFilePath());
            jSONObject.put("offerID", getOfferId());
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    public String toRawJSONString() {
        return toJSON().toString();
    }
}
