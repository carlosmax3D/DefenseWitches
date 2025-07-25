package com.google.android.gms.location;

import android.app.PendingIntent;
import android.content.Context;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.internal.gn;

public class ActivityRecognitionClient implements GooglePlayServicesClient {
    private final gn xl;

    public ActivityRecognitionClient(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.xl = new gn(context, connectionCallbacks, onConnectionFailedListener, "activity_recognition");
    }

    public void connect() {
        this.xl.connect();
    }

    public void disconnect() {
        this.xl.disconnect();
    }

    public boolean isConnected() {
        return this.xl.isConnected();
    }

    public boolean isConnecting() {
        return this.xl.isConnecting();
    }

    public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        return this.xl.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.xl.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.xl.registerConnectionCallbacks(connectionCallbacks);
    }

    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.xl.registerConnectionFailedListener(onConnectionFailedListener);
    }

    public void removeActivityUpdates(PendingIntent pendingIntent) {
        this.xl.removeActivityUpdates(pendingIntent);
    }

    public void requestActivityUpdates(long j, PendingIntent pendingIntent) {
        this.xl.requestActivityUpdates(j, pendingIntent);
    }

    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.xl.unregisterConnectionCallbacks(connectionCallbacks);
    }

    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.xl.unregisterConnectionFailedListener(onConnectionFailedListener);
    }
}
