package com.ansca.corona.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.ansca.corona.ApplicationContextProvider;
import com.ansca.corona.CoronaData;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeProvider;
import com.ansca.corona.events.NotificationRegistrationTask;
import com.ansca.corona.purchasing.StoreName;
import com.ansca.corona.storage.FileContentProvider;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import java.util.LinkedList;
import jp.stargarage.g2metrics.BuildConfig;
import org.json.JSONObject;

public final class GoogleCloudMessagingServices extends ApplicationContextProvider {
    private static final String CORONA_PREFERENCES_NAME = "Corona";
    private static final String CORONA_PREFERENCE_PROJECT_NUMBERS_KEY = "google-cloud-messaging-project-numbers";
    private static final String CORONA_PREFERENCE_REGISTRATION_ID_KEY = "google-cloud-messaging-registration-id";
    private static final String GOOGLE_SERVICE_FRAMEWORK_PACKAGE_NAME = "com.google.android.gsf";
    private static final String INTENT_EXTRA_ERROR_ID = "error";
    private static final String INTENT_EXTRA_MESSAGE_TYPE = "message_type";
    private static final String INTENT_EXTRA_PENDING_INTENT = "app";
    private static final String INTENT_EXTRA_PROJECT_NUMBERS = "sender";
    private static final String INTENT_EXTRA_REGISTRATION_ID = "registration_id";
    private static final String INTENT_EXTRA_TOTAL_DELETED = "total_deleted";
    private static final String INTENT_EXTRA_UNREGISTERED = "unregistered";
    private static LinkedList<Operation> sOperationQueue = new LinkedList<>();
    private static Operation sPendingOperation = null;
    private static String sRegisteredProjectNumbers = null;
    private static String sRegistrationId = null;

    private static abstract class Operation implements Runnable {
        public abstract void run();
    }

    private static class RegisterOperation extends Operation {
        private String fProjectNumbers;

        public RegisterOperation(String str) {
            if (str == null) {
                throw new NullPointerException();
            }
            this.fProjectNumbers = str;
        }

        public String getProjectNumbers() {
            return this.fProjectNumbers;
        }

        public void run() {
            Context applicationContext = CoronaEnvironment.getApplicationContext();
            if (applicationContext != null) {
                Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
                intent.setPackage(GoogleCloudMessagingServices.GOOGLE_SERVICE_FRAMEWORK_PACKAGE_NAME);
                intent.putExtra(GoogleCloudMessagingServices.INTENT_EXTRA_PENDING_INTENT, PendingIntent.getBroadcast(applicationContext, 0, new Intent(), 0));
                intent.putExtra(GoogleCloudMessagingServices.INTENT_EXTRA_PROJECT_NUMBERS, this.fProjectNumbers);
                applicationContext.startService(intent);
            }
        }
    }

    private static class UnregisterOperation extends Operation {
        public void run() {
            Context applicationContext = CoronaEnvironment.getApplicationContext();
            if (applicationContext != null) {
                Intent intent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
                intent.setPackage(GoogleCloudMessagingServices.GOOGLE_SERVICE_FRAMEWORK_PACKAGE_NAME);
                intent.putExtra(GoogleCloudMessagingServices.INTENT_EXTRA_PENDING_INTENT, PendingIntent.getBroadcast(applicationContext, 0, new Intent(), 0));
                applicationContext.startService(intent);
            }
        }
    }

    public GoogleCloudMessagingServices(Context context) {
        super(context);
        synchronized (GoogleCloudMessagingServices.class) {
            if (sRegistrationId == null) {
                sRegistrationId = BuildConfig.FLAVOR;
                sRegisteredProjectNumbers = BuildConfig.FLAVOR;
                try {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(CORONA_PREFERENCES_NAME, 0);
                    String string = sharedPreferences.getString(CORONA_PREFERENCE_REGISTRATION_ID_KEY, BuildConfig.FLAVOR);
                    if (string != null) {
                        sRegistrationId = string;
                    }
                    String string2 = sharedPreferences.getString(CORONA_PREFERENCE_PROJECT_NUMBERS_KEY, BuildConfig.FLAVOR);
                    if (string2 != null) {
                        sRegisteredProjectNumbers = string2;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void executeNextQueuedOperation() {
        synchronized (GoogleCloudMessagingServices.class) {
            if (sPendingOperation == null) {
                if (!sOperationQueue.isEmpty()) {
                    sPendingOperation = sOperationQueue.removeFirst();
                    sPendingOperation.run();
                }
            }
        }
    }

    private void onReceivedNotification(Bundle bundle) {
        if (bundle != null) {
            NotificationServices notificationServices = new NotificationServices(getApplicationContext());
            StatusBarNotificationSettings statusBarNotificationSettings = new StatusBarNotificationSettings();
            statusBarNotificationSettings.setId(notificationServices.reserveId());
            statusBarNotificationSettings.setSourceName(StoreName.GOOGLE);
            statusBarNotificationSettings.setSourceLocal(false);
            statusBarNotificationSettings.setSourceDataName("androidGcmBundle");
            statusBarNotificationSettings.setSourceData(CoronaData.from((Object) bundle));
            String str = (String) getApplicationContext().getPackageManager().getApplicationLabel(getApplicationContext().getApplicationInfo());
            statusBarNotificationSettings.setContentTitle(str);
            statusBarNotificationSettings.setTickerText(str);
            Object obj = bundle.get("alert");
            if (obj instanceof String) {
                String str2 = (String) obj;
                boolean z = false;
                try {
                    JSONObject jSONObject = new JSONObject(str2);
                    Object opt = jSONObject.opt("title");
                    if (opt instanceof String) {
                        statusBarNotificationSettings.setContentTitle((String) opt);
                    }
                    Object opt2 = jSONObject.opt("body");
                    if (opt2 instanceof String) {
                        statusBarNotificationSettings.setContentText((String) opt2);
                        statusBarNotificationSettings.setTickerText((String) opt2);
                    } else {
                        Object opt3 = jSONObject.opt("text");
                        if (opt3 instanceof String) {
                            statusBarNotificationSettings.setContentText((String) opt3);
                            statusBarNotificationSettings.setTickerText((String) opt3);
                        }
                    }
                    Object opt4 = jSONObject.opt("number");
                    if (opt4 instanceof Number) {
                        statusBarNotificationSettings.setBadgeNumber(((Number) opt4).intValue());
                    }
                    z = true;
                } catch (Exception e) {
                }
                if (!z) {
                    statusBarNotificationSettings.setContentText(str2);
                    statusBarNotificationSettings.setTickerText(str2);
                }
            } else if (obj == null) {
                Object obj2 = bundle.get("title");
                if (obj2 instanceof String) {
                    statusBarNotificationSettings.setContentTitle((String) obj2);
                }
                Object obj3 = bundle.get("body");
                if (obj3 instanceof String) {
                    statusBarNotificationSettings.setContentText((String) obj3);
                    statusBarNotificationSettings.setTickerText((String) obj3);
                } else {
                    Object obj4 = bundle.get("text");
                    if (obj4 instanceof String) {
                        statusBarNotificationSettings.setContentText((String) obj4);
                        statusBarNotificationSettings.setTickerText((String) obj4);
                    }
                }
                Object obj5 = bundle.get("number");
                if (obj5 instanceof Number) {
                    statusBarNotificationSettings.setBadgeNumber(((Number) obj5).intValue());
                }
            }
            Object obj6 = bundle.get("sound");
            if (obj6 instanceof String) {
                Uri uri = null;
                try {
                    uri = FileContentProvider.createContentUriForFile(getApplicationContext(), ((String) obj6).trim());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                statusBarNotificationSettings.setSoundFileUri(uri);
            }
            CoronaData.Table table = null;
            Object obj7 = bundle.get("custom");
            if (obj7 instanceof String) {
                try {
                    table = CoronaData.Table.from(new JSONObject((String) obj7));
                } catch (Exception e3) {
                }
            } else if (obj7 instanceof Bundle) {
                table = CoronaData.Table.from((Bundle) obj7);
            }
            if (table != null) {
                statusBarNotificationSettings.setData(table);
            }
            notificationServices.post((NotificationSettings) statusBarNotificationSettings);
        }
    }

    private void onRegisteredWith(String str, String str2) {
        if (str != null && str.length() > 0) {
            saveRegistrationInformation(str, str2);
            NotificationRegistrationTask notificationRegistrationTask = new NotificationRegistrationTask(str);
            for (CoronaRuntime taskDispatcher : CoronaRuntimeProvider.getAllCoronaRuntimes()) {
                taskDispatcher.getTaskDispatcher().send(notificationRegistrationTask);
            }
        }
    }

    private void onUnregistered() {
        saveRegistrationInformation(BuildConfig.FLAVOR, BuildConfig.FLAVOR);
    }

    private void saveRegistrationInformation(String str, String str2) {
        synchronized (GoogleCloudMessagingServices.class) {
            if (str == null) {
                str = BuildConfig.FLAVOR;
            }
            if (str2 == null) {
                str2 = BuildConfig.FLAVOR;
            }
            sRegistrationId = str;
            sRegisteredProjectNumbers = str2;
            try {
                SharedPreferences.Editor edit = getApplicationContext().getSharedPreferences(CORONA_PREFERENCES_NAME, 0).edit();
                edit.putString(CORONA_PREFERENCE_REGISTRATION_ID_KEY, str);
                edit.putString(CORONA_PREFERENCE_PROJECT_NUMBERS_KEY, str2);
                edit.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getCommaSeparatedRegisteredProjectNumbers() {
        String str;
        synchronized (GoogleCloudMessagingServices.class) {
            str = sRegisteredProjectNumbers == null ? BuildConfig.FLAVOR : sRegisteredProjectNumbers;
        }
        return str;
    }

    public String[] getRegisteredProjectNumbers() {
        String[] split;
        synchronized (GoogleCloudMessagingServices.class) {
            split = (sRegisteredProjectNumbers == null || sRegisteredProjectNumbers.length() <= 0) ? null : sRegisteredProjectNumbers.split(",");
        }
        return split;
    }

    public String getRegistrationId() {
        return sRegistrationId;
    }

    public boolean isRegistered() {
        return sRegistrationId.length() > 0;
    }

    public boolean isUnregistered() {
        return !isRegistered();
    }

    /* access modifiers changed from: package-private */
    public void process(Intent intent) {
        String action;
        if (intent != null && (action = intent.getAction()) != null && action.length() > 0) {
            if (action.equals("com.google.android.c2dm.intent.REGISTRATION")) {
                String stringExtra = intent.getStringExtra("registration_id");
                String stringExtra2 = intent.getStringExtra(INTENT_EXTRA_UNREGISTERED);
                String stringExtra3 = intent.getStringExtra("error");
                if (stringExtra != null && stringExtra.length() > 0) {
                    String str = BuildConfig.FLAVOR;
                    synchronized (GoogleCloudMessagingServices.class) {
                        if (sPendingOperation instanceof RegisterOperation) {
                            str = ((RegisterOperation) sPendingOperation).getProjectNumbers();
                        }
                        sPendingOperation = null;
                    }
                    onRegisteredWith(stringExtra, str);
                    executeNextQueuedOperation();
                } else if (stringExtra2 != null) {
                    synchronized (GoogleCloudMessagingServices.class) {
                        sPendingOperation = null;
                    }
                    onUnregistered();
                    executeNextQueuedOperation();
                } else if (stringExtra3 != null && stringExtra3.length() > 0) {
                    synchronized (GoogleCloudMessagingServices.class) {
                        Log.v(CORONA_PREFERENCES_NAME, "Google Cloud Messaging Registration Error: " + stringExtra3);
                        if ((sPendingOperation instanceof RegisterOperation) && sOperationQueue.size() > 0) {
                            sPendingOperation = null;
                            executeNextQueuedOperation();
                        } else if (stringExtra3.equals(GoogleCloudMessaging.ERROR_SERVICE_NOT_AVAILABLE) && sPendingOperation != null) {
                            new Handler(getApplicationContext().getMainLooper()).postDelayed(sPendingOperation, 60000);
                        }
                    }
                }
            } else if (action.equals("com.google.android.c2dm.intent.RECEIVE")) {
                String stringExtra4 = intent.getStringExtra(INTENT_EXTRA_MESSAGE_TYPE);
                if (stringExtra4 == null || stringExtra4.length() <= 0) {
                    onReceivedNotification(intent.getExtras());
                } else if (stringExtra4.equals(GoogleCloudMessaging.MESSAGE_TYPE_DELETED)) {
                    Log.v(CORONA_PREFERENCES_NAME, "Google Cloud Messaging has deleted messages.");
                } else {
                    Log.v(CORONA_PREFERENCES_NAME, "Received unkown message type '" + stringExtra4 + "' from Google Cloud Messaging.");
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void register(java.lang.String r5) {
        /*
            r4 = this;
            java.lang.Class<com.ansca.corona.notifications.GoogleCloudMessagingServices> r2 = com.ansca.corona.notifications.GoogleCloudMessagingServices.class
            monitor-enter(r2)
            if (r5 == 0) goto L_0x000b
            int r1 = r5.length()     // Catch:{ all -> 0x0026 }
            if (r1 > 0) goto L_0x000d
        L_0x000b:
            monitor-exit(r2)     // Catch:{ all -> 0x0026 }
        L_0x000c:
            return
        L_0x000d:
            java.util.LinkedList<com.ansca.corona.notifications.GoogleCloudMessagingServices$Operation> r1 = sOperationQueue     // Catch:{ all -> 0x0026 }
            r1.clear()     // Catch:{ all -> 0x0026 }
            boolean r1 = r4.isRegistered()     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x0029
            java.lang.String r1 = sRegisteredProjectNumbers     // Catch:{ all -> 0x0026 }
            boolean r1 = r1.equals(r5)     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x0029
            com.ansca.corona.notifications.GoogleCloudMessagingServices$Operation r1 = sPendingOperation     // Catch:{ all -> 0x0026 }
            if (r1 != 0) goto L_0x0029
            monitor-exit(r2)     // Catch:{ all -> 0x0026 }
            goto L_0x000c
        L_0x0026:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0026 }
            throw r1
        L_0x0029:
            com.ansca.corona.notifications.GoogleCloudMessagingServices$Operation r1 = sPendingOperation     // Catch:{ all -> 0x0026 }
            boolean r1 = r1 instanceof com.ansca.corona.notifications.GoogleCloudMessagingServices.RegisterOperation     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x003f
            com.ansca.corona.notifications.GoogleCloudMessagingServices$Operation r0 = sPendingOperation     // Catch:{ all -> 0x0026 }
            com.ansca.corona.notifications.GoogleCloudMessagingServices$RegisterOperation r0 = (com.ansca.corona.notifications.GoogleCloudMessagingServices.RegisterOperation) r0     // Catch:{ all -> 0x0026 }
            java.lang.String r1 = r0.getProjectNumbers()     // Catch:{ all -> 0x0026 }
            boolean r1 = r1.equals(r5)     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x003f
            monitor-exit(r2)     // Catch:{ all -> 0x0026 }
            goto L_0x000c
        L_0x003f:
            com.ansca.corona.notifications.GoogleCloudMessagingServices$Operation r1 = sPendingOperation     // Catch:{ all -> 0x0026 }
            if (r1 != 0) goto L_0x006a
            boolean r1 = r4.isRegistered()     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x005b
            java.lang.String r1 = sRegisteredProjectNumbers     // Catch:{ all -> 0x0026 }
            boolean r1 = r1.equals(r5)     // Catch:{ all -> 0x0026 }
            if (r1 != 0) goto L_0x005b
            java.util.LinkedList<com.ansca.corona.notifications.GoogleCloudMessagingServices$Operation> r1 = sOperationQueue     // Catch:{ all -> 0x0026 }
            com.ansca.corona.notifications.GoogleCloudMessagingServices$UnregisterOperation r3 = new com.ansca.corona.notifications.GoogleCloudMessagingServices$UnregisterOperation     // Catch:{ all -> 0x0026 }
            r3.<init>()     // Catch:{ all -> 0x0026 }
            r1.addLast(r3)     // Catch:{ all -> 0x0026 }
        L_0x005b:
            com.ansca.corona.notifications.GoogleCloudMessagingServices$RegisterOperation r0 = new com.ansca.corona.notifications.GoogleCloudMessagingServices$RegisterOperation     // Catch:{ all -> 0x0026 }
            r0.<init>(r5)     // Catch:{ all -> 0x0026 }
            java.util.LinkedList<com.ansca.corona.notifications.GoogleCloudMessagingServices$Operation> r1 = sOperationQueue     // Catch:{ all -> 0x0026 }
            r1.addLast(r0)     // Catch:{ all -> 0x0026 }
            r4.executeNextQueuedOperation()     // Catch:{ all -> 0x0026 }
            monitor-exit(r2)     // Catch:{ all -> 0x0026 }
            goto L_0x000c
        L_0x006a:
            com.ansca.corona.notifications.GoogleCloudMessagingServices$Operation r1 = sPendingOperation     // Catch:{ all -> 0x0026 }
            boolean r1 = r1 instanceof com.ansca.corona.notifications.GoogleCloudMessagingServices.RegisterOperation     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x005b
            java.util.LinkedList<com.ansca.corona.notifications.GoogleCloudMessagingServices$Operation> r1 = sOperationQueue     // Catch:{ all -> 0x0026 }
            com.ansca.corona.notifications.GoogleCloudMessagingServices$UnregisterOperation r3 = new com.ansca.corona.notifications.GoogleCloudMessagingServices$UnregisterOperation     // Catch:{ all -> 0x0026 }
            r3.<init>()     // Catch:{ all -> 0x0026 }
            r1.addLast(r3)     // Catch:{ all -> 0x0026 }
            goto L_0x005b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.notifications.GoogleCloudMessagingServices.register(java.lang.String):void");
    }

    public void register(String[] strArr) {
        if (strArr != null && strArr.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (String str : strArr) {
                if (str != null && str.length() > 0) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append(str);
                }
            }
            register(sb.toString());
        }
    }

    public void unregister() {
        sOperationQueue.clear();
        if (!(sPendingOperation instanceof UnregisterOperation)) {
            if (sPendingOperation != null || !isUnregistered()) {
                sOperationQueue.addLast(new UnregisterOperation());
                executeNextQueuedOperation();
            }
        }
    }
}
