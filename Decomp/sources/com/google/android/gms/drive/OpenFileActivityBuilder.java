package com.google.android.gms.drive;

import android.content.IntentSender;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.internal.OpenFileIntentSenderRequest;
import com.google.android.gms.drive.internal.j;
import com.google.android.gms.internal.eg;

public class OpenFileActivityBuilder {
    public static final String EXTRA_RESPONSE_DRIVE_ID = "response_drive_id";
    private String qL;
    private DriveId qM;
    private String[] qW;

    public IntentSender build(GoogleApiClient googleApiClient) {
        eg.b(this.qW, (Object) "setMimeType(String[]) must be called on this builder before calling build()");
        eg.a(googleApiClient.isConnected(), "Client must be connected");
        try {
            return ((j) googleApiClient.a(Drive.jO)).cN().a(new OpenFileIntentSenderRequest(this.qL, this.qW, this.qM));
        } catch (RemoteException e) {
            throw new RuntimeException("Unable to connect Drive Play Service", e);
        }
    }

    public OpenFileActivityBuilder setActivityStartFolder(DriveId driveId) {
        this.qM = (DriveId) eg.f(driveId);
        return this;
    }

    public OpenFileActivityBuilder setActivityTitle(String str) {
        this.qL = (String) eg.f(str);
        return this;
    }

    public OpenFileActivityBuilder setMimeType(String[] strArr) {
        eg.b(strArr != null && strArr.length > 0, (Object) "mimeTypes may not be null and must contain at least one value");
        this.qW = strArr;
        return this;
    }
}
