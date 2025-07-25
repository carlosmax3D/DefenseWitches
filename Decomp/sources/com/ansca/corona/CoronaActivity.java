package com.ansca.corona;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.ansca.corona.events.EventManager;
import com.ansca.corona.events.ImagePickerTask;
import com.ansca.corona.events.MediaPickerTask;
import com.ansca.corona.events.NotificationReceivedTask;
import com.ansca.corona.events.RunnableEvent;
import com.ansca.corona.events.VideoPickerTask;
import com.ansca.corona.graphics.opengl.CoronaGLSurfaceView;
import com.ansca.corona.input.InputDeviceServices;
import com.ansca.corona.input.ViewInputHandler;
import com.ansca.corona.notifications.NotificationServices;
import com.ansca.corona.purchasing.StoreProxy;
import com.ansca.corona.storage.FileContentProvider;
import com.ansca.corona.storage.FileServices;
import com.ansca.corona.storage.UniqueFileNameBuilder;
import com.tapjoy.TapjoyConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import jp.stargarage.g2metrics.BuildConfig;

public class CoronaActivity extends Activity {
    private final int MIN_REQUEST_CODE = 1;
    private HashMap<Integer, OnActivityResultHandler> fActivityResultHandlers = new HashMap<>();
    private ContentObserver fAutoRotateObserver = null;
    /* access modifiers changed from: private */
    public Controller fController;
    /* access modifiers changed from: private */
    public CoronaRuntime fCoronaRuntime;
    private EventHandler fEventHandler;
    private int fLoggedOrientation = -1;
    /* access modifiers changed from: private */
    public ImageView fSplashScreenView = null;
    private CoronaGLSurfaceView myGLView;
    private Handler myHandler = null;
    private Intent myInitialIntent = null;
    private int myInitialOrientationSetting = -1;
    private ViewInputHandler myInputHandler;
    /* access modifiers changed from: private */
    public boolean myIsActivityResumed = false;
    private boolean myIsOrientationLocked = false;
    private CoronaRuntimeTaskDispatcher myRuntimeTaskDispatcher = null;
    private CoronaStatusBarSettings myStatusBarMode;
    private StoreProxy myStore = null;

    private static class ApiLevel16 {
        private ApiLevel16() {
        }

        public static void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
            if (viewTreeObserver != null && onGlobalLayoutListener != null) {
                try {
                    viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener);
                } catch (Exception e) {
                }
            }
        }
    }

    private static class EventHandler implements ViewTreeObserver.OnGlobalLayoutListener {
        private CoronaActivity fActivity;
        private boolean fIsUpdatingLayout;
        private Ticks fUpdateLayoutEndTicks;

        public EventHandler(CoronaActivity coronaActivity) {
            View contentView;
            if (coronaActivity == null) {
                throw new NullPointerException();
            }
            this.fActivity = coronaActivity;
            this.fIsUpdatingLayout = false;
            this.fUpdateLayoutEndTicks = Ticks.fromCurrentTime();
            if ((this.fActivity.getWindow().getAttributes().flags & 512) == 0 && (contentView = getContentView()) != null) {
                contentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
            }
        }

        private View getContentView() {
            ViewManager viewManager;
            ViewGroup contentView;
            CoronaRuntime access$200 = this.fActivity.fCoronaRuntime;
            if (access$200 == null || (viewManager = access$200.getViewManager()) == null || (contentView = viewManager.getContentView()) == null) {
                return null;
            }
            return contentView;
        }

        public void dispose() {
            View contentView = getContentView();
            if (contentView == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 16) {
                ApiLevel16.removeOnGlobalLayoutListener(contentView.getViewTreeObserver(), this);
            } else {
                contentView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        }

        public void onGlobalLayout() {
            View contentView;
            boolean isAcceptingText = ((InputMethodManager) this.fActivity.getSystemService("input_method")).isAcceptingText();
            Ticks fromCurrentTime = Ticks.fromCurrentTime();
            if (isAcceptingText) {
                this.fIsUpdatingLayout = true;
                this.fUpdateLayoutEndTicks = fromCurrentTime.addSeconds(2);
            }
            if (this.fIsUpdatingLayout && this.fUpdateLayoutEndTicks.compareTo(fromCurrentTime) < 0) {
                this.fIsUpdatingLayout = false;
            }
            if (this.fIsUpdatingLayout && (contentView = getContentView()) != null) {
                contentView.requestLayout();
            }
        }
    }

    private class ImagePickerEventGenerator implements MediaEventGenerator {
        private ImagePickerEventGenerator() {
        }

        public MediaPickerTask generateEvent(String str) {
            return new ImagePickerTask(str);
        }

        public MediaPickerTask generateEvent(String str, int i, long j) {
            return new ImagePickerTask(str);
        }
    }

    private interface MediaEventGenerator {
        MediaPickerTask generateEvent(String str);

        MediaPickerTask generateEvent(String str, int i, long j);
    }

    public interface OnActivityResultHandler {
        void onHandleActivityResult(CoronaActivity coronaActivity, int i, int i2, Intent intent);
    }

    private class PopupActivityResultHandler implements OnActivityResultHandler {
        private String fPopupName;

        private PopupActivityResultHandler(String str) {
            this.fPopupName = str;
        }

        public void onHandleActivityResult(CoronaActivity coronaActivity, int i, int i2, Intent intent) {
            coronaActivity.unregisterActivityResultHandler(this);
            EventManager eventManager = CoronaActivity.this.fCoronaRuntime.getController().getEventManager();
            if (eventManager != null) {
                final String str = this.fPopupName;
                eventManager.addEvent(new RunnableEvent(new Runnable() {
                    public void run() {
                        if (CoronaActivity.this.fCoronaRuntime.getController() != null) {
                            JavaToNativeShim.popupClosedEvent(CoronaActivity.this.fCoronaRuntime, str, false);
                        }
                    }
                }));
            }
        }
    }

    private static class SelectImageActivityResultHandler extends SelectMediaActivityResultHandler {
        public SelectImageActivityResultHandler(CoronaRuntime coronaRuntime) {
            super(coronaRuntime, "jpg", "Picture");
        }

        /* access modifiers changed from: protected */
        public MediaPickerTask generateEvent(String str, int i, long j) {
            return new ImagePickerTask(str);
        }

        /* access modifiers changed from: protected */
        public String[] getColumns() {
            return new String[]{"_data", "_size"};
        }

        /* access modifiers changed from: protected */
        public String handleContentUri(Uri uri, File file, Context context, String str) {
            String str2 = BuildConfig.FLAVOR;
            FileServices fileServices = new FileServices(context);
            InputStream inputStream = null;
            try {
                inputStream = context.getContentResolver().openInputStream(uri);
                if (file == null) {
                    UniqueFileNameBuilder uniqueFileNameBuilder = new UniqueFileNameBuilder();
                    uniqueFileNameBuilder.setDirectory(CoronaEnvironment.getInternalTemporaryDirectory(context));
                    uniqueFileNameBuilder.setFileNameFormat(this.fGenericFileName);
                    uniqueFileNameBuilder.setFileExtension(str);
                    file = uniqueFileNameBuilder.build();
                }
                if (fileServices.writeToFile(inputStream, file)) {
                    str2 = file.getAbsolutePath();
                }
            } catch (Exception e) {
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e2) {
                }
            }
            return str2;
        }
    }

    private static abstract class SelectMediaActivityResultHandler implements OnActivityResultHandler {
        /* access modifiers changed from: private */
        public CoronaRuntime fCoronaRuntime;
        /* access modifiers changed from: private */
        public String fDefaultExtention;
        private String fDestinationFilePath = null;
        protected String fGenericFileName;

        public SelectMediaActivityResultHandler(CoronaRuntime coronaRuntime, String str, String str2) {
            this.fCoronaRuntime = coronaRuntime;
            this.fDefaultExtention = str;
            this.fGenericFileName = str2 + " %d";
        }

        /* access modifiers changed from: protected */
        public abstract MediaPickerTask generateEvent(String str, int i, long j);

        /* access modifiers changed from: protected */
        public abstract String[] getColumns();

        /* access modifiers changed from: protected */
        public abstract String handleContentUri(Uri uri, File file, Context context, String str);

        public void onHandleActivityResult(CoronaActivity coronaActivity, int i, int i2, Intent intent) {
            coronaActivity.unregisterActivityResultHandler(this);
            Uri uri = null;
            if (intent != null) {
                uri = intent.getData();
            }
            final Uri uri2 = uri;
            File file = null;
            if (this.fDestinationFilePath != null && this.fDestinationFilePath.length() > 0) {
                file = new File(this.fDestinationFilePath);
            }
            final File file2 = file;
            this.fDestinationFilePath = null;
            if (i2 == -1 && uri2 != null) {
                new Thread(new Runnable() {
                    public void run() {
                        Context applicationContext = CoronaEnvironment.getApplicationContext();
                        if (applicationContext != null) {
                            FileServices fileServices = new FileServices(applicationContext);
                            File file = null;
                            String str = null;
                            long j = -1;
                            boolean z = false;
                            try {
                                String scheme = uri2.getScheme();
                                if ("file".equals(scheme)) {
                                    File file2 = new File(uri2.getPath());
                                    try {
                                        if (file2.exists()) {
                                            j = file2.length();
                                            file = file2;
                                        } else {
                                            file = file2;
                                        }
                                    } catch (Exception e) {
                                        file = file2;
                                    }
                                } else if ("content".equals(scheme)) {
                                    z = true;
                                    String[] columns = SelectMediaActivityResultHandler.this.getColumns();
                                    Cursor query = applicationContext.getContentResolver().query(uri2, columns, (String) null, (String[]) null, (String) null);
                                    query.moveToFirst();
                                    String string = query.getString(query.getColumnIndex(columns[0]));
                                    j = query.getLong(query.getColumnIndex(columns[1]));
                                    query.close();
                                    file = new File(string);
                                }
                            } catch (Exception e2) {
                            }
                            if (file != null) {
                                str = fileServices.getExtensionFrom(file);
                                if (!file.exists()) {
                                    file = null;
                                }
                            }
                            String str2 = BuildConfig.FLAVOR;
                            if (file == null || !file.exists()) {
                                if (z) {
                                    String access$400 = SelectMediaActivityResultHandler.this.fDefaultExtention;
                                    if (str != null) {
                                        access$400 = str;
                                    }
                                    str2 = SelectMediaActivityResultHandler.this.handleContentUri(uri2, file2, applicationContext, access$400);
                                }
                            } else if (file2 != null) {
                                if (fileServices.copyFile(file, file2)) {
                                    str2 = file2.getAbsolutePath();
                                }
                            } else {
                                str2 = file.getAbsolutePath();
                            }
                            if (SelectMediaActivityResultHandler.this.fCoronaRuntime != null) {
                                SelectMediaActivityResultHandler.this.fCoronaRuntime.getTaskDispatcher().send(SelectMediaActivityResultHandler.this.generateEvent(str2, CoronaActivity.getDurationOfVideo(str2), j));
                            }
                        }
                    }
                }).start();
            } else if (this.fCoronaRuntime != null) {
                this.fCoronaRuntime.getTaskDispatcher().send(generateEvent((String) null, -1, -1));
            }
        }

        public void setDestinationFilePath(String str) {
            this.fDestinationFilePath = str;
        }
    }

    private static class SelectVideoActivityResultHandler extends SelectMediaActivityResultHandler {
        public SelectVideoActivityResultHandler(CoronaRuntime coronaRuntime) {
            super(coronaRuntime, "3gp", "Video");
        }

        /* access modifiers changed from: protected */
        public MediaPickerTask generateEvent(String str, int i, long j) {
            return new VideoPickerTask(str, i, j);
        }

        /* access modifiers changed from: protected */
        public String[] getColumns() {
            return new String[]{"_data", "_size"};
        }

        /* access modifiers changed from: protected */
        public String handleContentUri(Uri uri, File file, Context context, String str) {
            return uri.toString();
        }
    }

    private static class TakeMediaWithExternalActivityResultHandler implements OnActivityResultHandler {
        /* access modifiers changed from: private */
        public CoronaRuntime fCoronaRuntime;
        private File fDestinationFile = null;
        /* access modifiers changed from: private */
        public MediaEventGenerator fEventGenerator;
        private Uri fSourceUri = null;

        public TakeMediaWithExternalActivityResultHandler(CoronaRuntime coronaRuntime, MediaEventGenerator mediaEventGenerator) {
            this.fEventGenerator = mediaEventGenerator;
            this.fCoronaRuntime = coronaRuntime;
        }

        public void onHandleActivityResult(CoronaActivity coronaActivity, int i, int i2, Intent intent) {
            coronaActivity.unregisterActivityResultHandler(this);
            String str = null;
            if (!(intent == null || intent.getData() == null || intent.getData().getScheme() == null)) {
                str = intent.getData().getScheme();
            }
            String str2 = BuildConfig.FLAVOR;
            long j = -1;
            if (i2 == -1 && (this.fSourceUri != null || "file".equals(str))) {
                File file = new File(this.fSourceUri.getPath());
                if (file.exists()) {
                    j = file.length();
                    if (this.fDestinationFile != null) {
                        final File file2 = file;
                        final File file3 = this.fDestinationFile;
                        final long j2 = j;
                        new Thread(new Runnable() {
                            public void run() {
                                boolean moveFile = new FileServices(CoronaEnvironment.getApplicationContext()).moveFile(file2, file3);
                                int access$600 = CoronaActivity.getDurationOfVideo(file3.getAbsolutePath());
                                if (TakeMediaWithExternalActivityResultHandler.this.fCoronaRuntime != null && TakeMediaWithExternalActivityResultHandler.this.fEventGenerator != null) {
                                    if (moveFile) {
                                        TakeMediaWithExternalActivityResultHandler.this.fCoronaRuntime.getTaskDispatcher().send(TakeMediaWithExternalActivityResultHandler.this.fEventGenerator.generateEvent(file3.getAbsolutePath(), access$600, j2));
                                    } else {
                                        TakeMediaWithExternalActivityResultHandler.this.fCoronaRuntime.getTaskDispatcher().send(TakeMediaWithExternalActivityResultHandler.this.fEventGenerator.generateEvent(BuildConfig.FLAVOR));
                                    }
                                }
                            }
                        }).start();
                        return;
                    }
                    str2 = file.getAbsolutePath();
                }
            } else if (i2 == -1 && "content".equals(str) && intent != null && intent.getData() != null) {
                String[] strArr = {"_data", "_size"};
                Cursor query = CoronaEnvironment.getApplicationContext().getContentResolver().query(intent.getData(), strArr, (String) null, (String[]) null, (String) null);
                query.moveToFirst();
                String string = query.getString(query.getColumnIndex(strArr[0]));
                j = query.getLong(query.getColumnIndex(strArr[1]));
                query.close();
                str2 = string;
            }
            if (this.fCoronaRuntime != null && this.fEventGenerator != null) {
                this.fCoronaRuntime.getTaskDispatcher().send(this.fEventGenerator.generateEvent(str2, CoronaActivity.getDurationOfVideo(str2), j));
            }
        }

        public void setDestinationFilePath(String str) {
            this.fDestinationFile = null;
            if (str != null && str.length() > 0) {
                this.fDestinationFile = new File(str);
            }
        }

        public void setSourceUri(Uri uri) {
            this.fSourceUri = uri;
        }
    }

    private static class TakePictureWithCoronaActivityResultHandler implements OnActivityResultHandler {
        private CoronaRuntime fCoronaRuntime;

        public TakePictureWithCoronaActivityResultHandler(CoronaRuntime coronaRuntime) {
            this.fCoronaRuntime = coronaRuntime;
        }

        public void onHandleActivityResult(CoronaActivity coronaActivity, int i, int i2, Intent intent) {
            Uri data;
            coronaActivity.unregisterActivityResultHandler(this);
            String str = BuildConfig.FLAVOR;
            if (!(i2 != -1 || intent == null || (data = intent.getData()) == null)) {
                str = data.getPath();
            }
            if (this.fCoronaRuntime != null) {
                this.fCoronaRuntime.getTaskDispatcher().send(new ImagePickerTask(str));
            }
        }
    }

    private class VideoPickerEventGenerator implements MediaEventGenerator {
        private VideoPickerEventGenerator() {
        }

        public MediaPickerTask generateEvent(String str) {
            return new VideoPickerTask(str);
        }

        public MediaPickerTask generateEvent(String str, int i, long j) {
            return new VideoPickerTask(str, i, j);
        }
    }

    private boolean canWriteToExternalStorage() {
        return "mounted".equals(Environment.getExternalStorageState()) && checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0;
    }

    private static boolean copyAsset(AssetManager assetManager, String str, String str2) {
        try {
            InputStream open = assetManager.open(str);
            new File(str2).createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            copyFile(open, fileOutputStream);
            open.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean copyAssetFolder(AssetManager assetManager, String str, String str2, boolean z) {
        if (z) {
            try {
                if (new File(str2 + "/xorgx11").exists()) {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        String[] list = assetManager.list(str);
        new File(str2).mkdirs();
        boolean z2 = true;
        for (String str3 : list) {
            z2 = assetManager.list(new StringBuilder().append(str).append("/").append(str3).toString()).length < 1 ? z2 & copyAsset(assetManager, str + "/" + str3, str2 + "/" + str3) : z2 & copyAssetFolder(assetManager, str + "/" + str3, str2 + "/" + str3, false);
        }
        return z2;
    }

    private static void copyFile(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            int i = read;
            if (read != -1) {
                outputStream.write(bArr, 0, i);
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public static int getDurationOfVideo(String str) {
        MediaPlayer create;
        try {
            Context applicationContext = CoronaEnvironment.getApplicationContext();
            if (applicationContext == null || (create = MediaPlayer.create(applicationContext, Uri.parse(str))) == null) {
                return -1;
            }
            int duration = create.getDuration();
            create.release();
            return duration;
        } catch (Exception e) {
            return -1;
        }
    }

    private void initializeOrientation() {
        this.myInitialOrientationSetting = getRequestedOrientation();
        try {
            ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), 128);
            if (!(activityInfo == null || activityInfo.metaData == null)) {
                String string = activityInfo.metaData.getString("requestedDefaultOrientation");
                if (string.equals("portrait")) {
                    this.myInitialOrientationSetting = 1;
                } else if (string.equals("landscape")) {
                    this.myInitialOrientationSetting = 0;
                } else if (string.equals("reversePortrait")) {
                    this.myInitialOrientationSetting = 9;
                } else if (string.equals("reverseLandscape")) {
                    this.myInitialOrientationSetting = 8;
                } else if (string.equals("sensorPortrait")) {
                    this.myInitialOrientationSetting = 7;
                } else if (string.equals("sensorLandscape")) {
                    this.myInitialOrientationSetting = 6;
                } else if (string != null) {
                    this.myInitialOrientationSetting = -1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.myInitialOrientationSetting = screenOrientationFilter(this.myInitialOrientationSetting);
        logOrientation(this.myInitialOrientationSetting);
        setRequestedOrientation(this.myInitialOrientationSetting);
        if (needManualOrientationHandling()) {
            this.fAutoRotateObserver = new ContentObserver((Handler) null) {
                public void onChange(boolean z) {
                    super.onChange(z);
                    if (Settings.System.getInt(CoronaActivity.this.getContentResolver(), "accelerometer_rotation", 0) != 0) {
                        CoronaActivity.this.restoreInitialOrientationSetting();
                    } else if (!CoronaActivity.this.myIsActivityResumed) {
                    } else {
                        if (CoronaActivity.this.fController.getSystemMonitor() != null && CoronaActivity.this.fController.getSystemMonitor().isScreenUnlocked()) {
                            CoronaActivity.this.lockCurrentOrientation();
                        } else if ((!CoronaActivity.this.isAtPortraitOrientation() || CoronaActivity.this.supportsPortraitOrientation()) && (!CoronaActivity.this.isAtLandscapeOrientation() || CoronaActivity.this.supportsLandscapeOrientation())) {
                            CoronaActivity.this.lockCurrentOrientation();
                        } else {
                            CoronaActivity.this.lockOrientation(CoronaActivity.this.getLoggedOrientation());
                        }
                    }
                }
            };
        }
    }

    private void requestResumeCoronaRuntime() {
        if (this.myIsActivityResumed && this.fController.getSystemMonitor() != null && this.fController.getSystemMonitor().isScreenUnlocked()) {
            if (this.fController != null) {
                this.fController.start();
            } else {
                Log.d("Corona", "CoronaActivity.requestResumeCoronaRuntime(): Can't resume the CoronaRuntime because our Controller died!");
            }
            this.myGLView.onResumeCoronaRuntime();
        }
    }

    private void requestSuspendCoronaRuntime() {
        if (this.fController != null) {
            this.fController.stop();
        } else {
            Log.d("Corona", "CoronaActivity.requestSuspendCoronaRuntime(): Can't suspend the CoronaRuntime because our Controller died!");
        }
        this.myGLView.onSuspendCoronaRuntime();
    }

    private void showCameraWindowUsing(String str, Intent intent, MediaEventGenerator mediaEventGenerator, Uri uri) {
        boolean hasCamera = CameraServices.hasCamera();
        if (!hasCamera) {
            Log.v("Corona", "Warning: " + "Camera not found.");
            if (this.fController != null) {
                this.fController.showNativeAlert("Warning", "Camera not found.", new String[]{"OK"});
            }
        }
        boolean hasPermission = CameraServices.hasPermission();
        if (!hasPermission) {
            Log.v("Corona", "Warning: " + "This application does not have permission to use the camera.");
            if (this.fController != null) {
                this.fController.showNativeAlert("Warning", "This application does not have permission to use the camera.", new String[]{"OK"});
            }
        }
        if (hasCamera && hasPermission) {
            boolean z = false;
            if (canWriteToExternalStorage()) {
                TakeMediaWithExternalActivityResultHandler takeMediaWithExternalActivityResultHandler = new TakeMediaWithExternalActivityResultHandler(this.fCoronaRuntime, mediaEventGenerator);
                takeMediaWithExternalActivityResultHandler.setDestinationFilePath(str);
                int registerActivityResultHandler = registerActivityResultHandler(takeMediaWithExternalActivityResultHandler);
                if (uri != null) {
                    takeMediaWithExternalActivityResultHandler.setSourceUri(uri);
                }
                if (intent.resolveActivity(getPackageManager()) != null) {
                    z = true;
                    startActivityForResult(intent, registerActivityResultHandler);
                }
            }
            if (!canWriteToExternalStorage() || !z) {
                Intent intent2 = new Intent(this, CameraActivity.class);
                if (str != null && str.length() > 0) {
                    intent2.setData(Uri.parse(str));
                }
                startActivityForResult(intent2, registerActivityResultHandler(new TakePictureWithCoronaActivityResultHandler(this.fCoronaRuntime)));
            }
        } else if (this.fCoronaRuntime != null && this.fCoronaRuntime.isRunning()) {
            this.fCoronaRuntime.getTaskDispatcher().send(mediaEventGenerator.generateEvent(BuildConfig.FLAVOR));
        }
    }

    public Point convertCoronaPointToAndroidPoint(int i, int i2) {
        return JavaToNativeShim.convertCoronaPointToAndroidPoint(this.fCoronaRuntime, i, i2);
    }

    public int getContentHeightInPixels() {
        return JavaToNativeShim.getContentHeightInPixels(this.fCoronaRuntime);
    }

    public int getContentWidthInPixels() {
        return JavaToNativeShim.getContentWidthInPixels(this.fCoronaRuntime);
    }

    /* access modifiers changed from: package-private */
    public int getCurrentOrientation() {
        boolean z = true;
        Display defaultDisplay = ((WindowManager) getSystemService("window")).getDefaultDisplay();
        int rotation = defaultDisplay.getRotation();
        if ((defaultDisplay.getWidth() > defaultDisplay.getHeight() || !(rotation == 0 || rotation == 2)) && (defaultDisplay.getWidth() <= defaultDisplay.getHeight() || !(rotation == 1 || rotation == 3))) {
            z = false;
        }
        switch (rotation) {
            case 1:
                return z ? 0 : 9;
            case 2:
                return z ? 9 : 8;
            case 3:
                return z ? 8 : 1;
            default:
                return z ? 1 : 0;
        }
    }

    /* access modifiers changed from: package-private */
    public CoronaGLSurfaceView getGLView() {
        return this.myGLView;
    }

    public Handler getHandler() {
        return this.myHandler;
    }

    public int getHorizontalMarginInPixels() {
        return JavaToNativeShim.getHorizontalMarginInPixels(this.fCoronaRuntime);
    }

    public Intent getInitialIntent() {
        return this.myInitialIntent;
    }

    public Intent getIntent() {
        return super.getIntent();
    }

    /* access modifiers changed from: package-private */
    public int getLoggedOrientation() {
        return this.fLoggedOrientation;
    }

    public int getOrientationFromManifest() {
        return this.myInitialOrientationSetting;
    }

    public FrameLayout getOverlayView() {
        ViewManager viewManager = this.fCoronaRuntime.getViewManager();
        if (viewManager == null) {
            return null;
        }
        return viewManager.getOverlayView();
    }

    /* access modifiers changed from: package-private */
    public CoronaRuntime getRuntime() {
        return this.fCoronaRuntime;
    }

    public CoronaRuntimeTaskDispatcher getRuntimeTaskDispatcher() {
        return this.myRuntimeTaskDispatcher;
    }

    /* access modifiers changed from: package-private */
    public int getStatusBarHeight() {
        String lowerCase = Build.MANUFACTURER.toLowerCase();
        if (Build.MODEL.equals("Kindle Fire")) {
            return 40;
        }
        if (Build.MODEL.equals("KFOT")) {
            return 27;
        }
        if (Build.MODEL.equals("KFTT")) {
            return 35;
        }
        if (Build.MODEL.equals("KFJWI") || Build.MODEL.equals("KFJWA")) {
            return 40;
        }
        if (Build.MODEL.equals("KFSOWI")) {
            return 34;
        }
        if (Build.MODEL.equals("KFTHWA") || Build.MODEL.equals("KFTHWI")) {
            return 51;
        }
        if (Build.MODEL.equals("KFAPWA") || Build.MODEL.equals("KFAPWI")) {
            return 53;
        }
        if (Build.MODEL.toLowerCase().contains("gamestick") || lowerCase.contains("ouya")) {
            return 0;
        }
        if (lowerCase.contains("barnes") && lowerCase.contains("noble")) {
            return 0;
        }
        if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT <= 13) {
            return 0;
        }
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE);
        if (identifier > 0) {
            return getResources().getDimensionPixelSize(identifier);
        }
        return (int) (((Build.VERSION.SDK_INT >= 23 ? 24.0d : 25.0d) * (((double) getResources().getDisplayMetrics().densityDpi) / 160.0d)) + 0.5d);
    }

    /* access modifiers changed from: package-private */
    public CoronaStatusBarSettings getStatusBarMode() {
        return this.myStatusBarMode;
    }

    /* access modifiers changed from: package-private */
    public StoreProxy getStore() {
        return this.myStore;
    }

    public int getVerticalMarginInPixels() {
        return JavaToNativeShim.getVerticalMarginInPixels(this.fCoronaRuntime);
    }

    /* access modifiers changed from: package-private */
    public void hideSplashScreen() {
        if (this.fSplashScreenView != null && this.fSplashScreenView.getAnimation() == null) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(500);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationEnd(Animation animation) {
                    if (CoronaActivity.this.fSplashScreenView != null) {
                        CoronaActivity.this.fSplashScreenView.post(new Runnable() {
                            public void run() {
                                if (CoronaActivity.this.fSplashScreenView != null) {
                                    ViewGroup viewGroup = (ViewGroup) CoronaActivity.this.fSplashScreenView.getParent();
                                    if (viewGroup != null) {
                                        viewGroup.removeView(CoronaActivity.this.fSplashScreenView);
                                    }
                                    ImageView unused = CoronaActivity.this.fSplashScreenView = null;
                                }
                            }
                        });
                    }
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }
            });
            this.fSplashScreenView.startAnimation(alphaAnimation);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isAtLandscapeOrientation() {
        switch (getCurrentOrientation()) {
            case 0:
            case 8:
                return true;
            default:
                return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isAtPortraitOrientation() {
        switch (getCurrentOrientation()) {
            case 1:
            case 9:
                return true;
            default:
                return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isSplashScreenShown() {
        return this.fSplashScreenView != null;
    }

    /* access modifiers changed from: package-private */
    public void lockCurrentOrientation() {
        if (!this.myIsOrientationLocked) {
            logCurrentOrientation();
            lockOrientation(getLoggedOrientation());
        }
    }

    /* access modifiers changed from: package-private */
    public void lockOrientation(int i) {
        if (!this.myIsOrientationLocked) {
            logOrientation(i);
            setRequestedOrientation(i);
            this.myIsOrientationLocked = true;
        }
    }

    /* access modifiers changed from: package-private */
    public void logCurrentOrientation() {
        logOrientation(getCurrentOrientation());
    }

    /* access modifiers changed from: package-private */
    public void logOrientation(int i) {
        this.fLoggedOrientation = i;
    }

    /* access modifiers changed from: package-private */
    public boolean needManualOrientationHandling() {
        return this.myInitialOrientationSetting == 6 || this.myInitialOrientationSetting == 7;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        OnActivityResultHandler onActivityResultHandler = this.fActivityResultHandlers.get(Integer.valueOf(i));
        if (onActivityResultHandler != null) {
            onActivityResultHandler.onHandleActivityResult(this, i, i2, intent);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (isSplashScreenShown()) {
            showSplashScreen();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.myInitialIntent = getIntent();
        boolean z = false;
        try {
            ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), 128);
            if (!(activityInfo == null || activityInfo.metaData == null)) {
                z = activityInfo.metaData.getBoolean("coronaWindowMovesWhenKeyboardAppears");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        requestWindowFeature(1);
        if (!z) {
            getWindow().setFlags(512, 512);
        }
        setStatusBarMode(CoronaStatusBarSettings.CORONA_STATUSBAR_MODE_HIDDEN);
        getWindow().setSoftInputMode(34);
        initializeOrientation();
        if (CoronaEnvironment.isNewInstall(this)) {
            CoronaEnvironment.onNewInstall(this);
        }
        CoronaEnvironment.deleteTempDirectory(this);
        CoronaEnvironment.setCoronaActivity(this);
        this.fCoronaRuntime = new CoronaRuntime(this, false);
        this.fController = this.fCoronaRuntime.getController();
        CoronaEnvironment.setController(this.fController);
        this.fController.setCoronaApiListener(new CoronaApiHandler(this, this.fCoronaRuntime));
        this.fController.setCoronaShowApiListener(new CoronaShowApiHandler(this, this.fCoronaRuntime));
        this.fController.setCoronaSplashScreenApiListener(new CoronaSplashScreenApiHandler(this));
        this.fController.setCoronaStatusBarApiListener(new CoronaStatusBarApiHandler(this));
        this.fController.setCoronaStoreApiListener(new CoronaStoreApiHandler(this));
        this.fController.setCoronaSystemApiListener(new CoronaSystemApiHandler(this));
        new FileServices(this).loadExpansionFiles();
        this.myStore = new StoreProxy(this.fCoronaRuntime, this.fCoronaRuntime.getController());
        this.myStore.setActivity(this);
        this.myHandler = new Handler();
        this.myRuntimeTaskDispatcher = new CoronaRuntimeTaskDispatcher(this.fCoronaRuntime);
        try {
            FileContentProvider.validateManifest(this);
        } catch (Exception e2) {
            this.fController.showNativeAlert("Error", e2.getMessage(), (String[]) null);
        }
        this.myGLView = this.fCoronaRuntime.getGLView();
        this.myGLView.setActivity(this);
        ViewManager viewManager = this.fCoronaRuntime.getViewManager();
        setContentView(viewManager.getContentView());
        this.myInputHandler = new ViewInputHandler(this.fCoronaRuntime.getController());
        this.myInputHandler.setDispatcher(this.myRuntimeTaskDispatcher);
        this.myInputHandler.setView(viewManager.getContentView());
        new NotificationServices(this);
        new InputDeviceServices(this);
        this.fEventHandler = new EventHandler(this);
        copyAssetFolder(getAssets(), "downloaded", "/data/data/" + getApplicationContext().getPackageName(), true);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        CameraActivity.clearCachedPhotos(this);
        this.fEventHandler.dispose();
        this.myGLView = null;
        this.myStore.disable();
        this.fCoronaRuntime.dispose();
        this.fCoronaRuntime = null;
        CoronaEnvironment.setCoronaActivity((CoronaActivity) null);
        super.onDestroy();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        ViewManager viewManager;
        if (this.myInputHandler.handle(keyEvent)) {
            return true;
        }
        if (i == 24 || i == 25) {
            try {
                ((AudioManager) getSystemService("audio")).adjustSuggestedStreamVolume(i == 24 ? 1 : -1, getVolumeControlStream(), 21);
                return true;
            } catch (Exception e) {
            }
        }
        if (i != 4 || (viewManager = this.fCoronaRuntime.getViewManager()) == null || !viewManager.goBack()) {
            return super.onKeyDown(i, keyEvent);
        }
        return true;
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (this.myInputHandler.handle(keyEvent)) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if ((intent.getData() != null || ((extras != null && extras.size() > 0) || (intent.getAction() != null && !intent.getAction().equals("android.intent.action.MAIN")))) && !intent.hasExtra(NotificationReceivedTask.NAME)) {
                setIntent(intent);
                EventManager eventManager = this.fCoronaRuntime.getController().getEventManager();
                if (eventManager != null) {
                    eventManager.addEvent(new RunnableEvent(new Runnable() {
                        public void run() {
                            JavaToNativeShim.applicationOpenEvent(CoronaActivity.this.fCoronaRuntime);
                        }
                    }));
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (needManualOrientationHandling()) {
            logCurrentOrientation();
            getContentResolver().unregisterContentObserver(this.fAutoRotateObserver);
        }
        this.myIsActivityResumed = false;
        requestSuspendCoronaRuntime();
        ViewManager viewManager = this.fCoronaRuntime.getViewManager();
        if (viewManager != null) {
            viewManager.suspend();
        } else {
            Log.d("Corona", "CoronaActivity.onPause(): Can't suspend the activity's views since we don't have a viewManager!");
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (needManualOrientationHandling()) {
            getContentResolver().registerContentObserver(Settings.System.getUriFor("accelerometer_rotation"), false, this.fAutoRotateObserver);
            if (Settings.System.getInt(getContentResolver(), "accelerometer_rotation", 0) != 0) {
                restoreInitialOrientationSetting();
            } else if ((!isAtPortraitOrientation() || supportsPortraitOrientation()) && (!isAtLandscapeOrientation() || supportsLandscapeOrientation())) {
                lockCurrentOrientation();
            } else {
                lockOrientation(getLoggedOrientation());
            }
        }
        this.myIsActivityResumed = true;
        requestResumeCoronaRuntime();
        this.fCoronaRuntime.updateViews();
        ViewManager viewManager = this.fCoronaRuntime.getViewManager();
        if (viewManager != null) {
            viewManager.resume();
        } else {
            Log.d("Corona", "CoronaActivity.onResume(): Can't resume the activity's views since we don't have a viewManager!");
        }
    }

    /* access modifiers changed from: package-private */
    public void onScreenLockStateChanged(boolean z) {
        if (this.myIsActivityResumed) {
            this.fCoronaRuntime.updateViews();
        }
        requestResumeCoronaRuntime();
        ViewManager viewManager = this.fCoronaRuntime.getViewManager();
        if (viewManager != null) {
            viewManager.onScreenLockStateChanged(z);
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
        }
    }

    public int registerActivityResultHandler(OnActivityResultHandler onActivityResultHandler) {
        if (onActivityResultHandler == null) {
            return -1;
        }
        int i = 1;
        while (this.fActivityResultHandlers.containsKey(Integer.valueOf(i))) {
            i++;
            if (i < 1) {
                i = 1;
            }
        }
        this.fActivityResultHandlers.put(Integer.valueOf(i), onActivityResultHandler);
        return i;
    }

    public int registerActivityResultHandler(OnActivityResultHandler onActivityResultHandler, int i) {
        if (onActivityResultHandler == null || i < 1) {
            return -1;
        }
        int i2 = 1;
        if (!this.fActivityResultHandlers.isEmpty()) {
            i2 = ((Integer) new TreeSet(this.fActivityResultHandlers.keySet()).last()).intValue() + 1;
        }
        for (int i3 = i2; i3 < i2 + i; i3++) {
            this.fActivityResultHandlers.put(Integer.valueOf(i3), onActivityResultHandler);
        }
        return i2;
    }

    /* access modifiers changed from: package-private */
    public void restoreInitialOrientationSetting() {
        if (this.myIsOrientationLocked) {
            setRequestedOrientation(this.myInitialOrientationSetting);
            this.myIsOrientationLocked = false;
        }
    }

    /* access modifiers changed from: package-private */
    public int screenOrientationFilter(int i) {
        int i2 = i;
        switch (i) {
            case 2:
            case 3:
            case 5:
            case 14:
                return 1;
            case 4:
            case 10:
            case 13:
                return -1;
            case 11:
                return 6;
            case 12:
                return 7;
            default:
                return i2;
        }
    }

    public void setRequestedOrientation(int i) {
        super.setRequestedOrientation(screenOrientationFilter(i));
    }

    /* access modifiers changed from: package-private */
    public void setStatusBarMode(CoronaStatusBarSettings coronaStatusBarSettings) {
        if (coronaStatusBarSettings != this.myStatusBarMode) {
            if (coronaStatusBarSettings == CoronaStatusBarSettings.CORONA_STATUSBAR_MODE_HIDDEN) {
                getWindow().addFlags(1024);
                getWindow().clearFlags(2048);
            } else if (coronaStatusBarSettings == CoronaStatusBarSettings.CORONA_STATUSBAR_MODE_DEFAULT || coronaStatusBarSettings == CoronaStatusBarSettings.CORONA_STATUSBAR_MODE_TRANSLUCENT || coronaStatusBarSettings == CoronaStatusBarSettings.CORONA_STATUSBAR_MODE_DARK) {
                if (this.myStatusBarMode == CoronaStatusBarSettings.CORONA_STATUSBAR_MODE_HIDDEN) {
                    getWindow().addFlags(2048);
                    getWindow().clearFlags(1024);
                }
                if (Build.VERSION.SDK_INT >= 19) {
                    if (coronaStatusBarSettings == CoronaStatusBarSettings.CORONA_STATUSBAR_MODE_TRANSLUCENT) {
                        getWindow().addFlags(67108864);
                    } else {
                        getWindow().clearFlags(67108864);
                    }
                }
            } else {
                return;
            }
            this.myStatusBarMode = coronaStatusBarSettings;
        }
    }

    /* access modifiers changed from: package-private */
    public void showCameraWindowForImage(String str) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        Uri uri = null;
        ImagePickerEventGenerator imagePickerEventGenerator = new ImagePickerEventGenerator();
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (canWriteToExternalStorage() && externalStoragePublicDirectory != null) {
            externalStoragePublicDirectory.mkdirs();
            UniqueFileNameBuilder uniqueFileNameBuilder = new UniqueFileNameBuilder();
            uniqueFileNameBuilder.setDirectory(externalStoragePublicDirectory);
            uniqueFileNameBuilder.setFileNameFormat("Picture");
            uniqueFileNameBuilder.setFileExtension(".jpg");
            File build = uniqueFileNameBuilder.build();
            if (build == null) {
                Log.v("Corona", "Failed to generate a unique file name for the camera shot.");
                if (this.fCoronaRuntime != null && this.fCoronaRuntime.isRunning()) {
                    this.fCoronaRuntime.getTaskDispatcher().send(imagePickerEventGenerator.generateEvent(BuildConfig.FLAVOR));
                    return;
                }
                return;
            }
            uri = Uri.fromFile(build);
            intent.putExtra("output", uri);
        }
        showCameraWindowUsing(str, intent, imagePickerEventGenerator, uri);
    }

    /* access modifiers changed from: package-private */
    public void showCameraWindowForVideo(int i, int i2) {
        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        Uri uri = null;
        VideoPickerEventGenerator videoPickerEventGenerator = new VideoPickerEventGenerator();
        if (Build.VERSION.SDK_INT == 18) {
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            if (canWriteToExternalStorage() && externalStoragePublicDirectory != null) {
                externalStoragePublicDirectory.mkdirs();
                UniqueFileNameBuilder uniqueFileNameBuilder = new UniqueFileNameBuilder();
                uniqueFileNameBuilder.setDirectory(externalStoragePublicDirectory);
                uniqueFileNameBuilder.setFileNameFormat("Video");
                uniqueFileNameBuilder.setFileExtension(".3gp");
                File build = uniqueFileNameBuilder.build();
                if (build == null) {
                    Log.v("Corona", "Failed to generate a unique file name for the camera shot.");
                    if (this.fCoronaRuntime != null && this.fCoronaRuntime.isRunning()) {
                        this.fCoronaRuntime.getTaskDispatcher().send(videoPickerEventGenerator.generateEvent(BuildConfig.FLAVOR));
                        return;
                    }
                    return;
                }
                uri = Uri.fromFile(build);
                intent.putExtra("output", uri);
            }
        }
        if (i > 0) {
            intent.putExtra("android.intent.extra.durationLimit", i);
        }
        intent.putExtra("android.intent.extra.videoQuality", i2);
        showCameraWindowUsing((String) null, intent, videoPickerEventGenerator, uri);
    }

    /* access modifiers changed from: package-private */
    public void showSelectImageWindowUsing(String str) {
        SelectImageActivityResultHandler selectImageActivityResultHandler = new SelectImageActivityResultHandler(this.fCoronaRuntime);
        selectImageActivityResultHandler.setDestinationFilePath(str);
        int registerActivityResultHandler = registerActivityResultHandler(selectImageActivityResultHandler);
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, BuildConfig.FLAVOR), registerActivityResultHandler);
    }

    /* access modifiers changed from: package-private */
    public void showSelectVideoWindow() {
        int registerActivityResultHandler = registerActivityResultHandler(new SelectVideoActivityResultHandler(this.fCoronaRuntime));
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("video/*");
        startActivityForResult(Intent.createChooser(intent, BuildConfig.FLAVOR), registerActivityResultHandler);
    }

    /* access modifiers changed from: package-private */
    public void showSendMailWindowUsing(MailSettings mailSettings) {
        if (mailSettings == null) {
            mailSettings = new MailSettings();
        }
        startActivityForResult(mailSettings.toIntent(), registerActivityResultHandler(new PopupActivityResultHandler("mail")));
    }

    /* access modifiers changed from: package-private */
    public void showSendSmsWindowUsing(SmsSettings smsSettings) {
        if (smsSettings == null) {
            smsSettings = new SmsSettings();
        }
        startActivityForResult(smsSettings.toIntent(), registerActivityResultHandler(new PopupActivityResultHandler("sms")));
    }

    /* access modifiers changed from: package-private */
    public void showSplashScreen() {
        float f;
        float f2;
        FileServices fileServices = new FileServices(this);
        InputStream inputStream = null;
        String str = null;
        int i = 0;
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        WindowOrientation fromCurrentWindowUsing = WindowOrientation.fromCurrentWindowUsing(this);
        try {
            int height = defaultDisplay.getHeight() > defaultDisplay.getWidth() ? defaultDisplay.getHeight() : defaultDisplay.getWidth();
            if (height > 480) {
                if (fromCurrentWindowUsing == WindowOrientation.LANDSCAPE_RIGHT) {
                    str = "Default-LandscapeRight.png";
                    inputStream = fileServices.openFile(str);
                    if (inputStream == null) {
                        str = "Default-Landscape.png";
                        inputStream = fileServices.openFile(str);
                    }
                } else if (fromCurrentWindowUsing == WindowOrientation.LANDSCAPE_LEFT) {
                    str = "Default-LandscapeLeft.png";
                    inputStream = fileServices.openFile(str);
                    if (inputStream == null) {
                        str = "Default-Landscape.png";
                        inputStream = fileServices.openFile(str);
                    }
                } else if (fromCurrentWindowUsing == WindowOrientation.PORTRAIT_UPSIDE_DOWN) {
                    str = "Default-PortraitUpsideDown.png";
                    inputStream = fileServices.openFile(str);
                    if (inputStream == null) {
                        str = "Default-Portrait.png";
                        inputStream = fileServices.openFile(str);
                    }
                } else {
                    str = "Default-Portrait.png";
                    inputStream = fileServices.openFile(str);
                }
            }
            if (inputStream == null) {
                if (height > 480) {
                    str = "Default@2x.png";
                    inputStream = fileServices.openFile(str);
                }
                if (inputStream == null) {
                    str = "Default.png";
                    inputStream = fileServices.openFile(str);
                }
                if (fromCurrentWindowUsing == WindowOrientation.LANDSCAPE_RIGHT) {
                    i = 270;
                } else if (fromCurrentWindowUsing == WindowOrientation.LANDSCAPE_LEFT) {
                    i = 90;
                } else if (fromCurrentWindowUsing == WindowOrientation.PORTRAIT_UPSIDE_DOWN) {
                    i = 180;
                }
            }
        } catch (Exception e) {
        }
        if (inputStream != null) {
            Bitmap bitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            try {
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bitmap = BitmapFactory.decodeStream(inputStream, (Rect) null, options);
            } catch (OutOfMemoryError e2) {
                if (options.inPreferredConfig == Bitmap.Config.ARGB_8888) {
                    Log.v("Corona", "Failed to load splash screen image file '" + str + "' as a 32-bit image. Reducing the image quality to 16-bit.");
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    bitmap = BitmapFactory.decodeStream(inputStream, (Rect) null, options);
                } else {
                    throw e2;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
            if (bitmap == null) {
                Log.v("Corona", "Unable to load file '" + str + "' as an image file for the splash screen.");
                return;
            }
            if (this.fSplashScreenView == null) {
                this.fSplashScreenView = new ImageView(this);
                this.fSplashScreenView.setBackgroundColor(-16777216);
                this.fCoronaRuntime.getViewManager().getContentView().addView(this.fSplashScreenView, new FrameLayout.LayoutParams(-1, -1, 17));
            }
            if (this.fSplashScreenView.getImageMatrix() != null && i == 0) {
                this.fSplashScreenView.getImageMatrix().reset();
            } else if (i != 0) {
                if (i == 0 || i == 180) {
                    f = (float) bitmap.getWidth();
                    f2 = (float) bitmap.getHeight();
                } else {
                    f = (float) bitmap.getHeight();
                    f2 = (float) bitmap.getWidth();
                }
                float min = Math.min(((float) defaultDisplay.getWidth()) / f, ((float) defaultDisplay.getHeight()) / f2);
                Matrix matrix = new Matrix();
                matrix.postTranslate(-(((float) bitmap.getWidth()) / 2.0f), -(((float) bitmap.getHeight()) / 2.0f));
                matrix.postRotate((float) i);
                matrix.postTranslate(f / 2.0f, f2 / 2.0f);
                matrix.postScale(min, min);
                matrix.postTranslate((((float) defaultDisplay.getWidth()) - (f * min)) / 2.0f, (((float) defaultDisplay.getHeight()) - (f2 * min)) / 2.0f);
                this.fSplashScreenView.setScaleType(ImageView.ScaleType.MATRIX);
                this.fSplashScreenView.setImageMatrix(matrix);
            }
            this.fSplashScreenView.setImageBitmap(bitmap);
        }
    }

    public boolean supportsLandscapeOrientation() {
        switch (this.myInitialOrientationSetting) {
            case -1:
            case 0:
            case 4:
            case 6:
            case 8:
            case 10:
            case 11:
            case 13:
                return true;
            default:
                return false;
        }
    }

    public boolean supportsPortraitOrientation() {
        switch (this.myInitialOrientationSetting) {
            case -1:
            case 1:
            case 4:
            case 7:
            case 9:
            case 10:
            case 12:
            case 13:
                return true;
            default:
                return false;
        }
    }

    public void unregisterActivityResultHandler(OnActivityResultHandler onActivityResultHandler) {
        if (onActivityResultHandler != null) {
            ArrayList arrayList = new ArrayList();
            for (Map.Entry next : this.fActivityResultHandlers.entrySet()) {
                if (next.getValue() == onActivityResultHandler) {
                    arrayList.add(next.getKey());
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.fActivityResultHandlers.remove((Integer) it.next());
            }
        }
    }
}
