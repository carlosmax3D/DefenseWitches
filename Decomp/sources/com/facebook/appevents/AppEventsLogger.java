package com.facebook.appevents;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import bolts.AppLinks;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppEventsLogger {
    public static final String ACTION_APP_EVENTS_FLUSHED = "com.facebook.sdk.APP_EVENTS_FLUSHED";
    public static final String APP_EVENTS_EXTRA_FLUSH_RESULT = "com.facebook.sdk.APP_EVENTS_FLUSH_RESULT";
    public static final String APP_EVENTS_EXTRA_NUM_EVENTS_FLUSHED = "com.facebook.sdk.APP_EVENTS_NUM_EVENTS_FLUSHED";
    public static final String APP_EVENT_PREFERENCES = "com.facebook.sdk.appEventPreferences";
    private static final int APP_SUPPORTS_ATTRIBUTION_ID_RECHECK_PERIOD_IN_SECONDS = 86400;
    private static final int FLUSH_APP_SESSION_INFO_IN_SECONDS = 30;
    private static final int FLUSH_PERIOD_IN_SECONDS = 15;
    private static final int NUM_LOG_EVENTS_TO_TRY_TO_FLUSH_AFTER = 100;
    private static final String SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT = "_fbSourceApplicationHasBeenSet";
    /* access modifiers changed from: private */
    public static final String TAG = AppEventsLogger.class.getCanonicalName();
    private static String anonymousAppDeviceGUID;
    /* access modifiers changed from: private */
    public static Context applicationContext;
    /* access modifiers changed from: private */
    public static ScheduledThreadPoolExecutor backgroundExecutor;
    private static FlushBehavior flushBehavior = FlushBehavior.AUTO;
    private static boolean isOpenedByApplink;
    private static boolean requestInFlight;
    private static String sourceApplication;
    /* access modifiers changed from: private */
    public static Map<AccessTokenAppIdPair, SessionEventsState> stateMap = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public static Object staticLock = new Object();
    private final AccessTokenAppIdPair accessTokenAppId;
    private final Context context;

    private static class AccessTokenAppIdPair implements Serializable {
        private static final long serialVersionUID = 1;
        private final String accessTokenString;
        private final String applicationId;

        private static class SerializationProxyV1 implements Serializable {
            private static final long serialVersionUID = -2488473066578201069L;
            private final String accessTokenString;
            private final String appId;

            private SerializationProxyV1(String str, String str2) {
                this.accessTokenString = str;
                this.appId = str2;
            }

            private Object readResolve() {
                return new AccessTokenAppIdPair(this.accessTokenString, this.appId);
            }
        }

        AccessTokenAppIdPair(AccessToken accessToken) {
            this(accessToken.getToken(), FacebookSdk.getApplicationId());
        }

        AccessTokenAppIdPair(String str, String str2) {
            this.accessTokenString = Utility.isNullOrEmpty(str) ? null : str;
            this.applicationId = str2;
        }

        private Object writeReplace() {
            return new SerializationProxyV1(this.accessTokenString, this.applicationId);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof AccessTokenAppIdPair)) {
                return false;
            }
            AccessTokenAppIdPair accessTokenAppIdPair = (AccessTokenAppIdPair) obj;
            return Utility.areObjectsEqual(accessTokenAppIdPair.accessTokenString, this.accessTokenString) && Utility.areObjectsEqual(accessTokenAppIdPair.applicationId, this.applicationId);
        }

        /* access modifiers changed from: package-private */
        public String getAccessTokenString() {
            return this.accessTokenString;
        }

        /* access modifiers changed from: package-private */
        public String getApplicationId() {
            return this.applicationId;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = this.accessTokenString == null ? 0 : this.accessTokenString.hashCode();
            if (this.applicationId != null) {
                i = this.applicationId.hashCode();
            }
            return hashCode ^ i;
        }
    }

    static class AppEvent implements Serializable {
        private static final long serialVersionUID = 1;
        private static final HashSet<String> validatedIdentifiers = new HashSet<>();
        private boolean isImplicit;
        private JSONObject jsonObject;
        private String name;

        private static class SerializationProxyV1 implements Serializable {
            private static final long serialVersionUID = -2488473066578201069L;
            private final boolean isImplicit;
            private final String jsonString;

            private SerializationProxyV1(String str, boolean z) {
                this.jsonString = str;
                this.isImplicit = z;
            }

            private Object readResolve() throws JSONException {
                return new AppEvent(this.jsonString, this.isImplicit);
            }
        }

        public AppEvent(Context context, String str, Double d, Bundle bundle, boolean z) {
            try {
                validateIdentifier(str);
                this.name = str;
                this.isImplicit = z;
                this.jsonObject = new JSONObject();
                this.jsonObject.put("_eventName", str);
                this.jsonObject.put("_logTime", System.currentTimeMillis() / 1000);
                this.jsonObject.put("_ui", Utility.getActivityName(context));
                if (d != null) {
                    this.jsonObject.put("_valueToSum", d.doubleValue());
                }
                if (this.isImplicit) {
                    this.jsonObject.put("_implicitlyLogged", AppEventsConstants.EVENT_PARAM_VALUE_YES);
                }
                if (bundle != null) {
                    for (String str2 : bundle.keySet()) {
                        validateIdentifier(str2);
                        Object obj = bundle.get(str2);
                        if ((obj instanceof String) || (obj instanceof Number)) {
                            this.jsonObject.put(str2, obj.toString());
                        } else {
                            throw new FacebookException(String.format("Parameter value '%s' for key '%s' should be a string or a numeric type.", new Object[]{obj, str2}));
                        }
                    }
                }
                if (!this.isImplicit) {
                    Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Created app event '%s'", this.jsonObject.toString());
                }
            } catch (JSONException e) {
                Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "JSON encoding for app event failed: '%s'", e.toString());
                this.jsonObject = null;
            } catch (FacebookException e2) {
                Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Invalid app event name or parameter:", e2.toString());
                this.jsonObject = null;
            }
        }

        private AppEvent(String str, boolean z) throws JSONException {
            this.jsonObject = new JSONObject(str);
            this.isImplicit = z;
        }

        private void validateIdentifier(String str) throws FacebookException {
            boolean contains;
            if (str == null || str.length() == 0 || str.length() > 40) {
                if (str == null) {
                    str = "<None Provided>";
                }
                throw new FacebookException(String.format(Locale.ROOT, "Identifier '%s' must be less than %d characters", new Object[]{str, 40}));
            }
            synchronized (validatedIdentifiers) {
                contains = validatedIdentifiers.contains(str);
            }
            if (contains) {
                return;
            }
            if (str.matches("^[0-9a-zA-Z_]+[0-9a-zA-Z _-]*$")) {
                synchronized (validatedIdentifiers) {
                    validatedIdentifiers.add(str);
                }
                return;
            }
            throw new FacebookException(String.format("Skipping event named '%s' due to illegal name - must be under 40 chars and alphanumeric, _, - or space, and not start with a space or hyphen.", new Object[]{str}));
        }

        private Object writeReplace() {
            return new SerializationProxyV1(this.jsonObject.toString(), this.isImplicit);
        }

        public boolean getIsImplicit() {
            return this.isImplicit;
        }

        public JSONObject getJSONObject() {
            return this.jsonObject;
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return String.format("\"%s\", implicit: %b, json: %s", new Object[]{this.jsonObject.optString("_eventName"), Boolean.valueOf(this.isImplicit), this.jsonObject.toString()});
        }
    }

    public enum FlushBehavior {
        AUTO,
        EXPLICIT_ONLY
    }

    private enum FlushReason {
        EXPLICIT,
        TIMER,
        SESSION_CHANGE,
        PERSISTED_EVENTS,
        EVENT_THRESHOLD,
        EAGER_FLUSHING_EVENT
    }

    private enum FlushResult {
        SUCCESS,
        SERVER_ERROR,
        NO_CONNECTIVITY,
        UNKNOWN_ERROR
    }

    private static class FlushStatistics {
        public int numEvents;
        public FlushResult result;

        private FlushStatistics() {
            this.numEvents = 0;
            this.result = FlushResult.SUCCESS;
        }
    }

    static class PersistedAppSessionInfo {
        private static final String PERSISTED_SESSION_INFO_FILENAME = "AppEventsLogger.persistedsessioninfo";
        private static final Runnable appSessionInfoFlushRunnable = new Runnable() {
            public void run() {
                PersistedAppSessionInfo.saveAppSessionInformation(AppEventsLogger.applicationContext);
            }
        };
        private static Map<AccessTokenAppIdPair, FacebookTimeSpentData> appSessionInfoMap;
        private static boolean hasChanges = false;
        private static boolean isLoaded = false;
        private static final Object staticLock = new Object();

        PersistedAppSessionInfo() {
        }

        private static FacebookTimeSpentData getTimeSpentData(Context context, AccessTokenAppIdPair accessTokenAppIdPair) {
            restoreAppSessionInformation(context);
            FacebookTimeSpentData facebookTimeSpentData = appSessionInfoMap.get(accessTokenAppIdPair);
            if (facebookTimeSpentData != null) {
                return facebookTimeSpentData;
            }
            FacebookTimeSpentData facebookTimeSpentData2 = new FacebookTimeSpentData();
            appSessionInfoMap.put(accessTokenAppIdPair, facebookTimeSpentData2);
            return facebookTimeSpentData2;
        }

        static void onResume(Context context, AccessTokenAppIdPair accessTokenAppIdPair, AppEventsLogger appEventsLogger, long j, String str) {
            synchronized (staticLock) {
                getTimeSpentData(context, accessTokenAppIdPair).onResume(appEventsLogger, j, str);
                onTimeSpentDataUpdate();
            }
        }

        static void onSuspend(Context context, AccessTokenAppIdPair accessTokenAppIdPair, AppEventsLogger appEventsLogger, long j) {
            synchronized (staticLock) {
                getTimeSpentData(context, accessTokenAppIdPair).onSuspend(appEventsLogger, j);
                onTimeSpentDataUpdate();
            }
        }

        private static void onTimeSpentDataUpdate() {
            if (!hasChanges) {
                hasChanges = true;
                AppEventsLogger.backgroundExecutor.schedule(appSessionInfoFlushRunnable, 30, TimeUnit.SECONDS);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:21:0x004d A[Catch:{ FileNotFoundException -> 0x0040, Exception -> 0x005e, all -> 0x005b }] */
        /* JADX WARNING: Removed duplicated region for block: B:32:0x0089 A[Catch:{ FileNotFoundException -> 0x0040, Exception -> 0x005e, all -> 0x005b }] */
        /* JADX WARNING: Removed duplicated region for block: B:37:0x00a4 A[Catch:{ FileNotFoundException -> 0x0040, Exception -> 0x005e, all -> 0x005b }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static void restoreAppSessionInformation(android.content.Context r7) {
            /*
                r1 = 0
                java.lang.Object r4 = staticLock
                monitor-enter(r4)
                boolean r3 = isLoaded     // Catch:{ all -> 0x005b }
                if (r3 != 0) goto L_0x003e
                java.io.ObjectInputStream r2 = new java.io.ObjectInputStream     // Catch:{ FileNotFoundException -> 0x0040, Exception -> 0x005e }
                java.lang.String r3 = "AppEventsLogger.persistedsessioninfo"
                java.io.FileInputStream r3 = r7.openFileInput(r3)     // Catch:{ FileNotFoundException -> 0x0040, Exception -> 0x005e }
                r2.<init>(r3)     // Catch:{ FileNotFoundException -> 0x0040, Exception -> 0x005e }
                java.lang.Object r3 = r2.readObject()     // Catch:{ FileNotFoundException -> 0x00bb, Exception -> 0x00b8, all -> 0x00b5 }
                java.util.HashMap r3 = (java.util.HashMap) r3     // Catch:{ FileNotFoundException -> 0x00bb, Exception -> 0x00b8, all -> 0x00b5 }
                appSessionInfoMap = r3     // Catch:{ FileNotFoundException -> 0x00bb, Exception -> 0x00b8, all -> 0x00b5 }
                com.facebook.LoggingBehavior r3 = com.facebook.LoggingBehavior.APP_EVENTS     // Catch:{ FileNotFoundException -> 0x00bb, Exception -> 0x00b8, all -> 0x00b5 }
                java.lang.String r5 = "AppEvents"
                java.lang.String r6 = "App session info loaded"
                com.facebook.internal.Logger.log(r3, r5, r6)     // Catch:{ FileNotFoundException -> 0x00bb, Exception -> 0x00b8, all -> 0x00b5 }
                com.facebook.internal.Utility.closeQuietly(r2)     // Catch:{ all -> 0x00b2 }
                java.lang.String r3 = "AppEventsLogger.persistedsessioninfo"
                r7.deleteFile(r3)     // Catch:{ all -> 0x00b2 }
                java.util.Map<com.facebook.appevents.AppEventsLogger$AccessTokenAppIdPair, com.facebook.appevents.FacebookTimeSpentData> r3 = appSessionInfoMap     // Catch:{ all -> 0x00b2 }
                if (r3 != 0) goto L_0x0037
                java.util.HashMap r3 = new java.util.HashMap     // Catch:{ all -> 0x00b2 }
                r3.<init>()     // Catch:{ all -> 0x00b2 }
                appSessionInfoMap = r3     // Catch:{ all -> 0x00b2 }
            L_0x0037:
                r3 = 1
                isLoaded = r3     // Catch:{ all -> 0x00b2 }
                r3 = 0
                hasChanges = r3     // Catch:{ all -> 0x00b2 }
                r1 = r2
            L_0x003e:
                monitor-exit(r4)     // Catch:{ all -> 0x005b }
                return
            L_0x0040:
                r3 = move-exception
            L_0x0041:
                com.facebook.internal.Utility.closeQuietly(r1)     // Catch:{ all -> 0x005b }
                java.lang.String r3 = "AppEventsLogger.persistedsessioninfo"
                r7.deleteFile(r3)     // Catch:{ all -> 0x005b }
                java.util.Map<com.facebook.appevents.AppEventsLogger$AccessTokenAppIdPair, com.facebook.appevents.FacebookTimeSpentData> r3 = appSessionInfoMap     // Catch:{ all -> 0x005b }
                if (r3 != 0) goto L_0x0054
                java.util.HashMap r3 = new java.util.HashMap     // Catch:{ all -> 0x005b }
                r3.<init>()     // Catch:{ all -> 0x005b }
                appSessionInfoMap = r3     // Catch:{ all -> 0x005b }
            L_0x0054:
                r3 = 1
                isLoaded = r3     // Catch:{ all -> 0x005b }
                r3 = 0
                hasChanges = r3     // Catch:{ all -> 0x005b }
                goto L_0x003e
            L_0x005b:
                r3 = move-exception
            L_0x005c:
                monitor-exit(r4)     // Catch:{ all -> 0x005b }
                throw r3
            L_0x005e:
                r0 = move-exception
            L_0x005f:
                java.lang.String r3 = com.facebook.appevents.AppEventsLogger.TAG     // Catch:{ all -> 0x0097 }
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0097 }
                r5.<init>()     // Catch:{ all -> 0x0097 }
                java.lang.String r6 = "Got unexpected exception: "
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0097 }
                java.lang.String r6 = r0.toString()     // Catch:{ all -> 0x0097 }
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0097 }
                java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0097 }
                android.util.Log.d(r3, r5)     // Catch:{ all -> 0x0097 }
                com.facebook.internal.Utility.closeQuietly(r1)     // Catch:{ all -> 0x005b }
                java.lang.String r3 = "AppEventsLogger.persistedsessioninfo"
                r7.deleteFile(r3)     // Catch:{ all -> 0x005b }
                java.util.Map<com.facebook.appevents.AppEventsLogger$AccessTokenAppIdPair, com.facebook.appevents.FacebookTimeSpentData> r3 = appSessionInfoMap     // Catch:{ all -> 0x005b }
                if (r3 != 0) goto L_0x0090
                java.util.HashMap r3 = new java.util.HashMap     // Catch:{ all -> 0x005b }
                r3.<init>()     // Catch:{ all -> 0x005b }
                appSessionInfoMap = r3     // Catch:{ all -> 0x005b }
            L_0x0090:
                r3 = 1
                isLoaded = r3     // Catch:{ all -> 0x005b }
                r3 = 0
                hasChanges = r3     // Catch:{ all -> 0x005b }
                goto L_0x003e
            L_0x0097:
                r3 = move-exception
            L_0x0098:
                com.facebook.internal.Utility.closeQuietly(r1)     // Catch:{ all -> 0x005b }
                java.lang.String r5 = "AppEventsLogger.persistedsessioninfo"
                r7.deleteFile(r5)     // Catch:{ all -> 0x005b }
                java.util.Map<com.facebook.appevents.AppEventsLogger$AccessTokenAppIdPair, com.facebook.appevents.FacebookTimeSpentData> r5 = appSessionInfoMap     // Catch:{ all -> 0x005b }
                if (r5 != 0) goto L_0x00ab
                java.util.HashMap r5 = new java.util.HashMap     // Catch:{ all -> 0x005b }
                r5.<init>()     // Catch:{ all -> 0x005b }
                appSessionInfoMap = r5     // Catch:{ all -> 0x005b }
            L_0x00ab:
                r5 = 1
                isLoaded = r5     // Catch:{ all -> 0x005b }
                r5 = 0
                hasChanges = r5     // Catch:{ all -> 0x005b }
                throw r3     // Catch:{ all -> 0x005b }
            L_0x00b2:
                r3 = move-exception
                r1 = r2
                goto L_0x005c
            L_0x00b5:
                r3 = move-exception
                r1 = r2
                goto L_0x0098
            L_0x00b8:
                r0 = move-exception
                r1 = r2
                goto L_0x005f
            L_0x00bb:
                r3 = move-exception
                r1 = r2
                goto L_0x0041
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.restoreAppSessionInformation(android.content.Context):void");
        }

        /* JADX WARNING: Unknown top exception splitter block from list: {B:24:0x0057=Splitter:B:24:0x0057, B:12:0x002e=Splitter:B:12:0x002e} */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        static void saveAppSessionInformation(android.content.Context r7) {
            /*
                r1 = 0
                java.lang.Object r4 = staticLock
                monitor-enter(r4)
                boolean r3 = hasChanges     // Catch:{ all -> 0x0053 }
                if (r3 == 0) goto L_0x002e
                java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ Exception -> 0x0030 }
                java.io.BufferedOutputStream r3 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x0030 }
                java.lang.String r5 = "AppEventsLogger.persistedsessioninfo"
                r6 = 0
                java.io.FileOutputStream r5 = r7.openFileOutput(r5, r6)     // Catch:{ Exception -> 0x0030 }
                r3.<init>(r5)     // Catch:{ Exception -> 0x0030 }
                r2.<init>(r3)     // Catch:{ Exception -> 0x0030 }
                java.util.Map<com.facebook.appevents.AppEventsLogger$AccessTokenAppIdPair, com.facebook.appevents.FacebookTimeSpentData> r3 = appSessionInfoMap     // Catch:{ Exception -> 0x0061, all -> 0x005e }
                r2.writeObject(r3)     // Catch:{ Exception -> 0x0061, all -> 0x005e }
                r3 = 0
                hasChanges = r3     // Catch:{ Exception -> 0x0061, all -> 0x005e }
                com.facebook.LoggingBehavior r3 = com.facebook.LoggingBehavior.APP_EVENTS     // Catch:{ Exception -> 0x0061, all -> 0x005e }
                java.lang.String r5 = "AppEvents"
                java.lang.String r6 = "App session info saved"
                com.facebook.internal.Logger.log(r3, r5, r6)     // Catch:{ Exception -> 0x0061, all -> 0x005e }
                com.facebook.internal.Utility.closeQuietly(r2)     // Catch:{ all -> 0x005b }
                r1 = r2
            L_0x002e:
                monitor-exit(r4)     // Catch:{ all -> 0x0053 }
                return
            L_0x0030:
                r0 = move-exception
            L_0x0031:
                java.lang.String r3 = com.facebook.appevents.AppEventsLogger.TAG     // Catch:{ all -> 0x0056 }
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0056 }
                r5.<init>()     // Catch:{ all -> 0x0056 }
                java.lang.String r6 = "Got unexpected exception: "
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0056 }
                java.lang.String r6 = r0.toString()     // Catch:{ all -> 0x0056 }
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0056 }
                java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0056 }
                android.util.Log.d(r3, r5)     // Catch:{ all -> 0x0056 }
                com.facebook.internal.Utility.closeQuietly(r1)     // Catch:{ all -> 0x0053 }
                goto L_0x002e
            L_0x0053:
                r3 = move-exception
            L_0x0054:
                monitor-exit(r4)     // Catch:{ all -> 0x0053 }
                throw r3
            L_0x0056:
                r3 = move-exception
            L_0x0057:
                com.facebook.internal.Utility.closeQuietly(r1)     // Catch:{ all -> 0x0053 }
                throw r3     // Catch:{ all -> 0x0053 }
            L_0x005b:
                r3 = move-exception
                r1 = r2
                goto L_0x0054
            L_0x005e:
                r3 = move-exception
                r1 = r2
                goto L_0x0057
            L_0x0061:
                r0 = move-exception
                r1 = r2
                goto L_0x0031
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.AppEventsLogger.PersistedAppSessionInfo.saveAppSessionInformation(android.content.Context):void");
        }
    }

    static class PersistedEvents {
        static final String PERSISTED_EVENTS_FILENAME = "AppEventsLogger.persistedevents";
        private static Object staticLock = new Object();
        private Context context;
        private HashMap<AccessTokenAppIdPair, List<AppEvent>> persistedEvents = new HashMap<>();

        private PersistedEvents(Context context2) {
            this.context = context2;
        }

        public static void persistEvents(Context context2, AccessTokenAppIdPair accessTokenAppIdPair, SessionEventsState sessionEventsState) {
            HashMap hashMap = new HashMap();
            hashMap.put(accessTokenAppIdPair, sessionEventsState);
            persistEvents(context2, hashMap);
        }

        public static void persistEvents(Context context2, Map<AccessTokenAppIdPair, SessionEventsState> map) {
            synchronized (staticLock) {
                PersistedEvents readAndClearStore = readAndClearStore(context2);
                for (Map.Entry next : map.entrySet()) {
                    List<AppEvent> eventsToPersist = ((SessionEventsState) next.getValue()).getEventsToPersist();
                    if (eventsToPersist.size() != 0) {
                        readAndClearStore.addEvents((AccessTokenAppIdPair) next.getKey(), eventsToPersist);
                    }
                }
                readAndClearStore.write();
            }
        }

        public static PersistedEvents readAndClearStore(Context context2) {
            PersistedEvents persistedEvents2;
            synchronized (staticLock) {
                persistedEvents2 = new PersistedEvents(context2);
                persistedEvents2.readAndClearStore();
            }
            return persistedEvents2;
        }

        private void readAndClearStore() {
            ObjectInputStream objectInputStream = null;
            try {
                ObjectInputStream objectInputStream2 = new ObjectInputStream(new BufferedInputStream(this.context.openFileInput(PERSISTED_EVENTS_FILENAME)));
                try {
                    this.context.getFileStreamPath(PERSISTED_EVENTS_FILENAME).delete();
                    this.persistedEvents = (HashMap) objectInputStream2.readObject();
                    Utility.closeQuietly(objectInputStream2);
                    ObjectInputStream objectInputStream3 = objectInputStream2;
                } catch (FileNotFoundException e) {
                    objectInputStream = objectInputStream2;
                    Utility.closeQuietly(objectInputStream);
                } catch (Exception e2) {
                    e = e2;
                    objectInputStream = objectInputStream2;
                    try {
                        Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + e.toString());
                        Utility.closeQuietly(objectInputStream);
                    } catch (Throwable th) {
                        th = th;
                        Utility.closeQuietly(objectInputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    objectInputStream = objectInputStream2;
                    Utility.closeQuietly(objectInputStream);
                    throw th;
                }
            } catch (FileNotFoundException e3) {
                Utility.closeQuietly(objectInputStream);
            } catch (Exception e4) {
                e = e4;
                Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + e.toString());
                Utility.closeQuietly(objectInputStream);
            }
        }

        private void write() {
            ObjectOutputStream objectOutputStream = null;
            try {
                ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new BufferedOutputStream(this.context.openFileOutput(PERSISTED_EVENTS_FILENAME, 0)));
                try {
                    objectOutputStream2.writeObject(this.persistedEvents);
                    Utility.closeQuietly(objectOutputStream2);
                    ObjectOutputStream objectOutputStream3 = objectOutputStream2;
                } catch (Exception e) {
                    e = e;
                    objectOutputStream = objectOutputStream2;
                    try {
                        Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + e.toString());
                        Utility.closeQuietly(objectOutputStream);
                    } catch (Throwable th) {
                        th = th;
                        Utility.closeQuietly(objectOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    objectOutputStream = objectOutputStream2;
                    Utility.closeQuietly(objectOutputStream);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + e.toString());
                Utility.closeQuietly(objectOutputStream);
            }
        }

        public void addEvents(AccessTokenAppIdPair accessTokenAppIdPair, List<AppEvent> list) {
            if (!this.persistedEvents.containsKey(accessTokenAppIdPair)) {
                this.persistedEvents.put(accessTokenAppIdPair, new ArrayList());
            }
            this.persistedEvents.get(accessTokenAppIdPair).addAll(list);
        }

        public List<AppEvent> getEvents(AccessTokenAppIdPair accessTokenAppIdPair) {
            return this.persistedEvents.get(accessTokenAppIdPair);
        }

        public Set<AccessTokenAppIdPair> keySet() {
            return this.persistedEvents.keySet();
        }
    }

    static class SessionEventsState {
        public static final String ENCODED_EVENTS_KEY = "encoded_events";
        public static final String EVENT_COUNT_KEY = "event_count";
        public static final String NUM_SKIPPED_KEY = "num_skipped";
        private final int MAX_ACCUMULATED_LOG_EVENTS = 1000;
        private List<AppEvent> accumulatedEvents = new ArrayList();
        private String anonymousAppDeviceGUID;
        private AttributionIdentifiers attributionIdentifiers;
        private List<AppEvent> inFlightEvents = new ArrayList();
        private int numSkippedEventsDueToFullBuffer;
        private String packageName;

        public SessionEventsState(AttributionIdentifiers attributionIdentifiers2, String str, String str2) {
            this.attributionIdentifiers = attributionIdentifiers2;
            this.packageName = str;
            this.anonymousAppDeviceGUID = str2;
        }

        private byte[] getStringAsByteArray(String str) {
            try {
                return str.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                Utility.logd("Encoding exception: ", (Exception) e);
                return null;
            }
        }

        private void populateRequest(GraphRequest graphRequest, int i, JSONArray jSONArray, boolean z) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("event", "CUSTOM_APP_EVENTS");
                if (this.numSkippedEventsDueToFullBuffer > 0) {
                    jSONObject.put("num_skipped_events", i);
                }
                Utility.setAppEventAttributionParameters(jSONObject, this.attributionIdentifiers, this.anonymousAppDeviceGUID, z);
                try {
                    Utility.setAppEventExtendedDeviceInfoParameters(jSONObject, AppEventsLogger.applicationContext);
                } catch (Exception e) {
                }
                jSONObject.put("application_package_name", this.packageName);
            } catch (JSONException e2) {
            }
            graphRequest.setGraphObject(jSONObject);
            Bundle parameters = graphRequest.getParameters();
            if (parameters == null) {
                parameters = new Bundle();
            }
            String jSONArray2 = jSONArray.toString();
            if (jSONArray2 != null) {
                parameters.putByteArray("custom_events_file", getStringAsByteArray(jSONArray2));
                graphRequest.setTag(jSONArray2);
            }
            graphRequest.setParameters(parameters);
        }

        public synchronized void accumulatePersistedEvents(List<AppEvent> list) {
            this.accumulatedEvents.addAll(list);
        }

        public synchronized void addEvent(AppEvent appEvent) {
            if (this.accumulatedEvents.size() + this.inFlightEvents.size() >= 1000) {
                this.numSkippedEventsDueToFullBuffer++;
            } else {
                this.accumulatedEvents.add(appEvent);
            }
        }

        public synchronized void clearInFlightAndStats(boolean z) {
            if (z) {
                this.accumulatedEvents.addAll(this.inFlightEvents);
            }
            this.inFlightEvents.clear();
            this.numSkippedEventsDueToFullBuffer = 0;
        }

        public synchronized int getAccumulatedEventCount() {
            return this.accumulatedEvents.size();
        }

        public synchronized List<AppEvent> getEventsToPersist() {
            List<AppEvent> list;
            list = this.accumulatedEvents;
            this.accumulatedEvents = new ArrayList();
            return list;
        }

        public int populateRequest(GraphRequest graphRequest, boolean z, boolean z2) {
            synchronized (this) {
                int i = this.numSkippedEventsDueToFullBuffer;
                this.inFlightEvents.addAll(this.accumulatedEvents);
                this.accumulatedEvents.clear();
                JSONArray jSONArray = new JSONArray();
                for (AppEvent next : this.inFlightEvents) {
                    if (z || !next.getIsImplicit()) {
                        jSONArray.put(next.getJSONObject());
                    }
                }
                if (jSONArray.length() == 0) {
                    return 0;
                }
                populateRequest(graphRequest, i, jSONArray, z2);
                return jSONArray.length();
            }
        }
    }

    private AppEventsLogger(Context context2, String str, AccessToken accessToken) {
        Validate.notNull(context2, "context");
        this.context = context2;
        accessToken = accessToken == null ? AccessToken.getCurrentAccessToken() : accessToken;
        if (accessToken == null || (str != null && !str.equals(accessToken.getApplicationId()))) {
            this.accessTokenAppId = new AccessTokenAppIdPair((String) null, str == null ? Utility.getMetadataApplicationId(context2) : str);
        } else {
            this.accessTokenAppId = new AccessTokenAppIdPair(accessToken);
        }
        synchronized (staticLock) {
            if (applicationContext == null) {
                applicationContext = context2.getApplicationContext();
            }
        }
        initializeTimersIfNeeded();
    }

    private static int accumulatePersistedEvents() {
        PersistedEvents readAndClearStore = PersistedEvents.readAndClearStore(applicationContext);
        int i = 0;
        for (AccessTokenAppIdPair next : readAndClearStore.keySet()) {
            SessionEventsState sessionEventsState = getSessionEventsState(applicationContext, next);
            List<AppEvent> events = readAndClearStore.getEvents(next);
            sessionEventsState.accumulatePersistedEvents(events);
            i += events.size();
        }
        return i;
    }

    public static void activateApp(Context context2) {
        FacebookSdk.sdkInitialize(context2);
        activateApp(context2, Utility.getMetadataApplicationId(context2));
    }

    public static void activateApp(Context context2, String str) {
        if (context2 == null || str == null) {
            throw new IllegalArgumentException("Both context and applicationId must be non-null");
        }
        if (context2 instanceof Activity) {
            setSourceApplication((Activity) context2);
        } else {
            resetSourceApplication();
            Log.d(AppEventsLogger.class.getName(), "To set source application the context of activateApp must be an instance of Activity");
        }
        FacebookSdk.publishInstallAsync(context2, str);
        AppEventsLogger appEventsLogger = new AppEventsLogger(context2, str, (AccessToken) null);
        final long currentTimeMillis = System.currentTimeMillis();
        final String sourceApplication2 = getSourceApplication();
        backgroundExecutor.execute(new Runnable(appEventsLogger) {
            final /* synthetic */ AppEventsLogger val$logger;

            {
                this.val$logger = r1;
            }

            public void run() {
                this.val$logger.logAppSessionResumeEvent(currentTimeMillis, sourceApplication2);
            }
        });
    }

    private static FlushStatistics buildAndExecuteRequests(FlushReason flushReason, Set<AccessTokenAppIdPair> set) {
        GraphRequest buildRequestForSession;
        FlushStatistics flushStatistics = new FlushStatistics();
        boolean limitEventAndDataUsage = FacebookSdk.getLimitEventAndDataUsage(applicationContext);
        ArrayList<GraphRequest> arrayList = new ArrayList<>();
        for (AccessTokenAppIdPair next : set) {
            SessionEventsState sessionEventsState = getSessionEventsState(next);
            if (!(sessionEventsState == null || (buildRequestForSession = buildRequestForSession(next, sessionEventsState, limitEventAndDataUsage, flushStatistics)) == null)) {
                arrayList.add(buildRequestForSession);
            }
        }
        if (arrayList.size() <= 0) {
            return null;
        }
        Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Flushing %d events due to %s.", Integer.valueOf(flushStatistics.numEvents), flushReason.toString());
        for (GraphRequest executeAndWait : arrayList) {
            executeAndWait.executeAndWait();
        }
        return flushStatistics;
    }

    private static GraphRequest buildRequestForSession(final AccessTokenAppIdPair accessTokenAppIdPair, final SessionEventsState sessionEventsState, boolean z, final FlushStatistics flushStatistics) {
        String applicationId = accessTokenAppIdPair.getApplicationId();
        Utility.FetchedAppSettings queryAppSettings = Utility.queryAppSettings(applicationId, false);
        final GraphRequest newPostRequest = GraphRequest.newPostRequest((AccessToken) null, String.format("%s/activities", new Object[]{applicationId}), (JSONObject) null, (GraphRequest.Callback) null);
        Bundle parameters = newPostRequest.getParameters();
        if (parameters == null) {
            parameters = new Bundle();
        }
        parameters.putString("access_token", accessTokenAppIdPair.getAccessTokenString());
        newPostRequest.setParameters(parameters);
        if (queryAppSettings == null) {
            return null;
        }
        int populateRequest = sessionEventsState.populateRequest(newPostRequest, queryAppSettings.supportsImplicitLogging(), z);
        if (populateRequest == 0) {
            return null;
        }
        flushStatistics.numEvents += populateRequest;
        newPostRequest.setCallback(new GraphRequest.Callback() {
            public void onCompleted(GraphResponse graphResponse) {
                AppEventsLogger.handleResponse(accessTokenAppIdPair, newPostRequest, graphResponse, sessionEventsState, flushStatistics);
            }
        });
        return newPostRequest;
    }

    public static void deactivateApp(Context context2) {
        deactivateApp(context2, Utility.getMetadataApplicationId(context2));
    }

    public static void deactivateApp(Context context2, String str) {
        if (context2 == null || str == null) {
            throw new IllegalArgumentException("Both context and applicationId must be non-null");
        }
        resetSourceApplication();
        AppEventsLogger appEventsLogger = new AppEventsLogger(context2, str, (AccessToken) null);
        final long currentTimeMillis = System.currentTimeMillis();
        backgroundExecutor.execute(new Runnable(appEventsLogger) {
            final /* synthetic */ AppEventsLogger val$logger;

            {
                this.val$logger = r1;
            }

            public void run() {
                this.val$logger.logAppSessionSuspendEvent(currentTimeMillis);
            }
        });
    }

    static void eagerFlush() {
        if (getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY) {
            flush(FlushReason.EAGER_FLUSHING_EVENT);
        }
    }

    private static void flush(final FlushReason flushReason) {
        FacebookSdk.getExecutor().execute(new Runnable() {
            public void run() {
                AppEventsLogger.flushAndWait(flushReason);
            }
        });
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r1 = buildAndExecuteRequests(r6, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004c, code lost:
        com.facebook.internal.Utility.logd(TAG, "Caught unexpected exception while flushing: ", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0018, code lost:
        accumulatePersistedEvents();
        r1 = null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void flushAndWait(com.facebook.appevents.AppEventsLogger.FlushReason r6) {
        /*
            java.lang.Object r5 = staticLock
            monitor-enter(r5)
            boolean r4 = requestInFlight     // Catch:{ all -> 0x0048 }
            if (r4 == 0) goto L_0x0009
            monitor-exit(r5)     // Catch:{ all -> 0x0048 }
        L_0x0008:
            return
        L_0x0009:
            r4 = 1
            requestInFlight = r4     // Catch:{ all -> 0x0048 }
            java.util.HashSet r3 = new java.util.HashSet     // Catch:{ all -> 0x0048 }
            java.util.Map<com.facebook.appevents.AppEventsLogger$AccessTokenAppIdPair, com.facebook.appevents.AppEventsLogger$SessionEventsState> r4 = stateMap     // Catch:{ all -> 0x0048 }
            java.util.Set r4 = r4.keySet()     // Catch:{ all -> 0x0048 }
            r3.<init>(r4)     // Catch:{ all -> 0x0048 }
            monitor-exit(r5)     // Catch:{ all -> 0x0048 }
            accumulatePersistedEvents()
            r1 = 0
            com.facebook.appevents.AppEventsLogger$FlushStatistics r1 = buildAndExecuteRequests(r6, r3)     // Catch:{ Exception -> 0x004b }
        L_0x0020:
            java.lang.Object r5 = staticLock
            monitor-enter(r5)
            r4 = 0
            requestInFlight = r4     // Catch:{ all -> 0x0054 }
            monitor-exit(r5)     // Catch:{ all -> 0x0054 }
            if (r1 == 0) goto L_0x0008
            android.content.Intent r2 = new android.content.Intent
            java.lang.String r4 = "com.facebook.sdk.APP_EVENTS_FLUSHED"
            r2.<init>(r4)
            java.lang.String r4 = "com.facebook.sdk.APP_EVENTS_NUM_EVENTS_FLUSHED"
            int r5 = r1.numEvents
            r2.putExtra(r4, r5)
            java.lang.String r4 = "com.facebook.sdk.APP_EVENTS_FLUSH_RESULT"
            com.facebook.appevents.AppEventsLogger$FlushResult r5 = r1.result
            r2.putExtra(r4, r5)
            android.content.Context r4 = applicationContext
            android.support.v4.content.LocalBroadcastManager r4 = android.support.v4.content.LocalBroadcastManager.getInstance(r4)
            r4.sendBroadcast(r2)
            goto L_0x0008
        L_0x0048:
            r4 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0048 }
            throw r4
        L_0x004b:
            r0 = move-exception
            java.lang.String r4 = TAG
            java.lang.String r5 = "Caught unexpected exception while flushing: "
            com.facebook.internal.Utility.logd(r4, r5, r0)
            goto L_0x0020
        L_0x0054:
            r4 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0054 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.AppEventsLogger.flushAndWait(com.facebook.appevents.AppEventsLogger$FlushReason):void");
    }

    /* access modifiers changed from: private */
    public static void flushIfNecessary() {
        synchronized (staticLock) {
            if (getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY && getAccumulatedEventCount() > 100) {
                flush(FlushReason.EVENT_THRESHOLD);
            }
        }
    }

    private static int getAccumulatedEventCount() {
        int i;
        synchronized (staticLock) {
            i = 0;
            for (SessionEventsState accumulatedEventCount : stateMap.values()) {
                i += accumulatedEventCount.getAccumulatedEventCount();
            }
        }
        return i;
    }

    public static String getAnonymousAppDeviceGUID(Context context2) {
        if (anonymousAppDeviceGUID == null) {
            synchronized (staticLock) {
                if (anonymousAppDeviceGUID == null) {
                    anonymousAppDeviceGUID = context2.getSharedPreferences(APP_EVENT_PREFERENCES, 0).getString("anonymousAppDeviceGUID", (String) null);
                    if (anonymousAppDeviceGUID == null) {
                        anonymousAppDeviceGUID = "XZ" + UUID.randomUUID().toString();
                        context2.getSharedPreferences(APP_EVENT_PREFERENCES, 0).edit().putString("anonymousAppDeviceGUID", anonymousAppDeviceGUID).apply();
                    }
                }
            }
        }
        return anonymousAppDeviceGUID;
    }

    public static FlushBehavior getFlushBehavior() {
        FlushBehavior flushBehavior2;
        synchronized (staticLock) {
            flushBehavior2 = flushBehavior;
        }
        return flushBehavior2;
    }

    /* access modifiers changed from: private */
    public static SessionEventsState getSessionEventsState(Context context2, AccessTokenAppIdPair accessTokenAppIdPair) {
        AttributionIdentifiers attributionIdentifiers = null;
        if (stateMap.get(accessTokenAppIdPair) == null) {
            attributionIdentifiers = AttributionIdentifiers.getAttributionIdentifiers(context2);
        }
        synchronized (staticLock) {
            try {
                SessionEventsState sessionEventsState = stateMap.get(accessTokenAppIdPair);
                if (sessionEventsState == null) {
                    SessionEventsState sessionEventsState2 = new SessionEventsState(attributionIdentifiers, context2.getPackageName(), getAnonymousAppDeviceGUID(context2));
                    try {
                        stateMap.put(accessTokenAppIdPair, sessionEventsState2);
                        sessionEventsState = sessionEventsState2;
                    } catch (Throwable th) {
                        th = th;
                        SessionEventsState sessionEventsState3 = sessionEventsState2;
                        throw th;
                    }
                }
                return sessionEventsState;
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    private static SessionEventsState getSessionEventsState(AccessTokenAppIdPair accessTokenAppIdPair) {
        SessionEventsState sessionEventsState;
        synchronized (staticLock) {
            sessionEventsState = stateMap.get(accessTokenAppIdPair);
        }
        return sessionEventsState;
    }

    static String getSourceApplication() {
        String str = "Unclassified";
        if (isOpenedByApplink) {
            str = "Applink";
        }
        return sourceApplication != null ? str + "(" + sourceApplication + ")" : str;
    }

    /* access modifiers changed from: private */
    public static void handleResponse(AccessTokenAppIdPair accessTokenAppIdPair, GraphRequest graphRequest, GraphResponse graphResponse, SessionEventsState sessionEventsState, FlushStatistics flushStatistics) {
        String str;
        FacebookRequestError error = graphResponse.getError();
        String str2 = "Success";
        FlushResult flushResult = FlushResult.SUCCESS;
        if (error != null) {
            if (error.getErrorCode() == -1) {
                str2 = "Failed: No Connectivity";
                flushResult = FlushResult.NO_CONNECTIVITY;
            } else {
                str2 = String.format("Failed:\n  Response: %s\n  Error %s", new Object[]{graphResponse.toString(), error.toString()});
                flushResult = FlushResult.SERVER_ERROR;
            }
        }
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.APP_EVENTS)) {
            try {
                str = new JSONArray((String) graphRequest.getTag()).toString(2);
            } catch (JSONException e) {
                str = "<Can't encode events for debug logging>";
            }
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Flush completed\nParams: %s\n  Result: %s\n  Events JSON: %s", graphRequest.getGraphObject().toString(), str2, str);
        }
        sessionEventsState.clearInFlightAndStats(error != null);
        if (flushResult == FlushResult.NO_CONNECTIVITY) {
            PersistedEvents.persistEvents(applicationContext, accessTokenAppIdPair, sessionEventsState);
        }
        if (flushResult != FlushResult.SUCCESS && flushStatistics.result != FlushResult.NO_CONNECTIVITY) {
            flushStatistics.result = flushResult;
        }
    }

    private static void initializeTimersIfNeeded() {
        synchronized (staticLock) {
            if (backgroundExecutor == null) {
                backgroundExecutor = new ScheduledThreadPoolExecutor(1);
                backgroundExecutor.scheduleAtFixedRate(new Runnable() {
                    public void run() {
                        if (AppEventsLogger.getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY) {
                            AppEventsLogger.flushAndWait(FlushReason.TIMER);
                        }
                    }
                }, 0, 15, TimeUnit.SECONDS);
                backgroundExecutor.scheduleAtFixedRate(new Runnable() {
                    public void run() {
                        HashSet<String> hashSet = new HashSet<>();
                        synchronized (AppEventsLogger.staticLock) {
                            for (AccessTokenAppIdPair applicationId : AppEventsLogger.stateMap.keySet()) {
                                hashSet.add(applicationId.getApplicationId());
                            }
                        }
                        for (String queryAppSettings : hashSet) {
                            Utility.queryAppSettings(queryAppSettings, true);
                        }
                    }
                }, 0, 86400, TimeUnit.SECONDS);
            }
        }
    }

    /* access modifiers changed from: private */
    public void logAppSessionResumeEvent(long j, String str) {
        PersistedAppSessionInfo.onResume(applicationContext, this.accessTokenAppId, this, j, str);
    }

    /* access modifiers changed from: private */
    public void logAppSessionSuspendEvent(long j) {
        PersistedAppSessionInfo.onSuspend(applicationContext, this.accessTokenAppId, this, j);
    }

    private static void logEvent(final Context context2, final AppEvent appEvent, final AccessTokenAppIdPair accessTokenAppIdPair) {
        FacebookSdk.getExecutor().execute(new Runnable() {
            public void run() {
                AppEventsLogger.getSessionEventsState(context2, accessTokenAppIdPair).addEvent(appEvent);
                AppEventsLogger.flushIfNecessary();
            }
        });
    }

    private void logEvent(String str, Double d, Bundle bundle, boolean z) {
        logEvent(this.context, new AppEvent(this.context, str, d, bundle, z), this.accessTokenAppId);
    }

    public static AppEventsLogger newLogger(Context context2) {
        return new AppEventsLogger(context2, (String) null, (AccessToken) null);
    }

    public static AppEventsLogger newLogger(Context context2, AccessToken accessToken) {
        return new AppEventsLogger(context2, (String) null, accessToken);
    }

    public static AppEventsLogger newLogger(Context context2, String str) {
        return new AppEventsLogger(context2, str, (AccessToken) null);
    }

    public static AppEventsLogger newLogger(Context context2, String str, AccessToken accessToken) {
        return new AppEventsLogger(context2, str, accessToken);
    }

    private static void notifyDeveloperError(String str) {
        Logger.log(LoggingBehavior.DEVELOPER_ERRORS, "AppEvents", str);
    }

    public static void onContextStop() {
        PersistedEvents.persistEvents(applicationContext, stateMap);
    }

    static void resetSourceApplication() {
        sourceApplication = null;
        isOpenedByApplink = false;
    }

    public static void setFlushBehavior(FlushBehavior flushBehavior2) {
        synchronized (staticLock) {
            flushBehavior = flushBehavior2;
        }
    }

    private static void setSourceApplication(Activity activity) {
        ComponentName callingActivity = activity.getCallingActivity();
        if (callingActivity != null) {
            String packageName = callingActivity.getPackageName();
            if (packageName.equals(activity.getPackageName())) {
                resetSourceApplication();
                return;
            }
            sourceApplication = packageName;
        }
        Intent intent = activity.getIntent();
        if (intent == null || intent.getBooleanExtra(SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, false)) {
            resetSourceApplication();
            return;
        }
        Bundle appLinkData = AppLinks.getAppLinkData(intent);
        if (appLinkData == null) {
            resetSourceApplication();
            return;
        }
        isOpenedByApplink = true;
        Bundle bundle = appLinkData.getBundle("referer_app_link");
        if (bundle == null) {
            sourceApplication = null;
            return;
        }
        sourceApplication = bundle.getString("package");
        intent.putExtra(SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, true);
    }

    static void setSourceApplication(String str, boolean z) {
        sourceApplication = str;
        isOpenedByApplink = z;
    }

    public void flush() {
        flush(FlushReason.EXPLICIT);
    }

    public String getApplicationId() {
        return this.accessTokenAppId.getApplicationId();
    }

    public boolean isValidForAccessToken(AccessToken accessToken) {
        return this.accessTokenAppId.equals(new AccessTokenAppIdPair(accessToken));
    }

    public void logEvent(String str) {
        logEvent(str, (Bundle) null);
    }

    public void logEvent(String str, double d) {
        logEvent(str, d, (Bundle) null);
    }

    public void logEvent(String str, double d, Bundle bundle) {
        logEvent(str, Double.valueOf(d), bundle, false);
    }

    public void logEvent(String str, Bundle bundle) {
        logEvent(str, (Double) null, bundle, false);
    }

    public void logPurchase(BigDecimal bigDecimal, Currency currency) {
        logPurchase(bigDecimal, currency, (Bundle) null);
    }

    public void logPurchase(BigDecimal bigDecimal, Currency currency, Bundle bundle) {
        if (bigDecimal == null) {
            notifyDeveloperError("purchaseAmount cannot be null");
        } else if (currency == null) {
            notifyDeveloperError("currency cannot be null");
        } else {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency.getCurrencyCode());
            logEvent(AppEventsConstants.EVENT_NAME_PURCHASED, bigDecimal.doubleValue(), bundle);
            eagerFlush();
        }
    }

    public void logSdkEvent(String str, Double d, Bundle bundle) {
        logEvent(str, d, bundle, true);
    }
}
