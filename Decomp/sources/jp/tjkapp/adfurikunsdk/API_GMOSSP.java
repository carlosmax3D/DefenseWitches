package jp.tjkapp.adfurikunsdk;

import android.net.Uri;
import jp.tjkapp.adfurikunsdk.API_Controller2;
import jp.tjkapp.adfurikunsdk.AdfurikunNativeAd;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;
import org.json.JSONArray;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class API_GMOSSP extends API_Base {
    private static final String GMOSSP_URL = "http://sp.gmossp-sp.jp/ads/ssp.ad";

    API_GMOSSP() {
    }

    private void setResultParam(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, int i, LogUtil logUtil) {
        try {
            JSONArray jSONArray = new JSONArray(str);
            if (jSONArray.length() > 0) {
                String string = jSONArray.get(0).toString();
                String valueFromJSON = getValueFromJSON(string, "clickUrl");
                String valueFromJSON2 = getValueFromJSON(string, "imageUrl");
                String valueFromJSON3 = getValueFromJSON(string, "title");
                String valueFromJSON4 = getValueFromJSON(string, "description");
                aPI_ResultParam.nativeAdInfo = new AdfurikunNativeAd.AdfurikunNativeAdInfo();
                aPI_ResultParam.nativeAdInfo.img_url = valueFromJSON2;
                aPI_ResultParam.nativeAdInfo.link_url = valueFromJSON;
                aPI_ResultParam.nativeAdInfo.title = valueFromJSON3;
                aPI_ResultParam.nativeAdInfo.text = valueFromJSON4;
            } else {
                aPI_ResultParam.err = -4;
            }
        } catch (Exception e) {
            logUtil.debug_e(Constants.TAG_NAME, "Exception");
            logUtil.debug_e(Constants.TAG_NAME, e);
            aPI_ResultParam.err = -7;
        }
    }

    @Override // jp.tjkapp.adfurikunsdk.API_Base
    public void getContent(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, String str3, API_Controller2.API_CpntrolParam aPI_CpntrolParam, LogUtil logUtil, int i) throws Exception {
        if (str3.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        String valueFromJSON = getValueFromJSON(str3, "space_id");
        if (AdInfo.getAdType(i) != 3) {
            aPI_ResultParam.err = -2;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(GMOSSP_URL);
        sb.append("?");
        sb.append("space_id=");
        sb.append(valueFromJSON);
        sb.append("&");
        sb.append("ad_format=json");
        sb.append("&");
        sb.append("ua=");
        sb.append(Uri.encode(aPI_CpntrolParam.useragent));
        if (aPI_CpntrolParam.idfa == null || aPI_CpntrolParam.idfa.length() <= 0) {
            sb.append("&");
            sb.append("auid=");
            sb.append("&");
            sb.append("auid_dnt=1");
        } else {
            sb.append("&");
            sb.append("auid=");
            sb.append(aPI_CpntrolParam.idfa);
            sb.append("&");
            sb.append("auid_dnt=0");
        }
        ApiAccessUtil.WebAPIResult webAPIResultCallWebAPI = ApiAccessUtil.callWebAPI(sb.toString(), logUtil, aPI_CpntrolParam.useragent, false);
        if (webAPIResultCallWebAPI.return_code != 200) {
            aPI_ResultParam.err = -7;
        } else if (webAPIResultCallWebAPI.message.length() > 0) {
            setResultParam(aPI_ResultParam, webAPIResultCallWebAPI.message.trim(), aPI_CpntrolParam.idfa, i, logUtil);
        } else {
            aPI_ResultParam.err = -7;
        }
    }
}
