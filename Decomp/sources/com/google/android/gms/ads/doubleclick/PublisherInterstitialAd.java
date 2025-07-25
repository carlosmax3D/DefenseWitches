package com.google.android.gms.ads.doubleclick;

import android.content.Context;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.internal.ah;

public final class PublisherInterstitialAd {
    private final ah ea;

    public PublisherInterstitialAd(Context context) {
        this.ea = new ah(context);
    }

    public AdListener getAdListener() {
        return this.ea.getAdListener();
    }

    public String getAdUnitId() {
        return this.ea.getAdUnitId();
    }

    public AppEventListener getAppEventListener() {
        return this.ea.getAppEventListener();
    }

    public boolean isLoaded() {
        return this.ea.isLoaded();
    }

    public void loadAd(PublisherAdRequest publisherAdRequest) {
        this.ea.a(publisherAdRequest.v());
    }

    public void setAdListener(AdListener adListener) {
        this.ea.setAdListener(adListener);
    }

    public void setAdUnitId(String str) {
        this.ea.setAdUnitId(str);
    }

    public void setAppEventListener(AppEventListener appEventListener) {
        this.ea.setAppEventListener(appEventListener);
    }

    public void show() {
        this.ea.show();
    }
}
