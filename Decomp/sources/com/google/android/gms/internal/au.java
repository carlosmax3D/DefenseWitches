package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class au {
    public final List<at> fI;
    public final long fJ;
    public final List<String> fK;
    public final List<String> fL;
    public final List<String> fM;
    public final String fN;
    public final long fO;

    public au(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        if (ct.n(2)) {
            ct.u("Mediation Response JSON: " + jSONObject.toString(2));
        }
        JSONArray jSONArray = jSONObject.getJSONArray("ad_networks");
        ArrayList arrayList = new ArrayList(jSONArray.length());
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(new at(jSONArray.getJSONObject(i)));
        }
        this.fI = Collections.unmodifiableList(arrayList);
        this.fN = jSONObject.getString("qdata");
        JSONObject optJSONObject = jSONObject.optJSONObject("settings");
        if (optJSONObject != null) {
            this.fJ = optJSONObject.optLong("ad_network_timeout_millis", -1);
            this.fK = az.a(optJSONObject, "click_urls");
            this.fL = az.a(optJSONObject, "imp_urls");
            this.fM = az.a(optJSONObject, "nofill_urls");
            long optLong = optJSONObject.optLong("refresh", -1);
            this.fO = optLong > 0 ? optLong * 1000 : -1;
            return;
        }
        this.fJ = -1;
        this.fK = null;
        this.fL = null;
        this.fM = null;
        this.fO = -1;
    }
}
