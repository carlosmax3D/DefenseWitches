package com.facebook.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.R;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class LoginClient implements Parcelable {
    public static final Parcelable.Creator<LoginClient> CREATOR = new Parcelable.Creator() {
        public LoginClient createFromParcel(Parcel parcel) {
            return new LoginClient(parcel);
        }

        public LoginClient[] newArray(int i) {
            return new LoginClient[i];
        }
    };
    BackgroundProcessingListener backgroundProcessingListener;
    boolean checkedInternetPermission;
    int currentHandler = -1;
    Fragment fragment;
    LoginMethodHandler[] handlersToTry;
    Map<String, String> loggingExtras;
    private LoginLogger loginLogger;
    OnCompletedListener onCompletedListener;
    Request pendingRequest;

    interface BackgroundProcessingListener {
        void onBackgroundProcessingStarted();

        void onBackgroundProcessingStopped();
    }

    public interface OnCompletedListener {
        void onCompleted(Result result);
    }

    private static class PermissionsPair {
        List<String> declinedPermissions;
        List<String> grantedPermissions;

        public PermissionsPair(List<String> list, List<String> list2) {
            this.grantedPermissions = list;
            this.declinedPermissions = list2;
        }

        public List<String> getDeclinedPermissions() {
            return this.declinedPermissions;
        }

        public List<String> getGrantedPermissions() {
            return this.grantedPermissions;
        }
    }

    public static class Request implements Parcelable {
        public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator() {
            public Request createFromParcel(Parcel parcel) {
                return new Request(parcel);
            }

            public Request[] newArray(int i) {
                return new Request[i];
            }
        };
        private final String applicationId;
        private final String authId;
        private final DefaultAudience defaultAudience;
        private boolean isRerequest;
        private final LoginBehavior loginBehavior;
        private Set<String> permissions;

        private Request(Parcel parcel) {
            DefaultAudience defaultAudience2 = null;
            this.isRerequest = false;
            String readString = parcel.readString();
            this.loginBehavior = readString != null ? LoginBehavior.valueOf(readString) : null;
            ArrayList arrayList = new ArrayList();
            parcel.readStringList(arrayList);
            this.permissions = new HashSet(arrayList);
            String readString2 = parcel.readString();
            this.defaultAudience = readString2 != null ? DefaultAudience.valueOf(readString2) : defaultAudience2;
            this.applicationId = parcel.readString();
            this.authId = parcel.readString();
            this.isRerequest = parcel.readByte() != 0;
        }

        Request(LoginBehavior loginBehavior2, Set<String> set, DefaultAudience defaultAudience2, String str, String str2) {
            this.isRerequest = false;
            this.loginBehavior = loginBehavior2;
            this.permissions = set == null ? new HashSet<>() : set;
            this.defaultAudience = defaultAudience2;
            this.applicationId = str;
            this.authId = str2;
        }

        public int describeContents() {
            return 0;
        }

        /* access modifiers changed from: package-private */
        public String getApplicationId() {
            return this.applicationId;
        }

        /* access modifiers changed from: package-private */
        public String getAuthId() {
            return this.authId;
        }

        /* access modifiers changed from: package-private */
        public DefaultAudience getDefaultAudience() {
            return this.defaultAudience;
        }

        /* access modifiers changed from: package-private */
        public LoginBehavior getLoginBehavior() {
            return this.loginBehavior;
        }

        /* access modifiers changed from: package-private */
        public Set<String> getPermissions() {
            return this.permissions;
        }

        /* access modifiers changed from: package-private */
        public boolean isRerequest() {
            return this.isRerequest;
        }

        /* access modifiers changed from: package-private */
        public void setPermissions(Set<String> set) {
            Validate.notNull(set, NativeProtocol.RESULT_ARGS_PERMISSIONS);
            this.permissions = set;
        }

        /* access modifiers changed from: package-private */
        public void setRerequest(boolean z) {
            this.isRerequest = z;
        }

        public void writeToParcel(Parcel parcel, int i) {
            String str = null;
            parcel.writeString(this.loginBehavior != null ? this.loginBehavior.name() : null);
            parcel.writeStringList(new ArrayList(this.permissions));
            if (this.defaultAudience != null) {
                str = this.defaultAudience.name();
            }
            parcel.writeString(str);
            parcel.writeString(this.applicationId);
            parcel.writeString(this.authId);
            parcel.writeByte((byte) (this.isRerequest ? 1 : 0));
        }
    }

    public static class Result implements Parcelable {
        public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator() {
            public Result createFromParcel(Parcel parcel) {
                return new Result(parcel);
            }

            public Result[] newArray(int i) {
                return new Result[i];
            }
        };
        final Code code;
        final String errorCode;
        final String errorMessage;
        public Map<String, String> loggingExtras;
        final Request request;
        final AccessToken token;

        enum Code {
            SUCCESS(GraphResponse.SUCCESS_KEY),
            CANCEL("cancel"),
            ERROR("error");
            
            private final String loggingValue;

            private Code(String str) {
                this.loggingValue = str;
            }

            /* access modifiers changed from: package-private */
            public String getLoggingValue() {
                return this.loggingValue;
            }
        }

        private Result(Parcel parcel) {
            this.code = Code.valueOf(parcel.readString());
            this.token = (AccessToken) parcel.readParcelable(AccessToken.class.getClassLoader());
            this.errorMessage = parcel.readString();
            this.errorCode = parcel.readString();
            this.request = (Request) parcel.readParcelable(Request.class.getClassLoader());
            this.loggingExtras = Utility.readStringMapFromParcel(parcel);
        }

        Result(Request request2, Code code2, AccessToken accessToken, String str, String str2) {
            Validate.notNull(code2, "code");
            this.request = request2;
            this.token = accessToken;
            this.errorMessage = str;
            this.code = code2;
            this.errorCode = str2;
        }

        static Result createCancelResult(Request request2, String str) {
            return new Result(request2, Code.CANCEL, (AccessToken) null, str, (String) null);
        }

        static Result createErrorResult(Request request2, String str, String str2) {
            return createErrorResult(request2, str, str2, (String) null);
        }

        static Result createErrorResult(Request request2, String str, String str2, String str3) {
            return new Result(request2, Code.ERROR, (AccessToken) null, TextUtils.join(": ", Utility.asListNoNulls(str, str2)), str3);
        }

        static Result createTokenResult(Request request2, AccessToken accessToken) {
            return new Result(request2, Code.SUCCESS, accessToken, (String) null, (String) null);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.code.name());
            parcel.writeParcelable(this.token, i);
            parcel.writeString(this.errorMessage);
            parcel.writeString(this.errorCode);
            parcel.writeParcelable(this.request, i);
            Utility.writeStringMapToParcel(parcel, this.loggingExtras);
        }
    }

    public LoginClient(Parcel parcel) {
        Parcelable[] readParcelableArray = parcel.readParcelableArray(LoginMethodHandler.class.getClassLoader());
        this.handlersToTry = new LoginMethodHandler[readParcelableArray.length];
        for (int i = 0; i < readParcelableArray.length; i++) {
            this.handlersToTry[i] = (LoginMethodHandler) readParcelableArray[i];
            this.handlersToTry[i].setLoginClient(this);
        }
        this.currentHandler = parcel.readInt();
        this.pendingRequest = (Request) parcel.readParcelable(Request.class.getClassLoader());
        this.loggingExtras = Utility.readStringMapFromParcel(parcel);
    }

    public LoginClient(Fragment fragment2) {
        this.fragment = fragment2;
    }

    private void addLoggingExtra(String str, String str2, boolean z) {
        if (this.loggingExtras == null) {
            this.loggingExtras = new HashMap();
        }
        if (this.loggingExtras.containsKey(str) && z) {
            str2 = this.loggingExtras.get(str) + "," + str2;
        }
        this.loggingExtras.put(str, str2);
    }

    private void completeWithFailure() {
        complete(Result.createErrorResult(this.pendingRequest, "Login attempt failed.", (String) null));
    }

    private static AccessToken createFromTokenWithRefreshedPermissions(AccessToken accessToken, Collection<String> collection, Collection<String> collection2) {
        return new AccessToken(accessToken.getToken(), accessToken.getApplicationId(), accessToken.getUserId(), collection, collection2, accessToken.getSource(), accessToken.getExpires(), accessToken.getLastRefresh());
    }

    private LoginMethodHandler getCurrentHandler() {
        if (this.currentHandler >= 0) {
            return this.handlersToTry[this.currentHandler];
        }
        return null;
    }

    static String getE2E() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("init", System.currentTimeMillis());
        } catch (JSONException e) {
        }
        return jSONObject.toString();
    }

    private LoginMethodHandler[] getHandlersToTry(Request request) {
        ArrayList arrayList = new ArrayList();
        LoginBehavior loginBehavior = request.getLoginBehavior();
        if (loginBehavior.allowsKatanaAuth()) {
            arrayList.add(new GetTokenLoginMethodHandler(this));
            arrayList.add(new KatanaProxyLoginMethodHandler(this));
        }
        if (loginBehavior.allowsWebViewAuth()) {
            arrayList.add(new WebViewLoginMethodHandler(this));
        }
        LoginMethodHandler[] loginMethodHandlerArr = new LoginMethodHandler[arrayList.size()];
        arrayList.toArray(loginMethodHandlerArr);
        return loginMethodHandlerArr;
    }

    private LoginLogger getLogger() {
        if (this.loginLogger == null || !this.loginLogger.getApplicationId().equals(this.pendingRequest.getApplicationId())) {
            this.loginLogger = new LoginLogger(getActivity(), this.pendingRequest.getApplicationId());
        }
        return this.loginLogger;
    }

    public static int getLoginRequestCode() {
        return CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode();
    }

    private static PermissionsPair handlePermissionResponse(GraphResponse graphResponse) {
        JSONObject jSONObject;
        JSONArray optJSONArray;
        String optString;
        if (graphResponse.getError() != null || (jSONObject = graphResponse.getJSONObject()) == null || (optJSONArray = jSONObject.optJSONArray("data")) == null || optJSONArray.length() == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(optJSONArray.length());
        ArrayList arrayList2 = new ArrayList(optJSONArray.length());
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            String optString2 = optJSONObject.optString("permission");
            if (!(optString2 == null || optString2.equals("installed") || (optString = optJSONObject.optString("status")) == null)) {
                if (optString.equals("granted")) {
                    arrayList.add(optString2);
                } else if (optString.equals("declined")) {
                    arrayList2.add(optString2);
                }
            }
        }
        return new PermissionsPair(arrayList, arrayList2);
    }

    private void logAuthorizationMethodComplete(String str, Result result, Map<String, String> map) {
        logAuthorizationMethodComplete(str, result.code.getLoggingValue(), result.errorMessage, result.errorCode, map);
    }

    private void logAuthorizationMethodComplete(String str, String str2, String str3, String str4, Map<String, String> map) {
        if (this.pendingRequest == null) {
            getLogger().logUnexpectedError("fb_mobile_login_method_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.", str);
        } else {
            getLogger().logAuthorizationMethodComplete(this.pendingRequest.getAuthId(), str, str2, str3, str4, map);
        }
    }

    private void notifyOnCompleteListener(Result result) {
        if (this.onCompletedListener != null) {
            this.onCompletedListener.onCompleted(result);
        }
    }

    /* access modifiers changed from: package-private */
    public void authorize(Request request) {
        if (request != null) {
            if (this.pendingRequest != null) {
                throw new FacebookException("Attempted to authorize while a request is pending.");
            } else if (AccessToken.getCurrentAccessToken() == null || checkInternetPermission()) {
                this.pendingRequest = request;
                this.handlersToTry = getHandlersToTry(request);
                tryNextHandler();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void cancelCurrentHandler() {
        if (this.currentHandler >= 0) {
            getCurrentHandler().cancel();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean checkInternetPermission() {
        if (this.checkedInternetPermission) {
            return true;
        }
        if (checkPermission("android.permission.INTERNET") != 0) {
            FragmentActivity activity = getActivity();
            complete(Result.createErrorResult(this.pendingRequest, activity.getString(R.string.com_facebook_internet_permission_error_title), activity.getString(R.string.com_facebook_internet_permission_error_message)));
            return false;
        }
        this.checkedInternetPermission = true;
        return true;
    }

    /* access modifiers changed from: package-private */
    public int checkPermission(String str) {
        return getActivity().checkCallingOrSelfPermission(str);
    }

    /* access modifiers changed from: package-private */
    public void complete(Result result) {
        LoginMethodHandler currentHandler2 = getCurrentHandler();
        if (currentHandler2 != null) {
            logAuthorizationMethodComplete(currentHandler2.getNameForLogging(), result, currentHandler2.methodLoggingExtras);
        }
        if (this.loggingExtras != null) {
            result.loggingExtras = this.loggingExtras;
        }
        this.handlersToTry = null;
        this.currentHandler = -1;
        this.pendingRequest = null;
        this.loggingExtras = null;
        notifyOnCompleteListener(result);
    }

    /* access modifiers changed from: package-private */
    public void completeAndValidate(Result result) {
        if (result.token == null || AccessToken.getCurrentAccessToken() == null) {
            complete(result);
        } else {
            validateSameFbidAndFinish(result);
        }
    }

    /* access modifiers changed from: package-private */
    public GraphRequest createGetPermissionsRequest(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("access_token", str);
        return new GraphRequest((AccessToken) null, "me/permissions", bundle, HttpMethod.GET, (GraphRequest.Callback) null);
    }

    /* access modifiers changed from: package-private */
    public GraphRequest createGetProfileIdRequest(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("fields", ShareConstants.WEB_DIALOG_PARAM_ID);
        bundle.putString("access_token", str);
        return new GraphRequest((AccessToken) null, "me", bundle, HttpMethod.GET, (GraphRequest.Callback) null);
    }

    public int describeContents() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public FragmentActivity getActivity() {
        return this.fragment.getActivity();
    }

    /* access modifiers changed from: package-private */
    public BackgroundProcessingListener getBackgroundProcessingListener() {
        return this.backgroundProcessingListener;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    /* access modifiers changed from: package-private */
    public boolean getInProgress() {
        return this.pendingRequest != null && this.currentHandler >= 0;
    }

    /* access modifiers changed from: package-private */
    public OnCompletedListener getOnCompletedListener() {
        return this.onCompletedListener;
    }

    public Request getPendingRequest() {
        return this.pendingRequest;
    }

    /* access modifiers changed from: package-private */
    public void notifyBackgroundProcessingStart() {
        if (this.backgroundProcessingListener != null) {
            this.backgroundProcessingListener.onBackgroundProcessingStarted();
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyBackgroundProcessingStop() {
        if (this.backgroundProcessingListener != null) {
            this.backgroundProcessingListener.onBackgroundProcessingStopped();
        }
    }

    public boolean onActivityResult(int i, int i2, Intent intent) {
        if (this.pendingRequest != null) {
            return getCurrentHandler().onActivityResult(i, i2, intent);
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void setBackgroundProcessingListener(BackgroundProcessingListener backgroundProcessingListener2) {
        this.backgroundProcessingListener = backgroundProcessingListener2;
    }

    /* access modifiers changed from: package-private */
    public void setFragment(Fragment fragment2) {
        if (this.fragment != null) {
            throw new FacebookException("Can't set fragment once it is already set.");
        }
        this.fragment = fragment2;
    }

    /* access modifiers changed from: package-private */
    public void setOnCompletedListener(OnCompletedListener onCompletedListener2) {
        this.onCompletedListener = onCompletedListener2;
    }

    /* access modifiers changed from: package-private */
    public void startOrContinueAuth(Request request) {
        if (!getInProgress()) {
            authorize(request);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean tryCurrentHandler() {
        boolean z = false;
        LoginMethodHandler currentHandler2 = getCurrentHandler();
        if (!currentHandler2.needsInternetPermission() || checkInternetPermission()) {
            z = currentHandler2.tryAuthorize(this.pendingRequest);
            if (z) {
                getLogger().logAuthorizationMethodStart(this.pendingRequest.getAuthId(), currentHandler2.getNameForLogging());
            } else {
                addLoggingExtra("not_tried", currentHandler2.getNameForLogging(), true);
            }
        } else {
            addLoggingExtra("no_internet_permission", AppEventsConstants.EVENT_PARAM_VALUE_YES, false);
        }
        return z;
    }

    /* access modifiers changed from: package-private */
    public void tryNextHandler() {
        if (this.currentHandler >= 0) {
            logAuthorizationMethodComplete(getCurrentHandler().getNameForLogging(), "skipped", (String) null, (String) null, getCurrentHandler().methodLoggingExtras);
        }
        while (this.handlersToTry != null && this.currentHandler < this.handlersToTry.length - 1) {
            this.currentHandler++;
            if (tryCurrentHandler()) {
                return;
            }
        }
        if (this.pendingRequest != null) {
            completeWithFailure();
        }
    }

    /* access modifiers changed from: package-private */
    public void validateSameFbidAndFinish(Result result) {
        Result createErrorResult;
        if (result.token == null) {
            throw new FacebookException("Can't validate without a token");
        }
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        AccessToken accessToken = result.token;
        if (!(currentAccessToken == null || accessToken == null)) {
            try {
                if (currentAccessToken.getUserId().equals(accessToken.getUserId())) {
                    createErrorResult = Result.createTokenResult(this.pendingRequest, result.token);
                    complete(createErrorResult);
                }
            } catch (Exception e) {
                complete(Result.createErrorResult(this.pendingRequest, "Caught exception", e.getMessage()));
                return;
            }
        }
        createErrorResult = Result.createErrorResult(this.pendingRequest, "User logged in as different Facebook user.", (String) null);
        complete(createErrorResult);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelableArray(this.handlersToTry, i);
        parcel.writeInt(this.currentHandler);
        parcel.writeParcelable(this.pendingRequest, i);
        Utility.writeStringMapToParcel(parcel, this.loggingExtras);
    }
}
