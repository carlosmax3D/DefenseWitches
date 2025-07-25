package bolts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import bolts.AppLink;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.AnalyticsEvents;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppLinkNavigation {
    private static final String KEY_NAME_REFERER_APP_LINK = "referer_app_link";
    private static final String KEY_NAME_REFERER_APP_LINK_APP_NAME = "app_name";
    private static final String KEY_NAME_REFERER_APP_LINK_PACKAGE = "package";
    private static final String KEY_NAME_USER_AGENT = "user_agent";
    private static final String KEY_NAME_VERSION = "version";
    private static final String VERSION = "1.0";
    private static AppLinkResolver defaultResolver;
    private final AppLink appLink;
    private final Bundle appLinkData;
    private final Bundle extras;

    public enum NavigationResult {
        FAILED("failed", false),
        WEB(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_WEB, true),
        APP("app", true);
        
        private String code;
        private boolean succeeded;

        private NavigationResult(String str, boolean z) {
            this.code = str;
            this.succeeded = z;
        }

        public String getCode() {
            return this.code;
        }

        public boolean isSucceeded() {
            return this.succeeded;
        }
    }

    public AppLinkNavigation(AppLink appLink2, Bundle bundle, Bundle bundle2) {
        if (appLink2 == null) {
            throw new IllegalArgumentException("appLink must not be null.");
        }
        bundle = bundle == null ? new Bundle() : bundle;
        bundle2 = bundle2 == null ? new Bundle() : bundle2;
        this.appLink = appLink2;
        this.extras = bundle;
        this.appLinkData = bundle2;
    }

    private Bundle buildAppLinkDataForNavigation(Context context) {
        String string;
        Bundle bundle = new Bundle();
        Bundle bundle2 = new Bundle();
        if (context != null) {
            String packageName = context.getPackageName();
            if (packageName != null) {
                bundle2.putString(KEY_NAME_REFERER_APP_LINK_PACKAGE, packageName);
            }
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (!(applicationInfo == null || (string = context.getString(applicationInfo.labelRes)) == null)) {
                bundle2.putString("app_name", string);
            }
        }
        bundle.putAll(getAppLinkData());
        bundle.putString("target_url", getAppLink().getSourceUrl().toString());
        bundle.putString("version", VERSION);
        bundle.putString(KEY_NAME_USER_AGENT, "Bolts Android 1.1.2");
        bundle.putBundle(KEY_NAME_REFERER_APP_LINK, bundle2);
        bundle.putBundle("extras", getExtras());
        return bundle;
    }

    public static AppLinkResolver getDefaultResolver() {
        return defaultResolver;
    }

    private JSONObject getJSONForBundle(Bundle bundle) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            jSONObject.put(str, getJSONValue(bundle.get(str)));
        }
        return jSONObject;
    }

    private Object getJSONValue(Object obj) throws JSONException {
        if (obj instanceof Bundle) {
            return getJSONForBundle((Bundle) obj);
        }
        if (obj instanceof CharSequence) {
            return obj.toString();
        }
        if (obj instanceof List) {
            JSONArray jSONArray = new JSONArray();
            for (Object jSONValue : (List) obj) {
                jSONArray.put(getJSONValue(jSONValue));
            }
            return jSONArray;
        } else if (obj instanceof SparseArray) {
            JSONArray jSONArray2 = new JSONArray();
            SparseArray sparseArray = (SparseArray) obj;
            for (int i = 0; i < sparseArray.size(); i++) {
                jSONArray2.put(sparseArray.keyAt(i), getJSONValue(sparseArray.valueAt(i)));
            }
            return jSONArray2;
        } else if (obj instanceof Character) {
            return obj.toString();
        } else {
            if (obj instanceof Boolean) {
                return obj;
            }
            if (obj instanceof Number) {
                return ((obj instanceof Double) || (obj instanceof Float)) ? Double.valueOf(((Number) obj).doubleValue()) : Long.valueOf(((Number) obj).longValue());
            }
            if (obj instanceof boolean[]) {
                JSONArray jSONArray3 = new JSONArray();
                for (boolean valueOf : (boolean[]) obj) {
                    jSONArray3.put(getJSONValue(Boolean.valueOf(valueOf)));
                }
                return jSONArray3;
            } else if (obj instanceof char[]) {
                JSONArray jSONArray4 = new JSONArray();
                for (char valueOf2 : (char[]) obj) {
                    jSONArray4.put(getJSONValue(Character.valueOf(valueOf2)));
                }
                return jSONArray4;
            } else if (obj instanceof CharSequence[]) {
                JSONArray jSONArray5 = new JSONArray();
                for (CharSequence jSONValue2 : (CharSequence[]) obj) {
                    jSONArray5.put(getJSONValue(jSONValue2));
                }
                return jSONArray5;
            } else if (obj instanceof double[]) {
                JSONArray jSONArray6 = new JSONArray();
                for (double valueOf3 : (double[]) obj) {
                    jSONArray6.put(getJSONValue(Double.valueOf(valueOf3)));
                }
                return jSONArray6;
            } else if (obj instanceof float[]) {
                JSONArray jSONArray7 = new JSONArray();
                for (float valueOf4 : (float[]) obj) {
                    jSONArray7.put(getJSONValue(Float.valueOf(valueOf4)));
                }
                return jSONArray7;
            } else if (obj instanceof int[]) {
                JSONArray jSONArray8 = new JSONArray();
                for (int valueOf5 : (int[]) obj) {
                    jSONArray8.put(getJSONValue(Integer.valueOf(valueOf5)));
                }
                return jSONArray8;
            } else if (obj instanceof long[]) {
                JSONArray jSONArray9 = new JSONArray();
                for (long valueOf6 : (long[]) obj) {
                    jSONArray9.put(getJSONValue(Long.valueOf(valueOf6)));
                }
                return jSONArray9;
            } else if (obj instanceof short[]) {
                JSONArray jSONArray10 = new JSONArray();
                for (short valueOf7 : (short[]) obj) {
                    jSONArray10.put(getJSONValue(Short.valueOf(valueOf7)));
                }
                return jSONArray10;
            } else if (!(obj instanceof String[])) {
                return null;
            } else {
                JSONArray jSONArray11 = new JSONArray();
                for (String jSONValue3 : (String[]) obj) {
                    jSONArray11.put(getJSONValue(jSONValue3));
                }
                return jSONArray11;
            }
        }
    }

    private static AppLinkResolver getResolver(Context context) {
        return getDefaultResolver() != null ? getDefaultResolver() : new WebViewAppLinkResolver(context);
    }

    public static NavigationResult navigate(Context context, AppLink appLink2) {
        return new AppLinkNavigation(appLink2, (Bundle) null, (Bundle) null).navigate(context);
    }

    public static Task<NavigationResult> navigateInBackground(Context context, Uri uri) {
        return navigateInBackground(context, uri, getResolver(context));
    }

    public static Task<NavigationResult> navigateInBackground(final Context context, Uri uri, AppLinkResolver appLinkResolver) {
        return appLinkResolver.getAppLinkFromUrlInBackground(uri).onSuccess(new Continuation<AppLink, NavigationResult>() {
            public NavigationResult then(Task<AppLink> task) throws Exception {
                return AppLinkNavigation.navigate(context, task.getResult());
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    public static Task<NavigationResult> navigateInBackground(Context context, String str) {
        return navigateInBackground(context, str, getResolver(context));
    }

    public static Task<NavigationResult> navigateInBackground(Context context, String str, AppLinkResolver appLinkResolver) {
        return navigateInBackground(context, Uri.parse(str), appLinkResolver);
    }

    public static Task<NavigationResult> navigateInBackground(Context context, URL url) {
        return navigateInBackground(context, url, getResolver(context));
    }

    public static Task<NavigationResult> navigateInBackground(Context context, URL url, AppLinkResolver appLinkResolver) {
        return navigateInBackground(context, Uri.parse(url.toString()), appLinkResolver);
    }

    private void sendAppLinkNavigateEventBroadcast(Context context, Intent intent, NavigationResult navigationResult, JSONException jSONException) {
        HashMap hashMap = new HashMap();
        if (jSONException != null) {
            hashMap.put("error", jSONException.getLocalizedMessage());
        }
        hashMap.put(GraphResponse.SUCCESS_KEY, navigationResult.isSucceeded() ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
        hashMap.put("type", navigationResult.getCode());
        MeasurementEvent.sendBroadcastEvent(context, MeasurementEvent.APP_LINK_NAVIGATE_OUT_EVENT_NAME, intent, hashMap);
    }

    public static void setDefaultResolver(AppLinkResolver appLinkResolver) {
        defaultResolver = appLinkResolver;
    }

    public AppLink getAppLink() {
        return this.appLink;
    }

    public Bundle getAppLinkData() {
        return this.appLinkData;
    }

    public Bundle getExtras() {
        return this.extras;
    }

    public NavigationResult navigate(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Bundle buildAppLinkDataForNavigation = buildAppLinkDataForNavigation(context);
        Intent intent = null;
        Iterator<AppLink.Target> it = getAppLink().getTargets().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            AppLink.Target next = it.next();
            Intent intent2 = new Intent("android.intent.action.VIEW");
            if (next.getUrl() != null) {
                intent2.setData(next.getUrl());
            } else {
                intent2.setData(this.appLink.getSourceUrl());
            }
            intent2.setPackage(next.getPackageName());
            if (next.getClassName() != null) {
                intent2.setClassName(next.getPackageName(), next.getClassName());
            }
            intent2.putExtra("al_applink_data", buildAppLinkDataForNavigation);
            if (packageManager.resolveActivity(intent2, 65536) != null) {
                intent = intent2;
                break;
            }
        }
        Intent intent3 = null;
        NavigationResult navigationResult = NavigationResult.FAILED;
        if (intent != null) {
            intent3 = intent;
            navigationResult = NavigationResult.APP;
        } else {
            Uri webUrl = getAppLink().getWebUrl();
            if (webUrl != null) {
                try {
                    intent3 = new Intent("android.intent.action.VIEW", webUrl.buildUpon().appendQueryParameter("al_applink_data", getJSONForBundle(buildAppLinkDataForNavigation).toString()).build());
                    navigationResult = NavigationResult.WEB;
                } catch (JSONException e) {
                    sendAppLinkNavigateEventBroadcast(context, intent, NavigationResult.FAILED, e);
                    throw new RuntimeException(e);
                }
            }
        }
        sendAppLinkNavigateEventBroadcast(context, intent3, navigationResult, (JSONException) null);
        if (intent3 != null) {
            context.startActivity(intent3);
        }
        return navigationResult;
    }
}
