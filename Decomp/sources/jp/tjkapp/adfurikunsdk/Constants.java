package jp.tjkapp.adfurikunsdk;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class Constants {
    public static final String ADFURIKUN_VERSION = "2.6.4";
    public static final String CLASH_LYTICS_API_KEY = "********************************";
    public static final String CLASH_LYTICS_META_KEY = "com.crashlytics.ApiKey";
    public static final int WEBAPI_NOERR = 0;
    public static final int WEBAPI_CONNECTEDERR = -1;
    public static final int WEBAPI_EXCEPTIONERR = -2;
    public static final int WEBAPI_EMPTYADERR = -3;
    public static final String TAG_NAME = "adfurikun";
    public static final String DEFAULT_APP_ID = "XXXXXXXXXXXXXXXXXXXXXXXX";
    public static final int RETRY_TIME = 10000;
    public static final String DEFAULT_LOCALE = "en";
    public static final int GETINFO_RETRY_TIME = 900000;
    public static final int GETINFO_FAILED_RETRY_TIME = 180000;
    public static final int GETINFO_CONNECTERR_RETRY_TIME = 60000;
    public static final int IP_RETRY_TIME = 60000;
    public static final int IDFA_RETRY_TIME = 60000;
    public static final int DEFAULT_CYCLE_TIME = 30000;
    public static final int DEFAULT_POST_DELAY = 10;
    public static final int WAIT_TIME = 10;
    public static final int WEBVIEW_TIMEOUT = 10000;
    public static final String EXTRA_INTERS_AD_INDEX = "inters_ad_index";
    public static final String EXTRA_WALL_AD_INDEX = "wall_ad_index";
    public static final String REPLACE_IDFA = "[ADF_IDFA]";
    public static final String REPLACE_IDFA2 = "%5BADF_IDFA%5D";
    public static final String REPLACE_PACKAGE = "[ADF_PACKAGE]";
    public static final String REPLACE_PACKAGE2 = "%5BADF_PACKAGE%5D";
    public static final boolean DETAIL_LOG = false;
    public static final boolean AD_RANDOM = true;
    public static final int AD_LOADING_NOTHING = 0;
    public static final int AD_LOADING = 1;
    public static final int AD_LOADING_END = 2;

    private Constants() {
    }
}
