package com.facebook.login;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.PlatformServiceClient;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class GetTokenLoginMethodHandler extends LoginMethodHandler {
    public static final Parcelable.Creator<GetTokenLoginMethodHandler> CREATOR = new Parcelable.Creator() {
        public GetTokenLoginMethodHandler createFromParcel(Parcel parcel) {
            return new GetTokenLoginMethodHandler(parcel);
        }

        public GetTokenLoginMethodHandler[] newArray(int i) {
            return new GetTokenLoginMethodHandler[i];
        }
    };
    private GetTokenClient getTokenClient;

    GetTokenLoginMethodHandler(Parcel parcel) {
        super(parcel);
    }

    GetTokenLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    /* access modifiers changed from: package-private */
    public void cancel() {
        if (this.getTokenClient != null) {
            this.getTokenClient.cancel();
            this.getTokenClient = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void complete(final LoginClient.Request request, final Bundle bundle) {
        String string = bundle.getString(NativeProtocol.EXTRA_USER_ID);
        if (string == null || string.isEmpty()) {
            this.loginClient.notifyBackgroundProcessingStart();
            Utility.getGraphMeRequestWithCacheAsync(bundle.getString(NativeProtocol.EXTRA_ACCESS_TOKEN), new Utility.GraphMeRequestWithCacheCallback() {
                public void onFailure(FacebookException facebookException) {
                    GetTokenLoginMethodHandler.this.loginClient.complete(LoginClient.Result.createErrorResult(GetTokenLoginMethodHandler.this.loginClient.getPendingRequest(), "Caught exception", facebookException.getMessage()));
                }

                public void onSuccess(JSONObject jSONObject) {
                    try {
                        bundle.putString(NativeProtocol.EXTRA_USER_ID, jSONObject.getString(ShareConstants.WEB_DIALOG_PARAM_ID));
                        GetTokenLoginMethodHandler.this.onComplete(request, bundle);
                    } catch (JSONException e) {
                        GetTokenLoginMethodHandler.this.loginClient.complete(LoginClient.Result.createErrorResult(GetTokenLoginMethodHandler.this.loginClient.getPendingRequest(), "Caught exception", e.getMessage()));
                    }
                }
            });
            return;
        }
        onComplete(request, bundle);
    }

    public int describeContents() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public String getNameForLogging() {
        return "get_token";
    }

    /* access modifiers changed from: package-private */
    public void getTokenCompleted(LoginClient.Request request, Bundle bundle) {
        this.getTokenClient = null;
        this.loginClient.notifyBackgroundProcessingStop();
        if (bundle != null) {
            ArrayList<String> stringArrayList = bundle.getStringArrayList(NativeProtocol.EXTRA_PERMISSIONS);
            Set<String> permissions = request.getPermissions();
            if (stringArrayList == null || (permissions != null && !stringArrayList.containsAll(permissions))) {
                HashSet hashSet = new HashSet();
                for (String next : permissions) {
                    if (!stringArrayList.contains(next)) {
                        hashSet.add(next);
                    }
                }
                if (!hashSet.isEmpty()) {
                    addLoggingExtra("new_permissions", TextUtils.join(",", hashSet));
                }
                request.setPermissions(hashSet);
            } else {
                complete(request, bundle);
                return;
            }
        }
        this.loginClient.tryNextHandler();
    }

    /* access modifiers changed from: package-private */
    public void onComplete(LoginClient.Request request, Bundle bundle) {
        this.loginClient.completeAndValidate(LoginClient.Result.createTokenResult(this.loginClient.getPendingRequest(), createAccessTokenFromNativeLogin(bundle, AccessTokenSource.FACEBOOK_APPLICATION_SERVICE, request.getApplicationId())));
    }

    /* access modifiers changed from: package-private */
    public boolean tryAuthorize(final LoginClient.Request request) {
        this.getTokenClient = new GetTokenClient(this.loginClient.getActivity(), request.getApplicationId());
        if (!this.getTokenClient.start()) {
            return false;
        }
        this.loginClient.notifyBackgroundProcessingStart();
        this.getTokenClient.setCompletedListener(new PlatformServiceClient.CompletedListener() {
            public void completed(Bundle bundle) {
                GetTokenLoginMethodHandler.this.getTokenCompleted(request, bundle);
            }
        });
        return true;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
