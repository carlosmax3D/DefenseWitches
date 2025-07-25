package com.facebook;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.BoltsMeasurementEventListener;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public final class FacebookSdk {
    private static final String ANALYTICS_EVENT = "event";
    public static final String APPLICATION_ID_PROPERTY = "com.facebook.sdk.ApplicationId";
    public static final String APPLICATION_NAME_PROPERTY = "com.facebook.sdk.ApplicationName";
    private static final String ATTRIBUTION_ID_COLUMN_NAME = "aid";
    private static final Uri ATTRIBUTION_ID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider");
    private static final String ATTRIBUTION_PREFERENCES = "com.facebook.sdk.attributionTracking";
    static final String CALLBACK_OFFSET_CHANGED_AFTER_INIT = "The callback request code offset can't be updated once the SDK is initialized.";
    static final String CALLBACK_OFFSET_NEGATIVE = "The callback request code offset can't be negative.";
    public static final String CLIENT_TOKEN_PROPERTY = "com.facebook.sdk.ClientToken";
    private static final int DEFAULT_CORE_POOL_SIZE = 5;
    private static final int DEFAULT_KEEP_ALIVE = 1;
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = 128;
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger counter = new AtomicInteger(0);

        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "FacebookSdk #" + this.counter.incrementAndGet());
        }
    };
    private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE = new LinkedBlockingQueue(10);
    private static final String FACEBOOK_COM = "facebook.com";
    private static final Object LOCK = new Object();
    private static final int MAX_REQUEST_CODE_RANGE = 100;
    private static final String MOBILE_INSTALL_EVENT = "MOBILE_APP_INSTALL";
    private static final String PUBLISH_ACTIVITY_PATH = "%s/activities";
    private static final String TAG = FacebookSdk.class.getCanonicalName();
    private static volatile String appClientToken;
    private static Context applicationContext;
    private static volatile String applicationId;
    private static volatile String applicationName;
    private static File cacheDir;
    private static int callbackRequestCodeOffset = 64206;
    private static volatile Executor executor;
    private static volatile String facebookDomain = FACEBOOK_COM;
    private static volatile boolean isDebugEnabled = false;
    private static boolean isLegacyTokenUpgradeSupported = false;
    private static final HashSet<LoggingBehavior> loggingBehaviors = new HashSet<>(Arrays.asList(new LoggingBehavior[]{LoggingBehavior.DEVELOPER_ERRORS}));
    private static AtomicLong onProgressThreshold = new AtomicLong(65536);
    private static Boolean sdkInitialized = false;

    public static void addLoggingBehavior(LoggingBehavior loggingBehavior) {
        synchronized (loggingBehaviors) {
            loggingBehaviors.add(loggingBehavior);
            updateGraphDebugBehavior();
        }
    }

    public static void clearLoggingBehaviors() {
        synchronized (loggingBehaviors) {
            loggingBehaviors.clear();
        }
    }

    public static Context getApplicationContext() {
        Validate.sdkInitialized();
        return applicationContext;
    }

    public static String getApplicationId() {
        Validate.sdkInitialized();
        return applicationId;
    }

    public static String getApplicationName() {
        Validate.sdkInitialized();
        return applicationName;
    }

    public static String getApplicationSignature(Context context) {
        PackageManager packageManager;
        Validate.sdkInitialized();
        if (context == null || (packageManager = context.getPackageManager()) == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 64);
            Signature[] signatureArr = packageInfo.signatures;
            if (signatureArr == null || signatureArr.length == 0) {
                return null;
            }
            try {
                MessageDigest instance = MessageDigest.getInstance("SHA-1");
                instance.update(packageInfo.signatures[0].toByteArray());
                return Base64.encodeToString(instance.digest(), 9);
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            return null;
        }
    }

    private static Executor getAsyncTaskExecutor() {
        try {
            try {
                Object obj = AsyncTask.class.getField("THREAD_POOL_EXECUTOR").get((Object) null);
                if (obj == null) {
                    return null;
                }
                if (!(obj instanceof Executor)) {
                    return null;
                }
                return (Executor) obj;
            } catch (IllegalAccessException e) {
                return null;
            }
        } catch (NoSuchFieldException e2) {
            return null;
        }
    }

    public static String getAttributionId(ContentResolver contentResolver) {
        String str = null;
        Validate.sdkInitialized();
        Cursor cursor = null;
        try {
            ContentResolver contentResolver2 = contentResolver;
            cursor = contentResolver2.query(ATTRIBUTION_ID_CONTENT_URI, new String[]{ATTRIBUTION_ID_COLUMN_NAME}, (String) null, (String[]) null, (String) null);
            if (cursor == null || !cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                return str;
            }
            str = cursor.getString(cursor.getColumnIndex(ATTRIBUTION_ID_COLUMN_NAME));
            if (cursor != null) {
                cursor.close();
            }
            return str;
        } catch (Exception e) {
            Log.d(TAG, "Caught unexpected exception in getAttributionId(): " + e.toString());
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public static File getCacheDir() {
        Validate.sdkInitialized();
        return cacheDir;
    }

    public static int getCallbackRequestCodeOffset() {
        Validate.sdkInitialized();
        return callbackRequestCodeOffset;
    }

    public static String getClientToken() {
        Validate.sdkInitialized();
        return appClientToken;
    }

    public static Executor getExecutor() {
        synchronized (LOCK) {
            if (executor == null) {
                Executor asyncTaskExecutor = getAsyncTaskExecutor();
                if (asyncTaskExecutor == null) {
                    asyncTaskExecutor = new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, DEFAULT_WORK_QUEUE, DEFAULT_THREAD_FACTORY);
                }
                executor = asyncTaskExecutor;
            }
        }
        return executor;
    }

    public static String getFacebookDomain() {
        return facebookDomain;
    }

    public static boolean getLimitEventAndDataUsage(Context context) {
        Validate.sdkInitialized();
        return context.getSharedPreferences(AppEventsLogger.APP_EVENT_PREFERENCES, 0).getBoolean("limitEventUsage", false);
    }

    public static Set<LoggingBehavior> getLoggingBehaviors() {
        Set<LoggingBehavior> unmodifiableSet;
        synchronized (loggingBehaviors) {
            unmodifiableSet = Collections.unmodifiableSet(new HashSet(loggingBehaviors));
        }
        return unmodifiableSet;
    }

    public static long getOnProgressThreshold() {
        Validate.sdkInitialized();
        return onProgressThreshold.get();
    }

    public static String getSdkVersion() {
        Validate.sdkInitialized();
        return FacebookSdkVersion.BUILD;
    }

    public static boolean isDebugEnabled() {
        return isDebugEnabled;
    }

    public static boolean isFacebookRequestCode(int i) {
        return i >= callbackRequestCodeOffset && i < callbackRequestCodeOffset + 100;
    }

    public static synchronized boolean isInitialized() {
        boolean booleanValue;
        synchronized (FacebookSdk.class) {
            booleanValue = sdkInitialized.booleanValue();
        }
        return booleanValue;
    }

    public static boolean isLegacyTokenUpgradeSupported() {
        return isLegacyTokenUpgradeSupported;
    }

    public static boolean isLoggingBehaviorEnabled(LoggingBehavior loggingBehavior) {
        boolean z;
        synchronized (loggingBehaviors) {
            z = isDebugEnabled() && loggingBehaviors.contains(loggingBehavior);
        }
        return z;
    }

    static void loadDefaultsFromMetadata(Context context) {
        if (context != null) {
            try {
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                if (applicationInfo != null && applicationInfo.metaData != null) {
                    if (applicationId == null) {
                        applicationId = applicationInfo.metaData.getString("com.facebook.sdk.ApplicationId");
                    }
                    if (applicationName == null) {
                        applicationName = applicationInfo.metaData.getString(APPLICATION_NAME_PROPERTY);
                    }
                    if (appClientToken == null) {
                        appClientToken = applicationInfo.metaData.getString(CLIENT_TOKEN_PROPERTY);
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00f9, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0105, code lost:
        throw new com.facebook.FacebookException("An error occurred while publishing install.", (java.lang.Throwable) r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        return new com.facebook.GraphResponse((com.facebook.GraphRequest) null, (java.net.HttpURLConnection) null, new com.facebook.FacebookRequestError((java.net.HttpURLConnection) null, r4));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000c, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000d, code lost:
        com.facebook.internal.Utility.logd("Facebook-publish", r4);
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:2:0x0004, B:10:0x0086] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.facebook.GraphResponse publishInstallAndWaitForResponse(android.content.Context r24, java.lang.String r25) {
        /*
            if (r24 == 0) goto L_0x0004
            if (r25 != 0) goto L_0x0029
        L_0x0004:
            java.lang.IllegalArgumentException r19 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x000c }
            java.lang.String r20 = "Both context and applicationId must be non-null"
            r19.<init>(r20)     // Catch:{ Exception -> 0x000c }
            throw r19     // Catch:{ Exception -> 0x000c }
        L_0x000c:
            r4 = move-exception
            java.lang.String r19 = "Facebook-publish"
            r0 = r19
            com.facebook.internal.Utility.logd((java.lang.String) r0, (java.lang.Exception) r4)
            com.facebook.GraphResponse r19 = new com.facebook.GraphResponse
            r20 = 0
            r21 = 0
            com.facebook.FacebookRequestError r22 = new com.facebook.FacebookRequestError
            r23 = 0
            r0 = r22
            r1 = r23
            r0.<init>(r1, r4)
            r19.<init>(r20, r21, r22)
        L_0x0028:
            return r19
        L_0x0029:
            com.facebook.internal.AttributionIdentifiers r8 = com.facebook.internal.AttributionIdentifiers.getAttributionIdentifiers(r24)     // Catch:{ Exception -> 0x000c }
            java.lang.String r19 = "com.facebook.sdk.attributionTracking"
            r20 = 0
            r0 = r24
            r1 = r19
            r2 = r20
            android.content.SharedPreferences r14 = r0.getSharedPreferences(r1, r2)     // Catch:{ Exception -> 0x000c }
            java.lang.StringBuilder r19 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x000c }
            r19.<init>()     // Catch:{ Exception -> 0x000c }
            r0 = r19
            r1 = r25
            java.lang.StringBuilder r19 = r0.append(r1)     // Catch:{ Exception -> 0x000c }
            java.lang.String r20 = "ping"
            java.lang.StringBuilder r19 = r19.append(r20)     // Catch:{ Exception -> 0x000c }
            java.lang.String r13 = r19.toString()     // Catch:{ Exception -> 0x000c }
            java.lang.StringBuilder r19 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x000c }
            r19.<init>()     // Catch:{ Exception -> 0x000c }
            r0 = r19
            r1 = r25
            java.lang.StringBuilder r19 = r0.append(r1)     // Catch:{ Exception -> 0x000c }
            java.lang.String r20 = "json"
            java.lang.StringBuilder r19 = r19.append(r20)     // Catch:{ Exception -> 0x000c }
            java.lang.String r9 = r19.toString()     // Catch:{ Exception -> 0x000c }
            r20 = 0
            r0 = r20
            long r10 = r14.getLong(r13, r0)     // Catch:{ Exception -> 0x000c }
            r19 = 0
            r0 = r19
            java.lang.String r12 = r14.getString(r9, r0)     // Catch:{ Exception -> 0x000c }
            org.json.JSONObject r15 = new org.json.JSONObject     // Catch:{ Exception -> 0x000c }
            r15.<init>()     // Catch:{ Exception -> 0x000c }
            java.lang.String r19 = "event"
            java.lang.String r20 = "MOBILE_APP_INSTALL"
            r0 = r19
            r1 = r20
            r15.put(r0, r1)     // Catch:{ JSONException -> 0x00f9 }
            java.lang.String r19 = com.facebook.appevents.AppEventsLogger.getAnonymousAppDeviceGUID(r24)     // Catch:{ JSONException -> 0x00f9 }
            boolean r20 = getLimitEventAndDataUsage(r24)     // Catch:{ JSONException -> 0x00f9 }
            r0 = r19
            r1 = r20
            com.facebook.internal.Utility.setAppEventAttributionParameters(r15, r8, r0, r1)     // Catch:{ JSONException -> 0x00f9 }
            java.lang.String r19 = "application_package_name"
            java.lang.String r20 = r24.getPackageName()     // Catch:{ JSONException -> 0x00f9 }
            r0 = r19
            r1 = r20
            r15.put(r0, r1)     // Catch:{ JSONException -> 0x00f9 }
            java.lang.String r19 = "%s/activities"
            r20 = 1
            r0 = r20
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x000c }
            r20 = r0
            r21 = 0
            r20[r21] = r25     // Catch:{ Exception -> 0x000c }
            java.lang.String r18 = java.lang.String.format(r19, r20)     // Catch:{ Exception -> 0x000c }
            r19 = 0
            r20 = 0
            r0 = r19
            r1 = r18
            r2 = r20
            com.facebook.GraphRequest r16 = com.facebook.GraphRequest.newPostRequest(r0, r1, r15, r2)     // Catch:{ Exception -> 0x000c }
            r20 = 0
            int r19 = (r10 > r20 ? 1 : (r10 == r20 ? 0 : -1))
            if (r19 == 0) goto L_0x011b
            r6 = 0
            if (r12 == 0) goto L_0x00d4
            org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0144 }
            r7.<init>(r12)     // Catch:{ JSONException -> 0x0144 }
            r6 = r7
        L_0x00d4:
            if (r6 != 0) goto L_0x0106
            java.lang.String r19 = "true"
            r20 = 0
            com.facebook.GraphRequestBatch r21 = new com.facebook.GraphRequestBatch     // Catch:{ Exception -> 0x000c }
            r22 = 1
            r0 = r22
            com.facebook.GraphRequest[] r0 = new com.facebook.GraphRequest[r0]     // Catch:{ Exception -> 0x000c }
            r22 = r0
            r23 = 0
            r22[r23] = r16     // Catch:{ Exception -> 0x000c }
            r21.<init>((com.facebook.GraphRequest[]) r22)     // Catch:{ Exception -> 0x000c }
            java.util.List r19 = com.facebook.GraphResponse.createResponsesFromString(r19, r20, r21)     // Catch:{ Exception -> 0x000c }
            r20 = 0
            java.lang.Object r19 = r19.get(r20)     // Catch:{ Exception -> 0x000c }
            com.facebook.GraphResponse r19 = (com.facebook.GraphResponse) r19     // Catch:{ Exception -> 0x000c }
            goto L_0x0028
        L_0x00f9:
            r4 = move-exception
            com.facebook.FacebookException r19 = new com.facebook.FacebookException     // Catch:{ Exception -> 0x000c }
            java.lang.String r20 = "An error occurred while publishing install."
            r0 = r19
            r1 = r20
            r0.<init>((java.lang.String) r1, (java.lang.Throwable) r4)     // Catch:{ Exception -> 0x000c }
            throw r19     // Catch:{ Exception -> 0x000c }
        L_0x0106:
            com.facebook.GraphResponse r19 = new com.facebook.GraphResponse     // Catch:{ Exception -> 0x000c }
            r20 = 0
            r21 = 0
            r22 = 0
            r0 = r19
            r1 = r20
            r2 = r21
            r3 = r22
            r0.<init>((com.facebook.GraphRequest) r1, (java.net.HttpURLConnection) r2, (java.lang.String) r3, (org.json.JSONObject) r6)     // Catch:{ Exception -> 0x000c }
            goto L_0x0028
        L_0x011b:
            com.facebook.GraphResponse r17 = r16.executeAndWait()     // Catch:{ Exception -> 0x000c }
            android.content.SharedPreferences$Editor r5 = r14.edit()     // Catch:{ Exception -> 0x000c }
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x000c }
            r5.putLong(r13, r10)     // Catch:{ Exception -> 0x000c }
            org.json.JSONObject r19 = r17.getJSONObject()     // Catch:{ Exception -> 0x000c }
            if (r19 == 0) goto L_0x013d
            org.json.JSONObject r19 = r17.getJSONObject()     // Catch:{ Exception -> 0x000c }
            java.lang.String r19 = r19.toString()     // Catch:{ Exception -> 0x000c }
            r0 = r19
            r5.putString(r9, r0)     // Catch:{ Exception -> 0x000c }
        L_0x013d:
            r5.apply()     // Catch:{ Exception -> 0x000c }
            r19 = r17
            goto L_0x0028
        L_0x0144:
            r19 = move-exception
            goto L_0x00d4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.FacebookSdk.publishInstallAndWaitForResponse(android.content.Context, java.lang.String):com.facebook.GraphResponse");
    }

    public static void publishInstallAsync(Context context, final String str) {
        final Context applicationContext2 = context.getApplicationContext();
        getExecutor().execute(new Runnable() {
            public void run() {
                FacebookSdk.publishInstallAndWaitForResponse(applicationContext2, str);
            }
        });
    }

    public static void removeLoggingBehavior(LoggingBehavior loggingBehavior) {
        synchronized (loggingBehaviors) {
            loggingBehaviors.remove(loggingBehavior);
        }
    }

    public static synchronized void sdkInitialize(Context context) {
        synchronized (FacebookSdk.class) {
            if (!sdkInitialized.booleanValue()) {
                Validate.notNull(context, "applicationContext");
                applicationContext = context.getApplicationContext();
                loadDefaultsFromMetadata(applicationContext);
                Utility.loadAppSettingsAsync(applicationContext, applicationId);
                BoltsMeasurementEventListener.getInstance(applicationContext);
                cacheDir = applicationContext.getCacheDir();
                getExecutor().execute(new FutureTask(new Callable<Void>() {
                    public Void call() throws Exception {
                        AccessTokenManager.getInstance().loadCurrentAccessToken();
                        ProfileManager.getInstance().loadCurrentProfile();
                        if (AccessToken.getCurrentAccessToken() == null || Profile.getCurrentProfile() != null) {
                            return null;
                        }
                        Profile.fetchProfileForCurrentAccessToken();
                        return null;
                    }
                }));
                sdkInitialized = true;
            }
        }
    }

    public static synchronized void sdkInitialize(Context context, int i) {
        synchronized (FacebookSdk.class) {
            if (sdkInitialized.booleanValue() && i != callbackRequestCodeOffset) {
                throw new FacebookException(CALLBACK_OFFSET_CHANGED_AFTER_INIT);
            } else if (i < 0) {
                throw new FacebookException(CALLBACK_OFFSET_NEGATIVE);
            } else {
                callbackRequestCodeOffset = i;
                sdkInitialize(context);
            }
        }
    }

    public static void setApplicationId(String str) {
        applicationId = str;
    }

    public static void setApplicationName(String str) {
        applicationName = str;
    }

    public static void setCacheDir(File file) {
        cacheDir = file;
    }

    public static void setClientToken(String str) {
        appClientToken = str;
    }

    public static void setExecutor(Executor executor2) {
        Validate.notNull(executor2, "executor");
        synchronized (LOCK) {
            executor = executor2;
        }
    }

    public static void setFacebookDomain(String str) {
        Log.w(TAG, "WARNING: Calling setFacebookDomain from non-DEBUG code.");
        facebookDomain = str;
    }

    public static void setIsDebugEnabled(boolean z) {
        isDebugEnabled = z;
    }

    public static void setLegacyTokenUpgradeSupported(boolean z) {
        isLegacyTokenUpgradeSupported = z;
    }

    public static void setLimitEventAndDataUsage(Context context, boolean z) {
        context.getSharedPreferences(AppEventsLogger.APP_EVENT_PREFERENCES, 0).edit().putBoolean("limitEventUsage", z).apply();
    }

    public static void setOnProgressThreshold(long j) {
        onProgressThreshold.set(j);
    }

    private static void updateGraphDebugBehavior() {
        if (loggingBehaviors.contains(LoggingBehavior.GRAPH_API_DEBUG_INFO) && !loggingBehaviors.contains(LoggingBehavior.GRAPH_API_DEBUG_WARNING)) {
            loggingBehaviors.add(LoggingBehavior.GRAPH_API_DEBUG_WARNING);
        }
    }
}
