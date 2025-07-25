package com.google.android.gms.internal;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.internal.ab;

public final class t extends ab.a {
    private final AdListener ev;

    public t(AdListener adListener) {
        this.ev = adListener;
    }

    public void onAdClosed() {
        this.ev.onAdClosed();
    }

    public void onAdFailedToLoad(int i) {
        this.ev.onAdFailedToLoad(i);
    }

    public void onAdLeftApplication() {
        this.ev.onAdLeftApplication();
    }

    public void onAdLoaded() {
        this.ev.onAdLoaded();
    }

    public void onAdOpened() {
        this.ev.onAdOpened();
    }
}
