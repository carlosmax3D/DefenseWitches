package jp.tjkapp.adfurikunsdk;

import android.net.Uri;
import android.os.Build;
import com.ansca.corona.Crypto;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.share.internal.ShareConstants;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.API_Controller2;
import jp.tjkapp.adfurikunsdk.AdfurikunNativeAd;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class API_TJK extends API_Base {
    private static final String TJK_HTML1 = "<html><head><base target=\"_blank\"><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><style>img{width:320px;height: auto;}@media screen and (min-width: 640px) and orientation: portrait){img{width:640px;height:auto;}}@media screen and (min-width:768px) and (orientation:landscape){img{width:640px;height: auto;}}</style></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;'><div style=\"text-align:center;\"><a href=\"";
    private static final String TJK_HTML11 = "<html><head><base target=\"_blank\"><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;'><div style=\"text-align:center;\"><a href=\"";
    private static final String TJK_HTML12 = "\"><img src=\"";
    private static final String TJK_HTML13 = "\" width=\"100%%\"></a></div></body></html>";
    private static final String TJK_HTML2 = "\"><img src=\"";
    private static final String TJK_HTML3 = "\"></a></div></body></html>";
    private static final String TJK_URL = "http://d3nuh0o2v20a02.cloudfront.net/adfurikunpr/api/getad";
    private String mAppID;

    API_TJK() {
    }

    private String addAdfkParam(String str, String str2, LogUtil logUtil) {
        return Uri.parse(str).buildUpon().appendQueryParameter("adfk", md5(str2, logUtil)).build().toString();
    }

    private boolean isShowDate(String str, String str2) {
        boolean z = true;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Calendar calendar = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            if (str != null && str.length() > 0 && !"null".equals(str)) {
                calendar2.setTime(simpleDateFormat.parse(str));
                if (calendar.before(calendar2)) {
                    z = false;
                }
            }
            if (str2 == null || str2.length() <= 0 || "null".equals(str2)) {
                return z;
            }
            calendar2.setTime(simpleDateFormat.parse(str2));
            if (calendar.after(calendar2)) {
                return false;
            }
            return z;
        } catch (Exception e) {
            return false;
        }
    }

    private static String md5(String str, LogUtil logUtil) {
        try {
            String string = new BigInteger(1, MessageDigest.getInstance(Crypto.ALGORITHM_MD5).digest(str.getBytes())).toString(16);
            while (string.length() < 32) {
                string = AppEventsConstants.EVENT_PARAM_VALUE_NO + string;
            }
            return string;
        } catch (NoSuchAlgorithmException e) {
            logUtil.debug_e(Crypto.ALGORITHM_MD5, e.getMessage());
            return null;
        }
    }

    private void setResultParam(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, int i, LogUtil logUtil) throws JSONException, NumberFormatException {
        String valueFromJSON = getValueFromJSON(str, "result");
        String valueFromJSON2 = getValueFromJSON(str, "msg");
        String valueFromJSON3 = getValueFromJSON(str, "item");
        int i2 = 1;
        try {
            i2 = Integer.parseInt(getValueFromJSON(str, "cnt"));
        } catch (Exception e) {
        }
        try {
            FileUtil.setCustomValue(this.mContext, this.mAppID + "_" + FileUtil.PREFKEY_TJK_CNT_MAX, Integer.parseInt(getValueFromJSON(str, "cnt_max")));
        } catch (Exception e2) {
        }
        if (!"OK".equals(valueFromJSON)) {
            logUtil.debug_e(Constants.TAG_NAME, valueFromJSON2);
            aPI_ResultParam.err = -7;
            return;
        }
        if (i2 <= 0 || valueFromJSON3 == null || valueFromJSON3.length() <= 0) {
            return;
        }
        try {
            JSONArray jSONArray = new JSONArray(valueFromJSON3);
            if (jSONArray.length() <= 0) {
                aPI_ResultParam.err = -7;
                return;
            }
            String string = jSONArray.get(0).toString();
            if (!AppEventsConstants.EVENT_PARAM_VALUE_NO.equals(getValueFromJSON(string, "mov_flg"))) {
                aPI_ResultParam.err = -7;
                return;
            }
            String valueFromJSON4 = getValueFromJSON(string, ShareConstants.WEB_DIALOG_PARAM_ID);
            String valueFromJSON5 = getValueFromJSON(string, "url");
            String valueFromJSON6 = getValueFromJSON(string, "bnr_url");
            String valueFromJSON7 = getValueFromJSON(string, "strt_dt");
            String valueFromJSON8 = getValueFromJSON(string, "end_dt");
            String valueFromJSON9 = getValueFromJSON(string, "imp_url");
            String valueFromJSON10 = getValueFromJSON(string, "clk_url");
            String valueFromJSON11 = getValueFromJSON(string, "title");
            String valueFromJSON12 = getValueFromJSON(string, "text");
            if (!isShowDate(valueFromJSON7, valueFromJSON8)) {
                aPI_ResultParam.err = -4;
                return;
            }
            aPI_ResultParam.imp_url = ApiAccessUtil.replaceIDFA(valueFromJSON9, str2, logUtil);
            aPI_ResultParam.click_url = ApiAccessUtil.replaceIDFA(valueFromJSON10, str2, logUtil);
            String strMd5 = BuildConfig.FLAVOR;
            if (str2.length() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(valueFromJSON4).append("_").append(this.mAppID).append("_").append(str2);
                strMd5 = md5(sb.toString(), logUtil);
                valueFromJSON5 = addAdfkParam(valueFromJSON5, sb.toString(), logUtil);
                aPI_ResultParam.click_url = addAdfkParam(aPI_ResultParam.click_url, sb.toString(), logUtil);
            }
            if (AdInfo.getAdType(i) == 3) {
                aPI_ResultParam.nativeAdInfo = new AdfurikunNativeAd.AdfurikunNativeAdInfo();
                aPI_ResultParam.nativeAdInfo.img_url = valueFromJSON6;
                aPI_ResultParam.nativeAdInfo.link_url = valueFromJSON5;
                aPI_ResultParam.nativeAdInfo.title = valueFromJSON11;
                aPI_ResultParam.nativeAdInfo.text = valueFromJSON12;
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("paid", valueFromJSON4);
                jSONObject.put("adfk", strMd5);
                aPI_ResultParam.rec_click_param = jSONObject.toString();
                return;
            }
            StringBuilder sb2 = new StringBuilder();
            if (i == 9) {
                sb2.append(TJK_HTML11);
                sb2.append(valueFromJSON5);
                sb2.append("\"><img src=\"");
                sb2.append(valueFromJSON6);
                sb2.append(TJK_HTML13);
            } else {
                sb2.append(TJK_HTML1);
                sb2.append(valueFromJSON5);
                sb2.append("\"><img src=\"");
                sb2.append(valueFromJSON6);
                sb2.append(TJK_HTML3);
            }
            aPI_ResultParam.html = sb2.toString();
        } catch (JSONException e3) {
            logUtil.debug_e(Constants.TAG_NAME, "JSONException");
            logUtil.debug_e(Constants.TAG_NAME, e3);
            aPI_ResultParam.err = -7;
        }
    }

    @Override // jp.tjkapp.adfurikunsdk.API_Base
    public void getContent(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, String str3, API_Controller2.API_CpntrolParam aPI_CpntrolParam, LogUtil logUtil, int i) throws Exception {
        if (aPI_CpntrolParam.pkg_name.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        this.mAppID = str;
        StringBuilder sb = new StringBuilder();
        sb.append(TJK_URL);
        sb.append("?");
        sb.append("aid=");
        sb.append(this.mAppID);
        sb.append("&");
        sb.append("sdk=");
        sb.append(Constants.ADFURIKUN_VERSION);
        sb.append("&");
        sb.append("lang=");
        sb.append(Locale.getDefault().toString());
        if (aPI_CpntrolParam.idfa == null || aPI_CpntrolParam.idfa.length() > 0) {
        }
        sb.append("&");
        sb.append("mode=");
        sb.append(AppEventsConstants.EVENT_PARAM_VALUE_NO);
        sb.append("&");
        sb.append("lim=1");
        int customValue = FileUtil.getCustomValue(this.mContext, this.mAppID + "_" + FileUtil.PREFKEY_TJK_CNT_MAX, 1);
        if (customValue >= 2) {
            int iNextInt = new Random().nextInt(customValue) + 1;
            sb.append("&");
            sb.append("cntn=");
            sb.append(iNextInt);
        }
        if (aPI_CpntrolParam.ipua.carrier != null && aPI_CpntrolParam.ipua.carrier.length() > 0) {
            sb.append("&");
            sb.append("carr=");
            sb.append(aPI_CpntrolParam.ipua.carrier);
        }
        if (aPI_CpntrolParam.ipua.loc != null && aPI_CpntrolParam.ipua.loc.length() > 0) {
            sb.append("&");
            sb.append("loc=");
            sb.append(aPI_CpntrolParam.ipua.loc);
        }
        ApiAccessUtil.WebAPIResult webAPIResultCallWebAPI = ApiAccessUtil.callWebAPI(sb.toString(), logUtil, "adfsdk;os android;osv " + Build.VERSION.RELEASE + ";app " + aPI_CpntrolParam.pkg_name, false);
        if (webAPIResultCallWebAPI.return_code == 200) {
            if (webAPIResultCallWebAPI.message.length() > 0) {
                setResultParam(aPI_ResultParam, webAPIResultCallWebAPI.message.trim(), aPI_CpntrolParam.idfa, i, logUtil);
                return;
            } else {
                aPI_ResultParam.err = -7;
                return;
            }
        }
        if (webAPIResultCallWebAPI.return_code == 204) {
            aPI_ResultParam.err = -4;
        } else {
            aPI_ResultParam.err = -7;
        }
    }
}
