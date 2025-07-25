package com.ansca.corona;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.MailTo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import com.ansca.corona.events.AlertTask;
import com.ansca.corona.events.EventManager;
import com.ansca.corona.graphics.opengl.CoronaGLSurfaceView;
import com.ansca.corona.listeners.CoronaShowApiListener;
import com.ansca.corona.listeners.CoronaSplashScreenApiListener;
import com.ansca.corona.listeners.CoronaStatusBarApiListener;
import com.ansca.corona.listeners.CoronaStoreApiListener;
import com.ansca.corona.listeners.CoronaSystemApiListener;
import com.ansca.corona.storage.FileContentProvider;
import com.ansca.corona.storage.FileServices;
import com.ansca.corona.version.AndroidVersionSpecificFactory;
import com.ansca.corona.version.IAndroidVersionSpecific;
import com.tapjoy.TapjoyConstants;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import jp.stargarage.g2metrics.BuildConfig;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;

public class Controller {
    private static final int IMAGE_SOURCE_CAMERA = 1;
    private static final int IMAGE_SOURCE_PHOTO_LIBRARY = 0;
    private static final int IMAGE_SOURCE_SAVED_PHOTOS_ALBUM = 2;
    private static final int MEDIA_CAPTURE_QUALITY_HIGH = 2;
    private static final int MEDIA_CAPTURE_QUALITY_LOW = 0;
    private static final int MEDIA_CAPTURE_QUALITY_MEDIUM = 1;
    /* access modifiers changed from: private */
    public ActivityIndicatorDialog myActivityIndicatorDialog = null;
    /* access modifiers changed from: private */
    public AlertDialog myAlertDialog;
    private IAndroidVersionSpecific myAndroidVersion;
    private NativeToJavaBridge myBridge;
    /* access modifiers changed from: private */
    public Context myContext;
    private CoronaApiListener myCoronaApiListener;
    /* access modifiers changed from: private */
    public CoronaShowApiListener myCoronaShowApiListener;
    private CoronaSplashScreenApiListener myCoronaSplashScreenApiListener;
    private CoronaStatusBarApiListener myCoronaStatusBarApiListener;
    private CoronaStoreApiListener myCoronaStoreApiListener;
    private CoronaSystemApiListener myCoronaSystemApiListener;
    private float myDefaultFontSize;
    private int myDefaultTextFieldPaddingInPixels;
    private EventManager myEventManager;
    private CoronaGLSurfaceView myGLView;
    private Handler myHandler;
    private boolean myIdleEnabled;
    private boolean myInitialResume;
    private boolean myIsNaturalOrientationPortrait = true;
    private MediaManager myMediaManager;
    /* access modifiers changed from: private */
    public CoronaRuntime myRuntime;
    /* access modifiers changed from: private */
    public RuntimeState myRuntimeState;
    private CoronaSensorManager mySensorManager;
    private SystemMonitor mySystemMonitor;
    /* access modifiers changed from: private */
    public Handler myTimerHandler;
    /* access modifiers changed from: private */
    public int myTimerMilliseconds;
    private Runnable myTimerTask;

    private static class ApiLevel11 {
        private ApiLevel11() {
        }

        public static AlertDialog.Builder createAlertDialogBuilder(Context context) {
            return new AlertDialog.Builder(context, 2);
        }

        public static AlertDialog.Builder createAlertDialogBuilder(Context context, int i) {
            return new AlertDialog.Builder(context, i);
        }

        public static int getSystemUiVisibility(View view) {
            return view.getSystemUiVisibility();
        }

        public static void setSystemUiVisibility(View view, int i) {
            view.setSystemUiVisibility(i);
        }
    }

    private static class ApiLevel21 {
        private ApiLevel21() {
        }

        public static AlertDialog.Builder createAlertDialogBuilder(Context context) {
            return new AlertDialog.Builder(context, 16974394);
        }

        public static AlertDialog.Builder createAlertDialogBuilder(Context context, int i) {
            return new AlertDialog.Builder(context, i);
        }
    }

    enum RuntimeState {
        Starting,
        Running,
        Stopping,
        Stopped
    }

    Controller(Context context, CoronaRuntime coronaRuntime) {
        boolean z = true;
        this.myContext = context;
        this.myRuntime = coronaRuntime;
        this.myBridge = new NativeToJavaBridge(this.myContext);
        this.myMediaManager = new MediaManager(this.myRuntime, this.myContext);
        this.mySensorManager = new CoronaSensorManager(this.myRuntime);
        this.mySystemMonitor = new SystemMonitor(this.myRuntime, this.myContext);
        this.myHandler = new Handler(Looper.getMainLooper());
        this.myTimerHandler = new Handler(Looper.getMainLooper());
        this.myEventManager = new EventManager(this);
        Display defaultDisplay = ((WindowManager) this.myContext.getSystemService("window")).getDefaultDisplay();
        switch (defaultDisplay.getRotation()) {
            case 0:
            case 2:
                this.myIsNaturalOrientationPortrait = defaultDisplay.getWidth() < defaultDisplay.getHeight();
                break;
            case 1:
            case 3:
                this.myIsNaturalOrientationPortrait = defaultDisplay.getWidth() <= defaultDisplay.getHeight() ? false : z;
                break;
            default:
                this.myIsNaturalOrientationPortrait = true;
                break;
        }
        CoronaEditText coronaEditText = new CoronaEditText(context, coronaRuntime);
        this.myDefaultFontSize = coronaEditText.getTextSize();
        this.myDefaultTextFieldPaddingInPixels = (int) ((((double) ((coronaEditText.getPaddingTop() + coronaEditText.getPaddingBottom()) - (coronaEditText.getBorderPaddingTop() + coronaEditText.getBorderPaddingBottom()))) / 2.0d) + 0.5d);
    }

    /* access modifiers changed from: private */
    public AlertDialog.Builder createAlertDialogBuilder(Context context) {
        return Build.VERSION.SDK_INT >= 21 ? ApiLevel21.createAlertDialogBuilder(context) : Build.VERSION.SDK_INT >= 11 ? ApiLevel11.createAlertDialogBuilder(context) : new AlertDialog.Builder(context);
    }

    private void internalSetIdleTimer(boolean z) {
        if (this.myCoronaApiListener == null) {
            Log.d("Corona", "Controller.internalSetIdleTimer(): Can't set internal idle timer because our ApiListener is gone!");
        } else if (!z || Build.VERSION.SDK_INT == 22) {
            this.myCoronaApiListener.addKeepScreenOnFlag();
        } else {
            this.myCoronaApiListener.removeKeepScreenOnFlag();
        }
    }

    public static void updateRuntimeState(CoronaRuntime coronaRuntime, boolean z) {
        Controller controller = coronaRuntime.getController();
        EventManager eventManager = controller.getEventManager();
        if (controller != null && eventManager != null) {
            synchronized (controller) {
                if (RuntimeState.Starting == controller.myRuntimeState && z) {
                    JavaToNativeShim.resume(coronaRuntime);
                    controller.requestRender();
                    controller.myRuntimeState = RuntimeState.Running;
                }
                if (z) {
                    eventManager.sendEvents();
                }
                if (RuntimeState.Stopping == controller.myRuntimeState) {
                    JavaToNativeShim.pause(coronaRuntime);
                    controller.myRuntimeState = RuntimeState.Stopped;
                    controller.getHandler().post(new Runnable(controller) {
                        final /* synthetic */ Controller val$controller;

                        {
                            this.val$controller = r1;
                        }

                        public void run() {
                            if (RuntimeState.Stopped == this.val$controller.myRuntimeState) {
                                this.val$controller.getGLView().onPause();
                            }
                        }
                    });
                }
                if (RuntimeState.Running == controller.myRuntimeState && z) {
                    JavaToNativeShim.render(coronaRuntime);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public String GetHardwareIdentifier() {
        TelephonyManager telephonyManager;
        try {
            if (this.myContext.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0 || (telephonyManager = (TelephonyManager) this.myContext.getSystemService("phone")) == null || telephonyManager.getDeviceId() == null) {
                return null;
            }
            return telephonyManager.getDeviceId();
        } catch (Exception e) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public String GetOSIdentifier() {
        Context context = this.myContext;
        if (context == null) {
            return null;
        }
        return Settings.Secure.getString(context.getContentResolver(), TapjoyConstants.TJC_ANDROID_ID);
    }

    public void addImageFileToPhotoGallery(String str) {
        if (str != null && str.length() > 0) {
            MediaScannerConnection.scanFile(this.myContext, new String[]{str}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String str, Uri uri) {
                }
            });
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r5.myContext;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean canShowActivityFor(android.content.Intent r6) {
        /*
            r5 = this;
            r2 = 0
            if (r6 != 0) goto L_0x0004
        L_0x0003:
            return r2
        L_0x0004:
            android.content.Context r0 = r5.myContext
            if (r0 == 0) goto L_0x0003
            android.content.pm.PackageManager r3 = r0.getPackageManager()
            r4 = 65536(0x10000, float:9.18355E-41)
            java.util.List r1 = r3.queryIntentActivities(r6, r4)
            int r3 = r1.size()
            if (r3 <= 0) goto L_0x0003
            r2 = 1
            goto L_0x0003
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.Controller.canShowActivityFor(android.content.Intent):boolean");
    }

    public boolean canShowPopup(String str) {
        boolean z = false;
        Intent intent = null;
        if (this.myContext == null) {
            return false;
        }
        String lowerCase = str.toLowerCase();
        if (lowerCase.equals("mail")) {
            intent = new MailSettings().toIntent();
        } else if (lowerCase.equals("sms")) {
            intent = new SmsSettings().toIntent();
        } else if (lowerCase.equals("appstore") || lowerCase.equals("rateapp")) {
            z = true;
        }
        return (z || intent == null) ? z : canShowActivityFor(intent);
    }

    public void cancelNativeAlert(final int i) {
        final AlertDialog alertDialog;
        synchronized (this) {
            alertDialog = this.myAlertDialog;
            this.myAlertDialog = null;
        }
        if (alertDialog != null) {
            this.myHandler.post(new Runnable() {
                public void run() {
                    alertDialog.cancel();
                    if (Controller.this.myRuntime != null && Controller.this.myRuntime.isRunning()) {
                        Controller.this.myRuntime.getTaskDispatcher().send(new AlertTask(i, true));
                    }
                }
            });
        }
    }

    public void cancelTimer() {
        stopTimer();
        this.myTimerMilliseconds = 0;
    }

    public void closeNativeActivityIndicator() {
        if (this.myActivityIndicatorDialog != null) {
            this.myActivityIndicatorDialog.dismiss();
            this.myActivityIndicatorDialog = null;
        }
    }

    public synchronized void destroy() {
        cancelNativeAlert(-1);
        closeNativeActivityIndicator();
        stopTimer();
        this.mySensorManager.stop();
        this.myMediaManager.release();
        this.mySystemMonitor.stop();
        this.myEventManager.removeAllEvents();
        JavaToNativeShim.destroy(this.myRuntime);
    }

    public void displayUpdate() {
        if (this.myGLView != null) {
            this.myGLView.setNeedsSwap();
        }
    }

    public IAndroidVersionSpecific getAndroidVersionSpecific() {
        return this.myAndroidVersion;
    }

    public NativeToJavaBridge getBridge() {
        return this.myBridge;
    }

    public Context getContext() {
        return this.myContext;
    }

    /* access modifiers changed from: package-private */
    public CoronaApiListener getCoronaApiListener() {
        return this.myCoronaApiListener;
    }

    /* access modifiers changed from: package-private */
    public CoronaShowApiListener getCoronaShowApiListener() {
        return this.myCoronaShowApiListener;
    }

    /* access modifiers changed from: package-private */
    public CoronaSplashScreenApiListener getCoronaSplashScreenApiListener() {
        return this.myCoronaSplashScreenApiListener;
    }

    /* access modifiers changed from: package-private */
    public CoronaStatusBarApiListener getCoronaStatusBarApiListener() {
        return this.myCoronaStatusBarApiListener;
    }

    /* access modifiers changed from: package-private */
    public CoronaStoreApiListener getCoronaStoreApiListener() {
        return this.myCoronaStoreApiListener;
    }

    /* access modifiers changed from: package-private */
    public CoronaSystemApiListener getCoronaSystemApiListener() {
        return this.myCoronaSystemApiListener;
    }

    public float getDefaultFontSize() {
        return this.myDefaultFontSize;
    }

    public int getDefaultTextFieldPaddingInPixels() {
        return this.myDefaultTextFieldPaddingInPixels;
    }

    public EventManager getEventManager() {
        return this.myEventManager;
    }

    public CoronaGLSurfaceView getGLView() {
        return this.myGLView;
    }

    public Handler getHandler() {
        return this.myHandler;
    }

    public boolean getIdleTimer() {
        return this.myIdleEnabled;
    }

    public String getManufacturerName() {
        return Build.MANUFACTURER;
    }

    public MediaManager getMediaManager() {
        return this.myMediaManager;
    }

    public String getModel() {
        return Build.MODEL;
    }

    public String getName() {
        return "unknown";
    }

    public String getPlatformVersion() {
        return Build.VERSION.RELEASE;
    }

    public String getProductName() {
        return Build.PRODUCT;
    }

    public SystemMonitor getSystemMonitor() {
        return this.mySystemMonitor;
    }

    /* access modifiers changed from: package-private */
    public String getSystemUiVisibility() {
        CoronaGLSurfaceView coronaGLSurfaceView = this.myGLView;
        if (coronaGLSurfaceView == null || Build.VERSION.SDK_INT < 14 || Build.MANUFACTURER.equals("Amazon") || Build.MANUFACTURER.equals("BN LLC")) {
            return BuildConfig.FLAVOR;
        }
        int systemUiVisibility = ApiLevel11.getSystemUiVisibility(coronaGLSurfaceView);
        return (systemUiVisibility & 4102) == 4102 ? "immersiveSticky" : (systemUiVisibility & 2054) == 2054 ? "immersive" : (systemUiVisibility & 1) == 1 ? "lowProfile" : (systemUiVisibility == 0 || (systemUiVisibility & 2048) == 2048) ? AdNetworkKey.DEFAULT : "unknown";
    }

    public String getUniqueIdentifier(int i) {
        String str = null;
        switch (i) {
            case 0:
                String GetHardwareIdentifier = GetHardwareIdentifier();
                if (GetHardwareIdentifier != null && GetHardwareIdentifier.length() > 0) {
                    str = GetHardwareIdentifier;
                }
                if (str == null) {
                    str = GetOSIdentifier();
                    break;
                }
                break;
            case 1:
                str = GetHardwareIdentifier();
                break;
            case 2:
                str = GetOSIdentifier();
                break;
        }
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public boolean hasAccelerometer() {
        return this.mySensorManager.hasAccelerometer();
    }

    public boolean hasGyroscope() {
        return this.mySensorManager.hasGyroscope();
    }

    public boolean hasMediaSource(int i) {
        switch (i) {
            case 0:
            case 2:
                return true;
            case 1:
                return CameraServices.hasCamera() && CameraServices.hasPermission();
            default:
                return false;
        }
    }

    public void httpPost(String str, String str2, String str3) {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        BasicHttpContext basicHttpContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(str);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        try {
            httpPost.setEntity(new StringEntity(str2 + "=" + str3, "UTF-8"));
            defaultHttpClient.execute(httpPost, basicHttpContext);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void init() {
        boolean z = true;
        synchronized (this) {
            this.myMediaManager.init();
            this.mySensorManager.init();
            this.mySystemMonitor.start();
            this.myTimerMilliseconds = 0;
            this.myInitialResume = true;
            this.myRuntimeState = RuntimeState.Stopped;
            this.myAndroidVersion = AndroidVersionSpecificFactory.create();
            if (Build.VERSION.SDK_INT == 22) {
                z = false;
            }
            this.myIdleEnabled = z;
        }
    }

    public boolean isMultitouchEnabled() {
        return getAndroidVersionSpecific().apiVersion() >= 5 && this.mySensorManager.isActive(5);
    }

    public boolean isNaturalOrientationPortrait() {
        return this.myIsNaturalOrientationPortrait;
    }

    public boolean isRunning() {
        return RuntimeState.Running == this.myRuntimeState || RuntimeState.Stopping == this.myRuntimeState;
    }

    public boolean openUrl(String str) {
        Context context;
        Intent intent;
        if (str == null || str.length() <= 0 || (context = this.myContext) == null) {
            return false;
        }
        Uri parse = Uri.parse(str);
        String str2 = "/data/data/" + context.getPackageName();
        FileServices fileServices = new FileServices(context);
        if (str.indexOf(str2) >= 0 || str.startsWith("content://") || fileServices.doesAssetFileExist(str)) {
            if (parse.getScheme() == null || !parse.getScheme().equals("content")) {
                parse = FileContentProvider.createContentUriForFile(context, str);
            }
            String str3 = null;
            int lastIndexOf = str.lastIndexOf(46);
            if (lastIndexOf >= 0 && lastIndexOf + 1 < str.length()) {
                String substring = str.substring(lastIndexOf + 1);
                MimeTypeMap singleton = MimeTypeMap.getSingleton();
                if (singleton.hasExtension(substring)) {
                    str3 = singleton.getMimeTypeFromExtension(substring);
                }
                if (str3 == null) {
                    str3 = "application/" + substring;
                }
            }
            intent = new Intent("android.intent.action.VIEW", parse);
            if (str3 != null) {
                intent.setDataAndType(parse, str3);
            }
        } else if (MailTo.isMailTo(str)) {
            intent = Intent.createChooser(MailSettings.fromUrl(str).toIntent(), "Send mail...");
        } else {
            String str4 = "android.intent.action.VIEW";
            if (parse.getScheme() == null) {
                if (!str.startsWith("http://")) {
                    parse = Uri.parse("http://" + str);
                }
            } else if (parse.getScheme().equals("tel")) {
                str4 = "android.intent.action.CALL";
            }
            intent = new Intent(str4, parse);
        }
        if (intent == null) {
            return false;
        }
        try {
            if (!canShowActivityFor(intent)) {
                return false;
            }
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void requestEventRender() {
        if (this.myTimerTask == null) {
            this.mySystemMonitor.update();
            if (this.myGLView != null) {
                this.myGLView.requestRender();
            }
        }
    }

    public void requestRender() {
        if (this.myContext != null) {
            this.mySystemMonitor.update();
            if (this.myGLView != null) {
                this.myGLView.requestRender();
            }
        }
    }

    public boolean saveBitmap(Bitmap bitmap, int i, String str) {
        boolean z = false;
        if (bitmap == null || str == null || str.length() <= 0) {
            return false;
        }
        Bitmap.CompressFormat compressFormat = str.toLowerCase().endsWith(".png") ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            z = bitmap.compress(compressFormat, i, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }

    public boolean saveBitmap(Bitmap bitmap, Uri uri) {
        Bitmap.CompressFormat compressFormat;
        int i;
        boolean z = false;
        if (this.myContext == null || bitmap == null || uri == null) {
            return false;
        }
        String type = this.myContext.getContentResolver().getType(uri);
        if (type == null || !type.toLowerCase().endsWith("png")) {
            compressFormat = Bitmap.CompressFormat.JPEG;
            i = 90;
        } else {
            compressFormat = Bitmap.CompressFormat.PNG;
            i = 100;
        }
        try {
            OutputStream openOutputStream = this.myContext.getContentResolver().openOutputStream(uri);
            z = bitmap.compress(compressFormat, i, openOutputStream);
            openOutputStream.flush();
            openOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }

    public void setAccelerometerInterval(int i) {
        this.mySensorManager.setAccelerometerInterval(i);
    }

    /* access modifiers changed from: package-private */
    public void setCoronaApiListener(CoronaApiListener coronaApiListener) {
        this.myCoronaApiListener = coronaApiListener;
        this.mySystemMonitor.setCoronaApiListener(coronaApiListener);
    }

    /* access modifiers changed from: package-private */
    public void setCoronaShowApiListener(CoronaShowApiListener coronaShowApiListener) {
        this.myCoronaShowApiListener = coronaShowApiListener;
    }

    /* access modifiers changed from: package-private */
    public void setCoronaSplashScreenApiListener(CoronaSplashScreenApiListener coronaSplashScreenApiListener) {
        this.myCoronaSplashScreenApiListener = coronaSplashScreenApiListener;
    }

    /* access modifiers changed from: package-private */
    public void setCoronaStatusBarApiListener(CoronaStatusBarApiListener coronaStatusBarApiListener) {
        this.myCoronaStatusBarApiListener = coronaStatusBarApiListener;
    }

    /* access modifiers changed from: package-private */
    public void setCoronaStoreApiListener(CoronaStoreApiListener coronaStoreApiListener) {
        this.myCoronaStoreApiListener = coronaStoreApiListener;
    }

    /* access modifiers changed from: package-private */
    public void setCoronaSystemApiListener(CoronaSystemApiListener coronaSystemApiListener) {
        this.myCoronaSystemApiListener = coronaSystemApiListener;
    }

    public void setEventNotification(int i, boolean z) {
        this.mySensorManager.setEventNotification(i, z);
    }

    /* access modifiers changed from: package-private */
    public void setGLView(CoronaGLSurfaceView coronaGLSurfaceView) {
        this.myGLView = coronaGLSurfaceView;
    }

    public void setGyroscopeInterval(int i) {
        this.mySensorManager.setGyroscopeInterval(i);
    }

    public void setIdleTimer(boolean z) {
        internalSetIdleTimer(z);
        if (Build.VERSION.SDK_INT == 22) {
            this.myIdleEnabled = false;
        } else {
            this.myIdleEnabled = z;
        }
    }

    public void setLocationThreshold(double d) {
        this.mySensorManager.setLocationThreshold(d);
    }

    /* access modifiers changed from: package-private */
    public void setSystemUiVisibility(final String str) {
        final CoronaGLSurfaceView coronaGLSurfaceView = this.myGLView;
        if (coronaGLSurfaceView != null && Build.VERSION.SDK_INT >= 14 && !Build.MANUFACTURER.equals("Amazon") && !Build.MANUFACTURER.equals("BN LLC")) {
            this.myHandler.post(new Runnable() {
                public void run() {
                    int i = -1;
                    if (str.equals("immersiveSticky") && Build.VERSION.SDK_INT >= 19) {
                        i = 4102;
                    } else if (str.equals("immersive") && Build.VERSION.SDK_INT >= 19) {
                        i = 2054;
                    } else if (str.equals("lowProfile")) {
                        i = 1;
                    } else if (str.equals(AdNetworkKey.DEFAULT)) {
                        i = 0;
                    }
                    if (i > -1) {
                        ApiLevel11.setSystemUiVisibility(coronaGLSurfaceView, i);
                    }
                }
            });
        }
    }

    public void setTimer(int i) {
        this.myTimerMilliseconds = i;
        startTimer();
    }

    public boolean showAppStoreWindow(HashMap<String, Object> hashMap) {
        if (this.myCoronaShowApiListener == null) {
            return false;
        }
        return this.myCoronaShowApiListener.showAppStoreWindow(hashMap);
    }

    public void showImagePickerWindow(final int i, final String str) {
        this.myHandler.post(new Runnable() {
            /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
                return;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r2 = this;
                    monitor-enter(r2)
                    com.ansca.corona.Controller r0 = com.ansca.corona.Controller.this     // Catch:{ all -> 0x0019 }
                    com.ansca.corona.listeners.CoronaShowApiListener r0 = r0.myCoronaShowApiListener     // Catch:{ all -> 0x0019 }
                    if (r0 != 0) goto L_0x000b
                    monitor-exit(r2)     // Catch:{ all -> 0x0019 }
                L_0x000a:
                    return
                L_0x000b:
                    int r0 = r3     // Catch:{ all -> 0x0019 }
                    switch(r0) {
                        case 0: goto L_0x001c;
                        case 1: goto L_0x0029;
                        case 2: goto L_0x001c;
                        default: goto L_0x0010;
                    }     // Catch:{ all -> 0x0019 }
                L_0x0010:
                    java.lang.String r0 = "Corona"
                    java.lang.String r1 = "The given image source is not supported."
                    android.util.Log.v(r0, r1)     // Catch:{ all -> 0x0019 }
                    monitor-exit(r2)     // Catch:{ all -> 0x0019 }
                    goto L_0x000a
                L_0x0019:
                    r0 = move-exception
                    monitor-exit(r2)     // Catch:{ all -> 0x0019 }
                    throw r0
                L_0x001c:
                    com.ansca.corona.Controller r0 = com.ansca.corona.Controller.this     // Catch:{ all -> 0x0019 }
                    com.ansca.corona.listeners.CoronaShowApiListener r0 = r0.myCoronaShowApiListener     // Catch:{ all -> 0x0019 }
                    java.lang.String r1 = r4     // Catch:{ all -> 0x0019 }
                    r0.showSelectImageWindowUsing(r1)     // Catch:{ all -> 0x0019 }
                L_0x0027:
                    monitor-exit(r2)     // Catch:{ all -> 0x0019 }
                    goto L_0x000a
                L_0x0029:
                    com.ansca.corona.Controller r0 = com.ansca.corona.Controller.this     // Catch:{ all -> 0x0019 }
                    com.ansca.corona.listeners.CoronaShowApiListener r0 = r0.myCoronaShowApiListener     // Catch:{ all -> 0x0019 }
                    java.lang.String r1 = r4     // Catch:{ all -> 0x0019 }
                    r0.showCameraWindowForImage(r1)     // Catch:{ all -> 0x0019 }
                    goto L_0x0027
                */
                throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.Controller.AnonymousClass9.run():void");
            }
        });
    }

    public void showNativeActivityIndicator() {
        this.myHandler.post(new Runnable() {
            /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
                return;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r6 = this;
                    com.ansca.corona.Controller r4 = com.ansca.corona.Controller.this
                    monitor-enter(r4)
                    com.ansca.corona.Controller r3 = com.ansca.corona.Controller.this     // Catch:{ all -> 0x004e }
                    android.content.Context r0 = r3.myContext     // Catch:{ all -> 0x004e }
                    if (r0 != 0) goto L_0x000d
                    monitor-exit(r4)     // Catch:{ all -> 0x004e }
                L_0x000c:
                    return
                L_0x000d:
                    com.ansca.corona.Controller r3 = com.ansca.corona.Controller.this     // Catch:{ all -> 0x004e }
                    com.ansca.corona.ActivityIndicatorDialog r3 = r3.myActivityIndicatorDialog     // Catch:{ all -> 0x004e }
                    if (r3 != 0) goto L_0x0037
                    int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x004e }
                    r5 = 21
                    if (r3 < r5) goto L_0x0051
                    r2 = 16974393(0x1030239, float:2.4062495E-38)
                L_0x001e:
                    android.view.ContextThemeWrapper r1 = new android.view.ContextThemeWrapper     // Catch:{ all -> 0x004e }
                    r1.<init>(r0, r2)     // Catch:{ all -> 0x004e }
                    com.ansca.corona.Controller r3 = com.ansca.corona.Controller.this     // Catch:{ all -> 0x004e }
                    com.ansca.corona.ActivityIndicatorDialog r5 = new com.ansca.corona.ActivityIndicatorDialog     // Catch:{ all -> 0x004e }
                    r5.<init>(r1)     // Catch:{ all -> 0x004e }
                    com.ansca.corona.ActivityIndicatorDialog unused = r3.myActivityIndicatorDialog = r5     // Catch:{ all -> 0x004e }
                    com.ansca.corona.Controller r3 = com.ansca.corona.Controller.this     // Catch:{ all -> 0x004e }
                    com.ansca.corona.ActivityIndicatorDialog r3 = r3.myActivityIndicatorDialog     // Catch:{ all -> 0x004e }
                    r5 = 0
                    r3.setCancelable(r5)     // Catch:{ all -> 0x004e }
                L_0x0037:
                    com.ansca.corona.Controller r3 = com.ansca.corona.Controller.this     // Catch:{ all -> 0x004e }
                    com.ansca.corona.ActivityIndicatorDialog r3 = r3.myActivityIndicatorDialog     // Catch:{ all -> 0x004e }
                    boolean r3 = r3.isShowing()     // Catch:{ all -> 0x004e }
                    if (r3 != 0) goto L_0x004c
                    com.ansca.corona.Controller r3 = com.ansca.corona.Controller.this     // Catch:{ all -> 0x004e }
                    com.ansca.corona.ActivityIndicatorDialog r3 = r3.myActivityIndicatorDialog     // Catch:{ all -> 0x004e }
                    r3.show()     // Catch:{ all -> 0x004e }
                L_0x004c:
                    monitor-exit(r4)     // Catch:{ all -> 0x004e }
                    goto L_0x000c
                L_0x004e:
                    r3 = move-exception
                    monitor-exit(r4)     // Catch:{ all -> 0x004e }
                    throw r3
                L_0x0051:
                    int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x004e }
                    r5 = 11
                    if (r3 < r5) goto L_0x005b
                    r2 = 16973935(0x103006f, float:2.406121E-38)
                    goto L_0x001e
                L_0x005b:
                    r2 = 16973835(0x103000b, float:2.406093E-38)
                    goto L_0x001e
                */
                throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.Controller.AnonymousClass8.run():void");
            }
        });
    }

    public void showNativeAlert(final String str, final String str2, final String[] strArr) {
        synchronized (this) {
            if (this.myAlertDialog == null) {
                this.myHandler.post(new Runnable() {
                    public void run() {
                        AlertDialog.Builder access$400 = Controller.this.createAlertDialogBuilder(Controller.this.myContext);
                        AnonymousClass1 r3 = new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int i2 = i;
                                if (i < 0) {
                                    switch (i) {
                                        case -3:
                                            i2 = 1;
                                            break;
                                        case -2:
                                            i2 = 2;
                                            break;
                                        case -1:
                                            i2 = 0;
                                            break;
                                    }
                                }
                                if (Controller.this.myRuntime != null && Controller.this.myRuntime.isRunning()) {
                                    Controller.this.myRuntime.getTaskDispatcher().send(new AlertTask(i2, false));
                                }
                                synchronized (Controller.this) {
                                    AlertDialog unused = Controller.this.myAlertDialog = null;
                                }
                            }
                        };
                        AnonymousClass2 r2 = new DialogInterface.OnCancelListener() {
                            public void onCancel(DialogInterface dialogInterface) {
                                if (Controller.this.myRuntime != null && Controller.this.myRuntime.isRunning()) {
                                    Controller.this.myRuntime.getTaskDispatcher().send(new AlertTask(-1, true));
                                }
                                synchronized (Controller.this) {
                                    AlertDialog unused = Controller.this.myAlertDialog = null;
                                }
                            }
                        };
                        int length = strArr != null ? strArr.length : 0;
                        if (length <= 0) {
                            access$400.setTitle(str);
                            access$400.setMessage(str2);
                        } else if (length <= 3) {
                            access$400.setTitle(str);
                            access$400.setMessage(str2);
                            access$400.setPositiveButton(strArr[0], r3);
                            if (length > 1) {
                                access$400.setNeutralButton(strArr[1], r3);
                            }
                            if (length > 2) {
                                access$400.setNegativeButton(strArr[2], r3);
                            }
                        } else {
                            access$400.setTitle(str + ": " + str2);
                            access$400.setItems(strArr, r3);
                        }
                        access$400.setOnCancelListener(r2);
                        synchronized (Controller.this) {
                            AlertDialog unused = Controller.this.myAlertDialog = access$400.create();
                            Controller.this.myAlertDialog.setCanceledOnTouchOutside(false);
                            Controller.this.myAlertDialog.show();
                        }
                    }
                });
            }
        }
    }

    public void showSendMailWindow(HashMap<String, Object> hashMap) {
        final MailSettings from = MailSettings.from(this.myContext, hashMap);
        this.myHandler.post(new Runnable() {
            public void run() {
                if (Controller.this.myCoronaShowApiListener != null) {
                    synchronized (this) {
                        Controller.this.myCoronaShowApiListener.showSendMailWindowUsing(from);
                    }
                }
            }
        });
    }

    public void showSendSmsWindow(HashMap<String, Object> hashMap) {
        final SmsSettings from = SmsSettings.from(hashMap);
        this.myHandler.post(new Runnable() {
            public void run() {
                if (Controller.this.myCoronaShowApiListener != null) {
                    synchronized (this) {
                        Controller.this.myCoronaShowApiListener.showSendSmsWindowUsing(from);
                    }
                }
            }
        });
    }

    public void showStoreDeprecatedAlert() {
        this.myHandler.post(new Runnable() {
            public void run() {
                Context access$300 = Controller.this.myContext;
                if (access$300 != null) {
                    AlertDialog.Builder access$400 = Controller.this.createAlertDialogBuilder(access$300);
                    AnonymousClass1 r1 = new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Controller.this.openUrl("https://docs.coronalabs.com/guide/monetization/IAP/index.html#google-play-setup");
                        }
                    };
                    access$400.setTitle("store.* library removed on Android");
                    access$400.setMessage("Due to Google removing In-App Billing Version 2 in January 2015, the Corona store.* library on Android is no longer active.\n\nPlease migrate to the Google IAP V3 plugin.\n\nSee our IAP Guide for more info.");
                    access$400.setPositiveButton("OK", (DialogInterface.OnClickListener) null);
                    access$400.setNeutralButton("Learn More", r1);
                    access$400.show();
                }
            }
        });
    }

    public void showTrialAlert() {
        this.myHandler.post(new Runnable() {
            public void run() {
                Context access$300 = Controller.this.myContext;
                if (access$300 != null) {
                    AlertDialog.Builder access$400 = Controller.this.createAlertDialogBuilder(access$300);
                    AnonymousClass1 r1 = new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Controller.this.openUrl("http://www.coronalabs.com/products/corona-sdk/?utm_source=corona-sdk&utm_medium=corona-sdk&utm_campaign=trial-popup");
                        }
                    };
                    access$400.setTitle("Corona SDK Trial");
                    access$400.setMessage("This message only appears in the trial version");
                    access$400.setPositiveButton("OK", (DialogInterface.OnClickListener) null);
                    access$400.setNeutralButton("Learn More", r1);
                    access$400.show();
                }
            }
        });
    }

    public void showVideoPickerWindow(final int i, final int i2, final int i3) {
        this.myHandler.post(new Runnable() {
            /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
                return;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r4 = this;
                    r3 = 1
                    monitor-enter(r4)
                    com.ansca.corona.Controller r2 = com.ansca.corona.Controller.this     // Catch:{ all -> 0x001a }
                    com.ansca.corona.listeners.CoronaShowApiListener r2 = r2.myCoronaShowApiListener     // Catch:{ all -> 0x001a }
                    if (r2 != 0) goto L_0x000c
                    monitor-exit(r4)     // Catch:{ all -> 0x001a }
                L_0x000b:
                    return
                L_0x000c:
                    int r2 = r3     // Catch:{ all -> 0x001a }
                    switch(r2) {
                        case 0: goto L_0x001d;
                        case 1: goto L_0x0028;
                        case 2: goto L_0x001d;
                        default: goto L_0x0011;
                    }     // Catch:{ all -> 0x001a }
                L_0x0011:
                    java.lang.String r2 = "Corona"
                    java.lang.String r3 = "The given video source is not supported."
                    android.util.Log.v(r2, r3)     // Catch:{ all -> 0x001a }
                    monitor-exit(r4)     // Catch:{ all -> 0x001a }
                    goto L_0x000b
                L_0x001a:
                    r2 = move-exception
                    monitor-exit(r4)     // Catch:{ all -> 0x001a }
                    throw r2
                L_0x001d:
                    com.ansca.corona.Controller r2 = com.ansca.corona.Controller.this     // Catch:{ all -> 0x001a }
                    com.ansca.corona.listeners.CoronaShowApiListener r2 = r2.myCoronaShowApiListener     // Catch:{ all -> 0x001a }
                    r2.showSelectVideoWindow()     // Catch:{ all -> 0x001a }
                L_0x0026:
                    monitor-exit(r4)     // Catch:{ all -> 0x001a }
                    goto L_0x000b
                L_0x0028:
                    int r0 = r4     // Catch:{ all -> 0x001a }
                    int r1 = r5     // Catch:{ all -> 0x001a }
                    if (r0 >= r3) goto L_0x0031
                    r0 = 2147483647(0x7fffffff, float:NaN)
                L_0x0031:
                    if (r1 == 0) goto L_0x0035
                    if (r1 != r3) goto L_0x0040
                L_0x0035:
                    r1 = 0
                L_0x0036:
                    com.ansca.corona.Controller r2 = com.ansca.corona.Controller.this     // Catch:{ all -> 0x001a }
                    com.ansca.corona.listeners.CoronaShowApiListener r2 = r2.myCoronaShowApiListener     // Catch:{ all -> 0x001a }
                    r2.showCameraWindowForVideo(r0, r1)     // Catch:{ all -> 0x001a }
                    goto L_0x0026
                L_0x0040:
                    r1 = 1
                    goto L_0x0036
                */
                throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.Controller.AnonymousClass10.run():void");
            }
        });
    }

    public synchronized void start() {
        if (this.myInitialResume) {
            this.myInitialResume = false;
            this.myRuntimeState = RuntimeState.Running;
        } else if (this.myRuntimeState == RuntimeState.Stopping || this.myRuntimeState == RuntimeState.Running) {
            this.myRuntimeState = RuntimeState.Running;
        } else {
            this.myRuntimeState = RuntimeState.Starting;
        }
        this.myGLView.onResume();
        requestEventRender();
        this.myMediaManager.resumeAll();
        this.mySensorManager.resume();
        startTimer();
        internalSetIdleTimer(this.myIdleEnabled);
    }

    public void startTimer() {
        if (this.myTimerMilliseconds != 0 && this.myTimerTask == null) {
            this.myTimerTask = new Runnable() {
                public void run() {
                    if (Controller.this.myTimerMilliseconds != 0) {
                        Controller.this.myTimerHandler.postDelayed(this, (long) Controller.this.myTimerMilliseconds);
                        Controller.this.requestRender();
                    }
                }
            };
            this.myTimerHandler.postDelayed(this.myTimerTask, (long) this.myTimerMilliseconds);
        }
    }

    public synchronized void stop() {
        stopTimer();
        this.mySensorManager.pause();
        if (this.myRuntimeState == RuntimeState.Starting || this.myRuntimeState == RuntimeState.Stopped) {
            this.myRuntimeState = RuntimeState.Stopped;
        } else {
            this.myRuntimeState = RuntimeState.Stopping;
        }
        requestEventRender();
        this.myMediaManager.pauseAll();
        internalSetIdleTimer(true);
    }

    public void stopTimer() {
        if (this.myTimerTask != null) {
            this.myTimerHandler.removeCallbacks(this.myTimerTask);
            this.myTimerTask = null;
        }
    }

    public void vibrate() {
        Context context = this.myContext;
        if (context != null) {
            ((Vibrator) context.getSystemService("vibrator")).vibrate(100);
        }
    }
}
