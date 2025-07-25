package jp.tjkapp.adfurikunsdk;

import com.facebook.appevents.AppEventsConstants;
import jp.tjkapp.adfurikunsdk.API_Controller2;
import jp.tjkapp.adfurikunsdk.AdfurikunNativeAd;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class API_ZucksIcon extends API_Base {
    private static final String ZUCKS_NATIVE_URL = "http://sh.zucks.net/opt/native/api/v1";

    API_ZucksIcon() {
    }

    private void setResultParam(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, int i, LogUtil logUtil) {
        String valueFromJSON = getValueFromJSON(str, "status");
        String valueFromJSON2 = getValueFromJSON(str, "type");
        if (!"ok".equals(valueFromJSON)) {
            if ("no_ad".equals(valueFromJSON)) {
                aPI_ResultParam.err = -4;
                return;
            } else {
                aPI_ResultParam.err = -7;
                return;
            }
        }
        if (valueFromJSON2 == null || valueFromJSON2.length() <= 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        if (!"native".equals(valueFromJSON2)) {
            aPI_ResultParam.err = -7;
            return;
        }
        String valueFromJSON3 = getValueFromJSON(str, "image_src");
        String valueFromJSON4 = getValueFromJSON(str, "imp_url");
        String valueFromJSON5 = getValueFromJSON(str, "landing_url");
        String valueFromJSON6 = getValueFromJSON(str, "text");
        aPI_ResultParam.nativeAdInfo = new AdfurikunNativeAd.AdfurikunNativeAdInfo();
        aPI_ResultParam.nativeAdInfo.img_url = valueFromJSON3;
        aPI_ResultParam.nativeAdInfo.link_url = valueFromJSON5;
        aPI_ResultParam.nativeAdInfo.title = valueFromJSON6;
        aPI_ResultParam.nativeAdInfo.text = valueFromJSON6;
        aPI_ResultParam.imp_url = valueFromJSON4;
    }

    @Override // jp.tjkapp.adfurikunsdk.API_Base
    public void getContent(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, String str3, API_Controller2.API_CpntrolParam aPI_CpntrolParam, LogUtil logUtil, int i) throws Exception {
        if (AdInfo.getAdType(i) != 3) {
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
        String valueFromJSON = getValueFromJSON(str3, "frame_id");
        StringBuilder sb = new StringBuilder();
        sb.append(ZUCKS_NATIVE_URL);
        sb.append("?");
        sb.append("frameid=");
        sb.append(valueFromJSON);
        sb.append("&ip=");
        sb.append(aPI_CpntrolParam.ipua.f3207ip);
        sb.append("&lat=");
        if (aPI_CpntrolParam.idfa == null || aPI_CpntrolParam.idfa.length() <= 0) {
            sb.append(AppEventsConstants.EVENT_PARAM_VALUE_YES);
        } else {
            sb.append(AppEventsConstants.EVENT_PARAM_VALUE_NO);
            sb.append("&");
            sb.append("ida=");
            sb.append(aPI_CpntrolParam.idfa);
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
