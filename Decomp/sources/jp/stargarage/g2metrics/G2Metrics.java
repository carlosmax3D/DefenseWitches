package jp.stargarage.g2metrics;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.ExploreByTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.gcm.GoogleCloudMessaging;
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

public final class G2Metrics implements Thread.UncaughtExceptionHandler {
    private static final String eventLogFileName = "event.log";
    private static final String g2mFirstLaunch = "g2mFirstLaunch";
    private static final String g2mUuidFileName = "g2mUuid";
    /* access modifiers changed from: private */
    public static final G2Metrics instance = new G2Metrics();
    private static final String notificationLogFileName = "notification.log";
    private static final String sessionLogFileName = "session.log";
    /* access modifiers changed from: private */
    public AppInfo appInfo;
    private Date appLaunchDate;
    /* access modifiers changed from: private */
    public Application application;
    private boolean catchUncaughtExceptions;
    /* access modifiers changed from: private */
    public DeviceInfo deviceInfo;
    /* access modifiers changed from: private */
    public ArrayList<ArrayList<String>> deviceTokenArray;
    /* access modifiers changed from: private */
    public String g2mUuid;
    /* access modifiers changed from: private */
    public String gameKey;
    /* access modifiers changed from: private */
    public GoogleCloudMessaging gcm;
    final Handler handler;
    /* access modifiers changed from: private */
    public HealthStatus healthStatus;
    /* access modifiers changed from: private */
    public boolean isDryRun;
    /* access modifiers changed from: private */
    public final LogSettings logSettings;
    /* access modifiers changed from: private */
    public String registrationId;
    private Thread.UncaughtExceptionHandler savedUncaughtExceptionHandler;
    /* access modifiers changed from: private */
    public Integer sendEventLogCount;
    /* access modifiers changed from: private */
    public ArrayList<ArrayList<String>> sessionLogArray;
    /* access modifiers changed from: private */
    public Timer timerSendEventLog;

    private G2Metrics() {
        this.sessionLogArray = new ArrayList<>();
        this.deviceTokenArray = new ArrayList<>();
        this.healthStatus = HealthStatus.Unknown;
        this.handler = new Handler();
        this.isDryRun = false;
        this.catchUncaughtExceptions = true;
        this.healthStatus = HealthStatus.Unknown;
        this.logSettings = new LogSettings();
    }

    private void addExceptionReportLog(String str, Throwable th) {
        EventValueCrash eventValueCrash = new EventValueCrash();
        eventValueCrash.message = th.getMessage();
        eventValueCrash.stack_trace = getStackTrace(str, th);
        eventValueCrash.stack_trace_hash = getStackTraceHash(th);
        addEventLog("cr01", eventValueCrash);
    }

    /* access modifiers changed from: private */
    public void addSessionLog(SessionEventType sessionEventType) {
        List<String> sendErrorSessionLog;
        if (sessionEventType == SessionEventType.Launch && (sendErrorSessionLog = FileManager.getSendErrorSessionLog(this.application, sessionLogFileName, Integer.MAX_VALUE)) != null && sendErrorSessionLog.size() > 0) {
            if (Constant.devModel) {
                Log.d("G2Metrics", "[セッション送信エラーログ登録件数] sessionSendErrorLog.size() = " + String.valueOf(sendErrorSessionLog.size()));
            }
            for (int i = 0; i < sendErrorSessionLog.size(); i++) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(0, AppEventsConstants.EVENT_PARAM_VALUE_NO);
                arrayList.add(1, sendErrorSessionLog.get(i));
                this.sessionLogArray.add(arrayList);
                if (Constant.devModel) {
                    Log.d("G2Metrics", "[セッション送信エラーログ内容] idx = " + String.valueOf(i) + " 送信状態 = " + ((String) this.sessionLogArray.get(i).get(0)) + " データ = " + ((String) this.sessionLogArray.get(i).get(1)));
                }
            }
            FileManager.deleteTopData(this.application, sessionLogFileName, Integer.MAX_VALUE);
        }
        EntityLogData entityLogData = new EntityLogData();
        entityLogData.event_type = sessionEventType.id;
        if (sessionEventType == SessionEventType.Terminate) {
            EventValueTerminate eventValueTerminate = new EventValueTerminate();
            eventValueTerminate.once_play_time = Integer.valueOf(((int) (new Date().getTime() - this.appLaunchDate.getTime())) / 1000);
            entityLogData.event_value = eventValueTerminate.toJson().toString();
        }
        ArrayList arrayList2 = new ArrayList();
        String jSONObject = entityLogData.toJson().toString();
        arrayList2.add(0, AppEventsConstants.EVENT_PARAM_VALUE_NO);
        arrayList2.add(1, jSONObject);
        this.sessionLogArray.add(arrayList2);
        if (Constant.devModel) {
            Log.d("G2Metrics", "[セッションログ保持件数(送信エラーログ含)] sessionLogArray.size() = " + String.valueOf(this.sessionLogArray.size()));
            for (int i2 = 0; i2 < this.sessionLogArray.size(); i2++) {
                Log.d("G2Metrics", "[セッションログ保持内容] idx = " + String.valueOf(i2) + " 送信状態 = " + ((String) this.sessionLogArray.get(i2).get(0)) + " データ = " + ((String) this.sessionLogArray.get(i2).get(1)));
            }
        }
        if (this.healthStatus == HealthStatus.Ok) {
            sendSavedSessionLog();
        } else if (sessionEventType == SessionEventType.Terminate) {
            if (Constant.devModel) {
                Log.d("G2Metrics", "[アプリ終了時のヘルスチェックエラー] セッション送信エラーログ書込");
            }
            FileManager.appendSendErrorSessionLog(this.application, sessionLogFileName, this.sessionLogArray);
        }
    }

    /* access modifiers changed from: private */
    public boolean appInfoReday() {
        return this.appInfo != null;
    }

    /* access modifiers changed from: private */
    public boolean checkPlayServices() {
        try {
            return GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.application) == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: private */
    public boolean deviceReady() {
        return this.deviceInfo != null;
    }

    private void gcmRegist(final String str) {
        new Thread() {
            public void run() {
                if (G2Metrics.this.checkPlayServices()) {
                    while (!G2Metrics.this.appInfoReday()) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    GoogleCloudMessaging unused = G2Metrics.this.gcm = GoogleCloudMessaging.getInstance(G2Metrics.this.application);
                    String unused2 = G2Metrics.this.registrationId = G2Metrics.this.getRegistrationId(G2Metrics.this.application);
                    if (G2Metrics.this.registrationId.isEmpty()) {
                        if (Constant.devModel) {
                            Log.d("G2Metrics", "[PUSH通知] サーバから登録ID取得 registrationId = " + G2Metrics.this.registrationId);
                        }
                        try {
                            String unused3 = G2Metrics.this.registrationId = G2Metrics.this.gcm.register(str);
                            G2Metrics.this.addDeviceTokenLog("ps01", G2Metrics.this.registrationId);
                        } catch (IOException e2) {
                            String unused4 = G2Metrics.this.registrationId = null;
                            e2.printStackTrace();
                        }
                    } else if (Constant.devModel) {
                        Log.d("G2Metrics", "[PUSH通知] ローカルから登録ID取得 registrationId = " + G2Metrics.this.registrationId);
                    }
                }
            }
        }.start();
    }

    public static DeviceInfo getDeviceInfo() {
        return instance.deviceInfo;
    }

    public static String getG2mCode() {
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

    /* access modifiers changed from: private */
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

    /* access modifiers changed from: private */
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
        for (Throwable th2 = th; th2 != null; th2 = th2.getCause()) {
            th2.printStackTrace(printWriter);
        }
        String obj = stringWriter.toString();
        printWriter.close();
        return obj;
    }

    private String getStackTraceHash(Throwable th) {
        StringBuilder sb = new StringBuilder();
        for (Throwable th2 = th; th2 != null; th2 = th2.getCause()) {
            for (StackTraceElement stackTraceElement : th2.getStackTrace()) {
                sb.append(stackTraceElement.getClassName());
                sb.append(stackTraceElement.getMethodName());
            }
        }
        return Integer.toHexString(sb.toString().hashCode());
    }

    /* access modifiers changed from: private */
    public void healthCheckThenDo(final Work... workArr) {
        if (Arrays.asList(workArr).contains(Work.NetHealthCheck)) {
            this.healthStatus = HealthStatus.Unknown;
        }
        if (this.healthStatus == HealthStatus.Ok) {
            thenDo(workArr);
            return;
        }
        ParamHealthCheck paramHealthCheck = new ParamHealthCheck();
        paramHealthCheck.gamekey = this.gameKey;
        new ApiClient(this.application, paramHealthCheck, new IAsyncApiRequestCallBack() {
            private void checkStatus(HealthStatus healthStatus) {
                HealthStatus unused = G2Metrics.this.healthStatus = healthStatus;
                switch (G2Metrics.this.healthStatus) {
                    case Ok:
                        G2Metrics.this.thenDo(workArr);
                        return;
                    case NotRegistered:
                        boolean unused2 = G2Metrics.this.isDryRun = true;
                        if (Constant.devModel) {
                            Log.d("G2Metrics", "health check is Not Registered");
                            return;
                        }
                        return;
                    case InMaintenance:
                        if (Constant.devModel) {
                            Log.d("G2Metrics", "health check is In Maintenance");
                        }
                        G2Metrics.this.startHealthCheckTimer(workArr);
                        return;
                    case ServerError:
                        if (Constant.devModel) {
                            Log.d("G2Metrics", "health check is Server Error");
                        }
                        G2Metrics.this.startHealthCheckTimer(workArr);
                        return;
                    default:
                        return;
                }
            }

            public void onFailure(Integer num) {
                checkStatus(HealthStatus.ServerError);
            }

            public void onSuccess(ApiEntityBase apiEntityBase) {
                checkStatus(HealthStatus.valueOf(((ResponseHealthCheck) apiEntityBase).status));
            }
        }).execute(new Void[0]);
    }

    /* access modifiers changed from: private */
    public boolean isNotFirstLaunch() {
        try {
            return this.application.getFileStreamPath(g2mUuidFileName).exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isSendedRegistrationId(Context context) {
        return getGCMPreferences(context).getBoolean("sendFlag", false);
    }

    private void launch(final Work... workArr) {
        new AsyncTask<G2Metrics, Void, Void>() {
            /* access modifiers changed from: protected */
            public Void doInBackground(G2Metrics... g2MetricsArr) {
                while (!G2Metrics.this.deviceReady()) {
                    try {
                        Thread.sleep(10);
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
                String unused = G2Metrics.this.g2mUuid = G2Metrics.this.readUuid();
                if (G2Metrics.this.g2mUuid != null) {
                    paramStartupCheck.uuid = G2Metrics.this.g2mUuid;
                }
                new ApiClient(G2Metrics.this.application, paramStartupCheck, new IAsyncApiRequestCallBack() {
                    public void onFailure(Integer num) {
                        if (G2Metrics.this.isNotFirstLaunch()) {
                            G2Metrics.this.startGetSettingRequest(workArr);
                            G2Metrics.instance.addSessionLog(SessionEventType.Launch);
                            return;
                        }
                        boolean unused = G2Metrics.this.isDryRun = true;
                    }

                    public void onSuccess(ApiEntityBase apiEntityBase) {
                        ResponseStartupCheck responseStartupCheck = (ResponseStartupCheck) apiEntityBase;
                        String unused = G2Metrics.this.g2mUuid = responseStartupCheck.uuid;
                        G2Metrics.this.writerUuid(responseStartupCheck.uuid);
                        G2Metrics.this.startGetSettingRequest(workArr);
                        if (responseStartupCheck.status.intValue() == 1) {
                            G2Metrics.this.remeberFirstLaunch();
                            if (responseStartupCheck.popup.intValue() == 1) {
                                G2Metrics.this.handler.post(new Runnable() {
                                    public void run() {
                                        G2Metrics.this.popupBrowser();
                                    }
                                });
                            } else {
                                G2Metrics.instance.addSessionLog(SessionEventType.FirstLaunch);
                            }
                        } else {
                            G2Metrics.instance.addSessionLog(SessionEventType.Launch);
                        }
                    }
                }).execute(new Void[0]);
                return null;
            }
        }.execute(new G2Metrics[]{instance});
    }

    private void loadAppInfo() {
        new Thread(new AppInfoLoader(this.application.getApplicationContext(), new IAsyncInfoLoadCallBack() {
            public void onLoadComplete(Object obj) {
                AppInfo unused = G2Metrics.this.appInfo = (AppInfo) obj;
            }
        })).start();
    }

    private void loadDeviceInfo() {
        new Thread(new DeviceInfoLoader(this.application.getApplicationContext(), new IAsyncInfoLoadCallBack() {
            public void onLoadComplete(Object obj) {
                DeviceInfo unused = G2Metrics.this.deviceInfo = (DeviceInfo) obj;
            }
        })).start();
    }

    public static void notifyAppEnd() {
        if (!instance.isDryRun) {
            try {
                instance.addSessionLog(SessionEventType.Terminate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public void popupBrowser() {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Constant.getPopupBrowserUrl(this.gameKey, this.g2mUuid, this.appInfo.version)));
        intent.setFlags(DriveFile.MODE_READ_ONLY);
        this.application.startActivity(intent);
    }

    /* access modifiers changed from: private */
    public String readUuid() {
        try {
            if (this.application.getFileStreamPath(g2mUuidFileName).exists()) {
                FileInputStream openFileInput = this.application.openFileInput(g2mUuidFileName);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput));
                this.g2mUuid = bufferedReader.readLine();
                bufferedReader.close();
                openFileInput.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.g2mUuid;
    }

    /* access modifiers changed from: private */
    public void remeberFirstLaunch() {
        try {
            FileOutputStream openFileOutput = this.application.openFileOutput(g2mFirstLaunch, 0);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(openFileOutput));
            bufferedWriter.write(1);
            bufferedWriter.close();
            openFileOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendCrashReport(String str, Throwable th) {
        if (!instance.isDryRun) {
            instance.addExceptionReportLog(str, th);
        }
    }

    /* access modifiers changed from: private */
    public void sendSavedEventLog() {
        List<EntityLogData> topData = FileManager.getTopData(this.application, eventLogFileName, this.logSettings.logSendCount);
        if (topData != null && topData.size() != 0) {
            this.sendEventLogCount = Integer.valueOf(topData.size());
            ParamLog initializedSendLogParam = getInitializedSendLogParam();
            initializedSendLogParam.logList = topData;
            new ApiClient(this.application, initializedSendLogParam, new IAsyncApiRequestCallBack() {
                public void onFailure(Integer num) {
                    G2Metrics.this.timerSendEventLog.cancel();
                    G2Metrics.this.timerSendEventLog.purge();
                    Timer unused = G2Metrics.this.timerSendEventLog = null;
                    G2Metrics.this.healthCheckThenDo(Work.SendLog);
                }

                public void onSuccess(ApiEntityBase apiEntityBase) {
                    FileManager.deleteTopData(G2Metrics.this.application, G2Metrics.eventLogFileName, G2Metrics.this.sendEventLogCount);
                    Integer unused = G2Metrics.this.sendEventLogCount = 0;
                }
            }).execute(new Void[0]);
        }
    }

    /* access modifiers changed from: private */
    public void sendSavedReadNotificationLog() {
        List<EntityLogData> topData = FileManager.getTopData(this.application, notificationLogFileName, Integer.MAX_VALUE);
        if (topData != null && topData.size() != 0) {
            ParamReadNotification initializedReadNotificationParam = getInitializedReadNotificationParam();
            initializedReadNotificationParam.noticeList = topData;
            new ApiClient(this.application, initializedReadNotificationParam, new IAsyncApiRequestCallBack() {
                public void onFailure(Integer num) {
                    G2Metrics.this.healthCheckThenDo(Work.SendLog);
                }

                public void onSuccess(ApiEntityBase apiEntityBase) {
                    FileManager.deleteTopData(G2Metrics.this.application, G2Metrics.notificationLogFileName, Integer.MAX_VALUE);
                }
            }).execute(new Void[0]);
        }
    }

    private void sendSavedSessionLog() {
        boolean z = false;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.sessionLogArray.size(); i++) {
            if (((String) this.sessionLogArray.get(i).get(0)).equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                EntityLogData entityLogData = new EntityLogData();
                entityLogData.setDataByJsonStr((String) this.sessionLogArray.get(i).get(1));
                arrayList.add(entityLogData);
                this.sessionLogArray.get(i).set(0, AppEventsConstants.EVENT_PARAM_VALUE_YES);
                z = true;
            }
        }
        if (z) {
            if (Constant.devModel) {
                Log.d("G2Metrics", "[セッション送信件数] logDataList.size() = " + String.valueOf(arrayList.size()));
                for (int i2 = 0; i2 < this.sessionLogArray.size(); i2++) {
                    Log.d("G2Metrics", "[セッションログ送信内容] idx = " + String.valueOf(i2) + " 送信状態 = " + ((String) this.sessionLogArray.get(i2).get(0)) + " データ = " + ((String) this.sessionLogArray.get(i2).get(1)));
                }
            }
            ParamLog initializedSendLogParam = getInitializedSendLogParam();
            initializedSendLogParam.logList = arrayList;
            new ApiClient(this.application, initializedSendLogParam, new IAsyncApiRequestCallBack() {
                public void onFailure(Integer num) {
                    FileManager.appendSendErrorSessionLog(G2Metrics.this.application, G2Metrics.sessionLogFileName, G2Metrics.this.sessionLogArray);
                    for (int size = G2Metrics.this.sessionLogArray.size() - 1; size >= 0; size--) {
                        if (((String) ((ArrayList) G2Metrics.this.sessionLogArray.get(size)).get(0)).equals(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
                            ((ArrayList) G2Metrics.this.sessionLogArray.get(size)).set(0, AppEventsConstants.EVENT_PARAM_VALUE_NO);
                        }
                    }
                    G2Metrics.this.healthCheckThenDo(Work.SendLog);
                }

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

    /* access modifiers changed from: private */
    public void sendSavedTokenLog() {
        if (this.deviceTokenArray.size() != 0 && !isSendedRegistrationId(this.application)) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < this.deviceTokenArray.size(); i++) {
                if (((String) this.deviceTokenArray.get(i).get(0)).equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                    EntityLogData entityLogData = new EntityLogData();
                    entityLogData.setDataByJsonStr((String) this.deviceTokenArray.get(i).get(1));
                    arrayList.add(entityLogData);
                    this.deviceTokenArray.get(i).set(0, AppEventsConstants.EVENT_PARAM_VALUE_YES);
                }
            }
            if (Constant.devModel) {
                for (int i2 = 0; i2 < this.deviceTokenArray.size(); i2++) {
                    Log.d("G2Metrics", "[デバイストークン送信内容] idx = " + String.valueOf(i2) + " 送信状態 = " + ((String) this.deviceTokenArray.get(i2).get(0)) + " データ = " + ((String) this.deviceTokenArray.get(i2).get(1)));
                }
            }
            ParamDeviceToken initializedDeviceTokenParam = getInitializedDeviceTokenParam();
            initializedDeviceTokenParam.tokenList = arrayList;
            new ApiClient(this.application, initializedDeviceTokenParam, new IAsyncApiRequestCallBack() {
                public void onFailure(Integer num) {
                    for (int size = G2Metrics.this.deviceTokenArray.size() - 1; size >= 0; size--) {
                        if (((String) ((ArrayList) G2Metrics.this.deviceTokenArray.get(size)).get(0)).equals(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
                            ((ArrayList) G2Metrics.this.deviceTokenArray.get(size)).set(0, AppEventsConstants.EVENT_PARAM_VALUE_NO);
                        }
                    }
                }

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
    }

    /* access modifiers changed from: private */
    public void sendedRegistrationId(Context context) {
        SharedPreferences.Editor edit = getGCMPreferences(context).edit();
        edit.putBoolean("sendFlag", true);
        edit.apply();
    }

    public static void setApplication(Application application2, boolean z) {
        if (!z) {
            try {
                instance.appLaunchDate = new Date();
                instance.sessionLogArray.clear();
                instance.deviceTokenArray.clear();
                if (instance.application == null) {
                    instance.application = application2;
                    instance.savedUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
                    Thread.setDefaultUncaughtExceptionHandler(instance);
                    try {
                        ApplicationInfo applicationInfo = application2.getPackageManager().getApplicationInfo(application2.getPackageName(), 128);
                        instance.gameKey = applicationInfo.metaData.getString("g2metrics_game_id");
                    } catch (Exception e) {
                        Log.d("G2Metrics", "meta data g2metrics_game_id is not exists.");
                        e.printStackTrace();
                    }
                    instance.isDryRun = z;
                    instance.loadDeviceInfo();
                    instance.loadAppInfo();
                    instance.healthCheckThenDo(Work.NetHealthCheck, Work.SendLog, Work.StartTimer, Work.Launch);
                    return;
                }
                instance.healthCheckThenDo(Work.NetHealthCheck, Work.SendLog, Work.Launch);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void setCatchUncaughtExceptions(boolean z) {
        if (!instance.isDryRun) {
            instance.catchUncaughtExceptions = z;
        }
    }

    public static void setGcmSenderId(String str) {
        if (!instance.isDryRun) {
            instance.gcmRegist(str);
        }
    }

    /* access modifiers changed from: private */
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
        this.timerSendEventLog.schedule(new TimerTask() {
            public void run() {
                if (Constant.devModel) {
                    Log.d("G2Metrics", "Send EventLog From Send Timer.");
                }
                G2Metrics.this.sendSavedEventLog();
                G2Metrics.this.sendSavedTokenLog();
                G2Metrics.this.sendSavedReadNotificationLog();
            }
        }, 15000, (long) (this.logSettings.logSendInterval.intValue() * 1000));
    }

    /* access modifiers changed from: private */
    public void startGetSettingRequest(Work... workArr) {
        List asList = Arrays.asList(workArr);
        if (asList.contains(Work.SendLog)) {
            sendSavedSessionLog();
        }
        if (asList.contains(Work.StartTimer)) {
            ParamLogSetting paramLogSetting = new ParamLogSetting();
            paramLogSetting.gameKey = this.gameKey;
            new ApiClient(this.application, paramLogSetting, new IAsyncApiRequestCallBack() {
                public void onFailure(Integer num) {
                    G2Metrics.this.startEventLogSendTimer();
                }

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

    /* access modifiers changed from: private */
    public void startHealthCheckTimer(final Work... workArr) {
        final Timer timer = new Timer();
        if (Constant.devModel) {
            Log.d("G2Metrics", "Start Health Check Timer.");
        }
        timer.schedule(new TimerTask() {
            public void run() {
                timer.cancel();
                G2Metrics.this.healthCheckThenDo(workArr);
            }
        }, (long) (this.logSettings.healthCheckInterval.intValue() * 1000), (long) (this.logSettings.healthCheckInterval.intValue() * 1000));
    }

    private void storeRegistrationId(Context context, String str) {
        SharedPreferences gCMPreferences = getGCMPreferences(context);
        int intValue = Integer.valueOf(this.appInfo.version).intValue();
        SharedPreferences.Editor edit = gCMPreferences.edit();
        edit.putString("registrationId", str);
        edit.putInt(Constants.PROPERTY_APP_VERSION, intValue);
        edit.putBoolean("sendFlag", false);
        edit.apply();
    }

    /* access modifiers changed from: private */
    public void thenDo(Work... workArr) {
        if (Arrays.asList(workArr).contains(Work.Launch)) {
            instance.launch(workArr);
        }
    }

    /* access modifiers changed from: private */
    public void writerUuid(String str) {
        try {
            FileOutputStream openFileOutput = this.application.openFileOutput(g2mUuidFileName, 0);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(openFileOutput));
            bufferedWriter.write(str);
            bufferedWriter.close();
            openFileOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void addDeviceTokenLog(String str, String str2) {
        if (!this.isDryRun) {
            storeRegistrationId(this.application, str2);
            EventValueDeviceToken eventValueDeviceToken = new EventValueDeviceToken();
            eventValueDeviceToken.device_token = str2;
            EntityLogData entityLogData = new EntityLogData();
            entityLogData.event_type = str;
            entityLogData.event_value = eventValueDeviceToken.toJson().toString();
            this.deviceTokenArray.clear();
            ArrayList arrayList = new ArrayList();
            String jSONObject = entityLogData.toJson().toString();
            arrayList.add(0, AppEventsConstants.EVENT_PARAM_VALUE_NO);
            arrayList.add(1, jSONObject);
            this.deviceTokenArray.add(arrayList);
            if (Constant.devModel) {
                for (int i = 0; i < this.deviceTokenArray.size(); i++) {
                    Log.d("G2Metrics", "[デバイストークン保持件数] idx = " + String.valueOf(i) + " 送信状態 = " + ((String) this.deviceTokenArray.get(i).get(0)) + " データ = " + ((String) this.deviceTokenArray.get(i).get(1)));
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void addEventLog(String str, ApiEntityBase apiEntityBase) {
        if (!this.isDryRun) {
            EntityLogData entityLogData = new EntityLogData();
            entityLogData.event_type = str;
            if (apiEntityBase != null) {
                entityLogData.event_value = apiEntityBase.toJson().toString();
            }
            FileManager.appendLog(this.application, eventLogFileName, entityLogData);
        }
    }

    /* access modifiers changed from: package-private */
    public void addNotificationLog(String str, ApiEntityBase apiEntityBase) {
        if (!this.isDryRun) {
            EntityLogData entityLogData = new EntityLogData();
            entityLogData.event_type = str;
            if (apiEntityBase != null) {
                entityLogData.event_value = apiEntityBase.toJson().toString();
            }
            FileManager.appendLog(this.application, notificationLogFileName, entityLogData);
        }
    }

    public void uncaughtException(Thread thread, Throwable th) {
        if (instance.catchUncaughtExceptions) {
            if (thread != null) {
                try {
                    instance.addExceptionReportLog("Report requested by g2m sdk.", th);
                    this.savedUncaughtExceptionHandler.uncaughtException(thread, th);
                } catch (Throwable th2) {
                    if (this.savedUncaughtExceptionHandler != null) {
                        this.savedUncaughtExceptionHandler.uncaughtException(thread, th);
                    }
                }
            }
        } else if (this.savedUncaughtExceptionHandler != null) {
            this.savedUncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }
}
