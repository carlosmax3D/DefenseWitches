package jp.tjkapp.adfurikunsdk;

import com.facebook.appevents.AppEventsConstants;
import com.tapjoy.TapjoyConstants;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.API_Controller2;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class API_GameFeat extends API_Base {
    private static final String TPL_HTML = "<!DOCTYPE html><html><head><base target=\"_blank\"><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><meta name='viewport' content='width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no'></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;background-color:#000000;'><div style=\"width:300px;margin:0 auto;\">%%TAG%%</div></body></html>";
    private final String API_URL = "http://www.gamefeat.net/webapi/v1/requestCustomAds/";
    private final String adsize = "10";

    /* renamed from: os */
    private final String f3206os = TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE;
    private final String dummy = AppEventsConstants.EVENT_PARAM_VALUE_YES;
    private String adID = BuildConfig.FLAVOR;
    private String userAgent = BuildConfig.FLAVOR;

    API_GameFeat() {
    }

    @Override // jp.tjkapp.adfurikunsdk.API_Base
    public void getContent(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, String str3, API_Controller2.API_CpntrolParam aPI_CpntrolParam, LogUtil logUtil, int i) throws Exception {
        if (AdInfo.getAdType(i) == 3) {
            aPI_ResultParam.err = -2;
            return;
        }
        JSONObject jSONObject = new JSONObject(str3);
        this.userAgent = jSONObject.getString("u");
        this.adID = jSONObject.getString("site_id");
        StringBuilder sb = new StringBuilder();
        sb.append("http://www.gamefeat.net/webapi/v1/requestCustomAds/");
        sb.append("?adid=" + this.adID);
        StringBuilder sbAppend = new StringBuilder().append("&adsize=");
        getClass();
        sb.append(sbAppend.append("10").toString());
        StringBuilder sbAppend2 = new StringBuilder().append("&os=");
        getClass();
        sb.append(sbAppend2.append(TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE).toString());
        ApiAccessUtil.WebAPIResult webAPIResultCallWebAPI = ApiAccessUtil.callWebAPI(sb.toString(), logUtil, this.userAgent, false);
        if (webAPIResultCallWebAPI.return_code != 200) {
            aPI_ResultParam.err = webAPIResultCallWebAPI.return_code;
            return;
        }
        if (webAPIResultCallWebAPI.message.length() <= 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        JSONObject jSONObject2 = new JSONObject(webAPIResultCallWebAPI.message);
        String string = jSONObject2.getString("img");
        String string2 = jSONObject2.getString("url");
        if (string.equals(BuildConfig.FLAVOR) || string2.equals(BuildConfig.FLAVOR)) {
            aPI_ResultParam.err = -7;
        } else {
            aPI_ResultParam.html = TPL_HTML;
            aPI_ResultParam.html = aPI_ResultParam.html.replace("%%TAG%%", "<a href=\"" + string2 + "\"><img src=\"" + string + "\" /></>");
        }
    }
}
