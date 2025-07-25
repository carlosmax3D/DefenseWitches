package jp.tjkapp.adfurikunsdk;

import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.API_Controller2;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class API_InMobi extends API_Base {
    private static final String GETINFO_INMOBI_PARAM_ADSIZE = "adsize";
    private static final String GETINFO_INMOBI_PARAM_SITE_ID = "site_id";
    private static final int INMOBI_ADS = 1;
    private static final int INMOBI_ADTYPE_BANNER = 15;
    private static final int INMOBI_ADTYPE_INTERS = 10;
    private static final String INMOBI_DISPLAYMANAGER = "s_terajima";
    private static final String INMOBI_HTML_BANNER = "<html><head><base target=\"_blank\"><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;'><div style=\"text-align:center;\">[INMOBI_TAG]</div></body></html>";
    private static final String INMOBI_HTML_INTERS = "<html><head><base target=\"_blank\"><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;'><div style=\"text-align:center;\">[INMOBI_TAG]</div></body></html>";
    private static final String INMOBI_HTML_OTHER = "<html><head><base target=\"_blank\"><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;'><div style=\"text-align:center;\">[INMOBI_TAG]</div></body></html>";
    private static final String INMOBI_PARAM_ADS = "ads";
    private static final String INMOBI_PARAM_ADSIZE = "adsize";
    private static final String INMOBI_PARAM_API = "api";
    private static final String INMOBI_PARAM_BANNER = "banner";
    private static final String INMOBI_PARAM_DEVICE = "device";
    private static final String INMOBI_PARAM_DISPLAYMANAGER = "displaymanager";
    private static final String INMOBI_PARAM_GPID = "gpid";
    private static final String INMOBI_PARAM_ID = "id";
    private static final String INMOBI_PARAM_IMP = "imp";
    private static final String INMOBI_PARAM_IP = "ip";
    private static final String INMOBI_PARAM_RESPONSE_FORMAT = "responseformat";
    private static final String INMOBI_PARAM_SITE = "site";
    private static final String INMOBI_PARAM_UA = "ua";
    private static final String INMOBI_RESPONSE_FORMAT = "html";
    private static final String[] INMOBI_RESULT_EMPTY = {"<!-- mKhoj: No advt for this position -->"};
    private static final String INMOBI_TAG = "[INMOBI_TAG]";
    private static final String INMOBI_URL = "http://api.w.inmobi.com/showad/v2";

    API_InMobi() {
    }

    private String createHTML(ApiAccessUtil.WebAPIResult webAPIResult, String str) {
        String str2;
        String strReplaceCode = replaceCode(webAPIResult.message.trim());
        try {
            if (Integer.valueOf(str).intValue() != 15) {
                if (Integer.valueOf(str).intValue() == 10) {
                }
            }
        } catch (Exception e) {
        } finally {
        }
        return str2.replace(INMOBI_TAG, strReplaceCode);
    }

    private String createInMobiJson(String str, String str2, String str3, String str4, String str5, LogUtil logUtil) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(INMOBI_PARAM_RESPONSE_FORMAT, "html");
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(INMOBI_PARAM_DISPLAYMANAGER, INMOBI_DISPLAYMANAGER);
            jSONObject2.put(INMOBI_PARAM_ADS, 1);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("adsize", Integer.parseInt(str2));
            jSONObject2.put("banner", jSONObject3);
            jSONArray.put(jSONObject2);
            jSONObject.put(INMOBI_PARAM_IMP, jSONArray);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("id", str);
            jSONObject.put(INMOBI_PARAM_SITE, jSONObject4);
            JSONObject jSONObject5 = new JSONObject();
            jSONObject5.put(INMOBI_PARAM_IP, str3);
            jSONObject5.put(INMOBI_PARAM_UA, str4);
            jSONObject5.put(INMOBI_PARAM_GPID, str5);
            jSONObject.put("device", jSONObject5);
            return jSONObject.toString();
        } catch (JSONException e) {
            logUtil.debug_e(Constants.TAG_NAME, "JSONException");
            logUtil.debug_e(Constants.TAG_NAME, e);
            return BuildConfig.FLAVOR;
        }
    }

    private boolean isErrorCode(ApiAccessUtil.WebAPIResult webAPIResult) {
        return webAPIResult.return_code != 200;
    }

    private boolean isStockout(ApiAccessUtil.WebAPIResult webAPIResult) {
        if (webAPIResult.message.length() == 0) {
            return true;
        }
        for (String str : INMOBI_RESULT_EMPTY) {
            if (webAPIResult.message.indexOf(str) != -1) {
                return true;
            }
        }
        return false;
    }

    @Override // jp.tjkapp.adfurikunsdk.API_Base
    public void getContent(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, String str3, API_Controller2.API_CpntrolParam aPI_CpntrolParam, LogUtil logUtil, int i) throws Exception {
        if (AdInfo.getAdType(i) == 3) {
            aPI_ResultParam.err = -2;
            return;
        }
        if (str3.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        if (aPI_CpntrolParam.useragent.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        if (aPI_CpntrolParam.idfa.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        if (aPI_CpntrolParam.ipua.f3207ip.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        String valueFromJSON = getValueFromJSON(str3, GETINFO_INMOBI_PARAM_SITE_ID);
        if (valueFromJSON.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        String valueFromJSON2 = getValueFromJSON(str3, "adsize");
        if (valueFromJSON2.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        ApiAccessUtil.WebAPIResult webAPIResultCallWebAPI = ApiAccessUtil.callWebAPI(INMOBI_URL, logUtil, aPI_CpntrolParam.useragent, createInMobiJson(valueFromJSON, valueFromJSON2, aPI_CpntrolParam.ipua.f3207ip, aPI_CpntrolParam.useragent, aPI_CpntrolParam.idfa, logUtil), aPI_CpntrolParam.ipua.f3207ip, false);
        if (isErrorCode(webAPIResultCallWebAPI)) {
            aPI_ResultParam.err = webAPIResultCallWebAPI.return_code;
        } else if (isStockout(webAPIResultCallWebAPI)) {
            aPI_ResultParam.err = -7;
        } else {
            aPI_ResultParam.html = createHTML(webAPIResultCallWebAPI, valueFromJSON2);
        }
    }
}
