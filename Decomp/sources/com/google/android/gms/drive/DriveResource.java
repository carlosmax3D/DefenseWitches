package com.google.android.gms.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;

public interface DriveResource {

    public interface MetadataResult extends Result {
        Metadata getMetadata();
    }

    DriveId getDriveId();

    PendingResult<MetadataResult> getMetadata(GoogleApiClient googleApiClient);

    PendingResult<MetadataResult> updateMetadata(GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet);
}
