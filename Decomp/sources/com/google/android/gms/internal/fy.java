package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Notifications;

public final class fy implements Notifications {
    public void clear(GoogleApiClient googleApiClient, int i) {
        Games.j(googleApiClient).clearNotifications(i);
    }

    public void clearAll(GoogleApiClient googleApiClient) {
        clear(googleApiClient, -1);
    }
}
