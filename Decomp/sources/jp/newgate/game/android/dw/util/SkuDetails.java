package jp.newgate.game.android.dw.util;

import com.tapjoy.TapjoyConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class SkuDetails {
    String mDescription;
    String mItemType;
    String mJson;
    String mPrice;
    String mSku;
    String mTitle;
    String mType;

    public SkuDetails(String str) throws JSONException {
        this("inapp", str);
    }

    public SkuDetails(String str, String str2) throws JSONException {
        this.mItemType = str;
        this.mJson = str2;
        JSONObject jSONObject = new JSONObject(this.mJson);
        this.mSku = jSONObject.optString("productId");
        this.mType = jSONObject.optString("type");
        this.mPrice = jSONObject.optString(TapjoyConstants.TJC_EVENT_IAP_PRICE);
        this.mTitle = jSONObject.optString("title");
        this.mDescription = jSONObject.optString("description");
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getPrice() {
        return this.mPrice;
    }

    public String getSku() {
        return this.mSku;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getType() {
        return this.mType;
    }

    public String toString() {
        return "SkuDetails:" + this.mJson;
    }
}
