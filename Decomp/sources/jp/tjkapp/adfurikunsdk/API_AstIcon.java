package jp.tjkapp.adfurikunsdk;

import CoronaProvider.ads.admob.AdMobAd;
import com.facebook.share.internal.ShareConstants;
import java.io.IOException;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.API_Controller2;
import jp.tjkapp.adfurikunsdk.AdfurikunNativeAd;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class API_AstIcon extends API_Base {
    private static final String AST_URL = "http://public.astrsk.net/";

    API_AstIcon() {
    }

    private String createClickUrl(String str, String str2) {
        return AST_URL + str + "/click.cgi?idx=1&ucd=" + str2 + "&sspcode=adfurikun";
    }

    private String createImpUrl(String str, String str2) {
        return BuildConfig.FLAVOR;
    }

    private String createRequestUrl(String str, String str2) {
        return AST_URL + str + "/mbget.cgi?idx=1&size=73x75&ucd=" + str2 + "&sspcode=adfurikun&outfmt=json";
    }

    private String getSessionId(API_Controller2.API_CpntrolParam aPI_CpntrolParam, LogUtil logUtil) throws IOException {
        ApiAccessUtil.WebAPIResult webAPIResultCallWebAPI = ApiAccessUtil.callWebAPI(AST_URL + "getsess/g?idfa=" + aPI_CpntrolParam.idfa, logUtil, aPI_CpntrolParam.useragent, false);
        return webAPIResultCallWebAPI.return_code == 200 ? webAPIResultCallWebAPI.message : BuildConfig.FLAVOR;
    }

    private void setResultParam(API_Controller2.API_ResultParam aPI_ResultParam, String str, LogUtil logUtil) {
        String valueFromJSON;
        String valueFromJSON2;
        aPI_ResultParam.err = -7;
        if (!"bannerset".equals(getValueFromJSON(str, "name")) || (valueFromJSON = getValueFromJSON(str, "children")) == null || valueFromJSON.length() <= 0) {
            return;
        }
        try {
            JSONArray jSONArray = new JSONArray(valueFromJSON);
            if (jSONArray.length() > 1) {
                String valueFromJSON3 = BuildConfig.FLAVOR;
                String str2 = BuildConfig.FLAVOR;
                String string = BuildConfig.FLAVOR;
                String string2 = jSONArray.get(0).toString();
                if ("imgbase".equals(getValueFromJSON(string2, "name")) && (valueFromJSON2 = getValueFromJSON(string2, "attributes")) != null && valueFromJSON2.length() > 0) {
                    valueFromJSON3 = getValueFromJSON(valueFromJSON2, ShareConstants.WEB_DIALOG_PARAM_HREF);
                }
                String string3 = jSONArray.get(1).toString();
                if ("content".equals(getValueFromJSON(string3, "name"))) {
                    JSONArray jSONArray2 = new JSONArray(getValueFromJSON(string3, "children"));
                    if (jSONArray2.length() > 0) {
                        String string4 = jSONArray2.get(0).toString();
                        if (AdMobAd.BANNER.equals(getValueFromJSON(string4, "name"))) {
                            JSONArray jSONArray3 = new JSONArray(getValueFromJSON(string4, "children"));
                            int length = jSONArray3.length();
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < length; i++) {
                                String string5 = jSONArray3.get(i).toString();
                                String valueFromJSON4 = getValueFromJSON(string5, "name");
                                String valueFromJSON5 = getValueFromJSON(string5, "children");
                                String string6 = BuildConfig.FLAVOR;
                                try {
                                    string6 = new JSONArray(valueFromJSON5).get(0).toString();
                                } catch (JSONException e) {
                                }
                                if ("text".equals(valueFromJSON4)) {
                                    sb.append(string6);
                                } else if ("path".equals(valueFromJSON4)) {
                                    str2 = string6;
                                }
                            }
                            string = sb.toString();
                        }
                    }
                }
                if (valueFromJSON3 == null || valueFromJSON3.length() <= 0) {
                    return;
                }
                aPI_ResultParam.nativeAdInfo = new AdfurikunNativeAd.AdfurikunNativeAdInfo();
                aPI_ResultParam.nativeAdInfo.img_url = valueFromJSON3 + str2;
                aPI_ResultParam.nativeAdInfo.title = BuildConfig.FLAVOR;
                aPI_ResultParam.nativeAdInfo.text = string;
                aPI_ResultParam.err = 0;
            }
        } catch (JSONException e2) {
        }
    }

    @Override // jp.tjkapp.adfurikunsdk.API_Base
    public void getContent(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, String str3, API_Controller2.API_CpntrolParam aPI_CpntrolParam, LogUtil logUtil, int i) throws Exception {
        if (AdInfo.getAdType(i) != 3) {
            aPI_ResultParam.err = -2;
            return;
        }
        String valueFromJSON = getValueFromJSON(str3, "media_code");
        if (valueFromJSON == null || valueFromJSON.length() <= 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        String sessionId = getSessionId(aPI_CpntrolParam, logUtil);
        if (sessionId == null || sessionId.length() <= 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        ApiAccessUtil.WebAPIResult webAPIResultCallWebAPI = ApiAccessUtil.callWebAPI(createRequestUrl(valueFromJSON, sessionId), logUtil, aPI_CpntrolParam.useragent, false);
        if (webAPIResultCallWebAPI.return_code != 200) {
            if (webAPIResultCallWebAPI.return_code == 204) {
                aPI_ResultParam.err = -4;
                return;
            } else {
                aPI_ResultParam.err = -7;
                return;
            }
        }
        if (webAPIResultCallWebAPI.message.length() <= 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        setResultParam(aPI_ResultParam, webAPIResultCallWebAPI.message.trim(), logUtil);
        if (aPI_ResultParam.err != 0 || aPI_ResultParam.nativeAdInfo == null) {
            return;
        }
        aPI_ResultParam.nativeAdInfo.link_url = createClickUrl(valueFromJSON, sessionId);
        aPI_ResultParam.imp_url = createImpUrl(valueFromJSON, sessionId);
    }
}
