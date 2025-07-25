package plugin.google.iap.v3.util;

import com.tapjoy.TJAdUnitConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class Purchase {
    String mDeveloperPayload;
    String mItemType;
    String mOrderId;
    String mOriginalJson;
    String mPackageName;
    State mPurchaseState;
    long mPurchaseTime;
    String mSignature;
    String mSku;
    String mToken;

    public enum State {
        Purchased,
        Cancelled,
        Refunded,
        Consumed,
        Unknown
    }

    public Purchase(String str, String str2, String str3) throws JSONException {
        this.mItemType = str;
        this.mOriginalJson = str2;
        JSONObject jSONObject = new JSONObject(this.mOriginalJson);
        this.mOrderId = jSONObject.optString("orderId");
        this.mPackageName = jSONObject.optString("packageName");
        this.mSku = jSONObject.optString("productId");
        this.mPurchaseTime = jSONObject.optLong("purchaseTime");
        this.mDeveloperPayload = jSONObject.optString("developerPayload");
        this.mToken = jSONObject.optString(TJAdUnitConstants.String.EVENT_TOKEN, jSONObject.optString("purchaseToken"));
        this.mSignature = str3;
        switch (jSONObject.optInt("purchaseState")) {
            case 0:
                this.mPurchaseState = State.Purchased;
                return;
            case 1:
                this.mPurchaseState = State.Cancelled;
                return;
            case 2:
                this.mPurchaseState = State.Refunded;
                return;
            default:
                this.mPurchaseState = State.Unknown;
                return;
        }
    }

    public String getDeveloperPayload() {
        return this.mDeveloperPayload;
    }

    public String getItemType() {
        return this.mItemType;
    }

    public String getOrderId() {
        return this.mOrderId;
    }

    public String getOriginalJson() {
        return this.mOriginalJson;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public State getPurchaseState() {
        return this.mPurchaseState;
    }

    public long getPurchaseTime() {
        return this.mPurchaseTime;
    }

    public String getSignature() {
        return this.mSignature;
    }

    public String getSku() {
        return this.mSku;
    }

    public String getToken() {
        return this.mToken;
    }

    public void setPurchaseState(State state) {
        this.mPurchaseState = state;
    }

    public String toString() {
        return "PurchaseInfo(type:" + this.mItemType + "):" + this.mOriginalJson;
    }
}
