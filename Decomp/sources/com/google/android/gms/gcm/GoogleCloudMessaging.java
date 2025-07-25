package com.google.android.gms.gcm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import jp.newgate.game.android.dw.Constants;

public class GoogleCloudMessaging {
    public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
    public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    public static final String MESSAGE_TYPE_DELETED = "deleted_messages";
    public static final String MESSAGE_TYPE_MESSAGE = "gcm";
    public static final String MESSAGE_TYPE_SEND_ERROR = "send_error";
    static GoogleCloudMessaging xf;
    private Context eh;
    private PendingIntent xg;
    final BlockingQueue<Intent> xh = new LinkedBlockingQueue();
    private Handler xi = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            GoogleCloudMessaging.this.xh.add((Intent) message.obj);
        }
    };
    private Messenger xj = new Messenger(this.xi);

    private void b(String... strArr) {
        String c = c(strArr);
        Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
        intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
        intent.putExtra("google.messenger", this.xj);
        c(intent);
        intent.putExtra("sender", c);
        this.eh.startService(intent);
    }

    private void dz() {
        Intent intent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
        intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
        this.xh.clear();
        intent.putExtra("google.messenger", this.xj);
        c(intent);
        this.eh.startService(intent);
    }

    public static synchronized GoogleCloudMessaging getInstance(Context context) {
        GoogleCloudMessaging googleCloudMessaging;
        synchronized (GoogleCloudMessaging.class) {
            if (xf == null) {
                xf = new GoogleCloudMessaging();
                xf.eh = context;
            }
            googleCloudMessaging = xf;
        }
        return googleCloudMessaging;
    }

    /* access modifiers changed from: package-private */
    public String c(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            throw new IllegalArgumentException("No senderIds");
        }
        StringBuilder sb = new StringBuilder(strArr[0]);
        for (int i = 1; i < strArr.length; i++) {
            sb.append(',').append(strArr[i]);
        }
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public synchronized void c(Intent intent) {
        if (this.xg == null) {
            this.xg = PendingIntent.getBroadcast(this.eh, 0, new Intent(), 0);
        }
        intent.putExtra("app", this.xg);
    }

    public void close() {
        dA();
    }

    /* access modifiers changed from: package-private */
    public synchronized void dA() {
        if (this.xg != null) {
            this.xg.cancel();
            this.xg = null;
        }
    }

    public String getMessageType(Intent intent) {
        if (!"com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
            return null;
        }
        String stringExtra = intent.getStringExtra("message_type");
        return stringExtra == null ? MESSAGE_TYPE_MESSAGE : stringExtra;
    }

    public String register(String... strArr) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException(ERROR_MAIN_THREAD);
        }
        this.xh.clear();
        b(strArr);
        try {
            Intent poll = this.xh.poll(5000, TimeUnit.MILLISECONDS);
            if (poll == null) {
                throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
            }
            String stringExtra = poll.getStringExtra(Constants.PROPERTY_REG_ID);
            if (stringExtra != null) {
                return stringExtra;
            }
            poll.getStringExtra("error");
            String stringExtra2 = poll.getStringExtra("error");
            if (stringExtra2 != null) {
                throw new IOException(stringExtra2);
            }
            throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
        } catch (InterruptedException e) {
            throw new IOException(e.getMessage());
        }
    }

    public void send(String str, String str2, long j, Bundle bundle) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException(ERROR_MAIN_THREAD);
        } else if (str == null) {
            throw new IllegalArgumentException("Missing 'to'");
        } else {
            Intent intent = new Intent("com.google.android.gcm.intent.SEND");
            intent.putExtras(bundle);
            c(intent);
            intent.putExtra("google.to", str);
            intent.putExtra("google.message_id", str2);
            intent.putExtra("google.ttl", Long.toString(j));
            this.eh.sendOrderedBroadcast(intent, (String) null);
        }
    }

    public void send(String str, String str2, Bundle bundle) throws IOException {
        send(str, str2, -1, bundle);
    }

    public void unregister() throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException(ERROR_MAIN_THREAD);
        }
        dz();
        try {
            Intent poll = this.xh.poll(5000, TimeUnit.MILLISECONDS);
            if (poll == null) {
                throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
            } else if (poll.getStringExtra("unregistered") == null) {
                String stringExtra = poll.getStringExtra("error");
                if (stringExtra != null) {
                    throw new IOException(stringExtra);
                }
                throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
            }
        } catch (InterruptedException e) {
            throw new IOException(e.getMessage());
        }
    }
}
