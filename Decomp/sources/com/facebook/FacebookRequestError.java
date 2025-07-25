package com.facebook;

import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.Utility;
import java.net.HttpURLConnection;
import org.json.JSONException;
import org.json.JSONObject;
import twitter4j.HttpResponseCode;

public final class FacebookRequestError {
    private static final String BODY_KEY = "body";
    private static final String CODE_KEY = "code";
    private static final String ERROR_CODE_FIELD_KEY = "code";
    private static final String ERROR_CODE_KEY = "error_code";
    private static final String ERROR_IS_TRANSIENT_KEY = "is_transient";
    private static final String ERROR_KEY = "error";
    private static final String ERROR_MESSAGE_FIELD_KEY = "message";
    private static final String ERROR_MSG_KEY = "error_msg";
    private static final String ERROR_REASON_KEY = "error_reason";
    private static final String ERROR_SUB_CODE_KEY = "error_subcode";
    private static final String ERROR_TYPE_FIELD_KEY = "type";
    private static final String ERROR_USER_MSG_KEY = "error_user_msg";
    private static final String ERROR_USER_TITLE_KEY = "error_user_title";
    static final Range HTTP_RANGE_SUCCESS = new Range(HttpResponseCode.OK, 299);
    public static final int INVALID_ERROR_CODE = -1;
    public static final int INVALID_HTTP_STATUS_CODE = -1;
    private final Object batchRequestResult;
    private final Category category;
    private final HttpURLConnection connection;
    private final int errorCode;
    private final String errorMessage;
    private final String errorRecoveryMessage;
    private final String errorType;
    private final String errorUserMessage;
    private final String errorUserTitle;
    private final FacebookException exception;
    private final JSONObject requestResult;
    private final JSONObject requestResultBody;
    private final int requestStatusCode;
    private final int subErrorCode;

    public enum Category {
        LOGIN_RECOVERABLE,
        OTHER,
        TRANSIENT
    }

    private static class Range {
        private final int end;
        private final int start;

        private Range(int i, int i2) {
            this.start = i;
            this.end = i2;
        }

        /* access modifiers changed from: package-private */
        public boolean contains(int i) {
            return this.start <= i && i <= this.end;
        }
    }

    private FacebookRequestError(int i, int i2, int i3, String str, String str2, String str3, String str4, boolean z, JSONObject jSONObject, JSONObject jSONObject2, Object obj, HttpURLConnection httpURLConnection, FacebookException facebookException) {
        this.requestStatusCode = i;
        this.errorCode = i2;
        this.subErrorCode = i3;
        this.errorType = str;
        this.errorMessage = str2;
        this.requestResultBody = jSONObject;
        this.requestResult = jSONObject2;
        this.batchRequestResult = obj;
        this.connection = httpURLConnection;
        this.errorUserTitle = str3;
        this.errorUserMessage = str4;
        boolean z2 = false;
        if (facebookException != null) {
            this.exception = facebookException;
            z2 = true;
        } else {
            this.exception = new FacebookServiceException(this, str2);
        }
        FacebookRequestErrorClassification errorClassification = getErrorClassification();
        this.category = z2 ? Category.OTHER : errorClassification.classify(i2, i3, z);
        this.errorRecoveryMessage = errorClassification.getRecoveryMessage(this.category);
    }

    public FacebookRequestError(int i, String str, String str2) {
        this(-1, i, -1, str, str2, (String) null, (String) null, false, (JSONObject) null, (JSONObject) null, (Object) null, (HttpURLConnection) null, (FacebookException) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    FacebookRequestError(HttpURLConnection httpURLConnection, Exception exc) {
        this(-1, -1, -1, (String) null, (String) null, (String) null, (String) null, false, (JSONObject) null, (JSONObject) null, (Object) null, httpURLConnection, exc instanceof FacebookException ? (FacebookException) exc : new FacebookException((Throwable) exc));
    }

    static FacebookRequestError checkResponseAndCreateError(JSONObject jSONObject, Object obj, HttpURLConnection httpURLConnection) {
        try {
            if (jSONObject.has("code")) {
                int i = jSONObject.getInt("code");
                Object stringPropertyAsJSON = Utility.getStringPropertyAsJSON(jSONObject, BODY_KEY, GraphResponse.NON_JSON_RESPONSE_PROPERTY);
                if (stringPropertyAsJSON != null && (stringPropertyAsJSON instanceof JSONObject)) {
                    JSONObject jSONObject2 = (JSONObject) stringPropertyAsJSON;
                    String str = null;
                    String str2 = null;
                    String str3 = null;
                    String str4 = null;
                    boolean z = false;
                    int i2 = -1;
                    int i3 = -1;
                    boolean z2 = false;
                    if (jSONObject2.has("error")) {
                        JSONObject jSONObject3 = (JSONObject) Utility.getStringPropertyAsJSON(jSONObject2, "error", (String) null);
                        str = jSONObject3.optString("type", (String) null);
                        str2 = jSONObject3.optString("message", (String) null);
                        i2 = jSONObject3.optInt("code", -1);
                        i3 = jSONObject3.optInt("error_subcode", -1);
                        str3 = jSONObject3.optString(ERROR_USER_MSG_KEY, (String) null);
                        str4 = jSONObject3.optString(ERROR_USER_TITLE_KEY, (String) null);
                        z = jSONObject3.optBoolean(ERROR_IS_TRANSIENT_KEY, false);
                        z2 = true;
                    } else if (jSONObject2.has("error_code") || jSONObject2.has(ERROR_MSG_KEY) || jSONObject2.has(ERROR_REASON_KEY)) {
                        str = jSONObject2.optString(ERROR_REASON_KEY, (String) null);
                        str2 = jSONObject2.optString(ERROR_MSG_KEY, (String) null);
                        i2 = jSONObject2.optInt("error_code", -1);
                        i3 = jSONObject2.optInt("error_subcode", -1);
                        z2 = true;
                    }
                    if (z2) {
                        return new FacebookRequestError(i, i2, i3, str, str2, str4, str3, z, jSONObject2, jSONObject, obj, httpURLConnection, (FacebookException) null);
                    }
                }
                if (!HTTP_RANGE_SUCCESS.contains(i)) {
                    return new FacebookRequestError(i, -1, -1, (String) null, (String) null, (String) null, (String) null, false, jSONObject.has(BODY_KEY) ? (JSONObject) Utility.getStringPropertyAsJSON(jSONObject, BODY_KEY, GraphResponse.NON_JSON_RESPONSE_PROPERTY) : null, jSONObject, obj, httpURLConnection, (FacebookException) null);
                }
            }
        } catch (JSONException e) {
        }
        return null;
    }

    static synchronized FacebookRequestErrorClassification getErrorClassification() {
        FacebookRequestErrorClassification defaultErrorClassification;
        synchronized (FacebookRequestError.class) {
            Utility.FetchedAppSettings appSettingsWithoutQuery = Utility.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId());
            defaultErrorClassification = appSettingsWithoutQuery == null ? FacebookRequestErrorClassification.getDefaultErrorClassification() : appSettingsWithoutQuery.getErrorClassification();
        }
        return defaultErrorClassification;
    }

    public Object getBatchRequestResult() {
        return this.batchRequestResult;
    }

    public Category getCategory() {
        return this.category;
    }

    public HttpURLConnection getConnection() {
        return this.connection;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage != null ? this.errorMessage : this.exception.getLocalizedMessage();
    }

    public String getErrorRecoveryMessage() {
        return this.errorRecoveryMessage;
    }

    public String getErrorType() {
        return this.errorType;
    }

    public String getErrorUserMessage() {
        return this.errorUserMessage;
    }

    public String getErrorUserTitle() {
        return this.errorUserTitle;
    }

    public FacebookException getException() {
        return this.exception;
    }

    public JSONObject getRequestResult() {
        return this.requestResult;
    }

    public JSONObject getRequestResultBody() {
        return this.requestResultBody;
    }

    public int getRequestStatusCode() {
        return this.requestStatusCode;
    }

    public int getSubErrorCode() {
        return this.subErrorCode;
    }

    public String toString() {
        return "{HttpStatus: " + this.requestStatusCode + ", errorCode: " + this.errorCode + ", errorType: " + this.errorType + ", errorMessage: " + getErrorMessage() + "}";
    }
}
