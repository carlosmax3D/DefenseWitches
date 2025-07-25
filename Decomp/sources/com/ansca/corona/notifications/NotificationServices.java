package com.ansca.corona.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import com.ansca.corona.ApplicationContextProvider;
import com.ansca.corona.CoronaData;
import com.ansca.corona.storage.ResourceServices;
import com.facebook.share.internal.ShareConstants;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import jp.stargarage.g2metrics.BuildConfig;
import org.xmlpull.v1.XmlPullParser;

public final class NotificationServices extends ApplicationContextProvider {
    private static final String DEFAULT_STATUS_BAR_TAG = "corona";
    private static NotificationSettingsCollection<NotificationSettings> sNotificationCollection = new NotificationSettingsCollection<>();
    private static HashSet<Integer> sReservedNotificationIdSet = new HashSet<>();
    private static boolean sWasInitialized = false;

    private static class ApiLevel19 {
        private ApiLevel19() {
        }

        public static void alarmManagerSetExact(AlarmManager alarmManager, int i, long j, PendingIntent pendingIntent) {
            if (alarmManager != null) {
                alarmManager.setExact(i, j, pendingIntent);
            }
        }
    }

    private static class Gingerbread {
        private Gingerbread() {
        }

        public static Notification.Builder createNotificationBuilderFrom(Context context, StatusBarNotificationSettings statusBarNotificationSettings) {
            boolean z = true;
            if (context == null || statusBarNotificationSettings == null) {
                return null;
            }
            int iconResourceId = statusBarNotificationSettings.getIconResourceId();
            if (iconResourceId == 0) {
                iconResourceId = new ResourceServices(context).getDrawableResourceId(StatusBarNotificationSettings.DEFAULT_ICON_RESOURCE_NAME);
            }
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle(statusBarNotificationSettings.getContentTitle());
            builder.setContentText(statusBarNotificationSettings.getContentText());
            builder.setTicker(statusBarNotificationSettings.getTickerText());
            builder.setWhen(statusBarNotificationSettings.getTimestamp().getTime());
            builder.setSmallIcon(iconResourceId);
            builder.setNumber(statusBarNotificationSettings.getBadgeNumber());
            if (statusBarNotificationSettings.getSoundFileUri() != null) {
                builder.setSound(statusBarNotificationSettings.getSoundFileUri());
            } else {
                builder.setDefaults(1);
            }
            builder.setOnlyAlertOnce(true);
            builder.setAutoCancel(statusBarNotificationSettings.isRemovable());
            if (statusBarNotificationSettings.isRemovable()) {
                z = false;
            }
            builder.setOngoing(z);
            builder.setContentIntent(PendingIntent.getBroadcast(context, 0, StatusBarBroadcastReceiver.createContentIntentFrom(context, statusBarNotificationSettings), 0));
            builder.setDeleteIntent(PendingIntent.getBroadcast(context, 0, StatusBarBroadcastReceiver.createDeleteIntentFrom(context, statusBarNotificationSettings), 0));
            return builder;
        }

        public static Notification createNotificationFrom(Context context, StatusBarNotificationSettings statusBarNotificationSettings) {
            Notification.Builder createNotificationBuilderFrom = createNotificationBuilderFrom(context, statusBarNotificationSettings);
            if (createNotificationBuilderFrom == null) {
                return null;
            }
            return createNotificationBuilderFrom.getNotification();
        }
    }

    private static class JellyBean {
        private JellyBean() {
        }

        public static Notification createNotificationFrom(Context context, StatusBarNotificationSettings statusBarNotificationSettings) {
            Notification.Builder createNotificationBuilderFrom = Gingerbread.createNotificationBuilderFrom(context, statusBarNotificationSettings);
            if (createNotificationBuilderFrom == null) {
                return null;
            }
            return createNotificationBuilderFrom.build();
        }
    }

    public NotificationServices(Context context) {
        super(context);
        synchronized (NotificationServices.class) {
            if (!sWasInitialized) {
                NotificationSettingsCollection notificationSettingsCollection = new NotificationSettingsCollection();
                loadSettingsTo(notificationSettingsCollection);
                post(notificationSettingsCollection);
                sWasInitialized = true;
            }
        }
    }

    private <T extends NotificationSettings> NotificationSettingsCollection<T> fetchNotificationsByType(Class<T> cls) {
        NotificationSettingsCollection<T> notificationSettingsCollection;
        synchronized (NotificationServices.class) {
            notificationSettingsCollection = new NotificationSettingsCollection<>();
            if (cls != null) {
                Iterator<NotificationSettings> it = sNotificationCollection.iterator();
                while (it.hasNext()) {
                    NotificationSettings next = it.next();
                    if (cls.isInstance(next)) {
                        notificationSettingsCollection.add(next.clone());
                    }
                }
            }
        }
        return notificationSettingsCollection;
    }

    private NotificationSettings loadSettingsFrom(XmlPullParser xmlPullParser) {
        synchronized (NotificationServices.class) {
            if (xmlPullParser == null) {
                return null;
            }
            try {
                if (xmlPullParser.getEventType() != 2) {
                    return null;
                }
                if ("scheduled".equals(xmlPullParser.getName())) {
                    ScheduledNotificationSettings scheduledNotificationSettings = new ScheduledNotificationSettings();
                    scheduledNotificationSettings.setEndTime(new Date(Long.parseLong(xmlPullParser.getAttributeValue(BuildConfig.FLAVOR, "endTime"))));
                    int nextTag = xmlPullParser.nextTag();
                    if (xmlPullParser.getEventType() == 2 && "statusBar".equals(xmlPullParser.getName())) {
                        NotificationSettings loadSettingsFrom = loadSettingsFrom(xmlPullParser);
                        if (loadSettingsFrom instanceof StatusBarNotificationSettings) {
                            scheduledNotificationSettings.getStatusBarSettings().copyFrom((StatusBarNotificationSettings) loadSettingsFrom);
                            return scheduledNotificationSettings;
                        }
                    }
                } else if ("statusBar".equals(xmlPullParser.getName())) {
                    StatusBarNotificationSettings statusBarNotificationSettings = new StatusBarNotificationSettings();
                    statusBarNotificationSettings.setId(Integer.parseInt(xmlPullParser.getAttributeValue(BuildConfig.FLAVOR, ShareConstants.WEB_DIALOG_PARAM_ID)));
                    statusBarNotificationSettings.setTimestamp(new Date(Long.parseLong(xmlPullParser.getAttributeValue(BuildConfig.FLAVOR, "timestamp"))));
                    statusBarNotificationSettings.setContentTitle(xmlPullParser.getAttributeValue(BuildConfig.FLAVOR, "contentTitle"));
                    statusBarNotificationSettings.setContentText(xmlPullParser.getAttributeValue(BuildConfig.FLAVOR, "contentText"));
                    statusBarNotificationSettings.setTickerText(xmlPullParser.getAttributeValue(BuildConfig.FLAVOR, "tickerText"));
                    statusBarNotificationSettings.setIconResourceName(xmlPullParser.getAttributeValue(BuildConfig.FLAVOR, "iconResourceName"));
                    statusBarNotificationSettings.setBadgeNumber(Integer.parseInt(xmlPullParser.getAttributeValue(BuildConfig.FLAVOR, "badgeNumber")));
                    String attributeValue = xmlPullParser.getAttributeValue(BuildConfig.FLAVOR, "soundFileUri");
                    if (attributeValue != null && attributeValue.length() > 0) {
                        statusBarNotificationSettings.setSoundFileUri(Uri.parse(attributeValue));
                    }
                    statusBarNotificationSettings.setSourceName(xmlPullParser.getAttributeValue(BuildConfig.FLAVOR, "sourceName"));
                    String attributeValue2 = xmlPullParser.getAttributeValue(BuildConfig.FLAVOR, "isSourceLocal");
                    if (attributeValue2 != null && attributeValue2.length() > 0) {
                        statusBarNotificationSettings.setSourceLocal(Boolean.valueOf(attributeValue2).booleanValue());
                    }
                    while (true) {
                        int nextTag2 = xmlPullParser.nextTag();
                        if (xmlPullParser.getEventType() == 2) {
                            if ("sourceData".equals(xmlPullParser.getName())) {
                                statusBarNotificationSettings.setSourceDataName(xmlPullParser.getAttributeValue(BuildConfig.FLAVOR, "name"));
                                statusBarNotificationSettings.setSourceData(CoronaData.from(xmlPullParser));
                            } else if ("data".equals(xmlPullParser.getName())) {
                                CoronaData from = CoronaData.from(xmlPullParser);
                                if (from instanceof CoronaData.Table) {
                                    statusBarNotificationSettings.setData((CoronaData.Table) from);
                                }
                            }
                        } else if (xmlPullParser.getEventType() == 3 && "statusBar".equals(xmlPullParser.getName())) {
                            return statusBarNotificationSettings;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0061 A[SYNTHETIC, Splitter:B:37:0x0061] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x006a A[SYNTHETIC, Splitter:B:42:0x006a] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:29:0x0056=Splitter:B:29:0x0056, B:44:0x006d=Splitter:B:44:0x006d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void loadSettingsTo(com.ansca.corona.notifications.NotificationSettingsCollection<com.ansca.corona.notifications.NotificationSettings> r12) {
        /*
            r11 = this;
            java.lang.Class<com.ansca.corona.notifications.NotificationServices> r9 = com.ansca.corona.notifications.NotificationServices.class
            monitor-enter(r9)
            if (r12 != 0) goto L_0x0007
            monitor-exit(r9)     // Catch:{ all -> 0x0028 }
        L_0x0006:
            return
        L_0x0007:
            r12.clear()     // Catch:{ all -> 0x0028 }
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x0028 }
            android.content.Context r8 = getApplicationContext()     // Catch:{ all -> 0x0028 }
            java.io.File r8 = r8.getCacheDir()     // Catch:{ all -> 0x0028 }
            java.lang.String r10 = ".system"
            r1.<init>(r8, r10)     // Catch:{ all -> 0x0028 }
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x0028 }
            java.lang.String r8 = "NotificationSettings.xml"
            r2.<init>(r1, r8)     // Catch:{ all -> 0x0028 }
            boolean r8 = r2.exists()     // Catch:{ all -> 0x0028 }
            if (r8 != 0) goto L_0x002b
            monitor-exit(r9)     // Catch:{ all -> 0x0028 }
            goto L_0x0006
        L_0x0028:
            r8 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x0028 }
            throw r8
        L_0x002b:
            r3 = 0
            org.xmlpull.v1.XmlPullParser r7 = android.util.Xml.newPullParser()     // Catch:{ Exception -> 0x005b }
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ Exception -> 0x005b }
            r4.<init>(r2)     // Catch:{ Exception -> 0x005b }
            r7.setInput(r4)     // Catch:{ Exception -> 0x0073, all -> 0x0070 }
            int r6 = r7.getEventType()     // Catch:{ Exception -> 0x0073, all -> 0x0070 }
        L_0x003c:
            r8 = 1
            if (r6 == r8) goto L_0x0050
            r8 = 2
            if (r6 != r8) goto L_0x004b
            com.ansca.corona.notifications.NotificationSettings r5 = r11.loadSettingsFrom(r7)     // Catch:{ Exception -> 0x0073, all -> 0x0070 }
            if (r5 == 0) goto L_0x004b
            r12.add(r5)     // Catch:{ Exception -> 0x0073, all -> 0x0070 }
        L_0x004b:
            int r6 = r7.next()     // Catch:{ Exception -> 0x0073, all -> 0x0070 }
            goto L_0x003c
        L_0x0050:
            if (r4 == 0) goto L_0x0076
            r4.close()     // Catch:{ Exception -> 0x0058 }
            r3 = r4
        L_0x0056:
            monitor-exit(r9)     // Catch:{ all -> 0x0028 }
            goto L_0x0006
        L_0x0058:
            r8 = move-exception
            r3 = r4
            goto L_0x0056
        L_0x005b:
            r0 = move-exception
        L_0x005c:
            r0.printStackTrace()     // Catch:{ all -> 0x0067 }
            if (r3 == 0) goto L_0x0056
            r3.close()     // Catch:{ Exception -> 0x0065 }
            goto L_0x0056
        L_0x0065:
            r8 = move-exception
            goto L_0x0056
        L_0x0067:
            r8 = move-exception
        L_0x0068:
            if (r3 == 0) goto L_0x006d
            r3.close()     // Catch:{ Exception -> 0x006e }
        L_0x006d:
            throw r8     // Catch:{ all -> 0x0028 }
        L_0x006e:
            r10 = move-exception
            goto L_0x006d
        L_0x0070:
            r8 = move-exception
            r3 = r4
            goto L_0x0068
        L_0x0073:
            r0 = move-exception
            r3 = r4
            goto L_0x005c
        L_0x0076:
            r3 = r4
            goto L_0x0056
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.notifications.NotificationServices.loadSettingsTo(com.ansca.corona.notifications.NotificationSettingsCollection):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void postAndUpdateCollectionWith(com.ansca.corona.notifications.NotificationSettings r12) {
        /*
            r11 = this;
            java.lang.Class<com.ansca.corona.notifications.NotificationServices> r7 = com.ansca.corona.notifications.NotificationServices.class
            monitor-enter(r7)
            if (r12 != 0) goto L_0x0007
            monitor-exit(r7)     // Catch:{ all -> 0x0078 }
        L_0x0006:
            return
        L_0x0007:
            boolean r6 = r12 instanceof com.ansca.corona.notifications.ScheduledNotificationSettings     // Catch:{ all -> 0x0078 }
            if (r6 == 0) goto L_0x0022
            r0 = r12
            com.ansca.corona.notifications.ScheduledNotificationSettings r0 = (com.ansca.corona.notifications.ScheduledNotificationSettings) r0     // Catch:{ all -> 0x0078 }
            r4 = r0
            java.util.Date r1 = new java.util.Date     // Catch:{ all -> 0x0078 }
            r1.<init>()     // Catch:{ all -> 0x0078 }
            java.util.Date r6 = r4.getEndTime()     // Catch:{ all -> 0x0078 }
            int r6 = r6.compareTo(r1)     // Catch:{ all -> 0x0078 }
            if (r6 > 0) goto L_0x0022
            com.ansca.corona.notifications.StatusBarNotificationSettings r12 = r4.getStatusBarSettings()     // Catch:{ all -> 0x0078 }
        L_0x0022:
            r11.postSystemNotification(r12)     // Catch:{ all -> 0x0078 }
            com.ansca.corona.notifications.NotificationSettingsCollection<com.ansca.corona.notifications.NotificationSettings> r6 = sNotificationCollection     // Catch:{ all -> 0x0078 }
            int r8 = r12.getId()     // Catch:{ all -> 0x0078 }
            com.ansca.corona.notifications.NotificationSettings r2 = r6.getById(r8)     // Catch:{ all -> 0x0078 }
            if (r2 != 0) goto L_0x007b
            java.util.HashSet<java.lang.Integer> r6 = sReservedNotificationIdSet     // Catch:{ all -> 0x0078 }
            int r8 = r12.getId()     // Catch:{ all -> 0x0078 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x0078 }
            r6.add(r8)     // Catch:{ all -> 0x0078 }
            com.ansca.corona.notifications.NotificationSettingsCollection<com.ansca.corona.notifications.NotificationSettings> r6 = sNotificationCollection     // Catch:{ all -> 0x0078 }
            com.ansca.corona.notifications.NotificationSettings r8 = r12.clone()     // Catch:{ all -> 0x0078 }
            r6.add(r8)     // Catch:{ all -> 0x0078 }
        L_0x0047:
            boolean r6 = r12 instanceof com.ansca.corona.notifications.StatusBarNotificationSettings     // Catch:{ all -> 0x0078 }
            if (r6 == 0) goto L_0x00b1
            r0 = r12
            com.ansca.corona.notifications.StatusBarNotificationSettings r0 = (com.ansca.corona.notifications.StatusBarNotificationSettings) r0     // Catch:{ all -> 0x0078 }
            r5 = r0
            java.util.Collection r6 = com.ansca.corona.CoronaRuntimeProvider.getAllCoronaRuntimes()     // Catch:{ all -> 0x0078 }
            java.util.Iterator r6 = r6.iterator()     // Catch:{ all -> 0x0078 }
        L_0x0057:
            boolean r8 = r6.hasNext()     // Catch:{ all -> 0x0078 }
            if (r8 == 0) goto L_0x00b1
            java.lang.Object r3 = r6.next()     // Catch:{ all -> 0x0078 }
            com.ansca.corona.CoronaRuntime r3 = (com.ansca.corona.CoronaRuntime) r3     // Catch:{ all -> 0x0078 }
            boolean r8 = r3.isRunning()     // Catch:{ all -> 0x0078 }
            if (r8 == 0) goto L_0x0057
            com.ansca.corona.CoronaRuntimeTaskDispatcher r8 = r3.getTaskDispatcher()     // Catch:{ all -> 0x0078 }
            com.ansca.corona.events.NotificationReceivedTask r9 = new com.ansca.corona.events.NotificationReceivedTask     // Catch:{ all -> 0x0078 }
            java.lang.String r10 = "active"
            r9.<init>(r10, r5)     // Catch:{ all -> 0x0078 }
            r8.send(r9)     // Catch:{ all -> 0x0078 }
            goto L_0x0057
        L_0x0078:
            r6 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0078 }
            throw r6
        L_0x007b:
            boolean r6 = r2 instanceof com.ansca.corona.notifications.ScheduledNotificationSettings     // Catch:{ all -> 0x0078 }
            if (r6 == 0) goto L_0x008d
            boolean r6 = r12 instanceof com.ansca.corona.notifications.ScheduledNotificationSettings     // Catch:{ all -> 0x0078 }
            if (r6 == 0) goto L_0x008d
            com.ansca.corona.notifications.ScheduledNotificationSettings r2 = (com.ansca.corona.notifications.ScheduledNotificationSettings) r2     // Catch:{ all -> 0x0078 }
            r0 = r12
            com.ansca.corona.notifications.ScheduledNotificationSettings r0 = (com.ansca.corona.notifications.ScheduledNotificationSettings) r0     // Catch:{ all -> 0x0078 }
            r6 = r0
            r2.copyFrom(r6)     // Catch:{ all -> 0x0078 }
            goto L_0x0047
        L_0x008d:
            boolean r6 = r2 instanceof com.ansca.corona.notifications.StatusBarNotificationSettings     // Catch:{ all -> 0x0078 }
            if (r6 == 0) goto L_0x009f
            boolean r6 = r12 instanceof com.ansca.corona.notifications.StatusBarNotificationSettings     // Catch:{ all -> 0x0078 }
            if (r6 == 0) goto L_0x009f
            com.ansca.corona.notifications.StatusBarNotificationSettings r2 = (com.ansca.corona.notifications.StatusBarNotificationSettings) r2     // Catch:{ all -> 0x0078 }
            r0 = r12
            com.ansca.corona.notifications.StatusBarNotificationSettings r0 = (com.ansca.corona.notifications.StatusBarNotificationSettings) r0     // Catch:{ all -> 0x0078 }
            r6 = r0
            r2.copyFrom(r6)     // Catch:{ all -> 0x0078 }
            goto L_0x0047
        L_0x009f:
            r11.removeSystemNotification(r2)     // Catch:{ all -> 0x0078 }
            com.ansca.corona.notifications.NotificationSettingsCollection<com.ansca.corona.notifications.NotificationSettings> r6 = sNotificationCollection     // Catch:{ all -> 0x0078 }
            r6.remove(r2)     // Catch:{ all -> 0x0078 }
            com.ansca.corona.notifications.NotificationSettingsCollection<com.ansca.corona.notifications.NotificationSettings> r6 = sNotificationCollection     // Catch:{ all -> 0x0078 }
            com.ansca.corona.notifications.NotificationSettings r8 = r12.clone()     // Catch:{ all -> 0x0078 }
            r6.add(r8)     // Catch:{ all -> 0x0078 }
            goto L_0x0047
        L_0x00b1:
            monitor-exit(r7)     // Catch:{ all -> 0x0078 }
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.notifications.NotificationServices.postAndUpdateCollectionWith(com.ansca.corona.notifications.NotificationSettings):void");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void postSystemNotification(com.ansca.corona.notifications.NotificationSettings r20) {
        /*
            r19 = this;
            java.lang.Class<com.ansca.corona.notifications.NotificationServices> r15 = com.ansca.corona.notifications.NotificationServices.class
            monitor-enter(r15)
            if (r20 != 0) goto L_0x0007
            monitor-exit(r15)     // Catch:{ all -> 0x0045 }
        L_0x0006:
            return
        L_0x0007:
            android.content.Context r3 = getApplicationContext()     // Catch:{ all -> 0x0045 }
            r0 = r20
            boolean r14 = r0 instanceof com.ansca.corona.notifications.ScheduledNotificationSettings     // Catch:{ Exception -> 0x0057 }
            if (r14 == 0) goto L_0x005c
            r0 = r20
            com.ansca.corona.notifications.ScheduledNotificationSettings r0 = (com.ansca.corona.notifications.ScheduledNotificationSettings) r0     // Catch:{ Exception -> 0x0057 }
            r11 = r0
            r14 = 0
            android.content.Intent r16 = com.ansca.corona.notifications.AlarmManagerBroadcastReceiver.createIntentFrom(r3, r11)     // Catch:{ Exception -> 0x0057 }
            r17 = 0
            r0 = r16
            r1 = r17
            android.app.PendingIntent r9 = android.app.PendingIntent.getBroadcast(r3, r14, r0, r1)     // Catch:{ Exception -> 0x0057 }
            java.lang.String r12 = "alarm"
            java.lang.Object r2 = r3.getSystemService(r12)     // Catch:{ Exception -> 0x0057 }
            android.app.AlarmManager r2 = (android.app.AlarmManager) r2     // Catch:{ Exception -> 0x0057 }
            int r14 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0057 }
            r16 = 19
            r0 = r16
            if (r14 < r0) goto L_0x0048
            r14 = 0
            java.util.Date r16 = r11.getEndTime()     // Catch:{ Exception -> 0x0057 }
            long r16 = r16.getTime()     // Catch:{ Exception -> 0x0057 }
            r0 = r16
            com.ansca.corona.notifications.NotificationServices.ApiLevel19.alarmManagerSetExact(r2, r14, r0, r9)     // Catch:{ Exception -> 0x0057 }
        L_0x0043:
            monitor-exit(r15)     // Catch:{ all -> 0x0045 }
            goto L_0x0006
        L_0x0045:
            r14 = move-exception
            monitor-exit(r15)     // Catch:{ all -> 0x0045 }
            throw r14
        L_0x0048:
            r14 = 0
            java.util.Date r16 = r11.getEndTime()     // Catch:{ Exception -> 0x0057 }
            long r16 = r16.getTime()     // Catch:{ Exception -> 0x0057 }
            r0 = r16
            r2.set(r14, r0, r9)     // Catch:{ Exception -> 0x0057 }
            goto L_0x0043
        L_0x0057:
            r4 = move-exception
            r4.printStackTrace()     // Catch:{ all -> 0x0045 }
            goto L_0x0043
        L_0x005c:
            r0 = r20
            boolean r14 = r0 instanceof com.ansca.corona.notifications.StatusBarNotificationSettings     // Catch:{ Exception -> 0x0057 }
            if (r14 == 0) goto L_0x0043
            r0 = r20
            com.ansca.corona.notifications.StatusBarNotificationSettings r0 = (com.ansca.corona.notifications.StatusBarNotificationSettings) r0     // Catch:{ Exception -> 0x0057 }
            r13 = r0
            r7 = 0
            int r14 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0057 }
            r16 = 16
            r0 = r16
            if (r14 < r0) goto L_0x0088
            android.app.Notification r7 = com.ansca.corona.notifications.NotificationServices.JellyBean.createNotificationFrom(r3, r13)     // Catch:{ Exception -> 0x0057 }
        L_0x0074:
            java.lang.String r12 = "notification"
            java.lang.Object r8 = r3.getSystemService(r12)     // Catch:{ Exception -> 0x0057 }
            android.app.NotificationManager r8 = (android.app.NotificationManager) r8     // Catch:{ Exception -> 0x0057 }
            java.lang.String r14 = "corona"
            int r16 = r13.getId()     // Catch:{ Exception -> 0x0057 }
            r0 = r16
            r8.notify(r14, r0, r7)     // Catch:{ Exception -> 0x0057 }
            goto L_0x0043
        L_0x0088:
            int r14 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0057 }
            r16 = 11
            r0 = r16
            if (r14 < r0) goto L_0x0095
            android.app.Notification r7 = com.ansca.corona.notifications.NotificationServices.Gingerbread.createNotificationFrom(r3, r13)     // Catch:{ Exception -> 0x0057 }
            goto L_0x0074
        L_0x0095:
            int r5 = r13.getIconResourceId()     // Catch:{ Exception -> 0x0057 }
            if (r5 != 0) goto L_0x00a6
            com.ansca.corona.storage.ResourceServices r10 = new com.ansca.corona.storage.ResourceServices     // Catch:{ Exception -> 0x0057 }
            r10.<init>(r3)     // Catch:{ Exception -> 0x0057 }
            java.lang.String r14 = "corona_statusbar_icon_default"
            int r5 = r10.getDrawableResourceId(r14)     // Catch:{ Exception -> 0x0057 }
        L_0x00a6:
            android.app.Notification r7 = new android.app.Notification     // Catch:{ Exception -> 0x0057 }
            java.lang.String r14 = r13.getTickerText()     // Catch:{ Exception -> 0x0057 }
            java.util.Date r16 = r13.getTimestamp()     // Catch:{ Exception -> 0x0057 }
            long r16 = r16.getTime()     // Catch:{ Exception -> 0x0057 }
            r0 = r16
            r7.<init>(r5, r14, r0)     // Catch:{ Exception -> 0x0057 }
            android.content.Intent r6 = com.ansca.corona.notifications.StatusBarBroadcastReceiver.createContentIntentFrom(r3, r13)     // Catch:{ Exception -> 0x0057 }
            java.lang.String r14 = r13.getContentTitle()     // Catch:{ Exception -> 0x0057 }
            java.lang.String r16 = r13.getContentText()     // Catch:{ Exception -> 0x0057 }
            r17 = 0
            r18 = 0
            r0 = r17
            r1 = r18
            android.app.PendingIntent r17 = android.app.PendingIntent.getBroadcast(r3, r0, r6, r1)     // Catch:{ Exception -> 0x0057 }
            r0 = r16
            r1 = r17
            r7.setLatestEventInfo(r3, r14, r0, r1)     // Catch:{ Exception -> 0x0057 }
            android.content.Intent r6 = com.ansca.corona.notifications.StatusBarBroadcastReceiver.createDeleteIntentFrom(r3, r13)     // Catch:{ Exception -> 0x0057 }
            r14 = 0
            r16 = 0
            r0 = r16
            android.app.PendingIntent r14 = android.app.PendingIntent.getBroadcast(r3, r14, r6, r0)     // Catch:{ Exception -> 0x0057 }
            r7.deleteIntent = r14     // Catch:{ Exception -> 0x0057 }
            int r14 = r13.getBadgeNumber()     // Catch:{ Exception -> 0x0057 }
            r7.number = r14     // Catch:{ Exception -> 0x0057 }
            android.net.Uri r14 = r13.getSoundFileUri()     // Catch:{ Exception -> 0x0057 }
            if (r14 == 0) goto L_0x010b
            android.net.Uri r14 = r13.getSoundFileUri()     // Catch:{ Exception -> 0x0057 }
            r7.sound = r14     // Catch:{ Exception -> 0x0057 }
        L_0x00f9:
            r14 = 8
            r7.flags = r14     // Catch:{ Exception -> 0x0057 }
            boolean r14 = r13.isRemovable()     // Catch:{ Exception -> 0x0057 }
            if (r14 == 0) goto L_0x010f
            int r14 = r7.flags     // Catch:{ Exception -> 0x0057 }
            r14 = r14 | 16
            r7.flags = r14     // Catch:{ Exception -> 0x0057 }
            goto L_0x0074
        L_0x010b:
            r14 = 1
            r7.defaults = r14     // Catch:{ Exception -> 0x0057 }
            goto L_0x00f9
        L_0x010f:
            int r14 = r7.flags     // Catch:{ Exception -> 0x0057 }
            r14 = r14 | 2
            r7.flags = r14     // Catch:{ Exception -> 0x0057 }
            int r14 = r7.flags     // Catch:{ Exception -> 0x0057 }
            r14 = r14 | 32
            r7.flags = r14     // Catch:{ Exception -> 0x0057 }
            goto L_0x0074
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.notifications.NotificationServices.postSystemNotification(com.ansca.corona.notifications.NotificationSettings):void");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void removeSystemNotification(com.ansca.corona.notifications.NotificationSettings r13) {
        /*
            r12 = this;
            java.lang.Class<com.ansca.corona.notifications.NotificationServices> r9 = com.ansca.corona.notifications.NotificationServices.class
            monitor-enter(r9)
            if (r13 != 0) goto L_0x0007
            monitor-exit(r9)     // Catch:{ all -> 0x002a }
        L_0x0006:
            return
        L_0x0007:
            android.content.Context r2 = getApplicationContext()     // Catch:{ all -> 0x002a }
            boolean r8 = r13 instanceof com.ansca.corona.notifications.ScheduledNotificationSettings     // Catch:{ Exception -> 0x0043 }
            if (r8 == 0) goto L_0x002d
            java.lang.String r7 = "alarm"
            java.lang.Object r1 = r2.getSystemService(r7)     // Catch:{ Exception -> 0x0043 }
            android.app.AlarmManager r1 = (android.app.AlarmManager) r1     // Catch:{ Exception -> 0x0043 }
            r0 = r13
            com.ansca.corona.notifications.ScheduledNotificationSettings r0 = (com.ansca.corona.notifications.ScheduledNotificationSettings) r0     // Catch:{ Exception -> 0x0043 }
            r6 = r0
            r8 = 0
            android.content.Intent r10 = com.ansca.corona.notifications.AlarmManagerBroadcastReceiver.createIntentFrom(r2, r6)     // Catch:{ Exception -> 0x0043 }
            r11 = 0
            android.app.PendingIntent r5 = android.app.PendingIntent.getBroadcast(r2, r8, r10, r11)     // Catch:{ Exception -> 0x0043 }
            r1.cancel(r5)     // Catch:{ Exception -> 0x0043 }
        L_0x0028:
            monitor-exit(r9)     // Catch:{ all -> 0x002a }
            goto L_0x0006
        L_0x002a:
            r8 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x002a }
            throw r8
        L_0x002d:
            boolean r8 = r13 instanceof com.ansca.corona.notifications.StatusBarNotificationSettings     // Catch:{ Exception -> 0x0043 }
            if (r8 == 0) goto L_0x0028
            java.lang.String r7 = "notification"
            java.lang.Object r4 = r2.getSystemService(r7)     // Catch:{ Exception -> 0x0043 }
            android.app.NotificationManager r4 = (android.app.NotificationManager) r4     // Catch:{ Exception -> 0x0043 }
            java.lang.String r8 = "corona"
            int r10 = r13.getId()     // Catch:{ Exception -> 0x0043 }
            r4.cancel(r8, r10)     // Catch:{ Exception -> 0x0043 }
            goto L_0x0028
        L_0x0043:
            r3 = move-exception
            r3.printStackTrace()     // Catch:{ all -> 0x002a }
            goto L_0x0028
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.notifications.NotificationServices.removeSystemNotification(com.ansca.corona.notifications.NotificationSettings):void");
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:4:0x0007=Splitter:B:4:0x0007, B:10:0x0039=Splitter:B:10:0x0039} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void saveSettings(com.ansca.corona.notifications.NotificationSettings r11, org.xmlpull.v1.XmlSerializer r12) {
        /*
            r10 = this;
            java.lang.Class<com.ansca.corona.notifications.NotificationServices> r6 = com.ansca.corona.notifications.NotificationServices.class
            monitor-enter(r6)
            if (r11 == 0) goto L_0x0007
            if (r12 != 0) goto L_0x0009
        L_0x0007:
            monitor-exit(r6)     // Catch:{ all -> 0x003b }
        L_0x0008:
            return
        L_0x0009:
            boolean r5 = r11 instanceof com.ansca.corona.notifications.ScheduledNotificationSettings     // Catch:{ Exception -> 0x0123 }
            if (r5 == 0) goto L_0x003e
            r0 = r11
            com.ansca.corona.notifications.ScheduledNotificationSettings r0 = (com.ansca.corona.notifications.ScheduledNotificationSettings) r0     // Catch:{ Exception -> 0x0123 }
            r3 = r0
            java.lang.String r5 = ""
            java.lang.String r7 = "scheduled"
            r12.startTag(r5, r7)     // Catch:{ Exception -> 0x0123 }
            java.lang.String r5 = ""
            java.lang.String r7 = "endTime"
            java.util.Date r8 = r3.getEndTime()     // Catch:{ Exception -> 0x0123 }
            long r8 = r8.getTime()     // Catch:{ Exception -> 0x0123 }
            java.lang.String r8 = java.lang.Long.toString(r8)     // Catch:{ Exception -> 0x0123 }
            r12.attribute(r5, r7, r8)     // Catch:{ Exception -> 0x0123 }
            com.ansca.corona.notifications.StatusBarNotificationSettings r5 = r3.getStatusBarSettings()     // Catch:{ Exception -> 0x0123 }
            r10.saveSettings(r5, r12)     // Catch:{ Exception -> 0x0123 }
            java.lang.String r5 = ""
            java.lang.String r7 = "scheduled"
            r12.endTag(r5, r7)     // Catch:{ Exception -> 0x0123 }
        L_0x0039:
            monitor-exit(r6)     // Catch:{ all -> 0x003b }
            goto L_0x0008
        L_0x003b:
            r5 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x003b }
            throw r5
        L_0x003e:
            boolean r5 = r11 instanceof com.ansca.corona.notifications.StatusBarNotificationSettings     // Catch:{ Exception -> 0x0123 }
            if (r5 == 0) goto L_0x0039
            r0 = r11
            com.ansca.corona.notifications.StatusBarNotificationSettings r0 = (com.ansca.corona.notifications.StatusBarNotificationSettings) r0     // Catch:{ Exception -> 0x0123 }
            r4 = r0
            java.lang.String r5 = ""
            java.lang.String r7 = "statusBar"
            r12.startTag(r5, r7)     // Catch:{ Exception -> 0x0123 }
            java.lang.String r5 = ""
            java.lang.String r7 = "id"
            int r8 = r4.getId()     // Catch:{ Exception -> 0x0123 }
            java.lang.String r8 = java.lang.Integer.toString(r8)     // Catch:{ Exception -> 0x0123 }
            r12.attribute(r5, r7, r8)     // Catch:{ Exception -> 0x0123 }
            java.lang.String r5 = ""
            java.lang.String r7 = "timestamp"
            java.util.Date r8 = r4.getTimestamp()     // Catch:{ Exception -> 0x0123 }
            long r8 = r8.getTime()     // Catch:{ Exception -> 0x0123 }
            java.lang.String r8 = java.lang.Long.toString(r8)     // Catch:{ Exception -> 0x0123 }
            r12.attribute(r5, r7, r8)     // Catch:{ Exception -> 0x0123 }
            java.lang.String r5 = ""
            java.lang.String r7 = "contentTitle"
            java.lang.String r8 = r4.getContentTitle()     // Catch:{ Exception -> 0x0123 }
            r12.attribute(r5, r7, r8)     // Catch:{ Exception -> 0x0123 }
            java.lang.String r5 = ""
            java.lang.String r7 = "contentText"
            java.lang.String r8 = r4.getContentText()     // Catch:{ Exception -> 0x0123 }
            r12.attribute(r5, r7, r8)     // Catch:{ Exception -> 0x0123 }
            java.lang.String r5 = ""
            java.lang.String r7 = "tickerText"
            java.lang.String r8 = r4.getTickerText()     // Catch:{ Exception -> 0x0123 }
            r12.attribute(r5, r7, r8)     // Catch:{ Exception -> 0x0123 }
            java.lang.String r5 = ""
            java.lang.String r7 = "iconResourceName"
            java.lang.String r8 = r4.getIconResourceName()     // Catch:{ Exception -> 0x0123 }
            r12.attribute(r5, r7, r8)     // Catch:{ Exception -> 0x0123 }
            java.lang.String r5 = ""
            java.lang.String r7 = "badgeNumber"
            int r8 = r4.getBadgeNumber()     // Catch:{ Exception -> 0x0123 }
            java.lang.String r8 = java.lang.Integer.toString(r8)     // Catch:{ Exception -> 0x0123 }
            r12.attribute(r5, r7, r8)     // Catch:{ Exception -> 0x0123 }
            android.net.Uri r5 = r4.getSoundFileUri()     // Catch:{ Exception -> 0x0123 }
            if (r5 == 0) goto L_0x00bf
            java.lang.String r5 = ""
            java.lang.String r7 = "soundFileUri"
            android.net.Uri r8 = r4.getSoundFileUri()     // Catch:{ Exception -> 0x0123 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0123 }
            r12.attribute(r5, r7, r8)     // Catch:{ Exception -> 0x0123 }
        L_0x00bf:
            java.lang.String r5 = ""
            java.lang.String r7 = "sourceName"
            java.lang.String r8 = r4.getSourceName()     // Catch:{ Exception -> 0x0123 }
            r12.attribute(r5, r7, r8)     // Catch:{ Exception -> 0x0123 }
            java.lang.String r5 = ""
            java.lang.String r7 = "isSourceLocal"
            boolean r8 = r4.isSourceLocal()     // Catch:{ Exception -> 0x0123 }
            java.lang.String r8 = java.lang.Boolean.toString(r8)     // Catch:{ Exception -> 0x0123 }
            r12.attribute(r5, r7, r8)     // Catch:{ Exception -> 0x0123 }
            com.ansca.corona.CoronaData r5 = r4.getSourceData()     // Catch:{ Exception -> 0x0123 }
            if (r5 == 0) goto L_0x00ff
            java.lang.String r5 = ""
            java.lang.String r7 = "sourceData"
            r12.startTag(r5, r7)     // Catch:{ Exception -> 0x0123 }
            java.lang.String r5 = ""
            java.lang.String r7 = "name"
            java.lang.String r8 = r4.getSourceDataName()     // Catch:{ Exception -> 0x0123 }
            r12.attribute(r5, r7, r8)     // Catch:{ Exception -> 0x0123 }
            com.ansca.corona.CoronaData r5 = r4.getSourceData()     // Catch:{ Exception -> 0x0123 }
            r5.writeTo(r12)     // Catch:{ Exception -> 0x0123 }
            java.lang.String r5 = ""
            java.lang.String r7 = "sourceData"
            r12.endTag(r5, r7)     // Catch:{ Exception -> 0x0123 }
        L_0x00ff:
            com.ansca.corona.CoronaData$Table r5 = r4.getData()     // Catch:{ Exception -> 0x0123 }
            if (r5 == 0) goto L_0x011a
            java.lang.String r5 = ""
            java.lang.String r7 = "data"
            r12.startTag(r5, r7)     // Catch:{ Exception -> 0x0123 }
            com.ansca.corona.CoronaData$Table r5 = r4.getData()     // Catch:{ Exception -> 0x0123 }
            r5.writeTo(r12)     // Catch:{ Exception -> 0x0123 }
            java.lang.String r5 = ""
            java.lang.String r7 = "data"
            r12.endTag(r5, r7)     // Catch:{ Exception -> 0x0123 }
        L_0x011a:
            java.lang.String r5 = ""
            java.lang.String r7 = "statusBar"
            r12.endTag(r5, r7)     // Catch:{ Exception -> 0x0123 }
            goto L_0x0039
        L_0x0123:
            r2 = move-exception
            r2.printStackTrace()     // Catch:{ all -> 0x003b }
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.notifications.NotificationServices.saveSettings(com.ansca.corona.notifications.NotificationSettings, org.xmlpull.v1.XmlSerializer):void");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x006f A[SYNTHETIC, Splitter:B:33:0x006f] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0094 A[SYNTHETIC, Splitter:B:52:0x0094] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:37:0x0075=Splitter:B:37:0x0075, B:56:0x009a=Splitter:B:56:0x009a, B:11:0x002c=Splitter:B:11:0x002c} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void saveSettings(com.ansca.corona.notifications.NotificationSettingsCollection<com.ansca.corona.notifications.NotificationSettings> r11) {
        /*
            r10 = this;
            java.lang.Class<com.ansca.corona.notifications.NotificationServices> r8 = com.ansca.corona.notifications.NotificationServices.class
            monitor-enter(r8)
            if (r11 != 0) goto L_0x0007
            monitor-exit(r8)     // Catch:{ all -> 0x002e }
        L_0x0006:
            return
        L_0x0007:
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x002e }
            android.content.Context r7 = getApplicationContext()     // Catch:{ all -> 0x002e }
            java.io.File r7 = r7.getCacheDir()     // Catch:{ all -> 0x002e }
            java.lang.String r9 = ".system"
            r1.<init>(r7, r9)     // Catch:{ all -> 0x002e }
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x002e }
            java.lang.String r7 = "NotificationSettings.xml"
            r2.<init>(r1, r7)     // Catch:{ all -> 0x002e }
            int r7 = r11.size()     // Catch:{ all -> 0x002e }
            if (r7 > 0) goto L_0x0036
            boolean r7 = r2.exists()     // Catch:{ Exception -> 0x0031 }
            if (r7 == 0) goto L_0x002c
            r2.delete()     // Catch:{ Exception -> 0x0031 }
        L_0x002c:
            monitor-exit(r8)     // Catch:{ all -> 0x002e }
            goto L_0x0006
        L_0x002e:
            r7 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x002e }
            throw r7
        L_0x0031:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x002e }
            goto L_0x002c
        L_0x0036:
            r3 = 0
            org.xmlpull.v1.XmlSerializer r6 = android.util.Xml.newSerializer()     // Catch:{ Exception -> 0x00a8 }
            java.io.FileWriter r4 = new java.io.FileWriter     // Catch:{ Exception -> 0x00a8 }
            r4.<init>(r2)     // Catch:{ Exception -> 0x00a8 }
            r6.setOutput(r4)     // Catch:{ Exception -> 0x0068, all -> 0x00a5 }
            java.lang.String r7 = "UTF-8"
            r9 = 1
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r9)     // Catch:{ Exception -> 0x0068, all -> 0x00a5 }
            r6.startDocument(r7, r9)     // Catch:{ Exception -> 0x0068, all -> 0x00a5 }
            java.lang.String r7 = ""
            java.lang.String r9 = "notifications"
            r6.startTag(r7, r9)     // Catch:{ Exception -> 0x0068, all -> 0x00a5 }
            java.util.Iterator r7 = r11.iterator()     // Catch:{ Exception -> 0x0068, all -> 0x00a5 }
        L_0x0058:
            boolean r9 = r7.hasNext()     // Catch:{ Exception -> 0x0068, all -> 0x00a5 }
            if (r9 == 0) goto L_0x0077
            java.lang.Object r5 = r7.next()     // Catch:{ Exception -> 0x0068, all -> 0x00a5 }
            com.ansca.corona.notifications.NotificationSettings r5 = (com.ansca.corona.notifications.NotificationSettings) r5     // Catch:{ Exception -> 0x0068, all -> 0x00a5 }
            r10.saveSettings(r5, r6)     // Catch:{ Exception -> 0x0068, all -> 0x00a5 }
            goto L_0x0058
        L_0x0068:
            r0 = move-exception
            r3 = r4
        L_0x006a:
            r0.printStackTrace()     // Catch:{ all -> 0x0091 }
            if (r3 == 0) goto L_0x0075
            r3.flush()     // Catch:{ Exception -> 0x009d }
        L_0x0072:
            r3.close()     // Catch:{ Exception -> 0x009f }
        L_0x0075:
            monitor-exit(r8)     // Catch:{ all -> 0x002e }
            goto L_0x0006
        L_0x0077:
            java.lang.String r7 = ""
            java.lang.String r9 = "notifications"
            r6.endTag(r7, r9)     // Catch:{ Exception -> 0x0068, all -> 0x00a5 }
            r6.endDocument()     // Catch:{ Exception -> 0x0068, all -> 0x00a5 }
            r6.flush()     // Catch:{ Exception -> 0x0068, all -> 0x00a5 }
            if (r4 == 0) goto L_0x00aa
            r4.flush()     // Catch:{ Exception -> 0x009b }
        L_0x0089:
            r4.close()     // Catch:{ Exception -> 0x008e }
            r3 = r4
            goto L_0x0075
        L_0x008e:
            r7 = move-exception
            r3 = r4
            goto L_0x0075
        L_0x0091:
            r7 = move-exception
        L_0x0092:
            if (r3 == 0) goto L_0x009a
            r3.flush()     // Catch:{ Exception -> 0x00a1 }
        L_0x0097:
            r3.close()     // Catch:{ Exception -> 0x00a3 }
        L_0x009a:
            throw r7     // Catch:{ all -> 0x002e }
        L_0x009b:
            r7 = move-exception
            goto L_0x0089
        L_0x009d:
            r7 = move-exception
            goto L_0x0072
        L_0x009f:
            r7 = move-exception
            goto L_0x0075
        L_0x00a1:
            r9 = move-exception
            goto L_0x0097
        L_0x00a3:
            r9 = move-exception
            goto L_0x009a
        L_0x00a5:
            r7 = move-exception
            r3 = r4
            goto L_0x0092
        L_0x00a8:
            r0 = move-exception
            goto L_0x006a
        L_0x00aa:
            r3 = r4
            goto L_0x0075
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.notifications.NotificationServices.saveSettings(com.ansca.corona.notifications.NotificationSettingsCollection):void");
    }

    public NotificationSettings fetchById(int i) {
        NotificationSettings fetchById;
        synchronized (NotificationServices.class) {
            fetchById = fetchById(NotificationSettings.class, i);
        }
        return fetchById;
    }

    public <T extends NotificationSettings> T fetchById(Class<T> cls, int i) {
        T t;
        synchronized (NotificationServices.class) {
            if (cls != null) {
                NotificationSettings byId = sNotificationCollection.getById(i);
                if (cls.isInstance(byId)) {
                    t = byId.clone();
                }
            }
            t = null;
        }
        return t;
    }

    public ScheduledNotificationSettings fetchScheduledNotificationById(int i) {
        ScheduledNotificationSettings scheduledNotificationSettings;
        synchronized (NotificationServices.class) {
            scheduledNotificationSettings = (ScheduledNotificationSettings) fetchById(ScheduledNotificationSettings.class, i);
        }
        return scheduledNotificationSettings;
    }

    public NotificationSettingsCollection<ScheduledNotificationSettings> fetchScheduledNotifications() {
        NotificationSettingsCollection<ScheduledNotificationSettings> fetchNotificationsByType;
        synchronized (NotificationServices.class) {
            fetchNotificationsByType = fetchNotificationsByType(ScheduledNotificationSettings.class);
        }
        return fetchNotificationsByType;
    }

    public StatusBarNotificationSettings fetchStatusBarNotificationById(int i) {
        StatusBarNotificationSettings statusBarNotificationSettings;
        synchronized (NotificationServices.class) {
            statusBarNotificationSettings = (StatusBarNotificationSettings) fetchById(StatusBarNotificationSettings.class, i);
        }
        return statusBarNotificationSettings;
    }

    public NotificationSettingsCollection<StatusBarNotificationSettings> fetchStatusBarNotifications() {
        NotificationSettingsCollection<StatusBarNotificationSettings> fetchNotificationsByType;
        synchronized (NotificationServices.class) {
            fetchNotificationsByType = fetchNotificationsByType(StatusBarNotificationSettings.class);
        }
        return fetchNotificationsByType;
    }

    public boolean hasNotifications() {
        boolean z;
        synchronized (NotificationServices.class) {
            z = sNotificationCollection.size() > 0;
        }
        return z;
    }

    public void post(NotificationSettings notificationSettings) {
        synchronized (NotificationServices.class) {
            if (notificationSettings != null) {
                postAndUpdateCollectionWith(notificationSettings);
                saveSettings(sNotificationCollection);
            }
        }
    }

    public <T extends NotificationSettings> void post(Iterable<T> iterable) {
        synchronized (NotificationServices.class) {
            if (iterable != null) {
                for (T postAndUpdateCollectionWith : iterable) {
                    postAndUpdateCollectionWith(postAndUpdateCollectionWith);
                }
                saveSettings(sNotificationCollection);
            }
        }
    }

    public void removeAll() {
        synchronized (NotificationServices.class) {
            Iterator<NotificationSettings> it = sNotificationCollection.iterator();
            while (it.hasNext()) {
                NotificationSettings next = it.next();
                removeSystemNotification(next);
                sReservedNotificationIdSet.remove(Integer.valueOf(next.getId()));
            }
            sNotificationCollection.clear();
            saveSettings(sNotificationCollection);
        }
    }

    public boolean removeById(int i) {
        boolean z;
        synchronized (NotificationServices.class) {
            NotificationSettings byId = sNotificationCollection.getById(i);
            if (byId == null) {
                z = false;
            } else {
                removeSystemNotification(byId);
                sReservedNotificationIdSet.remove(Integer.valueOf(i));
                sNotificationCollection.remove(byId);
                saveSettings(sNotificationCollection);
                z = true;
            }
        }
        return z;
    }

    public int reserveId() {
        int i;
        synchronized (NotificationServices.class) {
            i = 1;
            while (true) {
                if (i != 0) {
                    if (!reserveId(i)) {
                    }
                }
                i++;
            }
        }
        return i;
    }

    public boolean reserveId(int i) {
        boolean add;
        synchronized (NotificationServices.class) {
            add = sReservedNotificationIdSet.add(Integer.valueOf(i));
        }
        return add;
    }
}
