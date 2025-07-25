package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import java.util.ArrayList;
import java.util.HashMap;

public class LocalBroadcastManager {
    private static final boolean DEBUG = false;
    static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private static final String TAG = "LocalBroadcastManager";
    private static LocalBroadcastManager mInstance;
    private static final Object mLock = new Object();
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap<>();
    private final Context mAppContext;
    private final Handler mHandler;
    private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList<>();
    private final HashMap<BroadcastReceiver, ArrayList<IntentFilter>> mReceivers = new HashMap<>();

    private static class BroadcastRecord {
        final Intent intent;
        final ArrayList<ReceiverRecord> receivers;

        BroadcastRecord(Intent intent2, ArrayList<ReceiverRecord> arrayList) {
            this.intent = intent2;
            this.receivers = arrayList;
        }
    }

    private static class ReceiverRecord {
        boolean broadcasting;
        final IntentFilter filter;
        final BroadcastReceiver receiver;

        ReceiverRecord(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.filter = intentFilter;
            this.receiver = broadcastReceiver;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Receiver{");
            sb.append(this.receiver);
            sb.append(" filter=");
            sb.append(this.filter);
            sb.append("}");
            return sb.toString();
        }
    }

    private LocalBroadcastManager(Context context) {
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()) {
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        LocalBroadcastManager.this.executePendingBroadcasts();
                        return;
                    default:
                        super.handleMessage(message);
                        return;
                }
            }
        };
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        if (r3 >= r2.length) goto L_0x0000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
        r1 = r2[r3];
        r4 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0028, code lost:
        if (r4 >= r1.receivers.size()) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002a, code lost:
        r1.receivers.get(r4).receiver.onReceive(r8.mAppContext, r1.intent);
        r4 = r4 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0041, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001b, code lost:
        r3 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void executePendingBroadcasts() {
        /*
            r8 = this;
        L_0x0000:
            r2 = 0
            java.util.HashMap<android.content.BroadcastReceiver, java.util.ArrayList<android.content.IntentFilter>> r6 = r8.mReceivers
            monitor-enter(r6)
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$BroadcastRecord> r5 = r8.mPendingBroadcasts     // Catch:{ all -> 0x003e }
            int r0 = r5.size()     // Catch:{ all -> 0x003e }
            if (r0 > 0) goto L_0x000e
            monitor-exit(r6)     // Catch:{ all -> 0x003e }
            return
        L_0x000e:
            android.support.v4.content.LocalBroadcastManager$BroadcastRecord[] r2 = new android.support.v4.content.LocalBroadcastManager.BroadcastRecord[r0]     // Catch:{ all -> 0x003e }
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$BroadcastRecord> r5 = r8.mPendingBroadcasts     // Catch:{ all -> 0x003e }
            r5.toArray(r2)     // Catch:{ all -> 0x003e }
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$BroadcastRecord> r5 = r8.mPendingBroadcasts     // Catch:{ all -> 0x003e }
            r5.clear()     // Catch:{ all -> 0x003e }
            monitor-exit(r6)     // Catch:{ all -> 0x003e }
            r3 = 0
        L_0x001c:
            int r5 = r2.length
            if (r3 >= r5) goto L_0x0000
            r1 = r2[r3]
            r4 = 0
        L_0x0022:
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$ReceiverRecord> r5 = r1.receivers
            int r5 = r5.size()
            if (r4 >= r5) goto L_0x0041
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$ReceiverRecord> r5 = r1.receivers
            java.lang.Object r5 = r5.get(r4)
            android.support.v4.content.LocalBroadcastManager$ReceiverRecord r5 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r5
            android.content.BroadcastReceiver r5 = r5.receiver
            android.content.Context r6 = r8.mAppContext
            android.content.Intent r7 = r1.intent
            r5.onReceive(r6, r7)
            int r4 = r4 + 1
            goto L_0x0022
        L_0x003e:
            r5 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x003e }
            throw r5
        L_0x0041:
            int r3 = r3 + 1
            goto L_0x001c
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.LocalBroadcastManager.executePendingBroadcasts():void");
    }

    public static LocalBroadcastManager getInstance(Context context) {
        LocalBroadcastManager localBroadcastManager;
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new LocalBroadcastManager(context.getApplicationContext());
            }
            localBroadcastManager = mInstance;
        }
        return localBroadcastManager;
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        synchronized (this.mReceivers) {
            ReceiverRecord receiverRecord = new ReceiverRecord(intentFilter, broadcastReceiver);
            ArrayList arrayList = this.mReceivers.get(broadcastReceiver);
            if (arrayList == null) {
                arrayList = new ArrayList(1);
                this.mReceivers.put(broadcastReceiver, arrayList);
            }
            arrayList.add(intentFilter);
            for (int i = 0; i < intentFilter.countActions(); i++) {
                String action = intentFilter.getAction(i);
                ArrayList arrayList2 = this.mActions.get(action);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList(1);
                    this.mActions.put(action, arrayList2);
                }
                arrayList2.add(receiverRecord);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0176, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean sendBroadcast(android.content.Intent r18) {
        /*
            r17 = this;
            r0 = r17
            java.util.HashMap<android.content.BroadcastReceiver, java.util.ArrayList<android.content.IntentFilter>> r15 = r0.mReceivers
            monitor-enter(r15)
            java.lang.String r2 = r18.getAction()     // Catch:{ all -> 0x010b }
            r0 = r17
            android.content.Context r1 = r0.mAppContext     // Catch:{ all -> 0x010b }
            android.content.ContentResolver r1 = r1.getContentResolver()     // Catch:{ all -> 0x010b }
            r0 = r18
            java.lang.String r3 = r0.resolveTypeIfNeeded(r1)     // Catch:{ all -> 0x010b }
            android.net.Uri r5 = r18.getData()     // Catch:{ all -> 0x010b }
            java.lang.String r4 = r18.getScheme()     // Catch:{ all -> 0x010b }
            java.util.Set r6 = r18.getCategories()     // Catch:{ all -> 0x010b }
            int r1 = r18.getFlags()     // Catch:{ all -> 0x010b }
            r1 = r1 & 8
            if (r1 == 0) goto L_0x00ce
            r8 = 1
        L_0x002c:
            if (r8 == 0) goto L_0x0062
            java.lang.String r1 = "LocalBroadcastManager"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x010b }
            r7.<init>()     // Catch:{ all -> 0x010b }
            java.lang.String r16 = "Resolving type "
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            java.lang.StringBuilder r7 = r7.append(r3)     // Catch:{ all -> 0x010b }
            java.lang.String r16 = " scheme "
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            java.lang.StringBuilder r7 = r7.append(r4)     // Catch:{ all -> 0x010b }
            java.lang.String r16 = " of intent "
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            r0 = r18
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x010b }
            android.util.Log.v(r1, r7)     // Catch:{ all -> 0x010b }
        L_0x0062:
            r0 = r17
            java.util.HashMap<java.lang.String, java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$ReceiverRecord>> r1 = r0.mActions     // Catch:{ all -> 0x010b }
            java.lang.String r7 = r18.getAction()     // Catch:{ all -> 0x010b }
            java.lang.Object r9 = r1.get(r7)     // Catch:{ all -> 0x010b }
            java.util.ArrayList r9 = (java.util.ArrayList) r9     // Catch:{ all -> 0x010b }
            if (r9 == 0) goto L_0x0175
            if (r8 == 0) goto L_0x008e
            java.lang.String r1 = "LocalBroadcastManager"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x010b }
            r7.<init>()     // Catch:{ all -> 0x010b }
            java.lang.String r16 = "Action list: "
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            java.lang.StringBuilder r7 = r7.append(r9)     // Catch:{ all -> 0x010b }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x010b }
            android.util.Log.v(r1, r7)     // Catch:{ all -> 0x010b }
        L_0x008e:
            r14 = 0
            r10 = 0
        L_0x0090:
            int r1 = r9.size()     // Catch:{ all -> 0x010b }
            if (r10 >= r1) goto L_0x013c
            java.lang.Object r13 = r9.get(r10)     // Catch:{ all -> 0x010b }
            android.support.v4.content.LocalBroadcastManager$ReceiverRecord r13 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r13     // Catch:{ all -> 0x010b }
            if (r8 == 0) goto L_0x00be
            java.lang.String r1 = "LocalBroadcastManager"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x010b }
            r7.<init>()     // Catch:{ all -> 0x010b }
            java.lang.String r16 = "Matching against filter "
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            android.content.IntentFilter r0 = r13.filter     // Catch:{ all -> 0x010b }
            r16 = r0
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x010b }
            android.util.Log.v(r1, r7)     // Catch:{ all -> 0x010b }
        L_0x00be:
            boolean r1 = r13.broadcasting     // Catch:{ all -> 0x010b }
            if (r1 == 0) goto L_0x00d1
            if (r8 == 0) goto L_0x00cb
            java.lang.String r1 = "LocalBroadcastManager"
            java.lang.String r7 = "  Filter's target already added"
            android.util.Log.v(r1, r7)     // Catch:{ all -> 0x010b }
        L_0x00cb:
            int r10 = r10 + 1
            goto L_0x0090
        L_0x00ce:
            r8 = 0
            goto L_0x002c
        L_0x00d1:
            android.content.IntentFilter r1 = r13.filter     // Catch:{ all -> 0x010b }
            java.lang.String r7 = "LocalBroadcastManager"
            int r11 = r1.match(r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x010b }
            if (r11 < 0) goto L_0x010e
            if (r8 == 0) goto L_0x00fd
            java.lang.String r1 = "LocalBroadcastManager"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x010b }
            r7.<init>()     // Catch:{ all -> 0x010b }
            java.lang.String r16 = "  Filter matched!  match=0x"
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            java.lang.String r16 = java.lang.Integer.toHexString(r11)     // Catch:{ all -> 0x010b }
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x010b }
            android.util.Log.v(r1, r7)     // Catch:{ all -> 0x010b }
        L_0x00fd:
            if (r14 != 0) goto L_0x0104
            java.util.ArrayList r14 = new java.util.ArrayList     // Catch:{ all -> 0x010b }
            r14.<init>()     // Catch:{ all -> 0x010b }
        L_0x0104:
            r14.add(r13)     // Catch:{ all -> 0x010b }
            r1 = 1
            r13.broadcasting = r1     // Catch:{ all -> 0x010b }
            goto L_0x00cb
        L_0x010b:
            r1 = move-exception
            monitor-exit(r15)     // Catch:{ all -> 0x010b }
            throw r1
        L_0x010e:
            if (r8 == 0) goto L_0x00cb
            switch(r11) {
                case -4: goto L_0x0133;
                case -3: goto L_0x0130;
                case -2: goto L_0x0136;
                case -1: goto L_0x0139;
                default: goto L_0x0113;
            }
        L_0x0113:
            java.lang.String r12 = "unknown reason"
        L_0x0115:
            java.lang.String r1 = "LocalBroadcastManager"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x010b }
            r7.<init>()     // Catch:{ all -> 0x010b }
            java.lang.String r16 = "  Filter did not match: "
            r0 = r16
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x010b }
            java.lang.StringBuilder r7 = r7.append(r12)     // Catch:{ all -> 0x010b }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x010b }
            android.util.Log.v(r1, r7)     // Catch:{ all -> 0x010b }
            goto L_0x00cb
        L_0x0130:
            java.lang.String r12 = "action"
            goto L_0x0115
        L_0x0133:
            java.lang.String r12 = "category"
            goto L_0x0115
        L_0x0136:
            java.lang.String r12 = "data"
            goto L_0x0115
        L_0x0139:
            java.lang.String r12 = "type"
            goto L_0x0115
        L_0x013c:
            if (r14 == 0) goto L_0x0175
            r10 = 0
        L_0x013f:
            int r1 = r14.size()     // Catch:{ all -> 0x010b }
            if (r10 >= r1) goto L_0x0151
            java.lang.Object r1 = r14.get(r10)     // Catch:{ all -> 0x010b }
            android.support.v4.content.LocalBroadcastManager$ReceiverRecord r1 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r1     // Catch:{ all -> 0x010b }
            r7 = 0
            r1.broadcasting = r7     // Catch:{ all -> 0x010b }
            int r10 = r10 + 1
            goto L_0x013f
        L_0x0151:
            r0 = r17
            java.util.ArrayList<android.support.v4.content.LocalBroadcastManager$BroadcastRecord> r1 = r0.mPendingBroadcasts     // Catch:{ all -> 0x010b }
            android.support.v4.content.LocalBroadcastManager$BroadcastRecord r7 = new android.support.v4.content.LocalBroadcastManager$BroadcastRecord     // Catch:{ all -> 0x010b }
            r0 = r18
            r7.<init>(r0, r14)     // Catch:{ all -> 0x010b }
            r1.add(r7)     // Catch:{ all -> 0x010b }
            r0 = r17
            android.os.Handler r1 = r0.mHandler     // Catch:{ all -> 0x010b }
            r7 = 1
            boolean r1 = r1.hasMessages(r7)     // Catch:{ all -> 0x010b }
            if (r1 != 0) goto L_0x0172
            r0 = r17
            android.os.Handler r1 = r0.mHandler     // Catch:{ all -> 0x010b }
            r7 = 1
            r1.sendEmptyMessage(r7)     // Catch:{ all -> 0x010b }
        L_0x0172:
            r1 = 1
            monitor-exit(r15)     // Catch:{ all -> 0x010b }
        L_0x0174:
            return r1
        L_0x0175:
            monitor-exit(r15)     // Catch:{ all -> 0x010b }
            r1 = 0
            goto L_0x0174
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.LocalBroadcastManager.sendBroadcast(android.content.Intent):boolean");
    }

    public void sendBroadcastSync(Intent intent) {
        if (sendBroadcast(intent)) {
            executePendingBroadcasts();
        }
    }

    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        synchronized (this.mReceivers) {
            ArrayList remove = this.mReceivers.remove(broadcastReceiver);
            if (remove != null) {
                for (int i = 0; i < remove.size(); i++) {
                    IntentFilter intentFilter = (IntentFilter) remove.get(i);
                    for (int i2 = 0; i2 < intentFilter.countActions(); i2++) {
                        String action = intentFilter.getAction(i2);
                        ArrayList arrayList = this.mActions.get(action);
                        if (arrayList != null) {
                            int i3 = 0;
                            while (i3 < arrayList.size()) {
                                if (((ReceiverRecord) arrayList.get(i3)).receiver == broadcastReceiver) {
                                    arrayList.remove(i3);
                                    i3--;
                                }
                                i3++;
                            }
                            if (arrayList.size() <= 0) {
                                this.mActions.remove(action);
                            }
                        }
                    }
                }
            }
        }
    }
}
