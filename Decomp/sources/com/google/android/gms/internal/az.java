package com.google.android.gms.internal;

import android.content.Context;
import com.facebook.appevents.AppEventsConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class az {
    public static List<String> a(JSONObject jSONObject, String str) throws JSONException {
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        if (optJSONArray == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(optJSONArray.length());
        for (int i = 0; i < optJSONArray.length(); i++) {
            arrayList.add(optJSONArray.getString(i));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static void a(Context context, String str, cj cjVar, String str2, boolean z, List<String> list) {
        String str3 = z ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO;
        for (String replaceAll : list) {
            String replaceAll2 = replaceAll.replaceAll("@gw_adlocid@", str2).replaceAll("@gw_adnetrefresh@", str3).replaceAll("@gw_qdata@", cjVar.is.fN).replaceAll("@gw_sdkver@", str).replaceAll("@gw_sessid@", ck.iu).replaceAll("@gw_seqnum@", cjVar.hu);
            if (cjVar.gb != null) {
                replaceAll2 = replaceAll2.replaceAll("@gw_adnetid@", cjVar.gb.fD).replaceAll("@gw_allocid@", cjVar.gb.fF);
            }
            new cr(context, str, replaceAll2).start();
        }
    }
}
