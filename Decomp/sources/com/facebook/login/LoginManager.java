package com.facebook.login;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.ansca.corona.CoronaLuaEvent;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.Validate;
import com.facebook.login.LoginClient;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LoginManager {
    private static final String MANAGE_PERMISSION_PREFIX = "manage";
    private static final Set<String> OTHER_PUBLISH_PERMISSIONS = getOtherPublishPermissions();
    private static final String PUBLISH_PERMISSION_PREFIX = "publish";
    private static volatile LoginManager instance;
    private Context context;
    private DefaultAudience defaultAudience = DefaultAudience.FRIENDS;
    private LoginBehavior loginBehavior = LoginBehavior.SSO_WITH_FALLBACK;
    private LoginLogger loginLogger;
    private HashMap<String, String> pendingLoggingExtras;
    private LoginClient.Request pendingLoginRequest;

    private static class ActivityStartActivityDelegate implements StartActivityDelegate {
        private final Activity activity;

        ActivityStartActivityDelegate(Activity activity2) {
            Validate.notNull(activity2, "activity");
            this.activity = activity2;
        }

        public Activity getActivityContext() {
            return this.activity;
        }

        public void startActivityForResult(Intent intent, int i) {
            this.activity.startActivityForResult(intent, i);
        }
    }

    private static class FragmentStartActivityDelegate implements StartActivityDelegate {
        private final Fragment fragment;

        FragmentStartActivityDelegate(Fragment fragment2) {
            Validate.notNull(fragment2, "fragment");
            this.fragment = fragment2;
        }

        public Activity getActivityContext() {
            return this.fragment.getActivity();
        }

        public void startActivityForResult(Intent intent, int i) {
            this.fragment.startActivityForResult(intent, i);
        }
    }

    LoginManager() {
        Validate.sdkInitialized();
    }

    static LoginResult computeLoginResult(LoginClient.Request request, AccessToken accessToken) {
        Set<String> permissions = request.getPermissions();
        HashSet hashSet = new HashSet(accessToken.getPermissions());
        if (request.isRerequest()) {
            hashSet.retainAll(permissions);
        }
        HashSet hashSet2 = new HashSet(permissions);
        hashSet2.removeAll(hashSet);
        return new LoginResult(accessToken, hashSet, hashSet2);
    }

    private LoginClient.Request createLoginRequest(Collection<String> collection) {
        LoginClient.Request request = new LoginClient.Request(this.loginBehavior, Collections.unmodifiableSet(collection != null ? new HashSet(collection) : new HashSet()), this.defaultAudience, FacebookSdk.getApplicationId(), UUID.randomUUID().toString());
        request.setRerequest(AccessToken.getCurrentAccessToken() != null);
        return request;
    }

    private LoginClient.Request createLoginRequestFromResponse(GraphResponse graphResponse) {
        Validate.notNull(graphResponse, CoronaLuaEvent.RESPONSE_KEY);
        AccessToken accessToken = graphResponse.getRequest().getAccessToken();
        return createLoginRequest(accessToken != null ? accessToken.getPermissions() : null);
    }

    private void finishLogin(AccessToken accessToken, FacebookException facebookException, boolean z, FacebookCallback<LoginResult> facebookCallback) {
        if (accessToken != null) {
            AccessToken.setCurrentAccessToken(accessToken);
            Profile.fetchProfileForCurrentAccessToken();
        }
        if (facebookCallback != null) {
            LoginResult computeLoginResult = accessToken != null ? computeLoginResult(this.pendingLoginRequest, accessToken) : null;
            if (z || (computeLoginResult != null && computeLoginResult.getRecentlyGrantedPermissions().size() == 0)) {
                facebookCallback.onCancel();
            } else if (facebookException != null) {
                facebookCallback.onError(facebookException);
            } else if (accessToken != null) {
                facebookCallback.onSuccess(computeLoginResult);
            }
        }
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    private LoginLogger getLogger() {
        if (this.loginLogger == null || !this.loginLogger.getApplicationId().equals(this.pendingLoginRequest.getApplicationId())) {
            this.loginLogger = new LoginLogger(this.context, this.pendingLoginRequest.getApplicationId());
        }
        return this.loginLogger;
    }

    private Intent getLoginActivityIntent(LoginClient.Request request) {
        Intent intent = new Intent();
        intent.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
        intent.setAction(request.getLoginBehavior().toString());
        intent.putExtras(LoginFragment.populateIntentExtras(request));
        return intent;
    }

    private static Set<String> getOtherPublishPermissions() {
        return Collections.unmodifiableSet(new HashSet<String>() {
            {
                add("ads_management");
                add("create_event");
                add("rsvp_event");
            }
        });
    }

    private static boolean isPublishPermission(String str) {
        return str != null && (str.startsWith(PUBLISH_PERMISSION_PREFIX) || str.startsWith(MANAGE_PERMISSION_PREFIX) || OTHER_PUBLISH_PERMISSIONS.contains(str));
    }

    private void logCompleteLogin(LoginClient.Result.Code code, Map<String, String> map, Exception exc) {
        if (this.pendingLoginRequest == null) {
            getLogger().logUnexpectedError("fb_mobile_login_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.");
        } else {
            getLogger().logCompleteLogin(this.pendingLoginRequest.getAuthId(), this.pendingLoggingExtras, code, map, exc);
        }
    }

    private void logStartLogin() {
        getLogger().logStartLogin(this.pendingLoginRequest);
    }

    private boolean resolveIntent(Intent intent) {
        return FacebookSdk.getApplicationContext().getPackageManager().resolveActivity(intent, 0) != null;
    }

    private void startLogin(StartActivityDelegate startActivityDelegate, LoginClient.Request request) throws FacebookException {
        this.pendingLoginRequest = request;
        this.pendingLoggingExtras = new HashMap<>();
        this.context = startActivityDelegate.getActivityContext();
        logStartLogin();
        CallbackManagerImpl.registerStaticCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode(), new CallbackManagerImpl.Callback() {
            public boolean onActivityResult(int i, Intent intent) {
                return LoginManager.this.onActivityResult(i, intent);
            }
        });
        boolean tryLoginActivity = tryLoginActivity(startActivityDelegate, request);
        this.pendingLoggingExtras.put("try_login_activity", tryLoginActivity ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
        if (!tryLoginActivity) {
            FacebookException facebookException = new FacebookException("Log in attempt failed: LoginActivity could not be started");
            logCompleteLogin(LoginClient.Result.Code.ERROR, (Map<String, String>) null, facebookException);
            this.pendingLoginRequest = null;
            throw facebookException;
        }
    }

    private boolean tryLoginActivity(StartActivityDelegate startActivityDelegate, LoginClient.Request request) {
        Intent loginActivityIntent = getLoginActivityIntent(request);
        if (!resolveIntent(loginActivityIntent)) {
            return false;
        }
        try {
            startActivityDelegate.startActivityForResult(loginActivityIntent, LoginClient.getLoginRequestCode());
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    private void validatePublishPermissions(Collection<String> collection) {
        if (collection != null) {
            for (String next : collection) {
                if (!isPublishPermission(next)) {
                    throw new FacebookException(String.format("Cannot pass a read permission (%s) to a request for publish authorization", new Object[]{next}));
                }
            }
        }
    }

    private void validateReadPermissions(Collection<String> collection) {
        if (collection != null) {
            for (String next : collection) {
                if (isPublishPermission(next)) {
                    throw new FacebookException(String.format("Cannot pass a publish or manage permission (%s) to a request for read authorization", new Object[]{next}));
                }
            }
        }
    }

    public DefaultAudience getDefaultAudience() {
        return this.defaultAudience;
    }

    public LoginBehavior getLoginBehavior() {
        return this.loginBehavior;
    }

    /* access modifiers changed from: package-private */
    public LoginClient.Request getPendingLoginRequest() {
        return this.pendingLoginRequest;
    }

    public void logInWithPublishPermissions(Activity activity, Collection<String> collection) {
        validatePublishPermissions(collection);
        startLogin(new ActivityStartActivityDelegate(activity), createLoginRequest(collection));
    }

    public void logInWithPublishPermissions(Fragment fragment, Collection<String> collection) {
        validatePublishPermissions(collection);
        startLogin(new FragmentStartActivityDelegate(fragment), createLoginRequest(collection));
    }

    public void logInWithReadPermissions(Activity activity, Collection<String> collection) {
        validateReadPermissions(collection);
        startLogin(new ActivityStartActivityDelegate(activity), createLoginRequest(collection));
    }

    public void logInWithReadPermissions(Fragment fragment, Collection<String> collection) {
        validateReadPermissions(collection);
        startLogin(new FragmentStartActivityDelegate(fragment), createLoginRequest(collection));
    }

    public void logOut() {
        AccessToken.setCurrentAccessToken((AccessToken) null);
        Profile.setCurrentProfile((Profile) null);
    }

    /* access modifiers changed from: package-private */
    public boolean onActivityResult(int i, Intent intent) {
        return onActivityResult(i, intent, (FacebookCallback<LoginResult>) null);
    }

    /* access modifiers changed from: package-private */
    public boolean onActivityResult(int i, Intent intent, FacebookCallback<LoginResult> facebookCallback) {
        if (this.pendingLoginRequest == null) {
            return false;
        }
        FacebookException facebookException = null;
        AccessToken accessToken = null;
        LoginClient.Result.Code code = LoginClient.Result.Code.ERROR;
        Map<String, String> map = null;
        boolean z = false;
        if (intent != null) {
            LoginClient.Result result = (LoginClient.Result) intent.getParcelableExtra("com.facebook.LoginFragment:Result");
            if (result != null) {
                code = result.code;
                if (i == -1) {
                    if (result.code == LoginClient.Result.Code.SUCCESS) {
                        accessToken = result.token;
                    } else {
                        facebookException = new FacebookAuthorizationException(result.errorMessage);
                    }
                } else if (i == 0) {
                    z = true;
                }
                map = result.loggingExtras;
            }
        } else if (i == 0) {
            z = true;
            code = LoginClient.Result.Code.CANCEL;
        }
        if (facebookException == null && accessToken == null && !z) {
            facebookException = new FacebookException("Unexpected call to LoginManager.onActivityResult");
        }
        logCompleteLogin(code, map, facebookException);
        finishLogin(accessToken, facebookException, z, facebookCallback);
        return true;
    }

    public void registerCallback(CallbackManager callbackManager, final FacebookCallback<LoginResult> facebookCallback) {
        if (!(callbackManager instanceof CallbackManagerImpl)) {
            throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
        }
        ((CallbackManagerImpl) callbackManager).registerCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode(), new CallbackManagerImpl.Callback() {
            public boolean onActivityResult(int i, Intent intent) {
                return LoginManager.this.onActivityResult(i, intent, facebookCallback);
            }
        });
    }

    public void resolveError(Activity activity, GraphResponse graphResponse) {
        startLogin(new ActivityStartActivityDelegate(activity), createLoginRequestFromResponse(graphResponse));
    }

    public void resolveError(Fragment fragment, GraphResponse graphResponse) {
        startLogin(new FragmentStartActivityDelegate(fragment), createLoginRequestFromResponse(graphResponse));
    }

    public LoginManager setDefaultAudience(DefaultAudience defaultAudience2) {
        this.defaultAudience = defaultAudience2;
        return this;
    }

    public LoginManager setLoginBehavior(LoginBehavior loginBehavior2) {
        this.loginBehavior = loginBehavior2;
        return this;
    }
}
