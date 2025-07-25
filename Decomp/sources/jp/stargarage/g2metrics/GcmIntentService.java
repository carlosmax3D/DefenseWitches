package jp.stargarage.g2metrics;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.ansca.corona.events.NotificationReceivedTask;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import jp.newgate.game.android.dw.Constants;

public class GcmIntentService extends IntentService {
    public GcmIntentService() {
        super(GcmIntentService.class.getSimpleName());
    }

    private int getIcon(Context context) throws PackageManager.NameNotFoundException {
        int i = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getInt("g2metrics_ticker_icon");
        if (i != 0) {
            return i;
        }
        Resources resources = context.getResources();
        if (resources.getIdentifier("ic_launcher", "drawable", context.getPackageName()) != 0) {
            return resources.getIdentifier("ic_launcher", "drawable", context.getPackageName());
        }
        if (resources.getIdentifier("app_icon", "drawable", context.getPackageName()) != 0) {
            return resources.getIdentifier("app_icon", "drawable", context.getPackageName());
        }
        Log.e("G2Metrics", "[PUSH ICON] you need setting g2metrics_ticker_icon meta data.");
        return i;
    }

    private boolean getVibrate(Context context) throws PackageManager.NameNotFoundException {
        for (String equals : context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions) {
            if (equals.equals("android.permission.VIBRATE")) {
                return true;
            }
        }
        return false;
    }

    private void handleRegistration(Context context, Intent intent) {
        if (intent.getExtras().containsKey(Constants.PROPERTY_REG_ID)) {
            String stringExtra = intent.getStringExtra(Constants.PROPERTY_REG_ID);
            if (Constant.devModel) {
                Log.d("G2Metrics", "[PUSH通知] 登録ID = " + stringExtra);
            }
            G2Metrics.getInstance().addDeviceTokenLog("ps01", stringExtra);
        } else if (intent.getStringExtra("error") != null) {
            if (Constant.devModel) {
                Log.e("G2Metrics", intent.getStringExtra("error"));
            }
        } else if (intent.getStringExtra("unregistered") != null && Constant.devModel) {
            Log.e("G2Metrics", intent.getStringExtra("unregistered"));
        }
    }

    private boolean isForeground() {
        for (ActivityManager.RunningAppProcessInfo next : ((ActivityManager) getSystemService("activity")).getRunningAppProcesses()) {
            if (next.processName.equals(getPackageName()) && next.importance == 100) {
                return true;
            }
        }
        return false;
    }

    private void showNotification(Context context, Intent intent) throws PackageManager.NameNotFoundException {
        int icon = getIcon(context);
        String stringExtra = intent.getStringExtra("title");
        String stringExtra2 = intent.getStringExtra("message");
        boolean z = false;
        if (intent.getExtras().containsKey("sound")) {
            z = Boolean.valueOf(intent.getStringExtra("sound")).booleanValue();
        }
        boolean vibrate = getVibrate(context);
        Intent intent2 = new Intent(context, PushDialogActivity.class);
        intent2.putExtras(intent.getExtras());
        intent2.putExtra("showG2MPushDialog", false);
        intent2.setFlags(1342177280);
        PendingIntent activity = PendingIntent.getActivity(context, 0, intent2, DriveFile.MODE_READ_ONLY);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(stringExtra2);
        builder.setSmallIcon(icon);
        builder.setContentTitle(stringExtra);
        builder.setContentText(stringExtra2);
        builder.setContentIntent(activity);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        if (z && vibrate) {
            builder.setDefaults(7);
        } else if (z) {
            builder.setDefaults(5);
        } else {
            builder.setDefaults(4);
        }
        ((NotificationManager) context.getSystemService(NotificationReceivedTask.NAME)).notify("G2Metrics" + context.getPackageName(), 1, builder.build());
    }

    /* access modifiers changed from: protected */
    public void handleReceive(Context context, Intent intent) {
        String messageType = GoogleCloudMessaging.getInstance(context).getMessageType(intent);
        if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            if (intent.getStringExtra("segment_id") != null) {
                boolean isForeground = isForeground();
                try {
                    if ("PushToast".equals(getApplication().getPackageManager().getApplicationInfo(getApplication().getPackageName(), 128).metaData.getString("g2metrics_push_type")) && !isForeground) {
                        Intent intent2 = new Intent(context, PushDialogActivity.class);
                        intent2.putExtras(intent.getExtras());
                        intent2.putExtra("showG2MPushDialog", true);
                        intent2.setFlags(1350565888);
                        getApplicationContext().startActivity(intent2);
                    }
                    showNotification(context, intent);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
            if (Constant.devModel) {
                Log.d("G2Metrics", "[PUSH通知] GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR");
            }
        } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType) && Constant.devModel) {
            Log.d("G2Metrics", "[PUSH通知] GoogleCloudMessaging.MESSAGE_TYPE_DELETED");
        }
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        if (intent == null) {
            try {
                GcmBroadcastReceiver.completeWakefulIntent(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (Constant.devModel) {
                Log.d("G2Metrics", "[PUSH通知] 受信したintent = " + intent);
                Bundle extras = intent.getExtras();
                if (extras != null && !extras.isEmpty()) {
                    for (String str : extras.keySet()) {
                        Log.d("G2Metrics", "[PUSH通知] " + str + " = " + extras.get(str));
                    }
                    Log.d("G2Metrics", "[PUSH通知] title = " + extras.getString("title"));
                    Log.d("G2Metrics", "[PUSH通知] message = " + extras.getString("message"));
                    Log.d("G2Metrics", "[PUSH通知] sound = " + extras.getString("sound"));
                    Log.d("G2Metrics", "[PUSH通知] ab = " + extras.getString("ab"));
                    Log.d("G2Metrics", "[PUSH通知] history_id = " + extras.getString("history_id"));
                    Log.d("G2Metrics", "[PUSH通知] segment_id = " + extras.getString("segment_id"));
                }
            }
            Context applicationContext = getApplicationContext();
            if ("com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
                handleReceive(applicationContext, intent);
            } else if ("com.google.android.c2dm.intent.REGISTRATION".equals(intent.getAction())) {
                handleRegistration(applicationContext, intent);
            }
            GcmBroadcastReceiver.completeWakefulIntent(intent);
        }
    }
}
