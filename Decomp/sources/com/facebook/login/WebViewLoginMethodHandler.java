package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookServiceException;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.WebDialog;
import com.facebook.login.LoginClient;
import java.util.Locale;
import jp.stargarage.g2metrics.BuildConfig;

class WebViewLoginMethodHandler extends LoginMethodHandler {
    public static final Parcelable.Creator<WebViewLoginMethodHandler> CREATOR = new Parcelable.Creator() {
        public WebViewLoginMethodHandler createFromParcel(Parcel parcel) {
            return new WebViewLoginMethodHandler(parcel);
        }

        public WebViewLoginMethodHandler[] newArray(int i) {
            return new WebViewLoginMethodHandler[i];
        }
    };
    private static final String WEB_VIEW_AUTH_HANDLER_STORE = "com.facebook.login.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY";
    private static final String WEB_VIEW_AUTH_HANDLER_TOKEN_KEY = "TOKEN";
    private String e2e;
    private WebDialog loginDialog;

    static class AuthDialogBuilder extends WebDialog.Builder {
        private static final String OAUTH_DIALOG = "oauth";
        static final String REDIRECT_URI = "fbconnect://success";
        private String e2e;
        private boolean isRerequest;

        public AuthDialogBuilder(Context context, String str, Bundle bundle) {
            super(context, str, OAUTH_DIALOG, bundle);
        }

        public WebDialog build() {
            Bundle parameters = getParameters();
            parameters.putString(ServerProtocol.DIALOG_PARAM_REDIRECT_URI, "fbconnect://success");
            parameters.putString("client_id", getApplicationId());
            parameters.putString("e2e", this.e2e);
            parameters.putString(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, ServerProtocol.DIALOG_RESPONSE_TYPE_TOKEN_AND_SIGNED_REQUEST);
            parameters.putString(ServerProtocol.DIALOG_PARAM_RETURN_SCOPES, "true");
            if (this.isRerequest) {
                parameters.putString(ServerProtocol.DIALOG_PARAM_AUTH_TYPE, ServerProtocol.DIALOG_REREQUEST_AUTH_TYPE);
            }
            return new WebDialog(getContext(), OAUTH_DIALOG, parameters, getTheme(), getListener());
        }

        public AuthDialogBuilder setE2E(String str) {
            this.e2e = str;
            return this;
        }

        public AuthDialogBuilder setIsRerequest(boolean z) {
            this.isRerequest = z;
            return this;
        }
    }

    WebViewLoginMethodHandler(Parcel parcel) {
        super(parcel);
        this.e2e = parcel.readString();
    }

    WebViewLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    private String loadCookieToken() {
        return this.loginClient.getActivity().getSharedPreferences(WEB_VIEW_AUTH_HANDLER_STORE, 0).getString(WEB_VIEW_AUTH_HANDLER_TOKEN_KEY, BuildConfig.FLAVOR);
    }

    private void saveCookieToken(String str) {
        this.loginClient.getActivity().getSharedPreferences(WEB_VIEW_AUTH_HANDLER_STORE, 0).edit().putString(WEB_VIEW_AUTH_HANDLER_TOKEN_KEY, str).apply();
    }

    /* access modifiers changed from: package-private */
    public void cancel() {
        if (this.loginDialog != null) {
            this.loginDialog.cancel();
            this.loginDialog = null;
        }
    }

    public int describeContents() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public String getNameForLogging() {
        return "web_view";
    }

    /* access modifiers changed from: package-private */
    public boolean needsInternetPermission() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void onWebDialogComplete(LoginClient.Request request, Bundle bundle, FacebookException facebookException) {
        LoginClient.Result createErrorResult;
        if (bundle != null) {
            if (bundle.containsKey("e2e")) {
                this.e2e = bundle.getString("e2e");
            }
            try {
                AccessToken createAccessTokenFromWebBundle = createAccessTokenFromWebBundle(request.getPermissions(), bundle, AccessTokenSource.WEB_VIEW, request.getApplicationId());
                createErrorResult = LoginClient.Result.createTokenResult(this.loginClient.getPendingRequest(), createAccessTokenFromWebBundle);
                CookieSyncManager.createInstance(this.loginClient.getActivity()).sync();
                saveCookieToken(createAccessTokenFromWebBundle.getToken());
            } catch (FacebookException e) {
                createErrorResult = LoginClient.Result.createErrorResult(this.loginClient.getPendingRequest(), (String) null, e.getMessage());
            }
        } else if (facebookException instanceof FacebookOperationCanceledException) {
            createErrorResult = LoginClient.Result.createCancelResult(this.loginClient.getPendingRequest(), "User canceled log in.");
        } else {
            this.e2e = null;
            String str = null;
            String message = facebookException.getMessage();
            if (facebookException instanceof FacebookServiceException) {
                FacebookRequestError requestError = ((FacebookServiceException) facebookException).getRequestError();
                str = String.format(Locale.ROOT, "%d", new Object[]{Integer.valueOf(requestError.getErrorCode())});
                message = requestError.toString();
            }
            createErrorResult = LoginClient.Result.createErrorResult(this.loginClient.getPendingRequest(), (String) null, message, str);
        }
        if (!Utility.isNullOrEmpty(this.e2e)) {
            logWebLoginCompleted(this.e2e);
        }
        this.loginClient.completeAndValidate(createErrorResult);
    }

    /* access modifiers changed from: package-private */
    public boolean tryAuthorize(final LoginClient.Request request) {
        Bundle bundle = new Bundle();
        if (!Utility.isNullOrEmpty(request.getPermissions())) {
            String join = TextUtils.join(",", request.getPermissions());
            bundle.putString("scope", join);
            addLoggingExtra("scope", join);
        }
        bundle.putString(ServerProtocol.DIALOG_PARAM_DEFAULT_AUDIENCE, request.getDefaultAudience().getNativeProtocolAudience());
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        String token = currentAccessToken != null ? currentAccessToken.getToken() : null;
        if (token == null || !token.equals(loadCookieToken())) {
            Utility.clearFacebookCookies(this.loginClient.getActivity());
            addLoggingExtra("access_token", AppEventsConstants.EVENT_PARAM_VALUE_NO);
        } else {
            bundle.putString("access_token", token);
            addLoggingExtra("access_token", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        }
        AnonymousClass1 r4 = new WebDialog.OnCompleteListener() {
            public void onComplete(Bundle bundle, FacebookException facebookException) {
                WebViewLoginMethodHandler.this.onWebDialogComplete(request, bundle, facebookException);
            }
        };
        this.e2e = LoginClient.getE2E();
        addLoggingExtra("e2e", this.e2e);
        FragmentActivity activity = this.loginClient.getActivity();
        this.loginDialog = new AuthDialogBuilder(activity, request.getApplicationId(), bundle).setE2E(this.e2e).setIsRerequest(request.isRerequest()).setOnCompleteListener(r4).build();
        FacebookDialogFragment facebookDialogFragment = new FacebookDialogFragment();
        facebookDialogFragment.setRetainInstance(true);
        facebookDialogFragment.setDialog(this.loginDialog);
        facebookDialogFragment.show(activity.getSupportFragmentManager(), FacebookDialogFragment.TAG);
        return true;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.e2e);
    }
}
