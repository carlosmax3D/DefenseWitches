package jp.tjkapp.adfurikunsdk;

import jp.stargarage.g2metrics.BuildConfig;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class AdError {
    public static final int ADAPI_ERR = -7;
    public static final int AD_EMPTY = -4;
    public static final int BAD_REQUEST = -100;
    public static final int CONNECT_ERR = -6;
    public static final int NOT_FOUND = -3;
    public static final int NOT_NATIVE_ID = -300;
    public static final int NO_ERR = 0;
    public static final int NO_SUPPORT_API = -2;
    public static final int NO_SUPPORT_TYPE = -1;
    public static final int SETTING_EMPTY = -200;
    public static final int UNKNOWN_ERR = -5;

    public static String getErrorMessage(int i) {
        switch (i) {
            case NOT_NATIVE_ID /* -300 */:
                return "not native ad";
            case SETTING_EMPTY /* -200 */:
                return "rate 0%";
            case BAD_REQUEST /* -100 */:
                return "empty app id";
            case ADAPI_ERR /* -7 */:
                return "api access error";
            case CONNECT_ERR /* -6 */:
                return "network access error";
            case UNKNOWN_ERR /* -5 */:
                return "unknown error";
            case -4:
                return "no stock";
            case -3:
                return "not found";
            case -2:
                return "unsupported api";
            case -1:
                return "unsupported ad type";
            case 0:
                return "OK";
            default:
                return BuildConfig.FLAVOR;
        }
    }
}
