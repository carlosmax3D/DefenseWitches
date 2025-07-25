package jp.stargarage.g2metrics;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.customview.widget.ExploreByTouchHelper;

import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.drive.DriveFile;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import jp.newgate.game.android.dw.Constants;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public final class G2Metrics implements Thread.UncaughtExceptionHandler {
    private static final String eventLogFileName = "event.log";
    private static final String g2mFirstLaunch = "g2mFirstLaunch";
    private static final String g2mUuidFileName = "g2mUuid";
    private static final G2Metrics instance = new G2Metrics();
    private static final String notificationLogFileName = "notification.log";
    private static final String sessionLogFileName = "session.log";
    private AppInfo appInfo;
    private Date appLaunchDate;
    private Application application;
    private DeviceInfo deviceInfo;
    private String g2mUuid;
    private String gameKey;
    private Object gcm;
    private Constant2 healthStatus;
    private String registrationId;
    private Thread.UncaughtExceptionHandler savedUncaughtExceptionHandler;
    private Integer sendEventLogCount;
    private Timer timerSendEventLog;
    private ArrayList<ArrayList<String>> sessionLogArray = new ArrayList<>();
    private ArrayList<ArrayList<String>> deviceTokenArray = new ArrayList<>();
    final Handler handler = new Handler();
    private boolean isDryRun = false;
    private boolean catchUncaughtExceptions = true;
    private final LogSettings logSettings = new LogSettings();

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    /* renamed from: jp.stargarage.g2metrics.G2Metrics$3 */
    class AsyncTaskC12553 extends AsyncTask<G2Metrics, Void, Void> {
        final /* synthetic */ Constant4[] val$works;

        AsyncTaskC12553(Constant4[] constant4Arr) {
            this.val$works = constant4Arr;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @SuppressLint("WrongThread")
        @Override // android.os.AsyncTask
        public Void doInBackground(G2Metrics... g2MetricsArr) {
            while (!G2Metrics.this.deviceReady()) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ParamStartupCheck paramStartupCheck = new ParamStartupCheck();
            paramStartupCheck.deviceInfo = G2Metrics.this.getInitializedDeviceInfoParam();
            paramStartupCheck.game_key = G2Metrics.this.gameKey;
            paramStartupCheck.game_version = G2Metrics.this.appInfo.version;
            if (G2Metrics.this.isNotFirstLaunch()) {
                paramStartupCheck.firstLaunch = 0;
            } else {
                paramStartupCheck.firstLaunch = 1;
            }
            try {
                G2Metrics.this.g2mUuid = G2Metrics.this.readUuid();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (G2Metrics.this.g2mUuid != null) {
                paramStartupCheck.uuid = G2Metrics.this.g2mUuid;
            }
            new ApiClient(G2Metrics.this.application, paramStartupCheck, new IAsyncApiRequestCallBack() { // from class: jp.stargarage.g2metrics.G2Metrics.3.1
                @Override // jp.stargarage.g2metrics.IAsyncApiRequestCallBack
                public void onFailure(Integer num) {
                    if (!G2Metrics.this.isNotFirstLaunch()) {
                        G2Metrics.this.isDryRun = true;
                    } else {
                        G2Metrics.this.startGetSettingRequest(AsyncTaskC12553.this.val$works);
                        try {
                            G2Metrics.instance.addSessionLog(Constant3.Launch);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override // jp.stargarage.g2metrics.IAsyncApiRequestCallBack
                public void onSuccess(ApiEntityBase apiEntityBase) {
                    ResponseStartupCheck responseStartupCheck = (ResponseStartupCheck) apiEntityBase;
                    G2Metrics.this.g2mUuid = responseStartupCheck.uuid;
                    try {
                        G2Metrics.this.writerUuid(responseStartupCheck.uuid);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    G2Metrics.this.startGetSettingRequest(AsyncTaskC12553.this.val$works);
                    if (responseStartupCheck.status.intValue() != 1) {
                        try {
                            G2Metrics.instance.addSessionLog(Constant3.Launch);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return;
                    }
                    try {
                        G2Metrics.this.remeberFirstLaunch();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (responseStartupCheck.popup.intValue() == 1) {
                        G2Metrics.this.handler.post(new Runnable() { // from class: jp.stargarage.g2metrics.G2Metrics.3.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                G2Metrics.this.popupBrowser();
                            }
                        });
                    } else {
                        try {
                            G2Metrics.instance.addSessionLog(Constant3.FirstLaunch);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }).execute(new Void[0]);
            return null;
        }
    }

    private G2Metrics() {
        this.healthStatus = Constant2.Unknown;
        this.healthStatus = Constant2.Unknown;
    }

    private void addExceptionReportLog(String str, Throwable th) throws JSONException, IllegalAccessException {
        EventValueCrash eventValueCrash = new EventValueCrash();
        eventValueCrash.message = th.getMessage();
        eventValueCrash.stack_trace = getStackTrace(str, th);
        eventValueCrash.stack_trace_hash = getStackTraceHash(th);
        addEventLog("cr01", eventValueCrash);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addSessionLog(Constant3 constant3) throws IOException {
        List<String> sendErrorSessionLog;
        if (constant3 == Constant3.Launch && (sendErrorSessionLog = FileManager.getSendErrorSessionLog(this.application, sessionLogFileName, Integer.MAX_VALUE)) != null && sendErrorSessionLog.size() > 0) {
            if (Constant.devModel) {
                Log.d("G2Metrics", "[セッション送信エラーログ登録件数] sessionSendErrorLog.size() = " + String.valueOf(sendErrorSessionLog.size()));
            }
            for (int i = 0; i < sendErrorSessionLog.size(); i++) {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(0, AppEventsConstants.EVENT_PARAM_VALUE_NO);
                arrayList.add(1, sendErrorSessionLog.get(i));
                this.sessionLogArray.add(arrayList);
                if (Constant.devModel) {
                    Log.d("G2Metrics", "[セッション送信エラーログ内容] idx = " + String.valueOf(i) + " 送信状態 = " + this.sessionLogArray.get(i).get(0) + " データ = " + this.sessionLogArray.get(i).get(1));
                }
            }
            FileManager.deleteTopData(this.application, sessionLogFileName, Integer.MAX_VALUE);
        }
        EntityLogData entityLogData = new EntityLogData();
        entityLogData.event_type = constant3.f3204id;
        if (constant3 == Constant3.Terminate) {
            EventValueTerminate eventValueTerminate = new EventValueTerminate();
            eventValueTerminate.once_play_time = Integer.valueOf(((int) (new Date().getTime() - this.appLaunchDate.getTime())) / 1000);
            try {
                entityLogData.event_value = eventValueTerminate.toJson().toString();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        ArrayList<String> arrayList2 = new ArrayList<>();
        String string = null;
        try {
            string = entityLogData.toJson().toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        arrayList2.add(0, AppEventsConstants.EVENT_PARAM_VALUE_NO);
        arrayList2.add(1, string);
        this.sessionLogArray.add(arrayList2);
        if (Constant.devModel) {
            Log.d("G2Metrics", "[セッションログ保持件数(送信エラーログ含)] sessionLogArray.size() = " + String.valueOf(this.sessionLogArray.size()));
            for (int i2 = 0; i2 < this.sessionLogArray.size(); i2++) {
                Log.d("G2Metrics", "[セッションログ保持内容] idx = " + String.valueOf(i2) + " 送信状態 = " + this.sessionLogArray.get(i2).get(0) + " データ = " + this.sessionLogArray.get(i2).get(1));
            }
        }
        if (this.healthStatus == Constant2.Ok) {
            sendSavedSessionLog();
        } else if (constant3 == Constant3.Terminate) {
            if (Constant.devModel) {
                Log.d("G2Metrics", "[アプリ終了時のヘルスチェックエラー] セッション送信エラーログ書込");
            }
            FileManager.appendSendErrorSessionLog(this.application, sessionLogFileName, this.sessionLogArray);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean appInfoReday() {
        return this.appInfo != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkPlayServices() {
        try {
            return GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.application) == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean deviceReady() {
        return this.deviceInfo != null;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [jp.stargarage.g2metrics.G2Metrics$12] */
    private void gcmRegist(final String str) {
        new Thread() { // from class: jp.stargarage.g2metrics.G2Metrics.12
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                if (G2Metrics.this.checkPlayServices()) {
                    while (!G2Metrics.this.appInfoReday()) {
                        try {
                            Thread.sleep(10L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    G2Metrics.this.gcm = null;//GoogleCloudMessaging.getInstance(G2Metrics.this.application);
                    G2Metrics.this.registrationId = null;//G2Metrics.this.getRegistrationId(G2Metrics.this.application);
                    if (G2Metrics.this.registrationId != null && !G2Metrics.this.registrationId.isEmpty()) {
                        if (Constant.devModel) {
                            Log.d("G2Metrics", "[PUSH通知] ローカルから登録ID取得 registrationId = " + G2Metrics.this.registrationId);
                            return;
                        }
                        return;
                    }
                    if (Constant.devModel) {
                        Log.d("G2Metrics", "[PUSH通知] サーバから登録ID取得 registrationId = " + G2Metrics.this.registrationId);
                    }
                    try {
                        G2Metrics.this.registrationId = null;//G2Metrics.this.gcm.register(str);
                        G2Metrics.this.addDeviceTokenLog("ps01", G2Metrics.this.registrationId);
                    } catch (Exception e2) {
                        G2Metrics.this.registrationId = null;
                        e2.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public static DeviceInfo getDeviceInfo() {
        return instance.deviceInfo;
    }

    public static String getG2mCode() throws IOException {
        instance.readUuid();
        return instance.g2mUuid;
    }

    private SharedPreferences getGCMPreferences(Context context) {
        return context.getSharedPreferences(G2Metrics.class.getSimpleName(), 0);
    }

    private EntityAppInfo getInitializedAppInfoParam() {
        EntityAppInfo entityAppInfo = new EntityAppInfo();
        entityAppInfo.bundle_identifier = this.appInfo.package_name;
        entityAppInfo.name = this.appInfo.name;
        entityAppInfo.display_name = BuildConfig.FLAVOR;
        entityAppInfo.game_version_code = this.appInfo.version;
        entityAppInfo.display_version = this.appInfo.displayVersion;
        return entityAppInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public EntityDeviceInfo getInitializedDeviceInfoParam() {
        EntityDeviceInfo entityDeviceInfo = new EntityDeviceInfo();
        entityDeviceInfo.platform_id = 2;
        entityDeviceInfo.carrier = this.deviceInfo.careerName;
        entityDeviceInfo.mcc = this.deviceInfo.mcc;
        entityDeviceInfo.mnc = this.deviceInfo.mnc;
        entityDeviceInfo.iso_country_code = this.deviceInfo.isoCountryCode;
        entityDeviceInfo.phone_number = this.deviceInfo.phoneNumber;
        entityDeviceInfo.sim_serial_number = this.deviceInfo.simSerialNumber;
        entityDeviceInfo.sim_state = this.deviceInfo.simState;
        entityDeviceInfo.voice_mail_number = this.deviceInfo.voiceMailNumber;
        entityDeviceInfo.device_serial_number = this.deviceInfo.serialNumber;
        entityDeviceInfo.idfa = this.deviceInfo.advertisingIdentifier;
        entityDeviceInfo.os_version = this.deviceInfo.systemVersion;
        entityDeviceInfo.idfv = this.deviceInfo.androidId;
        entityDeviceInfo.android_id = this.deviceInfo.androidId;
        entityDeviceInfo.model = this.deviceInfo.model;
        entityDeviceInfo.os_language = this.deviceInfo.language;
        entityDeviceInfo.os_timezone = this.deviceInfo.timeZone;
        entityDeviceInfo.mac_address = this.deviceInfo.macAddress;
        entityDeviceInfo.location_ipv4 = this.deviceInfo.ipv4;
        entityDeviceInfo.location_ipv6 = this.deviceInfo.ipv6;
        entityDeviceInfo.resolution = this.deviceInfo.resolution;
        return entityDeviceInfo;
    }

    private ParamDeviceToken getInitializedDeviceTokenParam() {
        ParamDeviceToken paramDeviceToken = new ParamDeviceToken();
        paramDeviceToken.game_key = this.gameKey;
        paramDeviceToken.uuid = this.g2mUuid;
        paramDeviceToken.sdk_version = BuildConfig.VERSION_NAME;
        paramDeviceToken.appInfo = getInitializedAppInfoParam();
        paramDeviceToken.deviceInfo = getInitializedDeviceInfoParam();
        return paramDeviceToken;
    }

    private ParamReadNotification getInitializedReadNotificationParam() {
        ParamReadNotification paramReadNotification = new ParamReadNotification();
        paramReadNotification.game_key = this.gameKey;
        paramReadNotification.uuid = this.g2mUuid;
        paramReadNotification.sdk_version = BuildConfig.VERSION_NAME;
        paramReadNotification.appInfo = getInitializedAppInfoParam();
        paramReadNotification.deviceInfo = getInitializedDeviceInfoParam();
        return paramReadNotification;
    }

    private ParamLog getInitializedSendLogParam() {
        ParamLog paramLog = new ParamLog(this.logSettings.useSsl);
        paramLog.game_key = this.gameKey;
        paramLog.uuid = this.g2mUuid;
        paramLog.sdk_version = BuildConfig.VERSION_NAME;
        paramLog.appInfo = getInitializedAppInfoParam();
        paramLog.deviceInfo = getInitializedDeviceInfoParam();
        return paramLog;
    }

    static G2Metrics getInstance() {
        return instance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getRegistrationId(Context context) {
        SharedPreferences gCMPreferences = getGCMPreferences(context);
        String string = gCMPreferences.getString("registrationId", BuildConfig.FLAVOR);
        return (string.isEmpty() || gCMPreferences.getInt(Constants.PROPERTY_APP_VERSION, ExploreByTouchHelper.INVALID_ID) == Integer.valueOf(this.appInfo.version).intValue()) ? string : BuildConfig.FLAVOR;
    }

    private String getStackTrace(String str, Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        if (str != null && !TextUtils.isEmpty(str)) {
            printWriter.println(str);
        }
        for (Throwable cause = th; cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        String string = stringWriter.toString();
        printWriter.close();
        return string;
    }

    private String getStackTraceHash(Throwable th) {
        StringBuilder sb = new StringBuilder();
        for (Throwable cause = th; cause != null; cause = cause.getCause()) {
            for (StackTraceElement stackTraceElement : cause.getStackTrace()) {
                sb.append(stackTraceElement.getClassName());
                sb.append(stackTraceElement.getMethodName());
            }
        }
        return Integer.toHexString(sb.toString().hashCode());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void healthCheckThenDo(final Constant4... constant4Arr) {
        if (Arrays.asList(constant4Arr).contains(Constant4.NetHealthCheck)) {
            this.healthStatus = Constant2.Unknown;
        }
        if (this.healthStatus == Constant2.Ok) {
            thenDo(constant4Arr);
            return;
        }
        ParamHealthCheck paramHealthCheck = new ParamHealthCheck();
        paramHealthCheck.gamekey = this.gameKey;
        new ApiClient(this.application, paramHealthCheck, new IAsyncApiRequestCallBack() { // from class: jp.stargarage.g2metrics.G2Metrics.8
            private void checkStatus(Constant2 constant2) {
                G2Metrics.this.healthStatus = constant2;
                switch (G2Metrics.this.healthStatus) {
                    case Ok:
                        G2Metrics.this.thenDo(constant4Arr);
                        break;
                    case NotRegistered:
                        G2Metrics.this.isDryRun = true;
                        if (Constant.devModel) {
                            Log.d("G2Metrics", "health check is Not Registered");
                            break;
                        }
                        break;
                    case InMaintenance:
                        if (Constant.devModel) {
                            Log.d("G2Metrics", "health check is In Maintenance");
                        }
                        G2Metrics.this.startHealthCheckTimer(constant4Arr);
                        break;
                    case ServerError:
                        if (Constant.devModel) {
                            Log.d("G2Metrics", "health check is Server Error");
                        }
                        G2Metrics.this.startHealthCheckTimer(constant4Arr);
                        break;
                }
            }

            @Override // jp.stargarage.g2metrics.IAsyncApiRequestCallBack
            public void onFailure(Integer num) {
                checkStatus(Constant2.ServerError);
            }

            @Override // jp.stargarage.g2metrics.IAsyncApiRequestCallBack
            public void onSuccess(ApiEntityBase apiEntityBase) {
                checkStatus(Constant2.valueOf(((ResponseHealthCheck) apiEntityBase).status));
            }
        }).execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isNotFirstLaunch() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.application.getFileStreamPath(g2mUuidFileName).exists();
    }

    private boolean isSendedRegistrationId(Context context) {
        return getGCMPreferences(context).getBoolean("sendFlag", false);
    }

    private void launch(Constant4... constant4Arr) {
        new AsyncTaskC12553(constant4Arr).execute(instance);
    }

    private void loadAppInfo() {
        new Thread(new AppInfoLoader(this.application.getApplicationContext(), new IAsyncInfoLoadCallBack() { // from class: jp.stargarage.g2metrics.G2Metrics.2
            @Override // jp.stargarage.g2metrics.IAsyncInfoLoadCallBack
            public void onLoadComplete(Object obj) {
                G2Metrics.this.appInfo = (AppInfo) obj;
            }
        })).start();
    }

    private void loadDeviceInfo() {
        new Thread(new DeviceInfoLoader(this.application.getApplicationContext(), new IAsyncInfoLoadCallBack() { // from class: jp.stargarage.g2metrics.G2Metrics.1
            @Override // jp.stargarage.g2metrics.IAsyncInfoLoadCallBack
            public void onLoadComplete(Object obj) {
                G2Metrics.this.deviceInfo = (DeviceInfo) obj;
            }
        })).start();
    }

    public static void notifyAppEnd() {
        if (instance.isDryRun) {
            return;
        }
        try {
            instance.addSessionLog(Constant3.Terminate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void popupBrowser() {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Constant.getPopupBrowserUrl(this.gameKey, this.g2mUuid, this.appInfo.version)));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.application.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String readUuid() throws IOException {
        try {
            if (this.application.getFileStreamPath(g2mUuidFileName).exists()) {
                FileInputStream fileInputStreamOpenFileInput = this.application.openFileInput(g2mUuidFileName);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStreamOpenFileInput));
                this.g2mUuid = bufferedReader.readLine();
                bufferedReader.close();
                fileInputStreamOpenFileInput.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.g2mUuid;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void remeberFirstLaunch() throws IOException {
        try {
            FileOutputStream fileOutputStreamOpenFileOutput = this.application.openFileOutput(g2mFirstLaunch, 0);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStreamOpenFileOutput));
            bufferedWriter.write(1);
            bufferedWriter.close();
            fileOutputStreamOpenFileOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendCrashReport(String str, Throwable th) throws JSONException, IllegalAccessException {
        if (instance.isDryRun) {
            return;
        }
        instance.addExceptionReportLog(str, th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSavedEventLog() throws IOException {
        List<EntityLogData> topData = FileManager.getTopData(this.application, eventLogFileName, this.logSettings.logSendCount);
        if (topData == null || topData.size() == 0) {
            return;
        }
        this.sendEventLogCount = Integer.valueOf(topData.size());
        ParamLog initializedSendLogParam = getInitializedSendLogParam();
        initializedSendLogParam.logList = topData;
        new ApiClient(this.application, initializedSendLogParam, new IAsyncApiRequestCallBack() { // from class: jp.stargarage.g2metrics.G2Metrics.5
            @Override // jp.stargarage.g2metrics.IAsyncApiRequestCallBack
            public void onFailure(Integer num) {
                G2Metrics.this.timerSendEventLog.cancel();
                G2Metrics.this.timerSendEventLog.purge();
                G2Metrics.this.timerSendEventLog = null;
                G2Metrics.this.healthCheckThenDo(Constant4.SendLog);
            }

            @Override // jp.stargarage.g2metrics.IAsyncApiRequestCallBack
            public void onSuccess(ApiEntityBase apiEntityBase) {
                try {
                    FileManager.deleteTopData(G2Metrics.this.application, G2Metrics.eventLogFileName, G2Metrics.this.sendEventLogCount);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                G2Metrics.this.sendEventLogCount = 0;
            }
        }).execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSavedReadNotificationLog() throws IOException {
        List<EntityLogData> topData = FileManager.getTopData(this.application, notificationLogFileName, Integer.MAX_VALUE);
        if (topData == null || topData.size() == 0) {
            return;
        }
        ParamReadNotification initializedReadNotificationParam = getInitializedReadNotificationParam();
        initializedReadNotificationParam.noticeList = topData;
        new ApiClient(this.application, initializedReadNotificationParam, new IAsyncApiRequestCallBack() { // from class: jp.stargarage.g2metrics.G2Metrics.7
            @Override // jp.stargarage.g2metrics.IAsyncApiRequestCallBack
            public void onFailure(Integer num) {
                G2Metrics.this.healthCheckThenDo(Constant4.SendLog);
            }

            @Override // jp.stargarage.g2metrics.IAsyncApiRequestCallBack
            public void onSuccess(ApiEntityBase apiEntityBase) {
                try {
                    FileManager.deleteTopData(G2Metrics.this.application, G2Metrics.notificationLogFileName, Integer.MAX_VALUE);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).execute(new Void[0]);
    }

    private void sendSavedSessionLog() {
        boolean z = false;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.sessionLogArray.size(); i++) {
            if (this.sessionLogArray.get(i).get(0).equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                EntityLogData entityLogData = new EntityLogData();
                try {
                    entityLogData.setDataByJsonStr(this.sessionLogArray.get(i).get(1));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                arrayList.add(entityLogData);
                this.sessionLogArray.get(i).set(0, AppEventsConstants.EVENT_PARAM_VALUE_YES);
                z = true;
            }
        }
        if (z) {
            if (Constant.devModel) {
                Log.d("G2Metrics", "[セッション送信件数] logDataList.size() = " + String.valueOf(arrayList.size()));
                for (int i2 = 0; i2 < this.sessionLogArray.size(); i2++) {
                    Log.d("G2Metrics", "[セッションログ送信内容] idx = " + String.valueOf(i2) + " 送信状態 = " + this.sessionLogArray.get(i2).get(0) + " データ = " + this.sessionLogArray.get(i2).get(1));
                }
            }
            ParamLog initializedSendLogParam = getInitializedSendLogParam();
            initializedSendLogParam.logList = arrayList;
            new ApiClient(this.application, initializedSendLogParam, new IAsyncApiRequestCallBack() { // from class: jp.stargarage.g2metrics.G2Metrics.4
                @Override // jp.stargarage.g2metrics.IAsyncApiRequestCallBack
                public void onFailure(Integer num) {
                    FileManager.appendSendErrorSessionLog(G2Metrics.this.application, G2Metrics.sessionLogFileName, G2Metrics.this.sessionLogArray);
                    for (int size = G2Metrics.this.sessionLogArray.size() - 1; size >= 0; size--) {
                        if (((String) ((ArrayList) G2Metrics.this.sessionLogArray.get(size)).get(0)).equals(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
                            ((ArrayList) G2Metrics.this.sessionLogArray.get(size)).set(0, AppEventsConstants.EVENT_PARAM_VALUE_NO);
                        }
                    }
                    G2Metrics.this.healthCheckThenDo(Constant4.SendLog);
                }

                @Override // jp.stargarage.g2metrics.IAsyncApiRequestCallBack
                public void onSuccess(ApiEntityBase apiEntityBase) {
                    for (int size = G2Metrics.this.sessionLogArray.size() - 1; size >= 0; size--) {
                        if (((String) ((ArrayList) G2Metrics.this.sessionLogArray.get(size)).get(0)).equals(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
                            G2Metrics.this.sessionLogArray.remove(size);
                        }
                    }
                }
            }).execute(new Void[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSavedTokenLog() {
        if (this.deviceTokenArray.size() == 0 || isSendedRegistrationId(this.application)) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.deviceTokenArray.size(); i++) {
            if (this.deviceTokenArray.get(i).get(0).equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                EntityLogData entityLogData = new EntityLogData();
                try {
                    entityLogData.setDataByJsonStr(this.deviceTokenArray.get(i).get(1));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                arrayList.add(entityLogData);
                this.deviceTokenArray.get(i).set(0, AppEventsConstants.EVENT_PARAM_VALUE_YES);
            }
        }
        if (Constant.devModel) {
            for (int i2 = 0; i2 < this.deviceTokenArray.size(); i2++) {
                Log.d("G2Metrics", "[デバイストークン送信内容] idx = " + String.valueOf(i2) + " 送信状態 = " + this.deviceTokenArray.get(i2).get(0) + " データ = " + this.deviceTokenArray.get(i2).get(1));
            }
        }
        ParamDeviceToken initializedDeviceTokenParam = getInitializedDeviceTokenParam();
        initializedDeviceTokenParam.tokenList = arrayList;
        new ApiClient(this.application, initializedDeviceTokenParam, new IAsyncApiRequestCallBack() { // from class: jp.stargarage.g2metrics.G2Metrics.6
            @Override // jp.stargarage.g2metrics.IAsyncApiRequestCallBack
            public void onFailure(Integer num) {
                for (int size = G2Metrics.this.deviceTokenArray.size() - 1; size >= 0; size--) {
                    if (((String) ((ArrayList) G2Metrics.this.deviceTokenArray.get(size)).get(0)).equals(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
                        ((ArrayList) G2Metrics.this.deviceTokenArray.get(size)).set(0, AppEventsConstants.EVENT_PARAM_VALUE_NO);
                    }
                }
            }

            @Override // jp.stargarage.g2metrics.IAsyncApiRequestCallBack
            public void onSuccess(ApiEntityBase apiEntityBase) {
                for (int size = G2Metrics.this.deviceTokenArray.size() - 1; size >= 0; size--) {
                    if (((String) ((ArrayList) G2Metrics.this.deviceTokenArray.get(size)).get(0)).equals(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
                        G2Metrics.this.deviceTokenArray.remove(size);
                    }
                }
                G2Metrics.this.sendedRegistrationId(G2Metrics.this.application);
            }
        }).execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendedRegistrationId(Context context) {
        SharedPreferences.Editor editorEdit = getGCMPreferences(context).edit();
        editorEdit.putBoolean("sendFlag", true);
        editorEdit.apply();
    }

    public static void setApplication(Application application, boolean z) throws PackageManager.NameNotFoundException {
        if (z) {
            return;
        }
        try {
            instance.appLaunchDate = new Date();
            instance.sessionLogArray.clear();
            instance.deviceTokenArray.clear();
            if (instance.application != null) {
                instance.healthCheckThenDo(Constant4.NetHealthCheck, Constant4.SendLog, Constant4.Launch);
                return;
            }
            instance.application = application;
            instance.savedUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(instance);
            try {
                instance.gameKey = application.getPackageManager().getApplicationInfo(application.getPackageName(), 128).metaData.getString("g2metrics_game_id");
            } catch (Exception e) {
                Log.d("G2Metrics", "meta data g2metrics_game_id is not exists.");
                e.printStackTrace();
            }
            instance.isDryRun = z;
            instance.loadDeviceInfo();
            instance.loadAppInfo();
            instance.healthCheckThenDo(Constant4.NetHealthCheck, Constant4.SendLog, Constant4.StartTimer, Constant4.Launch);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void setCatchUncaughtExceptions(boolean z) {
        if (instance.isDryRun) {
            return;
        }
        instance.catchUncaughtExceptions = z;
    }

    public static void setGcmSenderId(String str) {
        if (instance.isDryRun) {
            return;
        }
        instance.gcmRegist(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startEventLogSendTimer() {
        if (this.timerSendEventLog != null) {
            this.timerSendEventLog.cancel();
            this.timerSendEventLog.purge();
            this.timerSendEventLog = null;
        }
        this.timerSendEventLog = new Timer();
        if (Constant.devModel) {
            Log.d("G2Metrics", "Start EventLog Send Timer.");
        }
        this.timerSendEventLog.schedule(new TimerTask() { // from class: jp.stargarage.g2metrics.G2Metrics.10
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                if (Constant.devModel) {
                    Log.d("G2Metrics", "Send EventLog From Send Timer.");
                }
                try {
                    G2Metrics.this.sendSavedEventLog();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                G2Metrics.this.sendSavedTokenLog();
                try {
                    G2Metrics.this.sendSavedReadNotificationLog();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 15000L, this.logSettings.logSendInterval.intValue() * 1000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startGetSettingRequest(Constant4... constant4Arr) {
        List listAsList = Arrays.asList(constant4Arr);
        if (listAsList.contains(Constant4.SendLog)) {
            sendSavedSessionLog();
        }
        if (listAsList.contains(Constant4.StartTimer)) {
            ParamLogSetting paramLogSetting = new ParamLogSetting();
            paramLogSetting.gameKey = this.gameKey;
            new ApiClient(this.application, paramLogSetting, new IAsyncApiRequestCallBack() { // from class: jp.stargarage.g2metrics.G2Metrics.9
                @Override // jp.stargarage.g2metrics.IAsyncApiRequestCallBack
                public void onFailure(Integer num) {
                    G2Metrics.this.startEventLogSendTimer();
                }

                @Override // jp.stargarage.g2metrics.IAsyncApiRequestCallBack
                public void onSuccess(ApiEntityBase apiEntityBase) {
                    ResponseLogSetting responseLogSetting = (ResponseLogSetting) apiEntityBase;
                    G2Metrics.this.logSettings.logSendInterval = responseLogSetting.interval;
                    G2Metrics.this.logSettings.logSendCount = responseLogSetting.count;
                    G2Metrics.this.logSettings.useSsl = responseLogSetting.useSsl;
                    G2Metrics.this.startEventLogSendTimer();
                }
            }).execute(new Void[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startHealthCheckTimer(final Constant4... constant4Arr) {
        final Timer timer = new Timer();
        if (Constant.devModel) {
            Log.d("G2Metrics", "Start Health Check Timer.");
        }
        timer.schedule(new TimerTask() { // from class: jp.stargarage.g2metrics.G2Metrics.11
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                timer.cancel();
                G2Metrics.this.healthCheckThenDo(constant4Arr);
            }
        }, this.logSettings.healthCheckInterval.intValue() * 1000, this.logSettings.healthCheckInterval.intValue() * 1000);
    }

    private void storeRegistrationId(Context context, String str) {
        SharedPreferences gCMPreferences = getGCMPreferences(context);
        int iIntValue = Integer.valueOf(this.appInfo.version).intValue();
        SharedPreferences.Editor editorEdit = gCMPreferences.edit();
        editorEdit.putString("registrationId", str);
        editorEdit.putInt(Constants.PROPERTY_APP_VERSION, iIntValue);
        editorEdit.putBoolean("sendFlag", false);
        editorEdit.apply();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void thenDo(Constant4... constant4Arr) {
        if (Arrays.asList(constant4Arr).contains(Constant4.Launch)) {
            instance.launch(constant4Arr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writerUuid(String str) throws IOException {
        try {
            FileOutputStream fileOutputStreamOpenFileOutput = this.application.openFileOutput(g2mUuidFileName, 0);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStreamOpenFileOutput));
            bufferedWriter.write(str);
            bufferedWriter.close();
            fileOutputStreamOpenFileOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void addDeviceTokenLog(String str, String str2) throws JSONException, IllegalAccessException {
        if (this.isDryRun) {
            return;
        }
        storeRegistrationId(this.application, str2);
        EventValueDeviceToken eventValueDeviceToken = new EventValueDeviceToken();
        eventValueDeviceToken.device_token = str2;
        EntityLogData entityLogData = new EntityLogData();
        entityLogData.event_type = str;
        entityLogData.event_value = eventValueDeviceToken.toJson().toString();
        this.deviceTokenArray.clear();
        ArrayList<String> arrayList = new ArrayList<>();
        String string = entityLogData.toJson().toString();
        arrayList.add(0, AppEventsConstants.EVENT_PARAM_VALUE_NO);
        arrayList.add(1, string);
        this.deviceTokenArray.add(arrayList);
        if (Constant.devModel) {
            for (int i = 0; i < this.deviceTokenArray.size(); i++) {
                Log.d("G2Metrics", "[デバイストークン保持件数] idx = " + String.valueOf(i) + " 送信状態 = " + this.deviceTokenArray.get(i).get(0) + " データ = " + this.deviceTokenArray.get(i).get(1));
            }
        }
    }

    void addEventLog(String str, ApiEntityBase apiEntityBase) throws JSONException, IllegalAccessException {
        if (this.isDryRun) {
            return;
        }
        EntityLogData entityLogData = new EntityLogData();
        entityLogData.event_type = str;
        if (apiEntityBase != null) {
            entityLogData.event_value = apiEntityBase.toJson().toString();
        }
        FileManager.appendLog(this.application, eventLogFileName, entityLogData);
    }

    void addNotificationLog(String str, ApiEntityBase apiEntityBase) throws JSONException, IllegalAccessException {
        if (this.isDryRun) {
            return;
        }
        EntityLogData entityLogData = new EntityLogData();
        entityLogData.event_type = str;
        if (apiEntityBase != null) {
            entityLogData.event_value = apiEntityBase.toJson().toString();
        }
        FileManager.appendLog(this.application, notificationLogFileName, entityLogData);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        if (!instance.catchUncaughtExceptions) {
            if (this.savedUncaughtExceptionHandler != null) {
                this.savedUncaughtExceptionHandler.uncaughtException(thread, th);
            }
        } else if (thread != null) {
            try {
                instance.addExceptionReportLog("Report requested by g2m sdk.", th);
                this.savedUncaughtExceptionHandler.uncaughtException(thread, th);
            } catch (Throwable th2) {
                if (this.savedUncaughtExceptionHandler != null) {
                    this.savedUncaughtExceptionHandler.uncaughtException(thread, th);
                }
            }
        }
    }
}
