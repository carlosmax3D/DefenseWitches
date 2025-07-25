package com.google.android.gms.internal;

import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class at {
    public final String adJson;
    public final String fD;
    public final List<String> fE;
    public final String fF;
    public final List<String> fG;
    public final String fH;

    public at(JSONObject jSONObject) throws JSONException {
        String str = null;
        this.fD = jSONObject.getString(ShareConstants.WEB_DIALOG_PARAM_ID);
        JSONArray jSONArray = jSONObject.getJSONArray("adapters");
        ArrayList arrayList = new ArrayList(jSONArray.length());
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(jSONArray.getString(i));
        }
        this.fE = Collections.unmodifiableList(arrayList);
        this.fF = jSONObject.optString("allocation_id", (String) null);
        this.fG = az.a(jSONObject, "imp_urls");
        JSONObject optJSONObject = jSONObject.optJSONObject("ad");
        this.adJson = optJSONObject != null ? optJSONObject.toString() : null;
        JSONObject optJSONObject2 = jSONObject.optJSONObject("data");
        this.fH = optJSONObject2 != null ? optJSONObject2.toString() : str;
    }
}
