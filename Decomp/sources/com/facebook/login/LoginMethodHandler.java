package com.facebook.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

abstract class LoginMethodHandler implements Parcelable {
    protected LoginClient loginClient;
    Map<String, String> methodLoggingExtras;

    LoginMethodHandler(Parcel parcel) {
        this.methodLoggingExtras = Utility.readStringMapFromParcel(parcel);
    }

    LoginMethodHandler(LoginClient loginClient2) {
        this.loginClient = loginClient2;
    }

    static AccessToken createAccessTokenFromNativeLogin(Bundle bundle, AccessTokenSource accessTokenSource, String str) {
        Date bundleLongAsDate = Utility.getBundleLongAsDate(bundle, NativeProtocol.EXTRA_EXPIRES_SECONDS_SINCE_EPOCH, new Date(0));
        ArrayList<String> stringArrayList = bundle.getStringArrayList(NativeProtocol.EXTRA_PERMISSIONS);
        String string = bundle.getString(NativeProtocol.EXTRA_ACCESS_TOKEN);
        if (Utility.isNullOrEmpty(string)) {
            return null;
        }
        return new AccessToken(string, str, bundle.getString(NativeProtocol.EXTRA_USER_ID), stringArrayList, (Collection<String>) null, accessTokenSource, bundleLongAsDate, new Date());
    }

    public static AccessToken createAccessTokenFromWebBundle(Collection<String> collection, Bundle bundle, AccessTokenSource accessTokenSource, String str) throws FacebookException {
        Date bundleLongAsDate = Utility.getBundleLongAsDate(bundle, AccessToken.EXPIRES_IN_KEY, new Date());
        String string = bundle.getString("access_token");
        String string2 = bundle.getString("granted_scopes");
        if (!Utility.isNullOrEmpty(string2)) {
            collection = new ArrayList<>(Arrays.asList(string2.split(",")));
        }
        String string3 = bundle.getString("denied_scopes");
        ArrayList arrayList = null;
        if (!Utility.isNullOrEmpty(string3)) {
            arrayList = new ArrayList(Arrays.asList(string3.split(",")));
        }
        if (Utility.isNullOrEmpty(string)) {
            return null;
        }
        return new AccessToken(string, str, getUserIDFromSignedRequest(bundle.getString("signed_request")), collection, arrayList, accessTokenSource, bundleLongAsDate, new Date());
    }

    private static String getUserIDFromSignedRequest(String str) throws FacebookException {
        if (str == null || str.isEmpty()) {
            throw new FacebookException("Authorization response does not contain the signed_request");
        }
        try {
            String[] split = str.split("\\.");
            if (split.length == 2) {
                return new JSONObject(new String(Base64.decode(split[1], 0), "UTF-8")).getString("user_id");
            }
        } catch (UnsupportedEncodingException | JSONException e) {
        }
        throw new FacebookException("Failed to retrieve user_id from signed_request");
    }

    /* access modifiers changed from: protected */
    public void addLoggingExtra(String str, Object obj) {
        if (this.methodLoggingExtras == null) {
            this.methodLoggingExtras = new HashMap();
        }
        this.methodLoggingExtras.put(str, obj == null ? null : obj.toString());
    }

    /* access modifiers changed from: package-private */
    public void cancel() {
    }

    /* access modifiers changed from: package-private */
    public abstract String getNameForLogging();

    /* access modifiers changed from: protected */
    public void logWebLoginCompleted(String str) {
        String applicationId = this.loginClient.getPendingRequest().getApplicationId();
        AppEventsLogger newLogger = AppEventsLogger.newLogger((Context) this.loginClient.getActivity(), applicationId);
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsEvents.PARAMETER_WEB_LOGIN_E2E, str);
        bundle.putLong(AnalyticsEvents.PARAMETER_WEB_LOGIN_SWITCHBACK_TIME, System.currentTimeMillis());
        bundle.putString("app_id", applicationId);
        newLogger.logSdkEvent(AnalyticsEvents.EVENT_WEB_LOGIN_COMPLETE, (Double) null, bundle);
    }

    /* access modifiers changed from: package-private */
    public boolean needsInternetPermission() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean onActivityResult(int i, int i2, Intent intent) {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void setLoginClient(LoginClient loginClient2) {
        if (this.loginClient != null) {
            throw new FacebookException("Can't set LoginClient if it is already set.");
        }
        this.loginClient = loginClient2;
    }

    /* access modifiers changed from: package-private */
    public abstract boolean tryAuthorize(LoginClient.Request request);

    public void writeToParcel(Parcel parcel, int i) {
        Utility.writeStringMapToParcel(parcel, this.methodLoggingExtras);
    }
}
