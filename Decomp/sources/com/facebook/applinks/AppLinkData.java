package com.facebook.applinks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppLinkData {
    private static final String APPLINK_BRIDGE_ARGS_KEY = "bridge_args";
    private static final String APPLINK_METHOD_ARGS_KEY = "method_args";
    private static final String APPLINK_VERSION_KEY = "version";
    public static final String ARGUMENTS_NATIVE_CLASS_KEY = "com.facebook.platform.APPLINK_NATIVE_CLASS";
    public static final String ARGUMENTS_NATIVE_URL = "com.facebook.platform.APPLINK_NATIVE_URL";
    public static final String ARGUMENTS_REFERER_DATA_KEY = "referer_data";
    public static final String ARGUMENTS_TAPTIME_KEY = "com.facebook.platform.APPLINK_TAP_TIME_UTC";
    private static final String BRIDGE_ARGS_METHOD_KEY = "method";
    private static final String BUNDLE_AL_APPLINK_DATA_KEY = "al_applink_data";
    static final String BUNDLE_APPLINK_ARGS_KEY = "com.facebook.platform.APPLINK_ARGS";
    private static final String DEFERRED_APP_LINK_ARGS_FIELD = "applink_args";
    private static final String DEFERRED_APP_LINK_CLASS_FIELD = "applink_class";
    private static final String DEFERRED_APP_LINK_CLICK_TIME_FIELD = "click_time";
    private static final String DEFERRED_APP_LINK_EVENT = "DEFERRED_APP_LINK";
    private static final String DEFERRED_APP_LINK_PATH = "%s/activities";
    private static final String DEFERRED_APP_LINK_URL_FIELD = "applink_url";
    private static final String METHOD_ARGS_REF_KEY = "ref";
    private static final String METHOD_ARGS_TARGET_URL_KEY = "target_url";
    private static final String REFERER_DATA_REF_KEY = "fb_ref";
    private static final String TAG = AppLinkData.class.getCanonicalName();
    private Bundle argumentBundle;
    private JSONObject arguments;
    private String ref;
    private Uri targetUri;

    public interface CompletionHandler {
        void onDeferredAppLinkDataFetched(AppLinkData appLinkData);
    }

    private AppLinkData() {
    }

    public static AppLinkData createFromActivity(Activity activity) {
        Validate.notNull(activity, "activity");
        Intent intent = activity.getIntent();
        if (intent == null) {
            return null;
        }
        AppLinkData createFromAlApplinkData = createFromAlApplinkData(intent);
        if (createFromAlApplinkData == null) {
            createFromAlApplinkData = createFromJson(intent.getStringExtra(BUNDLE_APPLINK_ARGS_KEY));
        }
        return createFromAlApplinkData == null ? createFromUri(intent.getData()) : createFromAlApplinkData;
    }

    private static AppLinkData createFromAlApplinkData(Intent intent) {
        String string;
        Bundle bundleExtra = intent.getBundleExtra(BUNDLE_AL_APPLINK_DATA_KEY);
        if (bundleExtra == null) {
            return null;
        }
        AppLinkData appLinkData = new AppLinkData();
        appLinkData.targetUri = intent.getData();
        if (appLinkData.targetUri == null && (string = bundleExtra.getString(METHOD_ARGS_TARGET_URL_KEY)) != null) {
            appLinkData.targetUri = Uri.parse(string);
        }
        appLinkData.argumentBundle = bundleExtra;
        appLinkData.arguments = null;
        Bundle bundle = bundleExtra.getBundle(ARGUMENTS_REFERER_DATA_KEY);
        if (bundle == null) {
            return appLinkData;
        }
        appLinkData.ref = bundle.getString(REFERER_DATA_REF_KEY);
        return appLinkData;
    }

    private static AppLinkData createFromJson(String str) {
        if (str == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("version");
            if (jSONObject.getJSONObject("bridge_args").getString("method").equals("applink") && string.equals("2")) {
                AppLinkData appLinkData = new AppLinkData();
                appLinkData.arguments = jSONObject.getJSONObject("method_args");
                if (appLinkData.arguments.has(METHOD_ARGS_REF_KEY)) {
                    appLinkData.ref = appLinkData.arguments.getString(METHOD_ARGS_REF_KEY);
                } else if (appLinkData.arguments.has(ARGUMENTS_REFERER_DATA_KEY)) {
                    JSONObject jSONObject2 = appLinkData.arguments.getJSONObject(ARGUMENTS_REFERER_DATA_KEY);
                    if (jSONObject2.has(REFERER_DATA_REF_KEY)) {
                        appLinkData.ref = jSONObject2.getString(REFERER_DATA_REF_KEY);
                    }
                }
                if (appLinkData.arguments.has(METHOD_ARGS_TARGET_URL_KEY)) {
                    appLinkData.targetUri = Uri.parse(appLinkData.arguments.getString(METHOD_ARGS_TARGET_URL_KEY));
                }
                appLinkData.argumentBundle = toBundle(appLinkData.arguments);
                return appLinkData;
            }
        } catch (JSONException e) {
            Log.d(TAG, "Unable to parse AppLink JSON", e);
        } catch (FacebookException e2) {
            Log.d(TAG, "Unable to parse AppLink JSON", e2);
        }
        return null;
    }

    private static AppLinkData createFromUri(Uri uri) {
        if (uri == null) {
            return null;
        }
        AppLinkData appLinkData = new AppLinkData();
        appLinkData.targetUri = uri;
        return appLinkData;
    }

    public static void fetchDeferredAppLinkData(Context context, CompletionHandler completionHandler) {
        fetchDeferredAppLinkData(context, (String) null, completionHandler);
    }

    public static void fetchDeferredAppLinkData(Context context, String str, final CompletionHandler completionHandler) {
        Validate.notNull(context, "context");
        Validate.notNull(completionHandler, "completionHandler");
        if (str == null) {
            str = Utility.getMetadataApplicationId(context);
        }
        Validate.notNull(str, "applicationId");
        final Context applicationContext = context.getApplicationContext();
        final String str2 = str;
        FacebookSdk.getExecutor().execute(new Runnable() {
            public void run() {
                AppLinkData.fetchDeferredAppLinkFromServer(applicationContext, str2, completionHandler);
            }
        });
    }

    /* access modifiers changed from: private */
    public static void fetchDeferredAppLinkFromServer(Context context, String str, CompletionHandler completionHandler) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("event", DEFERRED_APP_LINK_EVENT);
            Utility.setAppEventAttributionParameters(jSONObject, AttributionIdentifiers.getAttributionIdentifiers(context), AppEventsLogger.getAnonymousAppDeviceGUID(context), FacebookSdk.getLimitEventAndDataUsage(context));
            jSONObject.put("application_package_name", context.getPackageName());
            AppLinkData appLinkData = null;
            try {
                JSONObject jSONObject2 = GraphRequest.newPostRequest((AccessToken) null, String.format(DEFERRED_APP_LINK_PATH, new Object[]{str}), jSONObject, (GraphRequest.Callback) null).executeAndWait().getJSONObject();
                if (jSONObject2 != null) {
                    String optString = jSONObject2.optString(DEFERRED_APP_LINK_ARGS_FIELD);
                    long optLong = jSONObject2.optLong(DEFERRED_APP_LINK_CLICK_TIME_FIELD, -1);
                    String optString2 = jSONObject2.optString(DEFERRED_APP_LINK_CLASS_FIELD);
                    String optString3 = jSONObject2.optString(DEFERRED_APP_LINK_URL_FIELD);
                    if (!TextUtils.isEmpty(optString)) {
                        appLinkData = createFromJson(optString);
                        if (optLong != -1) {
                            try {
                                if (appLinkData.arguments != null) {
                                    appLinkData.arguments.put(ARGUMENTS_TAPTIME_KEY, optLong);
                                }
                                if (appLinkData.argumentBundle != null) {
                                    appLinkData.argumentBundle.putString(ARGUMENTS_TAPTIME_KEY, Long.toString(optLong));
                                }
                            } catch (JSONException e) {
                                Log.d(TAG, "Unable to put tap time in AppLinkData.arguments");
                            }
                        }
                        if (optString2 != null) {
                            try {
                                if (appLinkData.arguments != null) {
                                    appLinkData.arguments.put(ARGUMENTS_NATIVE_CLASS_KEY, optString2);
                                }
                                if (appLinkData.argumentBundle != null) {
                                    appLinkData.argumentBundle.putString(ARGUMENTS_NATIVE_CLASS_KEY, optString2);
                                }
                            } catch (JSONException e2) {
                                Log.d(TAG, "Unable to put tap time in AppLinkData.arguments");
                            }
                        }
                        if (optString3 != null) {
                            try {
                                if (appLinkData.arguments != null) {
                                    appLinkData.arguments.put(ARGUMENTS_NATIVE_URL, optString3);
                                }
                                if (appLinkData.argumentBundle != null) {
                                    appLinkData.argumentBundle.putString(ARGUMENTS_NATIVE_URL, optString3);
                                }
                            } catch (JSONException e3) {
                                Log.d(TAG, "Unable to put tap time in AppLinkData.arguments");
                            }
                        }
                    }
                }
            } catch (Exception e4) {
                Utility.logd(TAG, "Unable to fetch deferred applink from server");
            }
            completionHandler.onDeferredAppLinkDataFetched(appLinkData);
        } catch (JSONException e5) {
            throw new FacebookException("An error occurred while preparing deferred app link", (Throwable) e5);
        }
    }

    private static Bundle toBundle(JSONObject jSONObject) throws JSONException {
        Bundle bundle = new Bundle();
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            Object obj = jSONObject.get(next);
            if (obj instanceof JSONObject) {
                bundle.putBundle(next, toBundle((JSONObject) obj));
            } else if (obj instanceof JSONArray) {
                JSONArray jSONArray = (JSONArray) obj;
                if (jSONArray.length() == 0) {
                    bundle.putStringArray(next, new String[0]);
                } else {
                    Object obj2 = jSONArray.get(0);
                    if (obj2 instanceof JSONObject) {
                        Bundle[] bundleArr = new Bundle[jSONArray.length()];
                        for (int i = 0; i < jSONArray.length(); i++) {
                            bundleArr[i] = toBundle(jSONArray.getJSONObject(i));
                        }
                        bundle.putParcelableArray(next, bundleArr);
                    } else if (obj2 instanceof JSONArray) {
                        throw new FacebookException("Nested arrays are not supported.");
                    } else {
                        String[] strArr = new String[jSONArray.length()];
                        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                            strArr[i2] = jSONArray.get(i2).toString();
                        }
                        bundle.putStringArray(next, strArr);
                    }
                }
            } else {
                bundle.putString(next, obj.toString());
            }
        }
        return bundle;
    }

    public Bundle getArgumentBundle() {
        return this.argumentBundle;
    }

    public String getRef() {
        return this.ref;
    }

    public Bundle getRefererData() {
        if (this.argumentBundle != null) {
            return this.argumentBundle.getBundle(ARGUMENTS_REFERER_DATA_KEY);
        }
        return null;
    }

    public Uri getTargetUri() {
        return this.targetUri;
    }
}
