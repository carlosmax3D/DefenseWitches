package plugin.google.iap.v3.util;

import com.naef.jnlua.LuaState;
import com.tapjoy.TapjoyConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class SkuDetails {
    String mDescription;
    String mItemType;
    String mJson;
    String mPrice;
    String mPriceAmountMicros;
    String mPriceCurrencyCode;
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
        this.mPriceCurrencyCode = jSONObject.optString("price_currency_code");
        this.mPriceAmountMicros = jSONObject.optString("price_amount_micros");
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getPrice() {
        return this.mPrice;
    }

    public String getPriceAmountMicros() {
        return this.mPriceAmountMicros;
    }

    public String getPriceCurrencyCode() {
        return this.mPriceCurrencyCode;
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

    public void pushToLua(LuaState luaState, int i) {
        luaState.pushString(this.mTitle);
        luaState.setField(i, "title");
        luaState.pushString(this.mDescription);
        luaState.setField(i, "description");
        luaState.pushString(this.mPrice);
        luaState.setField(i, "localizedPrice");
        luaState.pushString(this.mSku);
        luaState.setField(i, "productIdentifier");
        luaState.pushString(this.mType);
        luaState.setField(i, "type");
        luaState.pushString(this.mPriceAmountMicros);
        luaState.setField(i, "priceAmountMicros");
        luaState.pushString(this.mPriceCurrencyCode);
        luaState.setField(i, "priceCurrencyCode");
        luaState.pushString(this.mJson);
        luaState.setField(i, "originalJson");
    }

    public String toString() {
        return "SkuDetails:" + this.mJson;
    }
}
