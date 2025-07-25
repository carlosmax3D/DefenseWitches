package com.tapjoy;

import android.content.Context;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class TapjoyGpsHelper {
    private static final String TAG = "TapjoyGpsHelper";
    private boolean adTrackingEnabled;
    private String advertisingID;
    private Context context;
    private int deviceGpsVersion = 0;
    private boolean isAdIdAvailable;
    private Boolean isGpsAvailable;
    private Boolean isGpsManifestConfigured;
    private int packagedGpsVersion = 0;

    public TapjoyGpsHelper(Context context2) {
        this.context = context2;
    }

    public void checkGooglePlayIntegration() throws TapjoyIntegrationException {
        if (!isGooglePlayServicesAvailable()) {
            throw new TapjoyIntegrationException("Tapjoy SDK is disabled because Google Play Services was not found. For more information about including the Google Play services client library visit http://developer.android.com/google/play-services/setup.html or http://tech.tapjoy.com/product-overview/sdk-change-log/tapjoy-and-identifiers");
        } else if (!isGooglePlayManifestConfigured()) {
            throw new TapjoyIntegrationException("Failed to load manifest.xml meta-data, 'com.google.android.gms.version' not found. For more information about including the Google Play services client library visit http://developer.android.com/google/play-services/setup.html or http://tech.tapjoy.com/product-overview/sdk-change-log/tapjoy-and-identifiers");
        }
    }

    public String getAdvertisingId() {
        return this.advertisingID;
    }

    public int getDeviceGooglePlayServicesVersion() {
        return this.deviceGpsVersion;
    }

    public int getPackagedGooglePlayServicesVersion() {
        return this.packagedGpsVersion;
    }

    public boolean isAdIdAvailable() {
        return this.isAdIdAvailable;
    }

    public boolean isAdTrackingEnabled() {
        return this.adTrackingEnabled;
    }

    public boolean isGooglePlayManifestConfigured() {
        if (this.isGpsManifestConfigured == null) {
            try {
                this.packagedGpsVersion = this.context.getPackageManager().getApplicationInfo(this.context.getPackageName(), 128).metaData.getInt("com.google.android.gms.version");
                this.isGpsManifestConfigured = true;
            } catch (Exception e) {
                this.isGpsManifestConfigured = false;
            }
        }
        return this.isGpsManifestConfigured.booleanValue();
    }

    public boolean isGooglePlayServicesAvailable() {
        if (this.isGpsAvailable == null) {
            try {
                this.context.getClassLoader().loadClass("com.google.android.gms.ads.identifier.AdvertisingIdClient");
                this.isGpsAvailable = true;
            } catch (Exception e) {
                this.isGpsAvailable = false;
            } catch (Error e2) {
                this.isGpsAvailable = false;
            }
        }
        return this.isGpsAvailable.booleanValue();
    }

    public void loadAdvertisingId() {
        TapjoyLog.i(TAG, "Looking for Google Play Services...");
        if (!isGooglePlayServicesAvailable() || !isGooglePlayManifestConfigured()) {
            TapjoyLog.i(TAG, "Google Play Services not found");
            return;
        }
        TapjoyLog.i(TAG, "Packaged Google Play Services found, fetching advertisingID...");
        TapjoyLog.i(TAG, "Packaged Google Play Services version: " + this.packagedGpsVersion);
        TapjoyAdIdClient tapjoyAdIdClient = new TapjoyAdIdClient(this.context);
        this.isAdIdAvailable = tapjoyAdIdClient.setupAdIdInfo();
        try {
            this.deviceGpsVersion = this.context.getPackageManager().getPackageInfo(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, 0).versionCode;
            TapjoyLog.i(TAG, "Device's Google Play Services version: " + this.deviceGpsVersion);
        } catch (Exception e) {
            TapjoyLog.i(TAG, "Error getting device's Google Play Services version");
        }
        if (this.isAdIdAvailable) {
            this.adTrackingEnabled = tapjoyAdIdClient.isAdTrackingEnabled();
            this.advertisingID = tapjoyAdIdClient.getAdvertisingId();
            TapjoyLog.i(TAG, "Found advertising ID: " + this.advertisingID);
            TapjoyLog.i(TAG, "Is ad tracking enabled: " + Boolean.toString(this.adTrackingEnabled));
            return;
        }
        TapjoyLog.i(TAG, "Error getting advertisingID from Google Play Services");
    }
}
