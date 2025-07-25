package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import com.facebook.appevents.AppEventsConstants;
import com.tapjoy.TapjoyConstants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.AdInfo;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class ApiAccessUtil {
    private static final String GETINFO_URL_AMAZON = "http://d830x8j3o1b2k.cloudfront.net/";
    private static final String GETINFO_URL_DEVELOPMENT = "http://115.30.5.174/";
    private static final String GETINFO_URL_PRODUCTION = "https://adfurikun.jp/";
    private static final String GETINFO_URL_STAGING = "http://115.30.27.96/";
    private static final String REC_URL_AMAZON = "http://d2cjo8xlt6fbwy.cloudfront.net/";
    private static final String REC_URL_DEVELOPMENT = "http://115.30.5.174/";
    private static final String REC_URL_EC2 = "http://i.adfurikun.jp/";
    private static final String REC_URL_PRODUCTION = "http://api.adfurikun.jp/";
    private static final String REC_URL_STAGING = "http://115.30.27.96/";
    private static int SERVER_TYPE = 4;
    private static final int SERVER_TYPE_AMAZON = 4;
    private static final int SERVER_TYPE_DEVELOPMENT = 0;
    private static final int SERVER_TYPE_PRODUCTION = 1;
    private static final int SERVER_TYPE_STAGING = 3;
    private static final int WEBAPI_CO_TIMEOUT = 2000;
    private static final int WEBAPI_CO_TIMEOUT_LONG = 5000;
    private static final String WEBAPI_GETINFO = "adfurikun/api/getinfo/";
    private static final String WEBAPI_GETIPUA = "http://ipua.adfurikun.jp/ua.php";
    private static final String WEBAPI_GETPRICECODE = "https://d20eh7i01l428h.cloudfront.net/get-rtb-encrypted-price.php?";
    private static final String WEBAPI_GETPRICECODE_OLD = "http://115.30.5.174/adfurikun/api/get-game-logic-encrypted-price?price=";
    private static final String WEBAPI_GETPRICECODE_OLD_AMAZON = "https://d830x8j3o1b2k.cloudfront.net/adfurikun/api/get-game-logic-encrypted-price/price/";
    private static final String WEBAPI_HOUSEAD_CLICK = "adfurikun/api/rec-click";
    private static final String WEBAPI_HOUSEAD_IMPRESSION = "adfurikun/api/rec-impression";
    private static final String WEBAPI_KEY_ADNETWORKKEY = "adnetwork_key";
    private static final String WEBAPI_KEY_BANNER_KIND = "banner_kind";
    private static final String WEBAPI_KEY_BG_COLOR = "bg_color";
    private static final String WEBAPI_KEY_CYCLE_TIME = "cycle_time";
    private static final String WEBAPI_KEY_EXT_ACT_URL = "ext_act_url";
    private static final String WEBAPI_KEY_HTML = "html";
    private static final String WEBAPI_KEY_NOADCHECK = "noadcheck";
    private static final String WEBAPI_KEY_PARAM = "param";
    private static final String WEBAPI_KEY_SETTINGS = "settings";
    private static final String WEBAPI_KEY_TRANSITION_OFF = "ta_off";
    private static final String WEBAPI_KEY_USER_AD_ID = "user_ad_id";
    private static final String WEBAPI_KEY_WALL_TYPE = "wall_type";
    private static final String WEBAPI_KEY_WEIGHT = "weight";
    private static final String WEBAPI_OPTION_APP_ID = "app_id/";
    private static final String WEBAPI_OPTION_APP_ID_AMAZON = "app_id=";
    private static final String WEBAPI_OPTION_IDFA_ID = "uid/";
    private static final String WEBAPI_OPTION_IDFA_ID_AMAZON = "uid=";
    private static final String WEBAPI_OPTION_LOCALE = "locale/";
    private static final String WEBAPI_OPTION_LOCALE_AMAZON = "locale=";
    private static final String WEBAPI_OPTION_PRICE_ID = "rev/";
    private static final String WEBAPI_OPTION_PRICE_ID_AMAZON = "rev=";
    private static final String WEBAPI_OPTION_USERAD_ID = "user_ad_id/";
    private static final String WEBAPI_OPTION_USERAD_ID_AMAZON = "user_ad_id=";
    private static final String WEBAPI_OPTION_VERSION = "ver/";
    private static final int WEBAPI_SO_TIMEOUT = 2000;
    private static final int WEBAPI_SO_TIMEOUT_LONG = 5000;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    static class WebAPIResult {
        public String message = BuildConfig.FLAVOR;
        public JSONArray array = null;
        public int return_code = Constants.WEBAPI_EXCEPTIONERR;
    }

    ApiAccessUtil() {
    }

    private static void addOptParam(StringBuilder sb, String str, String str2, String str3) throws JSONException {
        if (sb == null || str == null || str.length() <= 0) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                String string = jSONObject.getString(next);
                sb.append(str2);
                sb.append(next);
                sb.append(str3);
                sb.append(string);
            }
        } catch (Exception e) {
        }
    }

    public static WebAPIResult callWebAPI(String str, LogUtil logUtil, String str2, String str3, String str4, boolean z) throws IOException {
        WebAPIResult webAPIResult = new WebAPIResult();
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        try {
            HttpPost httpPost = new HttpPost(str.toString());
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
            if (str4 != null && str4.length() > 0) {
                httpPost.addHeader("x-forwarded-for", str4);
            }
            HttpParams params = defaultHttpClient.getParams();
            if (z) {
                HttpConnectionParams.setConnectionTimeout(params, 5000);
                HttpConnectionParams.setSoTimeout(params, 5000);
            } else {
                HttpConnectionParams.setConnectionTimeout(params, 2000);
                HttpConnectionParams.setSoTimeout(params, 2000);
            }
            if (str2 != null && str2.length() > 0) {
                params.setParameter("http.useragent", str2);
                if (Constants.DETAIL_LOG) {
                }
            }
            if (str3 != null && str3.length() > 0) {
                httpPost.setEntity(new StringEntity(str3));
                if (Constants.DETAIL_LOG) {
                }
            }
            HttpResponse httpResponseExecute = defaultHttpClient.execute(httpPost);
            webAPIResult.return_code = httpResponseExecute.getStatusLine().getStatusCode();
            if (Constants.DETAIL_LOG) {
                logUtil.debug(Constants.TAG_NAME, "return_code=" + webAPIResult.return_code);
            }
            if (webAPIResult.return_code == 200) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                httpResponseExecute.getEntity().writeTo(byteArrayOutputStream);
                webAPIResult.message = byteArrayOutputStream.toString();
            } else if (webAPIResult.return_code == 404) {
                if (Constants.DETAIL_LOG) {
                    logUtil.debug_e(Constants.TAG_NAME, "url not found:" + str);
                } else {
                    logUtil.debug_e(Constants.TAG_NAME, "url not found");
                }
            } else if (webAPIResult.return_code == 408) {
                logUtil.debug_e(Constants.TAG_NAME, "SC_REQUEST_TIMEOUT");
            } else if (webAPIResult.return_code == 400) {
                logUtil.debug_e(Constants.TAG_NAME, "SC_BAD_REQUEST");
            } else {
                logUtil.debug_e(Constants.TAG_NAME, "取得時エラー発生");
            }
        } catch (IOException e) {
            webAPIResult.return_code = Constants.WEBAPI_EXCEPTIONERR;
            logUtil.debug_e(Constants.TAG_NAME, "IOException");
            logUtil.debug_e(Constants.TAG_NAME, e);
        } catch (IllegalArgumentException e2) {
            webAPIResult.return_code = Constants.WEBAPI_EXCEPTIONERR;
            logUtil.debug_e(Constants.TAG_NAME, "IllegalArgumentException");
            logUtil.debug_e(Constants.TAG_NAME, e2);
        } catch (ClientProtocolException e3) {
            webAPIResult.return_code = Constants.WEBAPI_EXCEPTIONERR;
            logUtil.debug_e(Constants.TAG_NAME, "ClientProtocolException");
            logUtil.debug_e(Constants.TAG_NAME, e3);
        }
        if (Constants.DETAIL_LOG) {
        }
        return webAPIResult;
    }

    public static WebAPIResult callWebAPI(String str, LogUtil logUtil, String str2, boolean z) throws IOException {
        WebAPIResult webAPIResult = new WebAPIResult();
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(str);
            httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpParams params = defaultHttpClient.getParams();
            if (z) {
                HttpConnectionParams.setConnectionTimeout(params, 5000);
                HttpConnectionParams.setSoTimeout(params, 5000);
            } else {
                HttpConnectionParams.setConnectionTimeout(params, 2000);
                HttpConnectionParams.setSoTimeout(params, 2000);
            }
            if (str2 != null && str2.length() > 0) {
                params.setParameter("http.useragent", str2);
                if (Constants.DETAIL_LOG) {
                }
            }
            HttpResponse httpResponseExecute = defaultHttpClient.execute(httpGet);
            webAPIResult.return_code = httpResponseExecute.getStatusLine().getStatusCode();
            if (Constants.DETAIL_LOG) {
                logUtil.debug(Constants.TAG_NAME, "return_code=" + webAPIResult.return_code);
            }
            if (webAPIResult.return_code == 200) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                httpResponseExecute.getEntity().writeTo(byteArrayOutputStream);
                webAPIResult.message = byteArrayOutputStream.toString();
            } else if (webAPIResult.return_code == 404) {
                if (Constants.DETAIL_LOG) {
                    logUtil.debug_e(Constants.TAG_NAME, "url not found:" + str);
                } else {
                    logUtil.debug_e(Constants.TAG_NAME, "url not found");
                }
            } else if (webAPIResult.return_code == 408) {
                logUtil.debug_e(Constants.TAG_NAME, "SC_REQUEST_TIMEOUT");
            } else if (webAPIResult.return_code == 400) {
                logUtil.debug_e(Constants.TAG_NAME, "SC_BAD_REQUEST");
            } else {
                logUtil.debug_e(Constants.TAG_NAME, "取得時エラー発生");
            }
        } catch (ClientProtocolException e) {
            webAPIResult.return_code = Constants.WEBAPI_EXCEPTIONERR;
            logUtil.debug_e(Constants.TAG_NAME, "ClientProtocolException");
            logUtil.debug_e(Constants.TAG_NAME, e);
        } catch (IOException e2) {
            webAPIResult.return_code = Constants.WEBAPI_EXCEPTIONERR;
            logUtil.debug_e(Constants.TAG_NAME, "IOException");
            logUtil.debug_e(Constants.TAG_NAME, e2);
        } catch (IllegalArgumentException e3) {
            webAPIResult.return_code = Constants.WEBAPI_EXCEPTIONERR;
            logUtil.debug_e(Constants.TAG_NAME, "IllegalArgumentException");
            logUtil.debug_e(Constants.TAG_NAME, e3);
        }
        if (Constants.DETAIL_LOG) {
        }
        return webAPIResult;
    }

    public static String createUniqueID(String str) {
        try {
            return toHexString(MessageDigest.getInstance("SHA-1").digest((str + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime()) + new Random().nextInt()).getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return BuildConfig.FLAVOR;
        }
    }

    private static void getAdInfoDetail(Context context, String str, AdInfo adInfo, String str2, LogUtil logUtil, boolean z) throws JSONException {
        String[] strArrSplit;
        if (str2 != null) {
            try {
                if (str2.length() > 0) {
                    JSONArray jSONArray = new JSONArray(str2);
                    int length = jSONArray.length();
                    if (!z) {
                        logUtil.debug_i(Constants.TAG_NAME, "---------------------------------------------------------");
                        logUtil.debug_i(Constants.TAG_NAME, "adfurikun_appkey[" + str + "]");
                        if (Constants.DETAIL_LOG) {
                            logUtil.debug_i(Constants.TAG_NAME, "[adnetwork_key][user_ad_id]weight");
                        } else {
                            logUtil.debug_i(Constants.TAG_NAME, "[adnetwork_key]weight");
                        }
                    }
                    for (int i = 0; i < length; i++) {
                        String string = jSONArray.getString(i);
                        if (string != null && string.length() > 0) {
                            JSONObject jSONObject = new JSONObject(string);
                            Iterator<String> itKeys = jSONObject.keys();
                            AdInfo.AdInfoDetail adInfoDetail = new AdInfo.AdInfoDetail(null);
                            while (itKeys.hasNext()) {
                                String next = itKeys.next();
                                if (WEBAPI_KEY_USER_AD_ID.equals(next)) {
                                    adInfoDetail.user_ad_id = jSONObject.getString(next);
                                } else if (WEBAPI_KEY_WEIGHT.equals(next)) {
                                    adInfoDetail.weight = new JSONObject(jSONObject.getString(next));
                                } else if (WEBAPI_KEY_ADNETWORKKEY.equals(next)) {
                                    adInfoDetail.adnetwork_key = jSONObject.getString(next);
                                } else if ("html".equals(next)) {
                                    adInfoDetail.html = new String(Base64.decode(jSONObject.getString(next), 0));
                                } else if (WEBAPI_KEY_WALL_TYPE.equals(next)) {
                                    try {
                                        adInfoDetail.wall_type = Integer.parseInt(jSONObject.getString(next));
                                    } catch (NumberFormatException e) {
                                        adInfoDetail.wall_type = 0;
                                    }
                                } else if (WEBAPI_KEY_PARAM.equals(next)) {
                                    adInfoDetail.param = jSONObject.getString(next);
                                    if (adInfoDetail.param == null) {
                                        adInfoDetail.param = BuildConfig.FLAVOR;
                                    }
                                } else if (WEBAPI_KEY_EXT_ACT_URL.equals(next)) {
                                    String string2 = jSONObject.getString(next);
                                    if (string2 != null && string2.length() > 0 && (strArrSplit = string2.split(",")) != null && strArrSplit.length > 0) {
                                        int length2 = strArrSplit.length;
                                        adInfoDetail.ext_act_url = new String[length2];
                                        for (int i2 = 0; i2 < length2; i2++) {
                                            adInfoDetail.ext_act_url[i2] = new String(Base64.decode(strArrSplit[i2], 0));
                                        }
                                    }
                                } else if (WEBAPI_KEY_NOADCHECK.equals(next)) {
                                    try {
                                        adInfoDetail.noadcheck = Integer.parseInt(jSONObject.getString(next)) == 1 ? 1 : 0;
                                    } catch (NumberFormatException e2) {
                                        adInfoDetail.noadcheck = 0;
                                    }
                                }
                            }
                            adInfoDetail.html = replaceIDFA(context, adInfoDetail.html, logUtil);
                            if (!z) {
                                if (Constants.DETAIL_LOG) {
                                    logUtil.debug_i(Constants.TAG_NAME, "[" + adInfoDetail.adnetwork_key + "][" + adInfoDetail.user_ad_id + "]" + adInfoDetail.weight);
                                } else {
                                    logUtil.debug_i(Constants.TAG_NAME, "[" + adInfoDetail.adnetwork_key + "]" + adInfoDetail.weight);
                                }
                            }
                            String adInfoDetailFilePath = FileUtil.getAdInfoDetailFilePath(context, str, adInfoDetail);
                            boolean z2 = false;
                            if (z) {
                                File file = new File(adInfoDetailFilePath);
                                if (file != null && !file.exists()) {
                                    z2 = true;
                                }
                            } else {
                                z2 = true;
                            }
                            if (z2) {
                                FileUtil.saveStringFile(adInfoDetailFilePath, adInfoDetail.html);
                            }
                            adInfo.adInfoDetailArray.add(adInfoDetail);
                        }
                    }
                    if (!z) {
                        logUtil.debug_i(Constants.TAG_NAME, "---------------------------------------------------------");
                    }
                }
            } catch (JSONException e3) {
                logUtil.debug_e(Constants.TAG_NAME, "JSONException");
                logUtil.debug_e(Constants.TAG_NAME, e3);
                return;
            }
        }
        adInfo.initCalc();
    }

    public static String getConversionBaseUrl() {
        return SERVER_TYPE == 0 ? "http://115.30.5.174/" : REC_URL_PRODUCTION;
    }

    public static String getGetInfoBaseUrl() {
        switch (SERVER_TYPE) {
        }
        return "http://115.30.5.174/";
    }

    private static String getGetInfoSubUrl() {
        return SERVER_TYPE == 0 ? "http://115.30.5.174/" : GETINFO_URL_PRODUCTION;
    }

    public static WebAPIResult getIPUA(LogUtil logUtil, String str) throws IOException {
        if (Constants.DETAIL_LOG) {
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>getIPUA()");
        }
        WebAPIResult webAPIResultCallWebAPI = callWebAPI(WEBAPI_GETIPUA, logUtil, str, true);
        if (Constants.DETAIL_LOG) {
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>ipua:" + webAPIResultCallWebAPI.message);
        }
        return webAPIResultCallWebAPI;
    }

    public static WebAPIResult getInfo(String str, LogUtil logUtil, String str2, boolean z) {
        String string = Locale.getDefault().toString();
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append(getGetInfoBaseUrl());
        } else {
            sb.append(getGetInfoSubUrl());
        }
        sb.append(WEBAPI_GETINFO);
        sb.append(WEBAPI_OPTION_APP_ID);
        sb.append(str);
        sb.append("/");
        sb.append(WEBAPI_OPTION_LOCALE);
        sb.append(string);
        sb.append("/");
        sb.append(WEBAPI_OPTION_VERSION);
        sb.append(Constants.ADFURIKUN_VERSION);
        if (Constants.DETAIL_LOG) {
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>getInfo()");
        }
        if (Constants.DETAIL_LOG) {
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>app_id:" + str);
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>locale:" + string);
        }
        return callWebAPI(sb.toString(), logUtil, str2, true);
    }

    public static WebAPIResult getPriceCode(String str, String str2, LogUtil logUtil, String str3) throws IOException {
        if (Constants.DETAIL_LOG) {
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>getPriceCode()");
        }
        WebAPIResult webAPIResultCallWebAPI = callWebAPI(WEBAPI_GETPRICECODE + "ankey=" + str + "&" + TapjoyConstants.TJC_EVENT_IAP_PRICE + "=" + str2, logUtil, str3, true);
        if (Constants.DETAIL_LOG) {
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>price:" + str2);
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>price_code:" + webAPIResultCallWebAPI.message);
        }
        return webAPIResultCallWebAPI;
    }

    public static WebAPIResult getPriceCode_old(String str, LogUtil logUtil, String str2) throws IOException {
        if (Constants.DETAIL_LOG) {
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>getPriceCode()");
        }
        WebAPIResult webAPIResultCallWebAPI = callWebAPI((SERVER_TYPE == 4 ? WEBAPI_GETPRICECODE_OLD_AMAZON : WEBAPI_GETPRICECODE_OLD) + str, logUtil, str2, true);
        if (Constants.DETAIL_LOG) {
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>price:" + str);
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>price_code:" + webAPIResultCallWebAPI.message);
        }
        return webAPIResultCallWebAPI;
    }

    private static String getRecClickBaseUrl() {
        switch (SERVER_TYPE) {
        }
        return "http://115.30.5.174/";
    }

    private static String getRecImpressionBaseUrl() {
        switch (SERVER_TYPE) {
            case 0:
                return "http://115.30.5.174/";
            case 1:
                return REC_URL_PRODUCTION;
            case 2:
            default:
                return "http://115.30.5.174/";
            case 3:
                return "http://115.30.27.96/";
            case 4:
                return REC_URL_EC2;
        }
    }

    static boolean isAmazonServerType() {
        return SERVER_TYPE == 4;
    }

    protected static boolean isConnected(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    static boolean isDevelopmentServerType() {
        return SERVER_TYPE == 0;
    }

    static boolean isProductionServerType() {
        return SERVER_TYPE == 1;
    }

    static boolean isStagingServerType() {
        return SERVER_TYPE == 3;
    }

    public static WebAPIResult recClick(String str, String str2, LogUtil logUtil, String str3, String str4, String str5) throws JSONException {
        String language = Locale.getDefault().getLanguage();
        if (Constants.DETAIL_LOG) {
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>recClick()");
        }
        String recClickBaseUrl = getRecClickBaseUrl();
        boolean z = SERVER_TYPE == 4;
        StringBuilder sb = new StringBuilder();
        sb.append(recClickBaseUrl);
        sb.append(WEBAPI_HOUSEAD_CLICK);
        if (z) {
            sb.append("?");
            sb.append(WEBAPI_OPTION_APP_ID_AMAZON);
            sb.append(str);
            sb.append("&");
            sb.append(WEBAPI_OPTION_LOCALE_AMAZON);
            sb.append(language);
            sb.append("&");
            sb.append(WEBAPI_OPTION_USERAD_ID_AMAZON);
            sb.append(str2);
            sb.append("&");
            sb.append(WEBAPI_OPTION_IDFA_ID_AMAZON);
            sb.append(str4);
            addOptParam(sb, str5, "&", "=");
        } else {
            sb.append("/");
            sb.append(WEBAPI_OPTION_APP_ID);
            sb.append(str);
            sb.append("/");
            sb.append(WEBAPI_OPTION_LOCALE);
            sb.append(language);
            sb.append("/");
            sb.append(WEBAPI_OPTION_USERAD_ID);
            sb.append(str2);
            sb.append("/");
            sb.append(WEBAPI_OPTION_IDFA_ID);
            sb.append(str4);
            addOptParam(sb, str5, "/", "/");
        }
        if (Constants.DETAIL_LOG) {
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>app_id:" + str);
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>user_ad_id:" + str2);
        }
        return callWebAPI(sb.toString(), logUtil, str3, true);
    }

    public static WebAPIResult recImpression(String str, String str2, LogUtil logUtil, String str3, String str4, String str5, String str6) throws JSONException {
        String language = Locale.getDefault().getLanguage();
        if (Constants.DETAIL_LOG) {
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>recImpression()");
        }
        boolean z = false;
        String recImpressionBaseUrl = getRecImpressionBaseUrl();
        if (SERVER_TYPE == 4) {
            z = true;
            if (str5 != null && str5.length() > 0) {
                recImpressionBaseUrl = recImpressionBaseUrl.replace("http", "https");
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(recImpressionBaseUrl);
        sb.append(WEBAPI_HOUSEAD_IMPRESSION);
        if (z) {
            sb.append("?");
            sb.append(WEBAPI_OPTION_APP_ID_AMAZON);
            sb.append(str);
            sb.append("&");
            sb.append(WEBAPI_OPTION_LOCALE_AMAZON);
            sb.append(language);
            sb.append("&");
            sb.append(WEBAPI_OPTION_USERAD_ID_AMAZON);
            sb.append(str2);
            sb.append("&");
            sb.append(WEBAPI_OPTION_IDFA_ID_AMAZON);
            sb.append(str4);
            if (str5 != null && str5.length() > 0) {
                sb.append("&");
                sb.append(WEBAPI_OPTION_PRICE_ID_AMAZON);
                sb.append(str5);
            }
            addOptParam(sb, str6, "&", "=");
        } else {
            sb.append("/");
            sb.append(WEBAPI_OPTION_APP_ID);
            sb.append(str);
            sb.append("/");
            sb.append(WEBAPI_OPTION_LOCALE);
            sb.append(language);
            sb.append("/");
            sb.append(WEBAPI_OPTION_USERAD_ID);
            sb.append(str2);
            sb.append("/");
            sb.append(WEBAPI_OPTION_IDFA_ID);
            sb.append(str4);
            if (str5 != null && str5.length() > 0) {
                sb.append("/");
                sb.append(WEBAPI_OPTION_PRICE_ID);
                sb.append(str5);
            }
            addOptParam(sb, str6, "/", "/");
        }
        if (Constants.DETAIL_LOG) {
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>app_id:" + str);
            logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>user_ad_id:" + str2);
        }
        return callWebAPI(sb.toString(), logUtil, str3, true);
    }

    public static String replaceIDFA(Context context, String str, LogUtil logUtil) {
        String idfa = FileUtil.getIDFA(context);
        String packageName = context.getPackageName();
        try {
            return str.replace(Constants.REPLACE_IDFA, idfa).replace(Constants.REPLACE_IDFA2, idfa).replace(Constants.REPLACE_PACKAGE, packageName).replace(Constants.REPLACE_PACKAGE2, packageName);
        } catch (Exception e) {
            logUtil.debug_e(Constants.TAG_NAME, "Exception");
            logUtil.debug_e(Constants.TAG_NAME, e);
            return str;
        }
    }

    public static String replaceIDFA(String str, String str2, LogUtil logUtil) {
        try {
            return str.replace(Constants.REPLACE_IDFA, str2).replace(Constants.REPLACE_IDFA2, str2);
        } catch (Exception e) {
            logUtil.debug_e(Constants.TAG_NAME, "Exception");
            logUtil.debug_e(Constants.TAG_NAME, e);
            return str;
        }
    }

    public static void reportClashLytics(Context context, String str) {
        try {
            String str2 = (String) context.getApplicationInfo().metaData.get(Constants.CLASH_LYTICS_META_KEY);
            if (str2 == null) {
                return;
            }
            if (str2.equals(Constants.CLASH_LYTICS_API_KEY)) {
                try {
                    Class<?> cls = Class.forName("com.crashlytics.android.Crashlytics");
                    Method method = cls.getMethod("logException", Throwable.class);
                    if (method != null) {
                        method.invoke(cls, new Throwable(str));
                    }
                } catch (ClassNotFoundException e) {
                } catch (Error e2) {
                } catch (NullPointerException e3) {
                } catch (Exception e4) {
                }
            }
        } catch (NullPointerException e5) {
        }
    }

    public static AdInfo stringToAdInfo(Context context, String str, String str2, boolean z) {
        LogUtil logUtil = LogUtil.getInstance(context);
        if (Constants.DETAIL_LOG) {
        }
        if (str2.length() <= 0) {
            return null;
        }
        AdInfo adInfo = new AdInfo(null);
        try {
            JSONObject jSONObject = new JSONObject(str2);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                if (WEBAPI_KEY_CYCLE_TIME.equals(next)) {
                    adInfo.cycle_time = jSONObject.getLong(next);
                    if (Constants.DETAIL_LOG) {
                        logUtil.debug_i(Constants.TAG_NAME, "cycle_time[" + adInfo.cycle_time + "]");
                    }
                } else if (WEBAPI_KEY_BANNER_KIND.equals(next)) {
                    adInfo.banner_kind = jSONObject.getInt(next);
                    if (Constants.DETAIL_LOG) {
                        logUtil.debug_i(Constants.TAG_NAME, "banner_kind[" + adInfo.banner_kind + "]");
                    }
                } else if (WEBAPI_KEY_BG_COLOR.equals(next)) {
                    adInfo.bg_color = jSONObject.getString(next);
                    if (Constants.DETAIL_LOG) {
                        logUtil.debug_i(Constants.TAG_NAME, "bg_color[" + adInfo.bg_color + "]");
                    }
                } else if (WEBAPI_KEY_TRANSITION_OFF.equals(next)) {
                    adInfo.ta_off = jSONObject.getInt(next) == 1;
                    if (Constants.DETAIL_LOG) {
                        logUtil.debug_i(Constants.TAG_NAME, "ta_off[" + adInfo.ta_off + "]");
                    }
                } else if (WEBAPI_KEY_SETTINGS.equals(next)) {
                    getAdInfoDetail(context, str, adInfo, jSONObject.getString(next), logUtil, z);
                }
            }
            return adInfo;
        } catch (JSONException e) {
            logUtil.debug_e(Constants.TAG_NAME, "JSONException");
            logUtil.debug_e(Constants.TAG_NAME, e);
            return adInfo;
        }
    }

    private static String toHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & com.flurry.android.Constants.UNKNOWN);
            for (int length = hexString.length(); length < 2; length++) {
                sb.append(AppEventsConstants.EVENT_PARAM_VALUE_NO);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }
}
