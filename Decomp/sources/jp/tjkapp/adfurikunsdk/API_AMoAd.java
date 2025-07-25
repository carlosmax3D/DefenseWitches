package jp.tjkapp.adfurikunsdk;

import jp.tjkapp.adfurikunsdk.API_Controller2;
import jp.tjkapp.adfurikunsdk.AdfurikunNativeAd;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;
import org.json.JSONArray;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class API_AMoAd extends API_Base {
    private static final String AMOAD_URL = "http://n.amoad.com/n/v1/";

    API_AMoAd() {
    }

    private void setResultParam(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, int i, LogUtil logUtil) {
        String valueFromJSON = getValueFromJSON(str, "numOfAd");
        String valueFromJSON2 = getValueFromJSON(str, "ads");
        try {
            if (Integer.valueOf(valueFromJSON).intValue() <= 0 || valueFromJSON2 == null || valueFromJSON2.length() <= 0) {
                aPI_ResultParam.err = -4;
            } else {
                JSONArray jSONArray = new JSONArray(valueFromJSON2);
                if (jSONArray.length() > 0) {
                    String string = jSONArray.get(0).toString();
                    String valueFromJSON3 = getValueFromJSON(string, "NATIVE_LINK");
                    String valueFromJSON4 = getValueFromJSON(string, "NATIVE_ICON_URL");
                    String valueFromJSON5 = getValueFromJSON(string, "NATIVE_IMP_URL");
                    String valueFromJSON6 = getValueFromJSON(string, "NATIVE_TITLE_SHORT");
                    String valueFromJSON7 = getValueFromJSON(string, "NATIVE_TITLE_LONG");
                    aPI_ResultParam.nativeAdInfo = new AdfurikunNativeAd.AdfurikunNativeAdInfo();
                    aPI_ResultParam.nativeAdInfo.img_url = valueFromJSON4;
                    aPI_ResultParam.nativeAdInfo.link_url = valueFromJSON3;
                    aPI_ResultParam.nativeAdInfo.title = valueFromJSON6;
                    aPI_ResultParam.nativeAdInfo.text = valueFromJSON7;
                    aPI_ResultParam.imp_url = valueFromJSON5;
                }
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
        String valueFromJSON = getValueFromJSON(str3, "sid");
        if (AdInfo.getAdType(i) != 3) {
            aPI_ResultParam.err = -2;
            return;
        }
        ApiAccessUtil.WebAPIResult webAPIResultCallWebAPI = ApiAccessUtil.callWebAPI(AMOAD_URL + "?sid=" + valueFromJSON + "&nul=1", logUtil, aPI_CpntrolParam.useragent, false);
        if (webAPIResultCallWebAPI.return_code != 200) {
            aPI_ResultParam.err = -7;
        } else if (webAPIResultCallWebAPI.message.length() > 0) {
            setResultParam(aPI_ResultParam, webAPIResultCallWebAPI.message.trim(), aPI_CpntrolParam.idfa, i, logUtil);
        } else {
            aPI_ResultParam.err = -7;
        }
    }
}
