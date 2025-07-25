package com.google.ads.mediation.customevent;

import android.app.Activity;
import android.view.View;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.google.android.gms.ads.mediation.customevent.CustomEventExtras;
import com.google.android.gms.internal.ct;

public final class CustomEventAdapter implements MediationBannerAdapter<CustomEventExtras, CustomEventServerParameters>, MediationInterstitialAdapter<CustomEventExtras, CustomEventServerParameters> {
    private View m;
    private CustomEventBanner n;
    private CustomEventInterstitial o;

    private static final class a implements CustomEventBannerListener {
        private final MediationBannerListener k;
        private final CustomEventAdapter p;

        public a(CustomEventAdapter customEventAdapter, MediationBannerListener mediationBannerListener) {
            this.p = customEventAdapter;
            this.k = mediationBannerListener;
        }

        public void onClick() {
            ct.r("Custom event adapter called onFailedToReceiveAd.");
            this.k.onClick(this.p);
        }

        public void onDismissScreen() {
            ct.r("Custom event adapter called onFailedToReceiveAd.");
            this.k.onDismissScreen(this.p);
        }

        public void onFailedToReceiveAd() {
            ct.r("Custom event adapter called onFailedToReceiveAd.");
            this.k.onFailedToReceiveAd(this.p, AdRequest.ErrorCode.NO_FILL);
        }

        public void onLeaveApplication() {
            ct.r("Custom event adapter called onFailedToReceiveAd.");
            this.k.onLeaveApplication(this.p);
        }

        public void onPresentScreen() {
            ct.r("Custom event adapter called onFailedToReceiveAd.");
            this.k.onPresentScreen(this.p);
        }

        public void onReceivedAd(View view) {
            ct.r("Custom event adapter called onReceivedAd.");
            this.p.a(view);
            this.k.onReceivedAd(this.p);
        }
    }

    private class b implements CustomEventInterstitialListener {
        private final MediationInterstitialListener l;
        private final CustomEventAdapter p;

        public b(CustomEventAdapter customEventAdapter, MediationInterstitialListener mediationInterstitialListener) {
            this.p = customEventAdapter;
            this.l = mediationInterstitialListener;
        }

        public void onDismissScreen() {
            ct.r("Custom event adapter called onDismissScreen.");
            this.l.onDismissScreen(this.p);
        }

        public void onFailedToReceiveAd() {
            ct.r("Custom event adapter called onFailedToReceiveAd.");
            this.l.onFailedToReceiveAd(this.p, AdRequest.ErrorCode.NO_FILL);
        }

        public void onLeaveApplication() {
            ct.r("Custom event adapter called onLeaveApplication.");
            this.l.onLeaveApplication(this.p);
        }

        public void onPresentScreen() {
            ct.r("Custom event adapter called onPresentScreen.");
            this.l.onPresentScreen(this.p);
        }

        public void onReceivedAd() {
            ct.r("Custom event adapter called onReceivedAd.");
            this.l.onReceivedAd(CustomEventAdapter.this);
        }
    }

    private static <T> T a(String str) {
        try {
            return Class.forName(str).newInstance();
        } catch (Throwable th) {
            ct.v("Could not instantiate custom event adapter: " + str + ". " + th.getMessage());
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void a(View view) {
        this.m = view;
    }

    public void destroy() {
        if (this.n != null) {
            this.n.destroy();
        }
        if (this.o != null) {
            this.o.destroy();
        }
    }

    public Class<CustomEventExtras> getAdditionalParametersType() {
        return CustomEventExtras.class;
    }

    public View getBannerView() {
        return this.m;
    }

    public Class<CustomEventServerParameters> getServerParametersType() {
        return CustomEventServerParameters.class;
    }

    public void requestBannerAd(MediationBannerListener mediationBannerListener, Activity activity, CustomEventServerParameters customEventServerParameters, AdSize adSize, MediationAdRequest mediationAdRequest, CustomEventExtras customEventExtras) {
        this.n = (CustomEventBanner) a(customEventServerParameters.className);
        if (this.n == null) {
            mediationBannerListener.onFailedToReceiveAd(this, AdRequest.ErrorCode.INTERNAL_ERROR);
        } else {
            this.n.requestBannerAd(new a(this, mediationBannerListener), activity, customEventServerParameters.label, customEventServerParameters.parameter, adSize, mediationAdRequest, customEventExtras == null ? null : customEventExtras.getExtra(customEventServerParameters.label));
        }
    }

    public void requestInterstitialAd(MediationInterstitialListener mediationInterstitialListener, Activity activity, CustomEventServerParameters customEventServerParameters, MediationAdRequest mediationAdRequest, CustomEventExtras customEventExtras) {
        this.o = (CustomEventInterstitial) a(customEventServerParameters.className);
        if (this.o == null) {
            mediationInterstitialListener.onFailedToReceiveAd(this, AdRequest.ErrorCode.INTERNAL_ERROR);
        } else {
            this.o.requestInterstitialAd(new b(this, mediationInterstitialListener), activity, customEventServerParameters.label, customEventServerParameters.parameter, mediationAdRequest, customEventExtras == null ? null : customEventExtras.getExtra(customEventServerParameters.label));
        }
    }

    public void showInterstitial() {
        this.o.showInterstitial();
    }
}
