package com.facebook.applinks;

import android.net.Uri;
import android.os.Bundle;
import bolts.AppLink;
import bolts.AppLinkResolver;
import bolts.Continuation;
import bolts.Task;
import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jp.stargarage.g2metrics.BuildConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookAppLinkResolver implements AppLinkResolver {
    private static final String APP_LINK_ANDROID_TARGET_KEY = "android";
    private static final String APP_LINK_KEY = "app_links";
    private static final String APP_LINK_TARGET_APP_NAME_KEY = "app_name";
    private static final String APP_LINK_TARGET_CLASS_KEY = "class";
    private static final String APP_LINK_TARGET_PACKAGE_KEY = "package";
    private static final String APP_LINK_TARGET_SHOULD_FALLBACK_KEY = "should_fallback";
    private static final String APP_LINK_TARGET_URL_KEY = "url";
    private static final String APP_LINK_WEB_TARGET_KEY = "web";
    /* access modifiers changed from: private */
    public final HashMap<Uri, AppLink> cachedAppLinks = new HashMap<>();

    /* access modifiers changed from: private */
    public static AppLink.Target getAndroidTargetFromJson(JSONObject jSONObject) {
        String tryGetStringFromJson = tryGetStringFromJson(jSONObject, APP_LINK_TARGET_PACKAGE_KEY, (String) null);
        if (tryGetStringFromJson == null) {
            return null;
        }
        String tryGetStringFromJson2 = tryGetStringFromJson(jSONObject, APP_LINK_TARGET_CLASS_KEY, (String) null);
        String tryGetStringFromJson3 = tryGetStringFromJson(jSONObject, "app_name", (String) null);
        String tryGetStringFromJson4 = tryGetStringFromJson(jSONObject, "url", (String) null);
        Uri uri = null;
        if (tryGetStringFromJson4 != null) {
            uri = Uri.parse(tryGetStringFromJson4);
        }
        return new AppLink.Target(tryGetStringFromJson, tryGetStringFromJson2, uri, tryGetStringFromJson3);
    }

    /* access modifiers changed from: private */
    public static Uri getWebFallbackUriFromJson(Uri uri, JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("web");
            if (!tryGetBooleanFromJson(jSONObject2, APP_LINK_TARGET_SHOULD_FALLBACK_KEY, true)) {
                return null;
            }
            String tryGetStringFromJson = tryGetStringFromJson(jSONObject2, "url", (String) null);
            Uri uri2 = null;
            if (tryGetStringFromJson != null) {
                uri2 = Uri.parse(tryGetStringFromJson);
            }
            return uri2 == null ? uri : uri2;
        } catch (JSONException e) {
            return uri;
        }
    }

    private static boolean tryGetBooleanFromJson(JSONObject jSONObject, String str, boolean z) {
        try {
            return jSONObject.getBoolean(str);
        } catch (JSONException e) {
            return z;
        }
    }

    private static String tryGetStringFromJson(JSONObject jSONObject, String str, String str2) {
        try {
            return jSONObject.getString(str);
        } catch (JSONException e) {
            return str2;
        }
    }

    public Task<AppLink> getAppLinkFromUrlInBackground(final Uri uri) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(uri);
        return getAppLinkFromUrlsInBackground(arrayList).onSuccess(new Continuation<Map<Uri, AppLink>, AppLink>() {
            public AppLink then(Task<Map<Uri, AppLink>> task) throws Exception {
                return (AppLink) task.getResult().get(uri);
            }
        });
    }

    public Task<Map<Uri, AppLink>> getAppLinkFromUrlsInBackground(List<Uri> list) {
        AppLink appLink;
        final HashMap hashMap = new HashMap();
        final HashSet hashSet = new HashSet();
        StringBuilder sb = new StringBuilder();
        for (Uri next : list) {
            synchronized (this.cachedAppLinks) {
                appLink = this.cachedAppLinks.get(next);
            }
            if (appLink != null) {
                hashMap.put(next, appLink);
            } else {
                if (!hashSet.isEmpty()) {
                    sb.append(',');
                }
                sb.append(next.toString());
                hashSet.add(next);
            }
        }
        if (hashSet.isEmpty()) {
            return Task.forResult(hashMap);
        }
        final Task<TResult>.TaskCompletionSource create = Task.create();
        Bundle bundle = new Bundle();
        bundle.putString("ids", sb.toString());
        bundle.putString("fields", String.format("%s.fields(%s,%s)", new Object[]{APP_LINK_KEY, "android", "web"}));
        new GraphRequest((AccessToken) null, BuildConfig.FLAVOR, bundle, (HttpMethod) null, new GraphRequest.Callback() {
            public void onCompleted(GraphResponse graphResponse) {
                FacebookRequestError error = graphResponse.getError();
                if (error != null) {
                    create.setError(error.getException());
                    return;
                }
                JSONObject jSONObject = graphResponse.getJSONObject();
                if (jSONObject == null) {
                    create.setResult(hashMap);
                    return;
                }
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    Uri uri = (Uri) it.next();
                    if (jSONObject.has(uri.toString())) {
                        try {
                            JSONObject jSONObject2 = jSONObject.getJSONObject(uri.toString()).getJSONObject(FacebookAppLinkResolver.APP_LINK_KEY);
                            JSONArray jSONArray = jSONObject2.getJSONArray("android");
                            int length = jSONArray.length();
                            ArrayList arrayList = new ArrayList(length);
                            for (int i = 0; i < length; i++) {
                                AppLink.Target access$000 = FacebookAppLinkResolver.getAndroidTargetFromJson(jSONArray.getJSONObject(i));
                                if (access$000 != null) {
                                    arrayList.add(access$000);
                                }
                            }
                            AppLink appLink = new AppLink(uri, arrayList, FacebookAppLinkResolver.getWebFallbackUriFromJson(uri, jSONObject2));
                            hashMap.put(uri, appLink);
                            synchronized (FacebookAppLinkResolver.this.cachedAppLinks) {
                                FacebookAppLinkResolver.this.cachedAppLinks.put(uri, appLink);
                            }
                        } catch (JSONException e) {
                        }
                    }
                }
                create.setResult(hashMap);
            }
        }).executeAsync();
        return create.getTask();
    }
}
