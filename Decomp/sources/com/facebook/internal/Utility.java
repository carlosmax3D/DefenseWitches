package com.facebook.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.widget.ExploreByTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.share.internal.ShareConstants;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import jp.stargarage.g2metrics.BuildConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class Utility {
    private static final String APPLICATION_FIELDS = "fields";
    private static final String APP_SETTINGS_PREFS_KEY_FORMAT = "com.facebook.internal.APP_SETTINGS.%s";
    private static final String APP_SETTINGS_PREFS_STORE = "com.facebook.internal.preferences.APP_SETTINGS";
    private static final String APP_SETTING_ANDROID_SDK_ERROR_CATEGORIES = "android_sdk_error_categories";
    private static final String APP_SETTING_DIALOG_CONFIGS = "android_dialog_configs";
    private static final String[] APP_SETTING_FIELDS = {APP_SETTING_SUPPORTS_IMPLICIT_SDK_LOGGING, APP_SETTING_NUX_CONTENT, APP_SETTING_NUX_ENABLED, APP_SETTING_DIALOG_CONFIGS, APP_SETTING_ANDROID_SDK_ERROR_CATEGORIES};
    private static final String APP_SETTING_NUX_CONTENT = "gdpv4_nux_content";
    private static final String APP_SETTING_NUX_ENABLED = "gdpv4_nux_enabled";
    private static final String APP_SETTING_SUPPORTS_IMPLICIT_SDK_LOGGING = "supports_implicit_sdk_logging";
    public static final int DEFAULT_STREAM_BUFFER_SIZE = 8192;
    private static final String DIALOG_CONFIG_DIALOG_NAME_FEATURE_NAME_SEPARATOR = "\\|";
    private static final String DIALOG_CONFIG_NAME_KEY = "name";
    private static final String DIALOG_CONFIG_URL_KEY = "url";
    private static final String DIALOG_CONFIG_VERSIONS_KEY = "versions";
    private static final String EXTRA_APP_EVENTS_INFO_FORMAT_VERSION = "a1";
    private static final String HASH_ALGORITHM_MD5 = "MD5";
    private static final String HASH_ALGORITHM_SHA1 = "SHA-1";
    static final String LOG_TAG = "FacebookSDK";
    private static final String URL_SCHEME = "https";
    private static final String UTF8 = "UTF-8";
    private static Map<String, FetchedAppSettings> fetchedAppSettings = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public static AtomicBoolean loadingSettings = new AtomicBoolean(false);

    public static class DialogFeatureConfig {
        private String dialogName;
        private Uri fallbackUrl;
        private String featureName;
        private int[] featureVersionSpec;

        private DialogFeatureConfig(String str, String str2, Uri uri, int[] iArr) {
            this.dialogName = str;
            this.featureName = str2;
            this.fallbackUrl = uri;
            this.featureVersionSpec = iArr;
        }

        /* access modifiers changed from: private */
        public static DialogFeatureConfig parseDialogConfig(JSONObject jSONObject) {
            String optString = jSONObject.optString("name");
            if (Utility.isNullOrEmpty(optString)) {
                return null;
            }
            String[] split = optString.split(Utility.DIALOG_CONFIG_DIALOG_NAME_FEATURE_NAME_SEPARATOR);
            if (split.length != 2) {
                return null;
            }
            String str = split[0];
            String str2 = split[1];
            if (Utility.isNullOrEmpty(str) || Utility.isNullOrEmpty(str2)) {
                return null;
            }
            String optString2 = jSONObject.optString("url");
            Uri uri = null;
            if (!Utility.isNullOrEmpty(optString2)) {
                uri = Uri.parse(optString2);
            }
            return new DialogFeatureConfig(str, str2, uri, parseVersionSpec(jSONObject.optJSONArray(Utility.DIALOG_CONFIG_VERSIONS_KEY)));
        }

        private static int[] parseVersionSpec(JSONArray jSONArray) {
            int[] iArr = null;
            if (jSONArray != null) {
                int length = jSONArray.length();
                iArr = new int[length];
                for (int i = 0; i < length; i++) {
                    int optInt = jSONArray.optInt(i, -1);
                    if (optInt == -1) {
                        String optString = jSONArray.optString(i);
                        if (!Utility.isNullOrEmpty(optString)) {
                            try {
                                optInt = Integer.parseInt(optString);
                            } catch (NumberFormatException e) {
                                Utility.logd(Utility.LOG_TAG, (Exception) e);
                                optInt = -1;
                            }
                        }
                    }
                    iArr[i] = optInt;
                }
            }
            return iArr;
        }

        public String getDialogName() {
            return this.dialogName;
        }

        public Uri getFallbackUrl() {
            return this.fallbackUrl;
        }

        public String getFeatureName() {
            return this.featureName;
        }

        public int[] getVersionSpec() {
            return this.featureVersionSpec;
        }
    }

    public static class FetchedAppSettings {
        private Map<String, Map<String, DialogFeatureConfig>> dialogConfigMap;
        private FacebookRequestErrorClassification errorClassification;
        private String nuxContent;
        private boolean nuxEnabled;
        private boolean supportsImplicitLogging;

        private FetchedAppSettings(boolean z, String str, boolean z2, Map<String, Map<String, DialogFeatureConfig>> map, FacebookRequestErrorClassification facebookRequestErrorClassification) {
            this.supportsImplicitLogging = z;
            this.nuxContent = str;
            this.nuxEnabled = z2;
            this.dialogConfigMap = map;
            this.errorClassification = facebookRequestErrorClassification;
        }

        public Map<String, Map<String, DialogFeatureConfig>> getDialogConfigurations() {
            return this.dialogConfigMap;
        }

        public FacebookRequestErrorClassification getErrorClassification() {
            return this.errorClassification;
        }

        public String getNuxContent() {
            return this.nuxContent;
        }

        public boolean getNuxEnabled() {
            return this.nuxEnabled;
        }

        public boolean supportsImplicitLogging() {
            return this.supportsImplicitLogging;
        }
    }

    public interface GraphMeRequestWithCacheCallback {
        void onFailure(FacebookException facebookException);

        void onSuccess(JSONObject jSONObject);
    }

    public interface Mapper<T, K> {
        K apply(T t);
    }

    public interface Predicate<T> {
        boolean apply(T t);
    }

    public static <T> boolean areObjectsEqual(T t, T t2) {
        return t == null ? t2 == null : t.equals(t2);
    }

    public static <T> ArrayList<T> arrayList(T... tArr) {
        ArrayList<T> arrayList = new ArrayList<>(tArr.length);
        for (T add : tArr) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public static <T> List<T> asListNoNulls(T... tArr) {
        ArrayList arrayList = new ArrayList();
        for (T t : tArr) {
            if (t != null) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static JSONObject awaitGetGraphMeRequestWithCache(String str) {
        JSONObject profileInformation = ProfileInformationCache.getProfileInformation(str);
        if (profileInformation != null) {
            return profileInformation;
        }
        GraphResponse executeAndWait = getGraphMeRequestWithCache(str).executeAndWait();
        if (executeAndWait.getError() != null) {
            return null;
        }
        return executeAndWait.getJSONObject();
    }

    public static Uri buildUri(String str, String str2, Bundle bundle) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(URL_SCHEME);
        builder.authority(str);
        builder.path(str2);
        if (bundle != null) {
            for (String str3 : bundle.keySet()) {
                Object obj = bundle.get(str3);
                if (obj instanceof String) {
                    builder.appendQueryParameter(str3, (String) obj);
                }
            }
        }
        return builder.build();
    }

    public static void clearCaches(Context context) {
        ImageDownloader.clearCache(context);
    }

    private static void clearCookiesForDomain(Context context, String str) {
        CookieSyncManager.createInstance(context).sync();
        CookieManager instance = CookieManager.getInstance();
        String cookie = instance.getCookie(str);
        if (cookie != null) {
            for (String split : cookie.split(";")) {
                String[] split2 = split.split("=");
                if (split2.length > 0) {
                    instance.setCookie(str, split2[0].trim() + "=;expires=Sat, 1 Jan 2000 00:00:01 UTC;");
                }
            }
            instance.removeExpiredCookie();
        }
    }

    public static void clearFacebookCookies(Context context) {
        clearCookiesForDomain(context, "facebook.com");
        clearCookiesForDomain(context, ".facebook.com");
        clearCookiesForDomain(context, "https://facebook.com");
        clearCookiesForDomain(context, "https://.facebook.com");
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static String coerceValueIfNullOrEmpty(String str, String str2) {
        return isNullOrEmpty(str) ? str2 : str;
    }

    static Map<String, Object> convertJSONObjectToHashMap(JSONObject jSONObject) {
        HashMap hashMap = new HashMap();
        JSONArray names = jSONObject.names();
        for (int i = 0; i < names.length(); i++) {
            try {
                String string = names.getString(i);
                Object obj = jSONObject.get(string);
                if (obj instanceof JSONObject) {
                    obj = convertJSONObjectToHashMap((JSONObject) obj);
                }
                hashMap.put(string, obj);
            } catch (JSONException e) {
            }
        }
        return hashMap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0026  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x002b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int copyAndCloseInputStream(java.io.InputStream r6, java.io.OutputStream r7) throws java.io.IOException {
        /*
            r1 = 0
            r4 = 0
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ all -> 0x0023 }
            r2.<init>(r6)     // Catch:{ all -> 0x0023 }
            r5 = 8192(0x2000, float:1.14794E-41)
            byte[] r0 = new byte[r5]     // Catch:{ all -> 0x002f }
        L_0x000b:
            int r3 = r2.read(r0)     // Catch:{ all -> 0x002f }
            r5 = -1
            if (r3 == r5) goto L_0x0018
            r5 = 0
            r7.write(r0, r5, r3)     // Catch:{ all -> 0x002f }
            int r4 = r4 + r3
            goto L_0x000b
        L_0x0018:
            if (r2 == 0) goto L_0x001d
            r2.close()
        L_0x001d:
            if (r6 == 0) goto L_0x0022
            r6.close()
        L_0x0022:
            return r4
        L_0x0023:
            r5 = move-exception
        L_0x0024:
            if (r1 == 0) goto L_0x0029
            r1.close()
        L_0x0029:
            if (r6 == 0) goto L_0x002e
            r6.close()
        L_0x002e:
            throw r5
        L_0x002f:
            r5 = move-exception
            r1 = r2
            goto L_0x0024
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.Utility.copyAndCloseInputStream(java.io.InputStream, java.io.OutputStream):int");
    }

    public static void deleteDirectory(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File deleteDirectory : file.listFiles()) {
                    deleteDirectory(deleteDirectory);
                }
            }
            file.delete();
        }
    }

    public static void disconnectQuietly(URLConnection uRLConnection) {
        if (uRLConnection instanceof HttpURLConnection) {
            ((HttpURLConnection) uRLConnection).disconnect();
        }
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (T next : list) {
            if (predicate.apply(next)) {
                arrayList.add(next);
            }
        }
        if (arrayList.size() == 0) {
            arrayList = null;
        }
        return arrayList;
    }

    public static String getActivityName(Context context) {
        return context == null ? "null" : context == context.getApplicationContext() ? "unknown" : context.getClass().getSimpleName();
    }

    /* access modifiers changed from: private */
    public static JSONObject getAppSettingsQueryResponse(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(APPLICATION_FIELDS, TextUtils.join(",", APP_SETTING_FIELDS));
        GraphRequest newGraphPathRequest = GraphRequest.newGraphPathRequest((AccessToken) null, str, (GraphRequest.Callback) null);
        newGraphPathRequest.setSkipClientToken(true);
        newGraphPathRequest.setParameters(bundle);
        return newGraphPathRequest.executeAndWait().getJSONObject();
    }

    public static FetchedAppSettings getAppSettingsWithoutQuery(String str) {
        if (str != null) {
            return fetchedAppSettings.get(str);
        }
        return null;
    }

    public static Date getBundleLongAsDate(Bundle bundle, String str, Date date) {
        long parseLong;
        if (bundle == null) {
            return null;
        }
        Object obj = bundle.get(str);
        if (obj instanceof Long) {
            parseLong = ((Long) obj).longValue();
        } else if (!(obj instanceof String)) {
            return null;
        } else {
            try {
                parseLong = Long.parseLong((String) obj);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return parseLong == 0 ? new Date(Long.MAX_VALUE) : new Date(date.getTime() + (1000 * parseLong));
    }

    public static DialogFeatureConfig getDialogFeatureConfig(String str, String str2, String str3) {
        FetchedAppSettings fetchedAppSettings2;
        Map map;
        if (isNullOrEmpty(str2) || isNullOrEmpty(str3) || (fetchedAppSettings2 = fetchedAppSettings.get(str)) == null || (map = fetchedAppSettings2.getDialogConfigurations().get(str2)) == null) {
            return null;
        }
        return (DialogFeatureConfig) map.get(str3);
    }

    private static GraphRequest getGraphMeRequestWithCache(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(APPLICATION_FIELDS, "id,name,first_name,middle_name,last_name,link");
        bundle.putString("access_token", str);
        return new GraphRequest((AccessToken) null, "me", bundle, HttpMethod.GET, (GraphRequest.Callback) null);
    }

    public static void getGraphMeRequestWithCacheAsync(final String str, final GraphMeRequestWithCacheCallback graphMeRequestWithCacheCallback) {
        JSONObject profileInformation = ProfileInformationCache.getProfileInformation(str);
        if (profileInformation != null) {
            graphMeRequestWithCacheCallback.onSuccess(profileInformation);
            return;
        }
        AnonymousClass2 r1 = new GraphRequest.Callback() {
            public void onCompleted(GraphResponse graphResponse) {
                if (graphResponse.getError() != null) {
                    graphMeRequestWithCacheCallback.onFailure(graphResponse.getError().getException());
                    return;
                }
                ProfileInformationCache.putProfileInformation(str, graphResponse.getJSONObject());
                graphMeRequestWithCacheCallback.onSuccess(graphResponse.getJSONObject());
            }
        };
        GraphRequest graphMeRequestWithCache = getGraphMeRequestWithCache(str);
        graphMeRequestWithCache.setCallback(r1);
        graphMeRequestWithCache.executeAsync();
    }

    public static String getMetadataApplicationId(Context context) {
        Validate.notNull(context, "context");
        FacebookSdk.sdkInitialize(context);
        return FacebookSdk.getApplicationId();
    }

    public static Method getMethodQuietly(Class<?> cls, String str, Class<?>... clsArr) {
        try {
            return cls.getMethod(str, clsArr);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Method getMethodQuietly(String str, String str2, Class<?>... clsArr) {
        try {
            return getMethodQuietly(Class.forName(str), str2, clsArr);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static Object getStringPropertyAsJSON(JSONObject jSONObject, String str, String str2) throws JSONException {
        Object opt = jSONObject.opt(str);
        if (opt != null && (opt instanceof String)) {
            opt = new JSONTokener((String) opt).nextValue();
        }
        if (opt == null || (opt instanceof JSONObject) || (opt instanceof JSONArray)) {
            return opt;
        }
        if (str2 != null) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.putOpt(str2, opt);
            return jSONObject2;
        }
        throw new FacebookException("Got an unexpected non-JSON object.");
    }

    public static String getUriString(Uri uri) {
        if (uri == null) {
            return null;
        }
        return uri.toString();
    }

    public static boolean hasSameId(JSONObject jSONObject, JSONObject jSONObject2) {
        if (jSONObject == null || jSONObject2 == null || !jSONObject.has(ShareConstants.WEB_DIALOG_PARAM_ID) || !jSONObject2.has(ShareConstants.WEB_DIALOG_PARAM_ID)) {
            return false;
        }
        if (jSONObject.equals(jSONObject2)) {
            return true;
        }
        String optString = jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_ID);
        String optString2 = jSONObject2.optString(ShareConstants.WEB_DIALOG_PARAM_ID);
        if (optString == null || optString2 == null) {
            return false;
        }
        return optString.equals(optString2);
    }

    private static String hashBytes(MessageDigest messageDigest, byte[] bArr) {
        messageDigest.update(bArr);
        byte[] digest = messageDigest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(Integer.toHexString((b >> 4) & 15));
            sb.append(Integer.toHexString((b >> 0) & 15));
        }
        return sb.toString();
    }

    public static <T> HashSet<T> hashSet(T... tArr) {
        HashSet<T> hashSet = new HashSet<>(tArr.length);
        for (T add : tArr) {
            hashSet.add(add);
        }
        return hashSet;
    }

    private static String hashWithAlgorithm(String str, String str2) {
        return hashWithAlgorithm(str, str2.getBytes());
    }

    private static String hashWithAlgorithm(String str, byte[] bArr) {
        try {
            return hashBytes(MessageDigest.getInstance(str), bArr);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static int[] intersectRanges(int[] iArr, int[] iArr2) {
        if (iArr == null) {
            return iArr2;
        }
        if (iArr2 == null) {
            return iArr;
        }
        int[] iArr3 = new int[(iArr.length + iArr2.length)];
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= iArr.length || i3 >= iArr2.length) {
                break;
            }
            int i4 = ExploreByTouchHelper.INVALID_ID;
            int i5 = Integer.MAX_VALUE;
            int i6 = iArr[i2];
            int i7 = Integer.MAX_VALUE;
            int i8 = iArr2[i3];
            int i9 = Integer.MAX_VALUE;
            if (i2 < iArr.length - 1) {
                i7 = iArr[i2 + 1];
            }
            if (i3 < iArr2.length - 1) {
                i9 = iArr2[i3 + 1];
            }
            if (i6 < i8) {
                if (i7 > i8) {
                    i4 = i8;
                    if (i7 > i9) {
                        i5 = i9;
                        i3 += 2;
                    } else {
                        i5 = i7;
                        i2 += 2;
                    }
                } else {
                    i2 += 2;
                }
            } else if (i9 > i6) {
                i4 = i6;
                if (i9 > i7) {
                    i5 = i7;
                    i2 += 2;
                } else {
                    i5 = i9;
                    i3 += 2;
                }
            } else {
                i3 += 2;
            }
            if (i4 != Integer.MIN_VALUE) {
                int i10 = i + 1;
                iArr3[i] = i4;
                if (i5 == Integer.MAX_VALUE) {
                    i = i10;
                    break;
                }
                i = i10 + 1;
                iArr3[i10] = i5;
            }
        }
        return Arrays.copyOf(iArr3, i);
    }

    public static Object invokeMethodQuietly(Object obj, Method method, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    public static boolean isContentUri(Uri uri) {
        return uri != null && "content".equalsIgnoreCase(uri.getScheme());
    }

    public static boolean isCurrentAccessToken(AccessToken accessToken) {
        if (accessToken != null) {
            return accessToken.equals(AccessToken.getCurrentAccessToken());
        }
        return false;
    }

    public static boolean isFileUri(Uri uri) {
        return uri != null && "file".equalsIgnoreCase(uri.getScheme());
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return collection == null || collection.size() == 0;
    }

    public static <T> boolean isSubset(Collection<T> collection, Collection<T> collection2) {
        if (collection2 == null || collection2.size() == 0) {
            return collection == null || collection.size() == 0;
        }
        HashSet hashSet = new HashSet(collection2);
        for (T contains : collection) {
            if (!hashSet.contains(contains)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWebUri(Uri uri) {
        return uri != null && ("http".equalsIgnoreCase(uri.getScheme()) || URL_SCHEME.equalsIgnoreCase(uri.getScheme()));
    }

    public static List<String> jsonArrayToStringList(JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(jSONArray.getString(i));
        }
        return arrayList;
    }

    public static void loadAppSettingsAsync(final Context context, final String str) {
        boolean compareAndSet = loadingSettings.compareAndSet(false, true);
        if (!isNullOrEmpty(str) && !fetchedAppSettings.containsKey(str) && compareAndSet) {
            final String format = String.format(APP_SETTINGS_PREFS_KEY_FORMAT, new Object[]{str});
            FacebookSdk.getExecutor().execute(new Runnable() {
                public void run() {
                    JSONObject access$000 = Utility.getAppSettingsQueryResponse(str);
                    if (access$000 != null) {
                        FetchedAppSettings unused = Utility.parseAppSettingsFromJSON(str, access$000);
                        context.getSharedPreferences(Utility.APP_SETTINGS_PREFS_STORE, 0).edit().putString(format, access$000.toString()).apply();
                    }
                    Utility.loadingSettings.set(false);
                }
            });
            String string = context.getSharedPreferences(APP_SETTINGS_PREFS_STORE, 0).getString(format, (String) null);
            if (!isNullOrEmpty(string)) {
                JSONObject jSONObject = null;
                try {
                    jSONObject = new JSONObject(string);
                } catch (JSONException e) {
                    logd(LOG_TAG, (Exception) e);
                }
                if (jSONObject != null) {
                    parseAppSettingsFromJSON(str, jSONObject);
                }
            }
        }
    }

    public static void logd(String str, Exception exc) {
        if (FacebookSdk.isDebugEnabled() && str != null && exc != null) {
            Log.d(str, exc.getClass().getSimpleName() + ": " + exc.getMessage());
        }
    }

    public static void logd(String str, String str2) {
        if (FacebookSdk.isDebugEnabled() && str != null && str2 != null) {
            Log.d(str, str2);
        }
    }

    public static void logd(String str, String str2, Throwable th) {
        if (FacebookSdk.isDebugEnabled() && !isNullOrEmpty(str)) {
            Log.d(str, str2, th);
        }
    }

    public static <T, K> List<K> map(List<T> list, Mapper<T, K> mapper) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (T apply : list) {
            K apply2 = mapper.apply(apply);
            if (apply2 != null) {
                arrayList.add(apply2);
            }
        }
        if (arrayList.size() == 0) {
            arrayList = null;
        }
        return arrayList;
    }

    public static String md5hash(String str) {
        return hashWithAlgorithm("MD5", str);
    }

    /* access modifiers changed from: private */
    public static FetchedAppSettings parseAppSettingsFromJSON(String str, JSONObject jSONObject) {
        JSONArray optJSONArray = jSONObject.optJSONArray(APP_SETTING_ANDROID_SDK_ERROR_CATEGORIES);
        FetchedAppSettings fetchedAppSettings2 = new FetchedAppSettings(jSONObject.optBoolean(APP_SETTING_SUPPORTS_IMPLICIT_SDK_LOGGING, false), jSONObject.optString(APP_SETTING_NUX_CONTENT, BuildConfig.FLAVOR), jSONObject.optBoolean(APP_SETTING_NUX_ENABLED, false), parseDialogConfigurations(jSONObject.optJSONObject(APP_SETTING_DIALOG_CONFIGS)), optJSONArray == null ? FacebookRequestErrorClassification.getDefaultErrorClassification() : FacebookRequestErrorClassification.createFromJSON(optJSONArray));
        fetchedAppSettings.put(str, fetchedAppSettings2);
        return fetchedAppSettings2;
    }

    private static Map<String, Map<String, DialogFeatureConfig>> parseDialogConfigurations(JSONObject jSONObject) {
        JSONArray optJSONArray;
        HashMap hashMap = new HashMap();
        if (!(jSONObject == null || (optJSONArray = jSONObject.optJSONArray("data")) == null)) {
            for (int i = 0; i < optJSONArray.length(); i++) {
                DialogFeatureConfig access$400 = DialogFeatureConfig.parseDialogConfig(optJSONArray.optJSONObject(i));
                if (access$400 != null) {
                    String dialogName = access$400.getDialogName();
                    Map map = (Map) hashMap.get(dialogName);
                    if (map == null) {
                        map = new HashMap();
                        hashMap.put(dialogName, map);
                    }
                    map.put(access$400.getFeatureName(), access$400);
                }
            }
        }
        return hashMap;
    }

    public static Bundle parseUrlQueryString(String str) {
        Bundle bundle = new Bundle();
        if (!isNullOrEmpty(str)) {
            for (String split : str.split("&")) {
                String[] split2 = split.split("=");
                try {
                    if (split2.length == 2) {
                        bundle.putString(URLDecoder.decode(split2[0], UTF8), URLDecoder.decode(split2[1], UTF8));
                    } else if (split2.length == 1) {
                        bundle.putString(URLDecoder.decode(split2[0], UTF8), BuildConfig.FLAVOR);
                    }
                } catch (UnsupportedEncodingException e) {
                    logd(LOG_TAG, (Exception) e);
                }
            }
        }
        return bundle;
    }

    public static void putCommaSeparatedStringList(Bundle bundle, String str, ArrayList<String> arrayList) {
        if (arrayList != null) {
            StringBuilder sb = new StringBuilder();
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                sb.append(it.next());
                sb.append(",");
            }
            String str2 = BuildConfig.FLAVOR;
            if (sb.length() > 0) {
                str2 = sb.substring(0, sb.length() - 1);
            }
            bundle.putString(str, str2);
        }
    }

    public static boolean putJSONValueInBundle(Bundle bundle, String str, Object obj) {
        if (obj == null) {
            bundle.remove(str);
        } else if (obj instanceof Boolean) {
            bundle.putBoolean(str, ((Boolean) obj).booleanValue());
        } else if (obj instanceof boolean[]) {
            bundle.putBooleanArray(str, (boolean[]) obj);
        } else if (obj instanceof Double) {
            bundle.putDouble(str, ((Double) obj).doubleValue());
        } else if (obj instanceof double[]) {
            bundle.putDoubleArray(str, (double[]) obj);
        } else if (obj instanceof Integer) {
            bundle.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof int[]) {
            bundle.putIntArray(str, (int[]) obj);
        } else if (obj instanceof Long) {
            bundle.putLong(str, ((Long) obj).longValue());
        } else if (obj instanceof long[]) {
            bundle.putLongArray(str, (long[]) obj);
        } else if (obj instanceof String) {
            bundle.putString(str, (String) obj);
        } else if (obj instanceof JSONArray) {
            bundle.putString(str, ((JSONArray) obj).toString());
        } else if (!(obj instanceof JSONObject)) {
            return false;
        } else {
            bundle.putString(str, ((JSONObject) obj).toString());
        }
        return true;
    }

    public static void putNonEmptyString(Bundle bundle, String str, String str2) {
        if (!isNullOrEmpty(str2)) {
            bundle.putString(str, str2);
        }
    }

    public static void putUri(Bundle bundle, String str, Uri uri) {
        if (uri != null) {
            putNonEmptyString(bundle, str, uri.toString());
        }
    }

    public static FetchedAppSettings queryAppSettings(String str, boolean z) {
        if (!z && fetchedAppSettings.containsKey(str)) {
            return fetchedAppSettings.get(str);
        }
        JSONObject appSettingsQueryResponse = getAppSettingsQueryResponse(str);
        if (appSettingsQueryResponse == null) {
            return null;
        }
        return parseAppSettingsFromJSON(str, appSettingsQueryResponse);
    }

    public static String readStreamToString(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader;
        BufferedInputStream bufferedInputStream = null;
        InputStreamReader inputStreamReader2 = null;
        try {
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(inputStream);
            try {
                inputStreamReader = new InputStreamReader(bufferedInputStream2);
            } catch (Throwable th) {
                th = th;
                bufferedInputStream = bufferedInputStream2;
                closeQuietly(bufferedInputStream);
                closeQuietly(inputStreamReader2);
                throw th;
            }
            try {
                StringBuilder sb = new StringBuilder();
                char[] cArr = new char[2048];
                while (true) {
                    int read = inputStreamReader.read(cArr);
                    if (read != -1) {
                        sb.append(cArr, 0, read);
                    } else {
                        String sb2 = sb.toString();
                        closeQuietly(bufferedInputStream2);
                        closeQuietly(inputStreamReader);
                        return sb2;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                inputStreamReader2 = inputStreamReader;
                bufferedInputStream = bufferedInputStream2;
                closeQuietly(bufferedInputStream);
                closeQuietly(inputStreamReader2);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            closeQuietly(bufferedInputStream);
            closeQuietly(inputStreamReader2);
            throw th;
        }
    }

    public static Map<String, String> readStringMapFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt < 0) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (int i = 0; i < readInt; i++) {
            hashMap.put(parcel.readString(), parcel.readString());
        }
        return hashMap;
    }

    public static String safeGetStringFromResponse(JSONObject jSONObject, String str) {
        return jSONObject != null ? jSONObject.optString(str, BuildConfig.FLAVOR) : BuildConfig.FLAVOR;
    }

    public static void setAppEventAttributionParameters(JSONObject jSONObject, AttributionIdentifiers attributionIdentifiers, String str, boolean z) throws JSONException {
        boolean z2 = true;
        if (!(attributionIdentifiers == null || attributionIdentifiers.getAttributionId() == null)) {
            jSONObject.put("attribution", attributionIdentifiers.getAttributionId());
        }
        if (!(attributionIdentifiers == null || attributionIdentifiers.getAndroidAdvertiserId() == null)) {
            jSONObject.put("advertiser_id", attributionIdentifiers.getAndroidAdvertiserId());
            jSONObject.put("advertiser_tracking_enabled", !attributionIdentifiers.isTrackingLimited());
        }
        jSONObject.put("anon_id", str);
        if (z) {
            z2 = false;
        }
        jSONObject.put("application_tracking_enabled", z2);
    }

    public static void setAppEventExtendedDeviceInfoParameters(JSONObject jSONObject, Context context) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(EXTRA_APP_EVENTS_INFO_FORMAT_VERSION);
        String packageName = context.getPackageName();
        int i = -1;
        String str = BuildConfig.FLAVOR;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            i = packageInfo.versionCode;
            str = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        jSONArray.put(packageName);
        jSONArray.put(i);
        jSONArray.put(str);
        jSONObject.put("extinfo", jSONArray.toString());
    }

    public static String sha1hash(String str) {
        return hashWithAlgorithm(HASH_ALGORITHM_SHA1, str);
    }

    public static String sha1hash(byte[] bArr) {
        return hashWithAlgorithm(HASH_ALGORITHM_SHA1, bArr);
    }

    public static boolean stringsEqualOrEmpty(String str, String str2) {
        boolean isEmpty = TextUtils.isEmpty(str);
        boolean isEmpty2 = TextUtils.isEmpty(str2);
        if (isEmpty && isEmpty2) {
            return true;
        }
        if (isEmpty || isEmpty2) {
            return false;
        }
        return str.equals(str2);
    }

    public static JSONArray tryGetJSONArrayFromResponse(JSONObject jSONObject, String str) {
        if (jSONObject != null) {
            return jSONObject.optJSONArray(str);
        }
        return null;
    }

    public static JSONObject tryGetJSONObjectFromResponse(JSONObject jSONObject, String str) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(str);
        }
        return null;
    }

    public static <T> Collection<T> unmodifiableCollection(T... tArr) {
        return Collections.unmodifiableCollection(Arrays.asList(tArr));
    }

    public static void writeStringMapToParcel(Parcel parcel, Map<String, String> map) {
        if (map == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(map.size());
        for (Map.Entry next : map.entrySet()) {
            parcel.writeString((String) next.getKey());
            parcel.writeString((String) next.getValue());
        }
    }
}
