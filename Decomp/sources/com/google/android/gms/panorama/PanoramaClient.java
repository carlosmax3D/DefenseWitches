package com.google.android.gms.panorama;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.hm;
import com.google.android.gms.panorama.Panorama;

public class PanoramaClient implements GooglePlayServicesClient {
    private final hm Di;

    public interface OnPanoramaInfoLoadedListener {
        void onPanoramaInfoLoaded(ConnectionResult connectionResult, Intent intent);
    }

    public PanoramaClient(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.Di = new hm(context, connectionCallbacks, onConnectionFailedListener);
    }

    public void connect() {
        this.Di.connect();
    }

    public void disconnect() {
        this.Di.disconnect();
    }

    public boolean isConnected() {
        return this.Di.isConnected();
    }

    public boolean isConnecting() {
        return this.Di.isConnecting();
    }

    public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        return this.Di.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.Di.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    public void loadPanoramaInfo(final OnPanoramaInfoLoadedListener onPanoramaInfoLoadedListener, Uri uri) {
        this.Di.a(new a.c<Panorama.PanoramaResult>() {
            public void a(Panorama.PanoramaResult panoramaResult) {
                onPanoramaInfoLoadedListener.onPanoramaInfoLoaded(panoramaResult.getStatus().bu(), panoramaResult.getViewerIntent());
            }
        }, uri, false);
    }

    public void loadPanoramaInfoAndGrantAccess(final OnPanoramaInfoLoadedListener onPanoramaInfoLoadedListener, Uri uri) {
        this.Di.a(new a.c<Panorama.PanoramaResult>() {
            public void a(Panorama.PanoramaResult panoramaResult) {
                onPanoramaInfoLoadedListener.onPanoramaInfoLoaded(panoramaResult.getStatus().bu(), panoramaResult.getViewerIntent());
            }
        }, uri, true);
    }

    public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.Di.registerConnectionCallbacks(connectionCallbacks);
    }

    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.Di.registerConnectionFailedListener(onConnectionFailedListener);
    }

    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.Di.unregisterConnectionCallbacks(connectionCallbacks);
    }

    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.Di.unregisterConnectionFailedListener(onConnectionFailedListener);
    }
}
