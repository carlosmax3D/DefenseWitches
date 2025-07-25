package com.facebook.internal;

import com.facebook.FacebookException;
import com.facebook.share.internal.ShareConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GraphUtil {
    private static final String[] dateFormats = {"yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd"};

    public static JSONObject createOpenGraphActionForPost(String str) {
        JSONObject jSONObject = new JSONObject();
        if (str != null) {
            try {
                jSONObject.put("type", str);
            } catch (JSONException e) {
                throw new FacebookException("An error occurred while setting up the open graph action", (Throwable) e);
            }
        }
        return jSONObject;
    }

    public static JSONObject createOpenGraphObjectForPost(String str) {
        return createOpenGraphObjectForPost(str, (String) null, (String) null, (String) null, (String) null, (JSONObject) null, (String) null);
    }

    public static JSONObject createOpenGraphObjectForPost(String str, String str2, String str3, String str4, String str5, JSONObject jSONObject, String str6) {
        JSONObject jSONObject2 = new JSONObject();
        if (str != null) {
            try {
                jSONObject2.put("type", str);
            } catch (JSONException e) {
                throw new FacebookException("An error occurred while setting up the graph object", (Throwable) e);
            }
        }
        jSONObject2.put("title", str2);
        if (str3 != null) {
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("url", str3);
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(jSONObject3);
            jSONObject2.put("image", jSONArray);
        }
        jSONObject2.put("url", str4);
        jSONObject2.put("description", str5);
        jSONObject2.put(NativeProtocol.OPEN_GRAPH_CREATE_OBJECT_KEY, true);
        if (jSONObject != null) {
            jSONObject2.put("data", jSONObject);
        }
        if (str6 != null) {
            jSONObject2.put(ShareConstants.WEB_DIALOG_PARAM_ID, str6);
        }
        return jSONObject2;
    }

    public static boolean isOpenGraphObjectForPost(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optBoolean(NativeProtocol.OPEN_GRAPH_CREATE_OBJECT_KEY);
        }
        return false;
    }
}
