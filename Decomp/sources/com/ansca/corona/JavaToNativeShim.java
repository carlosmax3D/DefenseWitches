package com.ansca.corona;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import com.ansca.corona.input.AxisDataEventInfo;
import com.ansca.corona.input.AxisInfo;
import com.ansca.corona.input.InputDeviceInterface;
import com.ansca.corona.input.InputDeviceStatusEventInfo;
import com.ansca.corona.input.KeyPhase;
import com.ansca.corona.input.TouchTracker;
import com.ansca.corona.purchasing.StoreTransactionResultSettings;
import com.ansca.corona.storage.AssetFileLocationInfo;
import com.ansca.corona.storage.FileServices;
import java.util.Iterator;
import jp.stargarage.g2metrics.BuildConfig;

public class JavaToNativeShim {
    public static final int EventTypeAccelerometer = 1;
    public static final int EventTypeGyroscope = 2;
    public static final int EventTypeHeading = 4;
    public static final int EventTypeLocation = 3;
    public static final int EventTypeMultitouch = 5;
    public static final int EventTypeNumTypes = 6;
    public static final int EventTypeOrientation = 0;
    public static final int EventTypeUnknown = -1;

    static {
        System.loadLibrary("lua");
        System.loadLibrary("jnlua5.1");
        System.loadLibrary("openal");
        try {
            System.loadLibrary("mpg123");
        } catch (Exception | UnsatisfiedLinkError e) {
        }
        System.loadLibrary("almixer");
        System.loadLibrary("corona");
    }

    private JavaToNativeShim() {
    }

    public static void accelerometerEvent(CoronaRuntime coronaRuntime, double d, double d2, double d3, double d4) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeAccelerometerEvent(coronaRuntime.getJavaToNativeBridgeAddress(), d, d2, d3, d4);
        }
    }

    public static void alertCallback(CoronaRuntime coronaRuntime, int i, boolean z) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeAlertCallback(coronaRuntime.getJavaToNativeBridgeAddress(), i, z);
        }
    }

    public static void applicationOpenEvent(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeApplicationOpenEvent(coronaRuntime.getJavaToNativeBridgeAddress());
        }
    }

    public static void axisEvent(CoronaRuntime coronaRuntime, InputDeviceInterface inputDeviceInterface, AxisDataEventInfo axisDataEventInfo) {
        if (inputDeviceInterface != null && axisDataEventInfo != null && coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeAxisEvent(coronaRuntime.getJavaToNativeBridgeAddress(), inputDeviceInterface != null ? inputDeviceInterface.getCoronaDeviceId() : 0, axisDataEventInfo.getAxisIndex(), axisDataEventInfo.getDataPoint().getValue());
        }
    }

    public static Point convertCoronaPointToAndroidPoint(CoronaRuntime coronaRuntime, int i, int i2) {
        Object nativeConvertCoronaPointToAndroidPoint;
        if (coronaRuntime == null || coronaRuntime.wasDisposed() || (nativeConvertCoronaPointToAndroidPoint = nativeConvertCoronaPointToAndroidPoint(coronaRuntime.getJavaToNativeBridgeAddress(), i, i2)) == null || !(nativeConvertCoronaPointToAndroidPoint instanceof Point)) {
            return null;
        }
        return (Point) nativeConvertCoronaPointToAndroidPoint;
    }

    public static boolean copyBitmap(CoronaRuntime coronaRuntime, long j, Bitmap bitmap, float f, int i, boolean z) {
        if (j == 0 || bitmap == null || coronaRuntime == null || coronaRuntime.wasDisposed()) {
            return false;
        }
        return nativeCopyBitmap(coronaRuntime.getJavaToNativeBridgeAddress(), j, bitmap, f, i, z);
    }

    public static boolean copyBitmapInfo(CoronaRuntime coronaRuntime, long j, int i, int i2, float f, int i3) {
        if (j == 0 || coronaRuntime == null || coronaRuntime.wasDisposed()) {
            return false;
        }
        return nativeCopyBitmapInfo(coronaRuntime.getJavaToNativeBridgeAddress(), j, i, i2, f, i3);
    }

    public static void destroy(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null) {
            nativeDone(coronaRuntime.getJavaToNativeBridgeAddress());
        }
    }

    public static void dispatchEventInLua(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeDispatchEventInLua(coronaRuntime.getJavaToNativeBridgeAddress());
        }
    }

    public static int getContentHeightInPixels(CoronaRuntime coronaRuntime) {
        if (coronaRuntime == null || coronaRuntime.wasDisposed()) {
            return 0;
        }
        return nativeGetContentHeightInPixels(coronaRuntime.getJavaToNativeBridgeAddress());
    }

    public static int getContentWidthInPixels(CoronaRuntime coronaRuntime) {
        if (coronaRuntime == null || coronaRuntime.wasDisposed()) {
            return 0;
        }
        return nativeGetContentWidthInPixels(coronaRuntime.getJavaToNativeBridgeAddress());
    }

    public static CoronaRuntime getCoronaRuntimeFromBridge(long j) {
        return (CoronaRuntime) nativeGetCoronaRuntime(j);
    }

    public static int getHorizontalMarginInPixels(CoronaRuntime coronaRuntime) {
        if (coronaRuntime == null || coronaRuntime.wasDisposed()) {
            return 0;
        }
        return nativeGetHorizontalMarginInPixels(coronaRuntime.getJavaToNativeBridgeAddress());
    }

    public static String getKeyNameFromKeyCode(int i) {
        return nativeGetKeyNameFromKeyCode(i);
    }

    public static int getMaxTextureSize(CoronaRuntime coronaRuntime) {
        if (coronaRuntime == null || coronaRuntime.wasDisposed()) {
            return 0;
        }
        return nativeGetMaxTextureSize(coronaRuntime.getJavaToNativeBridgeAddress());
    }

    public static String getVersion() {
        return nativeGetVersion();
    }

    public static int getVerticalMarginInPixels(CoronaRuntime coronaRuntime) {
        if (coronaRuntime == null || coronaRuntime.wasDisposed()) {
            return 0;
        }
        return nativeGetVerticalMarginInPixels(coronaRuntime.getJavaToNativeBridgeAddress());
    }

    public static void gyroscopeEvent(CoronaRuntime coronaRuntime, double d, double d2, double d3, double d4) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeGyroscopeEvent(coronaRuntime.getJavaToNativeBridgeAddress(), d, d2, d3, d4);
        }
    }

    public static void imagePickerEvent(CoronaRuntime coronaRuntime, String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeImagePickerEvent(coronaRuntime.getJavaToNativeBridgeAddress(), str);
        }
    }

    public static void init(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null) {
            coronaRuntime.setJavaToNativeBridgeAddress(nativeInit(coronaRuntime));
        }
    }

    public static void inputDeviceStatusEvent(CoronaRuntime coronaRuntime, InputDeviceInterface inputDeviceInterface, InputDeviceStatusEventInfo inputDeviceStatusEventInfo) {
        if (inputDeviceInterface != null && inputDeviceStatusEventInfo != null && coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeInputDeviceStatusEvent(coronaRuntime.getJavaToNativeBridgeAddress(), inputDeviceInterface.getCoronaDeviceId(), inputDeviceStatusEventInfo.hasConnectionStateChanged(), inputDeviceStatusEventInfo.wasReconfigured());
        }
    }

    public static boolean keyEvent(CoronaRuntime coronaRuntime, InputDeviceInterface inputDeviceInterface, KeyPhase keyPhase, int i, boolean z, boolean z2, boolean z3) {
        int i2 = 0;
        if (coronaRuntime == null || coronaRuntime.wasDisposed()) {
            return false;
        }
        if (inputDeviceInterface != null) {
            i2 = inputDeviceInterface.getCoronaDeviceId();
        }
        return nativeKeyEvent(coronaRuntime.getJavaToNativeBridgeAddress(), i2, keyPhase.toCoronaNumericId(), i, z, z2, z3);
    }

    public static void locationEvent(CoronaRuntime coronaRuntime, double d, double d2, double d3, double d4, double d5, double d6, double d7) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeLocationEvent(coronaRuntime.getJavaToNativeBridgeAddress(), d, d2, d3, d4, d5, d6, d7);
        }
    }

    public static void mapAddressReceivedEvent(CoronaRuntime coronaRuntime, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeMapAddressReceivedEvent(coronaRuntime.getJavaToNativeBridgeAddress(), str, str2, str3, str4, str5, str6, str7, str8, str9);
        }
    }

    public static void mapAddressRequestFailedEvent(CoronaRuntime coronaRuntime, String str) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeMapAddressRequestFailedEvent(coronaRuntime.getJavaToNativeBridgeAddress(), str);
        }
    }

    public static void mapMarkerEvent(CoronaRuntime coronaRuntime, int i, int i2, double d, double d2) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeMapMarkerEvent(coronaRuntime.getJavaToNativeBridgeAddress(), i, i2, d, d2);
        }
    }

    public static void mapRequestLocationEvent(CoronaRuntime coronaRuntime, int i, double d, double d2, String str) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeMapRequestLocationEvent(coronaRuntime.getJavaToNativeBridgeAddress(), i, d, d2, str);
        }
    }

    public static void mapRequestLocationFailed(CoronaRuntime coronaRuntime, int i, String str, String str2) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeMapRequestLocationFailedEvent(coronaRuntime.getJavaToNativeBridgeAddress(), i, str, str2);
        }
    }

    public static void mapTappedEvent(CoronaRuntime coronaRuntime, int i, double d, double d2) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeMapTappedEvent(coronaRuntime.getJavaToNativeBridgeAddress(), i, d, d2);
        }
    }

    public static void memoryWarningEvent(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeMemoryWarningEvent(coronaRuntime.getJavaToNativeBridgeAddress());
        }
    }

    public static void mouseEvent(CoronaRuntime coronaRuntime, int i, int i2, long j, boolean z, boolean z2, boolean z3) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeMouseEvent(coronaRuntime.getJavaToNativeBridgeAddress(), i, i2, j, z, z2, z3);
        }
    }

    public static void multitouchEventAdd(CoronaRuntime coronaRuntime, TouchTracker touchTracker) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeMultitouchEventAdd(coronaRuntime.getJavaToNativeBridgeAddress(), (int) touchTracker.getLastPoint().getX(), (int) touchTracker.getLastPoint().getY(), (int) touchTracker.getStartPoint().getX(), (int) touchTracker.getStartPoint().getY(), touchTracker.getPhase().toCoronaNumericId(), touchTracker.getLastPoint().getTimestamp(), touchTracker.getTouchId());
        }
    }

    public static void multitouchEventBegin(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeMultitouchEventBegin(coronaRuntime.getJavaToNativeBridgeAddress());
        }
    }

    public static void multitouchEventEnd(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeMultitouchEventEnd(coronaRuntime.getJavaToNativeBridgeAddress());
        }
    }

    private static native void nativeAccelerometerEvent(long j, double d, double d2, double d3, double d4);

    private static native void nativeAddInputDeviceAxis(long j, int i, int i2, float f, float f2, float f3, boolean z);

    private static native void nativeAlertCallback(long j, int i, boolean z);

    private static native void nativeApplicationOpenEvent(long j);

    private static native void nativeAxisEvent(long j, int i, int i2, float f);

    private static native void nativeClearInputDeviceAxes(long j, int i);

    private static native Object nativeConvertCoronaPointToAndroidPoint(long j, int i, int i2);

    private static native boolean nativeCopyBitmap(long j, long j2, Bitmap bitmap, float f, int i, boolean z);

    private static native boolean nativeCopyBitmapInfo(long j, long j2, int i, int i2, float f, int i3);

    private static native void nativeDispatchEventInLua(long j);

    private static native void nativeDone(long j);

    private static native int nativeGetContentHeightInPixels(long j);

    private static native int nativeGetContentWidthInPixels(long j);

    private static native Object nativeGetCoronaRuntime(long j);

    private static native int nativeGetHorizontalMarginInPixels(long j);

    private static native String nativeGetKeyNameFromKeyCode(int i);

    private static native int nativeGetMaxTextureSize(long j);

    private static native String nativeGetVersion();

    private static native int nativeGetVerticalMarginInPixels(long j);

    private static native void nativeGyroscopeEvent(long j, double d, double d2, double d3, double d4);

    private static native void nativeImagePickerEvent(long j, String str);

    private static native long nativeInit(CoronaRuntime coronaRuntime);

    private static native void nativeInputDeviceStatusEvent(long j, int i, boolean z, boolean z2);

    private static native boolean nativeKeyEvent(long j, int i, int i2, int i3, boolean z, boolean z2, boolean z3);

    private static native void nativeLocationEvent(long j, double d, double d2, double d3, double d4, double d5, double d6, double d7);

    private static native void nativeMapAddressReceivedEvent(long j, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9);

    private static native void nativeMapAddressRequestFailedEvent(long j, String str);

    private static native void nativeMapMarkerEvent(long j, int i, int i2, double d, double d2);

    private static native void nativeMapRequestLocationEvent(long j, int i, double d, double d2, String str);

    private static native void nativeMapRequestLocationFailedEvent(long j, int i, String str, String str2);

    private static native void nativeMapTappedEvent(long j, int i, double d, double d2);

    private static native void nativeMemoryWarningEvent(long j);

    private static native void nativeMouseEvent(long j, int i, int i2, long j2, boolean z, boolean z2, boolean z3);

    private static native void nativeMultitouchEventAdd(long j, int i, int i2, int i3, int i4, int i5, long j2, int i6);

    private static native void nativeMultitouchEventBegin(long j);

    private static native void nativeMultitouchEventEnd(long j);

    private static native void nativeOrientationChanged(long j, int i, int i2);

    private static native void nativePause(long j);

    private static native void nativePopupClosedEvent(long j, String str, boolean z);

    private static native void nativeRecordCallback(long j, int i, int i2);

    private static native void nativeReinitializeRenderer(long j);

    private static native void nativeRender(long j);

    private static native void nativeResize(long j, String str, String str2, String str3, String str4, String str5, String str6, int i, int i2, int i3, boolean z);

    private static native void nativeResizeEvent(long j);

    private static native void nativeResume(long j);

    private static native void nativeSetZipFileEntryInfo(long j, String str, String str2, long j2, long j3, boolean z);

    private static native void nativeSoundEndCallback(long j, int i);

    private static native void nativeStoreTransactionEvent(long j, int i, int i2, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9);

    private static native void nativeTapEvent(long j, int i, int i2, int i3);

    private static native void nativeTextEditingEvent(long j, int i, int i2, int i3, String str, String str2, String str3);

    private static native void nativeTextEvent(long j, int i, boolean z, boolean z2);

    private static native void nativeTouchEvent(long j, int i, int i2, int i3, int i4, int i5, long j2, int i6);

    private static native void nativeUnloadResources(long j);

    private static native void nativeUpdateInputDevice(long j, int i, int i2, int i3, String str, String str2, String str3, boolean z, int i4, int i5);

    private static native void nativeUseDefaultLuaErrorHandler();

    private static native void nativeUseJavaLuaErrorHandler();

    private static native void nativeVideoEndCallback(long j, int i);

    private static native void nativeVideoPickerEvent(long j, String str, int i, long j2);

    private static native void nativeVideoViewEnded(long j, int i);

    private static native void nativeVideoViewPrepared(long j, int i);

    private static native void nativeWebViewClosed(long j, int i);

    private static native void nativeWebViewDidFailLoadUrl(long j, int i, String str, String str2, int i2);

    private static native void nativeWebViewFinishedLoadUrl(long j, int i, String str);

    private static native void nativeWebViewHistoryUpdated(long j, int i, boolean z, boolean z2);

    private static native void nativeWebViewShouldLoadUrl(long j, int i, String str, int i2);

    public static void orientationChanged(CoronaRuntime coronaRuntime, int i, int i2) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeOrientationChanged(coronaRuntime.getJavaToNativeBridgeAddress(), i, i2);
        }
    }

    public static void pause(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativePause(coronaRuntime.getJavaToNativeBridgeAddress());
        }
    }

    public static void popupClosedEvent(CoronaRuntime coronaRuntime, String str, boolean z) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativePopupClosedEvent(coronaRuntime.getJavaToNativeBridgeAddress(), str, z);
        }
    }

    public static void recordCallback(CoronaRuntime coronaRuntime, int i, int i2) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeRecordCallback(coronaRuntime.getJavaToNativeBridgeAddress(), i, i2);
        }
    }

    public static void reinitializeRenderer(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeReinitializeRenderer(coronaRuntime.getJavaToNativeBridgeAddress());
        }
    }

    public static void render(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeRender(coronaRuntime.getJavaToNativeBridgeAddress());
        }
    }

    public static void resize(CoronaRuntime coronaRuntime, Context context, int i, int i2, WindowOrientation windowOrientation, boolean z) {
        FileServices fileServices = new FileServices(context);
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeResize(coronaRuntime.getJavaToNativeBridgeAddress(), context.getPackageName(), CoronaEnvironment.getDocumentsDirectory(context).getAbsolutePath(), CoronaEnvironment.getTemporaryDirectory(context).getAbsolutePath(), CoronaEnvironment.getCachesDirectory(context).getAbsolutePath(), CoronaEnvironment.getInternalCachesDirectory(context).getAbsolutePath(), fileServices.getExpansionFileDirectory().getAbsolutePath(), i, i2, windowOrientation.toCoronaIntegerId(), z);
        }
    }

    public static void resizeEvent(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeResizeEvent(coronaRuntime.getJavaToNativeBridgeAddress());
        }
    }

    public static void resume(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeResume(coronaRuntime.getJavaToNativeBridgeAddress());
        }
    }

    public static void setZipFileEntryInfo(long j, AssetFileLocationInfo assetFileLocationInfo) {
        if (j != 0 && assetFileLocationInfo != null) {
            String str = null;
            if (assetFileLocationInfo.getPackageFile() != null) {
                str = assetFileLocationInfo.getPackageFile().getAbsolutePath();
            }
            nativeSetZipFileEntryInfo(j, str, assetFileLocationInfo.getZipEntryName(), assetFileLocationInfo.getByteOffsetInPackage(), assetFileLocationInfo.getByteCountInPackage(), assetFileLocationInfo.isCompressed());
        }
    }

    public static void soundEndCallback(CoronaRuntime coronaRuntime, int i) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeSoundEndCallback(coronaRuntime.getJavaToNativeBridgeAddress(), i);
        }
    }

    public static void storeTransactionEvent(CoronaRuntime coronaRuntime, StoreTransactionResultSettings storeTransactionResultSettings) {
        if (storeTransactionResultSettings != null && coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            String str = BuildConfig.FLAVOR;
            String str2 = BuildConfig.FLAVOR;
            if (storeTransactionResultSettings.hasTransactionTime()) {
                str = storeTransactionResultSettings.getTransactionTime().toString();
            }
            if (storeTransactionResultSettings.hasOriginalTransactionTime()) {
                str2 = storeTransactionResultSettings.getOriginalTransactionTime().toString();
            }
            nativeStoreTransactionEvent(coronaRuntime.getJavaToNativeBridgeAddress(), storeTransactionResultSettings.getState().toValue(), storeTransactionResultSettings.getErrorType().toValue(), storeTransactionResultSettings.getErrorMessage(), storeTransactionResultSettings.getProductName(), storeTransactionResultSettings.getSignature(), storeTransactionResultSettings.getReceipt(), storeTransactionResultSettings.getTransactionStringId(), str, storeTransactionResultSettings.getOriginalReceipt(), storeTransactionResultSettings.getOriginalTransactionStringId(), str2);
        }
    }

    public static void tapEvent(CoronaRuntime coronaRuntime, int i, int i2, int i3) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeTapEvent(coronaRuntime.getJavaToNativeBridgeAddress(), i, i2, i3);
        }
    }

    public static void textEditingEvent(CoronaRuntime coronaRuntime, int i, int i2, int i3, String str, String str2, String str3) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed() && coronaRuntime.getViewManager().hasDisplayObjectWithId(i)) {
            nativeTextEditingEvent(coronaRuntime.getJavaToNativeBridgeAddress(), i, i2, i3, str, str2, str3);
        }
    }

    public static void textEvent(CoronaRuntime coronaRuntime, int i, boolean z, boolean z2) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed() && coronaRuntime.getViewManager().hasDisplayObjectWithId(i)) {
            nativeTextEvent(coronaRuntime.getJavaToNativeBridgeAddress(), i, z, z2);
        }
    }

    public static void touchEvent(CoronaRuntime coronaRuntime, TouchTracker touchTracker) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeTouchEvent(coronaRuntime.getJavaToNativeBridgeAddress(), (int) touchTracker.getLastPoint().getX(), (int) touchTracker.getLastPoint().getY(), (int) touchTracker.getStartPoint().getX(), (int) touchTracker.getStartPoint().getY(), touchTracker.getPhase().toCoronaNumericId(), touchTracker.getLastPoint().getTimestamp(), touchTracker.getTouchId());
        }
    }

    public static void unloadResources(CoronaRuntime coronaRuntime) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeUnloadResources(coronaRuntime.getJavaToNativeBridgeAddress());
        }
    }

    public static void update(CoronaRuntime coronaRuntime, InputDeviceInterface inputDeviceInterface) {
        if (inputDeviceInterface != null && coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeUpdateInputDevice(coronaRuntime.getJavaToNativeBridgeAddress(), inputDeviceInterface.getCoronaDeviceId(), inputDeviceInterface.getDeviceInfo().getAndroidDeviceId(), inputDeviceInterface.getDeviceInfo().getType().toCoronaIntegerId(), inputDeviceInterface.getDeviceInfo().getPermanentStringId(), inputDeviceInterface.getDeviceInfo().getProductName(), inputDeviceInterface.getDeviceInfo().getDisplayName(), inputDeviceInterface.getDeviceInfo().canVibrate(), inputDeviceInterface.getDeviceInfo().getPlayerNumber(), inputDeviceInterface.getConnectionState().toCoronaIntegerId());
            nativeClearInputDeviceAxes(coronaRuntime.getJavaToNativeBridgeAddress(), inputDeviceInterface.getCoronaDeviceId());
            Iterator<AxisInfo> it = inputDeviceInterface.getDeviceInfo().getAxes().iterator();
            while (it.hasNext()) {
                AxisInfo next = it.next();
                if (next != null) {
                    nativeAddInputDeviceAxis(coronaRuntime.getJavaToNativeBridgeAddress(), inputDeviceInterface.getCoronaDeviceId(), next.getType().toCoronaIntegerId(), next.getMinValue(), next.getMaxValue(), next.getAccuracy(), next.isProvidingAbsoluteValues());
                }
            }
        }
    }

    public static void useDefaultLuaErrorHandler() {
        nativeUseDefaultLuaErrorHandler();
    }

    public static void useJavaLuaErrorHandler() {
        nativeUseJavaLuaErrorHandler();
    }

    public static void videoEndCallback(CoronaRuntime coronaRuntime, int i) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeVideoEndCallback(coronaRuntime.getJavaToNativeBridgeAddress(), i);
        }
    }

    public static void videoPickerEvent(CoronaRuntime coronaRuntime, String str, int i, long j) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            if (str == null) {
                str = BuildConfig.FLAVOR;
            }
            nativeVideoPickerEvent(coronaRuntime.getJavaToNativeBridgeAddress(), str, i / 1000, j);
        }
    }

    public static void videoViewEnded(CoronaRuntime coronaRuntime, int i) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeVideoViewEnded(coronaRuntime.getJavaToNativeBridgeAddress(), i);
        }
    }

    public static void videoViewPrepared(CoronaRuntime coronaRuntime, int i) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeVideoViewPrepared(coronaRuntime.getJavaToNativeBridgeAddress(), i);
        }
    }

    public static void webViewClosed(CoronaRuntime coronaRuntime, int i) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeWebViewClosed(coronaRuntime.getJavaToNativeBridgeAddress(), i);
        }
    }

    public static void webViewDidFailLoadUrl(CoronaRuntime coronaRuntime, int i, String str, String str2, int i2) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeWebViewDidFailLoadUrl(coronaRuntime.getJavaToNativeBridgeAddress(), i, str, str2, i2);
        }
    }

    public static void webViewFinishedLoadUrl(CoronaRuntime coronaRuntime, int i, String str) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeWebViewFinishedLoadUrl(coronaRuntime.getJavaToNativeBridgeAddress(), i, str);
        }
    }

    public static void webViewHistoryUpdated(CoronaRuntime coronaRuntime, int i, boolean z, boolean z2) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeWebViewHistoryUpdated(coronaRuntime.getJavaToNativeBridgeAddress(), i, z, z2);
        }
    }

    public static void webViewShouldLoadUrl(CoronaRuntime coronaRuntime, int i, String str, int i2) {
        if (coronaRuntime != null && !coronaRuntime.wasDisposed()) {
            nativeWebViewShouldLoadUrl(coronaRuntime.getJavaToNativeBridgeAddress(), i, str, i2);
        }
    }
}
