package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginClient;
import java.util.Map;
import jp.stargarage.g2metrics.BuildConfig;
import org.json.JSONException;
import org.json.JSONObject;

class LoginLogger {
    static final String EVENT_EXTRAS_DEFAULT_AUDIENCE = "default_audience";
    static final String EVENT_EXTRAS_LOGIN_BEHAVIOR = "login_behavior";
    static final String EVENT_EXTRAS_MISSING_INTERNET_PERMISSION = "no_internet_permission";
    static final String EVENT_EXTRAS_NEW_PERMISSIONS = "new_permissions";
    static final String EVENT_EXTRAS_NOT_TRIED = "not_tried";
    static final String EVENT_EXTRAS_PERMISSIONS = "permissions";
    static final String EVENT_EXTRAS_REQUEST_CODE = "request_code";
    static final String EVENT_EXTRAS_TRY_LOGIN_ACTIVITY = "try_login_activity";
    static final String EVENT_NAME_LOGIN_COMPLETE = "fb_mobile_login_complete";
    static final String EVENT_NAME_LOGIN_METHOD_COMPLETE = "fb_mobile_login_method_complete";
    static final String EVENT_NAME_LOGIN_METHOD_START = "fb_mobile_login_method_start";
    static final String EVENT_NAME_LOGIN_START = "fb_mobile_login_start";
    static final String EVENT_PARAM_AUTH_LOGGER_ID = "0_auth_logger_id";
    static final String EVENT_PARAM_ERROR_CODE = "4_error_code";
    static final String EVENT_PARAM_ERROR_MESSAGE = "5_error_message";
    static final String EVENT_PARAM_EXTRAS = "6_extras";
    static final String EVENT_PARAM_LOGIN_RESULT = "2_result";
    static final String EVENT_PARAM_METHOD = "3_method";
    static final String EVENT_PARAM_METHOD_RESULT_SKIPPED = "skipped";
    static final String EVENT_PARAM_TIMESTAMP = "1_timestamp_ms";
    private final AppEventsLogger appEventsLogger;
    private String applicationId;

    LoginLogger(Context context, String str) {
        this.applicationId = str;
        this.appEventsLogger = AppEventsLogger.newLogger(context, str);
    }

    static Bundle newAuthorizationLoggingBundle(String str) {
        Bundle bundle = new Bundle();
        bundle.putLong(EVENT_PARAM_TIMESTAMP, System.currentTimeMillis());
        bundle.putString(EVENT_PARAM_AUTH_LOGGER_ID, str);
        bundle.putString(EVENT_PARAM_METHOD, BuildConfig.FLAVOR);
        bundle.putString(EVENT_PARAM_LOGIN_RESULT, BuildConfig.FLAVOR);
        bundle.putString(EVENT_PARAM_ERROR_MESSAGE, BuildConfig.FLAVOR);
        bundle.putString(EVENT_PARAM_ERROR_CODE, BuildConfig.FLAVOR);
        bundle.putString(EVENT_PARAM_EXTRAS, BuildConfig.FLAVOR);
        return bundle;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public void logAuthorizationMethodComplete(String str, String str2, String str3, String str4, String str5, Map<String, String> map) {
        Bundle newAuthorizationLoggingBundle = newAuthorizationLoggingBundle(str);
        if (str3 != null) {
            newAuthorizationLoggingBundle.putString(EVENT_PARAM_LOGIN_RESULT, str3);
        }
        if (str4 != null) {
            newAuthorizationLoggingBundle.putString(EVENT_PARAM_ERROR_MESSAGE, str4);
        }
        if (str5 != null) {
            newAuthorizationLoggingBundle.putString(EVENT_PARAM_ERROR_CODE, str5);
        }
        if (map != null && !map.isEmpty()) {
            newAuthorizationLoggingBundle.putString(EVENT_PARAM_EXTRAS, new JSONObject(map).toString());
        }
        newAuthorizationLoggingBundle.putString(EVENT_PARAM_METHOD, str2);
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_METHOD_COMPLETE, (Double) null, newAuthorizationLoggingBundle);
    }

    public void logAuthorizationMethodStart(String str, String str2) {
        Bundle newAuthorizationLoggingBundle = newAuthorizationLoggingBundle(str);
        newAuthorizationLoggingBundle.putString(EVENT_PARAM_METHOD, str2);
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_METHOD_START, (Double) null, newAuthorizationLoggingBundle);
    }

    public void logCompleteLogin(String str, Map<String, String> map, LoginClient.Result.Code code, Map<String, String> map2, Exception exc) {
        Bundle newAuthorizationLoggingBundle = newAuthorizationLoggingBundle(str);
        if (code != null) {
            newAuthorizationLoggingBundle.putString(EVENT_PARAM_LOGIN_RESULT, code.getLoggingValue());
        }
        if (!(exc == null || exc.getMessage() == null)) {
            newAuthorizationLoggingBundle.putString(EVENT_PARAM_ERROR_MESSAGE, exc.getMessage());
        }
        JSONObject jSONObject = null;
        if (!map.isEmpty()) {
            jSONObject = new JSONObject(map);
        }
        if (map2 != null) {
            if (jSONObject == null) {
                jSONObject = new JSONObject();
            }
            try {
                for (Map.Entry next : map2.entrySet()) {
                    jSONObject.put((String) next.getKey(), next.getValue());
                }
            } catch (JSONException e) {
            }
        }
        if (jSONObject != null) {
            newAuthorizationLoggingBundle.putString(EVENT_PARAM_EXTRAS, jSONObject.toString());
        }
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_COMPLETE, (Double) null, newAuthorizationLoggingBundle);
    }

    public void logStartLogin(LoginClient.Request request) {
        Bundle newAuthorizationLoggingBundle = newAuthorizationLoggingBundle(request.getAuthId());
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(EVENT_EXTRAS_LOGIN_BEHAVIOR, request.getLoginBehavior().toString());
            jSONObject.put(EVENT_EXTRAS_REQUEST_CODE, LoginClient.getLoginRequestCode());
            jSONObject.put("permissions", TextUtils.join(",", request.getPermissions()));
            jSONObject.put("default_audience", request.getDefaultAudience().toString());
            newAuthorizationLoggingBundle.putString(EVENT_PARAM_EXTRAS, jSONObject.toString());
        } catch (JSONException e) {
        }
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_START, (Double) null, newAuthorizationLoggingBundle);
    }

    public void logUnexpectedError(String str, String str2) {
        logUnexpectedError(str, str2, BuildConfig.FLAVOR);
    }

    public void logUnexpectedError(String str, String str2, String str3) {
        Bundle newAuthorizationLoggingBundle = newAuthorizationLoggingBundle(BuildConfig.FLAVOR);
        newAuthorizationLoggingBundle.putString(EVENT_PARAM_LOGIN_RESULT, LoginClient.Result.Code.ERROR.getLoggingValue());
        newAuthorizationLoggingBundle.putString(EVENT_PARAM_ERROR_MESSAGE, str2);
        newAuthorizationLoggingBundle.putString(EVENT_PARAM_METHOD, str3);
        this.appEventsLogger.logSdkEvent(str, (Double) null, newAuthorizationLoggingBundle);
    }
}
