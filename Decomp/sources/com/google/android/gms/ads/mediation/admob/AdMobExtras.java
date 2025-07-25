package com.google.android.gms.ads.mediation.admob;

import android.os.Bundle;
import com.google.ads.mediation.NetworkExtras;

public final class AdMobExtras implements NetworkExtras {
    private final Bundle jh;

    public AdMobExtras(Bundle bundle) {
        this.jh = bundle != null ? new Bundle(bundle) : null;
    }

    public Bundle getExtras() {
        return this.jh;
    }
}
