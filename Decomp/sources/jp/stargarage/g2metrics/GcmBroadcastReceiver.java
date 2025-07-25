package jp.stargarage.g2metrics;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        try {
            startWakefulService(context, intent.setComponent(new ComponentName(context.getPackageName(), GcmIntentService.class.getName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
