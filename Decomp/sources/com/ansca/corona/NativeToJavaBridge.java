package com.ansca.corona;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.ansca.corona.AudioRecorder;
import com.ansca.corona.events.NotificationReceivedTask;
import com.ansca.corona.events.NotificationRegistrationTask;
import com.ansca.corona.graphics.FontServices;
import com.ansca.corona.graphics.HorizontalAlignment;
import com.ansca.corona.graphics.TextRenderer;
import com.ansca.corona.input.InputDeviceInterface;
import com.ansca.corona.input.InputDeviceServices;
import com.ansca.corona.listeners.CoronaSplashScreenApiListener;
import com.ansca.corona.listeners.CoronaStatusBarApiListener;
import com.ansca.corona.listeners.CoronaStoreApiListener;
import com.ansca.corona.listeners.CoronaSystemApiListener;
import com.ansca.corona.maps.MapMarker;
import com.ansca.corona.maps.MapRequestLocationFailedTask;
import com.ansca.corona.maps.MapRequestLocationTask;
import com.ansca.corona.maps.MapType;
import com.ansca.corona.notifications.GoogleCloudMessagingServices;
import com.ansca.corona.notifications.NotificationServices;
import com.ansca.corona.notifications.NotificationSettings;
import com.ansca.corona.notifications.ScheduledNotificationSettings;
import com.ansca.corona.purchasing.StoreServices;
import com.ansca.corona.storage.AssetFileLocationInfo;
import com.ansca.corona.storage.FileServices;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaError;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import jp.stargarage.g2metrics.BuildConfig;

public class NativeToJavaBridge {
    private static DexClassLoader sClassLoader = null;
    private static HashMap<String, JavaFunction> sPluginCache = new HashMap<>();
    private Context myContext;

    private static class ApiLevel21 {
        private ApiLevel21() {
        }

        public static String getScriptFrom(Locale locale) {
            if (locale == null) {
                return null;
            }
            return locale.getScript();
        }
    }

    private static class LoadBitmapResult {
        private Bitmap fBitmap;
        private int fHeight;
        private int fRotationInDegrees;
        private float fScaleFactor;
        private int fWidth;

        public LoadBitmapResult(int i, int i2, float f, int i3) {
            this.fBitmap = null;
            this.fWidth = i;
            this.fHeight = i2;
            this.fScaleFactor = f;
            this.fRotationInDegrees = i3;
        }

        public LoadBitmapResult(Bitmap bitmap, float f, int i) {
            this.fBitmap = bitmap;
            this.fWidth = 0;
            this.fHeight = 0;
            this.fScaleFactor = f;
            this.fRotationInDegrees = i;
        }

        public Bitmap getBitmap() {
            return this.fBitmap;
        }

        public int getHeight() {
            return this.fBitmap != null ? this.fBitmap.getHeight() : this.fHeight;
        }

        public int getRotationInDegrees() {
            return this.fRotationInDegrees;
        }

        public float getScaleFactor() {
            return this.fScaleFactor;
        }

        public int getWidth() {
            return this.fBitmap != null ? this.fBitmap.getWidth() : this.fWidth;
        }

        public boolean wasSuccessful() {
            return this.fBitmap != null;
        }
    }

    NativeToJavaBridge(Context context) {
        this.myContext = context;
    }

    protected static boolean callCanShowPopup(CoronaRuntime coronaRuntime, String str) {
        return coronaRuntime.getController().canShowPopup(str);
    }

    protected static void callCancelNativeAlert(int i, CoronaRuntime coronaRuntime) {
        coronaRuntime.getController().cancelNativeAlert(i);
    }

    protected static void callCancelTimer(CoronaRuntime coronaRuntime) {
        coronaRuntime.getController().cancelTimer();
    }

    protected static void callCloseNativeActivityIndicator(CoronaRuntime coronaRuntime) {
        coronaRuntime.getController().closeNativeActivityIndicator();
    }

    protected static byte[] callCryptoCalculateDigest(String str, byte[] bArr) {
        return Crypto.CalculateDigest(str, bArr);
    }

    protected static byte[] callCryptoCalculateHMAC(String str, byte[] bArr, byte[] bArr2) {
        return Crypto.CalculateHMAC(str, bArr, bArr2);
    }

    protected static int callCryptoGetDigestLength(String str) {
        return Crypto.GetDigestLength(str);
    }

    protected static void callDisplayObjectDestroy(CoronaRuntime coronaRuntime, int i) {
        coronaRuntime.getViewManager().destroyDisplayObject(i);
    }

    protected static float callDisplayObjectGetAlpha(CoronaRuntime coronaRuntime, int i) {
        return coronaRuntime.getViewManager().getDisplayObjectAlpha(i);
    }

    protected static boolean callDisplayObjectGetBackground(CoronaRuntime coronaRuntime, int i) {
        return coronaRuntime.getViewManager().getDisplayObjectBackground(i);
    }

    protected static boolean callDisplayObjectGetVisible(CoronaRuntime coronaRuntime, int i) {
        return coronaRuntime.getViewManager().getDisplayObjectVisible(i);
    }

    protected static void callDisplayObjectSetAlpha(CoronaRuntime coronaRuntime, int i, float f) {
        coronaRuntime.getViewManager().setDisplayObjectAlpha(i, f);
    }

    protected static void callDisplayObjectSetBackground(CoronaRuntime coronaRuntime, int i, boolean z) {
        coronaRuntime.getViewManager().setDisplayObjectBackground(i, z);
    }

    protected static void callDisplayObjectSetFocus(CoronaRuntime coronaRuntime, int i, boolean z) {
        coronaRuntime.getViewManager().setTextViewFocus(i, z);
    }

    protected static void callDisplayObjectSetVisible(CoronaRuntime coronaRuntime, int i, boolean z) {
        coronaRuntime.getViewManager().setDisplayObjectVisible(i, z);
    }

    protected static void callDisplayObjectUpdateScreenBounds(CoronaRuntime coronaRuntime, int i, int i2, int i3, int i4, int i5) {
        coronaRuntime.getViewManager().displayObjectUpdateScreenBounds(i, i2, i3, i4, i5);
    }

    protected static void callDisplayUpdate(CoronaRuntime coronaRuntime) {
        coronaRuntime.getController().displayUpdate();
    }

    protected static String callExternalizeResource(String str, CoronaRuntime coronaRuntime) {
        File extractAssetFile;
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext == null || (extractAssetFile = new FileServices(applicationContext).extractAssetFile(coronaRuntime.getPath() + str)) == null) {
            return null;
        }
        return extractAssetFile.getAbsolutePath();
    }

    protected static void callFetchAllInputDevices(CoronaRuntime coronaRuntime) {
        Iterator<InputDeviceInterface> it = new InputDeviceServices(CoronaEnvironment.getApplicationContext()).fetchAll().iterator();
        while (it.hasNext()) {
            JavaToNativeShim.update(coronaRuntime, it.next());
        }
    }

    protected static void callFetchInputDevice(int i, CoronaRuntime coronaRuntime) {
        InputDeviceInterface fetchByCoronaDeviceId = new InputDeviceServices(CoronaEnvironment.getApplicationContext()).fetchByCoronaDeviceId(i);
        if (fetchByCoronaDeviceId != null) {
            JavaToNativeShim.update(coronaRuntime, fetchByCoronaDeviceId);
        }
    }

    protected static void callFlurryEvent(String str) {
    }

    protected static void callFlurryInit(String str) {
    }

    protected static int callGetApproximateScreenDpi() {
        DisplayMetrics displayMetrics = getDisplayMetrics();
        if (displayMetrics != null) {
            return displayMetrics.densityDpi;
        }
        return -1;
    }

    protected static boolean callGetAssetFileLocation(String str, long j) {
        AssetFileLocationInfo assetFileLocation;
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext == null || (assetFileLocation = new FileServices(applicationContext).getAssetFileLocation(str)) == null) {
            return false;
        }
        JavaToNativeShim.setZipFileEntryInfo(j, assetFileLocation);
        return true;
    }

    protected static String[] callGetAvailableStoreNames() {
        return StoreServices.getAvailableInAppStoreNames();
    }

    protected static byte[] callGetBytesFromFile(String str) {
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext == null) {
            return null;
        }
        return new FileServices(applicationContext).getBytesFromFile(str);
    }

    protected static boolean callGetCoronaResourceFileExists(CoronaRuntime coronaRuntime, String str) {
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext != null) {
            return new File(applicationContext.getFileStreamPath("coronaResources"), coronaRuntime.getPath() + str).exists();
        }
        return false;
    }

    protected static float callGetDefaultFontSize(CoronaRuntime coronaRuntime) {
        return coronaRuntime.getController().getDefaultFontSize();
    }

    protected static int callGetDefaultTextFieldPaddingInPixels(CoronaRuntime coronaRuntime) {
        return coronaRuntime.getController().getDefaultTextFieldPaddingInPixels();
    }

    protected static String callGetExceptionStackTraceFrom(Throwable th) {
        if (th == null) {
            return null;
        }
        return new LuaError((String) null, th).toString();
    }

    protected static String[] callGetFonts() {
        return new FontServices(CoronaEnvironment.getApplicationContext()).fetchAllSystemFontNames();
    }

    protected static boolean callGetIdleTimer(CoronaRuntime coronaRuntime) {
        return coronaRuntime.getController().getIdleTimer();
    }

    protected static String callGetManufacturerName(CoronaRuntime coronaRuntime) {
        return coronaRuntime.getController().getManufacturerName();
    }

    protected static String callGetModel(CoronaRuntime coronaRuntime) {
        return coronaRuntime.getController().getModel();
    }

    protected static String callGetName(CoronaRuntime coronaRuntime) {
        return coronaRuntime.getController().getName();
    }

    protected static String callGetPlatformVersion(CoronaRuntime coronaRuntime) {
        return coronaRuntime.getController().getPlatformVersion();
    }

    protected static String callGetPreference(int i, CoronaRuntime coronaRuntime) {
        switch (i) {
            case 0:
                return Locale.getDefault().toString();
            case 1:
                return Locale.getDefault().getLanguage();
            case 2:
                return Locale.getDefault().getCountry();
            case 3:
                return Locale.getDefault().getDisplayLanguage();
            default:
                System.err.println("getPreference: Unknown category " + Integer.toString(i));
                return BuildConfig.FLAVOR;
        }
    }

    protected static String callGetProductName(CoronaRuntime coronaRuntime) {
        return coronaRuntime.getController().getProductName();
    }

    protected static boolean callGetRawAssetExists(CoronaRuntime coronaRuntime, String str) {
        return coronaRuntime.getController().getBridge().getRawAssetExists(coronaRuntime.getPath() + str);
    }

    protected static int callGetStatusBarHeight(CoronaRuntime coronaRuntime) {
        int i = 0;
        synchronized (coronaRuntime.getController()) {
            CoronaStatusBarApiListener coronaStatusBarApiListener = coronaRuntime.getController().getCoronaStatusBarApiListener();
            if (coronaStatusBarApiListener != null) {
                i = coronaStatusBarApiListener.getStatusBarHeight();
            }
        }
        return i;
    }

    protected static int callGetStatusBarMode(CoronaRuntime coronaRuntime) {
        CoronaStatusBarSettings coronaStatusBarSettings = CoronaStatusBarSettings.CORONA_STATUSBAR_MODE_HIDDEN;
        synchronized (coronaRuntime.getController()) {
            CoronaStatusBarApiListener coronaStatusBarApiListener = coronaRuntime.getController().getCoronaStatusBarApiListener();
            if (coronaStatusBarApiListener != null) {
                coronaStatusBarSettings = coronaStatusBarApiListener.getStatusBarMode();
            }
        }
        return coronaStatusBarSettings.toCoronaIntId();
    }

    protected static String callGetSystemUiVisibility(CoronaRuntime coronaRuntime) {
        String str = null;
        if (coronaRuntime != null) {
            str = coronaRuntime.getController().getSystemUiVisibility();
        }
        return str == null ? BuildConfig.FLAVOR : str;
    }

    protected static String callGetTargetedStoreName(CoronaRuntime coronaRuntime) {
        return StoreServices.getTargetedAppStoreName();
    }

    protected static String callGetUniqueIdentifier(int i, CoronaRuntime coronaRuntime) {
        return coronaRuntime.getController().getUniqueIdentifier(i);
    }

    protected static float callGetVolume(CoronaRuntime coronaRuntime, int i) {
        return coronaRuntime.getController().getMediaManager().getVolume(i);
    }

    protected static void callGooglePushNotificationsRegister(CoronaRuntime coronaRuntime, String str) {
        if (str != null && str.length() > 0) {
            GoogleCloudMessagingServices googleCloudMessagingServices = new GoogleCloudMessagingServices(CoronaEnvironment.getApplicationContext());
            String registrationId = googleCloudMessagingServices.getRegistrationId();
            String commaSeparatedRegisteredProjectNumbers = googleCloudMessagingServices.getCommaSeparatedRegisteredProjectNumbers();
            if (registrationId.length() <= 0 || !commaSeparatedRegisteredProjectNumbers.equals(str)) {
                googleCloudMessagingServices.register(str);
            } else {
                coronaRuntime.getTaskDispatcher().send(new NotificationRegistrationTask(registrationId));
            }
        }
    }

    protected static void callGooglePushNotificationsUnregister(CoronaRuntime coronaRuntime) {
        new GoogleCloudMessagingServices(CoronaEnvironment.getApplicationContext()).unregister();
    }

    protected static boolean callHasAccelerometer(CoronaRuntime coronaRuntime) {
        return coronaRuntime.getController().hasAccelerometer();
    }

    protected static boolean callHasGyroscope(CoronaRuntime coronaRuntime) {
        return coronaRuntime.getController().hasGyroscope();
    }

    protected static boolean callHasMediaSource(CoronaRuntime coronaRuntime, int i) {
        return coronaRuntime.getController().hasMediaSource(i);
    }

    protected static void callHttpPost(CoronaRuntime coronaRuntime, String str, String str2, String str3) {
        coronaRuntime.getController().httpPost(str, str2, str3);
    }

    protected static int callInvokeLuaErrorHandler(long j) {
        return CoronaEnvironment.invokeLuaErrorHandler(j);
    }

    private static boolean callLoadBitmap(CoronaRuntime coronaRuntime, String str, long j, boolean z, int i, int i2, boolean z2) {
        Bitmap bitmap;
        if (str == null || str.length() <= 0 || j == 0) {
            return false;
        }
        if (!new File(str).exists()) {
            str = coronaRuntime.getPath() + str;
        }
        Uri parse = Uri.parse(Uri.encode(str, ":/\\."));
        String scheme = parse.getScheme();
        if (scheme == null) {
            scheme = BuildConfig.FLAVOR;
        }
        LoadBitmapResult loadBitmapResult = null;
        boolean z3 = false;
        if (scheme.toLowerCase().equals("android.app.icon")) {
            try {
                Context applicationContext = CoronaEnvironment.getApplicationContext();
                ApplicationInfo applicationInfo = applicationContext.getApplicationInfo();
                PackageManager packageManager = applicationContext.getPackageManager();
                String host = parse.getHost();
                if (host == null || host.length() <= 0) {
                    host = applicationInfo.packageName;
                }
                Drawable applicationIcon = packageManager.getApplicationIcon(host);
                if (applicationIcon instanceof BitmapDrawable) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) applicationIcon;
                    if (bitmapDrawable.getBitmap() != null) {
                        loadBitmapResult = z2 ? new LoadBitmapResult(bitmapDrawable.getBitmap().getWidth(), bitmapDrawable.getBitmap().getHeight(), 1.0f, 0) : new LoadBitmapResult(bitmapDrawable.getBitmap(), 1.0f, 0);
                    }
                    z3 = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            loadBitmapResult = coronaRuntime.getController().getBridge().loadBitmap(str, i, i2, z2);
            if (loadBitmapResult == null) {
                loadBitmapResult = coronaRuntime.getController().getBridge().loadBitmap(coronaRuntime.getController().getContext().getFileStreamPath("coronaResources") + "/" + str, i, i2, z2);
            }
            z3 = true;
        }
        if (loadBitmapResult == null) {
            return false;
        }
        Bitmap bitmap2 = loadBitmapResult.getBitmap();
        boolean z4 = false;
        if (z2) {
            z4 = JavaToNativeShim.copyBitmapInfo(coronaRuntime, j, loadBitmapResult.getWidth(), loadBitmapResult.getHeight(), loadBitmapResult.getScaleFactor(), loadBitmapResult.getRotationInDegrees());
            bitmap = bitmap2;
        } else if (bitmap2 != null) {
            if (bitmap2.getConfig() == null) {
                Bitmap bitmap3 = null;
                if (z) {
                    try {
                        bitmap3 = bitmap2.copy(Bitmap.Config.ALPHA_8, false);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    bitmap3 = bitmap2.copy(Bitmap.Config.ARGB_8888, false);
                }
                if (z3) {
                    bitmap2.recycle();
                }
                bitmap = bitmap3;
                z3 = true;
                if (bitmap == null) {
                    return false;
                }
            } else {
                bitmap = bitmap2;
            }
            z4 = JavaToNativeShim.copyBitmap(coronaRuntime, j, bitmap, loadBitmapResult.getScaleFactor(), loadBitmapResult.getRotationInDegrees(), z);
        } else {
            bitmap = bitmap2;
        }
        if (!z3 || bitmap == null) {
            return z4;
        }
        bitmap.recycle();
        return z4;
    }

    protected static int callLoadClass(CoronaRuntime coronaRuntime, long j, String str, String str2) {
        int i = 0;
        if (coronaRuntime == null) {
            return 0;
        }
        LuaState luaState = coronaRuntime.getLuaState();
        if (luaState == null) {
            luaState = new LuaState(j);
        }
        String str3 = str + "." + str2;
        try {
            Log.v("Corona", "> Class.forName: " + str3);
            Class<?> cls = Class.forName(str3);
            Log.v("Corona", "< Class.forName: " + str3);
            i = instantiateClass(luaState, coronaRuntime, cls);
            Log.v("Corona", "Loading via reflection: " + str3);
            return i;
        } catch (Exception e) {
            if (str3.toLowerCase().contains("plugin.fuse")) {
                return i;
            }
            Log.v("Corona", "WARNING: Could not load '" + str3 + "'");
            e.printStackTrace();
            return i;
        }
    }

    protected static void callLoadEventSound(CoronaRuntime coronaRuntime, int i, String str) {
        coronaRuntime.getController().getEventManager().loadEventSound(i, str);
    }

    protected static int callLoadFile(CoronaRuntime coronaRuntime, long j, String str) {
        int i = 0;
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext == null) {
            return 0;
        }
        if (coronaRuntime != null) {
            FileServices fileServices = new FileServices(applicationContext);
            String replace = str.replace('.', '/');
            String str2 = coronaRuntime.getPath() + replace + ".lua";
            File file = new File(str2);
            if (!fileServices.doesAssetFileExist(str2)) {
                return 0;
            }
            LuaState luaState = coronaRuntime.getLuaState();
            if (luaState == null) {
                luaState = new LuaState(j);
            }
            InputStream inputStream = null;
            try {
                inputStream = fileServices.openFile(file);
                luaState.load(inputStream, replace);
                i = 1;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e2) {
                Log.v("Corona", "WARNING: Could not load '" + replace + "'");
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e3) {
                    }
                }
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e4) {
                    }
                }
                throw th;
            }
        }
        return i;
    }

    protected static void callLoadSound(CoronaRuntime coronaRuntime, int i, String str) {
        coronaRuntime.getController().getEventManager().loadSound(i, str);
    }

    protected static int callMapViewAddMarker(CoronaRuntime coronaRuntime, int i, double d, double d2, String str, String str2, int i2, String str3) {
        MapMarker mapMarker = new MapMarker(d, d2);
        mapMarker.setTitle(str);
        mapMarker.setSubtitle(str2);
        mapMarker.setListener(i2);
        mapMarker.setImageFile(str3);
        return coronaRuntime.getViewManager().addMapMarker(i, mapMarker);
    }

    protected static void callMapViewCreate(CoronaRuntime coronaRuntime, int i, int i2, int i3, int i4, int i5) {
        coronaRuntime.getViewManager().addMapView(i, i2, i3, i4, i5);
    }

    protected static String callMapViewGetType(CoronaRuntime coronaRuntime, int i) {
        MapType mapType = coronaRuntime.getViewManager().getMapType(i);
        if (mapType == null) {
            mapType = MapType.STANDARD;
        }
        return mapType.toInvariantString();
    }

    protected static boolean callMapViewIsCurrentLocationVisible(CoronaRuntime coronaRuntime, int i) {
        return coronaRuntime.getViewManager().isCurrentLocationVisibleInMap(i);
    }

    protected static boolean callMapViewIsScrollEnabled(CoronaRuntime coronaRuntime, int i) {
        return coronaRuntime.getViewManager().isMapScrollEnabled(i);
    }

    protected static boolean callMapViewIsZoomEnabled(CoronaRuntime coronaRuntime, int i) {
        return coronaRuntime.getViewManager().isMapZoomEnabled(i);
    }

    protected static int callMapViewPushCurrentLocationToLua(CoronaRuntime coronaRuntime, int i, long j) {
        return coronaRuntime.getViewManager().pushMapCurrentLocationToLua(i, j);
    }

    protected static void callMapViewRemoveAllMarkers(int i, CoronaRuntime coronaRuntime) {
        coronaRuntime.getViewManager().removeAllMapViewMarkers(i);
    }

    protected static void callMapViewRemoveMarker(CoronaRuntime coronaRuntime, int i, int i2) {
        coronaRuntime.getViewManager().removeMapMarker(i, i2);
    }

    protected static void callMapViewSetCenter(CoronaRuntime coronaRuntime, int i, double d, double d2, boolean z) {
        coronaRuntime.getViewManager().setMapCenter(i, d, d2, z);
    }

    protected static void callMapViewSetRegion(CoronaRuntime coronaRuntime, int i, double d, double d2, double d3, double d4, boolean z) {
        coronaRuntime.getViewManager().setMapRegion(i, d, d2, d3, d4, z);
    }

    protected static void callMapViewSetScrollEnabled(CoronaRuntime coronaRuntime, int i, boolean z) {
        coronaRuntime.getViewManager().setMapScrollEnabled(i, z);
    }

    protected static void callMapViewSetType(CoronaRuntime coronaRuntime, int i, String str) {
        MapType fromInvariantString = MapType.fromInvariantString(str);
        if (fromInvariantString != null) {
            coronaRuntime.getViewManager().setMapType(i, fromInvariantString);
        }
    }

    protected static void callMapViewSetZoomEnabled(CoronaRuntime coronaRuntime, int i, boolean z) {
        coronaRuntime.getViewManager().setMapZoomEnabled(i, z);
    }

    protected static void callNotificationCancel(int i) {
        new NotificationServices(CoronaEnvironment.getApplicationContext()).removeById(i);
    }

    protected static void callNotificationCancelAll(CoronaRuntime coronaRuntime) {
        new NotificationServices(CoronaEnvironment.getApplicationContext()).removeAll();
    }

    protected static int callNotificationSchedule(CoronaRuntime coronaRuntime, long j, int i) {
        if (j == 0) {
            return 0;
        }
        LuaState luaState = null;
        if (coronaRuntime != null) {
            luaState = coronaRuntime.getLuaState();
        }
        if (luaState == null || CoronaRuntimeProvider.getLuaStateMemoryAddress(luaState) != j) {
            luaState = new LuaState(j);
        }
        return notificationSchedule(luaState, i);
    }

    protected static void callOnAudioEnabled() {
        MediaManager.onUsingAudio();
    }

    protected static void callOnRuntimeLoaded(long j, CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null) {
            coronaRuntime.onLoaded(j);
        }
    }

    protected static void callOnRuntimeResumed(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null) {
            coronaRuntime.onResumed();
        }
    }

    protected static void callOnRuntimeStarted(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null) {
            coronaRuntime.onStarted();
        }
        CoronaSplashScreenApiListener coronaSplashScreenApiListener = coronaRuntime.getController().getCoronaSplashScreenApiListener();
        if (coronaSplashScreenApiListener != null) {
            coronaSplashScreenApiListener.hideSplashScreen();
        }
    }

    protected static void callOnRuntimeSuspended(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null) {
            coronaRuntime.onSuspended();
        }
    }

    protected static void callOnRuntimeWillLoadMain(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null) {
            coronaRuntime.onWillLoadMain();
        }
    }

    protected static boolean callOpenUrl(CoronaRuntime coronaRuntime, String str) {
        return coronaRuntime.getController().openUrl(str);
    }

    protected static void callPauseSound(int i, CoronaRuntime coronaRuntime) {
        coronaRuntime.getController().getEventManager().pauseSound(i);
    }

    protected static void callPlaySound(CoronaRuntime coronaRuntime, int i, String str, boolean z) {
        coronaRuntime.getController().getEventManager().playSound(i, str, z);
    }

    protected static void callPlayVideo(CoronaRuntime coronaRuntime, int i, String str, boolean z) {
        coronaRuntime.getController().getMediaManager().playVideo(i, str, z);
    }

    protected static void callPushApplicationOpenArgumentsToLuaTable(CoronaRuntime coronaRuntime, long j) {
        CoronaSystemApiListener coronaSystemApiListener = coronaRuntime.getController().getCoronaSystemApiListener();
        if (coronaSystemApiListener != null) {
            pushArgumentsToLuaTable(coronaRuntime, j, coronaSystemApiListener.getIntent());
        }
    }

    protected static void callPushLaunchArgumentsToLuaTable(CoronaRuntime coronaRuntime, long j) {
        CoronaSystemApiListener coronaSystemApiListener = coronaRuntime.getController().getCoronaSystemApiListener();
        if (coronaSystemApiListener != null) {
            pushArgumentsToLuaTable(coronaRuntime, j, coronaSystemApiListener.getInitialIntent());
        }
    }

    protected static int callPushLocationNameCoordinatesToLua(CoronaRuntime coronaRuntime, String str, long j) {
        double d = 0.0d;
        double d2 = 0.0d;
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext != null) {
            applicationContext.enforceCallingOrSelfPermission("android.permission.INTERNET", (String) null);
        }
        LuaState luaState = null;
        if (coronaRuntime != null) {
            luaState = coronaRuntime.getLuaState();
        }
        if (luaState == null || CoronaRuntimeProvider.getLuaStateMemoryAddress(luaState) != j) {
            luaState = new LuaState(j);
        }
        Location locationFromName = getLocationFromName(str);
        if (locationFromName != null) {
            d = locationFromName.getLatitude();
            d2 = locationFromName.getLongitude();
        }
        luaState.pushNumber(d);
        luaState.pushNumber(d2);
        return 2;
    }

    protected static int callPushSystemInfoToLua(CoronaRuntime coronaRuntime, long j, String str) {
        String scriptFrom;
        if (j == 0) {
            return 0;
        }
        LuaState luaState = null;
        if (coronaRuntime != null) {
            luaState = coronaRuntime.getLuaState();
        }
        if (luaState == null || CoronaRuntimeProvider.getLuaStateMemoryAddress(luaState) != j) {
            luaState = new LuaState(j);
        }
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext == null) {
            return 0;
        }
        ApplicationInfo applicationInfo = applicationContext.getApplicationInfo();
        PackageManager packageManager = applicationContext.getPackageManager();
        int i = 0;
        if (str != null && str.length() > 0) {
            if (str.equals("appName")) {
                String str2 = null;
                CharSequence applicationLabel = packageManager.getApplicationLabel(applicationInfo);
                if (applicationLabel != null) {
                    str2 = applicationLabel.toString();
                }
                if (str2 == null) {
                    str2 = BuildConfig.FLAVOR;
                }
                luaState.pushString(str2);
                i = 1;
            } else if (str.equals("appVersionString")) {
                String str3 = null;
                try {
                    str3 = packageManager.getPackageInfo(applicationContext.getPackageName(), 0).versionName;
                } catch (Exception e) {
                }
                if (str3 == null) {
                    str3 = BuildConfig.FLAVOR;
                }
                luaState.pushString(str3);
                i = 1;
            } else if (str.equals("androidAppVersionCode")) {
                int i2 = 0;
                try {
                    i2 = packageManager.getPackageInfo(applicationContext.getPackageName(), 0).versionCode;
                } catch (Exception e2) {
                }
                luaState.pushInteger(i2);
                i = 1;
            } else if (str.equals("androidAppPackageName")) {
                String str4 = applicationInfo.packageName;
                if (str4 == null) {
                    str4 = BuildConfig.FLAVOR;
                }
                luaState.pushString(str4);
                i = 1;
            } else if (str.equals("androidApiLevel")) {
                luaState.pushInteger(Build.VERSION.SDK_INT);
                i = 1;
            } else if (str.equals("androidDisplayDensityName")) {
                String str5 = "unknown";
                DisplayMetrics displayMetrics = getDisplayMetrics();
                if (displayMetrics != null) {
                    switch (displayMetrics.densityDpi) {
                        case 120:
                            str5 = "ldpi";
                            break;
                        case 160:
                            str5 = "mdpi";
                            break;
                        case 213:
                            str5 = "tvdpi";
                            break;
                        case 240:
                            str5 = "hdpi";
                            break;
                        case 320:
                            str5 = "xhdpi";
                            break;
                        case 480:
                            str5 = "xxhdpi";
                            break;
                        case 640:
                            str5 = "xxxhdpi";
                            break;
                    }
                }
                luaState.pushString(str5);
                i = 1;
            } else if (str.equals("androidDisplayApproximateDpi")) {
                DisplayMetrics displayMetrics2 = getDisplayMetrics();
                if (displayMetrics2 != null) {
                    luaState.pushInteger(displayMetrics2.densityDpi);
                    i = 1;
                }
            } else if (str.equals("androidDisplayXDpi")) {
                DisplayMetrics displayMetrics3 = getDisplayMetrics();
                if (displayMetrics3 != null) {
                    luaState.pushNumber((double) (WindowOrientation.fromCurrentWindowUsing(applicationContext).isPortrait() ? displayMetrics3.xdpi : displayMetrics3.ydpi));
                    i = 1;
                }
            } else if (str.equals("androidDisplayYDpi")) {
                DisplayMetrics displayMetrics4 = getDisplayMetrics();
                if (displayMetrics4 != null) {
                    luaState.pushNumber((double) (WindowOrientation.fromCurrentWindowUsing(applicationContext).isPortrait() ? displayMetrics4.ydpi : displayMetrics4.xdpi));
                    i = 1;
                }
            } else if (str.equals("androidDisplayWidthInInches")) {
                DisplayMetrics displayMetrics5 = getDisplayMetrics();
                if (displayMetrics5 != null) {
                    double d = (double) (WindowOrientation.fromCurrentWindowUsing(applicationContext).isPortrait() ? displayMetrics5.xdpi : displayMetrics5.ydpi);
                    if (d > 0.0d) {
                        luaState.pushNumber(((double) displayMetrics5.widthPixels) / d);
                        i = 1;
                    }
                }
            } else if (str.equals("androidDisplayHeightInInches")) {
                DisplayMetrics displayMetrics6 = getDisplayMetrics();
                if (displayMetrics6 != null) {
                    double d2 = (double) (WindowOrientation.fromCurrentWindowUsing(applicationContext).isPortrait() ? displayMetrics6.ydpi : displayMetrics6.xdpi);
                    if (d2 > 0.0d) {
                        luaState.pushNumber(((double) displayMetrics6.heightPixels) / d2);
                        i = 1;
                    }
                }
            } else if (str.equals("isoCountryCode")) {
                luaState.pushString(Locale.getDefault().getCountry());
                i = 1;
            } else if (str.equals("isoLanguageCode")) {
                String language = Locale.getDefault().getLanguage();
                String lowerCase = language != null ? language.toLowerCase() : BuildConfig.FLAVOR;
                if ("zh".equals(lowerCase) && Build.VERSION.SDK_INT >= 21 && (scriptFrom = ApiLevel21.getScriptFrom(Locale.getDefault())) != null && scriptFrom.length() > 0) {
                    lowerCase = lowerCase + AdNetworkKey.DEFAULT_AD + scriptFrom.toLowerCase();
                }
                luaState.pushString(lowerCase);
                i = 1;
            }
        }
        if (i > 0) {
            return i;
        }
        luaState.pushNil();
        return 1;
    }

    protected static ByteBuffer callRecordGetBytes(CoronaRuntime coronaRuntime, int i) {
        AudioRecorder.AudioByteBufferHolder nextBuffer = coronaRuntime.getController().getMediaManager().getAudioRecorder(i).getNextBuffer();
        if (nextBuffer != null) {
            return nextBuffer.myBuffer;
        }
        return null;
    }

    protected static int callRecordGetCurrentByteCount(CoronaRuntime coronaRuntime, int i) {
        AudioRecorder.AudioByteBufferHolder currentBuffer = coronaRuntime.getController().getMediaManager().getAudioRecorder(i).getCurrentBuffer();
        if (currentBuffer != null) {
            return currentBuffer.myValidBytes;
        }
        return 0;
    }

    protected static void callRecordReleaseCurrentBuffer(int i, CoronaRuntime coronaRuntime) {
        coronaRuntime.getController().getMediaManager().getAudioRecorder(i).releaseCurrentBuffer();
    }

    protected static void callRecordStart(CoronaRuntime coronaRuntime, String str, int i) {
        coronaRuntime.getController().getMediaManager().getAudioRecorder(i).startRecording(str);
    }

    protected static void callRecordStop(int i, CoronaRuntime coronaRuntime) {
        coronaRuntime.getController().getMediaManager().getAudioRecorder(i).stopRecording();
    }

    protected static boolean callRenderText(CoronaRuntime coronaRuntime, long j, String str, String str2, float f, boolean z, int i, int i2, int i3, String str3) {
        Context applicationContext;
        if (j == 0 || (applicationContext = CoronaEnvironment.getApplicationContext()) == null) {
            return false;
        }
        HorizontalAlignment fromCoronaStringId = HorizontalAlignment.fromCoronaStringId(str3);
        if (fromCoronaStringId == null) {
            fromCoronaStringId = HorizontalAlignment.LEFT;
        }
        TextRenderer textRenderer = new TextRenderer(applicationContext);
        textRenderer.getFontSettings().setName(str2);
        textRenderer.getFontSettings().setPointSize(f);
        textRenderer.getFontSettings().setIsBold(z);
        textRenderer.setText(str);
        textRenderer.setHorizontalAlignment(fromCoronaStringId);
        textRenderer.setWrapWidth(i);
        textRenderer.setClipWidth(i2);
        textRenderer.setClipHeight(i3);
        Bitmap createBitmap = textRenderer.createBitmap();
        if (createBitmap == null) {
            return false;
        }
        boolean copyBitmap = JavaToNativeShim.copyBitmap(coronaRuntime, j, createBitmap, 1.0f, 0, true);
        createBitmap.recycle();
        return copyBitmap;
    }

    protected static void callRequestLocationAsync(CoronaRuntime coronaRuntime, long j) {
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext != null) {
            applicationContext.enforceCallingOrSelfPermission("android.permission.INTERNET", (String) null);
        }
        LuaState luaState = null;
        if (coronaRuntime != null) {
            luaState = coronaRuntime.getLuaState();
        }
        if (luaState == null || CoronaRuntimeProvider.getLuaStateMemoryAddress(luaState) != j) {
            luaState = new LuaState(j);
        }
        luaState.checkArg(-1, CoronaLua.isListener(luaState, -1, "mapLocation"), "The third arguement of requestLocation should be a listener.");
        final int newRef = CoronaLua.newRef(luaState, -1);
        final String checkString = luaState.checkString(-2);
        final CoronaRuntimeTaskDispatcher taskDispatcher = coronaRuntime.getTaskDispatcher();
        new Thread(new Runnable() {
            public void run() {
                Location access$000 = NativeToJavaBridge.getLocationFromName(checkString);
                if (taskDispatcher == null) {
                    return;
                }
                if (access$000 != null) {
                    taskDispatcher.send(new MapRequestLocationTask(newRef, access$000.getLatitude(), access$000.getLongitude(), checkString));
                } else {
                    taskDispatcher.send(new MapRequestLocationFailedTask(newRef, BuildConfig.FLAVOR, checkString));
                }
            }
        }).start();
    }

    protected static void callRequestNearestAddressFromCoordinates(CoronaRuntime coronaRuntime, long j) {
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext != null) {
            applicationContext.enforceCallingOrSelfPermission("android.permission.INTERNET", (String) null);
        }
        LuaState luaState = null;
        if (coronaRuntime != null) {
            luaState = coronaRuntime.getLuaState();
        }
        if (luaState == null || CoronaRuntimeProvider.getLuaStateMemoryAddress(luaState) != j) {
            luaState = new LuaState(j);
        }
        luaState.checkArg(-1, CoronaLua.isListener(luaState, -1, "requestLocation"), "The third arguement of nearestAddress should be a listener.");
        int newRef = CoronaLua.newRef(luaState, -1);
        double checkNumber = luaState.checkNumber(-2);
        final double checkNumber2 = luaState.checkNumber(-3);
        final double d = checkNumber;
        final int i = newRef;
        final CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher = new CoronaRuntimeTaskDispatcher(luaState);
        new Thread(new Runnable() {
            public void run() {
                Address address = null;
                String str = null;
                try {
                    List<Address> fromLocation = new Geocoder(CoronaEnvironment.getApplicationContext()).getFromLocation(checkNumber2, d, 1);
                    if (fromLocation != null && fromLocation.size() > 0) {
                        address = fromLocation.get(0);
                    }
                } catch (Exception e) {
                    str = e.getMessage();
                }
                if (str == null) {
                    str = "Address not found for given coordinates.";
                }
                final String str2 = str;
                final Address address2 = address;
                coronaRuntimeTaskDispatcher.send(new CoronaRuntimeTask() {
                    public void executeUsing(CoronaRuntime coronaRuntime) {
                        LuaState luaState = coronaRuntime.getLuaState();
                        CoronaLua.newEvent(luaState, "nearestAddress");
                        if (address2 != null) {
                            try {
                                if (address2.getThoroughfare() != null) {
                                    luaState.pushString(address2.getThoroughfare());
                                    luaState.setField(-2, "street");
                                }
                                if (address2.getSubThoroughfare() != null) {
                                    luaState.pushString(address2.getSubThoroughfare());
                                    luaState.setField(-2, "streetDetail");
                                }
                                if (address2.getLocality() != null) {
                                    luaState.pushString(address2.getLocality());
                                    luaState.setField(-2, "city");
                                }
                                if (address2.getSubLocality() != null) {
                                    luaState.pushString(address2.getSubLocality());
                                    luaState.setField(-2, "cityDetail");
                                }
                                if (address2.getAdminArea() != null) {
                                    luaState.pushString(address2.getAdminArea());
                                    luaState.setField(-2, "region");
                                }
                                if (address2.getSubAdminArea() != null) {
                                    luaState.pushString(address2.getSubAdminArea());
                                    luaState.setField(-2, "regionDetail");
                                }
                                if (address2.getPostalCode() != null) {
                                    luaState.pushString(address2.getPostalCode());
                                    luaState.setField(-2, "postalCode");
                                }
                                if (address2.getCountryName() != null) {
                                    luaState.pushString(address2.getCountryName());
                                    luaState.setField(-2, "country");
                                }
                                if (address2.getCountryCode() != null) {
                                    luaState.pushString(address2.getCountryCode());
                                    luaState.setField(-2, "countryCode");
                                }
                                CoronaLua.dispatchEvent(luaState, i, 0);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                luaState.pushBoolean(true);
                                luaState.setField(-2, CoronaLuaEvent.ISERROR_KEY);
                                luaState.pushString(str2);
                                luaState.setField(-2, "errorMessage");
                                CoronaLua.dispatchEvent(luaState, i, 0);
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                        CoronaLua.deleteRef(luaState, i);
                    }
                });
            }
        }).start();
    }

    protected static boolean callRequestSystem(CoronaRuntime coronaRuntime, long j, String str, int i) {
        CoronaSystemApiListener coronaSystemApiListener = coronaRuntime.getController().getCoronaSystemApiListener();
        if (coronaSystemApiListener != null) {
            return coronaSystemApiListener.requestSystem(str);
        }
        return false;
    }

    protected static void callResumeSound(int i, CoronaRuntime coronaRuntime) {
        coronaRuntime.getController().getEventManager().resumeSound(i);
    }

    protected static boolean callSaveBitmap(CoronaRuntime coronaRuntime, int[] iArr, int i, int i2, int i3, String str) {
        boolean z = false;
        if (coronaRuntime.getController() == null) {
            Log.v("Corona", "callSaveBitmap has invalid controller");
        } else {
            boolean z2 = false;
            if (str == null || str.length() <= 0) {
                File createUniqueFileNameInPicturesDirectory = createUniqueFileNameInPicturesDirectory(".png");
                if (createUniqueFileNameInPicturesDirectory == null) {
                    Log.v("Corona", "ERROR: Failed to save bitmap to the photo library.");
                } else {
                    str = createUniqueFileNameInPicturesDirectory.getPath();
                    z2 = true;
                }
            }
            Bitmap bitmap = null;
            try {
                bitmap = Bitmap.createBitmap(iArr, i, i2, Bitmap.Config.ARGB_8888);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                z = coronaRuntime.getController().saveBitmap(bitmap, i3, str);
                if (z && z2) {
                    coronaRuntime.getController().addImageFileToPhotoGallery(str);
                }
                bitmap.recycle();
            }
        }
        return z;
    }

    protected static boolean callSaveImageToPhotoLibrary(CoronaRuntime coronaRuntime, String str) {
        Controller controller;
        if (str == null || str.length() <= 0 || (controller = coronaRuntime.getController()) == null) {
            return false;
        }
        FileServices fileServices = new FileServices(CoronaEnvironment.getApplicationContext());
        File createUniqueFileNameInPicturesDirectory = createUniqueFileNameInPicturesDirectory(fileServices.getExtensionFrom(str));
        if (createUniqueFileNameInPicturesDirectory == null) {
            return false;
        }
        String path = createUniqueFileNameInPicturesDirectory.getPath();
        if (!fileServices.copyFile(str, path)) {
            return false;
        }
        controller.addImageFileToPhotoGallery(path);
        return true;
    }

    protected static void callSetAccelerometerInterval(int i, CoronaRuntime coronaRuntime) {
        coronaRuntime.getController().setAccelerometerInterval(i);
    }

    protected static void callSetEventNotification(CoronaRuntime coronaRuntime, int i, boolean z) {
        coronaRuntime.getController().setEventNotification(i, z);
    }

    protected static void callSetGyroscopeInterval(int i, CoronaRuntime coronaRuntime) {
        coronaRuntime.getController().setGyroscopeInterval(i);
    }

    protected static void callSetIdleTimer(CoronaRuntime coronaRuntime, boolean z) {
        coronaRuntime.getController().setIdleTimer(z);
    }

    protected static void callSetLocationAccuracy(double d, CoronaRuntime coronaRuntime) {
    }

    protected static void callSetLocationThreshold(double d, CoronaRuntime coronaRuntime) {
        coronaRuntime.getController().setLocationThreshold(d);
    }

    protected static void callSetStatusBarMode(int i, CoronaRuntime coronaRuntime) {
        CoronaStatusBarApiListener coronaStatusBarApiListener = coronaRuntime.getController().getCoronaStatusBarApiListener();
        if (coronaStatusBarApiListener != null) {
            coronaStatusBarApiListener.setStatusBarMode(CoronaStatusBarSettings.fromCoronaIntId(i));
        }
    }

    protected static void callSetSystemUiVisibility(CoronaRuntime coronaRuntime, String str) {
        if (coronaRuntime != null) {
            coronaRuntime.getController().setSystemUiVisibility(str);
        }
    }

    protected static void callSetTimer(int i, CoronaRuntime coronaRuntime) {
        coronaRuntime.getController().setTimer(i);
    }

    protected static void callSetVolume(CoronaRuntime coronaRuntime, int i, float f) {
        coronaRuntime.getController().getMediaManager().setVolume(i, f);
    }

    protected static boolean callShowAppStorePopup(CoronaRuntime coronaRuntime, HashMap hashMap) {
        return coronaRuntime.getController().showAppStoreWindow(hashMap);
    }

    protected static void callShowImagePicker(CoronaRuntime coronaRuntime, int i, String str) {
        coronaRuntime.getController().showImagePickerWindow(i, str);
    }

    protected static void callShowNativeActivityIndicator(CoronaRuntime coronaRuntime) {
        coronaRuntime.getController().showNativeActivityIndicator();
    }

    protected static void callShowNativeAlert(CoronaRuntime coronaRuntime, String str, String str2, String[] strArr) {
        coronaRuntime.getController().showNativeAlert(str, str2, strArr);
    }

    protected static void callShowSendMailPopup(CoronaRuntime coronaRuntime, HashMap hashMap) {
        coronaRuntime.getController().showSendMailWindow(hashMap);
    }

    protected static void callShowSendSmsPopup(CoronaRuntime coronaRuntime, HashMap hashMap) {
        coronaRuntime.getController().showSendSmsWindow(hashMap);
    }

    protected static void callShowSplashScreen(CoronaRuntime coronaRuntime) {
        CoronaSplashScreenApiListener coronaSplashScreenApiListener = coronaRuntime.getController().getCoronaSplashScreenApiListener();
        if (coronaSplashScreenApiListener != null) {
            coronaSplashScreenApiListener.showSplashScreen();
        }
    }

    protected static void callShowTrialAlert(CoronaRuntime coronaRuntime) {
        coronaRuntime.getController().showTrialAlert();
    }

    protected static void callShowVideoPicker(CoronaRuntime coronaRuntime, int i, int i2, int i3) {
        coronaRuntime.getController().showVideoPickerWindow(i, i2, i3);
    }

    protected static void callStopSound(int i, CoronaRuntime coronaRuntime) {
        coronaRuntime.getController().getEventManager().stopSound(i);
    }

    protected static void callStoreFinishTransaction(CoronaRuntime coronaRuntime, String str) {
        CoronaStoreApiListener coronaStoreApiListener = coronaRuntime.getController().getCoronaStoreApiListener();
        if (coronaStoreApiListener != null) {
            coronaStoreApiListener.storeFinishTransaction(str);
        }
    }

    protected static void callStoreInit(CoronaRuntime coronaRuntime, String str) {
        CoronaStoreApiListener coronaStoreApiListener = coronaRuntime.getController().getCoronaStoreApiListener();
        if (coronaStoreApiListener != null) {
            coronaStoreApiListener.storeInit(str);
        }
    }

    protected static void callStorePurchase(CoronaRuntime coronaRuntime, String str) {
        CoronaStoreApiListener coronaStoreApiListener = coronaRuntime.getController().getCoronaStoreApiListener();
        if (coronaStoreApiListener != null) {
            coronaStoreApiListener.storePurchase(str);
        }
    }

    protected static void callStoreRestoreCompletedTransactions(CoronaRuntime coronaRuntime) {
        CoronaStoreApiListener coronaStoreApiListener = coronaRuntime.getController().getCoronaStoreApiListener();
        if (coronaStoreApiListener != null) {
            coronaStoreApiListener.storeRestore();
        }
    }

    protected static int callTextFieldCreate(CoronaRuntime coronaRuntime, int i, int i2, int i3, int i4, int i5, boolean z) {
        coronaRuntime.getViewManager().addTextView(i, i2, i3, i4, i5, z);
        return 1;
    }

    protected static String callTextFieldGetAlign(int i, CoronaRuntime coronaRuntime) {
        return coronaRuntime.getViewManager().getTextViewAlign(i);
    }

    protected static int[] callTextFieldGetColor(CoronaRuntime coronaRuntime, int i) {
        int textViewColor = coronaRuntime.getViewManager().getTextViewColor(i);
        return new int[]{Color.red(textViewColor), Color.green(textViewColor), Color.blue(textViewColor), Color.alpha(textViewColor)};
    }

    protected static String callTextFieldGetFont(int i) {
        return BuildConfig.FLAVOR;
    }

    protected static String callTextFieldGetInputType(int i, CoronaRuntime coronaRuntime) {
        return coronaRuntime.getViewManager().getTextViewInputType(i);
    }

    protected static String callTextFieldGetPlaceholder(int i, CoronaRuntime coronaRuntime) {
        return coronaRuntime.getViewManager().getTextViewPlaceholder(i);
    }

    protected static boolean callTextFieldGetSecure(CoronaRuntime coronaRuntime, int i) {
        return coronaRuntime.getViewManager().getTextViewPassword(i);
    }

    protected static float callTextFieldGetSize(CoronaRuntime coronaRuntime, int i) {
        return coronaRuntime.getViewManager().getTextViewSize(i);
    }

    protected static String callTextFieldGetText(int i, CoronaRuntime coronaRuntime) {
        return coronaRuntime.getViewManager().getTextViewText(i);
    }

    protected static boolean callTextFieldIsEditable(CoronaRuntime coronaRuntime, int i) {
        return coronaRuntime.getViewManager().isTextViewEditable(i);
    }

    protected static boolean callTextFieldIsSingleLine(CoronaRuntime coronaRuntime, int i) {
        return coronaRuntime.getViewManager().isTextViewSingleLine(i);
    }

    protected static void callTextFieldSetAlign(CoronaRuntime coronaRuntime, int i, String str) {
        coronaRuntime.getViewManager().setTextViewAlign(i, str);
    }

    protected static void callTextFieldSetColor(CoronaRuntime coronaRuntime, int i, int i2, int i3, int i4, int i5) {
        coronaRuntime.getViewManager().setTextViewColor(i, Color.argb(i5, i2, i3, i4));
    }

    protected static void callTextFieldSetEditable(CoronaRuntime coronaRuntime, int i, boolean z) {
        coronaRuntime.getViewManager().setTextViewEditable(i, z);
    }

    protected static void callTextFieldSetFont(CoronaRuntime coronaRuntime, int i, String str, float f, boolean z) {
        coronaRuntime.getViewManager().setTextViewFont(i, str, f, z);
    }

    protected static void callTextFieldSetInputType(CoronaRuntime coronaRuntime, int i, String str) {
        coronaRuntime.getViewManager().setTextViewInputType(i, str);
    }

    protected static void callTextFieldSetPlaceholder(CoronaRuntime coronaRuntime, int i, String str) {
        coronaRuntime.getViewManager().setTextPlaceholder(i, str);
    }

    protected static void callTextFieldSetReturnKey(CoronaRuntime coronaRuntime, int i, String str) {
        coronaRuntime.getViewManager().setTextReturnKey(i, str);
    }

    protected static void callTextFieldSetSecure(CoronaRuntime coronaRuntime, int i, boolean z) {
        coronaRuntime.getViewManager().setTextViewPassword(i, z);
    }

    protected static void callTextFieldSetSelection(CoronaRuntime coronaRuntime, int i, int i2, int i3) {
        coronaRuntime.getViewManager().setTextSelection(i, i2, i3);
    }

    protected static void callTextFieldSetSize(CoronaRuntime coronaRuntime, int i, float f) {
        coronaRuntime.getViewManager().setTextViewSize(i, f);
    }

    protected static void callTextFieldSetText(CoronaRuntime coronaRuntime, int i, String str) {
        coronaRuntime.getViewManager().setTextViewText(i, str);
    }

    protected static void callVibrate(CoronaRuntime coronaRuntime) {
        coronaRuntime.getController().vibrate();
    }

    protected static void callVibrateInputDevice(int i, CoronaRuntime coronaRuntime) {
        InputDeviceInterface fetchByCoronaDeviceId;
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext != null && (fetchByCoronaDeviceId = new InputDeviceServices(applicationContext).fetchByCoronaDeviceId(i)) != null) {
            fetchByCoronaDeviceId.vibrate();
        }
    }

    protected static void callVideoViewCreate(CoronaRuntime coronaRuntime, int i, int i2, int i3, int i4, int i5) {
        coronaRuntime.getViewManager().addVideoView(i, i2, i3, i4, i5);
    }

    protected static int callVideoViewGetCurrentTime(CoronaRuntime coronaRuntime, int i) {
        return coronaRuntime.getViewManager().videoViewGetCurrentTime(i);
    }

    protected static boolean callVideoViewGetIsMuted(CoronaRuntime coronaRuntime, int i) {
        return coronaRuntime.getViewManager().videoViewGetIsMuted(i);
    }

    protected static boolean callVideoViewGetIsTouchTogglesPlay(CoronaRuntime coronaRuntime, int i) {
        return coronaRuntime.getViewManager().videoViewGetIsTouchTogglesPlay(i);
    }

    protected static int callVideoViewGetTotalTime(CoronaRuntime coronaRuntime, int i) {
        return coronaRuntime.getViewManager().videoViewGetTotalTime(i);
    }

    protected static void callVideoViewLoad(CoronaRuntime coronaRuntime, int i, String str) {
        coronaRuntime.getViewManager().videoViewLoad(i, str);
    }

    protected static void callVideoViewMute(CoronaRuntime coronaRuntime, int i, boolean z) {
        coronaRuntime.getViewManager().videoViewMute(i, z);
    }

    protected static void callVideoViewPause(CoronaRuntime coronaRuntime, int i) {
        coronaRuntime.getViewManager().videoViewPause(i);
    }

    protected static void callVideoViewPlay(CoronaRuntime coronaRuntime, int i) {
        coronaRuntime.getViewManager().videoViewPlay(i);
    }

    protected static void callVideoViewSeek(CoronaRuntime coronaRuntime, int i, int i2) {
        coronaRuntime.getViewManager().videoViewSeek(i, i2);
    }

    protected static void callVideoViewTouchTogglesPlay(CoronaRuntime coronaRuntime, int i, boolean z) {
        coronaRuntime.getViewManager().videoViewTouchTogglesPlay(i, z);
    }

    protected static void callWebViewCreate(CoronaRuntime coronaRuntime, int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
        coronaRuntime.getViewManager().addWebView(i, i2, i3, i4, i5, z, z2);
    }

    protected static void callWebViewRequestDeleteCookies(int i, CoronaRuntime coronaRuntime) {
        coronaRuntime.getViewManager().requestWebViewDeleteCookies(i);
    }

    protected static void callWebViewRequestGoBack(int i, CoronaRuntime coronaRuntime) {
        coronaRuntime.getViewManager().requestWebViewGoBack(i);
    }

    protected static void callWebViewRequestGoForward(int i, CoronaRuntime coronaRuntime) {
        coronaRuntime.getViewManager().requestWebViewGoForward(i);
    }

    protected static void callWebViewRequestLoadUrl(CoronaRuntime coronaRuntime, int i, String str) {
        coronaRuntime.getViewManager().requestWebViewLoadUrl(i, str);
    }

    protected static void callWebViewRequestReload(int i, CoronaRuntime coronaRuntime) {
        coronaRuntime.getViewManager().requestWebViewReload(i);
    }

    protected static void callWebViewRequestStop(int i, CoronaRuntime coronaRuntime) {
        coronaRuntime.getViewManager().requestWebViewStop(i);
    }

    protected static File createUniqueFileNameInPicturesDirectory(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        } else if (str.length() > 0 && !str.startsWith(".")) {
            str = "." + str;
        }
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext == null) {
            return null;
        }
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!externalStoragePublicDirectory.exists() && !externalStoragePublicDirectory.mkdirs()) {
            externalStoragePublicDirectory = new File("/mnt/media/My Files/Pictures");
            if (!externalStoragePublicDirectory.exists()) {
                return null;
            }
        }
        String str2 = (String) applicationContext.getPackageManager().getApplicationLabel(applicationContext.getApplicationInfo());
        if (str2 == null || str2.length() <= 0) {
            str2 = "Corona";
        }
        int i = 1;
        while (i <= 10000) {
            try {
                File file = new File(externalStoragePublicDirectory, str2 + " Picture " + Integer.toString(i) + str);
                if (!file.exists()) {
                    return file;
                }
                i++;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0037 A[SYNTHETIC, Splitter:B:22:0x0037] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0040 A[SYNTHETIC, Splitter:B:27:0x0040] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.BitmapFactory.Options getBitmapFileInfo(java.lang.String r8) {
        /*
            r7 = this;
            r5 = 0
            r2 = 0
            if (r8 == 0) goto L_0x000a
            int r6 = r8.length()
            if (r6 > 0) goto L_0x000b
        L_0x000a:
            return r5
        L_0x000b:
            r4 = 0
            com.ansca.corona.storage.FileServices r1 = new com.ansca.corona.storage.FileServices     // Catch:{ Exception -> 0x0031 }
            android.content.Context r5 = r7.myContext     // Catch:{ Exception -> 0x0031 }
            r1.<init>(r5)     // Catch:{ Exception -> 0x0031 }
            java.io.InputStream r4 = r1.openFile((java.lang.String) r8)     // Catch:{ Exception -> 0x0031 }
            if (r4 == 0) goto L_0x002a
            android.graphics.BitmapFactory$Options r3 = new android.graphics.BitmapFactory$Options     // Catch:{ Exception -> 0x0031 }
            r3.<init>()     // Catch:{ Exception -> 0x0031 }
            r5 = 1
            r3.inJustDecodeBounds = r5     // Catch:{ Exception -> 0x004b, all -> 0x0048 }
            r5 = 0
            android.graphics.BitmapFactory.decodeStream(r4, r5, r3)     // Catch:{ Exception -> 0x004b, all -> 0x0048 }
            int r5 = r3.outWidth     // Catch:{ Exception -> 0x004b, all -> 0x0048 }
            if (r5 >= 0) goto L_0x004e
            r2 = 0
        L_0x002a:
            if (r4 == 0) goto L_0x002f
            r4.close()     // Catch:{ Exception -> 0x0044 }
        L_0x002f:
            r5 = r2
            goto L_0x000a
        L_0x0031:
            r0 = move-exception
        L_0x0032:
            r0.printStackTrace()     // Catch:{ all -> 0x003d }
            if (r4 == 0) goto L_0x002f
            r4.close()     // Catch:{ Exception -> 0x003b }
            goto L_0x002f
        L_0x003b:
            r5 = move-exception
            goto L_0x002f
        L_0x003d:
            r5 = move-exception
        L_0x003e:
            if (r4 == 0) goto L_0x0043
            r4.close()     // Catch:{ Exception -> 0x0046 }
        L_0x0043:
            throw r5
        L_0x0044:
            r5 = move-exception
            goto L_0x002f
        L_0x0046:
            r6 = move-exception
            goto L_0x0043
        L_0x0048:
            r5 = move-exception
            r2 = r3
            goto L_0x003e
        L_0x004b:
            r0 = move-exception
            r2 = r3
            goto L_0x0032
        L_0x004e:
            r2 = r3
            goto L_0x002a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.NativeToJavaBridge.getBitmapFileInfo(java.lang.String):android.graphics.BitmapFactory$Options");
    }

    private static DisplayMetrics getDisplayMetrics() {
        WindowManager windowManager;
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext == null || (windowManager = (WindowManager) applicationContext.getSystemService("window")) == null) {
            return null;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    private int getImageExifRotationInDegreesFrom(String str) {
        if (str == null || str.length() <= 0 || new FileServices(CoronaEnvironment.getApplicationContext()).isAssetFile(str)) {
            return 0;
        }
        try {
            switch (new ExifInterface(str).getAttributeInt("Orientation", 1)) {
                case 3:
                    return 180;
                case 6:
                    return 90;
                case 8:
                    return 270;
                default:
                    return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x009a A[SYNTHETIC, Splitter:B:27:0x009a] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a3 A[SYNTHETIC, Splitter:B:32:0x00a3] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.location.Location getLocationFromName(java.lang.String r14) {
        /*
            r5 = 0
            if (r14 == 0) goto L_0x0093
            int r12 = r14.length()
            if (r12 <= 0) goto L_0x0093
            r2 = 0
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0094 }
            r12.<init>()     // Catch:{ Exception -> 0x0094 }
            java.lang.String r13 = "http://maps.googleapis.com/maps/api/geocode/json?address="
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ Exception -> 0x0094 }
            java.lang.String r13 = java.net.URLEncoder.encode(r14)     // Catch:{ Exception -> 0x0094 }
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ Exception -> 0x0094 }
            java.lang.String r13 = "&sensor=true"
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ Exception -> 0x0094 }
            java.lang.String r11 = r12.toString()     // Catch:{ Exception -> 0x0094 }
            java.lang.String r12 = "Android"
            android.net.http.AndroidHttpClient r2 = android.net.http.AndroidHttpClient.newInstance(r12)     // Catch:{ Exception -> 0x0094 }
            org.apache.http.client.methods.HttpGet r12 = new org.apache.http.client.methods.HttpGet     // Catch:{ Exception -> 0x0094 }
            r12.<init>(r11)     // Catch:{ Exception -> 0x0094 }
            org.apache.http.HttpResponse r3 = r2.execute(r12)     // Catch:{ Exception -> 0x0094 }
            org.apache.http.StatusLine r12 = r3.getStatusLine()     // Catch:{ Exception -> 0x0094 }
            int r12 = r12.getStatusCode()     // Catch:{ Exception -> 0x0094 }
            r13 = 200(0xc8, float:2.8E-43)
            if (r12 != r13) goto L_0x008e
            org.apache.http.HttpEntity r12 = r3.getEntity()     // Catch:{ Exception -> 0x0094 }
            if (r12 == 0) goto L_0x008e
            org.apache.http.HttpEntity r12 = r3.getEntity()     // Catch:{ Exception -> 0x0094 }
            java.lang.String r8 = org.apache.http.util.EntityUtils.toString(r12)     // Catch:{ Exception -> 0x0094 }
            if (r8 == 0) goto L_0x008e
            int r12 = r8.length()     // Catch:{ Exception -> 0x0094 }
            if (r12 <= 0) goto L_0x008e
            org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ Exception -> 0x0094 }
            r7.<init>(r8)     // Catch:{ Exception -> 0x0094 }
            java.lang.String r12 = "results"
            org.json.JSONArray r9 = r7.getJSONArray(r12)     // Catch:{ Exception -> 0x0094 }
            r12 = 0
            org.json.JSONObject r10 = r9.getJSONObject(r12)     // Catch:{ Exception -> 0x0094 }
            java.lang.String r12 = "geometry"
            org.json.JSONObject r1 = r10.getJSONObject(r12)     // Catch:{ Exception -> 0x0094 }
            java.lang.String r12 = "location"
            org.json.JSONObject r4 = r1.getJSONObject(r12)     // Catch:{ Exception -> 0x0094 }
            android.location.Location r6 = new android.location.Location     // Catch:{ Exception -> 0x0094 }
            java.lang.String r12 = "Google"
            r6.<init>(r12)     // Catch:{ Exception -> 0x0094 }
            java.lang.String r12 = "lat"
            double r12 = r4.getDouble(r12)     // Catch:{ Exception -> 0x00ae, all -> 0x00ab }
            r6.setLatitude(r12)     // Catch:{ Exception -> 0x00ae, all -> 0x00ab }
            java.lang.String r12 = "lng"
            double r12 = r4.getDouble(r12)     // Catch:{ Exception -> 0x00ae, all -> 0x00ab }
            r6.setLongitude(r12)     // Catch:{ Exception -> 0x00ae, all -> 0x00ab }
            r5 = r6
        L_0x008e:
            if (r2 == 0) goto L_0x0093
            r2.close()     // Catch:{ Exception -> 0x00a7 }
        L_0x0093:
            return r5
        L_0x0094:
            r0 = move-exception
        L_0x0095:
            r0.printStackTrace()     // Catch:{ all -> 0x00a0 }
            if (r2 == 0) goto L_0x0093
            r2.close()     // Catch:{ Exception -> 0x009e }
            goto L_0x0093
        L_0x009e:
            r12 = move-exception
            goto L_0x0093
        L_0x00a0:
            r12 = move-exception
        L_0x00a1:
            if (r2 == 0) goto L_0x00a6
            r2.close()     // Catch:{ Exception -> 0x00a9 }
        L_0x00a6:
            throw r12
        L_0x00a7:
            r12 = move-exception
            goto L_0x0093
        L_0x00a9:
            r13 = move-exception
            goto L_0x00a6
        L_0x00ab:
            r12 = move-exception
            r5 = r6
            goto L_0x00a1
        L_0x00ae:
            r0 = move-exception
            r5 = r6
            goto L_0x0095
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.NativeToJavaBridge.getLocationFromName(java.lang.String):android.location.Location");
    }

    private boolean getRawAssetExists(String str) {
        if (str == null || str.length() <= 0) {
            return false;
        }
        Uri parse = Uri.parse(Uri.encode(str, ":/\\."));
        String scheme = parse.getScheme();
        if (scheme == null) {
            scheme = BuildConfig.FLAVOR;
        }
        boolean z = false;
        if (scheme.toLowerCase().equals("android.app.icon")) {
            try {
                String host = parse.getHost();
                if (host == null || host.length() <= 0) {
                    return true;
                }
                return CoronaEnvironment.getApplicationContext().getPackageManager().getPackageInfo(host, 0) != null;
            } catch (Exception e) {
                return false;
            }
        } else {
            Context applicationContext = CoronaEnvironment.getApplicationContext();
            if (applicationContext != null) {
                z = new FileServices(applicationContext).doesAssetFileExist(str);
            }
            if (z) {
                return z;
            }
            Log.v("Corona", "WARNING: Asset file \"" + str + "\" does not exist.");
            return z;
        }
    }

    protected static int instantiateClass(LuaState luaState, CoronaRuntime coronaRuntime, Class<?> cls) {
        try {
            if (!JavaFunction.class.isAssignableFrom(cls)) {
                return 0;
            }
            JavaFunction javaFunction = sPluginCache.get(cls.getName());
            if (javaFunction == null) {
                Object newInstance = cls.newInstance();
                if (CoronaRuntimeListener.class.isAssignableFrom(cls)) {
                    CoronaRuntimeListener coronaRuntimeListener = (CoronaRuntimeListener) newInstance;
                    CoronaEnvironment.addRuntimeListener(coronaRuntimeListener);
                    coronaRuntimeListener.onLoaded(coronaRuntime);
                }
                javaFunction = (JavaFunction) newInstance;
                sPluginCache.put(cls.getName(), javaFunction);
            }
            luaState.pushJavaFunction(javaFunction);
            return 1;
        } catch (Exception e) {
            Log.v("Corona", "ERROR: Could not instantiate class (" + cls.getName() + "): " + e.getLocalizedMessage());
            e.printStackTrace();
            return 0;
        }
    }

    private LoadBitmapResult loadBitmap(String str, int i, int i2, boolean z) {
        BitmapFactory.Options bitmapFileInfo = getBitmapFileInfo(str);
        if (bitmapFileInfo == null) {
            return null;
        }
        int i3 = bitmapFileInfo.outWidth;
        int i4 = bitmapFileInfo.outHeight;
        FileServices fileServices = new FileServices(this.myContext);
        boolean isAssetFile = fileServices.isAssetFile(str);
        InputStream openFile = fileServices.openFile(str);
        if (openFile == null) {
            return null;
        }
        int i5 = 0;
        if (!isAssetFile) {
            i5 = getImageExifRotationInDegreesFrom(str);
        }
        int i6 = 0;
        if (i > 0 && i2 > 0) {
            i6 = i < i2 ? i : i2;
        } else if (i > 0) {
            i6 = i;
        } else if (i2 > 0) {
            i6 = i2;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        if (i6 > 0) {
            for (float max = ((float) Math.max(i3, i4)) / ((float) i6); max > 1.0f; max /= 2.0f) {
                options.inSampleSize *= 2;
            }
            if (options.inSampleSize > 1) {
                Log.v("Corona", "Downsampling image file '" + str + "' to fit max pixel size of " + Integer.toString(i6) + ".");
            }
        }
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        if ((((long) ((i3 * i4) / options.inSampleSize)) * 4) + 2000000 > Runtime.getRuntime().maxMemory()) {
            Log.v("Corona", "Not enough memory to load file '" + str + "' as a 32-bit image. Reducing the image quality to 16-bit.");
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        if (z) {
            int i7 = i3;
            int i8 = i4;
            float f = 1.0f;
            if (options.inSampleSize > 1) {
                i7 = i3 / options.inSampleSize;
                if (i3 % options.inSampleSize > 0) {
                    i7++;
                }
                i8 = i4 / options.inSampleSize;
                if (i4 % options.inSampleSize > 0) {
                    i8++;
                }
                f = ((float) i7) / ((float) i3);
            }
            return new LoadBitmapResult(i7, i8, f, i5);
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(openFile, (Rect) null, options);
        } catch (OutOfMemoryError e) {
            if (options.inPreferredConfig == Bitmap.Config.ARGB_8888) {
                Log.v("Corona", "Failed to load file '" + str + "' as a 32-bit image. Reducing the image quality to 16-bit.");
                System.gc();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bitmap = BitmapFactory.decodeStream(openFile, (Rect) null, options);
            } else {
                throw e;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            openFile.close();
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        if (bitmap == null) {
            Log.v("Corona", "Unable to decode file '" + str + "'");
            return null;
        }
        float f2 = 1.0f;
        if (bitmap.getWidth() != i3) {
            f2 = ((float) bitmap.getWidth()) / ((float) i3);
        }
        return new LoadBitmapResult(bitmap, f2, i5);
    }

    protected static int notificationSchedule(LuaState luaState, int i) {
        Date date = null;
        try {
            if (luaState.isTable(i)) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
                luaState.getField(i, "year");
                if (luaState.isNumber(-1)) {
                    gregorianCalendar.set(1, luaState.toInteger(-1));
                }
                luaState.pop(1);
                luaState.getField(i, "month");
                if (luaState.isNumber(-1)) {
                    gregorianCalendar.set(2, luaState.toInteger(-1) - 1);
                }
                luaState.pop(1);
                luaState.getField(i, "day");
                if (luaState.isNumber(-1)) {
                    gregorianCalendar.set(5, luaState.toInteger(-1));
                }
                luaState.pop(1);
                luaState.getField(i, "hour");
                if (luaState.isNumber(-1)) {
                    gregorianCalendar.set(11, luaState.toInteger(-1));
                }
                luaState.pop(1);
                luaState.getField(i, "min");
                if (luaState.isNumber(-1)) {
                    gregorianCalendar.set(12, luaState.toInteger(-1));
                }
                luaState.pop(1);
                luaState.getField(i, "sec");
                if (luaState.isNumber(-1)) {
                    gregorianCalendar.set(13, luaState.toInteger(-1));
                }
                luaState.pop(1);
                date = gregorianCalendar.getTime();
            } else if (luaState.type(i) == LuaType.NUMBER) {
                date = new Date(new Date().getTime() + ((long) (1000.0d * luaState.toNumber(i))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (date == null) {
            return 0;
        }
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        NotificationServices notificationServices = new NotificationServices(applicationContext);
        ScheduledNotificationSettings from = ScheduledNotificationSettings.from(applicationContext, luaState, i + 1);
        from.setId(notificationServices.reserveId());
        from.setEndTime(date);
        from.getStatusBarSettings().setTimestamp(date);
        notificationServices.post((NotificationSettings) from);
        return from.getId();
    }

    protected static void ping() {
        System.out.println("NativeToJavaBridge.ping()");
    }

    private static void pushArgumentsToLuaTable(CoronaRuntime coronaRuntime, long j, Intent intent) {
        if (j != 0 && intent != null) {
            LuaState luaState = null;
            if (coronaRuntime != null) {
                luaState = coronaRuntime.getLuaState();
            }
            if (luaState == null || CoronaRuntimeProvider.getLuaStateMemoryAddress(luaState) != j) {
                luaState = new LuaState(j);
            }
            int top = luaState.getTop();
            if (luaState.isTable(top)) {
                Uri data = intent.getData();
                luaState.pushString(data != null ? data.toString() : BuildConfig.FLAVOR);
                luaState.setField(top, "url");
                luaState.newTable();
                int top2 = luaState.getTop();
                luaState.pushString(data != null ? data.toString() : BuildConfig.FLAVOR);
                luaState.setField(top2, "url");
                String action = intent.getAction();
                if (action == null) {
                    action = BuildConfig.FLAVOR;
                }
                luaState.pushString(action);
                luaState.setField(top2, "action");
                if (!pushToLua(luaState, intent.getCategories())) {
                    luaState.newTable();
                }
                luaState.setField(top2, "categories");
                luaState.newTable();
                int top3 = luaState.getTop();
                Bundle extras = intent.getExtras();
                if (extras != null && extras.size() > 0) {
                    for (String str : extras.keySet()) {
                        if (pushToLua(luaState, extras.get(str))) {
                            luaState.setField(top3, str);
                        }
                    }
                }
                luaState.setField(top2, "extras");
                luaState.setField(top, "androidIntent");
                if (pushToLua(luaState, intent.getBundleExtra(NotificationReceivedTask.NAME))) {
                    luaState.setField(top, NotificationReceivedTask.NAME);
                }
            }
        }
    }

    private static boolean pushToLua(LuaState luaState, Object obj) {
        if (luaState == null || obj == null) {
            return false;
        }
        if (obj instanceof Boolean) {
            luaState.pushBoolean(((Boolean) obj).booleanValue());
        } else if ((obj instanceof Float) || (obj instanceof Double)) {
            luaState.pushNumber(((Number) obj).doubleValue());
        } else if (obj instanceof Number) {
            luaState.pushInteger(((Number) obj).intValue());
        } else if (obj instanceof Character) {
            luaState.pushString(obj.toString());
        } else if (obj instanceof String) {
            luaState.pushString((String) obj);
        } else if (obj instanceof File) {
            luaState.pushString(((File) obj).getAbsolutePath());
        } else if (obj instanceof Uri) {
            luaState.pushString(obj.toString());
        } else if (obj instanceof CoronaData) {
            ((CoronaData) obj).pushTo(luaState);
        } else if (obj instanceof Bundle) {
            Bundle bundle = (Bundle) obj;
            if (bundle.size() > 0) {
                luaState.newTable(0, bundle.size());
                int top = luaState.getTop();
                for (String str : bundle.keySet()) {
                    if (pushToLua(luaState, bundle.get(str))) {
                        luaState.setField(top, str);
                    }
                }
            } else {
                luaState.newTable();
            }
        } else if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            if (length > 0) {
                luaState.newTable(length, 0);
                int top2 = luaState.getTop();
                for (int i = 0; i < length; i++) {
                    if (pushToLua(luaState, Array.get(obj, i))) {
                        luaState.rawSet(top2, i + 1);
                    }
                }
            } else {
                luaState.newTable();
            }
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            if (map.size() > 0) {
                luaState.newTable(0, map.size());
                int top3 = luaState.getTop();
                for (Map.Entry entry : map.entrySet()) {
                    if (((entry.getKey() instanceof String) || (entry.getKey() instanceof Number)) && pushToLua(luaState, entry.getValue())) {
                        luaState.setField(top3, entry.getKey().toString());
                    }
                }
            } else {
                luaState.newTable();
            }
        } else if (!(obj instanceof Iterable)) {
            return false;
        } else {
            luaState.newTable();
            int top4 = luaState.getTop();
            int i2 = 0;
            for (Object pushToLua : (Iterable) obj) {
                if (pushToLua(luaState, pushToLua)) {
                    luaState.rawSet(top4, i2 + 1);
                }
                i2++;
            }
        }
        return true;
    }
}
