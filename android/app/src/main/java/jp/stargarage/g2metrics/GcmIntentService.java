package jp.stargarage.g2metrics;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.ansca.corona.events.NotificationReceivedTask;
import com.google.android.gms.drive.DriveFile;

import org.json.JSONException;

import jp.newgate.game.android.dw.Constants;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
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
        for (String str : context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions) {
            if (str.equals("android.permission.VIBRATE")) {
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
            try {
                G2Metrics.getInstance().addDeviceTokenLog("ps01", stringExtra);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        if (intent.getStringExtra("error") != null) {
            if (Constant.devModel) {
                Log.e("G2Metrics", intent.getStringExtra("error"));
            }
        } else {
            if (intent.getStringExtra("unregistered") == null || !Constant.devModel) {
                return;
            }
            Log.e("G2Metrics", intent.getStringExtra("unregistered"));
        }
    }

    private boolean isForeground() {
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) getSystemService(android.content.Context.ACTIVITY_SERVICE)).getRunningAppProcesses()) {
            if (runningAppProcessInfo.processName.equals(getPackageName()) && runningAppProcessInfo.importance == 100) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("WrongConstant")
    private void showNotification(Context context, Intent intent) throws PackageManager.NameNotFoundException {
        int icon = getIcon(context);
        String stringExtra = intent.getStringExtra("title");
        String stringExtra2 = intent.getStringExtra("message");
        boolean zBooleanValue = intent.getExtras().containsKey("sound") ? Boolean.valueOf(intent.getStringExtra("sound")).booleanValue() : false;
        boolean vibrate = getVibrate(context);
        Intent intent2 = new Intent(context, (Class<?>) PushDialogActivity.class);
        intent2.putExtras(intent.getExtras());
        intent2.putExtra("showG2MPushDialog", false);
        intent2.setFlags(1342177280);
        PendingIntent activity = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(stringExtra2);
        builder.setSmallIcon(icon);
        builder.setContentTitle(stringExtra);
        builder.setContentText(stringExtra2);
        builder.setContentIntent(activity);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        if (zBooleanValue && vibrate) {
            builder.setDefaults(7);
        } else if (zBooleanValue) {
            builder.setDefaults(5);
        } else {
            builder.setDefaults(4);
        }
        ((NotificationManager) context.getSystemService(NotificationReceivedTask.NAME)).notify("G2Metrics" + context.getPackageName(), 1, builder.build());
    }

    @SuppressLint("WrongConstant")
    protected void handleReceive(Context context, Intent intent) {
        String messageType = null;//GoogleCloudMessaging.getInstance(context).getMessageType(intent);
        if (messageType != null) { //!GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)
            if (1 != 0) { //GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)
                if (Constant.devModel) {
                    Log.d("G2Metrics", "[PUSH通知] GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR");
                    return;
                }
                return;
            } else {
                if (1 != 0) { //GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType) && Constant.devModel
                    Log.d("G2Metrics", "[PUSH通知] GoogleCloudMessaging.MESSAGE_TYPE_DELETED");
                    return;
                }
                return;
            }
        }
        if (intent.getStringExtra("segment_id") != null) {
            boolean zIsForeground = isForeground();
            try {
                if ("PushToast".equals(getApplication().getPackageManager().getApplicationInfo(getApplication().getPackageName(), 128).metaData.getString("g2metrics_push_type")) && !zIsForeground) {
                    Intent intent2 = new Intent(context, (Class<?>) PushDialogActivity.class);
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
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        try {
            if (intent == null) {
//                GcmBroadcastReceiver.completeWakefulIntent(intent);
                return;
            }
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
//            GcmBroadcastReceiver.completeWakefulIntent(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
