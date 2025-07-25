package com.facebook.login;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient;

class KatanaProxyLoginMethodHandler extends LoginMethodHandler {
    public static final Parcelable.Creator<KatanaProxyLoginMethodHandler> CREATOR = new Parcelable.Creator() {
        public KatanaProxyLoginMethodHandler createFromParcel(Parcel parcel) {
            return new KatanaProxyLoginMethodHandler(parcel);
        }

        public KatanaProxyLoginMethodHandler[] newArray(int i) {
            return new KatanaProxyLoginMethodHandler[i];
        }
    };

    KatanaProxyLoginMethodHandler(Parcel parcel) {
        super(parcel);
    }

    KatanaProxyLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    private LoginClient.Result handleResultOk(LoginClient.Request request, Intent intent) {
        Bundle extras = intent.getExtras();
        String string = extras.getString("error");
        if (string == null) {
            string = extras.getString(NativeProtocol.BRIDGE_ARG_ERROR_TYPE);
        }
        String string2 = extras.getString(NativeProtocol.BRIDGE_ARG_ERROR_CODE);
        String string3 = extras.getString(AnalyticsEvents.PARAMETER_SHARE_ERROR_MESSAGE);
        if (string3 == null) {
            string3 = extras.getString(NativeProtocol.BRIDGE_ARG_ERROR_DESCRIPTION);
        }
        String string4 = extras.getString("e2e");
        if (!Utility.isNullOrEmpty(string4)) {
            logWebLoginCompleted(string4);
        }
        if (string == null && string2 == null && string3 == null) {
            try {
                return LoginClient.Result.createTokenResult(request, createAccessTokenFromWebBundle(request.getPermissions(), extras, AccessTokenSource.FACEBOOK_APPLICATION_WEB, request.getApplicationId()));
            } catch (FacebookException e) {
                return LoginClient.Result.createErrorResult(request, (String) null, e.getMessage());
            }
        } else if (!ServerProtocol.errorsProxyAuthDisabled.contains(string)) {
            return ServerProtocol.errorsUserCanceled.contains(string) ? LoginClient.Result.createCancelResult(request, (String) null) : LoginClient.Result.createErrorResult(request, string, string3, string2);
        } else {
            return null;
        }
    }

    public int describeContents() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public String getNameForLogging() {
        return "katana_proxy_auth";
    }

    /* access modifiers changed from: package-private */
    public boolean onActivityResult(int i, int i2, Intent intent) {
        LoginClient.Request pendingRequest = this.loginClient.getPendingRequest();
        LoginClient.Result createCancelResult = intent == null ? LoginClient.Result.createCancelResult(pendingRequest, "Operation canceled") : i2 == 0 ? LoginClient.Result.createCancelResult(pendingRequest, intent.getStringExtra("error")) : i2 != -1 ? LoginClient.Result.createErrorResult(pendingRequest, "Unexpected resultCode from authorization.", (String) null) : handleResultOk(pendingRequest, intent);
        if (createCancelResult != null) {
            this.loginClient.completeAndValidate(createCancelResult);
            return true;
        }
        this.loginClient.tryNextHandler();
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean tryAuthorize(LoginClient.Request request) {
        String e2e = LoginClient.getE2E();
        Intent createProxyAuthIntent = NativeProtocol.createProxyAuthIntent(this.loginClient.getActivity(), request.getApplicationId(), request.getPermissions(), e2e, request.isRerequest(), request.getDefaultAudience());
        addLoggingExtra("e2e", e2e);
        return tryIntent(createProxyAuthIntent, LoginClient.getLoginRequestCode());
    }

    /* access modifiers changed from: protected */
    public boolean tryIntent(Intent intent, int i) {
        if (intent == null) {
            return false;
        }
        try {
            this.loginClient.getFragment().startActivityForResult(intent, i);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
