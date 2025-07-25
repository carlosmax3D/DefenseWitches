package com.facebook.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public abstract class PlatformServiceClient implements ServiceConnection {
    private final String applicationId;
    private final Context context;
    private final Handler handler;
    private CompletedListener listener;
    private final int protocolVersion;
    private int replyMessage;
    private int requestMessage;
    private boolean running;
    private Messenger sender;

    public interface CompletedListener {
        void completed(Bundle bundle);
    }

    public PlatformServiceClient(Context context2, int i, int i2, int i3, String str) {
        Context applicationContext = context2.getApplicationContext();
        this.context = applicationContext == null ? context2 : applicationContext;
        this.requestMessage = i;
        this.replyMessage = i2;
        this.applicationId = str;
        this.protocolVersion = i3;
        this.handler = new Handler() {
            public void handleMessage(Message message) {
                PlatformServiceClient.this.handleMessage(message);
            }
        };
    }

    private void callback(Bundle bundle) {
        if (this.running) {
            this.running = false;
            CompletedListener completedListener = this.listener;
            if (completedListener != null) {
                completedListener.completed(bundle);
            }
        }
    }

    private void sendMessage() {
        Bundle bundle = new Bundle();
        bundle.putString(NativeProtocol.EXTRA_APPLICATION_ID, this.applicationId);
        populateRequestBundle(bundle);
        Message obtain = Message.obtain((Handler) null, this.requestMessage);
        obtain.arg1 = this.protocolVersion;
        obtain.setData(bundle);
        obtain.replyTo = new Messenger(this.handler);
        try {
            this.sender.send(obtain);
        } catch (RemoteException e) {
            callback((Bundle) null);
        }
    }

    public void cancel() {
        this.running = false;
    }

    /* access modifiers changed from: protected */
    public Context getContext() {
        return this.context;
    }

    /* access modifiers changed from: protected */
    public void handleMessage(Message message) {
        if (message.what == this.replyMessage) {
            Bundle data = message.getData();
            if (data.getString(NativeProtocol.STATUS_ERROR_TYPE) != null) {
                callback((Bundle) null);
            } else {
                callback(data);
            }
            this.context.unbindService(this);
        }
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.sender = new Messenger(iBinder);
        sendMessage();
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.sender = null;
        try {
            this.context.unbindService(this);
        } catch (IllegalArgumentException e) {
        }
        callback((Bundle) null);
    }

    /* access modifiers changed from: protected */
    public abstract void populateRequestBundle(Bundle bundle);

    public void setCompletedListener(CompletedListener completedListener) {
        this.listener = completedListener;
    }

    public boolean start() {
        Intent createPlatformServiceIntent;
        if (this.running || NativeProtocol.getLatestAvailableProtocolVersionForService(this.protocolVersion) == -1 || (createPlatformServiceIntent = NativeProtocol.createPlatformServiceIntent(this.context)) == null) {
            return false;
        }
        this.running = true;
        this.context.bindService(createPlatformServiceIntent, this, 1);
        return true;
    }
}
