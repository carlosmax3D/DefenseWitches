package jp.tjkapp.adfurikunsdk;

import android.net.Uri;
import com.ansca.corona.Crypto;
import java.security.MessageDigest;
import java.util.Iterator;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.API_Controller2;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class API_mMedia extends API_Base {
    private static final String GETINFO_MMEDIA_PARAM_PLACEMENT_APID = "placement_apid";
    private static final String MMEDIA_HTML_BANNER = "<!DOCTYPE html><html><head><base target=\"_blank\"><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><meta name='viewport' content='width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no'></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;background-color:#000000;'><div style=\"width:320px;margin:0 auto;\">[MMEDIA_TAG]</div></body></html>";
    private static final String MMEDIA_HTML_INTERS = "<!DOCTYPE html><html><head><base target=\"_blank\"><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><meta name='viewport' content='width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no'></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;background-color:#000000;'><div style=\"width:300px;margin:0 auto;\">[MMEDIA_TAG]</div></body></html>";
    private static final String MMEDIA_HTML_OTHERS = "<!DOCTYPE html><html><head><base target=\"_blank\"><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><meta name='viewport' content='width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no'></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;background-color:#000000;'><div style=\"width:320px;margin:0 auto;\">[MMEDIA_TAG]</div></body></html>";
    private static final String MMEDIA_PARAM_AAID = "aaid=";
    private static final String MMEDIA_PARAM_APID = "apid=";
    private static final String MMEDIA_PARAM_ATE = "ate=";
    private static final String MMEDIA_PARAM_UA = "ua=";
    private static final String MMEDIA_PARAM_UIP = "uip=";
    private static final String MMEDIA_TAG = "[MMEDIA_TAG]";
    private static final String MMEDIA_URL = "http://ads.mydas.mobi/getAd?";

    API_mMedia() {
    }

    private static String byteArrayToString(byte[] bArr) {
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(cArr[(b & 240) >> 4]);
            sb.append(cArr[(b & 15) >> 0]);
        }
        return sb.toString();
    }

    private static String get_MMDID(String str) {
        StringBuilder sb = new StringBuilder("mmh_");
        try {
            sb.append(byteArrayToString(MessageDigest.getInstance(Crypto.ALGORITHM_MD5).digest(str.getBytes())));
            sb.append("_");
            sb.append(byteArrayToString(MessageDigest.getInstance("SHA1").digest(str.getBytes())));
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    @Override // jp.tjkapp.adfurikunsdk.API_Base
    public void getContent(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, String str3, API_Controller2.API_CpntrolParam aPI_CpntrolParam, LogUtil logUtil, int i) throws Exception {
        if (AdInfo.getAdType(i) == 3) {
            aPI_ResultParam.err = -2;
            return;
        }
        if (str3 == null || str3.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        if (aPI_CpntrolParam.useragent == null || aPI_CpntrolParam.useragent.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        if (aPI_CpntrolParam.idfa == null || aPI_CpntrolParam.idfa.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        if (aPI_CpntrolParam.ipua.f3207ip == null || aPI_CpntrolParam.ipua.f3207ip.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        String _mmdid = get_MMDID(aPI_CpntrolParam.idfa);
        String string = BuildConfig.FLAVOR;
        if (str3 != null && str3.length() > 0) {
            try {
                JSONObject jSONObject = new JSONObject(str3);
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    if (GETINFO_MMEDIA_PARAM_PLACEMENT_APID.equals(next)) {
                        string = jSONObject.getString(next);
                        if (Constants.DETAIL_LOG) {
                            logUtil.debug_i(Constants.TAG_NAME, "placement_apid[" + string + "]");
                        }
                    }
                }
            } catch (JSONException e) {
                if (Constants.DETAIL_LOG) {
                    logUtil.debug_e(Constants.TAG_NAME, "JSONException");
                    logUtil.debug_e(Constants.TAG_NAME, e);
                }
            }
        }
        String str4 = string;
        if (str4 == null || str4.length() <= 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        ApiAccessUtil.WebAPIResult webAPIResultCallWebAPI = ApiAccessUtil.callWebAPI(MMEDIA_URL + MMEDIA_PARAM_APID + str4 + "&" + MMEDIA_PARAM_AAID + _mmdid + "&" + MMEDIA_PARAM_UA + Uri.encode(aPI_CpntrolParam.useragent) + "&" + MMEDIA_PARAM_UIP + aPI_CpntrolParam.ipua.f3207ip + "&" + MMEDIA_PARAM_ATE + "true", logUtil, aPI_CpntrolParam.useragent, false);
        if (webAPIResultCallWebAPI.return_code != 200) {
            aPI_ResultParam.err = webAPIResultCallWebAPI.return_code;
            return;
        }
        if (webAPIResultCallWebAPI.message.length() <= 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        String strReplaceCode = replaceCode(webAPIResultCallWebAPI.message);
        if (i == 0) {
            aPI_ResultParam.html = "<!DOCTYPE html><html><head><base target=\"_blank\"><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><meta name='viewport' content='width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no'></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;background-color:#000000;'><div style=\"width:320px;margin:0 auto;\">[MMEDIA_TAG]</div></body></html>";
        } else {
            aPI_ResultParam.html = MMEDIA_HTML_INTERS;
        }
        aPI_ResultParam.html = aPI_ResultParam.html.replace(MMEDIA_TAG, strReplaceCode);
        aPI_ResultParam.html = aPI_ResultParam.html.replace("width=\"480\"", "width=\"320\"");
        aPI_ResultParam.html = aPI_ResultParam.html.replace("height=\"60\"", "height=\"auto\"");
    }
}
