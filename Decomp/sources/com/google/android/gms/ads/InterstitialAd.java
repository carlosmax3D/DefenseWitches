package com.google.android.gms.ads;

import android.content.Context;
import com.google.android.gms.internal.ah;

public final class InterstitialAd {
    private final ah ea;

    public InterstitialAd(Context context) {
        this.ea = new ah(context);
    }

    public AdListener getAdListener() {
        return this.ea.getAdListener();
    }

    public String getAdUnitId() {
        return this.ea.getAdUnitId();
    }

    public boolean isLoaded() {
        return this.ea.isLoaded();
    }

    public void loadAd(AdRequest adRequest) {
        this.ea.a(adRequest.v());
    }

    public void setAdListener(AdListener adListener) {
        this.ea.setAdListener(adListener);
    }

    public void setAdUnitId(String str) {
        this.ea.setAdUnitId(str);
    }

    public void show() {
        this.ea.show();
    }
}
