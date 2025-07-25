package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.webkit.WebView;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.AdInfo;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;
import org.json.JSONException;
import org.json.JSONObject;
import twitter4j.HttpResponseCode;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class FileUtil {
    private static final String ADFURIKUN_FOLDER = "/adfurikun/";
    private static final String FILLER_FILE = "filler.html";
    private static final String GETINFO_FILE = "adfurikun_getinfo.dat";
    private static final String PREFKEY_AD_LAST_STATE = "ad_last_state_";
    private static final String PREFKEY_AD_LAST_TIME = "ad_last_time_";
    private static final String PREFKEY_CARR = "carrier";
    private static final String PREFKEY_GETINFO_STATE = "getinfo_state";
    private static final String PREFKEY_IDFA = "idfa";
    private static final String PREFKEY_IDFA_LIMIT = "idfa_limit";
    private static final String PREFKEY_IDFA_TIME = "idfa_time";
    private static final String PREFKEY_INTERS_AD_FREQUENCY_CT = "_inters_ad_frequency_ct";
    private static final String PREFKEY_INTERS_AD_MAX_CT = "_inters_ad_max_ct";
    private static final String PREFKEY_IP = "ip";
    private static final String PREFKEY_IP_TIME = "ip_time";
    private static final String PREFKEY_LOC = "loc";
    private static final String PREFKEY_TESTMODE = "test_mode";
    public static final String PREFKEY_TJK_CNT_MAX = "tjk_cnt_max";
    private static final String PREFKEY_USERAGENT = "useragent";
    private static final String PREF_FILE = "adfurikun_adpref.dat";
    public static final int PREF_TESTMODE_NOSETTING = -1;
    public static final int PREF_TESTMODE_PRODUCT = 1;
    public static final int PREF_TESTMODE_TEST = 0;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    static class IPUA {

        /* renamed from: ip */
        public String f3207ip = BuildConfig.FLAVOR;
        public String carrier = BuildConfig.FLAVOR;
        public String loc = BuildConfig.FLAVOR;
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    static class IntersAdPref {
        public int frequency_ct;
        public int max_ct;

        IntersAdPref() {
        }
    }

    private FileUtil() {
    }

    public static boolean deleteFile(String str) {
        File file = new File(str);
        if (file.isDirectory()) {
            for (String str2 : file.list()) {
                if (!deleteFile(new File(file, str2).getPath())) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static String getAdInfoDetailFilePath(Context context, String str, AdInfo.AdInfoDetail adInfoDetail) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(context.getCacheDir().getPath());
            sb.append(ADFURIKUN_FOLDER);
            sb.append(str);
            sb.append("/");
        } catch (Exception e) {
        }
        sb.append(adInfoDetail.adnetwork_key);
        sb.append("_");
        sb.append(adInfoDetail.user_ad_id);
        sb.append(".html");
        return sb.toString();
    }

    public static long getAdLastState(Context context, String str) {
        return context.getSharedPreferences(PREF_FILE, 0).getInt(PREFKEY_AD_LAST_STATE + str, HttpResponseCode.f3212OK);
    }

    public static long getAdLastTime(Context context, String str) {
        return context.getSharedPreferences(PREF_FILE, 0).getLong(PREFKEY_AD_LAST_TIME + str, -1L);
    }

    public static int getCustomValue(Context context, String str, int i) {
        return context != null ? context.getSharedPreferences(PREF_FILE, 0).getInt(str, i) : i;
    }

    public static String getFillerFilePath(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(context.getCacheDir().getPath());
            sb.append(ADFURIKUN_FOLDER);
            sb.append(str);
            sb.append("/");
        } catch (Exception e) {
        }
        sb.append(FILLER_FILE);
        return sb.toString();
    }

    public static String getGetInfoFilePath(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(context.getCacheDir().getPath());
            sb.append(ADFURIKUN_FOLDER);
            sb.append(str);
            sb.append("/");
        } catch (Exception e) {
        }
        sb.append(GETINFO_FILE);
        return sb.toString();
    }

    public static int getGetInfoState(Context context, String str) {
        return context.getSharedPreferences(PREF_FILE, 0).getInt(PREFKEY_GETINFO_STATE + str, Constants.AD_LOADING_NOTHING);
    }

    public static String getIDFA(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        if (sharedPreferences.getBoolean(PREFKEY_IDFA_LIMIT, false)) {
            return BuildConfig.FLAVOR;
        }
        String string = sharedPreferences.getString(PREFKEY_IDFA, BuildConfig.FLAVOR);
        if (string.length() <= 0) {
            return string;
        }
        try {
            return new String(Base64.decode(string, 2));
        } catch (Exception e) {
            return BuildConfig.FLAVOR;
        }
    }

    public static String getIDFA2(Context context) {
        AdvertisingIdClient.Info advertisingIdInfo;
        String str;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        long j = sharedPreferences.getLong(PREFKEY_IDFA_TIME, -1L);
        long time = new Date().getTime();
        if (j != -1 && Constants.IDFA_RETRY_TIME > time - j) {
            if (sharedPreferences.getBoolean(PREFKEY_IDFA_LIMIT, false)) {
                return BuildConfig.FLAVOR;
            }
            try {
                str = new String(Base64.decode(sharedPreferences.getString(PREFKEY_IDFA, BuildConfig.FLAVOR), 2));
            } catch (Exception e) {
            }
            return str == null ? BuildConfig.FLAVOR : str;
        }
        try {
            if (Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient") != null && (advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)) != null) {
                String id = advertisingIdInfo.getId();
                boolean zIsLimitAdTrackingEnabled = advertisingIdInfo.isLimitAdTrackingEnabled();
                if (id != null) {
                    setIDFA(context, id, zIsLimitAdTrackingEnabled);
                    return zIsLimitAdTrackingEnabled ? BuildConfig.FLAVOR : id;
                }
            }
        } catch (GooglePlayServicesNotAvailableException e2) {
        } catch (GooglePlayServicesRepairableException e3) {
        } catch (IOException e4) {
        } catch (ClassNotFoundException e5) {
        } catch (IllegalStateException e6) {
        }
        return BuildConfig.FLAVOR;
    }

    public static IPUA getIPUA(Context context, LogUtil logUtil, String str) throws IOException {
        IPUA ipua = new IPUA();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        String string = sharedPreferences.getString(PREFKEY_IP, BuildConfig.FLAVOR);
        String string2 = sharedPreferences.getString(PREFKEY_CARR, BuildConfig.FLAVOR);
        String string3 = sharedPreferences.getString(PREFKEY_LOC, BuildConfig.FLAVOR);
        long j = sharedPreferences.getLong(PREFKEY_IP_TIME, -1L);
        long time = new Date().getTime();
        if (string == null || string.length() <= 0 || j == -1 || Constants.IP_RETRY_TIME <= time - j) {
            ApiAccessUtil.WebAPIResult ipua2 = ApiAccessUtil.getIPUA(logUtil, str);
            if (ipua2.return_code == 200) {
                try {
                    JSONObject jSONObject = new JSONObject(ipua2.message);
                    Iterator<String> itKeys = jSONObject.keys();
                    while (itKeys.hasNext()) {
                        String next = itKeys.next();
                        if (PREFKEY_IP.equals(next)) {
                            ipua.f3207ip = jSONObject.getString(next);
                            if (Constants.DETAIL_LOG) {
                                logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>ip:" + ipua.f3207ip);
                            }
                        } else if (PREFKEY_CARR.equals(next)) {
                            ipua.carrier = jSONObject.getString(next);
                            if (Constants.DETAIL_LOG) {
                                logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>carrier:" + ipua.carrier);
                            }
                        } else if (PREFKEY_LOC.equals(next)) {
                            ipua.loc = jSONObject.getString(next);
                            if (Constants.DETAIL_LOG) {
                                logUtil.debug(Constants.TAG_NAME, ">>>>>>>>>>>>>>>>>loc:" + ipua.loc);
                            }
                        }
                    }
                    SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                    editorEdit.putString(PREFKEY_IP, ipua.f3207ip);
                    editorEdit.putString(PREFKEY_CARR, ipua.carrier);
                    editorEdit.putString(PREFKEY_LOC, ipua.loc);
                    editorEdit.putLong(PREFKEY_IP_TIME, time);
                    editorEdit.commit();
                } catch (JSONException e) {
                    if (Constants.DETAIL_LOG) {
                        logUtil.debug_e(Constants.TAG_NAME, "JSONException");
                        logUtil.debug_e(Constants.TAG_NAME, e);
                    }
                }
            }
        } else {
            ipua.f3207ip = string;
            ipua.carrier = string2;
            ipua.loc = string3;
        }
        return ipua;
    }

    public static IntersAdPref getIntersAdPref(Context context, String str) {
        IntersAdPref intersAdPref = new IntersAdPref();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        intersAdPref.frequency_ct = sharedPreferences.getInt(str + PREFKEY_INTERS_AD_FREQUENCY_CT, 0);
        intersAdPref.max_ct = sharedPreferences.getInt(str + PREFKEY_INTERS_AD_MAX_CT, 0);
        return intersAdPref;
    }

    public static int getTestMode(Context context) {
        return context.getSharedPreferences(PREF_FILE, 0).getInt(PREFKEY_TESTMODE, -1);
    }

    public static String getUserAgent(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        String string = sharedPreferences.getString(PREFKEY_USERAGENT, BuildConfig.FLAVOR);
        if (string != null && string.length() > 0) {
            return string;
        }
        String userAgentString = new WebView(context.getApplicationContext()).getSettings().getUserAgentString();
        if (userAgentString == null) {
            userAgentString = BuildConfig.FLAVOR;
        }
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putString(PREFKEY_USERAGENT, userAgentString);
        editorEdit.commit();
        return userAgentString;
    }

    public static boolean isFillerCache(Context context, String str) {
        return new File(getFillerFilePath(context, str)).exists();
    }

    public static boolean isGetInfoCache(Context context, String str) {
        return new File(getGetInfoFilePath(context, str)).exists();
    }

    public static String loadStringFile(String str) {
        String string = BuildConfig.FLAVOR;
        File file = new File(str);
        if (file.exists()) {
            BufferedReader bufferedReader = null;
            try {
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                    try {
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            String line = bufferedReader2.readLine();
                            if (line == null) {
                                break;
                            }
                            sb.append(line);
                        }
                        string = sb.toString();
                        if (bufferedReader2 != null) {
                            try {
                                bufferedReader2.close();
                            } catch (IOException e) {
                            }
                        }
                    } catch (FileNotFoundException e2) {
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e3) {
                            }
                        }
                        return string;
                    } catch (UnsupportedEncodingException e4) {
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e5) {
                            }
                        }
                        return string;
                    } catch (IOException e6) {
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e7) {
                            }
                        }
                        return string;
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e8) {
                            }
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e9) {
                } catch (UnsupportedEncodingException e10) {
                } catch (IOException e11) {
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (FileNotFoundException e12) {
            } catch (UnsupportedEncodingException e13) {
            } catch (IOException e14) {
            } catch (Throwable th3) {
                th = th3;
            }
        }
        return string;
    }

    public static void saveStringFile(String str, String str2) {
        if (str2.length() <= 0) {
            return;
        }
        File file = new File(str);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        PrintWriter printWriter = null;
        try {
            try {
                PrintWriter printWriter2 = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
                try {
                    printWriter2.write(str2);
                    if (printWriter2 != null) {
                        printWriter2.close();
                    }
                } catch (FileNotFoundException e) {
                    printWriter = printWriter2;
                    if (printWriter != null) {
                        printWriter.close();
                    }
                } catch (UnsupportedEncodingException e2) {
                    printWriter = printWriter2;
                    if (printWriter != null) {
                        printWriter.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    printWriter = printWriter2;
                    if (printWriter != null) {
                        printWriter.close();
                    }
                    throw th;
                }
            } catch (FileNotFoundException e3) {
            } catch (UnsupportedEncodingException e4) {
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (FileNotFoundException e5) {
        } catch (UnsupportedEncodingException e6) {
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static void setAdLastTime(Context context, String str, int i) {
        long time = new Date().getTime();
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREF_FILE, 0).edit();
        editorEdit.putLong(PREFKEY_AD_LAST_TIME + str, time);
        editorEdit.putInt(PREFKEY_AD_LAST_STATE + str, i);
        editorEdit.commit();
    }

    public static void setCustomValue(Context context, String str, int i) {
        if (context != null) {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREF_FILE, 0).edit();
            editorEdit.putInt(str, i);
            editorEdit.commit();
        }
    }

    public static void setGetInfoState(Context context, String str, int i) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREF_FILE, 0).edit();
        editorEdit.putInt(PREFKEY_GETINFO_STATE + str, i);
        editorEdit.commit();
    }

    public static void setIDFA(Context context, String str, boolean z) {
        if (str != null) {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREF_FILE, 0).edit();
            if (str.length() > 0) {
                try {
                    str = new String(Base64.encodeToString(str.getBytes(), 2));
                } catch (Exception e) {
                }
            }
            long time = new Date().getTime();
            editorEdit.putString(PREFKEY_IDFA, str);
            editorEdit.putBoolean(PREFKEY_IDFA_LIMIT, z);
            editorEdit.putLong(PREFKEY_IDFA_TIME, time);
            editorEdit.commit();
        }
    }

    public static void setIntersAdPref(Context context, String str, IntersAdPref intersAdPref) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREF_FILE, 0).edit();
        editorEdit.putInt(str + PREFKEY_INTERS_AD_MAX_CT, intersAdPref.max_ct);
        editorEdit.putInt(str + PREFKEY_INTERS_AD_FREQUENCY_CT, intersAdPref.frequency_ct);
        editorEdit.commit();
    }

    public static void setTestMode(Context context, int i) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREF_FILE, 0).edit();
        editorEdit.putInt(PREFKEY_TESTMODE, i);
        editorEdit.commit();
    }
}
