package com.google.android.gms.wallet;

import android.app.Activity;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.internal.iu;

@Deprecated
public class WalletClient implements GooglePlayServicesClient {
    private final iu Hj;

    public WalletClient(Activity activity, int i, String str, int i2, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.Hj = new iu(activity, connectionCallbacks, onConnectionFailedListener, i, str, i2);
    }

    public WalletClient(Activity activity, int i, String str, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this(activity, i, str, 0, connectionCallbacks, onConnectionFailedListener);
    }

    public void changeMaskedWallet(String str, String str2, int i) {
        this.Hj.changeMaskedWallet(str, str2, i);
    }

    public void checkForPreAuthorization(int i) {
        this.Hj.checkForPreAuthorization(i);
    }

    public void connect() {
        this.Hj.connect();
    }

    public void disconnect() {
        this.Hj.disconnect();
    }

    public boolean isConnected() {
        return this.Hj.isConnected();
    }

    public boolean isConnecting() {
        return this.Hj.isConnecting();
    }

    public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        return this.Hj.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.Hj.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    public void loadFullWallet(FullWalletRequest fullWalletRequest, int i) {
        this.Hj.loadFullWallet(fullWalletRequest, i);
    }

    public void loadMaskedWallet(MaskedWalletRequest maskedWalletRequest, int i) {
        this.Hj.loadMaskedWallet(maskedWalletRequest, i);
    }

    public void notifyTransactionStatus(NotifyTransactionStatusRequest notifyTransactionStatusRequest) {
        this.Hj.notifyTransactionStatus(notifyTransactionStatusRequest);
    }

    public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.Hj.registerConnectionCallbacks(connectionCallbacks);
    }

    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.Hj.registerConnectionFailedListener(onConnectionFailedListener);
    }

    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.Hj.unregisterConnectionCallbacks(connectionCallbacks);
    }

    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.Hj.unregisterConnectionFailedListener(onConnectionFailedListener);
    }
}
