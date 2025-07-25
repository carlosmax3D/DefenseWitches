package com.facebook;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.Date;

final class AccessTokenManager {
    static final String ACTION_CURRENT_ACCESS_TOKEN_CHANGED = "com.facebook.sdk.ACTION_CURRENT_ACCESS_TOKEN_CHANGED";
    static final String EXTRA_NEW_ACCESS_TOKEN = "com.facebook.sdk.EXTRA_NEW_ACCESS_TOKEN";
    static final String EXTRA_OLD_ACCESS_TOKEN = "com.facebook.sdk.EXTRA_OLD_ACCESS_TOKEN";
    static final String SHARED_PREFERENCES_NAME = "com.facebook.AccessTokenManager.SharedPreferences";
    private static final int TOKEN_EXTEND_RETRY_SECONDS = 3600;
    private static final int TOKEN_EXTEND_THRESHOLD_SECONDS = 86400;
    private static volatile AccessTokenManager instance;
    private final AccessTokenCache accessTokenCache;
    private AccessToken currentAccessToken;
    /* access modifiers changed from: private */
    public TokenRefreshRequest currentTokenRefreshRequest;
    /* access modifiers changed from: private */
    public Date lastAttemptedTokenExtendDate = new Date(0);
    private final LocalBroadcastManager localBroadcastManager;

    class TokenRefreshRequest implements ServiceConnection {
        final Messenger messageReceiver;
        Messenger messageSender = null;

        TokenRefreshRequest(AccessToken accessToken) {
            this.messageReceiver = new Messenger(new TokenRefreshRequestHandler(accessToken, this));
        }

        /* access modifiers changed from: private */
        public void cleanup() {
            if (AccessTokenManager.this.currentTokenRefreshRequest == this) {
                TokenRefreshRequest unused = AccessTokenManager.this.currentTokenRefreshRequest = null;
            }
        }

        private void refreshToken() {
            Bundle bundle = new Bundle();
            bundle.putString("access_token", AccessTokenManager.this.getCurrentAccessToken().getToken());
            Message obtain = Message.obtain();
            obtain.setData(bundle);
            obtain.replyTo = this.messageReceiver;
            try {
                this.messageSender.send(obtain);
            } catch (RemoteException e) {
                cleanup();
            }
        }

        public void bind() {
            Context applicationContext = FacebookSdk.getApplicationContext();
            Intent createTokenRefreshIntent = NativeProtocol.createTokenRefreshIntent(applicationContext);
            if (createTokenRefreshIntent == null || !applicationContext.bindService(createTokenRefreshIntent, this, 1)) {
                cleanup();
            } else {
                Date unused = AccessTokenManager.this.lastAttemptedTokenExtendDate = new Date();
            }
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.messageSender = new Messenger(iBinder);
            refreshToken();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            cleanup();
            try {
                FacebookSdk.getApplicationContext().unbindService(this);
            } catch (IllegalArgumentException e) {
            }
        }
    }

    static class TokenRefreshRequestHandler extends Handler {
        private AccessToken accessToken;
        private TokenRefreshRequest tokenRefreshRequest;

        TokenRefreshRequestHandler(AccessToken accessToken2, TokenRefreshRequest tokenRefreshRequest2) {
            super(Looper.getMainLooper());
            this.accessToken = accessToken2;
            this.tokenRefreshRequest = tokenRefreshRequest2;
        }

        public void handleMessage(Message message) {
            AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
            if (!(currentAccessToken == null || !currentAccessToken.equals(this.accessToken) || message.getData().getString("access_token") == null)) {
                AccessToken.setCurrentAccessToken(AccessToken.createFromRefresh(this.accessToken, message.getData()));
            }
            FacebookSdk.getApplicationContext().unbindService(this.tokenRefreshRequest);
            this.tokenRefreshRequest.cleanup();
        }
    }

    AccessTokenManager(LocalBroadcastManager localBroadcastManager2, AccessTokenCache accessTokenCache2) {
        Validate.notNull(localBroadcastManager2, "localBroadcastManager");
        Validate.notNull(accessTokenCache2, "accessTokenCache");
        this.localBroadcastManager = localBroadcastManager2;
        this.accessTokenCache = accessTokenCache2;
    }

    static AccessTokenManager getInstance() {
        if (instance == null) {
            synchronized (AccessTokenManager.class) {
                if (instance == null) {
                    instance = new AccessTokenManager(LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext()), new AccessTokenCache());
                }
            }
        }
        return instance;
    }

    private void sendCurrentAccessTokenChangedBroadcast(AccessToken accessToken, AccessToken accessToken2) {
        Intent intent = new Intent(ACTION_CURRENT_ACCESS_TOKEN_CHANGED);
        intent.putExtra(EXTRA_OLD_ACCESS_TOKEN, accessToken);
        intent.putExtra(EXTRA_NEW_ACCESS_TOKEN, accessToken2);
        this.localBroadcastManager.sendBroadcast(intent);
    }

    private void setCurrentAccessToken(AccessToken accessToken, boolean z) {
        AccessToken accessToken2 = this.currentAccessToken;
        this.currentAccessToken = accessToken;
        this.currentTokenRefreshRequest = null;
        this.lastAttemptedTokenExtendDate = new Date(0);
        if (z) {
            if (accessToken != null) {
                this.accessTokenCache.save(accessToken);
            } else {
                this.accessTokenCache.clear();
            }
        }
        if (!Utility.areObjectsEqual(accessToken2, accessToken)) {
            sendCurrentAccessTokenChangedBroadcast(accessToken2, accessToken);
        }
    }

    private boolean shouldExtendAccessToken() {
        if (this.currentAccessToken == null || this.currentTokenRefreshRequest != null) {
            return false;
        }
        Long valueOf = Long.valueOf(new Date().getTime());
        return this.currentAccessToken.getSource().canExtendToken() && valueOf.longValue() - this.lastAttemptedTokenExtendDate.getTime() > 3600000 && valueOf.longValue() - this.currentAccessToken.getLastRefresh().getTime() > 86400000;
    }

    /* access modifiers changed from: package-private */
    public void extendAccessTokenIfNeeded() {
        if (shouldExtendAccessToken()) {
            this.currentTokenRefreshRequest = new TokenRefreshRequest(this.currentAccessToken);
            this.currentTokenRefreshRequest.bind();
        }
    }

    /* access modifiers changed from: package-private */
    public AccessToken getCurrentAccessToken() {
        return this.currentAccessToken;
    }

    /* access modifiers changed from: package-private */
    public boolean loadCurrentAccessToken() {
        AccessToken load = this.accessTokenCache.load();
        if (load == null) {
            return false;
        }
        setCurrentAccessToken(load, false);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void setCurrentAccessToken(AccessToken accessToken) {
        setCurrentAccessToken(accessToken, true);
    }
}
