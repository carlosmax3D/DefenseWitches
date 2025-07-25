package com.ansca.corona.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {
    private static final String INTENT_EXTRA_ID_NAME = "id";

    public static Intent createIntentFrom(Context context, ScheduledNotificationSettings scheduledNotificationSettings) {
        if (context == null || scheduledNotificationSettings == null) {
            throw new NullPointerException();
        }
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.setData(Uri.parse("notification://alarm?id=" + Integer.toString(scheduledNotificationSettings.getId())));
        intent.putExtra("id", scheduledNotificationSettings.getId());
        return intent;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000b, code lost:
        r0 = r7.getIntExtra("id", -1);
        r1 = new com.ansca.corona.notifications.NotificationServices(r6);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onReceive(android.content.Context r6, android.content.Intent r7) {
        /*
            r5 = this;
            if (r7 != 0) goto L_0x0003
        L_0x0002:
            return
        L_0x0003:
            java.lang.String r3 = "id"
            boolean r3 = r7.hasExtra(r3)
            if (r3 == 0) goto L_0x0002
            java.lang.String r3 = "id"
            r4 = -1
            int r0 = r7.getIntExtra(r3, r4)
            com.ansca.corona.notifications.NotificationServices r1 = new com.ansca.corona.notifications.NotificationServices
            r1.<init>(r6)
            com.ansca.corona.notifications.ScheduledNotificationSettings r2 = r1.fetchScheduledNotificationById(r0)
            if (r2 == 0) goto L_0x0002
            com.ansca.corona.notifications.StatusBarNotificationSettings r3 = r2.getStatusBarSettings()
            r1.post((com.ansca.corona.notifications.NotificationSettings) r3)
            goto L_0x0002
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.notifications.AlarmManagerBroadcastReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }
}
