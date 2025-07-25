package com.google.android.gms.location;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Looper;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.internal.eg;
import com.google.android.gms.internal.gn;
import com.google.android.gms.internal.go;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocationClient implements GooglePlayServicesClient {
    public static final String KEY_LOCATION_CHANGED = "com.google.android.location.LOCATION";
    public static final String KEY_MOCK_LOCATION = "mockLocation";
    private final gn xl;

    public interface OnAddGeofencesResultListener {
        void onAddGeofencesResult(int i, String[] strArr);
    }

    public interface OnRemoveGeofencesResultListener {
        void onRemoveGeofencesByPendingIntentResult(int i, PendingIntent pendingIntent);

        void onRemoveGeofencesByRequestIdsResult(int i, String[] strArr);
    }

    public LocationClient(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.xl = new gn(context, connectionCallbacks, onConnectionFailedListener, "location");
    }

    public static int getErrorCode(Intent intent) {
        return intent.getIntExtra("gms_error_code", -1);
    }

    public static int getGeofenceTransition(Intent intent) {
        int intExtra = intent.getIntExtra("com.google.android.location.intent.extra.transition", -1);
        if (intExtra == -1) {
            return -1;
        }
        if (intExtra == 1 || intExtra == 2 || intExtra == 4) {
            return intExtra;
        }
        return -1;
    }

    public static List<Geofence> getTriggeringGeofences(Intent intent) {
        ArrayList arrayList = (ArrayList) intent.getSerializableExtra("com.google.android.location.intent.extra.geofence_list");
        if (arrayList == null) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(go.f((byte[]) it.next()));
        }
        return arrayList2;
    }

    public static boolean hasError(Intent intent) {
        return intent.hasExtra("gms_error_code");
    }

    public void addGeofences(List<Geofence> list, PendingIntent pendingIntent, OnAddGeofencesResultListener onAddGeofencesResultListener) {
        ArrayList arrayList = null;
        if (list != null) {
            ArrayList arrayList2 = new ArrayList();
            for (Geofence next : list) {
                eg.b(next instanceof go, (Object) "Geofence must be created using Geofence.Builder.");
                arrayList2.add((go) next);
            }
            arrayList = arrayList2;
        }
        this.xl.addGeofences(arrayList, pendingIntent, onAddGeofencesResultListener);
    }

    public void connect() {
        this.xl.connect();
    }

    public void disconnect() {
        this.xl.disconnect();
    }

    public Location getLastLocation() {
        return this.xl.getLastLocation();
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

    public void removeGeofences(PendingIntent pendingIntent, OnRemoveGeofencesResultListener onRemoveGeofencesResultListener) {
        this.xl.removeGeofences(pendingIntent, onRemoveGeofencesResultListener);
    }

    public void removeGeofences(List<String> list, OnRemoveGeofencesResultListener onRemoveGeofencesResultListener) {
        this.xl.removeGeofences(list, onRemoveGeofencesResultListener);
    }

    public void removeLocationUpdates(PendingIntent pendingIntent) {
        this.xl.removeLocationUpdates(pendingIntent);
    }

    public void removeLocationUpdates(LocationListener locationListener) {
        this.xl.removeLocationUpdates(locationListener);
    }

    public void requestLocationUpdates(LocationRequest locationRequest, PendingIntent pendingIntent) {
        this.xl.requestLocationUpdates(locationRequest, pendingIntent);
    }

    public void requestLocationUpdates(LocationRequest locationRequest, LocationListener locationListener) {
        this.xl.requestLocationUpdates(locationRequest, locationListener);
    }

    public void requestLocationUpdates(LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
        this.xl.requestLocationUpdates(locationRequest, locationListener, looper);
    }

    public void setMockLocation(Location location) {
        this.xl.setMockLocation(location);
    }

    public void setMockMode(boolean z) {
        this.xl.setMockMode(z);
    }

    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.xl.unregisterConnectionCallbacks(connectionCallbacks);
    }

    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.xl.unregisterConnectionFailedListener(onConnectionFailedListener);
    }
}
