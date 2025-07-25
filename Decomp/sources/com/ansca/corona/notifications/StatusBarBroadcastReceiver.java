package com.ansca.corona.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class StatusBarBroadcastReceiver extends BroadcastReceiver {
    private static final String INTENT_EXTRA_ID_NAME = "id";

    public static Intent createContentIntentFrom(Context context, StatusBarNotificationSettings statusBarNotificationSettings) {
        Intent createDeleteIntentFrom = createDeleteIntentFrom(context, statusBarNotificationSettings);
        createDeleteIntentFrom.setAction("android.intent.action.VIEW");
        return createDeleteIntentFrom;
    }

    public static Intent createDeleteIntentFrom(Context context, StatusBarNotificationSettings statusBarNotificationSettings) {
        if (context == null || statusBarNotificationSettings == null) {
            throw new NullPointerException();
        }
        Intent intent = new Intent(context, StatusBarBroadcastReceiver.class);
        intent.setData(Uri.parse("notification://statusbar?id=" + Integer.toString(statusBarNotificationSettings.getId())));
        intent.setAction("android.intent.action.DELETE");
        intent.putExtra("id", statusBarNotificationSettings.getId());
        return intent;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000b, code lost:
        r2 = r10.getIntExtra("id", -1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onReceive(android.content.Context r9, android.content.Intent r10) {
        /*
            r8 = this;
            if (r10 != 0) goto L_0x0003
        L_0x0002:
            return
        L_0x0003:
            java.lang.String r6 = "id"
            boolean r6 = r10.hasExtra(r6)
            if (r6 == 0) goto L_0x0002
            java.lang.String r6 = "id"
            r7 = -1
            int r2 = r10.getIntExtra(r6, r7)
            com.ansca.corona.notifications.NotificationServices r3 = new com.ansca.corona.notifications.NotificationServices
            r3.<init>(r9)
            com.ansca.corona.notifications.StatusBarNotificationSettings r4 = r3.fetchStatusBarNotificationById(r2)
            if (r4 == 0) goto L_0x0002
            int r6 = r4.getId()
            r3.removeById(r6)
            java.lang.String r6 = "android.intent.action.VIEW"
            java.lang.String r7 = r10.getAction()
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x0002
            java.lang.String r0 = "inactive"
            com.ansca.corona.events.NotificationReceivedTask r1 = new com.ansca.corona.events.NotificationReceivedTask
            r1.<init>(r0, r4)
            java.util.Collection r6 = com.ansca.corona.CoronaRuntimeProvider.getAllCoronaRuntimes()
            java.util.Iterator r6 = r6.iterator()
        L_0x003f:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x0063
            java.lang.Object r5 = r6.next()
            com.ansca.corona.CoronaRuntime r5 = (com.ansca.corona.CoronaRuntime) r5
            boolean r7 = r5.isRunning()
            if (r7 == 0) goto L_0x0060
            java.lang.String r0 = "active"
        L_0x0053:
            com.ansca.corona.events.NotificationReceivedTask r1 = new com.ansca.corona.events.NotificationReceivedTask
            r1.<init>(r0, r4)
            com.ansca.corona.CoronaRuntimeTaskDispatcher r7 = r5.getTaskDispatcher()
            r7.send(r1)
            goto L_0x003f
        L_0x0060:
            java.lang.String r0 = "inactive"
            goto L_0x0053
        L_0x0063:
            android.content.Intent r10 = new android.content.Intent
            java.lang.Class<com.ansca.corona.CoronaActivity> r6 = com.ansca.corona.CoronaActivity.class
            r10.<init>(r9, r6)
            r6 = 268435456(0x10000000, float:2.5243549E-29)
            r10.addFlags(r6)
            r6 = 131072(0x20000, float:1.83671E-40)
            r10.addFlags(r6)
            java.lang.String r6 = "notification"
            android.os.Bundle r7 = r1.toBundle()
            r10.putExtra(r6, r7)
            r9.startActivity(r10)
            goto L_0x0002
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.notifications.StatusBarBroadcastReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }
}
