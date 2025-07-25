package com.ansca.corona;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.ansca.corona.notifications.NotificationServices;

public class SystemStartupBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (new NotificationServices(context).hasNotifications()) {
            context.startService(new Intent(context, CoronaService.class));
        }
    }
}
