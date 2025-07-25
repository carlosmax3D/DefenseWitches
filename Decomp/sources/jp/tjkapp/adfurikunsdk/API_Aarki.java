package jp.tjkapp.adfurikunsdk;

import jp.tjkapp.adfurikunsdk.API_Controller2;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class API_Aarki extends API_Base {
    private static final String AARKI_HTML_BANNER = "<html><head><base target=\"_blank><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;background-color:#ffffff;'><div style=\"text-align:center;\">[AARKI_TAG]</div></body></html>";
    private static final String AARKI_HTML_INTERS = "<html><head><base target=\"_blank><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;background-color:#ffffff;'><div style=\"text-align:center;\">[AARKI_TAG]</div></body></html>";
    private static final String AARKI_HTML_OTHER = "<html><head><base target=\"_blank><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;background-color:#ffffff;'><div style=\"text-align:center;\">[AARKI_TAG]</div></body></html>";
    private static final String AARKI_PARAM_ADN = "adn=";
    private static final String AARKI_PARAM_APPID = "appid=";
    private static final String AARKI_PARAM_SID = "sid=";
    private static final String AARKI_PARAM_UID = "uid=";
    private static final String AARKI_TAG = "[AARKI_TAG]";
    private static final String AARKI_URL = "http://ad.adfurikun.jp/api/rec-cnv?";

    API_Aarki() {
    }

    @Override // jp.tjkapp.adfurikunsdk.API_Base
    public void getContent(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, String str3, API_Controller2.API_CpntrolParam aPI_CpntrolParam, LogUtil logUtil, int i) throws Exception {
        if (AdInfo.getAdType(i) == 3) {
            aPI_ResultParam.err = -2;
            return;
        }
        if (str == null || str.length() == 0 || aPI_CpntrolParam.idfa == null || aPI_CpntrolParam.idfa.length() == 0) {
            return;
        }
        ApiAccessUtil.WebAPIResult webAPIResultCallWebAPI = ApiAccessUtil.callWebAPI(AARKI_URL + AARKI_PARAM_ADN + AdNetworkKey.AARKI + "&" + AARKI_PARAM_SID + "xxxxxx&" + AARKI_PARAM_UID + aPI_CpntrolParam.idfa + "&" + AARKI_PARAM_APPID + str, logUtil, aPI_CpntrolParam.useragent, false);
        if (webAPIResultCallWebAPI.return_code != 200) {
            aPI_ResultParam.err = webAPIResultCallWebAPI.return_code;
        } else {
            if (webAPIResultCallWebAPI.message.length() <= 0) {
                aPI_ResultParam.err = -7;
                return;
            }
            String str4 = webAPIResultCallWebAPI.message;
            aPI_ResultParam.html = "<html><head><base target=\"_blank><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;background-color:#ffffff;'><div style=\"text-align:center;\">[AARKI_TAG]</div></body></html>";
            aPI_ResultParam.html = aPI_ResultParam.html.replace(AARKI_TAG, str4);
        }
    }
}
