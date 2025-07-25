package com.ansca.corona.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GoogleCloudMessagingBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        new GoogleCloudMessagingServices(context).process(intent);
    }
}
