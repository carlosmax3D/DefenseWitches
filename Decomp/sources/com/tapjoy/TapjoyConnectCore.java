package com.tapjoy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.facebook.appevents.AppEventsConstants;
import com.tapjoy.TJAdUnitConstants;
import com.threatmetrix.TrustDefenderMobile.ProfileNotify;
import com.threatmetrix.TrustDefenderMobile.TrustDefenderMobile;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Vector;
import jp.stargarage.g2metrics.BuildConfig;
import org.w3c.dom.Document;

public class TapjoyConnectCore {
    private static final String DOCUMENTATION_URL = "http://tech.tapjoy.com";
    public static final int MAX_NUMBER_OF_OFFLINE_LOGS = 50;
    private static final String TAG = "TapjoyConnect";
    protected static String adIdCheckDisabled = BuildConfig.FLAVOR;
    protected static boolean adTrackingEnabled;
    protected static String advertisingID = BuildConfig.FLAVOR;
    private static String androidID = BuildConfig.FLAVOR;
    private static String appID = BuildConfig.FLAVOR;
    private static String appVersion = BuildConfig.FLAVOR;
    private static String bridgeVersion = BuildConfig.FLAVOR;
    private static String carrierCountryCode = BuildConfig.FLAVOR;
    private static String carrierName = BuildConfig.FLAVOR;
    private static Hashtable<String, Object> connectFlags = TapjoyConnectFlag.CONNECT_FLAG_DEFAULTS;
    private static TapjoyConnectNotifier connectNotifier = null;
    private static String connectionSubType = BuildConfig.FLAVOR;
    private static String connectionType = BuildConfig.FLAVOR;
    /* access modifiers changed from: private */
    public static Context context = null;
    private static float currencyMultiplier = 1.0f;
    private static Vector<String> dependencyClassesRequired = new Vector<>(Arrays.asList(TapjoyConstants.dependencyClassNames));
    private static String deviceCountryCode = BuildConfig.FLAVOR;
    protected static int deviceGooglePlayServicesVersion = 0;
    private static String deviceID = BuildConfig.FLAVOR;
    private static String deviceLanguage = BuildConfig.FLAVOR;
    private static boolean deviceLocation = false;
    private static String deviceManufacturer = BuildConfig.FLAVOR;
    private static String deviceModel = BuildConfig.FLAVOR;
    private static String deviceOSVersion = BuildConfig.FLAVOR;
    private static int deviceScreenDensity = 1;
    private static float deviceScreenDensityScale = 1.0f;
    private static int deviceScreenLayoutSize = 1;
    private static String deviceType = BuildConfig.FLAVOR;
    private static String installID = BuildConfig.FLAVOR;
    private static boolean isViewShowing = false;
    private static long lastTimeStamp = 0;
    private static String libraryVersion = BuildConfig.FLAVOR;
    private static String macAddress = BuildConfig.FLAVOR;
    private static String matchingPackageNames = BuildConfig.FLAVOR;
    private static String mobileCountryCode = BuildConfig.FLAVOR;
    private static String mobileNetworkCode = BuildConfig.FLAVOR;
    private static PackageManager packageManager;
    protected static int packagedGoogleServicesVersion = 0;
    /* access modifiers changed from: private */
    public static String paidAppActionID = null;
    protected static String persistentIdsDisabled = BuildConfig.FLAVOR;
    private static String platformName = BuildConfig.FLAVOR;

    /* renamed from: plugin  reason: collision with root package name */
    private static String f4plugin = "native";
    private static String redirectDomain = BuildConfig.FLAVOR;
    private static String sdkType = BuildConfig.FLAVOR;
    private static String secretKey = BuildConfig.FLAVOR;
    private static Hashtable<String, Object> segmentationParams = null;
    private static String sessionID = BuildConfig.FLAVOR;
    private static String sha2DeviceID = BuildConfig.FLAVOR;
    private static boolean shareFacebook = false;
    private static boolean shareGooglePlus = false;
    private static boolean shareLinkedIn = false;
    private static boolean shareTwitter = false;
    private static String storeName = BuildConfig.FLAVOR;
    private static boolean storeView = false;
    private static TapjoyConnectCore tapjoyConnectCore = null;
    /* access modifiers changed from: private */
    public static TapjoyURLConnection tapjoyURLConnection = null;
    /* access modifiers changed from: private */
    public static String threatmetrixSessionID;
    private static String userID = BuildConfig.FLAVOR;
    private static TapjoyViewNotifier viewNotifier = null;
    private boolean appPaused = false;
    /* access modifiers changed from: private */
    public long elapsed_time = 0;
    /* access modifiers changed from: private */
    public TapjoyGpsHelper gpsHelper;
    private boolean initialized = false;
    /* access modifiers changed from: private */
    public TrustDefenderMobile profile;
    private Timer timer = null;

    public class PPAThread implements Runnable {
        private Map<String, String> params;

        public PPAThread(Map<String, String> map) {
            this.params = map;
        }

        public void run() {
            TapjoyHttpURLResponse responseFromURL = TapjoyConnectCore.tapjoyURLConnection.getResponseFromURL(TapjoyConnectCore.getHostURL() + TapjoyConstants.TJC_CONNECT_URL_PATH, this.params);
            if (responseFromURL.response != null) {
                boolean unused = TapjoyConnectCore.this.handlePayPerActionResponse(responseFromURL.response);
            }
        }
    }

    private class PaidAppTimerTask extends TimerTask {
        private PaidAppTimerTask() {
        }

        public void run() {
            TapjoyConnectCore.access$414(TapjoyConnectCore.this, 10000);
            TapjoyLog.i(TapjoyConnectCore.TAG, "elapsed_time: " + TapjoyConnectCore.this.elapsed_time + " (" + ((TapjoyConnectCore.this.elapsed_time / 1000) / 60) + "m " + ((TapjoyConnectCore.this.elapsed_time / 1000) % 60) + "s)");
            SharedPreferences.Editor edit = TapjoyConnectCore.context.getSharedPreferences(TapjoyConstants.TJC_PREFERENCE, 0).edit();
            edit.putLong(TapjoyConstants.PREF_ELAPSED_TIME, TapjoyConnectCore.this.elapsed_time);
            edit.commit();
            if (TapjoyConnectCore.this.elapsed_time >= TapjoyConstants.PAID_APP_TIME) {
                TapjoyLog.i(TapjoyConnectCore.TAG, "timer done...");
                if (TapjoyConnectCore.paidAppActionID != null && TapjoyConnectCore.paidAppActionID.length() > 0) {
                    TapjoyLog.i(TapjoyConnectCore.TAG, "Calling PPA actionComplete...");
                    TapjoyConnectCore.this.actionComplete(TapjoyConnectCore.paidAppActionID);
                }
                cancel();
            }
        }
    }

    public TapjoyConnectCore(Context context2) throws TapjoyException {
        context = context2;
        tapjoyURLConnection = new TapjoyURLConnection();
        packageManager = context.getPackageManager();
        this.gpsHelper = new TapjoyGpsHelper(context);
        try {
            if (init()) {
                callConnect();
                this.initialized = true;
            }
        } catch (TapjoyIntegrationException e) {
            Log.e(TAG, "IntegrationException: " + e.getMessage());
            if (connectNotifier != null) {
                connectNotifier.connectFail();
            }
        } catch (TapjoyException e2) {
            Log.e(TAG, "TapjoyException: " + e2.getMessage());
            if (connectNotifier != null) {
                connectNotifier.connectFail();
            }
        }
    }

    static /* synthetic */ long access$414(TapjoyConnectCore tapjoyConnectCore2, long j) {
        long j2 = tapjoyConnectCore2.elapsed_time + j;
        tapjoyConnectCore2.elapsed_time = j2;
        return j2;
    }

    private void addConnectFlag(String str, String str2) {
        if ((str.equals(TapjoyConnectFlag.HOST_URL) || str.equals(TapjoyConnectFlag.EVENT_URL)) && !str2.endsWith("/")) {
            str2 = str2 + "/";
        }
        connectFlags.put(str, str2);
    }

    /* access modifiers changed from: private */
    public static boolean arePersistentIdsDisabled() {
        return isAdvertisingIdPresent() && getConnectFlagValue(TapjoyConnectFlag.DISABLE_PERSISTENT_IDS) != null && getConnectFlagValue(TapjoyConnectFlag.DISABLE_PERSISTENT_IDS).equals("true");
    }

    private void checkForDependency(ActivityInfo activityInfo) throws TapjoyIntegrationException {
        if (dependencyClassesRequired.contains(activityInfo.name)) {
            int indexOf = dependencyClassesRequired.indexOf(activityInfo.name);
            try {
                Class.forName(dependencyClassesRequired.get(indexOf));
                Vector vector = new Vector();
                if ((activityInfo.configChanges & 128) != 128) {
                    vector.add("orientation");
                }
                if ((activityInfo.configChanges & 32) != 32) {
                    vector.add("keyboardHidden");
                }
                if (vector.size() == 0) {
                    if (Build.VERSION.SDK_INT >= 13 && (activityInfo.configChanges & 1024) != 1024) {
                        TapjoyLog.w(TAG, "WARNING -- screenSize property is not specified in manifest configChanges for " + dependencyClassesRequired.get(indexOf));
                    }
                    if (Build.VERSION.SDK_INT < 11 || !activityInfo.name.equals("com.tapjoy.TJAdUnitView") || (activityInfo.flags & 512) == 512) {
                        dependencyClassesRequired.remove(indexOf);
                        return;
                    }
                    throw new TapjoyIntegrationException("'hardwareAccelerated' property not specified in manifest for " + dependencyClassesRequired.get(indexOf));
                } else if (vector.size() == 1) {
                    throw new TapjoyIntegrationException(vector.toString() + " property is not specified in manifest configChanges for " + dependencyClassesRequired.get(indexOf));
                } else {
                    throw new TapjoyIntegrationException(vector.toString() + " properties are not specified in manifest configChanges for " + dependencyClassesRequired.get(indexOf));
                }
            } catch (ClassNotFoundException e) {
                throw new TapjoyIntegrationException("[ClassNotFoundException] Could not find dependency class " + dependencyClassesRequired.get(indexOf));
            }
        }
    }

    private void checkManifestForConfigurations() {
        try {
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 128);
                if (applicationInfo == null || applicationInfo.metaData == null) {
                    TapjoyLog.i(TAG, "No metadata present.");
                    return;
                }
                for (String str : TapjoyConnectFlag.FLAG_ARRAY) {
                    String string = applicationInfo.metaData.getString("tapjoy." + str);
                    if (string != null) {
                        TapjoyLog.i(TAG, "Found manifest flag: " + str + ", " + string);
                        addConnectFlag(str, string);
                    }
                }
                TapjoyLog.i(TAG, "Metadata successfully loaded");
            }
        } catch (Exception e) {
            TapjoyLog.e(TAG, "Error reading manifest meta-data: " + e.toString());
        }
    }

    private void checkPermissions() throws TapjoyIntegrationException {
        Vector vector = new Vector();
        for (String str : TapjoyConstants.dependencyPermissions) {
            if (!isPermissionGranted(str)) {
                vector.add(str);
            }
        }
        if (vector.size() == 0) {
            Vector vector2 = new Vector();
            for (String str2 : TapjoyConstants.optionalPermissions) {
                if (!isPermissionGranted(str2)) {
                    vector2.add(str2);
                }
            }
            if (vector2.size() == 0) {
                return;
            }
            if (vector2.size() == 1) {
                TapjoyLog.w(TAG, "WARNING -- " + vector2.toString() + " permission was not found in manifest. The exclusion of this permission could cause problems.");
            } else {
                TapjoyLog.w(TAG, "WARNING -- " + vector2.toString() + " permissions were not found in manifest. The exclusion of these permissions could cause problems.");
            }
        } else if (vector.size() == 1) {
            throw new TapjoyIntegrationException("Missing 1 permission in manifest: " + vector.toString());
        } else {
            throw new TapjoyIntegrationException("Missing " + vector.size() + " permissions in manifest: " + vector.toString());
        }
    }

    private void checkResourceFileForConfigurations() {
        int identifier = context.getResources().getIdentifier("raw/tapjoy_config", (String) null, context.getPackageName());
        Properties properties = new Properties();
        try {
            properties.load(context.getResources().openRawResource(identifier));
            parsePropertiesIntoConfigFlags(properties);
        } catch (Exception e) {
        }
    }

    private void determineInstallID() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TapjoyConstants.TJC_PREFERENCE, 0);
        installID = sharedPreferences.getString(TapjoyConstants.PREF_INSTALL_ID, BuildConfig.FLAVOR);
        if (installID == null || installID.length() == 0) {
            try {
                installID = TapjoyUtil.SHA256(UUID.randomUUID().toString() + System.currentTimeMillis());
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString(TapjoyConstants.PREF_INSTALL_ID, installID);
                edit.commit();
            } catch (Exception e) {
                TapjoyLog.e(TAG, "Error generating install id: " + e.toString());
            }
        }
    }

    private static String generateSessionID() {
        String str = null;
        TapjoyLog.i(TAG, "generating sessionID...");
        try {
            str = TapjoyUtil.SHA256((System.currentTimeMillis() / 1000) + appID + deviceID);
            lastTimeStamp = System.currentTimeMillis();
            return str;
        } catch (Exception e) {
            TapjoyLog.e(TAG, "unable to generate session id: " + e.toString());
            return str;
        }
    }

    public static String getAndroidID() {
        return androidID;
    }

    public static String getAppID() {
        return appID;
    }

    public static String getAwardPointsVerifier(long j, int i, String str) {
        try {
            return TapjoyUtil.SHA256(appID + ":" + getVerifierID() + ":" + j + ":" + secretKey + ":" + i + ":" + str);
        } catch (Exception e) {
            TapjoyLog.e(TAG, "getAwardPointsVerifier ERROR: " + e.toString());
            return BuildConfig.FLAVOR;
        }
    }

    public static String getCarrierName() {
        return carrierName;
    }

    public static String getConnectFlagValue(String str) {
        return (connectFlags == null || connectFlags.get(str) == null) ? BuildConfig.FLAVOR : connectFlags.get(str).toString();
    }

    public static String getConnectURL() {
        return TapjoyConfig.TJC_CONNECT_SERVICE_URL;
    }

    public static String getConnectionSubType() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return BuildConfig.FLAVOR;
            }
            String subtypeName = connectivityManager.getActiveNetworkInfo().getSubtypeName();
            TapjoyLog.i(TAG, "connection_sub_type: " + subtypeName);
            return subtypeName;
        } catch (Exception e) {
            TapjoyLog.e(TAG, "getConnectionSubType error: " + e.toString());
            return BuildConfig.FLAVOR;
        }
    }

    public static String getConnectionType() {
        String str = BuildConfig.FLAVOR;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (!(connectivityManager == null || connectivityManager.getActiveNetworkInfo() == null)) {
                switch (connectivityManager.getActiveNetworkInfo().getType()) {
                    case 1:
                    case 6:
                        str = TapjoyConstants.TJC_CONNECTION_TYPE_WIFI;
                        break;
                    default:
                        str = TapjoyConstants.TJC_CONNECTION_TYPE_MOBILE;
                        break;
                }
                TapjoyLog.i(TAG, "connectivity: " + connectivityManager.getActiveNetworkInfo().getType());
                TapjoyLog.i(TAG, "connection_type: " + str);
            }
        } catch (Exception e) {
            TapjoyLog.e(TAG, "getConnectionType error: " + e.toString());
        }
        return str;
    }

    public static Context getContext() {
        return context;
    }

    public static String getDeviceID() {
        return deviceID;
    }

    public static float getDeviceScreenDensityScale() {
        return deviceScreenDensityScale;
    }

    public static String getEventURL() {
        return getConnectFlagValue(TapjoyConnectFlag.EVENT_URL);
    }

    public static Map<String, String> getGenericURLParams() {
        Map<String, String> paramsWithoutAppID = getParamsWithoutAppID();
        TapjoyUtil.safePut(paramsWithoutAppID, "app_id", appID, true);
        return paramsWithoutAppID;
    }

    public static String getHostURL() {
        return getConnectFlagValue(TapjoyConnectFlag.HOST_URL);
    }

    public static TapjoyConnectCore getInstance() {
        return tapjoyConnectCore;
    }

    public static String getMacAddress() {
        return macAddress;
    }

    public static Map<String, ?> getOfflineLogs() {
        return context.getSharedPreferences(TapjoyConstants.PREF_OFFLINE_LOG, 0).getAll();
    }

    private static String getPackageNamesVerifier(long j, String str) {
        try {
            return TapjoyUtil.SHA256(appID + ":" + getVerifierID() + ":" + j + ":" + secretKey + ":" + str);
        } catch (Exception e) {
            TapjoyLog.e(TAG, "getVerifier ERROR: " + e.toString());
            return BuildConfig.FLAVOR;
        }
    }

    private static Map<String, String> getParamsWithoutAppID() {
        HashMap hashMap = new HashMap();
        if (isAdvertisingIdPresent()) {
            TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_ADVERTISING_ID, advertisingID, true);
            TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_AD_TRACKING_ENABLED, String.valueOf(adTrackingEnabled), true);
        }
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_ADVERTISING_ID_CHECK_DISABLED, adIdCheckDisabled, true);
        if (!arePersistentIdsDisabled()) {
            TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_ANDROID_ID, androidID, true);
            TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_DEVICE_ID_NAME, deviceID, true);
            TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_DEVICE_MAC_ADDRESS, macAddress, true);
        }
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_PERSISTENT_ID_DISABLED, persistentIdsDisabled, true);
        if (packagedGoogleServicesVersion != 0) {
            TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_PACKAGED_GOOGLE_PLAY_SERVICES_VERSION, Integer.toString(packagedGoogleServicesVersion), true);
        }
        if (deviceGooglePlayServicesVersion != 0) {
            TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_DEVICE_GOOGLE_PLAY_SERVICES_VERSION, Integer.toString(deviceGooglePlayServicesVersion), true);
        }
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_USER_ID, userID, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_INSTALL_ID, installID, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_DEVICE_NAME, deviceModel, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_DEVICE_TYPE_NAME, deviceType, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_DEVICE_OS_VERSION_NAME, deviceOSVersion, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_DEVICE_COUNTRY_CODE, deviceCountryCode, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_DEVICE_LANGUAGE, deviceLanguage, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_APP_VERSION_NAME, appVersion, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_CONNECT_LIBRARY_VERSION_NAME, libraryVersion, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_CONNECT_BRIDGE_VERSION_NAME, bridgeVersion, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_CONNECT_LIBRARY_REVISION_NAME, TapjoyRevision.GIT_REVISION, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_PLATFORM, platformName, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_CURRENCY_MULTIPLIER, Float.toString(currencyMultiplier), true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_CARRIER_NAME, carrierName, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_CARRIER_COUNTRY_CODE, carrierCountryCode, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_MOBILE_COUNTRY_CODE, mobileCountryCode, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_MOBILE_NETWORK_CODE, mobileNetworkCode, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_DEVICE_MANUFACTURER, deviceManufacturer, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_DEVICE_SCREEN_DENSITY, BuildConfig.FLAVOR + deviceScreenDensity, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_DEVICE_SCREEN_LAYOUT_SIZE, BuildConfig.FLAVOR + deviceScreenLayoutSize, true);
        connectionType = getConnectionType();
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_CONNECTION_TYPE, connectionType, true);
        connectionSubType = getConnectionSubType();
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_CONNECTION_SUBTYPE, connectionSubType, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_PLUGIN, f4plugin, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_SDK_TYPE, sdkType, true);
        TapjoyUtil.safePut(hashMap, "store_name", storeName, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_DEVICE_LOCATION, String.valueOf(deviceLocation), true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_STORE_VIEW, String.valueOf(storeView), true);
        if (segmentationParams != null) {
            for (String next : segmentationParams.keySet()) {
                TapjoyUtil.safePut(hashMap, "segments[" + next + "]", segmentationParams.get(next).toString(), true);
            }
        }
        if (sessionID == null || sessionID.length() == 0 || System.currentTimeMillis() - lastTimeStamp > TapjoyConstants.SESSION_ID_INACTIVITY_TIME) {
            sessionID = generateSessionID();
        } else {
            lastTimeStamp = System.currentTimeMillis();
        }
        if (!(TapjoyCache.getInstance() == null || TapjoyCache.getInstance().getCachedOfferIDs() == null || TapjoyCache.getInstance().getCachedOfferIDs().length() <= 0)) {
            TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_CACHED_OFFERS, TapjoyCache.getInstance().getCachedOfferIDs(), true);
        }
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_SESSION_ID, sessionID, true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_OPTION_THREATMETRIX_SESSION_ID, threatmetrixSessionID, true);
        if (TapjoyLog.isLoggingEnabled()) {
            TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_EVENT_DEBUGGING, "true", true);
        }
        return hashMap;
    }

    public static String getRedirectDomain() {
        return redirectDomain;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public static Hashtable<String, Object> getSegmentationParams() {
        return segmentationParams;
    }

    public static String getSha1MacAddress() {
        try {
            return TapjoyUtil.SHA1(macAddress);
        } catch (Exception e) {
            TapjoyLog.e(TAG, "Error generating sha1 of macAddress: " + e.toString());
            return null;
        }
    }

    public static String getSha2DeviceID() {
        return sha2DeviceID;
    }

    public static Map<String, String> getTimeStampAndVerifierParams() {
        HashMap hashMap = new HashMap();
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        String verifier = getVerifier(currentTimeMillis);
        TapjoyUtil.safePut(hashMap, "timestamp", String.valueOf(currentTimeMillis), true);
        TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_VERIFIER, verifier, true);
        return hashMap;
    }

    public static Map<String, String> getURLParams() {
        Map<String, String> genericURLParams = getGenericURLParams();
        genericURLParams.putAll(getTimeStampAndVerifierParams());
        return genericURLParams;
    }

    public static String getUserID() {
        return userID;
    }

    private static String getVerifier(long j) {
        try {
            return TapjoyUtil.SHA256(appID + ":" + getVerifierID() + ":" + j + ":" + secretKey);
        } catch (Exception e) {
            TapjoyLog.e(TAG, "getVerifier ERROR: " + e.toString());
            return BuildConfig.FLAVOR;
        }
    }

    private static String getVerifierID() {
        if (arePersistentIdsDisabled()) {
            return advertisingID;
        }
        if (isDeviceIdPresent()) {
            return deviceID;
        }
        if (isMacAddressPresent()) {
            return macAddress;
        }
        if (isAdvertisingIdPresent()) {
            return advertisingID;
        }
        if (isAndroidIdPresent()) {
            return androidID;
        }
        Log.e(TAG, "Error -- no valid device identifier");
        return null;
    }

    /* access modifiers changed from: private */
    public static boolean handleConnectResponse(String str) {
        Document buildDocument = TapjoyUtil.buildDocument(str);
        if (buildDocument == null) {
            return true;
        }
        String nodeTrimValue = TapjoyUtil.getNodeTrimValue(buildDocument.getElementsByTagName("PackageNames"));
        if (nodeTrimValue != null && nodeTrimValue.length() > 0) {
            String str2 = nodeTrimValue;
            Vector vector = new Vector();
            int i = 0;
            while (true) {
                int indexOf = str2.indexOf(44, i);
                if (indexOf == -1) {
                    break;
                }
                TapjoyLog.i(TAG, "parse: " + str2.substring(i, indexOf).trim());
                vector.add(str2.substring(i, indexOf).trim());
                i = indexOf + 1;
            }
            TapjoyLog.i(TAG, "parse: " + str2.substring(i).trim());
            vector.add(str2.substring(i).trim());
            matchingPackageNames = BuildConfig.FLAVOR;
            for (ApplicationInfo next : packageManager.getInstalledApplications(0)) {
                if ((next.flags & 1) != 1 && vector.contains(next.packageName)) {
                    TapjoyLog.i(TAG, "MATCH: installed packageName: " + next.packageName);
                    if (matchingPackageNames.length() > 0) {
                        matchingPackageNames += ",";
                    }
                    matchingPackageNames += next.packageName;
                }
            }
        }
        String nodeTrimValue2 = TapjoyUtil.getNodeTrimValue(buildDocument.getElementsByTagName("Success"));
        return nodeTrimValue2 != null && nodeTrimValue2.equals("true");
    }

    /* access modifiers changed from: private */
    public boolean handlePayPerActionResponse(String str) {
        Document buildDocument = TapjoyUtil.buildDocument(str);
        if (buildDocument != null) {
            String nodeTrimValue = TapjoyUtil.getNodeTrimValue(buildDocument.getElementsByTagName("Success"));
            if (nodeTrimValue == null || !nodeTrimValue.equals("true")) {
                TapjoyLog.e(TAG, "Completed Pay-Per-Action call failed.");
            } else {
                TapjoyLog.i(TAG, "Successfully sent completed Pay-Per-Action to Tapjoy server.");
                return true;
            }
        }
        return false;
    }

    private boolean init() throws TapjoyException {
        loadConfigurations();
        if (Build.VERSION.SDK_INT > 8) {
            try {
                doProfileAsync();
            } catch (Exception e) {
                TapjoyLog.w(TAG, "Error building Threatmetrix profile: " + e.toString());
            }
        }
        if (getConnectFlagValue("unit_test_mode") == BuildConfig.FLAVOR) {
            integrationCheck();
        }
        obtainDeviceInformation();
        if (getConnectFlagValue(TapjoyConnectFlag.DEBUG_HOST_URL) != null && getConnectFlagValue(TapjoyConnectFlag.DEBUG_HOST_URL).length() > 0) {
            addConnectFlag(TapjoyConnectFlag.HOST_URL, getConnectFlagValue(TapjoyConnectFlag.DEBUG_HOST_URL));
        }
        if (getConnectFlagValue(TapjoyConnectFlag.DISABLE_PERSISTENT_IDS) != null && getConnectFlagValue(TapjoyConnectFlag.DISABLE_PERSISTENT_IDS).length() > 0) {
            persistentIdsDisabled = getConnectFlagValue(TapjoyConnectFlag.DISABLE_PERSISTENT_IDS);
        }
        if (getConnectFlagValue(TapjoyConnectFlag.DISABLE_ADVERTISING_ID_CHECK) != null && getConnectFlagValue(TapjoyConnectFlag.DISABLE_ADVERTISING_ID_CHECK).length() > 0) {
            adIdCheckDisabled = getConnectFlagValue(TapjoyConnectFlag.DISABLE_ADVERTISING_ID_CHECK);
        }
        if (getConnectFlagValue("user_id") != null && getConnectFlagValue("user_id").length() > 0) {
            TapjoyLog.i(TAG, "Setting userID to: " + getConnectFlagValue("user_id"));
            setUserID(getConnectFlagValue("user_id"));
        }
        if (connectFlags.get(TapjoyConnectFlag.SEGMENTATION_PARAMS) != null && (connectFlags.get(TapjoyConnectFlag.SEGMENTATION_PARAMS) instanceof Hashtable)) {
            setSegmentationParams((Hashtable) connectFlags.get(TapjoyConnectFlag.SEGMENTATION_PARAMS));
        }
        redirectDomain = TapjoyUtil.getRedirectDomain(getConnectFlagValue(TapjoyConnectFlag.HOST_URL));
        TapjoyLog.i(TAG, "deviceID: " + deviceID + ((getConnectFlagValue(TapjoyConnectFlag.DEBUG_DEVICE_ID) == null || getConnectFlagValue(TapjoyConnectFlag.DEBUG_DEVICE_ID).length() <= 0) ? BuildConfig.FLAVOR : " *debug_device_id*"));
        TapjoyLog.i(TAG, "sha2_udid: " + sha2DeviceID);
        if (connectFlags == null) {
            return true;
        }
        logConnectFlags();
        return true;
    }

    private void integrationCheck() throws TapjoyIntegrationException {
        try {
            List<ActivityInfo> asList = Arrays.asList(packageManager.getPackageInfo(context.getPackageName(), 1).activities);
            if (asList != null) {
                for (ActivityInfo checkForDependency : asList) {
                    checkForDependency(checkForDependency);
                }
            }
            if (dependencyClassesRequired.size() == 0) {
                checkPermissions();
                resolveJSBridgeMethods();
                if (getConnectFlagValue(TapjoyConnectFlag.DISABLE_ADVERTISING_ID_CHECK) == null || !getConnectFlagValue(TapjoyConnectFlag.DISABLE_ADVERTISING_ID_CHECK).equals("true")) {
                    this.gpsHelper.checkGooglePlayIntegration();
                } else {
                    TapjoyLog.i(TAG, "Skipping integration check for Google Play Services and Advertising ID. Do this only if you do not have access to Google Play Services.");
                }
            } else if (dependencyClassesRequired.size() == 1) {
                throw new TapjoyIntegrationException("Missing " + dependencyClassesRequired.size() + " dependency class in manifest: " + dependencyClassesRequired.toString());
            } else {
                throw new TapjoyIntegrationException("Missing " + dependencyClassesRequired.size() + " dependency classes in manifest: " + dependencyClassesRequired.toString());
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new TapjoyIntegrationException("NameNotFoundException: Could not find package.");
        }
    }

    private static boolean isAdvertisingIdPresent() {
        return advertisingID != null && advertisingID.length() > 0;
    }

    private static boolean isAndroidIdPresent() {
        return androidID != null && androidID.length() > 0;
    }

    private static boolean isDeviceIdPresent() {
        return deviceID != null && deviceID.length() > 0;
    }

    private static boolean isMacAddressPresent() {
        return macAddress != null && macAddress.length() > 0;
    }

    private boolean isPermissionGranted(String str) {
        return packageManager.checkPermission(str, context.getPackageName()) == 0;
    }

    public static boolean isVideoEnabled() {
        return getConnectFlagValue(TapjoyConnectFlag.DISABLE_VIDEOS) == null || !getConnectFlagValue(TapjoyConnectFlag.DISABLE_VIDEOS).equals("true");
    }

    public static boolean isViewOpen() {
        return isViewShowing;
    }

    private void loadConfigurations() {
        if (connectFlags == null) {
            connectFlags = new Hashtable<>();
        }
        if (getConnectFlagValue(TapjoyConnectFlag.ENABLE_LOGGING) != null && getConnectFlagValue(TapjoyConnectFlag.ENABLE_LOGGING).equals("true")) {
            TapjoyLog.enableLogging(true);
        }
        checkManifestForConfigurations();
        checkResourceFileForConfigurations();
    }

    private void logConnectFlags() {
        TapjoyLog.i(TAG, "Connect Flags:");
        TapjoyLog.i(TAG, "--------------------");
        for (Map.Entry next : connectFlags.entrySet()) {
            TapjoyLog.i(TAG, "key: " + ((String) next.getKey()) + ", value: " + Uri.encode(next.getValue().toString()));
            if (((String) next.getKey()).equals(TapjoyConnectFlag.SHA_2_UDID) && !sdkType.equals(TapjoyConstants.TJC_SDK_TYPE_CONNECT)) {
                TapjoyLog.w(TAG, "WARNING -- only the Connect/Advertiser SDK can support sha_2_udid");
                connectFlags.remove(TapjoyConnectFlag.SHA_2_UDID);
            }
        }
        TapjoyLog.i(TAG, "hostURL: [" + getConnectFlagValue(TapjoyConnectFlag.HOST_URL) + "]");
        TapjoyLog.i(TAG, "redirectDomain: [" + redirectDomain + "]");
        TapjoyLog.i(TAG, "--------------------");
    }

    private void obtainCarrierInformation() {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager != null) {
            carrierName = telephonyManager.getNetworkOperatorName();
            carrierCountryCode = telephonyManager.getNetworkCountryIso();
            if (telephonyManager.getNetworkOperator() != null && (telephonyManager.getNetworkOperator().length() == 5 || telephonyManager.getNetworkOperator().length() == 6)) {
                mobileCountryCode = telephonyManager.getNetworkOperator().substring(0, 3);
                mobileNetworkCode = telephonyManager.getNetworkOperator().substring(3);
            }
            if (isPermissionGranted("android.permission.READ_PHONE_STATE")) {
                try {
                    if (getConnectFlagValue(TapjoyConnectFlag.DEBUG_DEVICE_ID) == null || getConnectFlagValue(TapjoyConnectFlag.DEBUG_DEVICE_ID).length() <= 0) {
                        deviceID = telephonyManager.getDeviceId();
                    } else {
                        deviceID = getConnectFlagValue(TapjoyConnectFlag.DEBUG_DEVICE_ID);
                    }
                    TapjoyLog.i(TAG, "deviceID: " + deviceID);
                    boolean z = false;
                    if (deviceID == null) {
                        TapjoyLog.e(TAG, "Device id is null.");
                    } else if (deviceID.length() == 0 || deviceID.equals("000000000000000") || deviceID.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                        TapjoyLog.e(TAG, "Device id is empty or an emulator.");
                    } else {
                        deviceID = deviceID.toLowerCase(Locale.getDefault());
                        z = true;
                    }
                    TapjoyLog.i(TAG, "ANDROID SDK VERSION: " + Build.VERSION.SDK_INT);
                    if (Build.VERSION.SDK_INT >= 9) {
                        TapjoyLog.i(TAG, "TRYING TO GET SERIAL OF 2.3+ DEVICE...");
                        String serial = getSerial();
                        if (!z) {
                            deviceID = serial;
                        }
                        if (deviceID == null) {
                            TapjoyLog.e(TAG, "SERIAL: Device id is null.");
                        } else if (deviceID.length() == 0 || deviceID.equals("000000000000000") || deviceID.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO) || deviceID.equals("unknown")) {
                            TapjoyLog.e(TAG, "SERIAL: Device id is empty or an emulator.");
                        } else {
                            deviceID = deviceID.toLowerCase(Locale.getDefault());
                            z = true;
                        }
                    }
                    if (z) {
                        sha2DeviceID = TapjoyUtil.SHA256(deviceID);
                    }
                } catch (Exception e) {
                    TapjoyLog.e(TAG, "Cannot get deviceID. e: " + e.toString());
                    deviceID = null;
                }
            } else {
                TapjoyLog.i(TAG, "*** ignore deviceID");
            }
        }
    }

    private void obtainDeviceInformation() throws TapjoyException {
        androidID = Settings.Secure.getString(context.getContentResolver(), TapjoyConstants.TJC_ANDROID_ID);
        if (androidID != null) {
            androidID = androidID.toLowerCase();
        }
        try {
            appVersion = packageManager.getPackageInfo(context.getPackageName(), 0).versionName;
            deviceType = TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE;
            platformName = TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE;
            deviceModel = Build.MODEL;
            deviceManufacturer = Build.MANUFACTURER;
            deviceOSVersion = Build.VERSION.RELEASE;
            deviceCountryCode = Locale.getDefault().getCountry();
            deviceLanguage = Locale.getDefault().getLanguage();
            libraryVersion = TapjoyConstants.TJC_LIBRARY_VERSION_NUMBER;
            bridgeVersion = TapjoyConstants.TJC_BRIDGE_VERSION_NUMBER;
            obtainScreenInformation();
            obtainMacAddress();
            obtainCarrierInformation();
            determineInstallID();
            setDeviceCapabilityFlags();
        } catch (PackageManager.NameNotFoundException e) {
            throw new TapjoyException(e.getMessage());
        }
    }

    private void obtainMacAddress() {
        WifiInfo connectionInfo;
        if (isPermissionGranted("android.permission.ACCESS_WIFI_STATE")) {
            try {
                WifiManager wifiManager = (WifiManager) context.getSystemService(TapjoyConstants.TJC_CONNECTION_TYPE_WIFI);
                if (wifiManager != null && (connectionInfo = wifiManager.getConnectionInfo()) != null) {
                    macAddress = connectionInfo.getMacAddress();
                    if (macAddress != null) {
                        macAddress = macAddress.replace(":", BuildConfig.FLAVOR).toLowerCase();
                    }
                }
            } catch (Exception e) {
                TapjoyLog.e(TAG, "Error getting device mac address: " + e.toString());
            }
        } else {
            TapjoyLog.i(TAG, "*** ignore macAddress");
        }
    }

    private void obtainScreenInformation() {
        try {
            if (Build.VERSION.SDK_INT > 3) {
                TapjoyDisplayMetricsUtil tapjoyDisplayMetricsUtil = new TapjoyDisplayMetricsUtil(context);
                deviceScreenDensity = tapjoyDisplayMetricsUtil.getScreenDensityDPI();
                deviceScreenDensityScale = tapjoyDisplayMetricsUtil.getScreenDensityScale();
                deviceScreenLayoutSize = tapjoyDisplayMetricsUtil.getScreenLayoutSize();
            }
        } catch (Exception e) {
            TapjoyLog.e(TAG, "Error getting screen density/dimensions/layout: " + e.toString());
        }
    }

    private void parsePropertiesIntoConfigFlags(Properties properties) {
        Enumeration keys = properties.keys();
        while (keys.hasMoreElements()) {
            try {
                String str = (String) keys.nextElement();
                addConnectFlag(str, (String) properties.get(str));
            } catch (ClassCastException e) {
                TapjoyLog.e(TAG, "Error parsing configuration properties in tapjoy_config.txt");
            }
        }
    }

    public static void removeOfflineLog(String str) {
        SharedPreferences.Editor edit = context.getSharedPreferences(TapjoyConstants.PREF_OFFLINE_LOG, 0).edit();
        edit.remove(str);
        edit.commit();
    }

    public static void requestTapjoyConnect(Context context2, String str, String str2) throws TapjoyException {
        requestTapjoyConnect(context2, str, str2, (Hashtable<String, ?>) null);
    }

    public static void requestTapjoyConnect(Context context2, String str, String str2, Hashtable<String, ?> hashtable) throws TapjoyException {
        requestTapjoyConnect(context2, str, str2, hashtable, (TapjoyConnectNotifier) null);
    }

    public static void requestTapjoyConnect(Context context2, String str, String str2, Hashtable<String, ?> hashtable, TapjoyConnectNotifier tapjoyConnectNotifier) throws TapjoyException {
        appID = str;
        secretKey = str2;
        if (hashtable != null) {
            connectFlags.putAll(hashtable);
        }
        connectNotifier = tapjoyConnectNotifier;
        tapjoyConnectCore = new TapjoyConnectCore(context2);
    }

    private void resolveJSBridgeMethods() throws TapjoyIntegrationException {
        try {
            try {
                Class.forName("com.tapjoy.TJAdUnitJSBridge").getMethod(TJAdUnitConstants.String.CLOSE_REQUESTED, new Class[0]);
                String str = (String) TapjoyUtil.getResource("mraid.js");
                if (str == null) {
                    str = TapjoyUtil.copyTextFromJarIntoString("js/mraid.js", context);
                }
                if (str == null) {
                    throw new TapjoyIntegrationException("ClassNotFoundException: mraid.js was not found.");
                }
            } catch (NoSuchMethodException e) {
                throw new TapjoyIntegrationException("Try configuring Proguard or other code obfuscators to ignore com.tapjoy classes. Visit http://tech.tapjoy.comfor more information.");
            }
        } catch (ClassNotFoundException e2) {
            throw new TapjoyIntegrationException("ClassNotFoundException: com.tapjoy.TJAdUnitJSBridge was not found.");
        }
    }

    public static void saveOfflineLog(String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TapjoyConstants.PREF_OFFLINE_LOG, 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (getOfflineLogs().size() >= 50) {
            edit.remove((String) new TreeMap(sharedPreferences.getAll()).firstKey());
            edit.commit();
        }
        edit.putString(Long.toString(System.currentTimeMillis()), (str + "&original_timestamp=" + (System.currentTimeMillis() / 1000)) + "&offline=true");
        edit.commit();
    }

    public static void sendOfflineLogs() {
        new Thread(new Runnable() {
            public void run() {
                TapjoyURLConnection tapjoyURLConnection = new TapjoyURLConnection();
                for (Map.Entry next : TapjoyConnectCore.getOfflineLogs().entrySet()) {
                    try {
                        TapjoyLog.i(TapjoyConnectCore.TAG, "sending offline log: " + next.getValue());
                        tapjoyURLConnection.getResponseFromURL(((String) next.getValue()) + "&" + TapjoyUtil.convertURLParams(TapjoyConnectCore.getTimeStampAndVerifierParams(), false), BuildConfig.FLAVOR);
                    } catch (Exception e) {
                        TapjoyLog.i(TapjoyConnectCore.TAG, "error sending offline log");
                    }
                    TapjoyConnectCore.removeOfflineLog((String) next.getKey());
                }
            }
        }).start();
    }

    private void setDeviceCapabilityFlags() {
        try {
            deviceLocation = detectCapability("android.hardware.location", "android.permission.ACCESS_FINE_LOCATION");
        } catch (Exception e) {
            TapjoyLog.e(TAG, "Error trying to detect capabilities on devicee: " + e.toString());
        }
        try {
            shareFacebook = detectSharingApplication("com.facebook.");
            shareTwitter = detectSharingApplication("com.twitter.");
            shareGooglePlus = detectSharingApplication("com.google.android.apps.plus");
            shareLinkedIn = detectSharingApplication("com.linkedin.");
        } catch (Exception e2) {
            TapjoyLog.e(TAG, "Error trying to detect sharing applications installed on devicee: " + e2.toString());
        }
        if (getConnectFlagValue("store_name") != null && getConnectFlagValue("store_name").length() > 0) {
            storeName = getConnectFlagValue("store_name");
            if (!new ArrayList(Arrays.asList(TapjoyConnectFlag.STORE_ARRAY)).contains(storeName)) {
                TapjoyLog.w(TAG, "Warning -- undefined STORE_NAME: " + storeName);
            }
        }
        try {
            storeView = detectStore(storeName);
        } catch (Exception e3) {
            TapjoyLog.e(TAG, "Error trying to detect store intent on devicee: " + e3.toString());
        }
    }

    public static void setPlugin(String str) {
        f4plugin = str;
    }

    public static void setSDKType(String str) {
        sdkType = str;
    }

    public static void setSegmentationParams(Hashtable<String, Object> hashtable) {
        segmentationParams = hashtable;
    }

    public static void setUserID(String str) {
        userID = str;
        TapjoyLog.i(TAG, "URL parameters: " + getURLParams());
        new Thread(new Runnable() {
            public void run() {
                TapjoyLog.i(TapjoyConnectCore.TAG, "setUserID...");
                TapjoyHttpURLResponse responseFromURL = TapjoyConnectCore.tapjoyURLConnection.getResponseFromURL(TapjoyConnectCore.getHostURL() + TapjoyConstants.TJC_USER_ID_URL_PATH, TapjoyConnectCore.getURLParams());
                if (responseFromURL.response != null) {
                    if (TapjoyConnectCore.handleConnectResponse(responseFromURL.response)) {
                    }
                    TapjoyLog.i(TapjoyConnectCore.TAG, "setUserID successful...");
                }
            }
        }).start();
    }

    public static void setViewShowing(boolean z) {
        isViewShowing = z;
    }

    public static void viewDidClose(int i) {
        isViewShowing = false;
        if (viewNotifier != null) {
            viewNotifier.viewDidClose(i);
        }
    }

    public static void viewDidOpen(int i) {
        if (viewNotifier != null) {
            viewNotifier.viewDidOpen(i);
        }
    }

    public static void viewWillClose(int i) {
        if (viewNotifier != null) {
            viewNotifier.viewWillClose(i);
        }
    }

    public static void viewWillOpen(int i) {
        isViewShowing = true;
        if (viewNotifier != null) {
            viewNotifier.viewWillOpen(i);
        }
    }

    public void actionComplete(String str) {
        TapjoyLog.i(TAG, "actionComplete: " + str);
        Map<String, String> paramsWithoutAppID = getParamsWithoutAppID();
        TapjoyUtil.safePut(paramsWithoutAppID, "app_id", str, true);
        paramsWithoutAppID.putAll(getTimeStampAndVerifierParams());
        TapjoyLog.i(TAG, "PPA URL parameters: " + paramsWithoutAppID);
        new Thread(new PPAThread(paramsWithoutAppID)).start();
    }

    public void appPause() {
        this.appPaused = true;
    }

    public void appResume() {
        if (this.appPaused) {
            generateSessionID();
            this.appPaused = false;
        }
    }

    public void callConnect() {
        fetchAdvertisingID();
    }

    public void completeConnectCall() {
        TapjoyLog.i(TAG, "starting connect call...");
        String str = TapjoyConfig.TJC_CONNECT_SERVICE_URL;
        if (getHostURL() != TapjoyConfig.TJC_SERVICE_URL) {
            str = getHostURL();
        }
        TapjoyHttpURLResponse responseFromURL = tapjoyURLConnection.getResponseFromURL(str + TapjoyConstants.TJC_CONNECT_URL_PATH, getURLParams());
        if (responseFromURL != null && responseFromURL.statusCode == 200) {
            if (handleConnectResponse(responseFromURL.response)) {
                TapjoyLog.i(TAG, "Successfully connected to Tapjoy");
                for (Map.Entry next : getGenericURLParams().entrySet()) {
                    TapjoyLog.i(TAG, ((String) next.getKey()) + ": " + ((String) next.getValue()));
                }
                sendOfflineLogs();
                if (connectNotifier != null) {
                    connectNotifier.connectSuccess();
                }
            } else if (connectNotifier != null) {
                connectNotifier.connectFail();
            }
            if (matchingPackageNames.length() > 0) {
                Map<String, String> genericURLParams = getGenericURLParams();
                TapjoyUtil.safePut(genericURLParams, TapjoyConstants.TJC_PACKAGE_NAMES, matchingPackageNames, true);
                long currentTimeMillis = System.currentTimeMillis() / 1000;
                String packageNamesVerifier = getPackageNamesVerifier(currentTimeMillis, matchingPackageNames);
                TapjoyUtil.safePut(genericURLParams, "timestamp", String.valueOf(currentTimeMillis), true);
                TapjoyUtil.safePut(genericURLParams, TapjoyConstants.TJC_VERIFIER, packageNamesVerifier, true);
                TapjoyHttpURLResponse responseFromURL2 = new TapjoyURLConnection().getResponseFromURL(getHostURL() + TapjoyConstants.TJC_SDK_LESS_CONNECT, genericURLParams);
                if (responseFromURL2 != null && responseFromURL2.statusCode == 200) {
                    TapjoyLog.i(TAG, "Successfully pinged sdkless api.");
                }
            }
        } else if (connectNotifier != null) {
            connectNotifier.connectFail();
        }
    }

    /* access modifiers changed from: protected */
    public boolean detectApplication(String str) {
        for (ApplicationInfo applicationInfo : packageManager.getInstalledApplications(0)) {
            if (applicationInfo.packageName.startsWith(str)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean detectCapability(String str, String str2) {
        for (FeatureInfo featureInfo : packageManager.getSystemAvailableFeatures()) {
            if (featureInfo.name.matches(str)) {
                return str2 == null || packageManager.checkPermission(str2, context.getPackageName()) == 0;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean detectSharingApplication(String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(intent, 0)) {
            if (resolveInfo.activityInfo.packageName.startsWith(str)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean detectStore(String str) {
        boolean z = false;
        Intent intent = new Intent("android.intent.action.VIEW");
        if (str.length() < 1) {
            intent.setData(Uri.parse("market://details"));
            if (packageManager.queryIntentActivities(intent, 0).size() > 0) {
                z = true;
            }
        } else if (str.equals(TapjoyConnectFlag.STORE_GFAN)) {
            z = detectApplication("com.mappn.gfan");
        } else if (str.equals(TapjoyConnectFlag.STORE_SKT)) {
            z = detectApplication("com.skt.skaf.TSCINSTALL");
        }
        if (z) {
        }
        return z;
    }

    public void doProfileAsync() {
        TapjoyLog.i(TAG, "Initializing Threatmetrix: 2.5-16");
        this.profile = new TrustDefenderMobile();
        try {
            this.profile.setCompletionNotifier(new ProfileNotify() {
                public void complete() {
                    try {
                        if (TapjoyConnectCore.this.profile.getStatus() == TrustDefenderMobile.THMStatusCode.THM_OK) {
                            String unused = TapjoyConnectCore.threatmetrixSessionID = TapjoyConnectCore.this.profile.getSessionID();
                        } else {
                            TapjoyLog.w(TapjoyConnectCore.TAG, "No Threatmetrix session ID");
                        }
                    } catch (Exception e) {
                        TapjoyLog.w(TapjoyConnectCore.TAG, "Error retrieving Threatmetrix session ID: " + e.toString());
                    } finally {
                        TapjoyConnectCore.this.profile.tidyUp();
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.profile.setTimeout(10);
        this.profile.doProfileRequest(context, TapjoyConstants.TJC_THREATMETRIX_ID, TapjoyConstants.TJC_THREATMETRIX_SERVER_URL, TapjoyConstants.TJC_THREATMETRIX_TAPJOY_URL, (int) TapjoyConstants.THREATMETRIX_CONNECT_OPTIONS);
    }

    public void enablePaidAppWithActionID(String str) {
        TapjoyLog.i(TAG, "enablePaidAppWithActionID: " + str);
        paidAppActionID = str;
        this.elapsed_time = context.getSharedPreferences(TapjoyConstants.TJC_PREFERENCE, 0).getLong(TapjoyConstants.PREF_ELAPSED_TIME, 0);
        TapjoyLog.i(TAG, "paidApp elapsed: " + this.elapsed_time);
        if (this.elapsed_time >= TapjoyConstants.PAID_APP_TIME) {
            if (paidAppActionID != null && paidAppActionID.length() > 0) {
                TapjoyLog.i(TAG, "Calling PPA actionComplete...");
                actionComplete(paidAppActionID);
            }
        } else if (this.timer == null) {
            this.timer = new Timer();
            this.timer.schedule(new PaidAppTimerTask(), 10000, 10000);
        }
    }

    public void fetchAdvertisingID() {
        new Thread(new Runnable() {
            public void run() {
                TapjoyConnectCore.this.gpsHelper.loadAdvertisingId();
                if (TapjoyConnectCore.this.gpsHelper.isGooglePlayServicesAvailable() && TapjoyConnectCore.this.gpsHelper.isGooglePlayManifestConfigured()) {
                    TapjoyConnectCore.deviceGooglePlayServicesVersion = TapjoyConnectCore.this.gpsHelper.getDeviceGooglePlayServicesVersion();
                    TapjoyConnectCore.packagedGoogleServicesVersion = TapjoyConnectCore.this.gpsHelper.getPackagedGooglePlayServicesVersion();
                }
                if (TapjoyConnectCore.this.gpsHelper.isAdIdAvailable()) {
                    TapjoyConnectCore.adTrackingEnabled = TapjoyConnectCore.this.gpsHelper.isAdTrackingEnabled();
                    TapjoyConnectCore.advertisingID = TapjoyConnectCore.this.gpsHelper.getAdvertisingId();
                }
                if (TapjoyConnectCore.arePersistentIdsDisabled()) {
                    TapjoyLog.i(TapjoyConnectCore.TAG, "Disabling persistent IDs. Do this only if you are not using Tapjoy to manage currency.");
                }
                TapjoyConnectCore.this.completeConnectCall();
            }
        }).start();
    }

    public float getCurrencyMultiplier() {
        return currencyMultiplier;
    }

    public String getSerial() {
        try {
            Field declaredField = Class.forName("android.os.Build").getDeclaredField("SERIAL");
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }
            String obj = declaredField.get(Build.class).toString();
            TapjoyLog.i(TAG, "serial: " + obj);
            return obj;
        } catch (Exception e) {
            TapjoyLog.e(TAG, e.toString());
            return null;
        }
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void release() {
        tapjoyConnectCore = null;
        tapjoyURLConnection = null;
        TapjoyLog.i(TAG, "Releasing core static instance.");
    }

    public void setCurrencyMultiplier(float f) {
        TapjoyLog.i(TAG, "setVirtualCurrencyMultiplier: " + f);
        currencyMultiplier = f;
    }

    public void setTapjoyViewNotifier(TapjoyViewNotifier tapjoyViewNotifier) {
        viewNotifier = tapjoyViewNotifier;
    }

    public void setVideoEnabled(boolean z) {
        addConnectFlag(TapjoyConnectFlag.DISABLE_VIDEOS, String.valueOf(z));
    }
}
